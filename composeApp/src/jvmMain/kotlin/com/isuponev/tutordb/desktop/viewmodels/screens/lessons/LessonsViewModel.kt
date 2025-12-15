package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

/**
 * A ViewModel for managing the Lessons screen in the application.
 *
 * This class currently serves as a placeholder/stub implementation for the Lessons feature.
 * It inherits from [com.isuponev.tutordb.core.views.screens.AppScreenViewModel] but incorrectly associates with [com.isuponev.tutordb.core.views.screens.Screen.StudentsScreen]
 * instead of an actual Lessons screen. This suggests incomplete implementation where lesson-related
 * data operations and UI state management would eventually be added.
 *
 * @param navHostController The navigation controller used to handle screen transitions,
 *                          though no navigation logic is currently implemented here.
 */
class LessonsViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen)