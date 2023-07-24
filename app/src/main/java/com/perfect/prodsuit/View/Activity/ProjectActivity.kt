package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.perfect.prodsuit.R

class ProjectActivity : AppCompatActivity() , View.OnClickListener  {

    val TAG : String = "ProjectActivity"
    lateinit var context: Context

    private var llSiteVisit: LinearLayout? = null
    private var llMaterialUsage: LinearLayout? = null
    private var llProjectFollowup: LinearLayout? = null
    private var llMaterialRequest: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project)
        context = this@ProjectActivity

        setRegViews()


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llSiteVisit = findViewById(R.id.llSiteVisit)
        llMaterialUsage = findViewById(R.id.llMaterialUsage)
        llProjectFollowup = findViewById(R.id.llProjectFollowup)
        llMaterialRequest = findViewById(R.id.llMaterialRequest)

        llSiteVisit!!.setOnClickListener(this)
        llMaterialUsage!!.setOnClickListener(this)
        llProjectFollowup!!.setOnClickListener(this)
        llMaterialRequest!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llSiteVisit->{
                val i = Intent(this@ProjectActivity, ProjectSiteVisitActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            R.id.llMaterialUsage->{

            }
            R.id.llProjectFollowup->{

            }
            R.id.llMaterialRequest->{

            }
        }
    }
}