package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import com.perfect.prodsuit.scanners.BarcodeScannerActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class LeadGenerationQuickActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {
    lateinit var cusNameSort: JSONArray
    val TAG: String = "LeadGenerationQuickActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var txtDate: TextView? = null
    private var tv_Mrp: EditText? = null
    private var txtLocation: TextView? = null

    private var actv_nammob: AutoCompleteTextView? = null
    private var actv_namTitle: AutoCompleteTextView? = null

    private var edt_customer: EditText? = null
    private var edtCustname: EditText? = null
    private var edtCustphone: EditText? = null
    private var edtCustAddres2: EditText? = null
//
    private var edtProdcategory: EditText? = null
    private var edtProdproduct: EditText? = null
    private var edtProjectName: EditText? = null
    private var edtProdqty: EditText? = null
    private var edtAmount: EditText? = null  // offer Price
    private var edtEmployee: EditText? = null
    private var edtProdfeedback: EditText? = null
    private var edtFollowaction: EditText? = null
    private var edtFollowtype: EditText? = null
    private var edtFollowdate: EditText? = null
    private var edtExpecteddate: EditText? = null
    private var edtProdpriority: EditText? = null
//
    private var img_search: ImageView? = null
    private var imvContactbook: ImageView? = null
    private var edtScan: ImageView? = null
//    private var refresh_btn: ImageView? = null
    private var imgvupload1: ImageView? = null
    private var imgvupload2: ImageView? = null
    private var imgClose1: ImageView? = null
    private var imgClose2: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    private val WRITE = 3
    private val READ = 4

    private var ll_product_qty: LinearLayout? = null
    private var llfollowup: LinearLayout? = null
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private var image1 = ""
    private var image2 = ""
    private var strImage: String? = null
    private var destination: File? = null

    var listview: ListView? = null
    private var cursor: Cursor? = null
    var dialogContact: Dialog? = null
    var idsearch_contact: SearchView? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    var SubModeSearch: String? = ""
    var searchType = arrayOf<String>()
    var searchNameTitle = arrayOf<String>()

    var priorityMode = 0
    var followUpActionMode = 0
    var followUpTypeMode = 0

    lateinit var leadGenerateDefaultvalueViewModel: LeadGenerationDefaultvalueViewModel

    var prodcategory = 0
    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategoryArrayList: JSONArray
    lateinit var prodCategorySort: JSONArray
    private var dialogProdCat: Dialog? = null
    var recyProdCategory: RecyclerView? = null

    var proddetail = 0
    var proddetailMode = 0
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    private var dialogProdDet: Dialog? = null
    var recyProdDetail: RecyclerView? = null

    var prodpriority = 0
    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var prodPriorityArrayList: JSONArray
    lateinit var prodPrioritySort: JSONArray
    private var dialogProdPriority: Dialog? = null
    var recyProdPriority: RecyclerView? = null

    var followUpAction = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList: JSONArray
    lateinit var followUpActionSort: JSONArray
    private var dialogFollowupAction: Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    var followUpType = 0
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList: JSONArray
    lateinit var followUpTypeSort: JSONArray
    private var dialogFollowupType: Dialog? = null
    var recyFollowupType: RecyclerView? = null

    var dateSelectMode = 0

    var employee = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null

    private var barcodeCount = 0
    lateinit var itemSearchListViewModel: ItemSearchListViewModel

    var custDet = 0
    lateinit var customersearchViewModel: CustomerSearchViewModel
    lateinit var customerArrayList: JSONArray
    var dialogCustSearch: Dialog? = null

    var prodstatus = 0

    var modeProjectProduct: String? = "0"

    var ID_Category: String? = ""
    var ID_Product: String? = ""
    var ID_Priority: String? = ""
    var ID_NextAction: String = ""
    var ID_Status: String? = ""
    var ID_ActionType: String = ""
    var ID_LeadFrom: String? = ""
    var ID_LeadThrough: String? = ""
    var ID_MediaSubMaster: String? = ""

    var strQty: String = ""
    var MRP = ""
    var MRRP = ""
    var Offerprice = ""
    var strFollowupdate: String = ""
    var strExpecteddate: String = ""
    var Project = ""
    var strDate: String = ""

    var saveAttendanceMark = false
    var saveLeadGenDet = 0
    var CompanyCategory = ""

    var ID_Customer: String? = ""
    var ID_CustomerAssignment: String? = ""
    var strCustomer = ""
    var ID_CollectedBy: String? = ""
    var ID_Employee: String? = ""
    var ID_BranchType: String = ""
    var ID_Branch: String = ""
    var ID_Department: String = ""
    var FK_Country: String = "0"
    var FK_States: String = "0"
    var FK_District: String = "0"
    var FK_Area: String = "0"
    var FK_Place: String = "0"
    var FK_Post: String = "0"
    var strPincode = ""
    var strLeadThrough: String = ""
    var Customer_Mode: String? = "0"
    var Customer_Name: String? = ""
    var Customer_Mobile: String? = ""
    var Customer_Email: String? = ""
    var Customer_Address1: String? = ""
    var Customer_Address2: String? = ""
    var strComapnyName: String? = ""
    var strContactPerson: String? = ""
    var strContactNumber: String? = ""
    var CusNameTitle: String? = ""
    var strWhatsAppNo: String = ""
    var strCompanyContact: String = ""
    var strProduct: String = ""
    var strProject: String = ""
    var strFeedback: String = ""
    var strMrp: String = ""
    var strOfferPrice: String = ""
    var stramount: String = ""

    var saveUpdateMode: String = "1"
    var ID_LeadGenerate: String = "0"

    var strLatitude: String = ""
    var strLongitue: String = ""
    var encode1: String = ""
    var encode2: String = ""
    var Customer_Type: String = ""
    var ID_AuthorizationData = ""

    var ProductMRP = ""
    var editableMrp: String? = ""


    private var array_product_lead = JSONArray()
    lateinit var leadGenerateSaveViewModel: LeadGenerateSaveViewModel
    lateinit var saveLeadGenArrayList: JSONArray
    var CAMERA_PERMISSION_REQUEST_CODE = 100

    val multiplePermissionId = 14

     private val multiplePermissionList = if (Build.VERSION.SDK_INT >= 33) {
         arrayOf(
             Manifest.permission.READ_MEDIA_VIDEO,
             Manifest.permission.READ_MEDIA_AUDIO,
             Manifest.permission.READ_MEDIA_IMAGES,
             // Add other permissions as needed
         )
     }
    else{
        arrayOf(
             Manifest.permission.WRITE_EXTERNAL_STORAGE,
             Manifest.permission.READ_EXTERNAL_STORAGE
         )
     }

    var PERMISSION_CODE = 100
    var readMediaAudio = Manifest.permission.READ_MEDIA_AUDIO
    var readMediaVideo = Manifest.permission.READ_MEDIA_VIDEO
    var readMediaImages = Manifest.permission.READ_MEDIA_IMAGES


//    private val permissions = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        // Add other permissions as needed
//    )
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lead_generation_quick)
        window.decorView.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        context = this@LeadGenerationQuickActivity

        leadGenerateDefaultvalueViewModel = ViewModelProvider(this).get(LeadGenerationDefaultvalueViewModel::class.java)
        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        itemSearchListViewModel = ViewModelProvider(this).get(ItemSearchListViewModel::class.java)
        leadGenerateSaveViewModel = ViewModelProvider(this).get(LeadGenerateSaveViewModel::class.java)

        val EditMRPLeadSP = context.getSharedPreferences(Config.SHARED_PREF78, 0)
        editableMrp = EditMRPLeadSP.getString("EditMRPLead","0")

        setRegViews()
        getDefaultValueSettings()
        checkAttendance()

        proddetailMode = 0
        proddetail = 0
        getProductDetail("0")

        prodpriority = 0
        priorityMode = 0
        getProductPriority()

        followUpActionMode = 0
        followUpAction = 0
        getFollowupAction()

        followUpTypeMode = 0
        followUpType = 0
        getFollowupType()


