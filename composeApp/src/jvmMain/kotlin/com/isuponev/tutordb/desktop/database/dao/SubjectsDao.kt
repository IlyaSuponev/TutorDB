package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.tables.StudentsSubjectsTable
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import com.isuponev.tutordb.desktop.utils.AppTransactionManager
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update

/**
 * A Data Access Object (DAO) implementation for managing [Subject] entities in the database.
 *
 * This class provides CRUD operations for subjects and maintains a reactive flow of subject data
 * through a [StateFlow]. It uses [AppTransactionManager] to ensure all database operations are performed
 * within managed transactions.
 */
class SubjectsDao private constructor(
    database: Database
) : Dao<Subject, SubjectsDao.IData>(
    database,
    listOf(SubjectsTable)
) {
    class IData(val name: Name, val description: String) : InsertData<Subject>

    override fun Transaction.onInsert(data: IData): Subject {
        val id = SubjectsTable.insert {
            it[name] = data.name.value
            it[description] = data.description
        } get SubjectsTable.id
        return Subject(id.value, data.name, data.description)
    }

    override fun Transaction.onGetById(id: Long): Subject? {
        val results = SubjectsTable.selectAll().where { SubjectsTable.id eq id }.map { row: ResultRow ->
            Subject(id, Name.of(row[SubjectsTable.name]), row[SubjectsTable.description])
        }
        return if (results.isEmpty()) null else results.first()
    }

    override fun Transaction.onUpdate(model: Subject) {
        SubjectsTable.update(
            where = { SubjectsTable.id eq model.id }
        ) {
            it[SubjectsTable.name] = model.name.value
            it[SubjectsTable.description] = model.description
        }
    }

    override fun Transaction.onRemove(model: Subject) {
        SubjectsTable.deleteWhere { SubjectsTable.id eq model.id }
        StudentsSubjectsTable.deleteWhere { StudentsSubjectsTable.subjectId eq model.id }
        if (StudentsDao.isInitialized(database)) StudentsDao.reload(database)
    }

    override fun Transaction.onLoadAll(): List<Subject> = SubjectsTable.selectAll().map { row: ResultRow ->
        Subject(
            id = row[SubjectsTable.id].value,
            name = Name.of(row[SubjectsTable.name]),
            description = row[SubjectsTable.description]
        )
    }

    /**
     * Companion object containing factory methods for creating instances of [SubjectsDao].
     */
    companion object : DaoBuilder<Subject, IData, SubjectsDao>() {
        override fun new(db: Database): SubjectsDao = instances.getOrPut(db) { SubjectsDao(db) }
    }
}
