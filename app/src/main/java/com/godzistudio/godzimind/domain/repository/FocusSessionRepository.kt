package com.godzistudio.godzimind.domain.repository

import com.godzistudio.godzimind.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusSessionRepository {

    fun obtenerSesiones(): Flow<List<FocusSession>>

    suspend fun insertarSesion(sesion: FocusSession)

    suspend fun actualizarSesion(sesion: FocusSession)

    suspend fun eliminarSesion(sesion: FocusSession)
}