//        checkCameraPermissions()
//        checkAndRequestStoragePermission()

      //  checkAndRequestPermissions()
        if(checkAndRequestPermissions1()) {
            // carry on the normal flow, as the case of  permissions  granted.
            Log.e(TAG,"325551     Granted")
        }else{
            Log.e(TAG,"325552     Not Granted")
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun checkAndRequestPermissions1(): Boolean {
        val readVideo = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO)
        val readAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
        val readImages = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)

        val listPermissionsNeeded: List<String> = ArrayList()


        if (!multiplePermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, multiplePermissionList,multiplePermissionId);
            return false
        }
        return true

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_Mrp = findViewById<EditText>(R.id.tv_Mrp)

        actv_namTitle = findViewById<AutoCompleteTextView>(R.id.actv_namTitle)
        actv_nammob = findViewById<AutoCompleteTextView>(R.id.actv_nammob)

        edt_customer = findViewById<EditText>(R.id.edt_customer)
        edtCustname = findViewById<EditText>(R.id.edtCustname)
        edtCustphone = findViewById<EditText>(R.id.edtCustphone)
        edtCustAddres2 = findViewById<EditText>(R.id.edtCustAddres2)
        edtProdcategory = findViewById<EditText>(R.id.edtProdcategory)
        edtProdproduct = findViewById<EditText>(R.id.edtProdproduct)
        edtProjectName = findViewById<EditText>(R.id.edtProjectName)
        edtProdqty = findViewById<EditText>(R.id.edtProdqty)
        edtAmount = findViewById<EditText>(R.id.edtAmount)
        edtProdpriority = findViewById<EditText>(R.id.edtProdpriority)
        edtFollowaction = findViewById<EditText>(R.id.edtFollowaction)
        edtFollowtype = findViewById<EditText>(R.id.edtFollowtype)
        edtFollowdate = findViewById<EditText>(R.id.edtFollowdate)
        edtExpecteddate = findViewById<EditText>(R.id.edtExpecteddate)
        edtEmployee = findViewById<EditText>(R.id.edtEmployee)
        edtProdfeedback = findViewById<EditText>(R.id.edtProdfeedback)

        ll_product_qty = findViewById<LinearLayout>(R.id.ll_product_qty)
        llfollowup = findViewById<LinearLayout>(R.id.llfollowup)

        img_search = findViewById<ImageView>(R.id.img_search)
        imvContactbook = findViewById<ImageView>(R.id.imvContactbook)
        edtScan = findViewById<ImageView>(R.id.edtScan)

        imgvupload1 = findViewById(R.id.imgv_upload1)
        imgvupload2 = findViewById(R.id.imgv_upload2)
        imgClose1 = findViewById(R.id.imgClose1)
        imgClose2 = findViewById(R.id.imgClose2)

        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        edtProdcategory!!.setOnClickListener(this)
        edtProdproduct!!.setOnClickListener(this)
        edtProdpriority!!.setOnClickListener(this)
        edtFollowaction!!.setOnClickListener(this)
        edtFollowtype!!.setOnClickListener(this)
        edtFollowdate!!.setOnClickListener(this)
        edtExpecteddate!!.setOnClickListener(this)
        edtEmployee!!.setOnClickListener(this)

        img_search!!.setOnClickListener(this)
        imvContactbook!!.setOnClickListener(this)
        imgvupload1!!.setOnClickListener(this)
        imgvupload2!!.setOnClickListener(this)
        imgClose1!!.setOnClickListener(this)
        imgClose2!!.setOnClickListener(this)
        edtScan!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)


        searchNameTitle = arrayOf("Mr. ", "Mrs. ", "Miss. ", "M/s. ", "Dr. ", "Ms. ", "Fr. ", "Sr. ")
        detailsShowing()
        setLabelbyCompany()
    }

    private fun detailsShowing() {
        SubModeSearch = "1"
        searchType = resources.getStringArray(R.array.array_spinner)
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        actv_nammob!!.setAdapter(adapter)
        actv_nammob!!.setText(searchType.get(0), false)
        actv_nammob!!.setOnClickListener {
            actv_nammob!!.showDropDown()
        }
        actv_nammob!!.setOnItemClickListener { parent, view, position, id ->
            Log.e(TAG, "info: $position $id" + "   " + searchType.get(position))
            if (position == 0) {
                SubModeSearch = "1"
                edt_customer!!.setInputType(InputType.TYPE_CLASS_TEXT)
                edt_customer!!.setText("")



            }
            if (position == 1) {
                SubModeSearch = "2"
                edt_customer!!.setInputType(InputType.TYPE_CLASS_NUMBER)
                edt_customer!!.setText("")
                edt_customer!!.filters = arrayOf(InputFilter.LengthFilter(15))
            }

        }

        Log.e(TAG, "1542    " + searchNameTitle.get(0).length + "  :   " + searchNameTitle.get(0))
        Log.e(TAG, "1542    " + searchNameTitle)
        val adapter1 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchNameTitle)
        actv_namTitle!!.setAdapter(adapter1)
        actv_namTitle!!.setText(searchNameTitle.get(0), false)
        actv_namTitle!!.setOnClickListener {
            actv_namTitle!!.showDropDown()
        }
        actv_namTitle!!.setOnItemClickListener { parent, view, position, id ->
            Log.e(TAG, "info: $position $id" + "   " + searchNameTitle.get(position))

        }


    }

    private fun setLabelbyCompany() {

        val CompanyCategorySP = applicationContext.getSharedPreferences(Config.SHARED_PREF46, 0)
        CompanyCategory = CompanyCategorySP.getString("CompanyCategory", "").toString()

        Log.e(TAG, "CompanyCategory  857   " + CompanyCategory)
        if (CompanyCategory.equals("0") || CompanyCategory.equals("1")) {

            edtProdproduct!!.setHint("Product")
            edtProdqty!!.setHint("Qty")
            ll_product_qty!!.orientation = LinearLayout.HORIZONTAL
            edtExpecteddate!!.visibility = View.GONE
            edtScan!!.visibility = View.VISIBLE
            edtProjectName!!.setHint("Model")
        } else if (CompanyCategory.equals("2")) {

            edtProdproduct!!.setHint("Destination")
            edtProdqty!!.setHint("No.of Passengers")
            ll_product_qty!!.orientation = LinearLayout.VERTICAL
            edtExpecteddate!!.visibility = View.VISIBLE
            edtScan!!.visibility = View.GONE
            edtProjectName!!.setHint("Destination")

            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 10, 0, 0)
            edtProdqty!!.setLayoutParams(params)
        }


    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance) {
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status", "")
            if (status.equals("0") || status.equals("")) {
                Common.punchingRedirectionConfirm(this, "", "")
            } else if (status.equals("1")) {
                saveAttendanceMark = true
            }

        } else {
            saveAttendanceMark = true
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }

            R.id.edtProdcategory -> {
                Config.disableClick(v)
                prodcategory = 0
                getCategory()
            }

            R.id.edtProdproduct -> {
                Config.disableClick(v)
                proddetailMode = 1
                proddetail = 0
                getProductDetail("0")
//                if (ID_Category.equals("")) {
//                    Config.snackBars(applicationContext, v, "Select Category")
//                }else{
//                    Config.disableClick(v)
//                    proddetail = 0
//                    getProductDetail(ID_Category!!)
//                }
            }
            R.id.edtProdpriority -> {
                Config.disableClick(v)
                Log.v("sdfsdfsd4fgf", "clicked")
                prodpriority = 0
                priorityMode = 1
                getProductPriority()
            }
            R.id.edtFollowaction -> {
                Config.disableClick(v)
                followUpAction = 0
                followUpActionMode = 1
                getFollowupAction()
            }

            R.id.edtFollowtype -> {
                Config.disableClick(v)
                followUpTypeMode = 1
                followUpType = 0
                getFollowupType()
            }

            R.id.edtFollowdate -> {
                Config.disableClick(v)
                dateSelectMode = 1
                openBottomSheet()
            }

            R.id.edtExpecteddate -> {
                dateSelectMode = 2
                openBottomSheet()
            }
            R.id.edtEmployee -> {

                Config.disableClick(v)
                employee = 0
                getEmployee()
                // }
            }

            R.id.imvContactbook -> {
                getContactRequest()
            }

            R.id.edtScan -> {
                Config.disableClick(v)
                val intent = Intent(this, BarcodeScannerActivity::class.java)
                startActivityForResult(intent, Config.SCANNER_CODE)

            }

            R.id.img_search -> {
                try {
                    strCustomer = edt_customer!!.text.toString()
                    if (strCustomer.equals("")) {
                        val snackbar: Snackbar =
                            Snackbar.make(v, "Enter Customer", Snackbar.LENGTH_LONG)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                        snackbar.show()

                    } else {
                        Config.disableClick(v)
                        custDet = 0
                        getCustomerSearch()
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Exception  64   " + e.toString())
                }
            }

            R.id.imgv_upload1 -> {
                try {
                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 23)
                    Log.e("TAG", "Exception  5465656   " )
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationQuickActivity, v)
                    strImage = "1"
                    showPictureDialog()
                } catch (e: java.lang.Exception) {
                    if (checkCamera()) {
                    } else {
                        requestPermission()
                    }
                }
            }
            R.id.imgv_upload2 -> {
                try {
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationQuickActivity, v)
                    strImage = "2"
                    showPictureDialog()


                } catch (e: java.lang.Exception) {
                    if (checkCamera()) {
                    } else {
                        requestPermission()
                    }
                }
            }

            R.id.imgClose1 -> {
                try {
                    image1 = ""
                    encode1 = ""
                    imgvupload1!!.setImageResource(R.drawable.lead_uploads)
                } catch (e: java.lang.Exception) {

                }
            }
            R.id.imgClose2 -> {
                try {

                    image2 = ""
                    encode2 = ""

                    imgvupload2!!.setImageResource(R.drawable.lead_uploads)
                } catch (e: java.lang.Exception) {

                }
            }

            R.id.btnSubmit -> {
                getCurrentDateNTime()
                checkAttendance()
                if (saveAttendanceMark) {
                    Config.disableClick(v)
                    saveLeadGenDet = 0
                    LeadValidations(v)
                }
            }

            R.id.btnReset -> {
                Config.disableClick(v)
                clearData()
            }



        }

    }

    private fun getDefaultValueSettings() {

        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        Log.e(TAG, " UserName  143     " + UserNameSP.getString("UserName", null))

        ID_CollectedBy = FK_EmployeeSP.getString("FK_Employee", null)
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        edtEmployee!!.setText(UserNameSP.getString("UserName", null))


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadGenerateDefaultvalueViewModel.getLeadGenerationDefaultvalue(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.v("hkhkhkjjj", "in")
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   14781   " + msg.length)
                            Log.e(TAG, "msg   14782   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt =
                                    jObject.getJSONObject("LeadGenerationDefaultvalueSettings")
                                Log.e(TAG, "msg   14783   " + jobjt.getString("EmpFName"))


//                                {"LeadGenerationDefaultvalueSettings":{"ID_Employee":40,"EmpFName":"VYSHAKH PN","ID_BranchType":2,
//                                    "BranchType":"Head Office","ID_Branch":2,"Branch":"Head Office Chalappuram","FK_Department":2,"Department":null,"FK_Country":0,
//                                    "Country":"","FK_States":1,"StateName":"KERALA",
//                                    "FK_District":4,"DistrictName":"KOZHIKODE","ResponseCode":"0","ResponseMessage":"Transaction Verified"}

                                ID_BranchType = jobjt.getString("ID_BranchType")
                                ID_Branch = jobjt.getString("ID_Branch")
                                ID_Department = jobjt.getString("FK_Department")

                                FK_Country = "0"
                                FK_States = "0"
                                FK_District = "0"

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationQuickActivity,
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
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }
    }

    private fun getCustomerSearch() {
//         var custDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                customersearchViewModel.getCustomer(this,
                    strCustomer, SubModeSearch!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (custDet == 0) {
                                    custDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   105   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                        customerArrayList = jobjt.getJSONArray("CustomerDetails")

                                        if (customerArrayList.length() > 0) {
                                            Log.e(TAG, "msg   1052   " + msg)

                                            customerSearchPopup(customerArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun customerSearchPopup(customerArrayList: JSONArray) {
        try {

            dialogCustSearch = Dialog(this)
            dialogCustSearch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCustSearch!!.setContentView(R.layout.customersearch_popup)
            dialogCustSearch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyCustomer = dialogCustSearch!!.findViewById(R.id.recyCustomer) as RecyclerView
            val llsearch = dialogCustSearch!!.findViewById(R.id.llsearch) as LinearLayout
            llsearch.visibility=View.VISIBLE
            recyCustomer.visibility=View.VISIBLE
            val etsearch = dialogCustSearch!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogCustSearch!!.findViewById(R.id.txt_nodata) as TextView
            cusNameSort = JSONArray()
            for (k in 0 until customerArrayList.length()) {
                val jsonObject = customerArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                cusNameSort.put(jsonObject)
            }



            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = CustomerAdapter(this@LeadGenerationQuickActivity, customerArrayList)
            recyCustomer!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)



            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    cusNameSort = JSONArray()

                    for (k in 0 until customerArrayList.length()) {
                        val jsonObject = customerArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CusName").length) {
                            if (jsonObject.getString("CusName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                cusNameSort.put(jsonObject)
                            }

                        }
                    }


                    if (cusNameSort.length()>0)
                    {
                        txt_nodata.visibility=View.GONE
                        recyCustomer.visibility=View.VISIBLE
                        Log.e(TAG, "employeeSort               7103    " + cusNameSort)
                        val adapter = CustomerAdapter(this@LeadGenerationQuickActivity, cusNameSort)
                        recyCustomer!!.adapter = adapter
                        adapter.setClickListener(this@LeadGenerationQuickActivity)
                    }
                    else
                    {
                        txt_nodata.visibility=View.VISIBLE
                        recyCustomer.visibility=View.GONE
                    }


//                    Log.e(TAG, "employeeSort               7103    " + cusNameSort)
//                    val adapter = CustomerAdapter(this@LeadGenerationQuickActivity, cusNameSort)
//                    recyCustomer!!.adapter = adapter
//                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })


            dialogCustSearch!!.show()
            dialogCustSearch!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogCustSearch!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getContactRequest() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        }

        getContactList()
    }

    @SuppressLint("Range")
    private fun getContactList() {

        try {

//
            cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
            )
            startManagingCursor(cursor)


            val dataContact = arrayOf(
                ContactsContract.CommonDataKinds.Photo.PHOTO,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID
            )
            val to = intArrayOf(android.R.id.text1, android.R.id.text2)


            dialogContact = Dialog(this)
            dialogContact!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogContact!!.setContentView(R.layout.contact_pop_up)
            dialogContact!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            listview = dialogContact!!.findViewById(R.id.ListView) as ListView

            idsearch_contact = dialogContact!!.findViewById(R.id.idsearch_contact) as SearchView
            val window: Window? = dialogContact!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent)
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            Log.e("rreee", "wwwwwww " + dataContact.size)
            Log.e("rreee", "wwwwwww " + dataContact)
            val adapter =
                SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    dataContact,
                    to
                )
//              val adapter = ArrayAdapter(this@LeadGenerationQuickActivity, android.R.layout.simple_list_item_1, dataContact)
//            ListView!!.setAdapter(adapter as ListAdapter?)
            listview!!.setAdapter(adapter)
            dialogContact!!.show()

            Log.e("eerr", "eeeeeeeeeeeeeeee    " )
//            cursor!!.moveToPosition(0)
//            cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//            Log.e("ffgg","ffffeeeeeeeeeee "+adapter.cursor.count)
//            Log.e("ffgg","ffffeeeeeeeeeee11222 "+cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))


            listview!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {
//                    cursor!!.moveToPosition(position)
//                    Customer_Mobile = cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    Log.e("eedd", "hfhfhfhfgdgdyfghh    " + position)
                    cursor!!.moveToPosition(position)
                    Log.e("eedd", "weqweefdfdsa")
                    Log.e(
                        "ffgg",
                        "ffffeeeeeeeeeee11222 " + cursor!!.getString(
                            cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                        )
                    )

                    Customer_Mode = "0"
                    Customer_Type = ""
                    edt_customer!!.setText("")
                    ID_Customer = ""

                    edtCustname!!.setText(
                        cursor!!.getString(
                            cursor!!.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                            )
                        )
                    )
                    edtCustphone!!.setText(
                        cursor!!.getString(
                            cursor!!.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                    )
                    Log.e("eedd", "weqweefdfdsa")
//                    Log.e("ffgg","ffffeeeeeeeeeee11222 "+cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                    dialogContact!!.dismiss()
                }

            })


            idsearch_contact!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    Log.e(TAG, "cursor 3443432 length ==" + cursor!!.count)
//
//                   if (cursor!!.count>0)
//                   {
//
//                   }
//                    else
//                   {
//
//                   }

                    cursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        dataContact,"${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                        Array(1){"%$newText%"},
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    adapter.changeCursor(cursor)
                    return false
                }
            })


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    private fun getCategory() {
//         var prodcategory = 0
        var ReqMode = "13"
        var SubMode = "0"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this, ReqMode!!, SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (prodcategory == 0) {
                                    prodcategory++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        prodCategoryArrayList = jobjt.getJSONArray("CategoryList")
                                        if (prodCategoryArrayList.length() > 0) {
//                                             if (prodcategory == 0){
//                                                 prodcategory++
                                            productCategoryPopup(prodCategoryArrayList)
//                                             }

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!!.setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val etsearch = dialogProdCat!!.findViewById(R.id.etsearch) as EditText

            prodCategorySort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCategorySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductCategoryAdapter(this@LeadGenerationQuickActivity, prodCategoryArrayList)
            val adapter = ProductCategoryAdapter(this@LeadGenerationQuickActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodCategorySort = JSONArray()

                    for (k in 0 until prodCategoryArrayList.length()) {
                        val jsonObject = prodCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodCategorySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodCategorySort               7103    " + prodCategorySort)
                    val adapter =
                        ProductCategoryAdapter(this@LeadGenerationQuickActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })

            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail(ID_Categorys: String) {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, ID_Categorys)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (proddetail == 0) {
                                    proddetail++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductDetailsList")
                                        prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                        if (prodDetailArrayList.length() > 0) {

                                            if (proddetailMode == 0){

                                                val jsonObject = prodDetailArrayList.getJSONObject(0)
                                                Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
                                                ID_Category = jsonObject.getString("FK_Category")
                                                ID_Product = jsonObject.getString("ID_Product")
                                                edtProdproduct!!.setText(jsonObject.getString("ProductName"))
                                                edtProdqty!!.setText("1")
                                                strQty = "1"
                                                MRP = jsonObject.getString("MRP")
                                                Log.e(TAG, "SalPrice 3333   " + MRP)

                                                MRRP = jsonObject.getString("MRP")
                                                Offerprice = jsonObject.getString("SalPrice")

                                                tv_Mrp!!.setText(jsonObject.getString("MRP"))
                                                edtAmount!!.setText(jsonObject.getString("SalPrice"))
                                                Log.e(TAG, "MRP 3333   " + jsonObject.getString("MRP"))
                                                Log.e(TAG, "SalPrice 3333   " + jsonObject.getString("SalPrice"))

                                                ProductMRP = jsonObject!!.getString("MRP")

                                                if (editableMrp.equals("true")){
                                                    var strMrp =  jsonObject!!.getString("MRP")
                                                    Log.e(TAG, "69973  strMrp  " +strMrp)
                                                    if (strMrp.equals("") || strMrp.equals(".")){
                                                        strMrp = "0"
                                                    }

                                                    if (strMrp.toFloat() > 0){
                                                        tv_Mrp!!.isEnabled = false
                                                    }else{
                                                        tv_Mrp!!.isEnabled = true
                                                    }
                                                }else {
                                                    Log.e(TAG, "69974  editArrayList  " )
                                                    tv_Mrp!!.isEnabled = false
                                                }

                                            }else{
                                                Log.v("sdfsdfsd4fgf", "in4")
                                                productDetailPopup(prodDetailArrayList)
                                            }




                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogProdDet!! .findViewById(R.id.txt_nodata) as TextView

            prodDetailSort = JSONArray()
            for (k in 0 until prodDetailArrayList.length()) {
                val jsonObject = prodDetailArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodDetailSort.put(jsonObject)
            }
            if (prodDetailSort.length() <= 0){
                recyProdDetail!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }

            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationQuickActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@LeadGenerationQuickActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodDetailSort = JSONArray()

                    for (k in 0 until prodDetailArrayList.length()) {
                        val jsonObject = prodDetailArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("ProductName").length) {
                        if (textlength > 0) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) ||
                                jsonObject.getString("ProdBarcode")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }
                    if (prodDetailSort.length() <= 0){
                        recyProdDetail!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyProdDetail!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@LeadGenerationQuickActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProductPriority() {
//         var prodpriority = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productPriorityViewModel.getProductPriority(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.v("sdfsdfsd4fgf", "in2")
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   353   " + msg)
                            Log.v("sdfsdfsd4fgf", "in3")
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                if (prodPriorityArrayList.length() > 0) {
                                    if (prodpriority == 0) {
                                        prodpriority++

                                        if (priorityMode == 0){

                                            val jsonObject = prodPriorityArrayList.getJSONObject(0)
                                            Log.e(TAG, "ID_Priority   " + jsonObject.getString("ID_Priority"))
                                            ID_Priority = jsonObject.getString("ID_Priority")
                                            edtProdpriority!!.setText(jsonObject.getString("PriorityName"))

                                        }else{
                                            Log.v("sdfsdfsd4fgf", "in4")
                                            productPriorityPopup(prodPriorityArrayList)
                                        }

                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationQuickActivity,
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
//                             Toast.makeText(
//                                 applicationContext,
//                                 "Some Technical Issues.",
//                                 Toast.LENGTH_LONG
//                             ).show()
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

    private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

        try {

            dialogProdPriority = Dialog(this)
            dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdPriority!!.setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority =
                dialogProdPriority!!.findViewById(R.id.recyProdPriority) as RecyclerView
            val etsearch = dialogProdPriority!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogProdPriority!! .findViewById(R.id.txt_nodata) as TextView

            prodPrioritySort = JSONArray()
            for (k in 0 until prodPriorityArrayList.length()) {
                val jsonObject = prodPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodPrioritySort.put(jsonObject)
            }
            if (prodPrioritySort.length() <= 0){
                recyProdPriority!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }

            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductPriorityAdapter(this@LeadGenerationQuickActivity, prodPriorityArrayList)
            val adapter = ProductPriorityAdapter(this@LeadGenerationQuickActivity, prodPrioritySort)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodPrioritySort = JSONArray()

                    for (k in 0 until prodPriorityArrayList.length()) {
                        val jsonObject = prodPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("PriorityName").length) {
                            if (jsonObject.getString("PriorityName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    if (prodPrioritySort.length() <= 0){
                        recyProdPriority!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyProdPriority!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "reportNamesort               7103    " + prodPrioritySort)
                    val adapter =
                        ProductPriorityAdapter(this@LeadGenerationQuickActivity, prodPrioritySort)
                    recyProdPriority!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getFollowupAction() {
//         var followUpAction = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this, SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (followUpAction == 0) {
                                    followUpAction++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length() > 0) {

                                            if (followUpActionMode == 0){
                                                val jsonObject = followUpActionArrayList.getJSONObject(0)
                                                Log.e(TAG, "ID_NextAction   " + jsonObject.getString("ID_NextAction"))
                                                ID_NextAction = jsonObject.getString("ID_NextAction")
                                              //  edtFollowaction!!.setText(jsonObject.getString("NxtActnName"))

                                                ID_Status = jsonObject.getString("Status")
                                                Log.e(TAG, "Status   " + jsonObject.getString("Status"))
                                                // edtProdstatus!!.setText(jsonObject.getString("StatusName"))


                                                edtFollowtype!!.setText("")
                                                ID_ActionType = ""

                                                if (ID_Status.equals("1")) {
                                                    llfollowup!!.visibility = View.VISIBLE

                                                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                                                    val currentDate = sdf.format(Date())
                                                    edtFollowdate!!.setText(currentDate)

                                                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                                                    val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                                                    val currentDateFormate = inputFormat.parse(currentDate)
                                                    strFollowupdate = outputFormat.format(currentDateFormate)

                                                } else {
                                                    llfollowup!!.visibility = View.GONE
                                                }


                                            }else{
                                                followUpActionPopup(followUpActionArrayList)
                                            }


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!!.setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction =
                dialogFollowupAction!!.findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogFollowupAction!! .findViewById(R.id.txt_nodata) as TextView

            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }


            if (followUpActionSort.length() <= 0){
                recyFollowupAction!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }
            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupActionAdapter(this@LeadGenerationQuickActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@LeadGenerationQuickActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

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
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    if (followUpActionSort.length() <= 0){
                        recyFollowupAction!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyFollowupAction!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "followUpActionSort               7103    " + followUpActionSort)
                    val adapter =
                        FollowupActionAdapter(this@LeadGenerationQuickActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getFollowupType() {
//         var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (followUpType == 0) {
                                    followUpType++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                        followUpTypeArrayList =
                                            jobjt.getJSONArray("FollowUpTypeDetailsList")
                                        if (followUpTypeArrayList.length() > 0) {

                                            if (followUpTypeMode == 0){
                                                val jsonObject = followUpTypeArrayList.getJSONObject(0)
                                                Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))
                                                ID_ActionType = jsonObject.getString("ID_ActionType")
                                                edtFollowtype!!.setText(jsonObject.getString("ActnTypeName"))
                                            }else{
                                                followupTypePopup(followUpTypeArrayList)
                                            }



                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!!.setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!!.findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogFollowupType!! .findViewById(R.id.txt_nodata) as TextView

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            if (followUpTypeSort.length() <= 0){
                recyFollowupType!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }


            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupTypeAdapter(this@LeadGenerationQuickActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@LeadGenerationQuickActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    if (followUpTypeSort.length() <= 0){
                        recyFollowupType!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyFollowupType!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "followUpTypeSort               7103    " + followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@LeadGenerationQuickActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getEmployee() {
//         var employee = 0
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
                                if (employee == 0) {
                                    employee++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList =
                                            jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

            val lLayout = GridLayoutManager(this@LeadGenerationQuickActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationQuickActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@LeadGenerationQuickActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationQuickActivity)

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
                    val adapter = EmployeeAdapter(this@LeadGenerationQuickActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@LeadGenerationQuickActivity)
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

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        if (dateSelectMode == 0) {
            date_Picker1.maxDate = System.currentTimeMillis()
        } else if (dateSelectMode == 1) {
            date_Picker1.setMinDate(System.currentTimeMillis());
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

                //  dateSelectMode = 0

//                if (dateSelectMode == 0) {
//                    txtDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                    strDate = strYear + "-" + strMonth + "-" + strDay
//                }
                if (dateSelectMode == 1) {
                    edtFollowdate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strFollowupdate = strYear + "-" + strMonth + "-" + strDay
                }

                if (dateSelectMode == 2) {
                    edtExpecteddate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strExpecteddate = strYear + "-" + strMonth + "-" + strDay
                }

//                if (DateType == 0){
//                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }
//                if (DateType == 1){
//                    tie_NextFollowupDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult  256   " + requestCode + "   " + resultCode + "  " + data)

        if(requestCode == Config.SCANNER_CODE) {

            if (data != null){
                Log.e("TAG", "onActivityResult " + data!!.getStringExtra("barcodeValue"))
                var barcodeValue = data!!.getStringExtra("barcodeValue").toString()
                barcodeCount = 0
                getItemList(barcodeValue)
            }
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if (strImage.equals("1")) {

                        //   val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        imgvupload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        Log.e(TAG, "image1  2052    " + image1)
                        if (image1 != null) {
                        }
                    }
                    if (strImage.equals("2")) {

                        //  val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                        imgvupload2!!.setImageURI(contentURI)
                        image2 = getRealPathFromURI(selectedImageUri)
                        if (image2 != null) {
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@LeadGenerationQuickActivity, "Failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        } else if (requestCode == CAMERA) {

            try {
                Log.e(TAG,"2154441        ")
                if (data != null) {
                    try {
                        Log.e(TAG,"2154442        ")
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
//                                Log.e(TAG,"2154443        ")
//                            }
//                        } else {
                            Log.e(TAG,"2154444        ")
                            val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                            val bytes = ByteArrayOutputStream()
                            thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//                    destination = File(
//                        (Environment.getExternalStorageDirectory()).toString() + "/" +
//                                getString(R.string.app_name),
//                        "IMG_" + System.currentTimeMillis() + ".jpg"
//                    )
//                    destination = File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" +
//                               "",
//                        "IMG_" + System.currentTimeMillis() + ".jpg"
//                    )
//                    val fo: FileOutputStream

                            try {
                                Log.e(TAG,"2154445        ")
//                        if (!destination!!.getParentFile().exists()) {
//                            destination!!.getParentFile().mkdirs()
//                        }
//                        if (!destination!!.exists()) {
//                            destination!!.createNewFile()
//                        }
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
                                    "IMG_" + System.currentTimeMillis() + ".jpg"
                                )
                                val fo: FileOutputStream


                                fo = FileOutputStream(destination)
                                fo.write(bytes.toByteArray())
                                fo.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                                Log.e(TAG, "FileNotFoundException   23671    " + e.toString())
                                Log.e(TAG,"2154446        "+e.toString())

                            } catch (e: IOException) {
                                e.printStackTrace()
                                Log.e(TAG,"2154447        "+e.toString())
                                Log.e(TAG, "FileNotFoundException   23672    " + e.toString())
                            }

                            if (strImage.equals("1")) {
                                Log.e(TAG,"2154448        ")
                                image1 = destination!!.getAbsolutePath()
                                Log.e(TAG, "image1  20522    " + image1)
                                destination = File(image1)
                                Log.e(TAG,"21544481       "+destination)

                                val myBitmap = BitmapFactory.decodeFile(destination.toString())
                                val converetdImage = getResizedBitmap(myBitmap, 500)
                                //  val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                                if (imgvupload1 != null) {
                                    imgvupload1!!.setImageBitmap(converetdImage)
                                    Log.e(TAG,"215444821       "+converetdImage)
                                }
                                Log.e(TAG,"21544482       "+converetdImage)
                                imgvupload1!!.setImageBitmap(converetdImage)


                                if (image1 != null) {

                                }
                            }
                            if (strImage.equals("2")) {
                                Log.e(TAG,"2154449        ")
                                image2 = destination!!.getAbsolutePath()
                                Log.e(TAG, "image2  20522    " + image2)
                                destination = File(image2)

                                val myBitmap = BitmapFactory.decodeFile(destination.toString())
                                val converetdImage = getResizedBitmap(myBitmap, 500)
                                //   val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                                if (imgvupload2 != null) {
                                    imgvupload2!!.setImageBitmap(converetdImage)
                                }
                                imgvupload2!!.setImageBitmap(converetdImage)

                                if (image2 != null) {

                                }
                            }

                     //   }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e(TAG,"21544410  Exception       "+e.toString())
                        Toast.makeText(this@LeadGenerationQuickActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {

                Log.e(TAG,"21544411  Exception       "+e.toString())
            }


        }

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

    private fun getItemList(barcodeValue: String) {
        var ReqMode = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                itemSearchListViewModel.getProductEnquiry(this, ReqMode!!, barcodeValue)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (barcodeCount == 0) {
                                    barcodeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "608   barcodeValue   " + barcodeValue)
                                    Log.e(TAG, "608   msg   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {


                                        try {

                                            val jobjt = jObject.getJSONObject("ItemList")
                                            val jresult = jobjt.getJSONArray("ItemSearchListData")

                                            Log.i("responseTry", "inside try" + jresult)
                                            var jsonObject = jresult.getJSONObject(0)
                                            Log.i("responseTry", "in try   = " + jsonObject)
//                                            ID_Category = jresult.getJSONObject(0).getString("FK_Category")

                                            ID_Product = jsonObject.getString("ID_Product")
                                            ID_Category = jsonObject.getString("FK_Category")
                                            Project = jsonObject.getString("Project")
                                            edtProdcategory!!.setText("" + jsonObject!!.getString("CategoryName"))
                                            edtProdproduct!!.setText("" + jsonObject!!.getString("ProductName"))
                                            tv_Mrp!!.setText("" + jsonObject!!.getString("MRP"))
                                            edtAmount!!.setText("" + jsonObject!!.getString("Price"))

                                            ProductMRP = jsonObject.getString("MRP")

                                            if (editableMrp.equals("true")){
                                                var strMrp =  jsonObject.getString("MRP")
                                                Log.e(TAG, "69973  strMrp  " +strMrp)
                                                if (strMrp.equals("") || strMrp.equals(".")){
                                                    strMrp = "0"
                                                }

                                                if (strMrp.toFloat() > 0){
                                                    tv_Mrp!!.isEnabled = false
                                                }else{
                                                    tv_Mrp!!.isEnabled = true
                                                }
                                            }else {
                                                Log.e(TAG, "69974  editArrayList  " )
                                                tv_Mrp!!.isEnabled = false
                                            }


                                            Log.i(
                                                "responseTry",
                                                "ID_Category=" + jObject.getString("FK_Category")
                                            )
//                                            Log.i("responseTry","mrp="+jObject!!.getString("MRP"))

                                        } catch (e: Exception) {
                                            Log.i("responseTry", "inside catch")
                                            Log.e(TAG, "msg   82223   " + e.toString())
                                        }

                                    } else {

                                        tv_Mrp!!.setText("")
                                        ProductMRP = ""
                                        edtAmount!!.setText("")
                                        edtProdcategory!!.setText("")
                                        edtProdproduct!!.setText("")
                                        ID_Category = ""
                                        ID_Product = ""

                                        ID_Category = ""
                                        strProduct = ""



                                        proddetailMode = 0
                                        proddetail = 0
                                        getProductDetail("0")

                                        prodpriority = 0
                                        priorityMode = 0
                                        getProductPriority()
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationQuickActivity,
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

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun showPictureDialog() {

        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)


            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "22239991   ")


                if (Build.VERSION.SDK_INT >= 33) {
                    //ActivityCompat.requestPermissions(this,String[]{readMediaAudio},PERMISSION_CODE)
                    Log.e(TAG, "222399912   ")
                    if (Config.check13Permission(context)) {
                        Log.e(TAG, "222399913   ")

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
//                    ActivityCompat.requestPermissions(this, arrayOf(readMediaAudio,readMediaImages,readMediaVideo), PERMISSION_CODE)


                } else {
                    Log.e(TAG, "222399914   ")



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
                            Log.e(TAG, "22239992   ")
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed; request the permission
                            Log.e(TAG, "22239993   ")
                            ActivityCompat.requestPermissions(
                                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                            )
                        }
                    } else {
                        Log.e(TAG, "22239994   ")
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
                }
            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                CAMERA
            )
        } catch (e: Exception) {

            Log.e(TAG,"2256    "+e)
        }

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



    override fun onClick(position: Int, data: String) {

        if (data.equals("customer")) {
            dialogCustSearch!!.dismiss()
            val jsonObject = customerArrayList.getJSONObject(position)
//            txtcustomer!!.text = jsonObject!!.getString("CusName")
            if (SubModeSearch == "1") {
                edt_customer!!.setText(jsonObject!!.getString("CusName"))
            } else {
                edt_customer!!.setText(jsonObject!!.getString("CusPhnNo"))
            }

            // custDetailMode = "1"
            ID_CustomerAssignment = ""
            Customer_Mode = "1"  // SEARCH
            ID_Customer = jsonObject.getString("ID_Customer")
            Customer_Type = jsonObject.getString("Customer_Type")
            Customer_Name = jsonObject.getString("CusName")
            Customer_Mobile = jsonObject.getString("CusPhnNo")
            Customer_Email = jsonObject.getString("CusEmail")
            Customer_Address1 = jsonObject.getString("CusAddress1")
            Customer_Address2 = jsonObject.getString("CusAddress2")

            // llCustomerDetail!!.visibility = View.GONE
            actv_namTitle!!.setText(jsonObject.getString("CusNameTitle"))
            edtCustname!!.setText(jsonObject.getString("CusName"))
            edtCustphone!!.setText(jsonObject.getString("CusPhnNo"))
            edtCustAddres2!!.setText(jsonObject.getString("CusAddress2"))


//            edtCustemail!!.setText(jsonObject.getString("CusEmail"))
//            edtCustaddress1!!.setText(jsonObject.getString("CusAddress1"))
//            edtCustaddress2!!.setText(jsonObject.getString("CusAddress2"))
//            edtWhatsApp!!.setText(jsonObject.getString("CusMobileAlternate"))
//
//
//
//            FK_Country = jsonObject.getString("CountryID")
//            FK_States = jsonObject.getString("StatesID")
//            FK_District = jsonObject.getString("DistrictID")
//            FK_Post = jsonObject.getString("PostID")
//            FK_Area = jsonObject.getString("FK_Area")
//
//            edtPincode!!.setText(jsonObject.getString("Pincode"))
//            edtCountry!!.setText(jsonObject.getString("CntryName"))
//            edtState!!.setText(jsonObject.getString("StName"))
//            edtDistrict!!.setText(jsonObject.getString("DtName"))
//            Log.e(TAG, "123455   ")
//            edtArea!!.setText(jsonObject.getString("Area"))
//            edtPost!!.setText(jsonObject.getString("PostName"))
//            edtPincode!!.setText(jsonObject.getString("Pincode"))


        }

        if (data.equals("prodcategory")) {
            dialogProdCat!!.dismiss()
//             val jsonObject = prodCategoryArrayList.getJSONObject(position)
            val jsonObject = prodCategorySort.getJSONObject(position)
            Log.e(TAG, "ID_Category   " + jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            edtProdcategory!!.setText(jsonObject.getString("CategoryName"))
            ID_Product = ""
            strQty = ""
            edtProdproduct!!.setText("")
            edtProdqty!!.setText("")
            edtProjectName!!.setText("")
            edtAmount!!.setText("")
            tv_Mrp!!.setText("")
            ProductMRP = ""
            modeProjectProduct = jsonObject.getString("Project")
            if (jsonObject.getString("Project").equals("0")) {
                ll_product_qty!!.visibility = View.VISIBLE
                edtProjectName!!.visibility = View.GONE

            } else if (jsonObject.getString("Project").equals("1")) {
                ll_product_qty!!.visibility = View.GONE
                edtProjectName!!.visibility = View.VISIBLE
            }



        }


        if (data.equals("proddetails")) {
            dialogProdDet!!.dismiss()
//             val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
            ID_Category = jsonObject.getString("FK_Category")
            ID_Product = jsonObject.getString("ID_Product")
            edtProdproduct!!.setText(jsonObject.getString("ProductName"))
            edtProdqty!!.setText("1")
            strQty = "1"
            MRP = jsonObject.getString("MRP")
            Log.e(TAG, "SalPrice 3333   " + MRP)

            MRRP = jsonObject.getString("MRP")
            Offerprice = jsonObject.getString("SalPrice")

//            if (MRRP >= Offerprice){
//                edtAmount!!.isClickable.equals(false)
//            }

            tv_Mrp!!.setText(jsonObject.getString("MRP"))
            edtAmount!!.setText(jsonObject.getString("SalPrice"))
            Log.e(TAG, "MRP 3333   " + jsonObject.getString("MRP"))
            Log.e(TAG, "SalPrice 3333   " + jsonObject.getString("SalPrice"))

            ProductMRP = jsonObject.getString("MRP")

            if (editableMrp.equals("true")){
                var strMrp =  jsonObject.getString("MRP")
                Log.e(TAG, "69973  strMrp  " +strMrp)
                if (strMrp.equals("") || strMrp.equals(".")){
                    strMrp = "0"
                }

                if (strMrp.toFloat() > 0){
                    tv_Mrp!!.isEnabled = false
                }else{
                    tv_Mrp!!.isEnabled = true
                }
            }else {
                Log.e(TAG, "69974  editArrayList  " )
                tv_Mrp!!.isEnabled = false
            }

        }

        if (data.equals("prodpriority")) {
            dialogProdPriority!!.dismiss()
//             val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = prodPrioritySort.getJSONObject(position)
            Log.e(TAG, "ID_Priority   " + jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            edtProdpriority!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("followupaction")) {
            dialogFollowupAction!!.dismiss()
//             val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG, "ID_NextAction   " + jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            edtFollowaction!!.setText(jsonObject.getString("NxtActnName"))

            ID_Status = jsonObject.getString("Status")
            Log.e(TAG, "Status  11111 " + jsonObject.getString("Status"))
            // edtProdstatus!!.setText(jsonObject.getString("StatusName"))


            edtFollowtype!!.setText("")
            ID_ActionType = ""
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = sdf.format(Date())

            if (ID_Status.equals("1")) {
                llfollowup!!.visibility = View.VISIBLE


                edtFollowdate!!.setText(currentDate)



                val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val currentDateFormate = outputFormat.parse(currentDate)
               // strFollowupdate = outputFormat.format(currentDateFormate)
              //  strFollowupdate = currentDateFormate.toString()

                Log.e(TAG,"currentDate  11111111  "+currentDate)
                Log.e(TAG,"currentDateFormate  11111111  "+currentDateFormate)
                Log.e(TAG,"currentDateFormate  11111112  "+outputFormat.format(currentDateFormate))

                val inputDateFormat = "dd-MM-yyyy"
                val outputDateFormat = "yyyy-MM-dd"

                val formattedDate = changeDateFormat(currentDate, inputDateFormat, outputDateFormat)
                Log.e(TAG,"currentDate  11111111  "+formattedDate)
                strFollowupdate = formattedDate

//                val inputDate = "2023-09-08" // Your input date string
//                val inputDateFormat = "yyyy-MM-dd" // The format of your input date
//                val outputDateFormat = "dd MMM yyyy" // The format you want to change it to

//                val formattedDate = changeDateFormat(inputDate, inputDateFormat, outputDateFormat)
//                Log.e(TAG,"currentDate  11111111  "+formattedDate)


            } else {
                llfollowup!!.visibility = View.GONE
                val inputDateFormat = "dd-MM-yyyy"
                val outputDateFormat = "yyyy-MM-dd"

                val formattedDate = changeDateFormat(currentDate, inputDateFormat, outputDateFormat)
                Log.e(TAG,"currentDate  11111111  "+formattedDate)
                strFollowupdate = formattedDate

            }


        }

        if (data.equals("followuptype")) {
            dialogFollowupType!!.dismiss()
//             val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            edtFollowtype!!.setText(jsonObject.getString("ActnTypeName"))


        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            edtEmployee!!.setText(jsonObject.getString("EmpName"))


        }

    }

    private fun getCurrentDateNTime() {

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDateFormate = inputFormat.parse(currentDate)
        strDate = outputFormat.format(currentDateFormate)


    }

    private fun LeadValidations(v: View) {
        Log.e(TAG, "LeadValidations  3732   " + ID_Customer + "  " + ID_Customer!!.length)
        Log.e(TAG, "LeadValidations  3732   " + ID_CustomerAssignment + "  " + ID_CustomerAssignment!!.length)
        CusNameTitle = actv_namTitle!!.text.toString()
        Customer_Name = edtCustname!!.text.toString()
        Customer_Mobile = edtCustphone!!.text.toString()
        Customer_Address2 = edtCustAddres2!!.text.toString()

        val MobilePattern = "[0-9]{10}"
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"



        if (strDate.equals("")) {
            Config.snackBars(context, v, "Select Enquiry Date")
        }
        else if (CusNameTitle.equals("")){
            Config.snackBars(context, v, "Select Customer Name Title")
        }
        else if (Customer_Name.equals("")){
            Config.snackBars(context, v, "Enter Customer Name")
        }
        else{
            Log.e(TAG,"17555   ")
            MoreValidations(v)

        }

    }

    fun changeDateFormat(inputDate: String, inputFormat: String, outputFormat: String): String {
        try {
            val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
            val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
            val date = inputDateFormat.parse(inputDate)
            return outputDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun MoreValidations(v: View) {

        Log.e(
            TAG, "LeadValidations  37321"
                    + "\n" + "Enquiry date       : " + strDate
                    + "\n" + "Attended by        : " + ID_CollectedBy
                    + "\n" + "Lead Source        : " + ID_LeadFrom
                    + "\n" + "Lead From          : " + ID_LeadThrough
                    + "\n" + "ID_MediaSubMaster  : " + ID_MediaSubMaster
                    + "\n" + "strLeadThrough     : " + strLeadThrough
                    + "\n" + "Customer_Mode      : " + Customer_Mode
                    + "\n" + "CusNameTitle       : " + CusNameTitle + "@"
                    + "\n" + "CusNameTitle       : " + CusNameTitle!!.length
                    + "\n" + "ID_Customer        : " + ID_Customer
                    + "\n" + "Customer_Name      : " + Customer_Name
                    + "\n" + "Customer_Mobile    : " + Customer_Mobile
                    + "\n" + "WhatsApp No        : " + strWhatsAppNo
                    + "\n" + "Company Contact    : " + strCompanyContact
                    + "\n" + "Customer_Email     : " + Customer_Email
                    + "\n" + "Customer_Address1  : " + Customer_Address1
                    + "\n" + "Customer_Address2  : " + Customer_Address2
                    + "\n" + "Address 3  : " + FK_Area
                    + "\n" + "ID_CustomerAssignment : " + ID_CustomerAssignment
        )

        MRRP = tv_Mrp!!.text.toString()
        strQty = edtProdqty!!.text.toString()
        var fQty = 0
        if (strQty.equals("")){
            fQty = 0
        }else{
            fQty = strQty.toInt()
        }
        stramount = edtAmount!!.text.toString()
        if (MRRP!!.equals("")) {
            MRRP = "0"
        }
//        if (strQty.equals("")) {
//            strQty = "0"
//
//        }
        if (stramount.equals("")) {
            stramount = "0"

        }
        strProduct = edtProdproduct!!.text.toString()
        strProject = edtProjectName!!.text.toString()
        strFeedback = edtProdfeedback!!.text.toString()
//        strFollowupdate = edtFollowdate!!.text.toString()

//        if (ID_Category.equals("")) {
//            Config.snackBars(context, v, "Select Category")
//
//        }
        if (ID_Product.equals("")) {


            if (CompanyCategory.equals("0") || CompanyCategory.equals("1")) {
                Config.snackBars(context, v, "Select Product")
            } else if (CompanyCategory.equals("2")) {
                Config.snackBars(context, v, "Select Destination")
            }else{
                Config.snackBars(context, v, "Select Product")
            }

        }
        else if (strQty.equals("") || fQty == 0) {
            if (CompanyCategory.equals("0") || CompanyCategory.equals("1")) {
                Config.snackBars(context, v, "Enter Quantity")
            } else if (CompanyCategory.equals("2")) {
                Config.snackBars(context, v, "Enter No.of Passengers")
            }else{
                Config.snackBars(context, v, "Enter Quantity")
            }
        }
        else if (CompanyCategory.equals("2") && strExpecteddate.equals("")) {
            Config.snackBars(context, v, "Expected date")

        }
        else if ((MRRP.toFloat() != "0".toFloat()) && (stramount.toFloat() > MRRP.toFloat())) {
            Config.snackBars(context, v, "Offer Price Should be less than or Equal to MRP")
        }
//        else if (strFeedback.equals("")) {
//            Config.snackBars(context, v, "Enter Enquiry Note ")
//
//
//        }
        else if (ID_Status.equals("")) {
            Config.snackBars(context, v, "Select Action")

        }
        else if (ID_Status.equals("1")) {
            if (ID_NextAction.equals("")) {
                Config.snackBars(context, v, "Select Followup Action")

            } else if (ID_ActionType.equals("")) {
                Config.snackBars(context, v, "Select Action type")

            } else if (strFollowupdate.equals("")) {
                Config.snackBars(context, v, "Select Followup Date")


            } else if (ID_Employee.equals("")) {
                Config.snackBars(context, v, "Select Assigned To")

            } else if (ID_Priority.equals("")) {
                Config.snackBars(context, v, "Select Priority")
                Log.e(TAG, "   stramount1        " + stramount + "===" + MRRP)

            }
            else{
                Log.v("gfdfgdfgdf", "else")
                //clickMode = "1"
                savelistDetail()
            }
        }
        else{
            savelistDetail()
        }


    }

    private fun savelistDetail() {
        array_product_lead  = JSONArray()
        val jObject = JSONObject()

        jObject.put("FK_Category",ID_Category)
        jObject.put("ID_Product", ID_Product)
        jObject.put("FK_Employee", ID_Employee)

        jObject.put("ProdName", strProduct)
        jObject.put("ProjectName",strProject)

        jObject.put("LgpPQuantity", strQty)
        jObject.put("FK_Priority", ID_Priority)
        jObject.put("LgpDescription",strFeedback)
        jObject.put("ActStatus",ID_Status)
        jObject.put("FK_NetAction", ID_NextAction)
        jObject.put("FK_ActionType", ID_ActionType)
        jObject.put("NextActionDate", strFollowupdate)

        jObject.put("LgpExpectDate", strExpecteddate)
        jObject.put("LgpMRP",MRRP)
        jObject.put("LgpSalesPrice", stramount)


        jObject.put("BranchID", ID_Branch)
        jObject.put("BranchTypeID", ID_BranchType)
        jObject.put("FK_Departement", ID_Department)

        array_product_lead.put(jObject)
        Log.e(TAG, "array_product_lead     1122  " + array_product_lead)

        if (image1.equals("")) {
            encode1 = ""
        } else {
            val bitmap = BitmapFactory.decodeFile(image1)
            val converetdImage = getResizedBitmap(bitmap, 500)
            val stream = ByteArrayOutputStream()
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
            } else {
                encode1 = android.util.Base64.encodeToString(
                    stream.toByteArray(),
                    android.util.Base64.DEFAULT
                )
            }
        }
        if (image2.equals("")) {
            encode2 = ""
        } else {
            val bitmap = BitmapFactory.decodeFile(image2)
            val converetdImage = getResizedBitmap(bitmap, 500)
            val stream = ByteArrayOutputStream()
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
            } else {
                encode2 = android.util.Base64.encodeToString(
                    stream.toByteArray(),
                    android.util.Base64.DEFAULT
                )
            }
        }

        LeadConfirmationPopup()

    }

    private fun LeadConfirmationPopup() {

        try {
            val dialogConfirmPop = Dialog(this)
            dialogConfirmPop!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirmPop!!.setContentView(R.layout.confirmation_pop_quicklead)
            dialogConfirmPop!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val tvp_cust_name = dialogConfirmPop!!.findViewById(R.id.tvp_cust_name) as TextView
            val tvp_cust_phone = dialogConfirmPop!!.findViewById(R.id.tvp_cust_phone) as TextView
            val tvp_lead_category = dialogConfirmPop!!.findViewById(R.id.tvp_lead_category) as TextView
            val tvp_lead_product = dialogConfirmPop!!.findViewById(R.id.tvp_lead_product) as TextView
            val tvp_lead_mrp_offer = dialogConfirmPop!!.findViewById(R.id.tvp_lead_mrp_offer) as TextView
            val tvp_lead_qty = dialogConfirmPop!!.findViewById(R.id.tvp_lead_qty) as TextView
            val tvp_lead_project = dialogConfirmPop!!.findViewById(R.id.tvp_lead_project) as TextView
            val tvp_lead_action = dialogConfirmPop!!.findViewById(R.id.tvp_lead_action) as TextView
            val tvp_lead_actiontype = dialogConfirmPop!!.findViewById(R.id.tvp_lead_actiontype) as TextView
            val tvp_lead_followupdate = dialogConfirmPop!!.findViewById(R.id.tvp_lead_followupdate) as TextView
            val tvp_lead_assigned = dialogConfirmPop!!.findViewById(R.id.tvp_lead_assigned) as TextView
            val tvp_lead_priority = dialogConfirmPop!!.findViewById(R.id.tvp_lead_priority) as TextView
            val tvp_lead_expected = dialogConfirmPop!!.findViewById(R.id.tvp_lead_expected) as TextView

            val tvp_label_product = dialogConfirmPop!!.findViewById(R.id.tvp_label_product) as TextView
            val tvp_label_qty = dialogConfirmPop!!.findViewById(R.id.tvp_label_qty) as TextView

            val ll_lead_hideproduct = dialogConfirmPop!!.findViewById(R.id.ll_lead_hideproduct) as LinearLayout
            val ll_lead_hideAction = dialogConfirmPop!!.findViewById(R.id.ll_lead_hideAction) as LinearLayout
            val ll_lead_project = dialogConfirmPop!!.findViewById(R.id.ll_lead_project) as LinearLayout
            val ll_lead_mrp_offer = dialogConfirmPop!!.findViewById(R.id.ll_lead_mrp_offer) as LinearLayout
            val ll_lead_expected = dialogConfirmPop!!.findViewById(R.id.ll_lead_expected) as LinearLayout

            val btnCancel = dialogConfirmPop!!.findViewById(R.id.btnCancel) as Button
            val btnOk = dialogConfirmPop!!.findViewById(R.id.btnOk) as Button

            if (CompanyCategory.equals("0") || CompanyCategory.equals("1")) {

                tvp_label_product.text = "Product"
                tvp_label_qty.text = "Quantity"
                ll_lead_mrp_offer.visibility = View.VISIBLE
                ll_lead_expected.visibility = View.GONE
            } else if (CompanyCategory.equals("2")) {

                tvp_label_product.text = "Destination"
                tvp_label_qty.text = "No.of Passengers"
                ll_lead_mrp_offer.visibility = View.GONE
                ll_lead_expected.visibility = View.VISIBLE

            }


            if (modeProjectProduct.equals("0")){
                ll_lead_hideproduct.visibility = View.VISIBLE
                ll_lead_project.visibility = View.GONE
            }else if (modeProjectProduct.equals("1")){
                ll_lead_hideproduct.visibility = View.GONE
                ll_lead_project.visibility = View.VISIBLE
            }

            if (ID_Status.equals("1")){
                ll_lead_hideAction.visibility = View.VISIBLE
            }else{
                ll_lead_hideAction.visibility = View.GONE
            }

            tvp_cust_name.text = CusNameTitle+""+Customer_Name
            tvp_cust_phone.text = Customer_Mobile

            tvp_lead_category.text = edtProdcategory!!.text.toString()
            tvp_lead_product.text = edtProdproduct!!.text.toString()
            tvp_lead_mrp_offer.text = MRRP+" / "+stramount
            tvp_lead_qty.text = strQty
            tvp_lead_project.text = strProject
            tvp_lead_action.text = edtFollowaction!!.text.toString()
            tvp_lead_actiontype.text = edtFollowtype!!.text.toString()
            tvp_lead_followupdate.text = edtFollowdate!!.text.toString()
            tvp_lead_assigned.text = edtEmployee!!.text.toString()
            tvp_lead_priority.text = edtProdpriority!!.text.toString()
            tvp_lead_expected.text = edtExpecteddate!!.text.toString()

            btnCancel.setOnClickListener {
                dialogConfirmPop.dismiss()
            }

            btnOk.setOnClickListener {
                dialogConfirmPop.dismiss()
                saveLeadGenDet = 0
                saveLeadGeneration()
            }



            dialogConfirmPop!!.show()
            dialogConfirmPop!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );

        }catch (e : Exception){

        }

    }

    private fun saveLeadGeneration() {
//        var saveLeadGenDet = 0
        try {

            Log.e(TAG, "encode1   4759   " + encode1)
            Log.e(TAG, "encode2   4759   " + encode2)
            Log.e(TAG, "strDate   4759   " + strDate + "   " + strFollowupdate)


            Log.e(
                TAG, "LocationValidation  64211"
                        + "\n" + "ID_LeadGenerate    : " + ID_LeadGenerate
                        + "\n" + "Enquiry date       : " + strDate
                        + "\n" + "Attended by        : " + ID_CollectedBy
                        + "\n" + "Lead Source        : " + ID_LeadFrom
                        + "\n" + "Lead From          : " + ID_LeadThrough
                        + "\n" + "ID_MediaSubMaster  : " + ID_MediaSubMaster
                        + "\n" + "strLeadThrough     : " + strLeadThrough
                        + "\n"
                        + "\n" + "Customer_Mode      : " + Customer_Mode
                        + "\n" + "CusNameTitle       : " + CusNameTitle + "@"
                        + "\n" + "CusNameTitle       : " + CusNameTitle!!.length
                        + "\n" + "ID_Customer        : " + ID_Customer
                        + "\n" + "ID_CustomerAssignment        : " + ID_CustomerAssignment
                        + "\n" + "Customer_Name      : " + Customer_Name
                        + "\n" + "Customer_Mobile    : " + Customer_Mobile
                        + "\n" + "WhatsApp No        : " + strWhatsAppNo
                        + "\n" + "Company Contact    : " + strCompanyContact
                        + "\n" + "Customer_Email     : " + Customer_Email
                        + "\n" + "Customer_Address1  : " + Customer_Address1
                        + "\n" + "Customer_Address2  : " + Customer_Address2
                        + "\n" + "Address 3          : " + FK_Area
                        + "\n"
                        + "\n" + "FK_Country        : " + FK_Country
                        + "\n" + "FK_States         : " + FK_States
                        + "\n" + "FK_District       : " + FK_District
                        + "\n" + "FK_Area           : " + FK_Area
                        + "\n" + "FK_Post           : " + FK_Post
                        + "\n" + "strPincode        : " + strPincode
                        + "\n"
                        + "\n" + "ID_Category        : " + ID_Category
                        + "\n" + "ID_Product         : " + ID_Product
                        + "\n" + "strQty             : " + strQty
                        + "\n" + "strProduct         : " + strProduct
                        + "\n" + "strProject         : " + strProject
                        + "\n" + "ID_Priority        : " + ID_Priority
                        + "\n" + "strFeedback        : " + strFeedback
                        + "\n" + "ID_Status          : " + ID_Status
                        + "\n" + "ID_NextAction      : " + ID_NextAction
                        + "\n" + "ID_ActionType      : " + ID_ActionType
                        + "\n" + "strFollowupdate    : " + strFollowupdate
                        + "\n" + "ID_Branch          : " + ID_Branch
                        + "\n" + "ID_BranchType      : " + ID_BranchType
                        + "\n" + "ID_Department      : " + ID_Department
                        + "\n" + "ID_Employee        : " + ID_Employee
                        + "\n"
                        + "\n" + "strLatitude        : " + strLatitude
                        + "\n" + "strLongitue        : " + strLongitue
                      //  + "\n" + "locAddress         : " + locAddress + "," + locCity + "," + locState + "," + locCountry + "," + locpostalCode
                        + "\n" + "strExpecteddate    : " + strExpecteddate
            )

            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    leadGenerateSaveViewModel.saveLeadGenerate(
                        this,
                        saveUpdateMode!!,
                        ID_LeadGenerate!!,
                        strDate,
                        ID_Customer!!,
                        ID_MediaSubMaster!!,
                        CusNameTitle!!,
                        Customer_Name!!,
                        Customer_Address1!!,
                        Customer_Address2!!,
                        Customer_Mobile!!,
                        Customer_Email!!,
                        strCompanyContact,
                        FK_Country,
                        FK_States,
                        FK_District,
                        FK_Post,
                        strPincode,
                        FK_Area,
                        ID_LeadFrom!!,
                        ID_LeadThrough!!,
                        strLeadThrough,
                        strWhatsAppNo,
                        strLatitude!!,
                        strLongitue!!,
                        encode1,
                        encode2,
                        Customer_Mode!!,
                        Customer_Type!!,
//                        strExpecteddate,
                        ID_CustomerAssignment!!,
                        ID_CollectedBy!!,
                        ID_AuthorizationData,
                        array_product_lead!!,
                        LeadGenerationActivity.Customer_Mobile2!!
                    )!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {

                                if (msg!!.length > 0) {
                                    if (saveLeadGenDet == 0) {
                                        saveLeadGenDet++
                                        val jObject = JSONObject(msg)
                                        Log.e(TAG, "msg   4120   " + msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            val jobjt =
                                                jObject.getJSONObject("UpdateLeadGeneration")
                                            try {

                                                val suceessDialog = Dialog(this)
                                                suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                suceessDialog!!.setCancelable(false)
                                                suceessDialog!!.setContentView(R.layout.success_popup)
                                                suceessDialog!!.window!!.attributes.gravity =
                                                    Gravity.CENTER_VERTICAL;

                                                val tv_succesmsg =
                                                    suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
                                                val tv_leadid =
                                                    suceessDialog!!.findViewById(R.id.tv_leadid) as TextView
                                                val tv_succesok =
                                                    suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                                //LeadNumber
                                                tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
                                                tv_leadid!!.setText(jobjt.getString("LeadNumber"))

                                                tv_succesok!!.setOnClickListener {
                                                    suceessDialog!!.dismiss()

//                                                    val i = Intent(this@LeadGenerationQuickActivity, LeadActivity::class.java)
//                                                    startActivity(i)
                                                    finish()


                                                }

                                                suceessDialog!!.show()
                                                suceessDialog!!.getWindow()!!.setLayout(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                                );
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                                val builder = AlertDialog.Builder(
                                                    this@LeadGenerationQuickActivity,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage(e.toString())
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                    onBackPressed()
                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()

                                            }

                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@LeadGenerationQuickActivity,
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
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                }


                            } catch (e: Exception) {

                                Log.e(TAG, "Exception  4133    " + e.toString())
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationQuickActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(e.toString())
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()

                            }

                        })
                    progressDialog!!.dismiss()
                }
                false -> {
//                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception  226666    " + e.toString())
        }


    }

    private fun clearData() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())


        Customer_Mode = "0"
        ID_Customer = "0"
        Customer_Type = ""
        Customer_Name = ""
        Customer_Mobile = ""
        edt_customer!!.setText("")
        edtCustname!!.setText("")
        edtCustphone!!.setText("")
        edtCustAddres2!!.setText("")
        strCustomer = ""


        ID_Category = ""
        ID_Product = ""
        strQty = ""
        strProduct = ""
        strProject = ""
        strDate = ""
        ID_Priority = ""
        strFeedback = ""
        ID_Status = ""
        ID_NextAction = ""
        ID_ActionType = ""

        ID_BranchType = ""
        ID_Branch = ""
        ID_Department = ""
        ID_Employee = ""

        edtProdcategory!!.setText("")
       // edtProdproduct!!.setText("")
        edtProjectName!!.setText("")
      //  edtProdqty!!.setText("")
        edtProdfeedback!!.setText("")
        edtExpecteddate!!.setText("")
        edtProdpriority!!.setText("")

        edtFollowaction!!.setText("")
        edtFollowtype!!.setText("")
        edtFollowdate!!.setText(currentDate)

        edtEmployee!!.setText("")
     //   edtAmount!!.setText("")

      //  tv_Mrp!!.setText("")
        ProductMRP = ""
        strPincode = ""

        strLatitude = ""
        strLongitue = ""

        image1 = ""
        image2 = ""
        encode1 = ""
        encode2 = ""

        imgvupload1!!.setImageResource(R.drawable.lead_uploads)
        imgvupload2!!.setImageResource(R.drawable.lead_uploads)


        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDateFormate = inputFormat.parse(currentDate)
        strDate = outputFormat.format(currentDateFormate)
      //  strFollowupdate = outputFormat.format(currentDateFormate)

        val inputDateFormat = "dd-MM-yyyy"
        val outputDateFormat = "yyyy-MM-dd"

        val formattedDate = changeDateFormat(currentDate, inputDateFormat, outputDateFormat)
        Log.e(TAG,"currentDate  11111111  "+formattedDate)
        strFollowupdate = formattedDate


        strExpecteddate = ""

        btnSubmit!!.setText("Submit")
        array_product_lead = JSONArray()

        searchNameTitle = arrayOf("Mr. ", "Mrs. ", "Miss. ", "M/s. ", "Dr. ", "Ms. ", "Fr. ", "Sr. ")
        detailsShowing()
        setLabelbyCompany()

        getDefaultValueSettings()
        checkAttendance()

        prodpriority = 0
        priorityMode = 0
        getProductPriority()

        followUpActionMode = 0
        followUpAction = 0
        getFollowupAction()

        followUpTypeMode = 0
        followUpType = 0
        getFollowupType()

    }


    private fun checkCameraPermissions() {
        // Check if the camera permission is already granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Camera permission is already granted
            // You can proceed with using the camera
        } else {
            // Camera permission has not been granted
            // Request camera permission
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        // Check if the device is running Android 6.0 (Marshmallow) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
        // If the device is running a version lower than Android 6.0,
        // the permission is already granted in the manifest
    }

    // Override onRequestPermissionsResult to handle the result of the permission request
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            // Check if the camera permission is granted
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Camera permission granted
//                // You can proceed with using the camera
//            } else {
//                // Camera permission denied
//                // You may want to inform the user about the importance of the camera permission
//            }
//        }
//    }
//
//    private fun checkAndRequestStoragePermission() {
//        // Check if the WRITE_EXTERNAL_STORAGE permission is already granted
//        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            Manifest.permission.READ_MEDIA_IMAGES
//        else
//            Manifest.permission.READ_EXTERNAL_STORAGE
//
//
//
//            checkAndRequestPermissions()
//
////        if(ContextCompat.checkSelfPermission(this, readImagePermission) == PackageManager.PERMISSION_GRANTED)
////        {
//////permission granted
////            Log.e(TAG,"  325000    Permission Granted")
////        } else {
//////request permission here
////            Log.e(TAG,"325000      request permission here")
////        }
//    }
//
//    private fun checkAndRequestPermissions() {
//        val permissions = arrayOf(
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE)
//        // Check if any of the permissions are not granted
//        val permissionsToRequest = permissions.filter {
//            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
//        }
//
//        if (permissionsToRequest.isNotEmpty()) {
//            // Request the permissions that are not granted
//            requestMultiplePermissionsLauncher.launch(permissionsToRequest.toTypedArray())
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST_CODE
//            )
//        } else {
//            // All permissions are already granted
//            // Proceed with your operations
//            Log.e(TAG,"  32500033    Permission Granted")
//        }
//    }
//
//    private val requestMultiplePermissionsLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
//            // Check if all requested permissions are granted
//            val allPermissionsGranted = permissionsMap.all { it.value }
//
//            if (allPermissionsGranted) {
//                // All requested permissions are granted
//                // Proceed with your operations
//            } else {
//                // Some permissions are not granted
//                // You may want to inform the user about the importance of the permissions
//            }
//        }


    private fun requestCamera1Permission() {
        // Check if the device is running Android 6.0 (Marshmallow) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
        // If the device is running a version lower than Android 6.0,
        // the permission is already granted in the manifest
    }

    private fun requestReadPermission() {
        // Check if the device is running Android 6.0 (Marshmallow) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
        // If the device is running a version lower than Android 6.0,
        // the permission is already granted in the manifest
    }

    private fun requestWritePermission() {
        // Check if the device is running Android 6.0 (Marshmallow) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
        // If the device is running a version lower than Android 6.0,
        // the permission is already granted in the manifest
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@LeadGenerationQuickActivity, permission) == PackageManager.PERMISSION_DENIED) {
           // Toast.makeText(this@LeadGenerationQuickActivity, "Requesting the permission", Toast.LENGTH_SHORT).show()
            // Requesting the permission
            ActivityCompat.requestPermissions(this@LeadGenerationQuickActivity, arrayOf(permission), requestCode)
        } else {
          //  Toast.makeText(this@LeadGenerationQuickActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}