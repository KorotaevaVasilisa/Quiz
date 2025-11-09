package ru.vsls.surfquiz.presentation.screens.history

sealed interface HistoryState {
    data object Initial : HistoryState
    data object Loading : HistoryState
    data class Content(
        val history: List<QuizHistoryUiModel>,
        val selectedItemId: Long? = null,
    ) : HistoryState
}