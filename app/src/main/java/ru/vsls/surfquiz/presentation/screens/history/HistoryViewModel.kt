package ru.vsls.surfquiz.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vsls.surfquiz.domain.usecase.DeleteQuizHistoryEntryUseCase
import ru.vsls.surfquiz.domain.usecase.GetQuizHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getQuizHistoryUseCase: GetQuizHistoryUseCase,
    private val deleteQuizHistoryUseCase: DeleteQuizHistoryEntryUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HistoryState>(HistoryState.Initial)
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    // SharedFlow для событий (показ тоста)
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    fun loadHistory() {
        _state.value = HistoryState.Loading
        viewModelScope.launch {
            try {
                val history = getQuizHistoryUseCase().toUiModels()
                _state.value = HistoryState.Content(history = history)
            } catch (e: Exception) {
                _toastMessage.emit("Ошибка загрузки: ${e.message}")
            }
        }
    }

    fun selectItem(id: Long?) {
        val state = _state.value as? HistoryState.Content ?: return
        _state.value = state.copy(selectedItemId = id)
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            try {
                deleteQuizHistoryUseCase(id)
                _toastMessage.emit("Запись удалена")
                loadHistory()
            } catch (e: Exception) {
                _toastMessage.emit("Ошибка удаления: ${e.message}")
            }
        }
    }
}