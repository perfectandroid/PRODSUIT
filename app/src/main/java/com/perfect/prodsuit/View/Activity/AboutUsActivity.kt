package com.perfect.prodsuit.View.Activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.perfect.prodsuit.BuildConfig
import com.perfect.prodsuit.R

class AboutUsActivity : AppCompatActivity() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setRegViews()
    }

    private fun setRegViews() {
        val tvVersionid = findViewById<TextView>(R.id.tvVersionid)
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tvVersionid.text="Version : "+ BuildConfig.VERSION_NAME
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }
}