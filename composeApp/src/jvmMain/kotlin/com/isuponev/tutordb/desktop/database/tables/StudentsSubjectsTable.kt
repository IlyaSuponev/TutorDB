package com.isuponev.tutordb.desktop.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object StudentsSubjectsTable : LongIdTable("students_subjects") {
    val studentId = reference(
        "student_id",
        StudentsTable.id,
        onDelete = ReferenceOption.CASCADE
    )
    val subjectId = reference(
        "subject_id",
        SubjectsTable.id,
        onDelete = ReferenceOption.CASCADE
    )
}