package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.perfect.prodsuit.R

class SiteVisitActivity : AppCompatActivity(), View.OnClickListener  {

    val TAG : String = "SiteVisitActivity"
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_site_visit)
        context = this@SiteVisitActivity

        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        imback!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
       when(v.id){

           R.id.imback->{
               finish()
           }
       }
    }
}