package com.isuponev.tutordb.desktop.viewmodels.screens.abs

import kotlinx.coroutines.flow.StateFlow

interface Loadable {
    /**
     * A [StateFlow] exposing the current state to observers.
     */
    val state: StateFlow<State>

    /**
     * A [StateFlow] exposing the current loading progress to observers.
     */
    val loadingProgress: StateFlow<Progress>

    /**
     * Enum class representing possible states of the ViewModel.
     */
    enum class State {
        /**
         * Indicates the ViewModel is loading subject data.
         */
        Loading,

        /**
         * Indicates the ViewModel has successfully loaded subject data and is ready for interaction.
         */
        Loaded
    }

    enum class Progress(val percent: Float) {
        PROGRESS_ON_START(0f),
        PROGRESS_ON_HALF_OF_HALF(0.25f),
        PROGRESS_ON_HALF(0.5f),
        PROGRESS_ON_END(1f)
    }
}