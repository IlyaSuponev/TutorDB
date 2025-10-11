package com.isuponev.tutordb.core.config

@FunctionalInterface
interface Convertable<T> {
    fun convert(): T
}
