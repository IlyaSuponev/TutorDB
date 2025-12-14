package com.isuponev.tutordb.desktop.views.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.min
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.views.widgets.Detail.StringDetail
import dev.icerock.moko.resources.StringResource

sealed class Detail(
    val label: StringResource?,
    val minSize: DpSize = DpSize(Dp.Unspecified, Dp.Unspecified)
) {
    class StringDetail(
        label: StringResource?,
        minSize: DpSize,
        val value: String
        ): Detail(label, minSize)
    class IterableDetail(
        label: StringResource?,
        val asColumn: Boolean,
        val value: List<String>
    ): Detail(label)

    companion object {
        fun string(
            label: StringResource?,
            minSize: DpSize,
            value: String
        ): StringDetail = StringDetail(label, minSize, value)
        fun iterable(
            label: StringResource?,
            asColumn: Boolean,
            vararg elements: String
        ): IterableDetail = IterableDetail(label, asColumn, elements.toList())
        fun iterable(
            label: StringResource?,
            asColumn: Boolean,
            elements: Iterable<String>
        ): IterableDetail = IterableDetail(label, asColumn, elements.toList())
    }
}


@Composable
fun <E: Model> EntityCard(
    entity: E,
    headerMessage: String,
    details: Iterable<Detail>,
    onEditClick: ((E) -> Unit)? = null,
    onRemoveClick: ((E) -> Unit)? = null,
    onClick: ((E) -> Unit)? = null
) = CardWidget<ColumnScope>(
    cardShape = MaterialTheme.shapes.small,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    onClick = { if (onClick != null) onClick(entity) },
    scaleOnHover = AppDefaults.Scales.SMALL,
    alignment = Alignment.CenterHorizontally,
    contentPadding = PaddingValues(AppDefaults.Paddings.SMALL)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = headerMessage,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.titleLarge,
            softWrap = true
        )
        Spacer(Modifier.weight(AppDefaults.Weights.ONE))
        IconButton(
            onClick = {
                if (onEditClick != null) onEditClick(entity)
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
                if (onRemoveClick != null) onRemoveClick(entity)
            }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    details.forEach {
        DetailView(it, Modifier)
    }
}

@Composable
private fun DetailView(detail: Detail, modifier: Modifier = Modifier) = Column(
    modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.tertiaryContainer)
        .padding(AppDefaults.Paddings.SMALL)
        .defaultMinSize(detail.minSize.width, detail.minSize.height),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL)
) {
    val locale by AppConfig.General.locale.collectAsState()
    if (detail.label != null) {
        Text(
            text = locale.localize(detail.label),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
    when(detail) {
        is StringDetail -> StringDetailView(detail)
        is Detail.IterableDetail -> IterableDetailView(detail)
    }
}

@Composable
private fun IterableDetailView(detail: Detail.IterableDetail) {
    if (detail.asColumn) {
        detail.value.forEach { item ->
            Text(
                text = item,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Row {
            detail.value.forEach { item ->
                Text(
                    text = item,
                    modifier = Modifier.weight(AppDefaults.Weights.ONE),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun StringDetailView(detail: Detail.StringDetail) = Text(
    text = detail.value,
    color = MaterialTheme.colorScheme.onTertiaryContainer,
    style = MaterialTheme.typography.bodyLarge,
    textAlign = TextAlign.Center
)