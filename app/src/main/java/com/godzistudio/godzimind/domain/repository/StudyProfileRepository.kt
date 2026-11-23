package com.godzistudio.godzimind.domain.repository

import com.godzistudio.godzimind.domain.model.StudyProfile
import kotlinx.coroutines.flow.Flow

interface StudyProfileRepository {

    fun obtenerPerfil(): Flow<StudyProfile?>

    suspend fun guardarPerfil(perfil: StudyProfile)

    suspend fun actualizarPerfil(perfil: StudyProfile)

    suspend fun eliminarPerfil()
}
