package com.isuponev.tutordb.core.utils

fun <E> List<E?>.toNotNullable(): List<E> {
    val l = mutableListOf<E>()
    forEach { element ->
        if (element != null) l.addLast(element)
    }
    return l
}