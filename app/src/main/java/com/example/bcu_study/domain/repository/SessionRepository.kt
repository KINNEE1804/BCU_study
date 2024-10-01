package com.example.bcu_study.domain.repository

import com.example.bcu_study.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession (session: Session)

    fun getAllSessions(): Flow<List<Session>>

    fun getRecentFiveSessions(): Flow<List<Session>>

    fun getRencentTenSessionsForSubject(subjectId : Int): Flow<List<Session>>


}