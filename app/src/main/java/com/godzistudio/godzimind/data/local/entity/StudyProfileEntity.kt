package com.godzistudio.godzimind.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Perfil único de estudio (id = 1). Guarda el ritmo de estudio, el nivel
 * académico y algunos gustos personales.
 */
@Entity(tableName = "study_profiles")
data class StudyProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val studyMinutesPerDay: Int = 60,
    val academicLevel: String = "",
    val age: Int = 0,
    val occupation: String = "",
    val hobbies: String = "",
    val musicGenre: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
