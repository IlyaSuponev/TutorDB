package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.desktop.database.Database
import kotlinx.coroutines.flow.StateFlow

/**
 * A generic Data Access Object (DAO) interface for managing database operations related to a specific model.
 *
 * This interface defines common properties and behaviors that all DAO implementations should follow,
 * such as accessing the database and observing changes in data values.
 *
 * @param M The type of model this DAO is responsible for. It must conform to the [Model] interface.
 */
interface Dao<M : Model> {
    /**
     * The database instance used by this DAO for performing database operations.
     */
    val database: Database

    /**
     * A [StateFlow] that emits a list of model instances representing the current state of the data.
     *
     * This flow can be observed to react to changes in the data, such as insertions, updates, or deletions.
     */
    val values: StateFlow<List<M>>
}
