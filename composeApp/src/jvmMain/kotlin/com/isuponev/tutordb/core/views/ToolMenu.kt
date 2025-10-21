package com.isuponev.tutordb.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.resources.SharedResources
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.screens.Screen
import dev.icerock.moko.resources.compose.painterResource

/**
 * Represents a single element in the toolbar menu.
 *
 * A toolbar menu element is defined as a triple containing:
 * - First: `String` - The label/text to display for the menu item
 * - Second: `ImageVector` - The icon to display for the menu item
 * - Third: `(NavHostController) -> Unit` - The click handler callback function
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
typealias ToolMenuElement = Triple<String, ImageVector, (NavHostController) -> Unit>

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
 * @param navController Navigation controller for tools elements
 * @param toolMenuElements List of menu elements to display in the toolbar.
 *                         Each element should contain a label, icon, and click handler.
 *                         If empty, the menu may not be visible or show placeholder content.
 *
 * @see ToolMenuElement for the structure of individual menu items
 */
@Composable
internal fun ToolMenu(
    modifier: Modifier,
    navController: NavHostController,
    toolMenuElements: List<ToolMenuElement>
) = Column(
    modifier
        .padding(AppDefaults.Paddings.SMALL)
        .background(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.shapes.medium
        )
        .width(AppDefaults.Widths.TOOLS_MENU),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Image(
        painter = painterResource(SharedResources.images.logo),
        contentDescription = "app-logo",
        modifier = Modifier
            .size(AppDefaults.Sizes.LOGO_ICON_SIZE)
            .padding(AppDefaults.Paddings.SMALL)
            .clip(CircleShape)
            .border(
                AppDefaults.Widths.Borders.THIN,
                MaterialTheme.colorScheme.onPrimaryContainer,
                CircleShape
            )
            .clickable {
                navController.navigate(Screen.HomeScreen)
            }
    )
    LazyColumn(
        Modifier.weight(AppDefaults.Weights.ONE),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL)
    ) {
        items(toolMenuElements) { element: ToolMenuElement ->
            Icon(
                imageVector = element.second,
                contentDescription = element.first,
                modifier = Modifier
                    .size(AppDefaults.Sizes.TOOL_ICON_SIZE)
                    .clip(CircleShape)
                    .clickable(onClick = { element.third(navController) }),
                tint = MaterialTheme.colorScheme.onPrimaryContainer

            )
        }
    }
    IconButton(
        onClick = {
            // TODO: undo comments
            // navController.navigate(target = SettingsScreen)
        }
    ) {
        Icon(
            Icons.Default.Settings,
            SharedResourcesjvmMain.strings.screenSettings.localized(),
            modifier = Modifier
                .size(AppDefaults.Sizes.TOOL_ICON_SIZE)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
