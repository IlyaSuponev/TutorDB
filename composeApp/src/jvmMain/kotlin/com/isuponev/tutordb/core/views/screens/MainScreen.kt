package com.isuponev.tutordb.core.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.widgets.CardWidget

object MainScreen : Screen("/") {
    private typealias CardContent = Triple<() -> Unit, ImageVector, String>

    private const val MAIN_MENU_CARD_SCALE_ON_HOVER = 1.05f
    private const val MAIN_MENU_CARD_WEIGHT = 1f
    private const val MAIN_MENU_CARD_ROW_WEIGHT = 1f
    private val MAIN_MENU_CARD_ARRANGEMENT_SPACE = 24.dp
    private val MAIN_MENU_CONTENT_PADDING = 16.dp

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
        scaleOnHover = MAIN_MENU_CARD_SCALE_ON_HOVER,
        alignment = Alignment.CenterHorizontally,
    ) {
        Image(
            content.second,
            contentDescription = content.third,
            modifier = Modifier.size(128.dp).weight(1f),
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
        horizontalArrangement = Arrangement.spacedBy(MAIN_MENU_CARD_ARRANGEMENT_SPACE),
    ) {
        contentElements.forEach { content ->
            MainScreenMenuCard(
                modifier = Modifier
                    .weight(MAIN_MENU_CARD_WEIGHT)
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
            .padding(MAIN_MENU_CONTENT_PADDING)
            .fillMaxSize(),
        Arrangement.spacedBy(MAIN_MENU_CARD_ARRANGEMENT_SPACE)
    ) {
        MainScreenMenuCardRow(
            modifier = Modifier.weight(MAIN_MENU_CARD_ROW_WEIGHT).fillMaxWidth(),
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
            modifier = Modifier.weight(MAIN_MENU_CARD_ROW_WEIGHT).fillMaxWidth(),
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
