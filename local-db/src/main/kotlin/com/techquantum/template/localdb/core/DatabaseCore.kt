package com.techquantum.template.localdb.core

/**
 * DatabaseCore is the single Core file for the local-db module.
 * Holds the database file name, version number, WAL mode, schema export,
 * and migration configuration.
 */
object DatabaseCore {
    const val databaseName: String = "app_database.db"
    const val databaseVersion: Int = 1
    const val exportSchema: Boolean = false
    const val enableWal: Boolean = true
    const val fallbackToDestructiveMigration: Boolean = false
}
