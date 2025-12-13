package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.tables.StudentsSubjectsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsTable
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import javax.money.MonetaryAmount
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll

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
        println(id)
        data.subjects.forEach { subject ->
            StudentsSubjectsTable.insert {
                it[studentId] = id
                it[subjectId] = subject.id
            }
        }
        return Student(id.value, data.name, data.hourCost)
    }

    override fun Transaction.onGetById(id: Long): Student? {
        TODO("Not yet implemented")
    }

    override fun Transaction.onUpdate(model: Student) {
        Result
        TODO("Not yet implemented")
    }

    override fun Transaction.onRemove(model: Student) {
        TODO("Not yet implemented")
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