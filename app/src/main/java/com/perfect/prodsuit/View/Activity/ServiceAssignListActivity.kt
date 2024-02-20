package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.FollowupActionAdapter
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ServiceAssignListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    //.............
    var ID_Master: String? = ""
    var TransMode: String? = ""

    //...............

    var TAG  ="ServiceAssignListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var serviceListViewModel: ServiceListViewModel
    lateinit var serviceListArrayList: JSONArray
    lateinit var serviceListSort: JSONArray

    var recyServiceList: RecyclerView? = null
    private var tv_listCount: TextView? = null
    private var txtv_headlabel: TextView? = null
    private var imgv_filter: ImageView? = null

    var serviceList = 0
    var label : String?= ""
    var SubMode : String?= ""
    var ID_Branch : String?= ""
    var FK_Area : String?= ""
    var ID_Employee : String?= ""
    var strFromDate : String?= ""
    var strToDate : String?= ""
    var strCustomer : String?= ""
    var strMobile : String?= ""
    var strTicketNo : String?= ""
    var strDueDays : String?= ""

    private var til_Status: TextInputLayout? = null
    private var til_AttendedBy: TextInputLayout? = null

    private var tie_Status: TextInputEditText? = null
    private var tie_AttendedBy: TextInputEditText? = null

    private var tv_Ticket: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_Name: TextView? = null
    private var tv_Complaint: TextView? = null
    private var txtReset: TextView? = null
    private var txtUpdate: TextView? = null

    private var tie_TicketNumber: TextInputEditText? = null
    private var tie_Branch: TextInputEditText? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_DueDays: TextInputEditText? = null

    private var txtFilterReset: TextView? = null
    private var txtFilterSearch: TextView? = null

    var filterTicketNumber: String? = ""
    var filterBranch : String? = ""
    var filterCustomer : String? = ""
    var filterMobile : String? = ""
    var filterArea   : String? = ""
    var filterDueDays : String? = ""
    var exmessage : String? = ""


    var statusCount = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    var attendCount = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null

    var ReqMode: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""

    var serAssignCount = 0
    lateinit var serviceAssignDetailViewModel: ServiceAssignDetailsViewModel
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var TicketStatus: String? = ""
    var TicketDate: String? = ""

    var ID_Priority: String? = ""


    var strEditTicket : String?= ""
    var strEditProductComplaint : String?= ""
    var strEditCustomer : String?= ""
    var strEditProductname : String?= ""

    lateinit var serviceEditUpdateViewModel: ServiceEditUpdateViewModel
    var serUpdateCount = 0
    var strVisitDate : String?= ""
    var saveAttendanceMark = false
    val jsons= JSONObject()
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign_list)
        context = this@ServiceAssignListActivity
        serviceListViewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        serviceAssignDetailViewModel = ViewModelProvider(this).get(ServiceAssignDetailsViewModel::class.java)
        serviceEditUpdateViewModel = ViewModelProvider(this).get(ServiceEditUpdateViewModel::class.java)

        setRegViews()

        label   = intent.getStringExtra("label")
        SubMode   = intent.getStringExtra("SubMode")
        ID_Branch   = intent.getStringExtra("ID_Branch")
        FK_Area     = intent.getStringExtra("FK_Area")
        ID_Employee = intent.getStringExtra("ID_Employee")
        strFromDate = intent.getStringExtra("strFromDate")
        strToDate   = intent.getStringExtra("strToDate")
        strCustomer = intent.getStringExtra("strCustomer")
        strMobile   = intent.getStringExtra("strMobile")
        strTicketNo = intent.getStringExtra("strTicketNo")
        strDueDays  = intent.getStringExtra("strDueDays")

        txtv_headlabel!!.setText(label)


        serviceList = 0
        getServiceNewList()


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)
        recyServiceList!!.adapter = null
        tv_listCount = findViewById<TextView>(R.id.tv_listCount)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Status->{
                Config.disableClick(v)
                statusCount = 0
                ReqMode = "17"
                getStatus("2")
            }

            R.id.tie_AttendedBy->{
                Config.disableClick(v)
                attendCount = 0
                getChannelEmp()
            }
            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomSheet()

            }


        }
    }

    private fun filterBottomSheet() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.sal_filter_local, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            tie_TicketNumber    = view.findViewById<TextInputEditText>(R.id.tie_TicketNumber)
            tie_Branch          = view.findViewById<TextInputEditText>(R.id.tie_Branch)
            tie_Customer        = view.findViewById<TextInputEditText>(R.id.tie_Customer)
            tie_Mobile          = view.findViewById<TextInputEditText>(R.id.tie_Mobile)
            tie_Area            = view.findViewById<TextInputEditText>(R.id.tie_Area)
            tie_DueDays         = view.findViewById<TextInputEditText>(R.id.tie_DueDays)

            txtFilterReset = view.findViewById<TextView>(R.id.txtFilterReset)
            txtFilterSearch = view.findViewById<TextView>(R.id.txtFilterSearch)

            tie_TicketNumber!!.setText(""+filterTicketNumber)
            tie_Branch!!.setText(""+filterBranch)
            tie_Customer!!.setText(""+filterCustomer)
            tie_Mobile!!.setText(""+filterMobile)
            tie_Area!!.setText(""+filterArea)
            tie_DueDays!!.setText(""+filterDueDays)




            txtFilterSearch!!.setOnClickListener {


                if (tie_TicketNumber!!.text.toString().equals("")&&tie_Branch!!.text.toString().equals("")&&tie_Customer!!.text.toString().equals("")&&
                    tie_Mobile!!.text.toString().equals("")&&
                    tie_Area!!.text.toString().equals("")&&
                    tie_DueDays!!.text.toString().equals("")){

                    Toast.makeText(applicationContext,"Please enter any one field",Toast.LENGTH_LONG).show()
                }
                else
                {
                    filterTicketNumber = tie_TicketNumber!!.text!!.toString().toLowerCase().trim()
                    filterBranch       = tie_Branch!!.text!!.toString().toLowerCase().trim()
                    filterCustomer     = tie_Customer!!.text!!.toString().toLowerCase().trim()
                    filterMobile       = tie_Mobile!!.text!!.toString().toLowerCase().trim()
                    filterArea         = tie_Area!!.text!!.toString().toLowerCase().trim()
                    filterDueDays      = tie_DueDays!!.text!!.toString().toLowerCase().trim()

                    serviceListSort = JSONArray()

//                        || jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strCustomer)
//                        || jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(strMobile)
//                        || jsonObject.getString("Area")!!.toLowerCase().trim().contains(strArea)
//                        || jsonObject.getString("Due")!!.toLowerCase().trim().contains(strDueDays)

                    for (k in 0 until serviceListArrayList.length()) {
                        val jsonObject = serviceListArrayList.getJSONObject(k)
                        //  if (textlength <= jsonObject.getString("TicketNo").length) {
                        if ((jsonObject.getString("TicketNo")!!.toLowerCase().trim()
                                .contains(filterTicketNumber!!))
                            && (jsonObject.getString("Branch")!!.toLowerCase().trim()
                                .contains(filterBranch!!))
                            && (jsonObject.getString("Customer")!!.toLowerCase().trim()
                                .contains(filterCustomer!!))
                            && (jsonObject.getString("Mobile")!!.toLowerCase().trim()
                                .contains(filterMobile!!))
                            && (jsonObject.getString("Area")!!.toLowerCase().trim()
                                .contains(filterArea!!))
                            && (jsonObject.getString("Due")!!.toLowerCase().trim()
                                .contains(filterDueDays!!))
                        ) {
                            //   Log.e(TAG,"2161    "+strTicketNumber+"   "+strCustomer)
                            serviceListSort.put(jsonObject)
                        } else {
                            //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                        }

                        // }
                    }
                    Log.i("Length",serviceListArrayList.length().toString())

                    if (serviceListSort.length() > 0) {
                        dialog1.dismiss()
                        val adapter = ServiceListAdapter(
                            this@ServiceAssignListActivity,
                            serviceListSort,
                            SubMode!!
                        )
                        recyServiceList!!.adapter = adapter
                        adapter.setClickListener(this@ServiceAssignListActivity)

                        tv_listCount!!.setText("" + serviceListSort.length())

                    }
                    else
                    {
                     //   exmessage =jsons.getString("EXMessage")
                        val builder = AlertDialog.Builder(
                            this@ServiceAssignListActivity,
                            R.style.MyDialogTheme
                        )
                        builder.setMessage("Please Enter a Valid Ticket")
                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }

                }



            }

            txtFilterReset!!.setOnClickListener {
                tie_TicketNumber!!.setText("")
                tie_Branch!!.setText("")
                tie_Customer!!.setText("")
                tie_Mobile!!.setText("")
                tie_Area!!.setText("")
                tie_DueDays!!.setText("")
            }



//            ll_StafAdmin = view.findViewById<LinearLayout>(R.id.ll_StafAdmin)


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

            Log.e(TAG,"Exception  239   "+e.toString())
        }
    }

    private fun getServiceNewList() {
//        recyServiceList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceListViewModel.getServiceList(this,SubMode!!,ID_Branch!!,FK_Area!!,ID_Employee!!,strFromDate!!,strToDate!!,strCustomer!!,strMobile!!,strTicketNo!!,strDueDays!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serviceList == 0) {
                                    serviceList++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   serviceassign   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAssignNewDetails")

                                        serviceListArrayList = jobjt.getJSONArray("ServiceAssignNewList")
                                        if (serviceListArrayList.length() > 0) {
                                            imgv_filter!!.visibility  =View.VISIBLE

                                            serviceListSort = JSONArray()

                                            for (k in 0 until serviceListArrayList.length()) {
                                                val jsonObject = serviceListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                serviceListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+serviceListSort.length())
                                            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
                                            recyServiceList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListSort,SubMode!!)
                                            recyServiceList!!.adapter = adapter
                                            adapter.setClickListener(this@ServiceAssignListActivity)

                                        }
                                    } else {

                                        Log.i("ExMessage",exmessage.toString())
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("Service_timeLine"))
        {
            val jsonObject = serviceListArrayList.getJSONObject(position)

            TicketDate = jsonObject.getString("TicketDate")
            TicketStatus = jsonObject.getString("TicketStatus")
            ID_Master = jsonObject.getString("ID_Master")
            TransMode = jsonObject.getString("TransMode")

            Log.e(TAG, "time Flow 4545456 transmode  "+TransMode)
            Log.e(TAG, "time Flow 4545456 idcus  "+ID_Master )
            Log.e(TAG, "time Flow 4545456  status "+TicketStatus )


            val i = Intent(this@ServiceAssignListActivity, TimeFlowServiceActivity::class.java)
            i.putExtra("TicketDate",TicketDate)
            i.putExtra("TicketStatus",TicketStatus)
            i.putExtra("ID_Master",ID_Master)
            i.putExtra("TransMode",TransMode)
            startActivity(i)

            startActivity(i)
        }

        if (data.equals("ServiceList")) {
            val jsonObject = serviceListArrayList.getJSONObject(position)
         //   ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
          //  FK_CustomerserviceregisterProductDetails = jsonObject.getString("ID_CustomerServiceRegisterProductDetails")
            TicketDate = jsonObject.getString("TicketDate")
            TicketStatus = jsonObject.getString("TicketStatus")


            if(!TicketStatus.equals("3"))
            {
                val idcustservceregstSP = context.getSharedPreferences(Config.SHARED_PREF72, 0)
                ID_CustomerServiceRegister = idcustservceregstSP.getString("idcustsrvceregist","")

                val idcustservceregstprdctdetlSP = context.getSharedPreferences(Config.SHARED_PREF73, 0)
                FK_CustomerserviceregisterProductDetails = idcustservceregstprdctdetlSP.getString("idcustsrvceregistproductdetail","")


                Log.i("FKK",FK_CustomerserviceregisterProductDetails.toString()+"\n"+ID_CustomerServiceRegister)
                val i = Intent(this@ServiceAssignListActivity, ServiceAssignActivity::class.java)
                i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
                i.putExtra("FK_CustomerserviceregisterProductDetails",FK_CustomerserviceregisterProductDetails)
                i.putExtra("TicketStatus",TicketStatus)
                i.putExtra("TicketDate",TicketDate)
                startActivity(i)
            }

        }
        if (data.equals("ServiceEdit")) {

//            val i = Intent(this@ServiceAssignListActivity, ServiceAssignActivity::class.java)
//            startActivity(i)

           // serviceEditBottom()
            checkAttendance()
            if (saveAttendanceMark){
                val jsonObject = serviceListArrayList.getJSONObject(position)
                ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
                serAssignCount = 0
                getServiceAssignDetails()
            }


        }
        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
