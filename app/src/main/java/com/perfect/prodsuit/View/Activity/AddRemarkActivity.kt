package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ExpenseAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
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
    var btnSubmit: Button? = null
    var tie_Date: TextInputEditText? = null
    var tie_AgentNote: TextInputEditText? = null
    var tie_CustomerNote: TextInputEditText? = null
    var tie_Time: TextInputEditText? = null
    var tie_CallStatus: TextInputEditText? = null
    var tie_CallDuration: TextInputEditText? = null
    var tie_RiskType: TextInputEditText? = null
    var tie_CustomerMentionDate: TextInputEditText? = null
    lateinit var addRemarkViewModel: AddremarkViewModel
    companion object{

        var agentnote = ""
        var customernote = ""

    }


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

        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit!!.setOnClickListener(this)

        tie_AgentNote = findViewById<TextInputEditText>(R.id.tie_AgentNote)
        tie_CustomerNote = findViewById<TextInputEditText>(R.id.tie_CustomerNote)
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
            R.id.btnSubmit->{
                agentnote = tie_AgentNote!!.text.toString()
                customernote = tie_CustomerNote!!.text.toString()

                getAddremark(agentnote, customernote)
            }
        }
    }

    private fun getAddremark(agentnote: String, customernote: String) {
        addRemarkViewModel = ViewModelProvider(this).get(AddremarkViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                addRemarkViewModel.getAddremark(this)!!.observe(this,
                    { addRemarkSetterGetter ->
                        val msg = addRemarkSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {


                                var jobj = jObject.getJSONObject("AddRemark")
                                var msg =jobj.getString("ResponseMessage")
                                Log.i("Message",msg)
                                val builder = AlertDialog.Builder(
                                    this@AddRemarkActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(msg)
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()



                            }

                            else {
                                val builder = AlertDialog.Builder(
                                    this@AddRemarkActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()

                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
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

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
             //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
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
                tie_CustomerMentionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)

            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }



}