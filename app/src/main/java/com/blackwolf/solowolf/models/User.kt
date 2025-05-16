package com.blackwolf.solowolf.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val nickname: String,
    val age: Int,
    val height: Int, // in cm
    val weight: Int, // in kg
    val personalityType: PersonalityType,
    val sports: List<Sport>,
    val registrationDate: Date = Date(),
    val level: Int = 1,
    val xp: Int = 0,
    val physicalEnergy: Int = 100,
    val mentalEnergy: Int = 100,
    val auraPoints: Int = 50,
    val dailyTasksCompleted: Int = 0,
    val streak: Int = 0
) {
    val bmi: Double get() = weight.toDouble() / ((height.toDouble() / 100) * (height.toDouble() / 100))
}

enum class PersonalityType {
    LEADER, CALM, OPEN_MINDED, ADVENTUROUS, ANALYTICAL, CHARISMATIC
}

enum class Sport {
    BOXING, MMA, FULL_CONTACT, CALISTHENICS
}
