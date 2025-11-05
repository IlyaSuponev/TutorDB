package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.desktop.database.Database

/**
 * Base class for creating DAO instances.
 *
 * @param M The model type associated with the DAO.
 * @param I The insert data type associated with the DAO.
 * @param D The DAO type.
 */
abstract class DaoBuilder<M: Model, I: InsertData<M>, D: Dao<M, I>> {
    protected val instances = mutableMapOf<Database, D>()
    /**
     * Factory method to create or retrieve a [SubjectsDao] instance for the given [Database].
     *
     * @param db The [Database] instance to use.
     * @return The dao of type [D] for model instance associated with the provided database.
     */
    abstract fun new(db: Database): D
}