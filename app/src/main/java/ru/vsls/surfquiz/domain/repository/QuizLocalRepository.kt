package ru.vsls.surfquiz.domain.repository

import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.model.QuizHistoryEntry

interface QuizLocalRepository {
    suspend fun saveHistory(result: QuizHistoryEntry):Long
    suspend fun getHistory(): List<QuizHistoryEntry>
    suspend fun deleteHistoryById(id: Long)
    suspend fun getDetails(id: Long): QuizDetailsEntry
    suspend fun saveDetails(details: QuizDetailsEntry)
    suspend fun deleteDetailsById(id: Long)
}