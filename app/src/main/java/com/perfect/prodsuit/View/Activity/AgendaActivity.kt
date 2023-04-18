package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CallLog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Receivers.PhoneStatReceiver
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import info.hoang8f.android.segmented.SegmentedGroup
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Long
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception
import kotlin.Int
import kotlin.String
import kotlin.arrayOf


class AgendaActivity : AppCompatActivity() , View.OnClickListener  , ItemClickListener,
    RadioGroup.OnCheckedChangeListener {

    val TAG : String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    private var progressDialog1: ProgressDialog? = null
    lateinit var context: Context
    var sharedPreferences: SharedPreferences? =null

    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    private var Id_Agenda :String = ""

    internal var etxt_date: EditText? = null
    internal var etxt_Name: EditText? = null
    internal var etxt_date1: EditText? = null
    internal var etxt_name1: EditText? = null
//    private var tabLayout : TabLayout? = null
//    var llMainDetail: LinearLayout? = null
//    var llPending: LinearLayout? = null
//    var llUpComing: LinearLayout? = null
//    var llComplete: LinearLayout? = null

    var llMainLeads: LinearLayout? = null
    var llMainService: LinearLayout? = null

    var fab_Reminder: FloatingActionButton? = null

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
    var SubMode : String?= "1"
    internal var sortFilter:Int = 0
    var selectedPos:Int = 0

    lateinit var agendaTypeViewModel: AgendaTypeViewModel
    lateinit var sortAgendaViewModel: SortAgendaViewModel
    lateinit var agendaTypeArrayList : JSONArray

    lateinit var agendaCountViewModel: AgendaCountViewModel
    lateinit var agendaActionViewModel: AgendaActionViewModel
    lateinit var agendaDetailViewModel: AgendaDetailViewModel
    lateinit var agendaActionArrayList : JSONArray
    lateinit var agendaActionSort : JSONArray
    lateinit var agendaDetailArrayList : JSONArray
    var dialogAgendaAction : Dialog? = null
    var recyActionType: RecyclerView? = null
    var recyAgendaDetail: RecyclerView? = null
    var recyAgendaType: RecyclerView? = null
    var submode = "";





    var txtSelectPend: TextView? = null

  //  var inflaterPend : LayoutInflater? = null
  var myReceiver:  PhoneStatReceiver = PhoneStatReceiver()

    companion object{
        var CUSTOM_INTENT : String?= "PRODSUIT_CALL"
        var name = ""
//        var nxtactndate = ""
//        var name1 = ""
        var date = ""
        var criteria = ""
        var ID_LeadGenerate = ""
    }


    private var messageType = "";
    private var messageDesc = "";
    private var cbWhat = "0";
    private var cbEmail = "0";
    private var cbMessage = "0";

    private var ID_Branch = "";
    private var ID_Employee = "";
    private var ID_Lead_Details = "";
    private var strLeadValue = "";

    var toDoDet = 0
    lateinit var todolistViewModel: TodoListViewModel
    private var rv_todolist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray

    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList : JSONArray
    lateinit var branchSort : JSONArray
    private var dialogBranch : Dialog? = null
    var recyBranch: RecyclerView? = null
    var branch = 0

    var empUseBranch = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList : JSONArray
    lateinit var employeeAllSort : JSONArray
    private var dialogEmployeeAll : Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    var leadDetails = 0
    lateinit var leadDetailViewModel: LeadDetailViewModel
    lateinit var leadDetailArrayList : JSONArray
    lateinit var leadDetailSort : JSONArray
    private var dialogleadDetail : Dialog? = null
    var recyleadDetail: RecyclerView? = null

    var til_LeadValue: TextInputLayout? = null
    var tie_Branch: TextInputEditText? = null
    var tie_Employee: TextInputEditText? = null
    var tie_LeadDetails: TextInputEditText? = null
    var tie_LeadValue: TextInputEditText? = null


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
        sortAgendaViewModel= ViewModelProvider(this).get(SortAgendaViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        leadDetailViewModel = ViewModelProvider(this).get(LeadDetailViewModel::class.java)

        sharedPreferences = context!!.getSharedPreferences("AgendaReminder", Context.MODE_PRIVATE)


//        val telephony = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(myReceiver, PhoneStateListener.LISTEN_CALL_STATE)
////        val intentFilter = IntentFilter("com.perfect.prodsuit.Receivers.PhoneStatReceiver")
////        intentFilter.addAction(TelephonyManager.EXTRA_STATE)
////        registerReceiver(myReceiver, intentFilter)

        setRegViews()
      //  addTabItem()
        SubMode ="1"
        name = ""
        date = ""
        criteria = ""
        ID_LeadGenerate = ""
       // getAgendaCounts()

        getAgendatypes()
//        getActionTypes()

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        val imgv_filter= findViewById<ImageView>(R.id.imgv_filter)
        val imgv_sort= findViewById<ImageView>(R.id.imgv_sort)

        imgv_filter!!.setOnClickListener(this)
        imgv_sort!!.setOnClickListener(this)

//        tabLayout = findViewById(R.id.tabLayout);
//        llMainDetail = findViewById(R.id.llMainDetail);
//        llPending = findViewById(R.id.llPending);
//        llUpComing = findViewById(R.id.llUpComing);
//        llComplete = findViewById(R.id.llComplete);

        rv_todolist = findViewById(R.id.rv_todolist)

        llMainLeads = findViewById(R.id.llMainLeads);
        llMainService = findViewById(R.id.llMainService);

        fab_Reminder = findViewById(R.id.fab_Reminder);

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

        fab_Reminder!!.setOnClickListener(this)

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
            R.id.imgv_filter -> {
               // filterData()
                if (Id_Agenda.equals("1")){
                    filterBottomData()
                }

            }
            R.id.imgv_sort -> {
                sortData()
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
                Log.e(TAG," 8301   1")
              //  getActionTypes(Id_Agenda)
            }
            R.id.tv_tab_upcoming->{
                Log.e(TAG,"tv_tab_upcoming  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                SubMode ="2"
                Log.e(TAG," 8301   2")
               // getActionTypes(Id_Agenda)
            }
            R.id.tv_tab_completed->{
                Log.e(TAG,"tv_tab_completed  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_color);
                agendaTypeClick = "0"
                SubMode ="3"
                Log.e(TAG," 8301   3")
             //   getActionTypes(Id_Agenda)
            }
//            R.id.tv_actionType->{
//                Log.e(TAG,"tv_actionType  232   ")
//                agendaTypeClick = "1"
//                Log.e(TAG," 8301   4")
//                getActionTypes(Id_Agenda)
//
//            }
            R.id.fab_Reminder->{
                var strReminder: String=""
                var ii: Int=0
                val gson = Gson()
                val json = sharedPreferences!!.getString("Set", "")
                var lstChkArray = ArrayList<String>()
                if (json!!.isNotEmpty()){
                    val collectionType: Type = object : TypeToken<List<String?>?>() {}.getType()
                    val arrPackageData: List<String> = gson.fromJson(json, collectionType)
                    for (reminderText in arrPackageData) {
                        Log.e("lstChk_size   ","lstChk_size   "+reminderText)
                        if (ii==0){
                            ii++
                            strReminder = ii.toString()+" . "+reminderText
                        }else{
                            ii++
                            strReminder = strReminder+"\n"+"\n"+ii.toString()+" . "+reminderText
                        }
                        lstChkArray.add(reminderText)
                    }
                }

                if (!lstChkArray.isEmpty()){
                    //setReminder("You have set reminder for following defaulters accounts "+lstChkArray.toString())
                    Log.e(TAG,"strReminder   3411    "+strReminder)

                    setReminder("","",strReminder)
                }else{
                    Log.e(TAG,"strReminder   3412    Select")
                    Config.snackBars(context,v,"Select Atlest One Reminder")
                 //   Toast.makeText(context,"Select Account",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getAgendatypes() {
        var typeAgenda = 0
        Log.e(TAG,"msg   3966   ")
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
                                    val adapter = AgendaTypeAdapter(this@AgendaActivity, agendaTypeArrayList,selectedPos)
                                    recyAgendaType!!.adapter = adapter
                                    adapter.setClickListener(this@AgendaActivity)

                                    Id_Agenda = agendaTypeArrayList.getJSONObject(0).getString("Id_Agenda")
                                    Log.e(TAG,"Id_Agenda   423   "+Id_Agenda)
                                    Log.e(TAG," 8301   5")

                                    val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                                    val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                                    val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                                    val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                                    ID_Branch  = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
                                    ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()

                                    if (Id_Agenda.equals("1")){

                                        rv_todolist!!.visibility = View.VISIBLE
                                        rv_todolist!!.adapter = null
                                        toDoDet = 0
                                        getTodoList()
                                    }
                                    else if (Id_Agenda.equals("2")){
                                        rv_todolist!!.visibility = View.GONE
                                    }


                                    //     getActionTypes(Id_Agenda)

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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues1.",
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

    private fun getTodoList() {
//        var toDoDet = 0
       // rv_todolist!!.adapter = null
        context = this@AgendaActivity
        todolistViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                todolistViewModel.getTodolist(this, "1", name!!, criteria!!,date!!,ID_Branch!!,ID_Employee!!,ID_Lead_Details,strLeadValue)!!.observe(
                    this,
                    Observer { todolistSetterGetter ->
                        try {
                            val msg = todolistSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.e(TAG,"getTodoList   52412   "+msg)
                                Log.e(TAG,"getTodoList   524   "+toDoDet)
                                if (toDoDet == 0){
                                    toDoDet++

                                    val editor = sharedPreferences!!.edit()
                                    editor.clear()
                                    editor.commit()

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                        todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                        Log.e(TAG,"todoArrayList   5243   "+todoArrayList)
                                        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                        rv_todolist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        rv_todolist!!.setHasFixedSize(true)
                                        val adapter = TodoListAdapter(applicationContext, todoArrayList,SubMode!!)
                                        rv_todolist!!.adapter = adapter
                                        adapter.setClickListener(this@AgendaActivity)


                                        Log.e(TAG,"agendaTypeArrayList   5521   "+agendaTypeArrayList)
                                        Log.e(TAG,"agendaTypeArrayList   552   "+agendaTypeArrayList.length())
                                       var jsonObject = agendaTypeArrayList.getJSONObject(0)
                                        jsonObject.put("Count",todoArrayList.length())
                                        Log.e(TAG,"agendaTypeArrayList   553   "+agendaTypeArrayList)
                                        val adapter1 = AgendaTypeAdapter(this@AgendaActivity, agendaTypeArrayList,selectedPos)
                                        recyAgendaType!!.adapter = adapter1
                                        adapter1.setClickListener(this@AgendaActivity)


                                    } else {
                                        rv_todolist!!.adapter  =null
                                        val builder = AlertDialog.Builder(
                                            this@AgendaActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                                onBackPressed()
//                                                finish()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }

                            } else {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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

//    private fun getAgendaCounts() {
//        var countAgenda = 0
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//
//                agendaCountViewModel.getAgendaCount(this)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//
//
//                            val jObject = JSONObject(msg)
//                            Log.e(TAG,"msg   167   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("PendingCountDetails")
//                                tv_count_pending!!.setText(jobjt.getString("Pending"))
//                                tv_count_upcoming!!.setText(jobjt.getString("Upcoming"))
//                                tv_count_complete!!.setText(jobjt.getString("Completed"))
//
//                                tv_today_comp!!.setText(jobjt.getString("TodayCompletedCount"))
//                                tv_today_count!!.setText("out of " +jobjt.getString("TodayCount")+ " activity completed today")
////
////                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
////                                if (leadFromArrayList.length()>0){
////                                    if (countLeadFrom == 0){
////                                        countLeadFrom++
////                                        leadFromPopup(leadFromArrayList)
////                                    }
////
////                                }
//
//                            } else {
//
//                                val builder = AlertDialog.Builder(
//                                    this@AgendaActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//
//        }
//    }

    private fun getActionTypes(Id_Agenda : String) {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        Log.e(TAG,"537777      "+Id_Agenda)
        var agendaAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog1 = ProgressDialog(context, R.style.Progress)
                progressDialog1!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog1!!.setCancelable(false)
                progressDialog1!!.setIndeterminate(true)
                progressDialog1!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog1!!.show()

                agendaActionViewModel.getAgendaAction(this,Id_Agenda)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message

                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   5577   "+msg)
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
                progressDialog1!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun agendaTypePopup(agendaActionArrayList: JSONArray) {

        Log.e(TAG,"agendaTypePopup  599   "+agendaTypeClick)
        if (agendaTypeClick.equals("0")){

            agendaActionSort = JSONArray()
            for (k in 0 until agendaActionArrayList.length()) {
                val jsonObject = agendaActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                agendaActionSort.put(jsonObject)
            }

            val jsonObject = agendaActionArrayList.getJSONObject(0)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))
            Log.e(TAG,"agendaTypePopup  6222   "+tv_actionType)

            getAgendaDetails("0",Id_Agenda)

        }else{

            try {

                dialogAgendaAction = Dialog(this)
                dialogAgendaAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogAgendaAction!! .setContentView(R.layout.agenda_action_popup)
                dialogAgendaAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
                recyActionType = dialogAgendaAction!! .findViewById(R.id.recyActionType) as RecyclerView
                val etsearch = dialogAgendaAction!! .findViewById(R.id.etsearch) as EditText

                agendaActionSort = JSONArray()
                for (k in 0 until agendaActionArrayList.length()) {
                    val jsonObject = agendaActionArrayList.getJSONObject(k)
                    // reportNamesort.put(k,jsonObject)
                    agendaActionSort.put(jsonObject)
                }

                val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//                val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionArrayList)
                val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionSort)
                recyActionType!!.adapter = adapter
                adapter.setClickListener(this@AgendaActivity)

                etsearch!!.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        //  list_view!!.setVisibility(View.VISIBLE)
                        val textlength = etsearch!!.text.length
                        agendaActionSort = JSONArray()

                        for (k in 0 until agendaActionArrayList.length()) {
                            val jsonObject = agendaActionArrayList.getJSONObject(k)
                            if (textlength <= jsonObject.getString("ActionTypeName").length) {
                                if (jsonObject.getString("ActionTypeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                    agendaActionSort.put(jsonObject)
                                }

                            }
                        }

                        Log.e(TAG,"agendaActionSort               7103    "+agendaActionSort)
                        val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionSort)
                        recyActionType!!.adapter = adapter
                        adapter.setClickListener(this@AgendaActivity)
                    }
                })

                dialogAgendaAction!!.show()
                dialogAgendaAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("todolist")){
            val jsonObject = todoArrayList.getJSONObject(position)
            val i = Intent(this@AgendaActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            i.putExtra("SubMode", submode)
            startActivity(i)
        }

        if (data.equals("agendaactiontype")){
            dialogAgendaAction!!.dismiss()
//            val jsonObject = agendaActionArrayList.getJSONObject(position)
            val jsonObject = agendaActionSort.getJSONObject(position)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))

            Log.e(TAG,"ID_ActionType   607   "+ID_ActionType)
            name = ""
            date = ""
            criteria = ""
            getAgendaDetails(ID_ActionType!!,Id_Agenda)


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
            i.putExtra("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
            i.putExtra("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
            i.putExtra("Latitude",jsonObject.getString("Latitude"))
            startActivity(i)
        }

        if (data.equals("agendaMessage")){

            val jsonObject = agendaDetailArrayList.getJSONObject(position)

//            val i = Intent(this@AgendaActivity, FollowUpActivity::class.java)
//            i.putExtra("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
//            i.putExtra("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
//            i.putExtra("ActionMode","2")
//            startActivity(i)

//            val jsonObject = agendaDetailArrayList.getJSONObject(position)
//            val i = Intent(this@AgendaActivity, AddRemarkMultipleActivity::class.java)
//            i.putExtra("LgCusMobile", jsonObject.getString("CustomerMobile"))
//            i.putExtra("LgCusEmail","")
//            startActivity(i)

            messagePopup()

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
                    ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
                    val mobileno = jsonObject.getString("CustomerMobile")
                    val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                    val BroadCallEditer = BroadCallSP.edit()
                    BroadCallEditer.putString("BroadCall", "Yes")
                    BroadCallEditer.putString("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
                    BroadCallEditer.putString("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
                    BroadCallEditer.commit()

                    Log.e(TAG,"8001   "+jsonObject.getString("ID_LeadGenerate"))
                    Log.e(TAG,"8002   "+jsonObject.getString("ID_LeadGenerateProduct"))

//                    val i= Intent(this, PhoneStatReceiver::class.java)
//                    i.putExtra("txt", "the string value");
//                    startActivity(i)

//                    val extras = intent.extras
//                    val i = Intent("my.action.string")
//                    // Data you need to pass to activity
//                    // Data you need to pass to activity
//                    i.putExtra("message", "HELLO")
//                    context.sendBroadcast(i)

                   // intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))

                    intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                    startActivity(intent)


                }

            }

        }

        if (data.equals("CallReminder")){
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ActionTypeName1 = jsonObject.getString("ActionTypeName")
            val EnquiryAbout1 = jsonObject.getString("EnquiryAbout")
            val Status1 = jsonObject.getString("Status")
           // setReminder(ActionTypeName1,EnquiryAbout1,Status1)
        }

        if (data.equals("agendaType")){
            val jsonObject = agendaTypeArrayList.getJSONObject(position)
//            if (jsonObject.getString("Id_Agenda").equals("1")){
////                Lead
//            }
//            if (jsonObject.getString("Id_Agenda").equals("2")){
////                Service
//            }
            Log.e(TAG," 8301   6")
            Id_Agenda = jsonObject.getString("Id_Agenda")
            Log.e(TAG," 8301   7     "+Id_Agenda)
            agendaTypeClick = "0"
        //    getActionTypes(Id_Agenda)

            selectedPos = position
            if (Id_Agenda.equals("1")){

                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                ID_Branch  = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
                ID_Lead_Details = ""
                strLeadValue = ""

                rv_todolist!!.visibility  =View.VISIBLE
                rv_todolist!!.adapter = null
                toDoDet = 0
                getTodoList()
            }
            else if (Id_Agenda.equals("2")){
                rv_todolist!!.visibility  =View.GONE

//                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
//                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//
//                ID_Branch  = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
//                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//                ID_Lead_Details = ""
//                strLeadValue = ""
//
//                rv_todolist!!.visibility  =View.VISIBLE
//                rv_todolist!!.adapter = null
//                toDoDet = 0
//                getTodoList()
            }




        }

        if (data.equals("agendaDocument")){
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
            val ID_LeadGenerateProduct = jsonObject.getString("ID_LeadGenerateProduct")
            val i = Intent(this@AgendaActivity, DocumentListActivity::class.java)
            i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
            i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
          //  i.putExtra("jsonObject", jsonObject.toString())
            startActivity(i)
        }

        if (data.equals("DocumentList")){
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
            val ID_LeadGenerateProduct = jsonObject.getString("ID_LeadGenerateProduct")
            val i = Intent(this@AgendaActivity, DocumentListActivity::class.java)
            i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
            i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
           // i.putExtra("jsonObject", jsonObject.toString())
            startActivity(i)
        }

        if (data.equals("agendaList")){

//            val jsonObject = agendaDetailArrayList.getJSONObject(position)
//            val i = Intent(this@AgendaActivity, AccountDetailsActivity::class.java)
//            i.putExtra("jsonObject",jsonObject.toString())
//            startActivity(i)

        }

        if (data.equals("todocall")){

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
                val jsonObject = todoArrayList.getJSONObject(position)
                Log.e("TODO"," 289     jsonObject    "+jsonObject)
                val mobileno = jsonObject.getString("LgCusMobile")
                val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                val BroadCallEditer = BroadCallSP.edit()
                BroadCallEditer.putString("BroadCall", "Yes")
                BroadCallEditer.putString("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
                BroadCallEditer.putString("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
                BroadCallEditer.putString("FK_Employee", jsonObject.getString("FK_Employee"))
                BroadCallEditer.putString("AssignedTo", jsonObject.getString("AssignedTo"))
                BroadCallEditer.commit()


                Log.e("TODO","8001   "+mobileno)
                Log.e("TODO","8001   "+jsonObject.getString("ID_LeadGenerate"))
                Log.e("TODO","8002   "+jsonObject.getString("ID_LeadGenerateProduct"))

                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)

//                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
//                startActivity(intent)

            }
        }

        if (data.equals("todoMessage")){
            val jsonObject = todoArrayList.getJSONObject(position)
            Log.e("TAG","313  ID_LeadGenerate   :  "+jsonObject.getString("ID_LeadGenerate"))
            messagePopup()
        }


        if (data.equals("branch")){
            dialogBranch!!.dismiss()
//             val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchSort.getJSONObject(position)
            Log.e(TAG,"ID_Branch   "+jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))


        }

        if (data.equals("employeeAll")){
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("LeadDetail1")){
            dialogleadDetail!!.dismiss()
            val jsonObject = leadDetailSort.getJSONObject(position)
            Log.e(TAG,"ID_TodoListLeadDetails   "+jsonObject.getString("ID_TodoListLeadDetails"))
            ID_Lead_Details = jsonObject.getString("ID_TodoListLeadDetails")
            tie_LeadDetails!!.setText(jsonObject.getString("TodoListLeadDetailsName"))
            til_LeadValue!!.setHint(jsonObject.getString("TodoListLeadDetailsName"))


        }


    }

    private fun setReminder(ActionTypeName1 : String,EnquiryAbout1: String,descriptn: String) {
        try
        {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reminder_agenda_popup, null)
            val btncancel = layout.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout.findViewById(R.id.btnsubmit) as Button

            val tv_header = layout.findViewById(R.id.tv_header) as TextView
            etdate = layout.findViewById(R.id.etdate) as EditText
            ettime = layout.findViewById(R.id.ettime) as EditText
            etdis = layout.findViewById(R.id.etdis) as EditText

//            val desc = ActionTypeName1+"\n"+EnquiryAbout1+"\n"+Status1
            tv_header!!.setText("Reminder".toUpperCase())
            etdis!!.setText(descriptn)

            etdate!!.setKeyListener(null)
            ettime!!.setKeyListener(null)
            builder.setView(layout)
            val alertDialog = builder.create()
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val sdf1 = SimpleDateFormat("hh:mm a")
            val sdf2 = SimpleDateFormat("hh:mm")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            etdate!!.setText(sdf.format(c.time))
            ettime!!.setText(sdf1.format(c.time))
            val s = sdf2.format(c.time)
            val split = s.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val strhr = split[0]
            val strmin = split[1]
            hr = Integer.parseInt(strhr)
            min = Integer.parseInt(strmin)
            ettime!!.setOnClickListener(View.OnClickListener {
//                timeSelector()
                openBottomSheetTime()
            })
            etdate!!.setOnClickListener(View.OnClickListener {
//                dateSelector()
                sortFilter = 2
                openBottomSheet()
            })
            btncancel.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                alertDialog.dismiss() }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun messagePopup() {
        try {

            messageType = ""
            cbWhat = "0"
            cbEmail = "0"
            cbMessage = "0"

            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.send_message_popup)
            dialog1.window!!.attributes.gravity = Gravity.CENTER;

            val rbMessages = dialog1 .findViewById(R.id.rbMessages) as RadioButton
            val rbReminder = dialog1 .findViewById(R.id.rbReminder) as RadioButton
            val rbIntimation = dialog1 .findViewById(R.id.rbIntimation) as RadioButton

            val edt_message = dialog1 .findViewById(R.id.edt_message) as EditText

            val chk_whats = dialog1 .findViewById(R.id.chk_whats) as CheckBox
            val chk_Email = dialog1 .findViewById(R.id.chk_Email) as CheckBox
            val chk_Message = dialog1 .findViewById(R.id.chk_Message) as CheckBox

            val btnMssubmit = dialog1 .findViewById(R.id.btnMssubmit) as Button
            val btnMscancel = dialog1 .findViewById(R.id.btnMscancel) as Button

            val segmented2 = dialog1 .findViewById(R.id.segmented2) as SegmentedGroup
            segmented2.setTintColor(resources.getColor(R.color.color_msg_tab));
            segmented2.setOnCheckedChangeListener(this@AgendaActivity);

            rbMessages.isChecked  =true
            rbReminder.isChecked  =false
            rbIntimation.isChecked  =false

            chk_whats.setOnClickListener {
                if (chk_whats.isChecked){

                    cbWhat = "1"
                }else{
                    cbWhat = "0"
                }
            }

            chk_Email.setOnClickListener {
                if (chk_Email.isChecked){
                    cbEmail = "1"
                }else{
                    cbEmail = "0"
                }
            }

            chk_Message.setOnClickListener {

                if (chk_Message.isChecked){
                    cbMessage = "1"
                }else{
                    cbMessage = "0"
                }
            }

            btnMscancel.setOnClickListener {
                dialog1 .dismiss()
            }

            btnMssubmit.setOnClickListener {
                messageDesc = edt_message.text.toString()
                if (messageType.equals("")){

                }
                else if(messageDesc.equals("")){
                    Config.snackBars(context,it,"Please enter message")
//
                }
                else if (cbWhat.equals("0") && cbEmail.equals("0") && cbMessage.equals("0") ){
                    Config.snackBars(context,it,"Please select sending options")
//
                }
                else{
                    Log.e(TAG,"  927  messageType  "+messageType)
                    Log.e(TAG,"  927  messageDesc  "+messageDesc)
                    Log.e(TAG,"  927  HHHHH  "+cbWhat+"  :   "+cbEmail+"  :  "+cbMessage)

                    Config.Utils.hideSoftKeyBoard(context,it)
                    dialog1 .dismiss()
                    Toast.makeText(context,""+messageDesc,Toast.LENGTH_SHORT).show()
                }
            }

            dialog1.show()

//            val builder = AlertDialog.Builder(this)
//            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout = inflater1.inflate(R.layout.send_message_popup, null)
//
//            var  ss =inflater1.findViewById(android.R.id.segmented2).setTintColor(Color.DKGRAY)
//            builder.setView(layout)
//            val alertDialog = builder.create()
//            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            R.id.rbMessages->{

                messageType = "Message"
                Log.e(TAG,"rbMessages")
            }
            R.id.rbReminder->{
                Log.e(TAG,"rbReminder")
                messageType = "Reminder"
            }
            R.id.rbIntimation->{
                Log.e(TAG,"rbIntimation")
                messageType = "Intimation"
            }
        }
    }


    fun timeSelector() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val strDate = String.format(
                    "%02d:%02d %s", if (hourOfDay == 0) 12 else hourOfDay,
                    minute, if (hourOfDay < 12) "am" else "pm"
                )
                ettime!!.setText(strDate)
                hr = hourOfDay
                min = minute
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }


    fun dateSelector() {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    yr = year
                    month = (monthOfYear+1)
                    day = dayOfMonth
                    val days = (if (day < 10) "0$day" else day)
                    val mnth = (if (month < 10) "0$month" else month)

                    etxt_date!!.setText(days.toString() + "-" + (mnth) + "-" + year)
                }, mYear, mMonth, mDay
            )
            // datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
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

               // etdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)

                if (sortFilter == 0){
                    etxt_date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (sortFilter == 1){
                    etxt_date1!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (sortFilter == 2){
                    etdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
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

                val strTime = String.format(
                    "%02d:%02d %s", if (hr == 0) 12 else hr,
                    min, if (hr < 12) "AM" else "PM"
                )

                ettime!!.setText(strTime)


            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    fun addEvent(iyr: Int, imnth: Int, iday: Int, ihour: Int, imin: Int, descriptn: String, Title: String) {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                1
            )
        }
        val cr = contentResolver
        val beginTime = Calendar.getInstance()
        beginTime.set(2022, 11 - 1, 28, 9, 30)
        val endTime = Calendar.getInstance()
        endTime.set(iyr, imnth, iday, ihour, imin)
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, endTime.timeInMillis)
        values.put(CalendarContract.Events.DTEND, endTime.timeInMillis)
        values.put(CalendarContract.Events.TITLE, Title)
        values.put(CalendarContract.Events.DESCRIPTION, descriptn)


        val calendarId = getCalendarId(context)
        Log.i("Calender", calendarId.toString())
        if(calendarId != null) {
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId)
        }


        val tz = TimeZone.getDefault()
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.id)
        values.put(CalendarContract.Events.EVENT_LOCATION, "India")





        try {
            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
            val reminders = ContentValues()
            reminders.put(CalendarContract.Reminders.EVENT_ID, uri!!.lastPathSegment)
            reminders.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_ALERT
            )
            reminders.put(CalendarContract.Reminders.MINUTES, 10)
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders)
        }catch (e: Exception){
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Reminder set successfully.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    private fun getCalendarId(context: Context): kotlin.Long? {

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR),
                1
            )
        }

        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

        var calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )
        if (calCursor != null && calCursor.count <= 0) {
            calCursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }
        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calName: String
                val calID: String
                val nameCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calName = calCursor.getString(nameCol)
                calID = calCursor.getString(idCol)

                //    Log.d("Calendar name = $calName Calendar ID = $calID")

                calCursor.close()
                return calID.toLong()
            }
        }
        return null




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

    private fun getAgendaDetails(ID_ActionType: String , Id_Agenda : String) {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        recyAgendaDetail!!.adapter  =null
        var agendaDetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaDetailViewModel.getAgendaDetail(this,ID_ActionType,SubMode!!,Id_Agenda, name,date, criteria)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            //  progressDialog!!.dismiss()
                            if (msg!!.length > 0) {

                                if (agendaDetail == 0){
                                    agendaDetail++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   443   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("AgendaDetails")
                                        agendaDetailArrayList = jobjt.getJSONArray("AgendaDetailsList")
                                        if (agendaDetailArrayList.length()>0){
//                                    if (agendaDetail == 0){
//                                        agendaDetail++
                                            //  agendaTypePopup(agendaActionArrayList)

                                            val editor = sharedPreferences!!.edit()
                                            editor.clear()
                                            editor.commit()



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
//                                            onBackPressed()
//                                            finish()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }


                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            val builder = AlertDialog.Builder(
                                this@AgendaActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage(""+e.toString())
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                            onBackPressed()
//                                            finish()
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }

                    })
                progressDialog!!.dismiss()
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

    private fun filterData() {

        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.filter_popup, null)
            builder1.setCancelable(false)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button
            etxt_date  = layout1.findViewById<EditText>(R.id.etxt_date)
            etxt_Name  = layout1.findViewById<EditText>(R.id.etxt_Name)
            criteria = ""
            etxt_date!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date!!.setOnClickListener(View.OnClickListener {
//                dateSelector()
                sortFilter = 0
                openBottomSheet()
            })

            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                name = etxt_Name!!.text.toString()
                date = etxt_date!!.text.toString()

                if(etxt_date!!.text.toString().equals("") && etxt_Name!!.text.toString().equals("")) {
                    Toast.makeText(applicationContext, "Please select a value", Toast.LENGTH_LONG)
                        .show()
                }
                else {
//                    getAgendaDetails1(ID_ActionType!!, Id_Agenda)
                    getAgendaDetails(ID_ActionType!!, Id_Agenda)
                    alertDialogSort.dismiss()
                }
            }

            alertDialogSort.show()

        }catch (e: Exception){

        }

    }

    private fun filterBottomData() {

        try {
            val dialog = BottomSheetDialog(this)
            val layout1 = layoutInflater.inflate(R.layout.filter_popup, null)

            val ll_admin_staff = layout1.findViewById(R.id.ll_admin_staff) as LinearLayout

            val txtCancel = layout1.findViewById(R.id.txtCancel) as TextView
            val txtSubmit = layout1.findViewById(R.id.txtSubmit) as TextView

            til_LeadValue = layout1.findViewById(R.id.til_LeadValue) as TextInputLayout
            tie_Branch = layout1.findViewById(R.id.tie_Branch) as TextInputEditText
            tie_Employee = layout1.findViewById(R.id.tie_Employee) as TextInputEditText
            tie_LeadDetails = layout1.findViewById(R.id.tie_LeadDetails) as TextInputEditText
            tie_LeadValue = layout1.findViewById(R.id.tie_LeadValue) as TextInputEditText

            val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
            val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
            val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
            val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

            ID_Branch  = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
            tie_Branch !!.setText( BranchNameSP.getString("BranchName", null))
            ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
            tie_Employee!!.setText( UserNameSP.getString("UserName", null))
            ID_Lead_Details = ""
            tie_LeadDetails!!.setText("")
            tie_LeadValue!!.setText("")
            til_LeadValue!!.setHint("")



            tie_Branch!!.setOnClickListener(this)
            tie_Employee!!.setOnClickListener(this)

            etxt_date  = layout1.findViewById<EditText>(R.id.etxt_date)
            etxt_Name  = layout1.findViewById<EditText>(R.id.etxt_Name)
            criteria = ""
            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)
            if (isAdmin.equals("1")){
                ll_admin_staff!!.visibility  =View.VISIBLE
                tie_Branch!!.isEnabled  = true
                tie_Employee!!.isEnabled  = true
            }else{
                ll_admin_staff!!.visibility  =View.GONE
                tie_Branch!!.isEnabled  = false
                tie_Employee!!.isEnabled  = false
            }


            tie_Branch!!.setOnClickListener(View.OnClickListener {

                Config.disableClick(it)
                Log.e(TAG," 796   tie_Branch")
                ID_Employee = ""
                tie_Employee!!.setText("")
                branch = 0
                getBranch()
            })

            tie_Employee!!.setOnClickListener(View.OnClickListener {
                Config.disableClick(it)
                Log.e(TAG," 796   tie_Employee")
                if (ID_Branch.equals("")){
                    Config.snackBars(context,it,"Select Branch")
                }else{
                    empUseBranch = 0
                    getEmpByBranch()
                }

            })

            tie_LeadDetails!!.setOnClickListener(View.OnClickListener {

                Log.e(TAG," 796   tie_LeadDetails")

                Config.disableClick(it)
                leadDetails = 0
                getLeadDetails()
            })


            txtCancel.setOnClickListener {
                dialog.dismiss()
            }

            txtSubmit.setOnClickListener {
                strLeadValue  = tie_LeadValue!!.text.toString()
                if (ID_Branch.equals("")){
                    Toast.makeText(applicationContext, "Select Branch", Toast.LENGTH_SHORT).show()
                    // Config.snackBars(context,it,"Select Branch")
                }
                else if (ID_Employee.equals("")){
                    Toast.makeText(applicationContext, "Select Employee", Toast.LENGTH_SHORT).show()
                    // Config.snackBars(context,it,"Select Employee")
                }
//                else if (ID_Lead_Details.equals("")){
//                    Toast.makeText(applicationContext, "Select Lead Details", Toast.LENGTH_SHORT).show()
//                    // Config.snackBars(context,it,"Select Lead Details")
//                }
                else{
                    Log.e(TAG,"927  ")
                    dialog.dismiss()
                    toDoDet = 0
                    getTodoList()


                }
            }


            dialog.setCancelable(false)
            dialog!!.setContentView(layout1)

            dialog.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }



    }

    private fun getBranch() {
//         var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (branch == 0){
                                    branch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1062   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BranchDetails")
                                        branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                        if (branchArrayList.length()>0){

                                            branchPopup(branchArrayList)

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
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        }catch (e :Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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

    private fun branchPopup(branchArrayList: JSONArray) {

        try {

            dialogBranch = Dialog(this)
            dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranch!! .setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!! .findViewById(R.id.recyBranch) as RecyclerView
            val etsearch = dialogBranch!! .findViewById(R.id.etsearch) as EditText

            branchSort = JSONArray()
            for (k in 0 until branchArrayList.length()) {
                val jsonObject = branchArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                branchSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@AgendaActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = BranchAdapter(this@LeadGenerationActivity, branchArrayList)
            val adapter = BranchAdapter(this@AgendaActivity, branchSort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@AgendaActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    branchSort = JSONArray()

                    for (k in 0 until branchArrayList.length()) {
                        val jsonObject = branchArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BranchName").length) {
                            if (jsonObject.getString("BranchName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                branchSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"branchSort               7103    "+branchSort)
                    val adapter = BranchAdapter(this@AgendaActivity, branchSort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogBranch!!.show()
            dialogBranch!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1132   "+e.toString())
        }
    }

    private fun getEmpByBranch() {
//         var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                empByBranchViewModel.getEmpByBranch(this, ID_Branch)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (empUseBranch == 0){
                                    empUseBranch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeAllArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length()>0){

                                            employeeAllPopup(employeeAllArrayList)

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
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        }catch (e :Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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

    private fun employeeAllPopup(employeeAllArrayList: JSONArray) {
        try {

            dialogEmployeeAll = Dialog(this)
            dialogEmployeeAll!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAll!! .setContentView(R.layout.employeeall_popup)
            dialogEmployeeAll!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAll = dialogEmployeeAll!! .findViewById(R.id.recyEmployeeAll) as RecyclerView
            val etsearch = dialogEmployeeAll!! .findViewById(R.id.etsearch) as EditText


            employeeAllSort = JSONArray()
            for (k in 0 until employeeAllArrayList.length()) {
                val jsonObject = employeeAllArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeAllSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@AgendaActivity, 1)
            recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
            val adapter = EmployeeAllAdapter(this@AgendaActivity, employeeAllSort)
            recyEmployeeAll!!.adapter = adapter
            adapter.setClickListener(this@AgendaActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeAllSort = JSONArray()

                    for (k in 0 until employeeAllArrayList.length()) {
                        val jsonObject = employeeAllArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeAllSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllSort               7103    "+employeeAllSort)
                    val adapter = EmployeeAllAdapter(this@AgendaActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLeadDetails() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadDetailViewModel.getLeadDetail(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (leadDetails == 0){
                                    leadDetails++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TodoListLeadDetails")
                                        leadDetailArrayList = jobjt.getJSONArray("TodoListLeadDetailsList")
                                        if (leadDetailArrayList.length()>0){

                                            Log.e(TAG,"leadDetailArrayList   1205    "+leadDetailArrayList)
                                            leadDetailPopup(leadDetailArrayList)
                                            //   employeeAllPopup(leadDetailArrayList)

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
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        }catch (e :Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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


    private fun leadDetailPopup(leadDetailArrayList: JSONArray) {
        try {

            dialogleadDetail = Dialog(this)
            dialogleadDetail!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogleadDetail!! .setContentView(R.layout.leaddetail_popup)
            dialogleadDetail!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyleadDetail = dialogleadDetail!! .findViewById(R.id.recyleadDetail) as RecyclerView
            val etsearch = dialogleadDetail!! .findViewById(R.id.etsearch) as EditText


            leadDetailSort = JSONArray()
            for (k in 0 until leadDetailArrayList.length()) {
                val jsonObject = leadDetailArrayList.getJSONObject(k)
                leadDetailSort.put(jsonObject)
            }


            try {
                val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                recyleadDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = LeadDetailAdapter(this@AgendaActivity, leadDetailSort)
                recyleadDetail!!.adapter = adapter
                adapter.setClickListener(this@AgendaActivity)
            }catch (e: Exception){
                Log.e(TAG,"Exception  1275   "+e.toString())
            }


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadDetailSort = JSONArray()

                    for (k in 0 until leadDetailArrayList.length()) {
                        val jsonObject = leadDetailArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("TodoListLeadDetailsName").length) {
                            if (jsonObject.getString("TodoListLeadDetailsName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                leadDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"leadDetailSort               7103    "+leadDetailSort)
                    val adapter = LeadDetailAdapter(this@AgendaActivity, leadDetailSort)
                    recyleadDetail!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogleadDetail!!.show()
            dialogleadDetail!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun sortData() {

       /* try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.sort_popup, null)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button

            val checkbox_asc = layout1.findViewById<CheckBox>(R.id.checkbox_asc) as CheckBox
            val checkbox_dsc = layout1.findViewById<CheckBox>(R.id.checkbox_dsc)  as CheckBox

            val checkbox_date = layout1.findViewById<CheckBox>(R.id.checkbox_Date)  as CheckBox
            val checkbox_nme = layout1.findViewById<CheckBox>(R.id.checkbox_name)  as CheckBox

            etxt_date1 = layout1.findViewById<EditText>(R.id.etxt_date) as EditText
            etxt_name1 = layout1.findViewById<EditText>(R.id.etxt_name)  as EditText


            etxt_date1!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date1!!.setOnClickListener(View.OnClickListener { dateSelector1() })

            if (checkbox_asc.isChecked)
            {
                criteria ="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false
            }
            if (checkbox_dsc.isChecked){
                criteria ="2"
                checkbox_asc.isChecked=false
                checkbox_dsc.isChecked=true
            }
            if (checkbox_date.isChecked)
            {
               date =etxt_date1!!.text.toString()

            }
            else
            {
                date =""
            }
            if (checkbox_nme.isChecked)
            {
               date =etxt_name1!!.text.toString()

            }
            else
            {
               name =""
            }
            if(!checkbox_asc.isChecked()&& !checkbox_dsc.isChecked)
            {
                Toast.makeText(applicationContext,"Please select a value",Toast.LENGTH_LONG).show()
            }
            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

               criteria ="1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
               criteria ="2"
            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                if(etxt_date1!!.text.toString().equals("") && etxt_name1!!.text.toString().equals(""))
                {
                    Toast.makeText(applicationContext,"Please enter a value",Toast.LENGTH_LONG).show()
                }
                else
                {
                    getSortList()
                    alertDialogSort.dismiss()
                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }*/
        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.sort_popup, null)
            builder1.setCancelable(false)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button

            val checkbox_asc = layout1.findViewById<CheckBox>(R.id.checkbox_asc) as CheckBox
            val checkbox_dsc = layout1.findViewById<CheckBox>(R.id.checkbox_dsc)  as CheckBox

            val checkbox_date = layout1.findViewById<CheckBox>(R.id.checkbox_Date)  as CheckBox
            val checkbox_nme = layout1.findViewById<CheckBox>(R.id.checkbox_name)  as CheckBox

            etxt_date1 = layout1.findViewById<EditText>(R.id.etxt_date) as EditText
            etxt_name1 = layout1.findViewById<EditText>(R.id.etxt_name)  as EditText
            etxt_date1!!.setKeyListener(null)


            //  etxt_date1!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date1!!.setOnClickListener(View.OnClickListener {
//                dateSelector1()
                sortFilter = 1
                openBottomSheet()
            })

            if (checkbox_asc.isChecked)
            {
                criteria ="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false
                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)


            }
            if (checkbox_dsc.isChecked){
                criteria ="2"
                checkbox_asc.isChecked=false
                checkbox_dsc.isChecked=true

                val image = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_dsc.setButtonDrawable(image1)
            }


            checkbox_date.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image2 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_date.setButtonDrawable(image2)
                    date =etxt_date1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_date.setButtonDrawable(image5)
                    date =""
                }
            })

            checkbox_nme.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image4 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_nme.setButtonDrawable(image4)
                    OverDueActivity.name1 =etxt_name1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_nme.setButtonDrawable(image5)
                    OverDueActivity.name1 =""
                }
            })



            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)

                criteria ="1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
                criteria ="2"

                val image = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_dsc.setButtonDrawable(image1)
            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                date =etxt_date1!!.text.toString()
                name =etxt_name1!!.text.toString()
                Log.i("Detail",date+"\n"+name)

                if(date.equals("")&& name.equals("") )
                {
                    Toast.makeText(applicationContext, "Please enter a value", Toast.LENGTH_LONG)
                        .show()
                }

                else
                {

                    getAgendaDetails(ID_ActionType!!, Id_Agenda)
                    alertDialogSort.dismiss()

//                    if(!(date.equals("")))
//                    {
//                        if (!(checkbox_date.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else
//                        {
//                            getSortList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//                    else if(!(name.equals("")))
//                    {
//                        if (!(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else
//                        {
//                            getSortList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//                    else if(!(date.equals(""))&& !(name.equals(""))){
//
//
//                        if (!(checkbox_date.isChecked)&&!(checkbox_nme.isChecked)){
//
//
//
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        if (!(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else if (!(checkbox_date.isChecked)&&(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//
//                        else
//                        {
//                            getSortList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }

                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }

    }


    fun dateSelector1() {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    yr = year
                    month = (monthOfYear+1)
                    day = dayOfMonth
                    //  etxt_date1!!.setText(dayOfMonth.toString() + "-" + (monthOfYear) + "-" + year)g

                    val days = (if (day < 10) "0$day" else day)
                    val mnth = (if (month < 10) "0$month" else month)

                    etxt_date1!!.setText(days.toString() + "-" + (mnth) + "-" + year)

                }, mYear, mMonth, mDay
            )
            //    datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}