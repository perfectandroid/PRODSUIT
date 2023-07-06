package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class LocationMarkingNewActivity : AppCompatActivity(), OnMapReadyCallback , View.OnClickListener,
    ItemClickListener {

    var TAG = "LocationMarkingNewActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    lateinit var locationList : JSONArray

    var listMode: String? = "1"
    var detailMode: String? = "0"
    private var tv_tab_StaffList: TextView? = null
    private var tv_tab_StaffDetails: TextView? = null
    private var imgList: ImageView? = null
    private var imgMap: ImageView? = null
    private var ll_details: LinearLayout? = null
    private var ll_list: LinearLayout? = null
    private var ll_select_list: LinearLayout? = null
    private var ll_select_map: LinearLayout? = null
    var recyStaffList: RecyclerView? = null

    private var imgv_filter: ImageView? = null
    private var ll_StafAdmin: LinearLayout? = null
    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null

    private var tie_Branch: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Designation: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_Date: TextInputEditText? = null

    private var til_Branch: TextInputLayout? = null
    private var til_Department: TextInputLayout? = null
    private var til_Designation: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null
    private var til_Date: TextInputLayout? = null

    private var tv_error: TextView? = null
    private var ll_nodata: LinearLayout? = null
    private var ll_data: LinearLayout? = null

    var strDate: String? = ""
    var branchCount = 0
    var department = 0
    var employee = 0
    var designation = 0
    var EmployeeLocation = 0

    var ID_Branch : String?= "0"
    var ID_Department : String?= "0"
    var ID_Designation : String?= "0"
    var ID_Employee : String?= "0"

    var temp_Branch : String?= ""
    var temp_Department : String?= ""
    var temp_Designation : String?= ""
    var temp_Employee : String?= ""
    var temp_Date : String?= ""

    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null

    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var departmentArrayList : JSONArray
    lateinit var departmentSort : JSONArray
    private var dialogDepartment : Dialog? = null
    var recyDeaprtment: RecyclerView? = null

    lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel
    lateinit var employeeArrayList : JSONArray
    lateinit var employeeSort : JSONArray
    private var dialogEmployee : Dialog? = null
    var recyEmployee: RecyclerView? = null

    lateinit var designationViewModel: DesignationViewModel
    lateinit var designationArrayList : JSONArray
    lateinit var designationtSort : JSONArray
    private var dialogDesignation : Dialog? = null
    var recyDesignation: RecyclerView? = null

    lateinit var employeeLocationListViewModel: EmployeeLocationListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_location_marking_new)
        context = this@LocationMarkingNewActivity
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeDetailsViewModel = ViewModelProvider(this).get(EmployeeDetailsViewModel::class.java)
        designationViewModel = ViewModelProvider(this).get(DesignationViewModel::class.java)
        employeeLocationListViewModel = ViewModelProvider(this).get(EmployeeLocationListViewModel::class.java)

        setRegViews()
        hideViews()
        mapView = findViewById(R.id.map_view);
        mapView!!.onCreate(savedInstanceState);

