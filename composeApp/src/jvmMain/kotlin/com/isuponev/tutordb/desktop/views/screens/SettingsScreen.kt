package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.SettingsViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.forms.ChooseBoxForm

/**
 * A composable UI component for the "Settings" screen in the application.
 *
 * This screen displays two primary configuration sections: General Settings and UI Settings,
 * each rendered as a card with interactive controls. It uses the [SettingsViewModel] to handle
 * user interactions and updates application-wide preferences.
 *
 * @param viewModel The [SettingsViewModel] instance managing the screen's state and logic.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@Composable
fun SettingsScreenView(
    viewModel: SettingsViewModel,
    modifier: Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
) {
    viewModel.i("Load settings screen")
    Header(
        SharedResourcesjvmMain.strings.screenSettingsName,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
    ) {
        GeneralSettings(
            viewModel,
            Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight()
        )
        UISettings(
            viewModel,
            Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight()
        )
    }
}

@Composable
private fun GeneralSettings(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) = CardWidget<ColumnScope>(
    modifier = modifier,
    cardShape = MaterialTheme.shapes.medium,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
    alignment = Alignment.CenterHorizontally,
) {
    val locale by AppConfig.General.locale.collectAsState()
    Text(
        locale.localize(SharedResourcesjvmMain.strings.lbl_settings_general),
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    LazyColumn(
        modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
        contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
    ) {
        locale(viewModel, locale)
    }
}

@Composable
private fun UISettings(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) = CardWidget<ColumnScope>(
    modifier = modifier,
    cardShape = MaterialTheme.shapes.medium,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
    alignment = Alignment.CenterHorizontally,
) {
    val locale by AppConfig.General.locale.collectAsState()
    Text(
        locale.localize(SharedResourcesjvmMain.strings.lbl_settings_ui),
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    LazyColumn(
        modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
        contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
    ) {
        theme(viewModel, locale)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
private fun LazyListScope.locale(
    viewModel: SettingsViewModel,
    locale: AppLocale,
) = item(AppConfig.General.locale) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            locale.localize(SharedResourcesjvmMain.strings.lbl_settings_general_locale),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(AppDefaults.Weights.ONE),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ChooseBoxForm(
            expanded,
            onExpandedChange = { expanded = !expanded },
            onDismissRequest = { expanded = false },
            currentValue = locale,
            entries = AppLocale.entries.asIterable(),
            onChooseElement = viewModel::onChooseAppLocale,
            converter = { mode -> mode.name },
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
private fun LazyListScope.theme(
    viewModel: SettingsViewModel,
    locale: AppLocale,
) = item(AppConfig.General.locale) {
    var expanded by remember { mutableStateOf(false) }
    val themeMode by AppConfig.UI.themeMode.collectAsState()
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            locale.localize(SharedResourcesjvmMain.strings.lbl_settings_ui_theme),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(AppDefaults.Weights.ONE),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ChooseBoxForm(
            expanded,
            onExpandedChange = { expanded = !expanded },
            onDismissRequest = { expanded = false },
            currentValue = themeMode,
            entries = ThemeMode.entries.asIterable(),
            onChooseElement = viewModel::onChooseThemeMode,
            converter = { mode -> mode.name },
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
        )
    }
}
