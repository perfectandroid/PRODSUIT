package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ServiceAssignActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ServiceAssignActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    internal var llNew: LinearLayout? = null
    internal var llOnGoing: LinearLayout? = null

    internal var ll_prdcomplnt: LinearLayout? = null
    internal var ll_prddescrptn: LinearLayout? = null
    internal var ll_prdnamepick: LinearLayout? = null

    internal var card_details: CardView? = null
    internal var crdvw_tkt: CardView? = null
    internal var crdvw_srvce: CardView? = null
    internal var crdvw_product: CardView? = null
    internal var crdvw_pickup: CardView? = null
    var recyServiceAssign: RecyclerView? = null
    var depmode = 0

    lateinit var departListSort: JSONArray

    lateinit var serviceAssignViewModel: ServiceAssignViewModel
    lateinit var serviceAssignArrayList : JSONArray


    private var tv_TicketClick: TextView? = null
    private var tv_ServiceClick: TextView? = null
    private var tv_ProductClick: TextView? = null
    private var tv_ListClick: TextView? = null
    private var txtv_head: TextView? = null
    private var txtv_reqtimetitle: TextView? = null
    private var txtv_remrkk: TextView? = null
    private var tv_PickupClick: TextView? = null

    private var tv_ProductNamePick: TextView? = null
    private var tv_ProductComplaintPick: TextView? = null
    private var tv_DescriptionPick: TextView? = null






    private var rltv_allViews: RelativeLayout? = null
    private var lnrHead_Ticket: LinearLayout? = null
    private var lnrHead_Service: LinearLayout? = null
    private var lnrHead_Product: LinearLayout? = null
    private var lnrHead_List: LinearLayout? = null
    private var ll_lstclick: LinearLayout? = null
    private var lnrHead_Pickup: LinearLayout? = null

    private var ll_productname: LinearLayout? = null
    private var ll_productcomplnt: LinearLayout? = null
    private var ll_productdescrptn: LinearLayout? = null


   // Ticket Information
    private var tv_Ticket: TextView? = null
    private var tv_LandMark: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_ContactNo: TextView? = null
    private var tv_Address: TextView? = null
    private var tv_Mobile: TextView? = null
    private var txtv_reqdatetitle: TextView? = null


   // Service Information
   private var tv_RequestedDate: TextView? = null
    private var tv_RequestedTime: TextView? = null

    // Product Details
    private var tv_ProductName: TextView? = null
    private var tv_ProductComplaint: TextView? = null
    private var tv_Description: TextView? = null

    private var tv_no_record: TextView? = null

    private var tie_VisitDate: TextInputEditText? = null
    private var tie_VisitTime: TextInputEditText? = null
    private var tie_Priority: TextInputEditText? = null
    private var tie_vehicle: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_Role: TextInputEditText? = null
    private var til_Remarks: TextInputLayout? = null


    private var til_VisitDate: TextInputLayout? = null
    private var til_VisitTime: TextInputLayout? = null
    private var til_Priority: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_Role: TextInputLayout? = null
    private var til_vehicleDetail: TextInputLayout? = null
    private var txtv_custbalnce: TextView? = null



    private var btnClear: Button? = null
    private var btnAdd: Button? = null

    var serAssignCount = 0
    lateinit var serviceAssignDetailViewModel: ServiceAssignDetailsViewModel
   // lateinit var saDetailArrayList : JSONArray

    lateinit var servicePriorityViewModel: ServicePriorityViewModel
    lateinit var servPriorityArrayList : JSONArray
    lateinit var servPrioritySort : JSONArray
    private var dialogServPriority : Dialog? = null
    var recyServPriority: RecyclerView? = null

    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var departmentArrayList : JSONArray
    lateinit var departmentSort : JSONArray
    private var dialogDepartment : Dialog? = null
    var recyDeaprtment: RecyclerView? = null

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList : JSONArray
    lateinit var employeeSort : JSONArray
    private var dialogEmployee : Dialog? = null
    var recyEmployee: RecyclerView? = null

    var serRoleCount = 0
    lateinit var serviceRoleViewModel: ServiceRoleViewModel
    lateinit var serviceRoleArrayList : JSONArray
    lateinit var serviceRoleSort : JSONArray
    private var dialogServiceRole : Dialog? = null
    var recyServiceRole: RecyclerView? = null

    var btnSave: Button? = null

    var dateMode = 0
    var timeMode = 0
    var priorityMode = 0
    var department = 0
    var employee = 0
   

    var ID_Priority : String?= ""
    var ID_Department : String?= ""
    var ID_Employee : String?= ""
    var ID_Role : String?= ""

    var strVisitDate : String?= ""
    var strVisitTime : String?= ""
    var strRemark : String?= ""

    var ticketMode: String? = "0"
    var serviceMode: String? = "1"
    var productMode: String? = "1"
    var listMode: String? = "1"
    var pickupMode: String? = "1"
    var dep_id: String?=""
    var arrSaveUpdate: String? = "0"
    var arrIndexUpdate: Int? = 0


    lateinit var arrProducts: JSONArray
    lateinit var arrproductSort: JSONArray
    var adapterService : ServiceAssignListAdapter? = null
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var TicketStatus: String? = ""


    var strTicket: String? = ""
    var strLandmark: String? = ""
    var strCustomer: String? = ""
    var strContactNo: String? = ""
    var strAddress: String? = ""
    var strMobile: String? = ""
    var strReqDate: String? = ""
    var strReqTime: String? = ""
    var strProductname: String? = ""
    var strProductComplaint: String? = ""
    var strProductDescription: String? = ""
    var strPriorityName: String? = ""
    var TicketDate: String? = ""
    var dateattend: String? = ""
    var stridCustomer: String? = ""
    val calendar11: Calendar? = Calendar.getInstance()
    val calendar22: Calendar? = Calendar.getInstance()
//    var str: String? = ""

    var serUpdateCount = 0
    private var txt_Warning : TextView? = null
    var saveAttendanceMark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign)
        context = this@ServiceAssignActivity

        serviceAssignDetailViewModel = ViewModelProvider(this).get(ServiceAssignDetailsViewModel::class.java)
        servicePriorityViewModel = ViewModelProvider(this).get(ServicePriorityViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceRoleViewModel = ViewModelProvider(this).get(ServiceRoleViewModel::class.java)
        serviceAssignViewModel = ViewModelProvider(this).get(ServiceAssignViewModel::class.java)

        setRegViews()
        ID_CustomerServiceRegister = intent.getStringExtra("ID_CustomerServiceRegister")
        FK_CustomerserviceregisterProductDetails = intent.getStringExtra("FK_CustomerserviceregisterProductDetails")
        TicketStatus = intent.getStringExtra("TicketStatus")
        TicketDate = intent.getStringExtra("TicketDate")

//        ticketMode = "0"
//        hideViews()
        checkAttendance()
        getCurrentdateTime()
        getServiceAssignDetails(TicketStatus,TicketDate)
        val custbalidSP = context.getSharedPreferences(Config.SHARED_PREF71, 0)
        stridCustomer = custbalidSP.getString("custbalid","")
        Log.e(TAG,"ID_CustomerServiceRegister  163   "+ID_CustomerServiceRegister+"\n"+FK_CustomerserviceregisterProductDetails+"\n"+stridCustomer)
        depmode = 0
        getDepartment()

    }

    private fun getCurrentdateTime() {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)


            tie_VisitDate!!.setText(""+sdfDate1.format(newDate))
            strVisitDate = sdfDate2.format(newDate)

            tie_VisitTime!!.setText(""+sdfTime1.format(newDate))
            strVisitTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }




       // fghfg
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val sdf1 = SimpleDateFormat("hh:mm:ss")
//        val currentDate = sdf.format(Date())
//
//        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
//        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//        val currentDateFormate = inputFormat.parse(currentDate)
//
//        tie_VisitDate!!.setText(""+outputFormat.parse(currentDate))

