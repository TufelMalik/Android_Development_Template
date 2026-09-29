package com.techquantum.template.localdb.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.techquantum.template.localdb.core.DatabaseCore

object DatabaseFactory {
    fun create(
        context: Context,
        migrations: List<Migration> = emptyList(),
    ): AppDatabase {
        val builder = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DatabaseCore.databaseName,
        )

        if (DatabaseCore.enableWal) {
            builder.setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
        }

        if (DatabaseCore.fallbackToDestructiveMigration) {
            builder.fallbackToDestructiveMigration()
        }

        if (migrations.isNotEmpty()) {
            builder.addMigrations(*migrations.toTypedArray())
        }

        return builder.build()
    }
}
