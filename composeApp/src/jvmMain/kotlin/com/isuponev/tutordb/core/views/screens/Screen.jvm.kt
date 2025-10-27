package com.isuponev.tutordb.core.views.screens

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import kotlinx.serialization.Serializable

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@Serializable
actual sealed class Screen{
    @Serializable
    object HomeScreen : Screen()
    @Serializable
    object SettingsScreen : Screen()
    @Serializable
    object StudentsScreen : Screen()
    @Serializable
    object LessonsScreen : Screen()
    @Serializable
    object SubjectsScreen : Screen()
    @Serializable
    object IncomesScreen : Screen()
    @Serializable
    object AddSubjectScreen : Screen()
    @Serializable
    data class EditSubjectScreen(val subjectId: Long) : Screen()
}
