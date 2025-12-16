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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.currentDatabase
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.widgets.AppAlert
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.AddSubjectViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.EditSubjectViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.HomeViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.IncomesViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.lessons.LessonsViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.SettingsViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.student.AddStudentViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.student.EditStudentViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.student.StudentsViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.SubjectsViewModel
import com.isuponev.tutordb.desktop.views.screens.subject.AddSubjectScreenView
import com.isuponev.tutordb.desktop.views.screens.subject.EditSubjectScreenView
import com.isuponev.tutordb.desktop.views.screens.HomeView
import com.isuponev.tutordb.desktop.views.screens.IncomesScreenView
import com.isuponev.tutordb.desktop.views.screens.lessons.LessonsScreenView
import com.isuponev.tutordb.desktop.views.screens.SettingsScreenView
import com.isuponev.tutordb.desktop.views.screens.student.AddStudentScreenView
import com.isuponev.tutordb.desktop.views.screens.student.EditStudentScreenView
import com.isuponev.tutordb.desktop.views.screens.student.StudentsScreenView
import com.isuponev.tutordb.desktop.views.screens.subject.SubjectsScreenView

@Composable
internal actual fun AppMainContainer(
    navController: NavHostController,
    modifier: Modifier,
) = Row(
    modifier = modifier.fillMaxSize().padding(AppDefaults.Paddings.BIG),
    horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
    verticalAlignment = Alignment.CenterVertically,
) {
    val db by AppConfig.Platform.currentDatabase.collectAsState()
    ToolMenu(
        Modifier.fillMaxHeight(),
        navController
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
                val viewModel by remember { mutableStateOf(StudentsViewModel(navController, db)) }
                StudentsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.SubjectsScreen> {
                val viewModel by remember { mutableStateOf(SubjectsViewModel(navController, db)) }
                SubjectsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.LessonsScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<Screen.LessonsScreen>()
                val viewModel by remember {
                    mutableStateOf(LessonsViewModel(screen, navController, db))
                }
                LessonsScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.IncomesScreen> {
                val viewModel by remember { mutableStateOf(IncomesViewModel(navController)) }
                IncomesScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.AddSubjectScreen> {
                val viewModel by remember { mutableStateOf(AddSubjectViewModel(navController, db)) }
                AddSubjectScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.EditSubjectScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<Screen.EditSubjectScreen>()
                val viewModel by remember {
                    mutableStateOf(EditSubjectViewModel(screen, navController, db))
                }
                EditSubjectScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.AddStudentScreen> {
                val viewModel by remember { mutableStateOf(AddStudentViewModel(navController, db)) }
                AddStudentScreenView(viewModel, Modifier.fillMaxSize())
            }
            composable<Screen.EditStudentScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<Screen.EditStudentScreen>()
                val viewModel by remember {
                    mutableStateOf(
                        EditStudentViewModel(screen, navController, db)
                    )
                }
                EditStudentScreenView(viewModel, Modifier.fillMaxSize())
            }
        }
    }
    val alertData by AppConfig.Runtime.alertData.collectAsState()
    if (alertData.isVisible) {
        AppAlert(
            alertData.title,
            alertData.message,
            onConfirm = {
                AppConfig.Runtime.dismissAlert()
            },
            alertData.icon,
            alertData.onCancel
        )
    }
}
