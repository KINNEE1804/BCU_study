package com.example.bcu_study.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.bcu_study.domain.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    //Insert Subject
    @Upsert
    suspend fun upsertSubject (subject: Subject)

    //update subject
    @Query ("SELECT COUNT(*) FROM SUBJECT")
    fun getTotalSubjectCount (): Flow<Int>

    //update goals hours
    @Query("SELECT SUM(goalHours) FROM SUBJECT ")
    fun getTotalHours() : Flow<Float>

    //get subject 1 times
    @Query ("SELECT * FROM Subject WHERE subjectId =:subjectId")
    suspend fun getSubjectById(subjectId: Int): Subject?

    //delete 1 subject
    @Query ("DELETE FROM Subject WHERE subjectId =:subjectId")
    suspend fun deleteSubject(subjectId: Int)

    //select all subject
    @Query ("SELECT * FROM Subject ")
    fun  getAllSubject() : Flow<List<Subject>>
}