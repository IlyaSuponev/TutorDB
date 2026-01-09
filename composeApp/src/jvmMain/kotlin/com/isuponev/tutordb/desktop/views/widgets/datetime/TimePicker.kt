package com.isuponev.tutordb.desktop.views.widgets.datetime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.utils.now
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.desktop.views.widgets.WheelPicker
import com.isuponev.tutordb.desktop.views.widgets.WheelPickerColors
import com.isuponev.tutordb.desktop.views.widgets.WheelPickerTypography
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    timeState: TimePickerState,
    modifier: Modifier = Modifier,
    header: (@Composable RowScope.() -> Unit)? = null,
    hourTitle: String? = null,
    minuteTitle: String? = null,
    periodTitle: String? = null,
) {
    val hours = (
            (if (timeState.is24hour) 0 else 1)..(if (timeState.is24hour) 23 else 12)
            ).map { it.toString() }
    val minutes = (0..59).map { it.toString().padStart(2, '0') }
    val periods = if (timeState.is24hour) emptyList() else listOf("AM", "PM")
    var currentPeriod by remember { mutableStateOf(0) }
    Column(
        modifier = modifier.background(
            MaterialTheme.colorScheme.primaryContainer
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (header != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = header,
                modifier = Modifier
            )
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WheelPicker(
                items = hours,
                initialIndex = 0,
                onIndexChange = { idx ->
                    timeState.hour = hours[idx].toInt()
                    if (!timeState.is24hour) timeState.hour = (timeState.hour % 12 + 12 * currentPeriod) % 24
                },
                title = hourTitle,
                modifier = Modifier.padding(AppDefaults.Paddings.MEDIUM),
                colors = WheelPickerColors.defaults(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0f)
                )
            )
            Text(
                ":",
                textAlign = TextAlign.Center,
                fontWeight = WheelPickerTypography().selectedItemFontWeight,
                fontSize = WheelPickerTypography().selectedItemFontSize
            )
            WheelPicker(
                items = minutes,
                initialIndex = 0,
                onIndexChange = { idx ->
                    timeState.minute = minutes[idx].toInt()
                },
                title = minuteTitle,
                modifier = Modifier.padding(AppDefaults.Paddings.MEDIUM),
                colors = WheelPickerColors.defaults(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0f)
                )
            )
            if (!timeState.is24hour) {
                WheelPicker(
                    items = periods,
                    initialIndex = 0,
                    onIndexChange = { idx ->
                        timeState.hour = (timeState.hour % 12 + 12 * idx) % 24
                        currentPeriod = idx
                    },
                    title = periodTitle,
                    modifier = Modifier.padding(AppDefaults.Paddings.MEDIUM),
                    colors = WheelPickerColors.defaults(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0f)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    onChangeTime: (LocalTime) -> Unit,
    initLocalTime: LocalTime = LocalTime.now(),
    is24HourMode: Boolean = false,
    modifier: Modifier = Modifier,
    header: (@Composable RowScope.() -> Unit)? = null,
    hourTitle: String? = null,
    minuteTitle: String? = null,
    periodTitle: String? = null,
) {
    val state = rememberTimePickerState(
        initLocalTime.hour,
        initLocalTime.minute,
        is24HourMode
    )
    TimePicker(
        state,
        modifier,
        header,
        hourTitle,
        minuteTitle,
        periodTitle
    )
    LaunchedEffect(state.hour, state.minute) {
        onChangeTime(LocalTime(state.hour, state.minute, 0, 0))
    }
}
