package com.isuponev.tutordb.desktop.views.screens.subject

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
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.EditSubjectViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.forms.SubjectEditForm

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
) = SubjectEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.screenEditSubjectName
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        EditSubjectViewModel.State.Loaded -> SubjectEditForm(viewModel)
        EditSubjectViewModel.State.Loading -> ContentOnLoadingVM(viewModel)
    }
}
