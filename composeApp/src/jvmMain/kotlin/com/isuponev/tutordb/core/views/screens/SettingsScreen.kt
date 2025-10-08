package com.isuponev.tutordb.core.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.resources.SharedResources

object SettingsScreen : Screen(
    SharedResources.strings.routeOfSettingsScreen.localized(),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        Text(SharedResources.strings.screenSettings.localized())
    }
}