package com.isuponev.tutordb.core.utils

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

class LocalDateTimeTest {

    @Test
    fun `plus operator adds duration correctly`() {
        val startDateTime = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = 2.hours + 30.minutes

        val result = startDateTime + duration
        assertEquals(LocalDateTime(2024, 1, 15, 16, 30), result)
    }

    @Test
    fun `plus operator handles day overflow correctly`() {
        val startDateTime = LocalDateTime(2024, 1, 15, 23, 0)
        val duration = 3.hours

        val result = startDateTime + duration
        assertEquals(LocalDateTime(2024, 1, 16, 2, 0), result)
    }

    @Test
    fun `plus operator handles month overflow correctly`() {
        val startDateTime = LocalDateTime(2024, 1, 31, 23, 0)
        val duration = 2.hours

        val result = startDateTime + duration
        assertEquals(LocalDateTime(2024, 2, 1, 1, 0), result)
    }

    @Test
    fun `plus operator handles year overflow correctly`() {
        val startDateTime = LocalDateTime(2024, 12, 31, 23, 0)
        val duration = 3.hours

        val result = startDateTime + duration
        assertEquals(LocalDateTime(2025, 1, 1, 2, 0), result)
    }

    @Test
    fun `plus operator with zero duration returns same datetime`() {
        val startDateTime = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = Duration.ZERO

        val result = startDateTime + duration
        assertEquals(startDateTime, result)
    }

    @Test
    fun `minus operator subtracts duration correctly`() {
        val startDateTime = LocalDateTime(2024, 1, 15, 16, 30)
        val duration = 2.hours + 30.minutes

        val result = startDateTime - duration
        assertEquals(LocalDateTime(2024, 1, 15, 14, 0), result)
    }

    @Test
    fun `minus operator handles day underflow correctly`() {
        val startDateTime = LocalDateTime(2024, 1, 16, 2, 0)
        val duration = 3.hours

        val result = startDateTime - duration
        assertEquals(LocalDateTime(2024, 1, 15, 23, 0), result)
    }

    @Test
    fun `minus operator handles month underflow correctly`() {
        val startDateTime = LocalDateTime(2024, 2, 1, 1, 0)
        val duration = 2.hours

        val result = startDateTime - duration
        assertEquals(LocalDateTime(2024, 1, 31, 23, 0), result)
    }

    @Test
    fun `minus operator handles year underflow correctly`() {
        val startDateTime = LocalDateTime(2025, 1, 1, 2, 0)
        val duration = 3.hours

        val result = startDateTime - duration
        assertEquals(LocalDateTime(2024, 12, 31, 23, 0), result)
    }

    @Test
    fun `minus operator with zero duration returns same datetime`() {
        val startDateTime = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = Duration.ZERO

        val result = startDateTime - duration
        assertEquals(startDateTime, result)
    }

    @Test
    fun `between calculates duration correctly for positive difference`() {
        val start = LocalDateTime(2024, 1, 15, 14, 0)
        val finish = LocalDateTime(2024, 1, 15, 16, 30)

        val duration = Duration.between(start, finish)
        assertEquals(2.hours + 30.minutes, duration)
    }

    @Test
    fun `between calculates duration correctly for negative difference`() {
        val start = LocalDateTime(2024, 1, 15, 16, 30)
        val finish = LocalDateTime(2024, 1, 15, 14, 0)

        val duration = Duration.between(start, finish)
        assertEquals((-2).hours - 30.minutes, duration)
    }

    @Test
    fun `between calculates duration correctly for same datetime`() {
        val start = LocalDateTime(2024, 1, 15, 14, 0)
        val finish = LocalDateTime(2024, 1, 15, 14, 0)

        val duration = Duration.between(start, finish)
        assertEquals(Duration.ZERO, duration)
    }

    @Test
    fun `between calculates duration correctly across days`() {
        val start = LocalDateTime(2024, 1, 15, 23, 0)
        val finish = LocalDateTime(2024, 1, 16, 2, 30)

        val duration = Duration.between(start, finish)
        assertEquals(3.hours + 30.minutes, duration)
    }

    @Test
    fun `between calculates duration correctly across months`() {
        val start = LocalDateTime(2024, 1, 31, 23, 0)
        val finish = LocalDateTime(2024, 2, 1, 2, 0)

        val duration = Duration.between(start, finish)
        assertEquals(3.hours, duration)
    }

    @Test
    fun `between calculates duration correctly across years`() {
        val start = LocalDateTime(2024, 12, 31, 23, 0)
        val finish = LocalDateTime(2025, 1, 1, 2, 30)

        val duration = Duration.between(start, finish)
        assertEquals(3.hours + 30.minutes, duration)
    }

    @Test
    fun `between handles leap year correctly`() {
        val start = LocalDateTime(2024, 2, 28, 23, 0)
        val finish = LocalDateTime(2024, 3, 1, 1, 0)

        val duration = Duration.between(start, finish)
        assertEquals(1.days + 2.hours, duration)
    }

    @Test
    fun `between handles precision to seconds`() {
        val start = LocalDateTime(2024, 1, 15, 14, 0, 0)
        val finish = LocalDateTime(2024, 1, 15, 14, 1, 30)

        val duration = Duration.between(start, finish)
        assertEquals(90.seconds, duration)
    }

    @Test
    fun `chained operations work correctly`() {
        val start = LocalDateTime(2024, 1, 15, 14, 0)

        val result = start + 2.hours - 30.minutes + 15.minutes
        assertEquals(LocalDateTime(2024, 1, 15, 15, 45), result)
    }

    @Test
    fun `operator functions are commutative`() {
        val datetime = LocalDateTime(2024, 1, 15, 14, 0)
        val duration1 = 1.hours
        val duration2 = 30.minutes

        val result1 = datetime + duration1 + duration2
        val result2 = datetime + duration2 + duration1
        val result3 = datetime + (duration1 + duration2)
        assertEquals(result1, result2)
        assertEquals(result2, result3)
    }

    @Test
    fun `inverse operations cancel each other`() {
        val original = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = 5.hours + 45.minutes

        val result = (original + duration) - duration
        assertEquals(original, result)
    }

    @Test
    fun `duration calculation is inverse of addition`() {
        val start = LocalDateTime(2024, 1, 15, 14, 0)
        val duration = 3.hours + 15.minutes
        val finish = start + duration

        val calculatedDuration = Duration.between(start, finish)
        assertEquals(duration, calculatedDuration)
    }

    @Test
    fun `edge case with maximum duration`() {
        val start = LocalDateTime(2024, 1, 1, 0, 0)
        val duration = (365 * 750 + 366 * 250).days // 1000 years

        val result = start + duration
        assertEquals(2024 + 1000, result.year)
    }
}