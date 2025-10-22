package com.isuponev.tutordb.desktop.database.logging

import com.isuponev.tutordb.core.config.AppConfig
import kotlin.reflect.jvm.jvmName
import org.jetbrains.exposed.v1.core.SqlLogger
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.statements.StatementContext
import org.jetbrains.exposed.v1.core.statements.expandArgs

class AppSQLLogger(val tag: String = AppSQLLogger::class.jvmName) : SqlLogger {
    override fun log(
        context: StatementContext,
        transaction: Transaction
    ) {
        AppConfig.logger.i(tag = tag) {
            "SQL: ${context.expandArgs(transaction)}"
        }
    }
}