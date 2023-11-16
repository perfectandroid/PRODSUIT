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

class ProjectReportActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG: String = "ProjectReportActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    var tie_ReportName: TextInputEditText? = null
    var tie_LeadNo: TextInputEditText? = null
    var tie_FromDate: TextInputEditText? = null
    var tie_ToDate: TextInputEditText? = null
    var tie_Product: TextInputEditText? = null
    lateinit var reportNameProjectViewModel: ReportNameProjectViewModel
    lateinit var reportNameArrayList: JSONArray
    lateinit var reportNamesort: JSONArray
    private var dialogReportName: Dialog? = null
    var recyReportName: RecyclerView? = null
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    var recyBranch: RecyclerView? = null
    var empUseBranch = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList : JSONArray
    lateinit var employeeAllSort : JSONArray
    var recyEmployeeAll: RecyclerView? = null
    var btnSubmit: Button? = null
    var btnReset: Button? = null
    var ReqMode: String? = ""
    var SubMode: String? = ""
    private var fromToDate: Int = 0
    private var ReportMode: String = ""
    var FromDate: String = ""
    var ToDate: String = ""
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var dialogLeadNo : Dialog? = null
    var recyLeadNo: RecyclerView? = null
    lateinit var leadnoSort : JSONArray
    var strID_FIELD   = ""
    var leadcount       = 0
    lateinit var leadnoArrayList : JSONArray
    lateinit var leadnoViewModel: LeadNoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_report)
        context = this@ProjectReportActivity
        leadnoViewModel = ViewModelProvider(this).get(LeadNoViewModel::class.java)
        reportNameProjectViewModel = ViewModelProvider(this).get(ReportNameProjectViewModel::class.java)
       setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        tie_ReportName = findViewById(R.id.tie_ReportName)
        tie_FromDate = findViewById(R.id.tie_FromDate)
        tie_ToDate = findViewById(R.id.tie_ToDate)
        tie_LeadNo = findViewById(R.id.tie_LeadNo)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        imback!!.setOnClickListener(this)
        tie_ReportName!!.setOnClickListener(this)
        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_LeadNo!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.tie_ReportName -> {
                getReportName()
            }
            R.id.tie_LeadNo -> {
                leadcount = 0
                getLeadNo()
            }
            R.id.tie_FromDate -> {
                openBottomSheet(tie_FromDate,tie_ToDate)
            }
            R.id.tie_ToDate -> {
                openBottomSheet(tie_FromDate,tie_ToDate)
            }
            R.id.btnSubmit -> {
                validateData(v)
            }
            R.id.btnReset -> {
                resetData()
            }
        }
    }

    private fun resetData() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)
        tie_ReportName!!.setText("")
        tie_LeadNo!!.setText("")
        ReportMode = ""
        strID_FIELD = ""
    }

    private fun validateData(v: View) {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val fromDa = sdf.parse(tie_FromDate!!.text.toString());
        val toDa = sdf.parse(tie_ToDate!!.text.toString());

        if (ReportMode.equals("")) {
            Config.snackBars(context, v, "Select Report Name")
        }
        else if (strID_FIELD.equals("")) {
            Config.snackBars(context, v, "Select Lead Number")
        }else if (tie_FromDate!!.text.toString().equals("")) {
            Config.snackBars(context, v, "Select From Date")
        } else if (tie_ToDate!!.text.toString().equals("")) {
            Config.snackBars(context, v, "Select To Date")
        } else if (fromDa.after(toDa)) {
            Config.snackBars(context, v, "Check Selected Date Range")
        } else {
            PassData()
        }
    }

    private fun PassData() {
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFrom = inputFormat.parse(tie_FromDate!!.text.toString())
        val strFromDate = outputFormat.format(dateFrom)
        val dateTo = inputFormat.parse(tie_ToDate!!.text.toString())
        val strToDate = outputFormat.format(dateTo)
        Log.e(TAG, "strFromDate   " + strFromDate + "    " + strToDate)
        intent = Intent(applicationContext, ProjectReportDetailActivity::class.java)
        intent.putExtra("ReportName", tie_ReportName!!.text.toString())
        intent.putExtra("LeadNumber", tie_LeadNo!!.text.toString())
        intent.putExtra("ReportMode", ReportMode)
        intent.putExtra("Fromdate", strFromDate)
        intent.putExtra("Todate", strToDate)
        startActivity(intent)
    }

    private fun getReportName() {
        var reportName = 0
        var SubMode = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportNameProjectViewModel.getReportNameProject(this,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   1062   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("ProjectReportNameDetails")
                                reportNameArrayList = jobjt.getJSONArray("ProjectReportNameDetailsList")
                                if (reportNameArrayList.length() > 0) {
                                    if (reportName == 0) {
                                        reportName++
                                        reportNamePopup(reportNameArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProjectReportActivity,
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

    private fun reportNamePopup(reportNameArrayList: JSONArray) {
        try {

            dialogReportName = Dialog(this)
            dialogReportName!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReportName!!.setContentView(R.layout.report_name_popup)
            dialogReportName!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyReportName = dialogReportName!!.findViewById(R.id.recyReportName) as RecyclerView
            val etsearch = dialogReportName!!.findViewById(R.id.etsearch) as EditText

            reportNamesort = JSONArray()
            for (k in 0 until reportNameArrayList.length()) {
                val jsonObject = reportNameArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                reportNamesort.put(jsonObject)
            }

            Log.e(TAG, "reportNamesort               7101    " + reportNamesort)
            Log.e(TAG, "reportNameArrayList      7102    " + reportNameArrayList)


            val lLayout = GridLayoutManager(this@ProjectReportActivity, 1)
            recyReportName!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ReportNameAdapter(this@ProjectReportActivity, reportNamesort,"Project")
            recyReportName!!.adapter = adapter
            adapter.setClickListener(this@ProjectReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    reportNamesort = JSONArray()

                    for (k in 0 until reportNameArrayList.length()) {
                        val jsonObject = reportNameArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ReportName").length) {
                            if (jsonObject.getString("ReportName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                reportNamesort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "reportNamesort               7103    " + reportNamesort)
                    val adapter = ReportNameAdapter(this@ProjectReportActivity, reportNamesort,"Service")
                    recyReportName!!.adapter = adapter
                    adapter.setClickListener(this@ProjectReportActivity)
                }
            })

            dialogReportName!!.show()
            dialogReportName!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
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
                                            this@ProjectReportActivity,
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

            val lLayout = GridLayoutManager(this@ProjectReportActivity, 1)
            recyLeadNo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = LeadNoAdapter(this@ProjectReportActivity, leadnoSort)
            recyLeadNo!!.adapter = adapter
            adapter.setClickListener(this@ProjectReportActivity)

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
                    val adapter = LeadNoAdapter(this@ProjectReportActivity, leadnoSort)
                    recyLeadNo!!.adapter = adapter
                    adapter.setClickListener(this@ProjectReportActivity)
                }
            })

            dialogLeadNo!!.show()
            dialogLeadNo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openBottomSheet(dateField1:TextInputEditText?,dateField2:TextInputEditText?) {
        // BottomSheet
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_date_chooser, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val edt_fromDate = view.findViewById<EditText>(R.id.edt_fromDate)
        val edt_toDate = view.findViewById<EditText>(R.id.edt_toDate)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val rad_this_month = view.findViewById<RadioButton>(R.id.rad_this_month)
        val rad_last_month = view.findViewById<RadioButton>(R.id.rad_last_month)
        val rad_last_3_month = view.findViewById<RadioButton>(R.id.rad_last_3_month)
        val rad_last_6_month = view.findViewById<RadioButton>(R.id.rad_last_6_month)
        val rad_last_12_month = view.findViewById<RadioButton>(R.id.rad_last_12_month)
        rad_this_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")

            val calendar: Calendar = Calendar.getInstance()
            // calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar.getTime()
            rad_this_month!!.isChecked = true
            rad_last_month!!.isChecked = false
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = false


            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")

            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked = true
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = false

            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_3_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -3)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =true
            rad_last_6_month!!.isChecked =false
            rad_last_12_month!!.isChecked =false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_6_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -6)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =false
            rad_last_6_month!!.isChecked =true
            rad_last_12_month!!.isChecked =false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_12_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -12)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =false
            rad_last_6_month!!.isChecked =false
            rad_last_12_month!!.isChecked =true
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        edt_fromDate.setOnClickListener(View.OnClickListener {
            fromToDate = 0
            datePicker(edt_fromDate)
        })
        edt_toDate.setOnClickListener(View.OnClickListener {
            fromToDate = 1
            datePicker(edt_toDate)
        })

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            if(edt_fromDate.text.toString()==""&&edt_toDate.text.toString()!="")
            {
                val builder = AlertDialog.Builder(
                    this@ProjectReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            else if(edt_fromDate.text.toString()!=""&&edt_toDate.text.toString()=="")
            {
                val builder = AlertDialog.Builder(
                    this@ProjectReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            else
            {
                dialog.dismiss()
                dateField1!!.setText(FromDate)
                dateField2!!.setText(ToDate)
            }

//                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
//                val day: Int = date_Picker1!!.getDayOfMonth()
//                val mon: Int = date_Picker1!!.getMonth()
//                val month: Int = mon + 1
//                val year: Int = date_Picker1!!.getYear()
//                var strDay = day.toString()
//                var strMonth = month.toString()
//                var strYear = year.toString()
//                if (strDay.length == 1) {
//                    strDay = "0" + day
//                }
//                if (strMonth.length == 1) {
//                    strMonth = "0" + strMonth
//                }
//
//                if (fromToDate == 0) {
//                    tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                }
//                if (fromToDate == 1) {
//                    tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun datePicker(dateField:TextView) {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

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

                if (fromToDate == 0) {
                    tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    FromDate=strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" +strDay + "-" + strMonth + "-" + strYear)
                }
                if (fromToDate == 1) {
                    tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    ToDate=strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" +strDay + "-" + strMonth + "-" + strYear)
                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()

    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("reportname")) {
            dialogReportName!!.dismiss()
            val jsonObject = reportNamesort.getJSONObject(position)
            Log.e(TAG, "ReportMode   " + jsonObject.getString("ReportMode"))
            ReportMode = jsonObject.getString("ReportMode")
            tie_ReportName!!.setText(jsonObject.getString("ReportName"))
        }
        if (data.equals("LeadNumberClick")){
            val jsonObject = leadnoSort.getJSONObject(position)
            Log.e(TAG,"LeadNo list"+leadnoSort.toString())
            Log.e(TAG,"LeadNo   "+jsonObject.getString("LeadNo"))
            tie_LeadNo!!.setText(jsonObject.getString("LeadNo"))
            strID_FIELD = jsonObject.getString("ID_FIELD")
            dialogLeadNo!!.dismiss()
        }
    }

}