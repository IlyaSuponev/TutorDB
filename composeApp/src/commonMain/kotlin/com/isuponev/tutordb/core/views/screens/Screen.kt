package com.isuponev.tutordb.core.views.screens

import kotlinx.serialization.Serializable

/**
 * Navigation type of screens.
 *
 * Use its implementations to navigate between screens.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Serializable
expect sealed class Screen
