package com.isuponev.tutordb.core.views.widgets.values

/**
 * Defines the elevation levels for tonal elevation.
 *
 * Tonal elevation is a visual property that indicates the relative depth of a UI element
 * by applying a shadow and/or altering the background color. These levels represent
 * different shadow depths, providing a visual hierarchy.
 */
object TonalElevation {
    /**
     * The elevation level for no elevation.
     *
     * Represents a flat element with no shadow or depth.
     */
    const val LEVEL_0 = 0f

    /**
     * The elevation level for a subtle shadow.
     *
     * Indicates a slight lift from the surface.
     */
    const val LEVEL_1 = 1f

    /**
     * The elevation level for a moderate shadow.
     *
     * Suggests a more noticeable lift and visual separation.
     */
    const val LEVEL_2 = 3f

    /**
     * The elevation level for a significant shadow.
     *
     * Represents a more prominent lift and depth.
     */
    const val LEVEL_3 = 6f

    /**
     * The elevation level for a strong shadow.
     *
     * Indicates a substantial lift and visual emphasis.
     */
    const val LEVEL_4 = 8f

    /**
     * The elevation level for the highest elevation.
     *
     * Represents the most prominent visual depth and emphasis.
     */
    const val LEVEL_5 = 12f
}
