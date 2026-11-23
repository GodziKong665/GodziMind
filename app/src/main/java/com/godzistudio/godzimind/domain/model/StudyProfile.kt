package com.godzistudio.godzimind.domain.model

data class StudyProfile(
    val id: Int = 1,
    val studyMinutesPerDay: Int = 60,
    val academicLevel: String = "",
    val age: Int = 0,
    val occupation: String = "",
    val hobbies: String = "",
    val musicGenre: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
