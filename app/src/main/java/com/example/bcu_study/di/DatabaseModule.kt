package com.example.bcu_study.di

import android.app.Application
import androidx.room.Room
import com.example.bcu_study.data.local.AppDatabase
import com.example.bcu_study.data.local.SessionDao
import com.example.bcu_study.data.local.SubjectDao
import com.example.bcu_study.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        applicaton: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(
                applicaton,
                AppDatabase::class.java,
                "BCU_STUDY.db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(database: AppDatabase): SubjectDao {
        return database.subjectDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideSessionDao(database: AppDatabase): SessionDao {
        return database.sessionDao()
    }

}