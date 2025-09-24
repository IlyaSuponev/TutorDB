package com.isuponev.tutordb.core.database.columns

import com.isuponev.tutordb.core.models.values.Name
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.vendors.currentDialect

/**
 * A custom column type for storing and retrieving [Name] values in the database.
 *
 * This column type provides seamless mapping between the [Name] value class and
 * database text columns, ensuring type safety and validation consistency.
 *
 * @see Name for validation rules and format requirements
 * @see ColumnType for base functionality
 */
class NameColumnType : ColumnType<Name>() {
    override fun sqlType(): String =  currentDialect.dataTypeProvider.textType()

    @Throws(IllegalArgumentException::class)
    override fun valueFromDB(value: Any): Name = when (value) {
        is String -> Name.of(value)
        is Name -> value
        else -> throw IllegalArgumentException("Unexpected value of type ${value.javaClass} for Name column")
    }
}
