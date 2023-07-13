package com.perfect.prodsuit.fire

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.View.Activity.NotificationActivity
import com.perfect.prodsuit.View.Activity.SplashActivity

class NotificationHelper ( val context: Context) {

    var TAG = "NotificationHelper"

    fun getNotificationHelper(){

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = activityManager.getRunningTasks(1)
//        val isActivityOpen = runningTasks.isNotEmpty()
//        if (isActivityOpen){
//            Log.e(TAG,"17777001   isActivityOpen   "+isActivityOpen)
//
//            val topActivity = runningTasks[0].topActivity
//            val className = topActivity!!.className
//
//            Log.e("TAG","17777002   topActivity   "+topActivity)
//            Log.e("TAG","17777003   className   "+className)
//            var substring = ""
//
//            val lastDotIndex = className.lastIndexOf(".")
//            if (lastDotIndex != -1 && lastDotIndex < className.length - 1) {
//                substring = className.substring(lastDotIndex + 1)
//                // Use the substring
//                Log.e("Substring  177770031   ", substring) // Output: "txt"
//            }
//
//            if (substring.equals("SplashActivity")){
//
//                Config.setRedirection(context,"Home")
//
//
//            }else if (substring.equals("MpinActivity")){
//
//               // gotoSplash()
//                Config.setRedirection(context,"Home")
//
//            }else if (substring.equals("LocationEnableActivity")){
//              //  gotoSplash()
//
//            }else{
//
//                Config.setRedirection(context,"")
//
//                val i = Intent(context, NotificationActivity::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context.startActivity(i)
//            }
//
//
//        }else{
//
//            Log.e(TAG,"17777004   isActivityClosed   "+isActivityOpen)
//
//            gotoSplash()
//        }

        gotoSplash()
    }



    private fun gotoSplash() {

        Config.setRedirection(context,"Home")

        val i = Intent(context, SplashActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

}