package com.isuponev.tutordb.core.resources

import androidx.compose.ui.graphics.Color
import dev.icerock.moko.graphics.Color as MokoColor

/**
 * Converts a MOKO [Color] to its equivalent Jetpack Compose [Color].
 *
 * This extension function facilitates color interoperability in Compose Multiplatform
 * applications by converting MOKO's cross-platform color representation to Jetpack
 * Compose's native color format while maintaining identical RGBA values.
 *
 * @return A Compose [Color] with the same red, green, blue, and alpha components
 *         as the original MOKO color.
 *
 * @see Color
 * @see MokoColor
 */
fun MokoColor.toComposeColor(): Color = Color(
    red = red,
    green = green,
    blue = blue,
    alpha = alpha
)
