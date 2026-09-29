package com.techquantum.template.localdb.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "samples")
data class SampleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
