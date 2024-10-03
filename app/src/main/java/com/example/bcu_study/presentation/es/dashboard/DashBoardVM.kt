package com.example.bcu_study.presentation.es.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.domain.repository.SessionRepository
import com.example.bcu_study.domain.repository.SubjectRepository
import com.example.bcu_study.domain.repository.TaskRepository
import com.example.bcu_study.util.Snackbarevent
import com.example.bcu_study.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardVM @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository
) : ViewModel(){
        private val _state = MutableStateFlow(DashBoardState())
        val state = combine(
            _state,
            subjectRepository.getTotalSubjectCount(),
            subjectRepository.getTotalGoalHours(),
            subjectRepository.getAllSubjects(),
            sessionRepository.getTotalSessionsDuration()
        ){state, subjectCount, goalHours, subjects, totalSessionDuration ->
            state.copy(
                totalSubjectCount = subjectCount,
                totalGoalStudyHours = goalHours,
                subjects = subjects,
                totalStudiedHours = totalSessionDuration.toHours()
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // stop updating after 5s
            initialValue = DashBoardState()
        )

        val tasks: StateFlow<List<Tasks>> = taskRepository.getAllUpcomingTasks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), // stop updating after 5s
                initialValue = emptyList()
            )

        val recentSessions: StateFlow<List<Session>> = sessionRepository.getRecentFiveSessions()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), // stop updating after 5s
                initialValue = emptyList()
            )

        private val _snackbarEventFlow = MutableSharedFlow<Snackbarevent>()
        val snackbarEventFlow =  _snackbarEventFlow.asSharedFlow()

    fun  onEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is DashboardEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.colors)
                }
            }

            is DashboardEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(session = event.session)
                }
            }

            DashboardEvent.SaveSubject -> saveSubject()
            DashboardEvent.DeleteSession -> TODO()

            is DashboardEvent.OnTaskIsCompleteChange -> TODO()

        }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull()?: 1f, // from string to float and esscape null by taking 1f
                        colors = state.value.subjectCardColors.map { it.toArgb() }, // color to int
                    )
                )
                _state.update {
                    it.copy(
                        subjectName = "",
                        goalStudyHours = "",
                        subjectCardColors = Subject.subjectCardColors.random()
                    )
                }
                _snackbarEventFlow.emit(
                    Snackbarevent.ShowSnackbar("Subject save successfully")
                )
            }catch (e: Exception) {
                _snackbarEventFlow.emit(
                    Snackbarevent.ShowSnackbar("Couldn't save subject.${e.message}",
                        SnackbarDuration.Long)
                )
            }
        }
    }

}