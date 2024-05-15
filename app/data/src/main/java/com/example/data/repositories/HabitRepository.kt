package com.example.data.repositories

import android.util.Log
import com.example.data.database.AppDatabase
import com.example.data.net.HttpClient
import com.example.data.net.ResponseHandler
import com.example.data.net.transport.HabitInfoConverter
import com.example.domain.entities.HabitInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val database: AppDatabase
) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        val handler = ResponseHandler()
        val converter = HabitInfoConverter()

        repositoryScope.launch(Dispatchers.IO) {
            val response = httpClient.getHabits()
            val transportHabits = handler.habitsFromResponse(response)

            val oldHabits = database.habitDao().getAll()

            for (habit in oldHabits) {
                Log.e("dtapp:log", habit.toString())
            }

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

    fun habitDone(habit: HabitInfo, curDate: Int) {
        repositoryScope.launch(Dispatchers.IO) {
            httpClient.habitDone(curDate, habit.uid)

            if (habit.id == HabitInfo.DEFAULT_ID) {
                insert(habit)
            } else {
                update(habit)
            }
        }
    }

    fun addOrUpdateHabit(habit: HabitInfo) {
        repositoryScope.launch(Dispatchers.IO) {
            val uid = serverLoad(habit)
            val newHabit = habit.copy(uid = uid)

            if (habit.id == HabitInfo.DEFAULT_ID) {
                insert(newHabit)
            } else {
                update(newHabit)
            }
        }
    }

    private suspend fun serverLoad(habit: HabitInfo): String {
        val converter = HabitInfoConverter()
        val transportHabit = converter.toTransport(habit)

        val handler = ResponseHandler()
        val response = httpClient.addOrUpdateHabit(transportHabit)
        return handler.UIDFromResponse(response)
    }

    private suspend fun insert(habit: HabitInfo) {
        database.habitDao().insertAll(habit)
    }

    private suspend fun update(habit: HabitInfo) {
        database.habitDao().update(habit)
    }
}