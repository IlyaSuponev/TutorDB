package com.isuponev.tutordb.core.views.screens

import androidx.lifecycle.ViewModel
import com.isuponev.tutordb.core.config.AppConfig

abstract class AppScreenViewModel<S: Screen>(protected val screen: S) : ViewModel() {
    fun logInfo(message: String) {
        AppConfig.logger.i(tag = screen.javaClass.simpleName) { message }
    }
}