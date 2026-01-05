package com.isuponev.tutordb.core.utils

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@JvmSuppressWildcards
object LocalDateNavType : NavType<LocalDate>(isNullableAllowed = false) {
    private val formatter = LocalDate.Formats.ISO
    override fun put(bundle: SavedState, key: String, value: LocalDate) {
        bundle.write { putString(key, value.format(formatter)) }
    }

    override fun get(bundle: SavedState, key: String): LocalDate {
        return bundle.read { parseValue(getString(key)) }
    }

    override fun parseValue(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }
}