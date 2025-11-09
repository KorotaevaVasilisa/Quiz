package ru.vsls.surfquiz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vsls.surfquiz.data.local.QuizLocalRepositoryImpl
import ru.vsls.surfquiz.domain.repository.QuizRemoteRepository
import ru.vsls.surfquiz.data.remote.repository.QuizRemoteRepositoryImpl
import ru.vsls.surfquiz.domain.repository.QuizLocalRepository
import ru.vsls.surfquiz.domain.usecase.GetQuizzesUseCase
import ru.vsls.surfquiz.domain.usecase.GetQuizzesUseCaseImpl
import ru.vsls.surfquiz.domain.usecase.GetQuizHistoryUseCase
import ru.vsls.surfquiz.domain.usecase.GetQuizHistoryUseCaseImpl
import ru.vsls.surfquiz.domain.usecase.SaveQuizHistoryUseCase
import ru.vsls.surfquiz.domain.usecase.SaveQuizHistoryUseCaseImpl
import ru.vsls.surfquiz.domain.usecase.DeleteQuizHistoryEntryUseCase
import ru.vsls.surfquiz.domain.usecase.DeleteQuizHistoryEntryUseCaseImpl
import ru.vsls.surfquiz.domain.usecase.GetQuizDetailsUseCase
import ru.vsls.surfquiz.domain.usecase.GetQuizDetailsUseCaseImpl
import ru.vsls.surfquiz.domain.usecase.SaveQuizDetailsUseCase
import ru.vsls.surfquiz.domain.usecase.SaveQuizDetailsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindQuizRepository(impl: QuizRemoteRepositoryImpl): QuizRemoteRepository

    @Binds
    abstract fun bindGetQuizzesUseCase(useCaseImpl: GetQuizzesUseCaseImpl): GetQuizzesUseCase

    @Binds
    abstract fun bindQuizLocalRepository(impl: QuizLocalRepositoryImpl): QuizLocalRepository

    @Binds
    abstract fun bindGetQuizHistoryUseCase(impl: GetQuizHistoryUseCaseImpl): GetQuizHistoryUseCase

    @Binds
    abstract fun bindSaveQuizHistoryUseCase(impl: SaveQuizHistoryUseCaseImpl): SaveQuizHistoryUseCase

    @Binds
    abstract fun bindDeleteQuizHistoryEntryUseCase(impl: DeleteQuizHistoryEntryUseCaseImpl): DeleteQuizHistoryEntryUseCase

    @Binds
    abstract fun bindSaveQuizDetailsUseCase(impl: SaveQuizDetailsUseCaseImpl): SaveQuizDetailsUseCase

    @Binds
    abstract fun bindGetQuizDetailsUseCase(impl: GetQuizDetailsUseCaseImpl): GetQuizDetailsUseCase
}
