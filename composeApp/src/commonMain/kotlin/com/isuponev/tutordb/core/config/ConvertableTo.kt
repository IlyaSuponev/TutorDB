package com.isuponev.tutordb.core.config

@FunctionalInterface
interface ConvertableTo<T> {
    fun convert(): T
}
