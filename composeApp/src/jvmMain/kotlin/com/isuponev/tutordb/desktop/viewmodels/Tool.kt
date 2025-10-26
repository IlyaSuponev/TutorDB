package com.isuponev.tutordb.desktop.viewmodels

import androidx.compose.ui.graphics.vector.ImageVector

data class Tool(
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
