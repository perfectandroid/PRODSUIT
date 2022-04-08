package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import java.text.SimpleDateFormat
import java.util.*

class TicketReportActivity : AppCompatActivity() , View.OnClickListener {

    private var chipNavigationBar: ChipNavigationBar? = null
    var date_Picker: DatePicker? = null
    var txtok: TextView? = null
    var date_Picker1: DatePicker? = null
    var txtok1: TextView? = null
    var txtfromDate: TextView? = null
    var txttoDate: TextView? = null
    var ll_Fromdate: LinearLayout? = null
    var llfromdate: LinearLayout? = null
    var ll_Todate: LinearLayout? = null
    var lltodate: LinearLayout? = null
    var imclose: ImageView? = null
    var im_close: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketreport)
        setRegViews()
        bottombarnav()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        txttoDate!!.text = currentDate
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imclose = findViewById(R.id.imclose)
        im_close = findViewById(R.id.im_close)
        llfromdate = findViewById(R.id.llfromdate)
        ll_Fromdate = findViewById(R.id.ll_Fromdate)
        txtfromDate = findViewById(R.id.txtfromDate)
        txttoDate = findViewById(R.id.txttoDate)
        lltodate = findViewById(R.id.lltodate)
        date_Picker = findViewById(R.id.date_Picker)
        txtok = findViewById(R.id.txtok)
        ll_Todate = findViewById(R.id.ll_Todate)
        date_Picker1 = findViewById(R.id.date_Picker1)
        txtok1 = findViewById(R.id.txtok1)
        txtok1!!.setOnClickListener(this)
        llfromdate!!.setOnClickListener(this)
        lltodate!!.setOnClickListener(this)
        imclose!!.setOnClickListener(this)
        txtok!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        im_close!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llfromdate->{
                ll_Fromdate!!.visibility=View.VISIBLE
            }
            R.id.im_close->{
                ll_Fromdate!!.visibility=View.GONE
            }
            R.id.lltodate->{
                ll_Todate!!.visibility=View.VISIBLE
            }
            R.id.imclose->{
                ll_Todate!!.visibility=View.GONE
            }
            R.id.txtok1->{
                date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }
                txttoDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                ll_Todate!!.visibility=View.GONE
            }
            R.id.txtok->{
                date_Picker!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }
                txtfromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                ll_Fromdate!!.visibility=View.GONE
            }
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@TicketReportActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@TicketReportActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@TicketReportActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}