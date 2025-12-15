package com.isuponev.tutordb.desktop.views.screens.lessons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.desktop.viewmodels.screens.lessons.LessonsViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.widgets.CalendarWidget
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun LessonsScreenView(
    viewModel: LessonsViewModel,
    modifier: Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load subjects screen")
    Header(
        SharedResourcesjvmMain.strings.screenLessonsName,
//        tools = tools(viewModel),
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.weight(AppDefaults.Weights.ONE),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
    ) {
        val state = rememberDatePickerState(initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds())
        CalendarWidget(state, Modifier.weight(AppDefaults.Weights.ONE))
        Column(
            modifier = Modifier.weight(AppDefaults.Weights.ONE),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL)
        ) {
            Text("Lessons list of date")
        }
    }
}
