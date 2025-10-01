package com.isuponev.tutordb.core

import androidx.compose.ui.unit.dp

/**
 * A constant value used for hash code calculation in hash-based algorithms.
 *
 * This constant provides a standard prime number for implementing the `hashCode()` method
 * in Kotlin/Java classes. Using a prime number helps distribute hash values more evenly
 * and reduces collisions in hash-based collections like [HashMap], [HashSet], etc.
 *
 * ###### Why 31?
 * - **Prime number**: Reduces the likelihood of hash collisions when used in multiplication
 * - **Optimization**: `31 * i` can be optimized by JVM to `(i << 5) - i` for better performance
 * - **Tradition**: Established convention in Java ecosystem (used in [java.util.List], [String], etc.)
 * - **Distribution**: Provides good distribution for common data types
 *
 * ###### Basic hashCode implementation
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
 * ###### With multiple properties
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
 * ###### For custom hash calculations
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
 * ###### Best Practices
 * - Use this constant consistently across all hash code implementations in the project
 * - Always multiply the accumulated hash by this constant before adding the next property's hash
 * - Consider null-safe handling for nullable properties
 *
 * @see kotlin.Any.hashCode
 * @see java.util.Objects.hash
 * @see kotlin.comparisons for hash code comparison utilities
 */
const val HASH_CODE_NUMBER_GENERATOR = 31

/**
 * A constant used as a multiplier for values that don't require scaling.
 *
 * This constant simplifies the process of applying no scaling factor in calculations where a
 * multiplier is needed, improving code readability and maintainability.  It is particularly
 * useful when dealing with visual properties, sizes, or quantities that should be displayed
 * without any adjustment.
 *
 * ## Best Practices
 * - Use this constant consistently when you need to represent unscaled values.
 * - Makes code clearer when you explicitly specify that no scaling is intended.
 */
const val NON_SCALED_MULTIPLIER = 1f

/**
 * Constant representing the size of the logo image.
 *
 * This constant defines a standard size for the logo image within the application's UI. Using a
 * constant ensures consistency across the application and facilitates easy adjustments if the
 * logo size needs to be changed. It is defined using the `dp` (density-independent pixels)
 * unit, making it compatible with Compose UI.
 *
 * ###### Usage Example:
 * ```kotlin
 *  val logoImage = Image(
 *      painter = painterResource(Res.drawable.logo),
 *      contentDescription = "App Logo",
 *      modifier = Modifier.size(LOGO_IMAGE_SIZE)
 *  )
 * ```
 *
 * ###### Best Practices
 * - Use this constant when referencing the size of the logo image throughout the application.
 * - Update the constant value here to change the logo size globally.
 */
val LOGO_IMAGE_SIZE = 64.dp
