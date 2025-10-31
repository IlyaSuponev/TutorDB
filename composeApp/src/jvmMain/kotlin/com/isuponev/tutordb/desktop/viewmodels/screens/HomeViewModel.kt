package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * A ViewModel for the Home screen in the application.
 *
 * This class manages navigation actions triggered by user interactions with cards on the home screen.
 * It uses a [NavHostController] to navigate between different sections of the app.
 *
 * @param navHostController The navigation controller used to handle screen transitions.
 */
class HomeViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.HomeScreen>(Screen.HomeScreen) {
    /**
     * Navigates to the Students screen when the students card is clicked.
     */
    fun onStudentsCardClicked() {
        navHostController.navigate(Screen.StudentsScreen)
    }

    /**
     * Navigates to the Lessons screen when the lessons card is clicked.
     */
    fun onLessonsCardClicked() {
        navHostController.navigate(Screen.LessonsScreen)
    }

    /**
     * Navigates to the Subjects screen when the subjects card is clicked.
     */
    fun onSubjectsCardClicked() {
        navHostController.navigate(Screen.SubjectsScreen)
    }

    /**
     * Navigates to the Incomes screen when the incomes card is clicked.
     */
    fun onIncomesCardClicked() {
        navHostController.navigate(Screen.IncomesScreen)
    }
}
