package com.alfoll.kfdapplication.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alfoll.kfdapplication.data.TaskEntity

@Composable
fun TaskDetailRoute(
    id: String,
    viewModel: TasksViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var task by remember { mutableStateOf<TaskEntity?>(null) }

    // в блоке сразу дает coroutine scope
    // меняется в зависимости от задачи которую нажимаем (меняется ключ - id)
    // для каждого экрана поддерживаем свои данные
    LaunchedEffect(id) {
        task = viewModel.getTask(id)
    }

    TaskDetailScreen(
        task = task,
        onBack = onBack,
        onSave = { id, isDone -> viewModel.saveTask(id, isDone); onBack() },
        modifier = modifier,
    )
}

// тасок много, функция одна -> LaunchedEffect
@Composable
fun TaskDetailScreen(
    task: TaskEntity?,
    onBack: () -> Unit,
    onSave: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // если таски нет
            if (task == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else { // если таска пришла не нулевая- рисуем column
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "id = ${task.id}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = if (task.isDone) "Done!" else "Not done yet :(",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // кнопка на обновление
                        Button(onClick = { onSave(task.id, !task.isDone) }) {
                            Text(text = if (task.isDone) "undo task" else "make task done")
                        }

                        // кнопка на выход
                        Button(onClick = onBack) {
                            Text(text = "go back")
                        }
                    }
                }
            }
        }
    }
}