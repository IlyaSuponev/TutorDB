package com.isuponev.tutordb.desktop.views.screens.subject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.AddSubjectViewModel
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
) = SubjectEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.screenAddSubjectName,
) { SubjectEditForm(viewModel) }