package com.isuponev.tutordb.desktop.views.screens.student

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.Loadable
import com.isuponev.tutordb.desktop.viewmodels.screens.student.EditStudentViewModel
import com.isuponev.tutordb.desktop.views.forms.LoadingForm
import com.isuponev.tutordb.desktop.views.forms.StudentEditForm

@Composable
fun EditStudentScreenView(
    viewModel: EditStudentViewModel,
    modifier: Modifier = Modifier
) = StudentEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.screenEditStudentName,
    modifier
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        Loadable.State.Loaded -> StudentEditForm(viewModel)
        Loadable.State.Loading -> LoadingForm(viewModel)
    }
}
