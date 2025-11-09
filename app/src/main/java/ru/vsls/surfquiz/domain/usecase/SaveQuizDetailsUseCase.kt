package ru.vsls.surfquiz.domain.usecase

import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import javax.inject.Inject

interface SaveQuizDetailsUseCase {
    suspend operator fun invoke(entry: QuizDetailsEntry)
}

class SaveQuizDetailsUseCaseImpl @Inject constructor(
    private val repository: QuizLocalRepository,
) : SaveQuizDetailsUseCase {
    override suspend fun invoke(entry: QuizDetailsEntry) =
        repository.saveDetails(entry)
}