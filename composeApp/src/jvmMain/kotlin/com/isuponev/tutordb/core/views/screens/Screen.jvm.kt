package com.isuponev.tutordb.core.views.screens

import kotlinx.serialization.Serializable

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@Serializable
actual sealed class Screen{
    @Serializable
    object HomeScreen : Screen()
}