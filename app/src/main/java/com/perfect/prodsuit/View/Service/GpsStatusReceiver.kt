package com.perfect.prodsuit.View.Service

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONObject
import java.util.*

class GpsStatusReceiver : BroadcastReceiver(), TextToSpeech.OnInitListener {
    var TAG = "GpsStatusReceiver"
    private val checkInterval = 1000L // Check interval in milliseconds (e.g., every 5 seconds)
    private val timer = Timer()
    private var isGpsEnabled = false
    private var isChecking = false

    private val notificationChannelId = "LOCATION_SETTINGS_CHANNEL"
    private val notificationId = 123
    private val NOTIFICATION_ID = 1
    private var tts: TextToSpeech? = null
    lateinit var contexts: Context

    override fun onReceive(context: Context?, intent: Intent?) {
       // if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
        tts = TextToSpeech(context, this)
            startGpsStatusCheck(context)
     //   Toast.makeText(context!!,"GpsStatusReceiver  Start ", Toast.LENGTH_SHORT).show()
      //  }
    }

    fun startGpsStatusCheck(context: Context?) {
//        if (!isChecking) {
//            isChecking = true
        Log.e(TAG,"60000wq   :  startGpsStatusCheck")
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {

                    val UtilityListSP = context!!.getSharedPreferences(Config.SHARED_PREF57, 0)
                    val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
                    var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
                    Log.e(TAG,"600002    :   bTracker  "+bTracker)
                    if (bTracker){
                        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val newGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

//                    if (isGpsEnabled != newGpsEnabled) {
                        if (newGpsEnabled) {
                            Log.e(TAG,"600007    GPS is Cgeh  "+newGpsEnabled+" : "+isGpsEnabled)
                            isGpsEnabled = newGpsEnabled

                            // Run UI-related code on the main thread
                            Handler(Looper.getMainLooper()).post {
                                if (isGpsEnabled) {
                                    Log.e(TAG,"600008    GPS is isGpsEnabled")
                                    var notificationManager = NotificationManagerCompat.from(context)
                                    notificationManager.cancel(notificationId)

                                    startForegroundLocationService(context)
                                } else {
                                    Log.e(TAG,"600009    GPS is isGpsDisabled")
                                    // Perform actions when GPS is disabled

                                    stopForegroundLocationService(context)
                                    createNotificationChannel(context)
                                    showNotification(context)
                                }
                            }
                        }else{
                            Log.e(TAG,"6000010    GPS is disabled")
                            stopForegroundLocationService(context)
                            createNotificationChannel(context)
                            showNotification(context)
                        }

                    }else{
                        stopForegroundLocationService(context)
                    }



                }
            }, 1000L, checkInterval)
//        }else{
//
//        }
    }



    private fun openLocationSettings(context: Context) {
        try {
            Log.e(TAG,"1478521001    GPS is disabled")
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)

        }catch (e  :Exception){
            Log.e(TAG,"1478521002    GPS is disabled")
        }

    }

    private fun createNotificationChannel(context: Context) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel only on API 26+ (Android 8.0 and above)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId,
                "Location Settings", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun showNotification(context: Context) {
//        val intent = Intent(context, LocationSettingsActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        //context.startActivity(intent)
        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle("Location Settings")
            .setContentText("Tap to open location settings")
            .setSmallIcon(R.drawable.ic_location)
            .setSilent(true)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notification)

        textToSpeech(context)

      //  showAlert(context)
    }

    private fun textToSpeech(context: Context) {

    }


    private fun startForegroundLocationService(context: Context?) {
        val serviceIntent = Intent(context, ForegroundLocationService::class.java)
        context?.startForegroundService(serviceIntent)
    }

    private fun stopForegroundLocationService(context: Context) {
        val serviceIntent = Intent(context, ForegroundLocationService::class.java)
        context?.stopService(serviceIntent)
    }

    private fun showAlert(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to exit?").setCancelable(
            false
        ).setPositiveButton("Yes",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            }).setNegativeButton("No",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show()
            } else {
              //  tts?.speak("Hello", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
           // Toast.makeText(context, "Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }
}