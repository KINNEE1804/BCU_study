package com.example.bcu_study.presentation.es.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bcu_study.R
import com.example.bcu_study.components.AddSubjectDialog
import com.example.bcu_study.components.CountCard
import com.example.bcu_study.components.DeleteDialog
import com.example.bcu_study.components.StudySessionsList
import com.example.bcu_study.components.SubjectCard
import com.example.bcu_study.components.tasksList
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.presentation.es.destinations.SessionScreenRouteDestination
import com.example.bcu_study.presentation.es.destinations.SubjectScreenRouteDestination
import com.example.bcu_study.presentation.es.destinations.TaskScreenRouteDestination
import com.example.bcu_study.presentation.es.subject.SubjectScreenNavArgs
import com.example.bcu_study.presentation.es.task.TaskScreenNavArgs
import com.example.bcu_study.session
import com.example.bcu_study.subject
import com.example.bcu_study.tasks
import com.example.bcu_study.util.Snackbarevent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Destination (start = true)
@Composable
fun DashboardScreenRoute (
    navigator: DestinationsNavigator
) {
    val viewModel: DashBoardVM = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSessions by viewModel.recentSessions.collectAsStateWithLifecycle()
    DashboardScreen(
        state = state,
        tasks = tasks,
        recentSessions = recentSessions,
        onEvent = viewModel::onEvent,
        snackbarevent = viewModel.snackbarEventFlow,
          onSubjectCardClick = { subjectId -> subjectId?.let {
              val navArg = SubjectScreenNavArgs(subjectId = subjectId)
              navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
          }},
          onTaskCardClick = { taskId ->
              val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
              navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
          },
          onStartSessionButtonClick = {
              navigator.navigate(SessionScreenRouteDestination())
          }
      )
}

@Composable
private fun  DashboardScreen(
    state: DashBoardState,
    tasks : List<Tasks>,
    recentSessions: List<Session>,
    onEvent: (DashboardEvent) -> Unit,
    snackbarevent: SharedFlow<Snackbarevent>,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionButtonClick: () -> Unit
): Unit {


    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    val  snackbarHostState = remember {SnackbarHostState() }

    //this
    LaunchedEffect(key1 = true) {
        snackbarevent.collectLatest { event ->
            when(event){
                is Snackbarevent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }

        }
    }

    AddSubjectDialog(isOpen = isAddSubjectDialogOpen,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it))},
        onGoalsHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it))},
        selectedColors = state.subjectCardColors,
        onColorChange = {onEvent(DashboardEvent.OnSubjectCardColorChange(it)) },
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen = false })

    DeleteDialog(isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session ?",
        bodyText = "Are you sure, you want to delete this session ? Your studied hours will be reduced" +
                "by this session time. This action can not undo ",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvent.DeleteSession)
            isDeleteSessionDialogOpen = false })

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = { DashboardScreenTopbar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHours.toString(),
                    goalHours = state.totalGoalStudyHours.toString()
                )

            }
            item {
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = state.subjects,
                    onAddIconClicked = {
                        isAddSubjectDialogOpen = true
                    },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
            item {
                Button(
                    onClick = onStartSessionButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")

                }
            }
            tasksList(
                sectionTile = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks,\n" +
                        "Click the + button in subject screen to add new tasks",
                tasks = tasks,
                onCheckBoxClick = {onEvent(DashboardEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessionsList(
                sectionTile = "RECENT STUDY SESSION",
                emptyListText = "You don't have any Recent study sessions,\n" +
                        "Start study session to begin recording your process",
                sessions = recentSessions,
                onDeleteIconClick = {
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopbar(): Unit {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "BCU_study",
            style = MaterialTheme.typography.headlineMedium
        )
    })

}

//addcountcard
@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingtext = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        //
        CountCard(
            modifier = Modifier.weight(1f),
            headingtext = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        //
        CountCard(
            modifier = Modifier.weight(1f),
            headingtext = "Goal Study Hours",
            count = goalHours
        )
    }

}

//
@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subject. ",
    onAddIconClicked: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
): Unit {
    Column(
        modifier = modifier,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Subject")
            }

        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_book), contentDescription = emptyListText
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors.map { Color(it) },
                    onClick = {onSubjectCardClick(subject.subjectId)})
            }
        }
    }

}