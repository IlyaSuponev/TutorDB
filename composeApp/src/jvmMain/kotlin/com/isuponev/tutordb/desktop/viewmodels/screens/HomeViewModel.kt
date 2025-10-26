package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

class HomeViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.HomeScreen>(Screen.HomeScreen) {
    fun onStudentsCardClicked() {
        navHostController.navigate(Screen.StudentsScreen)
    }

    fun onLessonsCardClicked() {
        navHostController.navigate(Screen.LessonsScreen)
    }

    fun onSubjectsCardClicked() {
        navHostController.navigate(Screen.SubjectsScreen)
    }

    fun onIncomesCardClicked() {
        navHostController.navigate(Screen.IncomesScreen)
    }
}
