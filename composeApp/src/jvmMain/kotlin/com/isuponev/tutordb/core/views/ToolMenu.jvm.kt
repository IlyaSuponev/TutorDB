package com.isuponev.tutordb.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.isuponev.tutordb.core.resources.SharedResources
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal actual fun ToolMenu(
    modifier: Modifier,
    toolMenuElements: List<ToolMenuElement>
) = Column(
    modifier
        .padding(AppDefaults.Paddings.SMALL)
        .background(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.shapes.medium
        ),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Image(
        painter = painterResource(SharedResources.images.logo),
        contentDescription = "app-logo",
        modifier = Modifier
            .size(AppDefaults.Sizes.LOGO_ICON_SIZE)
            .clip(CircleShape)
            .border(
                AppDefaults.Widths.Borders.THIN,
                MaterialTheme.colorScheme.onPrimaryContainer,
                CircleShape
            )
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
                    .clickable(onClick = element.third),
                tint = MaterialTheme.colorScheme.onPrimaryContainer

            )
        }
    }
    IconButton(
        onClick = {
            println("Settings")
        }
    ) {
        Icon(
            Icons.Default.Settings,
            "Settings",
            modifier = Modifier
                .size(AppDefaults.Sizes.TOOL_ICON_SIZE)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
