package com.isuponev.tutordb.core.interfaces

import java.util.UUID

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
     * @see java.util.UUID
     */
    val id: UUID
}