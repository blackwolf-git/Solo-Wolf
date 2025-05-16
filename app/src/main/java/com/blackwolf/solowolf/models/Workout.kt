package com.blackwolf.solowolf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val difficulty: DifficultyLevel,
    val duration: Int, // in minutes
    val caloriesBurned: Int,
    val targetMuscles: List<MuscleGroup>,
    val equipmentRequired: Equipment,
    val steps: List<String>,
    val imageResId: Int,
    val videoUrl: String? = null,
    val isRecommended: Boolean = false,
    val xpReward: Int = 10
)

enum class DifficultyLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}

enum class MuscleGroup {
    CHEST, BACK, ARMS, SHOULDERS, LEGS, CORE, FULL_BODY
}

enum class Equipment {
    NONE, BODYWEIGHT, RESISTANCE_BANDS, DUMBBELLS, BARBELL, PULL_UP_BAR
}
