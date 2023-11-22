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
import android.view.View
import androidx.lifecycle.Observer
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
import com.perfect.prodsuit.View.Adapter.ProjectStatusAdapter
import com.perfect.prodsuit.View.Adapter.StageAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProjectFollowUpActivity : AppCompatActivity() ,  View.OnClickListener , ItemClickListener {

    val TAG                       : String                = "ProjectFollowUpActivity"
    lateinit var context          : Context
    lateinit var projectSort      : JSONArray
    lateinit var stagetSort       : JSONArray
    lateinit var currentSort      : JSONArray
    lateinit var projectArraylist : JSONArray
    lateinit var stageArraylist   : JSONArray
    lateinit var currentArraylist : JSONArray
    lateinit var projectArrayList  : JSONArray
    lateinit var stageArrayList    : JSONArray
    lateinit var stageSort         : JSONArray
    lateinit var projectViewModel : ProjectViewModel
    lateinit var currentstatusViewModel      : CurrentStatusViewModel
    lateinit var materialusageStageViewModel : MaterialUsageStageViewModel
    lateinit var materialusageProjectViewModel: MaterialUsageProjectViewModel
    lateinit var projectFollowupSaveViewModel: ProjectFollowupSaveViewModel
    private var progressDialog    : ProgressDialog?       = null
    private var tie_Project       : TextInputEditText?    = null
    private var tie_Followupdate  : TextInputEditText?    = null
    private var tie_Stage         : TextInputEditText?    = null
    private var tie_CurrentStatus : TextInputEditText?    = null
    private var tie_DueDate       : TextInputEditText?    = null
    private var tie_StatusDate    : TextInputEditText?    = null
    private var tie_Reason        : TextInputEditText?    = null
    private var tie_Remarks       : TextInputEditText?    = null
    private var imback            : ImageView?            = null
    private var til_Project       : TextInputLayout?      = null
    private var til_Followupdate  : TextInputLayout?      = null
    private var til_CurrentStatus : TextInputLayout?      = null
    private var til_StatusDate    : TextInputLayout?      = null
    private var til_Reason        : TextInputLayout?      = null
    private var til_Stage         : TextInputLayout?      = null
    private var til_Remarks       : TextInputLayout?      = null
    private var dialogProject     : Dialog?               = null
    private var dialogStage       : Dialog?               = null
    private var dialogCurrent     : Dialog?               = null
    private var recylist          : RecyclerView?         = null
    private var btnSubmit         : Button?               = null
    private var btnReset          : Button?               = null
    private var tvv_list_name     : TextView?             = null
    var strFollowupdate           : String?               = ""
    var strStatudate              : String?               = ""
    var datecheck                                         = ""
    var strProject                                        = ""
    var strCurrentStatus                                  = ""
    var strStage                                          = ""
    var strRemarks                                        = ""
    var strReason                                         = ""
    var ID_Project                                        = ""
    var ID_CurrentStatus                                  = ""
    var ID_Stage                                          = ""
    var ID_Team                                           = ""
    var ID_Employee                                       = "0"
    var projectcount                                      = 0
    var stagecount                                        = 0
    var currentcount                                      = 0
    var savecount                                         = 0

    var Critrea1                                          = "0"
    var SubMode                                           = ""

    var CreatedDate                                       = ""
    var DueDate                                       = ""
    var jsonObj: JSONObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_follow_up)

        context          = this@ProjectFollowUpActivity
        projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        materialusageStageViewModel   = ViewModelProvider(this).get(MaterialUsageStageViewModel::class.java)
        currentstatusViewModel   = ViewModelProvider(this).get(CurrentStatusViewModel::class.java)
        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)
        projectFollowupSaveViewModel   = ViewModelProvider(this).get(ProjectFollowupSaveViewModel::class.java)


        setRegViews()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)


        onTextChangedValues()
        ID_Project = jsonObj!!.getString("ID_Project")
        tie_Project!!.setText(jsonObj!!.getString("ProjName"))
        CreatedDate = jsonObj!!.getString("CreateDate")
        DueDate = jsonObj!!.getString("DueDate")
        tie_DueDate!!.setText(jsonObj!!.getString("DueDate"))

        getCurrentDate()




        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//        val date1 = dateFormat.parse("2023-01-02")
