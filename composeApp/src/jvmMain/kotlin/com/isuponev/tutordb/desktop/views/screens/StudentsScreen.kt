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
 * Screen object representing the Students management interface in the TutorDB desktop application.
 *
 * This screen provides functionality for viewing, adding, editing, and managing student records
 * within the tutoring system. It follows the same pattern as other screens with full localization
 * support and reactive configuration handling.
 *
 * Route: Localized string from [SharedResources.strings.routeOfStudentsScreen]
 * Default: English localization used as fallback during initialization
 *
 * @see Screen
 * @see AppLocale
 * @see SharedResources
 */
object StudentsScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfStudentsScreen),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = StudentsScreen::class.java.simpleName) { "Load students screen" }
        Text(locale.localize(SharedResources.strings.screenStudents))
    }
}
