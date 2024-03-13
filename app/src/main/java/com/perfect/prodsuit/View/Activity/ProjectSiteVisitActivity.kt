package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
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
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.Model.*
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
    private var tv_Checklist_Details: TextView? = null
    private var tv_OtherCharges: TextView? = null
    private var tvv_choosefile: TextView? = null

    private var ll_saveSiteVisit: LinearLayout? = null
    private var ll_UploadImage: LinearLayout? = null
    private var ll_LeadClick: LinearLayout? = null
    private var ll_EmployeeClick: LinearLayout? = null
    private var ll_MeasurementClick: LinearLayout? = null
    private var ll_Checklist_Details: LinearLayout? = null
    private var ll_OtherCharges: LinearLayout? = null
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
    private var tie_InspectionType: TextInputEditText? = null
    private var tie_Value: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null
    private var tie_Othercharges: TextInputEditText? = null
    private var tie_Remark: TextInputEditText? = null
    private var tie_Common_Remark: TextInputEditText? = null
    private var tie_Attachments: TextInputEditText? = null
    private var tie_doc_desc: TextInputEditText? = null
    private var imv_name: TextView? = null

    private var tie_WorkType: TextInputEditText? = null
    private var tie_MeasurementType: TextInputEditText? = null
    private var tie_Unit: TextInputEditText? = null


    private var til_LeadNo: TextInputLayout? = null
    private var til_VisitDate: TextInputLayout? = null
    private var til_InspectionNotes1: TextInputLayout? = null
    private var til_Attachments: TextInputLayout? = null
    private var til_VisitTime: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_EmployeeType: TextInputLayout? = null
    private var til_InspectionType: TextInputLayout? = null
    private var btnSubmit: Button? = null
    private var btnReset: Button? = null
    private var btnDocClose: Button? = null
    private var btnDocUpload: Button? = null

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
    var showChecklist   = 0
    var showOtherCharge = 0

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
    lateinit var otherChargeCalcArrayList : JSONArray
    lateinit var inspectionTypeArrayList : JSONArray
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
    private var dialogInspectionType : Dialog? = null
    private var dialogOthercharge : Dialog? = null
    private var dialogWorkType : Dialog? = null
    private var dialogMeasurement : Dialog? = null
    private var dialogImagemode : Dialog? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var image = ""
    private var destination: File? = null
    private val PERMISSION_REQUEST_CODE = 200
    var recyDeaprtment: RecyclerView? = null
    var recyLeadNo: RecyclerView? = null
    var recyInspectionType: RecyclerView? = null
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

    var siteCheckcount   = 0
    lateinit var siteCheckViewModel: SiteCheckViewModel

    var mode = ""

    lateinit var otherChargeTaxCalculationViewModel  : OtherChargeTaxCalculationViewModel
    var otherchargetaxcount   = 0
    var otherchargetaxMode   = 0  // 0 = Popup, 1 = change amount
    var otherChargeCalcPosition   = 0
    var otherChargeClickPosition   = 0

    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private var PICK_IMAGE_CAMERA = 1
    private var PICK_IMAGE_GALLERY = 2
    private var PICK_DOCUMRNT_GALLERY = 3
    private var PERMISSION_CAMERA = 1



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
    var ID_LeadGenerate = ""
    var strInspectionNote1 = ""
    var strInspectionNote2 = ""
    var strCustomerNotes   = ""
    var strInspectionCharge  = ""
    var strExpenseAmount        = ""
    var strCommonRemark        = ""
    var FK_TaxGroup   = ""
    var IncludeTaxCalc   = ""
    var AmountTaxCalc   = ""

    var strInspectCharge = ""

    val modelProjectEmpDetails = ArrayList<ModelProjectEmpDetails>()

    var jsonObj: JSONObject? = null
    val modelMesurementDetails = ArrayList<ModelMesurementDetails>()
    var measurementShowAdapter  : MeasurementShowAdapter? = null

    var recy_checklist: RecyclerView? = null
    var modelProjectCheckList = ArrayList<ModelProjectCheckList>()




    var saveEmployeeDetails= JSONArray()
    var saveMeasurementDetails= JSONArray()
    var saveCheckedDetails= JSONArray()
    var pssOtherCharge= JSONArray()
    var pssOtherChargeTax= JSONArray()

    private var inputStreamImg: InputStream? = null
    private var imgPath: String? = null
    private var bitmap: Bitmap? = null
    var documentPath : String = ""

    private val PICK_DOCUMENT_REQUEST_CODE = 123

    val modelOtherCharges = ArrayList<ModelOtherCharges>()
    val modelOtherChargesTemp = ArrayList<ModelOtherChargesTemp>()
    private var dialogOtherChargesSheet : Dialog? = null
    val modelOtherChargesCalculation = ArrayList<ModelOtherChargesCalculation>()
    private var dialogOtherChargesCalcSheet : Dialog? = null
    private var otherChargeAdapter : OtherChargeAdapter? = null
    private var taxDetailAdapter : TaxDetailAdapter? = null
    private var recyOtherCalc     : RecyclerView?      = null

    var upadateSitecount   = 0
    lateinit var upadateSiteVisitViewModel: UpadateSiteVisitViewModel
    var FK_SiteVisit   = ""  // Image Uploading ID
    var strDescription   = ""
    var strImageName   = ""
    var encodeDoc : String = ""
    var ID_SiteVisitAssignment : String = ""
    var ID_InspectionType : String = ""

    var updateDocscount   = 0
    lateinit var siteVisitDocUploadViewModel  : SiteVisitDocUploadViewModel
    var strWarningMessage : String = ""

    lateinit var projectLeadNoViewModel: ProjectLeadNoViewModel
    private var ReqMode :  String? =  ""
    private var SubMode :  String? =  ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    var inspectionTypecount   = 0
    lateinit var inspectionTypeViewModel  : InspectionTypeViewModel

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
        siteCheckViewModel = ViewModelProvider(this).get(SiteCheckViewModel::class.java)
        otherChargeTaxCalculationViewModel     = ViewModelProvider(this).get(OtherChargeTaxCalculationViewModel::class.java)
        upadateSiteVisitViewModel     = ViewModelProvider(this).get(UpadateSiteVisitViewModel::class.java)
        siteVisitDocUploadViewModel     = ViewModelProvider(this).get(SiteVisitDocUploadViewModel::class.java)
        inspectionTypeViewModel     = ViewModelProvider(this).get(InspectionTypeViewModel::class.java)

        setRegViews()

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        mode = intent.getStringExtra("SubMode").toString()
        jsonObj = JSONObject(jsonObject)
        ReqMode = intent.getStringExtra("ReqMode").toString()
        SubMode = intent.getStringExtra("SubMode").toString()


        Log.e(TAG,"Mode  32555   "+mode)
        if (mode.equals("0")){
            Log.e(TAG,"23111   ")
            tie_LeadNo!!.isEnabled = true
        }else{
            Log.e(TAG,"23112   ")
            strInspectCharge = jsonObj!!.getString("ExpenseAmount")
            ID_LeadGenerate = jsonObj!!.getString("ID_LeadGenerate")
            tie_LeadNo!!.setText(""+jsonObj!!.getString("LeadNo"))
            tie_LeadNo!!.isEnabled = false
            tie_InspectionCharge!!.setText(strInspectCharge)
            ID_SiteVisitAssignment = jsonObj!!.getString("ID_SiteVisitAssignment")
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

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
        tv_Checklist_Details = findViewById(R.id.tv_Checklist_Details)
        tv_OtherCharges = findViewById(R.id.tv_OtherCharges)

        ll_saveSiteVisit = findViewById(R.id.ll_saveSiteVisit)
        ll_UploadImage = findViewById(R.id.ll_UploadImage)
        ll_LeadClick = findViewById(R.id.ll_LeadClick)
        ll_EmployeeClick = findViewById(R.id.ll_EmployeeClick)
        ll_MeasurementClick = findViewById(R.id.ll_MeasurementClick)
        ll_Checklist_Details = findViewById(R.id.ll_Checklist_Details)
        ll_OtherCharges = findViewById(R.id.ll_OtherCharges)
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
        recy_checklist        = findViewById(R.id.recy_checklist)

        til_LeadNo            = findViewById<TextInputLayout>(R.id.til_LeadNo)
        til_VisitDate         = findViewById<TextInputLayout>(R.id.til_VisitDate)
        til_InspectionNotes1  = findViewById<TextInputLayout>(R.id.til_InspectionNotes1)
        til_Department        = findViewById<TextInputLayout>(R.id.til_Department)
        til_Employee          = findViewById<TextInputLayout>(R.id.til_Employee)
        til_EmployeeType      = findViewById<TextInputLayout>(R.id.til_EmployeeType)
        til_InspectionType      = findViewById<TextInputLayout>(R.id.til_InspectionType)
        til_Attachments = findViewById<TextInputLayout>(R.id.til_Attachments)
        tie_InspectionNotes1 = findViewById(R.id.tie_InspectionNotes1)
        tie_InspectionNotes2 = findViewById(R.id.tie_InspectionNotes2)
        tie_Value = findViewById(R.id.tie_Value)
        tie_Remarks = findViewById(R.id.tie_Remarks)
        tie_Othercharges = findViewById(R.id.tie_Othercharges)
        tie_Remark = findViewById(R.id.tie_Remark)
        tie_Common_Remark = findViewById(R.id.tie_Common_Remark)
        tie_Attachments = findViewById(R.id.tie_Attachments)
        tie_doc_desc = findViewById(R.id.tie_doc_desc)
        imv_name = findViewById(R.id.imv_name)
        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnDocClose = findViewById(R.id.btnDocClose)
        btnDocUpload = findViewById(R.id.btnDocUpload)
        tie_CustomerNotes = findViewById(R.id.tie_CustomerNotes)
        tie_InspectionCharge = findViewById(R.id.tie_InspectionCharge)
        tie_InspectionType = findViewById(R.id.tie_InspectionType)

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
        tv_Checklist_Details!!.setOnClickListener(this)
        tv_OtherCharges!!.setOnClickListener(this)

        tie_LeadNo!!.setOnClickListener(this)
        tie_VisitDate!!.setOnClickListener(this)
        tie_VisitTime!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_InspectionType!!.setOnClickListener(this)
        tie_EmployeeType!!.setOnClickListener(this)
        tie_WorkType!!.setOnClickListener(this)
        tie_MeasurementType!!.setOnClickListener(this)
        tie_Unit!!.setOnClickListener(this)
        til_Attachments!!.setOnClickListener(this)
        tvv_choosefile!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnDocClose!!.setOnClickListener(this)
        btnDocUpload!!.setOnClickListener(this)
        tie_Othercharges!!.setOnClickListener(this)
        tie_Attachments!!.setOnClickListener(this)

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
        til_InspectionType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)


        til_WorkType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_MeasurementType!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Value!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Unit!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        projectLeadNoViewModel = ViewModelProvider(this).get(ProjectLeadNoViewModel::class.java)

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
        tie_InspectionType!!.addTextChangedListener(watcher)

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
                editable === tie_InspectionType!!.editableText -> {
                    if (tie_InspectionType!!.text.toString().equals("")){
                        til_InspectionType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_InspectionType!!.isErrorEnabled = false
                        til_InspectionType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
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
                Config.disableClick(v)
                openBottomSheet()
            }
            R.id.img_EmpRefresh->{
                updateedit = "0"

                ID_Department = ""
                ID_Employee = ""
                ID_EmployeeType = ""

                tie_Department!!.setText("")
                tie_Employee!!.setText("")
                tie_EmployeeType!!.setText("")
            }
            R.id.tie_VisitTime->{
                Config.disableClick(v)
              openBottomTime()
            }


            R.id.tie_LeadNo->{

                Config.disableClick(v)
                leadcount = 0
               // getLeadNo()

                getLeadDetails()
//                showLead = 1
//                showEmployee = 0
//                 showMeasurement = 0
//                expandTab()
            }
            R.id.tie_InspectionType->{
                Config.disableClick(v)

                inspectionTypecount = 0
                getInspectionType()

            }
            R.id.tie_WorkType->{
                Config.disableClick(v)
                workcount = 0
                getWorkType()

            }

            R.id.tie_MeasurementType->{

                Config.disableClick(v)
                measurecount = 0
                getMeasurementDetails()

            }
            R.id.tie_Unit->{
                Config.disableClick(v)
                unitcount = 0
                getUnit()
            }

            R.id.tv_LeadClick->{
                showLead = 1
                showEmployee = 0
                showMeasurement = 0
                showChecklist = 0
                showOtherCharge = 0
                expandTab()
            }
            R.id.tv_EmployeeClick->{
                showLead = 0
                showEmployee = 1
                showMeasurement = 0
                showChecklist = 0
                showOtherCharge = 0
                expandTab()
            }
            R.id.tv_MeasurementClick->{
                showLead = 0
                showEmployee = 0
                showMeasurement = 1
                showChecklist = 0
                showOtherCharge = 0
                expandTab()
            }
            R.id.tv_Checklist_Details->{
                showLead = 0
                showEmployee = 0
                showMeasurement = 0
                showChecklist = 1
                showOtherCharge = 0
                expandTab()

                if (modelProjectCheckList.size == 0){
                    siteCheckcount = 0
                    getCheckedListData()
                }

            }

            R.id.tv_OtherCharges->{
                showLead = 0
                showEmployee = 0
                showMeasurement = 0
                showChecklist = 0
                showOtherCharge = 1
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
//                Config.disableClick(v)
//                otherchargecount = 0
//                getOtherCharges()
                Config.disableClick(v)
                if (modelOtherCharges.size == 0){

                    otherchargecount = 0
                    getOtherCharges()
                }else{
                    Log.e(TAG,"1491112   "+modelOtherCharges.size)
//                        modelOtherChargesTemp = modelOtherCharges :  ArrayList<ModelOtherCharges>
//                        modelOtherCharges!!.add(ModelOtherCharges(modelOtherCharges : ArrayList<ModelOtherCharges>))
                    //  modelOtherChargesTemp.add(ModelOtherChargesTemp(modelOtherCharges))
//                        modelOtherChargesTemp = modelOtherCharges : ArrayList<ModelOtherCharges>
                    // modelOtherChargesTemp.add(modelOtherCharges)
                    //   modelOtherChargesTemp.clear()
                    for (i in 0 until modelOtherCharges.size) {
//                            modelOtherChargesTemp.addAll(i,modelOtherCharges :  ArrayList<ModelOtherCharges>)
                        modelOtherChargesTemp[i].ID_OtherChargeType = modelOtherCharges[i].ID_OtherChargeType
                        modelOtherChargesTemp[i].OctyName = modelOtherCharges[i].OctyName
                        modelOtherChargesTemp[i].OctyTransTypeActive = modelOtherCharges[i].OctyTransTypeActive
                        modelOtherChargesTemp[i].OctyTransType = modelOtherCharges[i].OctyTransType
                        modelOtherChargesTemp[i].FK_TaxGroup = modelOtherCharges[i].FK_TaxGroup
                        modelOtherChargesTemp[i].OctyAmount = modelOtherCharges[i].OctyAmount
                        modelOtherChargesTemp[i].OctyTaxAmount = modelOtherCharges[i].OctyTaxAmount
                        modelOtherChargesTemp[i].OctyIncludeTaxAmount = modelOtherCharges[i].OctyIncludeTaxAmount
                        modelOtherChargesTemp[i].ID_TransType = modelOtherCharges[i].ID_TransType
                        modelOtherChargesTemp[i].TransType_Name = modelOtherCharges[i].TransType_Name
                        modelOtherChargesTemp[i].isCalculate = modelOtherCharges[i].isCalculate
                        modelOtherChargesTemp[i].isTaxCalculate = modelOtherCharges[i].isTaxCalculate

                    }
                    // otherChargesPopup(modelOtherCharges)
                    otherChargesPopup(modelOtherChargesTemp)
                }

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




            R.id.tie_Attachments->{

//                Toast.makeText(applicationContext, "test 2", Toast.LENGTH_LONG).show()
                imagemodecount = 0
                selectImage()
               // getImageMode()
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
                //successBottomSheet(v)

//                validateSaveOtherCharges(v)

            }
            R.id.btnReset->{
                clearData()
            }

            R.id.btnDocClose->{
               finish()
            }

            R.id.btnDocUpload->{
             //  docUploadSucces(v)
                docUploadValidation(v)
            }
        }
    }

    private fun getInspectionType() {
        var ReqMode = "12"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                inspectionTypeViewModel.getInspectionType(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (inspectionTypecount == 0){
                                    inspectionTypecount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   11277   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("InspectionTypeDetails")
                                        inspectionTypeArrayList = jobjt.getJSONArray("InspectionTypeList")
                                        if (inspectionTypeArrayList.length()>0){

                                            inspectionTypePopup(inspectionTypeArrayList)

                                        }
                                    }
                                    else {
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

    private fun inspectionTypePopup(inspectionTypeArrayList: JSONArray) {
        try {

            dialogInspectionType = Dialog(this)
            dialogInspectionType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogInspectionType!! .setContentView(R.layout.inspectiontype_popup)
            dialogInspectionType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyInspectionType = dialogInspectionType!! .findViewById(R.id.recyInspectionType) as RecyclerView

//            val txt_nodata = dialogLeadNo!! .findViewById(R.id.txt_nodata) as TextView
//            txt_nodata.text = "Invalid Lead Number"



            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyInspectionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = InspectionTypeAdapter(this@ProjectSiteVisitActivity, inspectionTypeArrayList)
            recyInspectionType!!.adapter = adapter
            adapter.setClickListener(this@ProjectSiteVisitActivity)



            dialogInspectionType!!.show()
            dialogInspectionType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun docUploadValidation(v: View) {


        strDescription = tie_doc_desc!!.text.toString()
        strImageName = tie_Attachments!!.text.toString()

        if(strImageName.equals("")){
            Config.snackBars(context,v,"Select Documents")
        }
        else if(documentPath.equals("")){
            Config.snackBars(context,v,"Pick Documents")
        }
        else{
            try {
                val inputStream: InputStream = FileInputStream(documentPath)
                val bos = ByteArrayOutputStream()
                val b = ByteArray(1024 * 8)
                var bytesRead = 0

                while (inputStream.read(b).also { bytesRead = it } != -1) {
                    bos.write(b, 0, bytesRead)
                }

                //   byteArray = bos.toByteArray()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    encodeDoc = Base64.getEncoder().encodeToString(stream.toByteArray());
                    encodeDoc = Base64.getEncoder().encodeToString(bos.toByteArray());
                } else {
                    //  encodeDoc = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                    encodeDoc = android.util.Base64.encodeToString(bos.toByteArray(), android.util.Base64.DEFAULT)
                }
                val extension: String = documentPath.substring(documentPath.lastIndexOf("."))
                Log.e(TAG,"encodeDoc   508   "+encodeDoc)
                updateDocscount = 0
                saveDocuments(strDescription,strImageName,encodeDoc,extension)
            }catch (e: Exception){

            }
        }

    }

    private fun saveDocuments(strDescription: String, strImageName: String, encodeDoc: String?, extension: String) {
        var TransMode = "PRSV"
      //  FK_SiteVisit = "19"
      //  Toast.makeText(applicationContext,""+FK_SiteVisit,Toast.LENGTH_SHORT).show()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                siteVisitDocUploadViewModel.getSiteVisitDocUpload(this,TransMode,FK_SiteVisit,strImageName,extension,strDescription,encodeDoc!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (updateDocscount == 0){
                                    updateDocscount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   10722     "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("DownloadImage")
                                        try {

                                            tie_Attachments!!.setText("")
                                            tie_doc_desc!!.setText("")
                                            imv_name!!.setText("")

                                            docUploadSucces(jobjt.getString("ResponseMessage"))

                                        }catch (e : Exception){

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

    private fun docUploadSucces(ResponseMessage: String) {
        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.upload_more_document_bottom, null)

            val btnNo = view.findViewById<Button>(R.id.btn_No)
            val btnYes = view.findViewById<Button>(R.id.btn_Yes)
            val textid1 = view.findViewById<TextView>(R.id.textid1)


            textid1!!.setText("Do you want to upload more document?")
            btnNo.setOnClickListener {
                dialog .dismiss()
                finish()
            }
            btnYes.setOnClickListener {
                dialog.dismiss()

                tie_Attachments!!.setText("")
                tie_doc_desc!!.setText("")
                encodeDoc = ""

            }
            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()
        }catch (e : Exception){

        }
    }

    private fun successBottomSheet(v: View) {

        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.success_project_bottom, null)

            val btnNo = view.findViewById<Button>(R.id.btn_No)
            val btnYes = view.findViewById<Button>(R.id.btn_Yes)
            val textid1 = view.findViewById<TextView>(R.id.textid1)
            val textid2 = view.findViewById<TextView>(R.id.textid2)

            textid1!!.setText("Succesfully Saved")
            textid2!!.setText("Do you want to upload document?")
            btnNo.setOnClickListener {
                dialog .dismiss()
                finish()
            }
            btnYes.setOnClickListener {
//                finish()

                dialog.dismiss()
                ll_saveSiteVisit!!.visibility = View.GONE
                ll_UploadImage!!.visibility = View.VISIBLE

            }
            dialog.setCancelable(true)
            dialog!!.setContentView(view)

            dialog.show()
        }catch (e : Exception){

        }
    }

    private fun getCheckedListData() {

        var ReqMode = "119"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                siteCheckViewModel.getSiteCheck(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (siteCheckcount == 0){
                                    siteCheckcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   917771   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("checkDetails")
                                        var arrayList = jobjt.getJSONArray("checkDetailsList")
//                                        val gson = GsonBuilder().create()
                                        val gson = Gson()
                                        modelProjectCheckList = gson.fromJson(arrayList.toString(),Array<ModelProjectCheckList>::class.java).toList() as ArrayList<ModelProjectCheckList>
//                                        siteCheckViewModel!!.add(SiteCheckViewModel(jsonObject.getString("ID_Type"),jsonObject.getString("Type_Name"), "","","0.00", "0.00",false))

                                        Log.e(TAG,"917772   "+modelProjectCheckList.size)
                                        Log.e(TAG,"917772   "+modelProjectCheckList[0].CLTyName)


                                        if (modelProjectCheckList.size > 0){
                                            val expandableListView: ExpandableListView = findViewById(R.id.expandableListView)
                                            val adapter = SiteCheckListAdapter(this@ProjectSiteVisitActivity, modelProjectCheckList) // Provide your data here
                                            expandableListView.setAdapter(adapter)
                                            expandableListView.setGroupIndicator(resources.getDrawable(R.drawable.transparent_drawable))
                                            expandableListView.setDividerHeight(0)
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


    private fun clearData(){

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdfTime1 = SimpleDateFormat("hh:mm aa")
        val currentDate = sdf.format(Date())
        val time = sdfTime1.format(Date())
        Log.e(TAG, "newtime  885  " + time)

        if (mode.equals("0")){
            ID_LeadGenerate = ""
            tie_LeadNo!!.setText("")
            strID_FIELD = ""
            ID_LeadGenerate = ""
            ID_SiteVisitAssignment = ""
            tie_InspectionCharge!!.setText("")
        }else{
            tie_InspectionCharge!!.setText(strInspectCharge)
        }
        ID_InspectionType = ""
        tie_InspectionType!!.setText("")
        tie_InspectionNotes1!!.setText("")
        tie_InspectionNotes2!!.setText("")
        tie_CustomerNotes!!.setText("")



        // Employee Details


        updateedit = "0"
        ID_Department          = ""
        ID_Employee          = ""
        ID_EmployeeType      = ""

        tie_Department!!.setText("")
        tie_Employee!!.setText("")
        tie_EmployeeType!!.setText("")

        modelProjectEmpDetails!!.clear()
        recyEmpDetails!!.adapter = null
        // Measurement

        WorkTypeID = ""
        ID_MeasurementUnit = ""
        ID_Unit = ""

        tie_WorkType!!.setText("")
        tie_MeasurementType!!.setText("")
        tie_Value!!.setText("")
        tie_Unit!!.setText("")
        tie_Remarks!!.setText("")

        modelMesurementDetails.clear()
        recy_mesurment!!.adapter = null
        // Check Details

        modelProjectCheckList.clear()
        modelOtherCharges.clear()
        modelOtherChargesTemp.clear()


        tie_Othercharges!!.setText("")
        tie_Common_Remark!!.setText("")


        modelOtherCharges.clear()
        otherChargeAdapter = null

        modelOtherChargesCalculation.clear()
        taxDetailAdapter = null


        showLead = 1
        showEmployee = 0
        showMeasurement = 0
        showChecklist = 0
        showOtherCharge = 0
        expandTab()

        // Other Charges


//        strInspectionNote1   = ""
//        strLeadno            = ""
//        strVisitdate         = ""
//        visitDate            = ""
//        visitTime            = ""
//
//        tie_LeadNo!!.setText("")
//        tie_VisitDate!!.setText(currentDate)
//        tie_VisitTime!!.setText(time)
//        tie_InspectionNotes1!!.setText("")
//        tie_InspectionNotes2!!.setText("")
//        tie_CustomerNotes!!.setText("")
//        tie_InspectionCharge!!.setText("")





        tie_Attachments!!.setText("")
        tie_doc_desc!!.setText("")
        imv_name!!.setText("")

    }

    private fun siteVisitValidation(v : View){

        strLeadno          = tie_LeadNo!!.text.toString()
        strVisitdate       = tie_VisitDate!!.text.toString()
        visitTime          = tie_VisitTime!!.text.toString()
        strInspectionNote1 = tie_InspectionNotes1!!.text.toString()
        strInspectionNote2 = tie_InspectionNotes2!!.text.toString()
        strCustomerNotes   = tie_CustomerNotes!!.text.toString()
        strInspectionCharge   = tie_InspectionCharge!!.text.toString()
        strExpenseAmount   = tie_Othercharges!!.text.toString()
        strCommonRemark   = tie_Common_Remark!!.text.toString()


        if (strInspectionCharge.equals("") || strInspectionCharge.equals(".")){
            strInspectionCharge = "0.00"
        }

        if (strExpenseAmount.equals("") || strExpenseAmount.equals(".")){
            strExpenseAmount = "0.00"
        }

        if (ID_LeadGenerate!!.equals("")){
            Config.snackBars(context, v, "Select LeadNo")
            Config.snackBars(context, v, "Select LeadNo")
            showLead = 1
            showEmployee = 0
            showMeasurement = 0
            showChecklist = 0
            showOtherCharge = 0
            expandTab()
        }
        else if (strLeadno!!.equals("")){
            Config.snackBars(context, v, "Select LeadNo")
            showLead = 1
            showEmployee = 0
            showMeasurement = 0
            showChecklist = 0
            showOtherCharge = 0
            expandTab()
        }else if (strVisitdate!!.equals("")){
            Config.snackBars(context, v, "Select Visitdate")
            showLead = 1
            showEmployee = 0
            showMeasurement = 0
            showChecklist = 0
            showOtherCharge = 0
            expandTab()
        }else if (strInspectionNote1!!.equals("")){
            Config.snackBars(context, v, "Please Enter InspectonNote 1")
            showLead = 1
            showEmployee = 0
            showMeasurement = 0
            showChecklist = 0
            showOtherCharge = 0
            expandTab()
        }else{
            validateSaveEmployee(v)
        }

    }

    private fun validateSaveEmployee(v : View) {
        saveEmployeeDetails = JSONArray()
        for (obj in modelProjectEmpDetails) {
            val jsonObject = JSONObject()
            jsonObject.put("EmployeeID", obj.ID_Employee)
            jsonObject.put("EmployeeType", obj.ID_EmployeeType)
            jsonObject.put("Department", obj.ID_Department)
            jsonObject.put("Employee", obj.Employee)

            saveEmployeeDetails.put(jsonObject)
        }

        if (saveEmployeeDetails.length() > 0){
            validateSaveMeasurement(v)
        }else{
            Config.snackBars(context, v, "Enter Atleast One Emplyee Details !!!")
            showLead = 0
            showEmployee = 1
            showMeasurement = 0
            showChecklist = 0
            showOtherCharge = 0
            expandTab()
        }
    }

    private fun validateSaveMeasurement(v: View) {
        saveMeasurementDetails = JSONArray()
        for (obj in modelMesurementDetails) {
            val jsonObject = JSONObject()
            jsonObject.put("WorkType", obj.WorkTypeID)
            jsonObject.put("MeasurementType", obj.ID_MeasurementUnit)
            jsonObject.put("MeasurementValue", obj.Value)
            jsonObject.put("MeasurementUnit", obj.ID_Unit)
            jsonObject.put("MDRemarks", obj.Remarks)

            saveMeasurementDetails.put(jsonObject)
        }

        validateSaveCheckList(v)
    }

    private fun validateSaveCheckList(v: View) {

        saveCheckedDetails = JSONArray()
        for (obj in modelProjectCheckList) {

            if (obj.is_checked){

                for (objSub in obj.SubArrary) {
                    if (objSub.is_checked){
                        val jsonObject = JSONObject()
                        Log.e(TAG,"obj.SubArrary 13131    "+obj.ID_CheckListType)
                        Log.e(TAG,"ID_CheckListType 13132    "+objSub.ID_CheckListType)
                        Log.e(TAG,"ID_CheckList 13133    "+objSub.ID_CheckList)

                        jsonObject.put("FK_CheckListType", obj.ID_CheckListType)  // main
                        jsonObject.put("FK_CheckList", objSub.ID_CheckList) // Sub

                        saveCheckedDetails.put(jsonObject)
                    }
                }
            }

        }
        validateSaveOtherCharges(v)

    }

    private fun validateSaveOtherCharges(v: View) {

        pssOtherCharge = JSONArray()
        pssOtherChargeTax = JSONArray()
        for (obj in modelOtherCharges) {

            if (obj.isCalculate){
                val jsonObject = JSONObject()
                jsonObject.put("ID_OtherChargeType", obj.ID_OtherChargeType)
                jsonObject.put("OctyTransType", obj.OctyTransType)
                jsonObject.put("FK_TaxGroup", obj.FK_TaxGroup)
                jsonObject.put("OctyAmount", obj.OctyAmount)
                jsonObject.put("OctyTaxAmount", obj.OctyTaxAmount)
                jsonObject.put("OctyIncludeTaxAmount", obj.OctyIncludeTaxAmount)

                pssOtherCharge.put(jsonObject)
                for (objSub in modelOtherChargesCalculation) {
                    val jsonObject1 = JSONObject()
                    if (obj.FK_TaxGroup.equals(objSub.FK_TaxGroup)){
                        jsonObject1.put("ID_OtherChargeType", obj.ID_OtherChargeType)
                        jsonObject1.put("ID_TaxSettings", objSub.ID_TaxSettings)
                        jsonObject1.put("Amount", objSub.Amount)
                        jsonObject1.put("TaxPercentage", objSub.TaxPercentage)
                        jsonObject1.put("TaxGrpID", objSub.FK_TaxGroup)
                        jsonObject1.put("FK_TaxType", objSub.FK_TaxType)
                        jsonObject1.put("TaxTyName", objSub.TaxTyName)

                        pssOtherChargeTax.put(jsonObject)
                    }
                }

            }

        }

        Log.e(TAG," 117881 saveEmployeeDetails     :  "+saveEmployeeDetails)
        Log.e(TAG," 117882 saveMeasurementDetails  :  "+saveMeasurementDetails)
        Log.e(TAG," 117883 saveCheckedDetails      :  "+saveCheckedDetails)
        Log.e(TAG," 117884 pssOtherCharge          :  "+pssOtherCharge)
        Log.e(TAG," 117885 pssOtherChargeTax       :  "+pssOtherChargeTax)

        upadateSitecount = 0
        saveUpadateSiteVisit()

    }

    private fun saveUpadateSiteVisit() {
        var UserAction = "1"

        Log.e(TAG,"ID_SiteVisitAssignment   157888    "+ID_SiteVisitAssignment)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                upadateSiteVisitViewModel.getUpadateSiteVisit(this,UserAction,ID_LeadGenerate,ID_SiteVisitAssignment,strVisitdate!!,visitTime,strInspectionNote1,strInspectionNote2,
                    strCustomerNotes,strExpenseAmount,strCommonRemark,strInspectionCharge,saveEmployeeDetails,saveMeasurementDetails,saveCheckedDetails,
                    pssOtherCharge,pssOtherChargeTax)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (upadateSitecount == 0){
                                    upadateSitecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   14111     "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UpadateSiteVisit")
                                        try {

                                            tie_Attachments!!.setText("")
                                            tie_doc_desc!!.setText("")
                                            imv_name!!.setText("")
                                            encodeDoc = ""

                                            val dialog = BottomSheetDialog(this)
                                            val view = layoutInflater.inflate(R.layout.success_project_bottom, null)

                                            val btnNo = view.findViewById<Button>(R.id.btn_No)
                                            val btnYes = view.findViewById<Button>(R.id.btn_Yes)
                                            val textid1 = view.findViewById<TextView>(R.id.textid1)
                                            val textid2 = view.findViewById<TextView>(R.id.textid2)

                                            FK_SiteVisit = jobjt.getString("FK_SiteVisit")
                                            textid1!!.setText(jobjt.getString("ResponseMessage"))
                                            textid2!!.setText("Do you want to upload document?")
                                            btnNo.setOnClickListener {
                                                dialog .dismiss()
                                                finish()
                                            }
                                            btnYes.setOnClickListener {
//                finish()

                                                dialog.dismiss()
                                                ll_saveSiteVisit!!.visibility = View.GONE
                                                ll_UploadImage!!.visibility = View.VISIBLE



                                            }
                                            dialog.setCancelable(true)
                                            dialog!!.setContentView(view)

                                            dialog.show()
                                        }catch (e : Exception){

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
//                var hasCheck =  hasMeasurement(modelMesurementDetails!!,WorkTypeID,ID_MeasurementUnit)
//                if (hasCheck){
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
//                }
//                else{
//                    Config.snackBars(context,v,"Duplicate entry found")
//                }
            }
            else if (updateedit!!.equals("1")){

//                var hasCheck =  hasMeasurement(modelMesurementDetails!!,WorkTypeID,ID_MeasurementUnit)
//                if (hasCheck){


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
//                }else{
//                    Config.snackBars(context,v,"Duplicate entry found")
//                }






            }
//            WorkTypeID, WorkType,ID_MeasurementUnit,MeasurementType,Value,ID_Unit,Unit,Remarks

        }
    }

    private fun hasMeasurement(modelMesurementDetails: ArrayList<ModelMesurementDetails>, WorkTypeID: String, ID_MeasurementUnit: String): Boolean {
        var isChecked = true
        for (i in 0 until modelMesurementDetails.size) {
            if (modelMesurementDetails.get(i).WorkTypeID.equals(WorkTypeID) && modelMesurementDetails.get(i).ID_MeasurementUnit.equals(ID_MeasurementUnit)){
                strWarningMessage = modelMesurementDetails.get(i).WorkType+" & "+modelMesurementDetails.get(i).MeasurementUnit +" Already Exists. Do you want to continue? "
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
        ll_Checklist_Details!!.visibility = View.GONE
        ll_OtherCharges!!.visibility = View.GONE

        if (showLead == 1){
            ll_LeadClick!!.visibility = View.VISIBLE
        }
        if (showEmployee == 1){
            ll_EmployeeClick!!.visibility = View.VISIBLE
        }
        if (showMeasurement == 1){
            ll_MeasurementClick!!.visibility = View.VISIBLE
        }
        if (showChecklist == 1){
            ll_Checklist_Details!!.visibility = View.VISIBLE
        }
        if (showOtherCharge == 1){
            ll_OtherCharges!!.visibility = View.VISIBLE
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

    private fun getLeadDetails() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                projectLeadNoViewModel.getProjectLeadNo(this,ReqMode!!,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (leadcount == 0){
                                    leadcount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectSiteVisitAssign")
                                        leadnoArrayList = jobjt.getJSONArray("ProjectSiteVisitAssignList")
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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
            val txt_nodata = dialogLeadNo!! .findViewById(R.id.txt_nodata) as TextView
            txt_nodata.text = "Invalid Lead Number"

            leadnoSort = JSONArray()
            for (k in 0 until leadnoArrayList.length()) {
                val jsonObject = leadnoArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadnoSort.put(jsonObject)
            }


            if (leadnoSort.length() <= 0){
                recyLeadNo!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
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
                    recyLeadNo!!.adapter = null
                    for (k in 0 until leadnoArrayList.length()) {
                        val jsonObject = leadnoArrayList.getJSONObject(k)
                        //if (textlength <= jsonObject.getString("Name").length) {
                            var searchValue = etsearch!!.text.toString().toLowerCase().trim()
                            Log.e(TAG,"searchValue  20633  "+searchValue)
                            if (jsonObject.getString("CustomeName")!!.toLowerCase().trim().contains(searchValue) || jsonObject.getString("LeadNo")!!.toLowerCase().trim().contains(searchValue)
                                || jsonObject.getString("MobileNo")!!.toLowerCase().trim().contains(searchValue)){
                                leadnoSort.put(jsonObject)
                            }

                        //}
                    }



                    if (leadnoSort.length() <= 0){
                        recyLeadNo!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyLeadNo!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
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

    private fun getOtherCharges() {
        var ReqMode = "117"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherchargesViewModel.getOthercharge(this,ReqMode)!!.observe(
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
                                        val jobjt = jObject.getJSONObject("ProjectOtherChargeDetails")
                                        otherChargeArrayList = jobjt.getJSONArray("ProjectOtherChargeDetailsList")
                                        if (otherChargeArrayList.length()>0){
                                            var gridList = Config.getTransType(this@ProjectSiteVisitActivity)
                                            val jObject1 = JSONObject(gridList)
                                            Log.e(TAG,"gridList  2111   "+gridList)
                                            val jobjt1 = jObject1.getJSONObject("TransType")
                                            var typeArrayList = jobjt1.getJSONArray("TransTypeDetails")

                                            for (i in 0 until otherChargeArrayList.length()) {
                                                var jsonObject = otherChargeArrayList.getJSONObject(i)

                                                var ID_TransType = ""
                                                var TransType_Name = ""

                                                if (jsonObject.getString("OctyTransTypeActive").equals("0")){
                                                    val jsonObject = typeArrayList.getJSONObject(0)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }
                                                else if (jsonObject.getString("OctyTransTypeActive").equals("1")){
                                                    val jsonObject = typeArrayList.getJSONObject(1)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }
                                                else if (jsonObject.getString("OctyTransTypeActive").equals("2")){
                                                    val jsonObject = typeArrayList.getJSONObject(0)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }

                                                modelOtherCharges!!.add(ModelOtherCharges(jsonObject.getString("ID_OtherChargeType"),jsonObject.getString("OctyName"),
                                                    jsonObject.getString("OctyTransTypeActive"),jsonObject.getString("OctyTransType"),jsonObject.getString("FK_TaxGroup"),
                                                    jsonObject.getString("OctyAmount"), jsonObject.getString("OctyTaxAmount"),false,
                                                    ID_TransType, TransType_Name,false,false))

                                                modelOtherChargesTemp!!.add(ModelOtherChargesTemp(jsonObject.getString("ID_OtherChargeType"),jsonObject.getString("OctyName"),
                                                    jsonObject.getString("OctyTransTypeActive"),jsonObject.getString("OctyTransType"),jsonObject.getString("FK_TaxGroup"),
                                                    jsonObject.getString("OctyAmount"), jsonObject.getString("OctyTaxAmount"),false,
                                                    ID_TransType,
                                                    TransType_Name,false,false))
                                            }
                                        }

                                        otherChargesPopup(modelOtherChargesTemp)
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

    private fun otherChargesPopup(modelOtherChargesTemp : ArrayList<ModelOtherChargesTemp>) {
        try {

            dialogOtherChargesSheet = Dialog(this)
            dialogOtherChargesSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogOtherChargesSheet!! .setContentView(R.layout.other_charges_popup)
            dialogOtherChargesSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogOtherChargesSheet!!.setCancelable(true)

            var recycOtherCharges = dialogOtherChargesSheet!! .findViewById(R.id.recycOtherCharges) as RecyclerView
            var txt_close = dialogOtherChargesSheet!! .findViewById(R.id.txt_close) as TextView
            var txt_apply = dialogOtherChargesSheet!! .findViewById(R.id.txt_apply) as TextView


            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recycOtherCharges!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            otherChargeAdapter = OtherChargeAdapter(this@ProjectSiteVisitActivity, modelOtherChargesTemp)
            recycOtherCharges!!.adapter = otherChargeAdapter
            otherChargeAdapter!!.setClickListener(this@ProjectSiteVisitActivity)
            otherChargeAdapter!!.setClickListener(this@ProjectSiteVisitActivity)

            txt_close!!.setOnClickListener {
                dialogOtherChargesSheet!!.dismiss()
            }

            txt_apply!!.setOnClickListener {

                var hasCalulate = hasCalulateMethod(modelOtherChargesTemp)
                if (hasCalulate){
                    var otherCharge = 0F
                    for (i in 0 until modelOtherChargesTemp.size) {

                        try {

                            if ( modelOtherChargesTemp[i].isCalculate){
                                modelOtherCharges[i].ID_OtherChargeType = modelOtherChargesTemp[i].ID_OtherChargeType
                                modelOtherCharges[i].OctyName = modelOtherChargesTemp[i].OctyName
                                modelOtherCharges[i].OctyTransTypeActive = modelOtherChargesTemp[i].OctyTransTypeActive
                                modelOtherCharges[i].OctyTransType = modelOtherChargesTemp[i].OctyTransType
                                modelOtherCharges[i].FK_TaxGroup = modelOtherChargesTemp[i].FK_TaxGroup
                                modelOtherCharges[i].OctyAmount = modelOtherChargesTemp[i].OctyAmount
                                modelOtherCharges[i].OctyTaxAmount = modelOtherChargesTemp[i].OctyTaxAmount
                                modelOtherCharges[i].OctyIncludeTaxAmount = modelOtherChargesTemp[i].OctyIncludeTaxAmount
                                modelOtherCharges[i].ID_TransType = modelOtherChargesTemp[i].ID_TransType
                                modelOtherCharges[i].TransType_Name = modelOtherChargesTemp[i].TransType_Name
                                modelOtherCharges[i].isCalculate = modelOtherChargesTemp[i].isCalculate
                                modelOtherCharges[i].isTaxCalculate = modelOtherChargesTemp[i].isTaxCalculate
                            }

                        }catch (e: Exception){
                            Log.e(TAG,"12548  "+e.toString())
                        }


                        if (modelOtherChargesTemp[i].isCalculate){
                            if (modelOtherChargesTemp[i].OctyIncludeTaxAmount){
                                if (modelOtherChargesTemp[i].ID_TransType.equals("1")){
                                    // Credit
                                    otherCharge = otherCharge - (modelOtherChargesTemp[i].OctyAmount).toFloat()
                                }else if (modelOtherChargesTemp[i].ID_TransType.equals("2")){
                                    // Debit
                                    otherCharge = otherCharge + (modelOtherChargesTemp[i].OctyAmount).toFloat()
                                }
                            }else{
                                if (modelOtherChargesTemp[i].ID_TransType.equals("1")){
                                    // Credit

                                    otherCharge = otherCharge - ((modelOtherChargesTemp[i].OctyAmount).toFloat()+(modelOtherChargesTemp[i].OctyTaxAmount).toFloat())
                                }else if (modelOtherChargesTemp[i].ID_TransType.equals("2")){
                                    // Debit
                                    otherCharge = otherCharge + ((modelOtherChargesTemp[i].OctyAmount).toFloat()+(modelOtherChargesTemp[i].OctyTaxAmount).toFloat())
                                }
                            }
                        }
                    }

                    tie_Othercharges!!.setText(Config.changeTwoDecimel(otherCharge.toString()))
                    dialogOtherChargesSheet!!.dismiss()
                }else{
                    Config.snackBars(context,it,"Enter atleast one transactions")
                    Log.e(TAG,"163111   Enter atleast one transactions")
                }


            }


            dialogOtherChargesSheet!!.show()
            val window: Window? = dialogOtherChargesSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.white);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        }catch (e : Exception){

            Log.e(TAG,"3856  "+e)
        }
    }

    private fun hasCalulateMethod(modelOtherChargesTemp: ArrayList<ModelOtherChargesTemp>): Boolean {

        var result = false
        for (i in 0 until modelOtherChargesTemp.size) {  // iterate through the JsonArray
            if (modelOtherChargesTemp[i].isCalculate){
                result = true
            }
        }
        return result
    }

    private fun getOtherChargeTax() {
        var ReqMode = "118"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherChargeTaxCalculationViewModel.getOtherChargeTaxCalculation(this,ReqMode!!,FK_TaxGroup,IncludeTaxCalc,AmountTaxCalc)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (otherchargetaxcount == 0){
                                    otherchargetaxcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   6733332   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("OtherChargeTaxCalculationDetails")
                                        otherChargeCalcArrayList = jobjt.getJSONArray("OtherChargeTaxCalculationDetailsList")

                                        Log.e(TAG,"67333321   "+otherChargeCalcArrayList.length())
                                        if (otherChargeCalcArrayList.length() > 0){
                                            if (otherchargetaxMode == 0){
                                                otherChargesCalcPopup(otherChargeCalcArrayList)
                                            }else{
                                                Log.e(TAG,"38777  ")
                                                var sumOfTax = 0F
                                                for (i in 0 until otherChargeCalcArrayList.length()) {
                                                    val jsonObject2 = otherChargeCalcArrayList.getJSONObject(i)


                                                    Log.e(TAG,"40551  "+jsonObject2.getString("Amount"))
                                                    sumOfTax = sumOfTax +(jsonObject2.getString("Amount")).toFloat()

                                                }

                                                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                                                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)

                                                Log.e(TAG,"40552     "+sumOfTax)
//                                                modelOtherCharges[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
//
//                                                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
//                                                dialogOtherChargesCalcSheet!!.dismiss()
                                            }

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
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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

    private fun otherChargesCalcPopup(otherChargeCalcArrayList: JSONArray) {
        try {


            dialogOtherChargesCalcSheet = Dialog(this)
            dialogOtherChargesCalcSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogOtherChargesCalcSheet!! .setContentView(R.layout.list_other_charge_popup)
            dialogOtherChargesCalcSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogOtherChargesCalcSheet!!.setCancelable(false)
            recyOtherCalc = dialogOtherChargesCalcSheet!! .findViewById(R.id.recyOtherCalc) as RecyclerView
            var btnOk = dialogOtherChargesCalcSheet!! .findViewById(R.id.btnOk) as Button

            var hasId1 =  hasOtherChecked(modelOtherChargesCalculation!!,FK_TaxGroup)
            Log.e(TAG,"3199   "+hasId1)
            if (hasId1){
                Log.e(TAG,"31991   "+hasId1)

                for (i in 0 until otherChargeCalcArrayList.length()) {

                    val jsonObject = otherChargeCalcArrayList.getJSONObject(i)
                    var empModel = modelOtherChargesCalculation[otherChargeCalcPosition]
                    empModel.FK_TaxGroup = FK_TaxGroup
                    empModel.ID_TaxSettings = jsonObject.getString("ID_TaxSettings")
                    empModel.FK_TaxType = jsonObject.getString("FK_TaxType")
                    empModel.TaxTyName = jsonObject.getString("TaxTyName")
                    empModel.TaxPercentage = jsonObject.getString("TaxPercentage")
                    empModel.TaxtyInterstate = jsonObject.getBoolean("TaxtyInterstate")
                    empModel.TaxUpto = jsonObject.getString("TaxUpto")
                    empModel.TaxUptoPercentage = jsonObject.getBoolean("TaxUptoPercentage")
                    empModel.Amount = jsonObject.getString("Amount")

                }

            }else{
                Log.e(TAG,"31992   "+hasId1)
                for (i in 0 until otherChargeCalcArrayList.length()) {
                    val jsonObject = otherChargeCalcArrayList.getJSONObject(i)
                    modelOtherChargesCalculation!!.add(
                        ModelOtherChargesCalculation(FK_TaxGroup,jsonObject.getString("ID_TaxSettings"),jsonObject.getString("FK_TaxType"),
                            jsonObject.getString("TaxTyName"),jsonObject.getString("TaxPercentage"),jsonObject.getBoolean("TaxtyInterstate"),
                            jsonObject.getString("TaxUpto"),jsonObject.getBoolean("TaxUptoPercentage"),jsonObject.getString("Amount"))
                    )
                }




            }

            val lLayout = GridLayoutManager(this@ProjectSiteVisitActivity, 1)
            recyOtherCalc!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            taxDetailAdapter = TaxDetailAdapter(this@ProjectSiteVisitActivity, modelOtherChargesCalculation)
            recyOtherCalc!!.adapter = taxDetailAdapter
            taxDetailAdapter!!.setClickListener(this@ProjectSiteVisitActivity)

            btnOk.setOnClickListener {
                var sumOfTax = 0F
                for (i in 0 until modelOtherChargesCalculation.size) {
                    if (modelOtherChargesCalculation[i].FK_TaxGroup.equals(FK_TaxGroup)){

                        Log.e(TAG,"40551  "+modelOtherChargesCalculation[i].Amount)
                        sumOfTax = sumOfTax +(modelOtherChargesCalculation[i].Amount).toFloat()
                    }
                }

                Log.e(TAG,"40552     "+sumOfTax)
                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                modelOtherChargesTemp[otherChargeClickPosition].isTaxCalculate = true

                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
                dialogOtherChargesCalcSheet!!.dismiss()
            }

            dialogOtherChargesCalcSheet!!.show()
            dialogOtherChargesCalcSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hasOtherChecked(modelOtherChargesCalculation: ArrayList<ModelOtherChargesCalculation>, FK_TaxGroup: String): Boolean {
        var isChecked = false
        for (i in 0 until modelOtherChargesCalculation.size) {
            if (modelOtherChargesCalculation.get(i).FK_TaxGroup.equals(FK_TaxGroup)){
                isChecked = true
                otherChargeCalcPosition = i
                break
            }
        }
        return isChecked
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
            val txt_nodata = dialogDepartment!! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Inavalid Department"

            departmentSort = JSONArray()
            for (k in 0 until departmentArrayList.length()) {
                val jsonObject = departmentArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                departmentSort.put(jsonObject)
            }

            if (departmentSort.length() <= 0){
                recyDeaprtment!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
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

                    if (departmentSort.length() <= 0){
                        recyDeaprtment!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyDeaprtment!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
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
            val txt_nodata = dialogEmployee!! .findViewById(R.id.txt_nodata) as TextView
            txt_nodata.text = "Invalid Employee"

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            if (employeeSort.length() <= 0){
                recyEmployee!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
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

                    if (employeeSort.length() <= 0){
                        recyEmployee!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyEmployee!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
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

            val txt_nodata = dialogWorkType!! .findViewById(R.id.txt_nodata) as TextView
            txt_nodata.text = "Invalid Work Type"


            worktypeSort = JSONArray()
            for (k in 0 until worktypeArrayList.length()) {
                val jsonObject = worktypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                worktypeSort.put(jsonObject)
            }

            if (worktypeSort.length() <= 0){
                recyWorkType!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
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

                    if (worktypeSort.length() <= 0){
                        recyWorkType!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyWorkType!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
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
            val txt_nodata = dialogWorkType!! .findViewById(R.id.txt_nodata) as TextView
            txt_nodata.text = "Invalid Measurement Type"

            measurementSort = JSONArray()
            for (k in 0 until worktypeArrayList.length()) {
                val jsonObject = worktypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                measurementSort.put(jsonObject)
            }

            if (measurementSort.length() <= 0){
                recyMeasurementType!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
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

                    if (measurementSort.length() <= 0){
                        recyMeasurementType!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyMeasurementType!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
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


    private fun selectImage() {
        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= 33) {
                    //ActivityCompat.requestPermissions(this,String[]{readMediaAudio},PERMISSION_CODE)
                    Log.e(TAG, "222399912   ")
                    if (Config.check13Permission(context)) {
                        Log.e(TAG, "222399913   ")

                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    // browseDocuments()
                                    //  browseFolders()

                                    pickDocument()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
//                    ActivityCompat.requestPermissions(this, arrayOf(readMediaAudio,readMediaImages,readMediaVideo), PERMISSION_CODE)


                } else {
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
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(
                                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                            )
                        }
                    } else {
                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    // browseDocuments()
                                    //  browseFolders()

                                    pickDocument()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
                }

            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun browseFolders() {
        val folderPath = Environment.getExternalStorageDirectory().absolutePath // or specify your folder path
        val folder = File(folderPath)

        if (folder.exists() && folder.isDirectory) {
            val folders = folder.listFiles { file ->
                file.isDirectory
            }

            // Now 'folders' contains only the directories in the folder
            for (folder in folders) {
                // Process each folder as needed
                // Example: Log the folder name
                Log.d("Folder", folder.name)
            }
        } else {
            // Handle the case when the folder doesn't exist or is not a directory
        }
    }

     private fun pickDocument() {

//         val mimetypes = arrayOf(
//             "jpeg/*",
//             "jpg/*",
//             "png/*",
//             "docx/*",
//             "doc/*",
//             "xlsx/*",
//             "xls/*",
//             "pdf/*"
//         )

         val mimetypes = arrayOf(
             "image/*",
             "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
             "application/pdf"
         )

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"  // All file types
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)

        startActivityForResult(intent, PICK_DOCUMENT_REQUEST_CODE)
    }


    private fun browseDocuments() {


        val mimetypes = arrayOf(
            "jpg/*",
            "jpeg/*",
            ".pdf/*",
            ".doc/*",
            ".docx/*",
            ".xls/*",
            ".xlsx/*"
        )

//        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //   intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//        val uri = Uri.parse(Environment.getExternalStorageDirectory().path + File.separator)
//        val file = DocumentFile.fromTreeUri(context, uri)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes) //Important part here
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
      //  intent.putExtra(Intent.EXTRA_INITIAL_URI, file!!.getUri());
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, PICK_DOCUMRNT_GALLERY)
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
        inputStreamImg = null
        if (requestCode == PICK_IMAGE_CAMERA) {

            try {
                if (data != null) {
                    try {
//                        if (ContextCompat.checkSelfPermission(
//                                this,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            ) != PackageManager.PERMISSION_GRANTED
//                        ) {
//                            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                                    this,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                )
//                            ) {
//                                // Show an explanation to the user *asynchronously* -- don't block
//                                // this thread waiting for the user's response! After the user
//                                // sees the explanation, try again to request the permission.
//
//                            } else {
//                                // No explanation needed; request the permission
//                                ActivityCompat.requestPermissions(
//                                    this,
//                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
//                                )
//                            }
//                        } else {

                            Log.e(TAG,"3961   "+data)
                            Log.e(TAG,"3962   "+data!!.getExtras()!!.get("data"))

                            val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                            val bytes = ByteArrayOutputStream()
                            thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                            val fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
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


                                destination = File(
                                    (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" +
                                            "",
                                    fileName
                                )
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


                            imgPath = destination!!.getAbsolutePath()
                            Log.e(TAG, "imgPath  20522    " + imgPath)
                            destination = File(imgPath)
                            documentPath = imgPath!!
                            //txtAttachmentPath!!.setText(imgPath)
                            //  tie_Attachment!!.setText(imgPath)
                            tie_Attachments!!.setText(fileName)


                      //  }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@ProjectSiteVisitActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {

            }

        }
        else if (requestCode == PICK_IMAGE_GALLERY) {
            if (data != null) {
                val selectedImage = data.data
                try {
                    val fileName = UriUtil.getFileName(this,data!!.data!!)
                    Log.e(TAG,"561 fileName :   "+fileName)
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    val bytes = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
                    imgPath = getRealPathFromURI(selectedImage!!)
                    destination = File(imgPath.toString())
                    documentPath = imgPath!!
                   // txtAttachmentPath!!.setText(imgPath)
                    tie_Attachments!!.setText(fileName)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "No image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        }
        else if (requestCode == PICK_DOCUMRNT_GALLERY) {
            try {
                if (data != null) {
                    val uri = data.data
                    Log.e(TAG,"561  uri "+uri)
                    Log.e(TAG,"561 data  "+data)

//                    OLD
                    //    selectedFilePath = UriUtil.fileFromContentUri(this, uri!!).toString()
                    //   selectedFilePath = UriUtil.getPath(this, uri!!).toString()
                    //   selectedFilePath = getRealPathFromURI(uri)


//                    selectedFilePath = getRealPathFromURI(uri)
////                    selectedFilePath = getRealPathUri(uri)
//                    Log.e(TAG,"561  selectedFilePath   "+selectedFilePath)
//                    destination = File(selectedFilePath.toString())
//                    Log.e(TAG,"561 destination  "+destination)
//                    documentPath = selectedFilePath!!
//                    Log.e(TAG,"561 documentPath   "+documentPath)
//                    txtAttachmentPath!!.setText(selectedFilePath)
//                    tie_Attachment!!.setText(selectedFilePath)
//
//                    22.02.2023

                    val fileName = UriUtil.getFileName(this,data!!.data!!)
                    val filepath= UriUtil.fileFromContentUri(this,data!!.data!!,fileName!!)  // WORKING 22.02.2023
                    tie_Attachments!!.setText(fileName)
                    Log.e(TAG,"561 filepath :   "+filepath)
                    documentPath = filepath.toString()

//                    val map: MimeTypeMap = MimeTypeMap.getSingleton()
//                    val ext: String = MimeTypeMap.getFileExtensionFromUrl(filepath.name)
//                    var type: String? = map.getMimeTypeFromExtension(ext)
//                    if (type == null)
//                        type = "*/*";
//
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    val data: Uri = Uri.fromFile(filepath)
//                    intent.setDataAndType(data, type)
//                    startActivity(intent)

                } else {
                    Toast.makeText(this, "No Document selected", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Log.e(TAG,"561   "+e.toString())
            }

        }

        else if (requestCode == PICK_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { documentUri ->
                // Perform the upload operation with the selected document URI

                val fileName = UriUtil.getFileName(this,data!!.data!!)
                val filepath= UriUtil.fileFromContentUri(this,data!!.data!!,fileName!!)  // WORKING 22.02.2023
                tie_Attachments!!.setText(fileName)
                Log.e(TAG,"561 filepath :   "+filepath)
                documentPath = filepath.toString()

                uploadDocument(documentUri)
            }
        }
    }

    private fun uploadDocument(documentUri: Uri) {
        val documentFile = DocumentFile.fromSingleUri(this, documentUri)

        if (documentFile != null && documentFile.exists()) {
            // Get information about the selected document
            val documentName = documentFile.name
            val documentSize = documentFile.length()

            Log.d("Document Info", "Name: $documentName, Size: $documentSize bytes")

            // Perform your upload logic here
            // Example: Upload the document to a server, etc.
        } else {
            Log.e("Error", "Unable to access the selected document.")
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
            strID_FIELD = jsonObject.getString("ID_LeadGenerate")
            ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
            ID_SiteVisitAssignment = jsonObject.getString("ID_SiteVisitAssignment")
            tie_InspectionCharge!!.setText(jsonObject.getString("ExpenseAmount"))

        }

        if (data.equals("InspectionTypeClick")){
            dialogInspectionType!!.dismiss()
            val jsonObject = inspectionTypeArrayList.getJSONObject(position)
            Log.e(TAG,"43099   "+jsonObject)
            tie_InspectionType!!.setText(jsonObject.getString("InspectionType"))
            ID_InspectionType = jsonObject.getString("ID_InspectionType")

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

            var hasCheck =  hasMeasurement(modelMesurementDetails!!,WorkTypeID,jsonObject.getString("MeasurementTypeID"))
            Log.e(TAG,"hasCheck  3686 "+hasCheck )
            if (hasCheck){
                Log.e(TAG,"MeasurementUnit 14561  "+jsonObject.getString("MeasurementType"))
                tie_MeasurementType!!.setText(jsonObject.getString("MeasurementType"))
                ID_MeasurementUnit = jsonObject.getString("MeasurementTypeID")
            }else{


                try {
                    val dialog = BottomSheetDialog(this)
                    val view = layoutInflater.inflate(R.layout.alert_delete, null)

                    val btnNo = view.findViewById<Button>(R.id.btn_No)
                    val btnYes = view.findViewById<Button>(R.id.btn_Yes)
                    val textid1 = view.findViewById<TextView>(R.id.textid1)

                    textid1!!.setText(""+strWarningMessage)
                    btnNo.setOnClickListener {
                        dialog .dismiss()

                    }
                    btnYes.setOnClickListener {
                        dialog .dismiss()
                        Log.e(TAG,"MeasurementUnit  14562   "+jsonObject.getString("MeasurementType"))
                        tie_MeasurementType!!.setText(jsonObject.getString("MeasurementType"))
                        ID_MeasurementUnit = jsonObject.getString("MeasurementTypeID")
                    }
                    dialog.setCancelable(true)
                    dialog!!.setContentView(view)

                    dialog.show()
                }catch (e : Exception){

                }
            }




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

        if (data.equals("IncludeTaxClick")){

            Log.e(TAG,"IncludeTaxClick  6733   "+ modelOtherCharges[position].OctyIncludeTaxAmount)
//            if (modelOtherCharges[position].OctyIncludeTaxAmount){
////                FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
////                otherchargetaxcount = 0
////                getOtherChargeTax()
//            }else{
//
//            }
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }

            otherchargetaxcount = 0
            otherchargetaxMode = 1
            getOtherChargeTax()

        }

        if (data.equals("IncludeTaxAmountClick")){

            Log.e(TAG,"IncludeTaxAmountClick  8288   "+ modelOtherCharges[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            otherchargetaxcount = 0
            otherchargetaxMode = 0
            getOtherChargeTax()
        }

        if (data.equals("TaxAmountClaculateClick")){

            Log.e(TAG,"TaxAmountClaculateClick  82881   "+ modelOtherChargesTemp[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup

            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount || modelOtherChargesTemp[position].isTaxCalculate){
//                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                //   modelOtherChargesTemp[position].isTaxCalculate = false
                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
            }else{
                otherchargetaxcount = 0
                otherchargetaxMode = 1

                getOtherChargeTax()
            }
        }


    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}