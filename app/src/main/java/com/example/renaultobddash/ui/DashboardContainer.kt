
package com.example.renaultobddash.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardContainer() {
    var mode by remember { mutableStateOf("main") }
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        if (mode == "main") {
            MainDashboard { mode = it }
        } else if (mode == "diagnostics") {
            DiagnosticsScreen { mode = it }
        } else if (mode == "settings") {
            SettingsScreen { mode = it }
        }
    }
}
