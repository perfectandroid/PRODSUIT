package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DepartmentAdapter
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.ServiceAssignAdapter
import com.perfect.prodsuit.View.Adapter.ServicePriorityAdapter
import com.perfect.prodsuit.Viewmodel.DepartmentViewModel
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.ServiceAssignViewModel
import com.perfect.prodsuit.Viewmodel.ServicePriorityViewModel
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

    internal var recyServiceAssign: RecyclerView? = null

    lateinit var serviceAssignViewModel: ServiceAssignViewModel
    lateinit var serviceAssignArrayList : JSONArray


    private var tv_TicketClick: TextView? = null
    private var tv_ServiceClick: TextView? = null
    private var tv_ProductClick: TextView? = null

    private var lnrHead_Ticket: LinearLayout? = null
    private var lnrHead_Service: LinearLayout? = null
    private var lnrHead_Product: LinearLayout? = null


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

    private var tie_VisitDate: TextInputEditText? = null
    private var tie_VisitTime: TextInputEditText? = null
    private var tie_Priority: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_Role: TextInputEditText? = null

    private var btnClear: Button? = null
    private var btnAdd: Button? = null

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

    var dateMode = 0
    var timeMode = 0
    var priorityMode = 0
    var department = 0
    var employee = 0

    var ID_Priority : String?= ""
    var ID_Department : String?= ""
    var ID_Employee : String?= ""

    var strVisitDate : String?= ""
    var strVisitTime : String?= ""

    var ticketMode: String? = "0"
    var serviceMode: String? = "1"
    var productMode: String? = "1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign)
        context = this@ServiceAssignActivity

        servicePriorityViewModel = ViewModelProvider(this).get(ServicePriorityViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        serviceAssignViewModel = ViewModelProvider(this).get(ServiceAssignViewModel::class.java)

        setRegViews()

        ticketMode = "0"
        hideViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

       // recyServiceAssign = findViewById<RecyclerView>(R.id.recyServiceAssign)

        tv_TicketClick = findViewById<TextView>(R.id.tv_TicketClick)
        tv_ServiceClick = findViewById<TextView>(R.id.tv_ServiceClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)

        tv_TicketClick!!.setOnClickListener(this)
        tv_ServiceClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)

        lnrHead_Ticket = findViewById<LinearLayout>(R.id.lnrHead_Ticket)
        lnrHead_Service = findViewById<LinearLayout>(R.id.lnrHead_Service)
        lnrHead_Product = findViewById<LinearLayout>(R.id.lnrHead_Product)

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


        tie_VisitDate = findViewById<TextInputEditText>(R.id.tie_VisitDate)
        tie_VisitTime = findViewById<TextInputEditText>(R.id.tie_VisitTime)
        tie_Priority = findViewById<TextInputEditText>(R.id.tie_Priority)
        tie_Remarks = findViewById<TextInputEditText>(R.id.tie_Remarks)
        tie_Department = findViewById<TextInputEditText>(R.id.tie_Department)
        tie_Employee = findViewById<TextInputEditText>(R.id.tie_Employee)
        tie_Role = findViewById<TextInputEditText>(R.id.tie_Role)

        tie_VisitDate!!.setOnClickListener(this)
        tie_VisitTime!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_Role!!.setOnClickListener(this)

        btnClear = findViewById<Button>(R.id.btnClear)
        btnAdd = findViewById<Button>(R.id.btnAdd)

        btnClear!!.setOnClickListener(this)
        btnAdd!!.setOnClickListener(this)

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
                hideViews()
            }

            R.id.tv_ServiceClick->{
                ticketMode = "1"
                serviceMode  = "0"
                productMode = "1"
                hideViews()
            }

            R.id.tv_ProductClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
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

            }

            R.id.btnAdd->{

                validations()
            }


        }
    }



    private fun validations() {
        TODO("Not yet implemented")
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
             //   strVisitDate = ""+strYear+"-"+strMonth+"-"+strDay
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

    private fun hideViews() {
        lnrHead_Ticket!!.visibility = View.VISIBLE
        lnrHead_Service!!.visibility = View.VISIBLE
        lnrHead_Product!!.visibility = View.VISIBLE

        if (ticketMode.equals("1")) {
            lnrHead_Ticket!!.visibility = View.GONE
        }
        if (serviceMode.equals("1")) {
            lnrHead_Service!!.visibility = View.GONE
        }
        if (productMode.equals("1")) {
            lnrHead_Product!!.visibility = View.GONE
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
    }
}