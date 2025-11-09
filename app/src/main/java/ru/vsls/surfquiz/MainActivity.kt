package ru.vsls.surfquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vsls.surfquiz.presentation.navigation.Screen
import ru.vsls.surfquiz.presentation.screens.detailed.DetailsScreen
import ru.vsls.surfquiz.presentation.screens.history.HistoryScreen
import ru.vsls.surfquiz.presentation.screens.start.QuizScreen
import ru.vsls.surfquiz.ui.theme.SurfQuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SurfQuizTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        when (currentRoute) {
                            Screen.Quiz.route, null -> {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Image(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = stringResource(R.string.app_name),
                                            modifier = Modifier.height(50.dp)
                                        )
                                    },
                                    actions = {
                                        IconButton(onClick = { navController.navigate(Screen.History.route) }) {
                                            Icon(
                                                painter = painterResource(R.drawable.access_time),
                                                contentDescription = stringResource(R.string.history),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                                )
                            }

                            Screen.History.route ->
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(
                                            stringResource(R.string.history),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                painter = painterResource(R.drawable.arrow_back),
                                                contentDescription = stringResource(R.string.back),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)

                                )

                            Screen.Details.route ->
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(
                                            stringResource(R.string.results),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                painter = painterResource(R.drawable.arrow_back),
                                                contentDescription = stringResource(R.string.back),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                                )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    QuizNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QuizNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Quiz.route,
        modifier = modifier
    ) {
        composable(Screen.Quiz.route) {
            QuizScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onBackToStart = {
                    navController.navigate(Screen.Quiz.route) {
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                },
                onNavigateDetailedScreen = { id ->
                    navController.navigate("details/$id")
                }
            )
        }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLong()
            DetailsScreen(
                id = id,
                onBackToStart = {
                    navController.navigate(Screen.Quiz.route) {
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    SurfQuizTheme {
        QuizScreen()
    }
}