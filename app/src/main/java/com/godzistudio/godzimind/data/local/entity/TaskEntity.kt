package com.godzistudio.godzimind.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla "tasks" de Room. El id es autogenerado por SQLite.
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val done: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    init {
        require(title.length <= 120) {
            "El título no puede tener más de 120 caracteres"
        }
    }
}
