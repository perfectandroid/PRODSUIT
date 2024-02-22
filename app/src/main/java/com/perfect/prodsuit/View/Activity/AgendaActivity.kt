package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract
import android.provider.CallLog
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Receivers.PhoneStatReceiver
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import info.hoang8f.android.segmented.SegmentedGroup
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Long
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.CharSequence
import kotlin.Exception
import kotlin.Int
import kotlin.String
import kotlin.arrayOf
import kotlin.toString

class AgendaActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener,
    ItemClickListenerData,
    OnMapReadyCallback,
    RadioGroup.OnCheckedChangeListener {

    var lstChkArray = ArrayList<String>()
    var textid: TextView? = null
    var coun: TextView? = null
    var lcoun: LinearLayout? = null


    var servicesecond: LinearLayout? = null
    var leadfirst: LinearLayout? = null
    var emithird: LinearLayout? = null

    //...............
    var mainpage: LinearLayout? = null
    var firstpage: LinearLayout? = null
    var secondpage: LinearLayout? = null
    var thirdpage: LinearLayout? = null

    private var lead_count_agenda: TextView? = null
    private var service_count_agenda: TextView? = null
    private var emi_count_agenda: TextView? = null

    private var lead_count: TextView? = null
    private var service_count: TextView? = null
    private var emi_count: TextView? = null

    lateinit var leadArrayList: JSONArray
    lateinit var serviceArrayList: JSONArray
    lateinit var emiArrayList: JSONArray
    var agendaAllListCount = 0
    var count_LEAD: String? = "0"
    var count_SERVICE: String? = "0"
    var count_EMI: String? = "0"

    var leadClick: String? = "1"
    var serviceClick: String? = "1"
    var emiClick: String? = "1"
//    var leadClick = 0
//    var serviceClick = 0
//    var emiClick = 0


    var relativeTab: RelativeLayout? = null
    var ll_tab_lead: LinearLayout? = null
    var ll_tab_service: LinearLayout? = null
    var ll_tab_emi: LinearLayout? = null
    //  var countLead: TextView? = null
//    var countService: TextView? = null
//    var countEmi: TextView? = null

    var strCustomer = ""
    var strMobile = ""
    var strArea = ""
    var strDueAmount = ""

    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_Due_Amount: TextInputEditText? = null


    lateinit var emiListSort: JSONArray
    var recyEmiList: RecyclerView? = null
    lateinit var emiListViewModel: EmiListViewModel
    lateinit var emiListArrayList: JSONArray
    private var ReqMode: String? = ""
    lateinit var emiCountViewModel: EmiCountViewModel
    private var ID_FinancePlanType: String = ""
    private var AsOnDate: String = ""
    private var ID_Category: String = ""
    private var ID_Area: String = ""
    private var Demand: String = "30"


    var emiCount = 0


    lateinit var serviceFollowUpInfoViewModel: ServiceFollowUpInfoViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    var city = ""
    private lateinit var currentLocation: Location
    var longitude = ""
    var latitude = ""
    var serviceFollowUpInfo = 0
    private val agendaList1 = ArrayList<String>()
    private val agendaNameList = ArrayList<String>()
    var checkEmptyList: LinearLayout? = null
    var lltab: LinearLayout? = null
    var tabLayout: TabLayout? = null
    var tabLayout1: TabLayout? = null
    var tabLayout2: TabLayout? = null
    var tabLayout3: TabLayout? = null
    var llMainDetail: LinearLayout? = null
    var agendaEmpty: LinearLayout? = null
    var strFromDate = ""
    var strToDate = ""
    val TAG: String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    private var progressDialog1: ProgressDialog? = null
    lateinit var context: Context
    var sharedPreferences: SharedPreferences? = null
    private lateinit var tv_nodata: TextView
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int = 0
    internal var month: Int = 0
    internal var day: Int = 0
    internal var hr: Int = 0
    internal var min: Int = 0
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var Id_Agenda: String = ""

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
    var agendaTypeClick: String? = "0"
    var ID_ActionType: String? = ""
    var SubMode: String? = "0"
    internal var sortFilter: Int = 0
    var selectedPos: Int = 0

    lateinit var agendaTypeViewModel: AgendaTypeViewModel
    lateinit var sortAgendaViewModel: SortAgendaViewModel
    lateinit var agendaTypeArrayList: JSONArray

    lateinit var agendaCountViewModel: AgendaCountViewModel
    lateinit var agendaActionViewModel: AgendaActionViewModel
    lateinit var agendaDetailViewModel: AgendaDetailViewModel
    lateinit var agendaActionArrayList: JSONArray
    lateinit var agendaActionSort: JSONArray
    lateinit var agendaDetailArrayList: JSONArray
    var dialogAgendaAction: Dialog? = null
    var recyActionType: RecyclerView? = null
    var recyAgendaDetail: RecyclerView? = null
    var recyAgendaType: RecyclerView? = null
    var submode = "0";

    var imgv_filter1: ImageView? = null
    var imgv_filter: ImageView? = null
    var imgv_filterEmi: ImageView? = null
    var imgv_filter2: ImageView? = null


    var txtSelectPend: TextView? = null

    //  var inflaterPend : LayoutInflater? = null
    var myReceiver: PhoneStatReceiver = PhoneStatReceiver()

    companion object {
        var CUSTOM_INTENT: String? = "PRODSUIT_CALL"
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
    var statusposition = 0
    var toDoDet = 0
    private var fromfilter = ""
    lateinit var todolistViewModel: TodoListViewModel
    lateinit var serviceFollowUpListViewModel: ServiceFollowUpListViewModel
    private var rv_todolist: RecyclerView? = null
    private var rv_todolist1: RecyclerView? = null  //service_rv
    lateinit var todoArrayList: JSONArray
    lateinit var todoArrayList1: JSONArray
    lateinit var listCurrentDay: JSONArray


    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchSort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null
    var branch = 0

    var empUseBranch = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList: JSONArray
    lateinit var employeeAllSort: JSONArray
    private var dialogEmployeeAll: Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    var leadDetails = 0
    lateinit var leadDetailViewModel: LeadDetailViewModel
    lateinit var leadDetailArrayList: JSONArray
    lateinit var leadDetailSort: JSONArray
    private var dialogleadDetail: Dialog? = null
    var recyleadDetail: RecyclerView? = null

    var til_LeadValue: TextInputLayout? = null
    var tie_Branch: TextInputEditText? = null
    var tie_Employee: TextInputEditText? = null
    var tie_LeadDetails: TextInputEditText? = null
    var tie_LeadValue: TextInputEditText? = null
    var iLead = ""
    var iService = ""
    var iCollection = ""

    lateinit var agendalistViewModel: AgendaListViewModel

    private var temp_ID_Branch: String = "0"
    private var temp_Branch: String = ""
    private var temp_ID_Employee: String = "0"
    private var temp_Employee: String = ""
    private var temp_ID_Branch1: String = ""
    private var temp_Branch1: String = ""
    private var temp_ID_Employee1: String = ""
    private var temp_Employee1: String = ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_agenda)

        context = this@AgendaActivity
        agendaTypeViewModel = ViewModelProvider(this).get(AgendaTypeViewModel::class.java)
        agendaCountViewModel = ViewModelProvider(this).get(AgendaCountViewModel::class.java)
        agendaActionViewModel = ViewModelProvider(this).get(AgendaActionViewModel::class.java)
        agendaDetailViewModel = ViewModelProvider(this).get(AgendaDetailViewModel::class.java)
        sortAgendaViewModel = ViewModelProvider(this).get(SortAgendaViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        leadDetailViewModel = ViewModelProvider(this).get(LeadDetailViewModel::class.java)

        agendalistViewModel = ViewModelProvider(this).get(AgendaListViewModel::class.java)

        sharedPreferences = context!!.getSharedPreferences("AgendaReminder", Context.MODE_PRIVATE)


//        val telephony = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//        telephony.listen(myReceiver, PhoneStateListener.LISTEN_CALL_STATE)
////        val intentFilter = IntentFilter("com.perfect.prodsuit.Receivers.PhoneStatReceiver")
////        intentFilter.addAction(TelephonyManager.EXTRA_STATE)
////        registerReceiver(myReceiver, intentFilter)

        setRegViews()
        loadLoginEmpDetails()


        SubMode = "1"
        name = ""
        date = ""
        criteria = ""
        ID_LeadGenerate = ""

        agendaAllListCount = 0
        getAgendaList()


        // getAgendaCounts()

        //    getAgendatypes()
        //    getActionTypes()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun loadLoginEmpDetails() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()



        temp_ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
        temp_Branch = BranchSP.getString("BranchName", null).toString()
        temp_ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        temp_Employee = UserNameSP.getString("UserName", null).toString()

    }


    private fun apiCall() {
        toDoDet = 0
        emiCount = 0
        getTodoList()
        getTodoList1()
        getEmiAgenda()

    }

    private fun addTabItem() {




        val DashBordMenu = context.getSharedPreferences(Config.SHARED_PREF54, 0)
        val jsonObj = JSONObject(DashBordMenu.getString("ModuleList", ""))
        iLead = jsonObj!!.getString("LEAD")
        iService = jsonObj!!.getString("SERVICE")
        iCollection = jsonObj!!.getString("ACCOUNTS")


        val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
        val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//
//        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
//        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        ID_Lead_Details = ""
        strLeadValue = ""




        if (iLead.equals("true")) {
            Log.i("rtretr", "iLead only=")

            leadfirst!!.visibility = View.VISIBLE


        }
        if (iService.equals("true")) {

            servicesecond!!.visibility = View.VISIBLE

            Log.i("rtretr", "Service only=")

        }
        if (iCollection.equals("true")) {

            emithird!!.visibility = View.VISIBLE
        }

        //........................


        if (iLead.equals("false") && iService.equals("false") && iCollection.equals("false")) {
            lltab!!.visibility = View.GONE
            relativeTab!!.visibility = View.GONE
            val builder = AlertDialog.Builder(
                this@AgendaActivity,
                R.style.MyDialogTheme
            )
            builder.setMessage("No Data")
            builder.setPositiveButton("OK") { dialogInterface, which ->
                finish()


            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } else {


            if (iLead.equals("true")) {
                Log.i("responseCheckApi", "leadsAPi")
                leadfirst!!.visibility = View.VISIBLE
            //    getLeadList(leadArrayList)
                //............................



            } else if (iService.equals("true")) {
                Log.i("responseCheckApi", "ServicesAPi")
                servicesecond!!.visibility = View.VISIBLE
             //   getServiceList(serviceArrayList)

                //............................



            } else if (iCollection.equals("true")) {
                Log.i("responseCheckApi", "EmiApi")
                emithird!!.visibility = View.VISIBLE
            //    getEmi(emiArrayList)
                //............................



            }

        }


//...................................


//        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
////        Log.i("rtretrerwerwrer","Service only="+ tabLayout!!.getTabAt(0)!!.text)
//        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//
//
//                if (tabLayout!!.getTabAt(tab.position)!!.text!!.equals("Leads")) {
//                    Log.i("responseCheckApi", "lead click")

//
//                }
//                if (tabLayout!!.getTabAt(tab.position)!!.text!!.equals("Services")) {
//
//
//
//                }
//                if (tabLayout!!.getTabAt(tab.position)!!.text!!.equals("Emi")) {
//

////            //...........
//                    emiCount = 0
//                    getEmiAgenda()
//                }
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//                Log.e(TAG, "onTabUnselected  162  " + tab.position)
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//                Log.e(TAG, "onTabReselected  165  " + tab.position)
//            }
//        })

//.....................................................
    }

    private fun getEmi(emiArrayList: JSONArray) {
        if (emiArrayList.length() > 0) {
            setEmiAgendaRecycler(emiArrayList)
        } else {
            val builder = AlertDialog.Builder(
                this@AgendaActivity,
                R.style.MyDialogTheme
            )
            emi_count!!.setText("0")



            builder.setMessage("No data Found")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
             //   dialogInterface.dismiss()
                onBackPressed()
                //                                               finish()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    private fun getServiceList(serviceArrayList: JSONArray) {

        if (serviceArrayList.length() > 0) {
            setServiceRecyclerAgenda(serviceArrayList)
        } else {
            val builder = AlertDialog.Builder(
                this@AgendaActivity,
                R.style.MyDialogTheme
            )
            service_count!!.setText("0")



            builder.setMessage("No data Found")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
             //   dialogInterface.dismiss()
                onBackPressed()
                //                                               finish()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }


    }


    private fun getLeadList(leadArrayList: JSONArray) {

        if (leadArrayList.length() > 0) {
            setLeasdRecyClerviewAdapter(leadArrayList)
        } else {
            val builder = AlertDialog.Builder(
                this@AgendaActivity,
                R.style.MyDialogTheme
            )
          //  lead_count!!.setText("0")



            builder.setMessage("No data Found")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
              //  dialogInterface.dismiss()
                onBackPressed()
                //                                               finish()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }


    }

    private fun getEmiAgenda() {
        // Toast.makeText(context,"Emi api",Toast.LENGTH_SHORT).show()
        getEmiList()
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun getAgendaList() {
        Log.e("responseww", "AsOnDate=  " + AsOnDate)
        ReqMode = "105"
        SubMode = "0"
        context = this@AgendaActivity
        Log.e(TAG,"6363 ID_Branch     "+ID_Branch)
        Log.e(TAG,"6363 ID_Employee   "+ID_Employee)
        agendalistViewModel = ViewModelProvider(this).get(AgendaListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                agendalistViewModel.getAgendalist(
                    this,
                    ReqMode!!,
                    SubMode!!,
                    ID_FinancePlanType!!,
                    AsOnDate!!,
                    ID_Category!!,
                    ID_Area!!,
                    Demand!!,ID_Branch!!,ID_Employee!!

                )!!.observe(
                    this

                ) { todolistSetterGetter ->
                    try {
                        val msg = todolistSetterGetter.message
                        Log.i("weweqweqwe", "msg=" + msg!!.length)

                        if (msg!!.length > 0) {
                            if (agendaAllListCount == 0) {
                                agendaAllListCount++

                                val editor = sharedPreferences!!.edit()
                                editor.clear()
                                editor.commit()

                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("AgendaCountDtls")

                                    count_LEAD = jobjt.get("LEAD").toString()
                                    count_SERVICE = jobjt.get("SERVICE").toString()
                                    count_EMI = jobjt.get("EMI").toString()



                                    Log.i("fgdgf", "lead=" + count_LEAD)
                                    Log.i("fgdgf", "SERVICE=" + count_SERVICE)
                                    Log.i("fgdgf", "emi=" + count_EMI)
                                    leadArrayList =
                                        jobjt.getJSONArray("LeadData")
                                    serviceArrayList = jobjt.getJSONArray("ServiceData")
                                    emiArrayList = jobjt.getJSONArray("EMIData")

                                    Log.i("fgdgf1", "lead size=" + leadArrayList.length())
                                    Log.i("fgdgf1", "service size=" + serviceArrayList.length())
                                    Log.i("fgdgf1", "emi size=" + emiArrayList.length())
                                    //    addTabItem()

                                  //add count here

                                    addCount(
                                        count_LEAD!!,
                                        count_SERVICE!!,
                                        count_EMI!!,
                                        leadArrayList,
                                        serviceArrayList,
                                        emiArrayList
                                    )


                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AgendaActivity,
                                        R.style.MyDialogTheme
                                    )
                                    lead_count!!.setText("0")

                                    service_count!!.setText("0")
                                    emi_count!!.setText("0")
                                    Log.i("fgdgf1", "EXMessage=" + jObject.getString("EXMessage"))
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                       // dialogInterface.dismiss()
                                        onBackPressed()
                                        //                                               finish()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }

                            }
                        }


                    } catch (e: Exception) {
                        Log.i("responseCatch", "ex=" + e.printStackTrace())
                    }
                }
                progressDialog!!.dismiss()
            }
            else -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun addCount(
        countLead: String, countService: String, countEmi: String,
        leadArrayList: JSONArray, serviceArrayList: JSONArray, emiArrayList: JSONArray
    ) {

        lead_count_agenda!!.text = countLead
        service_count_agenda!!.text = countService
        emi_count_agenda!!.text = countEmi

        addTabItem()
    }

    private fun getEmiList() {
        ReqMode = "100"
        SubMode = "1"
        recyEmiList!!.adapter = null
        emiListViewModel = ViewModelProvider(this).get(EmiListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                //  Log.i("response999", "SubMode=="+1)
                Log.i("response99933", "ID_FinancePlanType==" + ID_FinancePlanType)
                Log.i("response99933", "AsOnDate==" + AsOnDate)
                Log.i("response99933", "ID_Category==" + ID_Category)
                Log.i("response99933", "ID_FinancePlanType==" + ID_Area)
                Log.i("response99933", "Demand==" + Demand)

                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiListViewModel.getEmiList(
                    this,
                    ReqMode!!,
                    SubMode!!,
                    ID_FinancePlanType!!,
                    AsOnDate!!,
                    ID_Category!!,
                    ID_Area!!,
                    Demand!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            Log.i("weweqweqwe", "msg=" + msg!!.length)
                            if (msg!!.length > 0) {

                                if (emiCount == 0) {

                                    Log.i("weweqweqwe", "emiCount" + emiCount)
                                    emiCount++
                                    val jObject = JSONObject(msg)

                                    Log.e(TAG, "msg   1530   " + jObject)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.i("weweqweqwe", "StatusCode")


                                        val jobjt = jObject.getJSONObject("EMICollectionReport")
                                        emiListArrayList =
                                            jobjt.getJSONArray("EMICollectionReportList")


                                        Log.i(
                                            "weweqweqwe",
                                            "list size=" + emiListArrayList.length()
                                        )
                                        //   Log.i("weweqweqwe","list=="+emiListArrayList.toString(0))

                                        if (emiListArrayList.length() > 0) {
//                                            for (k in 0 until emiListArrayList.length()) {
//                                                val jsonObject = emiListArrayList.getJSONObject(k)
//                                                // reportNamesort.put(k,jsonObject)
//                                                emiListSort.put(jsonObject)
//                                            }


                                            setEmiAgendaRecycler(emiListArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@AgendaActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("OK") { dialogInterface, which ->
                                            //     finish()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issuesfghfghdfjhfg.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()

                            Log.i("responseCatchEmiList", "" + e.toString())


                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    private fun setRegViews() {
        lcoun = findViewById<LinearLayout>(R.id.lcoun)
        textid = findViewById<TextView>(R.id.textid);
        coun = findViewById<TextView>(R.id.coun);

        emithird = findViewById<LinearLayout>(R.id.emithird)
        servicesecond = findViewById<LinearLayout>(R.id.servicesecond)
        leadfirst = findViewById<LinearLayout>(R.id.leadfirst)




        mainpage = findViewById<LinearLayout>(R.id.mainpage)
        firstpage = findViewById<LinearLayout>(R.id.firstpage)
        secondpage = findViewById<LinearLayout>(R.id.secondpage)
        thirdpage = findViewById<LinearLayout>(R.id.thirdpage)

        lead_count_agenda = findViewById<TextView>(R.id.lead_count_agenda)
        service_count_agenda = findViewById<TextView>(R.id.service_count_agenda)
        emi_count_agenda = findViewById<TextView>(R.id.emi_count_agenda)

        lead_count = findViewById<TextView>(R.id.lead_count);
        service_count = findViewById<TextView>(R.id.service_count);
        emi_count = findViewById<TextView>(R.id.emi_count);


        relativeTab = findViewById<RelativeLayout>(R.id.relativeTab);
        ll_tab_lead = findViewById<LinearLayout>(R.id.ll_tab_lead);
        ll_tab_service = findViewById<LinearLayout>(R.id.ll_tab_service);
        ll_tab_emi = findViewById<LinearLayout>(R.id.ll_tab_emi);

        if (AsOnDate.equals("")) {
            getCurrentDate()
        }
//        countLead = findViewById(R.id.countLead)
//        countService = findViewById(R.id.countService)
//        countEmi = findViewById(R.id.countEmi)

        imgv_filterEmi = findViewById(R.id.imgv_filterEmi)
        recyEmiList = findViewById<RecyclerView>(R.id.recyEmiList)
        checkEmptyList = findViewById<LinearLayout>(R.id.checkId);
        lltab = findViewById<LinearLayout>(R.id.lltab);
        tabLayout = findViewById<TabLayout>(R.id.tabLay);
        tabLayout1 = findViewById<TabLayout>(R.id.tabLay1);
        tabLayout2 = findViewById<TabLayout>(R.id.tabLay2);
        tabLayout3 = findViewById<TabLayout>(R.id.tabLay3);
        llMainDetail = findViewById<LinearLayout>(R.id.llMainDetail);
        agendaEmpty = findViewById<LinearLayout>(R.id.agendaEmpty);

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById(R.id.imgv_filter)
        imgv_filter1 = findViewById(R.id.imgv_filter1)
        imgv_filter2 = findViewById(R.id.imgv_filter2)
        val imgv_sort = findViewById<ImageView>(R.id.imgv_sort)

        imgv_filter!!.setOnClickListener(this)
        imgv_filter2!!.setOnClickListener(this)
        imgv_filter1!!.setOnClickListener(this)
        imgv_sort!!.setOnClickListener(this)
        imgv_filterEmi!!.setOnClickListener(this)
        ll_tab_lead!!.setOnClickListener(this)
        ll_tab_service!!.setOnClickListener(this)
        ll_tab_emi!!.setOnClickListener(this)
        leadfirst!!.setOnClickListener(this)
        servicesecond!!.setOnClickListener(this)
        emithird!!.setOnClickListener(this)

//        tabLayout = findViewById(R.id.tabLayout);
//        llMainDetail = findViewById(R.id.llMainDetail);
//        llPending = findViewById(R.id.llPending);
//        llUpComing = findViewById(R.id.llUpComing);
//        llComplete = findViewById(R.id.llComplete);

        rv_todolist = findViewById(R.id.rv_todolist)
        rv_todolist1 = findViewById(R.id.rv_todolist1)
        llMainLeads = findViewById(R.id.llMainLeads);
        llMainService = findViewById(R.id.llMainService);
        tv_nodata = findViewById(R.id.tv_nodata1)
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

    private fun getCurrentDate() {

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            val newDate: Date = sdf.parse(currentDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            AsOnDate = sdfDate2.format(newDate)

            Log.e(TAG, "AsOnDate 196  " + AsOnDate)

        } catch (e: Exception) {

            Log.e(TAG, "Exception 196  " + e.toString())
        }
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
      //  var TIME: kotlin.Long = (1 * 1000).toLong()
        when (v.id) {

            R.id.leadfirst -> {
          //      leadfirst!!.setEnabled(false)
//                Handler().postDelayed(Runnable { v.isEnabled = true }, TIME)

                mainpage!!.visibility = View.GONE
//                secondpage!!.visibility = View.GONE
                recyEmiList!!.visibility = View.GONE
                rv_todolist1!!.visibility = View.GONE
                textid!!.text="LEAD"
                imgv_filterEmi!!.visibility=View.GONE
                imgv_filter!!.visibility = View.GONE
                imgv_filter1!!.visibility = View.GONE
                Log.e(TAG,"121212   ")
                imgv_filter2!!.visibility = View.VISIBLE

                coun!!.visibility = View.VISIBLE
                coun!!.text=leadArrayList.length().toString()
                rv_todolist!!.visibility = View.VISIBLE
                firstpage!!.visibility = View.VISIBLE
                fab_Reminder!!.visibility = View.VISIBLE



                rv_todolist!!.adapter = null
                getLeadList(leadArrayList)

            }
            R.id.servicesecond -> {
                mainpage!!.visibility = View.GONE
                rv_todolist!!.visibility = View.GONE
                recyEmiList!!.visibility = View.GONE
//                firstpage!!.visibility = View.GONE
//                thirdpage!!.visibility = View.GONE

                imgv_filterEmi!!.visibility=View.GONE
                imgv_filter!!.visibility = View.GONE
                fab_Reminder!!.visibility = View.GONE


                rv_todolist1!!.visibility = View.VISIBLE
                coun!!.visibility = View.VISIBLE
                coun!!.text=serviceArrayList.length().toString()
                textid!!.text="SERVICES"
                secondpage!!.visibility = View.VISIBLE
                imgv_filter1!!.visibility = View.VISIBLE


                rv_todolist1!!.adapter = null
                getServiceList(serviceArrayList)


            }
            R.id.emithird -> {
                mainpage!!.visibility = View.GONE
//                firstpage!!.visibility = View.GONE
//                secondpage!!.visibility = View.GONE
                imgv_filter!!.visibility = View.GONE
                fab_Reminder!!.visibility = View.GONE
                imgv_filter1!!.visibility = View.GONE
                rv_todolist!!.visibility = View.GONE
                rv_todolist1!!.visibility = View.GONE

                recyEmiList!!.visibility=View.VISIBLE
                textid!!.text="EMI"
               coun!!.visibility = View.VISIBLE
                coun!!.text=emiArrayList.length().toString()
                thirdpage!!.visibility = View.VISIBLE
                imgv_filterEmi!!.visibility=View.VISIBLE
                recyEmiList!!.adapter = null
                getEmi(emiArrayList)


            }


            R.id.ll_tab_lead -> {
                ll_tab_emi!!.setBackgroundResource(R.drawable.shape_rectangle_border1)
                ll_tab_service!!.setBackgroundResource(R.drawable.shape_rectangle_border1)
                ll_tab_lead!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg1)


                imgv_filter1!!.visibility = View.GONE
                imgv_filterEmi!!.visibility = View.GONE
                //    countService!!.visibility = View.GONE
                //    countEmi!!.visibility = View.GONE
                recyEmiList!!.visibility = View.GONE
                rv_todolist1!!.visibility = View.GONE

                llMainDetail!!.visibility = View.VISIBLE
                fab_Reminder!!.visibility = View.VISIBLE
                rv_todolist!!.visibility = View.VISIBLE
                imgv_filter!!.visibility = View.VISIBLE
                //   countLead!!.visibility = View.VISIBLE

                rv_todolist!!.adapter = null
//                toDoDet = 0
//                getTodoList()

                lead_count!!.text = leadArrayList.length().toString()
                service_count!!.text = serviceArrayList.length().toString()
                emi_count!!.text = emiArrayList.length().toString()

             //   getLeadList(leadArrayList);

            }
            R.id.ll_tab_service -> {
                ll_tab_emi!!.setBackgroundResource(R.drawable.shape_rectangle_border1)
                ll_tab_service!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg1)
                ll_tab_lead!!.setBackgroundResource(R.drawable.shape_rectangle_border1)

                rv_todolist!!.visibility = View.GONE
                imgv_filter!!.visibility = View.GONE
                imgv_filterEmi!!.visibility = View.GONE
                //  countEmi!!.visibility = View.GONE
                //  countLead!!.visibility = View.GONE
                fab_Reminder!!.visibility = View.GONE
                recyEmiList!!.visibility = View.GONE

                tabLayout!!.getTabAt(0)!!.text
                Log.i("rtretrerwerwrer", "Service only=" + tabLayout!!.getTabAt(0)!!.text)

                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
                ID_Lead_Details = ""
                strLeadValue = ""

                rv_todolist1!!.visibility = View.VISIBLE
                imgv_filter1!!.visibility = View.VISIBLE
                //    countService!!.visibility = View.VISIBLE
                rv_todolist1!!.adapter = null
                toDoDet = 0
                //   getTodoList1()
                lead_count!!.text = leadArrayList.length().toString()
                service_count!!.text = serviceArrayList.length().toString()
                emi_count!!.text = emiArrayList.length().toString()
                getServiceList(serviceArrayList)

            }
            R.id.ll_tab_emi -> {

                ll_tab_emi!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg1)
                ll_tab_service!!.setBackgroundResource(R.drawable.shape_rectangle_border1)
                ll_tab_lead!!.setBackgroundResource(R.drawable.shape_rectangle_border1)

                fab_Reminder!!.visibility = View.GONE
                imgv_filter!!.visibility = View.GONE
                //   countLead!!.visibility = View.GONE
                //  countService!!.visibility = View.GONE
                imgv_filter1!!.visibility = View.GONE
                rv_todolist!!.visibility = View.GONE
                rv_todolist1!!.visibility = View.GONE

                recyEmiList!!.visibility = View.VISIBLE


                imgv_filterEmi!!.visibility = View.VISIBLE

//
                recyEmiList!!.adapter = null
//            //...........
                emiCount = 0
                //         getEmiAgenda()
                lead_count!!.text = leadArrayList.length().toString()
                service_count!!.text = serviceArrayList.length().toString()
                emi_count!!.text = emiArrayList.length().toString()
                getEmi(emiArrayList)

            }

            R.id.imback -> {


                onBackPressed()

            }
            R.id.imgv_filter -> {
                // filterData()
                Config.disableClick(v)
              //  filterBottomData()

                filterBottomDataManagment()


            }
            R.id.imgv_filter1 -> {
                // filterData()
                Config.disableClick(v)
                showFilterAlertService()
                //   Toast.makeText(context,"Service filter",Toast.LENGTH_SHORT).show()
                //   showFilterAlertService()

            }

            R.id.imgv_filter2 -> {
                // filterData()
                Config.disableClick(v)
                filterBottomData()

            }


            R.id.imgv_filterEmi -> {
                Config.disableClick(v)
                filterBottomSheetEmi()
                //  Toast.makeText(context,"Emi filter",Toast.LENGTH_SHORT).show()

            }
            R.id.imgv_sort -> {
                sortData()
            }

            R.id.txtSelectPend -> {
                Log.e(TAG, "txtSelectPend  232   ")
            }
            R.id.tv_tab_pending -> {
                Log.e(TAG, "tv_tab_pending  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                SubMode = "1"
                Log.e(TAG, " 8301   1")
                //  getActionTypes(Id_Agenda)
            }
            R.id.tv_tab_upcoming -> {
                Log.e(TAG, "tv_tab_upcoming  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                SubMode = "2"
                Log.e(TAG, " 8301   2")
                // getActionTypes(Id_Agenda)
            }
            R.id.tv_tab_completed -> {
                Log.e(TAG, "tv_tab_completed  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_color);
                agendaTypeClick = "0"
                SubMode = "3"
                Log.e(TAG, " 8301   3")
                //   getActionTypes(Id_Agenda)
            }
//            R.id.tv_actionType->{
//                Log.e(TAG,"tv_actionType  232   ")
//                agendaTypeClick = "1"
//                Log.e(TAG," 8301   4")
//                getActionTypes(Id_Agenda)
//
//            }
            R.id.fab_Reminder -> {
                var strReminder: String = ""
                var ii: Int = 0
                val gson = Gson()
                val json = sharedPreferences!!.getString("Set", "")
                Log.i("responseShared", "from=" + json)
                var lstChkArray = ArrayList<String>()
                if (json!!.isNotEmpty()) {
                    val collectionType: Type = object : TypeToken<List<String?>?>() {}.getType()
                    val arrPackageData: List<String> = gson.fromJson(json, collectionType)
                    for (reminderText in arrPackageData) {
                        Log.i("lstChk_size   ", "lstChk_size   " + reminderText)
                        Log.e("lstChk_size   ", "lstChk_size   " + reminderText)
                        if (ii == 0) {
                            ii++
                            strReminder = ii.toString() + " . " + reminderText
                        } else {
                            ii++
                            strReminder =
                                strReminder + "\n" + "\n" + ii.toString() + " . " + reminderText
                        }
                        lstChkArray.add(reminderText)
                    }
                }

                if (!lstChkArray.isEmpty()) {
                    //setReminder("You have set reminder for following defaulters accounts "+lstChkArray.toString())
                    Log.e(TAG, "strReminder   3411    " + strReminder)

                    setReminder("", "", strReminder)
                } else {
                    Log.e(TAG, "strReminder   3412    Select")
                    Config.snackBars(context, v, "Select Atlest One Reminder")
                    //   Toast.makeText(context,"Select Account",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun filterBottomDataManagment() {

        try {
            val dialog = BottomSheetDialog(this)
            val layout1 = layoutInflater.inflate(R.layout.filter_manage, null)

            val ll_admin_staff = layout1.findViewById(R.id.ll_admin_staff) as LinearLayout

            val txtCancel = layout1.findViewById(R.id.txtCancel) as TextView
            val txtSubmit = layout1.findViewById(R.id.txtSubmit) as TextView
            val refresh = layout1.findViewById(R.id.refresh) as ImageView


            tie_Employee = layout1.findViewById(R.id.tie_Employee) as TextInputEditText
            tie_Branch = layout1.findViewById(R.id.tie_Branch) as TextInputEditText

            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)

            val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
            var IsManager = IsManagerSP.getString("IsManager", null)

            Log.e(TAG,"51021  IsAdminSP  : "+isAdmin)
            Log.e(TAG,"51022  IsManager  : "+IsManager)

            if (isAdmin.equals("1") && IsManager.equals("0")) {
                ll_admin_staff!!.visibility = View.VISIBLE
//                tie_Branch!!.isEnabled = true
//                tie_Employee!!.isEnabled = true
            }
            else if (isAdmin.equals("0") && IsManager.equals("1")){
                ll_admin_staff!!.visibility = View.VISIBLE
//                tie_Branch!!.isEnabled = false
//                tie_Employee!!.isEnabled = true
            }else{
//                tie_Branch!!.isEnabled = false
//                tie_Employee!!.isEnabled = false
            }


            Log.e(TAG,"52444   ")
            Log.e(TAG,"524441 temp_Employee1  :  "+temp_Employee1)
            Log.e(TAG,"524442 temp_ID_Employee1  :  "+temp_ID_Employee1)
            Log.e(TAG,"524443 temp_ID_Branch1  :  "+temp_ID_Branch1)
            Log.e(TAG,"524444 temp_Branch1  :  "+temp_Branch1)

            if (temp_Employee1.equals("")){
                tie_Employee!!.setText(temp_Employee)
            }else{
                tie_Employee!!.setText(temp_Employee1)
            }

            if (temp_ID_Employee1.equals("")){
                ID_Employee  =temp_ID_Employee
            }else{
                ID_Employee  =temp_ID_Employee1
            }

            if (temp_ID_Branch1.equals("")){
                ID_Branch = temp_ID_Branch
            }else{
                ID_Branch = temp_ID_Branch1
            }

            if (temp_Branch1.equals("")){
                tie_Branch!!.setText(temp_Branch)
            }else{
                tie_Branch!!.setText(temp_Branch1)
            }

            txtCancel.setOnClickListener {
                temp_ID_Branch1 = ""
                temp_Branch1 = ""
                temp_ID_Employee1 = ""
                temp_Employee1 = ""

                dialog.dismiss()

            }

            txtSubmit.setOnClickListener {

                if (ID_Branch.equals("")){
                    Toast.makeText(applicationContext, "Select Branch", Toast.LENGTH_SHORT).show()

                }else if (ID_Employee.equals("")){
                    Toast.makeText(applicationContext, "Select Employee", Toast.LENGTH_SHORT).show()
                }else{
                    dialog.dismiss()
                    agendaAllListCount = 0
                    getAgendaList()
                }
//
//                else{

//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//
//                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//                UserName = UserNameSP.getString("UserName", null).toString()

                Log.e(TAG,"002  "+ID_Employee)
                Log.e(TAG,"927  "+temp_Employee)

//                }

            }

            refresh.setOnClickListener {
//                dialog.dismiss()

                temp_ID_Branch1 = ""
                temp_Branch1 = ""
                temp_ID_Employee1 = ""
                temp_Employee1 = ""

                ID_Branch = temp_ID_Branch
                tie_Branch!!.setText(temp_Branch)
                ID_Employee = temp_ID_Employee

                tie_Employee!!.setText(temp_Employee)
                Log.e(TAG,"002  "+ID_Employee)

                Log.e(TAG,"5244421 temp_Employee1  :  "+temp_Employee1)
                Log.e(TAG,"5244422 temp_ID_Employee1  :  "+temp_ID_Employee1)
                Log.e(TAG,"5244423 temp_ID_Branch1  :  "+temp_ID_Branch1)
                Log.e(TAG,"5244424 temp_Branch1  :  "+temp_Branch1)
            }

            tie_Branch!!.setOnClickListener(View.OnClickListener {
                Config.disableClick(it)

                if (isAdmin.equals("1") && IsManager.equals("0")) {
                    branch = 0
                    getBranch()
                }

            })


            tie_Employee!!.setOnClickListener(View.OnClickListener {
                Config.disableClick(it)
//                ID_Employee = ""
//                leadDetailsAll = 0
//                getEmployeeAllD()
                Log.e(TAG," 796   tie_Employee"+ID_Employee)


                if (isAdmin.equals("1") && IsManager.equals("0")) {
                    empUseBranch = 0
                    getEmpByBranch()
                }
                else if (isAdmin.equals("0") && IsManager.equals("1")){
                    empUseBranch = 0
                    getEmpByBranch()
                }

            })





            dialog.setCancelable(false)
            dialog!!.setContentView(layout1)
            dialog.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }

    }


    private fun filterBottomSheetEmi() {
        try {

            //   filterCount = 1
            val dialog1 = BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.emi_list_filter_sheet, null)
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog1!!.setCanceledOnTouchOutside(false)



            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            tie_Customer = view.findViewById<TextInputEditText>(R.id.tie_Customer)
            tie_Mobile = view.findViewById<TextInputEditText>(R.id.tie_Mobile)
            tie_Area = view.findViewById<TextInputEditText>(R.id.tie_Area)
            tie_Due_Amount = view.findViewById<TextInputEditText>(R.id.tie_Due_Amount)

//            tie_Customer!!.setText(""+strCustomer)
//            tie_Mobile!!.setText(""+strMobile)
//            tie_Area!!.setText(""+strArea)
//            tie_Due_Amount!!.setText(""+strDueAmount)

            txtReset!!.setOnClickListener {
                tie_Customer!!.setText("")
                tie_Mobile!!.setText("")
                tie_Area!!.setText("")
                tie_Due_Amount!!.setText("")
            }
            txtSearch!!.setOnClickListener {

                strCustomer = tie_Customer!!.text.toString().toLowerCase().trim()
                strMobile = tie_Mobile!!.text.toString().toLowerCase().trim()
                strArea = tie_Area!!.text.toString().toLowerCase().trim()
                strDueAmount = tie_Due_Amount!!.text.toString().toLowerCase().trim()



                emiListSort = JSONArray()

                for (k in 0 until emiArrayList.length()) {
                    val jsonObject = emiArrayList.getJSONObject(k)
                    //  if (textlength <= jsonObject.getString("TicketNo").length) {
                    if ((jsonObject.getString("Customer")!!.toLowerCase().trim()
                            .contains(strCustomer!!))
                        && (jsonObject.getString("Mobile")!!.toLowerCase().trim()
                            .contains(strMobile!!))
                        && (jsonObject.getString("Customer")!!.toLowerCase().trim()
                            .contains(strArea!!))
                        && (jsonObject.getString("DueAmount")!!.toLowerCase().trim()
                            .contains(strDueAmount!!))
                    ) {
                        //   Log.e(TAG,"2161    "+strTicketNumber+"   "+strCustomer)
                        emiListSort!!.put(jsonObject)
                    } else {
                        //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                    }

                    // }
                }

                dialog1.dismiss()
                Log.i("responserere", "size array==" + emiListSort.length())
                SubMode = "1"
                setEmiAgendaRecycler(emiListSort)

//                val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
//                recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListSort!!,SubMode!!)
//                recyEmiList!!.adapter = adapter
//                adapter.setClickListener(this@EmiToDoListActivity)


            }


            dialog1!!.setContentView(view)
            dialog1.show()

        } catch (e: Exception) {

        }
    }

    private fun getAgendatypes() {
        var typeAgenda = 0
        Log.e(TAG, "msg   3966   ")
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
                            Log.e(TAG, "msg   302   " + msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("AgendaType")
                                agendaTypeArrayList = jobjt.getJSONArray("AgendaTypeList")
                                Log.i("response11223344", "agendaList=" + agendaTypeArrayList)
//                                Log.i("response112233","agendaList size="+agendaTypeArrayList.length())
                                if (agendaTypeArrayList.length() > 0) {


                                    recyAgendaType =
                                        findViewById(R.id.recyAgendaType) as RecyclerView
                                    // val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                    recyAgendaType!!.layoutManager = LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                    // recyAgendaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?

                                    Log.i("qq", "agendaTypeArrayList=" + agendaTypeArrayList)
                                    Log.i("qq", "selectedPos=" + selectedPos)


                                    val adapter = AgendaTypeAdapter(
                                        this@AgendaActivity,
                                        agendaTypeArrayList,
                                        selectedPos
                                    )
                                    recyAgendaType!!.adapter = adapter
                                    adapter.setClickListener(this@AgendaActivity)
                                    val myList: List<String> = ArrayList()

                                    agendaList1.clear()
                                    agendaNameList.clear()
                                    for (i in 0 until agendaTypeArrayList.length()) {

                                        agendaList1.add(
                                            agendaTypeArrayList.getJSONObject(i)
                                                .getString("Id_Agenda")
                                        )
                                        agendaNameList.add(
                                            agendaTypeArrayList.getJSONObject(i)
                                                .getString("AgendaName")
                                        )
                                    }
                                    Log.i("qqeeee", "agenda id=" + agendaList1)
                                    Log.i("qqeeee", "agenda name=" + agendaNameList)
                                    Id_Agenda =
                                        agendaTypeArrayList.getJSONObject(0).getString("Id_Agenda")
                                    Log.i("qq", "agenda id=" + Id_Agenda)
                                    Log.e(TAG, "Id_Agenda   423   " + Id_Agenda)
                                    Log.e(TAG, " 8301   5")

                                    val FK_BranchCodeUserSP =
                                        context.getSharedPreferences(Config.SHARED_PREF40, 0)
                                    val BranchNameSP = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF45,
                                        0
                                    )
                                    val FK_EmployeeSP =
                                        context.getSharedPreferences(Config.SHARED_PREF1, 0)
                                    val UserNameSP =
                                        context.getSharedPreferences(Config.SHARED_PREF2, 0)

                                    ID_Branch =
                                        FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)
                                            .toString()
                                    ID_Employee =
                                        FK_EmployeeSP.getString("FK_Employee", null).toString()

                                    if (Id_Agenda.equals("1")) {
                                        rv_todolist1
                                        rv_todolist!!.visibility = View.VISIBLE
                                        rv_todolist1!!.visibility = View.GONE
                                        imgv_filter1!!.visibility = View.GONE
                                        imgv_filter!!.visibility = View.VISIBLE
                                        rv_todolist!!.adapter = null
                                        toDoDet = 0
                                        getTodoList()
                                        //   getTodoList1()
                                    } else if (Id_Agenda.equals("2")) {
                                        rv_todolist!!.visibility = View.GONE
                                        imgv_filter!!.visibility = View.GONE
                                        fab_Reminder!!.visibility = View.GONE
                                        rv_todolist1!!.visibility = View.VISIBLE
                                        imgv_filter1!!.visibility = View.VISIBLE
                                        rv_todolist1!!.adapter = null
                                        toDoDet = 0

                                        getTodoList1()
                                    }


                                    //.............


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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }

    private fun filterBottomData() {

        try {
            fromfilter = ""
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

            ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
            tie_Branch!!.setText(BranchNameSP.getString("BranchName", null))
            ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
            tie_Employee!!.setText(UserNameSP.getString("UserName", null))
            ID_Lead_Details = ""
            tie_LeadDetails!!.setText("")
            tie_LeadValue!!.setText("")
            til_LeadValue!!.setHint("")

            tie_Employee?.isClickable = false

            tie_Branch!!.setOnClickListener(this)
            tie_Employee!!.setOnClickListener(this)

            etxt_date = layout1.findViewById<EditText>(R.id.etxt_date)
            etxt_Name = layout1.findViewById<EditText>(R.id.etxt_Name)
            criteria = ""
            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG, "isAdmin 796  " + isAdmin)
            if (isAdmin.equals("1")) {
                ll_admin_staff!!.visibility = View.VISIBLE
                tie_Branch!!.isEnabled = true
                //     tie_Employee!!.isEnabled = true
            } else {
                ll_admin_staff!!.visibility = View.GONE
                tie_Branch!!.isEnabled = false
                //     tie_Employee!!.isEnabled = false
            }


            tie_Branch!!.setOnClickListener(View.OnClickListener {

                Config.disableClick(it)
                Log.e(TAG, " 796   tie_Branch")
                ID_Employee = ""
                tie_Employee!!.setText("")
                branch = 0
                getBranch()
            })

//            tie_Employee!!.setOnClickListener(View.OnClickListener {
//                Config.disableClick(it)
//                Log.e(TAG, " 796   tie_Employee")
//                if (ID_Branch.equals("")) {
//                    Config.snackBars(context, it, "Select Branch")
//                } else {
//                    empUseBranch = 0
//                    getEmpByBranch()
//                }
//
//            })

            tie_LeadDetails!!.setOnClickListener(View.OnClickListener {

                Log.e(TAG, " 796   tie_LeadDetails")

                Config.disableClick(it)
                leadDetails = 0
                getLeadDetails()
            })


            txtCancel.setOnClickListener {
                dialog.dismiss()
            }

            txtSubmit.setOnClickListener {
                strLeadValue = tie_LeadValue!!.text.toString()
                if (ID_Branch.equals("")) {
                    Toast.makeText(applicationContext, "Select Branch", Toast.LENGTH_SHORT).show()
                    // Config.snackBars(context,it,"Select Branch")
                } else if (ID_Employee.equals("")) {
                    Toast.makeText(applicationContext, "Select Employee", Toast.LENGTH_SHORT).show()
                    // Config.snackBars(context,it,"Select Employee")
                }

                else {
                    Log.e(TAG, "927  ")

                    Log.i("response1212", "927  ")
                    dialog.dismiss()
              //      getLeadList(leadArrayList)

                   toDoDet = 0
//
                   getTodoList()
//.................

                    //..............


                }
            }


            dialog.setCancelable(false)
            dialog!!.setContentView(layout1)

            dialog.show()
        } catch (e: Exception) {
            Log.e(TAG, "777  Exception   " + e.toString())
        }


    }

    private fun showFilterAlertService() {


        try {
            val dialog = BottomSheetDialog(this)
            val layout1 = layoutInflater.inflate(R.layout.filter_popup_service, null)


            val edtTicket = layout1.findViewById<EditText>(R.id.edtTicket)
            val edt_fromDate = layout1.findViewById<EditText>(R.id.edt_fromDate)
            val edt_status = layout1.findViewById<AutoCompleteTextView>(R.id.edt_status)
            val edt_customer = layout1.findViewById<EditText>(R.id.edt_customer)


            val txtCancel = layout1.findViewById(R.id.txtCancel) as TextView
            val txtSubmit = layout1.findViewById(R.id.txtSubmit) as TextView

            edt_fromDate.setOnClickListener(View.OnClickListener {
                openBottomSheetDate(0, edt_fromDate)
            })
            ShowingStatus(edt_status)








            txtCancel.setOnClickListener {
                edtTicket.text = null

                edt_customer.text = null
                edt_fromDate.text = null

                strFromDate = ""
                strToDate = ""
                dialog.dismiss()
            }

            txtSubmit.setOnClickListener {
                // validation must
                var jsonArrayFilterd: JSONArray = JSONArray()
                var ticketNumber: String = ""
                var product: String = ""
                var date: String = ""
                var customer: String = ""
                var tickerNumber: String = ""
                var status: String = ""

                ticketNumber = edtTicket.text.toString()

                date = edt_fromDate.text.toString()
                customer = edt_customer.text.toString()
                status = edt_status.text.toString()
                if (status.equals("Choose")) {
                    status = ""
                }


                var x = 0

                for (i in 0 until serviceArrayList.length()) {
                    val item = serviceArrayList.getJSONObject(i)
                    Log.v("fdfddefe", "i" + i)
                    if (item.getString("Ticket").toLowerCase()
                            .contains(ticketNumber.toLowerCase()) &&
                        //item.getString("product").toLowerCase().contains(product.toLowerCase()) &&
                        item.getString("Customer").toLowerCase().contains(customer.toLowerCase()) &&
                        item.getString("CurrentStatus").toLowerCase().contains(status.toLowerCase())
                    ) {
                        jsonArrayFilterd.put(item)
                    }
                }
                Log.i("fdfddefe", "json size=" + jsonArrayFilterd.length())


                setServiceRecyclerAgenda(jsonArrayFilterd)
                dialog.dismiss()
//                rv_todolist1!!.layoutManager = LinearLayoutManager(
//                    this@AgendaActivity, LinearLayoutManager.VERTICAL,
//                    false
//                )
//
////                                    val lLayout = LinearLayoutManager(this@AgendaActivity, LinearLayoutManager.VERTICAL,
////                                        false)
////                                    rv_todolist1!!.layoutManager =
////                                        lLayout as RecyclerView.LayoutManager?
//                rv_todolist1!!.setHasFixedSize(true)
//
//                var adapter = ServiceFollowUpListAdapter1(this, jsonArrayFilterd)
//                rv_todolist1!!.adapter = adapter
//                adapter.addItemClickListener(this)
                //  Toast.makeText(applicationContext, "Submit", Toast.LENGTH_SHORT).show()
            }


            dialog.setCancelable(true)
            dialog!!.setContentView(layout1)

            dialog.show()
        } catch (e: java.lang.Exception) {
            Log.i("responseExceptioneeeee", "" + e.printStackTrace())
        }


    }

    private fun openBottomSheetDate(i: Int, edt_Date: EditText) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_date, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1.maxDate = System.currentTimeMillis()
        txtCancel.setOnClickListener {

            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()

                Log.i("wewewe", "Day=" + day)
                Log.i("wewewe", "mon=" + mon)




                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
                if (i == 0) {
                    edt_Date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strFromDate = strYear + "-" + strMonth + "-" + strDay
                } else {
                    edt_Date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strToDate = strYear + "-" + strMonth + "-" + strDay
                }
            } catch (e: Exception) {

            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    private fun ShowingStatus(edt_status: AutoCompleteTextView) {
        val searchType =
            arrayOf<String>("Choose", "New", "Pending", "On-Hold", "Completed")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        edt_status!!.setAdapter(adapter)
        edt_status!!.setText(searchType.get(0), false)
        edt_status!!.setOnClickListener {
            edt_status!!.showDropDown()
        }
        edt_status!!.setOnItemClickListener { parent, view, position, id ->
            statusposition = position
        }
    }

    private fun getTodoList1() {
//        var toDoDet = 0
        // rv_todolist!!.adapter = null
        context = this@AgendaActivity
        serviceFollowUpListViewModel =
            ViewModelProvider(this).get(ServiceFollowUpListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                serviceFollowUpListViewModel.getServiceFollowUplist(
                    this,
                    ID_Branch,
                    ID_Employee
                )!!.observe(this, Observer { serviceSetterGetter ->
                    try {
                        val msg = serviceSetterGetter.message
                        //   Log.i("response121212", "msg="+msg)
                        if (msg!!.length > 0) {
                            if (toDoDet == 0) {
                                toDoDet++

                                val editor = sharedPreferences!!.edit()
                                editor.clear()
                                editor.commit()

                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ServiceFollowUpdetails")
                                    todoArrayList1 =
                                        jobjt.getJSONArray("ServiceFollowUpdetailsList")
                                    //   Log.i("response121212", "service_todoList="+todoArrayList1)
                                    // rv_todolist1!!.visibility=View.VISIBLE

                                    // rv_todolist1!!.layoutManager

                                    //     setServiceFollowRecycler(todoArrayList1)
//
//                                    //...................
//                                    var jsonArrayFilterd: JSONArray = JSONArray()
//                                    val formatter = SimpleDateFormat("yyyy-MM-dd")
//                                    val formatter1 = SimpleDateFormat("dd/MM/yyyy")
//                                    val date = Date()
//                                    val ss="11/05/2023"
//                                    val current = formatter1.format(date)
//                                    Log.i("fdfddefeere", "current date==" +current )
//                                    for (i in 0 until todoArrayList1.length()) {
//                                        val item = todoArrayList1.getJSONObject(i)
//                                        Log.v("fdfddefe", "i" + i)
//                                        if (item.getString("AssignedDate").toLowerCase().contains(current.toLowerCase())
//                                        ) {
//                                            listCurrentDay.put(item)
//                                        }
//                                    }
//                                    Log.i("fdfddefeere", "jsonArrayFilterd==" +listCurrentDay.length() )
//                                    Log.i("fdfddefeere", "jsonArrayFilterd==" +listCurrentDay)
//                                    //............................

                                    setServiceRecyclerAgenda(todoArrayList1)
                                    Log.i(
                                        "fdfddefeere",
                                        "todoArrayList1==" + todoArrayList1.length()
                                    )
//                                    rv_todolist1!!.layoutManager = LinearLayoutManager(this@AgendaActivity, LinearLayoutManager.VERTICAL,
//                                        false)
//
//
//                                    rv_todolist1!!.setHasFixedSize(true)
//
//
//
//
//
//                                    var adapter = ServiceFollowUpListAdapter1(this, todoArrayList1)
//                                    rv_todolist1!!.adapter = adapter
//                                    adapter.addItemClickListener(this)


//                                    //...................
//                                    var jsonArrayFilterd: JSONArray = JSONArray()
//                                    val formatter = SimpleDateFormat("yyyy-MM-dd")
//                                    val formatter1 = SimpleDateFormat("dd/MM/yyyy")
//                                    val date = Date()
//                                    val ss="11/05/2023"
//                                    val current = formatter1.format(date)
//                                    Log.i("fdfddefeere", "current date==" +current )
//                                    for (i in 0 until todoArrayList1.length()) {
//                                        val item = todoArrayList1.getJSONObject(i)
//                                        Log.v("fdfddefe", "i" + i)
//                                        if (item.getString("AssignedDate").toLowerCase().contains(current.toLowerCase())
//                                        ) {
//                                            listCurrentDay.put(item)
//                                        }
//                                    }
//                                    Log.i("fdfddefeere", "jsonArrayFilterd==" +listCurrentDay.length() )
//                                    Log.i("fdfddefeere", "jsonArrayFilterd==" +listCurrentDay)
//                                    //............................


                                } else {
                                    rv_todolist1!!.adapter = null
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

                        }
                    } catch (e: Exception) {
//                        Toast.makeText(
//                            applicationContext,
//                            "rtrtr"+Config.SOME_TECHNICAL_ISSUES,
//                            Toast.LENGTH_LONG
//                        ).show()
                        Log.i("responseEx", "" + e)
                    }

                })

                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun setServiceRecyclerAgenda(todoArray: JSONArray) {

//        var a = tabLayout!!.getTabAt(tabLayout!!.selectedTabPosition)!!.text
//        var b= todoArrayList1.length()
//        val concatString = "$a $b"
//
//        tabLayout!!.getTabAt(tabLayout!!.selectedTabPosition)!!.setText(concatString.toString())
        //  todoArrayList1
        recyEmiList!!.visibility = View.GONE
        //    countService!!.setText(todoArray.length().toString())
      //  coun!!.text=emiArrayList.length().toString()
        coun!!.setText(todoArray.length().toString())
        //  tabLayout2!!.addTab(tabLayout2!!.newTab().setText(todoArray.length().toString()))
        rv_todolist1!!.layoutManager = LinearLayoutManager(
            this@AgendaActivity, LinearLayoutManager.VERTICAL,
            false
        )
        rv_todolist1!!.setHasFixedSize(true)

        var adapter = ServiceFollowUpListAdapter(this, todoArray)
        rv_todolist1!!.adapter = adapter


        adapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (todoArray.length() > 0) {
                    Log.i("eertret", "inside")
                    Log.i("eertret", "inside")
                    // checkEmptyList!!.visibility=View.GONE
                    // checkEmptyList!!.visibility=View.VISIBLE
                    //  Toast.makeText(this@AgendaActivity,"Not empty",Toast.LENGTH_SHORT).show()
                } else {
                    coun!!.text="0"
                    Log.i("eertret", "inside else")
                    // checkEmptyList!!.visibility=View.VISIBLE
                    val builder = AlertDialog.Builder(
                        this@AgendaActivity,
                        R.style.MyDialogTheme
                    )
                    builder.setMessage("No Data Found")
                    builder.setPositiveButton("OK") { dialogInterface, which ->
                        dialogInterface.dismiss()
                        toDoDet = 0
                        //  getTodoList1()

                        getServiceList(serviceArrayList)

                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }

            }
        })
        rv_todolist1!!.setFocusable(false)
        adapter.notifyDataSetChanged()

        adapter.addItemClickListener(this)

    }

    private fun setEmiAgendaRecycler(emiListArrayList: JSONArray) {

//
//        var a = tabLayout!!.getTabAt(tabLayout!!.selectedTabPosition)!!.text
//        var b= emiListArrayList.length()
//        val concatString = "$a $b"
//
//        tabLayout!!.getTabAt(tabLayout!!.selectedTabPosition)!!.setText(concatString.toString())


        //  countEmi!!.setText(emiListArrayList.length().toString())

        coun!!.setText(emiListArrayList.length().toString())

        //   tabLayout3!!.addTab(tabLayout3!!.newTab().setText(emiListArrayList.length().toString()))
        rv_todolist!!.visibility = View.GONE
        rv_todolist1!!.visibility = View.GONE
        recyEmiList!!.visibility = View.VISIBLE
        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
        recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        val adapter = EmiListAdapter(this@AgendaActivity, emiListArrayList!!, SubMode!!)
        recyEmiList!!.adapter = adapter


        adapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (emiListArrayList.length() > 0) {
                    Log.i("eertret", "inside")
                    Log.i("eertret", "inside")
                    // checkEmptyList!!.visibility=View.GONE
                    // checkEmptyList!!.visibility=View.VISIBLE
                    //  Toast.makeText(this@AgendaActivity,"Not empty",Toast.LENGTH_SHORT).show()
                } else {
                    Log.i("eertret", "inside else")
                    // checkEmptyList!!.visibility=View.VISIBLE
                    val builder = AlertDialog.Builder(
                        this@AgendaActivity,
                        R.style.MyDialogTheme
                    )
                    coun!!.text="0"
                    builder.setMessage("No Data Found")
                    builder.setPositiveButton("OK") { dialogInterface, which ->
                        dialogInterface.dismiss()
                        emiCount = 0
                        //      getEmiAgenda()
                        getEmi(emiArrayList)

                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }

            }
        })
        recyEmiList!!.setFocusable(false)
        adapter.notifyDataSetChanged()

        adapter.setClickListener(this@AgendaActivity)


    }

    private fun setLeasdRecyClerviewAdapter(leadListArrayList: JSONArray) {

    //    coun!!.text=emiArrayList.length().toString()
        coun!!.setText(leadListArrayList.length().toString())
        //    countLead!!.setText(leadListArrayList.length().toString())
        //    lead_count!!.setText(leadListArrayList.length().toString())
        //  tabLayout1!!.addTab(tabLayout1!!.newTab().setText(leadListArrayList.length().toString()))
        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
        rv_todolist!!.layoutManager =
            lLayout as RecyclerView.LayoutManager?
        rv_todolist!!.setHasFixedSize(true)
        val adapter = TodoListAdapter(
            applicationContext,
            leadListArrayList,
            SubMode!!
        )
        rv_todolist!!.adapter = adapter
        adapter.setClickListener(this@AgendaActivity)


//        adapter.registerAdapterDataObserver(object :
//            RecyclerView.AdapterDataObserver() {
//            override fun onChanged() {
//                if (leadListArrayList.length() > 0) {
//                    Log.i("eertret", "inside")
//                    Log.i("eertret", "inside")
//                    // checkEmptyList!!.visibility=View.GONE
//                    // checkEmptyList!!.visibility=View.VISIBLE
//                    //  Toast.makeText(this@AgendaActivity,"Not empty",Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.i("eertret", "inside else")
//                    // checkEmptyList!!.visibility=View.VISIBLE
//                    val builder = AlertDialog.Builder(
//                        this@AgendaActivity,
//                        R.style.MyDialogTheme
//                    )
//                    builder.setMessage("No Data Found")
//                    builder.setPositiveButton("OK") { dialogInterface, which ->
//                        dialogInterface.dismiss()
////                    dialogInterface.dismiss()
//                     toDoDet = 0
//                    getTodoList()
//
//                    }
//                    val alertDialog: AlertDialog = builder.create()
//                    alertDialog.setCancelable(false)
//                    alertDialog.show()
//                }
//
//            }
//        })
//        rv_todolist!!.setFocusable(false)
//        adapter.notifyDataSetChanged()


    }

    private fun setServiceFollowRecycler(jsonArray2: JSONArray) {
        rv_todolist1!!.layoutManager = LinearLayoutManager(
            this@AgendaActivity, LinearLayoutManager.VERTICAL,
            false
        )

//                                    val lLayout = LinearLayoutManager(this@AgendaActivity, LinearLayoutManager.VERTICAL,
//                                        false)
//                                    rv_todolist1!!.layoutManager =
//                                        lLayout as RecyclerView.LayoutManager?
        rv_todolist1!!.setHasFixedSize(true)
        var adapter = ServiceFollowUpListAdapter(this, todoArrayList1)
        rv_todolist1!!.adapter = adapter
        //    adapter.addItemClickListener(this)


    }

    private fun getTodoList2() {
        Log.i("response2", "getTodoList2")
//        var toDoDet = 0
        // rv_todolist!!.adapter = null
        rv_todolist1!!.visibility = View.GONE
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
                todolistViewModel.getTodolist(
                    this,
                    submode!!,
                    name!!,
                    criteria!!,
                    date!!,
                    ID_Branch!!,
                    ID_Employee!!,
                    ID_Lead_Details,
                    strLeadValue
                )!!.observe(
                    this,
                    Observer { todolistSetterGetter ->
                        try {
                            val msg = todolistSetterGetter.message
                            Log.i("response2", "msg=" + msg)
                            if (msg!!.length > 0) {

                                Log.e(TAG, "getTodoList   52412   " + msg)
                                Log.e(TAG, "getTodoList   524   " + toDoDet)
                                if (toDoDet == 0) {
                                    toDoDet++

                                    val editor = sharedPreferences!!.edit()
                                    editor.clear()
                                    editor.commit()

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("LeadManagementDetailsList")
                                        todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                        Log.i("response2", "lead size=" + todoArrayList.length())
                                        lead_count!!.setText(todoArrayList.length().toString())
                                        if (todoArrayList.length() > 0) {
                                            setLeasdRecyClerviewAdapter(todoArrayList);

//                                        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
//                                        rv_todolist!!.layoutManager =
//                                            lLayout as RecyclerView.LayoutManager?
//                                        rv_todolist!!.setHasFixedSize(true)
//                                        val adapter = TodoListAdapter(
//                                            applicationContext,
//                                            todoArrayList,
//                                            SubMode!!
//                                        )
//                                        rv_todolist!!.adapter = adapter
//                                        adapter.setClickListener(this@AgendaActivity)


                                            Log.e(
                                                TAG,
                                                "agendaTypeArrayList   5521   " + agendaTypeArrayList
                                            )
                                            Log.e(
                                                TAG,
                                                "agendaTypeArrayList   552   " + agendaTypeArrayList.length()
                                            )

                                            var jsonObject = agendaTypeArrayList.getJSONObject(0)


                                            Log.i(
                                                "responsepppppppppppppppppppp",
                                                "jsonObject before=" + jsonObject
                                            )
                                            Log.i(
                                                "responsepppppppppppppppppppp",
                                                "list before=" + agendaTypeArrayList
                                            )


                                            jsonObject.put("Count", todoArrayList.length())

                                            Log.i(
                                                "responsepppppppppppppppppppp",
                                                "jsonObject after=" + jsonObject
                                            )
                                            Log.i(
                                                "responsepppppppppppppppppppp",
                                                "list after=" + agendaTypeArrayList
                                            )


                                            //  Log.e(TAG,"agendaTypeArrayList   553   "+agendaTypeArrayList)
//                                            val adapter1 = AgendaTypeAdapter(
//                                                this@AgendaActivity,
//                                                agendaTypeArrayList,
//                                                selectedPos
//                                            )
//                                            recyAgendaType!!.adapter = adapter1
//                                            adapter1.setClickListener(this@AgendaActivity)
                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@AgendaActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage("No Data FOund")
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                dialogInterface.dismiss()
                                                toDoDet = 0
                                                getTodoList()
                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }


                                    } else {
                                        rv_todolist!!.adapter = null
                                        val builder = AlertDialog.Builder(
                                            this@AgendaActivity,
                                            R.style.MyDialogTheme
                                        )



                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            dialogInterface.dismiss()
                                            toDoDet = 0
                                            getTodoList()
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
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "yyy"+Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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

    private fun getActionTypes(Id_Agenda: String) {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        Log.e(TAG, "537777      " + Id_Agenda)
        var agendaAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog1 = ProgressDialog(context, R.style.Progress)
                progressDialog1!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog1!!.setCancelable(false)
                progressDialog1!!.setIndeterminate(true)
                progressDialog1!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog1!!.show()

                agendaActionViewModel.getAgendaAction(this, Id_Agenda)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message

                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   5577   " + msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ActionType")
                                agendaActionArrayList = jobjt.getJSONArray("ActionTypeList")
                                if (agendaActionArrayList.length() > 0) {
                                    if (agendaAction == 0) {
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }
    }

    private fun agendaTypePopup(agendaActionArrayList: JSONArray) {

        Log.e(TAG, "agendaTypePopup  599   " + agendaTypeClick)
        if (agendaTypeClick.equals("0")) {

            agendaActionSort = JSONArray()
            for (k in 0 until agendaActionArrayList.length()) {
                val jsonObject = agendaActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                agendaActionSort.put(jsonObject)
            }

            val jsonObject = agendaActionArrayList.getJSONObject(0)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))
            Log.e(TAG, "agendaTypePopup  6222   " + tv_actionType)

            getAgendaDetails("0", Id_Agenda)

        } else {

            try {

                dialogAgendaAction = Dialog(this)
                dialogAgendaAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogAgendaAction!!.setContentView(R.layout.agenda_action_popup)
                dialogAgendaAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
                recyActionType =
                    dialogAgendaAction!!.findViewById(R.id.recyActionType) as RecyclerView
                val etsearch = dialogAgendaAction!!.findViewById(R.id.etsearch) as EditText

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
                                if (jsonObject.getString("ActionTypeName")!!.toLowerCase().trim()
                                        .contains(etsearch!!.text.toString().toLowerCase().trim())
                                ) {
                                    agendaActionSort.put(jsonObject)
                                }

                            }
                        }

                        Log.e(TAG, "agendaActionSort               7103    " + agendaActionSort)
                        val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionSort)
                        recyActionType!!.adapter = adapter
                        adapter.setClickListener(this@AgendaActivity)
                    }
                })

                dialogAgendaAction!!.show()
                dialogAgendaAction!!.getWindow()!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("EmiList")) {

            Log.e(TAG, "EmiList  148")
            val jsonObject = emiArrayList.getJSONObject(position)
//            val ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
            val ID_CustomerWiseEMI = jsonObject.getString("ID_CustomerWiseEMI")
            val i = Intent(this@AgendaActivity, EmiCollectionActivity::class.java)
            i.putExtra("jsonObject", jsonObject.toString());
            i.putExtra("ID_CustomerWiseEMI", ID_CustomerWiseEMI)
            startActivity(i)
        }

        if (data.equals("todolist")) {
          //  Config.disableClick(View)
            
            val jsonObject = leadArrayList.getJSONObject(position)
            val i = Intent(this@AgendaActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject", jsonObject.toString())
            i.putExtra("SubMode", submode)
            startActivity(i)
        }

        if (data.equals("agendaactiontype")) {
            dialogAgendaAction!!.dismiss()
//            val jsonObject = agendaActionArrayList.getJSONObject(position)
            val jsonObject = agendaActionSort.getJSONObject(position)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))

            Log.e(TAG, "ID_ActionType   607   " + ID_ActionType)
            name = ""
            date = ""
            criteria = ""
            getAgendaDetails(ID_ActionType!!, Id_Agenda)


        }

        if (data.equals("agendaLocation")) {

            Log.e(TAG, "agendaLocation   433")
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            Log.e(TAG, "Latitude       4331    " + jsonObject.getString("Latitude"))
            Log.e(TAG, "Longitude      4332    " + jsonObject.getString("Longitude"))
            Log.e(TAG, "LocationName   4333    " + jsonObject.getString("LocationName"))

            val i = Intent(this@AgendaActivity, MapsAgendaActivity::class.java)
            i.putExtra("LocationName", jsonObject.getString("LocationName"))
            i.putExtra("Longitude", jsonObject.getString("Longitude"))
            i.putExtra("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
            i.putExtra("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
            i.putExtra("Latitude", jsonObject.getString("Latitude"))
            startActivity(i)
        }

        if (data.equals("agendaMessage")) {

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

        if (data.equals("agendaCall")) {
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            Log.e(TAG, "CustomerMobile       454    " + jsonObject.getString("CustomerMobile"))
            if (jsonObject.getString("CustomerMobile")
                    .equals("") || jsonObject.getString("CustomerMobile") == null
            ) {
                val builder = AlertDialog.Builder(this@AgendaActivity, R.style.MyDialogTheme)
                builder.setMessage("Customer Mobile Not Found")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                Log.e(TAG, "CALL DETAILS  506 getCallDetails  ")

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
                    val BroadCallSP =
                        applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                    val BroadCallEditer = BroadCallSP.edit()
                    BroadCallEditer.putString("BroadCall", "Yes")
                    BroadCallEditer.putString(
                        "ID_LeadGenerate",
                        jsonObject.getString("ID_LeadGenerate")
                    )
                    BroadCallEditer.putString(
                        "ID_LeadGenerateProduct",
                        jsonObject.getString("ID_LeadGenerateProduct")
                    )
                    BroadCallEditer.commit()

                    Log.e(TAG, "8001   " + jsonObject.getString("ID_LeadGenerate"))
                    Log.e(TAG, "8002   " + jsonObject.getString("ID_LeadGenerateProduct"))

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

        if (data.equals("CallReminder")) {
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ActionTypeName1 = jsonObject.getString("ActionTypeName")
            val EnquiryAbout1 = jsonObject.getString("EnquiryAbout")
            val Status1 = jsonObject.getString("Status")
            // setReminder(ActionTypeName1,EnquiryAbout1,Status1)
        }

        if (data.equals("agendaType")) {
            val jsonObject = agendaTypeArrayList.getJSONObject(position)
//            if (jsonObject.getString("Id_Agenda").equals("1")){
////                Lead
//            }
//            if (jsonObject.getString("Id_Agenda").equals("2")){
////                Service
//            }
            Log.e(TAG, " 8301   6")

            Id_Agenda = jsonObject.getString("Id_Agenda")
            Log.i("response112233", "id agenda===" + Id_Agenda)
            Log.e(TAG, " 8301   7     " + Id_Agenda)
            agendaTypeClick = "0"
            //    getActionTypes(Id_Agenda)

            selectedPos = position     //  very important
            if (Id_Agenda.equals("1")) {
                imgv_filter1!!.visibility = View.GONE
                imgv_filter!!.visibility = View.VISIBLE
                fab_Reminder!!.visibility = View.VISIBLE
                Log.i("response112233", "leads===" + Id_Agenda)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
                ID_Lead_Details = ""
                strLeadValue = ""

                rv_todolist!!.visibility = View.VISIBLE
                rv_todolist!!.adapter = null
                toDoDet = 0
                getTodoList()
            } else if (Id_Agenda.equals("2")) {
                rv_todolist!!.visibility = View.GONE
                imgv_filter!!.visibility = View.GONE
                fab_Reminder!!.visibility = View.GONE



                Log.i("response1122334444444444444", "service===" + Id_Agenda)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
                ID_Lead_Details = ""
                strLeadValue = ""

                rv_todolist1!!.visibility = View.VISIBLE
                imgv_filter1!!.visibility = View.VISIBLE
                rv_todolist1!!.adapter = null
                toDoDet = 0
                getTodoList1()
            }


        }

        if (data.equals("agendaDocument")) {
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
            val ID_LeadGenerateProduct = jsonObject.getString("ID_LeadGenerateProduct")
            val i = Intent(this@AgendaActivity, DocumentListActivity::class.java)
            i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
            i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
            //  i.putExtra("jsonObject", jsonObject.toString())
            startActivity(i)
        }

        if (data.equals("DocumentList")) {
            val jsonObject = agendaDetailArrayList.getJSONObject(position)
            val ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
            val ID_LeadGenerateProduct = jsonObject.getString("ID_LeadGenerateProduct")
            val i = Intent(this@AgendaActivity, DocumentListActivity::class.java)
            i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
            i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
            // i.putExtra("jsonObject", jsonObject.toString())
            startActivity(i)
        }

        if (data.equals("agendaList")) {

//            val jsonObject = agendaDetailArrayList.getJSONObject(position)
//            val i = Intent(this@AgendaActivity, AccountDetailsActivity::class.java)
//            i.putExtra("jsonObject",jsonObject.toString())
//            startActivity(i)

        }

        if (data.equals("todocall")) {

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
                val jsonObject = leadArrayList.getJSONObject(position)
                Log.e("TODO", " 289     jsonObject    " + jsonObject)
                val mobileno = jsonObject.getString("LgCusMobile")
                val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                val BroadCallEditer = BroadCallSP.edit()
                BroadCallEditer.putString("BroadCall", "Yes")
                BroadCallEditer.putString(
                    "ID_LeadGenerate",
                    jsonObject.getString("ID_LeadGenerate")
                )
                BroadCallEditer.putString(
                    "ID_LeadGenerateProduct",
                    jsonObject.getString("ID_LeadGenerateProduct")
                )
                BroadCallEditer.putString("FK_Employee", jsonObject.getString("FK_Employee"))
                BroadCallEditer.putString("AssignedTo", jsonObject.getString("AssignedTo"))
                BroadCallEditer.commit()


                Log.e("TODO", "8001   " + mobileno)
                Log.e("TODO", "8001   " + jsonObject.getString("ID_LeadGenerate"))
                Log.e("TODO", "8002   " + jsonObject.getString("ID_LeadGenerateProduct"))

                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)

//                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
//                startActivity(intent)

            }
        }

        if (data.equals("todoMessage")) {
            val jsonObject = leadArrayList.getJSONObject(position)
            Log.e("TAG", "31323  position   :  " + position)
            Log.e("TAG", "313  ID_LeadGenerate   :  " + jsonObject.getString("ID_LeadGenerate"))
            messagePopup()
        }


        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
//             val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchSort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))

            temp_ID_Branch1 = jsonObject.getString("ID_Branch")
            temp_Branch1 = jsonObject.getString("BranchName")

            ID_Employee = ""
            tie_Employee!!.setText("")

            temp_ID_Employee1 = ""
            temp_Employee1 = ""


        }

        if (data.equals("employeeAll")) {
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))

            temp_ID_Employee1 = jsonObject.getString("ID_Employee")
            temp_Employee1 = jsonObject.getString("EmpName")


        }

        if (data.equals("LeadDetail1")) {
            dialogleadDetail!!.dismiss()
            val jsonObject = leadDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_TodoListLeadDetails   " + jsonObject.getString("ID_TodoListLeadDetails"))
            ID_Lead_Details = jsonObject.getString("ID_TodoListLeadDetails")
            tie_LeadDetails!!.setText(jsonObject.getString("TodoListLeadDetailsName"))
            til_LeadValue!!.setHint(jsonObject.getString("TodoListLeadDetailsName"))


        }


    }

    private fun setReminder(ActionTypeName1: String, EnquiryAbout1: String, descriptn: String) {
        try {
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
                alertDialog.dismiss()
            }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.show()
        } catch (e: Exception) {
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
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCancelable(false)
            dialog1.setContentView(R.layout.send_message_popup)
            dialog1.window!!.attributes.gravity = Gravity.CENTER;

            val rbMessages = dialog1.findViewById(R.id.rbMessages) as RadioButton
            val rbReminder = dialog1.findViewById(R.id.rbReminder) as RadioButton
            val rbIntimation = dialog1.findViewById(R.id.rbIntimation) as RadioButton

            val edt_message = dialog1.findViewById(R.id.edt_message) as EditText

            val chk_whats = dialog1.findViewById(R.id.chk_whats) as CheckBox
            val chk_Email = dialog1.findViewById(R.id.chk_Email) as CheckBox
            val chk_Message = dialog1.findViewById(R.id.chk_Message) as CheckBox

            val btnMssubmit = dialog1.findViewById(R.id.btnMssubmit) as Button
            val btnMscancel = dialog1.findViewById(R.id.btnMscancel) as Button

            val segmented2 = dialog1.findViewById(R.id.segmented2) as SegmentedGroup
            segmented2.setTintColor(resources.getColor(R.color.color_msg_tab));
            segmented2.setOnCheckedChangeListener(this@AgendaActivity);

            rbMessages.isChecked = true
            rbReminder.isChecked = false
            rbIntimation.isChecked = false

            chk_whats.setOnClickListener {
                if (chk_whats.isChecked) {

                    cbWhat = "1"
                } else {
                    cbWhat = "0"
                }
            }

            chk_Email.setOnClickListener {
                if (chk_Email.isChecked) {
                    cbEmail = "1"
                } else {
                    cbEmail = "0"
                }
            }

            chk_Message.setOnClickListener {

                if (chk_Message.isChecked) {
                    cbMessage = "1"
                } else {
                    cbMessage = "0"
                }
            }

            btnMscancel.setOnClickListener {
                dialog1.dismiss()
            }

            btnMssubmit.setOnClickListener {
                messageDesc = edt_message.text.toString()
                if (messageType.equals("")) {

                } else if (messageDesc.equals("")) {
                    Config.snackBars(context, it, "Please enter message")
//
                } else if (cbWhat.equals("0") && cbEmail.equals("0") && cbMessage.equals("0")) {
                    Config.snackBars(context, it, "Please select sending options")
//
                } else {
                    Log.e(TAG, "  927  messageType  " + messageType)
                    Log.e(TAG, "  927  messageDesc  " + messageDesc)
                    Log.e(TAG, "  927  HHHHH  " + cbWhat + "  :   " + cbEmail + "  :  " + cbMessage)

                    Config.Utils.hideSoftKeyBoard(context, it)
                    dialog1.dismiss()
                    Toast.makeText(context, "" + messageDesc, Toast.LENGTH_SHORT).show()
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
        when (checkedId) {
            R.id.rbMessages -> {

                messageType = "Message"
                Log.e(TAG, "rbMessages")
            }
            R.id.rbReminder -> {
                Log.e(TAG, "rbReminder")
                messageType = "Reminder"
            }
            R.id.rbIntimation -> {
                Log.e(TAG, "rbIntimation")
                messageType = "Intimation"
            }
        }
    }


    fun timeSelector() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            this,
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
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    yr = year
                    month = (monthOfYear + 1)
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
                val month1: Int = mon1 + 1
                val year1: Int = date_Picker1!!.getYear()
                var strDay = day1.toString()
                var strMonth = month1.toString()
                var strYear = year1.toString()

                yr = year1
                month = month1
                day = day1


                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }

                // etdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)

                if (sortFilter == 0) {
                    etxt_date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }
                if (sortFilter == 1) {
                    etxt_date1!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }
                if (sortFilter == 2) {
                    etdate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }


            } catch (e: Exception) {
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


            } catch (e: Exception) {
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    fun addEvent(
        iyr: Int,
        imnth: Int,
        iday: Int,
        ihour: Int,
        imin: Int,
        descriptn: String,
        Title: String
    ) {
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
        if (calendarId != null) {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Reminder set successfully.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    private fun getCalendarId(context: Context): kotlin.Long? {

        try
        {
            val permissions = true
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
            Log.e("calcursor", calCursor.toString())
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
                    Log.e(TAG,"CALID : "+calID.toLong())
                    return calID.toLong()
                }
            }


        }
        catch(e:SecurityException)
        {
            Log.e(TAG,"Error"+e.toString())
        }
        return null

    }

    private fun getCallDetails() {

        try {
            val sb = StringBuffer()
            val contacts: Uri = CallLog.Calls.CONTENT_URI
            val managedCursor: Cursor? =
                context.contentResolver.query(contacts, null, null, null, null)
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

            Log.e(TAG, "CALL DETAILS  5061   " + sb)
        } catch (e: Exception) {
            Log.e(TAG, "CALL DETAILS  5062   " + e.toString())
        }

    }

    private fun getAgendaDetails(ID_ActionType: String, Id_Agenda: String) {
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog!!.dismiss()
//        }
        recyAgendaDetail!!.adapter = null
        var agendaDetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaDetailViewModel.getAgendaDetail(
                    this,
                    ID_ActionType,
                    SubMode!!,
                    Id_Agenda,
                    name,
                    date,
                    criteria
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            //  progressDialog!!.dismiss()
                            if (msg!!.length > 0) {

                                if (agendaDetail == 0) {
                                    agendaDetail++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   443   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("AgendaDetails")
                                        agendaDetailArrayList =
                                            jobjt.getJSONArray("AgendaDetailsList")
                                        if (agendaDetailArrayList.length() > 0) {
//                                    if (agendaDetail == 0){
//                                        agendaDetail++
                                            //  agendaTypePopup(agendaActionArrayList)

                                            val editor = sharedPreferences!!.edit()
                                            editor.clear()
                                            editor.commit()



                                            recyAgendaDetail =
                                                findViewById(R.id.recyAgendaDetail) as RecyclerView
                                            val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                            recyAgendaDetail!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            val adapter = AgendaDetailAdapter(
                                                this@AgendaActivity,
                                                agendaDetailArrayList
                                            )
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
                        } catch (e: Exception) {
                            val builder = AlertDialog.Builder(
                                this@AgendaActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage("" + e.toString())
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
            etxt_date = layout1.findViewById<EditText>(R.id.etxt_date)
            etxt_Name = layout1.findViewById<EditText>(R.id.etxt_Name)
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

                alertDialogSort.dismiss()
            }
            btnsubmit.setOnClickListener {

                name = etxt_Name!!.text.toString()
                date = etxt_date!!.text.toString()

                if (etxt_date!!.text.toString().equals("") && etxt_Name!!.text.toString()
                        .equals("")
                ) {
                    Toast.makeText(applicationContext, "Please select a value", Toast.LENGTH_LONG)
                        .show()
                } else {
//                    getAgendaDetails1(ID_ActionType!!, Id_Agenda)
                    getAgendaDetails(ID_ActionType!!, Id_Agenda)
                    alertDialogSort.dismiss()
                }
            }

            alertDialogSort.show()

        } catch (e: Exception) {

        }

    }


    private fun getBranch() {
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this, "0",SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (branch == 0) {
                                    branch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BranchDetails")
                                        branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                        if (branchArrayList.length() > 0) {

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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun branchPopup(branchArrayList: JSONArray) {

        try {

            dialogBranch = Dialog(this)
            dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranch!!.setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!!.findViewById(R.id.recyBranch) as RecyclerView
            val etsearch = dialogBranch!!.findViewById(R.id.etsearch) as EditText

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
                            if (jsonObject.getString("BranchName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                branchSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "branchSort               7103    " + branchSort)
                    val adapter = BranchAdapter(this@AgendaActivity, branchSort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogBranch!!.show()
            dialogBranch!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getEmpByBranch() {
//         var branch =
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                empByBranchViewModel.getEmpByBranch(this, ID_Branch,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (empUseBranch == 0) {
                                    empUseBranch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeAllArrayList =
                                            jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length() > 0) {

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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun employeeAllPopup(employeeAllArrayList: JSONArray) {
        try {

            dialogEmployeeAll = Dialog(this)
            dialogEmployeeAll!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAll!!.setContentView(R.layout.employeeall_popup)
            dialogEmployeeAll!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAll = dialogEmployeeAll!!.findViewById(R.id.recyEmployeeAll) as RecyclerView
            val etsearch = dialogEmployeeAll!!.findViewById(R.id.etsearch) as EditText


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
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeAllSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeAllSort               7103    " + employeeAllSort)
                    val adapter = EmployeeAllAdapter(this@AgendaActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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

                                if (leadDetails == 0) {
                                    leadDetails++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TodoListLeadDetails")
                                        leadDetailArrayList =
                                            jobjt.getJSONArray("TodoListLeadDetailsList")
                                        if (leadDetailArrayList.length() > 0) {

                                            Log.e(
                                                TAG,
                                                "leadDetailArrayList   1205    " + leadDetailArrayList
                                            )
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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    private fun leadDetailPopup(leadDetailArrayList: JSONArray) {
        try {

            dialogleadDetail = Dialog(this)
            dialogleadDetail!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogleadDetail!!.setContentView(R.layout.leaddetail_popup)
            dialogleadDetail!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyleadDetail = dialogleadDetail!!.findViewById(R.id.recyleadDetail) as RecyclerView
            val etsearch = dialogleadDetail!!.findViewById(R.id.etsearch) as EditText


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
            } catch (e: Exception) {
                Log.e(TAG, "Exception  1275   " + e.toString())
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
                            if (jsonObject.getString("TodoListLeadDetailsName")!!.toLowerCase()
                                    .trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                leadDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "leadDetailSort               7103    " + leadDetailSort)
                    val adapter = LeadDetailAdapter(this@AgendaActivity, leadDetailSort)
                    recyleadDetail!!.adapter = adapter
                    adapter.setClickListener(this@AgendaActivity)
                }
            })

            dialogleadDetail!!.show()
            dialogleadDetail!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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
            val checkbox_dsc = layout1.findViewById<CheckBox>(R.id.checkbox_dsc) as CheckBox

            val checkbox_date = layout1.findViewById<CheckBox>(R.id.checkbox_Date) as CheckBox
            val checkbox_nme = layout1.findViewById<CheckBox>(R.id.checkbox_name) as CheckBox

            etxt_date1 = layout1.findViewById<EditText>(R.id.etxt_date) as EditText
            etxt_name1 = layout1.findViewById<EditText>(R.id.etxt_name) as EditText
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

            if (checkbox_asc.isChecked) {
                criteria = "1"
                checkbox_asc.isChecked = true
                checkbox_dsc.isChecked = false
                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)


            }
            if (checkbox_dsc.isChecked) {
                criteria = "2"
                checkbox_asc.isChecked = false
                checkbox_dsc.isChecked = true

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
                    date = etxt_date1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_date.setButtonDrawable(image5)
                    date = ""
                }
            })

            checkbox_nme.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image4 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_nme.setButtonDrawable(image4)
                    OverDueActivity.name1 = etxt_name1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_nme.setButtonDrawable(image5)
                    OverDueActivity.name1 = ""
                }
            })



            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked = true
                checkbox_dsc.isChecked = false

                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)

                criteria = "1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked = true
                checkbox_asc.isChecked = false
                criteria = "2"

                val image = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_dsc.setButtonDrawable(image1)
            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss()
            }
            btnsubmit.setOnClickListener {

                date = etxt_date1!!.text.toString()
                name = etxt_name1!!.text.toString()
                Log.i("Detail", date + "\n" + name)

                if (date.equals("") && name.equals("")) {
                    Toast.makeText(applicationContext, "Please enter a value", Toast.LENGTH_LONG)
                        .show()
                } else {

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

        } catch (e: Exception) {

        }

    }


    fun dateSelector1() {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    yr = year
                    month = (monthOfYear + 1)
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

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {


        if (data.equals("followUp")) {
//            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
//            val intent = Intent(this, ServiceFollowUpActivity::class.java)
//            val runningStatus = 0
//            intent.putExtra("runningStatus", runningStatus)
//            intent.putExtra("customer_service_register", customer_service_register)
//            startActivity(intent)

            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
            val intent = Intent(this, ServiceFollowUpNewActivity::class.java)
            intent.putExtra("jsonObject", jsonObject!!.toString())
            intent.putExtra("runningStatus", jsonObject!!.getString("FK_Status"))
            intent.putExtra("customer_service_register", customer_service_register)
            startActivity(intent)
        }
        if (data.equals("info")) {
            serviceFollowUpInfo = 0;
            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
            val ID_CustomerserviceregisterProductDetails = jsonObject!!.getString("ID_CustomerserviceregisterProductDetails")
            Log.v("dsfdfdfddd", "customer_service_register  " + customer_service_register)
            loadInfo(customer_service_register,ID_CustomerserviceregisterProductDetails)
            // openAlertDialogForMoreInfo()
        }
        if (data.equals("call")) {
            var mobile = jsonObject.getString("Mobile")
            performCall(mobile)
        }
        if (data.equals("location")) {
            val jsonObject = serviceArrayList.getJSONObject(position)
            //   val jsonObject = jsonArray.getJSONObject(position)
            longitude = jsonObject.getString("LocLongitude")
            latitude = jsonObject.getString("LocLattitude")
//            longitude = "76.94756468242483"
//            latitude = "8.591705686530284"

            Log.i("jhjjkj", "longitude==" + longitude)
            Log.i("jhjjkj", "latitude==" + latitude)




            if (longitude.equals("") || latitude.equals("")) {
                showSnackBar("Location Not Found", this@AgendaActivity)
            } else {
                loadLocation()
            }
            //  loadLocation()
        }
        if (data.equals("tracking")) {
            val i = Intent(this, TrackerActivity::class.java)
            startActivity(i)
        }

    }

    private fun showSnackBar(message: String, activity: AgendaActivity) {

        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun loadInfo(customerServiceRegister: String,ID_CustomerserviceregisterProductDetails: String) {
        context = this@AgendaActivity
        serviceFollowUpInfoViewModel =
            ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpInfoViewModel.getServiceFollowUpInfo(
                    this, customerServiceRegister,ID_CustomerserviceregisterProductDetails,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.v("fsfsfds", "msg")
                                if (serviceFollowUpInfo == 0) {
                                    serviceFollowUpInfo++
                                    Log.v("fsfsfds", "det")
                                    val jObject = JSONObject(msg)
                                    Log.v("dfsfrffff", "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        //  swipeRefreshLayout.visibility = View.VISIBLE
                                        tv_nodata.visibility = View.GONE
                                        val jobjt =
                                            jObject.getJSONObject("EmployeeWiseTicketSelect")
                                        openAlertDialogForMoreInfo(jobjt)
                                        // setServiceFollowRecycler(overdueArrayList)
                                    } else {
//                                        swipeRefreshLayout.visibility = View.GONE
//                                        swipeRefreshLayout.isRefreshing = false
                                        tv_nodata.visibility = View.VISIBLE
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
                                //  swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
//                            swipeRefreshLayout.visibility = View.GONE
//                            swipeRefreshLayout.isRefreshing = false
                            tv_nodata.visibility = View.VISIBLE
                            Log.v("fsfsfds", "ex3 " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun openAlertDialogForMoreInfo(jobjt: JSONObject) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_more_info, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
        var tv_ticket: TextView = dialogView.findViewById<TextView>(R.id.tv_ticket)
        var tv_prod_regOn: TextView = dialogView.findViewById<TextView>(R.id.tv_prod_regOn)
        var tv_visit_on: TextView = dialogView.findViewById<TextView>(R.id.tv_visit_on)
        var tv_CustomerName: TextView = dialogView.findViewById<TextView>(R.id.tv_CustomerName)
        var tv_phone: TextView = dialogView.findViewById<TextView>(R.id.tv_phone)
        var tv_location: TextView = dialogView.findViewById<TextView>(R.id.tv_location)
        var tv_address: TextView = dialogView.findViewById<TextView>(R.id.tv_address)
        var tv_product_name: TextView = dialogView.findViewById<TextView>(R.id.tv_product_name)
        var tv_category: TextView = dialogView.findViewById<TextView>(R.id.tv_category)
        var tv_service: TextView = dialogView.findViewById<TextView>(R.id.tv_service)
        var tv_description: TextView = dialogView.findViewById<TextView>(R.id.tv_description)
        tv_description.setMovementMethod(ScrollingMovementMethod())
        tv_cancel.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
        })
        tv_ticket.text = jobjt.getString("Ticket")
        tv_prod_regOn.text = jobjt.getString("RegisterdOn")
        tv_visit_on.text = jobjt.getString("VisitOn")
        tv_CustomerName.text = jobjt.getString("CustomerName")
        tv_phone.text = jobjt.getString("Mobile")
        tv_address.text =
            jobjt.getString("Address1") + "," + jobjt.getString("Address2") + "," + jobjt.getString(
                "Address3"
            )
        tv_product_name.text = jobjt.getString("ProductName")
        tv_category.text = jobjt.getString("Category")
        tv_service.text = jobjt.getString("Service")
        tv_description.text = jobjt.getString("Description")
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }

    private fun performCall(mobile: String) {
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

//            val jsonObject = todoArrayList.getJSONObject(position)
//
//            val mobileno = jsonObject.getString("LgCusMobile")
//            val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
//            val BroadCallEditer = BroadCallSP.edit()
//            BroadCallEditer.putString("BroadCall", "Yes")
//            BroadCallEditer.putString("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
//            BroadCallEditer.putString("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
//            BroadCallEditer.putString("FK_Employee", jsonObject.getString("FK_Employee"))
//            BroadCallEditer.putString("AssignedTo", jsonObject.getString("AssignedTo"))
//            BroadCallEditer.commit()

            intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobile))
            startActivity(intent)
        }
    }

    private fun loadLocation() {
        Log.v("fsdfsdds", "location")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            this@AgendaActivity
        )
        Log.v("fsdfsdds", "location1")
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.v("fsdfsdds", "2")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            )
            return
        }
        Log.v("fsdfsdds", "2.5")
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            Log.v("fsdfsdds", "2.7")
            if (location != null) {
                Log.v("fsdfsdds", "3")
                currentLocation = location

                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment!!.getMapAsync(this)
            }
            val supportMapFragment =
                (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
            supportMapFragment!!.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //  cons_location.visibility = View.VISIBLE
        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
        Log.v("sfsfsf33rff", "latitude " + latitude.toDouble())
        Log.v("sfsfsf33rff", "longitude " + longitude.toDouble())
        val geocoder: Geocoder
        var addresses: List<Address?>
        geocoder = Geocoder(this@AgendaActivity, Locale.getDefault())
        Log.v("sfsfsf33rff", "geocoder " + geocoder)
        addresses =
            geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1) as List<Address?>;
        if (addresses != null) {
            try {
                Log.v("sfsfsf33rff", "addresses " + addresses.toString())
                city = addresses.get(0)!!.getAddressLine(0);
                //    tv_address.setText(city)
                Log.v("sfsfsf33rff", "city " + city)
                val markerOptions = MarkerOptions().position(latLng).title(city)
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                googleMap.addMarker(markerOptions)
            } catch (e: Exception) {
                Config.snackBars(
                    this,
                    getWindow().getDecorView().findViewById(android.R.id.content),
                    "No location data available"
                )
            }
        } else {
            Config.snackBars(
                this,
                getWindow().getDecorView().findViewById(android.R.id.content),
                "No location data available"
            )
        }
    }

    private fun getTodoList() {
        Log.i("response2", "getTodoList")
//        var toDoDet = 0
        rv_todolist!!.adapter = null
        submode="1"
        context = this@AgendaActivity
        todolistViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                //  progressDialog!!.show()
                todolistViewModel.getTodolist(
                    this,
                    submode!!,
                    name!!,
                    criteria!!,
                    date!!,
                    ID_Branch!!,
                    ID_Employee!!,
                    ID_Lead_Details,
                    strLeadValue
                )!!.observe(
                    this,
                    Observer { todolistSetterGetter ->
                        try {
                            val msg = todolistSetterGetter.message
                            Log.i("response121212", "msg=" + msg)
                            if (msg!!.length > 0) {

                                Log.e(TAG, "getTodoList   52412   " + msg)
                                Log.e(TAG, "getTodoList   524   " + toDoDet)
                                if (toDoDet == 0) {
                                    toDoDet++

                                    val editor = sharedPreferences!!.edit()
                                    editor.clear()
                                    editor.commit()

                                    val jObject = JSONObject(msg)

                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("LeadManagementDetailsList")
                                        todoArrayList = jobjt.getJSONArray("LeadManagementDetails")




                                        Log.i(
                                            "responseLeadSize",
                                            "lead size=" + todoArrayList.length()
                                        )
                                        lead_count!!.setText(todoArrayList.length().toString())
                                        setLeasdRecyClerviewAdapter(todoArrayList);

//                                        val lLayout = GridLayoutManager(this@AgendaActivity, 1)
//                                        rv_todolist!!.layoutManager =
//                                            lLayout as RecyclerView.LayoutManager?
//                                        rv_todolist!!.setHasFixedSize(true)
//                                        val adapter = TodoListAdapter(
//                                            applicationContext,
//                                            todoArrayList,
//                                            SubMode!!
//                                        )
//                                        rv_todolist!!.adapter = adapter
//                                        adapter.setClickListener(this@AgendaActivity)
//
//
//                                        Log.e(
//                                            TAG,
//                                            "agendaTypeArrayList   5521   " + agendaTypeArrayList
//                                        )
//                                        Log.e(
//                                            TAG,
//                                            "agendaTypeArrayList   552   " + agendaTypeArrayList.length()
//                                        )
//
//                                        var jsonObject = agendaTypeArrayList.getJSONObject(0)
//
//
//                                        Log.i(
//                                            "responsepppppppppppppppppppp",
//                                            "jsonObject before=" + jsonObject
//                                        )
//                                        Log.i(
//                                            "responsepppppppppppppppppppp",
//                                            "list before=" + agendaTypeArrayList
//                                        )
//
//
//                                        jsonObject.put("Count", todoArrayList.length())
//
//                                        Log.i(
//                                            "responsepppppppppppppppppppp",
//                                            "jsonObject after=" + jsonObject
//                                        )
//                                        Log.i(
//                                            "responsepppppppppppppppppppp",
//                                            "list after=" + agendaTypeArrayList
//                                        )
//
//
//                                        //  Log.e(TAG,"agendaTypeArrayList   553   "+agendaTypeArrayList)
//                                        val adapter1 = AgendaTypeAdapter(
//                                            this@AgendaActivity,
//                                            agendaTypeArrayList,
//                                            selectedPos
//                                        )
//                                        recyAgendaType!!.adapter = adapter1
//                                        adapter1.setClickListener(this@AgendaActivity)


                                    } else {
                                        rv_todolist!!.adapter = null
                                        val builder = AlertDialog.Builder(
                                            this@AgendaActivity,
                                            R.style.MyDialogTheme
                                        )
                                        lead_count!!.setText("0")
                                        coun!!.text="0"
                                        fab_Reminder!!.visibility = View.GONE
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            dialogInterface.dismiss()
                                            getLeadList(leadArrayList)
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
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "yyy"+Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }
    override fun onBackPressed() {


        if ((mainpage!!.getVisibility() == View.GONE)) {

            mainpage!!.visibility = View.VISIBLE
            textid!!.text="Today's List"
         //   lstChkArray.clear()

            val editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
          //  lcoun!!.visibility = View.GONE
            coun!!.visibility=View.GONE

            firstpage!!.visibility = View.GONE
            secondpage!!.visibility = View.GONE
            thirdpage!!.visibility = View.GONE
            imgv_filter!!.visibility = View.VISIBLE
            imgv_filter2!!.visibility = View.GONE
            imgv_filter1!!.visibility = View.GONE
            fab_Reminder!!.visibility = View.GONE
            imgv_filterEmi!!.visibility=View.GONE
        }
        else {
            lstChkArray.clear()
            super.onBackPressed()
        }

    }

















}