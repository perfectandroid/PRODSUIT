package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.FollowupTypeAdapter
import com.perfect.prodsuit.View.Adapter.ProductStatusAdapter
import com.perfect.prodsuit.View.Adapter.RemarkActionTypeAdapter
import com.perfect.prodsuit.Viewmodel.FollowUpTypeViewModel
import com.perfect.prodsuit.Viewmodel.ProductStatusViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Long
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddRemarkMultipleActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG : String = "AddRemarkMultipleActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var tv_actionType: TextView? = null

    var tie_ActionType: TextInputEditText? = null
    var tie_Date: TextInputEditText? = null
    var tie_Time: TextInputEditText? = null
    var tie_CallStatus: TextInputEditText? = null
    var tie_CallDuration: TextInputEditText? = null
    var tie_RiskType: TextInputEditText? = null
    var tie_AgentNote: TextInputEditText? = null
    var tie_CustomerNote: TextInputEditText? = null
    var tie_FollowType: TextInputEditText? = null
    var tie_Status: TextInputEditText? = null
    var tie_CustomerMentionDate: TextInputEditText? = null
    var tie_Latitude: TextInputEditText? = null
    var tie_Longitude: TextInputEditText? = null

    var til_CallStatus: TextInputLayout? = null
    var til_CallDuration: TextInputLayout? = null
    var til_FollowType: TextInputLayout? = null
    var til_Status: TextInputLayout? = null

    var ll_location: LinearLayout? = null
    var ll_images: LinearLayout? = null
    var ll_acknowledge: LinearLayout? = null
    var ll_need: LinearLayout? = null

    var dialogRemarkAction : Dialog? = null
    var recyActionType: RecyclerView? = null
    lateinit var remarkActionArrayList : JSONArray
    private var ActionType = ""
    var action_id:String?=""

    var strCallStatus:String?=""
    var strRiskType:String?=""
    var ID_ActionType : String?= ""
    var ID_Status : String?= ""

    private var DateType:Int = 0
    var strDate : String?= ""
    var strTime : String?= ""
    var strMentionDate : String?= ""
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0

    var rltv_addremark: RelativeLayout? = null

    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList : JSONArray
    lateinit var followUpTypeSort : JSONArray
    private var dialogFollowupType : Dialog? = null
    var recyFollowupType: RecyclerView? = null

    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var prodStatusArrayList : JSONArray
    lateinit var prodStatusSort : JSONArray
    private var dialogProdStatus : Dialog? = null
    var recyProdStatus: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_remark_multiple)
        context = this@AddRemarkMultipleActivity

        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)

        setRegViews()

       // getActiontype()

    }





    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_ActionType = findViewById(R.id.tie_ActionType);
        tie_Date = findViewById(R.id.tie_Date);
        tie_Time = findViewById(R.id.tie_Time);
        tie_CallStatus = findViewById(R.id.tie_CallStatus);
        tie_RiskType = findViewById(R.id.tie_RiskType);
        tie_FollowType = findViewById(R.id.tie_FollowType);
        tie_Status = findViewById(R.id.tie_Status);
        tie_CustomerMentionDate = findViewById(R.id.tie_CustomerMentionDate);

        til_CallStatus = findViewById(R.id.til_CallStatus);
        til_CallDuration = findViewById(R.id.til_CallDuration);
        til_FollowType = findViewById(R.id.til_FollowType);
        til_Status = findViewById(R.id.til_Status);

        ll_location = findViewById(R.id.ll_location);
        ll_images = findViewById(R.id.ll_images);
        ll_acknowledge = findViewById(R.id.ll_acknowledge);
        ll_need = findViewById(R.id.ll_need);

        rltv_addremark = findViewById(R.id.rltv_addremark);

        tie_ActionType!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)
        tie_Time!!.setOnClickListener(this)
        tie_CallStatus!!.setOnClickListener(this)
        tie_RiskType!!.setOnClickListener(this)
        tie_FollowType!!.setOnClickListener(this)
        tie_Status!!.setOnClickListener(this)
        tie_CustomerMentionDate!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_ActionType->{
                getActiontype()
            }
            R.id.tie_Date->{
                DateType = 0
                openBottomSheet()
            }
            R.id.tie_Time->{
                openBottomSheetTime()
            }
            R.id.tie_CallStatus->{
              //  getCallStatus()
            }
            R.id.tie_RiskType->{
                getRiskType()
            }
            R.id.tie_FollowType->{
                getFollowupType()
            }
            R.id.tie_CustomerMentionDate->{
                DateType = 1
                openBottomSheet()
            }
            R.id.tie_Status->{
                getStatus()
            }
        }
    }

    private fun getActiontype() {
        ActionType = Config.getActionTypes()

        Log.e(TAG,"ActionType   44  "+ActionType)
        actiontypePopup(ActionType)
    }

    private fun actiontypePopup(actionType: String) {
        val jObject = JSONObject(actionType)
        val jobjt = jObject.getJSONObject("actionType")
        remarkActionArrayList  = jobjt.getJSONArray("actionTypeDetails")
        try {

            dialogRemarkAction = Dialog(this)
            dialogRemarkAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogRemarkAction!! .setContentView(R.layout.remark_action_popup)
            dialogRemarkAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyActionType = dialogRemarkAction!! .findViewById(R.id.recyActionType) as RecyclerView
            val etsearch = dialogRemarkAction!! .findViewById(R.id.etsearch) as EditText

//            agendaActionSort = JSONArray()
//            for (k in 0 until agendaActionArrayList.length()) {
//                val jsonObject = agendaActionArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                agendaActionSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@AddRemarkMultipleActivity, 1)
            recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = RemarkActionTypeAdapter(this@AddRemarkMultipleActivity, remarkActionArrayList)
            recyActionType!!.adapter = adapter
            adapter.setClickListener(this@AddRemarkMultipleActivity)


            dialogRemarkAction!!.show()
            dialogRemarkAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        date_Picker1!!.minDate = System.currentTimeMillis() - 1000

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day1: Int = date_Picker1!!.getDayOfMonth()
                val mon1: Int = date_Picker1!!.getMonth()
                val month1: Int = mon1+1
                val year1: Int = date_Picker1!!.getYear()
                var strDay = day1.toString()
                var strMonth = month1.toString()
                var strYear = year1.toString()

                yr = year1
                month =  month1
                day= day1


                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                if (DateType == 0){
                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strDate = strYear+"-"+strMonth+"-"+strDay
                }
                if (DateType == 1){
                    tie_CustomerMentionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strMentionDate = strYear+"-"+strMonth+"-"+strDay
                }




            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomSheetTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {

//                val hour: Int = time_Picker1!!.hour
//                val min: Int = time_Picker1!!.minute

                hr = time_Picker1!!.hour
                min = time_Picker1!!.minute
                var am_pm = ""
                var minutes: String? = ""

                if (hr > 12) {
                    hr -= 12
                    am_pm = "PM"
                } else if (hr === 0) {
                    hr += 12
                    am_pm = "AM"
                } else if (hr === 12) {
                    am_pm = "PM"
                } else {
                    am_pm = "AM"
                }


                if (min < 10)
                    minutes = "0" + min ;
                else
                    minutes = ""+min

                strTime = ""+hr+":"+minutes+" "+am_pm


//                strTime = String.format(
//                    "%02d:%02d %s", if (hr == 0) 12 else hr,
//                    min, if (hr < 12) "AM" else "PM"
//                )

                tie_Time!!.setText(strTime)



            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
                    strTime = sdf.format(date)
                    Log.e("formated date ", date.toString() + "")
                } catch (e: ParseException) {
                    e.printStackTrace()
                }


                val formateDate = SimpleDateFormat("dd-MM-yyyy").format(date)
                strDate = formateDate
                tie_Date!!.setText(formateDate)
                tie_Time!!.setText(strTime)


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

    private fun getFollowupType() {
        var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                if (followUpTypeArrayList.length()>0){
                                    if (followUpType == 0){
                                        followUpType++
                                        followupTypePopup(followUpTypeArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AddRemarkMultipleActivity,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!! .findViewById(R.id.etsearch) as EditText

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@AddRemarkMultipleActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupTypeAdapter(this@FollowUpActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@AddRemarkMultipleActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@AddRemarkMultipleActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpTypeSort               7103    "+followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@AddRemarkMultipleActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@AddRemarkMultipleActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getStatus() {
        var prodstatus = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   333   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("StatusDetailsList")
                                prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                if (prodStatusArrayList.length()>0){
                                    if (prodstatus == 0){
                                        prodstatus++
                                        productStatusPopup(prodStatusArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AddRemarkMultipleActivity,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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

    private fun productStatusPopup(prodStatusArrayList: JSONArray) {
        try {
            dialogProdStatus = Dialog(this)
            dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdStatus!! .setContentView(R.layout.product_status_popup)
            dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdStatus = dialogProdStatus!! .findViewById(R.id.recyProdStatus) as RecyclerView
            val etsearch = dialogProdStatus!! .findViewById(R.id.etsearch) as EditText

            prodStatusSort = JSONArray()
            for (k in 0 until prodStatusArrayList.length()) {
                val jsonObject = prodStatusArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodStatusSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@AddRemarkMultipleActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = ProductStatusAdapter(this@FollowUpActivity, prodStatusArrayList)
            val adapter = ProductStatusAdapter(this@AddRemarkMultipleActivity, prodStatusSort)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@AddRemarkMultipleActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodStatusSort = JSONArray()

                    for (k in 0 until prodStatusArrayList.length()) {
                        val jsonObject = prodStatusArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("StatusName").length) {
                            if (jsonObject.getString("StatusName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                prodStatusSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"prodStatusSort               7103    "+prodStatusSort)
                    val adapter = ProductStatusAdapter(this@AddRemarkMultipleActivity, prodStatusSort)
                    recyProdStatus!!.adapter = adapter
                    adapter.setClickListener(this@AddRemarkMultipleActivity)
                }
            })

            dialogProdStatus!!.show()
            dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onClick(position: Int, data: String) {
        if (data.equals("remarkactiontype")){
            dialogRemarkAction!!.dismiss()
            rltv_addremark!!.visibility = View.GONE

            val jsonObject = remarkActionArrayList.getJSONObject(position)
            action_id = jsonObject.getString("action_id")
            tie_ActionType!!.setText(jsonObject.getString("action"))
            if (action_id.equals("1") || action_id.equals("2")){
                rltv_addremark!!.visibility = View.VISIBLE
            }
            usingActionId(action_id!!)

        }

        if (data.equals("followuptype")){
            dialogFollowupType!!.dismiss()
//            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))

            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_FollowType!!.setText(jsonObject.getString("ActnTypeName"))

        }

        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
//            val jsonObject = prodStatusArrayList.getJSONObject(position)
            val jsonObject = prodStatusSort.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            tie_Status!!.setText(jsonObject.getString("StatusName"))

        }
    }

    private fun usingActionId(actionId: String) {
        if (action_id.equals("1")){
            // ADD REMARK
            til_CallStatus!!.visibility = View.VISIBLE
            til_CallDuration!!.visibility = View.VISIBLE
            ll_acknowledge!!.visibility = View.VISIBLE
            ll_need!!.visibility = View.VISIBLE

            til_FollowType!!.visibility = View.GONE
            til_Status!!.visibility = View.GONE
            ll_location!!.visibility = View.GONE
            ll_images!!.visibility = View.GONE

        }
        else if (action_id.equals("2")){
            // SITE VISIT
            til_CallStatus!!.visibility = View.GONE
            til_CallDuration!!.visibility = View.GONE
            ll_acknowledge!!.visibility = View.GONE
            ll_need!!.visibility = View.GONE

            til_FollowType!!.visibility = View.VISIBLE
            til_Status!!.visibility = View.VISIBLE
            ll_location!!.visibility = View.VISIBLE
            ll_images!!.visibility = View.VISIBLE

        }
    }
}