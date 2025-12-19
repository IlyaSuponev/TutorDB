package com.isuponev.tutordb.desktop.views.forms.models

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.viewmodels.screens.lessons.LessonEditDialogViewModel
import com.isuponev.tutordb.desktop.views.forms.ChooseBoxForm
import com.isuponev.tutordb.desktop.views.forms.DialogButtons
import com.isuponev.tutordb.desktop.views.forms.MonetaryEditForm
import com.isuponev.tutordb.desktop.views.forms.TextEditForm

@Composable
fun <S: Screen> LessonEditForm(
    viewModel: LessonEditDialogViewModel<S>
) = LazyColumn(
    modifier = Modifier.fillMaxHeight(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    item {
        val name by viewModel.name.collectAsState()
        val errorMessageOfName by viewModel.nameErrorMessage.collectAsState()
        TextEditForm(
            name,
            viewModel::onChangeName,
            errorMessageOfName,
            SharedResourcesjvmMain.strings.lbl_lesson_name,
            Modifier.fillMaxWidth()
        )
    }
    item {
        val amount by viewModel.hourCostAmount.collectAsState()
        val errorMessageOfAmount by viewModel.hourCostAmountErrorMessage.collectAsState()
        val currency by viewModel.hourCostCurrency.collectAsState()
        MonetaryEditForm(
            amount,
            viewModel::onChangeHourCostAmount,
            errorMessageOfAmount,
            currency,
            viewModel::onChangeHourCostCurrency,
            Modifier.fillMaxWidth()
        )
    }
    // TODO: add date and time picker
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM)
        ) {
            val student by viewModel.student.collectAsState()
            val allStudents by viewModel.availableStudents.collectAsState()
            ChooseBoxForm(
                initExpandedState = false,
                student,
                allStudents,
                viewModel::onSelectStudent,
                { it?.name?.value ?: "null" },
                labelMessage = SharedResourcesjvmMain.strings.lbl_lesson_student,
                modifier = Modifier.weight(AppDefaults.Weights.ONE)
            )
            val subject by viewModel.subject.collectAsState()
            val allSubjects by viewModel.availableSubjects.collectAsState()
            ChooseBoxForm(
                initExpandedState = false,
                subject,
                allSubjects,
                viewModel::onSelectSubject,
                { it?.name?.value ?: "null" },
                labelMessage = SharedResourcesjvmMain.strings.lbl_lesson_subject,
                modifier = Modifier.weight(AppDefaults.Weights.ONE)
            )
        }
    }
    item {
        val description by viewModel.description.collectAsState()
        TextEditForm(
            description,
            viewModel::onChangeDescription,
            null,
            SharedResourcesjvmMain.strings.lbl_lesson_description,
            Modifier.fillMaxWidth(),
            false
        )
    }
    item {
        DialogButtons(
            SharedResources.strings.lbl_save,
            SharedResources.strings.lbl_cancel,
            viewModel::onClickAccept,
            viewModel::onClickCancel,
            Modifier.fillMaxWidth()
        )
    }
}