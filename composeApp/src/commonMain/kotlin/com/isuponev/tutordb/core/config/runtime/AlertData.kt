package com.isuponev.tutordb.core.config.runtime

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class for alerts.
 *
 * @property isVisible Whether the alert is visible or not.
 * @property title The title of the alert.
 * @property message The message of the alert.
 * @property icon The icon of the alert.
 * @property onCancel The callback to be called when the alert is dismissed.
 */
data class AlertData(
    val isVisible: Boolean,
    val title: String,
    val message: String,
    val icon: ImageVector = Icons.Default.Warning,
    val onCancel: (() -> Unit)? = null
)
