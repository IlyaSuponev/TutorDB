package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayLesson
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.desktop.viewmodels.screens.HomeViewModel

private typealias CardContent = Triple<() -> Unit, ImageVector, String>

@Composable
private fun MainScreenMenuCard(
    content: CardContent,
    modifier: Modifier = Modifier,
) = CardWidget<ColumnScope>(
    onClick = content.first,
    modifier = modifier,
    cardShape = MaterialTheme.shapes.medium,
    cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    scaleOnHover = AppDefaults.Scales.SMALL,
    alignment = Alignment.CenterHorizontally,
    contentPadding = PaddingValues(AppDefaults.Paddings.BIG)
) {
    Image(
        content.second,
        contentDescription = content.third,
        modifier = Modifier
            .weight(AppDefaults.Weights.ONE)
            .fillMaxSize(),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
    )
    Text(
        content.third,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displaySmall,
    )
}

@Composable
private fun MainScreenMenuCardRow(
    contentElements: List<CardContent>,
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
) {
    contentElements.forEach { content ->
        MainScreenMenuCard(
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
                .fillMaxSize(),
            content = content
        )
    }
}

@Composable
fun HomeView(
    viewModel: HomeViewModel,
    modifier: Modifier
) = Column(
    modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    val locale by AppConfig.General.locale.collectAsState()
    viewModel.i("Load home screen")
    MainScreenMenuCardRow(
        modifier = Modifier
            .weight(AppDefaults.Weights.ONE)
            .fillMaxWidth(),
        contentElements = listOf(
            CardContent(
                viewModel::onStudentsCardClicked,
                Icons.Default.Person,
                locale.localize(SharedResourcesjvmMain.strings.screenStudentsName)
            ),
            CardContent(
                viewModel::onSubjectsCardClicked,
                Icons.Default.Bookmarks,
                locale.localize(SharedResourcesjvmMain.strings.screenSubjectsName)
            )
        )
    )
    MainScreenMenuCardRow(
        modifier = Modifier
            .weight(AppDefaults.Weights.ONE)
            .fillMaxWidth(),
        contentElements = listOf(
            CardContent(
                viewModel::onLessonsCardClicked,
                Icons.Default.PlayLesson,
                locale.localize(SharedResourcesjvmMain.strings.screenLessonsName)
            ),
            CardContent(
                viewModel::onIncomesCardClicked,
                Icons.Default.Money,
                locale.localize(SharedResourcesjvmMain.strings.screenIncomesName)
            )
        )
    )
}
