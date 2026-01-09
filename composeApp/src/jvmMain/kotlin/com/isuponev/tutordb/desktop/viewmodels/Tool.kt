package com.isuponev.tutordb.desktop.viewmodels

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A data class representing a tool item in the UI.
 *
 * This class is typically used to represent items in a toolbar or menu, containing a description,
 * an icon, and an action to perform when clicked.
 *
 * @property description A brief textual description of the tool's purpose.
 * @property icon The visual icon associated with the tool, represented as an [ImageVector].
 * @property onClick The function to be invoked when the tool is clicked by the user.
 */
data class Tool(
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
