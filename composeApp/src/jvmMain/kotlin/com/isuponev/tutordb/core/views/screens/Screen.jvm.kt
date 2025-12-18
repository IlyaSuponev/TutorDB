package com.isuponev.tutordb.core.views.screens

import kotlinx.serialization.Serializable

/**
 * Navigation type of screens.
 *
 * Use its implementations to navigate between screens.
 */
@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@Serializable
actual sealed class Screen {
    /**
     * Navigate point to home screen.
     */
    @Serializable
    object HomeScreen : Screen()

    /**
     * Navigate point to settings screen.
     */
    @Serializable
    object SettingsScreen : Screen()

    /**
     * Navigate point to students screen.
     */
    @Serializable
    object StudentsScreen : Screen()

    /**
     * Navigate point to lessons screen.
     */
    @Serializable
    data class LessonsScreen(val chosenDateMillis: Long? = null) : Screen()

    /**
     * Navigate point to subjects screen.
     */
    @Serializable
    object SubjectsScreen : Screen()

    /**
     * Navigate point to incomes screen.
     */
    @Serializable
    object IncomesScreen : Screen()

    /**
     * Navigate point to add subject screen.
     */
    @Serializable
    object AddSubjectScreen : Screen()

    /**
     * Navigate point to edit subject screen.
     *
     * @property subjectId - id of subject to edit.
     */
    @Serializable
    data class EditSubjectScreen(val subjectId: Long) : Screen()

    @Serializable
    object AddStudentScreen : Screen()

    @Serializable
    data class EditStudentScreen(val studentId: Long) : Screen()

    @Serializable
    data class AddLessonScreen(val chosenDateMillis: Long) : Screen()

    @Serializable
    data class EditLessonScreen(val lessonId: Long) : Screen()
}
