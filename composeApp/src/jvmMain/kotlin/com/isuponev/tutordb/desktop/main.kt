package com.isuponev.tutordb.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.App

/**
 * Point of program start.
 */
fun main() {
    appLogger.i { "App has been started" }
    application {
        Window(
            onCloseRequest = {
                appLogger.i { "App has been closed" }
                AppConfig.close()
                exitApplication()
            },
            title = SharedResources.strings.fullAppName.localized(),
        ) {
            App()
        }
    }
}
