package com.isuponev.tutordb.core.interfaces

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Base interface for all domain models in the application.
 *
 * Provides a common contract for entities that require unique identification.
 * All domain models should implement this interface to ensure consistent ID handling.
 */
interface Model {
    /**
     * The unique identifier for the model instance using UUID.
     *
     * @see kotlin.uuid.Uuid
     * @see kotlin.uuid.ExperimentalUuidApi
     */
    @ExperimentalUuidApi
    val id: Uuid
}