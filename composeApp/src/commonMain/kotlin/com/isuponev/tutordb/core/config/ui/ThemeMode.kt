package com.isuponev.tutordb.core.config.ui

import com.isuponev.tutordb.core.config.ui.ThemeMode.DARK
import com.isuponev.tutordb.core.config.ui.ThemeMode.LIGHT
import com.isuponev.tutordb.core.config.ui.ThemeMode.SYSTEM
import kotlinx.serialization.Serializable

/**
 * Enumeration representing available theme modes for the application user interface.
 *
 * This enum defines the visual appearance options for the application, allowing users
 * to choose between light, dark, or system-default theme settings. It supports
 * serialization for persistent storage of user preferences.
 *
 * The theme mode selection affects the overall color scheme, contrast, and visual
 * styling of the application interface.
 *
 * Theme behavior:
 * - [LIGHT] - Always uses light color scheme
 * - [DARK] - Always uses dark color scheme
 * - [SYSTEM] - Follows the operating system's theme setting
 *
 * @see Serializable
 */
@Serializable
enum class ThemeMode {
    /**
     * Light theme mode.
     *
     * Forces the application to use a light color scheme regardless of system settings.
     * Typically, features light backgrounds with dark text for high contrast in well-lit environments.
     */
    LIGHT,

    /**
     * Dark theme mode.
     *
     * Forces the application to use a dark color scheme regardless of system settings.
     * Typically, features dark backgrounds with light text for reduced eye strain in low-light environments.
     */
    DARK,

    /**
     * System theme mode.
     *
     * Automatically follows the theme setting of the underlying operating system.
     * The application will switch between light and dark themes when the system theme changes.
     */
    SYSTEM
}
