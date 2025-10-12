package com.isuponev.tutordb.core.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.isuponev.tutordb.core.utils.and
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

class AppLogWriter(val logDir: File, val minSeverity: Severity) : LogWriter() {
    @OptIn(ExperimentalTime::class)
    private val nowDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val logFile = File(
        logDir,
        "${nowDateTime.date.format(LocalDate.Formats.ISO)}.log")
    private val maxFileSize: Long = 10 * 1024 * 1024L
    private val maxLogAge: Duration = 20.days

    private val fileLinesLimit: Int = 1000
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS")

    private val logScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logChannel = Channel<LogEntry>(BUFFERED)

    init {
        if (!logDir.exists()) logDir.mkdirs()
        cleanupOldFiles()
        startLogProcessor()
    }

    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?
    ) {
        logScope.launch {
            val entry = LogEntry(severity, message, tag, throwable)
            logChannel.send(entry)
        }
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean = Boolean.and(
        tag.isNotBlank(),
        severity.ordinal >= minSeverity.ordinal
    )

    private fun startLogProcessor() {
        logScope.launch {
            for (entry in logChannel) {
                writeLogToFile(entry)
            }
        }
    }

    private data class LogEntry(
        val severity: Severity,
        val message: String,
        val tag: String,
        val throwable: Throwable?,
        val timestamp: Long = System.currentTimeMillis()
    )

    private fun writeLogToFile(entry: LogEntry) {
        try {
            val timestamp = dateFormat.format(Date(entry.timestamp))
            val throwableStr = entry.throwable?.let {
                "\n${it.stackTraceToString()}"
            } ?: ""

            val logLine = "[$timestamp] [${entry.severity.name}] [${entry.tag}] ${entry.message}$throwableStr\n"
            logFile.appendText(logLine)
            cleanupOldLogs()
        } catch (e: IOException) {
            System.err.println("Failed to write log: ${e.message}")
        }
    }

    private fun cleanupOldLogs() {
        if (logFile.length() < maxFileSize) return
        try {
            val lines = logFile.readLines()
            val recentLines = lines.takeLast(fileLinesLimit)
            logFile.writeText(recentLines.joinToString("\n"))
        } catch (e: IOException) {
            System.err.println("Failed to clean log: ${e.message}")
        }
    }

    private fun cleanupOldFiles() {
        try {
            val cutoffTime = System.currentTimeMillis() - maxLogAge.inWholeMilliseconds

            logDir.listFiles()?.forEach { file ->
                if (shouldDeleteFile(file, cutoffTime)) {
                    file.delete()
                }
            }
        } catch (e: SecurityException) {
            System.err.println("Failed to cleanup old files: ${e.message}")
        }
    }

    private fun shouldDeleteFile(file: File, cutoffTime: Long): Boolean = Boolean.and(
        file.isFile,
        file.name.endsWith(".log"),
        file.lastModified() < cutoffTime
    )

    fun dispose() {
        runBlocking {
            logChannel.close()

            val job = logScope.coroutineContext[Job]
            job?.children?.forEach { it.join() }
            logScope.cancel()
        }
    }
}
