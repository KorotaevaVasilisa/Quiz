package ru.vsls.surfquiz.presentation.screens.detailed

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.vsls.surfquiz.presentation.items.QuizQuestionBlock
import ru.vsls.surfquiz.presentation.items.QuizResultBlock

@Composable
fun DetailsScreen(
    id: Long?,
    onBackToStart: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadDetails(id)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val currentState = state
        when (currentState) {
            is DetailsState.Initial,
            is DetailsState.Loading,
                -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.onPrimary
            )
            is DetailsState.Content ->
                ListDetails(currentState, onBackToStart = onBackToStart)

        }
    }
}

@Composable
fun ListDetails(state: DetailsState.Content, onBackToStart: () -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        QuizResultBlock(
            correct = state.details.correctCount,
            total = state.details.questions.size,
            onRestart = { onBackToStart() }
        )
        state.details.questions.forEachIndexed { index, question ->
            val userAnswer = state.details.usersAnswers.getOrNull(index)
            QuizQuestionBlock(
                question = question.question,
                answers = (question.incorrectAnswers + question.correctAnswer).shuffled(),
                selectedAnswer = userAnswer,
                onSelectAnswer = {}, // В детальном выводе не требуется, можно оставить пустым
                currentIndex = index,
                total = state.details.questions.size,
                isLast = index == state.details.questions.lastIndex,
                isInteractionBlocked = true, // Блокируем взаимодействие, т.к. просто показываем историю
                isAnswerCorrect = userAnswer == question.correctAnswer,
                isNextEnabled = false,
                correctAnswer = question.correctAnswer,
                onCheckAnswer = {},
                showButton = false
            )
        }
    }
}