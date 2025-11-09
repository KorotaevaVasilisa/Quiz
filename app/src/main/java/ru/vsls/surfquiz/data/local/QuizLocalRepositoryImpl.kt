package ru.vsls.surfquiz.data.local

import ru.vsls.surfquiz.data.local.dao.QuizDao
import ru.vsls.surfquiz.data.local.entities.ResultQuizDt
import ru.vsls.surfquiz.data.local.entities.UserQuizAnswer
import ru.vsls.surfquiz.data.local.mappers.toEntity
import ru.vsls.surfquiz.data.local.mappers.toUserQuizAnswerEntity
import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.model.QuizHistoryEntry
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

class QuizLocalRepositoryImpl @Inject constructor(private val api: QuizDao) : QuizLocalRepository {
    override suspend fun saveHistory(result: QuizHistoryEntry): Long {
        return api.insertResult(result.toEntity())
    }

    override suspend fun getHistory(): List<ResultQuizDt> {
        return api.getAllResults()
    }

    override suspend fun deleteHistoryById(id: Long) {
        api.deleteHistoryById(id)
    }

    override suspend fun getDetails(id: Long): UserQuizAnswer {
        return api.getDetailsById(id)
    }

    override suspend fun saveDetails(details: QuizDetailsEntry) {
        api.insertDetails(details.toUserQuizAnswerEntity())
    }

    override suspend fun deleteDetailsById(id: Long) {
        api.deleteDetailsById(id)
    }
}