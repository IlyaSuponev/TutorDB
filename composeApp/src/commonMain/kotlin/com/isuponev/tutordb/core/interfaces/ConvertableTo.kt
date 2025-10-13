package com.isuponev.tutordb.core.interfaces

@FunctionalInterface
interface ConvertableTo<T> {
    fun convert(): T
}
