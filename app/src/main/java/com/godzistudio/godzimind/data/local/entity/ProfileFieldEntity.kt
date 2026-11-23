package com.godzistudio.godzimind.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Campos personalizados del perfil, por ejemplo "Color favorito" o "Deporte".
 * Al ser nombre/valor se pueden agregar campos sin cambiar el esquema.
 */
@Entity(tableName = "profile_fields")
data class ProfileFieldEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val value: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
