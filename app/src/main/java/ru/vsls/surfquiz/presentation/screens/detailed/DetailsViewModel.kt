package ru.vsls.surfquiz.presentation.screens.detailed


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vsls.surfquiz.domain.usecase.GetQuizDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getQuizDetailUseCase: GetQuizDetailsUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state.asStateFlow()

    fun loadDetails(id: Long?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                if (id != null) {
                    val details = getQuizDetailUseCase.invoke(id)
                    _state.value = DetailsUiState(details = details)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}