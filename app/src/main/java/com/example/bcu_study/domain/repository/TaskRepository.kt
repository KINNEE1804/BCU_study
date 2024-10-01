package com.example.bcu_study.domain.repository

import android.adservices.adid.AdId
import com.example.bcu_study.domain.model.Tasks
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(tasks: Tasks)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTaskById(taskId: Int): Tasks?

    fun getUpcomingTasksForSubject(subjectInt: Int): Flow<List<Tasks>>

    fun getCompletedTasksForSubject(subjectInt: Int): Flow<List<Tasks>>

    fun getAllUpcomingTasks(): Flow<List<Tasks>>
}