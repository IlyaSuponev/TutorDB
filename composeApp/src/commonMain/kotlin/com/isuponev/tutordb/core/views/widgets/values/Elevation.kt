package com.isuponev.tutordb.core.views.widgets.values

/**
 * Represents elevation values for a UI element in different states.
 *
 * Elevation is a visual cue indicating the distance of an element from the surface,
 * often achieved through shadows. This data class defines elevation values for various
 * states of a UI element, such as default, hovered, pressed, disabled, and tonal.
 *
 * @property defaultElevation The elevation value when the element is in its default,
 *     un-interacted state.  This usually provides the base shadow depth.
 * @property hoveredElevation The elevation value when the element is hovered over (e.g., with a mouse).
 *     This typically increases the shadow depth to indicate interaction.
 * @property pressedElevation The elevation value when the element is pressed (e.g., clicked or touched).
 *     This usually further increases the shadow depth, sometimes feeling like the element is pushed down.
 * @property disabledElevation The elevation value when the element is disabled.  This usually reduces the shadow depth
 *     or removes the shadow entirely to communicate that the element is inactive.
 * @property tonalElevation A special elevation value.  May be used for elements with a tonal appearance,
 *     possibly related to background tones or specific visual styles. Its meaning depends on the
 *     implementation.
 */
data class Elevation(
    val defaultElevation: Float,
    val hoveredElevation: Float,
    val pressedElevation: Float,
    val disabledElevation: Float,
    val tonalElevation: Float
)
