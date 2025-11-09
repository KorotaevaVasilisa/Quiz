package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

interface DeleteQuizHistoryEntryUseCase {
    suspend operator fun invoke(id: Long)
}

class DeleteQuizHistoryEntryUseCaseImpl @Inject constructor(
    private val repository: QuizLocalRepository,
) : DeleteQuizHistoryEntryUseCase {
    override suspend fun invoke(id: Long) {
        repository.deleteHistoryById(id)
        repository.deleteDetailsById(id)
    }
}
