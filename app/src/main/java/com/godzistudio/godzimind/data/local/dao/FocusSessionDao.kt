package com.godzistudio.godzimind.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.godzistudio.godzimind.data.local.entity.FocusSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {

    @Insert
    suspend fun insertarSesion(sesion: FocusSessionEntity)

    @Update
    suspend fun actualizarSesion(sesion: FocusSessionEntity)

    @Delete
    suspend fun eliminarSesion(sesion: FocusSessionEntity)

    @Query("SELECT * FROM focus_sessions WHERE id = :id")
    fun obtenerSesion(id: Long): Flow<FocusSessionEntity?>

    @Query("SELECT * FROM focus_sessions ORDER BY completedAt DESC")
    fun obtenerSesiones(): Flow<List<FocusSessionEntity>>
}