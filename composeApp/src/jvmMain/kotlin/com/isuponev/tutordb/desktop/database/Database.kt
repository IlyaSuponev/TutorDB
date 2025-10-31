package com.isuponev.tutordb.desktop.database

import org.jetbrains.exposed.v1.jdbc.Database as JDBCDatabase

/**
 * Interface for database in application.
 */
interface Database {

    /**
     * Connection to JDBC database.
     */
    val jdbc: JDBCDatabase
}
