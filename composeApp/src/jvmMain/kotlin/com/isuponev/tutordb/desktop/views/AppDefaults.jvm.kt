package com.isuponev.tutordb.desktop.views

import com.isuponev.tutordb.core.views.AppDefaults

/**
 * The minimum recommended width for the main application window on desktop platforms.
 *
 * This value ensures that the application maintains proper layout and usability
 * even when the window is resized to smaller dimensions. The main content area
 * should be designed to function correctly at this minimum width.
 *
 * @see AppDefaults.Platform.MAIN_WINDOW_MIN_HEIGHT
 */
val AppDefaults.Platform.MAIN_WINDOW_MIN_WIDTH: Int
    get() = 1024

/**
 * The minimum recommended height for the main application window on desktop platforms.
 *
 * This value ensures that all application content remains accessible and properly
 * laid out even when the window is resized to smaller dimensions. Critical UI
 * elements should be visible and functional at this minimum height.
 *
 * @see AppDefaults.Platform.MAIN_WINDOW_MIN_WIDTH
 */
val AppDefaults.Platform.MAIN_WINDOW_MIN_HEIGHT: Int
    get() = 720
