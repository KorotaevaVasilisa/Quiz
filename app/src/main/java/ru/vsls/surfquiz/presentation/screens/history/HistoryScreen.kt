package ru.vsls.surfquiz.presentation.screens.history

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.vsls.surfquiz.R
import ru.vsls.surfquiz.presentation.items.InfoCard
import ru.vsls.surfquiz.ui.theme.SurfQuizTheme

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackToStart: () -> Unit = {},
    onNavigateDetailedScreen: (id: Long) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadHistory() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    val currentState = state
    when (currentState) {
        is HistoryState.Initial,
        is HistoryState.Loading,
            -> CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        )

        is HistoryState.Content ->
            if (currentState.history.isEmpty())
                InfoScreen(onBackToStart)
            else
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(currentState.history) { entry ->
                        val isSelected = currentState.selectedItemId == entry.id
                        HistoryItem(
                            model = entry,
                            isDimmed = currentState.selectedItemId != null && !isSelected,
                            isSelected = isSelected,
                            onLongClick = viewModel::selectItem,
                            onDelete = { viewModel.deleteEntry(entry.id) },
                            onNextScreen = { onNavigateDetailedScreen(entry.id) }
                        )
                    }
                }
    }
}


@Composable
private fun InfoScreen(onBackToStart: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InfoCard(onBackToStart)
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    SurfQuizTheme {
        HistoryItem(
            model = QuizHistoryUiModel(
                id = 222,
                title = "Quiz 1",
                formattedDate = "14.06.2024 19:15",
                correctAnswers = 4,
                totalQuestions = 5,
                difficulty = "medium"
            ),
            isDimmed = false,
            isSelected = false,
            onLongClick = {},
            onDelete = {},
            onNextScreen = {}
        )
    }
}