package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

class SettingsViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.SettingsScreen>(Screen.SettingsScreen) {
    fun onChooseAppLocale(locale: AppLocale) {
        AppConfig.General.setLocale(locale)
    }

    fun onChooseThemeMode(theme: ThemeMode) {
        AppConfig.UI.setThemeMode(theme)
    }
}
