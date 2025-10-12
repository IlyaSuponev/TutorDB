package com.isuponev.tutordb.core.utils

fun Boolean.Companion.and(default: Boolean, vararg conditions: Boolean): Boolean {
    if (conditions.isEmpty()) return default
    var result = default
    conditions.forEach {
        if (!result) return false
        result = it
    }
    return result
}
