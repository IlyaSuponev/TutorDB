package com.isuponev.tutordb.core.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.theme.AppTheme

@Composable
internal expect fun AppMainContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
)

/**
 * Root composable function that initializes the entire application.
 *
 * This function serves as the entry point for the Compose Multiplatform application.
 * It sets up the application theme and initializes the navigation system with
 * platform-specific screens and navigation structure.
 *
 * ## Application Initialization Flow:
 * 1. Applies the application theme via [AppTheme]
 * 2. Creates and remembers the navigation controller
 * 3. Initializes the main application container with navigation
 * 4. Sets up the screen structure and navigation graph
 *
 * @see AppTheme for theming configuration
 * @see AppMainContainer for navigation and screen setup
 * @see rememberNavController for navigation state management
 */
@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        AppMainContainer(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