//        val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
//        val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
//        val date: Date = inputDateFormat.parse(input)
//        val output = outputDateFormat.format(date)



    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        arrProducts = JSONArray()

        recyServiceAssign = findViewById(R.id.recyServiceAssign)as RecyclerView
        recyServiceAssign!!.adapter = null
        crdvw_tkt = findViewById<CardView>(R.id.crdvw_tkt)
        crdvw_pickup= findViewById<CardView>(R.id.crdvw_pickup)
        crdvw_srvce = findViewById<CardView>(R.id.crdvw_srvce)
        crdvw_product = findViewById<CardView>(R.id.crdvw_product)
        card_details = findViewById<CardView>(R.id.card_details)
        txtv_reqdatetitle= findViewById<TextView>(R.id.txtv_reqdatetitle)
        txtv_reqtimetitle= findViewById<TextView>(R.id.txtv_reqtimetitle)

        tv_TicketClick = findViewById<TextView>(R.id.tv_TicketClick)
        tv_ServiceClick = findViewById<TextView>(R.id.tv_ServiceClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)
        tv_ListClick = findViewById<TextView>(R.id.tv_ListClick)
        txtv_head= findViewById<TextView>(R.id.txtv_head)
        txtv_remrkk= findViewById<TextView>(R.id.txtv_remrkk)
        tv_PickupClick= findViewById<TextView>(R.id.tv_PickupClick)
        til_Remarks= findViewById(R.id.til_Remarks)as TextInputLayout

        tv_ProductNamePick= findViewById<TextView>(R.id.tv_ProductNamePick)
        tv_ProductComplaintPick= findViewById<TextView>(R.id.tv_ProductComplaintPick)
        tv_DescriptionPick= findViewById<TextView>(R.id.tv_DescriptionPick)

        txtv_custbalnce= findViewById(R.id.txtv_custbalnce)as TextView




        tv_TicketClick!!.setOnClickListener(this)
        tv_ServiceClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)
        tv_PickupClick!!.setOnClickListener(this)
        tv_ListClick!!.setOnClickListener(this)
        txtv_custbalnce!!.setOnClickListener(this)


        rltv_allViews = findViewById<RelativeLayout>(R.id.rltv_allViews)
        lnrHead_Ticket = findViewById<LinearLayout>(R.id.lnrHead_Ticket)
        lnrHead_Service = findViewById<LinearLayout>(R.id.lnrHead_Service)
        lnrHead_Product = findViewById<LinearLayout>(R.id.lnrHead_Product)
        lnrHead_List = findViewById<LinearLayout>(R.id.lnrHead_List)
        ll_lstclick= findViewById<LinearLayout>(R.id.ll_lstclick)
        lnrHead_Pickup= findViewById<LinearLayout>(R.id.lnrHead_Pickup)
       // ll_vehicledetail= findViewById<LinearLayout>(R.id.ll_vehicledetail)

        ll_productname= findViewById<LinearLayout>(R.id.ll_productname)
        ll_productdescrptn= findViewById<LinearLayout>(R.id.ll_productdescrptn)
        ll_productcomplnt= findViewById<LinearLayout>(R.id.ll_productcomplnt)

        ll_prdcomplnt= findViewById<LinearLayout>(R.id.ll_prdcomplnt)
        ll_prddescrptn= findViewById<LinearLayout>(R.id.ll_prddescrptn)
        ll_prdnamepick= findViewById<LinearLayout>(R.id.ll_prdnamepick)



        // Ticket Information

        tv_Ticket = findViewById<TextView>(R.id.tv_Ticket)
        tv_LandMark = findViewById<TextView>(R.id.tv_LandMark)
        tv_Customer = findViewById<TextView>(R.id.tv_Customer)
        tv_ContactNo = findViewById<TextView>(R.id.tv_ContactNo)
        tv_Address = findViewById<TextView>(R.id.tv_Address)
        tv_Mobile = findViewById<TextView>(R.id.tv_Mobile)

        // Service Information

        tv_RequestedDate = findViewById<TextView>(R.id.tv_RequestedDate)
        tv_RequestedTime = findViewById<TextView>(R.id.tv_RequestedTime)

        // Product Details
        tv_ProductName = findViewById<TextView>(R.id.tv_ProductName)
        tv_ProductComplaint = findViewById<TextView>(R.id.tv_ProductComplaint)
        tv_Description = findViewById<TextView>(R.id.tv_Description)

        tv_no_record = findViewById<TextView>(R.id.tv_no_record)


        tie_VisitDate = findViewById<TextInputEditText>(R.id.tie_VisitDate)
        tie_VisitTime = findViewById<TextInputEditText>(R.id.tie_VisitTime)
        tie_Priority = findViewById<TextInputEditText>(R.id.tie_Priority)
        tie_Remarks = findViewById<TextInputEditText>(R.id.tie_Remarks)
        tie_Department = findViewById<TextInputEditText>(R.id.tie_Department)
        tie_Employee = findViewById<TextInputEditText>(R.id.tie_Employee)
        tie_Role = findViewById<TextInputEditText>(R.id.tie_Role)
        tie_vehicle= findViewById<TextInputEditText>(R.id.tie_vehicle)

        til_VisitDate = findViewById<TextInputLayout>(R.id.til_VisitDate)
        til_VisitTime = findViewById<TextInputLayout>(R.id.til_VisitTime)
        til_Priority = findViewById<TextInputLayout>(R.id.til_Priority)
        til_Department = findViewById(R.id.til_Department)as TextInputLayout
        til_Employee = findViewById<TextInputLayout>(R.id.til_Employee)
        til_Role = findViewById(R.id.til_Role)as TextInputLayout
        til_vehicleDetail= findViewById(R.id.til_vehicleDetail)as TextInputLayout


        btnSave = findViewById<Button>(R.id.btnSave)

        tie_VisitDate!!.setOnClickListener(this)
        tie_VisitTime!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_Role!!.setOnClickListener(this)
        btnSave!!.setOnClickListener(this)

        tie_VisitDate!!.addTextChangedListener(watcher);
        tie_VisitTime!!.addTextChangedListener(watcher);
        tie_Priority!!.addTextChangedListener(watcher);
        tie_Department!!.addTextChangedListener(watcher);
        tie_Employee!!.addTextChangedListener(watcher);
        tie_Role!!.addTextChangedListener(watcher);

        btnClear = findViewById<Button>(R.id.btnClear)
        btnAdd = findViewById<Button>(R.id.btnAdd)

        btnClear!!.setOnClickListener(this)
        btnAdd!!.setOnClickListener(this)

        arrSaveUpdate ="0"
        btnAdd!!.setText("Add")





    }

    private fun getServiceAssignDetails(TicketStatus: String?, TicketDate: String?) {
        var ReqMode = "76"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignDetailViewModel.getServiceAssignDetail(this,ReqMode,ID_CustomerServiceRegister!!,FK_CustomerserviceregisterProductDetails!!,TicketDate)!!.observe(
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
                                   //     var saDetailArrayList = jobjt.getJSONArray("EmployeeRoleDetailsList")
                                   //     Log.e(TAG,"saDetailArrayList  2753   "+saDetailArrayList)

                                      //  rrrrrrrr

                                            strTicket = jobjt.getString("Ticket")
                                            strLandmark = jobjt.getString("Landmark")
                                            strCustomer = jobjt.getString("Customer")
                                            strContactNo = jobjt.getString("OtherMobile")
                                            strRemark=jobjt.getString("Remarks")
                                            strAddress = jobjt.getString("Address")
                                            strMobile = jobjt.getString("Mobile")
                                            strReqDate = jobjt.getString("FromDate")+" - "+jobjt.getString("ToDate")
                                            strReqTime = jobjt.getString("FromTime")+" - "+jobjt.getString("ToTime")
                                            strProductname = jobjt.getString("Productname")
                                            strProductComplaint = jobjt.getString("ProductComplaint")
                                            strProductDescription = jobjt.getString("ProductDescription")
                                            strPriorityName = jobjt.getString("PriorityName")
                                            stridCustomer= jobjt.getString("ID_Customer")

                                        val FK_IDCustbalSP =
                                            applicationContext.getSharedPreferences(Config.SHARED_PREF71, 0)
                                        val FK_CustbalidEditer = FK_IDCustbalSP.edit()
                                        FK_CustbalidEditer.putString(
                                            "custbalid",
                                            stridCustomer
                                        )
                                        FK_CustbalidEditer.commit()



                                        if (TicketStatus.equals("2"))
                                        {

                                                tv_ServiceClick!!.setText("Service Information")
                                                tv_ProductClick!!.setText("Product Details")
                                                ll_lstclick!!.visibility=View.VISIBLE


                                                crdvw_tkt !!.visibility=View.VISIBLE
                                                crdvw_srvce !!.visibility=View.VISIBLE
                                                crdvw_product !!.visibility=View.VISIBLE
                                                crdvw_pickup!!.visibility=View.GONE

                                                tv_Ticket!!.setText(""+strTicket)
                                                tv_LandMark!!.setText(""+strLandmark)
                                                tv_Customer!!.setText(""+strCustomer)
                                                tv_ContactNo!!.setText(""+strContactNo)
                                                tv_Address!!.setText(""+strAddress)
                                                tv_Mobile!!.setText(""+strMobile)
                                                tie_Remarks!!.setText(""+strRemark)

                                                txtv_head!!.setText("Service Assign")

                                             //  til_Remarks!!.visibility=View.VISIBLE
                                              // til_vehicleDetail!!.visibility=View.GONE

                                             /*
                                                til_Department!!.visibility=View.VISIBLE*/
                                                // Service Information

                                               tv_RequestedDate!!.setText(""+strReqDate)
                                                tv_RequestedTime!!.setText(""+strReqTime)

                                                // Product Details
                                                tv_ProductName!!.setText(""+strProductname)
                                                tv_ProductComplaint!!.setText(""+strProductComplaint)
                                                tv_Description!!.setText(""+strProductDescription)

                                                ID_Priority = jobjt.getString("Priority")
                                                tie_Priority!!.setText(""+strPriorityName)
                                                btnSave!!.setText("Save")

                                        }
                                        else if (TicketStatus.equals("3"))
                                        {
                                           /* tv_ServiceClick!!.setText("Service Information")
                                            tv_ProductClick!!.setText("Product Details")
                                            ll_lstclick!!.visibility=View.GONE

                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE


                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)
                                            txtv_head!!.setText("Service Assign")


                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductName!!.setText(""+strProductname)
                                            tv_ProductComplaint!!.setText(""+strProductComplaint)
                                            tv_Description!!.setText(""+strProductDescription)

                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                            btnSave!!.setText("Update")*/

                                        }
                                       else if (TicketStatus.equals("4"))
                                        {
                                            tv_ServiceClick!!.setText("Service Information")
                                            tv_ProductClick!!.setText("Product Details")
                                            ll_lstclick!!.visibility=View.VISIBLE

                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE


                                         /*   til_Remarks!!.visibility=View.VISIBLE
                                            til_vehicleDetail!!.visibility=View.GONE
                                            til_Department!!.visibility=View.VISIBLE*/


                                           // til_Remarks!!.visibility=View.GONE

                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)
                                            txtv_head!!.setText("Service Assign")
                                            tie_Remarks!!.setText(""+strRemark)

                                            txtv_custbalnce!!.visibility=View.VISIBLE
                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductName!!.setText(""+strProductname)
                                            tv_ProductComplaint!!.setText(""+strProductComplaint)
                                            tv_Description!!.setText(""+strProductDescription)

                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                            btnSave!!.setText("Update")

                                        }
                                        else if (TicketStatus.equals("5"))
                                        {
                                            tv_ServiceClick!!.setText("Followup Details")
                                            tv_ProductClick!!.setText("Pickup Information")
                                            ll_lstclick!!.visibility=View.VISIBLE

                                            txtv_reqdatetitle!!.setText("Follow Up Date")
                                            txtv_remrkk!!.visibility=View.VISIBLE
                                            txtv_reqtimetitle!!.visibility=View.GONE
                                            txtv_custbalnce!!.visibility=View.GONE
                                            txtv_remrkk!!.setText(""+strRemark)


                                            tv_PickupClick!!.setText("Product Details")

                                            crdvw_pickup!!.visibility=View.VISIBLE
                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE

                                           ll_productname!!.visibility=View.GONE
                                            ll_productcomplnt!!.visibility=View.GONE
                                            ll_productdescrptn!!.visibility=View.GONE

                                            ll_prdcomplnt!!.visibility=View.VISIBLE
                                            ll_prddescrptn!!.visibility=View.VISIBLE
                                            ll_prdnamepick!!.visibility=View.VISIBLE
                                            til_Remarks!!.visibility=View.GONE
                                            til_Department !!.visibility=View.GONE
                                            til_Role!!.visibility=View.GONE
                                            til_vehicleDetail!!.visibility=View.VISIBLE

                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)
                                            txtv_head!!.setText("PickUp Assign")
                                            til_VisitDate!!.hint="PickUp Date"
                                            til_VisitTime!!.hint = "PickUp Time"





                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductNamePick!!.setText(""+strProductname)
                                            tv_ProductComplaintPick!!.setText(""+strProductComplaint)
                                            tv_DescriptionPick!!.setText(""+strProductDescription)






                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                           // tv_ListClick!!.setText("PickUp Information")
                                            btnSave!!.setText("Update")

                                        }

                                        else if (TicketStatus.equals("6"))
                                        {
                                            tv_ServiceClick!!.setText("Followup Details")
                                            tv_ProductClick!!.setText("Product Details")
                                            ll_lstclick!!.visibility=View.VISIBLE
                                            txtv_head!!.setText("Replacement Request")
                                            tie_Remarks!!.setText(""+strRemark)
                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE

                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)

                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductName!!.setText(""+strProductname)
                                            tv_ProductComplaint!!.setText(""+strProductComplaint)
                                            tv_Description!!.setText(""+strProductDescription)

                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                            btnSave!!.setText("Update")

                                        }
                                        else if (TicketStatus.equals("7"))
                                        {
                                            tv_ServiceClick!!.setText("Followup Details")
                                            tv_ProductClick!!.setText("Product Details")
                                            ll_lstclick!!.visibility=View.GONE
                                            txtv_head!!.setText("Delivery Assign")


                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE


                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)
                                            txtv_custbalnce!!.visibility=View.GONE
                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductName!!.setText(""+strProductname)
                                            tv_ProductComplaint!!.setText(""+strProductComplaint)
                                            tv_Description!!.setText(""+strProductDescription)

                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                            tv_ListClick!!.setText("Delivery Information")
                                            btnSave!!.setText("Assign Delivery")
                                            tie_Remarks!!.setText(""+strRemark)

                                        }
                                        else if (TicketStatus.equals("8"))
                                        {
                                            tv_ServiceClick!!.setText("Followup Details")
                                            tv_ProductClick!!.setText("Product Details")
                                            ll_lstclick!!.visibility=View.VISIBLE
                                            txtv_head!!.setText("Factory Service")

                                            tie_Remarks!!.setText(""+strRemark)
                                            crdvw_tkt !!.visibility=View.VISIBLE
                                            crdvw_srvce !!.visibility=View.VISIBLE
                                            crdvw_product !!.visibility=View.VISIBLE

                                            tv_Ticket!!.setText(""+strTicket)
                                            tv_LandMark!!.setText(""+strLandmark)
                                            tv_Customer!!.setText(""+strCustomer)
                                            tv_ContactNo!!.setText(""+strContactNo)
                                            tv_Address!!.setText(""+strAddress)
                                            tv_Mobile!!.setText(""+strMobile)

                                            // Service Information

                                            tv_RequestedDate!!.setText(""+strReqDate)
                                            tv_RequestedTime!!.setText(""+strReqTime)

                                            // Product Details
                                            tv_ProductName!!.setText(""+strProductname)
                                            tv_ProductComplaint!!.setText(""+strProductComplaint)
                                            tv_Description!!.setText(""+strProductDescription)

                                            ID_Priority = jobjt.getString("Priority")
                                            tie_Priority!!.setText(""+strPriorityName)
                                            tv_ListClick!!.setText("Delivery Information")
                                            btnSave!!.setText("Update")

                                        }
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


