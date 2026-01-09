package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.utils.now
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

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
    private val _date = MutableStateFlow(screen.chosenDate)
    val date: StateFlow<LocalDate>
        get() = _date

    fun onClickAddLesson() {
        navHostController.navigate(
            Screen.AddLessonScreen(LocalDateTime(
                date.value,
                LocalTime.now()
            ))
        )
    }

    fun onChooseDate(date: LocalDate) {
        i("Choose date: $date")
        _date.value = date
    }
}