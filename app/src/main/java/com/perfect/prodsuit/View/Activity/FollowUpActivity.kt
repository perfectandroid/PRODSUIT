package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.R.id.message
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FollowUpActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {
    val TAG : String = "FollowUpActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient


    var til_Date: TextInputLayout? = null
    var til_NextFollowupDate: TextInputLayout? = null
    var til_CallTime: TextInputLayout? = null

    var tie_ActionType: TextInputEditText? = null
    var tie_FollowupBy: TextInputEditText? = null
    var tie_Status: TextInputEditText? = null
    var tie_Date: TextInputEditText? = null
    var tie_CustomerRemark: TextInputEditText? = null
    var tie_EmployeeRemarks: TextInputEditText? = null

    var tie_NextAction: TextInputEditText? = null
    var tie_NextActionType: TextInputEditText? = null
    var tie_NextFollowupDate: TextInputEditText? = null
    var tie_Priority: TextInputEditText? = null
    var tie_Department: TextInputEditText? = null
    var tie_NextEmployee: TextInputEditText? = null

//    Call

    var til_CallStatus: TextInputLayout? = null
    var til_CallDuration: TextInputLayout? = null
    var ll_location: LinearLayout? = null
    var ll_images: LinearLayout? = null


    var tie_CallTime: TextInputEditText? = null
    var tie_CallStatus: TextInputEditText? = null
    var tie_CallDuration: TextInputEditText? = null
    var tie_Latitude: TextInputEditText? = null
    var tie_Longitude: TextInputEditText? = null
    var imgv_upload1: ImageView? = null
    var imgv_upload2: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    private var strImage: String? = null
    private var destination: File? = null
    private var image1 = ""
    private var image2 = ""
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1



    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList : JSONArray
    lateinit var followUpTypeSort : JSONArray
    private var dialogFollowupType : Dialog? = null
    var recyFollowupType: RecyclerView? = null

    lateinit var employeeAllViewModel: EmployeeAllViewModel
    lateinit var employeeAllArrayList : JSONArray
    lateinit var employeeAllSort : JSONArray
    private var dialogEmployeeAll : Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    lateinit var leadByViewModel: LeadByViewModel
    lateinit var leadByArrayList : JSONArray
    var dialogLeadBy : Dialog? = null
    var recyLeadby: RecyclerView? = null
    lateinit var leadBySort : JSONArray

    lateinit var callStatusViewModel: CallStatusViewModel
    lateinit var callStatusArrayList : JSONArray
    var dialogCallStatus : Dialog? = null
    var recyCallStatus: RecyclerView? = null

    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var prodStatusArrayList : JSONArray
    lateinit var prodStatusSort : JSONArray
    private var dialogProdStatus : Dialog? = null
    var recyProdStatus: RecyclerView? = null

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var prodPriorityArrayList : JSONArray
    lateinit var prodPrioritySort : JSONArray
    private var dialogProdPriority : Dialog? = null
    var recyProdPriority: RecyclerView? = null

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

    lateinit var leadGenerateDefaultvalueViewModel: LeadGenerationDefaultvalueViewModel
    lateinit var updateLeadManagementViewModel: UpdateLeadManagementViewModel


    var ID_LeadGenerateProduct : String?= ""
    var ID_LeadGenerate : String?= ""
    var AssignedToID : String?= ""
    var AssignedTo : String?= ""
    var ActionModeIntent : String?= ""


    var ID_ActionType : String?= ""
    var ID_Employee : String?= ""
    var ID_Status : String?= ""

    var ID_NextAction : String?= ""
    var ID_NextActionType : String?= ""
    var ID_Priority : String?= ""
    var ID_Department : String?= ""
    var ID_NextEmployee : String?= ""

    private var ActiontypeFN:Int = 0
    private var DateType:Int = 0

    var isFuture = 0
    var strCallStatus:String?=""
    var strCallDuration:String?=""
    var strLongitude:String?=""
    var strLatitude:String?=""
    var strRiskType:String?=""
    var encode1: String?= ""
    var encode2: String?= ""
    var strAcknowledgement:String?="0"
    var strSiteVisit:String?="0"
    var strNotice:String?="0"
    var strFollowUpDate = ""
    var strFollowUpTime = ""
    var strCustomerRemark = ""
    var strEmployeeRemark = ""
    var strNextFollowUpDate = ""




    var btnReset: Button? = null
    var btnSubmit: Button? = null

    var ActionMode:String?=""

    var followUpType = 0
    var employee = 0
    var prodstatus = 0
    var followUpAction = 0
    var prodpriority = 0
    var department = 0
    var employee1 = 0
    var countLeadBy = 0
    var countCallStatus = 0
    var saveFollowupDet = 0

    private var tabLayout : TabLayout? = null
    private var card_followup : CardView? = null
    private var card_nextaction : CardView? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_follow_up)
        context = this@FollowUpActivity

        leadByViewModel = ViewModelProvider(this).get(LeadByViewModel::class.java)
        callStatusViewModel = ViewModelProvider(this).get(CallStatusViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        employeeAllViewModel = ViewModelProvider(this).get(EmployeeAllViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)

        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        leadGenerateDefaultvalueViewModel = ViewModelProvider(this).get(LeadGenerationDefaultvalueViewModel::class.java)
        updateLeadManagementViewModel = ViewModelProvider(this).get(UpdateLeadManagementViewModel::class.java)

        ActionMode = intent.getStringExtra("ActionMode")
        ActionModeIntent = intent.getStringExtra("ActionMode")
        ID_LeadGenerateProduct = intent.getStringExtra("ID_LeadGenerateProduct")
        ID_LeadGenerate = intent.getStringExtra("ID_LeadGenerate")
        AssignedToID = intent.getStringExtra("FK_Employee")
        AssignedTo = intent.getStringExtra("AssignedTo")

        Log.e(TAG,"LN204    "+ActionMode+"   :  "+ID_LeadGenerate+"   :   "+ID_LeadGenerateProduct)

        setRegViews()

//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val currentDate = sdf.format(Date())
//        tie_Date!!.setText(currentDate)
//        tie_NextFollowupDate!!.setText(currentDate)

        setDefaultFollowupBy()
        getDefaultValueSettings()
        getCurrentDateNTime()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        addTabItem()


    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Follow up"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Next Action"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Location"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Images"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Documents"))
       // tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        card_followup!!.visibility = View.VISIBLE
        card_nextaction!!.visibility = View.GONE
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)

                    card_followup!!.visibility = View.VISIBLE
                    card_nextaction!!.visibility = View.GONE
                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)

                    card_followup!!.visibility = View.GONE
                    card_nextaction!!.visibility = View.VISIBLE
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })
    }

    private fun getCurrentDateNTime() {

        val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        //   val currentTime1 = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())
        val currentTime1 = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date())

        Log.e(TAG,"CUR DATE TIME   1743     "+currentDate1+"   "+currentTime1)

        tie_CallTime!!.setText(currentTime1)

        tie_Date!!.setText(currentDate1)
     //   tie_NextFollowupDate!!.setText(currentDate1)
        tie_NextFollowupDate!!.setText("")

    }

    private fun setDefaultFollowupBy() {
//        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//        Log.e(TAG," UserName  143     "+UserNameSP.getString("UserName", null))
//        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null)
//        tie_FollowupBy!!.setText(UserNameSP.getString("UserName", null))


        ID_Employee = AssignedToID
        tie_FollowupBy!!.setText(AssignedTo)
        ActionMode = ActionModeIntent
        Log.e(TAG,"ActionMode  8141    "+ActionMode)
        if (ActionMode.equals("1") || ActionMode.equals("2")){
            tie_ActionType!!.isEnabled = false
            followUpType = 0
            getFollowupType()
        }

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        til_Date       = findViewById(R.id.til_Date) as TextInputLayout
        til_NextFollowupDate   = findViewById(R.id.til_NextFollowupDate) as TextInputLayout
        til_CallTime   = findViewById(R.id.til_CallTime) as TextInputLayout

        tie_ActionType       = findViewById(R.id.tie_ActionType) as TextInputEditText
        tie_FollowupBy       = findViewById(R.id.tie_FollowupBy) as TextInputEditText
        tie_Status           = findViewById(R.id.tie_Status) as TextInputEditText
        tie_Date             = findViewById(R.id.tie_Date) as TextInputEditText
        tie_CustomerRemark   = findViewById(R.id.tie_CustomerRemark) as TextInputEditText
        tie_EmployeeRemarks  = findViewById(R.id.tie_EmployeeRemarks) as TextInputEditText

        tie_NextAction       = findViewById(R.id.tie_NextAction) as TextInputEditText
        tie_NextActionType   = findViewById(R.id.tie_NextActionType) as TextInputEditText
        tie_NextFollowupDate = findViewById(R.id.tie_NextFollowupDate) as TextInputEditText
        tie_Priority         = findViewById(R.id.tie_Priority) as TextInputEditText
        tie_Department       = findViewById(R.id.tie_Department) as TextInputEditText
        tie_NextEmployee     = findViewById(R.id.tie_NextEmployee) as TextInputEditText

        til_CallStatus              = findViewById(R.id.til_CallStatus) as TextInputLayout
        til_CallDuration            = findViewById(R.id.til_CallDuration) as TextInputLayout
        ll_location                 = findViewById(R.id.ll_location) as LinearLayout
        ll_images                   = findViewById(R.id.ll_images) as LinearLayout


        tie_CallTime             = findViewById(R.id.tie_CallTime) as TextInputEditText
        tie_CallStatus = findViewById<TextInputEditText>(R.id.tie_CallStatus)
        tie_CallDuration = findViewById<TextInputEditText>(R.id.tie_CallDuration)

        tie_Latitude             = findViewById(R.id.tie_Latitude) as TextInputEditText
        tie_Longitude             = findViewById(R.id.tie_Longitude) as TextInputEditText
        imgv_upload1 = findViewById(R.id.imgv_upload1) as ImageView
        imgv_upload2 = findViewById(R.id.imgv_upload2) as ImageView

        tabLayout = findViewById(R.id.tabLayout) as TabLayout
        card_followup = findViewById(R.id.card_followup) as CardView
        card_nextaction = findViewById(R.id.card_nextaction) as CardView



        btnSubmit     = findViewById(R.id.btnSubmit) as Button
        btnReset     = findViewById(R.id.btnReset) as Button

        tie_ActionType!!.setOnClickListener(this)
        val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
        var isAdmin = IsAdminSP.getString("IsAdmin", null)
        if (isAdmin.equals("1")){
            tie_FollowupBy!!.setOnClickListener(this)
        }

        tie_Status!!.focusable
        tie_Status!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)

        tie_NextAction!!.setOnClickListener(this)
        tie_NextActionType!!.setOnClickListener(this)
        tie_NextFollowupDate!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_NextEmployee!!.setOnClickListener(this)


      //  tie_CallTime!!.setOnClickListener(this)

        tie_CallStatus!!.setOnClickListener(this)
        tie_Latitude!!.setOnClickListener(this)
        tie_Longitude!!.setOnClickListener(this)
        imgv_upload1!!.setOnClickListener(this)
        imgv_upload2!!.setOnClickListener(this)


        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        til_Date!!.hint = "Date"

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                setResult(2)
                finish()
            }

            R.id.tie_ActionType->{
                Config.disableClick(v)
                ActiontypeFN = 0
                followUpType = 0
                getFollowupType()
            }
            R.id.tie_FollowupBy->{
               // employee = 0
                Config.disableClick(v)
               // getAllEmployee()

                countLeadBy = 0
                getLeadBy(v)

            }
            R.id.tie_Status->{
                Config.disableClick(v)
                prodstatus = 0
                getStatus()
            }
            R.id.tie_Date->{
                DateType = 0
                openBottomSheet()
            }
            R.id.tie_NextAction->{
                Config.disableClick(v)
                followUpAction = 0
                getFollowupAction()
            }
            R.id.tie_NextActionType->{
                Config.disableClick(v)
                ActiontypeFN = 1
                followUpType = 0
                getFollowupType()
            }
            R.id.tie_NextFollowupDate->{

                DateType = 1
                openBottomSheet1()
            }
            R.id.tie_Priority->{
                Config.disableClick(v)
                prodpriority = 0
                getProductPriority()
            }

            R.id.tie_Department->{
                Config.disableClick(v)
                department = 0
                getDepartment()
            }

            R.id.tie_NextEmployee->{

                if (ID_Department.equals("")){

                    Config.snackBars(context,v,"Select Department")

                }else{
                    Config.disableClick(v)
                    employee = 0
                    getEmployee()
                }

            }


