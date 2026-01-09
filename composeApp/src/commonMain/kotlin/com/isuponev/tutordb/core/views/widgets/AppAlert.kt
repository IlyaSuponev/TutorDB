package com.isuponev.tutordb.core.views.widgets

import androidx.compose.material.icons.Icons
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

/**
 * A customizable alert dialog component for displaying important messages to users.
 *
 * This composable displays a Material 3 AlertDialog with localized buttons and optional cancel functionality.
 * Uses application-wide locale settings for button text localization.
 *
 * @param title The main title of the alert dialog.
 * @param message The detailed message to display in the alert body.
 * @param onConfirm The callback to execute when the confirm button is clicked.
 * @param icon The icon to display at the top-left corner of the dialog (defaults to Warning icon).
 * @param onCancel Optional callback to execute when the dismiss button is clicked or dialog is dismissed.
 *                 If null, the dialog will automatically execute [onConfirm] when dismissed.
 */
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
