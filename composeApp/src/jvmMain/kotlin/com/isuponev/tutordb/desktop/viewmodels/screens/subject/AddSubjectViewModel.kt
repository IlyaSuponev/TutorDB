package com.isuponev.tutordb.desktop.viewmodels.screens.subject

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
) : DialogViewModel<Screen.AddSubjectScreen>(Screen.AddSubjectScreen, navController) {
    private val subjectsDao = SubjectsDao.new(db)
    private val _name = MutableStateFlow("")

    /**
     * A [StateFlow] that exposes the current value of the subject name input field.
     */
    val name: StateFlow<String>
        get() = _name

    private val _description = MutableStateFlow("")

    /**
     * A [StateFlow] that exposes the current value of the subject description input field.
     */
    val description: StateFlow<String>
        get() = _description

    private val _nameError = MutableStateFlow<String?>(null)

    /**
     * A [StateFlow] that exposes any error message related to the subject name input.
     */
    val nameError: StateFlow<String?>
        get() = _nameError

    /**
     * Updates the name input field and clears any existing error message.
     *
     * @param newValue The new value entered by the user.
     */
    fun onNameChanged(newValue: String) {
        _name.value = newValue
        if (_nameError.value != null) _nameError.value = null
    }

    /**
     * Updates the description input field.
     *
     * @param newValue The new value entered by the user.
     */
    fun onDescriptionChanged(newValue: String) {
        _description.value = newValue
    }

    override fun onAcceptEvent() {
        i("Saving new subject")
        val newName = try {
            Name.of(_name.value.trim())
        } catch (e: IllegalArgumentException) {
            _nameError.value = e.message
            return
        }
        subjectsDao.insert(
            SubjectsDao.IData(newName, _description.value),
            { subject ->
                i("Saved new subject: $subject")
                viewModelScope.launch {
                    navController.navigateUp()
                }
            },
            { error ->
                e("Failed to save new subject", error)
                when (error) {
                    is ExposedSQLException -> {
                        _nameError.value = "Subject with name '${_name.value}' already exists"
                    }
                }
            }
        )
    }

    override fun onCancelEvent() {
        i("Cancelling creation of new subject")
    }
}
