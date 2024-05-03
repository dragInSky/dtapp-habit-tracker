package com.example.dtapp

import android.content.Context
import androidx.room.Room
import com.example.dtapp.database.AppDatabase
import com.example.dtapp.net.HttpClient
import com.example.dtapp.repositories.HabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitsModule {
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