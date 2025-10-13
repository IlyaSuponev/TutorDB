package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.screens.Screen

object SettingsScreen : Screen(
    SharedResources.strings.routeOfSettingsScreen.localized(),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        appLogger.i(tag = SettingsScreen::class.java.simpleName) { "Load settings screen" }
        Text(SharedResources.strings.screenSettings.localized())
    }
}
