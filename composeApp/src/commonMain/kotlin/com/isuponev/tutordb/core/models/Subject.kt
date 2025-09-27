package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
@OptIn(ExperimentalUuidApi::class)
data class Subject(override val id: Uuid, val name: Name, val description: String) : Model
