package com.techquantum.template.localdb.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.techquantum.template.localdb.base.BaseDao
import com.techquantum.template.localdb.database.entity.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao : BaseDao<SampleEntity> {
    @Query("SELECT * FROM samples ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<SampleEntity>>

    @Query("SELECT * FROM samples WHERE id = :id")
    suspend fun getById(id: String): SampleEntity?

    @Query("DELETE FROM samples")
    suspend fun clearAll(): Int
}
