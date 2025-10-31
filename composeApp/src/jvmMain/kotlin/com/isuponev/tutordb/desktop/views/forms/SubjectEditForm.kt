package com.isuponev.tutordb.desktop.views.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults

/**
 * A composable UI component for editing subject details.
 *
 * This form provides input fields for subject name and description, along with
 * validation error display and action buttons for saving/canceling changes.
 * Layout uses Material 3 components with localized labels and responsive spacing.
 *
 * @param name Current subject name value for the text field
 * @param description Current subject description value for the text field
 * @param onNameChanged Callback for name field value changes
 * @param onDescriptionChanged Callback for description field value changes
 * @param nameEditError Optional error message for name validation
 * @param onClickSave Callback triggered when the Save button is clicked
 * @param onClickCancel Callback triggered when the Cancel button is clicked
 */
@Composable
fun SubjectEditForm(
    name: String,
    description: String,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    nameEditError: String?,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit
) = Column(
    modifier = Modifier.fillMaxSize(AppDefaults.Fraction.TWO_THIRD),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
) {
    val locale by AppConfig.General.locale.collectAsState()
    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                locale.localize(SharedResourcesjvmMain.strings.lbl_subject_name)
            )
        },
        isError = nameEditError != null
    )
    if (nameEditError != null) {
        Text(
            nameEditError,
            color = MaterialTheme.colorScheme.error
        )
    }
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChanged,
        modifier = Modifier.fillMaxWidth().fillMaxHeight(AppDefaults.Fraction.TWO_THIRD),
        label = {
            Text(
                locale.localize(SharedResourcesjvmMain.strings.lbl_subject_description)
            )
        }
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(AppDefaults.Weights.ONE))
        Button(
            onClick = onClickCancel
        ) {
            Text(
                locale.localize(SharedResources.strings.lbl_cancel)
            )
        }
        Button(
            onClick = onClickSave
        ) {
            Text(
                locale.localize(SharedResources.strings.lbl_save)
            )
        }
    }
}
