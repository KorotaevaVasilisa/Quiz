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
    private val _state = MutableStateFlow(HistoryUiState())
    val state: StateFlow<HistoryUiState> = _state.asStateFlow()

    // SharedFlow для событий (показ тоста)
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    fun loadHistory() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val history = getQuizHistoryUseCase()
                val uiHistory = history.toUiModels()
                _state.value = _state.value.copy(isLoading = false, history = uiHistory)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun selectItem(id: Long?) {
        _state.value = _state.value.copy(selectedItemId = id)
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