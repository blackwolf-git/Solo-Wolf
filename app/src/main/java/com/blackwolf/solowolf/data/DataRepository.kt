package com.blackwolf.solowolf.data

import com.blackwolf.solowolf.data.local.LocalDataSource
import com.blackwolf.solowolf.models.Task
import com.blackwolf.solowolf.models.User
import com.blackwolf.solowolf.models.Workout
import com.blackwolf.solowolf.models.WomanProfile

class DataRepository(private val localDataSource: LocalDataSource) {
    
    // User operations
    suspend fun insertUser(user: User) = localDataSource.insertUser(user)
    suspend fun getCurrentUser(): User = localDataSource.getCurrentUser()
    suspend fun updateUser(user: User) = localDataSource.updateUser(user)
    
    // Workout operations
    suspend fun getAllWorkouts(): List<Workout> = localDataSource.getAllWorkouts()
    suspend fun getWorkoutsByDifficulty(difficulty: String): List<Workout> = 
        localDataSource.getWorkoutsByDifficulty(difficulty)
    suspend fun getRecommendedWorkouts(): List<Workout> = localDataSource.getRecommendedWorkouts()
    
    // Woman profile operations
    suspend fun getAllWomanProfiles(): List<WomanProfile> = localDataSource.getAllWomanProfiles()
    suspend fun getWomanProfileByType(type: String): WomanProfile? = 
        localDataSource.getWomanProfileByType(type)
    
    // Task operations
    suspend fun getDailyTasks(): List<Task> = localDataSource.getDailyTasks()
    suspend fun completeTask(task: Task) = localDataSource.completeTask(task)
    
    // Initial data population
    suspend fun populateInitialData() {
        if (localDataSource.isDatabaseEmpty()) {
            localDataSource.insertInitialWorkouts()
            localDataSource.insertInitialWomanProfiles()
        }
    }
}
