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
import android.view.Gravity
import androidx.lifecycle.Observer
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProjectAdapter
import com.perfect.prodsuit.Viewmodel.MaterialUsageProjectViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MaterialRequestActivity : AppCompatActivity() ,  View.OnClickListener , ItemClickListener {

    val TAG: String = "MaterialUsageActivity"
    private var tie_Project       : TextInputEditText?    = null
    private var tie_Followupdate  : TextInputEditText?    = null
    private var tie_Stage         : TextInputEditText?    = null
    private var tie_CurrentStatus : TextInputEditText?    = null
    private var tie_StatusDate    : TextInputEditText?    = null
    private var tie_Remarks       : TextInputEditText?    = null
    private var tie_Employee      : TextInputEditText?    = null
    private var tie_Team          : TextInputEditText?    = null
    private var tie_Product       : TextInputEditText?    = null
    private var tie_Quantity      : TextInputEditText?    = null
    private var imback            : ImageView?            = null
    private var til_Project       : TextInputLayout?      = null
    private var til_Followupdate  : TextInputLayout?      = null
    private var til_CurrentStatus : TextInputLayout?      = null
    private var til_StatusDate    : TextInputLayout?      = null
    private var til_Stage         : TextInputLayout?      = null
    private var til_Product       : TextInputLayout?      = null
    private var til_Quantity      : TextInputLayout?      = null
    private var til_Team          : TextInputLayout?      = null
    private var til_UsageDate     : TextInputLayout?      = null
    private var tie_UsageDate     : TextInputEditText?    = null
    private var tv_MaterialDetails: TextView?             = null
    private var tv_MaterialReqst  : TextView?             = null
    private var tvv_list_name     : TextView?             = null
    private var btnSubmit         : Button?               = null
    private var btnReset          : Button?               = null
    private var ll_MaterialDetails: LinearLayout?         = null
    private var ll_MaterialRequest: LinearLayout?         = null
    private var progressDialog    : ProgressDialog?       = null
    private var dialogProject     : Dialog?               = null
    private var dialogStage       : Dialog?               = null
    private var dialogTeam        : Dialog?               = null
    private var dialogProduct     : Dialog?               = null
    private var recylist          : RecyclerView?         = null
    private var recyEmpDetails    : RecyclerView?         = null
    lateinit var context          : Context
    lateinit var projectArrayList : JSONArray
    lateinit var stageArrayList   : JSONArray
    lateinit var projectSort      : JSONArray
    lateinit var stageSort        : JSONArray
    lateinit var teamSort         : JSONArray
    lateinit var productSort      : JSONArray
    lateinit var teamArrayList    : JSONArray
    lateinit var productArrayList : JSONArray
    lateinit var materialusageProjectViewModel: MaterialUsageProjectViewModel

    var projectcount = 0
    var stagecount   = 0
    var teamcount    = 0
    var productcount = 0
    var visibility   = "1"
    var strUsagedate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_request)


        context = this@MaterialRequestActivity
        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)
        setRegViews()
    }

    private fun setRegViews() {

        tie_Project        = findViewById(R.id.tie_Project)
        tie_Followupdate   = findViewById(R.id.tie_Followupdate)
        tie_Stage          = findViewById(R.id.tie_Stage)
        tie_CurrentStatus  = findViewById(R.id.tie_CurrentStatus)
        tie_StatusDate     = findViewById(R.id.tie_StatusDate)
        tie_Remarks        = findViewById(R.id.tie_Remarks)
        imback             = findViewById(R.id.imback)
        til_Project        = findViewById(R.id.til_Project)
        til_Followupdate   = findViewById(R.id.til_Followupdate)
        til_CurrentStatus  = findViewById(R.id.til_CurrentStatus)
        til_StatusDate     = findViewById(R.id.til_StatusDate)
        btnSubmit          = findViewById(R.id.btnSubmit)
        btnReset           = findViewById(R.id.btnReset)
        tv_MaterialDetails = findViewById(R.id.tv_MaterialDetails)
        ll_MaterialRequest = findViewById(R.id.ll_MaterialRequest)
        ll_MaterialDetails = findViewById(R.id.ll_MaterialDetails)
        tv_MaterialReqst   = findViewById(R.id.tv_MaterialReqst)
        tie_Employee       = findViewById(R.id.tie_Employee)
        til_Stage          = findViewById(R.id.til_Stage)
        til_Team           = findViewById(R.id.til_Team)
        til_UsageDate      = findViewById(R.id.til_UsageDate)
        til_Product        = findViewById(R.id.til_Product)
        til_Quantity       = findViewById(R.id.til_Quantity)
        tvv_list_name      = findViewById(R.id.tvv_list_name)
        tie_UsageDate      = findViewById(R.id.tie_UsageDate)
        tie_Team           = findViewById(R.id.tie_Team)
        tie_Product        = findViewById(R.id.tie_Product)
        recyEmpDetails     = findViewById(R.id.recyEmpDetails)
        tie_Quantity       = findViewById(R.id.tie_Quantity)

        tie_Project!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        tv_MaterialDetails!!.setOnClickListener(this)
        tv_MaterialReqst!!.setOnClickListener(this)
        ll_MaterialDetails!!.setOnClickListener(this)
        ll_MaterialRequest!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        til_Stage!!.setOnClickListener(this)
        til_Team!!.setOnClickListener(this)
        til_UsageDate!!.setOnClickListener(this)
        til_Product!!.setOnClickListener(this)
        til_Quantity!!.setOnClickListener(this)
        tie_UsageDate!!.setOnClickListener(this)
        tie_Team!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        tie_Quantity!!.setOnClickListener(this)


        til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_UsageDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Stage!!.defaultHintTextColor     = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Team!!.defaultHintTextColor      = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Product!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Quantity!!.defaultHintTextColor  = ContextCompat.getColorStateList(this,R.color.color_mandatory)


        getCurrentDate()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imback -> {
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_right)
            }
            R.id.tv_MaterialReqst -> {

                Log.e(TAG, "eeerrr 1 " + visibility)
                if (visibility.equals("0")) {
                    ll_MaterialRequest!!.visibility = View.VISIBLE
                    ll_MaterialDetails!!.visibility = View.GONE
                    visibility = "1"
                }
            }
            R.id.tv_MaterialDetails -> {

                Log.e(TAG, "eeerrr 2 " + visibility)
                if (visibility.equals("1")) {
                    ll_MaterialDetails!!.visibility = View.VISIBLE
                    ll_MaterialRequest!!.visibility = View.GONE
                    visibility = "0"
                }
            }
            R.id.tie_Project -> {
                projectcount = 0
                getProject()
            }
            R.id.tie_Stage -> {
                stagecount = 0
                getStage()
            }
