package com.kl3jvi.pomodroid.model.database

import androidx.annotation.WorkerThread
import com.kl3jvi.pomodroid.model.entities.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDAO) {

    @WorkerThread
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    val allTasksList: Flow<List<Task>> = taskDao.getAllTasks()
}