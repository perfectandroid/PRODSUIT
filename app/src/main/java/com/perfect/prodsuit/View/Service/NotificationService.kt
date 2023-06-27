package com.perfect.prodsuit.View.Service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.NotificationActivity
import com.perfect.prodsuit.View.lifes.MyApp
import org.json.JSONObject
import java.util.*

class NotificationService : Service() {

    private val channelId = "Notification from Service"
    private var TAG = "NotificationService"
    private lateinit var timer: Timer
    private var count = 1
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate() {
        Log.e("TAG","welcome 000  ")
        super.onCreate()
        timer = Timer()
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

//        val input = "inputExtra"
////        Log.e("TAG","welcome 33  "+input)
        sendNotification()
        startTimer()
//        }
//        val notificationIntent = Intent(this, NotificationActivity::class.java)
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
//        val notification= NotificationCompat.Builder(this, channelId)
////            .setContentText(input)
//            .setSmallIcon(R.drawable.email)
//            .setContentIntent(pendingIntent)
//            .build()
//        count = count+1
//        startForeground(count, notification)
//        startTimer()
        return START_NOT_STICKY


    }


    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun sendNotification(){
        val notificationIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        val notification= NotificationCompat.Builder(this, channelId)
//            .setContentText(input)
            .setSmallIcon(R.drawable.email)
            .setContentIntent(pendingIntent)
            .build()
        count = count+1
        startForeground(1, notification)
    }

    private fun startTimer() {
        Log.v("sdfdsfdsddd","startTimer")
        Log.e(TAG,"NotificationService   startTimer")
        TIMER_INTERVAL = Config.getMilliSeconds(applicationContext).toLong()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                Log.v("sdfdsfdsddd","run")
                // Perform the desired task here
                var isFor = Config.isAppInForeground(applicationContext)
                Log.e(TAG,"NotificationService  isFor  :   "+isFor)
                Log.e("NotificationService  123454  :  ", "Timer task executed")

                sendNotification()
            }
        }, 0, TIMER_INTERVAL)
    }

    private fun stopTimer() {
        Log.e(TAG,"NotificationService   stopTimer")
        timer.cancel()
    }

    companion object {

        private var TIMER_INTERVAL: Long = 30000 // 10 second
    }
}