//            R.id.tie_UsageDate -> {
////                openBottomSheet()
//            }
            R.id.tie_Team -> {
                teamcount = 0
                getTeam()
            }
            R.id.tie_Employee -> {
//                productcount = 0
//                getProduct()
            }
            R.id.tie_UsageDate -> {
                openBottomSheet()
            }
            R.id.tie_Product -> {
                productcount = 0
                getProduct()
//                getMode()
            }
            R.id.btnReset -> {
               tie_Project!!.setText("")
               tie_Stage!!.setText("")
               tie_Team!!.setText("")
               tie_Employee!!.setText("")
               tie_UsageDate!!.setText("")
               tie_Product!!.setText("")
               tie_Quantity!!.setText("")
            }
            R.id.btnSubmit -> {

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


            tie_UsageDate!!.setText("" + sdfDate1.format(newDate))
            strUsagedate = "" + sdfDate1.format(newDate)

            Log.e(TAG, "strUsagedate 4582  " + strUsagedate)

        } catch (e: Exception) {
            Log.e(TAG, "strUsagedate 4582   " + e.toString())
        }
    }

    private fun openBottomSheet() {


        val dialog = BottomSheetDialog(this)
        val view   = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel    = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit    = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        date_Picker1.setMinDate(System.currentTimeMillis())
        date_Picker1.minDate = System.currentTimeMillis()


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                val day: Int   = date_Picker1!!.getDayOfMonth()
                val mon: Int   = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int  = date_Picker1!!.getYear()
                var strDay     = day.toString()
                var strMonth   = month.toString()
                var strYear    = year.toString()
                if (strDay.length == 1) {
                    strDay     = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }

                tie_UsageDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                strUsagedate = strYear + "-" + strMonth + "-" + strDay

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getProject() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (projectcount == 0){
                                    projectcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        projectArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (projectArrayList.length()>0){

                                            projectPopup(projectArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialRequestActivity,
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

    private fun projectPopup(projectArraylist: JSONArray) {
        try {

            dialogProject = Dialog(this)
            dialogProject!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProject!! .setContentView(R.layout.list_popup)
            dialogProject!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProject!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProject!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogProject!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("PROJECT LIST")

            projectSort = JSONArray()
            for (k in 0 until projectArraylist.length()) {
                val jsonObject = projectArraylist.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                projectSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialRequestActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@MaterialRequestActivity, projectSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialRequestActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    projectSort = JSONArray()

                    for (k in 0 until projectArraylist.length()) {
                        val jsonObject = projectArraylist.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                projectSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"projectSort               7103    "+projectSort)
                    val adapter = ProjectAdapter(this@MaterialRequestActivity, projectSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialRequestActivity)
                }
            })

            dialogProject!!.show()
            dialogProject!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStage() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (stagecount == 0){
                                    stagecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        stageArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (stageArrayList.length()>0){

                                            stagePopup(stageArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialRequestActivity,
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

    private fun stagePopup(stageArrayList: JSONArray) {
        try {

            dialogStage = Dialog(this)
            dialogStage!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogStage!! .setContentView(R.layout.list_popup)
            dialogStage!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogStage!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogStage!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogStage!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("STAGE LIST")

            stageSort = JSONArray()
            for (k in 0 until stageArrayList.length()) {
                val jsonObject = stageArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                stageSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialRequestActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@MaterialRequestActivity, stageSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialRequestActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stageSort = JSONArray()

                    for (k in 0 until stageArrayList.length()) {
                        val jsonObject = stageArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                stageSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"stageSort               7103    "+stageSort)
                    val adapter = ProjectAdapter(this@MaterialRequestActivity, stageSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialRequestActivity)
                }
            })

            dialogStage!!.show()
            dialogStage!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTeam() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (teamcount == 0){
                                    teamcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        teamArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (teamArrayList.length()>0){

                                            teamPopup(teamArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialRequestActivity,
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

    private fun teamPopup(teamArrayList: JSONArray) {
        try {

            dialogTeam = Dialog(this)
            dialogTeam!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTeam!! .setContentView(R.layout.list_popup)
            dialogTeam!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogTeam!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogTeam!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogTeam!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("TEAM LIST")

            teamSort = JSONArray()
            for (k in 0 until teamArrayList.length()) {
                val jsonObject = teamArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                teamSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialRequestActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@MaterialRequestActivity, teamSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialRequestActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    teamSort = JSONArray()

                    for (k in 0 until teamArrayList.length()) {
                        val jsonObject = teamArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                teamSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"teamSort               7103    "+teamSort)
                    val adapter = ProjectAdapter(this@MaterialRequestActivity, teamSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialRequestActivity)
                }
            })

            dialogTeam!!.show()
            dialogTeam!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProduct() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (productcount == 0){
                                    productcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")
                                        productArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                        if (productArrayList.length()>0){

                                            productPopup(productArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialRequestActivity,
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

    private fun productPopup(productArrayList: JSONArray) {
        try {

            dialogProduct = Dialog(this)
            dialogProduct!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProduct!! .setContentView(R.layout.list_popup)
            dialogProduct!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProduct!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProduct!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogProduct!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("PRODUCT LIST")

            productSort = JSONArray()
            for (k in 0 until productArrayList.length()) {
                val jsonObject = productArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                productSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialRequestActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@MaterialRequestActivity, productSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialRequestActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    productSort = JSONArray()

                    for (k in 0 until productArrayList.length()) {
                        val jsonObject = productArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                productSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"productSort               7103    "+productSort)
                    val adapter = ProjectAdapter(this@MaterialRequestActivity, productSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialRequestActivity)
                }
            })

            dialogProduct!!.show()
            dialogProduct!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(position: Int, data: String) {
        TODO("Not yet implemented")
    }
}