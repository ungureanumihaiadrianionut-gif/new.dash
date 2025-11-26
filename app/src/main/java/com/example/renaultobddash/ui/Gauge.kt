
package com.example.renaultobddash.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun Gauge(label: String, value: Float, max: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(8.dp)) {
        Canvas(modifier = Modifier.aspectRatio(1f)) {
            val cx = size.width/2
            val cy = size.height/2
            val r = size.minDimension/2 * 0.8f
            drawCircle(alpha = 0.1f, radius = r, center = Offset(cx, cy))
            val angle = -120f + (value/max)*240f
            rotate(angle, pivot = Offset(cx, cy)) {
                drawLine(androidx.compose.ui.graphics.Color.Red, start=Offset(cx,cy), end=Offset(cx, cy-r+10f), strokeWidth=6f)
            }
        }
        Text(label, modifier = Modifier.padding(4.dp))
    }
}
