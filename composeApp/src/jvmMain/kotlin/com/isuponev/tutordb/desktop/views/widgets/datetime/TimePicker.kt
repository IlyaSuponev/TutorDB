package com.isuponev.tutordb.desktop.views.widgets.datetime

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWidget(
    state: TimePickerState,
    modifier: Modifier = Modifier,
    scaleOnHover: Float = AppDefaults.Scales.SMALL
) = CardWidget<BoxScope>(
    modifier = modifier,
    cardShape = MaterialTheme.shapes.small,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    scaleOnHover = scaleOnHover,
    alignment = Alignment.Center,
    contentPadding = PaddingValues(AppDefaults.Paddings.SMALL)
) {
    TimePicker(
        state,
        colors = TimePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        layoutType = TimePickerLayoutType.Vertical
    )
}