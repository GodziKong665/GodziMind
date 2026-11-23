package com.godzistudio.godzimind.data.mapper

import com.godzistudio.godzimind.data.local.entity.StudyProfileEntity
import com.godzistudio.godzimind.domain.model.StudyProfile

fun StudyProfileEntity.toDomain(): StudyProfile = StudyProfile(
    id = id,
    studyMinutesPerDay = studyMinutesPerDay,
    academicLevel = academicLevel,
    age = age,
    occupation = occupation,
    hobbies = hobbies,
    musicGenre = musicGenre,
    updatedAt = updatedAt
)

fun StudyProfile.toEntity(): StudyProfileEntity = StudyProfileEntity(
    id = id,
    studyMinutesPerDay = studyMinutesPerDay,
    academicLevel = academicLevel,
    age = age,
    occupation = occupation,
    hobbies = hobbies,
    musicGenre = musicGenre,
    updatedAt = updatedAt
)
