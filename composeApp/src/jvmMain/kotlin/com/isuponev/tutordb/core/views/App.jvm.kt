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
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.views.screens.HomeView
import com.isuponev.tutordb.desktop.viewmodels.HomeViewModel
import com.isuponev.tutordb.desktop.views.screens.IncomesScreenView
import com.isuponev.tutordb.desktop.viewmodels.IncomesViewModel
import com.isuponev.tutordb.desktop.views.screens.LessonsScreenView
import com.isuponev.tutordb.desktop.viewmodels.LessonsViewModel
import com.isuponev.tutordb.desktop.views.screens.SettingsScreenView
import com.isuponev.tutordb.desktop.viewmodels.SettingsViewModel
import com.isuponev.tutordb.desktop.views.screens.StudentsScreenView
import com.isuponev.tutordb.desktop.viewmodels.StudentsViewModel
import com.isuponev.tutordb.desktop.views.screens.SubjectsScreenView
import com.isuponev.tutordb.desktop.viewmodels.SubjectsViewModel

@Composable
internal actual fun AppMainContainer(
    navController: NavHostController,
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
            startDestination = Screen.HomeScreen,
        ) {
            composable<Screen.HomeScreen> {
                val viewModel by remember { mutableStateOf(HomeViewModel(navController)) }
                HomeView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.SettingsScreen> {
                val viewModel by remember { mutableStateOf(SettingsViewModel(navController)) }
                SettingsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.StudentsScreen> {
                val viewModel by remember { mutableStateOf(StudentsViewModel(navController)) }
                StudentsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.SubjectsScreen> {
                val viewModel by remember { mutableStateOf(SubjectsViewModel(navController)) }
                SubjectsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.LessonsScreen> {
                val viewModel by remember { mutableStateOf(LessonsViewModel(navController)) }
                LessonsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.IncomesScreen> {
                val viewModel by remember { mutableStateOf(IncomesViewModel(navController)) }
                IncomesScreenView(viewModel, Modifier.fillMaxSize())
            }
        }
    }
}
