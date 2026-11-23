package com.godzistudio.godzimind.domain.model

/**
 * Modelo de dominio de una tarea. No conoce Room, Android ni Compose.
 */
data class Task(
    val id: Long = 0,
    val title: String = "",
    val done: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
