package com.blackwolf.solowolf

import android.app.Application
import com.blackwolf.solowolf.data.DataRepository
import com.blackwolf.solowolf.data.local.LocalDataSource
import com.blackwolf.solowolf.data.local.SoloWolfDatabase

class SoloWolfApplication : Application() {
    val database by lazy { SoloWolfDatabase.getDatabase(this) }
    val localDataSource by lazy { LocalDataSource(database.userDao(), database.workoutDao(), database.womanProfileDao()) }
    val repository by lazy { DataRepository(localDataSource) }

    override fun onCreate() {
        super.onCreate()
        // Initialize any global components here
    }
}
