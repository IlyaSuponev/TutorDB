package com.isuponev.tutordb.desktop.database.tables

import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.operations.regex
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.stringLiteral

object SubjectsTable : LongIdTable(name = "subjects") {
    val name = text("name").check { column ->
        column regex stringLiteral(Name.NAME_REGEX.pattern)
    }
    val description = text("description").default("")
}