//        var location = Config.createLocation()
//        val jObject = JSONObject(location)
//        val jobjt = jObject.getJSONObject("LocationType")
//        locationList  = jobjt.getJSONArray("LocationDetails")
//        if (locationList.length()>0){
//            mapView!!.getMapAsync(this);
//
//            recyStaffList!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
//            val adapter = LocationMarkingAdapter(this@LocationMarkingNewActivity, locationList)
//            recyStaffList!!.adapter = adapter
//          //  adapter.setClickListener(this@LocationMarkingNewActivity)
//        }

        EmployeeLocation = 0
        getEmployeeLocationList()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        tv_tab_StaffList = findViewById<TextView>(R.id.tv_tab_StaffList)
        tv_tab_StaffDetails = findViewById<TextView>(R.id.tv_tab_StaffDetails)
        imgList = findViewById<ImageView>(R.id.imgList)
        imgMap = findViewById<ImageView>(R.id.imgMap)
        ll_details = findViewById<LinearLayout>(R.id.ll_details)
        ll_list = findViewById<LinearLayout>(R.id.ll_list)
        ll_select_list = findViewById<LinearLayout>(R.id.ll_select_list)
        ll_select_map = findViewById<LinearLayout>(R.id.ll_select_map)
        recyStaffList = findViewById<RecyclerView>(R.id.recyStaffList)
        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)

        tv_error = findViewById<TextView>(R.id.tv_error)
        ll_nodata = findViewById<LinearLayout>(R.id.ll_nodata)
        ll_data = findViewById<LinearLayout>(R.id.ll_data)

        imback!!.setOnClickListener(this)
        tv_tab_StaffDetails!!.setOnClickListener(this)
        tv_tab_StaffList!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)
        ll_select_list!!.setOnClickListener(this)
        ll_select_map!!.setOnClickListener(this)

        getCurrentdate("0")


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tv_tab_StaffList->{
                listMode = "1"
                detailMode="0"
                hideViews()
            }
            R.id.tv_tab_StaffDetails->{
                listMode = "0"
                detailMode="1"
                hideViews()
            }
            R.id.ll_select_list->{
                listMode = "1"
                detailMode="0"
                hideViews()
            }
            R.id.ll_select_map->{
                listMode = "0"
                detailMode="1"
                hideViews()
            }
            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomSheet()
            }

            R.id.tie_Branch->{
                Config.disableClick(v)
                branchCount = 0
                getBranch()
            }

            R.id.tie_Department->{
                Config.disableClick(v)
                department = 0
                getDepartment()
            }
            R.id.tie_Designation->{
                Config.disableClick(v)
                designation = 0
                getDesignation()
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
            R.id.tie_Date->{
                Config.disableClick(v)
                openBottomDate()
            }



        }
    }



    private fun hideViews() {
        ll_list!!.visibility = View.GONE
        ll_details!!.visibility = View.GONE

        val colorSelect = ContextCompat.getColor(this, R.color.colorPrimary)
        val colorUnSelect = ContextCompat.getColor(this, R.color.grey)

        if (listMode.equals("1")){
            ll_list!!.visibility = View.VISIBLE
        //    tv_tab_StaffList!!.setBackgroundResource(R.drawable.shape_rectangle_bg)
            tv_tab_StaffDetails!!.setBackgroundResource(R.drawable.shape_rectangle_trans)
//            tv_tab_StaffList!!.setTextColor(Color.parseColor("#FFFFFF"))
//            tv_tab_StaffDetails!!.setTextColor(Color.parseColor("#FFFFFF"))

            tv_tab_StaffList!!.setTextColor( getResources().getColor(R.color.colorPrimary))
            tv_tab_StaffDetails!!.setTextColor(getResources().getColor(R.color.grey))
            imgList!!.setColorFilter(colorSelect, PorterDuff.Mode.SRC_IN)
            imgMap!!.setColorFilter(colorUnSelect, PorterDuff.Mode.SRC_IN)


        }
        if (detailMode.equals("1")){
            ll_details!!.visibility = View.VISIBLE
            tv_tab_StaffList!!.setBackgroundResource(R.drawable.shape_rectangle_trans)
          //  tv_tab_StaffDetails!!.setBackgroundResource(R.drawable.shape_rectangle_bg)
//            tv_tab_StaffList!!.setTextColor(Color.parseColor("#FFFFFF"))
//            tv_tab_StaffDetails!!.setTextColor(Color.parseColor("#FFFFFF"))


            tv_tab_StaffList!!.setTextColor( getResources().getColor(R.color.grey))
            tv_tab_StaffDetails!!.setTextColor(getResources().getColor(R.color.colorPrimary))
            imgList!!.setColorFilter(colorUnSelect, PorterDuff.Mode.SRC_IN)
            imgMap!!.setColorFilter(colorSelect, PorterDuff.Mode.SRC_IN)

        }
    }

    private fun filterBottomSheet() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.filter_location_bottom_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)


            ll_StafAdmin = view.findViewById<LinearLayout>(R.id.ll_StafAdmin)
            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            til_Branch = view.findViewById<TextInputLayout>(R.id.til_Branch)
            til_Department = view.findViewById<TextInputLayout>(R.id.til_Department)
            til_Designation = view.findViewById<TextInputLayout>(R.id.til_Designation)
            til_Employee = view.findViewById<TextInputLayout>(R.id.til_Employee)
            til_Date = view.findViewById<TextInputLayout>(R.id.til_Date)

            tie_Branch = view.findViewById<TextInputEditText>(R.id.tie_Branch)
            tie_Department = view.findViewById<TextInputEditText>(R.id.tie_Department)
            tie_Designation = view.findViewById<TextInputEditText>(R.id.tie_Designation)
            tie_Employee = view.findViewById<TextInputEditText>(R.id.tie_Employee)
            tie_Date = view.findViewById<TextInputEditText>(R.id.tie_Date)

            tie_Branch!!.setOnClickListener(this)
            tie_Department!!.setOnClickListener(this)
            tie_Designation!!.setOnClickListener(this)
            tie_Employee!!.setOnClickListener(this)
            tie_Date!!.setOnClickListener(this)


            if (!temp_Branch.equals("")){
                tie_Branch!!.setText(temp_Branch)
            }

            if (!temp_Department.equals("")){
                tie_Department!!.setText(temp_Department)
            }
            if (!temp_Designation.equals("")){
                tie_Designation!!.setText(temp_Designation)
            }
            if (!temp_Employee.equals("")){
                tie_Employee!!.setText(temp_Employee)
            }
            if (!temp_Date.equals("")){
                tie_Date!!.setText(temp_Date)
            }



