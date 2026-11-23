package com.godzistudio.godzimind.domain.repository

import com.godzistudio.godzimind.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Contrato de persistencia de tareas. La capa de UI depende de esta interfaz,
 * no de Room. La implementación vive en data/repository.
 */
interface TaskRepository {

    fun obtenerTareas(): Flow<List<Task>>

    suspend fun insertarTarea(tarea: Task)

    suspend fun actualizarTarea(tarea: Task)

    suspend fun eliminarTarea(tarea: Task)

    suspend fun eliminarCompletadas()
}
