package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

class IncomesViewModel(
    private val navHostController: NavHostController
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen)
