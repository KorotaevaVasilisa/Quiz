package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.model.QuizHistoryEntry
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

interface SaveQuizHistoryUseCase {
    suspend operator fun invoke(entry: QuizHistoryEntry): Long
}

class SaveQuizHistoryUseCaseImpl @Inject constructor(
    private val repository: QuizLocalRepository,
) : SaveQuizHistoryUseCase {
    override suspend fun invoke(entry: QuizHistoryEntry): Long =
        repository.saveHistory(entry)
}
