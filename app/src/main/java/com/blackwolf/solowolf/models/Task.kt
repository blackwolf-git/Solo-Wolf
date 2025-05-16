package com.blackwolf.solowolf.models

data class Task(
    val id: Int,
    val title: String,
    val xpReward: Int,
    val isCompleted: Boolean
)
