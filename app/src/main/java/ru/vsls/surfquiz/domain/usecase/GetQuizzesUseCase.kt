package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.model.Question
import ru.vsls.surfquiz.domain.repository.QuizRemoteRepository
import javax.inject.Inject


interface GetQuizzesUseCase {
    suspend operator fun invoke(
        difficulty: String,
    ): List<Question>
}

class GetQuizzesUseCaseImpl @Inject constructor(
    private val repository: QuizRemoteRepository,
) : GetQuizzesUseCase {
    override suspend fun invoke(difficulty: String): List<Question> {
        return repository.getQuizzes(difficulty)
    }
}
