package com.example.dtapp.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    var selectedPriority = mutableStateOf(Priority.MEDIUM.getName())
    var selectedType = mutableStateOf(Type.GOOD.getName())
    var name = mutableStateOf("")
    var description = mutableStateOf("")
    var times = mutableStateOf("")
    var period = mutableStateOf("")

    fun habitInit(id: Int) {
        viewModelScope.launch {
            getHabitById(id)
                .asFlow()
                .collect {
                    selectedPriority.value = it.priority.getName()
                    selectedType.value = it.type.getName()
                    name.value = it.name
                    description.value = it.description
                    times.value = it.times
                    period.value = it.period
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClicked(onClick: () -> Unit, id: Int) {
        val habit = HabitInfo.habitInit(
            selectedPriority = selectedPriority.value,
            selectedType = selectedType.value,
            name = name.value,
            description = description.value,
            times = times.value,
            period = period.value,
            id = id
        )

        addOrUpdate(id, habit)

        onClick()
    }

    private fun getHabitById(id: Int): LiveData<HabitInfo> {
        return Model.database.habitDao().loadById(id)
    }

    private fun addOrUpdate(id: Int, habit: HabitInfo) {
        if (id == HabitInfo.DEFAULT_ID) {
            Model.database.habitDao().insertAll(habit)
        } else {
            Model.database.habitDao().update(habit)
        }
    }
}