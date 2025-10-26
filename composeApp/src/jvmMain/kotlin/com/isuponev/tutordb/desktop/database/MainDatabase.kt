package com.isuponev.tutordb.desktop.database

import com.isuponev.tutordb.core.config.AppConfig
import java.io.File
import org.jetbrains.exposed.v1.jdbc.Database as JDBCDatabase

object MainDatabase : Database {
    private val db = JDBCDatabase.connect(
        "jdbc:h2:file:${File(AppConfig.appDirs.getUserDataDir(), "main")}",
        driver = "org.h2.Driver"
    )
    override val jdbc: JDBCDatabase
        get() = db

    init {
        val x = File(AppConfig.appDirs.getUserDataDir(), "main")
        println(x)
    }
}