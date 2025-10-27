package com.isuponev.tutordb.core.config.runtime

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class AlertData(
    val isVisible: Boolean,
    val title: String,
    val message: String,
    val icon: ImageVector = Icons.Default.Warning,
    val onCancel: (() -> Unit)? = null
)
