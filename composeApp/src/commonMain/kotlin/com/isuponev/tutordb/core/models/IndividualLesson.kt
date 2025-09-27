package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.interfaces.Model
import kotlinx.datetime.LocalDateTime
import javax.money.MonetaryAmount
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents an individual tutoring session between a tutor and a student.
 *
 * Individual lessons are scheduled sessions with specific timing, duration, and cost.
 * Each lesson is associated with a student and includes descriptive information about the session.
 *
 * @property id The unique identifier for the lesson
 * @property dateTime The scheduled date and time for the lesson
 * @property duration The length of the lesson session
 * @property hourCost The hourly rate for this lesson as a [MonetaryAmount]
 * @property description Additional details or notes about the lesson content
 * @property student The student attending this lesson session
 *
 * @see Model
 * @see LocalDateTime
 * @see Duration
 * @see MonetaryAmount
 * @see Student
 */
@OptIn(ExperimentalUuidApi::class)
data class IndividualLesson(
    override val id: Uuid,
    val dateTime: LocalDateTime,
    val duration: Duration,
    val hourCost: MonetaryAmount,
    val description: String,
    val student: Student,
) : Model
