package com.isuponev.tutordb.desktop.views.screens.student

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.student.AddStudentViewModel
import com.isuponev.tutordb.desktop.views.forms.models.StudentEditForm

@Composable
fun AddStudentScreenView(
    viewModel: AddStudentViewModel,
    modifier: Modifier = Modifier
) = StudentEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.lbl_settings_ui,
    modifier
) { StudentEditForm(viewModel) }
