package com.godzistudio.godzimind.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godzistudio.godzimind.domain.model.Task
import com.godzistudio.godzimind.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel del patrón UDF: expone un StateFlow de solo lectura y recibe
 * eventos de la UI. Toda la persistencia pasa por TaskRepository.
 */
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.obtenerTareas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTask(rawTitle: String) {
        val title = rawTitle.trim()
        if (title.isEmpty()) return
        viewModelScope.launch {
            repository.insertarTarea(
                Task(
                    title = title,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            repository.actualizarTarea(task.copy(done = !task.done))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.eliminarTarea(task)
        }
    }

    fun clearCompleted() {
        viewModelScope.launch {
            repository.eliminarCompletadas()
        }
    }
}
