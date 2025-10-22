package com.isuponev.tutordb.desktop.database.operations

import org.jetbrains.exposed.v1.core.Expression
import org.jetbrains.exposed.v1.core.Op

fun <T> Expression<T>.regex(
    pattern: Expression<String>,
    caseSensitive: Boolean
): RegexOp<T> = RegexOp(
    this,
    pattern,
    caseSensitive
)

infix fun <T> Expression<T>.regex(pattern: Expression<String>): RegexOp<T> = regex(
    pattern, caseSensitive = true
)
