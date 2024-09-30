package com.example.bcu_study.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.bcu_study.domain.model.Tasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(tasks: Tasks)

    @Query("DELETE FROM Tasks WHERE tasksId = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM Tasks WHERE taskSubjectId = :subjectId")
    suspend fun deleteTasksBySubjectId(subjectId: Int)

    @Query("SELECT * FROM Tasks WHERE tasksId =:taskId")
    suspend fun getTaskById(taskId: Int): Tasks?

    @Query("SELECT * FROM Tasks WHERE taskSubjectId = :subjectId")
    fun  getTasksForSubject(subjectId: Int): Flow<List<Tasks>>

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): Flow<List<Tasks>>
}