package com.isuponev.tutordb.core.interfaces

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A generic interface for providing CRUD operations on model entities.
 *
 * `ModelProvider` defines a contract for data access layers to perform basic
 * create, read, update, and delete operations on model objects identified by
 * [Uuid]. This interface serves as a foundation for repository patterns and
 * data management throughout the application.
 *
 * ### Implementation Notes:
 * - All implementations should be thread-safe if used in concurrent environments
 * - Implementations may throw implementation-specific exceptions on failure
 * - The [ExperimentalUuidApi] opt-in is required due to UUID usage
 *
 * @param M The base model type that all managed entities must extend
 * @see Model for the base model interface that all entities should implement
 * @see Uuid for the identifier type used across all models
 */
@OptIn(ExperimentalUuidApi::class)
interface ModelProvider<M: Model> {

    /**
     * Retrieves a model entity by its unique identifier.
     *
     * This method attempts to find and return a model instance of the specified
     * type [M] with the given [id]. If no entity with the specified ID exists,
     * or if the entity exists but cannot be cast to type [M], returns `null`.
     *
     * @param id The unique identifier of the model to retrieve
     * @return The model instance of type [M] if found, `null` otherwise
     */
    fun getModel(id: Uuid): M?

    /**
     * Updates or creates a model entity with the specified identifier.
     *
     * This method stores the provided [model] instance, associating it with
     * the given [id]. If an entity with the specified [id] already exists,
     * it will be replaced with the new [model]. If no entity exists, a new
     * one will be created.
     *
     * @param M The specific model type to store (must extend [M])
     * @param id The unique identifier to associate with the model
     * @param model The model instance to store or update
     */
    fun setModel(id: Uuid, model: M)

    /**
     * Retrieves all model entities of the specified type.
     *
     * This method returns a list containing all available model instances
     * of type [M]. The returned list may be empty if no entities exist,
     * but should never be `null`.
     *
     * @return A list of all model instances of type [M], possibly empty
     */
    fun getAll(): List<M>

    /**
     * Inserts a new model entity with an auto-generated identifier.
     *
     * This method creates a new model entity from the provided [model].
     * The implementation is responsible for generating a unique identifier
     * for the new entity. This differs from [setModel] which requires
     * explicit ID specification.
     *
     * @param model The model instance to insert
     */
    fun insertModel(model: M)

    /**
     * Removes and returns a model entity by its unique identifier.
     *
     * This method finds the model entity with the specified [id], removes it
     * from storage, and returns the removed instance. If no entity with the
     * given [id] exists, the behavior is implementation-defined (may throw
     * an exception or return a default value).
     *
     * @param id The unique identifier of the model to remove
     * @return The removed model instance of type [M] or null if not exists
     */
    fun  removeModel(id: Uuid): M?
}