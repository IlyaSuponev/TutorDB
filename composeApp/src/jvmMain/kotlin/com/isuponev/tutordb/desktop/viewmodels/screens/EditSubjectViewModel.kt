package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException

class EditSubjectViewModel(
    screen: Screen.EditSubjectScreen,
    private val navController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.EditSubjectScreen>(screen) {
    private val subjectsDao = SubjectsDao.new(db)
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State>
        get() = _state

    private val _name = MutableStateFlow("")
    val name: StateFlow<String>
        get() = _name

    private val _description = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?>
        get() = _nameError

    private var _subject: Subject? = null

    init {
        subjectsDao.getById(
            screen.subjectId,
            { subject ->
                if (subject != null) {
                    _name.value = subject.name.value
                    _description.value = subject.description
                    _subject = subject
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
                viewModelScope.launch {
                    e("Subject with id '${screen.subjectId}' not found", throwable)
                    navController.navigateUp()
                }
            }
        )
    }

    fun onNameChanged(newValue: String) {
        _name.value = newValue
        if (_nameError.value != null) _nameError.value = null
    }

    fun onDescriptionChanged(newValue: String) {
        _description.value = newValue
    }

    fun onClickCancel() {
        i("Cancelling creation of new subject")
        navController.navigateUp()
    }

    fun onClickSave() {
        val oldSubject = _subject ?: return
        val newName = try {
            Name.of(_name.value.trim())
        } catch (e: IllegalArgumentException) {
            _nameError.value = e.message
            return
        }
        if (newName == oldSubject.name && _description.value == oldSubject.description) {
            navController.navigateUp()
            return
        }
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

    enum class State {
        Loading,
        Loaded,
    }
}