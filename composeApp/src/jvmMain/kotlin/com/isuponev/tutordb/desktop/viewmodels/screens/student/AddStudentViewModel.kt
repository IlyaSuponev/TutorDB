package com.isuponev.tutordb.desktop.viewmodels.screens.student

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
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
import org.javamoney.moneta.Money

class AddStudentViewModel(
    navController: NavHostController,
    db: Database
) : DialogViewModel<Screen.AddStudentScreen>(Screen.AddStudentScreen, navController) {
    private val studentsDao = StudentsDao.new(db)
    private val subjectsDao = SubjectsDao.new(db)

    private val _name = MutableStateFlow("")
    val name: StateFlow<String>
        get() = _name

    private val _nameErrorMessage = MutableStateFlow<String?>(null)
    val nameErrorMessage: StateFlow<String?>
        get() = _nameErrorMessage


    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String>
        get() = _amount

    private val _amountErrorMessage = MutableStateFlow<String?>(null)
    val amountErrorMessage: StateFlow<String?>
        get() = _amountErrorMessage


    private val _currency = MutableStateFlow(getSystemCurrency())
    val currency: StateFlow<CurrencyUnit>
        get() = _currency

    private val _subjects = MutableStateFlow(emptyList<Subject>())
    val subjects: StateFlow<List<Subject>>
        get() = _subjects

    val availableSubjects: StateFlow<List<Subject>>
        get() = subjectsDao.all

    fun onChangeName(newValue: String) {
        _name.value = newValue
        if (_nameErrorMessage.value != null) _nameErrorMessage.value = null
    }

    fun onChangeAmount(newValue: String) {
        _amount.value = newValue
        if (_amountErrorMessage.value != null) _amountErrorMessage.value = null
    }

    fun onChangeCurrency(newValue: CurrencyUnit) {
        _currency.value = newValue
    }

    fun onAddSubject(newValue: Subject) {
        _subjects.update { it + newValue }
    }

    fun onRemoveSubject(value: Subject) {
        _subjects.update { it - value }
    }

    override fun onAcceptEvent(): Result<Unit> {
        i("Click on accept event")
        val name = convertName() ?: return Result.failure(
            AppError.ValidationError(
                "Invalid name of student",
                "name",
                "Value of name is not matches with its regex"
            )
        )
        val amount = convertAmount() ?: return Result.failure(
            AppError.ValidationError(
                "Invalid monetary amount of student",
                "amount",
                "Value of amount of hour cost is not number"
            )
        )
        i("All checked pass")
        studentsDao.insert(
            StudentsDao.IData(
                name,
                Money.of(amount, currency.value),
                subjects.value.toSet()
            ),
            {
                i("Added new student: $it")
            },
            { throwable ->
                e("Can't added new student", throwable)
            }
        )
        return Result.success(Unit)
    }

    override fun onCancelEvent() {
        i("Cancelling creation of new student")
    }

    private fun convertName(): Name? {
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

    private fun convertAmount(): BigDecimal? {
        try {
            return BigDecimal(amount.value)
        } catch (ex: IllegalArgumentException) {
            _amountErrorMessage.value = ex.localizedMessage
        }
        return null
    }

}