//        val date2 = dateFormat.parse("2023-01-03")
        val date1 = Config.convertDate(CreatedDate)
        val date2 = Config.convertDate(jsonObj!!.getString("DueDate"))
        if (date1 >= date2) {
            Log.e(TAG,"124441    "+"date1 is greater than or equal to date2")
            println("date1 is greater than or equal to date2")
        } else {
            Log.e(TAG,"124442    "+"date1 is less than date2")
            println("date1 is less than date2")
        }

    }

    private fun setRegViews() {

        tie_Project        = findViewById(R.id.tie_Project)
        tie_Followupdate   = findViewById(R.id.tie_Followupdate)
        tie_Stage          = findViewById(R.id.tie_Stage)
        tie_CurrentStatus  = findViewById(R.id.tie_CurrentStatus)
        tie_DueDate        = findViewById(R.id.tie_DueDate)
        tie_StatusDate     = findViewById(R.id.tie_StatusDate)
        tie_Reason         = findViewById(R.id.tie_Reason)
        tie_Remarks        = findViewById(R.id.tie_Remarks)
        imback             = findViewById(R.id.imback)
        til_Project        = findViewById(R.id.til_Project)
        til_Followupdate   = findViewById(R.id.til_Followupdate)
        til_CurrentStatus  = findViewById(R.id.til_CurrentStatus)
        til_StatusDate     = findViewById(R.id.til_StatusDate)
        til_Reason         = findViewById(R.id.til_Reason)
        btnSubmit          = findViewById(R.id.btnSubmit)
        btnReset           = findViewById(R.id.btnReset)
//        tvv_list_name      = findViewById(R.id.tvv_list_name)


        tie_Project!!.setOnClickListener(this)
        tie_Followupdate!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        tie_CurrentStatus!!.setOnClickListener(this)
        tie_DueDate!!.setOnClickListener(this)
        tie_StatusDate!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)


        til_Project!!.defaultHintTextColor       = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Followupdate!!.defaultHintTextColor  = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_CurrentStatus!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_StatusDate!!.defaultHintTextColor    = ContextCompat.getColorStateList(this,R.color.color_mandatory)
      //  til_Reason!!.defaultHintTextColor    = ContextCompat.getColorStateList(this,R.color.color_mandatory)




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


            tie_Followupdate!!.setText("" + sdfDate1.format(newDate))
            tie_StatusDate!!.setText("" + sdfDate1.format(newDate))
//            tie_VisitTime!!.setText("" + sdfTime1.format(newDate))

