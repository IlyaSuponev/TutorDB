package com.isuponev.tutordb.core.models.values

import com.isuponev.tutordb.core.HASH_CODE_NUMBER_GENERATOR
import java.io.Serializable
import kotlin.jvm.Throws

/**
 * A type-safe wrapper for validated name values.
 *
 * This value class ensures that name strings adhere to specific formatting rules
 * and provides compile-time type safety for name values throughout the application.
 * It uses a factory pattern for instance creation with built-in validation.
 *
 * Validation Rules
 * - Allows: letters (Latin, Cyrillic), digits, and spaces
 * - Format: one or more words separated by single spaces
 * - Prohibits: special characters (@, #, $, etc.)
 * - Prohibits: leading/trailing spaces (automatically trimmed during validation)
 * - Prohibits: consecutive spaces
 *
 * @property value The underlying string value of the name
 *
 * @see isValidNameValue for validation without instance creation
 * @see of for the primary factory method
 */
class Name private constructor(val value: String) : Serializable {
    override fun toString(): String = "Name(value='$value')"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Name
        return value == other.value
    }

    override fun hashCode(): Int = HASH_CODE_NUMBER_GENERATOR * javaClass.packageName.hashCode() + value.hashCode()

    /**
     * Companion object containing factory methods and validation utilities for [Name] instances.
     */
    companion object Builder {
        private const val serialVersionUID = 9039605455430276L
        private const val LETTER_REGEX = "[a-zA-Zа-яА-Я0-9]"
        private const val FIRST_WORD_REGEX = "[A-ZА-Я]${LETTER_REGEX}+"
        private const val WORD_REGEX = "${LETTER_REGEX}+"

        /**
         * Regular expression pattern used for name validation.
         */
        val NAME_REGEX = "^$FIRST_WORD_REGEX(\\s$WORD_REGEX)*$".toRegex()

        /**
         * Validates whether a string represents a valid name format.
         *
         * This method performs validation without creating a [Name] instance,
         * making it suitable for preliminary checks or UI validation.
         *
         * @param name the string to validate
         * @return `true` if the string matches the name format, `false` otherwise
         */
        fun isValidNameValue(name: String): Boolean = NAME_REGEX.matches(name)

        /**
         * Creates a [Name] instance from a string after validation.
         *
         * This is the primary factory method for creating [Name] instances.
         * It validates the input string and returns a type-safe name object.
         *
         * @param name the string to convert to a [Name] instance
         * @return a validated [Name] instance
         * @throws IllegalArgumentException if the input string does not match the required name format
         */
        @Throws(IllegalArgumentException::class)
        fun of(name: String): Name {
            val trimmedName = name.trim()
            require(isValidNameValue(trimmedName)) { "Value '$name' is not a valid name value." }
            return Name(trimmedName)
        }
    }
}
