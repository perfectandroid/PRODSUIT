package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Receivers.PhoneStatReceiver
import com.perfect.prodsuit.View.Adapter.AgendaActionTypeAdapter
import com.perfect.prodsuit.View.Adapter.AgendaDetailAdapter
import com.perfect.prodsuit.View.Adapter.AgendaTypeAdapter
import com.perfect.prodsuit.Viewmodel.AgendaActionViewModel
import com.perfect.prodsuit.Viewmodel.AgendaCountViewModel
import com.perfect.prodsuit.Viewmodel.AgendaDetailViewModel
import com.perfect.prodsuit.Viewmodel.AgendaTypeViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Long
import java.util.*
import kotlin.Exception
import kotlin.Int
import kotlin.String
import kotlin.arrayOf


class AgendaActivity : AppCompatActivity() , View.OnClickListener  , ItemClickListener {

    val TAG : String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context




//    private var tabLayout : TabLayout? = null
//    var llMainDetail: LinearLayout? = null
//    var llPending: LinearLayout? = null
//    var llUpComing: LinearLayout? = null
//    var llComplete: LinearLayout? = null

    var llMainLeads: LinearLayout? = null
    var llMainService: LinearLayout? = null

    var tv_today_comp: TextView? = null
    var tv_today_count: TextView? = null

    var tv_count_pending: TextView? = null
    var tv_count_upcoming: TextView? = null
    var tv_count_complete: TextView? = null

    var tv_tab_pending: TextView? = null
    var tv_tab_upcoming: TextView? = null
    var tv_tab_completed: TextView? = null
    var tv_actionType: TextView? = null
    var agendaTypeClick : String?= "0"
    var ID_ActionType : String?= ""
    var SubMode : String?= ""

    lateinit var agendaTypeViewModel: AgendaTypeViewModel
    lateinit var agendaTypeArrayList : JSONArray

    lateinit var agendaCountViewModel: AgendaCountViewModel
    lateinit var agendaActionViewModel: AgendaActionViewModel
    lateinit var agendaDetailViewModel: AgendaDetailViewModel
    lateinit var agendaActionArrayList : JSONArray
    lateinit var agendaDetailArrayList : JSONArray
    var dialogAgendaAction : Dialog? = null
    var recyActionType: RecyclerView? = null
    var recyAgendaDetail: RecyclerView? = null
    var recyAgendaType: RecyclerView? = null





    var txtSelectPend: TextView? = null

  //  var inflaterPend : LayoutInflater? = null
  var myReceiver:  PhoneStatReceiver = PhoneStatReceiver()

    companion object{
        var CUSTOM_INTENT : String?= "PRODSUIT_CALL"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_agenda)

        context = this@AgendaActivity
        agendaTypeViewModel = ViewModelProvider(this).get(AgendaTypeViewModel::class.java)
        agendaCountViewModel = ViewModelProvider(this).get(AgendaCountViewModel::class.java)
        agendaActionViewModel = ViewModelProvider(this).get(AgendaActionViewModel::class.java)
        agendaDetailViewModel = ViewModelProvider(this).get(AgendaDetailViewModel::class.java)


//        val telephony = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(myReceiver, PhoneStateListener.LISTEN_CALL_STATE)
////        val intentFilter = IntentFilter("com.perfect.prodsuit.Receivers.PhoneStatReceiver")
////        intentFilter.addAction(TelephonyManager.EXTRA_STATE)
////        registerReceiver(myReceiver, intentFilter)

        setRegViews()
      //  addTabItem()
        SubMode ="1"
       // getAgendaCounts()
        getAgendatypes()
//        getActionTypes()

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

//        tabLayout = findViewById(R.id.tabLayout);
//        llMainDetail = findViewById(R.id.llMainDetail);
//        llPending = findViewById(R.id.llPending);
//        llUpComing = findViewById(R.id.llUpComing);
//        llComplete = findViewById(R.id.llComplete);

        llMainLeads = findViewById(R.id.llMainLeads);
        llMainService = findViewById(R.id.llMainService);

        tv_today_comp = findViewById(R.id.tv_today_comp);
        tv_today_count = findViewById(R.id.tv_today_count);

        tv_count_pending = findViewById(R.id.tv_count_pending);
        tv_count_upcoming = findViewById(R.id.tv_count_upcoming);
        tv_count_complete = findViewById(R.id.tv_count_complete);

