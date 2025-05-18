package com.blackwolf.solowolf.db

import androidx.room.TypeConverter
import com.blackwolf.solowolf.models.MuscleGroup
import com.blackwolf.solowolf.models.Sport
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()

    // For Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // For List<String>
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return gson.toJson(list)
    }

    // For List<Sport>
    @TypeConverter
    fun fromSports(value: String?): List<Sport>? {
        if (value == null) return null
        val listType = object : TypeToken<List<Sport>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun sportsToString(list: List<Sport>?): String? {
        return gson.toJson(list)
    }

    // For List<MuscleGroup>
    @TypeConverter
    fun fromMuscleGroups(value: String?): List<MuscleGroup>? {
        if (value == null) return null
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun muscleGroupsToString(list: List<MuscleGroup>?): String? {
        return gson.toJson(list)
    }
}
