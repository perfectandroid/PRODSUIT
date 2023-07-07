package com.perfect.prodsuit.Helper

import android.content.Context
import android.provider.Settings
import java.lang.Exception

object DeviceHelper {

    fun getDeviceID(context: Context) : String{
        var deviceID = ""
        try {
            deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }catch (e: Exception){

        }
        return  deviceID
    }
}