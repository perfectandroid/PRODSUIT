package com.perfect.prodsuit.fire

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.perfect.prodsuit.Helper.DeviceHelper
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.SplashActivity
import com.perfect.prodsuit.View.Service.NotificationEvent

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "ForegroundService Kotlin"
    var TAG = "MyFirebaseMessagingService"
    lateinit var context: Context
    override fun onNewToken(token: String) {
        Log.e(TAG,"Token  999900    "+token)
     //   val deviceId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
//        val deviceId: String = DeviceHelper.getDeviceID(context)
//        Log.e(FireBaseConfig.TAG,"uniqueId  99991    "+ deviceId)
//
//        FireBaseConfig.checkUserToken(context, token, deviceId)
        //Called whenever a new device runs the Android application. Registers in FCM and in PubNub.
    }

    //Handle when the device has received a mobile push notification from FCM.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        super.onMessageReceived(remoteMessage)

        Log.e(TAG,"remoteMessage  999900    "+remoteMessage)
        context = this
        if (!remoteMessage.data.isEmpty()) {
            Log.e(TAG,"remoteMessage  9999001    "+remoteMessage.data.get("title"))
            Log.e(TAG,"remoteMessage  9999001    "+remoteMessage.data.get("body"))

            createNotificationChannel()
            showNotification(remoteMessage)
        }




//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG,"remoteMessage  999900    "+remoteMessage.notification!!.title)
//            Log.e(TAG,"remoteMessage  999900    "+remoteMessage.notification!!.body)
//
//
//            createNotificationChannel()
//            showNotification(remoteMessage.notification!!.title,remoteMessage.notification!!.body)
//
//        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun showNotification(remoteMessage : RemoteMessage) {
        try {
//            val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
//            Log.e(TAG,"remoteMessage  000    "+remoteMessage.data.get("title"))
//            val notificationIntent = Intent(this, NotificationActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
//            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle(remoteMessage.data.get("title"))
//                .setContentText(remoteMessage.data.get("body"))
//                .setSmallIcon(R.drawable.applogo)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setSound(soundUri)
//                .build()
//
//            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            manager.notify(123, notification)


            ////////////

//            val notificationIntent = Intent(this, NotificationActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
//
//
//
//            val contentView = RemoteViews(packageName, R.layout.custom_notification_layout)
//            val expandedView = RemoteViews(packageName, R.layout.custom_notification_layout)
//
//// Set the content view of the notification to the custom layout
//            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.applogo)
//               // .setContent(contentView)
//                .setCustomBigContentView(expandedView)
//                .setContentText("")
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//// Customize the content view by accessing its views
//            expandedView.setImageViewResource(R.id.notificationIcon, R.drawable.applogo)
//            expandedView.setTextViewText(R.id.notificationTitle, remoteMessage.data.get("title"))
//            expandedView.setTextViewText(R.id.notificationContent, remoteMessage.data.get("body"))
//
//            if (Build.VERSION.SDK_INT >= 23) {
//                notificationBuilder.setColor(ContextCompat.getColor(this,R.color.grey));
//            } else {
//                notificationBuilder.setColor(R.color.grey)
//            }
//
//
////            val yesReceive = Intent()
////            yesReceive.action = "my.custom.broadcast"
////            val pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_IMMUTABLE)
////            notificationBuilder.addAction(R.drawable.icon_svg_contactno, "Click", pendingIntentYes)
//
//// Show the notification
//            val notificationManager = NotificationManagerCompat.from(this)
//            notificationManager.notify(123, notificationBuilder.build())



///////////////////////////////////////


            val notificationIntent = Intent(this, SplashActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

//            val notificationIntent = Intent(context, NotificationEvent::class.java)
//            notificationIntent.action = "my.custom.ACTION_NOTIFICATION_CLICK"
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                notificationIntent,
//                PendingIntent.FLAG_IMMUTABLE
//            )

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle(remoteMessage.data.get("title"))
                .setContentText(remoteMessage.data.get("body"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(123, notificationBuilder.build())





//            val largeIconBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bell)

// Build the notification

// Build the notification
//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Notification Title")
//                .setContentText("Notification Content")
//                .setLargeIcon(largeIconBitmap)
//                .setAutoCancel(true)
//
//// Show the notification
//
//// Show the notification
//            val notificationManager = NotificationManagerCompat.from(this)
//            notificationManager.notify(123, builder.build())



            /////////////////////////////


//            val notificationIntent = Intent(this, NotificationActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
//            val notification= NotificationCompat.Builder(this, channelId)
////            .setContentText(input)
//                .setSmallIcon(R.drawable.email)
//                .setContentIntent(pendingIntent)
//                .build()
//            startForeground(1, notification)


//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
//                .setContentTitle("Notifications Example")
//                .setContentText("This is a test notification")
//
//            val notificationIntent = Intent(this, HomeActivity::class.java)
//            val contentIntent = PendingIntent.getActivity(
//                this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            builder.setContentIntent(contentIntent)
//
//            // Add as notification
//
//            // Add as notification
//            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            manager.notify(0, builder.build())



//            val intent = Intent(this, HomeActivity::class.java)
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_IMMUTABLE)
//
//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "Web")
//                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
//                .setContentTitle("Notification Title")
//                .setContentText("Notification Text")
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//
//            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            manager.notify(0, builder.build())


//            val builder = NotificationCompat.Builder(this)
//            builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"))
//            val pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT)
//            builder.setContentIntent(pendingIntent)
//          //  builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
//
//            builder.setContentTitle("Notifications Title")
//            builder.setContentText("Your notification content here.")
//            builder.setSubText("Tap to view the website.")
//
//            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//
//            // Will display the notification in the notification bar
//
//            // Will display the notification in the notification bar
//            notificationManager.notify(1, builder.build())






        }catch (e : Exception){
            Log.e(TAG,"Exception  9999    "+e.toString())
        }

    }
}