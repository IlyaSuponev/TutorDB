package com.isuponev.tutordb.core.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

typealias ToolMenuElement = Triple<String, ImageVector, () -> Unit>

@Composable
expect fun ToolMenu(
    modifier: Modifier = Modifier,
    toolMenuElements: List<ToolMenuElement> = emptyList()
)
