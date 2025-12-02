package com.isuponev.tutordb.desktop.views.screens.student

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.Tool
import com.isuponev.tutordb.desktop.viewmodels.screens.student.StudentsViewModel
import com.isuponev.tutordb.desktop.views.Header

/**
 * A composable UI component for the "Students" screen in the application.
 *
 * This is currently a placeholder/stub implementation that displays a localized screen title.
 * The actual implementation would eventually handle student-related data operations,
 * UI state management, and user interactions through the [StudentsViewModel].
 *
 * @param viewModel The [StudentsViewModel] instance managing the screen's state and logic.
 *                  Currently serves as a base for future implementation.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@Composable
fun StudentsScreenView(
    viewModel: StudentsViewModel,
    modifier: Modifier
) {
    viewModel.i("Load students screen")
    Header(
        SharedResourcesjvmMain.strings.screenStudentsName,
        tools = tools(viewModel),
        modifier = Modifier.fillMaxWidth()
    )
}

private fun tools(viewModel: StudentsViewModel) = listOf(
    Tool(
        "Add student",
        Icons.Default.Add,
        viewModel::onClickAddStudent
    )
)
