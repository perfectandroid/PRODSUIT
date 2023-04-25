package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONObject

class ReportMainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var context: Context

    private var card_lead: CardView? = null
    private var card_service: CardView? = null
    private var card_collection: CardView? = null
    private var card_pickup: CardView? = null

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

        card_lead = findViewById<CardView>(R.id.card_lead)
        card_service = findViewById<CardView>(R.id.card_service)
        card_collection = findViewById<CardView>(R.id.card_collection)
        card_pickup = findViewById<CardView>(R.id.card_pickup)

        llLeadReport = findViewById<LinearLayout>(R.id.llLeadReport)
        llServiceReport = findViewById<LinearLayout>(R.id.llServiceReport)
        llLeadReport!!.setOnClickListener(this)
        llServiceReport!!.setOnClickListener(this)

        setLicence()
    }

    private fun setLicence() {
        val ModuleListSP = context.getSharedPreferences(Config.SHARED_PREF54, 0)
        val jsonObj = JSONObject(ModuleListSP.getString("ModuleList",""))
        var iLead = jsonObj!!.getString("LEAD")
        var iService = jsonObj!!.getString("SERVICE")
        var iCollection = jsonObj!!.getString("ACCOUNTS")
        var iPickUp = jsonObj!!.getString("DELIVERY")

        if(!iLead.equals("true")){
            card_lead!!.visibility = View.GONE
        }

        if(!iService.equals("true")){
            card_service!!.visibility = View.GONE
        }

        if(!iCollection.equals("true")){
            card_collection!!.visibility = View.GONE
        }

        if(!iPickUp.equals("true")){
            card_pickup!!.visibility = View.GONE
        }
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
               // Toast.makeText(this@ReportMainActivity, "Work in progess", Toast.LENGTH_SHORT).show()
                val i = Intent(this@ReportMainActivity, ServiceReportActivity::class.java)
                startActivity(i)
            }
        }
    }
}