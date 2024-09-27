package com.example.bcu_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.ui.theme.BCU_studyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BCU_studyTheme {

            }
        }
    }
}

val subject = listOf(
    Subject(
        name = "English",
        goalHours = 10f,
        colors = Subject.subjectCardColors[0],
        subjectId = 0
    ),
    Subject(name = "DB", goalHours = 10f, colors = Subject.subjectCardColors[1], subjectId = 0),
    Subject(name = "JS", goalHours = 10f, colors = Subject.subjectCardColors[2], subjectId = 0),
    Subject(
        name = "Python",
        goalHours = 10f,
        colors = Subject.subjectCardColors[3],
        subjectId = 0
    ),
    Subject(
        name = "C++",
        goalHours = 10f,
        colors = Subject.subjectCardColors[4],
        subjectId = 0
    ),
)
val tasks = listOf(
    Tasks(
        title = "prepare notes", description = "", dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = false,
        tasksId = 1, taskSubjectId = 0
    ),

    Tasks(
        title = "test 1", description = "", dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = true, tasksId = 1, taskSubjectId = 0
    ),

    Tasks(
        title = "test 2", description = "", dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = false, tasksId = 1, taskSubjectId = 0
    ),

    Tasks(
        title = "test 3", description = "", dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = true, tasksId = 1, taskSubjectId = 0
    )
)

val session = listOf(
    Session(
        relatedToSubject = "English",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0
    ),
    Session(
        relatedToSubject = "DB",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0
    ),
    Session(
        relatedToSubject = "JS",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0
    ),
    Session(
        relatedToSubject = "Python",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0
    ),
    Session(
        relatedToSubject = "C++",
        date = 0L, duration = 2, sessionId = 0, sessionSubjectId = 0
    )

)
