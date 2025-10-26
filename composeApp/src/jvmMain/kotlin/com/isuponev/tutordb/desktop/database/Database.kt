package com.isuponev.tutordb.desktop.database

import org.jetbrains.exposed.v1.jdbc.Database as JDBCDatabase

interface Database {
    val jdbc: JDBCDatabase
}