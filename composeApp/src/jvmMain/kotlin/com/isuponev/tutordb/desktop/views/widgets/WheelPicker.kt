package com.isuponev.tutordb.desktop.views.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class WheelPickerColors(
    val containerColor: Color,
    val selectedItemColor: Color,
    val unselectedItemColor: Color,
    val titleColor: Color
) {
    companion object {
        @Composable
        fun defaults(
            containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
            selectedItemColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedItemColor: Color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.5f),
            titleColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
        ): WheelPickerColors = WheelPickerColors(
            containerColor,
            selectedItemColor,
            unselectedItemColor,
            titleColor
        )
    }
}

data class WheelPickerTypography(
    val selectedItemFontWeight: FontWeight = FontWeight.Bold,
    val unselectedItemFontWeight: FontWeight = FontWeight.Medium,
    val titleFontWeight: FontWeight = FontWeight.Medium,
    val selectedItemFontSize: TextUnit = 20.sp,
    val unselectedItemFontSize: TextUnit = 20.sp,
    val titleFontSize: TextUnit = 10.sp,
)

data class WheelPickerSizes(
    val selectedItemScale: Float = 1.1f,
    val unselectedScale: Float = 0.9f,
    val selectedAlpha: Float = 1f,
    val unselectedAlpha: Float = 0.5f,
    val itemSize: Dp = 40.dp
)

@Composable
fun WheelPicker(
    items: List<String>,
    initialIndex: Int,
    onIndexChange: (Int) -> Unit,
    title: String? = null,
    modifier: Modifier = Modifier,
    colors: WheelPickerColors = WheelPickerColors.defaults(),
    typography: WheelPickerTypography = WheelPickerTypography(),
    sizes: WheelPickerSizes = WheelPickerSizes(),
    shape: Shape = RectangleShape
) = Column(
    modifier = modifier.background(colors.containerColor, shape),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerIndex = listState.firstVisibleItemIndex
            if (centerIndex in items.indices) {
                onIndexChange(centerIndex)
            }
        }
    }

    Box(
        modifier = Modifier.height(sizes.itemSize),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            items(items.size) { index ->
                val isSelected by remember {
                    derivedStateOf { listState.firstVisibleItemIndex == index }
                }

                // Dynamic Scale and Alpha based on selection
                val scale by remember {
                    derivedStateOf { if (isSelected) sizes.selectedItemScale else sizes.unselectedScale }
                }
                val alpha by remember {
                    derivedStateOf { if (isSelected) sizes.selectedAlpha else sizes.unselectedScale }
                }
                val color = if (isSelected) colors.selectedItemColor else colors.unselectedItemColor

                Box(
                    modifier = Modifier
                        .size(sizes.itemSize)
                        .scale(scale)
                        .alpha(alpha),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        fontSize =
                            if (isSelected) typography.selectedItemFontSize
                            else typography.unselectedItemFontSize,
                        fontWeight =
                            if (isSelected) typography.selectedItemFontWeight
                            else typography.unselectedItemFontWeight,
                        color = color,
                        modifier = Modifier
                    )
                }
            }
        }
    }
    if (title != null) {
        Text(
            text = title.uppercase(),
            fontSize = typography.titleFontSize,
            color = colors.titleColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier,
            textAlign = TextAlign.Center
        )
    }
}