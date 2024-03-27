package com.perfect.prodsuit.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast

class DeveloperOptionsReceiver : BroadcastReceiver() {

    var TAG = "DeveloperOptionsReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Toast.makeText(context,"1777773  true"+action, Toast.LENGTH_SHORT).show()
        Log.e(TAG,"1777771   "+action)
        var isUsbDebuggingEnabled =   Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1

        if (isUsbDebuggingEnabled){
            Toast.makeText(context,"1777774  true"+isUsbDebuggingEnabled, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"1777774  false"+isUsbDebuggingEnabled, Toast.LENGTH_SHORT).show()
        }


//        if (action == Settings.Global.ADB_ENABLED) {
//            val adbEnabled = Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0)
//
//            if (adbEnabled == 1) {
//                // USB debugging (and possibly developer options) is enabled
//                // Do something here
//                Toast.makeText(context,"1777771  true", Toast.LENGTH_SHORT).show()
//                Log.e(TAG,"1777771   "+adbEnabled)
//
//            } else {
//                // USB debugging (and possibly developer options) is disabled
//                // Do something here
//                Toast.makeText(context,"1777772  true", Toast.LENGTH_SHORT).show()
//                Log.e(TAG,"1777772   "+adbEnabled)
//            }
//        }
    }
}