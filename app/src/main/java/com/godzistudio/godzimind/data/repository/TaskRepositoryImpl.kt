package com.godzistudio.godzimind.data.repository

import com.godzistudio.godzimind.data.local.dao.TaskDao
import com.godzistudio.godzimind.data.mapper.toDomain
import com.godzistudio.godzimind.data.mapper.toEntity
import com.godzistudio.godzimind.domain.model.Task
import com.godzistudio.godzimind.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun obtenerTareas(): Flow<List<Task>> {
        return taskDao.obtenerTareas().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertarTarea(tarea: Task) {
        taskDao.insertarTarea(tarea.toEntity())
    }

    override suspend fun actualizarTarea(tarea: Task) {
        taskDao.actualizarTarea(tarea.toEntity())
    }

    override suspend fun eliminarTarea(tarea: Task) {
        taskDao.eliminarTarea(tarea.toEntity())
    }

    override suspend fun eliminarCompletadas() {
        taskDao.eliminarCompletadas()
    }
}
