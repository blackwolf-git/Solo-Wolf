package com.blackwolf.solowolf.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blackwolf.solowolf.db.Converters
import com.blackwolf.solowolf.models.User
import com.blackwolf.solowolf.models.Workout
import com.blackwolf.solowolf.models.WomanProfile

@Database(
    entities = [User::class, Workout::class, WomanProfile::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SoloWolfDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun womanProfileDao(): WomanProfileDao

    companion object {
        @Volatile
        private var INSTANCE: SoloWolfDatabase? = null

        fun getDatabase(context: Context): SoloWolfDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SoloWolfDatabase::class.java,
                    "solowolf_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
