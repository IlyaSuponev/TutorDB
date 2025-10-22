package com.isuponev.tutordb.desktop.database.tables

import com.isuponev.tutordb.desktop.database.columns.name
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object SubjectsTable : UUIDTable(name = "subjects") {
    val name = name("name")
    val description = text("description").default("")
}
