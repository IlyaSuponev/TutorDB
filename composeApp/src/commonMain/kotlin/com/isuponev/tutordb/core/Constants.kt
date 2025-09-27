package com.isuponev.tutordb.core

/**
 * A constant value used for hash code calculation in hash-based algorithms.
 *
 * This constant provides a standard prime number for implementing the `hashCode()` method
 * in Kotlin/Java classes. Using a prime number helps distribute hash values more evenly
 * and reduces collisions in hash-based collections like [HashMap], [HashSet], etc.
 *
 * ## Why 31?
 * - **Prime number**: Reduces the likelihood of hash collisions when used in multiplication
 * - **Optimization**: `31 * i` can be optimized by JVM to `(i << 5) - i` for better performance
 * - **Tradition**: Established convention in Java ecosystem (used in [java.util.List], [String], etc.)
 * - **Distribution**: Provides good distribution for common data types
 *
 * ## Usage Examples
 *
 * ### Basic hashCode implementation
 * ```kotlin
 * data class Person(val name: String, val age: Int) {
 *     override fun hashCode(): Int {
 *         var result = name.hashCode()
 *         result = HASH_CODE_NUMBER_GENERATOR * result + age
 *         return result
 *     }
 * }
 * ```
 *
 * ### With multiple properties
 * ```kotlin
 * class Product(val id: Long, val name: String, val price: Double) {
 *     override fun hashCode(): Int {
 *         var result = id.hashCode()
 *         result = HASH_CODE_NUMBER_GENERATOR * result + name.hashCode()
 *         result = HASH_CODE_NUMBER_GENERATOR * result + price.hashCode()
 *         return result
 *     }
 * }
 * ```
 *
 * ### For custom hash calculations
 * ```kotlin
 * fun calculateCombinedHash(vararg values: Any): Int {
 *     var hash = 0
 *     for (value in values) {
 *         hash = HASH_CODE_NUMBER_GENERATOR * hash + value.hashCode()
 *     }
 *     return hash
 * }
 * ```
 *
 * ## Best Practices
 * - Use this constant consistently across all hash code implementations in the project
 * - Always multiply the accumulated hash by this constant before adding the next property's hash
 * - Consider null-safe handling for nullable properties
 *
 * ## Null-Safe Example
 * ```kotlin
 * class Entity(val required: String, val optional: String?) {
 *     override fun hashCode(): Int {
 *         var result = required.hashCode()
 *         result = HASH_CODE_NUMBER_GENERATOR * result + (optional?.hashCode() ?: 0)
 *         return result
 *     }
 * }
 * ```
 *
 * @see kotlin.Any.hashCode
 * @see java.util.Objects.hash
 * @see kotlin.comparisons for hash code comparison utilities
 */
const val HASH_CODE_NUMBER_GENERATOR = 31
