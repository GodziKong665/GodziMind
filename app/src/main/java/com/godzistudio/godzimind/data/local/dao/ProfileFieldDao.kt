package com.godzistudio.godzimind.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.godzistudio.godzimind.data.local.entity.ProfileFieldEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileFieldDao {

    @Query("SELECT * FROM profile_fields ORDER BY name COLLATE NOCASE ASC")
    fun observarCampos(): Flow<List<ProfileFieldEntity>>

    @Query("SELECT * FROM profile_fields WHERE name = :name COLLATE NOCASE LIMIT 1")
    suspend fun buscarPorNombre(name: String): ProfileFieldEntity?

    @Insert
    suspend fun insertarCampo(campo: ProfileFieldEntity)

    @Update
    suspend fun actualizarCampo(campo: ProfileFieldEntity)

    @Delete
    suspend fun eliminarCampo(campo: ProfileFieldEntity)

    @Query("DELETE FROM profile_fields")
    suspend fun eliminarTodos()
}
