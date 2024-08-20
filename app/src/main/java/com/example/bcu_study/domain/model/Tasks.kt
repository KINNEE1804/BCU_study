package com.example.bcu_study.domain.model

data class Tasks(
    val title: String,
                 val description: String,
                 val dueDate: Long,
                 val priority: Int,
                 val relatedToSubject: String,
                 val isComplete: Boolean
)
