package com.example.bcu_study.presentation.es.subject

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.repository.SessionRepository
import com.example.bcu_study.domain.repository.SubjectRepository
import com.example.bcu_study.domain.repository.TaskRepository
import com.example.bcu_study.presentation.es.navArgs
import com.example.bcu_study.subject
import com.example.bcu_study.util.Snackbarevent
import com.example.bcu_study.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubjectVM @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val tasksRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val navArgs: SubjectScreenNavArgs = savedStateHandle.navArgs()

    private val  _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        tasksRepository.getUpcomingTasksForSubject(navArgs.subjectId),
        tasksRepository.getCompletedTasksForSubject(navArgs.subjectId),
        sessionRepository.getRencentTenSessionsForSubject(navArgs.subjectId),
        sessionRepository.getTotalSessionsDurationBySubject(navArgs.subjectId)
    ){ state, upcomingTasks, completeTask, recentSessions, totalSessionsDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,

            completedTasks = completeTask,
            recentSessions = recentSessions,
            studiedHours = totalSessionsDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )

    private val _snackbarEventFlow = MutableSharedFlow<Snackbarevent>()
    val snackbarEventFlow =  _snackbarEventFlow.asSharedFlow()

    init {
        fetchSubject()
    }

    fun onEvent(event: SubjectEvent) {
        when(event){

            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColors = event.color
                    )
                }
            }

            is SubjectEvent.onSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.name
                    )
                }
            }
            is SubjectEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(
                        goalStudyHours = event.hours
                    )
                }
            }

            is SubjectEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(

                    )
                }
            }
            is SubjectEvent.OnTaskIsCompleteChange -> updateSubject()
            SubjectEvent.DeleteSession -> {}
            SubjectEvent.DeleteSubject -> deleteSubject()
            SubjectEvent.UpdateProgess -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progess = (state.value.studiedHours/goalStudyHours).coerceIn(0f , 1f)
                    )
                }
            }
            SubjectEvent.UpdateSubject -> updateSubject()

        }
    }

    private fun updateSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        subjectId = state.value.currentSubjectId,
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colors = state.value.subjectCardColors.map { it.toArgb() }
                    )
                )
                _snackbarEventFlow.emit(
                    Snackbarevent.ShowSnackbar("Subject updated successfully")
                )
            }catch (e: Exception){
                _snackbarEventFlow.emit(
                    Snackbarevent.ShowSnackbar(
                        "Couldn't update Subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun fetchSubject (){
        viewModelScope.launch { 
            subjectRepository.getSubjectById(navArgs.subjectId)?.let { 
                subject ->
                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColors = subject.colors.map { Color(it) },
                        currentSubjectId = subject.subjectId
                    )
                }
            }
        }
    }

    private fun deleteSubject() {
        viewModelScope.launch {
            try {
                val currentSubjectId = state.value.currentSubjectId
                if (currentSubjectId != null) {
                    withContext(Dispatchers.IO) {
                        subjectRepository.deleteSubject(subjectId = currentSubjectId)
                    }
                    _snackbarEventFlow.emit(
                        Snackbarevent.ShowSnackbar(message = "Subject deleted successfully")
                    )
                    _snackbarEventFlow.emit(Snackbarevent.NavigateUp)
                } else {
                    _snackbarEventFlow.emit(
                        Snackbarevent.ShowSnackbar(message = "No Subject to delete")
                    )
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    Snackbarevent.ShowSnackbar(
                        message = "Couldn't delete subject. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}