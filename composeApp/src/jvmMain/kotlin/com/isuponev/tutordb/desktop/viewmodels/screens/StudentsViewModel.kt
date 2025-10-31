package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * A ViewModel for managing the Students screen in the application.
 *
 * This class currently serves as a placeholder/stub implementation for the Students feature.
 * It inherits from [AppScreenViewModel] and is associated with [Screen.StudentsScreen].
 * Navigation between screens is handled via the provided [NavHostController], though no
 * specific navigation logic has been implemented yet.
 *
 * @param navHostController The navigation controller used to handle screen transitions.
 *                          This parameter is passed to the superclass and may be used in
 *                          future implementations to navigate from the Students screen.
 */
class StudentsViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen)
