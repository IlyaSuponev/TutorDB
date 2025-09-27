package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.utils.plus
import kotlinx.datetime.LocalDateTime
import org.javamoney.moneta.Money
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class IndividualLessonTest {

    @Test
    fun `should create individual lesson with all parameters`() {
        val lesson = createTestLesson()

        assertEquals("Math tutoring session", lesson.description)
        assertEquals(2.hours, lesson.duration)
        assertEquals(Money.of(50, "USD"), lesson.hourCost)
        assertEquals("John Doe", lesson.student.name.value)
    }

    @Test
    fun `lessons with same properties should be equal`() {
        val student = createTestStudent()
        val dateTime = LocalDateTime(2024, 1, 15, 14, 0)
        val lesson1 = IndividualLesson(
            id = Uuid.random(),
            dateTime = dateTime,
            duration = 1.hours,
            hourCost = Money.of(50, "USD"),
            description = "Lesson",
            student = student
        )
        val lesson2 = IndividualLesson(
            id = lesson1.id,
            dateTime = dateTime,
            duration = 1.hours,
            hourCost = Money.of(50, "USD"),
            description = "Lesson",
            student = student
        )

        assertEquals(lesson1, lesson2)
        assertEquals(lesson1.hashCode(), lesson2.hashCode())
    }

    @Test
    fun `lessons with different date times should not be equal`() {
        val student = createTestStudent()
        val id = Uuid.random()
        val lesson1 = IndividualLesson(
            id = id,
            dateTime = LocalDateTime(2024, 1, 15, 14, 0),
            duration = 1.hours,
            hourCost = Money.of(50, "USD"),
            description = "Lesson",
            student = student
        )
        val lesson2 = IndividualLesson(
            id = id,
            dateTime = LocalDateTime(2024, 1, 15, 15, 0),
            duration = 1.hours,
            hourCost = Money.of(50, "USD"),
            description = "Lesson",
            student = student
        )

        assertNotEquals(lesson1, lesson2)
    }

    @Test
    fun `should calculate end time correctly`() {
        val startTime = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = 1.hours + 30.minutes
        val lesson = IndividualLesson(
            id = Uuid.random(),
            dateTime = startTime,
            duration = duration,
            hourCost = Money.of(50, "USD"),
            description = "Lesson",
            student = createTestStudent()
        )

        val endTime = lesson.dateTime + lesson.duration
        assertEquals(LocalDateTime(2024, 1, 15, 15, 30), endTime)
    }

    @Test
    fun `copy method should create modified instance`() {
        val original = createTestLesson()
        val newDuration = 1.hours + 30.minutes
        val newDescription = "Modified description"

        val modified = original.copy(
            duration = newDuration,
            description = newDescription
        )
        assertEquals(original.id, modified.id)
        assertEquals(original.dateTime, modified.dateTime)
        assertEquals(newDuration, modified.duration)
        assertEquals(original.hourCost, modified.hourCost)
        assertEquals(newDescription, modified.description)
        assertEquals(original.student, modified.student)
    }

    @Test
    fun `should support destructuring declaration`() {
        val lesson = createTestLesson()

        val (id, dateTime, duration, hourCost, description, student) = lesson
        assertEquals(lesson.id, id)
        assertEquals(lesson.dateTime, dateTime)
        assertEquals(lesson.duration, duration)
        assertEquals(lesson.hourCost, hourCost)
        assertEquals(lesson.description, description)
        assertEquals(lesson.student, student)
    }

    @Test
    fun `should handle different duration units`() {
        val durations = listOf(45.minutes, 2.hours, 90.minutes)

        durations.forEach { duration ->
            val lesson = IndividualLesson(
                id = Uuid.random(),
                dateTime = LocalDateTime(2024, 1, 15, 14, 0),
                duration = duration,
                hourCost = Money.of(50, "USD"),
                description = "Lesson with duration $duration",
                student = createTestStudent()
            )

            assertEquals(duration, lesson.duration)
        }
    }

    private fun createTestLesson(): IndividualLesson {
        return IndividualLesson(
            id = Uuid.random(),
            dateTime = LocalDateTime(2024, 1, 15, 14, 0),
            duration = 2.hours,
            hourCost = Money.of(50, "USD"),
            description = "Math tutoring session",
            student = createTestStudent()
        )
    }

    private fun createTestStudent(): Student {
        return Student(
            id = Uuid.random(),
            name = Name.of("John Doe"),
            hourCost = Money.of(50, "USD")
        )
    }
}