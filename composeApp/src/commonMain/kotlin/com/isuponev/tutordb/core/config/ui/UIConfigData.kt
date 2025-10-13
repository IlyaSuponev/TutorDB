package com.isuponev.tutordb.core.config.ui

import kotlinx.serialization.Serializable

/**
 * Data class representing the user interface configuration settings.
 *
 * This configuration container holds visual and thematic settings that control
 * the appearance and behavior of the application's user interface. It supports
 * serialization for persistent storage of user preferences.
 *
 * Theme application flow:
 * 1. Read [UIConfigData] from persistent storage on app start
 * 2. Apply theme based on [themeMode] and system settings
 * 3. Update configuration when user changes theme preference
 * 4. Persist updated configuration to storage
 * 5. Notify UI to recompose with new theme
 *
 * @property themeMode The visual theme mode for the application interface.
 *                    Defaults to [ThemeMode.SYSTEM] to follow OS-level theme settings.
 *
 * @see ThemeMode
 * @see Serializable
 */
@Serializable
data class UIConfigData(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)
