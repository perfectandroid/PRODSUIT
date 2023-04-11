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
import android.widget.Toast
import com.perfect.prodsuit.R

class ReportMainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var context: Context

    private var llLeadReport: LinearLayout? = null
    private var llServiceReport: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_report_main)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_report_main)
        context=this@ReportMainActivity
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llLeadReport = findViewById<LinearLayout>(R.id.llLeadReport)
        llServiceReport = findViewById<LinearLayout>(R.id.llServiceReport)
        llLeadReport!!.setOnClickListener(this)
        llServiceReport!!.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llLeadReport->{
                val i = Intent(this@ReportMainActivity, TicketReportActivity::class.java)
                startActivity(i)
            }
            R.id.llServiceReport->{
                Toast.makeText(this@ReportMainActivity, "Work in progess", Toast.LENGTH_SHORT).show()
            }
        }
    }
}