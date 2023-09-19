package com.perfect.prodsuit.Helper

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.VoiceRecordingActivity

class PlayerService : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, VoiceRecordingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Player Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(2, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}