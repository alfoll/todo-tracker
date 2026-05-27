package com.alfoll.todotracker.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alfoll.todotracker.data.FakeTaskRepository
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


object Routes {
    const val TASKS = "tasks"
    const val TASK_DETAIL = "task"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(
    repository: FakeTaskRepository,
    darkTheme: Boolean, // тема еще выше чем навигация - это конфиг приложения
    onThemeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val repo = FakeTaskRepository()
    val viewModel = remember(repo) { TasksViewModel(repo) }

    NavHost(
        navController = navController,
        startDestination = Routes.TASKS,
        modifier = modifier,
    ) {
        composable(Routes.TASKS) {
            TasksRoute(
                viewModel,
                onTaskClick = { id ->
                    navController.navigate("${Routes.TASK_DETAIL}/$id")
                }
            )
        }

        composable(
            "${Routes.TASK_DETAIL}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { entry ->
            val taskId = entry.arguments?.getString("id").orEmpty()

            TaskDetailRoute(
                taskId,
                viewModel,
                onBack = navController::popBackStack,
            )
        }
    }
}
