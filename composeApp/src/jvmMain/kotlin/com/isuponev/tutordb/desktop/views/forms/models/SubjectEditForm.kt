package com.isuponev.tutordb.desktop.views.forms.models

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.SubjectEditDialogViewModel
import com.isuponev.tutordb.desktop.views.forms.DialogButtons
import com.isuponev.tutordb.desktop.views.forms.TextEditForm


@Composable
fun <S: Screen> SubjectEditForm(
    viewModel: SubjectEditDialogViewModel<S>
) = Column(
    modifier = Modifier.fillMaxSize(AppDefaults.Fraction.TWO_THIRD),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val nameEditErrorMessage by viewModel.nameEditErrorMessage.collectAsState()
    TextEditForm(
        name,
        viewModel::onNameChanged,
        nameEditErrorMessage,
        SharedResourcesjvmMain.strings.lbl_subject_name,
        Modifier.fillMaxWidth()
    )
    TextEditForm(
        description,
        viewModel::onDescriptionChanged,
        null,
        SharedResourcesjvmMain.strings.lbl_subject_description,
        Modifier.fillMaxWidth().weight(AppDefaults.Weights.ONE),
        false
    )
    DialogButtons(
        SharedResources.strings.lbl_save,
        SharedResources.strings.lbl_cancel,
        viewModel::onClickAccept,
        viewModel::onClickCancel
    )
}
