package com.example.bcu_study.di

import com.example.bcu_study.data.repository.SessionRepoImpl
import com.example.bcu_study.data.repository.SubjectRepoImpl
import com.example.bcu_study.data.repository.TaskRepoImpl
import com.example.bcu_study.domain.repository.SessionRepository
import com.example.bcu_study.domain.repository.SubjectRepository
import com.example.bcu_study.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepoImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindsTaskRepository(
        impl: TaskRepoImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindsSessionRepository(
        impl: SessionRepoImpl
    ): SessionRepository
}