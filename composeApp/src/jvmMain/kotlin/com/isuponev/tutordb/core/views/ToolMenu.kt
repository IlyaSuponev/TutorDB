package com.isuponev.tutordb.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
 * A platform-specific toolbar menu composable.
 *
 * @param modifier The modifier to be applied to the toolbar menu layout.
 *                 Use this for styling, sizing, and positioning the menu.
 * @param navController Navigation controller for tools elements
 */
@Composable
internal fun ToolMenu(
    modifier: Modifier,
    navController: NavHostController,
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
    Spacer(Modifier.weight(AppDefaults.Weights.ONE))
    IconButton(
        onClick = {
            navController.navigate(Screen.SettingsScreen)
        }
    ) {
        Icon(
            Icons.Default.Settings,
            SharedResourcesjvmMain.strings.screenSettingsName.localized(),
            modifier = Modifier
                .size(AppDefaults.Sizes.TOOL_ICON_SIZE)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
