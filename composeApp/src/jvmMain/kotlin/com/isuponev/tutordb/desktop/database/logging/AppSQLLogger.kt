package com.isuponev.tutordb.desktop.database.logging

import com.isuponev.tutordb.core.config.AppConfig
import kotlin.reflect.jvm.jvmName
import org.jetbrains.exposed.v1.core.SqlLogger
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.statements.StatementContext
import org.jetbrains.exposed.v1.core.statements.expandArgs

/**
 * A custom SQL logger implementation for logging SQL statements executed during database transactions.
 *
 * This class is used to log SQL queries with optional tagging for better categorization and debugging.
 * It logs SQL statements using the [AppConfig.logger] at the info level.
 *
 * @property tag An optional tag used to identify the source or context of the SQL statement.
 *            Defaults to the fully qualified name of this class.
 */
class AppSQLLogger(val tag: String = AppSQLLogger::class.jvmName) : SqlLogger {
    /**
     * Logs an SQL statement that is being executed in the current transaction.
     *
     * This method is called by the Exposed library when a SQL statement is executed. It uses the
     * provided [StatementContext] and [Transaction] to expand the SQL query with bound arguments
     * and logs the result.
     *
     * @param context The context of the SQL statement, including the SQL template and parameters.
     * @param transaction The transaction in which the SQL statement is being executed.
     */
    override fun log(
        context: StatementContext,
        transaction: Transaction
    ) {
        AppConfig.logger.i(tag = tag) {
            "SQL: ${context.expandArgs(transaction)}"
        }
    }
}
