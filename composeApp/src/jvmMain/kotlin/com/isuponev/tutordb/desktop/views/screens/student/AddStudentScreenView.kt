package com.isuponev.tutordb.desktop.views.screens.student

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.student.AddStudentViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.forms.StudentEditForm

@Composable
fun AddStudentScreenView(
    viewModel: AddStudentViewModel,
    modifier: Modifier = Modifier
) = StudentEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.screenAddStudent,
    modifier
) { StudentEditForm(viewModel) }
