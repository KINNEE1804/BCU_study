package com.example.bcu_study.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.example.bcu_study.ui.theme.Green
import com.example.bcu_study.ui.theme.Orange
import com.example.bcu_study.ui.theme.Red
import java.sql.Date
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