package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.desktop.viewmodels.screens.LessonsViewModel

@Composable
fun LessonsScreenView(
    viewModel: LessonsViewModel,
    modifier: Modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    viewModel.logInfo("Load lessons screen")
    Text(locale.localize(SharedResourcesjvmMain.strings.screenLessonsName))
}