//            val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_Status = jsonObject.getString("ID_NextAction")
            tie_Status!!.setText(jsonObject.getString("NxtActnName"))
        }
        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))

            ID_AttendedBy = jsonObject.getString("ID_Employee")
            tie_AttendedBy!!.setText(jsonObject.getString("EmpName"))
        }




    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance){
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status","")
            if (status.equals("0") || status.equals("")){
                Common.punchingRedirectionConfirm(this,"","")
            }
            else if (status.equals("1")){
                saveAttendanceMark = true
            }

        }else{
            saveAttendanceMark = true
        }
    }

    private fun getServiceAssignDetails() {
        var ReqMode = "76"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignDetailViewModel.getServiceAssignDetail(
                    this,
                    ReqMode,
                    ID_CustomerServiceRegister!!,
                    FK_CustomerserviceregisterProductDetails!!,
                    TicketDate,

                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.e(TAG,"ServiceAssignDetails  2751   "+msg)

                                if (serAssignCount == 0){
                                    serAssignCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAssignDetails")
                                        Log.e(TAG,"FromDate  2752   "+jobjt.getString("FromDate"))
//                                        var saDetailArrayList = jobjt.getJSONArray("EmployeeRoleDetailsList")
//                                        Log.e(TAG,"saDetailArrayList  2753   "+saDetailArrayList)


                                        ID_CustomerServiceRegister = jobjt.getString("ID_Customerserviceregister")
                                        ID_Priority = jobjt.getString("Priority")

                                        strEditTicket = jobjt.getString("Ticket")
                                        strEditCustomer = jobjt.getString("Customer")
                                        strEditProductname = jobjt.getString("Productname")
                                        strEditProductComplaint = jobjt.getString("ProductComplaint")
                                        strEditProductComplaint
                                        serviceEditBottom()

//                                        tv_Ticket!!.setText(""+jobjt.getString("Ticket"))
//                                        tv_LandMark!!.setText(""+jobjt.getString("Landmark"))
//                                        tv_Customer!!.setText(""+jobjt.getString("Customer"))
//                                        tv_ContactNo!!.setText(""+jobjt.getString("OtherMobile"))
//                                        tv_Address!!.setText(""+jobjt.getString("Address"))
//                                        tv_Mobile!!.setText(""+jobjt.getString("Mobile"))
//
//                                        // Service Information
//
//                                        tv_RequestedDate!!.setText(""+jobjt.getString("FromDate")+" - "+jobjt.getString("ToDate"))
//                                        tv_RequestedTime!!.setText(""+jobjt.getString("FromTime")+" - "+jobjt.getString("ToTime"))
//
//                                        // Product Details
//                                        tv_ProductName!!.setText(""+jobjt.getString("Productname"))
//                                        tv_ProductComplaint!!.setText(""+jobjt.getString("ProductComplaint"))
//                                        tv_Description!!.setText(""+jobjt.getString("ProductDescription"))
//
//                                        ID_Priority = jobjt.getString("Priority")
//                                        tie_Priority!!.setText(""+jobjt.getString("PriorityName"))

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                         //   finish()
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
                        }catch (e:Exception){
//                            Toast.makeText(
//                                applicationContext,
//                                ""+Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                            val builder = AlertDialog.Builder(
                                this@ServiceAssignListActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage(Config.PLEASE_TRY_AGAIN)
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                //finish()
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
    }

    private fun serviceEditBottom() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.sa_edit_bottom_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            tv_Ticket = view.findViewById<TextView>(R.id.tv_Ticket)
            tv_Customer = view.findViewById<TextView>(R.id.tv_Customer)
            tv_Name = view.findViewById<TextView>(R.id.tv_Name)
            tv_Complaint = view.findViewById<TextView>(R.id.tv_Complaint)
            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtUpdate = view.findViewById<TextView>(R.id.txtUpdate)

            til_Status = view.findViewById<TextInputLayout>(R.id.til_Status)
            til_AttendedBy = view.findViewById<TextInputLayout>(R.id.til_AttendedBy)

            tie_Status = view.findViewById<TextInputEditText>(R.id.tie_Status)
            tie_AttendedBy = view.findViewById<TextInputEditText>(R.id.tie_AttendedBy)

            tie_Status!!.addTextChangedListener(watcher);
            tie_AttendedBy!!.addTextChangedListener(watcher);

            tv_Ticket!!.setText(""+strEditTicket)
            tv_Customer!!.setText(""+strEditCustomer)
            tv_Name!!.setText(""+strEditProductname)
            tv_Complaint!!.setText(""+strEditProductComplaint)

            tie_Status!!.setOnClickListener(this)
            tie_AttendedBy!!.setOnClickListener(this)
            resetEditdata()

            txtReset!!.setOnClickListener {
                resetEditdata()
            }
            txtUpdate!!.setOnClickListener {
                validateEdit(dialog1)
            }

            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    private fun validateEdit(dialog1: BottomSheetDialog) {

        if (ID_Status.equals("")){
            til_Status!!.setError("Select Status")
            til_Status!!.setErrorIconDrawable(null)
        }
        else if (ID_AttendedBy.equals("")){
            til_AttendedBy!!.setError("Select  Attended By")
            til_AttendedBy!!.setErrorIconDrawable(null)
        }
        else{
            dialog1.dismiss()
          //  Toast.makeText(applicationContext, "Successs", Toast.LENGTH_SHORT).show()

            try {
                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val currentDate = sdf.format(Date())
                val newDate: Date = sdf.parse(currentDate)
                val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
                strVisitDate = sdfDate2.format(newDate)
                serUpdateCount = 0
                serviceEditUpdate()

            }catch (e: Exception){

                Log.e(TAG,"Exception 196  "+e.toString())
            }

        }

    }

    private fun serviceEditUpdate() {
        var ReqMode = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceEditUpdateViewModel.getServiceUpdate(this,ReqMode,ID_CustomerServiceRegister!!,strVisitDate!!,ID_Priority!!,ID_AttendedBy!!,ID_Status!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serUpdateCount == 0){
                                    serUpdateCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        try {
                                            val jobjt = jObject.getJSONObject("CustomerserviceassignEdit")
                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!! .setContentView(R.layout.success_service_popup)
                                            suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
                                            suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                            suceessDialog!!.setCancelable(false)

                                            val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
                                            val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView

                                            tv_succesmsg!!.setText(jobjt.getString("Message"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                serviceList = 0
                                                getServiceNewList()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        }catch (e: Exception){
                                            val builder = AlertDialog.Builder(
                                                this@ServiceAssignListActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage(e.toString())
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->

                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun resetEditdata() {
        ID_Status = ""
        tie_Status!!.setText("")
        ID_AttendedBy = ""
        tie_AttendedBy!!.setText("")
    }

    private fun getStatus(statusSubmode: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this,statusSubmode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (statusCount == 0){
                                    statusCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length()>0){

                                            statusPopup(followUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun statusPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!! .findViewById(R.id.etsearch) as EditText


            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@ServiceAssignListActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignListActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpActionSort = JSONArray()

                    for (k in 0 until followUpActionArrayList.length()) {
                        val jsonObject = followUpActionArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("NxtActnName").length) {
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpActionSort               7103    "+followUpActionSort)
                    val adapter = FollowupActionAdapter(this@ServiceAssignListActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignListActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getChannelEmp() {
        var ID_Department = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, ID_Department)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (attendCount == 0) {
                                    attendCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!!.setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!!.findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!!.findViewById(R.id.etsearch) as EditText

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@ServiceAssignListActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignListActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSort = JSONArray()

                    for (k in 0 until employeeArrayList.length()) {
                        val jsonObject = employeeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeSort               7103    " + employeeSort)
                    val adapter = EmployeeAdapter(this@ServiceAssignListActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignListActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {
                editable === tie_Status!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_Status!!.isErrorEnabled = false
                }
                editable === tie_AttendedBy!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_AttendedBy!!.isErrorEnabled = false
                }

            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        serviceList = 0
        getServiceNewList()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


}