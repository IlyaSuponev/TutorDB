package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.LessonsViewModel

/**
 * A composable UI component for the "Lessons" screen in the application.
 *
 * This is currently a placeholder/stub implementation that displays a localized screen title.
 * The actual implementation would eventually handle lesson-related data operations,
 * UI state management, and user interactions through the [LessonsViewModel].
 *
 * @param viewModel The [LessonsViewModel] instance managing the screen's state and logic.
 *                  Currently serves as a base for future implementation.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@Composable
fun LessonsScreenView(
    viewModel: LessonsViewModel,
    modifier: Modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    viewModel.i("Load lessons screen")
    Text(locale.localize(SharedResourcesjvmMain.strings.screenLessonsName))
}
