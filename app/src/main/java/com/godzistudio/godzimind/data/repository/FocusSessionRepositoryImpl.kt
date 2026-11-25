package com.godzistudio.godzimind.data.repository

import com.godzistudio.godzimind.data.local.dao.FocusSessionDao
import com.godzistudio.godzimind.data.mapper.toDomain
import com.godzistudio.godzimind.data.mapper.toEntity
import com.godzistudio.godzimind.domain.model.FocusSession
import com.godzistudio.godzimind.domain.repository.FocusSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusSessionRepositoryImpl @Inject constructor(
    private val focusSessionDao: FocusSessionDao
) : FocusSessionRepository {

    override fun obtenerSesiones(): Flow<List<FocusSession>> {
        return focusSessionDao.obtenerSesiones().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertarSesion(sesion: FocusSession) {
        focusSessionDao.insertarSesion(sesion.toEntity())
    }

    override suspend fun actualizarSesion(sesion: FocusSession) {
        focusSessionDao.actualizarSesion(sesion.toEntity())
    }

    override suspend fun eliminarSesion(sesion: FocusSession) {
        focusSessionDao.eliminarSesion(sesion.toEntity())
    }
}
