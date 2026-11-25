package com.godzistudio.godzimind.domain.model

data class FocusSession(
    val id: Long = 0,
    val minutes: Int = 25,
    val completedAt: Long = System.currentTimeMillis()
)
