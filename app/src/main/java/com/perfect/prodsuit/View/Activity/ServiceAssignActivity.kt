package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.FullLenghRecyclertview
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ServiceAssignActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ServiceAssignActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    internal var llNew: LinearLayout? = null
    internal var llOnGoing: LinearLayout? = null

    internal var card_details: CardView? = null
    internal var recyServiceAssign: RecyclerView? = null

    lateinit var serviceAssignViewModel: ServiceAssignViewModel
    lateinit var serviceAssignArrayList : JSONArray


    private var tv_TicketClick: TextView? = null
    private var tv_ServiceClick: TextView? = null
    private var tv_ProductClick: TextView? = null
    private var tv_ListClick: TextView? = null

    private var rltv_allViews: RelativeLayout? = null
    private var lnrHead_Ticket: LinearLayout? = null
    private var lnrHead_Service: LinearLayout? = null
    private var lnrHead_Product: LinearLayout? = null
    private var lnrHead_List: LinearLayout? = null


   // Ticket Information
    private var tv_Ticket: TextView? = null
    private var tv_LandMark: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_ContactNo: TextView? = null
    private var tv_Address: TextView? = null
    private var tv_Mobile: TextView? = null

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
    private var tie_Remarks: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_Role: TextInputEditText? = null

    private var til_VisitDate: TextInputLayout? = null
    private var til_VisitTime: TextInputLayout? = null
    private var til_Priority: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_Role: TextInputLayout? = null

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

    var arrSaveUpdate: String? = "0"
    var arrIndexUpdate: Int? = 0

    var arrProducts = JSONArray()
    var adapterService : ServiceAssignListAdapter? = null
    var ID_CustomerServiceRegister: String? = ""

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
//    var str: String? = ""

    var serUpdateCount = 0

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
        Log.e(TAG,"ID_CustomerServiceRegister  163   "+ID_CustomerServiceRegister)
