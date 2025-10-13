package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * Screen object representing the application Settings and configuration interface.
 *
 * This screen provides users with access to application preferences, including
 * theme selection, language settings, and other configuration options. It serves
 * as the central hub for customizing the application experience.
 *
 * Route: Localized string from [SharedResources.strings.routeOfSettingsScreen]
 * Default: English localization used as fallback during initialization
 *
 * @see Screen
 * @see AppConfig
 * @see AppLocale
 */
object SettingsScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfSettingsScreen),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = SettingsScreen::class.java.simpleName) { "Load settings screen" }
        Text(locale.localize(SharedResources.strings.screenSettings))
    }
}
