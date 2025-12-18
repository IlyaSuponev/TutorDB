package com.isuponev.tutordb.desktop.views.screens.lessons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.lessons.AddLessonViewModel
import com.isuponev.tutordb.desktop.views.forms.models.LessonEditForm

@Composable
fun AddLessonScreenView(
    viewModel: AddLessonViewModel,
    modifier: Modifier = Modifier
) = LessonEditDialogView(
    viewModel,
    SharedResourcesjvmMain.strings.screenEditStudentName,
    modifier
) { LessonEditForm(viewModel) }