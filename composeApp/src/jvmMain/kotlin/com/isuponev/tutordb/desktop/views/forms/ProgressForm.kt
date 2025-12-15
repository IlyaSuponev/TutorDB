package com.isuponev.tutordb.desktop.views.forms

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.desktop.viewmodels.screens.abs.Loadable

@Composable
fun LoadingForm(loadable: Loadable) {
    val progress by loadable.loadingProgress.collectAsState()
    CircularProgressIndicator(
        modifier = Modifier,
        progress = { progress.percent }
    )
}