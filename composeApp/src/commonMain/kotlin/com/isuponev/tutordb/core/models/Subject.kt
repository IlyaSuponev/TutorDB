package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import kotlinx.serialization.Serializable

/**
 * Represents a subject or topic that can be taught by tutors.
 *
 * Subjects define the areas of knowledge available for tutoring sessions.
 * Each subject has a name and optional description for additional context.
 *
 * @property id The unique identifier for the subject
 * @property name The name of the subject wrapped in a [Name] value object
 * @property description Optional detailed description of the subject content
 *
 * @see Model
 * @see Name
 */
@Serializable
data class Subject(override val id: Long, val name: Name, val description: String) : Model
