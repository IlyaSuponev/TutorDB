package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * A ViewModel for managing the Incomes screen in the application.
 *
 * This class currently serves as a placeholder implementation for the Incomes feature.
 * It inherits from [AppScreenViewModel] but is incorrectly associated with [Screen.StudentsScreen]
 * instead of an actual Incomes screen. This likely represents incomplete implementation
 * that would eventually handle income-related data operations and UI state management.
 *
 * @param navHostController The navigation controller used to handle screen transitions.
 */
class IncomesViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen)
