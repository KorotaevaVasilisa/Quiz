package ru.vsls.surfquiz.presentation.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vsls.surfquiz.domain.model.QuizDetailsEntry
import ru.vsls.surfquiz.domain.model.QuizHistoryEntry
import ru.vsls.surfquiz.domain.usecase.GetQuizzesUseCase
import ru.vsls.surfquiz.domain.usecase.SaveQuizDetailsUseCase
import ru.vsls.surfquiz.domain.usecase.SaveQuizHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
    private val saveQuizResultUseCase: SaveQuizHistoryUseCase,
    private val saveQuizDetailsUseCase: SaveQuizDetailsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    fun loadQuiz(difficulty: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val questions = getQuizzesUseCase(difficulty = difficulty.lowercase())
                _state.value = QuizUiState(questions = questions)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun restartQuiz() {
        _state.value = QuizUiState()
    }

    fun selectAnswer(answer: String) {
        val state = _state.value
        if (state.isInteractionBlocked) return
        _state.value = state.copy(selectedAnswer = answer)
    }

    fun onCheckAnswer() {
        val state = _state.value
        if (state.selectedAnswer == null) return
        val isCorrect =
            state.selectedAnswer == state.questions[state.currentQuestionIndex].correctAnswer
        _state.value = state.copy(
            isAnswerCorrect = isCorrect,
            isInteractionBlocked = true
        )
        viewModelScope.launch {
            delay(1000)
            onNextQuestion()
        }
    }

    fun onNextQuestion() {
        val state = _state.value
        val updatedAnswers = state.userAnswers + (state.selectedAnswer ?: "")
        if (state.currentQuestionIndex < state.questions.lastIndex) {
            _state.value = state.copy(
                currentQuestionIndex = state.currentQuestionIndex + 1,
                selectedAnswer = null,
                userAnswers = updatedAnswers,
                isAnswerCorrect = null,
                isInteractionBlocked = false
            )
        } else {
            val correctCount =
                state.questions.zip(updatedAnswers).count { (q, a) -> q.correctAnswer == a }
            _state.value = state.copy(
                userAnswers = updatedAnswers,
                quizFinished = true,
                correctCount = correctCount,
                isAnswerCorrect = null,
                isInteractionBlocked = false
            )
            // Сохраняем результат викторины
            viewModelScope.launch {
                val historyId = saveQuizResultUseCase(
                    QuizHistoryEntry(
                        id = 0,
                        dateTime = System.currentTimeMillis(),
                        correctAnswers = correctCount,
                        totalQuestions = state.questions.size,
                        difficulty = state.questions.firstOrNull()?.difficulty ?: ""
                    )
                )
                saveQuizDetailsUseCase(
                    QuizDetailsEntry(
                        id = 0,
                        resultId = historyId,
                        correctCount = correctCount,
                        usersAnswers = state.userAnswers,
                        questions = state.questions
                    )
                )
            }
        }
    }
}