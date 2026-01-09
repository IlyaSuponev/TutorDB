package com.isuponev.tutordb.desktop.views.screens.lessons

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.lessons.LessonEditDialogViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.student.StudentEditDialogViewModel
import com.isuponev.tutordb.desktop.views.Header
import dev.icerock.moko.resources.StringResource

@Composable
fun <S: Screen> LessonEditDialogView(
    viewModel: LessonEditDialogViewModel<S>,
    header: StringResource,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load screen")
    Header(
        header,
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
    ) { content() }
}