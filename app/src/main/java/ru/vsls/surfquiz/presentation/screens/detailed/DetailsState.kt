package ru.vsls.surfquiz.presentation.screens.detailed

import ru.vsls.surfquiz.domain.model.QuizDetailsEntry

sealed interface DetailsState {
    data object Initial : DetailsState
    data object Loading : DetailsState
    data class Content(val details: QuizDetailsEntry) : DetailsState
}