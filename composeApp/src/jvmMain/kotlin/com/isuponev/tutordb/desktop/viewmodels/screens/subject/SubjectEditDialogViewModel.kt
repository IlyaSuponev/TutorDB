package com.isuponev.tutordb.desktop.viewmodels.screens.subject

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SubjectEditDialogViewModel<S: Screen>(
    screen: S,
    navController: NavHostController,
    db: Database
): DialogViewModel<S>(screen, navController) {
    protected val subjectsDao = SubjectsDao.new(db)

    protected val _name = MutableStateFlow("")
    val name: StateFlow<String>
        get() = _name

    protected val _description = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    protected val _nameErrorMessage = MutableStateFlow<String?>(null)
    val nameEditErrorMessage: StateFlow<String?>
        get() = _nameErrorMessage

    fun onNameChanged(newValue: String) {
        _name.value = newValue
        if (_nameErrorMessage.value != null) _nameErrorMessage.value = null
    }

    fun onDescriptionChanged(newValue: String) {
        _description.value = newValue
    }

    override fun onCancelEvent() {
        i("Cancelling dialog process")
    }

    protected fun convertName(): Name? {
        try {
            return Name.of(name.value)
        } catch (_: IllegalArgumentException) {
            val locale = AppConfig.General.locale.value
            _nameErrorMessage.value = locale.localize(
                SharedResourcesjvmMain.strings.error_invalid_name_of_entity
            )
        }
        return null
    }
}