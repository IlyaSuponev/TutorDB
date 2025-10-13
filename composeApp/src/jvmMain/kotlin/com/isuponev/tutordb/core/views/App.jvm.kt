package com.isuponev.tutordb.core.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.isuponev.tutordb.desktop.views.screens.IncomesScreen
import com.isuponev.tutordb.desktop.views.screens.LessonsScreen
import com.isuponev.tutordb.desktop.views.screens.MainScreen
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.views.screens.SettingsScreen
import com.isuponev.tutordb.desktop.views.screens.StudentsScreen
import com.isuponev.tutordb.desktop.views.screens.SubjectsScreen

@Composable
internal actual fun AppMainContainerGeneration(
    navController: NavHostController,
    screens: List<Screen>,
    startDestination: Screen,
    modifier: Modifier,
) = Row(
    modifier = modifier.fillMaxSize().padding(AppDefaults.Paddings.BIG),
    horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
    verticalAlignment = Alignment.CenterVertically,
) {
    var tools by remember { mutableStateOf(emptyList<ToolMenuElement>()) }
    ToolMenu(
        Modifier.fillMaxHeight(),
        navController,
        tools
    )
    Box(
        modifier = Modifier
            .weight(AppDefaults.Weights.ONE)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination.route,

        ) {
            screens.forEach { screen ->
                composable(screen.route) {
                    tools = screen.toolsMenuElements
                    screen.view(
                        navController,
                    )
                }
            }
        }
    }
}

internal actual fun appScreens(): List<Screen> = listOf(
    MainScreen,
    SettingsScreen,
    StudentsScreen,
    SubjectsScreen,
    LessonsScreen,
    IncomesScreen
)
