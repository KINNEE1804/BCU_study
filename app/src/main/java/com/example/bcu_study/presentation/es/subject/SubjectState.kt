package com.example.bcu_study.presentation.es.subject

import androidx.compose.ui.graphics.Color
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks

data class SubjectState (
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val studiedHours: Float = 0f,
    val progess: Float = 0f,
    val recentSessions: List<Session> = emptyList(),
    val upcomingTasks: List<Tasks> = emptyList(),
    val completedTasks: List<Tasks> = emptyList(),
    val session: Session? = null

) {
}