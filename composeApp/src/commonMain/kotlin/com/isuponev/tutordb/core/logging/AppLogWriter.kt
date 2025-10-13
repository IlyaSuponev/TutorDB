package com.isuponev.tutordb.core.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.isuponev.tutordb.core.utils.all
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

/**
 * A file-based log writer implementation for Compose Multiplatform applications with configurable log management.
 *
 * This class provides asynchronous file logging with automatic log rotation, retention policies, and size-based cleanup.
 * Logs are written to daily files and managed according to configurable limits in the companion object.
 *
 * Key features:
 * - Asynchronous log processing using coroutines and channels for non-blocking I/O
 * - Daily log file rotation (one file per day in ISO date format)
 * - Configurable log retention and size limits via companion object constants
 * - Automatic cleanup of old log files based on age and size
 * - Severity-based log filtering
 * - Thread-safe operations through buffered channel processing
 *
 * Log file format example:
 * ```
 * [31-12-2024 14:30:25.123] [INFO] [NetworkService] API call successful
 * [31-12-2024 14:30:26.456] [ERROR] [Database] Connection failed
 * java.sql.SQLException: Connection timeout
 *     at com.example.Database.connect(Database.kt:45)
 * ```
 *
 * @param logDir The directory where log files will be stored. Created automatically if it doesn't exist.
 * @param minSeverity The minimum severity level for logs to be written. Logs with lower severity are ignored.
 *
 * @see LogWriter
 * @see Severity
 */
class AppLogWriter(val logDir: File, val minSeverity: Severity) : LogWriter() {
    @OptIn(ExperimentalTime::class)
    private val nowDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val logFile = File(
        logDir,
        "${nowDateTime.date.format(LocalDate.Formats.ISO)}.log")

    private val logScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logChannel = Channel<LogEntry>(BUFFERED)

    init {
        if (!logDir.exists()) logDir.mkdirs()
        cleanupOldFiles()
        startLogProcessor()
    }

    /**
     * Asynchronously writes a log entry to the current log file after filtering.
     *
     * This method first checks if the log should be written based on [isLoggable] criteria,
     * then queues the entry for asynchronous processing via [logChannel].
     *
     * @param severity The severity level of the log entry.
     * @param message The log message content.
     * @param tag The tag identifying the source or category of the log entry.
     * @param throwable An optional throwable associated with the log entry for error tracking.
     *
     * @see isLoggable
     * @see LogEntry
     */
    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?
    ) {
        if (!isLoggable(tag, severity)) return
        logScope.launch {
            val entry = LogEntry(severity, message, tag, throwable)
            logChannel.send(entry)
        }
    }

    /**
     * Determines whether a log entry meets the criteria for being written.
     *
     * A log entry is considered loggable if both conditions are met:
     * 1. The tag is not blank (contains non-whitespace characters)
     * 2. The severity level is equal to or higher than [minSeverity]
     *
     * @param tag The tag identifying the source of the log entry.
     * @param severity The severity level of the log entry.
     * @return `true` if the log entry should be processed, `false` otherwise.
     */
    override fun isLoggable(tag: String, severity: Severity): Boolean = all(
        { tag.isNotBlank() },
        { severity >= minSeverity }
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
            val timestamp = LOG_DATE_FORMAT.format(Date(entry.timestamp))
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
        if (logFile.length() < MAX_LOG_FILE_SIZE) return
        try {
            val lines = logFile.readLines()
            val recentLines = lines.takeLast(FILE_LINES_LIMIT)
            logFile.writeText(recentLines.joinToString("\n"))
        } catch (e: IOException) {
            System.err.println("Failed to clean log: ${e.message}")
        }
    }

    private fun cleanupOldFiles() {
        try {
            val cutoffTime = System.currentTimeMillis() - MAX_LOG_FILE_AGE.inWholeMilliseconds

            logDir.listFiles()?.forEach { file ->
                if (shouldDeleteFile(file, cutoffTime)) {
                    file.delete()
                }
            }
        } catch (e: SecurityException) {
            System.err.println("Failed to cleanup old files: ${e.message}")
        }
    }

    private fun shouldDeleteFile(file: File, cutoffTime: Long): Boolean = all(
        { file.isFile },
        { file.name.endsWith(".log") },
        { file.lastModified() < cutoffTime }
    )

    /**
     * Gracefully shuts down the log writer and releases all resources.
     *
     * This method ensures orderly shutdown by:
     * 1. Closing the log channel to prevent new entries
     * 2. Waiting for all queued entries to be processed
     * 3. Cancelling the coroutine scope and its children
     *
     * Should be called when the log writer is no longer needed, typically during application shutdown.
     */
    fun dispose() {
        runBlocking {
            logChannel.close()

            val job = logScope.coroutineContext[Job]
            job?.children?.forEach { it.join() }
            logScope.cancel()
        }
    }

    /**
     * Companion object containing configuration constants for log management.
     *
     * These values control the behavior of log file rotation, retention, and cleanup policies.
     * Modify these constants to adjust log management according to application requirements.
     */
    companion object {
        /**
         * Maximum allowed size for a single log file in bytes.
         * When a log file exceeds this size, it will be truncated to [fileLinesLimit].
         *
         * Default: 10 MB (10 * 1024 * 1024 bytes)
         */
        const val MAX_LOG_FILE_SIZE: Long = 10 * 1024 * 1024L

        /**
         * Maximum age for log files before they are automatically deleted.
         * Files older than this duration will be removed during cleanup operations.
         *
         * Default: 20 days
         */
        val MAX_LOG_FILE_AGE: Duration = 20.days

        /**
         * Maximum number of lines to preserve when truncating oversized log files.
         * Only the most recent lines are kept when [maxFileSize] is exceeded.
         *
         * Default: 1000 lines
         */
        const val FILE_LINES_LIMIT: Int = 1000

        /**
         * Date formatter for log entry timestamps.
         * Format: "dd-MM-yyyy HH:mm:ss.SSS" (day-month-year hour:minute:second.millisecond)
         *
         * Example: "31-12-2024 14:30:25.123"
         */
        val LOG_DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS")
    }
}
