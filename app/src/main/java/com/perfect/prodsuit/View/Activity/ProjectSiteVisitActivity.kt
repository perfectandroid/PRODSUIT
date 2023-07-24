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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.FullLenghRecyclertview
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ModelProjectEmpDetails
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.DepartmentViewModel
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.ServiceRoleViewModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProjectSiteVisitActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectSiteVisitActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var tv_SiteVisit: TextView? = null
    private var tv_LeadInfo: TextView? = null
    private var tv_History: TextView? = null

    private var tv_LeadClick: TextView? = null
    private var tv_EmployeeClick: TextView? = null
    private var tv_MeasurementClick: TextView? = null

    private var ll_LeadClick: LinearLayout? = null
    private var ll_EmployeeClick: LinearLayout? = null
    private var ll_MeasurementClick: LinearLayout? = null

    private var tie_LeadNo: TextInputEditText? = null
    private var tie_VisitDate: TextInputEditText? = null
    private var tie_VisitTime: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_EmployeeType: TextInputEditText? = null

    private var tie_WorkType: TextInputEditText? = null
    private var tie_MeasurementType: TextInputEditText? = null
    private var tie_Unit: TextInputEditText? = null


    private var til_LeadNo: TextInputLayout? = null
    private var til_VisitDate: TextInputLayout? = null
    private var til_VisitTime: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_EmployeeType: TextInputLayout? = null

    private var til_WorkType: TextInputLayout? = null
    private var til_MeasurementType: TextInputLayout? = null
    private var til_Unit: TextInputLayout? = null

    private var img_EmpAdd: ImageView? = null
    private var img_EmpRefresh: ImageView? = null

    private var recyEmpDetails: FullLenghRecyclertview? = null


    var showLead = 1
    var showEmployee = 0
    var showMeasurement = 0

    var showSiteVisit = 1
    var showLeadInfo = 0
    var showHistory = 0

    var depCount = 0
    var empCount = 0
    var empTypeCount = 0

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

    lateinit var serviceRoleViewModel: ServiceRoleViewModel
    lateinit var serviceRoleArrayList : JSONArray
    lateinit var serviceRoleSort : JSONArray
    private var dialogServiceRole : Dialog? = null
    var recyServiceRole: RecyclerView? = null


    var ID_Department : String?= ""
    var ID_Employee : String?= ""
    var ID_EmployeeType : String?= ""

    val modelProjectEmpDetails = ArrayList<ModelProjectEmpDetails>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_site_visit)

        context = this@ProjectSiteVisitActivity
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceRoleViewModel = ViewModelProvider(this).get(ServiceRoleViewModel::class.java)

        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_SiteVisit = findViewById(R.id.tv_SiteVisit)
        tv_LeadInfo = findViewById(R.id.tv_LeadInfo)
        tv_History = findViewById(R.id.tv_History)

        tv_LeadClick = findViewById(R.id.tv_LeadClick)
        tv_EmployeeClick = findViewById(R.id.tv_EmployeeClick)
        tv_MeasurementClick = findViewById(R.id.tv_MeasurementClick)

        ll_LeadClick = findViewById(R.id.ll_LeadClick)
        ll_EmployeeClick = findViewById(R.id.ll_EmployeeClick)
        ll_MeasurementClick = findViewById(R.id.ll_MeasurementClick)

        tie_LeadNo = findViewById(R.id.tie_LeadNo)
        tie_VisitDate = findViewById(R.id.tie_VisitDate)
        tie_VisitTime = findViewById(R.id.tie_VisitTime)
        tie_Department = findViewById(R.id.tie_Department)
        tie_Employee = findViewById(R.id.tie_Employee)
        tie_EmployeeType = findViewById(R.id.tie_EmployeeType)
        tie_WorkType = findViewById(R.id.tie_WorkType)
        tie_MeasurementType = findViewById(R.id.tie_MeasurementType)
        tie_Unit = findViewById(R.id.tie_Unit)

        img_EmpAdd = findViewById(R.id.img_EmpAdd)
        img_EmpRefresh = findViewById(R.id.img_EmpRefresh)

        recyEmpDetails = findViewById(R.id.recyEmpDetails)


        tv_SiteVisit!!.setOnClickListener(this)
        tv_LeadInfo!!.setOnClickListener(this)
        tv_History!!.setOnClickListener(this)

        tv_LeadClick!!.setOnClickListener(this)
        tv_EmployeeClick!!.setOnClickListener(this)
        tv_MeasurementClick!!.setOnClickListener(this)

        tie_LeadNo!!.setOnClickListener(this)
        tie_VisitDate!!.setOnClickListener(this)
        tie_VisitTime!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_EmployeeType!!.setOnClickListener(this)
        tie_WorkType!!.setOnClickListener(this)
        tie_MeasurementType!!.setOnClickListener(this)
        tie_Unit!!.setOnClickListener(this)

        img_EmpAdd!!.setOnClickListener(this)
        img_EmpRefresh!!.setOnClickListener(this)

        hideShowTab()
        expandTab()
      //  setIconinDrawable()
