package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

interface GetQuizDetailsUseCase {
    suspend operator fun invoke(id:Long): QuizDetailsEntry
}

class GetQuizDetailsUseCaseImpl @Inject constructor(
    private val repository: QuizLocalRepository,
) : GetQuizDetailsUseCase {
    override suspend fun invoke(id:Long): QuizDetailsEntry {
        return repository.getDetails(id)
    }
}