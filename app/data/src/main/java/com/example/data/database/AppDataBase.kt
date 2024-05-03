package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}