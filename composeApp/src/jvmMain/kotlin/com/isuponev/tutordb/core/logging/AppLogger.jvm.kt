package com.isuponev.tutordb.core.logging

import ca.gosyer.appdirs.AppDirs
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import java.io.File

actual fun appLoggerConfig(appsDirs: AppDirs): LoggerConfig = loggerConfigInit(
    AppLogWriter(
        File(appsDirs.getUserLogDir()),
        Severity.Info
    ),
    CommonWriter(),
    minSeverity = Severity.Debug,
)

actual fun appLoggerClose() = appLogger.config.logWriterList.forEach {
    if (it is AppLogWriter) {
        it.dispose()
    }
}
