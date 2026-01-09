package com.isuponev.tutordb.core.views.widgets

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults
import com.isuponev.tutordb.core.views.widgets.values.Elevation

/**
 * A customizable Card widget with elevation and hover-based scaling capabilities.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale.
 *
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states (default, hovered, disabled, tonal).
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param content The content to be displayed inside the card.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardWidget(
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    content: @Composable () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val elevation by animateFloatAsState(
        targetValue = when {
            !isEnabled -> cardElevation.disabledElevation
            isHovered -> cardElevation.hoveredElevation
            else -> cardElevation.defaultElevation
        },
        animationSpec = elevationAnimationSpec
    )
    val scale by animateFloatAsState(
        if (isHovered) scaleOnHover else AppDefaults.Scales.INITIAL,
        animationSpec = scaleAnimationSpec
    )

    Surface(
        modifier = modifier
            .graphicsLayer {
                shape = cardShape
                clip = true
                shadowElevation = elevation
                if (scaleOnHover != AppDefaults.Scales.INITIAL) {
                    scaleX = scale
                    scaleY = scale
                }
            }
            .hoverable(interactionSource = remember { MutableInteractionSource() })
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        shape = cardShape,
        contentColor = if (isEnabled) cardColors.contentColor else cardColors.disabledContentColor,
        color = if (isEnabled) cardColors.containerColor else cardColors.disabledContainerColor,
        tonalElevation = cardElevation.tonalElevation.dp,
        content = content
    )
}

/**
 * A customizable Card widget with elevation and hover-based scaling capabilities.
 * This overload specifically allows for content to be placed within a [BoxScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale.
 *
 * @param S to choose type of container as [BoxScope]
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states (default, hovered, disabled, tonal).
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Box]. Defaults to [Alignment.TopStart].
 * @param content The content to be displayed inside the card, provided as a [BoxScope] lambda.
 */
@Composable
fun <S : BoxScope> CardWidget(
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) = CardWidget(
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Box(
        modifier = Modifier
            .padding(contentPadding),
        contentAlignment = alignment,
        content = content
    )
}

/**
 * A customizable Card widget with elevation and hover-based scaling capabilities.
 * This overload specifically allows for content to be placed within a [RowScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale.
 *
 * @param S to choose type of container as [RowScope]
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states (default, hovered, disabled, tonal).
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Row]. Defaults to [Alignment.Top].
 * @param content The content to be displayed inside the card, provided as a [RowScope] lambda.
 */
@Composable
fun <S : RowScope> CardWidget(
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) = CardWidget(
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Row(
        modifier = Modifier
            .padding(contentPadding),
        verticalAlignment = alignment,
        content = content
    )
}

/**
 * A customizable Card widget with elevation and hover-based scaling capabilities.
 * This overload specifically allows for content to be placed within a [ColumnScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale.
 *
 * @param S to choose type of container as [ColumnScope]
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states (default, hovered, disabled, tonal).
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Column]. Defaults to [Alignment.Start].
 * @param content The content to be displayed inside the card, provided as a [ColumnScope] lambda.
 */
