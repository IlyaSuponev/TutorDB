package com.isuponev.tutordb.desktop.views.screens.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.AddSubjectViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.forms.SubjectEditForm

/**
 * A composable UI component for the "Add Subject" screen in the application.
 *
 * This screen provides a form for creating a new subject with name and description fields,
 * along with validation and action buttons. It uses the [AddSubjectViewModel] to manage
 * state and business logic, and leverages Material 3 components for styling.
 *
 * @param viewModel The [AddSubjectViewModel] instance managing the screen's state and interactions.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@Composable
fun AddSubjectScreenView(
    viewModel: AddSubjectViewModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load add subject screen")
    Header(
        SharedResourcesjvmMain.strings.screenAddSubjectName,
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
        val name by viewModel.name.collectAsState()
        val description by viewModel.description.collectAsState()
        val nameError by viewModel.nameError.collectAsState()
        SubjectEditForm(
            name,
            description,
            viewModel::onNameChanged,
            viewModel::onDescriptionChanged,
            nameError,
            viewModel::onClickAccept,
            viewModel::onClickCancel
        )
    }
}
