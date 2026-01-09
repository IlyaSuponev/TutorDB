package com.isuponev.tutordb.desktop.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.Tool
import dev.icerock.moko.resources.StringResource

/**
 * A header component displaying a localized title with optional action tools.
 *
 * This composable renders a Material 3-styled card containing a primary title and an optional row of
 * action buttons (tools). The title is localized using the current application locale, and the tools
 * are displayed as icons with click handlers.
 *
 * @param titleResource The [StringResource] to display as the header title. Will be localized.
 * @param tools Optional list of [Tool] instances representing actionable icons with descriptions.
 *              Each tool must provide an icon and an onClick callback.
 * @param modifier Optional [Modifier] to customize the header's layout behavior.
 */
@Composable
fun Header(
    titleResource: StringResource,
    tools: List<Tool> = emptyList(),
    modifier: Modifier = Modifier
) = CardWidget<RowScope>(
    modifier = modifier,
    cardShape = MaterialTheme.shapes.medium,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
    alignment = Alignment.Top,
) {
    val locale by AppConfig.General.locale.collectAsState()
    Text(
        locale.localize(titleResource),
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
    if (tools.isNotEmpty()) {
        Spacer(Modifier.weight(AppDefaults.Weights.ONE))
        Row {
            tools.forEach { tool ->
                IconButton(
                    onClick = tool.onClick
                ) {
                    Icon(tool.icon, contentDescription = tool.description)
                }
            }
        }
    }
}
