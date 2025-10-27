package com.isuponev.tutordb.core.views

import androidx.compose.ui.unit.dp

/**
 * A centralized configuration object for application-wide design constants.
 *
 * `AppDefaults` provides a comprehensive set of design tokens and constants
 * that define the visual design system of the application. Using these
 * centralized constants ensures design consistency across all screens and
 * components while making global design changes easier to manage.
 *
 * #### Usage Example:
 * ```kotlin
 * Column(
 *     modifier = Modifier.padding(AppDefaults.Paddings.MEDIUM),
 *     verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.SMALL)
 * ) {
 *     Icon(
 *         imageVector = Icons.Default.Person,
 *         contentDescription = "Profile",
 *         modifier = Modifier.size(AppDefaults.Sizes.TOOL_ICON_SIZE)
 *     )
 * }
 * ```
 *
 * @see Paddings for spacing constants used in padding modifiers
 * @see Arrangements for spacing constants used in arrangement
 * @see Sizes for dimension constants used in sizing modifiers
 * @see Scales for animation scale transformation constants
 * @see Weights for layout weight distribution constants
 * @see Widths for width-related dimension constants
 */
object AppDefaults {
    /**
     * Constants for padding and spacing throughout the application.
     *
     * These values define the standard spacing system used for padding
     * modifiers and component spacing. Using these constants ensures
     * consistent spacing across all UI elements.
     *
     * @property ZERO Zero spacing (0.dp)
     * @property SMALL Small spacing (4.dp) - Used for tight spacing
     * @property MEDIUM Medium spacing (8.dp) - Standard spacing between related elements
     * @property BIG Big spacing (16.dp) - Used for section separation
     */
    object Paddings {
        val ZERO = 0.dp
        val SMALL = 4.dp
        val MEDIUM = 8.dp
        val BIG = 16.dp
    }

    /**
     * Constants for arrangement spacing between composable elements.
     *
     * These values are specifically intended for use with arrangement
     * parameters in layout composables like `Column` and `Row`.
     *
     * @property ZERO Zero arrangement spacing (0.dp)
     * @property SMALL Small arrangement spacing (8.dp) - Tight grouping
     * @property MEDIUM Medium arrangement spacing (16.dp) - Standard grouping
     * @property BIG Big arrangement spacing (24.dp) - Loose grouping
     */
    object Arrangements {
        val ZERO = 0.dp
        val SMALL = 8.dp
        val MEDIUM = 16.dp
        val BIG = 24.dp
    }

    /**
     * Constants for fixed dimensions and sizing throughout the application.
     *
     * These values define standard sizes for icons, logos, and other
     * UI elements that require fixed dimensions.
     *
     * @property ZERO Zero size (0.dp)
     * @property LOGO_ICON_SIZE Standard size for logo icons (64.dp)
     * @property TOOL_ICON_SIZE Standard size for toolbar icons (32.dp)
     */
    object Sizes {
        val ZERO = 0.dp
        val LOGO_ICON_SIZE = 64.dp
        val TOOL_ICON_SIZE = 32.dp
    }

    /**
     * Constants for scale transformations used in animations and visual effects.
     *
     * These float values represent scale factors for transform modifiers,
     * commonly used in hover effects, press animations, and focus states.
     *
     * @property ZERO Zero scale (0.dp) - Note: This seems to be a dimensional value,
     *               consider if it should be 0f for consistency with other scales
     * @property INITIAL Initial/normal scale (1f) - No transformation
     * @property SMALL Small scale increase (1.05f) - Subtle hover effect
     * @property MEDIUM Medium scale increase (1.1f) - Noticeable transformation
     * @property BIG Big scale increase (1.25f) - Prominent transformation
     */
    object Scales {
        val ZERO = 0.dp
        const val INITIAL = 1f
        const val SMALL = 1.05f
        const val MEDIUM = 1.1f
        const val BIG = 1.25f
    }

    /**
     * Constants for layout weight distribution in flexible layouts.
     *
     * These float values represent weight factors used in weight modifiers
     * to distribute available space between multiple composables in layouts
     * like `Row` and `Column`.
     *
     * @property ONE Single weight unit (1f) - Base weight
     * @property TWO Double weight unit (2f) - Takes twice the space of ONE
     * @property THREE Triple weight unit (3f) - Takes three times the space of ONE
     */
    object Weights {
        const val ONE = 1f
        const val TWO = 2f
        const val THREE = 3f
    }

    /**
     * Constants for width-related dimensions throughout the application.
     *
     * These values define standard widths for various UI components
     * and include nested constants for border widths.
     *
     * @property TOOLS_MENU Standard width for tools menu (80.dp)
     * @property Borders Nested object containing border width constants
     */
    object Widths {
        val TOOLS_MENU = 80.dp


        /**
         * Constants for border widths used throughout the application.
         *
         * These values define the standard border thickness for various
         * UI components like cards, buttons, and input fields.
         *
         * @property INITIAL No border (0.dp)
         * @property THIN Thin border (1.dp) - Subtle separation
         * @property FAT Thick border (4.dp) - Strong emphasis
         */
        object Borders {
            val INITIAL = 0.dp
            val THIN = 1.dp
            val FAT = 4.dp
        }
    }

    /**
     * Constants and configurations for platform-specific UI adaptations.
     *
     * This object contains constants that may vary across different platforms
     * (Desktop, Android, Web) to ensure optimal user experience on each platform.
     * Use these constants when platform-specific behavior or styling is required.
     */
    object Platform

    object Fraction {
        const val FULL = 1f
        const val HALF = 0.5f
        const val QUARTER = 0.25f
        const val THREE_QUARTERS = 0.75f
        const val THIRD = 0.33f
        const val TWO_THIRD = 0.67f
    }
}
