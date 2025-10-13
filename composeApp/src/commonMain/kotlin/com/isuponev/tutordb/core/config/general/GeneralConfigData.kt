package com.isuponev.tutordb.core.config.general

import kotlinx.serialization.Serializable

/**
 * Data class representing the general application configuration settings.
 *
 * This configuration container holds system-wide settings that affect the fundamental
 * behavior and localization of the application. It is designed to be serializable
 * for persistent storage and supports default values for all properties.
 *
 * Integration with application lifecycle:
 * - Initialize during app startup with stored or default values
 * - Update when user changes language preferences
 * - Persist to local storage or preferences database
 * - Observe changes to trigger UI updates
 *
 * @property locale The application's language and regional settings.
 *                 Defaults to the system-detected locale via [AppLocale.getSystem].
 *
 * @see AppLocale
 * @see Serializable
 */
@Serializable
data class GeneralConfigData(
    val locale: AppLocale = AppLocale.getSystem()
)