//                                        if (saDetailArrayList.length()>0){
//
//                                            for (i in 0 until saDetailArrayList.length()) {
//
////                                                "ID_Employee": "12",
////                                                "EmpCode": "E11647926087",
////                                                "Employee": "SACHIN T",
////                                                "ID_CSAEmployeeType": "1",
////                                                "EmployeeType": "Manager",
////                                                "Attend": "0",
////                                                "DepartmentID": "2",
////                                                "Designation": "Service Manager",
////                                                "Department": "Customer service"
//
//                                               val jsonObject = saDetailArrayList.getJSONObject(i)
//
//                                                val jObject = JSONObject()
//                                                jObject.put("DepartmentID",jsonObject.getString("DepartmentID"))
//                                                jObject.put("Department",jsonObject.getString("Department"))
//
//                                                jObject.put("ID_Employee", jsonObject.getString("ID_Employee"))
//                                                jObject.put("Employee", jsonObject.getString("Employee"))
//
//                                                jObject.put("ID_CSAEmployeeType",jsonObject.getString("ID_CSAEmployeeType")) // Role
//                                                jObject.put("EmployeeType",jsonObject.getString("EmployeeType")) // Role
//
//                                             //   jObject.put("ExistType","0") // ExistType = 0 Exist ,1 = Not
//
//
//                                                arrProducts.put(jObject)
//
//
//                                            }
//
//
//
//
//                                        }
                                     Log.i("TESTARRAY", arrProducts.toString())
                                        if (arrProducts.length()>0){
                                            card_details!!.visibility = View.VISIBLE
                                            lnrHead_List!!.visibility = View.VISIBLE
                                            viewList(arrProducts, dateattend!!)
                                        }
                                        rltv_allViews!!.visibility = View.VISIBLE
                                        ticketMode = "0"
                                        hideViews()


