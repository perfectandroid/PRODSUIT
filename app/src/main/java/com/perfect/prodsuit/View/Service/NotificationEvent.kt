package com.perfect.prodsuit.View.Service

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.perfect.prodsuit.View.Activity.NotificationActivity
import com.perfect.prodsuit.fire.NotificationHelper


class NotificationEvent : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.e("TAG","1111112222   Action   "+intent!!.getAction())

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.getNotificationHelper()

        val isAppOpen: Boolean = isApplicationSentToBackground(context!!)
        Log.e("TAG","1111112222   isAppOpen   "+isAppOpen)

//        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
//        val isIdle = powerManager.isDeviceIdleMode
//        val isAppForeground = powerManager.isInteractive
//
//        Log.e("TAG","1111112222   isAppForeground   "+isAppForeground)
//        Log.e("TAG","1111112222   isIdle   "+isIdle)

//        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningTasks = activityManager.getRunningTasks(1)
//        val isActivityOpen = runningTasks.isNotEmpty()
//
//        Log.e("TAG","1111112222   isActivityOpen   "+isActivityOpen)
//        if (isActivityOpen){
//            val topActivity = runningTasks[0].topActivity
//            val className = topActivity!!.className
//
//            Log.e("TAG","1111112222   topActivity   "+topActivity)
//            Log.e("TAG","1111112222   className   "+className)
//            Log.e("TAG","11111122221   isActivityOpen   "+isActivityOpen)
//
////            val i = Intent(context, NotificationActivity::class.java)
////            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////            context.startActivity(i)
//        }
//        else{
//            Log.e("TAG","1111112222   isActivityColed   "+isActivityOpen)
////            val activityIntent = Intent(context, SplashActivity::class.java)
////            context.startActivity(activityIntent)
//
////            val i = Intent(context, SplashActivity::class.java)
////            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
////            context.startActivity(i)
//
////            val intent = Intent(context, SplashActivity::class.java)
////            intent.action = Intent.ACTION_MAIN
////            intent.addCategory(Intent.CATEGORY_LAUNCHER)
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
////            context.startActivity(pendingIntent!!);
//
////            val i: Intent = Intent(context, SplashActivity::class.java)
////            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
////            i.addCategory(Intent.CATEGORY_LAUNCHER);
////            context.startActivity(i)
//
////            val pm: PackageManager = context.packageManager
////            val launchIntent: Intent? = pm.getLaunchIntentForPackage(context.packageName)
////            context.startActivity(launchIntent)
//            try {
////                val i = Intent()
////                i.setClassName("com.perfect.prodsuit", "com.perfect.prodsuit.View.Activity.SplashActivity")
////                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                context.startActivity(i)
//
////                val i = Intent("com.perfect.prodsuit.View.Activity.SplashActivity")
////                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                context.startActivity(i)
//
//
//            }catch (e : Exception){
//                Log.e("TAG","1111112222   Exception   "+e.toString())
//            }
//
//
//        }

    }
}

fun isApplicationSentToBackground(context: Context): Boolean {
    val am: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val tasks: List<ActivityManager.RunningTaskInfo> = am.getRunningTasks(1)
    if (!tasks.isEmpty()) {
        val topActivity: ComponentName? = tasks[0].topActivity
        if (!topActivity!!.getPackageName().equals(context.packageName)) {
            return true
        }
    }
    return false
}