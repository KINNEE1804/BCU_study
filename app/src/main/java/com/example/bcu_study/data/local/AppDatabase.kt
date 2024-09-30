package com.example.bcu_study.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.bcu_study.domain.model.Session
import com.example.bcu_study.domain.model.Subject
import com.example.bcu_study.domain.model.Tasks

@Database(
    entities = [Subject::class, Session::class, Tasks::class],
    version = 1
)
@TypeConverters(ColorListConveter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun  sessionDao(): SessionDao
}