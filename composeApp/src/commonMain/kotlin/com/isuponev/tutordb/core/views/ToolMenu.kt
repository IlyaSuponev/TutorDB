package com.isuponev.tutordb.core.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a single element in the toolbar menu.
 *
 * A toolbar menu element is defined as a triple containing:
 * - First: `String` - The label/text to display for the menu item
 * - Second: `ImageVector` - The icon to display for the menu item
 * - Third: `() -> Unit` - The click handler callback function
 *
 * This type alias provides a convenient way to define toolbar menu items
 * with their essential properties in a structured format.
 *
 * ## Usage Example:
 * ```kotlin
 * val settingsMenuElement = ToolMenuElement(
 *     "Settings",
 *     Icons.Default.Settings,
 *     { navigateToSettings() }
 * )
 *
 * val helpMenuElement = ToolMenuElement(
 *     "Help",
 *     Icons.Default.Help,
 *     { showHelpDialog() }
 * )
 * ```
 *
 * @see ToolMenu for the composable that uses these elements
 */
typealias ToolMenuElement = Triple<String, ImageVector, () -> Unit>

/**
 * A platform-specific toolbar menu composable.
 *
 * `ToolMenu` displays a collection of toolbar menu items, each represented by
 * a [ToolMenuElement]. The implementation is expected to vary across different
 * platforms (Android, iOS, Desktop) while providing consistent functionality.
 *
 * #### Expected Behavior:
 * - Displays menu items with icons and labels
 * - Handles click events for each menu item
 * - Adapts to platform-specific UI guidelines and conventions
 * - Supports modifier for customization and styling
 *
 * #### Usage Example:
 * ```kotlin
 * val menuElements = listOf(
 *     ToolMenuElement("Search", Icons.Default.Search, { openSearch() }),
 *     ToolMenuElement("Settings", Icons.Default.Settings, { openSettings() })
 * )
 *
 * ToolMenu(
 *     modifier = Modifier.fillMaxWidth(),
 *     toolMenuElements = menuElements
 * )
 * ```
 *
 * @param modifier The modifier to be applied to the toolbar menu layout.
 *                 Use this for styling, sizing, and positioning the menu.
 * @param toolMenuElements List of menu elements to display in the toolbar.
 *                         Each element should contain a label, icon, and click handler.
 *                         If empty, the menu may not be visible or show placeholder content.
 *
 * @see ToolMenuElement for the structure of individual menu items
 */
@Composable
expect fun ToolMenu(
    modifier: Modifier = Modifier,
    toolMenuElements: List<ToolMenuElement> = emptyList()
)
