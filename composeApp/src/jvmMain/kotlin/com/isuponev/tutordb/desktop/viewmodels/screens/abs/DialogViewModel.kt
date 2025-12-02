package com.isuponev.tutordb.desktop.viewmodels.screens.abs

import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.utils.Result
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen

abstract class DialogViewModel<S: Screen>(
    screen: S,
    protected val navController: NavHostController
) : AppScreenViewModel<S>(screen) {
    fun onClickCancel() {
        onCancelEvent()
        navController.navigateUp()
    }
    fun onClickAccept() {
        val result = onAcceptEvent()
        result
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
}