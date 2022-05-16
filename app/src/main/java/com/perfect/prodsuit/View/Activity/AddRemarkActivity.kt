package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.R
import java.lang.Long
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddRemarkActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "AddRemarkActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    var shortTimeStr:String?=""
    var strCallStatus:String?=""
    var strRiskType:String?=""

    var tie_Date: TextInputEditText? = null
    var tie_Time: TextInputEditText? = null
    var tie_CallStatus: TextInputEditText? = null
    var tie_CallDuration: TextInputEditText? = null
    var tie_RiskType: TextInputEditText? = null
    var tie_CustomerMentionDate: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_remark)
        context = this@AddRemarkActivity

        setRegViews()
        getCallDetails()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        tie_Time = findViewById<TextInputEditText>(R.id.tie_Time)
        tie_CallStatus = findViewById<TextInputEditText>(R.id.tie_CallStatus)
        tie_CallDuration = findViewById<TextInputEditText>(R.id.tie_CallDuration)
        tie_RiskType = findViewById<TextInputEditText>(R.id.tie_RiskType)
        tie_CustomerMentionDate = findViewById<TextInputEditText>(R.id.tie_CustomerMentionDate)

        tie_RiskType!!.setOnClickListener(this)
        tie_CustomerMentionDate!!.setOnClickListener(this)
    }

    private fun getCallDetails() {

        try {
            val sb = StringBuffer()
            val contacts: Uri = CallLog.Calls.CONTENT_URI
            val managedCursor: Cursor? = context.contentResolver.query(contacts, null, null, null, CallLog.Calls.DATE + " DESC limit 1")
            val number: Int = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
            val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
            val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
            val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
            sb.append("Call Details :")
            while (managedCursor.moveToNext()) {
                val rowDataCall = HashMap<String, String>()
                val phNumber: String = managedCursor.getString(number)
                val callType: String = managedCursor.getString(type)
                val callDate: String = managedCursor.getString(date)
                val callDayTime: String = Date(Long.valueOf(callDate)).toString()
                // long timestamp = convertDateToTimestamp(callDayTime);
                val callDuration: String = managedCursor.getString(duration)
                var dir: String? = null
                val dircode = callType.toInt()
                when (dircode) {
                    CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
                    CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
                    CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
                }
                sb.append("\nPhone Number:--- $phNumber \nCall Type:--- $dir \nCall Date:--- $callDayTime \nCall duration in sec :--- $callDuration")
                sb.append("\n----------------------------------")


                var date: Date? = null
                val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
                val temp = callDayTime
                try {
                    date = formatter.parse(temp)
                    val sdf = SimpleDateFormat("hh:mm aa")
                    shortTimeStr = sdf.format(date)
                    Log.e("formated date ", date.toString() + "")
                } catch (e: ParseException) {
                    e.printStackTrace()
                }


                val formateDate = SimpleDateFormat("dd-MM-yyyy").format(date)
                tie_Date!!.setText(formateDate)
                tie_Time!!.setText(shortTimeStr)


                tie_CallDuration!!.setText(callDuration/*+"(in Second)"*/)
                if(callDuration.equals("0"))
                {
                    strCallStatus = "2"
                    tie_CallStatus!!.setText("Not Connected")
                }
                else {
                    strCallStatus = "1"
                    tie_CallStatus!!.setText("Attended")

                }
            }
            managedCursor.close()

            Log.e(TAG,"CALL DETAILS  5061   "+sb)
        }catch (e : Exception){
            Log.e(TAG,"CALL DETAILS  5062   "+e.toString())
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_RiskType->{

                getRiskType()
            }
            R.id.tie_CustomerMentionDate->{
                openBottomSheet()
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
                tie_RiskType!!.setText(value)
                if (position == 0) {
                    SiteVisitActivity.strRiskType = "1"
                }
                if (position == 1) {
                    SiteVisitActivity.strRiskType = "2"
                }
                if (position == 2) {
                    SiteVisitActivity.strRiskType = "3"
                }
                alertDialog.dismiss()
            })
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openBottomSheet() {
      // BottomSheet
    }



}