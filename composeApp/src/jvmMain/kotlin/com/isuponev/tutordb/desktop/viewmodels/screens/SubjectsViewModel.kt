package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.viewmodels.Tool
import com.isuponev.tutordb.desktop.views.MAIN_WINDOW_MIN_WIDTH
import kotlinx.coroutines.flow.StateFlow

class SubjectsViewModel(
    private val navHostController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen) {
    private val logTag = "SubjectsViewModel"
    private val subjectsDao = SubjectsDao.new(db)
    val subjects: StateFlow<List<Subject>>
        get() = subjectsDao.values

    val tools = listOf(
        Tool(
            "Add subject",
            Icons.Default.Add,
            {

            }
        )
    )

    fun removeSubject(subject: Subject) = subjectsDao.remove(
        subject,
        onError = { AppConfig.logger.e(tag=logTag, throwable = it) { "Failed to remove subject $subject" } }
    )
}