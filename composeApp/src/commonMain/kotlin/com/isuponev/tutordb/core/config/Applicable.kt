package com.isuponev.tutordb.core.config

@FunctionalInterface
interface Applicable<T> {
    fun apply(value: T)
}
