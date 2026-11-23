package com.godzistudio.godzimind.domain.model

data class ProfileField(
    val id: Long = 0,
    val name: String = "",
    val value: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
