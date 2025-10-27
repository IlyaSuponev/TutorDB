package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
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
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SubjectsViewModel(
    private val navHostController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen) {
    private val logTag = "SubjectsViewModel"
    private val subjectsDao = SubjectsDao.new(db)
    val subjects: StateFlow<List<Subject>>
        get() = subjectsDao.values

    val columnsCount = subjects
        .map {
            val size = it.size
            var result = ColumnCount.MIN
            (ColumnCount.MIN + 1 .. ColumnCount.MAX).forEach { count ->
                if (size % count == 0) result = count
            }
            result
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ColumnCount.MIN
        )

    val tools = listOf(
        Tool(
            "Add subject",
            Icons.Default.Add,
            {
                navHostController.navigate(Screen.AddSubjectScreen)
            }
        )
    )

    fun removeSubject(subject: Subject) = subjectsDao.remove(
        subject,
        onError = { AppConfig.logger.e(tag=logTag, throwable = it) { "Failed to remove subject $subject" } }
    )

    fun editSubject(subject: Subject) {
        navHostController.navigate(
            Screen.EditSubjectScreen(subject.id)
        )
    }

    companion object {
        val DESCRIPTION_MIN_WIDTH = 100.dp
        object ColumnCount {
            val MIN = 2
            val MAX = 5
        }
    }
}