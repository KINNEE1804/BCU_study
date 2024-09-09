package com.example.bcu_study.subject

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bcu_study.components.CountCard


@Composable
fun SubjectScreen() {
    Scaffold(
        topBar = {
            SubjectscreenTopBar(
                title = "English",
                onBackButtonClick = { },
                onDeleteButtonClick = { },
                onEditButtonClick = {})
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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
) {
    LargeTopAppBar(
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
    val percentageProgess = remember (key1 = progess) {
        (progess * 100).toInt().
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
            Text(text = "")
        }
    }
}