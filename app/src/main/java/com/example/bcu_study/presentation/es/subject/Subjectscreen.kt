package com.example.bcu_study.presentation.es.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bcu_study.components.AddSubjectDialog
import com.example.bcu_study.components.CountCard
import com.example.bcu_study.components.DeleteDialog
import com.example.bcu_study.components.StudySessionsList
import com.example.bcu_study.components.tasksList
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.presentation.es.destinations.TaskScreenRouteDestination
import com.example.bcu_study.presentation.es.task.TaskScreenNavArgs
import com.example.bcu_study.session
import com.example.bcu_study.tasks
import com.example.bcu_study.util.Snackbarevent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination (navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun  SubjectScreenRoute (
    navigator: DestinationsNavigator
) {
    val viewModel: SubjectVM = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SubjectScreen(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarevent = viewModel.snackbarEventFlow,
        onBackButtonClick = { navigator.navigateUp() },
        onAddTaskButtonClick = {
            val navArg = TaskScreenNavArgs(taskId = null, subjectId = -1)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg)) },
        onTaskCardClick = {taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    state: SubjectState,
    onEvent: (SubjectEvent) -> Unit,
    snackbarevent: SharedFlow<Snackbarevent>,
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick : () -> Unit,
    onTaskCardClick: (Int?) -> Unit
) {

    val listSate = rememberLazyListState()
    val isFABExpanded by remember {
        derivedStateOf { listSate.firstVisibleItemIndex == 0 }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    val  snackbarHostState = remember { SnackbarHostState() }

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

                Snackbarevent.NavigateUp -> {onBackButtonClick()}
            }

        }
    }
    
    LaunchedEffect(key1 = state.studiedHours, key2 = state.goalStudyHours ) {
        onEvent(SubjectEvent.UpdateProgess)
    }

    AddSubjectDialog(isOpen = isEditSubjectDialogOpen,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(SubjectEvent.onSubjectNameChange(it)) },
        onGoalsHoursChange = { onEvent(SubjectEvent.OnGoalStudyHoursChange(it))},
        selectedColors = state.subjectCardColors,
        onColorChange = { onEvent(SubjectEvent.OnSubjectCardColorChange(it)) },
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(SubjectEvent.UpdateSubject)
            isEditSubjectDialogOpen = false })

    DeleteDialog(isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session ?",
        bodyText = "Are you sure, you want to delete this session ? Your studied hours will be reduced" +
                "by this session time. This action can not undo ",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSession)
            isDeleteSessionDialogOpen = false })

    DeleteDialog(isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Session ?",
        bodyText = "Are you sure, you want to delete this subject ? All related" +
                "tasks and study sessions will be permanently removed. This action can not undo ",
        onDismissRequest = { isDeleteSubjectDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSubject)
            isDeleteSubjectDialogOpen = false })

    Scaffold(
        snackbarHost ={ SnackbarHost(hostState = snackbarHostState)} ,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ,topBar = {
            SubjectscreenTopBar(
                title = state.subjectName,
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = { isDeleteSubjectDialogOpen = true},
                onEditButtonClick = {isEditSubjectDialogOpen = true},
                scrollBehavior= scrollBehavior)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddTaskButtonClick,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add")},
                text = { Text(text = "Add Task")},
                expanded = isFABExpanded
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listSate
            ,modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            item {
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp) ,
                    studiedHours = state.goalStudyHours,
                    goalHours = state.studiedHours.toString(),
                    progess = state.progess)
            }
            tasksList(
                sectionTile = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks,\n" +
                        "Click the + button to add subjects",
                tasks = state.upcomingTasks,
                onCheckBoxClick = {onEvent(SubjectEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            tasksList(
                sectionTile = "UPCOMING TASKS",
                emptyListText = "You don't have any completed tasks,\n" +
                        "Click the check box on completion of tasks",
                tasks = state.completedTasks,
                onCheckBoxClick = {onEvent(SubjectEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessionsList(
                sectionTile = "RECENT STUDY SESSION",
                emptyListText = "You don't have any Recent study sessions,\n" +
                        "Start study session to begin recording your process",
                sessions = state.recentSessions,
                onDeleteIconClick = {
                    onEvent(SubjectEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen = true}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectscreenTopBar(
    title : String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigate back"
                )

            }
        }, title = {
            Text(
                text = title, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )

            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )

            }
        }
    )

}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours : String,
    progess : Float
) {
    val percentageProgess = remember (progess) {
        (progess * 100).toInt().coerceIn(0,100)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingtext = "Goal Study Hours", count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingtext = "Study Hours", count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.size(75.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(), progress = 1f,
                strokeWidth = 4.dp, strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(), progress = progess,
                strokeWidth = 4.dp, strokeCap = StrokeCap.Round
            )
            Text(text = "$percentageProgess%")
        }
    }
}