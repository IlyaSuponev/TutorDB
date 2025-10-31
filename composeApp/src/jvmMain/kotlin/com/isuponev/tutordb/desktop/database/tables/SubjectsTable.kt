package com.isuponev.tutordb.desktop.database.tables

import com.isuponev.tutordb.core.models.values.Name
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.regexp
import org.jetbrains.exposed.v1.core.stringLiteral

/**
 * A database table definition for the `subjects` table.
 *
 * This object represents the schema of the `subjects` table in the database using Exposed's DSL.
 * It extends [LongIdTable], indicating that the primary key is a long integer.
 */
object SubjectsTable : LongIdTable(name = "subjects") {
    /**
     * The `name` column in the `subjects` table.
     *
     * - Stores the name of the subject as text.
     * - Enforces uniqueness via a unique index.
     * - Validates that the name conforms to a specific regular expression pattern defined in [Name.NAME_REGEX].
     */
    val name = text("name")
        .uniqueIndex()
        .check { column ->
            column.regexp(stringLiteral(Name.NAME_REGEX.pattern))
        }

    /**
     * The `description` column in the `subjects` table.
     *
     * - Stores an optional description of the subject as text.
     * - Has a default value of an empty string if not provided.
     */
    val description = text("description")
        .default("")
}
