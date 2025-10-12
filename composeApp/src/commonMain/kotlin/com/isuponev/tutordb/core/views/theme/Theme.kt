package com.isuponev.tutordb.core.views.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.ui.ThemeMode

/**
 * Custom application theme that automatically responds to system dark/light mode.
 *
 * `AppTheme` is a wrapper around MaterialTheme that dynamically selects between
 * light and dark color schemes based on the user's system preferences. This provides
 * a consistent theming experience that respects the device's theme settings.
 *
 * #### Features:
 * - Automatically detects system dark/light mode
 * - Uses Material Design 3 color schemes
 * - Provides consistent typography and shapes from MaterialTheme
 * - Easy to use as a root-level theme wrapper
 *
 * #### Example:
 * ```kotlin
 * @Composable
 * fun MyApp() {
 *     AppTheme {
 *         Surface(
 *             modifier = Modifier.fillMaxSize(),
 *             color = MaterialTheme.colorScheme.background
 *         ) {
 *             Greeting("Android")
 *         }
 *     }
 * }
 * ```
 *
 * @param content The composable content that will inherit this theme's styling,
 *                including color schemes, typography, and shapes.
 *
 * @see MaterialTheme for the underlying theme implementation
 * @see isSystemInDarkTheme for system theme detection logic
 * @see darkColorScheme for the dark theme color palette
 * @see lightColorScheme for the light theme color palette
 */
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val mode: ThemeMode by AppConfig.UI.themeMode.collectAsState()
    MaterialTheme(
        colorScheme = when(mode) {
            ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
            ThemeMode.DARK -> darkColorScheme()
            ThemeMode.LIGHT -> lightColorScheme()
        },
        content = content
    )
}