//            R.id.tie_CallTime->{
//
//            }

            R.id.tie_CallStatus->{
                Config.disableClick(v)
                countCallStatus = 0
                getCallStatus()
            }


            R.id.tie_Latitude->{
                Config.disableClick(v)
                getLastLocation()
            }
            R.id.tie_Longitude->{
                Config.disableClick(v)
                getLastLocation()
            }

            R.id.imgv_upload1->{
                try
                {
                    Config.Utils.hideSoftKeyBoard(this@FollowUpActivity,v)
                    strImage="1"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }
            R.id.imgv_upload2->{
                try {
                    Config.Utils.hideSoftKeyBoard(this@FollowUpActivity,v)
                    strImage="2"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }


            R.id.btnReset->{

                Config.disableClick(v)
//                if (ActionMode.equals("1") || ActionMode.equals("2")){
                if (ActionModeIntent.equals("1") || ActionModeIntent.equals("2")){
                    clearData("0")
                }else{
                    clearData("1")
                }

            }

            R.id.btnSubmit->{
                saveFollowupDet = 0
                Config.disableClick(v)
                ValidateData(v)
            }




        }
    }

    private fun getCallStatus() {

        try {
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()

                    callStatusViewModel.getCallStatus(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   228   "+msg.length)
                                    Log.e(TAG,"msg   228   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("StatusDetailsList")
                                        callStatusArrayList = jobjt.getJSONArray("StatusList")
                                        if (callStatusArrayList.length()>0){
                                            if (countCallStatus == 0){
                                                countCallStatus++
                                             //   leadByPopup(leadByArrayList)

                                                callStatusPopups(callStatusArrayList)
                                            }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                        .show()
                }

            }



//            val builder = android.app.AlertDialog.Builder(this)
//            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout = inflater1.inflate(R.layout.callstatus_popup, null)
//            val lvCallStatus  = layout.findViewById<ListView>(R.id.lvCallStatus)
//            builder.setView(layout)
//            val alertDialog = builder.create()
//            val listItem = resources.getStringArray(R.array.callstatus)
//            val adapter = ArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, listItem
//            )
//            lvCallStatus.setAdapter(adapter)
//            lvCallStatus.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
//                // TODO Auto-generated method stub
//                val value = adapter.getItem(position)
//                tie_CallStatus!!.setText(value)
//                if (position == 0) {
//                    strCallStatus = "1"
//                }
//                if (position == 1) {
//                    strCallStatus = "2"
//                }
//                if (position == 2) {
//                    strCallStatus = "3"
//                }
////                if (position == 2) {
////                    FollowUpActivity.strRiskType = "3"
////                }
//                alertDialog.dismiss()
//            })
//            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callStatusPopups(callStatusArrayList: JSONArray) {

        try {

            dialogCallStatus = Dialog(this)
            dialogCallStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCallStatus!! .setContentView(R.layout.call_status_popup)
            dialogCallStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCallStatus = dialogCallStatus!! .findViewById(R.id.recyCallStatus) as RecyclerView
        //    val etsearch = dialogCallStatus!! .findViewById(R.id.etsearch) as EditText



            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyCallStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            val adapter = CallStatusAdapter(this@FollowUpActivity, callStatusArrayList)
            recyCallStatus!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

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
//                    leadBySort = JSONArray()
//
//                    for (k in 0 until leadByArrayList.length()) {
//                        val jsonObject = leadByArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("Name").length) {
//                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                                leadBySort.put(jsonObject)
//                            }
//
//                        }
//                    }
//
//                    Log.e(TAG,"leadBySort               7103    "+leadBySort)
//                    val adapter = LeadByAdapter(this@FollowUpActivity, leadBySort)
//                    recyLeadby!!.adapter = adapter
//                    adapter.setClickListener(this@FollowUpActivity)
//                }
//            })

            dialogCallStatus!!.show()
            dialogCallStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {

                        tie_Longitude!!.setText(location.longitude.toString())
                        tie_Latitude!!.setText(location.latitude.toString())
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            tie_Longitude!!.setText(mLastLocation.longitude.toString())
            tie_Latitude!!.setText(mLastLocation.latitude.toString())
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select From")
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(pictureDialogItems   ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun checkCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;

    }

    private fun takePhotoFromCamera() {
        val photo =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photo, CAMERA)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG","onActivityResult  256   "+requestCode+ "   "+resultCode+ "  "+data)
        if (requestCode == GALLERY ) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if(strImage.equals("1")) {
                        imgv_upload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        if (image1 != null) {
                        }
                    }
                    if(strImage.equals("2")) {
                        imgv_upload2!!.setImageURI(contentURI)
                        image2 = getRealPathFromURI(selectedImageUri)
                        if (image2 != null) {
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@FollowUpActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
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
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }
                    else {
                        val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                        val bytes = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                        destination = File(
                            (Environment.getExternalStorageDirectory()).toString() + "/" +
                                    getString(R.string.app_name),
                            "IMG_" + System.currentTimeMillis() + ".jpg"
                        )
                        val fo: FileOutputStream
                        try {
                            if (!destination!!.getParentFile().exists()) {
                                destination!!.getParentFile().mkdirs()
                            }
                            if (!destination!!.exists()) {
                                destination!!.createNewFile()
                            }
                            fo = FileOutputStream(destination)
                            fo.write(bytes.toByteArray())
                            fo.close()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        if (strImage.equals("1")) {
                            image1 = destination!!.getAbsolutePath()
                            destination = File(image1)
                            val myBitmap = BitmapFactory.decodeFile(destination.toString())
                            if (imgv_upload1 != null) {
                                imgv_upload1!!.setImageBitmap(myBitmap)
                            }
                            imgv_upload1!!.setImageBitmap(myBitmap)
                            if (image1 != null) {
                            }
                        }
                        if (strImage.equals("2")) {
                            image2 = destination!!.getAbsolutePath()
                            destination = File(image2)
                            val myBitmap = BitmapFactory.decodeFile(destination.toString())
                            if (imgv_upload2 != null) {
                                imgv_upload2!!.setImageBitmap(myBitmap)
                            }
                            imgv_upload2!!.setImageBitmap(myBitmap)
                            if (image2 != null) {
                            }
                        }
                    }
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@FollowUpActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }


    private fun getDefaultValueSettings() {

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
                        try {
                            if (msg!!.length > 0) {

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   14781   "+msg.length)
                                Log.e(TAG,"msg   14782   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("LeadGenerationDefaultvalueSettings")
                                    Log.e(TAG,"msg   14783   "+jobjt.getString("EmpFName"))

                                    ID_Department = jobjt.getString("FK_Department")
                                    tie_Department!!.setText(jobjt.getString("Department"))
                                    ID_NextEmployee = jobjt.getString("ID_Employee")
                                    tie_NextEmployee!!.setText(jobjt.getString("EmpFName"))


                                } else {
//                                val builder = AlertDialog.Builder(
//                                    this@LeadGenerationActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
                                }
                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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

    private fun getFollowupType() {
//        var followUpType = 0
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
                                if (followUpType == 0){
                                    followUpType++


                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                        followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                        if (followUpTypeArrayList.length()>0){

                                            Log.e(TAG,"8142    "+followUpTypeArrayList)
                                            Log.e(TAG,"81422    "+ActionMode)

                                            if (ActionModeIntent!!.equals("1") || ActionModeIntent!!.equals("2")){
                                                for (i in 0 until followUpTypeArrayList.length()) {
                                                    val jsonObject = followUpTypeArrayList.getJSONObject(i)
                                                    Log.e(TAG,"8143    "+jsonObject.getString("ID_ActionType")+"   :  "+i+"  :   "+ActionMode)


                                                    if (jsonObject.getString("ActionMode")!!.contains(ActionMode!!)){
                                                        Log.e(TAG,"81431    "+jsonObject.getString("ID_ActionType")+"   :  "+i+"  :   "+ActionMode)
                                                        if (jsonObject.getString("ActionMode").equals("1")){
                                                            Log.e(TAG,"81432    "+jsonObject.getString("ID_ActionType")+"   :  "+i+"  :   "+ActionMode)
                                                            ID_ActionType = jsonObject.getString("ID_ActionType")
                                                            tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))
                                                            ActionMode =  jsonObject.getString("ActionMode")
                                                            til_CallStatus!!.visibility = View.VISIBLE
//                                                            til_CallDuration!!.visibility = View.VISIBLE
                                                            ll_location!!.visibility = View.GONE
                                                            ll_images!!.visibility = View.GONE
//                                                            til_CallTime!!.visibility = View.VISIBLE
                                                        }
                                                        if (jsonObject.getString("ActionMode").equals("2")){
                                                            Log.e(TAG,"81434    "+jsonObject.getString("ID_ActionType")+"   :  "+i+"  :   "+ActionMode)
                                                            ID_ActionType = jsonObject.getString("ID_ActionType")
                                                            tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))
                                                            ActionMode =  jsonObject.getString("ActionMode")
                                                            til_CallStatus!!.visibility = View.GONE
//                                                            til_CallDuration!!.visibility = View.GONE
                                                            ll_location!!.visibility = View.VISIBLE
                                                            ll_images!!.visibility = View.VISIBLE

//                                                            til_CallTime!!.visibility = View.VISIBLE
                                                        }
                                                    }
//                                                    if (jsonObject.getString("ID_ActionType").equals("1")){
//
//                                                    }
//                                                    if (jsonObject.getString("ID_ActionType").equals("2")){
//
//                                                    }

                                                }

                                            }else{

                                                tie_ActionType!!.isEnabled = true
                                                followupTypePopup(followUpTypeArrayList)
                                            }

                                        }
                                    } else {
                                        tie_ActionType!!.isEnabled = true
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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
                            tie_ActionType!!.isEnabled = true
                            Log.e(TAG,"8146    "+e.toString())
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
                tie_ActionType!!.isEnabled = true
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!! .findViewById(R.id.etsearch) as EditText

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupTypeAdapter(this@FollowUpActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@FollowUpActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

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
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpTypeSort               7103    "+followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@FollowUpActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLeadBy(v: View) {

//        var countLeadBy = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadByViewModel.getLeadBy(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   228   "+msg.length)
                            Log.e(TAG,"msg   228   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CollectedByUsersList")
                                leadByArrayList = jobjt.getJSONArray("CollectedByUsers")
                                if (leadByArrayList.length()>0){
                                    if (countLeadBy == 0){
                                        countLeadBy++
                                        leadByPopup(leadByArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@FollowUpActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }

    private fun leadByPopup(leadByArrayList: JSONArray) {

        try {

            dialogLeadBy = Dialog(this)
            dialogLeadBy!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadBy!! .setContentView(R.layout.lead_by_popup)
            dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadby = dialogLeadBy!! .findViewById(R.id.recyLeadby) as RecyclerView
            val etsearch = dialogLeadBy!! .findViewById(R.id.etsearch) as EditText

            leadBySort = JSONArray()
            for (k in 0 until leadByArrayList.length()) {
                val jsonObject = leadByArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadBySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyLeadby!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            val adapter = LeadByAdapter(this@FollowUpActivity, leadBySort)
            recyLeadby!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadBySort = JSONArray()

                    for (k in 0 until leadByArrayList.length()) {
                        val jsonObject = leadByArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                leadBySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"leadBySort               7103    "+leadBySort)
                    val adapter = LeadByAdapter(this@FollowUpActivity, leadBySort)
                    recyLeadby!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogLeadBy!!.show()
            dialogLeadBy!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAllEmployee() {

//        var employee = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeAllViewModel.getEmployeeAll(this)!!.observe(
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
                                        employeeAllArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length()>0){

                                            employeeAllPopup(employeeAllArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllSort)
            recyEmployeeAll!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

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
                    val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

                                if (employee1 == 0){
                                    employee1++

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
                                            this@FollowUpActivity,
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


            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

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
                    val adapter = EmployeeAdapter(this@FollowUpActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStatus() {
       // var prodstatus = 0
        var ReqMode = "61"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (prodstatus == 0){
                                    prodstatus++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   333   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("StatusDetailsList")
                                        prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                        if (prodStatusArrayList.length()>0){

                                            productStatusPopup(prodStatusArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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

    private fun productStatusPopup(prodStatusArrayList: JSONArray) {
        try {
            dialogProdStatus = Dialog(this)
            dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdStatus!! .setContentView(R.layout.product_status_popup)
            dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdStatus = dialogProdStatus!! .findViewById(R.id.recyProdStatus) as RecyclerView
            val etsearch = dialogProdStatus!! .findViewById(R.id.etsearch) as EditText

            prodStatusSort = JSONArray()
            for (k in 0 until prodStatusArrayList.length()) {
                val jsonObject = prodStatusArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodStatusSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = ProductStatusAdapter(this@FollowUpActivity, prodStatusArrayList)
            val adapter = ProductStatusAdapter(this@FollowUpActivity, prodStatusSort)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodStatusSort = JSONArray()

                    for (k in 0 until prodStatusArrayList.length()) {
                        val jsonObject = prodStatusArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("StatusName").length) {
                            if (jsonObject.getString("StatusName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                prodStatusSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"prodStatusSort               7103    "+prodStatusSort)
                    val adapter = ProductStatusAdapter(this@FollowUpActivity, prodStatusSort)
                    recyProdStatus!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogProdStatus!!.show()
            dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        val date_Picker = view.findViewById<DatePicker>(R.id.date_Picker1)
        if (isFuture == 1){
            date_Picker.minDate = System.currentTimeMillis()
        }else{
            date_Picker.maxDate = System.currentTimeMillis()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }


                if (DateType == 0){
                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }





            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomSheet1() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1.setMinDate(System.currentTimeMillis());

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


                if (DateType == 0){
                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (DateType == 1){
                    tie_NextFollowupDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }





            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }



    private fun getFollowupAction() {
//        var followUpAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (followUpAction == 0){
                                    followUpAction++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length()>0){

                                            followUpActionPopup(followUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

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

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)


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
                    val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProductPriority() {
//        var prodpriority = 0
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

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (prodpriority == 0){
                                    prodpriority++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   353   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                        prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                        if (prodPriorityArrayList.length()>0){

                                                productPriorityPopup(prodPriorityArrayList)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@FollowUpActivity,
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

    private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

        try {

            dialogProdPriority = Dialog(this)
            dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdPriority!! .setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority = dialogProdPriority!! .findViewById(R.id.recyProdPriority) as RecyclerView
            val etsearch = dialogProdPriority!! .findViewById(R.id.etsearch) as EditText

            prodPrioritySort = JSONArray()
            for (k in 0 until prodPriorityArrayList.length()) {
                val jsonObject = prodPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodPrioritySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPrioritySort)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)


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
                            if (jsonObject.getString("PriorityName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                prodPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"prodPrioritySort               7103    "+prodPrioritySort)
                    val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPrioritySort)
                    recyProdPriority!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                                            this@FollowUpActivity,
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

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentSort)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

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
                    val adapter = DepartmentAdapter(this@FollowUpActivity, departmentSort)
                    recyDeaprtment!!.adapter = adapter
                    adapter.setClickListener(this@FollowUpActivity)
                }
            })

            dialogDepartment!!.show()
            dialogDepartment!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"data   623 "+data)

        if (data.equals("followuptype")){

            dialogFollowupType!!.dismiss()
//            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))

            Log.e(TAG,"ActiontypeFN   "+ActiontypeFN)
            Log.e(TAG,"ActionMode   "+jsonObject.getString("ActionMode"))

            if (ActiontypeFN == 0 ){
                ID_ActionType = jsonObject.getString("ID_ActionType")
                tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))
                ActionMode =  jsonObject.getString("ActionMode")

                strCallStatus = ""
                strCallDuration = ""
                tie_CallStatus!!.setText("")
                tie_CallDuration!!.setText("")

                tie_Longitude!!.setText("")
                tie_Latitude!!.setText("")

                image1 = ""
                image2 = ""
                imgv_upload1!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))
                imgv_upload2!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))

                if (jsonObject.getString("ActionMode").equals("1")){

                    til_CallStatus!!.visibility = View.VISIBLE
//                    til_CallDuration!!.visibility = View.VISIBLE
                    ll_location!!.visibility = View.GONE
                    ll_images!!.visibility = View.GONE
//                    til_CallTime!!.visibility = View.VISIBLE

                }else if(jsonObject.getString("ActionMode").equals("2")){

                    til_CallStatus!!.visibility = View.GONE
//                    til_CallDuration!!.visibility = View.GONE
                    ll_location!!.visibility = View.VISIBLE
                    ll_images!!.visibility = View.VISIBLE

//                    til_CallTime!!.visibility = View.VISIBLE
                }
                else{
//                    til_CallTime!!.visibility = View.GONE
                    til_CallStatus!!.visibility = View.GONE
//                    til_CallDuration!!.visibility = View.GONE
                    ll_location!!.visibility = View.GONE
                    ll_images!!.visibility = View.GONE

                }

//                tie_CallStatus!!.setText("")
//                tie_CallDuration!!.setText("")
//
//                strCallStatus = ""
//                strRiskType = ""
//                tie_Longitude!!.setText("")
//                tie_Latitude!!.setText("")
//                image1 = ""
//                image2 = ""
//                imgv_upload1!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))
//                imgv_upload2!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))

          //      clearData("0")




//                var til_CallStatus: TextInputLayout? = null
//                var til_CallDuration: TextInputLayout? = null


            }
            if (ActiontypeFN == 1 ){
                ID_NextActionType = jsonObject.getString("ID_ActionType")
                tie_NextActionType!!.setText(jsonObject.getString("ActnTypeName"))
            }



        }
        if (data.equals("employeeAll")){
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_FollowupBy!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("leadby")){
            dialogLeadBy!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = leadBySort.getJSONObject(position)

            Log.e(TAG,"jsonObject   "+jsonObject)
            ID_Employee = jsonObject.getString("ID_CollectedBy")
            tie_FollowupBy!!.setText(jsonObject.getString("Name"))


        }

        if (data.equals("callStatus")){
            dialogCallStatus!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = callStatusArrayList.getJSONObject(position)

            Log.e(TAG,"jsonObject   "+jsonObject)
            strCallStatus = jsonObject.getString("ID_Status")
            tie_CallStatus!!.setText(jsonObject.getString("StatusName"))



        }



        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
//            val jsonObject = prodStatusArrayList.getJSONObject(position)
            val jsonObject = prodStatusSort.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            var IsEnable = jsonObject.getString("IsEnable")
            tie_Status!!.setText(jsonObject.getString("StatusName"))
            til_Date!!.hint = (jsonObject.getString("StatusName")+" Date")

            if (ID_Status.equals("2")){
                isFuture = 1
            }
            else{
                isFuture = 0
            }

            if (IsEnable.equals("0")){
                (tabLayout!!.getChildAt(0) as ViewGroup).getChildAt(1).isEnabled = false
            }else{
                (tabLayout!!.getChildAt(0) as ViewGroup).getChildAt(1).isEnabled = true
            }
        }

        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
//            val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            tie_NextAction!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("prodpriority")){
            dialogProdPriority!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = prodPrioritySort.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            tie_Priority!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
//            val jsonObject = departmentArrayList.getJSONObject(position)
            val jsonObject = departmentSort.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))

            ID_NextEmployee = ""
            tie_NextEmployee!!.setText("")


        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_NextEmployee = jsonObject.getString("ID_Employee")
            tie_NextEmployee!!.setText(jsonObject.getString("EmpName"))


        }



    }

    private fun clearData(mode :  String) {

        if (!mode.equals("0")){
            tie_ActionType!!.setText("")
            ID_ActionType = ""

            til_CallStatus!!.visibility = View.GONE
//            til_CallDuration!!.visibility = View.GONE
            ll_location!!.visibility = View.GONE
            ll_images!!.visibility = View.GONE
//            til_CallTime!!.visibility = View.GONE

        }


        tie_FollowupBy!!.setText("")
        tie_Status!!.setText("")
        tie_CustomerRemark!!.setText("")
        tie_EmployeeRemarks!!.setText("")


        ID_Employee = ""
        ID_Status = ""

        tie_NextAction!!.setText("")
        tie_NextActionType!!.setText("")
        tie_Priority!!.setText("")
        tie_Department!!.setText("")
        tie_NextEmployee!!.setText("")

        ID_NextAction = ""
        ID_NextActionType = ""
        ID_Priority = ""
        ID_Department = ""
        ID_NextEmployee = ""

        tie_CallStatus!!.setText("")
        tie_CallDuration!!.setText("")

        isFuture = 0  // 0 = back , 1 = future
        strCallStatus = ""
        strCallDuration=""
        strLongitude=""
        strLatitude=""
        encode1 = ""
        encode2 = ""
        strRiskType = ""
        tie_Longitude!!.setText("")
        tie_Latitude!!.setText("")
        image1 = ""
        image2 = ""
        imgv_upload1!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))
        imgv_upload2!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))



        getCurrentDateNTime()

//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val currentDate = sdf.format(Date())
//        tie_Date!!.setText(currentDate)
//        tie_NextFollowupDate!!.setText(currentDate)

        til_Date!!.hint = "Date"
        setDefaultFollowupBy()
        getDefaultValueSettings()
    }

    private fun ValidateData(v: View) {


        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val inputTimeFormat = SimpleDateFormat("h:mm a")
        val outputTimeFormat = SimpleDateFormat("HH:mm:ss")

        val dateFollowUp = inputFormat.parse(tie_Date!!.text.toString())
        strFollowUpDate = outputFormat.format(dateFollowUp)
        Log.e(TAG,"str    "+tie_CallTime!!.text.toString())
        Log.e(TAG,"ActionMode   2465   "+ActionMode)

        try {
            var date: Date? = null
            var str: String? = null
            date = inputTimeFormat.parse(tie_CallTime!!.text.toString())
            strFollowUpTime = outputTimeFormat.format(date)
            Log.e(TAG,"str   2481   "+str)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"str  2481  "+e.toString())
        }


        strNextFollowUpDate = ""
        strCallDuration = ""
        if (!tie_NextFollowupDate!!.text.toString().equals("")){
            val dateNextFollowUp = inputFormat.parse(tie_NextFollowupDate!!.text.toString())
            strNextFollowUpDate = outputFormat.format(dateNextFollowUp)
        }



        strCustomerRemark = tie_CustomerRemark!!.text.toString()
        strEmployeeRemark = tie_EmployeeRemarks!!.text.toString()


        if (ID_ActionType.equals("")){

            Config.snackBars(context,v,"Select Followup Action Type")
        }
        else if (ID_Employee.equals("")){
            Config.snackBars(context,v,"Select Followup By")
        }
        else if (ID_Status.equals("")){
            Config.snackBars(context,v,"Select Status")
        }

        else{


//            Log.e(TAG,"FOLLOWUP  9941 "
//                    +"\n ID_LeadGenerateProduct :  "+ID_LeadGenerateProduct
//                    +"\n ID_LeadGenerate        :  "+ID_LeadGenerate
//                    +"\n ID_ActionType          :  "+ID_ActionType
//                    +"\n ID_Employee            :  "+ID_Employee
//                    +"\n ID_Status              :  "+ID_Status
//                    +"\n strFollowUpDate        :  "+strFollowUpDate
//                    +"\n strFollowUpTime        :  "+strFollowUpTime
//                    +"\n strCallStatus          :  "+strCallStatus
//                    +"\n strCallDuration        :  "+strCallDuration
//                    +"\n strCustomerRemark      :  "+strCustomerRemark
//                    +"\n strEmployeeRemark      :  "+strEmployeeRemark)




           val strCallDur = tie_CallDuration!!.text.toString()

            if (ActionMode.equals("1")){

                if (strCallStatus.equals("")){
                    Config.snackBars(context,v,"Select Call Status")
                }
//                else if (strCallDur.equals("")){
//                    Config.snackBars(context,v,"Enter Call Duration")
//                }
                else{
                  //  val millis: Long = 35555
//                    val millis: Long = strCallDur.toLong()
//                    strCallDuration = java.lang.String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
//                    )

//                    val Second: Long = strCallDur.toLong()
//                    strCallDuration = java.lang.String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(Second),
//                        TimeUnit.SECONDS.toMinutes(Second) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(Second)),
//                        TimeUnit.SECONDS.toSeconds(Second) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(Second))
//                    )
//
//                    Log.e(TAG,"2547 strCallDuration   "+strCallDuration)

                    // val seconds = 3661
//                    val seconds = strCallDur.toInt()
//                    val duration: String = convertSeconds(seconds)
//                    strCallDuration = duration
//                    Log.e(TAG,"duration   2463   "+duration)
                    strCallDuration = "00:00:00"

                    saveUpdate()
//                    saveUpdateLeadManagement(ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,
//                        strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,
//                        strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)
                }

            }else if (ActionMode.equals("2")){
                strCallDuration = "00:00:00"
                strLongitude =tie_Longitude!!.text.toString();
                strLatitude=tie_Latitude!!.text.toString();

                if (strLatitude.equals("")){
                    Config.snackBars(context,v,"Select Latitude")
                }
                else if (strLongitude.equals("")){
                    Config.snackBars(context,v,"Select Longitude")
                }
                else{

                    if(image1.equals(""))
                    {
                        encode1 = ""
                    }
                    else
                    {
                        val bitmap = BitmapFactory.decodeFile(image1)
                        val stream =  ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
                        } else {
                            encode1 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                        }
                    }
                    if(image2.equals(""))
                    {
                        encode2 = ""
                    }
                    else
                    {
                        val bitmap = BitmapFactory.decodeFile(image2)
                        val stream =  ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
                        } else {
                            encode2 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                        }
                    }

                    saveUpdate()

//                    saveUpdateLeadManagement(ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,
//                        strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,
//                        strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)
                }
            }
            else{
//                saveUpdateLeadManagement(ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,
//                    strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,
//                    strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)

                strCallDuration = "00:00:00"
                saveUpdate()
            }




        }
    }

    private fun convertSeconds(seconds: Int): String {
//        val h = seconds / 3600
//        val m = seconds % 3600 / 60
//        val s = seconds % 60
//        val sh = if (h > 0) "$h h" else ""
//        val sm =
//            (if (m < 10 && m > 0 && h > 0) "0" else "") + if (m > 0) if (h > 0 && s == 0) m.toString() else "$m min" else ""
//        val ss =
//            if (s == 0 && (h > 0 || m > 0)) "" else (if (s < 10 && (h > 0 || m > 0)) "0" else "") + s.toString() + " " + "sec"
//        return sh + (if (h > 0) " " else "") + sm + (if (m > 0) " " else "") + ss

        val hours = seconds / 3600;
        val minutes = (seconds % 3600) / 60;
        val second = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(second)
    }
    private fun twoDigitString(number: Int): String? {
        if (number == 0) {
            return "00"
        }
        return if (number / 10 == 0) {
            "0$number"
        } else number.toString()
    }

    private fun saveUpdate() {



        Log.e(TAG,"FOLLOWUP  25981 "
                +"\n ID_LeadGenerateProduct :  "+ID_LeadGenerateProduct
                +"\n ID_LeadGenerate        :  "+ID_LeadGenerate
                +"\n ID_ActionType          :  "+ID_ActionType
                +"\n ID_Employee            :  "+ID_Employee
                +"\n ID_Status              :  "+ID_Status
                +"\n strFollowUpDate        :  "+strFollowUpDate
                +"\n strFollowUpTime        :  "+strFollowUpTime
                +"\n strCallStatus          :  "+strCallStatus
                +"\n strCallDuration        :  "+strCallDuration
                +"\n strCustomerRemark      :  "+strCustomerRemark
                +"\n strEmployeeRemark      :  "+strEmployeeRemark

                +"\n NEXT ACTION                :  "
                +"\n ID_NextAction              :  "+ID_NextAction
                +"\n ID_NextActionType          :  "+ID_NextActionType
                +"\n val strNextFollowUpDate    :  "+strNextFollowUpDate
                +"\n ID_Priority                :  "+ID_Priority
                +"\n ID_Department              :  "+ID_Department
                +"\n ID_NextEmployee            :  "+ID_NextEmployee)



        saveUpdateLeadManagement(ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,strFollowUpTime,
            strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,
            strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)




    }

    private fun saveUpdateLeadManagement(ID_LeadGenerateProduct: String?, ID_LeadGenerate: String?, ID_ActionType: String?, ID_Employee: String?,
        ID_Status: String?, strFollowUpDate: String,strFollowUpTime : String, strCustomerRemark: String, strEmployeeRemark: String, ID_NextAction: String?, ID_NextActionType: String?,
        strNextFollowUpDate: String, ID_Priority: String?, ID_Department: String?, ID_NextEmployee: String?,
                                         strCallStatus: String?,strCallDuration: String?,strLatitude: String?,strLongitude: String?,encode1: String?,encode2: String?) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                updateLeadManagementViewModel.getUpdateLeadManagement(this,ID_LeadGenerateProduct!!,ID_LeadGenerate!!,ID_ActionType!!,ID_Employee!!,ID_Status!!,strFollowUpDate,
                    strFollowUpTime, strCustomerRemark,strEmployeeRemark,ID_NextAction!!,ID_NextActionType!!,strNextFollowUpDate,ID_Priority!!,ID_Department!!,ID_NextEmployee!!,
                    strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)!!.observe(
                    this,
                    Observer { deleteleadSetterGetter ->
                        val msg = deleteleadSetterGetter.message
                        try {
                                if (msg!!.length > 0) {
                                    Log.e(TAG,"msg  1126     "+msg)
                                    val jObject = JSONObject(msg)
                                    if (saveFollowupDet == 0){
                                        saveFollowupDet++
                                        if (jObject.getString("StatusCode") == "0") {

                                            val jobjt = jObject.getJSONObject("UpdateLeadManagement")
                                            try {

                                                val suceessDialog = Dialog(this)
                                                suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                suceessDialog!!.setCancelable(false)
                                                suceessDialog!! .setContentView(R.layout.success_popup)
                                                suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

                                                val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
                                                val tv_label = suceessDialog!! .findViewById(R.id.tv_label) as TextView
                                                val tv_leadid = suceessDialog!! .findViewById(R.id.tv_leadid) as TextView
                                                val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView
                                                //LeadNumber
                                                tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
                                                tv_label!!.setText("LeadGenerate Action")
                                                tv_leadid!!.setText(jobjt.getString("FK_LeadGenerateAction"))

                                                tv_succesok!!.setOnClickListener {
                                                    suceessDialog!!.dismiss()
                                                    val intent = Intent()
                                                    intent.putExtra("MESSAGE", message)
                                                    setResult(2, intent)
                                                    onBackPressed()

                                                }

                                                suceessDialog!!.show()
                                                suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@FollowUpActivity,
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

                                }

                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        Log.e(TAG,"onBackPressed   2858   ")
        super.onBackPressed()
    }


}