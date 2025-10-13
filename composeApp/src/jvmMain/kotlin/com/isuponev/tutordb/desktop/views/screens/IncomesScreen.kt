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
 * Screen object representing the Financial Incomes tracking and management interface
 * in the TutorDB desktop application.
 *
 * This screen provides comprehensive financial management capabilities for tracking
 * tutoring income, generating revenue reports, and managing payment records. It serves
 * as the central hub for financial monitoring and revenue analysis within the tutoring business.
 *
 * Route: Localized string from [SharedResources.strings.routeOfIncomesScreen]
 * Default: English localization used as fallback during initialization
 *
 * @see Screen
 * @see AppLocale
 * @see SharedResources
 * @see AppConfig
 */
object IncomesScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfIncomesScreen),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = IncomesScreen::class.java.simpleName) { "Load incomes screen" }
        Text(locale.localize(SharedResources.strings.screenIncomes))
    }
}
