package com.blackwolf.solowolf.data.local

import androidx.room.*
import com.blackwolf.solowolf.models.Workout

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @Query("SELECT * FROM workouts WHERE difficulty = :difficulty")
    suspend fun getWorkoutsByDifficulty(difficulty: String): List<Workout>

    @Query("SELECT * FROM workouts WHERE isRecommended = 1")
    suspend fun getRecommendedWorkouts(): List<Workout>

    @Query("SELECT COUNT(*) FROM workouts")
    suspend fun getWorkoutCount(): Int
}
