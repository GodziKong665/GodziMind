package com.godzistudio.godzimind.data.repository

import com.godzistudio.godzimind.data.local.dao.ProfileFieldDao
import com.godzistudio.godzimind.data.mapper.toDomain
import com.godzistudio.godzimind.data.mapper.toEntity
import com.godzistudio.godzimind.domain.model.ProfileField
import com.godzistudio.godzimind.domain.repository.ProfileFieldRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileFieldRepositoryImpl @Inject constructor(
    private val profileFieldDao: ProfileFieldDao
) : ProfileFieldRepository {

    override fun obtenerCampos(): Flow<List<ProfileField>> {
        return profileFieldDao.observarCampos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun buscarPorNombre(name: String): ProfileField? {
        return profileFieldDao.buscarPorNombre(name)?.toDomain()
    }

    override suspend fun insertarCampo(campo: ProfileField) {
        profileFieldDao.insertarCampo(campo.toEntity())
    }

    override suspend fun actualizarCampo(campo: ProfileField) {
        profileFieldDao.actualizarCampo(campo.toEntity())
    }

    override suspend fun eliminarCampo(campo: ProfileField) {
        profileFieldDao.eliminarCampo(campo.toEntity())
    }

    override suspend fun eliminarTodos() {
        profileFieldDao.eliminarTodos()
    }
}
