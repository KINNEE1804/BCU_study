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
import com.example.bcu_study.components.SubjectCard
import com.example.bcu_study.components.tasksList
import com.example.bcu_study.domain.model.Tasks


@Composable
fun DashboardScreen(): Unit {

    val subject = listOf(
        Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0]),
        Subject(name = "DB", goalHours = 10f, colors = Subject.subjectCardColors[1]),
        Subject(name = "JS", goalHours = 10f, colors = Subject.subjectCardColors[2]),
        Subject(name = "Python", goalHours = 10f, colors = Subject.subjectCardColors[3]),
        Subject(name = "C++", goalHours = 10f, colors = Subject.subjectCardColors[4]),
    )
    val tasks = listOf(
        Tasks(title = "prepare notes", description = "", dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = false),

        Tasks(title = "test 1", description = "", dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = true),

        Tasks(title = "test 2", description = "", dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = false),

        Tasks(title = "test 3", description = "", dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = true)
    )
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
                    subjectList = subject
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
                tasks = tasks
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
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subject. "
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
            IconButton(onClick = { /*TODO*/ }) {
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