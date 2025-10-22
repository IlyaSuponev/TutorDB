package com.isuponev.tutordb.desktop.database.operations

import org.jetbrains.exposed.v1.core.Expression
import org.jetbrains.exposed.v1.core.LiteralOp
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.QueryBuilder
import org.jetbrains.exposed.v1.core.StringColumnType
import org.jetbrains.exposed.v1.core.append
import org.jetbrains.exposed.v1.core.stringParam
import org.jetbrains.exposed.v1.core.vendors.currentDialect

class RegexOp<T>(
    val verifiable: Expression<T>,
    val pattern: Expression<String>,
    val caseSensitive: Boolean
) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        queryBuilder {
            append("REGEXP_LIKE(", verifiable, ", ", pattern, ", ")
            if (caseSensitive) {
                append("'c'")
            } else {
                append("'i'")
            }
            append(")")
        }
    }
}
