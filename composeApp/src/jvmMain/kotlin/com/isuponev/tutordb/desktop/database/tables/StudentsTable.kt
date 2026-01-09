package com.isuponev.tutordb.desktop.database.tables

import com.isuponev.tutordb.desktop.database.name
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.money.compositeMoney

object StudentsTable : LongIdTable("students") {
    val name = name()
    val hourCost = compositeMoney(
        19,
        2,
        "hour_cost_amount",
        "hour_cost_currency"
    )
}
