package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.utils.getStartOfTodayInUtcMillisWithOffset
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import java.util.Locale
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    screen: Screen.LessonsScreen,
    private val navHostController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen) {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
    private val _calendarState = MutableStateFlow(
        DatePickerState(
            Locale.getDefault(),
            screen.chosenDateMillis  ?: getStartOfTodayInUtcMillisWithOffset()
        )
    )
    @OptIn(ExperimentalMaterial3Api::class)
    val calendarState: StateFlow<DatePickerState>
        get() = _calendarState

}