//        ticketMode = "0"
//        hideViews()

        getCurrentdateTime()
        getServiceAssignDetails()



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

        recyServiceAssign = findViewById<RecyclerView>(R.id.recyServiceAssign)
        card_details = findViewById<CardView>(R.id.card_details)

        tv_TicketClick = findViewById<TextView>(R.id.tv_TicketClick)
        tv_ServiceClick = findViewById<TextView>(R.id.tv_ServiceClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)
        tv_ListClick = findViewById<TextView>(R.id.tv_ListClick)

        tv_TicketClick!!.setOnClickListener(this)
        tv_ServiceClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)
        tv_ListClick!!.setOnClickListener(this)

        rltv_allViews = findViewById<RelativeLayout>(R.id.rltv_allViews)
        lnrHead_Ticket = findViewById<LinearLayout>(R.id.lnrHead_Ticket)
        lnrHead_Service = findViewById<LinearLayout>(R.id.lnrHead_Service)
        lnrHead_Product = findViewById<LinearLayout>(R.id.lnrHead_Product)
        lnrHead_List = findViewById<LinearLayout>(R.id.lnrHead_List)

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

        til_VisitDate = findViewById<TextInputLayout>(R.id.til_VisitDate)
        til_VisitTime = findViewById<TextInputLayout>(R.id.til_VisitTime)
        til_Priority = findViewById<TextInputLayout>(R.id.til_Priority)
        til_Department = findViewById<TextInputLayout>(R.id.til_Department)
        til_Employee = findViewById<TextInputLayout>(R.id.til_Employee)
        til_Role = findViewById<TextInputLayout>(R.id.til_Role)

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
                serviceAssignDetailViewModel.getServiceAssignDetail(this,ReqMode,ID_CustomerServiceRegister!!)!!.observe(
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
                                        strAddress = jobjt.getString("Address")
                                        strMobile = jobjt.getString("Mobile")
                                        strReqDate = jobjt.getString("FromDate")+" - "+jobjt.getString("ToDate")
                                        strReqTime = jobjt.getString("FromTime")+" - "+jobjt.getString("ToTime")
                                        strProductname = jobjt.getString("Productname")
                                        strProductComplaint = jobjt.getString("ProductComplaint")
                                        strProductDescription = jobjt.getString("ProductDescription")
                                        strPriorityName = jobjt.getString("PriorityName")


                                        tv_Ticket!!.setText(""+strTicket)
                                        tv_LandMark!!.setText(""+strLandmark)
                                        tv_Customer!!.setText(""+strCustomer)
                                        tv_ContactNo!!.setText(""+strContactNo)
                                        tv_Address!!.setText(""+strAddress)
                                        tv_Mobile!!.setText(""+strMobile)

                                        // Service Information

                                        tv_RequestedDate!!.setText(""+strReqDate)
                                        tv_RequestedTime!!.setText(""+strVisitTime)

                                        // Product Details
                                        tv_ProductName!!.setText(""+strProductname)
                                        tv_ProductComplaint!!.setText(""+strProductComplaint)
                                        tv_Description!!.setText(""+strProductDescription)

                                        ID_Priority = jobjt.getString("Priority")
                                        tie_Priority!!.setText(""+strPriorityName)


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

                                        if (arrProducts.length()>0){
                                            card_details!!.visibility = View.VISIBLE
                                            viewList(arrProducts)
                                        }
                                        rltv_allViews!!.visibility = View.VISIBLE
                                        ticketMode = "0"
                                        hideViews()


//                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
//                                        if (employeeArrayList.length()>0){
//
//                                            employeePopup(employeeArrayList)
//
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
                hideViews()
            }

            R.id.tv_ServiceClick->{
                ticketMode = "1"
                serviceMode  = "0"
                productMode = "1"
                listMode = "1"
                hideViews()
            }

            R.id.tv_ProductClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
                listMode = "1"
                hideViews()
            }

            R.id.tv_ListClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "1"
                listMode = "0"
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
                department = 0
                getDepartment()
            }
            R.id.tie_Employee->{
                if (ID_Department.equals("")){

                    Config.snackBars(context,v,"Select Department")

                }else{
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
                addValidation()
            }
            R.id.btnClear->{

                resetData("0")
            }
            R.id.btnSave->{
                Config.disableClick(v)
                saveValidation(v)
            }


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

    private fun addValidation() {
//        strVisitDate = tie_VisitDate!!.text.toString()
//        strVisitTime = tie_VisitTime!!.text.toString()
        strRemark = tie_Remarks!!.text.toString()

        if (strVisitDate.equals("")){
            til_VisitDate!!.setError("Select Visit Date");
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

        else if (ID_Department.equals("")){
            til_Department!!.setError("Select Department");
            til_Department!!.setErrorIconDrawable(null)
        }
        else if (ID_Employee.equals("")){
            til_Employee!!.setError("Select Employee");
            til_Employee!!.setErrorIconDrawable(null)
        }
        else if (ID_Role.equals("")){
            til_Role!!.setError("Select Role");
            til_Role!!.setErrorIconDrawable(null)
        }
        else{

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

            jObject.put("DepartmentID",ID_Department)
            jObject.put("Department",tie_Department!!.text.toString())

            jObject.put("ID_Employee", ID_Employee)
            jObject.put("Employee", tie_Employee!!.text.toString())

            jObject.put("ID_CSAEmployeeType",ID_Role) // Role
            jObject.put("EmployeeType",tie_Role!!.text.toString()) // Role



            if (arrSaveUpdate.equals("0")){
             //   jObject.put("ExistType","1") // ExistType = 0 Exist ,1 = Not
                var hasId = hasEmployee(arrProducts,"ID_Employee",ID_Employee!!)
                Log.e(TAG,"367122    "+hasId)
                if (hasId == true){
                    card_details!!.visibility = View.VISIBLE
                    arrProducts.put(jObject)
                    viewList(arrProducts)
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
            if (arrSaveUpdate.equals("1")){
            //    jObject.put("ExistType","0") // ExistType = 0 Exist ,1 = Not

                var hasId = hasEmployee(arrProducts,"ID_Employee",ID_Employee!!)
                Log.e(TAG,"367122    "+hasId)
                if (hasId == true){
                    card_details!!.visibility = View.VISIBLE

                    Log.e(TAG,"arrProducts  6091  "+arrProducts)
                    arrProducts.remove(arrIndexUpdate!!)
                    Log.e(TAG,"arrProducts  6092  "+arrProducts)
                    arrProducts.put(arrIndexUpdate!!,jObject)
                    Log.e(TAG,"arrProducts  6093  "+arrProducts)
                    viewList(arrProducts)
                    resetData("1")

                    ticketMode = "1"
                    serviceMode  = "1"
                    productMode = "1"
                    listMode = "0"
                    hideViews()
                }
                else{
                    til_Employee!!.setError(" Employee already exists.");
                    til_Employee!!.setErrorIconDrawable(null)
                }

            }

            Log.e(TAG,"  3671221     "+arrProducts)
        }
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
            viewList(arrProducts)
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

    private fun viewList(arrProducts: JSONArray) {

        val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
        recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterService = ServiceAssignListAdapter(this@ServiceAssignActivity, arrProducts)
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
            date_Picker1.minDate = System.currentTimeMillis()
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
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                tie_VisitTime!!.setText(output)
                val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)
                strVisitTime = inputDateFormat.format(date)
//                strVisitTime = input

//                val sdfTime = SimpleDateFormat("h:mm a")
//                val outputTimeFormat = SimpleDateFormat("HH:mm:ss")
//                var fromT: Date? = null
//                fromT = sdfTime.parse(output)
//                strVisitTime = outputTimeFormat.format(fromT)


//                if (timeMode == 0){
//                    tie_VisitTime!!.setText(output)
//                }
//                if (timeMode == 1){
//                    tie_ToTime!!.setText(output)
//                }


//                if (fromToTime == 0){
//                    tie_Comp_FromTime!!.setText(output)
//                }
//                if (fromToTime == 1){
//                    tie_Comp_ToTime!!.setText(output)
//                }



//                val strTime = String.format(
//                    "%02d:%02d %s", if (hr == 0) 12 else hr,
//                    min, if (hr < 12) "AM" else "PM"
//                )
//
//                ettime!!.setText(strTime)


            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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

                                            departmentPopup(departmentArrayList)

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
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))

            ID_Employee = ""
            tie_Employee!!.setText("")


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
//            arrProducts.remove(position)
//            adapterService!!.notifyItemRemoved(position)
                arrSaveUpdate = "1"
                arrIndexUpdate = position
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


                ID_Department = jsonObject.getString("DepartmentID")
                ID_Employee = jsonObject.getString("ID_Employee")
                ID_Role= jsonObject.getString("ID_CSAEmployeeType")

                tie_Department!!.setText(""+jsonObject.getString("Department"))
                tie_Employee!!.setText(""+jsonObject.getString("Employee"))
                tie_Role!!.setText(""+jsonObject.getString("EmployeeType"))

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

//        strVisitDate = tie_VisitDate!!.text.toString()
//        strVisitTime = tie_VisitTime!!.text.toString()
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

            serviceassignConfirm(arrAssignees.toString())

//            serUpdateCount = 0
//            serviceassignUpdate(arrAssignees.toString())
        }



    }

    private fun serviceassignConfirm(strAssignees: String) {

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
                serviceassignUpdate(strAssignees)
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

    private fun serviceassignUpdate(strAssignees: String) {
        var ReqMode = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignViewModel.getServiceAssign(this,ReqMode,ID_CustomerServiceRegister!!,strAssignees!!,strVisitDate!!,strVisitTime!!,ID_Priority!!,strRemark!!)!!.observe(
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