package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.Model.ModelMesurementDetails
import com.perfect.prodsuit.Model.ModelMoreServices
import com.perfect.prodsuit.Model.ModelProjectEmpDetails
import com.perfect.prodsuit.Model.OtherChargesMainModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    private var tvv_choosefile: TextView? = null

    private var ll_LeadClick: LinearLayout? = null
    private var ll_EmployeeClick: LinearLayout? = null
    private var ll_MeasurementClick: LinearLayout? = null
    private var ll_measureShow: LinearLayout? = null

    private var tie_LeadNo: TextInputEditText? = null
    private var tie_VisitDate: TextInputEditText? = null
    private var tie_VisitTime: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_EmployeeType: TextInputEditText? = null
    private var tie_InspectionNotes1: TextInputEditText? = null
    private var tie_InspectionNotes2: TextInputEditText? = null
    private var tie_CustomerNotes: TextInputEditText? = null
    private var tie_InspectionCharge: TextInputEditText? = null
    private var tie_Value: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null
    private var tie_Othercharges: TextInputEditText? = null
    private var tie_Remark: TextInputEditText? = null
    private var tie_Remark11: TextInputEditText? = null
    private var tie_Imagemode: TextInputEditText? = null
    private var imv_name: TextView? = null

    private var tie_WorkType: TextInputEditText? = null
    private var tie_MeasurementType: TextInputEditText? = null
    private var tie_Unit: TextInputEditText? = null


    private var til_LeadNo: TextInputLayout? = null
    private var til_VisitDate: TextInputLayout? = null
    private var til_InspectionNotes1: TextInputLayout? = null
    private var til_Imagemode: TextInputLayout? = null
    private var til_VisitTime: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_EmployeeType: TextInputLayout? = null
    private var btnSubmit: Button? = null
    private var btnReset: Button? = null

    private var til_WorkType: TextInputLayout? = null
    private var til_MeasurementType: TextInputLayout? = null
    private var til_Value: TextInputLayout? = null
    private var til_Unit: TextInputLayout? = null

    private var img_EmpAdd: ImageView? = null
    private var img_EmpRefresh: ImageView? = null

    private var img_MeasureAdd: ImageView? = null
    private var img_MeasureRefresh: ImageView? = null

    private var recyEmpDetails: FullLenghRecyclertview? = null
    private var recy_mesurment: RecyclerView? = null


    var showLead        = 1
    var showEmployee    = 0
    var showMeasurement = 0

    var showSiteVisit   = 1
    var showLeadInfo    = 0
    var showHistory     = 0

    var depCount        = 0
    var empCount        = 0
    var empTypeCount    = 0
    var unitcount       = 0
    var measurecount    = 0
    var workcount       = 0
    var leadcount       = 0
    var otherchargecount = 0
    var imagemodecount   = 0
    var strID_FIELD   = ""
    var WorkTypeID   = ""
    var ID_MeasurementUnit   = ""
    var ID_Unit   = ""
    var strUnit   = ""

    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var imagemodeViewModel: ImageModeViewModel
    lateinit var worktypeViewModel: WorkTypeViewModel
    lateinit var measurementViewModel: MeasurementViewModel
    lateinit var leadnoViewModel: LeadNoViewModel
    lateinit var otherchargesViewModel: OtherChargesViewModel
    lateinit var departmentArrayList : JSONArray
    lateinit var imagemodeArrayList : JSONArray
    lateinit var leadnoArrayList : JSONArray
    lateinit var otherChargeArrayList : JSONArray
    lateinit var worktypeArrayList : JSONArray
    lateinit var measurementDetailsArrayList : JSONArray
    lateinit var departmentSort : JSONArray
    lateinit var leadnoSort : JSONArray
    lateinit var otherchargeSort : JSONArray
    lateinit var worktypeSort : JSONArray
    lateinit var measurementSort : JSONArray
    lateinit var unitSort : JSONArray
    lateinit var imagemodeSort : JSONArray
    private var dialogDepartment : Dialog? = null
    private var dialogLeadNo : Dialog? = null
    private var dialogOthercharge : Dialog? = null
    private var dialogWorkType : Dialog? = null
    private var dialogMeasurement : Dialog? = null
    private var dialogImagemode : Dialog? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var image = ""
    private var destination: File? = null
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private val PERMISSION_REQUEST_CODE = 200
    var recyDeaprtment: RecyclerView? = null
    var recyLeadNo: RecyclerView? = null
    var recyOtherCharge: RecyclerView? = null
    var recyWorkType: RecyclerView? = null
    var recyMeasurementType: RecyclerView? = null
    var recyUnit: RecyclerView? = null


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

    lateinit var unitViewModel: UnitViewModel
    lateinit var unitArrayList : JSONArray
    private var dialogUnit : Dialog? = null



    var ID_Department : String?= ""
    var ID_Employee : String?= ""
    var ID_EmployeeType : String?= ""
    var strVisitdate : String? = ""
    var date_Picker1: DatePicker? = null
    var date_Picker2: DatePicker? = null
    var updateedit: String? = "0"
    var modelPosition: Int? = 0
    var visitTime = ""
    var visitDate = ""
    var strLeadno = ""
    var strInspectionNote1 = ""
    var strInspectionNote2 = ""
    var strCustomerNotes   = ""

    val modelProjectEmpDetails = ArrayList<ModelProjectEmpDetails>()

    var jsonObj: JSONObject? = null
    val modelMesurementDetails = ArrayList<ModelMesurementDetails>()
    var measurementShowAdapter  : MeasurementShowAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_site_visit)

        context = this@ProjectSiteVisitActivity
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceRoleViewModel = ViewModelProvider(this).get(ServiceRoleViewModel::class.java)
        leadnoViewModel = ViewModelProvider(this).get(LeadNoViewModel::class.java)
        worktypeViewModel = ViewModelProvider(this).get(WorkTypeViewModel::class.java)
        measurementViewModel = ViewModelProvider(this).get(MeasurementViewModel::class.java)
        imagemodeViewModel = ViewModelProvider(this).get(ImageModeViewModel::class.java)
        otherchargesViewModel = ViewModelProvider(this).get(OtherChargesViewModel::class.java)
        unitViewModel = ViewModelProvider(this).get(UnitViewModel::class.java)

        setRegViews()

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        if (jsonObject.equals("")){
            Log.e(TAG,"23111   ")
            tie_LeadNo!!.isEnabled = true
        }else{
            Log.e(TAG,"23112   ")
            jsonObj = JSONObject(jsonObject)
            tie_LeadNo!!.setText(""+jsonObj!!.getString("Lead_No"))
            tie_LeadNo!!.isEnabled = false
        }

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
        ll_measureShow = findViewById(R.id.ll_measureShow)

        tie_LeadNo = findViewById(R.id.tie_LeadNo)
        tie_VisitDate = findViewById(R.id.tie_VisitDate)
        tie_VisitTime = findViewById(R.id.tie_VisitTime)
        tie_Department = findViewById(R.id.tie_Department)
        tie_Employee = findViewById(R.id.tie_Employee)
        tie_EmployeeType = findViewById(R.id.tie_EmployeeType)
        tie_WorkType = findViewById(R.id.tie_WorkType)
        tie_MeasurementType = findViewById(R.id.tie_MeasurementType)
        tie_Unit = findViewById(R.id.tie_Unit)
        tvv_choosefile = findViewById(R.id.tvv_choosefile)

        img_EmpAdd = findViewById(R.id.img_EmpAdd)
        img_EmpRefresh = findViewById(R.id.img_EmpRefresh)

        img_MeasureAdd = findViewById(R.id.img_MeasureAdd)
        img_MeasureRefresh = findViewById(R.id.img_MeasureRefresh)

        recyEmpDetails        = findViewById(R.id.recyEmpDetails)
        recy_mesurment        = findViewById(R.id.recy_mesurment)
        til_LeadNo            = findViewById<TextInputLayout>(R.id.til_LeadNo)
        til_VisitDate         = findViewById<TextInputLayout>(R.id.til_VisitDate)
        til_InspectionNotes1  = findViewById<TextInputLayout>(R.id.til_InspectionNotes1)
        til_Department        = findViewById<TextInputLayout>(R.id.til_Department)
        til_Employee          = findViewById<TextInputLayout>(R.id.til_Employee)
        til_EmployeeType      = findViewById<TextInputLayout>(R.id.til_EmployeeType)
        til_Imagemode = findViewById<TextInputLayout>(R.id.til_Imagemode)
        tie_InspectionNotes1 = findViewById(R.id.tie_InspectionNotes1)
        tie_InspectionNotes2 = findViewById(R.id.tie_InspectionNotes2)
        tie_Value = findViewById(R.id.tie_Value)
        tie_Remarks = findViewById(R.id.tie_Remarks)
        tie_Othercharges = findViewById(R.id.tie_Othercharges)
        tie_Remark = findViewById(R.id.tie_Remark)
        tie_Remark11 = findViewById(R.id.tie_Remark11)
        tie_Imagemode = findViewById(R.id.tie_Imagemode)
        imv_name = findViewById(R.id.imv_name)
        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)
        tie_CustomerNotes = findViewById(R.id.tie_CustomerNotes)
        tie_InspectionCharge = findViewById(R.id.tie_InspectionCharge)

        til_WorkType            = findViewById<TextInputLayout>(R.id.til_WorkType)
        til_MeasurementType            = findViewById<TextInputLayout>(R.id.til_MeasurementType)
        til_Value            = findViewById<TextInputLayout>(R.id.til_Value)
        til_Unit            = findViewById<TextInputLayout>(R.id.til_Unit)


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
        til_Imagemode!!.setOnClickListener(this)
        tvv_choosefile!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        tie_Othercharges!!.setOnClickListener(this)
        tie_Imagemode!!.setOnClickListener(this)

        img_EmpAdd!!.setOnClickListener(this)
        img_EmpRefresh!!.setOnClickListener(this)

        img_MeasureAdd!!.setOnClickListener(this)
        img_MeasureRefresh!!.setOnClickListener(this)

        til_LeadNo!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_VisitDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_InspectionNotes1!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Department!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_EmployeeType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)


        til_WorkType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_MeasurementType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Value!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Unit!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        hideShowTab()
        expandTab()

        DecimelFormatters.setDecimelPlace(tie_InspectionCharge!!)
        DecimelFormatters.setDecimelPlace(tie_Value!!)

        onTextChangedValues()

        getCurrentDate()
      //  setIconinDrawable()
