package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FollowUpActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {
    val TAG : String = "FollowUpActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context


    var til_Date: TextInputLayout? = null
    var til_NextFollowupDate: TextInputLayout? = null

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

    var btnReset: Button? = null
    var btnSubmit: Button? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_follow_up)
        context = this@FollowUpActivity

        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        employeeAllViewModel = ViewModelProvider(this).get(EmployeeAllViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)

        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        leadGenerateDefaultvalueViewModel = ViewModelProvider(this).get(LeadGenerationDefaultvalueViewModel::class.java)
        updateLeadManagementViewModel = ViewModelProvider(this).get(UpdateLeadManagementViewModel::class.java)

        ID_LeadGenerateProduct = intent.getStringExtra("ID_LeadGenerateProduct")
        ID_LeadGenerate = intent.getStringExtra("ID_LeadGenerate")

        setRegViews()

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_Date!!.setText(currentDate)
        tie_NextFollowupDate!!.setText(currentDate)
        setDefaultFollowupBy()
        getDefaultValueSettings()


    }

    private fun setDefaultFollowupBy() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        Log.e(TAG," UserName  143     "+UserNameSP.getString("UserName", null))
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null)
        tie_FollowupBy!!.setText(UserNameSP.getString("UserName", null))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        til_Date       = findViewById(R.id.til_Date) as TextInputLayout
        til_NextFollowupDate   = findViewById(R.id.til_NextFollowupDate) as TextInputLayout

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

        btnSubmit     = findViewById(R.id.btnSubmit) as Button
        btnReset     = findViewById(R.id.btnReset) as Button

        tie_ActionType!!.setOnClickListener(this)
        tie_FollowupBy!!.setOnClickListener(this)
        tie_Status!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)

        tie_NextAction!!.setOnClickListener(this)
        tie_NextActionType!!.setOnClickListener(this)
        tie_NextFollowupDate!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_NextEmployee!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        til_Date!!.hint = "Date"

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_ActionType->{
                ActiontypeFN = 0
                getFollowupType()
            }
            R.id.tie_FollowupBy->{
                getAllEmployee()
            }
            R.id.tie_Status->{
                getStatus()
            }
            R.id.tie_Date->{
                DateType = 0
                openBottomSheet()
            }
            R.id.tie_NextAction->{
                getFollowupAction()
            }
            R.id.tie_NextActionType->{
                ActiontypeFN = 1
                getFollowupType()
            }
            R.id.tie_NextFollowupDate->{
                DateType = 1
                openBottomSheet()
            }
            R.id.tie_Priority->{
                getProductPriority()
            }

            R.id.tie_Department->{
                getDepartment()
            }

            R.id.tie_NextEmployee->{

                if (ID_Department.equals("")){

                    Config.snackBars(context,v,"Select Department")

                }else{
                    getEmployee()
                }

            }

            R.id.btnReset->{
                clearData()
            }

            R.id.btnSubmit->{

                ValidateData(v)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getFollowupType() {
        var followUpType = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                if (followUpTypeArrayList.length()>0){
                                    if (followUpType == 0){
                                        followUpType++
                                        followupTypePopup(followUpTypeArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getAllEmployee() {

        var employee = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1224   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("EmployeeDetails")
                                employeeAllArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                if (employeeAllArrayList.length()>0){
                                    if (employee == 0){
                                        employee++
                                        employeeAllPopup(employeeAllArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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
        var employee = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1224   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("EmployeeDetails")
                                employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                if (employeeArrayList.length()>0){
                                    if (employee == 0){
                                        employee++
                                        employeePopup(employeeArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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
        var prodstatus = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   333   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("StatusDetailsList")
                                prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                if (prodStatusArrayList.length()>0){
                                    if (prodstatus == 0){
                                        prodstatus++
                                        productStatusPopup(prodStatusArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

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
        var followUpAction = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                if (followUpActionArrayList.length()>0){
                                    if (followUpAction == 0){
                                        followUpAction++
                                        followUpActionPopup(followUpActionArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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
        var prodpriority = 0
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
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   353   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                if (prodPriorityArrayList.length()>0){
                                    if (prodpriority == 0){
                                        prodpriority++
                                        productPriorityPopup(prodPriorityArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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
        var department = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1142   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("DepartmentDetails")
                                departmentArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                if (departmentArrayList.length()>0){
                                    if (department == 0){
                                        department++
                                        departmentPopup(departmentArrayList)
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

            if (ActiontypeFN == 0 ){
                ID_ActionType = jsonObject.getString("ID_ActionType")
                tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))
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


        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
//            val jsonObject = prodStatusArrayList.getJSONObject(position)
            val jsonObject = prodStatusSort.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            tie_Status!!.setText(jsonObject.getString("StatusName"))
            til_Date!!.hint = (jsonObject.getString("StatusName")+" Date")
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

    private fun clearData() {
        tie_ActionType!!.setText("")
        tie_FollowupBy!!.setText("")
        tie_Status!!.setText("")
        tie_CustomerRemark!!.setText("")
        tie_EmployeeRemarks!!.setText("")

        ID_ActionType = ""
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

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_Date!!.setText(currentDate)
        tie_NextFollowupDate!!.setText(currentDate)

        til_Date!!.hint = "Date"
        setDefaultFollowupBy()
        getDefaultValueSettings()
    }

    private fun ValidateData(v: View) {

        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dateFollowUp = inputFormat.parse(tie_Date!!.text.toString())
        val strFollowUpDate = outputFormat.format(dateFollowUp)
        val dateNextFollowUp = inputFormat.parse(tie_NextFollowupDate!!.text.toString())
        val strNextFollowUpDate = outputFormat.format(dateNextFollowUp)


        val strCustomerRemark = tie_CustomerRemark!!.text.toString()
        val strEmployeeRemark = tie_EmployeeRemarks!!.text.toString()


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

            Log.e(TAG,"FOLLOWUP  9941 "
                    +"\n ID_LeadGenerateProduct :  "+ID_LeadGenerateProduct
                    +"\n ID_LeadGenerate        :  "+ID_LeadGenerate
                    +"\n ID_ActionType          :  "+ID_ActionType
                    +"\n ID_Employee            :  "+ID_Employee
                    +"\n ID_Status              :  "+ID_Status
                    +"\n strFollowUpDate        :  "+strFollowUpDate
                    +"\n strCustomerRemark      :  "+strCustomerRemark
                    +"\n strEmployeeRemark      :  "+strEmployeeRemark)


            Log.e(TAG,"NEXTACTION  9942 "
                    +"\n ID_NextAction         :  "+ID_NextAction
                    +"\n ID_NextActionType     :  "+ID_NextActionType
                    +"\n strNextFollowUpDate   :  "+strNextFollowUpDate
                    +"\n ID_Priority           :  "+ID_Priority
                    +"\n ID_Department         :  "+ID_Department
                    +"\n ID_NextEmployee       :  "+ID_NextEmployee)

            saveUpdateLeadManagement(ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,
                strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee)
        }
    }

    private fun saveUpdateLeadManagement(ID_LeadGenerateProduct: String?, ID_LeadGenerate: String?, ID_ActionType: String?, ID_Employee: String?,
        ID_Status: String?, strFollowUpDate: String, strCustomerRemark: String, strEmployeeRemark: String, ID_NextAction: String?, ID_NextActionType: String?,
        strNextFollowUpDate: String, ID_Priority: String?, ID_Department: String?, ID_NextEmployee: String?) {


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                updateLeadManagementViewModel.getUpdateLeadManagement(this,ID_LeadGenerateProduct!!,ID_LeadGenerate!!,ID_ActionType!!,ID_Employee!!,ID_Status!!,strFollowUpDate,
                    strCustomerRemark,strEmployeeRemark,ID_NextAction!!,ID_NextActionType!!,strNextFollowUpDate,ID_Priority!!,ID_Department!!,ID_NextEmployee!!)!!.observe(
                    this,
                    Observer { deleteleadSetterGetter ->
                        val msg = deleteleadSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                //  val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                                Log.e(TAG,"msg  1126     "+msg)
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
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

}