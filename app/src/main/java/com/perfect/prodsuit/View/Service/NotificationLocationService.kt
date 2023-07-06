package com.perfect.prodsuit.View.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONObject
import java.util.*


class NotificationLocationService : Service() {

    private val channelId = "Notification from Service"
    private var TAG = "NotificationLocationService"
    private lateinit var timer: Timer
    lateinit var context: Context
    lateinit var i: Intent
    private var count = 1


    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate() {
        Log.e("TAG","welcome 000  ")
        super.onCreate()
        context = this
        timer = Timer()
       // if (Build.VERSION.SDK_INT >= 26) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            } else {
                TODO("VERSION.SDK_INT < O")

            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
       // }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        i = intent
//        val input = "inputExtra"
////        Log.e("TAG","welcome 33  "+input)
        sendNotification()
        startTimer()

//        try {
//            val isMyServiceRunning = Config.isServiceRunning(context, LocationUpdateService::class.java)
//            if (!isMyServiceRunning){
//                val serviceIntent = Intent(context, LocationUpdateService::class.java)
//                context.startService(serviceIntent)
//            }
//        }catch (e : Exception){
//            Log.e(TAG,"11111   "+e.toString())
//        }


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

//        val notificationIntent = Intent(this, NotificationActivity::class.java)
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
//        val notification= NotificationCompat.Builder(this, channelId)
////            .setContentText(input)
//            .setSilent(false)
//            .setSmallIcon(R.drawable.email)
//            .setContentIntent(pendingIntent)
//            .build()
//        count = count+1
//        startForeground(124, notification)

        Log.e(TAG,"sendNotification  103     ")

        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(""+resources.getString(R.string.app_name))
            .setContentText("Running...")
            .setSmallIcon(R.drawable.applogo)
            .setSilent(true)
            .setContentIntent(null)
            .build()
        startForeground(NOTIFICATION_ID, notification)

        try {
            val intent = Intent(this, LocationReceiver::class.java)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.action = "my.custom.broadcast"
            sendBroadcast(intent)
        }catch (e: Exception){

        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun startTimer() {
        Log.v("sdfdsfdsddd","startTimer")
        Log.e(TAG,"NotificationService   startTimer")
        TIMER_INTERVAL = Config.getMilliSeconds(applicationContext).toLong()
        Log.e(TAG,"TIMER_INTERVAL   TIMER_INTERVAL  "+TIMER_INTERVAL)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
                val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
                var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
                Log.e(TAG,"15551   "+bTracker)
                if (bTracker){
                    Log.e(TAG,"15552   "+bTracker)
                    sendNotification()
                }else{
                    Log.e(TAG,"15553   "+bTracker)
                    stopForeground(true)
                }



            }
        }, 0, TIMER_INTERVAL)
    }

    private fun stopTimer() {
        Log.e(TAG,"NotificationService   stopTimer")
        timer.cancel()
    }

    companion object {

        private const val CHANNEL_ID = "LOCATION_SETTINGS_CHANNEL"
        private const val NOTIFICATION_ID = 1
        private var TIMER_INTERVAL: Long = 30000 // 10 second
    }
}