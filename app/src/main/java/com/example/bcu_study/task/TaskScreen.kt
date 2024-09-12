package com.example.bcu_study.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bcu_study.components.TaskCheckBox
import com.example.bcu_study.ui.theme.Red

@Composable
fun TaskScreen() {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var taskTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    taskTitleError = when {
        title.isBlank() -> "please enter task title"
        title.length < 4 -> "Task title is to short"
        title.length > 30 -> "Task title is to long"
        else -> null
    }
    Scaffold(
        topBar = {
            TaskScreenTopBar(
                isTaskExist = true,
                isComplete = false,
                checkBoxBorderColor = Red,
                onBackButtonClick = { /*TODO*/ },
                onDeleteButtonClick = { /*TODO*/ },
                onCheckBoxClick = {}
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(), value = title, onValueChange = { title = it },
                label = { Text(text = "Title") },
                singleLine = true,
                isError = taskTitleError != null && title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty()) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description, onValueChange = { description = it },
                label = { Text(text = "Description") },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "English", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Due Date"
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        },
        title = {
            Text(
                text = "Task",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if (isTaskExist) {
                TaskCheckBox(
                    isComplete = isComplete,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task"
                    )
                }

            }

        })
}