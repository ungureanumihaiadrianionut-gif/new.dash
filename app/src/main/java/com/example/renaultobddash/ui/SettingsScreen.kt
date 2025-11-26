
package com.example.renaultobddash.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onNavigate: (String)->Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text("Settings")
        Button(onClick = { onNavigate("main") }) { Text("Back") }
    }
}
