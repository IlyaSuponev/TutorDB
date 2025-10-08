package com.isuponev.tutordb.core.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.theme.AppTheme

/**
 * Error screen displayed when no valid screens are available.
 *
 * This fallback screen is shown when the [appScreens] function returns an empty list,
 * indicating that no valid application screens could be loaded or initialized.
 */
private object AppError : Screen("Error") {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        Text(text = "Something went wrong")
    }

}

/**
 * Platform-specific implementation for generating the main application container.
 *
 * This expects function should be implemented on each target platform to create
 * the appropriate navigation container and set up the navigation graph with
 * the provided screens.
 *
 * @param navController The navigation controller that manages screen transitions
 * @param screens The list of available screens in the application
 * @param startDestination The initial screen to display when the app launches
 * @param modifier The modifier to apply to the main container layout
 *
 * @see AppMainContainer for the common implementation that uses this function
 */
@Composable
internal expect fun AppMainContainerGeneration(
    navController: NavHostController,
    screens: List<Screen>,
    startDestination: Screen,
    modifier: Modifier = Modifier,
)

/**
 * Retrieves the platform-specific list of application screens.
 *
 * This expects function should be implemented on each target platform to provide
 * the appropriate screens for that platform. The screens define the navigation
 * structure and available routes in the application.
 *
 * @return A list of [Screen] objects representing the available screens in the app
 *
 * @throws Exception if screen initialization fails on the specific platform
 */
internal expect fun appScreens(): List<Screen>

@Composable
private fun AppMainContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val screens = remember { appScreens().ifEmpty { listOf<Screen>(AppError) } }
    AppMainContainerGeneration(
        navController = navController,
        screens = screens,
        startDestination = screens.first(),
        modifier = modifier,
    )
}

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
