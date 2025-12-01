package com.isuponev.tutordb.desktop.viewmodels.screens.subject

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException

/**
 * A ViewModel for the "Edit Subject" screen in the application.
 *
 * This class manages the state and behavior of the UI for editing an existing subject,
 * including loading subject data from the database, input validation, and updating
 * the database with changes made by the user.
 *
 * @param screen The [Screen.EditSubjectScreen] instance containing the subject ID to edit.
 * @param navController The [NavHostController] used for navigation between screens.
 * @param db The [Database] instance for database operations.
 */
class EditSubjectViewModel(
    screen: Screen.EditSubjectScreen,
    navController: NavHostController,
    db: Database
) : DialogViewModel<Screen.EditSubjectScreen>(screen, navController) {
    private val subjectsDao = SubjectsDao.new(db)
    private val _state = MutableStateFlow<State>(State.Loading)
    private val _loadingProgress = MutableStateFlow(PROGRESS_ON_START)

    /**
     * A [StateFlow] exposing the current state to observers.
     */
    val state: StateFlow<State>
        get() = _state

    /**
     * A [StateFlow] exposing the current loading progress to observers.
     */
    val loadingProgress: StateFlow<Float>
        get() = _loadingProgress

    private val _name = MutableStateFlow("")

    /**
     * A [MutableStateFlow] holding the current name input value for the subject.
     */
    val name: StateFlow<String>
        get() = _name

    private val _description = MutableStateFlow("")

    /**
     * A [MutableStateFlow] holding the current description input value for the subject.
     */
    val description: StateFlow<String>
        get() = _description

    private val _nameError = MutableStateFlow<String?>(null)

    /**
     * A [MutableStateFlow] holding the current description input value for the subject.
     */
    val nameError: StateFlow<String?>
        get() = _nameError

    private var _subject: Subject? = null

    init {
        _loadingProgress.value = PROGRESS_ON_HALF_HALF
        subjectsDao.getById(
            screen.subjectId,
            { subject ->
                _loadingProgress.value = PROGRESS_ON_HALF
                if (subject != null) {
                    _loadingProgress.value = PROGRESS_ON_END - PROGRESS_ON_HALF_HALF
                    _name.value = subject.name.value
                    _description.value = subject.description
                    _subject = subject
                    _loadingProgress.value = PROGRESS_ON_END
                    _state.value = State.Loaded

                } else {
                    viewModelScope.launch {
                        w("Subject with id '${screen.subjectId}' not found")
                        navController.navigateUp()
                        AppConfig.Runtime.alert(
                            "Can't edit subject",
                            "Subject with id '${screen.subjectId}' not found"
                        )
                    }
                }
            },
            { throwable ->
                e("Can't find subject to edit", throwable)
                viewModelScope.launch {
                    navController.navigateUp()
                }
            }
        )
    }

    /**
     * Updates the name input field and clears any existing name error.
     *
     * @param newValue The new name value entered by the user.
     */
    fun onNameChanged(newValue: String) {
        _name.value = newValue
        if (_nameError.value != null) _nameError.value = null
    }

    /**
     * Updates the description input field.
     *
     * @param newValue The new description value entered by the user.
     */
    fun onDescriptionChanged(newValue: String) {
        _description.value = newValue
    }

    override fun onAcceptEvent() {
        val oldSubject = _subject ?: return
        assertNewNameAndDescription().onSuccess { (newName, description) ->
            if (newName == oldSubject.name && description == oldSubject.description) {
                navController.navigateUp()
            } else {
                i("Updating subject")
                subjectsDao.update(
                    Subject(oldSubject.id, newName, _description.value),
                    {
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
        }.onFailure { throwable ->
            _nameError.value = throwable.message
        }
    }

    override fun onCancelEvent() {
        i("Cancelling edit of subject")
    }

    private fun assertNewNameAndDescription(): Result<Pair<Name, String>> {
        val name = Name.of(_name.value.trim())
        val description = _description.value.trim()
        return Result.success(name to description)
    }

    /**
     * Enum class representing possible states of the ViewModel.
     */
    enum class State {
        /**
         * Indicates the ViewModel is loading subject data.
         */
        Loading,

        /**
         * Indicates the ViewModel has successfully loaded subject data and is ready for interaction.
         */
        Loaded,
    }

    private companion object {
        const val PROGRESS_ON_START = 0f
        const val PROGRESS_ON_HALF_HALF = 0.25f
        const val PROGRESS_ON_HALF = 0.5f
        const val PROGRESS_ON_END = 1f
    }
}
