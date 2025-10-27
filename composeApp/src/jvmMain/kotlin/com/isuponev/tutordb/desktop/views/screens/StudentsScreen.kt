package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.StudentsViewModel

@Composable
fun StudentsScreenView(
    viewModel: StudentsViewModel,
    modifier: Modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    viewModel.i("Load students screen")
    Text(locale.localize(SharedResourcesjvmMain.strings.screenStudentsName))
}
