package com.example.dtapp.repositories

import com.example.dtapp.database.AppDatabase
import com.example.dtapp.entities.HabitInfo
import com.example.dtapp.net.HttpClient
import com.example.dtapp.net.ResponseHandler
import com.example.dtapp.net.transport.HabitInfoConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitRepository @Inject constructor(private val httpClient: HttpClient, private val database: AppDatabase) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        val handler = ResponseHandler()
        val converter = HabitInfoConverter()

        repositoryScope.launch(Dispatchers.IO) {
            val response = httpClient.getHabits()
            val transportHabits = handler.habitsFromResponse(response)

            val oldHabits = database.habitDao().getAll()

            val habits: MutableList<HabitInfo> = mutableListOf()
            for (transportHabit in transportHabits) {
                val habit = converter.fromTransport(transportHabit)

                if (!oldHabits.contains(habit)) {
                    habits.add(habit)
                }
            }

            database.habitDao().insertAll(*habits.toTypedArray())
        }
    }

    fun loadById(habitId: Int): Flow<HabitInfo> {
        return database.habitDao().loadById(habitId)
    }

    fun loadByType(habitType: Int): Flow<List<HabitInfo>> {
        return database.habitDao().loadByType(habitType)
    }

    fun addOrUpdateHabit(habit: HabitInfo) {
        repositoryScope.launch(Dispatchers.IO) {
            if (habit.id == HabitInfo.DEFAULT_ID) {
                insert(habit)
            } else {
                update(habit)
            }

            val converter = HabitInfoConverter()
            val transportHabit = converter.toTransport(habit)

            httpClient.addOrUpdateHabit(transportHabit)
        }
    }

    private suspend fun insert(habit: HabitInfo) {
        database.habitDao().insertAll(habit)
    }

    private suspend fun update(habit: HabitInfo) {
        database.habitDao().update(habit)
    }
}