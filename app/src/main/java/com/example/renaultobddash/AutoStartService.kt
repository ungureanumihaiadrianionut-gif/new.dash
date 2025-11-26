
package com.example.renaultobddash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AutoStartService: Service() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(NotificationChannel("obd", "OBD Service", NotificationManager.IMPORTANCE_LOW))
        }
        startForeground(9001, NotificationCompat.Builder(this, "obd")
            .setContentTitle("Renault OBD Dash")
            .setContentText("Service running")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .build())
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
