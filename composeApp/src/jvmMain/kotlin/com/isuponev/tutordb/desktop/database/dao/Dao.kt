package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.tables.SubjectsTable
import com.isuponev.tutordb.desktop.utils.AppTransactionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Transaction

/**
 * A generic Data Access Object (DAO) interface for managing database operations related to a specific model.
 *
 * This interface defines common properties and behaviors that all DAO implementations should follow,
 * such as accessing the database and observing changes in data values.
 *
 * @param M The type of model this DAO is responsible for. It must conform to the [Model] interface.
 * @param I The type of data used for inserting new models into the database.
 * @param database The database instance used by this DAO for performing database operations.
 * @param tables The list of tables involved in the database operations.
 */
abstract class Dao<M : Model, I: InsertData<M>>(
    protected val database: Database,
    protected val tables: List<Table>,
) {
    private val _all = MutableStateFlow<List<M>>(emptyList())
    val all: StateFlow<List<M>>
        get() = _all

    init {
        loadAll()
    }

    /**
     * Creates a new model entity in the database.
     *
     * @param data The data to be inserted into the database.
     * @param onSuccess Callback invoked when the creation is successful.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun insert(
        data: I,
        onSuccess: (M) -> Unit,
        onError: (Throwable) -> Unit
    ) = AppTransactionManager.new(
        db = database,
        logTag = "${javaClass.name}.create",
        onSuccess = { model: M ->
            _all.update { it + model }
            onSuccess(model)
        },
        onError = onError,
        tables,
    ) {
        onInsert(data)
    }

    protected abstract fun Transaction.onInsert(data: I): M


    /**
     * Retrieves a models by its ID from the database.
     *
     * @param id The unique identifier of the subject.
     * @param onSuccess Callback invoked with the retrieved subject or null if not found.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun getById(
        id: Long,
        onSuccess: suspend (M?) -> Unit,
        onError: suspend (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "${javaClass.name}.getById",
            onSuccess = onSuccess,
            onError = onError,
            tables,
        ) {
            onGetById(id)
        }
    }

    protected abstract fun Transaction.onGetById(id: Long): M?

    /**
     * Updates an existing model in the database.
     *
     * @param model The updated model data.
     * @param onSuccess Callback invoked when the update is successful.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun update(
        model: M,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "${javaClass.name}.update",
            onSuccess = {
                _all.update {
                    it.map { sub ->
                        if (sub.id == model.id) {
                            model
                        } else {
                            sub
                        }
                    }
                }
                onSuccess()
            },
            onError = onError,
            tables
        ) {
            onUpdate(model)
        }
    }

    protected abstract fun Transaction.onUpdate(model: M)

    /**
     * Deletes a model from the database.
     *
     * @param model The model to be removed.
     * @param onError Callback invoked when an error occurs during the operation.
     */
    fun remove(
        model: M,
        onError: (Throwable) -> Unit
    ) {
        AppTransactionManager.new(
            db = database,
            logTag = "${javaClass.name}.create",
            onSuccess = {
                _all.update { it - model }
            },
            onError = onError,
            tables,
        ) {
            onRemove(model)
        }
    }

    protected abstract fun Transaction.onRemove(model: M)

    fun loadAll() {
        AppTransactionManager.new(
            db = database,
            logTag = "${javaClass.name}.loadSubjects",
            onSuccess = { models: List<M> ->
                _all.update { models }
            },
            onError = { throwable: Throwable ->
                AppConfig.logger.e(tag = "SubjectsDao", throwable = throwable) {
                    "Can't load subjects"
                }
            },
            SubjectsTable
        ) {
            onLoadAll()
        }
    }

    protected abstract fun Transaction.onLoadAll(): List<M>
}
