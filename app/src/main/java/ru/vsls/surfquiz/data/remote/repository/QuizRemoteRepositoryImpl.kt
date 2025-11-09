package ru.vsls.surfquiz.data.remote.repository

import ru.vsls.surfquiz.data.remote.QuizApiService
import ru.vsls.surfquiz.data.remote.mapper.toDomain
import ru.vsls.surfquiz.domain.model.Question
import ru.vsls.surfquiz.domain.repository.QuizRemoteRepository
import javax.inject.Inject

class QuizRemoteRepositoryImpl @Inject constructor(
    private val api: QuizApiService,
) : QuizRemoteRepository {
    override suspend fun getQuizzes(difficulty: String): List<Question> {
        return api.getQuiz(difficulty = difficulty).results.toDomain()
    }
}
