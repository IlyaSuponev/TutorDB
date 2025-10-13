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
