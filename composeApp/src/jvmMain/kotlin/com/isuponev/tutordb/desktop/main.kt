package com.isuponev.tutordb.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.App

/**
 * Point of program start.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = SharedResources.strings.fullAppName.localized(),
    ) {
        App()
    }
}
