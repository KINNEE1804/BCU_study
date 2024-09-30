package com.example.bcu_study.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isComplete: Boolean,
    val taskSubjectId : Int,
    @PrimaryKey(autoGenerate = true)
    val tasksId: Int? = null
)
