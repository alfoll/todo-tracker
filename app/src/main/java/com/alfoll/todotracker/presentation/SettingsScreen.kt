package com.alfoll.todotracker.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


@Composable
fun SettingsRoute(
    darkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsScreen(
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged,
        onBack = onBack,
        modifier = modifier,
    )
}


@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Dark theme",
                    style = MaterialTheme.typography.titleMedium,
                )
                Switch(
                    checked = darkTheme,
                    onCheckedChange = { onThemeChanged(it) },
                )
            }

            Button(onClick = onBack) {
                Text(text = "go back")
            }
        }
    }
}