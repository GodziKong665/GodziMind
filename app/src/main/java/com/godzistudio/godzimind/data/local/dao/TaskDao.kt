package com.godzistudio.godzimind.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.godzistudio.godzimind.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertarTarea(tarea: TaskEntity)

    @Update
    suspend fun actualizarTarea(tarea: TaskEntity)

    @Delete
    suspend fun eliminarTarea(tarea: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun obtenerTarea(id: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks ORDER BY done ASC, createdAt DESC")
    fun obtenerTareas(): Flow<List<TaskEntity>>

    @Query("DELETE FROM tasks WHERE done = 1")
    suspend fun eliminarCompletadas()
}
