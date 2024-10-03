package com.example.bcu_study.presentation.es.subject

import androidx.compose.ui.graphics.Color
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Tasks

sealed class SubjectEvent {

    data object  UpdateSubject : SubjectEvent()

    data object  DeleteSubject : SubjectEvent()

    data object DeleteSession : SubjectEvent()

    data object UpdateProgess : SubjectEvent()

    data class  OnTaskIsCompleteChange(val tasks: Tasks) : SubjectEvent()

    data class  OnSubjectCardColorChange(val color: List<Color>) : SubjectEvent()

    data class  onSubjectNameChange(val name: String) : SubjectEvent()

    data class OnGoalStudyHoursChange(val hours: String) : SubjectEvent()

    data class OnDeleteSessionButtonClick (val session: Session) : SubjectEvent()

}