package com.isuponev.tutordb.desktop.viewmodels.screens

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException

class AddSubjectViewModel(
    private val navController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.AddSubjectScreen>(Screen.AddSubjectScreen) {
    private val subjectsDao = SubjectsDao.new(db)
    private val _name = MutableStateFlow("")
    val name: StateFlow<String>
        get() = _name

    private val _description = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?>
        get() = _nameError

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
        i("Saving new subject")
        val newName = try {
            Name.of(_name.value.trim())
        } catch (e: IllegalArgumentException) {
            _nameError.value = e.message
            return
        }
        subjectsDao.create(
            newName,
            _description.value,
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
}