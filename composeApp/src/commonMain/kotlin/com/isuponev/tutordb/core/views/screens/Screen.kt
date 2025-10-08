package com.isuponev.tutordb.core.views.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.HASH_CODE_NUMBER_GENERATOR
import com.isuponev.tutordb.core.views.ToolMenuElement

/**
 * Abstract base class representing a screen in the application.
 *
 * This class serves as the foundation for all screens in the app, providing common
 * functionality for navigation, toolbar menu elements, and screen composition.
 * Each screen is identified by a unique route and can optionally provide toolbar menu elements.
 *
 * #### Implementation Notes:
 * - Subclasses must implement the [view] function to define the screen's UI
 * - Screens are compared and hashed based on their route for navigation purposes
 * - The route should be unique within the application's navigation graph
 *
 * #### Example Implementation:
 * ```kotlin
 * class HomeScreen : Screen("home") {
 *     @Composable
 *     override fun view(navHostController: NavHostController, modifier: Modifier) {
 *         HomeScreenContent(
 *             onProfileClick = { navHostController.navigate("profile") },
 *             modifier = modifier
 *         )
 *     }
 * }
 *
 * class SettingsScreen : Screen(
 *     route = "settings",
 *     toolsMenuElements = listOf(helpMenuElement, aboutMenuElement)
 * ) {
 *     @Composable
 *     override fun view(navHostController: NavHostController, modifier: Modifier) {
 *         SettingsScreenContent(modifier = modifier)
 *     }
 * }
 * ```
 *
 * @property route The unique navigation route for this screen. Used for navigation
 *                 and as the screen's identifier.
 * @property toolsMenuElements List of toolbar menu elements to display when this
 *                             screen is active. Defaults to an empty list.
 *
 * @see NavHostController for navigation functionality
 * @see ToolMenuElement for toolbar menu item definition
 */
abstract class Screen(
    val route: String,
    val toolsMenuElements: List<ToolMenuElement> = emptyList()
) {
    /**
     * Composes the screen's user interface.
     *
     * This function must be implemented by subclasses to define the visual
     * representation of the screen. It receives navigation controller and
     * modifier for proper integration with the app's navigation and layout system.
     *
     * @param navHostController The navigation controller for handling navigation
     *                          between screens.
     * @param modifier The modifier to be applied to the screen's root composable.
     *                 Used for styling, layout, and behavior modifications.
     */
    @Composable
    abstract fun view(navHostController: NavHostController, modifier: Modifier = Modifier)

    override fun toString(): String = "Screen($route)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Screen) return false
        return route != other.route
    }

    override fun hashCode(): Int =
        HASH_CODE_NUMBER_GENERATOR * javaClass.name.hashCode() + route.hashCode()
}
