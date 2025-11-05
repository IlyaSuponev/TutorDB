package com.isuponev.tutordb.desktop.database

import com.isuponev.tutordb.core.models.values.Name
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.regexp

/**
 * Creates and configures a database column for storing a name with specific constraints.
 *
 * This function defines a text column named "name" with two constraints:
 * - Ensures all values are unique via a unique index
 * - Validates that values match the pattern defined in [Name.NAME_REGEX]
 *
 * The column is typically used in table definitions where name fields require
 * both uniqueness and format validation (e.g., student names, subject names).
 *
 * @return A configured [Column<String>] with uniqueness and regex validation constraints
 */
fun Table.name(): Column<String> = text("name")
    .check {
        it regexp Name.NAME_REGEX.pattern
    }