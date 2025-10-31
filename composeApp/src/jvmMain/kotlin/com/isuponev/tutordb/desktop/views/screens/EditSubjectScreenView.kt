package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.EditSubjectViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.forms.SubjectEditForm

@Composable
private fun ContentOnLoadedVM(
    viewModel: EditSubjectViewModel
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    SubjectEditForm(
        name,
        description,
        viewModel::onNameChanged,
        viewModel::onDescriptionChanged,
        nameError,
        viewModel::onClickSave,
        viewModel::onClickCancel
    )
}

@Composable
private fun ContentOnLoadingVM(viewModel: EditSubjectViewModel) {
    val progress by viewModel.loadingProgress.collectAsState()
    CircularProgressIndicator(
        modifier = Modifier,
        progress = { progress }
    )
}

/**
 * A composable UI component for the "Edit Subject" screen in the application.
 *
 * This screen displays a form for modifying an existing subject's name and description.
 * It observes the [EditSubjectViewModel] state to show either the form (when data is loaded)
 * or a placeholder (when data is still loading). Uses Material 3 components for styling
 * and localization for dynamic text resources.
 *
 * @param viewModel The [EditSubjectViewModel] managing the screen's state and interactions.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
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
        modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
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
            EditSubjectViewModel.State.Loading -> ContentOnLoadingVM(viewModel)
        }
    }
}
