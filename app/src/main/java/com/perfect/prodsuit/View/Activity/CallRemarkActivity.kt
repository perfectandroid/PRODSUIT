package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import java.util.*

class CallRemarkActivity : AppCompatActivity() , View.OnClickListener {

    val TAG : String = "CallRemarkActivity"
    lateinit var context: Context

    var llFromdate: LinearLayout? = null
    var llFromDatePick: LinearLayout? = null
    var llToDate: LinearLayout? = null
    var llToDatePick: LinearLayout? = null
    var llMentionDate: LinearLayout? = null
    var llMentionDatePick: LinearLayout? = null
    var llCallStatus: LinearLayout? = null
    var llRiskType: LinearLayout? = null

    var txtFromDate: TextView? = null
    var txtFromSubmit: TextView? = null
    var txtToDate: TextView? = null
    var txtToSubmit: TextView? = null
    var txtMentionDate: TextView? = null
    var txtMentionSubmit: TextView? = null
    var txtCallStatus: TextView? = null
    var txtRiskType: TextView? = null

    var imFromDate: ImageView? = null
    var imToDate: ImageView? = null
    var imMentionDate: ImageView? = null

    var datePickerFrom: DatePicker? = null
    var datePickerTo: DatePicker? = null
    var datePickerMention: DatePicker? = null

    var fromDateMode : String?= "1"  // GONE
    var toDateMode : String?= "1"  // GONE
    var MentionDateMode : String?= "1"  // GONE

    companion object {

        var strCallStatus : String?= "1"
        var strRiskType : String?= "1"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_call_remark)
        context = this@CallRemarkActivity

