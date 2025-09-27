package com.isuponev.tutordb.core.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlin.time.Duration
import kotlin.time.toKotlinDuration

/**
 * Adds a specified duration to a LocalDateTime.
 *
 * This operator function allows using the `+` operator to add a [Duration] to a [LocalDateTime].
 * The operation is performed by converting the duration to minutes and adding them to the datetime.
 *
 * @param duration the duration to add to this LocalDateTime
 * @return a new LocalDateTime representing the result of adding the duration
 */
operator fun LocalDateTime.plus(duration: Duration): LocalDateTime = toJavaLocalDateTime()
    .plusMinutes(duration.inWholeMinutes)
    .toKotlinLocalDateTime()

/**
 * Subtracts a specified duration from a LocalDateTime.
 *
 * This operator function allows using the `-` operator to subtract a [Duration] from a [LocalDateTime].
 * The operation is performed by converting the duration to minutes and subtracting them from the datetime.
 *
 * @param duration the duration to subtract from this LocalDateTime
 * @return a new LocalDateTime representing the result of subtracting the duration
 */
operator fun LocalDateTime.minus(duration: Duration): LocalDateTime = toJavaLocalDateTime()
    .plusMinutes(-duration.inWholeMinutes)
    .toKotlinLocalDateTime()

/**
 * Computes the duration between two LocalDateTime instances.
 *
 * This companion object function calculates the temporal distance between two [LocalDateTime] instances.
 * The result represents the amount of time that would need to be added to [start] to get [finish].
 *
 * @param start the starting LocalDateTime (inclusive)
 * @param finish the finishing LocalDateTime (exclusive)
 * @return a Duration representing the time between start and finish
 *
 * @throws IllegalArgumentException if the temporal distance exceeds what can be stored in a Duration
 */
fun Duration.Companion.between(start: LocalDateTime, finish: LocalDateTime): Duration = java.time.Duration.between(
    start.toJavaLocalDateTime(),
    finish.toJavaLocalDateTime()
).toKotlinDuration()
