package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import dev.icerock.moko.resources.StringResource

@Composable
internal fun ScreenHeader(
    titleResource: StringResource,
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
}