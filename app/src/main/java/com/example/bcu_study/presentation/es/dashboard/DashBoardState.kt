package com.example.bcu_study.presentation.es.dashboard

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CardColors
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject

data class DashBoardState (
    val totalSubjectCount: Int = 0,
    val totalStudiedHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
) {
}