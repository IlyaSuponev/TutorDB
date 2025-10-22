package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.AppTransactions
import com.isuponev.tutordb.desktop.database.entities.SubjectEntity
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.v1.jdbc.Database

class SubjectsDao(private val db: Database? = null) {

    private val _allSubjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects: StateFlow<List<Subject>> = _allSubjects

    fun create(
        name: Name,
        description: String = "",
        onSuccess: (Subject) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        AppTransactions.new(
            db = db,
            logTag = "SubjectsDao.create",
            onSuccess = onSuccess,
            onError = onError,
            SubjectsTable,
        ) {
            val entity = SubjectEntity.new {
                this.name = name
                this.description = description
            }
            val subject = entity.toDomain()
            _allSubjects.update { it + subject }
            subject
        }
    }
}