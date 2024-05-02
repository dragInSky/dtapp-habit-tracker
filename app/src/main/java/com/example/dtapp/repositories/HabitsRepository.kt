package com.example.dtapp.repositories

import com.example.dtapp.App
import com.example.dtapp.entities.HabitInfo
import com.example.dtapp.net.Client
import com.example.dtapp.net.ResponseHandler
import com.example.dtapp.net.transport.HabitInfoConverter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HabitsRepository {
    private val client = Client()
    private val dao = App.instance.database.habitDao()

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        val handler = ResponseHandler()
        val converter = HabitInfoConverter()

        repositoryScope.launch(Dispatchers.IO) {
            val response = client.getHabits()
            val transportHabits = handler.habitsFromResponse(response)

            val oldHabits = dao.getAll()

            val habits: MutableList<HabitInfo> = mutableListOf()
            for (transportHabit in transportHabits) {
                val habit = converter.fromTransport(transportHabit)

                if (!oldHabits.contains(habit)) {
                    habits.add(habit)
                }
            }

            dao.insertAll(*habits.toTypedArray())
        }
    }

    fun loadById(habitId: Int): Flow<HabitInfo> {
        return dao.loadById(habitId)
    }

    fun loadByType(habitType: Int): Flow<List<HabitInfo>> {
        return dao.loadByType(habitType)
    }

    suspend fun insert(habit: HabitInfo) {
        dao.insertAll(habit)
    }

    suspend fun update(habit: HabitInfo) {
        dao.insertAll(habit)
    }

    suspend fun addOrUpdateHabit(habit: HabitInfo): HttpResponse {
        val converter = HabitInfoConverter()
        val transportHabit = converter.toTransport(habit)

        return client.addOrUpdateHabit(transportHabit)
    }
}