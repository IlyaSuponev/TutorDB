package com.isuponev.tutordb.desktop.database.entities

import com.isuponev.tutordb.core.models.Subject
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import java.util.UUID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass

class SubjectEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SubjectEntity>(SubjectsTable)

    var name by SubjectsTable.name
    var description by SubjectsTable.description

    fun toDomain(): Subject = Subject(
        id = id.value,
        name = name,
        description = description
    )
}