package com.blackwolf.solowolf.data.local

import com.blackwolf.solowolf.models.Task
import com.blackwolf.solowolf.models.User
import com.blackwolf.solowolf.models.Workout
import com.blackwolf.solowolf.models.WomanProfile
import com.blackwolf.solowolf.models.DifficultyLevel
import com.blackwolf.solowolf.models.Equipment
import com.blackwolf.solowolf.models.MuscleGroup
import com.blackwolf.solowolf.models.WomanPersonalityType

class LocalDataSource(
    private val userDao: UserDao,
    private val workoutDao: WorkoutDao,
    private val womanProfileDao: WomanProfileDao
) {
    
    // User operations
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun getCurrentUser(): User = userDao.getCurrentUser()
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    // Workout operations
    suspend fun getAllWorkouts(): List<Workout> = workoutDao.getAllWorkouts()
    suspend fun getWorkoutsByDifficulty(difficulty: String): List<Workout> = 
        workoutDao.getWorkoutsByDifficulty(difficulty)
    suspend fun getRecommendedWorkouts(): List<Workout> = workoutDao.getRecommendedWorkouts()
    suspend fun insertWorkout(workout: Workout) = workoutDao.insertWorkout(workout)
    
    // Woman profile operations
    suspend fun getAllWomanProfiles(): List<WomanProfile> = womanProfileDao.getAllWomanProfiles()
    suspend fun getWomanProfileByType(type: String): WomanProfile? = 
        womanProfileDao.getWomanProfileByType(WomanPersonalityType.valueOf(type))
    suspend fun insertWomanProfile(profile: WomanProfile) = womanProfileDao.insertWomanProfile(profile)
    
    // Task operations
    suspend fun getDailyTasks(): List<Task> {
        // In a real app, this would come from a database
        return listOf(
            Task(1, "Complete morning workout", 50, false),
            Task(2, "Meditate for 10 minutes", 30, false),
            Task(3, "Read 10 pages of a book", 20, false),
            Task(4, "Practice aura exercises", 40, false)
        )
    }
    
    suspend fun completeTask(task: Task) {
        // In a real app, this would update the database
        val user = getCurrentUser()
        val updatedUser = user.copy(xp = user.xp + task.xpReward)
        updateUser(updatedUser)
    }
    
    // Initial data population
    suspend fun isDatabaseEmpty(): Boolean {
        return workoutDao.getWorkoutCount() == 0 && womanProfileDao.getProfileCount() == 0
    }
    
    suspend fun insertInitialWorkouts() {
        val workouts = listOf(
            Workout(
                title = "Beginner Full Body Workout",
                description = "A full body workout for beginners with no equipment needed",
                difficulty = DifficultyLevel.BEGINNER,
                duration = 20,
                caloriesBurned = 200,
                targetMuscles = listOf(MuscleGroup.FULL_BODY),
                equipmentRequired = Equipment.NONE,
                steps = listOf(
                    "Warm up with 5 minutes of light cardio",
                    "Perform 3 sets of 10 push-ups",
                    "Perform 3 sets of 15 bodyweight squats",
                    "Perform 3 sets of 10 lunges per leg",
                    "Perform 3 sets of 30-second planks",
                    "Cool down with stretching"
                ),
                imageResId = R.drawable.workout_beginner,
                xpReward = 50
            ),
            // Add more workouts here...
            Workout(
                title = "Advanced MMA Conditioning",
                description = "High-intensity workout for MMA fighters",
                difficulty = DifficultyLevel.ADVANCED,
                duration = 45,
                caloriesBurned = 500,
                targetMuscles = listOf(MuscleGroup.FULL_BODY),
                equipmentRequired = Equipment.NONE,
                steps = listOf(
                    "5 rounds of shadow boxing (3 minutes each)",
                    "5 rounds of sprawls (10 reps per round)",
                    "5 rounds of burpees (10 reps per round)",
                    "5 rounds of mountain climbers (30 seconds each)",
                    "Cool down with stretching"
                ),
                imageResId = R.drawable.workout_mma,
                xpReward = 100
            )
        )
        
        workouts.forEach { workoutDao.insertWorkout(it) }
    }
    
    suspend fun insertInitialWomanProfiles() {
        val profiles = listOf(
            WomanProfile(
                personalityType = WomanPersonalityType.COLD,
                title = "The Ice Queen",
                description = "She appears distant and unapproachable, but has deep emotions underneath.",
                strengths = listOf("Loyal once committed", "Intelligent", "Strong principles"),
                weaknesses = listOf("Can be emotionally closed off", "May seem arrogant", "Slow to trust"),
                approachStrategy = "Show confidence but don't be aggressive. Demonstrate your value through actions, not words.",
                buildingTrust = "Be consistent and reliable. She'll test you subtly to see if you're genuine.",
                makingHerFall = "Once she starts opening up, be patient. Show her your vulnerable side to encourage her to do the same.",
                psychologicalTips = "Use the 'hot-cold' technique - alternate between showing interest and pulling back slightly to create intrigue."
            ),
            WomanProfile(
                personalityType = WomanPersonalityType.CONSERVATIVE,
                title = "The Traditionalist",
                description = "Values family, tradition, and has strong moral principles.",
                strengths = listOf("Loyal", "Family-oriented", "Stable"),
                weaknesses = listOf("May be resistant to change", "Can be judgmental", "Might have rigid expectations"),
                approachStrategy = "Show respect for traditions and family values. Demonstrate your responsible side.",
                buildingTrust = "Introduce her to your family or talk about them positively. Show your long-term planning abilities.",
                makingHerFall = "Prove you're marriage material through consistent actions. Traditional romantic gestures work well.",
                psychologicalTips = "Use the 'foot-in-the-door' technique - start with small commitments that align with her values, then gradually build up."
            ),
            // Add more profiles here...
            WomanProfile(
                personalityType = WomanPersonalityType.PLAYFUL,
                title = "The Free Spirit",
                description = "Fun-loving, spontaneous, and always up for adventure.",
                strengths = listOf("Great sense of humor", "Spontaneous", "Easygoing"),
                weaknesses = listOf("Can be flaky", "May avoid serious topics", "Might have commitment issues"),
                approachStrategy = "Show your fun side but also demonstrate depth. Keep things light but interesting.",
                buildingTrust = "Participate in her adventures but also create your own to invite her to. Show you can match her energy.",
                makingHerFall = "Keep surprising her with new experiences. Once comfortable, gradually introduce more intimate moments.",
                psychologicalTips = "Use the 'reward unpredictability' principle - alternate between different types of fun activities to keep her engaged."
            )
        )
        
        profiles.forEach { womanProfileDao.insertWomanProfile(it) }
    }
}
