package com.isuponev.tutordb.core.config.ui

import kotlinx.serialization.Serializable

@Serializable
data class UIConfigData(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)
