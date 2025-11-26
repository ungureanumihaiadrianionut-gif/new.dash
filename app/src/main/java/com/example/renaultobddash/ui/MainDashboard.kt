
package com.example.renaultobddash.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainDashboard(onNavigate: (String)->Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().weight(1f)) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // placeholder image slot
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { onNavigate("diagnostics") }) { Text("Diagnostics") }
            Button(onClick = { onNavigate("settings") }) { Text("Settings") }
        }
    }
}