//            onTextChangedValues()

            val IsAdminSP = applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)
            if (isAdmin.equals("1")){
                ll_StafAdmin!!.visibility  =View.VISIBLE
              //  tie_Designation!!.isEnabled  = true
                tie_Employee!!.isEnabled  = true
            }else{
                ll_StafAdmin!!.visibility  =View.GONE
              //  tie_Employee!!.isEnabled  = false
                tie_Employee!!.isEnabled  = false
            }
            if (tie_Date!!.text.toString().equals("")){
                getCurrentdate("1")
            }


//            if (ID_Branch.equals("")){
//                loadLoginEmpDetails("1")
//            }
//            else{
//                ID_Branch = temp_ID_Branch
//                tie_Branch!!.setText(temp_Branch)
//                ID_Employee = temp_ID_Employee
//                tie_Employee!!.setText(temp_Employee)
//                tie_FromDate!!.setText(temp_FromDate)
//                tie_ToDate!!.setText(temp_ToDate)
//                FK_Area = temp_FK_Area
//                tie_Area!!.setText(temp_Area)
//                tie_Customer!!.setText(temp_Customer)
//                tie_Mobile!!.setText(temp_Mobile)
//                tie_TicketNumber!!.setText(temp_TicketNo)
//                tie_DueDays!!.setText(temp_DueDays)
//
//
//            }

            txtReset!!.setOnClickListener {
               // loadLoginEmpDetails("1")
                ID_Department = "0"
                temp_Department = ""
                temp_Branch = ""
                tie_Department!!.setText("")
                ID_Designation = "0"
                temp_Designation = ""
                tie_Designation!!.setText("")
                ID_Employee = "0"
                temp_Employee = ""
                tie_Employee!!.setText("")
                getCurrentdate("1")
                getCurrentBranch()

            }
            txtSearch!!.setOnClickListener {

              //  validateData(dialog1)
                dialog1.dismiss()
                EmployeeLocation = 0
                getEmployeeLocationList()
            }





//            dialog1!!.setCancelable(false)
            //   view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


            //      tabLayout = view.findViewById(R.id.tabLayout)



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    private fun getCurrentBranch() {
        val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null)
        tie_Branch !!.setText( BranchNameSP.getString("BranchName", null))
    }

    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

