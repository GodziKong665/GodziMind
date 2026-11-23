package com.godzistudio.godzimind.domain.repository

import com.godzistudio.godzimind.domain.model.ProfileField
import kotlinx.coroutines.flow.Flow

interface ProfileFieldRepository {

    fun obtenerCampos(): Flow<List<ProfileField>>

    suspend fun buscarPorNombre(name: String): ProfileField?

    suspend fun insertarCampo(campo: ProfileField)

    suspend fun actualizarCampo(campo: ProfileField)

    suspend fun eliminarCampo(campo: ProfileField)

    suspend fun eliminarTodos()
}
