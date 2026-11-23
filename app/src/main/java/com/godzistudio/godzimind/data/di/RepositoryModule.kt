package com.godzistudio.godzimind.data.di

import com.godzistudio.godzimind.data.repository.FocusSessionRepositoryImpl
import com.godzistudio.godzimind.data.repository.ProfileFieldRepositoryImpl
import com.godzistudio.godzimind.data.repository.StudyProfileRepositoryImpl
import com.godzistudio.godzimind.data.repository.TaskRepositoryImpl
import com.godzistudio.godzimind.domain.repository.FocusSessionRepository
import com.godzistudio.godzimind.domain.repository.ProfileFieldRepository
import com.godzistudio.godzimind.domain.repository.StudyProfileRepository
import com.godzistudio.godzimind.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindStudyProfileRepository(
        studyProfileRepositoryImpl: StudyProfileRepositoryImpl
    ): StudyProfileRepository

    @Binds
    @Singleton
    abstract fun bindProfileFieldRepository(
        profileFieldRepositoryImpl: ProfileFieldRepositoryImpl
    ): ProfileFieldRepository

    @Binds
    @Singleton
    abstract fun bindFocusSessionRepository(
        focusSessionRepositoryImpl: FocusSessionRepositoryImpl
    ): FocusSessionRepository
}
