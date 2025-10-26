package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.AppTransactions
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.entities.SubjectEntity
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.selectAll

class SubjectsDao private constructor(override val database: Database): Dao<Subject> {
    private val _allSubjects = MutableStateFlow<List<Subject>>(emptyList())
    override val values: StateFlow<List<Subject>>
        get() = _allSubjects

    init {
        loadSubjects()
    }

    private fun loadSubjects() {
        AppTransactions.new(
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

    fun create(
        name: Name,
        description: String = "",
        onSuccess: (Subject) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        AppTransactions.new(
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

    fun remove(
        subject: Subject,
        onError: (Throwable) -> Unit
    ) {
        AppTransactions.new(
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

    companion object {
        private var instances: MutableMap<Database, SubjectsDao> = mutableMapOf()
        fun new(db: Database): SubjectsDao {
            return instances.getOrPut(db) { SubjectsDao(db) }
        }
    }
}