package com.example.dtapp

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.dtapp.database.AppDatabase
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.net.Client
import com.example.dtapp.net.ResponseHandler
import com.example.dtapp.net.transport.HabitInfoConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = databaseBuilder(this, AppDatabase::class.java, "HabitTracker")
            .build()

        val client = Client()
        val converter = HabitInfoConverter()
        val handler = ResponseHandler()
        habitsLoad(client, converter, handler)
    }

    private fun habitsLoad(client: Client, converter: HabitInfoConverter, handler: ResponseHandler) {
        applicationScope.launch(Dispatchers.IO) {
            val response = client.getHabits()
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

    companion object {
        lateinit var instance: App
            private set
    }
}
