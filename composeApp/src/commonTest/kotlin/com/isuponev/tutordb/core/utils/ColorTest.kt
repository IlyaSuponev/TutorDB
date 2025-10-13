package com.isuponev.tutordb.core.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import dev.icerock.moko.graphics.Color as MokoColor

class ColorTest {
    @ParameterizedTest
    @MethodSource("mokoToComposeConvertProvider")
    fun `test convert moko-color to compose-color`(
        source: MokoColor,
        expectedComposeCode: Long
    ) {
        val result = source.toComposeColor()
        assertEquals(Color(expectedComposeCode), result)
    }

    companion object {
        @JvmStatic
        fun mokoToComposeConvertProvider(): List<Arguments> = listOf(
            Arguments.of(
                MokoColor(0xFFFF0000),
                0x00FFFF00L,
            ),
            Arguments.of(
                MokoColor(0xFFFF00FF),
                0xFFFFFF00L,
            ),
            Arguments.of(
                MokoColor(0x1A2B3C80),
                0x801A2B3CL,
            )
        )
    }
}
