package ru.vsls.surfquiz.presentation.screens.detailed


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vsls.surfquiz.domain.usecase.GetQuizDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getQuizDetailUseCase: GetQuizDetailsUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Initial)
    val state: StateFlow<DetailsState> = _state.asStateFlow()
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    fun loadDetails(id: Long?) {
        _state.value = DetailsState.Loading

        viewModelScope.launch {
            try {
                if (id != null) {
                    val details = getQuizDetailUseCase.invoke(id)
                    _state.value = DetailsState.Content(details = details)
                }
            } catch (e: Exception) {
                _toastMessage.emit("Ошибка загрузки: ${e.message}")
            }
        }
    }
}