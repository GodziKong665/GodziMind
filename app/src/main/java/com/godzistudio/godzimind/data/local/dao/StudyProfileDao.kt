package com.godzistudio.godzimind.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.godzistudio.godzimind.data.local.entity.StudyProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyProfileDao {

    @Query("SELECT * FROM study_profiles WHERE id = 1")
    fun observarPerfil(): Flow<StudyProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPerfil(perfil: StudyProfileEntity)

    @Update
    suspend fun actualizarPerfil(perfil: StudyProfileEntity)

    @Delete
    suspend fun eliminarPerfil(perfil: StudyProfileEntity)
}
