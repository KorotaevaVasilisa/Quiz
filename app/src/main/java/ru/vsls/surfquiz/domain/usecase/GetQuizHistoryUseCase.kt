package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.model.QuizHistoryEntry
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

interface GetQuizHistoryUseCase {
    suspend operator fun invoke(): List<QuizHistoryEntry>
}

class GetQuizHistoryUseCaseImpl @Inject constructor(
    private val repository: QuizLocalRepository,
) : GetQuizHistoryUseCase {
    override suspend fun invoke(): List<QuizHistoryEntry> =
        repository.getHistory()
}
