package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
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
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.EditSubjectViewModel
import com.isuponev.tutordb.desktop.views.Header

@Composable
private fun ContentOnLoadedVM(
    viewModel: EditSubjectViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(AppDefaults.Fraction.TWO_THIRD),
        verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
    ) {
        val locale by AppConfig.General.locale.collectAsState()
        val name by viewModel.name.collectAsState()
        val nameError by viewModel.nameError.collectAsState()
        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    locale.localize(SharedResourcesjvmMain.strings.lbl_subject_name)
                )
            },
            isError = nameError != null
        )
        val message = nameError
        if (message != null) {
            Text(
                message,
                color = MaterialTheme.colorScheme.error
            )
        }
        val description by viewModel.description.collectAsState()
        OutlinedTextField(
            value = description,
            onValueChange = viewModel::onDescriptionChanged,
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
                onClick = viewModel::onClickCancel
            ) {
                Text(
                    locale.localize(SharedResources.strings.lbl_cancel)
                )
            }
            Button(
                onClick = viewModel::onClickSave
            ) {
                Text(
                    locale.localize(SharedResources.strings.lbl_save)
                )
            }
        }
    }
}

@Composable
fun EditSubjectScreenView(
    viewModel: EditSubjectViewModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load edit subject screen")
    Header(
        SharedResourcesjvmMain.strings.screenEditSubjectName,
        modifier = Modifier.fillMaxWidth()
    )
    CardWidget<BoxScope>(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        cardShape = MaterialTheme.shapes.small,
        cardColors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        alignment = Alignment.Center,
        contentPadding = PaddingValues(AppDefaults.Paddings.SMALL)
    ) {
        val state by viewModel.state.collectAsState()
        when (state) {
            EditSubjectViewModel.State.Loaded -> ContentOnLoadedVM(viewModel)
            EditSubjectViewModel.State.Loading -> {
                Text(
                    ""
                )
            }
        }
    }
}