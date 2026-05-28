package com.alfoll.todotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.alfoll.todotracker.data.FakeTaskRepository
import com.alfoll.todotracker.presentation.AppNavHost
import com.alfoll.todotracker.ui.theme.TodoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var darkTheme by rememberSaveable {
                mutableStateOf(false) // false - светлая/ true - темная
            }

            val repo = remember { FakeTaskRepository() }

            TodoTrackerTheme(
                darkTheme = darkTheme,
            ) {
                // в корне живет только навигация
                AppNavHost(
                    repo,
                    darkTheme = darkTheme,
                    onThemeChanged = {newTheme -> darkTheme = newTheme},
                )
            }
        }
    }
}
