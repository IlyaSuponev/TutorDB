package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import javax.money.MonetaryAmount
import kotlin.time.Duration
import kotlinx.datetime.LocalDateTime

/**
 * Represents an individual tutoring session between a tutor and a student.
 *
 * Individual lessons are scheduled sessions with specific timing, duration, and cost.
 * Each lesson is associated with a student and includes descriptive information about the session.
 *
 * @property id The unique identifier for the lesson
 * @property dateOfStart The scheduled date and time for the lesson
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
data class IndividualLesson(
    override val id: Long,
    val name: Name,
    val dateOfStart: LocalDateTime,
    val duration: Duration,
    val hourCost: MonetaryAmount,
    val description: String,
    val student: Student,
    val subject: Subject,
    val isConducted: Boolean
) : Model
