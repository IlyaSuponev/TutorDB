package com.isuponev.tutordb.desktop.viewmodels.screens.abs

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.StudentsDao

abstract class DialogViewModel<S: Screen>(
    screen: S,
    protected val navController: NavHostController
) : AppScreenViewModel<S>(screen) {
    fun onClickCancel() {
        onCancelEvent()
        navController.navigateUp()
    }
    fun onClickAccept() {
        onAcceptEvent()
        navController.navigateUp()
    }
    protected abstract fun onAcceptEvent()

    protected abstract fun onCancelEvent()
}