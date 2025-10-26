package com.isuponev.tutordb.desktop.database.dao

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.desktop.database.Database
import kotlinx.coroutines.flow.StateFlow

interface Dao<M: Model> {
    val database: Database
    val values: StateFlow<List<M>>
}