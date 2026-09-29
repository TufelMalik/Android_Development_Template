package com.techquantum.template.localdb.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class Converters {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return emptyList()
        return try {
            json.decodeFromString<List<String>>(value)
        } catch (_: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        if (list == null) return null
        return json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringMap(value: String?): Map<String, String>? {
        if (value.isNullOrEmpty()) return emptyMap()
        return try {
            json.decodeFromString<Map<String, String>>(value)
        } catch (_: Exception) {
            emptyMap()
        }
    }

    @TypeConverter
    fun toStringMap(map: Map<String, String>?): String? {
        if (map == null) return null
        return json.encodeToString(map)
    }
}
