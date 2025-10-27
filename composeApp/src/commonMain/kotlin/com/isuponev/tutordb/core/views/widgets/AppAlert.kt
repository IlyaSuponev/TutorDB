package com.isuponev.tutordb.core.views.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusAlert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResources

@Composable
fun AppAlert(
    title: String,
    message: String,
    onConfirm: (() -> Unit),
    icon: ImageVector = Icons.Default.Warning,
    onCancel: (() -> Unit)? = null
) {
    val locale by AppConfig.General.locale.collectAsState()
    AlertDialog(
        onDismissRequest = { if (onCancel != null) onCancel() else onConfirm() },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(locale.localize(SharedResources.strings.lbl_ok))
            }
        },
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        icon = {
            Icon(icon, title)
        },
        dismissButton = {
            if (onCancel != null) {
                Button(
                    onClick = onCancel,
                ) {
                    Text(locale.localize(SharedResources.strings.lbl_cancel))
                }
            }
        }
    )
}