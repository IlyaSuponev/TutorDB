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
 * Screen object representing the Subjects management interface in the TutorDB desktop application.
 *
 * This screen provides access to view and manage educational subjects within the application.
 * It implements the [Screen] interface with proper localization support and integrates
 * with the application's navigation and configuration systems.
 *
 * Route: Localized string from [SharedResources.strings.routeOfSubjectsScreen]
 * Default: English localization used as fallback during initialization
 *
 * @see Screen
 * @see AppLocale
 * @see SharedResources
 */
object SubjectsScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfSubjectsScreen),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = SubjectsScreen::class.java.simpleName) { "Load subjects screen" }
        Text(locale.localize(SharedResources.strings.screenSubjects))
    }
}
