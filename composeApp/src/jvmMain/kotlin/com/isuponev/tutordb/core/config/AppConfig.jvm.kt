package com.isuponev.tutordb.core.config

import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.MainDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val currentDB = MutableStateFlow<Database>(MainDatabase)

/**
 * An extension property on [AppConfig.Platform] that provides read-only access to the current database.
 *
 * This property exposes the [currentDB] flow as a [StateFlow], allowing components to safely observe
 * the current database instance without being able to modify it. It enables platform-specific access
 * to the reactive database reference, supporting dependency observation and injection across the app.
 */
val AppConfig.Platform.currentDatabase: StateFlow<Database>
    get() = currentDB
