package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.perfect.prodsuit.R

class ReminderShowActivity : AppCompatActivity() {

    var TAG = "ReminderShowActivity"
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_reminder_show)
        context = this@ReminderShowActivity


        var title: String? = intent.getStringExtra("title")
        var message: String? = intent.getStringExtra("message")
        var date: String? = intent.getStringExtra("date")
        var time: String? = intent.getStringExtra("time")

        Log.e(TAG,"255551   "+title+"  :  "+message+"  :  "+date+"  :  "+time)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}