//
//                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            finish()
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
                                this@ServiceAssignActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage(Config.PLEASE_TRY_AGAIN)
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                finish()
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tv_TicketClick->{
                ticketMode = "0"
                serviceMode  = "1"
                productMode = "1"
                listMode = "1"
                pickupMode="1"
                hideViews()
            }

            R.id.tv_ServiceClick->{
                ticketMode = "1"
                serviceMode  = "0"
                productMode = "1"
                listMode = "1"
                pickupMode="1"
                hideViews()
            }

            R.id.tv_ProductClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
                listMode = "1"
                pickupMode="1"
                hideViews()
            }

            R.id.tv_ListClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "1"
                listMode = "0"
                pickupMode="1"
                hideViews()
            }

            R.id.tv_PickupClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "1"
                listMode = "1"
                pickupMode="0"
                hideViews()
            }


            R.id.tie_VisitDate->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
            }
            R.id.tie_VisitTime->{
                timeMode = 0
                openBottomTime()
            }
            R.id.tie_Priority->{
                priorityMode = 0
                getServicePriority()
            }

            R.id.tie_Department->{
                Config.disableClick(v)
                depmode=1
                department = 0
                tie_Department!!.setText("")
                getDepartment()
            }
            R.id.tie_Employee->{
                if(til_Department!!.visibility==View.VISIBLE)
                {
                    if (ID_Department.equals("")){

                        Config.snackBars(context,v,"Select Department")

                    }else{
                        Config.disableClick(v)
                        employee = 0
                        getEmployee()
                    }
                }
                else if(til_Department!!.visibility==View.GONE)
                {
                    Config.disableClick(v)
                    employee = 0
                    getEmployee()
                }

            }
            R.id.tie_Role->{
                Config.disableClick(v)
                serRoleCount = 0
                getServiceRoles()
            }

            R.id.btnAdd->{
                Config.disableClick(v)
                var dates =tie_VisitDate!!.text.toString()
                addValidation(dates)
            }
            R.id.btnClear->{

                resetData("0")
            }
            R.id.btnSave->{
                Config.disableClick(v)
                checkAttendance()
                if (saveAttendanceMark){
                    saveValidation(v)
                }


            }

            R.id.txtv_custbalnce->{

                val i = Intent(this@ServiceAssignActivity, CustomerBalanceActivity::class.java)
                i.putExtra("TicketDate",TicketDate)
                i.putExtra("Id_Cust",stridCustomer)

                startActivity(i)
            }

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

    private fun resetData(mode : String) {

        if (mode.equals("0")){
//            tie_VisitDate!!.setText("")
//            tie_VisitTime!!.setText("")
//            tie_Priority!!.setText("")


//            ID_Priority = ""
            getCurrentdateTime()
        }

      //  tie_Remarks!!.setText("")
        tie_Department!!.setText("")
        tie_Employee!!.setText("")
        tie_Role!!.setText("")

        ID_Department = ""
        ID_Employee = ""
        ID_Role = ""
        arrSaveUpdate ="0"
        btnAdd!!.setText("Add")


    }

    private fun addValidation(dates: String) {


     /*  til_VisitDate!!.setError("Visit On Date should be greater than or equal to Todays date");
        til_VisitDate!!.setErrorIconDrawable(null)*/
        if(til_vehicleDetail!!.visibility==View.VISIBLE)
        {
            strRemark = tie_vehicle!!.text.toString()
        }
         if(tie_Remarks!!.visibility==View.GONE)
        {
            strRemark = txtv_remrkk!!.text.toString()
        }
         if(tie_Remarks!!.visibility==View.VISIBLE)
        {
            strRemark = tie_Remarks!!.text.toString()
        }


        try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            val c = Calendar.getInstance().time
            println("Current time => $c")

            val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = df.format(c)
            println("Current date => $formattedDate")

            val date1 = dateFormat.parse(formattedDate)
            val date2 = dateFormat.parse(dates)

            calendar11!!.time = date1
            calendar22!!.time = date2

            System.out.println("Compare Result : " + calendar22.compareTo(calendar11))

            System.out.println("Compare Resultt : " + calendar22.compareTo(calendar11))

        /*    if(calendar22.compareTo(calendar11).equals(-1))
            {
                til_VisitDate!!.setError("Visit On Date should be greater than or equal to Today's Date")
                til_VisitDate!!.setErrorIconDrawable(null)
            }*/

            //  System.out.println("Compare Result : " + calendar1.compareTo(calendar2))
        }
        catch(e: ParseException)
        {


        }
        strVisitDate= calendar22!!.compareTo(calendar11).toString()

         if (strVisitDate.equals("-1")){
           /* til_VisitDate!!.setError("Select Visit Date");
            til_VisitDate!!.setErrorIconDrawable(null)*/

             til_VisitDate!!.setError("Visit On Date should be greater than or equal to Today's Date")
             til_VisitDate!!.setErrorIconDrawable(null)
        }

            else if (strVisitTime.equals("")){
                til_VisitTime!!.setError("Select Visit Time");
                til_VisitTime!!.setErrorIconDrawable(null)
            }
            else if (ID_Priority.equals("")){
                til_Priority!!.setError("Select Priority");
                til_Priority!!.setErrorIconDrawable(null)
            }

            else if (ID_Department.equals("")&& (til_Department!!.isShown)){

                if(til_Department!!.isShown)
                {
                    til_Department!!.setError("Select Department");
                    til_Department!!.setErrorIconDrawable(null)
                }
                else if(!til_Department!!.isShown){

                }




            }

       else if (ID_Employee.equals("")){

            til_Employee!!.setError("Select Employee");
            til_Employee!!.setErrorIconDrawable(null)


        }

        else if (ID_Role.equals("")&& (til_Role!!.isShown)){

               /* if(til_Role!!.isShown) {*/
                    til_Role!!.setError("Select Role");
                    til_Role!!.setErrorIconDrawable(null)

               /* }
                else if(!(til_Role!!.isShown)){

                }
*/

            }

     /*  else if(strVisitDate!=null)
        {

        }*/


     /*   if (ID_Role!!.equals("")){

            til_Role!!.setError("Select Role");
            til_Role!!.setErrorIconDrawable(null)


        }*/

            else
            {

                val jObject = JSONObject()
//            jObject.put("visit_date", strVisitDate)
//            jObject.put("visit_time", strVisitTime)
//            jObject.put("id_priority",ID_Priority )
//            jObject.put("priority",tie_Priority!!.text.toString() )
//            jObject.put("remark", strRemark)
//            jObject.put("id_department",ID_Department )
//            jObject.put("department",tie_Department!!.text.toString() )
//            jObject.put("id_employee", ID_Employee)
//            jObject.put("employee", tie_Employee!!.text.toString())
//            jObject.put("id_role", ID_Role)
//            jObject.put("role", tie_Role!!.text.toString())


                if(til_Department!!.isShown)
                {
                    jObject.put("Department",tie_Department!!.text.toString())
                    jObject.put("DepartmentID",ID_Department)
                }
                else if(!til_Department!!.isShown)
                {
                    jObject.put("Department","")
                    jObject.put("DepartmentID","")
                }


                jObject.put("ID_Employee", ID_Employee)
                jObject.put("Employee", tie_Employee!!.text.toString())
                Log.i("Add Id",ID_Employee.toString())


                if(til_Role!!.visibility==View.VISIBLE ){
                    jObject.put("ID_CSAEmployeeType",ID_Role) // Role
                    jObject.put("EmployeeType",tie_Role!!.text.toString())
                }
                else   if(til_Role!!.visibility==View.GONE){
                    jObject.put("ID_CSAEmployeeType","") // Role
                    jObject.put("EmployeeType","")
                }
                // Role

                var hasId = hasEmployee(arrProducts,"ID_Employee",ID_Employee!!)
                 dateattend=tie_VisitDate!!.text.toString()
                if (arrSaveUpdate.equals("0")){
                    //   jObject.put("ExistType","1") // ExistType = 0 Exist ,1 = Not

                    Log.e(TAG,"arrSaveUpdate 0    "+hasId)
                    if (hasId==true){
                        card_details!!.visibility = View.VISIBLE
                        lnrHead_List!!.visibility = View.VISIBLE
                        arrProducts.put(jObject)
                        Log.e(TAG,"arrProducts  arrSaveUpdate 0  "+arrProducts)

                        if (arrProducts.length() > 0) {
                            arrproductSort = JSONArray()
                            for (k in 0 until arrProducts.length()) {
                                val jsonObject = arrProducts.getJSONObject(k)
                                // reportNamesort.put(k,jsonObject)
                                arrproductSort.put(jsonObject)
                            }

                            viewList(arrProducts, dateattend!!)
                        }



                   /*     val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
                        recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                        //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                        val adapter = ServiceListHistAdapter(this@ServiceAssignActivity,arrProducts!!)
                        recyServiceAssign!!.adapter = adapter
                        adapter.setClickListener(this@ServiceAssignActivity)*/

                        resetData("1")

                        ticketMode = "1"
                        serviceMode  = "1"
                        productMode = "1"
                        listMode = "0"
                        hideViews()
                    }else{
                        til_Employee!!.setError(" Employee already exists.");
                        til_Employee!!.setErrorIconDrawable(null)
                    }
                }
                else if (arrSaveUpdate.equals("1")){
                    //    jObject.put("ExistType","0") // ExistType = 0 Exist ,1 = Not


                    Log.e(TAG,"arrSaveUpdate 1   "+hasId+arrIndexUpdate.toString())
                    if (hasId==false){
                        card_details!!.visibility = View.VISIBLE
                        lnrHead_List!!.visibility = View.VISIBLE

                        Log.e(TAG,"arrProducts  6091  "+arrProducts+"\n"+arrIndexUpdate!!)
                        arrProducts.remove(arrIndexUpdate!!)
                        Log.e(TAG,"arrProducts  6092  "+arrProducts)
                        Log.e(TAG,"jObject  6092  "+jObject)
                        Log.i("arrindex",arrIndexUpdate.toString())
                        arrProducts.put(arrIndexUpdate!!,jObject)
                        Log.e(TAG,"arrProducts  6093  "+arrProducts)
                     //   viewList(arrProducts)



                        if (arrProducts.length() > 0) {
                            arrproductSort = JSONArray()
                            for (k in 0 until arrProducts.length()) {
                                val jsonObject = arrProducts.getJSONObject(k)
                                // reportNamesort.put(k,jsonObject)
                                arrproductSort.put(jsonObject)
                            }
                            viewList(arrProducts, dateattend!!)
                        }

                      /*  val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
                        recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                        //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                        val adapter = ServiceAssignListAdapter(this@ServiceAssignActivity,arrProducts!!)
                        recyServiceAssign!!.adapter = adapter
                        adapter.setClickListener(this@ServiceAssignActivity)*/
                        resetData("1")

                        ticketMode = "1"
                        serviceMode  = "1"
                        productMode = "1"
                        listMode = "0"
                        hideViews()
                    }
                    else{
                        card_details!!.visibility = View.VISIBLE
                        lnrHead_List!!.visibility = View.VISIBLE

                        Log.e(TAG,"arrProducts  6091  "+arrProducts)
                        arrProducts.remove(arrIndexUpdate!!)
                        Log.e(TAG,"arrProducts  6092  "+arrProducts)
                        Log.e(TAG,"jObject  6092  "+jObject)
                        arrProducts.put(arrIndexUpdate!!,jObject)
                        Log.e(TAG,"arrProducts  6093  "+arrProducts)
                        //   viewList(arrProducts)



                        if (arrProducts.length() > 0) {
                            arrproductSort = JSONArray()
                            for (k in 0 until arrProducts.length()) {
                                val jsonObject = arrProducts.getJSONObject(k)
                                // reportNamesort.put(k,jsonObject)
                                arrproductSort.put(jsonObject)
                            }
                            viewList(arrProducts, dateattend!!)
                        }

                        /*  val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
                          recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                          val adapter = ServiceAssignListAdapter(this@ServiceAssignActivity,arrProducts!!)
                          recyServiceAssign!!.adapter = adapter
                          adapter.setClickListener(this@ServiceAssignActivity)*/
                        resetData("1")

                        ticketMode = "1"
                        serviceMode  = "1"
                        productMode = "1"
                        listMode = "0"
                        hideViews()
                      /*  til_Employee!!.setError("Employee already exists.")
                        til_Employee!!.setErrorIconDrawable(null)*/
                    }

                }
              /*  else{
                    if (arrProducts!!.length()>0){
                        card_details!!.visibility = View.VISIBLE
                        viewList(arrProducts)
                    }
                    rltv_allViews!!.visibility = View.VISIBLE
                    ticketMode = "0"
                    hideViews()

                    Log.e(TAG,"  chklatest     "+arrProducts)
                }
*/

            }

    /*    }
        catch (e:Exception)
        {
            Log.e(TAG,"Exception   validation   "+e.toString())
        }*/
