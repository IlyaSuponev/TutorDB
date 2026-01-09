package com.isuponev.tutordb.core.logging

import co.touchlab.kermit.Severity
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AppLogWriterTest {
    private val testLogTag = "AppLogWriterTest"
    private val testDir = File("tests-logs")
    private lateinit var appLogWriter: AppLogWriter

    @BeforeEach
    fun setup() {
        if (testDir.exists()) testDir.deleteRecursively()

        appLogWriter = AppLogWriter(
            testDir,
            minSeverity = Severity.Info,
        )
    }

    @Test
    fun `test init`() {
        assertTrue(testDir.exists())
    }

    @Test
    fun `test one log`() {
        val today = LocalDateTime.now()
        val logFile = File(testDir, "${today.format(DateTimeFormatter.ISO_DATE)}.log")
        Thread.sleep(50)
        appLogWriter.log(Severity.Info, "First log", testLogTag, null)
        Thread.sleep(100)
        val todayAfterLog = LocalDateTime.now()
        assertTrue(logFile.exists())
        val context = logFile.readLines()
        assertEquals(1, context.size)
        val entry = parseLogLine(context[0].trim())
        assertNotNull(entry)
        val logTime = LocalDateTime.parse(
            entry.timestamp,
            DateTimeFormatter.ofPattern(AppLogWriter.LOG_DATE_FORMAT.toPattern())
        )
        assertTrue {
            today < logTime && logTime < todayAfterLog
        }
        assertEquals(Severity.Info.name, entry.level)
        assertEquals("First log", entry.message)
        assertEquals(testLogTag, entry.tag)
    }

    @Test
    fun `test one error log`() {
        val today = LocalDateTime.now()
        val logFile = File(testDir, "${today.format(DateTimeFormatter.ISO_DATE)}.log")
        Thread.sleep(50)
        val error = RuntimeException("Something went wrong")
        appLogWriter.log(Severity.Error, "Something went wrong", testLogTag, error)
        Thread.sleep(100)
        val todayAfterLog = LocalDateTime.now()
        assertTrue(logFile.exists())
        val context = logFile.readLines()
        val errorStack = context.subList(1, context.size)
        val entry = parseLogLine(context[0].trim())
        assertNotNull(entry)
        val logTime = LocalDateTime.parse(
            entry.timestamp,
            DateTimeFormatter.ofPattern(AppLogWriter.LOG_DATE_FORMAT.toPattern())
        )
        assertTrue {
            today < logTime && logTime < todayAfterLog
        }
        assertEquals(Severity.Error.name, entry.level)
        assertEquals("Something went wrong", entry.message)
        assertEquals(testLogTag, entry.tag)
        assertEquals(
            error.stackTraceToString(),
            errorStack.joinToString("\n")
        )
    }

    @ParameterizedTest
    @MethodSource("multipleLogsProvider")
    fun `test multiple logs`(logEntries: List<Pair<Severity, String>>) {
        val today = LocalDateTime.now()
        Thread.sleep(50)
        val logFile = File(testDir, "${today.format(DateTimeFormatter.ISO_DATE)}.log")
        val expectedLogs = mutableListOf<Triple<Severity, String, LocalDateTime>>()
        logEntries.forEach { entry ->
            appLogWriter.log(entry.first, entry.second, testLogTag, null)
            if (entry.first >= appLogWriter.minSeverity) {
                Thread.sleep(100)
                val todayAfterLog = LocalDateTime.now()
                expectedLogs.add(
                    Triple(entry.first, entry.second, todayAfterLog)
                )
            }
        }
        assertTrue(logFile.exists())
        val logs = logFile.readLines()
        assertEquals(expectedLogs.size, logs.size)
        logs.forEachIndexed { index, logLine ->
            val entry = parseLogLine(logLine)
            val expectedEntry = expectedLogs[index]
            assertNotNull(entry)
            val logTime = LocalDateTime.parse(
                entry.timestamp,
                DateTimeFormatter.ofPattern(AppLogWriter.LOG_DATE_FORMAT.toPattern())
            )
            assertTrue {
                today < logTime && logTime < expectedEntry.third
            }
            assertEquals(expectedEntry.first.name, entry.level)
            assertEquals(expectedEntry.second, entry.message)
            assertEquals(testLogTag, entry.tag)
        }
    }

    @Test
    fun `test cleanup old logs`() {
        val today = LocalDateTime.now()
        val logFile = File(testDir, "${today.format(DateTimeFormatter.ISO_DATE)}.log")
        var lastFileSize = if (logFile.exists()) logFile.length() else 0
        while (true) {
            appLogWriter.log(
                Severity.Error,
                "Message",
                testLogTag,
                RuntimeException("Something went wrong")
            )
            Thread.sleep(100)
            val currentFileSize = logFile.length()
            assertTrue(currentFileSize <= AppLogWriter.MAX_LOG_FILE_SIZE)
            if (currentFileSize > lastFileSize) lastFileSize = currentFileSize
            else if (currentFileSize == lastFileSize) throw RuntimeException("Not add new log to log file")
            else break // cleanup is running
        }
        assertTrue(logFile.readLines().size <= AppLogWriter.FILE_LINES_LIMIT)
    }

    @Test
    fun `test cleanup old logs files`() {
        val today = LocalDateTime.now()
        (0L..30L).forEach { i ->
            val fileDate = today.plusDays(-i)
            val instant = fileDate.atZone(ZoneId.systemDefault()).toInstant()
            val milliseconds = instant.toEpochMilli()
            val file = File(
                testDir,
                "${fileDate.toKotlinLocalDateTime().date.format(LocalDate.Formats.ISO)}.log"
            )
            file.createNewFile()
            file.setLastModified(milliseconds)
        }
        appLogWriter.dispose()
        Thread.sleep(100)
        appLogWriter = AppLogWriter(
            testDir,
            minSeverity = Severity.Info,
        )
        (0L..30L).forEach { i ->
            val fileDate = today.plusDays(-i)
            val file = File(
                testDir,
                "${fileDate.toKotlinLocalDateTime().date.format(LocalDate.Formats.ISO)}.log"
            )
            if (i in 0L..<AppLogWriter.MAX_LOG_FILE_AGE.toLong(DurationUnit.DAYS)) {
                assertTrue(file.exists())
            } else {
                assertFalse(file.exists())
            }
        }
    }

    @AfterEach
    fun teardown() {
        appLogWriter.dispose()
        if (testDir.exists()) testDir.deleteRecursively()
    }

    companion object {
        data class LogEntry(
            val timestamp: String,
            val level: String,
            val tag: String,
            val message: String
        )

        fun parseLogLine(logLine: String): LogEntry? {
            val pattern = """\[(\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2}\.\d{3})\] \[(\w+)\] \[([^\]]+)\] (.+)"""
            val regex = Regex(pattern)

            return regex.find(logLine)?.let { matchResult ->
                val (timestamp, level, tag, message) = matchResult.destructured
                LogEntry(timestamp, level, tag, message)
            }
        }

        @JvmStatic
        fun multipleLogsProvider(): List<Arguments> {
            return (0..5).map {
                var summaryCount = 0
                val args = Severity.entries.flatMap { severity ->
                    val list = mutableListOf<Pair<Severity, String>>()
                    (1..Random.nextInt(1, 4)).forEach { _ ->
                        summaryCount++
                        list.add(severity to "Log #$summaryCount")
                    }
                    list
                }
                Arguments.of(args)
            }
        }
    }
}
