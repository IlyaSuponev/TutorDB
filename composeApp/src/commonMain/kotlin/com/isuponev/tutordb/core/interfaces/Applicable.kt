package com.isuponev.tutordb.core.interfaces

@FunctionalInterface
interface Applicable<T> {
    fun apply(value: T)
}
