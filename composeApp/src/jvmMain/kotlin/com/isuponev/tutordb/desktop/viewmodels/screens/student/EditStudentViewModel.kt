package com.isuponev.tutordb.desktop.viewmodels.screens.student

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.utils.AppError
import com.isuponev.tutordb.core.utils.Result
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.StudentsDao
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.utils.getSystemCurrency
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import java.math.BigDecimal
import javax.money.CurrencyUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.javamoney.moneta.Money

class EditStudentViewModel(
    screen: Screen.EditStudentScreen,
    navController: NavHostController,
    db: Database
) : StudentEditDialogViewModel<Screen.EditStudentScreen>(screen, navController, db) {
    private val _state = MutableStateFlow(State.Loading)

    val state: StateFlow<State>
        get() = _state

    private val _loadingProgress = MutableStateFlow(PROGRESS_ON_START)

    val loadingProgress: StateFlow<Float>
        get() = _loadingProgress

    init {
        _loadingProgress.value = PROGRESS_ON_HALF_HALF
        studentsDao.getById(
            screen.studentId,
            { student ->
                _loadingProgress.value = PROGRESS_ON_HALF
                if (student == null) {
                    w("Subject with id '${screen.studentId}' not found")
                    viewModelScope.launch { navController.navigateUp() }
                } else {
                    _name.value = student.name.value
                    _amount.value = BigDecimal(student.hourCost.number.toString()).toPlainString()
                    _currency.value = student.hourCost.currency
                    _subjects.value = student.subjects.toList()
                    _loadingProgress.value = PROGRESS_ON_END
                    _state.value = State.Loaded
                }
            },
            { throwable ->
                e("Can't find student to edit", throwable)
                viewModelScope.launch { navController.navigateUp() }
            }
        )
    }

    override fun onAcceptEvent(): Result<Unit> {
        var result = Result.success(Unit)
        allChecks()
            .onSuccess { (name, amount) ->
                studentsDao.update(
                    Student(
                        screen.studentId,
                        name,
                        Money.of(amount, currency.value),
                        subjects.value.toSet()
                    ),
                    {
                        i("Student updated")
                    },
                    { throwable ->
                        e("Can't update new student", throwable)
                    }
                )
            }
            .onFailure { fail ->
                result = Result.failure(fail)
            }
        return result
    }

    enum class State {
        /**
         * Indicates the ViewModel is loading data.
         */
        Loading,

        /**
         * Indicates the ViewModel has successfully loaded data and is ready for interaction.
         */
        Loaded,
    }
    companion object {
        const val PROGRESS_ON_START = 0f
        const val PROGRESS_ON_HALF_HALF = 0.25f
        const val PROGRESS_ON_HALF = 0.5f
        const val PROGRESS_ON_END = 1f
    }
}
