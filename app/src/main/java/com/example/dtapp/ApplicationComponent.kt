package com.example.dtapp

import com.example.dtapp.repositories.HabitRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsModule::class, ContextModule::class])
interface ApplicationComponent {
    fun getHabitRepository(): HabitRepository
}