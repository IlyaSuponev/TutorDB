package com.isuponev.tutordb.core.logging

import ca.gosyer.appdirs.AppDirs
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import com.isuponev.tutordb.core.config.AppConfig
import java.io.File

/**
 * JVM-specific implementation of the application logger configuration.
 *
 * This function configures the Kermit logging system for JVM platforms (Desktop)
 * with a multi-writer setup that combines file-based logging with console output.
 *
 * The configuration includes:
 * - [AppLogWriter] for persistent file-based logging to the user's log directory
 * - [CommonWriter] for console output during development and debugging
 * - Debug-level severity for comprehensive logging during development
 *
 * @param appsDirs The application directories provider for platform-specific file paths.
 * @return A [LoggerConfig] instance configured for JVM/Desktop environment.
 *
 * @see LoggerConfig
 * @see AppLogWriter
 * @see CommonWriter
 * @see Severity
 */
actual fun appLoggerConfig(appsDirs: AppDirs): LoggerConfig = loggerConfigInit(
    AppLogWriter(
        File(appsDirs.getUserLogDir()),
        Severity.Info
    ),
    CommonWriter(),
    minSeverity = Severity.Debug,
)

/**
 * JVM-specific implementation of application logger cleanup and resource management.
 *
 * This function performs platform-specific cleanup operations for the logging system
 * when the application is shutting down. It ensures that all log writers, particularly
 * file-based writers, are properly disposed to prevent resource leaks and ensure
 * all buffered log entries are flushed to disk.
 *
 * The function specifically:
 * - Iterates through all configured log writers in the global appLogger
 * - Identifies [AppLogWriter] instances (file-based loggers)
 * - Calls [AppLogWriter.dispose] on each file-based writer to:
 *   - Close the log channel and stop accepting new entries
 *   - Wait for pending log entries to be written to disk
 *   - Cancel the internal coroutine scope and release resources
 *   - Ensure all file handles are properly closed
 *
 * Important: This function should be called during application shutdown to ensure:
 * - No log entries are lost due to early process termination
 * - File system resources are properly released
 * - Log files are not left in a corrupted or incomplete state
 *
 * @see AppLogWriter.dispose
 * @see appLogger
 */
actual fun appLoggerClose() = AppConfig.logger.config.logWriterList.forEach {
    if (it is AppLogWriter) {
        it.dispose()
    }
}
