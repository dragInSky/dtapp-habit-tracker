package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.net.HttpClient
import com.example.data.repositories.HabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    fun provideHabitRepository(httpClient: HttpClient, database: AppDatabase): HabitRepository {
        return HabitRepository(httpClient, database)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient()
    }

    @Singleton
    @Provides
    fun provideAppDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "HabitTracker")
            .build()
    }
}