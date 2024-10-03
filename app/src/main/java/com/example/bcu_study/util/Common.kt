package com.example.bcu_study.util

import android.os.Build
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.example.bcu_study.presentation.es.theme.Green
import com.example.bcu_study.presentation.es.theme.Orange
import com.example.bcu_study.presentation.es.theme.Red
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Green, value = 0), MEDIUM(
        title = "Medium",
        color = Orange,
        value = 1
    ),
    HIGH(title = "High", color = Red, value = 2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                .toLocalDate()
    }?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHours(): Float{
    val hours = this.toFloat() / 3600f //1h co 3600s
    return "%.2f".format(hours).toFloat() // chi lay 2 so 0 sau dau .
}

sealed class Snackbarevent {

    data class ShowSnackbar (val message: String,
                             val duration: SnackbarDuration = SnackbarDuration.Short ) :Snackbarevent()
    data object NavigateUp: Snackbarevent()
}