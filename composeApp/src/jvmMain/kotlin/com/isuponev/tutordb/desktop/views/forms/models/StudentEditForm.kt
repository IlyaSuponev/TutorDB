package com.isuponev.tutordb.desktop.views.forms.models

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.student.StudentEditDialogViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.SubjectsViewModel
import com.isuponev.tutordb.desktop.views.forms.DialogButtons
import com.isuponev.tutordb.desktop.views.forms.MonetaryEditForm
import com.isuponev.tutordb.desktop.views.forms.TextEditForm

@Composable
fun <S: Screen> StudentEditForm(
    viewModel: StudentEditDialogViewModel<S>
) = Column(
    modifier = Modifier.fillMaxHeight(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val name by viewModel.name.collectAsState()
    val errorMessageOfName by viewModel.nameErrorMessage.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val errorMessageOfAmount by viewModel.amountErrorMessage.collectAsState()
    val currency by viewModel.currency.collectAsState()
    val availableSubjects by viewModel.availableSubjects.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    TextEditForm(
        name,
        viewModel::onChangeName,
        errorMessageOfName,
        SharedResourcesjvmMain.strings.lbl_student_name,
        Modifier.fillMaxWidth().weight(AppDefaults.Weights.ONE)
    )
    MonetaryEditForm(
        amount,
        viewModel::onChangeAmount,
        errorMessageOfAmount,
        currency,
        viewModel::onChangeCurrency,
        Modifier.fillMaxWidth().weight(AppDefaults.Weights.ONE)
    )
    SubjectsEditForm(
        availableSubjects,
        subjects,
        viewModel::onAddSubject,
        viewModel::onRemoveSubject,
        Modifier.fillMaxWidth().weight(AppDefaults.Weights.THREE)
    )
    DialogButtons(
        SharedResources.strings.lbl_save,
        SharedResources.strings.lbl_cancel,
        viewModel::onClickAccept,
        viewModel::onClickCancel,
        Modifier.fillMaxWidth().weight(AppDefaults.Weights.ONE)
    )
}

@Composable
private fun SubjectsEditForm(
    availableSubjects: List<Subject>,
    subjects: List<Subject>,
    onAddSubject: (Subject) -> Unit,
    onRemoveSubject: (Subject) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
) {
    LazyColumn(
        modifier = Modifier.weight(AppDefaults.Weights.ONE)
    ) {
        items(subjects) {
            SubjectCard(
                it,
                onRemoveSubject,
                Icons.Default.Remove
            )
        }
    }
    LazyColumn(
        modifier = Modifier.weight(AppDefaults.Weights.ONE)
    ) {
        items(availableSubjects.filter { subject -> !subjects.contains(subject) }) {
            SubjectCard(
                it,
                onAddSubject,
                Icons.Default.Add
            )
        }
    }
}

@Composable
private fun SubjectCard(
    subject: Subject,
    action: (Subject) -> Unit,
    actionImage: ImageVector
) = CardWidget<ColumnScope>(
    cardShape = MaterialTheme.shapes.small,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    scaleOnHover = AppDefaults.Scales.SMALL,
    alignment = Alignment.CenterHorizontally,
    contentPadding = PaddingValues(AppDefaults.Paddings.SMALL)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = subject.name.value,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.titleLarge,
            softWrap = true
        )
        Spacer(Modifier.weight(AppDefaults.Weights.ONE))
        IconButton(
            onClick = {
                action(subject)
            }
        ) {
            Icon(
                actionImage,
                contentDescription = "Subeject action",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    Text(
        text = subject.description.ifEmpty {
            "Empty description"
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(AppDefaults.Paddings.SMALL)
            .defaultMinSize(minHeight = SubjectsViewModel.DESCRIPTION_MIN_HEIGHT)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        style = MaterialTheme.typography.bodyLarge
    )
}