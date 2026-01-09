package com.isuponev.tutordb.desktop.views.screens.subject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.Loadable
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.EditSubjectViewModel
import com.isuponev.tutordb.desktop.views.forms.LoadingForm
import com.isuponev.tutordb.desktop.views.forms.models.SubjectEditForm

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
        Loadable.State.Loaded -> SubjectEditForm(viewModel)
        Loadable.State.Loading -> LoadingForm(viewModel)
    }
}
