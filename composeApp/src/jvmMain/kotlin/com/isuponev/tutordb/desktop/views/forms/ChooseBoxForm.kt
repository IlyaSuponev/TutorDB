package com.isuponev.tutordb.desktop.views.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import dev.icerock.moko.resources.StringResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> ChooseBoxForm(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    currentValue: T,
    entries: Iterable<T>,
    onChooseElement: (T) -> Unit,
    converter: (T) -> String,
    modifier: Modifier = Modifier,
    labelMessage: StringResource? = null
) = ExposedDropdownMenuBox(
    expanded = expanded,
    onExpandedChange = onExpandedChange,
    modifier = modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    OutlinedTextField(
        value = converter(currentValue),
        singleLine = true,
        onValueChange = { },
        enabled = true,
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        },
        label = labelMessage?.let {
            @Composable {
                Text(
                    locale.localize(labelMessage)
                )
            }
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
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        entries
            .asSequence()
            .filter { it != currentValue }
            .forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        onChooseElement(entry)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        converter(entry),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
    }
}