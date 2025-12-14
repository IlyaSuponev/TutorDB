package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.tables.StudentsSubjectsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsTable
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import javax.money.MonetaryAmount
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update

class StudentsDao private constructor(
    db: Database
) : Dao<Student, StudentsDao.IData>(
    db,
    listOf(SubjectsTable, StudentsSubjectsTable, StudentsTable)
) {

    override fun Transaction.onInsert(data: IData): Student {
        val id = StudentsTable.insert {
            it[name] = data.name.value
            it[hourCost] = data.hourCost
        } get StudentsTable.id
        data.subjects.forEach { subject ->
            StudentsSubjectsTable.insert {
                it[studentId] = id.value
                it[subjectId] = subject.id
            }
        }
        return Student(id.value, data.name, data.hourCost, data.subjects)
    }

    override fun Transaction.onGetById(id: Long): Student? {
        val subjects = StudentsSubjectsTable.selectAll().where { StudentsSubjectsTable.studentId eq id }.flatMap { row: ResultRow ->
            val subjectId = row[StudentsSubjectsTable.subjectId]
            SubjectsTable
                .selectAll()
                .where { SubjectsTable.id eq subjectId }
                .map { subjectRow ->
                    Subject(
                        subjectRow[SubjectsTable.id].value,
                        Name.of(subjectRow[SubjectsTable.name]),
                        subjectRow[SubjectsTable.description]
                    )
                }
        }
        val results = StudentsTable.selectAll().where { StudentsTable.id eq id }.map { row: ResultRow ->
            Student(id, Name.of(row[StudentsTable.name]), row[StudentsTable.hourCost], subjects.toSet())
        }
        return if (results.isEmpty()) null else results.first()
    }

    override fun Transaction.onUpdate(model: Student) {
        StudentsTable.update(
            where = { StudentsTable.id eq model.id }
        ) {
            it[StudentsTable.name] = model.name.value
            it[StudentsTable.hourCost] = model.hourCost
        }
        val oldSubjectsIds = StudentsSubjectsTable.selectAll()
            .where { StudentsSubjectsTable.studentId eq model.id }
            .map { it[StudentsSubjectsTable.subjectId].value }.toSet()
        val nowSubjectsIds = model.subjects.map { it.id }.toSet()
        val deletedSubjects = oldSubjectsIds - nowSubjectsIds
        val addedSubjects = nowSubjectsIds - oldSubjectsIds
        deletedSubjects.forEach { subjectId ->
            StudentsSubjectsTable.deleteWhere {
                (StudentsSubjectsTable.studentId eq model.id) and (StudentsSubjectsTable.subjectId eq subjectId)
            }
        }
        addedSubjects.forEach { subjectId ->
            StudentsSubjectsTable.insert {
                it[StudentsSubjectsTable.studentId] = model.id
                it[StudentsSubjectsTable.subjectId] = subjectId
            }
        }
    }

    override fun Transaction.onRemove(model: Student) {
        StudentsTable.deleteWhere { StudentsTable.id eq model.id }
        StudentsSubjectsTable.deleteWhere { StudentsSubjectsTable.studentId eq model.id }
    }

    override fun Transaction.onLoadAll(): List<Student> = StudentsTable
        .selectAll()
        .map { row ->
            val studentId = row[StudentsTable.id].value
            val subjects = StudentsSubjectsTable
                .selectAll()
                .where { StudentsSubjectsTable.studentId eq studentId }.flatMap { linkRow ->
                    val subjectId = linkRow[StudentsSubjectsTable.subjectId].value
                    SubjectsTable
                        .selectAll()
                        .where { SubjectsTable.id eq subjectId }
                        .map { subjectRow ->
                            Subject(
                                subjectRow[SubjectsTable.id].value,
                                Name.of(subjectRow[SubjectsTable.name]),
                                subjectRow[SubjectsTable.description]
                            )
                        }
                }
            Student(
                studentId,
                Name.of(row[StudentsTable.name]),
                row[StudentsTable.hourCost],
                subjects.toSet()
            )
        }

    data class IData(val name: Name, val hourCost: MonetaryAmount, val subjects: Set<Subject>) : InsertData<Student>

    companion object : DaoBuilder<Student, IData, StudentsDao>() {
        override fun new(db: Database): StudentsDao = instances.getOrPut(db) { StudentsDao(db) }
    }
}