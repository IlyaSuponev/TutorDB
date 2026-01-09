package com.isuponev.tutordb.core.exceptions

/**
 * Exception thrown when a type conversion operation fails.
 *
 * This exception should be used when an object cannot be converted to the target type
 * due to invalid data, incompatible types, or business logic constraints.
 *
 * @param message The detailed message describing the conversion failure.
 * @param cause The optional underlying cause of the conversion failure.
 * @property sourceValue The optional value that was being converted (for debugging purposes).
 */
class ConversionException(
    message: String,
    cause: Throwable? = null,
    val sourceValue: Any? = null
) : RuntimeException(message, cause) {

    /**
     * Creates a string representation of the exception including the source value if available.
     */
    override fun toString(): String = if (sourceValue != null) {
        "${super.localizedMessage} [Source: $sourceValue]"
    } else {
        super.localizedMessage
    }
}
