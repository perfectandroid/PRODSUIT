package com.perfect.prodsuit.Receivers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.NotificationActivity
import com.perfect.prodsuit.View.Activity.ReminderShowActivity
import com.perfect.prodsuit.View.Activity.SplashActivity
import java.text.SimpleDateFormat
import java.util.*

class MyAlarmReceiver : BroadcastReceiver() {
    var TAG = "MyAlarmReceiver"
    private var channelId = "my_channel_id"
    private val channelName = "my_channel_id"

    override fun onReceive(context: Context?, intent: Intent?) {
      //  Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show()
        val requestCode = intent?.getIntExtra("REQUEST_CODE", -1)
        val message = intent?.getStringExtra("message")
        val date = intent?.getStringExtra("date")
        val title = intent?.getStringExtra("title")
        val time = intent?.getStringExtra("time")
        val dateShow = intent?.getStringExtra("dateShow")
        val timeShow = intent?.getStringExtra("timeShow")
        Log.e(TAG,"1999      Alarm $requestCode triggered!")
        if (requestCode != -1) {
            // Handle the alarm based on the requestCode
//            Toast.makeText(context, "Alarm $requestCode triggered!", Toast.LENGTH_SHORT).show()
            Log.e(TAG,"1999      Alarm $requestCode triggered!   "+"    Message  :  $message")

            notify(context!!,message,date!!,time!!,requestCode!!,dateShow,timeShow)
        }
    }

    private fun notify(context :  Context , message: String?,date: String?,time: String?, chId: Int,dateShow: String?,timeShow: String?) {

        channelId = chId.toString()
        createNotificationChannel(context, channelId, "My Channel", message!!)

        val notificationTitle = context.resources.getString(R.string.app_name)
        val notificationMessage = "Hello, this is a notification!"

        showNotification(context, channelId, notificationTitle,message!!,date!!,time!!, chId,dateShow,timeShow)
    }

    fun showNotification(context: Context, channelId: String, title: String, message: String,date: String?,time: String?, notificationId: Int,dateShow: String?,timeShow: String?) {

//        val intent: Intent = Intent(context, NotificationActivity::class.java)
////        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        Log.e(TAG,"255550   "+title+"  :  "+message+"  :  "+date+"  :  "+time)

//        val notificationIntent = Intent(context, ReminderShowActivity::class.java)
//        notificationIntent.putExtra("title",title)
//        notificationIntent.putExtra("message",message)
//        notificationIntent.putExtra("date",date)
//        notificationIntent.putExtra("time",time)
//        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        //   val currentTime1 = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())
        val currentTime1 = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date())

     //   var mmmm = dateShow+"  "+timeShow
        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        var mmmm = currentDate1+"  "+currentTime1
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.applogo)
          //  .setContentTitle(title)
            .setContentTitle(mmmm)
            //.setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setStyle(NotificationCompat.BigTextStyle()// <-- Look here
                //.bigText("\n"+"With 22 years of empowering businesses worldwide with our software expertise. We are a trusted partner for major banking and finance sectors in Kerala. Other than banking, our knowledge and expertise have been honed by working with some of the biggest names in the education sector. Our software expertise sets us apart! We are dedicated to improving customer experience through the use of technology. We offer innovative solutions to streamline processes and increase efficiency, which \u200Csaves you time and resources."))// <---- Look here
                .bigText(message))// <---- Look here
           // .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
    private fun createNotificationChannel(context: Context, channelId: String, s: String, s1: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = s1
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}