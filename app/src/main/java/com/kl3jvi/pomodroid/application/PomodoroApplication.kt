package com.kl3jvi.pomodroid.application

import android.app.Application
import com.kl3jvi.pomodroid.model.database.TaskDatabase
import com.kl3jvi.pomodroid.model.database.TaskRepository

class PomodoroApplication : Application() {
    private val database by lazy { TaskDatabase.getDatabase(this@PomodoroApplication) }
    val repository by lazy { TaskRepository(database.taskDao()) }

}
