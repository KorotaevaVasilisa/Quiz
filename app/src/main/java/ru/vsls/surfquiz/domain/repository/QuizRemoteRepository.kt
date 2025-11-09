package ru.vsls.surfquiz.domain.repository

import ru.vsls.surfquiz.domain.model.Question

interface QuizRemoteRepository {
    suspend fun getQuizzes(
        difficulty: String,
    ): List<Question>
}