//            strFollowupdate = "" + sdfTime1.format(newDate)
            strFollowupdate = "" + sdfDate1.format(newDate)
            strStatudate    = "" + sdfDate1.format(newDate)

            Log.e(TAG, "strFollowupdate 4582  " + strFollowupdate)

        } catch (e: Exception) {

            Log.e(TAG, "strFollowupdate 4582   " + e.toString())
        }
    }

    private fun onTextChangedValues() {
        tie_Project!!.addTextChangedListener(watcher)
        tie_Followupdate!!.addTextChangedListener(watcher)
        tie_CurrentStatus!!.addTextChangedListener(watcher)
        tie_StatusDate!!.addTextChangedListener(watcher)
        tie_Reason!!.addTextChangedListener(watcher)

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
                editable === tie_Project!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Project!!.text.toString().equals("")){
                        til_Project!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Project!!.isErrorEnabled = false
                        til_Project!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_Followupdate!!.editableText -> {
                    Log.e(TAG,"283021    ")

                    val date1 = Config.convertDate(CreatedDate)
                    val date2 = Config.convertDate(tie_Followupdate!!.text.toString())
                    Log.e(TAG,"DATE  25441   "+date2 +"  <=  "+ date1)
                    var isValid = Config.convertTimemills(date1,date2)

                  //  Log.e(TAG,"DATE  254412   "+date1 +"  <=  "+ Config.convertTimemills(date1,date2))
//                    Log.e(TAG,"DATE  254413   "+date2 +"  <=  "+  Config.convertTimemills(date2))
                    if (tie_Followupdate!!.text.toString().equals("") || !isValid){
                        til_Followupdate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Followupdate!!.isErrorEnabled = false
                        til_Followupdate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_CurrentStatus!!.editableText -> {
                    if (tie_CurrentStatus!!.text.toString().equals("")){
                        til_CurrentStatus!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CurrentStatus!!.isErrorEnabled = false
                        til_CurrentStatus!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_StatusDate!!.editableText -> {
                    val date1 = Config.convertDate(tie_Followupdate!!.text.toString())
                    val date2 = Config.convertDate(tie_StatusDate!!.text.toString())
                    var isValid = Config.convertTimemills(date1,date2)
                    Log.e(TAG,"DATE  25442   "+date2 +"  <=  "+ date1)
                    if (tie_StatusDate!!.text.toString().equals("") || !isValid){
                        til_StatusDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_StatusDate!!.isErrorEnabled = false
                        til_StatusDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }


                }

                editable === tie_Reason!!.editableText -> {

                    strReason       = tie_Reason!!.text.toString()
                    val date3 = Config.convertDate(tie_StatusDate!!.text.toString())
                    val date4 = Config.convertDate(tie_DueDate!!.text.toString())
                    var isValid3 = Config.convertTimemills(date3,date4)
                    if (!isValid3 && strReason.equals("")){
                        til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Reason!!.isErrorEnabled = false
                        til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }



            }

        }
    }

    private fun openBottomSheet() {


        val dialog = BottomSheetDialog(this)
        val view   = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel    = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit    = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        if (datecheck!!.equals("0")){
            val date1 = Config.convertDate(CreatedDate)
            val mDate1 = sdf.parse(date1)
            val timeInMilliseconds1 = mDate1.time
            date_Picker1.minDate = timeInMilliseconds1
         //   date_Picker1.maxDate = System.currentTimeMillis()
        }else if (datecheck!!.equals("1")){
            val date1 = Config.convertDate(tie_Followupdate!!.text.toString())
            val mDate1 = sdf.parse(date1)
            val timeInMilliseconds1 = mDate1.time
            date_Picker1.minDate = timeInMilliseconds1
        }

//        date_Picker1.setMinDate(System.currentTimeMillis())
//        date_Picker1.minDate = System.currentTimeMillis()


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

                if (datecheck!!.equals("0")){
                    tie_Followupdate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strFollowupdate = strYear + "-" + strMonth + "-" + strDay
                }else if (datecheck!!.equals("1")){
                    tie_StatusDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strStatudate = strYear + "-" + strMonth + "-" + strDay
                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imback->{
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.tie_Project -> {
//                Toast.makeText(applicationContext, "please 0", Toast.LENGTH_SHORT).show()
                projectcount = 0
                getProject()
            }
            R.id.tie_Followupdate -> {
                datecheck = "0"
                openBottomSheet()
//                Toast.makeText(applicationContext, "please 1", Toast.LENGTH_SHORT).show()
            }
            R.id.tie_Stage -> {
                stagecount = 0
                getStage()
            }
            R.id.tie_StatusDate -> {
                datecheck = "1"
                openBottomSheet()

            }
            R.id.tie_Remarks -> {

            }
            R.id.tie_CurrentStatus -> {

                if (!ID_Stage.equals("")){
                    Critrea1 = ID_Stage
                    SubMode = "0"
                }
                else if (!ID_Project.equals("")){
                    // Critrea1 = ID_Project
//                    SubMode = "1"
                    Critrea1 = "0"
                    SubMode = "0"
                }

//                //OLD

//                if (!ID_Stage.equals("")){
//                    Critrea1 = ID_Stage
//                    SubMode = "2"
//                }
//                else if (!ID_Project.equals("")){
//                    Critrea1 = ID_Project
//                    SubMode = "1"
//                }
                currentcount = 0
                getCurrentStatus()
            }
            R.id.btnSubmit -> {

                projectFollowupValidation(v)
            }
            R.id.btnReset -> {

                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = sdf.format(Date())

               // tie_Project!!.setText("")
                tie_Followupdate!!.setText(currentDate)
                tie_Stage!!.setText("")
                tie_Reason!!.setText("")
                tie_StatusDate!!.setText(currentDate)
                tie_Remarks!!.setText("")
                tie_CurrentStatus!!.setText("")

               // ID_Project = ""
                ID_Stage   = ""
                ID_CurrentStatus = ""
                tie_DueDate!!.setText(DueDate)
            }
        }
    }

    private fun projectFollowupValidation(v: View){

        strProject      = tie_Project!!.text.toString()
        strFollowupdate = tie_Followupdate!!.text.toString()
        strStage        = tie_CurrentStatus!!.text.toString()
        strCurrentStatus= tie_CurrentStatus!!.text.toString()
        strCurrentStatus= "0"
        strStatudate    = tie_StatusDate!!.text.toString()
        strRemarks      = tie_Remarks!!.text.toString()
        strReason       = tie_Reason!!.text.toString()


        val date1 = Config.convertDate(CreatedDate)
        val date2 = Config.convertDate(tie_Followupdate!!.text.toString())
        val date3 = Config.convertDate(tie_StatusDate!!.text.toString())
        val date4 = Config.convertDate(tie_DueDate!!.text.toString())
        Log.e(TAG,"DATE  25441   "+date2 +"  <=  "+ date1)
        var isValid1 = Config.convertTimemills(date1,date2)
        var isValid2 = Config.convertTimemills(date2,date3)
        var isValid3 = Config.convertTimemills(date3,date4)

        if (strProject!!.equals("")){
           // Config.snackBars(context, v, "Select Project")
            til_Project!!.setError("Select Project")
            til_Project!!.setErrorIconDrawable(null)

        }else if (strFollowupdate!!.equals("") || !isValid1){
           // Config.snackBars(context, v, "Select FollowUp Date")
            til_Followupdate!!.setError("Follow Up date should be less than or equals to created date")
            til_Followupdate!!.setErrorIconDrawable(null)

        }else if (ID_CurrentStatus!!.equals("")){
//            Config.snackBars(context, v, "Select CurrentStatus")
            til_CurrentStatus!!.setError("Select Current Status")
            til_CurrentStatus!!.setErrorIconDrawable(null)

        }else if (strStatudate!!.equals("") || !isValid2){
           // Config.snackBars(context, v, "Select Statudate")
            til_StatusDate!!.setError("Status date should be less than or equals to Followu up date")
            til_StatusDate!!.setErrorIconDrawable(null)

        }
        else if (!isValid3 && strReason.equals("")){
            // Config.snackBars(context, v, "Select Statudate")
                Log.e(TAG,"484441    ")
            til_Reason!!.setError("Enter Reason")
            til_Reason!!.setErrorIconDrawable(null)
        }
        else{
            Log.e(TAG,"484442    ")
            //Toast.makeText(applicationContext,"hloo",Toast.LENGTH_SHORT).show()
            til_Reason!!.isErrorEnabled = false
            til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
            savecount = 0
            saveProjectFollowUP()

        }

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
                                        val jobjt = jObject.getJSONObject("ProjectList")
                                        projectArrayList = jobjt.getJSONArray("ProjectListDetails")
                                        if (projectArrayList.length()>0){

                                            projectPopup(projectArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectFollowUpActivity,
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
//            modeTest = 1
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

            val lLayout = GridLayoutManager(this@ProjectFollowUpActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@ProjectFollowUpActivity, projectSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectFollowUpActivity)

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
                        if (textlength <= jsonObject.getString("ProjName").length) {
                            if (jsonObject.getString("ProjName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                projectSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"projectSort               7103    "+projectSort)
                    val adapter = ProjectAdapter(this@ProjectFollowUpActivity, projectSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@ProjectFollowUpActivity)
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
        var ReqMode = "3"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageStageViewModel.getMaterialUsageStageModel(this,ID_Project!!,ReqMode)!!.observe(
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
                                        val jobjt = jObject.getJSONObject("ProjectStagesList")
                                        stageArrayList = jobjt.getJSONArray("ProjectStagesListDetails")
                                        if (stageArrayList.length()>0){

                                            stagePopup(stageArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectFollowUpActivity,
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
//            modeTest = 2
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

            val lLayout = GridLayoutManager(this@ProjectFollowUpActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = StageAdapter(this@ProjectFollowUpActivity, stageSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectFollowUpActivity)

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
                        if (textlength <= jsonObject.getString("StageName").length) {
                            if (jsonObject.getString("StageName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                stageSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"stageSort               7103    "+stageSort)
                    val adapter = StageAdapter(this@ProjectFollowUpActivity, stageSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@ProjectFollowUpActivity)
                }
            })

            dialogStage!!.show()
            dialogStage!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCurrentStatus() {
         var ReqMode = "19"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                currentstatusViewModel.getCurrentStatus(this,ReqMode,Critrea1!!,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (currentcount == 0){
                                    currentcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectStatus")
                                        currentArraylist = jobjt.getJSONArray("ProjectStatusList")
                                        if (currentArraylist.length()>0){

                                            currentStatusPopup(currentArraylist)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectFollowUpActivity,
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


    private fun saveProjectFollowUP() {

        var UserAction = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                projectFollowupSaveViewModel.saveProjectFollowup(this,UserAction,ID_Project,ID_Stage,strFollowupdate!!,ID_CurrentStatus,strStatudate!!,strReason,strRemarks)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (projectcount == 0){
                                    projectcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   77666   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UpdateProjectFollowUp")
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
                                            val tv_succesok = suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                            val ll_leadnumber = suceessDialog!!.findViewById(R.id.ll_leadnumber) as LinearLayout
                                            ll_leadnumber.visibility = View.GONE
                                            //LeadNumber
                                            tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
                                          //  tv_leadid!!.setText(jobjt.getString("LeadNumber"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
//                                                    val i = Intent(this@ProjectFollowUpActivity, LeadActivity::class.java)
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
                                                this@ProjectFollowUpActivity,
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
                                            this@ProjectFollowUpActivity,
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

    private fun currentStatusPopup(currentArraylist: JSONArray) {
        try {

            dialogCurrent = Dialog(this)
            dialogCurrent!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCurrent!! .setContentView(R.layout.list_popup)
            dialogCurrent!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogCurrent!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogCurrent!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogCurrent!! .findViewById(R.id.etsearch) as EditText
            val llsearch = dialogCurrent!! .findViewById(R.id.llsearch) as LinearLayout
            llsearch.visibility = View.GONE
            tvv_list_name!!.setText("CURRENT STATUS")

//            currentSort = JSONArray()
//            for (k in 0 until currentArraylist.length()) {
//                val jsonObject = currentArraylist.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                currentSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@ProjectFollowUpActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectStatusAdapter(this@ProjectFollowUpActivity, currentArraylist)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectFollowUpActivity)

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
//                    currentSort = JSONArray()
//
//                    for (k in 0 until currentArraylist.length()) {
//                        val jsonObject = currentArraylist.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("DeptName").length) {
//                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                                currentSort.put(jsonObject)
//                            }
//
//                        }
//                    }
//
//                    Log.e(TAG,"currentSort               7103    "+currentSort)
//                    val adapter = ProjectStatusAdapter(this@ProjectFollowUpActivity, currentSort)
//                    recylist!!.adapter = adapter
//                    adapter.setClickListener(this@ProjectFollowUpActivity)
//                }
//            })

            dialogCurrent!!.show()
            dialogCurrent!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("projectClick")){

            dialogProject!!.dismiss()
            val jsonObject = projectSort.getJSONObject(position)

            ID_Project = jsonObject.getString("ID_FIELD")
            tie_Project!!.setText(jsonObject.getString("ProjName"))

            ID_Stage = ""
            tie_Stage!!.setText("")
            tie_CurrentStatus!!.setText("")
//            ID_Team = ""
//            tie_Team!!.setText("")
//            ID_Employee = "0"
//            tie_Employee!!.setText("")

        }

        if (data.equals("stageCliik")){

            dialogStage!!.dismiss()

            val jsonObject = stageSort.getJSONObject(position)
            Log.e(TAG,"jsonObject  1074   "+jsonObject)
            ID_Stage = jsonObject.getString("ProjectStagesID")
            tie_Stage!!.setText(jsonObject.getString("StageName"))
            tie_DueDate!!.setText(jsonObject!!.getString("DueDate"))

//            ID_Team = ""
//            tie_Team!!.setText("")
//            ID_Employee = "0"
//            tie_Employee!!.setText("")

        }

        if (data.equals("ProjectStatusClick")){
            dialogCurrent!!.dismiss()
            val jsonObject = currentArraylist.getJSONObject(position)
            ID_CurrentStatus = jsonObject.getString("FK_Status")
            tie_CurrentStatus!!.setText(jsonObject.getString("StatusName"))

        }
    }
}