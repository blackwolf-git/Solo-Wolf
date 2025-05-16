package com.blackwolf.solowolf.data.local

import androidx.room.*
import com.blackwolf.solowolf.models.WomanProfile
import com.blackwolf.solowolf.models.WomanPersonalityType

@Dao
interface WomanProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWomanProfile(profile: WomanProfile)

    @Update
    suspend fun updateWomanProfile(profile: WomanProfile)

    @Delete
    suspend fun deleteWomanProfile(profile: WomanProfile)

    @Query("SELECT * FROM woman_profiles")
    suspend fun getAllWomanProfiles(): List<WomanProfile>

    @Query("SELECT * FROM woman_profiles WHERE personalityType = :type")
    suspend fun getWomanProfileByType(type: WomanPersonalityType): WomanProfile?

    @Query("SELECT COUNT(*) FROM woman_profiles")
    suspend fun getProfileCount(): Int
}
