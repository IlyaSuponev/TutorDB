package com.isuponev.tutordb.desktop

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Point of program start.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TutorDB",
    ) {
        Text("Hello World")
    }
}
