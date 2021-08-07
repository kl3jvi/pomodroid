package com.kl3jvi.pomodroid.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kl3jvi.pomodroid.model.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM TASK_TABLE ORDER BY ID")
    fun getAllTasks(): Flow<List<Task>>

}