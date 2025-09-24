package com.isuponev.tutordb.core.database.columns

import com.isuponev.tutordb.core.models.values.PhoneNumber
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.vendors.currentDialect

/**
 * Custom column type for storing and retrieving [PhoneNumber] values in the database.
 *
 * This column type maps the [PhoneNumber] value class to a VARCHAR SQL type
 * and handles conversion between database string representations and domain objects.
 * The phone numbers are stored in their international string format (E.164).
 *
 * The column supports reading values from the database as [String] or pre-converted [PhoneNumber] instances.
 * All stored phone numbers are validated according to international standards.
 *
 * @see ColumnType
 * @see PhoneNumber
 */
class PhoneNumberColumnType : ColumnType<PhoneNumber>() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.varcharType(MAX_PHONE_NUMBER_STRING_VIEW_LENGTH)

    override fun valueFromDB(value: Any): PhoneNumber = when (value) {
        is String -> PhoneNumber.of(value)
        is PhoneNumber -> value
        else -> throw IllegalArgumentException("Unexpected value of type ${value.javaClass} for PhoneNumber column")
    }

    /**
     * Companion object containing constants and utility definitions for the column type.
     */
    companion object {
        /**
         * Maximum length for storing phone number strings in the database.
         *
         * Based on the E.164 standard maximum length for international phone numbers.
         * The E.164 format includes: '+' prefix + country code (1-3 digits) + subscriber number (max 12 digits)
         * Total maximum length: 1 + 3 + 12 = 16 characters, using 16 for optimal storage efficiency
         * while covering the vast majority of international phone numbers.
         *
         * @see [E.164 standard](https://en.wikipedia.org/wiki/E.164)
         */
        const val MAX_PHONE_NUMBER_STRING_VIEW_LENGTH = 16
    }
}
