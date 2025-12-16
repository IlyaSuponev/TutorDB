package com.isuponev.tutordb.desktop.database.tables

import com.isuponev.tutordb.desktop.database.name
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.datetime.duration
import org.jetbrains.exposed.v1.money.compositeMoney

object IndividualLessonsTable : LongIdTable("individual_lessons") {
    val name = name()
    val dateOfStart = datetime("dateOfStart")
    val duration = duration("duration")
    val hourCost = compositeMoney(
        19,
        2,
        "hour_cost_amount",
        "hour_cost_currency"
    )
    val description = text("description").default("")
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
