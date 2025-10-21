package com.isuponev.tutordb.core.logging

import ca.gosyer.appdirs.AppDirs
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import com.isuponev.tutordb.core.config.AppConfig

/**
 * Platform-specific logger configuration function for Compose Multiplatform applications.
 *
 * This expect function must be implemented on each target platform (Android, iOS, Desktop)
 * to provide platform-appropriate logger configuration. The implementation should configure
 * log writers, severity levels, and other platform-specific logging settings.
 *
 * @param appsDirs The application directories provider for platform-specific file paths.
 * @return A configured [LoggerConfig] instance appropriate for the current platform.
 */
expect fun appLoggerConfig(appsDirs: AppDirs): LoggerConfig

/**
 * Platform-specific logger cleanup and resource release function.
 *
 * This expect function must be implemented on each target platform to perform
 * platform-specific cleanup operations when the application is shutting down
 * or when logging resources need to be released. Typical implementations may
 * include flushing log buffers, closing file handles, or releasing system resources.
 *
 * Example expected implementations:
 * ```
 * // Android - may not need specific cleanup
 * actual fun appLoggerClose() {
 *     // No-op or flush operations
 * }
 *
 * // Desktop - close file writers
 * actual fun appLoggerClose() {
 *     fileLogWriter?.close()
 * }
 * ```
 *
 * @see Logger
 * @see AppLogWriter.dispose
 */
expect fun appLoggerClose()

