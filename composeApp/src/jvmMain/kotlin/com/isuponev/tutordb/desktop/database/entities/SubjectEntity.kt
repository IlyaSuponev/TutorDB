package com.isuponev.tutordb.desktop.database.entities

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass

/**
 * A DAO entity class representing a subject in the database.
 *
 * This class maps to the [SubjectsTable] and provides access to the fields defined in that table.
 * It extends [LongEntity], indicating that the primary key is of type `Long`.
 */
class SubjectEntity(id: EntityID<Long>) : LongEntity(id) {
    /**
     * The name of the subject as stored in the database, mapped from [SubjectsTable.name].
     */
    var name by SubjectsTable.name

    /**
     * An optional description of the subject, mapped from [SubjectsTable.description].
     */
    var description by SubjectsTable.description

    /**
     * Converts this entity into a domain model [Subject].
     *
     * @return A [Subject] instance with the same data as this entity.
     */
    fun toDomain(): Subject = Subject(
        id = id.value,
        name = Name.of(name),
        description = description
    )

    /**
     * Companion object for [SubjectEntity] that provides access to the entity class
     * associated with [SubjectsTable].
     */
    companion object : LongEntityClass<SubjectEntity>(SubjectsTable)
}
