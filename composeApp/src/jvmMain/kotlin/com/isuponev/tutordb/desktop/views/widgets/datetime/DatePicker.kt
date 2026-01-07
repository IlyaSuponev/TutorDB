package com.isuponev.tutordb.desktop.views.widgets.datetime

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.utils.fromUTCMillis
import com.isuponev.tutordb.core.utils.localizedFormat
import com.isuponev.tutordb.core.utils.now
import com.isuponev.tutordb.core.utils.toUTCMillis
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import java.time.format.FormatStyle
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    state: DatePickerState,
    title: String,
) = DatePicker(
    state,
    Modifier.fillMaxSize(),
    title = {
        Text(
            title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.fillMaxWidth()
        )
    },
    showModeToggle = false,
    colors = DatePickerDefaults.colors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        headlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        navigationContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        dayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        weekdayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        yearContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWidget(
    state: DatePickerState,
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
    val locale by AppConfig.General.locale.collectAsState()
    DatePicker(state, locale.localize(SharedResourcesjvmMain.strings.lbl_lessons_calendar))
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePickerWidget(
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    modifier: Modifier = Modifier,
    scaleOnHover: Float = AppDefaults.Scales.SMALL
) {
    val locale by AppConfig.General.locale.collectAsState()
    val state = remember {
        DatePickerState(
            locale.type,
            initialDate.toUTCMillis()
        )
    }
    DatePickerWidget(state, modifier, scaleOnHover)
    LaunchedEffect(state.selectedDateMillis) {
        if (state.selectedDateMillis != null) {
            onDateSelected(LocalDate.fromUTCMillis(state.selectedDateMillis!!))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePickerDialogWidget(
    state: DatePickerState,
    initExpanded: Boolean = false,
    modifier: Modifier = Modifier,
    onUnSelectedDate: () -> String = { "Select date" }
) = Box(modifier) {
    var expanded by remember { mutableStateOf(initExpanded) }
    val locale by AppConfig.General.locale.collectAsState()
    Button(
        onClick = { expanded = true }
    ) {
        Text(
            state.selectedDateMillis?.let { selectedDateMillis ->
                val instant = Instant.fromEpochMilliseconds(selectedDateMillis)
                val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                localDate.localizedFormat(locale.type, FormatStyle.LONG)
            } ?: onUnSelectedDate()
        )
    }
    if (expanded) {
        val millisBeforeChoose by remember { mutableStateOf(state.selectedDateMillis) }
        DatePickerDialog(
            onDismissRequest = {
                state.selectedDateMillis = millisBeforeChoose
                expanded = false
            },
            confirmButton = {
                TextButton(onClick = { expanded = false }) {
                    Text(
                        locale.localize(SharedResources.strings.lbl_ok)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.selectedDateMillis = millisBeforeChoose
                        expanded = false
                    }
                ) {
                    Text(locale.localize(SharedResources.strings.lbl_cancel))
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                headlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                dayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                weekdayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                yearContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            DatePicker(
                state,
                locale.localize(SharedResourcesjvmMain.strings.lbl_lessons_calendar)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePickerDialogWidget(
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    initExpanded: Boolean = false,
    modifier: Modifier = Modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    val state = remember {
        DatePickerState(
            locale.type,
            initialDate.toUTCMillis()
        )
    }
    DatePickerDialogWidget(
        state,
        initExpanded = initExpanded,
        modifier = modifier
    )
    LaunchedEffect(state.selectedDateMillis) {
        if (state.selectedDateMillis != null) {
            onDateSelected(LocalDate.fromUTCMillis(state.selectedDateMillis!!))
        }
    }
}
