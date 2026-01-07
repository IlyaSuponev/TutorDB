package com.isuponev.tutordb.core.utils

import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toKotlinDuration
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaZoneOffset
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime

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


fun LocalDateTime.Companion.now(): LocalDateTime {
    return java.time.LocalDateTime.now().toKotlinLocalDateTime()
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.fromMillis(millis: Long, tz: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(millis)
    return instant.toLocalDateTime(tz)
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.fromUTCMillis(millis: Long): LocalDateTime = fromMillis(millis, TimeZone.UTC)

fun LocalDateTime.toMillis(tz: TimeZone = TimeZone.currentSystemDefault()): Long {
    return date.toMillis(tz) + time.toMillisecondOfDay()
}

fun LocalDateTime.toUTCMillis(): Long = toMillis(TimeZone.UTC)

fun LocalDate.Companion.now(): LocalDate = LocalDateTime.now().date

@OptIn(ExperimentalTime::class)
fun LocalDate.toMillis(tz: TimeZone = TimeZone.currentSystemDefault()): Long {
    return atStartOfDayIn(tz).toEpochMilliseconds()
}

fun LocalDate.toUTCMillis(): Long = toMillis(TimeZone.UTC)

fun LocalDate.Companion.fromMillis(millis: Long, tz: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return LocalDateTime.fromMillis(millis, tz).date
}

fun LocalDate.Companion.fromUTCMillis(millis: Long): LocalDate = fromMillis(millis, TimeZone.UTC)

fun LocalTime.Companion.now(): LocalTime = LocalDateTime.now().time

fun LocalDate.localizedFormat(
    locale: Locale = Locale.getDefault(),
    style: FormatStyle = FormatStyle.MEDIUM
): String = toJavaLocalDate().format(DateTimeFormatter.ofLocalizedDate(style).withLocale(locale))
