package com.godzistudio.godzimind.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.godzistudio.godzimind.data.local.dao.FocusSessionDao
import com.godzistudio.godzimind.data.local.dao.ProfileFieldDao
import com.godzistudio.godzimind.data.local.dao.StudyProfileDao
import com.godzistudio.godzimind.data.local.dao.TaskDao
import com.godzistudio.godzimind.data.local.entity.FocusSessionEntity
import com.godzistudio.godzimind.data.local.entity.ProfileFieldEntity
import com.godzistudio.godzimind.data.local.entity.StudyProfileEntity
import com.godzistudio.godzimind.data.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        FocusSessionEntity::class,
        StudyProfileEntity::class,
        ProfileFieldEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun studyProfileDao(): StudyProfileDao
    abstract fun profileFieldDao(): ProfileFieldDao
}
