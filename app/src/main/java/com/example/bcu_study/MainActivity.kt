package com.example.bcu_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.presentation.es.NavGraphs
import com.example.bcu_study.presentation.es.theme.BCU_studyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BCU_studyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

val subject = listOf(
    Subject(
        name = "English",
        goalHours = 10f,
        colors = Subject.subjectCardColors[0].map { it.toArgb() },
        subjectId = 0
    ),
    Subject(
        name = "DB",
        goalHours = 10f,
        colors = Subject.subjectCardColors[1].map { it.toArgb() },
        subjectId = 0
    ),
    Subject(
        name = "JS",
        goalHours = 10f,
        colors = Subject.subjectCardColors[2].map { it.toArgb() },
        subjectId = 0
    ),
    Subject(
        name = "Python",
        goalHours = 10f,
        colors = Subject.subjectCardColors[3].map { it.toArgb() },
        subjectId = 0
    ),
    Subject(
        name = "C++",
        goalHours = 10f,
        colors = Subject.subjectCardColors[4].map { it.toArgb() },
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