@Composable
fun <S : ColumnScope> CardWidget(
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = CardWidget(
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Column(
        modifier = Modifier
            .padding(contentPadding),
        horizontalAlignment = alignment,
        content = content
    )
}

/**
 * A customizable and clickable Card widget with elevation, hover-based scaling, and a pressed state.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale. Crucially, it also includes
 * a [onClick] listener, making the card interactive, and introduces a `pressedElevation`
 * for visual feedback when clicked.
 *
 * @param onClick The callback to be invoked when the card is clicked.
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation, and will not be clickable. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states
 *   (default, hovered, pressed, disabled, tonal). Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param content The content to be displayed inside the card.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardWidget(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    val elevation by animateFloatAsState(
        targetValue = when {
            !isEnabled -> cardElevation.disabledElevation
            isPressed -> cardElevation.pressedElevation
            isHovered -> cardElevation.hoveredElevation
            else -> cardElevation.defaultElevation
        },
        animationSpec = elevationAnimationSpec
    )
    val scale by animateFloatAsState(
        if (isHovered) scaleOnHover else AppDefaults.Scales.INITIAL,
        animationSpec = scaleAnimationSpec
    )

    Surface(
        modifier = modifier
            .graphicsLayer {
                shape = cardShape
                clip = true
                shadowElevation = elevation
                if (scaleOnHover != AppDefaults.Scales.INITIAL) {
                    scaleX = scale
                    scaleY = scale
                }
            }
            .clickable {
                onClick()
                isPressed = !isPressed
            }
            .hoverable(interactionSource = remember { MutableInteractionSource() })
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        shape = cardShape,
        contentColor = if (isEnabled) cardColors.contentColor else cardColors.disabledContentColor,
        color = if (isEnabled) cardColors.containerColor else cardColors.disabledContainerColor,
        tonalElevation = cardElevation.tonalElevation.dp,
        content = content
    )
}

/**
 * A customizable and clickable Card widget with elevation, hover-based scaling, and a pressed state.
 * This overload specifically allows for content to be placed within a [BoxScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale. Crucially, it also includes
 * a [onClick] listener, making the card interactive, and introduces a `pressedElevation`
 * for visual feedback when clicked.
 *
 * @param S to choose type of container as [BoxScope]
 * @param onClick The callback to be invoked when the card is clicked.
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation, and will not be clickable. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states.
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Box]. Defaults to [Alignment.TopStart].
 * @param content The content to be displayed inside the card, provided as a [BoxScope] lambda.
 */
@Composable
fun <S : BoxScope> CardWidget(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) = CardWidget(
    onClick = onClick,
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Box(
        modifier = Modifier
            .padding(contentPadding),
        contentAlignment = alignment,
        content = content
    )
}

/**
 * A customizable and clickable Card widget with elevation, hover-based scaling, and a pressed state.
 * This overload specifically allows for content to be placed within a [RowScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale. Crucially, it also includes
 * a [onClick] listener, making the card interactive, and introduces a `pressedElevation`
 * for visual feedback when clicked.
 *
 * @param S to choose type of container as [RowScope]
 * @param onClick The callback to be invoked when the card is clicked.
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation, and will not be clickable. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states.
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Row]. Defaults to [Alignment.Top].
 * @param content The content to be displayed inside the card, provided as a [RowScope] lambda.
 */
@Composable
fun <S : RowScope> CardWidget(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) = CardWidget(
    onClick = onClick,
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Row(
        modifier = Modifier
            .padding(contentPadding),
        verticalAlignment = alignment,
        content = content
    )
}

/**
 * A customizable and clickable Card widget with elevation, hover-based scaling, and a pressed state.
 * This overload specifically allows for content to be placed within a [ColumnScope],
 * providing convenient control over content padding and alignment.
 *
 * This composable provides a flexible card component that can be styled with various shapes,
 * colors, and elevation profiles. It supports a disabled state, and optionally,
 * a hover effect that can change its elevation and scale. Crucially, it also includes
 * a [onClick] listener, making the card interactive, and introduces a `pressedElevation`
 * for visual feedback when clicked.
 *
 * @param S to choose type of container as [ColumnScope]
 * @param onClick The callback to be invoked when the card is clicked.
 * @param modifier The modifier to be applied to the Card.
 * @param cardShape The shape of the card. Defaults to [RectangleShape].
 * @param cardColors The colors to be used for the card. Defaults to [CardDefaults.cardColors()].
 * @param isEnabled Whether the card is enabled. If false, the card will display disabled colors
 *   and elevation, and will not be clickable. Defaults to `true`.
 * @param cardElevation The elevation values for the card in different states.
 *   Defaults to [CardWidgetElevationDefaults.toElevation()].
 * @param elevationAnimationSpec The animation spec for elevation changes. Defaults to [tween()].
 * @param scaleOnHover The scale factor to apply when the card is hovered. A value of 1f means no scaling.
 *   Defaults to [AppDefaults.Scales.INITIAL].
 * @param scaleAnimationSpec The animation spec for scale changes. Defaults to the same as [elevationAnimationSpec].
 * @param contentPadding The padding to be applied to the content inside the card.
 *   Defaults to [PaddingValues()] (no padding).
 * @param alignment The alignment of the content within the card's inner [Column]. Defaults to [Alignment.Start].
 * @param content The content to be displayed inside the card, provided as a [ColumnScope] lambda.
 */
@Composable
fun <S : ColumnScope> CardWidget(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardShape: Shape = RectangleShape,
    cardColors: CardColors = CardDefaults.cardColors(),
    isEnabled: Boolean = true,
    cardElevation: Elevation = CardWidgetElevationDefaults.toElevation(),
    elevationAnimationSpec: AnimationSpec<Float> = tween(),
    scaleOnHover: Float = AppDefaults.Scales.INITIAL,
    scaleAnimationSpec: AnimationSpec<Float> = elevationAnimationSpec,
    contentPadding: PaddingValues = PaddingValues(AppDefaults.Paddings.ZERO),
    alignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = CardWidget(
    onClick = onClick,
    modifier = modifier,
    cardShape = cardShape,
    cardColors = cardColors,
    isEnabled = isEnabled,
    cardElevation = cardElevation,
    elevationAnimationSpec = elevationAnimationSpec,
    scaleOnHover = scaleOnHover,
    scaleAnimationSpec = scaleAnimationSpec
) {
    Column(
        modifier = Modifier
            .padding(contentPadding),
        horizontalAlignment = alignment,
        content = content
    )
}
