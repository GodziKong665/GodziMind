package com.godzistudio.godzimind.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val minutes: Int = 25,
    val completedAt: Long = System.currentTimeMillis()
)

