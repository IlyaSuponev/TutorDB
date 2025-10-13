package com.isuponev.tutordb.core.interfaces

/**
 * A functional interface representing objects that can be converted to another type [T].
 *
 * This interface provides a standardized way to implement type conversion patterns
 * across the application. It encapsulates the conversion logic in a single method
 * that produces an instance of the target type.
 *
 * [ConvertableTo] is particularly useful for:
 * - Data mapping between layers (DTO to Domain, Domain to ViewModel)
 * - Serialization and deserialization operations
 * - Adapter patterns between different type systems
 * - Fluent API designs with type transformations
 *
 * Implementation guidelines:
 * - Conversion should be pure and without side effects
 * - The method should return a new instance rather than modifying `this`
 * - Consider immutability of both source and target objects
 * - Document any potential conversion failures or constraints
 *
 * @param T the target type to convert to
 *
 * @see kotlin.extension functions
 * @see java.util.function.Function
 */
@FunctionalInterface
interface ConvertableTo<T> {
    /**
     * Converts the current instance to an instance of type [T].
     *
     * @return a new instance of type [T] representing the converted value
     * @throws com.isuponev.tutordb.core.exceptions.ConversionException if the conversion cannot be performed
     *         (implementation-specific exceptions should be documented)
     *
     * @see com.isuponev.tutordb.core.exceptions.ConversionException
     */
    fun convert(): T
}
