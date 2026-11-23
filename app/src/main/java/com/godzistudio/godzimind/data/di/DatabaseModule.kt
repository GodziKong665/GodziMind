package com.godzistudio.godzimind.data.di

import android.content.Context
import androidx.room.Room
import com.godzistudio.godzimind.data.local.AppDatabase
import com.godzistudio.godzimind.data.local.MIGRATION_4_5
import com.godzistudio.godzimind.data.local.dao.FocusSessionDao
import com.godzistudio.godzimind.data.local.dao.ProfileFieldDao
import com.godzistudio.godzimind.data.local.dao.StudyProfileDao
import com.godzistudio.godzimind.data.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "godzimind.db"
            )
                .addMigrations(MIGRATION_4_5)
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideFocusSessionDao(database: AppDatabase): FocusSessionDao {
        return database.focusSessionDao()
    }

    @Provides
    fun provideStudyProfileDao(database: AppDatabase): StudyProfileDao {
        return database.studyProfileDao()
    }

    @Provides
    fun provideProfileFieldDao(database: AppDatabase): ProfileFieldDao {
        return database.profileFieldDao()
    }
}