//        if (dateMode == 0){
//            date_Picker1.maxDate = System.currentTimeMillis()
//        }else if (dateMode == 1){
//
//            date_Picker1.minDate = System.currentTimeMillis()
//        }
//        else if (dateMode == 2){
//            date_Picker1.minDate = System.currentTimeMillis()
//        }

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

                tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                strDate = ""+strYear+"-"+strMonth+"-"+strDay
                temp_Date = ""+strDay+"-"+strMonth+"-"+strYear



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getCurrentdate(mode  :String) {

        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())
            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")


            val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
            val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
            if (mode.equals("1")){
                tie_Date!!.setText(""+sdfDate1.format(newDate))
                tie_Branch !!.setText( BranchNameSP.getString("BranchName", null))
            }
            strDate = sdfDate2.format(newDate)
            ID_Branch = FK_BranchSP.getString("FK_Branch", null)
            temp_Date = sdfDate1.format(newDate)









        }catch (e :Exception){

        }
    }

    private fun getDesignation() {
//        var department = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                designationViewModel.getDesignation(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (designation == 0){
                                    designation++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1142   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DesignationList")
                                        designationArrayList = jobjt.getJSONArray("DesignationDetails")
                                        if (designationArrayList.length()>0){

                                            designationPopup(designationArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@LocationMarkingNewActivity,
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

    private fun designationPopup(designationArrayList: JSONArray) {
        try {

            dialogDesignation = Dialog(this)
            dialogDesignation!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDesignation!! .setContentView(R.layout.designation_popup)
            dialogDesignation!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDesignation = dialogDesignation!! .findViewById(R.id.recyDesignation) as RecyclerView
            val etsearch = dialogDesignation!! .findViewById(R.id.etsearch) as EditText

            designationtSort = JSONArray()
            for (k in 0 until designationArrayList.length()) {
                val jsonObject = designationArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                designationtSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@LocationMarkingNewActivity, 1)
            recyDesignation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, designationArrayList)
            val adapter = DesignationAdapter(this@LocationMarkingNewActivity, designationtSort)
            recyDesignation!!.adapter = adapter
            adapter.setClickListener(this@LocationMarkingNewActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    designationtSort = JSONArray()

                    for (k in 0 until designationArrayList.length()) {
                        val jsonObject = designationArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DesignationName").length) {
                            if (jsonObject.getString("DesignationName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                designationtSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"designationtSort               7103    "+designationtSort)
                    val adapter = DesignationAdapter(this@LocationMarkingNewActivity, designationtSort)
                    recyDesignation!!.adapter = adapter
                    adapter.setClickListener(this@LocationMarkingNewActivity)
                }
            })

            dialogDesignation!!.show()
            dialogDesignation!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBranch() {
        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (branchCount == 0){
                                    branchCount ++
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
                                            this@LocationMarkingNewActivity,
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

    private fun branchPopup(branchArrayList: JSONArray) {

        try {

            dialogBranch = Dialog(this)
            dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranch!!.setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!!.findViewById(R.id.recyBranch) as RecyclerView
            val etsearch = dialogBranch!!.findViewById(R.id.etsearch) as EditText

            branchsort = JSONArray()
            for (k in 0 until branchArrayList.length()) {
                val jsonObject = branchArrayList.getJSONObject(k)
                branchsort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@LocationMarkingNewActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@TicketReportActivity, branchArrayList)
            val adapter = BranchAdapter(this@LocationMarkingNewActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@LocationMarkingNewActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    branchsort = JSONArray()

                    for (k in 0 until branchArrayList.length()) {
                        val jsonObject = branchArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BranchName").length) {
                            if (jsonObject.getString("BranchName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                branchsort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "branchsort               7103    " + branchsort)
                    val adapter = BranchAdapter(this@LocationMarkingNewActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@LocationMarkingNewActivity)
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
                                            this@LocationMarkingNewActivity,
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

            val lLayout = GridLayoutManager(this@LocationMarkingNewActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@LocationMarkingNewActivity, departmentSort)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@LocationMarkingNewActivity)

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
                    val adapter = DepartmentAdapter(this@LocationMarkingNewActivity, departmentSort)
                    recyDeaprtment!!.adapter = adapter
                    adapter.setClickListener(this@LocationMarkingNewActivity)
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
                employeeDetailsViewModel.getEmployee(this, ID_Department!!,ID_Designation!!)!!.observe(
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
                                            this@LocationMarkingNewActivity,
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


            val lLayout = GridLayoutManager(this@LocationMarkingNewActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@LocationMarkingNewActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@LocationMarkingNewActivity)

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
                    val adapter = EmployeeAdapter(this@LocationMarkingNewActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@LocationMarkingNewActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap!!.uiSettings.isCompassEnabled = false
        googleMap!!.uiSettings.isMapToolbarEnabled = false
        googleMap!!.setInfoWindowAdapter(null)

        Log.e(TAG,"jobjt  610   :  "+locationList)
        for (i in 0 until locationList.length()) {
            val json = locationList.getJSONObject(i)
            addMarkerWithIconAndTitle(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble()), json.getString("EmployeeName"), R.drawable.person_location,i)
            if (i==0){
                googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
                googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble())))
            }
        }

        googleMap!!.setOnMarkerClickListener(OnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            marker.hideInfoWindow()
            var pos = marker.snippet!!.toInt()
            Log.e(TAG,"902   "
            +"\n   "+marker.id
            +"\n   "+marker.snippet
            +"\n   "+marker.tag
            +"\n   "+marker.alpha)

            val jsonObject = locationList.getJSONObject(pos)
            Log.e(TAG,"1062   Location List")
            val i = Intent(this@LocationMarkingNewActivity, MapRootActivity::class.java)
            i.putExtra("FK_Employee",jsonObject.getString("FK_Employee"))
            i.putExtra("strDate",strDate)
            startActivity(i)

          //  Toast.makeText(this@LocationMarkingNewActivity, "Clicked location is $markerName", Toast.LENGTH_SHORT).show()
            true
        })


//        // Add markers with icons and titles
//        addMarkerWithIconAndTitle(LatLng(11.2590, 75.7863), "Calicut MOFUSIAL BUS STAND", R.drawable.person_location)
//        addMarkerWithIconAndTitle(LatLng(11.887509, 75.371029), "Kannur MOFUSIAL BUS STAND", R.drawable.person_location)
//        // Add more markers...
//
//        // Center the map on the first marker
//        googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(11.2590, 75.7863)))
    }

    private fun addMarkerWithIconAndTitle(position: LatLng, title: String, iconResId: Int,pos : Int) {
        val options = MarkerOptions()
            .position(position)
            .title(title)
            .snippet(""+pos)



        // Inflate the custom marker layout
        val markerView: View = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null)

        // Set the icon
        val markerIconImageView: ImageView = markerView.findViewById(R.id.marker_icon)
        markerIconImageView.setImageResource(iconResId)

        // Set the title
        val markerTitleTextView: TextView = markerView.findViewById(R.id.marker_title)
        markerTitleTextView.setText(title)

        // Convert the custom marker view to a bitmap
        val markerIcon = createBitmapDescriptorFromView(markerView)

        // Set the custom marker icon
        options.icon(markerIcon)

        // Add the marker to the map
        googleMap!!.addMarker(options)
    }

    private fun createBitmapDescriptorFromView(view: View): BitmapDescriptor {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width: Int = view.getMeasuredWidth()
        val height: Int = view.getMeasuredHeight()
        view.layout(0, 0, width, height)
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    private fun getEmployeeLocationList() {

        ll_data!!.visibility = View.GONE
        ll_nodata!!.visibility = View.GONE
        recyStaffList!!.adapter = null
        if (googleMap != null) {
            googleMap!!.clear();
        }
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeLocationListViewModel.getEmployeeLocationList(this,strDate!!,ID_Department!!,ID_Designation!!,ID_Employee!!,ID_Branch!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (EmployeeLocation == 0){
                                    EmployeeLocation++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   8620   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("EmployeeLocationList")
                                        locationList = jobjt.getJSONArray("EmployeeLocationListData")
                                        if (locationList.length()>0){

                                            ll_nodata!!.visibility = View.GONE
                                            ll_data!!.visibility = View.VISIBLE
                                                mapView!!.getMapAsync(this);

                                                recyStaffList!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                                val adapter = LocationMarkingAdapter(this@LocationMarkingNewActivity, locationList)
                                                recyStaffList!!.adapter = adapter
                                                adapter.setClickListener(this@LocationMarkingNewActivity)


                                        }
                                    } else {

                                        ll_nodata!!.visibility = View.VISIBLE
                                        ll_data!!.visibility = View.GONE
                                        tv_error!!.setText(jObject.getString("EXMessage"))

//                                        val builder = AlertDialog.Builder(
//                                            this@LocationMarkingNewActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
            //   val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchsort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))

            temp_Branch = jsonObject.getString("BranchName")
        }

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
//            val jsonObject = departmentArrayList.getJSONObject(position)
            val jsonObject = departmentSort.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))

            temp_Department = jsonObject.getString("DeptName")
            temp_Employee = ""
            ID_Employee = "0"
            tie_Employee!!.setText("")

        }

        if (data.equals("designation")){
            dialogDesignation!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = designationtSort.getJSONObject(position)
            Log.e(TAG,"ID_Designation   "+jsonObject.getString("ID_Designation"))
            ID_Designation = jsonObject.getString("ID_Designation")
            tie_Designation!!.setText(jsonObject.getString("DesignationName"))

            temp_Designation = jsonObject.getString("DesignationName")

            ID_Employee = "0"
            tie_Employee!!.setText("")

        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))

            temp_Employee = jsonObject.getString("EmpName")


        }
        if (data.equals("LocList")){
            val jsonObject = locationList.getJSONObject(position)
            Log.e(TAG,"1062   Location List")
            val i = Intent(this@LocationMarkingNewActivity, MapRootActivity::class.java)
            i.putExtra("FK_Employee",jsonObject.getString("FK_Employee"))
            i.putExtra("strDate",strDate)
            startActivity(i)
        }

        if (data.equals("LocDetails")){
            val jsonObject = locationList.getJSONObject(position)
            Log.e(TAG,"1062   Location List")
            val i = Intent(this@LocationMarkingNewActivity, MapRootDetailActivity::class.java)
            i.putExtra("FK_Employee",jsonObject.getString("FK_Employee"))
            i.putExtra("strDate",strDate)
            startActivity(i)
        }
    }
}