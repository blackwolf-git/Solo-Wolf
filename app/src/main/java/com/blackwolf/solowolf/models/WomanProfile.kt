package com.blackwolf.solowolf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "woman_profiles")
data class WomanProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val personalityType: WomanPersonalityType,
    val title: String,
    val description: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val approachStrategy: String,
    val buildingTrust: String,
    val makingHerFall: String,
    val psychologicalTips: String,
    val warning: String = "Use this knowledge ethically. Manipulation for harmful purposes is not condoned."
)

enum class WomanPersonalityType {
    COLD, CONSERVATIVE, PLAYFUL, ARROGANT, INTELLECTUAL, MYSTERIOUS, INDEPENDENT, TRADITIONAL
}
