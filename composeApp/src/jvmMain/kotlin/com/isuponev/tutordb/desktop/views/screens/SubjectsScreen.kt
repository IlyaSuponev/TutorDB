package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.SubjectsViewModel
import com.isuponev.tutordb.desktop.views.Header

@Composable
private fun SubjectCard(
    subject: Subject,
    onEditClick: (Subject) -> Unit = { AppConfig.logger.i { "Edit subject" } },
    onRemoveClick: (Subject) -> Unit = { AppConfig.logger.i { "Remove subject" } },
) {
    CardWidget<ColumnScope>(
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
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.weight(AppDefaults.Weights.ONE))
            IconButton(
                onClick = {
                    onEditClick(subject)
                }
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(
                onClick = {
                    onRemoveClick(subject)
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Text(
            text = subject.description.ifEmpty {
                "Empty description"
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiaryContainer,)
                .padding(AppDefaults.Paddings.SMALL)
                .defaultMinSize(minHeight = SubjectsViewModel.DESCRIPTION_MIN_WIDTH)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SubjectsScreenView(
    viewModel: SubjectsViewModel,
    modifier: Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load subjects screen")
    Header(
        SharedResourcesjvmMain.strings.screenSubjectsName,
        tools = viewModel.tools,
        modifier = Modifier.fillMaxWidth()
    )
    val subjects by viewModel.subjects.collectAsState()
    val columnsCount by viewModel.columnsCount.collectAsState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columnsCount),
        modifier = Modifier.weight(AppDefaults.Weights.ONE),
        verticalItemSpacing = AppDefaults.Arrangements.BIG,
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
    ) {
        items(subjects) { subject ->
            SubjectCard(
                subject,
                onEditClick = viewModel::editSubject,
                onRemoveClick = viewModel::removeSubject
            )
        }
    }
}
