package com.isuponev.tutordb.core.views.screens

import androidx.lifecycle.ViewModel
import com.isuponev.tutordb.core.config.AppConfig

abstract class AppScreenViewModel<S: Screen>(protected val screen: S) : ViewModel() {
    fun i(message: String) {
        AppConfig.logger.i(tag = screen.javaClass.simpleName) { message }
    }

    fun w(message: String) {
        AppConfig.logger.i(tag = screen.javaClass.simpleName) { message }
    }

    fun e(message: String, throwable: Throwable) {
        AppConfig.logger.e(tag = screen.javaClass.simpleName, throwable = throwable) { message }
    }
}