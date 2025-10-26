package com.isuponev.tutordb.desktop.database

import com.isuponev.tutordb.desktop.database.logging.AppSQLLogger
import java.util.Collections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object AppTransactions {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val activeJobs = Collections.synchronizedSet<Job>(mutableSetOf())

    private const val JOB_TIMEOUT_MILLIS = 250L

    fun <T> new(
        db: Database,
        logTag: String? = null,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        vararg dependsOnTables: Table,
        content: Transaction.() -> T
    ) {
        val job = scope.launch {
            try {
                val result: T = transaction(db.jdbc) {
                    addLogger(if (logTag == null) AppSQLLogger() else AppSQLLogger(logTag))
                    dependsOnTables.forEach { table ->
                        SchemaUtils.create(table)
                    }
                    content()
                }
                if (onSuccess != null) {
                    onSuccess(result)
                }
            } catch (e: Exception) {
                if (onError != null) {
                    onError(e)
                }
            }
        }
        activeJobs.add(job)
        job.invokeOnCompletion {
            activeJobs.remove(job)
        }
    }

    fun close() {
        waitForCompletion()
        scope.cancel()
    }

    private fun waitForCompletion() {
        runBlocking {
            val jobsToWait = activeJobs.toSet()
            if (jobsToWait.isNotEmpty()) {
                try {
                    withTimeout(JOB_TIMEOUT_MILLIS * jobsToWait.size) {
                        jobsToWait.joinAll()
                    }
                } catch (e: TimeoutCancellationException) {
                    jobsToWait.forEach { it.cancel() }
                }
            }
        }
    }
}
