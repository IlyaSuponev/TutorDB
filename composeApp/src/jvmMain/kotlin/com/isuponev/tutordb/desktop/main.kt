package com.isuponev.tutordb.desktop

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.desktop.utils.AppTransactionManager
import com.isuponev.tutordb.desktop.views.MAIN_WINDOW_MIN_HEIGHT
import com.isuponev.tutordb.desktop.views.MAIN_WINDOW_MIN_WIDTH
import com.isuponev.tutordb.desktop.views.widgets.datetime.TimePicker
import java.awt.Dimension

/**
 * Point of program start.
 */
fun main() {
    AppConfig.logger.i { "App has been started" }
    application {
        Window(
            onCloseRequest = {
                AppConfig.logger.i { "App has been closed" }
                AppTransactionManager.close()
                AppConfig.close()
                exitApplication()
            },
            title = SharedResources.strings.fullAppName.localized(),
            state = rememberWindowState(
                placement = WindowPlacement.Floating,
                position = WindowPosition.Aligned(Alignment.Center),
                size = DpSize(
                    AppDefaults.Platform.MAIN_WINDOW_MIN_WIDTH.dp,
                    AppDefaults.Platform.MAIN_WINDOW_MIN_HEIGHT.dp
                )
            )
        ) {
            window.minimumSize = Dimension(
                AppDefaults.Platform.MAIN_WINDOW_MIN_WIDTH,
                AppDefaults.Platform.MAIN_WINDOW_MIN_HEIGHT
            )
            App()
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        TimePicker(onChangeTime = { println(it) })
    }
}
