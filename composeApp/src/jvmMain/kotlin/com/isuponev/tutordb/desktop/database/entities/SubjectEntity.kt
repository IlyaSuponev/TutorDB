package com.isuponev.tutordb.desktop.database.entities

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import java.util.UUID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass

class SubjectEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SubjectEntity>(SubjectsTable)

    var name by SubjectsTable.name
    var description by SubjectsTable.description

    fun toDomain(): Subject = Subject(
        id = id.value,
        name = Name.of(name),
        description = description
    )
}