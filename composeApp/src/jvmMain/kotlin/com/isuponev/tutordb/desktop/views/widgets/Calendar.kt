package com.isuponev.tutordb.desktop.views.widgets

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarWidget(
    state: DatePickerState,
    modifier: Modifier = Modifier
) = CardWidget<BoxScope>(
    modifier = modifier,
    cardShape = MaterialTheme.shapes.small,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    scaleOnHover = AppDefaults.Scales.SMALL,
    alignment = Alignment.Center,
    contentPadding = PaddingValues(AppDefaults.Paddings.SMALL)
) {
//    LocalConfiguration
//    CompositionLocalProvider()
    val locale by AppConfig.General.locale.collectAsState()
    DatePicker(
        state,
        Modifier.fillMaxSize(),
        title = {
            Text(
                locale.localize(SharedResourcesjvmMain.strings.lbl_lessons_calendar),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        },
        headline = {
            DatePickerDefaults.DatePickerHeadline(
                selectedDateMillis = state.selectedDateMillis,
                displayMode = state.displayMode,
                dateFormatter = remember { DatePickerDefaults.dateFormatter() }
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
            yearContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
