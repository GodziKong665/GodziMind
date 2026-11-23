package com.godzistudio.godzimind.data.repository

import com.godzistudio.godzimind.data.local.dao.StudyProfileDao
import com.godzistudio.godzimind.data.local.entity.StudyProfileEntity
import com.godzistudio.godzimind.data.mapper.toDomain
import com.godzistudio.godzimind.data.mapper.toEntity
import com.godzistudio.godzimind.domain.model.StudyProfile
import com.godzistudio.godzimind.domain.repository.StudyProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyProfileRepositoryImpl @Inject constructor(
    private val studyProfileDao: StudyProfileDao
) : StudyProfileRepository {

    override fun obtenerPerfil(): Flow<StudyProfile?> {
        return studyProfileDao.observarPerfil().map { it?.toDomain() }
    }

    override suspend fun guardarPerfil(perfil: StudyProfile) {
        studyProfileDao.guardarPerfil(perfil.toEntity())
    }

    override suspend fun actualizarPerfil(perfil: StudyProfile) {
        studyProfileDao.actualizarPerfil(perfil.toEntity())
    }

    override suspend fun eliminarPerfil() {
        studyProfileDao.eliminarPerfil(StudyProfileEntity(id = 1))
    }
}
