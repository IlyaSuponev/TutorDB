package com.isuponev.tutordb.desktop.viewmodels.screens.subject

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.utils.AppError
import com.isuponev.tutordb.core.utils.Result
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException

/**
 * A ViewModel for the "Add Subject" screen in the application.
 *
 * This class manages the state and behavior of the UI for adding a new subject to the database,
 * including input validation, navigation, and communication with the DAO layer.
 *
 * @param navController The [NavHostController] used for navigating between screens.
 * @param db The [Database] instance used to access the database.
 */
class AddSubjectViewModel(
    navController: NavHostController,
    db: Database
) : SubjectEditDialogViewModel<Screen.AddSubjectScreen>(Screen.AddSubjectScreen, navController, db) {
    override fun onAcceptEvent(): Result<Unit> {
        i("Saving new subject")
        val newName = convertName() ?: return Result.failure(
            AppError.ValidationError(
                "Invalid name of student",
                "name",
                "Value of name is not matches with its regex"
            )
        )
        subjectsDao.insert(
            SubjectsDao.IData(newName, _description.value),
            { subject ->
                i("Saved new subject: $subject")
            },
            { error ->
                e("Failed to save new subject", error)
                when (error) {
                    is ExposedSQLException -> {
                        _nameErrorMessage.value = "Subject with name '${_name.value}' already exists"
                    }
                }
            }
        )
        return Result.success(Unit)
    }
}
