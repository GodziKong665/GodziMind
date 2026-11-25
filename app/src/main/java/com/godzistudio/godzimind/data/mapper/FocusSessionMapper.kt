package com.godzistudio.godzimind.data.mapper

import com.godzistudio.godzimind.data.local.entity.FocusSessionEntity
import com.godzistudio.godzimind.domain.model.FocusSession

fun FocusSessionEntity.toDomain(): FocusSession = FocusSession(
    id = id,
    minutes = minutes,
    completedAt = completedAt
)

fun FocusSession.toEntity(): FocusSessionEntity = FocusSessionEntity(
    id = id,
    minutes = minutes,
    completedAt = completedAt
)
