package ru.vsls.surfquiz.presentation.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.vsls.surfquiz.R
import ru.vsls.surfquiz.presentation.items.QuizQuestionBlock
import ru.vsls.surfquiz.ui.theme.LocalSurfQuizColors
import ru.vsls.surfquiz.presentation.items.QuizResultBlock
import ru.vsls.surfquiz.presentation.items.QuizStartBlock
import ru.vsls.surfquiz.ui.theme.SurfQuizTheme

@Composable
fun QuizScreen() {
    val viewModel: QuizViewModel = hiltViewModel()
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            uiState.error != null -> ErrorBlock(
                uiState.error,
                viewModel::loadQuiz)

            uiState.questions.isEmpty() -> QuizStartBlock(onStart = viewModel::loadQuiz)

            uiState.quizFinished ->
                QuizResultBlock(
                    correct = uiState.correctCount,
                    total = uiState.questions.size,
                    onRestart = { viewModel.restartQuiz() }
                )

            else -> {
                val qIdx = uiState.currentQuestionIndex
                val question = uiState.questions[qIdx]
                val answers = remember(question) {
                    (question.incorrectAnswers + question.correctAnswer).shuffled()
                }
                val quizColors = LocalSurfQuizColors.current
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    QuizQuestionBlock(
                        question = question.question,
                        answers = answers,
                        selectedAnswer = uiState.selectedAnswer,
                        onSelectAnswer = { viewModel.selectAnswer(it) },
                        currentIndex = qIdx,
                        total = uiState.questions.size,
                        correctAnswer = uiState.questions[qIdx].correctAnswer,
                        isNextEnabled = uiState.selectedAnswer != null,
                        isLast = qIdx == uiState.questions.lastIndex,
                        isAnswerCorrect = uiState.isAnswerCorrect,
                        onCheckAnswer = { viewModel.onCheckAnswer() },
                        isInteractionBlocked = uiState.isInteractionBlocked
                    )
                    Text(
                        text = stringResource(R.string.additional_info),
                        color = quizColors.inactive,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorBlock(
    error: String?,
    loadQuiz: (String) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        QuizStartBlock(onStart = loadQuiz )
        Text("Ошибка: $error", color = Color.Red)
    }
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    SurfQuizTheme {
        ErrorBlock("Error Message") { }
    }
}