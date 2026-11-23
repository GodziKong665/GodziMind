package com.godzistudio.godzimind.data.mapper

import com.godzistudio.godzimind.data.local.entity.TaskEntity
import com.godzistudio.godzimind.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    done = done,
    createdAt = createdAt
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    done = done,
    createdAt = createdAt
)
