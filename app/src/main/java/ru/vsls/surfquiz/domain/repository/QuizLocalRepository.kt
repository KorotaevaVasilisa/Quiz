package ru.vsls.surfquiz.domain.repository

import ru.vsls.surfquiz.data.local.entities.ResultQuizDt
import ru.vsls.surfquiz.data.local.entities.UserQuizAnswer
import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.model.QuizHistoryEntry

interface QuizLocalRepository {
    suspend fun saveHistory(result: QuizHistoryEntry):Long
    suspend fun getHistory(): List<ResultQuizDt>
    suspend fun deleteHistoryById(id: Long)
    suspend fun getDetails(id: Long): UserQuizAnswer
    suspend fun saveDetails(details: QuizDetailsEntry)
    suspend fun deleteDetailsById(id: Long)
}