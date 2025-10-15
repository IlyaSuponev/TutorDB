package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.widgets.CardWidget

/**
 * Screen object representing the application Settings and configuration interface.
 *
 * This screen provides users with access to application preferences, including
 * theme selection, language settings, and other configuration options. It serves
 * as the central hub for customizing the application experience.
 *
 * Route: Localized string from [SharedResources.strings.routeOfSettingsScreen]
 * Default: English localization used as fallback during initialization
 *
 * @see Screen
 * @see AppConfig
 * @see AppLocale
 */
object SettingsScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfSettingsScreen),
) {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) = Column(
        modifier = modifier
            .padding(AppDefaults.Paddings.BIG)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = SettingsScreen::class.java.simpleName) { "Load settings screen" }
        Header(Modifier.fillMaxWidth())
        Row(
            modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
        ) {
            GeneralSettings(Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight())
            UISettings(Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight())
        }
    }

    @Composable
    private fun GeneralSettings(
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
            locale.localize(SharedResources.strings.lbl_settings_general),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        LazyColumn(
            modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
            contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
        ) {
            Locale(locale)
        }
    }

    @Composable
    private fun UISettings(
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
            locale.localize(SharedResources.strings.lbl_settings_ui),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        LazyColumn(
            modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
            contentPadding = PaddingValues(AppDefaults.Paddings.MEDIUM),
        ) {
            Theme(locale)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    private fun LazyListScope.Locale(
        locale: AppLocale,
    ) = item(AppConfig.General.locale) {
        var expanded by remember { mutableStateOf(false) }
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                locale.localize(SharedResources.strings.lbl_settings_general_locale),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(AppDefaults.Weights.ONE),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .weight(AppDefaults.Weights.ONE)
            ) {
                OutlinedTextField(
                    value = locale.type.displayLanguage,
                    singleLine = true,
                    onValueChange = {  },
                    enabled = true,
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    AppLocale.entries
                        .asSequence()
                        .filter { it != locale }
                        .forEach { entry ->
                        DropdownMenuItem(
                            onClick = {
                                AppConfig.General.setLocale(entry)
                                expanded = false
                            }
                        ) {
                            Text(
                                entry.type.displayLanguage,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    private fun LazyListScope.Theme(
        locale: AppLocale,
    ) = item(AppConfig.General.locale) {
        var expanded by remember { mutableStateOf(false) }
        val themeMode by AppConfig.UI.themeMode.collectAsState()
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                locale.localize(SharedResources.strings.lbl_settings_ui_theme),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(AppDefaults.Weights.ONE),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .weight(AppDefaults.Weights.ONE)
            ) {
                OutlinedTextField(
                    value = themeMode.name,
                    singleLine = true,
                    onValueChange = {  },
                    enabled = true,
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    ThemeMode.entries
                        .asSequence()
                        .filter { it != themeMode }
                        .forEach { entry ->
                        DropdownMenuItem(
                            onClick = {
                                AppConfig.UI.setThemeMode(entry)
                                expanded = false
                            }
                        ) {
                            Text(
                                entry.name,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Header(
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
            locale.localize(SharedResources.strings.screenSettings),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
