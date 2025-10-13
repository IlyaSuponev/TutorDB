package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.screens.Screen

object IncomesScreen : Screen(
    SharedResources.strings.routeOfIncomesScreen.localized(),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        appLogger.i(tag = IncomesScreen::class.java.simpleName) { "Load incomes screen" }
        Text(SharedResources.strings.screenIncomes.localized())
    }
}
