package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.IndividualLesson
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.utils.toNotNullable
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.tables.IndividualLessonsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsSubjectsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsTable
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import javax.money.MonetaryAmount
import kotlin.time.Duration
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll

class LessonsDao private constructor(
    db: Database
): Dao<IndividualLesson, LessonsDao.IData>(
    db,
    listOf(
        SubjectsTable,
        StudentsTable,
        StudentsSubjectsTable,
        IndividualLessonsTable
    )
) {
    override fun Transaction.onInsert(data: IData): IndividualLesson {
        val id = IndividualLessonsTable.insert {
            it[IndividualLessonsTable.name] = data.name.value
            it[IndividualLessonsTable.dateOfStart] = data.dateOfStart
            it[IndividualLessonsTable.duration] = data.duration
            it[IndividualLessonsTable.hourCost] = data.hourCost
            it[IndividualLessonsTable.description] = data.description
            it[IndividualLessonsTable.studentId] = data.student.id
            it[IndividualLessonsTable.subjectId] = data.subject.id
        } get IndividualLessonsTable.id
        return IndividualLesson(
            id.value,
            data.name,
            data.dateOfStart,
            data.duration,
            data.hourCost,
            data.description,
            data.student,
            data.subject,
            data.isConducted
        )
    }

    override fun Transaction.onGetById(id: Long): IndividualLesson? {
        val results = IndividualLessonsTable
            .selectAll()
            .where { IndividualLessonsTable.id eq id }
            .map { row ->
                row.loadIndividualLesson(id)
            }
        return if (results.isEmpty()) null else results.first()
    }

    override fun Transaction.onUpdate(model: IndividualLesson) {
        TODO("Not yet implemented")
    }

    override fun Transaction.onRemove(model: IndividualLesson) {
        IndividualLessonsTable.deleteWhere { IndividualLessonsTable.id eq model.id }
    }

    override fun Transaction.onLoadAll(): List<IndividualLesson> = IndividualLessonsTable
        .selectAll()
        .map { row ->
            val id = row[IndividualLessonsTable.id]
            row.loadIndividualLesson(id.value)
        }.toNotNullable()

    data class IData(
        val name: Name,
        val dateOfStart: LocalDateTime,
        val duration: Duration,
        val hourCost: MonetaryAmount,
        val description: String,
        val student: Student,
        val subject: Subject,
        val isConducted: Boolean
    ) : InsertData<IndividualLesson>

    companion object : DaoBuilder<IndividualLesson, IData, LessonsDao>() {
        override fun new(db: Database): LessonsDao = instances.getOrPut(db) { LessonsDao(db) }
    }
}