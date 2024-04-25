package com.example.dtapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dtapp.App
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.net.Client
import com.example.dtapp.net.ResponseHandler
import com.example.dtapp.net.transport.HabitInfoConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetViewModel : ViewModel() {
    private val client = Client()
    private val converter = HabitInfoConverter()
    private val handler = ResponseHandler()

    fun onUploadClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val habits = App.instance.database.habitDao().getAll()

            for (habit in habits) {
                val transportHabit = converter.toTransport(habit)

                client.addOrUpdateHabit(transportHabit)
            }
        }
    }

    fun onLoadClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = client.getHabits()
            val transportHabits = handler.habitsFromResponse(response)

            val oldHabits = App.instance.database.habitDao().getAll()

            val habits: MutableList<HabitInfo> = mutableListOf()
            for (transportHabit in transportHabits) {
                val habit = converter.fromTransport(transportHabit)

                if (!oldHabits.contains(habit)) {
                    habits.add(habit)
                }
            }

            App.instance.database.habitDao().insertAll(*habits.toTypedArray())
        }
    }
}