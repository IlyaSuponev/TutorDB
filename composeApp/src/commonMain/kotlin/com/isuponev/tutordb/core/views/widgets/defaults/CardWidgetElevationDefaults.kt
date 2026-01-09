package com.isuponev.tutordb.core.views.widgets.defaults

import com.isuponev.tutordb.core.views.widgets.CardWidget
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults.DEFAULT
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults.DISABLED
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults.HOVERED
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults.PRESSED
import com.isuponev.tutordb.core.views.widgets.defaults.CardWidgetElevationDefaults.TONAL
import com.isuponev.tutordb.core.views.widgets.values.Elevation
import com.isuponev.tutordb.core.views.widgets.values.TonalElevation

/**
 * Object holding the default elevation values for a [CardWidget] widget, used to define the
 * visual depth and how it changes based on different states.
 */
object CardWidgetElevationDefaults {
    /**
     * The default elevation for the card.
     */
    const val DEFAULT = 4f

    /**
     * The elevation for the card when it is hovered over.
     */
    const val HOVERED = 12f

    /**
     * The elevation for the card when it is pressed.
     */
    const val PRESSED = 8f

    /**
     * The elevation for the card when it is disabled.
     */
    const val DISABLED = 0f

    /**
     * The tonal elevation to be used.
     * This is used as an offset when the card is using a tonal background color.
     */
    const val TONAL = TonalElevation.LEVEL_3

    /**
     * Creates an [Elevation] object using the specified elevation values.
     *
     * @param defaultElevation The default elevation for the card.  Defaults to [DEFAULT].
     * @param hoveredElevation The elevation for the card when it is hovered over. Defaults to [HOVERED].
     * @param pressedElevation The elevation for the card when it is pressed. Defaults to [PRESSED].
     * @param disabledElevation The elevation for the card when it is disabled. Defaults to [DISABLED].
     * @param tonalElevation The tonal elevation to be used.  Defaults to [TONAL].
     * @return An [Elevation] object.
     */
    fun toElevation(
        defaultElevation: Float = DEFAULT,
        hoveredElevation: Float = HOVERED,
        pressedElevation: Float = PRESSED,
        disabledElevation: Float = DISABLED,
        tonalElevation: Float = TONAL
    ): Elevation = Elevation(
        defaultElevation,
        hoveredElevation,
        pressedElevation,
        disabledElevation,
        tonalElevation
    )
}
