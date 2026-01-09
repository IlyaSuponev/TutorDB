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
) : StudentEditDialogViewModel<Screen.AddStudentScreen>(Screen.AddStudentScreen, navController, db) {
    override fun onAcceptEvent(): Result<Unit> {
        var result = Result.success(Unit)
        allChecks()
            .onSuccess { (name, amount) ->
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
            }
            .onFailure { fail ->
                result = Result.failure(fail)
            }
        return result
    }
}
