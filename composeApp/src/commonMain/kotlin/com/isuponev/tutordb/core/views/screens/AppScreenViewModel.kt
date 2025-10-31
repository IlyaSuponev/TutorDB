package com.isuponev.tutordb.core.views.screens

import androidx.lifecycle.ViewModel
import com.isuponev.tutordb.core.config.AppConfig

/**
 * Abstract view model for screens in app.
 *
 * @param S type of screen for getting navigation data
 * @property screen instance of screen of type S
 */
abstract class AppScreenViewModel<S : Screen>(protected val screen: S) : ViewModel() {
    /**
     * Logs info message.
     *
     * @param message message to log
     */
    fun i(message: String) {
        AppConfig.logger.i(tag = screen.javaClass.simpleName) { message }
    }

    /**
     * Logs warning message.
     *
     * @param message message to log
     */
    fun w(message: String) {
        AppConfig.logger.i(tag = screen.javaClass.simpleName) { message }
    }

    /**
     * Logs error message.
     *
     * @param message message to log
     * @param throwable throwable to log
     */
    fun e(message: String, throwable: Throwable) {
        AppConfig.logger.e(tag = screen.javaClass.simpleName, throwable = throwable) { message }
    }
}
