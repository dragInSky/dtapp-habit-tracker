package com.example.dtapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dtapp.models.HabitInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habitInfo")
    suspend fun getAll(): List<HabitInfo>

    @Query("SELECT * FROM habitInfo WHERE id = :habitId")
    fun loadById(habitId: Int): Flow<HabitInfo>

    @Query("SELECT * FROM habitInfo WHERE type = :habitType")
    fun loadByType(habitType: Int): Flow<List<HabitInfo>>

    @Insert
    suspend fun insertAll(vararg habits: HabitInfo)

    @Update
    suspend fun update(habit: HabitInfo)

    @Delete
    suspend fun delete(habit: HabitInfo)
}