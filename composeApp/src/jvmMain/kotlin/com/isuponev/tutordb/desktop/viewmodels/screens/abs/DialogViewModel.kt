package com.isuponev.tutordb.desktop.viewmodels.screens.abs

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.utils.Result
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import java.math.BigDecimal

abstract class DialogViewModel<S: Screen>(
    screen: S,
    protected val navController: NavHostController
) : AppScreenViewModel<S>(screen) {
    fun onClickCancel() {
        onCancelEvent()
        navController.navigateUp()
    }
    fun onClickAccept() {
        onAcceptEvent()
            .onSuccess { navController.navigateUp() }
            .onFailure { appError ->
                appError.onValidationError { error ->
                    i("Not approve validation: $error")
                }.onInvalidStateError { error ->
                    i("Invalid state of ${javaClass.name}: $error")
                    navController.navigateUp()
                }
            }
            .onError { throwable ->
                e("Can't accept by error: ", throwable)
            }
    }
    protected abstract fun onAcceptEvent(): Result<Unit>

    protected abstract fun onCancelEvent()

    protected fun convertToName(value: String): Result<Name> {
        return try {
            Result.success(Name.of(value))
        } catch (ex: IllegalArgumentException) {
            Result.error(ex)
        }
    }
    protected fun convertToAmount(value: String): Result<BigDecimal> {
        return try {
            Result.success(BigDecimal(value))
        } catch (ex: IllegalArgumentException) {
            Result.error(ex)
        }
    }
}