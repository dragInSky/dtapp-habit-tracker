package com.example.dtapp

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.dtapp.database.AppDatabase

class App : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = databaseBuilder(this, AppDatabase::class.java, "HabitTracker")
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