        tv_tab_pending = findViewById(R.id.tv_tab_pending);
        tv_tab_upcoming = findViewById(R.id.tv_tab_upcoming);
        tv_tab_completed = findViewById(R.id.tv_tab_completed);
        tv_actionType = findViewById(R.id.tv_actionType);

        recyAgendaDetail = findViewById(R.id.recyAgendaDetail)
        recyAgendaType = findViewById(R.id.recyAgendaType)

        tv_tab_pending!!.setOnClickListener(this)
        tv_tab_upcoming!!.setOnClickListener(this)
        tv_tab_completed!!.setOnClickListener(this)
        tv_actionType!!.setOnClickListener(this)



    }

//    private fun addTabItem() {
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("PENDING"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("UPCOMING"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("COMPLETED"))
//        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
//
//
//
//        PendingTab()
//
//
//
//
//        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabSelected  113  "+tab.position)
//                if (tab.position == 0){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//
//                    PendingTab()
//
//                }
//                if (tab.position == 1){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//                    UpComingTab()
//
//                }
//                if (tab.position == 2){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//
//                    CompleteTab()
//
//                }
//
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabUnselected  162  "+tab.position)
//
//            }
//            override fun onTabReselected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabReselected  165  "+tab.position)
//            }
//        })
//
//    }
//
//    private fun PendingTab() {
//        llMainDetail!!.removeAllViews()
//        val inflaterPend = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayoutPend: View = inflaterPend!!.inflate(R.layout.activity_subpending, null, false)
//        llMainDetail!!.addView(inflatedLayoutPend);
//
//        txtSelectPend = inflatedLayoutPend.findViewById<TextView>(R.id.txtSelectPend)
//        txtSelectPend!!.setOnClickListener(this)
//
//
//    }
//
//    private fun UpComingTab() {
//        llMainDetail!!.removeAllViews()
//        val inflater = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_subupcoming, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//    }
//
//    private fun CompleteTab() {
//        llMainDetail!!.removeAllViews()
//        val inflater = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_subcomplete, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.txtSelectPend->{
                Log.e(TAG,"txtSelectPend  232   ")
            }
            R.id.tv_tab_pending->{
                Log.e(TAG,"tv_tab_pending  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                SubMode ="1"
                getActionTypes()
            }
            R.id.tv_tab_upcoming->{
                Log.e(TAG,"tv_tab_upcoming  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                SubMode ="2"
                getActionTypes()
            }
            R.id.tv_tab_completed->{
                Log.e(TAG,"tv_tab_completed  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_color);
                agendaTypeClick = "0"
                SubMode ="3"
                getActionTypes()
            }
            R.id.tv_actionType->{
                Log.e(TAG,"tv_actionType  232   ")
                agendaTypeClick = "1"
                getActionTypes()

            }
        }
    }

    private fun getAgendatypes() {
        var typeAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaTypeViewModel.getAgendaType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   302   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("AgendaType")
                                agendaTypeArrayList = jobjt.getJSONArray("AgendaTypeList")
                                if (agendaTypeArrayList.length()>0){


                                    recyAgendaType = findViewById(R.id.recyAgendaType) as RecyclerView
                                   // val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                    recyAgendaType!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                   // recyAgendaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    val adapter = AgendaTypeAdapter(this@AgendaActivity, agendaTypeArrayList)
                                    recyAgendaType!!.adapter = adapter
                                    adapter.setClickListener(this@AgendaActivity)

//                                    val jsonObject = agendaTypeArrayList.getJSONObject(0)
//                                    if (jsonObject.getString("Id_Agenda").equals("1")){
////                                        Lead
//                                        llMainLeads!!.visibility = View.VISIBLE
//                                        llMainService!!.visibility = View.GONE
//
//                                        rrrrrrrrrrrrr
//                                    }
//                                    if (jsonObject.getString("Id_Agenda").equals("2")){
////                                        Service
//                                        llMainLeads!!.visibility = View.GONE
//                                        llMainService!!.visibility = View.VISIBLE
//                                    }


                                    getActionTypes()

                                }



                            } else {

                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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

    private fun getAgendaCounts() {
        var countAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaCountViewModel.getAgendaCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   167   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("PendingCountDetails")
                                tv_count_pending!!.setText(jobjt.getString("Pending"))
                                tv_count_upcoming!!.setText(jobjt.getString("Upcoming"))
                                tv_count_complete!!.setText(jobjt.getString("Completed"))

                                tv_today_comp!!.setText(jobjt.getString("TodayCompletedCount"))
                                tv_today_count!!.setText("out of " +jobjt.getString("TodayCount")+ " activity completed today")
//
//                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
//                                if (leadFromArrayList.length()>0){
//                                    if (countLeadFrom == 0){
//                                        countLeadFrom++
//                                        leadFromPopup(leadFromArrayList)
//                                    }
//
//                                }

                            } else {

                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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

    private fun getActionTypes() {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        var agendaAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaActionViewModel.getAgendaAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        progressDialog!!.dismiss()
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   284   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ActionType")
                                agendaActionArrayList = jobjt.getJSONArray("ActionTypeList")
                                if (agendaActionArrayList.length()>0){
                                    if (agendaAction == 0){
                                        agendaAction++
                                        agendaTypePopup(agendaActionArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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

            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun agendaTypePopup(agendaActionArrayList: JSONArray) {

        if (agendaTypeClick.equals("0")){
            val jsonObject = agendaActionArrayList.getJSONObject(0)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))

            getAgendaDetails(ID_ActionType!!)

        }else{

            try {

                dialogAgendaAction = Dialog(this)
                dialogAgendaAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogAgendaAction!! .setContentView(R.layout.agenda_action_popup)
                dialogAgendaAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
                recyActionType = dialogAgendaAction!! .findViewById(R.id.recyActionType) as RecyclerView

                val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
                val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionArrayList)
                recyActionType!!.adapter = adapter
                adapter.setClickListener(this@AgendaActivity)

                dialogAgendaAction!!.show()
                dialogAgendaAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("agendaactiontype")){
            dialogAgendaAction!!.dismiss()
            val jsonObject = agendaActionArrayList.getJSONObject(position)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))

            getAgendaDetails(ID_ActionType!!)


        }
        if (data.equals("agendaLocation")){

            Log.e(TAG,"agendaLocation   433")
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            Log.e(TAG,"Latitude       4331    "+jsonObject.getString("Latitude"))
            Log.e(TAG,"Longitude      4332    "+jsonObject.getString("Longitude"))
            Log.e(TAG,"LocationName   4333    "+jsonObject.getString("LocationName"))

            val i = Intent(this@AgendaActivity, MapsAgendaActivity::class.java)
            i.putExtra("LocationName", jsonObject.getString("LocationName"))
            i.putExtra("Longitude",jsonObject.getString("Longitude"))
            i.putExtra("Latitude",jsonObject.getString("Latitude"))
            startActivity(i)
        }

        if (data.equals("agendaMessage")){
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val i = Intent(this@AgendaActivity, MessagesActivity::class.java)
            i.putExtra("LgCusMobile", jsonObject.getString("CustomerMobile"))
            i.putExtra("LgCusEmail","")
            startActivity(i)
        }

        if (data.equals("agendaCall")){
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            Log.e(TAG,"CustomerMobile       454    "+jsonObject.getString("CustomerMobile"))
            if (jsonObject.getString("CustomerMobile").equals("") || jsonObject.getString("CustomerMobile") == null){
                val builder = AlertDialog.Builder(this@AgendaActivity, R.style.MyDialogTheme)
                builder.setMessage("Customer Mobile Not Found")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            else{
                Log.e(TAG,"CALL DETAILS  506 getCallDetails  ")

                val ALL_PERMISSIONS = 101

                val permissions = arrayOf(
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE
                )
                if (ContextCompat.checkSelfPermission(
                        this@AgendaActivity,
                        Manifest.permission.CALL_PHONE
                    ) + ContextCompat.checkSelfPermission(
                        this@AgendaActivity,
                        Manifest.permission.RECORD_AUDIO
                    )
                    + ContextCompat.checkSelfPermission(
                        this@AgendaActivity,
                        Manifest.permission.READ_PHONE_STATE
                    )
                    + ContextCompat.checkSelfPermission(
                        this@AgendaActivity,
                        Manifest.permission.READ_CALL_LOG
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
                } else {
                  //  getCallDetails()
                    //val mobileno = "7736902085"
//                    val i = Intent()
//                    i.putExtra("number",mobileno)
//                    context.sendBroadcast(i)

//                    val mobileno = "9744799045"

//                    var intent = Intent(this, PhoneStatReceiver::class.java)
//                    intent.action = "MyAction"
//                    sendBroadcast(intent)

                   // sendBroadcast(i)
                    val mobileno = jsonObject.getString("CustomerMobile")
                    val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                    val BroadCallEditer = BroadCallSP.edit()
                    BroadCallEditer.putString("BroadCall", "Yes")
                    BroadCallEditer.commit()

//                    val i= Intent(this, PhoneStatReceiver::class.java)
//                    i.putExtra("txt", "the string value");
//                    startActivity(i)

//                    val extras = intent.extras
//                    val i = Intent("my.action.string")
//                    // Data you need to pass to activity
//                    // Data you need to pass to activity
//                    i.putExtra("message", "HELLO")
//                    context.sendBroadcast(i)

                    intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                    startActivity(intent)
                }

            }

        }

        if (data.equals("agendaType")){
//            val jsonObject = agendaTypeArrayList.getJSONObject(position)
//            if (jsonObject.getString("Id_Agenda").equals("1")){
////                Lead
//            }
//            if (jsonObject.getString("Id_Agenda").equals("2")){
////                Service
//            }
            agendaTypeClick = "0"
            getActionTypes()
        }
    }

    private fun getCallDetails() {

        try {
            val sb = StringBuffer()
            val contacts: Uri = CallLog.Calls.CONTENT_URI
            val managedCursor: Cursor? = context.contentResolver.query(contacts, null, null, null, null)
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
            }
            managedCursor.close()

            Log.e(TAG,"CALL DETAILS  5061   "+sb)
        }catch (e : Exception){
            Log.e(TAG,"CALL DETAILS  5062   "+e.toString())
        }

    }

    private fun getAgendaDetails(ID_ActionType: String) {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        var agendaDetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaDetailViewModel.getAgendaDetail(this,ID_ActionType,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        progressDialog!!.dismiss()
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   443   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("AgendaDetails")
                                agendaDetailArrayList = jobjt.getJSONArray("AgendaDetailsList")
                                if (agendaDetailArrayList.length()>0){
//                                    if (agendaDetail == 0){
//                                        agendaDetail++
                                      //  agendaTypePopup(agendaActionArrayList)

                                        recyAgendaDetail = findViewById(R.id.recyAgendaDetail) as RecyclerView
                                        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                        recyAgendaDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = AgendaDetailAdapter(this@AgendaActivity, agendaDetailArrayList)
                                        recyAgendaDetail!!.adapter = adapter
                                        adapter.setClickListener(this@AgendaActivity)
                                  // }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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
               // progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }

//        var ss = ""
//
//        if (ID_ActionType.equals("1")){
//            ss = "[{\"ID_ActionType\": 1,\"Fileds\": \"Call1\"},{\"ID_ActionType\": 1,\"Fileds\": \"Call2\"},{\"ID_ActionType\": 1,\"Fileds\": \"Call3\"}]"
//        }
//        else if (ID_ActionType.equals("2")){
//
//            ss = "[{\"ID_ActionType\": 2,\"Fileds\": \"Message1\"},{\"ID_ActionType\": 2,\"Fileds\": \"Message2\"},{\"ID_ActionType\": 2,\"Fileds\": \"Message3\"}]"
//        }
//        else if (ID_ActionType.equals("3")){
//
//            ss = "[{\"ID_ActionType\": 3,\"Fileds\": \"Meeting1\"},{\"ID_ActionType\": 3,\"Fileds\": \"Meeting2\"},{\"ID_ActionType\": 3,\"Fileds\": \"Meeting3\"}]"
//        }
//        else if (ID_ActionType.equals("4")){
//
//            ss = "[{\"ID_ActionType\": 4,\"Fileds\": \"Document1\"},{\"ID_ActionType\": 4,\"Fileds\": \"Document2\"},{\"ID_ActionType\": 4,\"Fileds\": \"Document3\"}]"
//        }
//        else if (ID_ActionType.equals("5")){
//
//            ss = "[{\"ID_ActionType\": 5,\"Fileds\": \"Quotation1\"},{\"ID_ActionType\": 5,\"Fileds\": \"Quotation2\"},{\"ID_ActionType\": 5,\"Fileds\": \"Quotation3\"}]"
//        }
//
//        agendaDetailArrayList = JSONArray(ss)
//
//        recyAgendaDetail = findViewById(R.id.recyAgendaDetail) as RecyclerView
//        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
//        recyAgendaDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//        val adapter = AgendaDetailAdapter(this@AgendaActivity, agendaDetailArrayList)
//        recyAgendaDetail!!.adapter = adapter

    }

}