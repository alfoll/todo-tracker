package com.alfoll.todotracker.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alfoll.todotracker.data.TaskEntity

// на него переведет навигация когда захотим показать список тасок
// надстройка над экраном - для повышения связности
@Composable
fun TasksRoute(
    viewModel: TasksViewModel,
    onTaskClick: (String) -> Unit,
) {
    // подписываемся на state flow который вызывает рекомпозицию при его изменении
    // lifecycle активити - стейт связан с состоянием экрана
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    // колбэк который вызовется при запуске или смене ключа
    // Unit один и поменяться не может - уникальный неизменяемый ключ (можно const val сверху или др значение)
    // вызовется один раз при старте экрана
    LaunchedEffect(Unit) {
        viewModel.load()
    }

    TasksScreen(
        state = state.value,
        onQueryChanged = viewModel::onQueryChange,
        onTaskClick = onTaskClick,
    )
}

@Composable
fun TasksScreen(
    state: TasksState,
    onQueryChanged: (String) -> Unit,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // обработать запрос поиска
    val filteredTasks = state.tasks.filter { it.title.contains(state.query, ignoreCase = true) }

    // деволтная разметка material экрана
    // включает material theme для всего что ниже (переключалки внизу экрана/значок плюса и тд)
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // системные отступы чтобы не залезать на элементы Scaffold
                .padding(16.dp), // под внутр отступ для контента
            verticalArrangement = Arrangement.spacedBy(16.dp), // расстояние между элементами Column по вертикали
        ) {
            SearchBar(
                value = state.query, // все рисуем в зависимости от стейта
                onValueChanged = onQueryChanged,
            )
            // стейты на обработку:
                // isLoading
                // data -> tasks.notEmpty - пришел список
                // no data -> !isLoading && tasks.isEmpty
            when {
                state.isLoading -> {
                    // контейнет где можно элементы друг на друга - на весь экран + выравнивание по центру
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator() // индикатор загрузки
                    }
                }

                filteredTasks.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No data")
                    }
                }

                else -> {
                    // отрисовывает не все элементы сразу а только те что на экране + еще парочку наверх и вниз
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(filteredTasks) {
                            TaskItem(task = it, onClick = { onTaskClick(it.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskEntity,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = if (task.isDone) "Done!" else "Not done yet :(",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}