package com.isuponev.tutordb.core.views.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.CardWidget

object MainScreen : Screen(
    SharedResources.strings.routeOfMainMenu.localized()
) {
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
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) = Column(
        modifier
            .padding(AppDefaults.Paddings.BIG)
            .fillMaxSize(),
        Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
    ) {
        MainScreenMenuCardRow(
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
                .fillMaxWidth(),
            contentElements = listOf(
                CardContent(
                    {
                        println(SharedResources.strings.menuStudents.localized())
                    },
                    Icons.Default.Person,
                    SharedResources.strings.menuStudents.localized()
                ),
                CardContent(
                    {
                        println(SharedResources.strings.menuSubjects.localized())
                    },
                    Icons.Default.Bookmarks,
                    SharedResources.strings.menuSubjects.localized()
                )
            )
        )
        MainScreenMenuCardRow(
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
                .fillMaxWidth(),
            contentElements = listOf(
                CardContent(
                    {
                        println(SharedResources.strings.menuLessons.localized())
                    },
                    Icons.Default.PlayLesson,
                    SharedResources.strings.menuLessons.localized()
                ),
                CardContent(
                    {
                        println(SharedResources.strings.menuIncomes.localized())
                    },
                    Icons.Default.Money,
                    SharedResources.strings.menuIncomes.localized()
                )
            )
        )
    }
}
