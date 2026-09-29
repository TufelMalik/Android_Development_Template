package com.techquantum.template.localdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techquantum.template.localdb.converter.Converters
import com.techquantum.template.localdb.database.dao.SampleDao
import com.techquantum.template.localdb.database.entity.SampleEntity

@Database(
    entities = [SampleEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}
