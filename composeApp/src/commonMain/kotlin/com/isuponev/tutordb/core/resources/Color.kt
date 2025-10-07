package com.isuponev.tutordb.core.resources

import dev.icerock.moko.graphics.Color

fun Color.toComposeColor(): androidx.compose.ui.graphics.Color {
    return androidx.compose.ui.graphics.Color(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha
    )
}
