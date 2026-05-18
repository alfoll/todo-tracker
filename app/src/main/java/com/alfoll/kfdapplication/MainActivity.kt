package com.alfoll.kfdapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alfoll.kfdapplication.data.FakeTaskRepository
import com.alfoll.kfdapplication.presentation.AppNavHost
import com.alfoll.kfdapplication.ui.theme.KfdapplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KfdapplicationTheme {
                // в корне живет только навигация
                AppNavHost(FakeTaskRepository(), darkTheme = true, onThemeChanged = {})
            }
        }
    }
}
