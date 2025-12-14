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
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load edit student screen")
    Header(
        SharedResourcesjvmMain.strings.screenEditStudentName,
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
        val state by viewModel.state.collectAsState()
        when (state) {
            EditStudentViewModel.State.Loaded -> EditContent(viewModel)
            EditStudentViewModel.State.Loading -> ContentOnLoadingVM(viewModel)
        }
    }
}

@Composable
fun EditContent(viewModel: EditStudentViewModel) {
    val name by viewModel.name.collectAsState()
    val errorMessageOfName by viewModel.nameErrorMessage.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val errorMessageOfAmount by viewModel.amountErrorMessage.collectAsState()
    val currency by viewModel.currency.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val availableSubjects by viewModel.availableSubjects.collectAsState()
    StudentEditForm(
        name,
        viewModel::onChangeName,
        errorMessageOfName,
        amount,
        viewModel::onChangeAmount,
        errorMessageOfAmount,
        currency,
        viewModel::onChangeCurrency,
        availableSubjects.filter { subject -> subject !in subjects }.toList(),
        subjects,
        viewModel::onAddSubject,
        viewModel::onRemoveSubject,
        viewModel::onClickAccept,
        viewModel::onClickCancel
    )
}

@Composable
private fun ContentOnLoadingVM(viewModel: EditStudentViewModel) {
    val progress by viewModel.loadingProgress.collectAsState()
    CircularProgressIndicator(
        modifier = Modifier,
        progress = { progress }
    )
}
