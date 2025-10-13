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
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.screens.navigate
import com.isuponev.tutordb.core.views.widgets.CardWidget

/**
 * The main screen of the application displaying the primary navigation menu.
 *
 * This screen presents a grid-like layout of interactive cards that serve as
 * the main navigation menu for accessing different features of the application.
 * Each card represents a major functional area and provides visual feedback
 * through hover effects and consistent styling.
 *
 * #### Screen Layout:
 * - Two rows of menu cards
 * - Each row contains two cards arranged horizontally
 * - Cards feature icons and labels for clear identification
 * - Responsive layout that adapts to different screen sizes
 *
 * #### Menu Categories:
 * - Students management
 * - Subjects management
 * - Lessons scheduling
 * - Income tracking
 *
 * @see com.isuponev.tutordb.core.views.screens.Screen for the base screen class implementation
 */
object MainScreen : Screen(
    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfMainScreen)
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
        val locale by AppConfig.General.locale.collectAsState()
        appLogger.i(tag = MainScreen::class.java.simpleName) { "Load main screen" }
        MainScreenMenuCardRow(
            modifier = Modifier
                .weight(AppDefaults.Weights.ONE)
                .fillMaxWidth(),
            contentElements = listOf(
                CardContent(
                    {
                        navHostController.navigate(target = StudentsScreen)
                    },
                    Icons.Default.Person,
                    locale.localize(SharedResources.strings.screenStudents)
                ),
                CardContent(
                    {
                        navHostController.navigate(target = SubjectsScreen)
                    },
                    Icons.Default.Bookmarks,
                    locale.localize(SharedResources.strings.screenSubjects)
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
                        navHostController.navigate(target = LessonsScreen)
                    },
                    Icons.Default.PlayLesson,
                    locale.localize(SharedResources.strings.screenLessons)
                ),
                CardContent(
                    {
                        navHostController.navigate(target = IncomesScreen)
                    },
                    Icons.Default.Money,
                    locale.localize(SharedResources.strings.screenIncomes)
                )
            )
        )
    }
}
