package com.isuponev.tutordb.desktop.viewmodels.screens.subject

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.views.screens.AppScreenViewModel
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.dao.SubjectsDao
import kotlinx.coroutines.flow.StateFlow

/**
 * A ViewModel for managing the Subjects screen in the application.
 *
 * This class coordinates data operations and UI interactions for the Subjects screen,
 * including observing subject data from the database, handling user actions like adding/editing/removing
 * subjects, and providing UI-related state.
 *
 * @param navHostController The navigation controller used to handle screen transitions.
 * @param db The database instance for accessing subject data through [SubjectsDao].
 */
class SubjectsViewModel(
    private val navHostController: NavHostController,
    db: Database
) : AppScreenViewModel<Screen.StudentsScreen>(Screen.StudentsScreen) {
    private val logTag = "SubjectsViewModel"
    private val subjectsDao = SubjectsDao.new(db)

    /**
     * A reactive flow of the current list of subjects loaded from the database.
     * Updates automatically when the underlying data changes via [SubjectsDao.values].
     */
    val subjects: StateFlow<List<Subject>>
        get() = subjectsDao.all

    /**
     * Navigates to the AddSubjectScreen to create a new subject.
     *
     * This method is typically triggered by a UI action like a floating action button click.
     */
    fun onClickAddSubject() {
        navHostController.navigate(Screen.AddSubjectScreen)
    }

    /**
     * Removes a subject from the database.
     *
     * @param subject The subject to be removed.
     * @see SubjectsDao.remove
     */
    fun removeSubject(subject: Subject) = subjectsDao.remove(
        subject,
        onError = { AppConfig.logger.e(tag = logTag, throwable = it) { "Failed to remove subject $subject" } }
    )

    /**
     * Navigates to the EditSubjectScreen for the specified subject.
     *
     * @param subject The subject to be edited.
     */
    fun editSubject(subject: Subject) {
        navHostController.navigate(
            Screen.EditSubjectScreen(subject.id)
        )
    }

    /**
     * Companion object containing static constants related to UI layout and configuration.
     */
    companion object {
        /**
         * Minimum height allocated for subject description columns in the UI layout.
         * Ensures sufficient space for displaying subject descriptions.
         */
        val DESCRIPTION_MIN_HEIGHT = 100.dp

        /**
         * Default number of columns to display subjects in a grid layout.
         * Adjust this value based on screen size or user preferences if needed.
         */
        const val COLUMN_COUNT = 2
    }
}
