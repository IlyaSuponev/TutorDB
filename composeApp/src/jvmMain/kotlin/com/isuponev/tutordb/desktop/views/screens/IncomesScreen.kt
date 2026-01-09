package com.isuponev.tutordb.desktop.views.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.desktop.viewmodels.screens.IncomesViewModel

/**
 * A composable UI component for the "Incomes" screen in the application.
 *
 * This is currently a placeholder/stub implementation that displays a localized screen title.
 * The actual implementation would eventually handle income-related data operations,
 * UI state management, and user interactions through the [IncomesViewModel].
 *
 * @param viewModel The [IncomesViewModel] instance managing the screen's state and logic.
 *                  Currently serves as a base for future implementation.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomesScreenView(
    viewModel: IncomesViewModel,
    modifier: Modifier
) {
    val locale by AppConfig.General.locale.collectAsState()
    viewModel.i("Load incomes screen")
}
