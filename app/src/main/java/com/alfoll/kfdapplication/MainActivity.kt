package com.alfoll.kfdapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.alfoll.kfdapplication.data.FakeTaskRepository
import com.alfoll.kfdapplication.presentation.TasksRoute
import com.alfoll.kfdapplication.presentation.TasksViewModel
import com.alfoll.kfdapplication.ui.theme.KfdapplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = FakeTaskRepository()

        enableEdgeToEdge()
        setContent {
            KfdapplicationTheme {
                // модель нужно сохранить
                val viewModel = remember(repo) { TasksViewModel(repo) }

                TasksRoute(viewModel, onTaskClick = {})
            }
        }
    }
}
