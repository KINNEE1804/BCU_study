package com.example.bcu_study.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bcu_study.R
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Tasks
import com.example.bcu_study.util.Priority

fun LazyListScope.StudySessionsList(
    sectionTile: String,
    emptyListText : String,
    sessions: List<Session>,
    onDeleteIconClick : (Session) -> Unit

): Unit {
    item {
        Text(
            text = sectionTile,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
    }
    if (sessions.isEmpty()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.img_lamp),
                    contentDescription = emptyListText
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = emptyListText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
    items(sessions){ session -> StudySessionCard(
        modifier = Modifier.padding(12.dp, vertical = 4.dp),
        sessions = session,
        onDeleteIconClick = {onDeleteIconClick(session)}
    )

    }
}
@Composable
private fun StudySessionCard (
    modifier: Modifier = Modifier,
    sessions : Session,
    onDeleteIconClick : () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row (modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically)
        {
            Column (
                modifier = Modifier.padding(start = 12.dp)) {
                Text(text = sessions.relatedToSubject,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,)
                Text(text = "${sessions.date}", style = MaterialTheme.typography.bodySmall )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${sessions.duration} hr", style = MaterialTheme.typography.titleMedium )
            IconButton(onClick =  onDeleteIconClick ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Session")
                
            }

        }
    }
}