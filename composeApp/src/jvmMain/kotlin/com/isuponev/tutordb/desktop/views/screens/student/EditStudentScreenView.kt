package com.isuponev.tutordb.desktop.views.screens.student

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
import com.isuponev.tutordb.desktop.viewmodels.screens.student.EditStudentViewModel
import com.isuponev.tutordb.desktop.views.Header
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
        EditStudentViewModel.State.Loaded -> StudentEditForm(viewModel)
        EditStudentViewModel.State.Loading -> ContentOnLoadingVM(viewModel)
    }
}

@Composable
private fun ContentOnLoadingVM(viewModel: EditStudentViewModel) {
    val progress by viewModel.loadingProgress.collectAsState()
    CircularProgressIndicator(
        modifier = Modifier,
        progress = { progress }
    )
}
