package com.example.bcu_study.data.repository

import com.example.bcu_study.data.local.TaskDao
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.domain.repository.TaskRepository
import com.example.bcu_study.tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepoImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun upsertTask(tasks: Tasks) {
        taskDao.upsertTask(tasks)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Tasks? {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTasksForSubject(subjectInt: Int): Flow<List<Tasks>> {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasksForSubject(subjectInt: Int): Flow<List<Tasks>> {
        TODO("Not yet implemented")
    }

    override fun getAllUpcomingTasks(): Flow<List<Tasks>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.filter { it.isComplete.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    private fun sortTasks(tasks: List<Tasks>): List<Tasks>{
        return tasks.sortedWith(compareBy<Tasks>{it.dueDate}.thenByDescending { it.priority })
    }// sorting due date sorting descending and priority
}