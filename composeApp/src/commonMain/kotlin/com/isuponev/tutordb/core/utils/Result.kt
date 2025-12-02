package com.isuponev.tutordb.core.utils

import com.isuponev.tutordb.core.utils.Result.Failure
import java.io.Serializable

sealed class Result<out T> : Serializable {
    data class Success<out T>(val value: T) : Result<T>()

    data class Failure<out T>(val message: AppError) : Result<T>()

    data class Error<out T>(val exception: Throwable) : Result<T>()

    fun onSuccess(consumer: (T) -> Unit): Result<T> {
        if (this is Success) consumer(value)
        return this
    }
    fun onFailure(consumer: (AppError) -> Unit): Result<T> {
        if (this is Failure) consumer(message)
        return this
    }
    fun onError(consumer: (Throwable) -> Unit): Result<T> {
        if (this is Error) consumer(exception)
        return this
    }

    companion object {
        fun <T> success(value: T): Result<T> = Success(value)

        fun <T> failure(error: AppError): Result<T> = Failure(error)

        fun <T> error(exception: Throwable): Result<T> = Error(exception)
    }
}

sealed class AppError(
    open val message: String,
    open val details: Map<String, Any?> = emptyMap()
) : Serializable {

    data class ValidationError(
        val field: String,
        val reason: String,
        override val message: String = "Validation failed",
        override val details: Map<String, Any> = emptyMap()
    ) : AppError(message, details)

    data class InvalidStateError(
        val field: String,
        val reason: String,
        override val message: String = "Object in invalid state",
        override val details: Map<String, Any> = emptyMap()
    ) : AppError(message, details)

    fun onValidationError(consumer: (ValidationError) -> Unit): AppError {
        if (this is ValidationError) consumer(this)
        return this
    }

    fun onInvalidStateError(consumer: (InvalidStateError) -> Unit): AppError {
        if (this is InvalidStateError) consumer(this)
        return this
    }
}
