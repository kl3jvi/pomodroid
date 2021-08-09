package com.kl3jvi.pomodroid.viewmodel

import androidx.lifecycle.*
import com.kl3jvi.pomodroid.model.database.TaskRepository
import com.kl3jvi.pomodroid.model.entities.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    fun insert(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    val allTaskList: LiveData<List<Task>> = repository.allTasksList.asLiveData()

    fun delete(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }
}


class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