//        strVisitDate = tie_VisitDate!!.text.toString()
//        strVisitTime = tie_VisitTime!!.text.toString()


    }

    fun hasEmployee(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun addToArray(jObject: JSONObject,ID_Employee : String) {

        if (arrProducts.length() == 0){
            arrProducts.put(jObject)
            Log.e(TAG,"  3671     "+arrProducts)
            viewList(arrProducts, dateattend!!)
        }else{
            Log.e(TAG,"  3672     "+arrProducts.length())
            Log.e(TAG,"  3673     "+arrProducts)
            for (i in 0 until arrProducts.length()) {
                Log.e(TAG,"3674   "+i)
                var jsonObject = arrProducts.getJSONObject(i)
                Log.e(TAG,"  36731     "+jsonObject.getString("ID_Employee")+"  :   "+ID_Employee)


//                if (jsonObject.getString("id_employee").toString().equals(ID_Employee)){
//                    Log.e(TAG,"3675   "+jsonObject.getString("id_employee"))
//                    til_Employee!!.setError(" Employee already exists.");
//                    til_Employee!!.setErrorIconDrawable(null)
//                }
//                else{
//                    Log.e(TAG,"3676   "+jsonObject.getString("id_employee"))
//                    arrProducts.put(jObject)
//                    viewList(arrProducts)
//                }

            }
        }


    }

     fun viewList(arrProducts: JSONArray, dateattend: String) {


      /*  recyServiceAssign!!.visibility=View.VISIBLE
        val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
        recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterService = ServiceAssignListAdapter(this@ServiceAssignActivity, arrProducts)
        recyServiceAssign!!.adapter = adapterService
        adapterService!!.setClickListener(this@ServiceAssignActivity)
*/
         Log.i("ARRAYJSON", arrProducts.toString())
         val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
         recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
         //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
          adapterService = ServiceAssignListAdapter(this@ServiceAssignActivity,arrProducts!!,dateattend)
         recyServiceAssign!!.adapter = adapterService
         adapterService!!.setClickListener(this@ServiceAssignActivity)



    }

    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        if (dateMode == 0){
            date_Picker1.maxDate = System.currentTimeMillis()
        }

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
                tie_VisitDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                strVisitDate = ""+strYear+"-"+strMonth+"-"+strDay
//                if (dateMode == 0){
//                    tie_VisitDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        txt_Warning = view.findViewById<TextView>(R.id.txt_Warning)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        txt_Warning!!.visibility = View.GONE
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
          //  dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

//                tie_VisitTime!!.setText(output)
//                val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)
//                strVisitTime = inputDateFormat.format(date)

                futureDateDisable(output,dialog,date)



            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun futureDateDisable(outTime: String, dialog: BottomSheetDialog,date : Date) {
        try {


            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)

            var curDate = tie_VisitDate!!.text.toString()

            if (sdfDate1.format(newDate).equals(curDate)){
                Log.e(TAG,"Change date 2195   "+curDate+"  "+outTime)
                Log.e(TAG,"Change date 2195   "+sdfTime1.format(newDate))
//                tie_Time!!.setText(""+sdfTime1.format(newDate))
                val format = SimpleDateFormat("hh:mm a", Locale.US)
                val date1: Date = format.parse(outTime)
                val date2: Date = format.parse(sdfTime1.format(newDate))

                if (date1.compareTo(date2) > 0) {
                    // time1 is greater than or equal to time2
                    // Handle the logic for this case
                    Log.e(TAG,"Change date 2195 gdyretyreyre  ")
                    txt_Warning!!.visibility = View.VISIBLE
                } else {
                    // time1 is less than time2
                    // Handle the logic for this case
                    txt_Warning!!.visibility = View.GONE
                    tie_VisitTime!!.setText(outTime)
                    val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                    strVisitTime = inputDateFormat.format(date)
                    dialog.dismiss()
                }
            }else{
                txt_Warning!!.visibility = View.GONE
                tie_VisitTime!!.setText(outTime)
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                strVisitTime = inputDateFormat.format(date)
                dialog.dismiss()
            }



        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun getServicePriority() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                servicePriorityViewModel.getServicePriority(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (priorityMode == 0){
                                    priorityMode++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   353   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        servPriorityArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (servPriorityArrayList.length()>0){

                                            servicePriorityPopup(servPriorityArrayList)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
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

    private fun servicePriorityPopup(servPriorityArrayList: JSONArray) {

        try {

            dialogServPriority = Dialog(this)
            dialogServPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServPriority!! .setContentView(R.layout.service_priority_popup)
            dialogServPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyServPriority = dialogServPriority!! .findViewById(R.id.recyServPriority) as RecyclerView
            val etsearch = dialogServPriority!! .findViewById(R.id.etsearch) as EditText

            servPrioritySort = JSONArray()
            for (k in 0 until servPriorityArrayList.length()) {
                val jsonObject = servPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                servPrioritySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
            recyServPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServicePriorityAdapter(this@ServiceAssignActivity, servPrioritySort)
            recyServPriority!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    servPrioritySort = JSONArray()

                    for (k in 0 until servPriorityArrayList.length()) {
                        val jsonObject = servPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                servPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"servPrioritySort               7103    "+servPrioritySort)
                    val adapter = ServicePriorityAdapter(this@ServiceAssignActivity, servPrioritySort)
                    recyServPriority!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignActivity)
                }
            })

            dialogServPriority!!.show()
            dialogServPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun getDepartment() {
        Log.e(TAG,"ffffffffffffffff 1== "+depmode)
//        var department = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                departmentViewModel.getDepartment(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (department == 0){
                                    department++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1142   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        departmentArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (departmentArrayList.length()>0){
                                          /*  if(tie_Department!!.text.toString()!!.equals(""))
                                            {
                                                for (k in 0 until departmentArrayList.length()) {
                                                    val jsonObject = departmentArrayList.getJSONObject(k)
                                                    //   departListSort.put(k,jsonObject)
                                                    dep_id = jsonObject.getString("DeptName")
                                                    if(k==0)
                                                    {
                                                        tie_Department!!.setText(dep_id)
                                                    }
                                                }



                                            }
                                            else  if(!tie_Department!!.text.toString()!!.equals(""))
                                            {*/

                                            if (depmode == 0){
                                                var jsonObject1 = departmentArrayList.getJSONObject(0)
                                                tie_Department!!.setText(jsonObject1.getString("DeptName"))
                                                ID_Department = jsonObject1.getString("ID_Department")

                                            }else {


                                                departmentPopup(departmentArrayList)
                                            }
                                           // }




                                            ID_Employee = ""
                                            tie_Employee!!.setText("")



                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
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
    private fun getDepartment1() {
//        var department = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                departmentViewModel.getDepartment(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (department == 0){
                                    department++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1142   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        departmentArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (departmentArrayList.length()>0){

                                         //   departmentPopup(departmentArrayList)

                                            for (k in 0 until departmentArrayList.length()) {
                                                val jsonObject = departmentArrayList.getJSONObject(k)
                                                //   departListSort.put(k,jsonObject)
                                                 dep_id = jsonObject.getString("DeptName")
                                            }

                                            tie_Department!!.setText(dep_id)


                                            ID_Employee = ""
                                            tie_Employee!!.setText("")



                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
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

    private fun departmentPopup(departmentArrayList: JSONArray) {
        try {

            dialogDepartment = Dialog(this)
            dialogDepartment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDepartment!! .setContentView(R.layout.department_popup)
            dialogDepartment!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDeaprtment = dialogDepartment!! .findViewById(R.id.recyDeaprtment) as RecyclerView
            val etsearch = dialogDepartment!! .findViewById(R.id.etsearch) as EditText

            departmentSort = JSONArray()
            for (k in 0 until departmentArrayList.length()) {
                val jsonObject = departmentArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                departmentSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@ServiceAssignActivity, departmentSort)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    departmentSort = JSONArray()

                    for (k in 0 until departmentArrayList.length()) {
                        val jsonObject = departmentArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                departmentSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"departmentSort               7103    "+departmentSort)
                    val adapter = DepartmentAdapter(this@ServiceAssignActivity, departmentSort)
                    recyDeaprtment!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignActivity)
                }
            })

            dialogDepartment!!.show()
            dialogDepartment!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmployee() {
        //   var employee = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, ID_Department!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employee == 0){
                                    employee++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length()>0){

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
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
                        }catch (e:Exception){
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

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!! .setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!! .findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!! .findViewById(R.id.etsearch) as EditText

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@ServiceAssignActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignActivity)

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
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+employeeSort)
                    val adapter = EmployeeAdapter(this@ServiceAssignActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getServiceRoles() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceRoleViewModel.getServiceRole(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serRoleCount == 0){
                                    serRoleCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        
                                        val jobjt = jObject.getJSONObject("RoleDetails")
                                        serviceRoleArrayList = jobjt.getJSONArray("RoleDetailsList")
                                        if (serviceRoleArrayList.length()>0){

                                            serviceRolePopup(serviceRoleArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
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
                        }catch (e:Exception){
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

    private fun serviceRolePopup(serviceRoleArrayList: JSONArray) {
        try {

            dialogServiceRole = Dialog(this)
            dialogServiceRole!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServiceRole!! .setContentView(R.layout.service_role_popup)
            dialogServiceRole!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyServiceRole = dialogServiceRole!! .findViewById(R.id.recyServiceRole) as RecyclerView
          //  val etsearch = dialogServiceRole!! .findViewById(R.id.etsearch) as EditText

            serviceRoleSort = JSONArray()
            for (k in 0 until serviceRoleArrayList.length()) {
                val jsonObject = serviceRoleArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                serviceRoleSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
            recyServiceRole!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, serviceRoleArrayList)
            val adapter = ServiceRoleAdapter(this@ServiceAssignActivity, serviceRoleSort)
            recyServiceRole!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignActivity)

//            etsearch!!.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(p0: Editable?) {
//                }
//
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                    //  list_view!!.setVisibility(View.VISIBLE)
//                    val textlength = etsearch!!.text.length
//                    departmentSort = JSONArray()
//
//                    for (k in 0 until serviceRoleArrayList.length()) {
//                        val jsonObject = serviceRoleArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("DeptName").length) {
//                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                                departmentSort.put(jsonObject)
//                            }
//
//                        }
//                    }
//
//                    Log.e(TAG,"departmentSort               7103    "+departmentSort)
//                    val adapter = DepartmentAdapter(this@ServiceAssignActivity, departmentSort)
//                    recyDeaprtment!!.adapter = adapter
//                    adapter.setClickListener(this@ServiceAssignActivity)
//                }
//            })

            dialogServiceRole!!.show()
            dialogServiceRole!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideViews() {
        lnrHead_Ticket!!.visibility = View.VISIBLE
        lnrHead_Service!!.visibility = View.VISIBLE
        lnrHead_Product!!.visibility = View.VISIBLE
        lnrHead_List!!.visibility = View.VISIBLE
        lnrHead_Pickup!!.visibility = View.VISIBLE

        if (arrProducts.length() > 0){
            tv_no_record!!.visibility =View.GONE
        }else{
            tv_no_record!!.visibility =View.VISIBLE
        }

        if (ticketMode.equals("1")) {
            lnrHead_Ticket!!.visibility = View.GONE
        }
        if (serviceMode.equals("1")) {
            lnrHead_Service!!.visibility = View.GONE
        }
        if (productMode.equals("1")) {
            lnrHead_Product!!.visibility = View.GONE
        }
        if (listMode.equals("1")) {
            lnrHead_List!!.visibility = View.GONE
        }
        if (pickupMode.equals("1")) {
            lnrHead_Pickup!!.visibility = View.GONE
        }
    }


    override fun onClick(position: Int, data: String) {


        if (data.equals("servpriority")){
            dialogServPriority!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = servPrioritySort.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("Code"))
            ID_Priority = jsonObject.getString("Code")
            tie_Priority!!.setText(jsonObject.getString("Description"))
            strPriorityName = jsonObject.getString("Description")



        }

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
//            val jsonObject = departmentArrayList.getJSONObject(position)
            val jsonObject = departmentSort.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department")+"\n"+departmentSort.getJSONObject(position))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))

        /*    ID_Employee = ""
            tie_Employee!!.setText("")*/


        }


        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))



        }



        if (data.equals("serviceRole")){
            dialogServiceRole!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = serviceRoleSort.getJSONObject(position)
            Log.e(TAG,"ID_Role   "+jsonObject.getString("ID_Role"))
            ID_Role = jsonObject.getString("ID_Role")
            tie_Role!!.setText(jsonObject.getString("RoleName"))


        }

        if (data.equals("deleteArrayList")){
//            val jsonObject = arrProducts.getJSONObject(position)
//            if (jsonObject.getString("ExistType").equals("1")){
//                arrProducts.remove(position)
//                adapterService!!.notifyItemRemoved(position)
//            }
//            else{
//
//            }

            arrProducts.remove(position)
            adapterService!!.notifyItemRemoved(position)




            if (arrProducts.length() > 0){
                tv_no_record!!.visibility =View.GONE
            }else{
                tv_no_record!!.visibility =View.VISIBLE
            }


        }
        if (data.equals("editArrayList")){

            try {
                val jsonObject = arrProducts.getJSONObject(position)
         //   arrProducts.add(position)
//            adapterService!!.notifyItemRemoved(position)
                arrSaveUpdate = "1"
                arrIndexUpdate = position
                Log.i("arrindex",arrIndexUpdate.toString())
                btnAdd!!.setText("Update")


//            ID_Priority = jsonObject.getString("id_priority")
//            ID_Department = jsonObject.getString("id_department")
//            ID_Employee = jsonObject.getString("id_employee")
//            ID_Role= jsonObject.getString("id_role")
//
//            tie_VisitDate!!.setText(""+jsonObject.getString("visit_date"))
//            tie_VisitTime!!.setText(""+jsonObject.getString("visit_time"))
//            tie_Priority!!.setText(""+jsonObject.getString("priority"))
//            tie_Remarks!!.setText(""+jsonObject.getString("remark"))
//            tie_Department!!.setText(""+jsonObject.getString("department"))
//            tie_Employee!!.setText(""+jsonObject.getString("employee"))
//            tie_Role!!.setText(""+jsonObject.getString("role"))


              //  ID_Department = jsonObject.getString("DepartmentID")

                ID_Employee = jsonObject.getString("ID_Employee")
                Log.i("Editemp",ID_Employee.toString())
                if(til_Role!!.isShown())
                {
                    ID_Role= jsonObject.getString("ID_CSAEmployeeType")
                    tie_Role!!.setText(""+jsonObject.getString("EmployeeType"))

                }


                tie_Department!!.setText(""+jsonObject.getString("Department"))
                tie_Employee!!.setText(""+jsonObject.getString("Employee"))


                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
                listMode = "1"

                hideViews()


            }catch (e: Exception){
                Log.e(TAG,"Exception  1369   "+e.toString())
            }

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
                editable === tie_VisitDate!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_VisitDate!!.isErrorEnabled = false
                }
                editable === tie_VisitTime!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_VisitTime!!.isErrorEnabled = false
                }
                editable === tie_Department!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_Department!!.isErrorEnabled = false

                }
                editable === tie_Employee!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_Employee!!.isErrorEnabled = false
                }
                editable === tie_Priority!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_Priority!!.isErrorEnabled = false
                }

                editable === tie_Role!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_Role!!.isErrorEnabled = false
                }


            }

        }
    }

    private fun saveValidation(v: View) {

       strVisitDate = tie_VisitDate!!.text.toString()
       strVisitTime = tie_VisitTime!!.text.toString()
        strRemark = tie_Remarks!!.text.toString()

        if (strVisitDate.equals("")){
            til_VisitDate!!.setError("Select Visit Date");
            til_VisitDate!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"
            listMode = "1"
            hideViews()
        }
        else if (strVisitTime.equals("")){
            til_VisitTime!!.setError("Select Visit Time");
            til_VisitTime!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"
            listMode = "1"
            hideViews()
        }
        else if (ID_Priority.equals("")){
            til_Priority!!.setError("Select Priority");
            til_Priority!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"
            listMode = "1"
            hideViews()
        }
        else if (arrProducts.length() == 0){

            Config.snackBars(context,v,"Add Atleast One Employee Details")
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"
            listMode = "1"
            hideViews()

        }
        else{

//            val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
//            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val Visitdate = outputFormat.parse(strVisitDate)

       //     [{"FK_Employee":"1","EmployeeType":"3"}]

            var arrAssignees = JSONArray()
            for (i in 0 until arrProducts.length()) {
                var jsonObject = arrProducts.getJSONObject(i)
                val jObject = JSONObject()
                jObject.put("FK_Employee",  ProdsuitApplication.encryptStart(jsonObject.getString("ID_Employee")))
                jObject.put("EmployeeType",  ProdsuitApplication.encryptStart(jsonObject.getString("ID_CSAEmployeeType")))
                arrAssignees.put(jObject)
            }

            Log.e(TAG,"saveValidation  1585"
            +"\n"+"strVisitDate   "+strVisitDate
                    +"\n"+"strVisitTime   "+strVisitTime
                    +"\n"+"ID_Priority   "+ID_Priority
                    +"\n"+"arrAssignees   "+arrAssignees
                    +"\n"+"arrProducts   "+arrProducts)

            serviceassignConfirm(arrAssignees)

//            serUpdateCount = 0
//            serviceassignUpdate(arrAssignees.toString())
        }



    }

    private fun serviceassignConfirm(arrAssignees: JSONArray) {

        try {

            val dialogConfirmPop = Dialog(this)
            dialogConfirmPop!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirmPop!!.setContentView(R.layout.service_assign_confirmation)
            dialogConfirmPop!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


            ////////////////////////

            val ll_Ticket = dialogConfirmPop!!.findViewById(R.id.ll_Ticket) as LinearLayout
            val ll_LandMark = dialogConfirmPop!!.findViewById(R.id.ll_LandMark) as LinearLayout
            val ll_Customer = dialogConfirmPop!!.findViewById(R.id.ll_Customer) as LinearLayout
            val ll_ContactNo = dialogConfirmPop!!.findViewById(R.id.ll_ContactNo) as LinearLayout
            val ll_Address = dialogConfirmPop!!.findViewById(R.id.ll_Address) as LinearLayout
            val ll_Mobile = dialogConfirmPop!!.findViewById(R.id.ll_Mobile) as LinearLayout



            val ll_ReqDate = dialogConfirmPop!!.findViewById(R.id.ll_ReqDate) as LinearLayout
            val ll_ReqTime = dialogConfirmPop!!.findViewById(R.id.ll_ReqTime) as LinearLayout
            val ll_ProductName = dialogConfirmPop!!.findViewById(R.id.ll_ProductName) as LinearLayout
            val ll_ProductComplaint = dialogConfirmPop!!.findViewById(R.id.ll_ProductComplaint) as LinearLayout
            val ll_Description = dialogConfirmPop!!.findViewById(R.id.ll_Description) as LinearLayout
            val ll_VisitDate = dialogConfirmPop!!.findViewById(R.id.ll_VisitDate) as LinearLayout
            val ll_VisitTime = dialogConfirmPop!!.findViewById(R.id.ll_VisitTime) as LinearLayout
            val ll_Priority = dialogConfirmPop!!.findViewById(R.id.ll_Priority) as LinearLayout
            val ll_Remark = dialogConfirmPop!!.findViewById(R.id.ll_Remark) as LinearLayout


            ////////////////////////


            val tvp_Ticket = dialogConfirmPop!!.findViewById(R.id.tvp_Ticket) as TextView
            val tvp_LandMark = dialogConfirmPop!!.findViewById(R.id.tvp_LandMark) as TextView
            val tvp_Customer = dialogConfirmPop!!.findViewById(R.id.tvp_Customer) as TextView
            val tvp_ContactNo = dialogConfirmPop!!.findViewById(R.id.tvp_ContactNo) as TextView
            val tvp_Address = dialogConfirmPop!!.findViewById(R.id.tvp_Address) as TextView
            val tvp_Mobile = dialogConfirmPop!!.findViewById(R.id.tvp_Mobile) as TextView
            val tvp_ReqDate = dialogConfirmPop!!.findViewById(R.id.tvp_ReqDate) as TextView
            val tvp_ReqTime = dialogConfirmPop!!.findViewById(R.id.tvp_ReqTime) as TextView
            val tvp_ProductName = dialogConfirmPop!!.findViewById(R.id.tvp_ProductName) as TextView
            val tvp_ProductComplaint = dialogConfirmPop!!.findViewById(R.id.tvp_ProductComplaint) as TextView
            val tvp_Description = dialogConfirmPop!!.findViewById(R.id.tvp_Description) as TextView
            val tvp_VisitDate = dialogConfirmPop!!.findViewById(R.id.tvp_VisitDate) as TextView
            val tvp_VisitTime = dialogConfirmPop!!.findViewById(R.id.tvp_VisitTime) as TextView
            val tvp_Priority = dialogConfirmPop!!.findViewById(R.id.tvp_Priority) as TextView
            val tvp_Remark = dialogConfirmPop!!.findViewById(R.id.tvp_Remark) as TextView

            val recyAssignList = dialogConfirmPop!!.findViewById(R.id.recyAssignList) as FullLenghRecyclertview

            Log.e(TAG,"arrProducts  1809   "+arrProducts)

            val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
            recyAssignList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            var adapterConf = ServiceAssignConfirmListAdapter(this@ServiceAssignActivity, arrProducts)
            recyAssignList!!.adapter = adapterConf




            ////////////////////////////

            if (strTicket.equals("")) {
                ll_Ticket!!.visibility = View.GONE
            }

            if (strLandmark.equals("")) {
                ll_LandMark!!.visibility = View.GONE
            }
            if (strCustomer.equals("")) {
                ll_Customer!!.visibility = View.GONE
            }
            if (strContactNo.equals("")) {
                ll_ContactNo!!.visibility = View.GONE
            }
            if (strAddress.equals("")) {
                ll_Address!!.visibility = View.GONE
            }
            if (strMobile.equals("")) {
                ll_Mobile!!.visibility = View.GONE
            }
            if (strReqDate.equals("")) {
                ll_ReqDate!!.visibility = View.GONE
            }
            if (strReqTime.equals("")) {
                ll_ReqTime!!.visibility = View.GONE
            }
            if (strProductname.equals("")) {
                ll_ProductName!!.visibility = View.GONE
            }
            if (strProductComplaint.equals("")) {
                ll_ProductComplaint!!.visibility = View.GONE
            }
            if (strProductDescription.equals("")) {
                ll_Description!!.visibility = View.GONE
            }
            if (strVisitDate.equals("")) {
                ll_VisitDate!!.visibility = View.GONE
            }
            if (strVisitTime.equals("")) {
                ll_VisitTime!!.visibility = View.GONE
            }
            if (strPriorityName.equals("")) {
                ll_Priority!!.visibility = View.GONE
            }
            if (strRemark.equals("")) {
                ll_Remark!!.visibility = View.GONE
            }


            tvp_Ticket!!.setText(strTicket)
            tvp_LandMark!!.setText(strLandmark)
            tvp_Customer!!.setText(strCustomer)
            tvp_ContactNo!!.setText(strContactNo)
            tvp_Address!!.setText(strAddress)
            tvp_Mobile!!.setText(strMobile)
            tvp_ReqDate!!.setText(strReqDate)
            tvp_ReqTime!!.setText(strReqTime)
            tvp_ProductName!!.setText(strProductname)
            tvp_ProductComplaint!!.setText(strProductComplaint)
            tvp_Description!!.setText(strProductDescription)
            tvp_VisitDate!!.setText(tie_VisitDate!!.text.toString())
            tvp_VisitTime!!.setText(tie_VisitTime!!.text.toString())
            tvp_Priority!!.setText(strPriorityName)
            tvp_Remark!!.setText(strRemark)


            ///////////


            val btnCancel = dialogConfirmPop!!.findViewById(R.id.btnCancel) as Button
            val btnOk = dialogConfirmPop!!.findViewById(R.id.btnOk) as Button

            Log.e(TAG, "")


            btnCancel.setOnClickListener {
                dialogConfirmPop.dismiss()
            }

            btnOk.setOnClickListener {
                dialogConfirmPop.dismiss()
                serUpdateCount = 0
                serviceassignUpdate(arrAssignees)
            }


            dialogConfirmPop!!.show()
            dialogConfirmPop!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun serviceassignUpdate(arrAssignees: JSONArray) {
        var ReqMode = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignViewModel.getServiceAssign(this,ReqMode,ID_CustomerServiceRegister!!,arrAssignees!!,strVisitDate!!,strVisitTime!!,ID_Priority!!,strRemark!!,FK_CustomerserviceregisterProductDetails!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serUpdateCount == 0) {
                                    serUpdateCount++
                                    Log.e(TAG,"serviceAssignViewModel  1711   "+msg)
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        try {
                                            val jobjt = jObject.getJSONObject("CustomerserviceassignUpdate")
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
                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        }catch (e: Exception){
                                            val builder = AlertDialog.Builder(
                                                this@ServiceAssignActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage(e.toString())
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->

                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            onBackPressed()
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
                                this@ServiceAssignActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage(Config.PLEASE_TRY_AGAIN)
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                finish()
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

}