//        getCustomer()

    }

    private fun onTextChangedValues() {
        tie_LeadNo!!.addTextChangedListener(watcher)
        tie_VisitDate!!.addTextChangedListener(watcher)
        tie_InspectionNotes1!!.addTextChangedListener(watcher)

        tie_Department!!.addTextChangedListener(watcher)
        tie_Employee!!.addTextChangedListener(watcher)
        tie_EmployeeType!!.addTextChangedListener(watcher)

        tie_WorkType!!.addTextChangedListener(watcher)
        tie_MeasurementType!!.addTextChangedListener(watcher)
        tie_Value!!.addTextChangedListener(watcher)
        tie_Unit!!.addTextChangedListener(watcher)

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
                editable === tie_LeadNo!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_LeadNo!!.text.toString().equals("")){
                        til_LeadNo!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_LeadNo!!.isErrorEnabled = false
                        til_LeadNo!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_VisitDate!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_VisitDate!!.text.toString().equals("")){
                        til_VisitDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_VisitDate!!.isErrorEnabled = false
                        til_VisitDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_InspectionNotes1!!.editableText -> {
                    if (tie_InspectionNotes1!!.text.toString().equals("")){
                        til_InspectionNotes1!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_InspectionNotes1!!.isErrorEnabled = false
                        til_InspectionNotes1!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_Department!!.editableText -> {
                    if (tie_Department!!.text.toString().equals("")){
                        til_Department!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Department!!.isErrorEnabled = false
                        til_Department!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_Employee!!.editableText -> {
                    if (tie_Employee!!.text.toString().equals("")){
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Employee!!.isErrorEnabled = false
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_EmployeeType!!.editableText -> {
                    if (tie_EmployeeType!!.text.toString().equals("")){
                        til_EmployeeType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_EmployeeType!!.isErrorEnabled = false
                        til_EmployeeType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_WorkType!!.editableText -> {
                    if (tie_WorkType!!.text.toString().equals("")){
                        til_WorkType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_WorkType!!.isErrorEnabled = false
                        til_WorkType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_MeasurementType!!.editableText -> {
                    if (tie_MeasurementType!!.text.toString().equals("")){
                        til_MeasurementType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_MeasurementType!!.isErrorEnabled = false
                        til_MeasurementType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_Value!!.editableText -> {
                    if (tie_Value!!.text.toString().equals("")){
                        til_Value!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Value!!.isErrorEnabled = false
                        til_Value!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_Unit!!.editableText -> {
                    if (tie_Unit!!.text.toString().equals("")){
                        til_Unit!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Unit!!.isErrorEnabled = false
                        til_Unit!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

            }

        }
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


    @SuppressLint("SuspiciousIndentation")
    private fun openBottomSheet() {


        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

//            date_Picker1.setMinDate(System.currentTimeMillis());
//            date_Picker1.minDate = System.currentTimeMillis()


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
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }



                tie_VisitDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                strVisitdate = strYear + "-" + strMonth + "-" + strDay



            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
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



        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = "" + hr + ":" + min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                tie_VisitTime!!.setText(output)

                visitTime = tie_VisitTime.toString()
                Log.e(TAG, "PickDeliveryTime   " + output)
            } catch (e: Exception) {
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
            R.id.tie_VisitDate->{
                openBottomSheet()
            }
            R.id.img_EmpRefresh->{
                updateedit = "0"
                tie_Department!!.setText("")
                tie_Employee!!.setText("")
                tie_EmployeeType!!.setText("")
            }
            R.id.tie_VisitTime->{
              openBottomTime()
            }

            R.id.tv_LeadClick->{
                showLead = 1
                showEmployee = 0
                 showMeasurement = 0
                expandTab()
            }
            R.id.tie_LeadNo->{

                leadcount = 0
                getLeadNo()
//                showLead = 1
//                showEmployee = 0
//                 showMeasurement = 0
//                expandTab()
            }
            R.id.tie_WorkType->{
                workcount = 0
                getWorkType()

            }
            R.id.tie_MeasurementType->{
                measurecount = 0
                getMeasurementDetails()

            }
            R.id.tie_Unit->{
                unitcount = 0
                getUnit()
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
            R.id.tie_Othercharges->{
//                Toast.makeText(applicationContext, "test 1", Toast.LENGTH_LONG).show()
                otherchargecount = 0
                getOthercharges()

            }

            R.id.img_EmpRefresh->{


            }

            R.id.img_MeasureAdd->{

                Config.Utils.hideSoftKeyBoard(context,v)
                Config.disableClick(v)
                validateMeasurment(v)

            }

            R.id.img_MeasureRefresh->{

                updateedit = "0"
                WorkTypeID   = ""
                ID_MeasurementUnit     = ""
                ID_Unit =  ""

                tie_WorkType!!.setText("")
                tie_MeasurementType!!.setText("")
                tie_Value!!.setText("")
                tie_Unit!!.setText("")
                tie_Remarks!!.setText("")

            }




            R.id.tie_Imagemode->{

//                Toast.makeText(applicationContext, "test 2", Toast.LENGTH_LONG).show()
                imagemodecount = 0
                getImageMode()
            }
            R.id.tvv_choosefile->{
                try {
                    Config.Utils.hideSoftKeyBoard(this@ProjectSiteVisitActivity, v)
                    showPictureDialog()
                } catch (e: java.lang.Exception) {
                    if (checkCamera()) {
                    } else {
                        requestPermission()
                    }
                }
            }
            R.id.btnSubmit->{
                siteVisitValidation(v)
            }
            R.id.btnReset->{
                clearData()
            }
        }
    }



    private fun clearData(){

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdfTime1 = SimpleDateFormat("hh:mm aa")
        val currentDate = sdf.format(Date())
        val time = sdfTime1.format(Date())

        Log.e(TAG, "newtime  885  " + time)

        ID_Employee          = ""
        ID_Employee          = ""
        ID_EmployeeType      = ""
        strInspectionNote1   = ""
        strLeadno            = ""
        strVisitdate         = ""
        visitDate            = ""
        visitTime            = ""

        tie_LeadNo!!.setText("")
        tie_VisitDate!!.setText(currentDate)
        tie_VisitTime!!.setText(time)
        tie_InspectionNotes1!!.setText("")
        tie_InspectionNotes2!!.setText("")
        tie_CustomerNotes!!.setText("")
        tie_InspectionCharge!!.setText("")

        tie_Department!!.setText("")
        tie_Employee!!.setText("")
        tie_EmployeeType!!.setText("")

        tie_WorkType!!.setText("")
        tie_MeasurementType!!.setText("")
        tie_Value!!.setText("")
        tie_Unit!!.setText("")
        tie_Remarks!!.setText("")
        tie_Othercharges!!.setText("")
        tie_Remark11!!.setText("")
        tie_Imagemode!!.setText("")
        imv_name!!.setText("")

    }

    private fun siteVisitValidation(v : View){

        strLeadno          = tie_LeadNo!!.text.toString()
        strVisitdate       = tie_VisitTime!!.text.toString()
        visitTime          = tie_VisitTime!!.text.toString()
        strInspectionNote1 = tie_InspectionNotes1!!.text.toString()
        strInspectionNote2 = tie_InspectionNotes2!!.text.toString()
        strCustomerNotes   = tie_CustomerNotes!!.text.toString()

        if (strLeadno!!.equals("")){
            Config.snackBars(context, v, "Select LeadNo")

        }else if (strVisitdate!!.equals("")){
            Config.snackBars(context, v, "Select Visitdate")

        }else if (strInspectionNote1!!.equals("")){
            Config.snackBars(context, v, "Please Enter InspectonNote 1")

        }else{

        }

    }


    private fun getCurrentDate() {


        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG, "DATE TIME  196  " + currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG, "newDate  196  " + newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            tie_VisitDate!!.setText("" + sdfDate1.format(newDate))
            tie_VisitTime!!.setText("" + sdfTime1.format(newDate))

            visitTime = "" + sdfTime1.format(newDate)
            visitDate = "" + sdfDate1.format(newDate)

            Log.e(TAG, "Exception 456  " + visitTime + visitDate)

        } catch (e: Exception) {

            Log.e(TAG, "Exception 456  " + e.toString())
        }
    }

    private fun validateMeasurment(v: View) {

        strUnit = tie_Value!!.text.toString()
        var WorkType = tie_WorkType!!.text.toString()
        var MeasurementType = tie_MeasurementType!!.text.toString()
        var Value = tie_Value!!.text.toString()
        var Unit = tie_Unit!!.text.toString()
        var Remarks = tie_Remarks!!.text.toString()
        if (strUnit.equals("") || strUnit.equals(".")){
            strUnit = "0.00"
        }

        if (WorkTypeID.equals("")){
            Config.snackBars(context,v,"Select Work Type")
        }
        else if (ID_MeasurementUnit.equals("")){
            Config.snackBars(context,v,"Select Measurement Type")
        }
        else if (strUnit.toFloat() <= 0){
            Config.snackBars(context,v,"Please Enter Measurement Value")
        }
        else if (ID_Unit.equals("")){
            Config.snackBars(context,v,"Select Unit")
        }
        else{

            if (updateedit!!.equals("0")){
                var hasCheck =  hasMeasurement(modelMesurementDetails!!,WorkTypeID,ID_MeasurementUnit)
                if (hasCheck){
                    modelMesurementDetails!!.add(ModelMesurementDetails(WorkTypeID, WorkType,ID_MeasurementUnit,MeasurementType,Value,ID_Unit,Unit,Remarks))

                    updateedit = "0"
                    WorkTypeID   = ""
                    ID_MeasurementUnit     = ""
                    ID_Unit =  ""

                    tie_WorkType!!.setText("")
                    tie_MeasurementType!!.setText("")
                    tie_Value!!.setText("")
                    tie_Unit!!.setText("")
                    tie_Remarks!!.setText("")

                    if (modelMesurementDetails.size> 0){
                        ll_measureShow!!.visibility = View.VISIBLE
                        val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
                        recy_mesurment!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                        measurementShowAdapter = MeasurementShowAdapter(this@ProjectSiteVisitActivity,modelMesurementDetails)
                        recy_mesurment!!.adapter = measurementShowAdapter
                        measurementShowAdapter!!.setClickListener(this@ProjectSiteVisitActivity)
                    }
                }
                else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }
            }
            else if (updateedit!!.equals("1")){

                var hasCheck =  hasMeasurement(modelMesurementDetails!!,WorkTypeID,ID_MeasurementUnit)
                if (hasCheck){


                    updateedit = "0"
                    WorkTypeID   = ""
                    ID_MeasurementUnit     = ""
                    ID_Unit =  ""

                    tie_WorkType!!.setText("")
                    tie_MeasurementType!!.setText("")
                    tie_Value!!.setText("")
                    tie_Unit!!.setText("")
                    tie_Remarks!!.setText("")

                    var empModel = modelMesurementDetails[modelPosition!!]
                    empModel.WorkTypeID = WorkTypeID
                    empModel.WorkType = WorkType
                    empModel.ID_MeasurementUnit = ID_MeasurementUnit
                    empModel.MeasurementUnit = MeasurementType
                    empModel.Value = Value
                    empModel.ID_Unit = ID_Unit
                    empModel.Unit = Unit
                    empModel.Remarks = Remarks

                    measurementShowAdapter!!.notifyItemChanged(modelPosition!!)
                }else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }






            }
//            WorkTypeID, WorkType,ID_MeasurementUnit,MeasurementType,Value,ID_Unit,Unit,Remarks

        }
    }

    private fun hasMeasurement(modelMesurementDetails: ArrayList<ModelMesurementDetails>, WorkTypeID: String, ID_MeasurementUnit: String): Boolean {
        var isChecked = true
        for (i in 0 until modelMesurementDetails.size) {
            if (modelMesurementDetails.get(i).WorkTypeID.equals(WorkTypeID) && modelMesurementDetails.get(i).ID_MeasurementUnit.equals(ID_MeasurementUnit)){
                isChecked = false
            }
        }

        return isChecked
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

            if (updateedit!!.equals("0")){
                var hasId =  hasIDempOrDep(modelProjectEmpDetails!!,ID_Department!!,ID_Employee!!,ID_EmployeeType!!)

                Log.e(TAG,"innnnnnnnnnnnnn  1 "+modelProjectEmpDetails)
                Log.e(TAG,"innnnnnnnnnnnnn  2 "+ID_Department)
                Log.e(TAG,"innnnnnnnnnnnnn  3 "+ID_Employee)

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
                    tie_Department!!.setText("")
                    tie_Employee!!.setText("")
                    tie_EmployeeType!!.setText("")

                    ID_Employee = ""
                    ID_Department = ""
                    ID_EmployeeType = ""
                }
                else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }

//                tie_Department!!.setText("")
//                tie_Employee!!.setText("")
//                tie_EmployeeType!!.setText("")
//                ID_Employee = ""
//                ID_Department = ""
//                ID_EmployeeType = ""
            }

            if (updateedit!!.equals("1")){

                var hasId =  hasIDempOrDep(modelProjectEmpDetails!!,ID_Department!!,ID_Employee!!,ID_EmployeeType!!)
                if (hasId){

                    Log.e(TAG,"innnnnnnnnnnnnn 5  "+ID_Department)
                    Log.e(TAG,"innnnnnnnnnnnnn 6  "+ID_Employee)

                    modelProjectEmpDetails.removeAt(modelPosition!!)
                    modelProjectEmpDetails.add(modelPosition!!,ModelProjectEmpDetails(ID_Department!!,tie_Department!!.text.toString(),ID_Employee!!,tie_Employee!!.text.toString(),ID_EmployeeType!!,tie_EmployeeType!!.text.toString()))

                    if (modelProjectEmpDetails.size>0){
                        val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
                        recyEmpDetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                        val adapter1 = ProjectEmpDetailAdapter(this@ProjectSiteVisitActivity,modelProjectEmpDetails)
                        recyEmpDetails!!.adapter = adapter1
                        adapter1.setClickListener(this@ProjectSiteVisitActivity)
                        // adapter1.setClickListener(this@ProjectSiteVisitActivity)
                        updateedit = "0"
                    }

                    tie_Department!!.setText("")
                    tie_Employee!!.setText("")
                    tie_EmployeeType!!.setText("")

                    ID_Employee = ""
                    ID_Department = ""
                    ID_EmployeeType = ""

                }else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }

            }
//            tie_Department!!.setText("")
//            tie_Employee!!.setText("")
//            tie_EmployeeType!!.setText("")
//            ID_Employee = ""
//            ID_Department = ""
//            ID_EmployeeType = ""
        }
    }

    fun hasIDempOrDep(modelProjectEmpDetails: ArrayList<ModelProjectEmpDetails>, ID_Department: String, ID_Employee: String,ID_EmployeeType: String): Boolean {
        for (i in 0 until modelProjectEmpDetails.size) {  // iterate through the JsonArray
//            if (modelProjectEmpDetails.get(i).ID_Department == ID_Department && modelProjectEmpDetails.get(i).ID_Employee == ID_Employee && modelProjectEmpDetails.get(i).ID_EmployeeType == ID_EmployeeType) return false
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

    private fun getLeadNo() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadnoViewModel.getLeadNo(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (leadcount == 0){
                                    leadcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadList")
                                        leadnoArrayList = jobjt.getJSONArray("LeadListDetails")
                                        if (leadnoArrayList.length()>0){

                                            leadNoPopup(leadnoArrayList)

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

    private fun leadNoPopup(leadnoArrayList: JSONArray) {
        try {

            dialogLeadNo = Dialog(this)
            dialogLeadNo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadNo!! .setContentView(R.layout.lead_list_popup)
            dialogLeadNo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadNo = dialogLeadNo!! .findViewById(R.id.recyLeadNo) as RecyclerView
            val etsearch = dialogLeadNo!! .findViewById(R.id.etsearch) as EditText

            leadnoSort = JSONArray()
            for (k in 0 until leadnoArrayList.length()) {
                val jsonObject = leadnoArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadnoSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyLeadNo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = LeadNoAdapter(this@ProjectSiteVisitActivity, leadnoSort)
            recyLeadNo!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadnoSort = JSONArray()

                    for (k in 0 until leadnoArrayList.length()) {
                        val jsonObject = leadnoArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                leadnoSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"leadnoSort               7103    "+leadnoSort)
                    val adapter = LeadNoAdapter(this@ProjectSiteVisitActivity, leadnoSort)
                    recyLeadNo!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
                }
            })

            dialogLeadNo!!.show()
            dialogLeadNo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getOthercharges() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherchargesViewModel.getOthercharge(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (otherchargecount == 0){
                                    otherchargecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        otherChargeArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (otherChargeArrayList.length()>0){

                                            otherChargesPopup(otherChargeArrayList)

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

    private fun otherChargesPopup(otherChargeArrayList: JSONArray) {
        try {

            dialogOthercharge = Dialog(this)
            dialogOthercharge!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogOthercharge!! .setContentView(R.layout.othercharge_popup)
            dialogOthercharge!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyOtherCharge = dialogOthercharge!! .findViewById(R.id.recyOtherCharge) as RecyclerView
            val etsearch = dialogOthercharge!! .findViewById(R.id.etsearch) as EditText

            otherchargeSort = JSONArray()
            for (k in 0 until otherChargeArrayList.length()) {
                val jsonObject = otherChargeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                otherchargeSort.put(jsonObject)
            }

//            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
//            recyOtherCharge!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = OtherChargeAdapter(this@ProjectSiteVisitActivity, otherchargeSort)
//            recyOtherCharge!!.adapter = adapter
//            adapter.setClickListener(this@ProjectSiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    otherchargeSort = JSONArray()

                    for (k in 0 until otherChargeArrayList.length()) {
                        val jsonObject = otherChargeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                otherchargeSort.put(jsonObject)
                            }

                        }
                    }

//                    Log.e(TAG,"otherchargeSort               7103    "+otherchargeSort)
//                    val adapter = OtherChargeAdapter(this@ProjectSiteVisitActivity, otherchargeSort)
//                    recyOtherCharge!!.adapter = adapter
//                    adapter.setClickListener(this@ProjectSiteVisitActivity)
                }
            })

            dialogOthercharge!!.show()
            dialogOthercharge!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    private fun getWorkType() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                worktypeViewModel.getWorkType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (workcount == 0){
                                    workcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("WorkTypeList")
                                        worktypeArrayList = jobjt.getJSONArray("WorkTypeListDetails")
                                        if (worktypeArrayList.length()>0){

                                            workTypePopup(worktypeArrayList)

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

    private fun workTypePopup(worktypeArrayList: JSONArray) {
        try {

            dialogWorkType = Dialog(this)
            dialogWorkType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogWorkType!! .setContentView(R.layout.worktype_popup)
            dialogWorkType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyWorkType = dialogWorkType!! .findViewById(R.id.recyWorkType) as RecyclerView
            val etsearch = dialogWorkType!! .findViewById(R.id.etsearch) as EditText

            worktypeSort = JSONArray()
            for (k in 0 until worktypeArrayList.length()) {
                val jsonObject = worktypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                worktypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyWorkType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = WorkTypeAdapter(this@ProjectSiteVisitActivity, worktypeSort)
            recyWorkType!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    worktypeSort = JSONArray()

                    for (k in 0 until worktypeArrayList.length()) {
                        val jsonObject = worktypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("WorkType").length) {
                            if (jsonObject.getString("WorkType")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                worktypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"worktypeSort               7103    "+worktypeSort)
                    val adapter = WorkTypeAdapter(this@ProjectSiteVisitActivity, worktypeSort)
                    recyWorkType!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
                }
            })

            dialogWorkType!!.show()
            dialogWorkType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMeasurementDetails() {
         var ReqMode = "5"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                measurementViewModel.getMeasurement(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (measurecount == 0){
                                    measurecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("MeasurementTypeList")
                                        measurementDetailsArrayList = jobjt.getJSONArray("MeasurementTypeListDetails")
                                        if (measurementDetailsArrayList.length()>0){

                                            measurementTypePopup(measurementDetailsArrayList)

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

    private fun measurementTypePopup(worktypeArrayList: JSONArray) {
        try {

            dialogMeasurement = Dialog(this)
            dialogMeasurement!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMeasurement!! .setContentView(R.layout.measurement_type)
            dialogMeasurement!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyMeasurementType = dialogMeasurement!! .findViewById(R.id.recyMeasurementType) as RecyclerView
            val etsearch = dialogMeasurement!! .findViewById(R.id.etsearch) as EditText

            measurementSort = JSONArray()
            for (k in 0 until worktypeArrayList.length()) {
                val jsonObject = worktypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                measurementSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyMeasurementType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = MeasurementAdapter(this@ProjectSiteVisitActivity, measurementSort)
            recyMeasurementType!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    measurementSort = JSONArray()

                    for (k in 0 until worktypeArrayList.length()) {
                        val jsonObject = worktypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("MeasurementUnit").length) {
                            if (jsonObject.getString("MeasurementUnit")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                measurementSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"measurementSort               7103    "+measurementSort)
                    val adapter = MeasurementAdapter(this@ProjectSiteVisitActivity, measurementSort)
                    recyMeasurementType!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
                }
            })

            dialogMeasurement!!.show()
            dialogMeasurement!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUnit() {
         var ReqMode = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                unitViewModel.getUnit(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (unitcount == 0){
                                    unitcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("UnitList")
                                        unitArrayList = jobjt.getJSONArray("UnitListDetails")
                                        if (unitArrayList.length()>0){

                                            unitPopup(unitArrayList)

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

    private fun unitPopup(unitArrayList: JSONArray) {
        try {

            dialogUnit = Dialog(this)
            dialogUnit!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogUnit!! .setContentView(R.layout.unit_popup)
            dialogUnit!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyUnit = dialogUnit!! .findViewById(R.id.recyUnit) as RecyclerView
            val etsearch = dialogUnit!! .findViewById(R.id.etsearch) as EditText

//            unitSort = JSONArray()
//            for (k in 0 until unitArrayList.length()) {
//                val jsonObject = unitArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                unitSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyUnit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = UnitAdapter(this@ProjectSiteVisitActivity, unitArrayList)
            recyUnit!!.adapter = adapter
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
//                    unitSort = JSONArray()
//
//                    for (k in 0 until unitArrayList.length()) {
//                        val jsonObject = unitArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("DeptName").length) {
//                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                                unitSort.put(jsonObject)
//                            }
//
//                        }
//                    }
//
//                    Log.e(TAG,"unitSort               7103    "+unitSort)
//                    val adapter = UnitAdapter(this@ProjectSiteVisitActivity, unitSort)
//                    recyUnit!!.adapter = adapter
//                    adapter.setClickListener(this@ProjectSiteVisitActivity)
//                }
//            })

            dialogUnit!!.show()
            dialogUnit!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getImageMode() {
//        var department = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                imagemodeViewModel.getImageMode(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (imagemodecount == 0){
                                    imagemodecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1142   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        imagemodeArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (imagemodeArrayList.length()>0){

                                            imageMode_Popup(imagemodeArrayList)

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

    private fun imageMode_Popup(unitArrayList: JSONArray) {
        try {

            dialogImagemode = Dialog(this)
            dialogImagemode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogImagemode!! .setContentView(R.layout.unit_popup)
            dialogImagemode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyUnit = dialogImagemode!! .findViewById(R.id.recyUnit) as RecyclerView
            val etsearch = dialogImagemode!! .findViewById(R.id.etsearch) as EditText

            imagemodeSort = JSONArray()
            for (k in 0 until unitArrayList.length()) {
                val jsonObject = unitArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                imagemodeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyUnit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ImagemodeAdapter(this@ProjectSiteVisitActivity, imagemodeSort)
            recyUnit!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    imagemodeSort = JSONArray()

                    for (k in 0 until unitArrayList.length()) {
                        val jsonObject = unitArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                imagemodeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"imagemodeSort               7103    "+imagemodeSort)
                    val adapter = ImagemodeAdapter(this@ProjectSiteVisitActivity, imagemodeSort)
                    recyUnit!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitActivity)
                }
            })

            dialogImagemode!!.show()
            dialogImagemode!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()

                        //   val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
//                        imgvupload1!!.setImageURI(contentURI)
                        image = getRealPathFromURI(selectedImageUri)
                        Log.e(TAG, "image1  2052    " + image)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@ProjectSiteVisitActivity, "Failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

        else if (requestCode == CAMERA) {

            try {
                if (data != null) {
                    try {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            ) {

                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                                )
                            }
                        } else {

                            val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                            val bytes = ByteArrayOutputStream()
                            thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)


                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    destination = File(
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                        ""
                                    )
                                    // destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)  +"/" +  getString(R.string.app_name));
                                } else {
                                    destination = File(
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                        ""
                                    )
                                }

                                if (!destination!!.exists()) {
                                    destination!!.createNewFile()
                                }

                                destination = File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" + "", "IMG_" + System.currentTimeMillis() + ".jpg")
                                val fo: FileOutputStream


                                fo = FileOutputStream(destination)
                                fo.write(bytes.toByteArray())
                                fo.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                                Log.e(TAG, "FileNotFoundException   23671    " + e.toString())

                            } catch (e: IOException) {
                                e.printStackTrace()
                                Log.e(TAG, "FileNotFoundException   23672    " + e.toString())
                            }

                                image = destination!!.getAbsolutePath()
//                                Log.e(TAG, "image1  20522    " + image1)
                                destination = File(image)


                                val myBitmap = BitmapFactory.decodeFile(destination.toString())
                                val converetdImage = getResizedBitmap(myBitmap, 500)
                                //  val img_image1 = findViewById(R.id.img_image1) as RoundedImageView


                                if (image != null) {

                                }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@ProjectSiteVisitActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
    private fun showPictureDialog() {

        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                } else {
                    val pictureDialog = AlertDialog.Builder(this)
                    pictureDialog.setTitle("Select From")
                    val pictureDialogItems = arrayOf("Gallery", "Camera")
                    pictureDialog.setItems(pictureDialogItems) { dialog, which ->
                        when (which) {
                            0 -> choosePhotoFromGallary()
                            1 -> takePhotoFromCamera()
                        }
                    }
                    pictureDialog.show()
                }
            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                CAMERA
            )
        } catch (e: Exception) {

        }

    }

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun checkCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            return false;
        }
        return true;

    }

    private fun takePhotoFromCamera() {
        val photo = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photo, CAMERA)
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (getContentResolver() != null) {
            val cursor = getContentResolver().query(uri, null, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor!!.getString(idx)
                cursor!!.close()
            }
        }
        return path
    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    @SuppressLint("MissingInflatedId")
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

        if (data.equals("LeadNumberClick")){
            dialogLeadNo!!.dismiss()
            val jsonObject = leadnoSort.getJSONObject(position)

            Log.e(TAG,"LeadNo   "+jsonObject.getString("LeadNo"))
            tie_LeadNo!!.setText(jsonObject.getString("LeadNo"))
            strID_FIELD = jsonObject.getString("ID_FIELD")

        }

        if (data.equals("WorkType")){
            dialogWorkType!!.dismiss()
            val jsonObject = worktypeSort.getJSONObject(position)

            Log.e(TAG,"WorkType   "+jsonObject.getString("WorkType"))
            tie_WorkType!!.setText(jsonObject.getString("WorkType"))
            WorkTypeID = jsonObject.getString("WorkTypeID")

        }

        if (data.equals("MeasurementUnitClick")){
            dialogMeasurement!!.dismiss()
            val jsonObject = measurementSort.getJSONObject(position)

            Log.e(TAG,"MeasurementUnit   "+jsonObject.getString("MeasurementType"))
            tie_MeasurementType!!.setText(jsonObject.getString("MeasurementType"))
            ID_MeasurementUnit = jsonObject.getString("MeasurementTypeID")

        }

        if (data.equals("unitClick")){
            dialogUnit!!.dismiss()
            val jsonObject = unitArrayList.getJSONObject(position)

            Log.e(TAG,"unitClick   "+jsonObject.getString("MeasurementUnit"))
            tie_Unit!!.setText(jsonObject.getString("MeasurementUnit"))
            ID_Unit = jsonObject.getString("MeasurementUnitID")


        }

        if (data.equals("editMeasureClick")) {
            try {
                modelPosition = position
                updateedit = "1"

                WorkTypeID   = modelMesurementDetails.get(position).WorkTypeID
                ID_MeasurementUnit     = modelMesurementDetails.get(position).ID_MeasurementUnit
                ID_Unit =  modelMesurementDetails.get(position).Unit

                tie_WorkType!!.setText(modelMesurementDetails.get(position).WorkType)
                tie_MeasurementType!!.setText(modelMesurementDetails.get(position).MeasurementUnit)
                tie_Value!!.setText(modelMesurementDetails.get(position).Value)
                tie_Unit!!.setText(modelMesurementDetails.get(position).Unit)
                tie_Remarks!!.setText(modelMesurementDetails.get(position).Remarks)


            } catch (e: Exception) {

            }
        }

        if (data.equals("deleteMeasureClick")) {

            try {
                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.alert_delete, null)

                val btnNo = view.findViewById<Button>(R.id.btn_No)
                val btnYes = view.findViewById<Button>(R.id.btn_Yes)
                val textid1 = view.findViewById<TextView>(R.id.textid1)

                textid1!!.setText("Do you want to delete this Measurement?")
                btnNo.setOnClickListener {
                    dialog .dismiss()
//                chipNavigationBar!!.setItemSelected(R.id.home, true)
                }
                btnYes.setOnClickListener {
//                finish()

                    updateedit = "0"
                    WorkTypeID   = ""
                    ID_MeasurementUnit     = ""
                    ID_Unit =  ""

                    tie_WorkType!!.setText("")
                    tie_MeasurementType!!.setText("")
                    tie_Value!!.setText("")
                    tie_Unit!!.setText("")
                    tie_Remarks!!.setText("")


                    modelMesurementDetails.removeAt(position)
                    measurementShowAdapter!!.notifyItemRemoved(position)

                    if (modelMesurementDetails.size <= 0){
                        ll_measureShow!!.visibility = View.GONE
                    }

                    dialog.dismiss()

                }
                dialog.setCancelable(true)
                dialog!!.setContentView(view)

                dialog.show()
            }catch (e : Exception){

            }

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
        if (data.equals("deleteArrayList")) {



            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.alert_delete, null)

            val btnNo = view.findViewById<Button>(R.id.btn_No)
            val btnYes = view.findViewById<Button>(R.id.btn_Yes)
            val textid1 = view.findViewById<TextView>(R.id.textid1)

            textid1!!.setText("Do you want to delete this employee?")
            btnNo.setOnClickListener {
                dialog .dismiss()
//                chipNavigationBar!!.setItemSelected(R.id.home, true)
            }
            btnYes.setOnClickListener {
//                finish()

                updateedit = "0"
                ID_Department = ""
                tie_Department!!.setText("")

                ID_Employee = ""
                tie_Employee!!.setText("")

                ID_EmployeeType = ""
                tie_EmployeeType!!.setText("")

                modelProjectEmpDetails.removeAt(position)
                val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
                recyEmpDetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                val adapter1 = ProjectEmpDetailAdapter(this@ProjectSiteVisitActivity,modelProjectEmpDetails)
                recyEmpDetails!!.adapter = adapter1
                adapter1.setClickListener(this@ProjectSiteVisitActivity)
                dialog.dismiss()

            }
            dialog.setCancelable(true)
            dialog!!.setContentView(view)

            dialog.show()

        }
        if (data.equals("editArrayList")) {
            try {
                modelPosition = position
                updateedit = "1"

                ID_Department   = modelProjectEmpDetails.get(position).ID_Department
                ID_Employee     = modelProjectEmpDetails.get(position).ID_Employee
                ID_EmployeeType =  modelProjectEmpDetails.get(position).ID_EmployeeType
                tie_Department!!.setText(modelProjectEmpDetails.get(position).Department)
                tie_Employee!!.setText(modelProjectEmpDetails.get(position).Employee)
                tie_EmployeeType!!.setText(modelProjectEmpDetails.get(position).EmployeeType)


            } catch (e: Exception) {

            }
        }
    }
}