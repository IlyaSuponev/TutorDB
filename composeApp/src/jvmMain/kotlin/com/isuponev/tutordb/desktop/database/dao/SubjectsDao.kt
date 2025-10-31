package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.entities.SubjectEntity
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import com.isuponev.tutordb.desktop.utils.AppTransactionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update

/**
 * A Data Access Object (DAO) implementation for managing [Subject] entities in the database.
 *
 * This class provides CRUD operations for subjects and maintains a reactive flow of subject data
 * through a [StateFlow]. It uses [AppTransactionManager] to ensure all database operations are performed
 * within managed transactions.
 */
class SubjectsDao private constructor(override val database: Database) : Dao<Subject> {
    private val _allSubjects = MutableStateFlow<List<Subject>>(emptyList())

    /**
     * A [StateFlow] that exposes the current list of subjects. This should be observed to react
     * to changes in the subject data, such as insertions, updates, or deletions.
     */
    override val values: StateFlow<List<Subject>>
        get() = _allSubjects

    init {
        loadSubjects()
    }

    private fun loadSubjects() {
        AppTransactionManager.new(
            db = database,
            logTag = "SubjectsDao.loadSubjects",
            onSuccess = { subjects: List<Subject> ->
                _allSubjects.update { subjects }
            },
            onError = { throwable: Throwable ->
                AppConfig.logger.e(tag = "SubjectsDao", throwable = throwable) {
                    "Can't load subjects"
                }
            },
            SubjectsTable
        ) {
            SubjectsTable.selectAll().map { row: ResultRow ->
                Subject(
                    id = row[SubjectsTable.id].value,
                    name = Name.of(row[SubjectsTable.name]),
                    description = row[SubjectsTable.description]
                )
            }
        }
    }

    /**
     * Creates a new subject in the database.
     *
     * @param name The name of the subject.
     * @param description An optional description for the subject.
     * @param onSuccess Callback invoked when the subject is successfully created.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun create(
        name: Name,
        description: String = "",
        onSuccess: (Subject) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "SubjectsDao.create",
            onSuccess = { subject: Subject ->
                _allSubjects.update { it + subject }
                onSuccess(subject)
            },
            onError = onError,
            SubjectsTable,
        ) {
            val entity = SubjectEntity.new {
                this.name = name.value
                this.description = description
            }
            entity.toDomain()
        }
    }

    /**
     * Retrieves a subject by its ID from the database.
     *
     * @param id The unique identifier of the subject.
     * @param onSuccess Callback invoked with the retrieved subject or null if not found.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun getById(
        id: Long,
        onSuccess: suspend (Subject?) -> Unit,
        onError: suspend (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "SubjectsDao.getById",
            onSuccess = onSuccess,
            onError = onError,
            SubjectsTable,
        ) {
            val e = SubjectEntity.findById(id)
            e?.toDomain()
        }
    }

    /**
     * Updates an existing subject in the database.
     *
     * @param subject The updated subject data.
     * @param onSuccess Callback invoked when the update is successful.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun update(
        subject: Subject,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "SubjectsDao.update",
            onSuccess = {
                AppConfig.logger.i(tag = "SubjectsDao") {
                    "Subject $subject updated"
                }
                _allSubjects.update {
                    it.map { sub ->
                        if (sub.id == subject.id) {
                            subject
                        } else {
                            sub
                        }
                    }
                }
                onSuccess()
            },
            onError = onError,
            SubjectsTable
        ) {
            SubjectsTable.update(
                where = {
                    SubjectsTable.id eq subject.id
                }
            ) {
                it[SubjectsTable.name] = subject.name.value
                it[SubjectsTable.description] = subject.description
            }
        }
    }

    /**
     * Deletes a subject from the database.
     *
     * @param subject The subject to be removed.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun remove(
        subject: Subject,
        onError: (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "SubjectsDao.create",
            onSuccess = {
                _allSubjects.update { it - subject }
            },
            onError = onError,
            SubjectsTable,
        ) {
            SubjectsTable.deleteWhere {
                SubjectsTable.id eq subject.id
            }
        }
    }

    /**
     * Companion object containing factory methods for creating instances of [SubjectsDao].
     */
    companion object {
        private var instances: MutableMap<Database, SubjectsDao> = mutableMapOf()

        /**
         * Factory method to create or retrieve a [SubjectsDao] instance for the given [Database].
         *
         * @param db The [Database] instance to use.
         * @return The [SubjectsDao] instance associated with the provided database.
         */
        fun new(db: Database): SubjectsDao = instances.getOrPut(db) { SubjectsDao(db) }
    }
}
