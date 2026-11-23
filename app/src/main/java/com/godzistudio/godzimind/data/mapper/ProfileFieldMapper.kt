package com.godzistudio.godzimind.data.mapper

import com.godzistudio.godzimind.data.local.entity.ProfileFieldEntity
import com.godzistudio.godzimind.domain.model.ProfileField

fun ProfileFieldEntity.toDomain(): ProfileField = ProfileField(
    id = id,
    name = name,
    value = value,
    updatedAt = updatedAt
)

fun ProfileField.toEntity(): ProfileFieldEntity = ProfileFieldEntity(
    id = id,
    name = name,
    value = value,
    updatedAt = updatedAt
)
