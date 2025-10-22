package com.isuponev.tutordb.desktop.database.columns

import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.operations.regex
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.stringLiteral

fun Table.name(columnName: String): Column<Name> {
    return registerColumn<Name>(columnName, NameColumnType()).check { column ->
        column regex stringLiteral(Name.NAME_REGEX.pattern)
    }
}