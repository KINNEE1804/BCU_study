package com.example.bcu_study.dashboard

import android.widget.EditText
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.bcu_study.components.CountCard
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.R
import androidx.compose.ui.text.style.TextAlign
import com.example.bcu_study.components.AddSubjectDialog
import com.example.bcu_study.components.DeleteDialog
import com.example.bcu_study.components.StudySessionsList
import com.example.bcu_study.components.SubjectCard
import com.example.bcu_study.components.tasksList
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Tasks


@Composable
fun DashboardScreen(): Unit {

    val subject = listOf(
        Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "DB", goalHours = 10f, colors = Subject.subjectCardColors[1], subjectId = 0),
        Subject(name = "JS", goalHours = 10f, colors = Subject.subjectCardColors[2], subjectId = 0),
        Subject(name = "Python", goalHours = 10f, colors = Subject.subjectCardColors[3], subjectId = 0),
        Subject(name = "C++", goalHours = 10f, colors = Subject.subjectCardColors[4], subjectId = 0),
    )
    val tasks = listOf(
        Tasks(title = "prepare notes", description = "", dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = false,
            tasksId = 1, taskSubjectId = 0),

        Tasks(title = "test 1", description = "", dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = true,tasksId = 1, taskSubjectId = 0),

        Tasks(title = "test 2", description = "", dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isComplete = false,tasksId = 1, taskSubjectId = 0),

        Tasks(title = "test 3", description = "", dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = true,tasksId = 1, taskSubjectId = 0)
    )

    val session = listOf(
        Session(relatedToSubject = "English",
            date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0),
        Session(relatedToSubject = "DB",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0),
        Session(relatedToSubject = "JS",
            date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0),
        Session(relatedToSubject = "Python",
            date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0),
        Session(relatedToSubject = "C++",
            date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0)

    )
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf( false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf( false) }

    var subjectName by remember { mutableStateOf( value = "")}
    var goalHours by remember { mutableStateOf( value = "")}
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColors.random())}
    AddSubjectDialog(isOpen = isAddSubjectDialogOpen,
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = {subjectName = it},
        onGoalsHoursChange = {goalHours = it},
        selectedColors = selectedColor,
        onColorChange = {selectedColor = it},
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = { isAddSubjectDialogOpen = false})
    DeleteDialog(isOpen = isDeleteSessionDialogOpen , title = "Delete Session ?", bodyText = "Are you sure, you want to delete this session ? Your studied hours will be reduced"+
        "by this session time. This action can not undo ",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = {isDeleteSessionDialogOpen = false})

    Scaffold (
        topBar = { DashboardScreenTopbar()}
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
                    subjectCount = 5,
                    studiedHours = "10",
                    goalHours = "15")

            }
            item { 
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subject,
                    onAddIconClicked =  {
                        isAddSubjectDialogOpen = true
                    }
                )
            }
            item {
                Button(onClick = { /*TODO*/ },
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
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessionsList(
                sectionTile = "RECENT STUDY SESSION",
                emptyListText = "You don't have any Recent study sessions,\n" +
                "Start study session to begin recording your process",
                sessions = session,
                onDeleteIconClick = {isDeleteSessionDialogOpen = true }
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
            style = MaterialTheme.typography.headlineMedium)
    })
    
}

//addcountcard
@Composable
private fun CountCardsSection(
   modifier: Modifier,
   subjectCount: Int,
   studiedHours: String,
   goalHours: String
){
    Row(modifier = modifier){
        CountCard(modifier = Modifier.weight(1f),headingtext = "Subject Count" , count = "$subjectCount")
        Spacer(modifier = Modifier.width(10.dp))
        //
        CountCard(modifier = Modifier.weight(1f) ,headingtext = "Studied Hours" , count = studiedHours)
        Spacer(modifier = Modifier.width(10.dp))
        //
        CountCard(modifier = Modifier.weight(1f),headingtext = "Goal Study Hours" , count = goalHours)
    }

}

//
@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList : List<Subject>,
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subject. ",
    onAddIconClicked : () -> Unit
): Unit {
    Column(
        modifier = Modifier,

    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
                )
            IconButton(onClick = onAddIconClicked) {
               Icon(imageVector = Icons.Default.Add, contentDescription = "Add Subject" )
            }

        }
        if(subjectList.isEmpty()){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_book), contentDescription = emptyListText)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp )
        ) {
            items(subjectList) {subject -> SubjectCard(
                subjectName = subject.name,
                gradientColors = subject.colors,
                onClick = {}) }
        }
    }
    
}