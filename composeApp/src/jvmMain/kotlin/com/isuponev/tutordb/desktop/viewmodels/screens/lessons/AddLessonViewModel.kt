package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.utils.Result
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database

class AddLessonViewModel(
    screen: Screen.AddLessonScreen,
    navController: NavHostController,
    db: Database
): LessonEditDialogViewModel<Screen.AddLessonScreen>(
    screen,
    navController,
    db,
    screen.chosenDate
) {
    override fun onAcceptEvent(): Result<Unit> {
        TODO("Not yet implemented")
    }
}