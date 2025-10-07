package com.isuponev.tutordb.core.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.isuponev.tutordb.core.views.screens.MainScreen
import com.isuponev.tutordb.core.views.screens.Screen

@Composable
internal actual fun AppMainContainerGeneration(
    navController: NavHostController,
    screens: List<Screen>,
    startDestination: Screen,
    modifier: Modifier,
) = Row(
    modifier = modifier.fillMaxSize().padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    var tools by remember { mutableStateOf(emptyList<ToolMenuElement>()) }
    ToolMenu(
        Modifier
            .fillMaxHeight()
            .width(72.dp),
        tools
    )
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        NavHost(navController = navController, startDestination = startDestination.route) {
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
)