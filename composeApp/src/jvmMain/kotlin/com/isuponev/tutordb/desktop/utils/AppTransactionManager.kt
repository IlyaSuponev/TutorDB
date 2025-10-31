package com.isuponev.tutordb.desktop.utils

import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.logging.AppSQLLogger
import java.util.Collections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

/**
 * A utility object for managing database transactions in a coroutine-based environment.
 *
 * This object provides methods to execute transactions, handle success and error callbacks,
 * manage dependencies on database tables, and gracefully close all active transactions.
 */
object AppTransactionManager {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val activeJobs = Collections.synchronizedSet<Job>(mutableSetOf())

    private const val JOB_TIMEOUT_MILLIS = 250L

    /**
     * Starts a new database transaction.
     *
     * @param T The type of the result returned by the transaction.
     * @param db The database instance to use for the transaction.
     * @param logTag Optional tag for logging SQL statements related to this transaction.
     * @param onSuccess Callback invoked when the transaction completes successfully.
     * @param onError Callback invoked when an exception occurs during the transaction.
     * @param dependsOnTables Tables that should be created (if they don't exist)
     *                          before executing the transaction logic.
     * @param content The block of code to execute within the transaction.
     */
    fun <T> new(
        db: Database,
        logTag: String? = null,
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (suspend (Throwable) -> Unit)? = null,
        vararg dependsOnTables: Table,
        content: Transaction.() -> T
    ) {
        val job = scope.launch {
            runCatching {
                transaction(db.jdbc) {
                    addLogger(if (logTag == null) AppSQLLogger() else AppSQLLogger(logTag))
                    dependsOnTables.forEach { table ->
                        SchemaUtils.create(table)
                    }
                    content()
                }
            }.onSuccess {
                onSuccess?.invoke(it)
            }.onFailure {
                onError?.invoke(it)
            }
        }
        activeJobs.add(job)
        job.invokeOnCompletion {
            activeJobs.remove(job)
        }
    }

    /**
     * Closes the coroutine scope and waits for all active transactions to complete or timeout.
     * Ensures all running transactions are properly cleaned up before shutting down.
     */
    fun close() {
        waitForCompletion()
        scope.cancel()
    }

    private fun waitForCompletion() {
        runBlocking {
            val jobsToWait = activeJobs.toSet()
            if (jobsToWait.isNotEmpty()) {
                runCatching {
                    withTimeout(JOB_TIMEOUT_MILLIS * jobsToWait.size) {
                        jobsToWait.joinAll()
                    }
                }.onFailure { _ -> jobsToWait.forEach { it.cancel() } }
            }
        }
    }
}
