package com.isuponev.tutordb.core.config.general

import com.isuponev.tutordb.core.config.general.AppLocale.ENGLISH
import com.isuponev.tutordb.core.config.general.AppLocale.RUSSIAN
import dev.icerock.moko.resources.StringResource
import java.util.Locale
import kotlinx.serialization.Serializable

/**
 * Enumeration representing supported application locales with their corresponding Java [Locale] objects.
 *
 * This enum provides a type-safe way to manage application localization settings
 * and ensures consistency between Kotlin serialization and Java locale handling.
 *
 * Each enum value wraps a Java [Locale] instance, allowing seamless integration
 * with platform localization APIs while maintaining serialization capabilities.
 *
 * Supported locales:
 * - [ENGLISH] - English language (Locale.ENGLISH)
 * - [RUSSIAN] - Russian language (Locale("ru"))
 *
 * @property type The Java [Locale] instance associated with this application locale.
 * @property currencyCode mapping to valid currency
 *
 * @see Locale
 * @see Serializable
 */
@Serializable
enum class AppLocale(val type: Locale, val currencyCode: String) {
    /**
     * English language locale.
     *
     * Corresponds to [Locale.ENGLISH] (language: "en", country: "").
     * Used for English-speaking users and default application language.
     */
    ENGLISH(Locale.of("en-US"), "USD"),

    /**
     * Russian language locale.
     *
     * Corresponds to [Locale] with language code "ru".
     * Used for Russian-speaking users.
     */
    RUSSIAN(Locale.of("ru-Ru"), "RUB");

    /**
     * Method for localization Moko [StringResource]s.
     *
     * @param resource value to localize
     *
     * @see Locale
     * @see StringResource
     */
    fun localize(resource: StringResource): String = resource.localized(type)

    /**
     * Companion object providing utility methods for locale management.
     */
    companion object {
        /**
         * Detects and returns the best matching [AppLocale] for the system's default locale.
         *
         * This method compares the system's default locale with supported application locales
         * and returns the first matching enum value. If no exact match is found, returns
         * [ENGLISH] as the fallback default.
         *
         * The comparison uses [Locale.equals] which considers language, country, and variant
         * for equality comparison.
         *
         * @return The [AppLocale] that matches the system locale, or [ENGLISH] if no match found.
         *
         * @see Locale.equals
         */
        fun getSystem(): AppLocale {
            var locale = ENGLISH
            val default = Locale.getDefault()
            entries.forEach { entry ->
                if (entry.type == default) locale = entry
            }
            return locale
        }
    }
}
