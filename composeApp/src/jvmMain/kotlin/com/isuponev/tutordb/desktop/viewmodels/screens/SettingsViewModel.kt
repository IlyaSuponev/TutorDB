package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * A ViewModel for managing application settings state and behavior.
 *
 * This class handles user interactions related to changing application-wide preferences
 * such as locale selection and theme mode configuration. It persists these changes
 * through the [AppConfig] singleton and ensures UI updates propagate appropriately.
 *
 * @param navHostController The navigation host controller for managing navigation
 */
class SettingsViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.SettingsScreen>(Screen.SettingsScreen) {
    /**
     * Updates the application's locale setting.
     *
     * @param locale The new [AppLocale] value to apply across the application.
     *               This will affect all localized strings and resource loading.
     */
    fun onChooseAppLocale(locale: AppLocale) {
        AppConfig.General.setLocale(locale)
    }

    /**
     * Updates the application's theme mode preference.
     *
     * @param theme The new [ThemeMode] to apply (e.g., light, dark, system default).
     *            This will trigger a UI recomposition with the new theme.
     */
    fun onChooseThemeMode(theme: ThemeMode) {
        AppConfig.UI.setThemeMode(theme)
    }
}
