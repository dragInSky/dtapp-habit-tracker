package com.example.dtapp

import android.content.Context
import androidx.room.Room
import com.example.dtapp.database.AppDatabase

object Model {
    fun init(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "HabitTracker")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var database: AppDatabase
}