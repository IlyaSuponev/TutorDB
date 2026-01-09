package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.IndividualLesson
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.tables.IndividualLessonsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsSubjectsTable
import com.isuponev.tutordb.desktop.database.tables.StudentsTable
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.selectAll

internal fun ResultRow.loadStudent(id: Long): Student {
    val subjects = StudentsSubjectsTable
        .selectAll()
        .where { StudentsSubjectsTable.studentId eq id }.flatMap { linkRow ->
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
    return Student(
        id,
        Name.of(this[StudentsTable.name]),
        this[StudentsTable.hourCost],
        subjects.toSet()
    )
}

internal fun ResultRow.loadSubject(id: Long) = Subject(
    id = id,
    name = Name.of(this[SubjectsTable.name]),
    description = this[SubjectsTable.description]
)

internal fun ResultRow.loadIndividualLesson(id: Long): IndividualLesson? {
    val studentId = this[IndividualLessonsTable.studentId]
    val students = StudentsTable
        .selectAll()
        .where { StudentsTable.id eq studentId }
        .map { studentRow ->
            studentRow.loadStudent(studentId.value)
        }
    return if (students.isEmpty()) null
    else {
        val subjectId = this[IndividualLessonsTable.subjectId]
        val subjects = SubjectsTable
            .selectAll()
            .where { SubjectsTable.id eq subjectId }
            .map { subjectRow ->
                subjectRow.loadSubject(subjectId.value)
            }
        if (subjects.isEmpty()) null
        else {
            IndividualLesson(
                id,
                Name.of(this[IndividualLessonsTable.name]),
                this[IndividualLessonsTable.dateOfStart],
                this[IndividualLessonsTable.duration],
                this[IndividualLessonsTable.hourCost],
                this[IndividualLessonsTable.description],
                students.first(),
                subjects.first(),
                this[IndividualLessonsTable.isConducted]
            )
        }
    }
}