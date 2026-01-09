package com.isuponev.tutordb.desktop.viewmodels.screens.lessons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
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
import com.isuponev.tutordb.desktop.database.dao.LessonsDao
import com.isuponev.tutordb.desktop.database.dao.StudentsDao
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import com.isuponev.tutordb.desktop.utils.getSystemCurrency
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.DialogViewModel
import java.math.BigDecimal
import javax.money.CurrencyUnit
import javax.money.Monetary
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

abstract class LessonEditDialogViewModel<S : Screen>(
    screen: S,
    navController: NavHostController,
    db: Database,
    chosenDate: LocalDateTime
) : DialogViewModel<S>(screen, navController) {
    protected val lessonsDao = LessonsDao.new(db)
    protected val studentsDao = StudentsDao.new(db)
    protected val subjectsDao = SubjectsDao.new(db)

    protected val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    protected val _nameErrorMessage = MutableStateFlow<String?>(null)
    val nameErrorMessage: StateFlow<String?> get() = _nameErrorMessage

    protected val _dateTime = MutableStateFlow(chosenDate)
    val dateTime: StateFlow<LocalDateTime> get() = _dateTime

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

    protected val _isConducted = MutableStateFlow(false)
    val isConducted: StateFlow<Boolean>
        get() = _isConducted

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

    fun onChooseDate(newValue: LocalDate) {
        i("Choosing date: $newValue")
        _dateTime.value = LocalDateTime(newValue, _dateTime.value.time)
    }

    fun onChooseTime(newValue: LocalTime) {
        i("Choosing time: $newValue")
        _dateTime.value = LocalDateTime(_dateTime.value.date, newValue)
    }

    fun onChangeDuration(newValue: Duration) {
        _duration.value = newValue
    }

    fun onChangeIsConducted(newValue: Boolean) {
        _isConducted.value = newValue
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

    override fun onCancelEvent() {
        i("Cancelling lesson edit dialog")
    }
}