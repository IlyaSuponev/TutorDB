package com.isuponev.tutordb.core.interfaces

/**
 * A functional interface representing an operation that accepts a single input argument and returns no result.
 *
 * This interface is designed to represent side-effectful operations that consume or transform
 * a value of type [T]. It serves as a Kotlin alternative to Java's `Consumer<T>` interface
 * with a more domain-specific naming.
 *
 * [Applicable] is particularly useful for:
 * - Configuration builders and DSLs
 * - Value transformation pipelines
 * - Callback operations where a value needs processing
 * - Functional composition with other operations
 *
 * Why use Applicable over Function1?
 * - Semantically clearer for side-effectful operations
 * - Better interoperability with Java functional interfaces
 * - More explicit intent in method signatures
 *
 * @param T the type of the input to the operation
 *
 * @see java.util.function.Consumer
 * @see kotlin.Function1
 */
@FunctionalInterface
interface Applicable<T> {
    /**
     * Performs the operation on the given argument.
     *
     * @param value the input argument to be processed or transformed
     */
    fun apply(value: T)
}
