package com.isuponev.tutordb.desktop.viewmodels.screens.student

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.StudentsDao
import kotlinx.coroutines.flow.StateFlow

/**
 * A ViewModel for managing the Students screen in the application.
 *
 * This class currently serves as a placeholder/stub implementation for the Students feature.
 * It inherits from [com.isuponev.tutordb.core.views.screens.AppScreenViewModel] and is associated with [com.isuponev.tutordb.core.views.screens.Screen.StudentsScreen].
 * Navigation between screens is handled via the provided [androidx.navigation.NavHostController], though no
 * specific navigation logic has been implemented yet.
 *
 * @param navController The navigation controller used to handle screen transitions.
 *                          This parameter is passed to the superclass and may be used in
 *                          future implementations to navigate from the Students screen.
 */
class StudentsViewModel(
    private val navController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen) {
    private val studentsDao = StudentsDao.new(db)

    val students: StateFlow<List<Student>>
        get() = studentsDao.all

    fun onClickAddStudent() {
        navController.navigate(Screen.AddStudentScreen)
    }

    fun onEditStudent(student: Student) {
        navController.navigate(
            Screen.EditStudentScreen(student.id)
        )
    }

    fun onRemoveStudent(student: Student) {
        studentsDao.remove(student) { throwable ->
            e("Can't remove student $student", throwable)
        }
    }

    companion object {
        val DESCRIPTION_MIN_HEIGHT = 60.dp
        const val COLUMN_COUNT = 3
    }
}