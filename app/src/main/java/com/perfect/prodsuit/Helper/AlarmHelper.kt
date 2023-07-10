package com.perfect.prodsuit.Helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.perfect.prodsuit.Receivers.ReminderReceiver
import java.util.*

object AlarmHelper {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun setReminder(context: Context, hr: Int, min: Int){
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent for the BroadcastReceiver
        val intent = Intent(context, ReminderReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        // Set the specific date and time for the reminder
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, Calendar.JULY)
        calendar.set(Calendar.DAY_OF_MONTH, 7)
        calendar.set(Calendar.HOUR_OF_DAY, hr)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)

        // Schedule the reminder
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "Reminder Set!", Toast.LENGTH_SHORT).show()
    }
}