        setRegViews()
        removeData()
    }

    private fun removeData() {
        strCallStatus = ""
        strRiskType = ""
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        txtFromDate = findViewById(R.id.txtFromDate) as TextView
        txtFromSubmit = findViewById(R.id.txtFromSubmit) as TextView
        txtToDate = findViewById(R.id.txtToDate) as TextView
        txtToSubmit = findViewById(R.id.txtToSubmit) as TextView
        txtMentionDate = findViewById(R.id.txtMentionDate) as TextView
        txtMentionSubmit = findViewById(R.id.txtMentionSubmit) as TextView
        txtCallStatus = findViewById(R.id.txtCallStatus) as TextView
        txtRiskType = findViewById(R.id.txtRiskType) as TextView

        imFromDate = findViewById(R.id.imFromDate) as ImageView
        imToDate = findViewById(R.id.imToDate) as ImageView
        imMentionDate = findViewById(R.id.imMentionDate) as ImageView

        llFromdate = findViewById(R.id.llFromdate) as LinearLayout
        llFromDatePick = findViewById(R.id.llFromDatePick) as LinearLayout
        llToDate = findViewById(R.id.llToDate) as LinearLayout
        llToDatePick = findViewById(R.id.llToDatePick) as LinearLayout
        llMentionDate = findViewById(R.id.llMentionDate) as LinearLayout
        llMentionDatePick = findViewById(R.id.llMentionDatePick) as LinearLayout
        llCallStatus = findViewById(R.id.llCallStatus) as LinearLayout
        llRiskType = findViewById(R.id.llRiskType) as LinearLayout

        datePickerFrom = findViewById(R.id.datePickerFrom) as DatePicker
        datePickerTo = findViewById(R.id.datePickerTo) as DatePicker
        datePickerMention = findViewById(R.id.datePickerMention) as DatePicker

        llFromdate!!.setOnClickListener(this)
        llToDate!!.setOnClickListener(this)
        llMentionDate!!.setOnClickListener(this)
        llCallStatus!!.setOnClickListener(this)
        llRiskType!!.setOnClickListener(this)

        txtFromSubmit!!.setOnClickListener(this)
        txtToSubmit!!.setOnClickListener(this)
        txtMentionSubmit!!.setOnClickListener(this)

        imFromDate!!.setOnClickListener(this)
        imToDate!!.setOnClickListener(this)
        imMentionDate!!.setOnClickListener(this)

        datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis



    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llFromdate->{
                if (fromDateMode.equals("0")){
                    llFromDatePick!!.visibility = View.GONE
                    fromDateMode = "1"
                }else{
                    llFromDatePick!!.visibility = View.VISIBLE
                    fromDateMode = "0"
                }
                llToDatePick!!.visibility = View.GONE
                toDateMode = "1"
            }
            R.id.llToDate->{
                if (toDateMode.equals("0")){
                    llToDatePick!!.visibility = View.GONE
                    toDateMode = "1"
                }else{
                    llToDatePick!!.visibility = View.VISIBLE
                    toDateMode = "0"
                }
                llFromDatePick!!.visibility = View.GONE
                fromDateMode = "1"
            }
            R.id.llMentionDate->{
                if (MentionDateMode.equals("0")){
                    llMentionDatePick!!.visibility = View.GONE
                    MentionDateMode = "1"
                }else{
                    llMentionDatePick!!.visibility = View.VISIBLE
                    MentionDateMode = "0"
                }

            }

            R.id.imFromDate->{
                llFromDatePick!!.visibility = View.GONE
                fromDateMode = "1"
            }
            R.id.imToDate->{
                llToDatePick!!.visibility = View.GONE
                toDateMode = "1"
            }
            R.id.imMentionDate->{
                llMentionDatePick!!.visibility = View.GONE
                MentionDateMode = "1"
            }

            R.id.txtFromSubmit->{
                try {
                    datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerFrom!!.getDayOfMonth()
                    val mon: Int = datePickerFrom!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerFrom!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtFromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llFromDatePick!!.visibility=View.GONE
                    fromDateMode = "1"
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }
            }

            R.id.txtToSubmit->{
                try {
                    datePickerTo!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerTo!!.getDayOfMonth()
                    val mon: Int = datePickerTo!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerTo!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llToDatePick!!.visibility=View.GONE
                    toDateMode = "1"
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }
            }

            R.id.txtMentionSubmit->{
                try {
                    datePickerMention!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerMention!!.getDayOfMonth()
                    val mon: Int = datePickerMention!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerMention!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtMentionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llMentionDatePick!!.visibility=View.GONE
                    MentionDateMode = "1"
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }
            }

            R.id.llCallStatus->{

                getCallStatus()
            }
            R.id.llRiskType->{

                getRiskType()
            }


        }
    }

    private fun getRiskType() {
        try {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.riskstatus_popup, null)
            val lvRiskType  = layout.findViewById<ListView>(R.id.lvRiskType)
            builder.setView(layout)
            val alertDialog = builder.create()
            val listItem = resources.getStringArray(R.array.risk_type)
            val adapter = ArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, listItem
            )
            lvRiskType.setAdapter(adapter)
            lvRiskType.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
                // TODO Auto-generated method stub
                val value = adapter.getItem(position)
                txtRiskType!!.setText(value)
                if (position == 0) {
                    strRiskType = "1"
                }
                if (position == 1) {
                    strRiskType = "2"
                }
                if (position == 2) {
                    strRiskType = "3"
                }
                alertDialog.dismiss()
            })
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCallStatus() {
        try {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.callstatus_popup, null)
            val lvCallStatus  = layout.findViewById<ListView>(R.id.lvCallStatus)
            builder.setView(layout)
            val alertDialog = builder.create()
            val listItem = resources.getStringArray(R.array.callstatus)
            val adapter = ArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, listItem
            )
            lvCallStatus.setAdapter(adapter)
            lvCallStatus.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
                // TODO Auto-generated method stub
                val value = adapter.getItem(position)
                txtCallStatus!!.setText(value)
                if (position == 0) {
                    strCallStatus = "1"
                }
                if (position == 1) {
                    strCallStatus = "2"
                }
                if (position == 2) {
                    strCallStatus = "3"
                }
                alertDialog.dismiss()
            })
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}