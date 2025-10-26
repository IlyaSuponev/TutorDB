package com.isuponev.tutordb.core.config

import com.isuponev.tutordb.desktop.database.Database
import com.isuponev.tutordb.desktop.database.MainDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val currentDB = MutableStateFlow<Database>(MainDatabase)
val AppConfig.Platform.currentDatabase: StateFlow<Database>
    get() = currentDB