//        getCustomer()

    }

    private fun customerAccessDetails() {
        try {
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)

            val client = OkHttpClient.Builder()
                .sslSocketFactory(Config.getSSLSocketFactory(context))
                .hostnameVerifier(Config.getHostnameVerifier())
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URLSP.getString("BASE_URL", null))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            val apiService = retrofit.create(ApiInterface::class.java!!)
            val requestObject1 = JSONObject()
            try {

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39,0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)

                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val currentDate = sdf.format(Date())

                val newDate: Date = sdf.parse(currentDate)
                Log.e(TAG,"newDate  196  "+newDate)
                val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
                val sdfTime1 = SimpleDateFormat("hh:mm:ss")

                var curDate = sdfDate1.format(newDate)
                var curTime = sdfTime1.format(newDate)

//                {
//                    "BankKey": "",
//                    "Token": "9C19C49E-B5DE-4E55-8F09-546CBEA324F8",
//                    "Phone": "6282902294",
//                    "FK_Company": "1",
//                    "RequestMode":"0"// 0 => OTP request , 1 => MPIN request
//                }

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(""))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("Phone", ProdsuitApplication.encryptStart("6282902294"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("RequestMode", ProdsuitApplication.encryptStart("0"))

                Log.e(TAG,"2488888  requestObject1    "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getCustomerAccessDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        Log.e(TAG,"2488888 response  "+response.body())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        //  stopSelf()
                        Log.e(TAG,"2488888  Exception "+e.toString())
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_right);
            }

            R.id.tv_SiteVisit->{
                showSiteVisit = 1
                showLeadInfo = 0
                showHistory = 0
                hideShowTab()
            }

            R.id.tv_LeadInfo->{
                showSiteVisit = 0
                showLeadInfo = 1
                showHistory = 0
                hideShowTab()
            }

            R.id.tv_History->{
                showSiteVisit = 0
                showLeadInfo = 0
                showHistory = 1
                hideShowTab()
            }

            R.id.tv_LeadClick->{
                showLead = 1
                showEmployee = 0
                 showMeasurement = 0
                expandTab()
            }
            R.id.tv_EmployeeClick->{
                showLead = 0
                showEmployee = 1
                showMeasurement = 0
                expandTab()
            }
            R.id.tv_MeasurementClick->{
                showLead = 0
                showEmployee = 0
                showMeasurement = 1
                expandTab()
            }

            R.id.tie_Department->{
                Config.disableClick(v)
                depCount = 0
                getDepartment()
            }

            R.id.tie_Employee->{
                
                if (!ID_Department.equals("")){
                    Config.disableClick(v)
                    empCount = 0
                    getEmployee()
                }else{
                    Config.snackBars(context,v,"Select Department")
                }
               
            }
            R.id.tie_EmployeeType->{

                if (!ID_Employee.equals("")){
                    Config.disableClick(v)
                    empTypeCount = 0
                    getServiceRoles()
                }else{
                    Config.snackBars(context,v,"Select Employee")
                }

            }

            R.id.img_EmpAdd->{
                Config.disableClick(v)
                validateEmpAdd(v)

            }

            R.id.img_EmpRefresh->{


            }

        }
    }

    private fun validateEmpAdd(v :  View) {

        if (ID_Department.equals("")){
            Config.snackBars(context,v,"Select Department")
        }
        else if (ID_Employee.equals("")){
            Config.snackBars(context,v,"Select Employee")
        }
        else if (ID_EmployeeType.equals("")){
            Config.snackBars(context,v,"Select Employee Type")
        }
        else{

            var hasId =  hasIDempOrDep(modelProjectEmpDetails!!,ID_Department!!,ID_Employee!!)
            if (hasId){
                modelProjectEmpDetails.add(ModelProjectEmpDetails(ID_Department!!,tie_Department!!.text.toString(),ID_Employee!!,tie_Employee!!.text.toString(),ID_EmployeeType!!,tie_EmployeeType!!.text.toString()))

                if (modelProjectEmpDetails.size>0){
                    val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
                    recyEmpDetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                    val adapter1 = ProjectEmpDetailAdapter(this@ProjectSiteVisitActivity,modelProjectEmpDetails)
                    recyEmpDetails!!.adapter = adapter1
                    adapter1.setClickListener(this@ProjectSiteVisitActivity)
                   // adapter1.setClickListener(this@ProjectSiteVisitActivity)
                }
            }
            else{
                Config.snackBars(context,v,"Duplicate entry found")
            }

        }
    }

    fun hasIDempOrDep(modelProjectEmpDetails: ArrayList<ModelProjectEmpDetails>, ID_Department: String, ID_Employee: String): Boolean {
        for (i in 0 until modelProjectEmpDetails.size) {  // iterate through the JsonArray
            if (modelProjectEmpDetails.get(i).ID_Department == ID_Department && modelProjectEmpDetails.get(i).ID_Employee == ID_Employee) return false
        }
        return true
    }


    private fun  hideShowTab() {

        tv_SiteVisit!!.setBackgroundResource(R.drawable.shape_bottom_shadow_trans)
        tv_LeadInfo!!.setBackgroundResource(R.drawable.shape_bottom_shadow_trans)
        tv_History!!.setBackgroundResource(R.drawable.shape_bottom_shadow_trans)

        if (showSiteVisit == 1){
            tv_SiteVisit!!.setBackgroundResource(R.drawable.shape_bottom_shadow)
        }

        if (showLeadInfo == 1){
            tv_LeadInfo!!.setBackgroundResource(R.drawable.shape_bottom_shadow)
        }

        if (showHistory == 1){
            tv_History!!.setBackgroundResource(R.drawable.shape_bottom_shadow)
        }

    }


    private fun expandTab() {
        ll_LeadClick!!.visibility = View.GONE
        ll_EmployeeClick!!.visibility = View.GONE
        ll_MeasurementClick!!.visibility = View.GONE

        if (showLead == 1){
            ll_LeadClick!!.visibility = View.VISIBLE
        }
        if (showEmployee == 1){
            ll_EmployeeClick!!.visibility = View.VISIBLE
        }
        if (showMeasurement == 1){
            ll_MeasurementClick!!.visibility = View.VISIBLE
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

                                if (depCount == 0){
                                    depCount++

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
                                            this@ProjectSiteVisitActivity,
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

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@ProjectSiteVisitActivity, departmentSort)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

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
                    val adapter = DepartmentAdapter(this@ProjectSiteVisitActivity, departmentSort)
                    recyDeaprtment!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
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

                                if (empCount == 0){
                                    empCount++

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
                                            this@ProjectSiteVisitActivity,
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


            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@ProjectSiteVisitActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

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
                    val adapter = EmployeeAdapter(this@ProjectSiteVisitActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
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

                                if (empTypeCount == 0){
                                    empTypeCount++

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
                                            this@ProjectSiteVisitActivity,
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
            var tv_heading = dialogServiceRole!! .findViewById(R.id.tv_heading) as TextView
            recyServiceRole = dialogServiceRole!! .findViewById(R.id.recyServiceRole) as RecyclerView
            //  val etsearch = dialogServiceRole!! .findViewById(R.id.etsearch) as EditText

            tv_heading!!.text = "Employee Type"

            serviceRoleSort = JSONArray()
            for (k in 0 until serviceRoleArrayList.length()) {
                val jsonObject = serviceRoleArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                serviceRoleSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyServiceRole!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, serviceRoleArrayList)
            val adapter = ServiceRoleAdapter(this@ProjectSiteVisitActivity, serviceRoleSort)
            recyServiceRole!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_right);
    }


    override fun onClick(position: Int, data: String) {

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
            val jsonObject = departmentSort.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))

            ID_Employee = ""
            tie_Employee!!.setText("")

            ID_EmployeeType = ""
            tie_EmployeeType!!.setText("")


        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))

            ID_EmployeeType = ""
            tie_EmployeeType!!.setText("")
            
        }

        if (data.equals("serviceRole")){
            dialogServiceRole!!.dismiss()
            val jsonObject = serviceRoleSort.getJSONObject(position)
            Log.e(TAG,"ID_EmployeeType   "+jsonObject.getString("ID_Role"))
            ID_EmployeeType = jsonObject.getString("ID_Role")
            tie_EmployeeType!!.setText(jsonObject.getString("RoleName"))


        }
    }
}