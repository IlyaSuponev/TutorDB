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
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.Loadable
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
) : StudentEditDialogViewModel<Screen.EditStudentScreen>(screen, navController, db), Loadable {
    private val _state = MutableStateFlow(Loadable.State.Loading)

    override val state: StateFlow<Loadable.State>
        get() = _state

    private val _loadingProgress = MutableStateFlow(Loadable.Progress.PROGRESS_ON_START)

    override val loadingProgress: StateFlow<Loadable.Progress>
        get() = _loadingProgress

    init {
        _loadingProgress.value = Loadable.Progress.PROGRESS_ON_HALF_OF_HALF
        studentsDao.getById(
            screen.studentId,
            { student ->
                _loadingProgress.value = Loadable.Progress.PROGRESS_ON_HALF
                if (student == null) {
                    w("Subject with id '${screen.studentId}' not found")
                    viewModelScope.launch { navController.navigateUp() }
                } else {
                    _name.value = student.name.value
                    _amount.value = BigDecimal(student.hourCost.number.toString()).toPlainString()
                    _currency.value = student.hourCost.currency
                    _subjects.value = student.subjects.toList()
                    _loadingProgress.value = Loadable.Progress.PROGRESS_ON_END
                    _state.value = Loadable.State.Loaded
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
}
