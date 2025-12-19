package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.isuponev.tutordb.desktop.database.dao.LessonsDao
import com.isuponev.tutordb.desktop.database.dao.StudentsDao
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.utils.getSystemCurrency
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.money.CurrencyUnit
import javax.money.Monetary
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class LessonEditDialogViewModel<S : Screen>(
    screen: S,
    navController: NavHostController,
    db: Database
) : DialogViewModel<S>(screen, navController) {

    protected val lessonsDao = LessonsDao.new(db)
    protected val studentsDao = StudentsDao.new(db)
    protected val subjectsDao = SubjectsDao.new(db)

    protected val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    protected val _nameErrorMessage = MutableStateFlow<String?>(null)
    val nameErrorMessage: StateFlow<String?> get() = _nameErrorMessage

    protected val _dateOfStart = MutableStateFlow(LocalDateTime.now())
    val dateOfStart: StateFlow<LocalDateTime> get() = _dateOfStart

    protected val _duration = MutableStateFlow(60.minutes)
    val duration: StateFlow<Duration> get() = _duration

    protected val _hourCostAmount = MutableStateFlow("")
    val hourCostAmount: StateFlow<String> get() = _hourCostAmount

    protected val _hourCostAmountErrorMessage = MutableStateFlow<String?>(null)
    val hourCostAmountErrorMessage: StateFlow<String?> get() = _hourCostAmountErrorMessage

    protected val _hourCostCurrency = MutableStateFlow(getSystemCurrency())
    val hourCostCurrency: StateFlow<CurrencyUnit> get() = _hourCostCurrency

    protected val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    protected val _student = MutableStateFlow<Student?>(null)
    val student: StateFlow<Student?> get() = _student

    protected val _subject = MutableStateFlow<Subject?>(null)
    val subject: StateFlow<Subject?> get() = _subject

    val availableStudents: StateFlow<List<Student>> get() = studentsDao.all
    val availableSubjects: StateFlow<List<Subject>> get() = subjectsDao.all

    init {
        i("Initializing lesson edit dialog")
        this.viewModelScope.launch {
            delay(500)
            if (availableSubjects.value.isEmpty()) {
                w("No subjects available")
                AppConfig.Runtime.alert(
                    "No subjects available",
                    "No subjects available. Please add a subject first.",
                    Icons.Default.Error
                )
                navController.popBackStack(Screen.HomeScreen, false)
            } else if (availableStudents.value.isEmpty()) {
                w("No students available")
                AppConfig.Runtime.alert(
                    "No students available",
                    "No students available. Please add a student first.",
                    Icons.Default.Error
                )
                navController.popBackStack(Screen.HomeScreen, false)
            } else {
                i("Subjects and students are available")
                _subject.value = availableSubjects.value.first()
                _student.value = availableStudents.value.first()
            }
        }
    }

    fun onChangeName(newValue: String) {
        _name.value = newValue
        if (_nameErrorMessage.value != null) _nameErrorMessage.value = null
    }

    fun onChangeDateOfStart(newValue: LocalDateTime) {
        _dateOfStart.value = newValue
    }

    fun onChangeDuration(newValue: Duration) {
        _duration.value = newValue
    }

    fun onChangeHourCostAmount(newValue: String) {
        _hourCostAmount.value = newValue
        if (_hourCostAmountErrorMessage.value != null) _hourCostAmountErrorMessage.value = null
    }

    fun onChangeHourCostCurrency(newValue: CurrencyUnit) {
        _hourCostCurrency.value = newValue
    }

    fun onChangeDescription(newValue: String) {
        _description.value = newValue
    }

    fun onSelectStudent(student: Student?) {
        _student.value = student
    }

    fun onSelectSubject(subject: Subject?) {
        _subject.value = subject
    }

    protected fun convertName(): Result<Name> {
        return try {
            Result.success(Name.of(_name.value))
        } catch (ex: IllegalArgumentException) {
            val message = AppConfig.General.locale.value.localize(
                SharedResourcesjvmMain.strings.error_invalid_name_of_entity
            )
            _nameErrorMessage.value = message
            Result.failure(
                AppError.ValidationError("Invalid lesson name", "name", ex.message ?: "Invalid format")
            )
        }
    }

    protected fun convertHourCost(): Result<javax.money.MonetaryAmount> {
        return try {
            val amount = BigDecimal(_hourCostAmount.value)
            if (amount < BigDecimal.ZERO) {
                _hourCostAmountErrorMessage.value = "Amount cannot be negative"
                return Result.failure(
                    AppError.ValidationError("Invalid hour cost", "hourCost", "Amount is negative")
                )
            }
            val monetaryAmount = Monetary.getDefaultAmountFactory()
                .setNumber(amount)
                .setCurrency(_hourCostCurrency.value)
                .create()
            Result.success(monetaryAmount)
        } catch (ex: NumberFormatException) {
            _hourCostAmountErrorMessage.value = "Invalid number format"
            Result.failure(
                AppError.ValidationError("Invalid hour cost", "hourCost", "Not a valid number")
            )
        }
    }

    override fun onCancelEvent() {
        i("Cancelling lesson edit dialog")
    }
}