package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProjectSitevisitReportAdapter
import com.perfect.prodsuit.View.Adapter.ProjectStatusListReportAdapter
import com.perfect.prodsuit.Viewmodel.ReportSitevisitProjectViewModel
import com.perfect.prodsuit.Viewmodel.ReportStatusListProjectViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectReportDetailActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG: String = "ProjectReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var imback: ImageView? = null
    private var tv_ReportName: TextView? = null
    private var report_date: TextView? = null
    private var ReportMode: String? = ""
    private var strFromdate: String? = ""
    private var strTodate: String? = ""
    private var ID_Leadno: String? = ""
    private var CatID: String? = ""
    private var strID_FIELD: String? = ""

    /*  internal var ll_SiteVisit: LinearLayout? = null
      internal var ll_OutStanding: LinearLayout? = null
      internal var ll_Service: LinearLayout? = null*/

    lateinit var reportSitevisitProjectViewModel: ReportSitevisitProjectViewModel
    lateinit var reportsitevisitArrayList: JSONArray
    var recySiteVisit: RecyclerView? = null

    lateinit var reportStatusListProjectViewModel: ReportStatusListProjectViewModel
    lateinit var projectListArrayList: JSONArray
//    var recySiteVisit : RecyclerView? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_project_report_detail)
        setRegViews()
        context = this@ProjectReportDetailActivity


        reportSitevisitProjectViewModel =
            ViewModelProvider(this).get(ReportSitevisitProjectViewModel::class.java)
        reportStatusListProjectViewModel =
            ViewModelProvider(this).get(ReportStatusListProjectViewModel::class.java)

        if (getIntent().hasExtra("ReportName")) {
            tv_ReportName!!.setText(intent.getStringExtra("ReportName"))
        }
        if (getIntent().hasExtra("ReportMode")) {
            ReportMode = intent.getStringExtra("ReportMode")
        }
        if (getIntent().hasExtra("Fromdate")) {
            strFromdate = intent.getStringExtra("Fromdate")
        }
        if (getIntent().hasExtra("Todate")) {
            strTodate = intent.getStringExtra("Todate")
        }
        if (getIntent().hasExtra("LeadNumber")) {
            ID_Leadno = intent.getStringExtra("LeadNumber")
        }
        if (getIntent().hasExtra("CatID")) {
            CatID = intent.getStringExtra("CatID")
        }
        if (getIntent().hasExtra("strID_FIELD")) {
            strID_FIELD = intent.getStringExtra("strID_FIELD")
        }

        if (ReportMode.equals("2")) {
            getReportSitevisit(ReportMode!!, strFromdate!!, strTodate!!, ID_Leadno!!,strID_FIELD!!)
        }
        if (ReportMode.equals("1")) {
            // getProjectStatusListReport(ReportMode!!,strFromdate!!,strTodate!!, ID_Leadno!!)
            getProjectListReport(ReportMode!!, strFromdate!!, strTodate!!, CatID!!)
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)
        tv_ReportName = findViewById(R.id.tv_ReportName)
        report_date = findViewById(R.id.report_date)
/*        ll_SiteVisit = findViewById(R.id.ll_SiteVisit)
        ll_OutStanding = findViewById(R.id.ll_OutStanding)
        ll_Service = findViewById(R.id.ll_Service)
        ll_SiteVisit = findViewById(R.id.ll_SiteVisit)*/
        recySiteVisit = findViewById(R.id.recySiteVisit)
        /*      recyOutStanding = findViewById(R.id.recyOutStanding)
              recyService = findViewById(R.id.recyService)*/
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("sitevisitReportClick")) {
                openBottomSheetReport(reportsitevisitArrayList, position, "1")
        }
        if (data.equals("ProjectListReportClick")) {
                openBottomSheetReport(projectListArrayList, position, "2")
        }

    }


    private fun openBottomSheetReport(jsonArray: JSONArray, position: Int, ReprtMode: String) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_projectreport_followup, null)

        var jsonObject = jsonArray.getJSONObject(position)

        val ll_ProjectListReport = view.findViewById<LinearLayout>(R.id.ll_ProjectListReport)
        val ll_SiteVisitListReport = view.findViewById<LinearLayout>(R.id.ll_SiteVisitListReport)

        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val txtProjectName = view.findViewById<TextView>(R.id.txtProjectName)
        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
        val txtLeadNumber = view.findViewById<TextView>(R.id.txtLeadNumber)
        val txtFinalAmount_R = view.findViewById<TextView>(R.id.txtFinalAmount_R)
        val txtStartDate = view.findViewById<TextView>(R.id.txtStartDate)
        val txtDueDate = view.findViewById<TextView>(R.id.txtDueDate)
        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        val txtDuration = view.findViewById<TextView>(R.id.txtDuration)
        val txtSubCategory = view.findViewById<TextView>(R.id.txtSubCategory)
        val txtShortName = view.findViewById<TextView>(R.id.txtShortName)
        val txtCreateDate = view.findViewById<TextView>(R.id.txtCreateDate)
        val txtDurationType = view.findViewById<TextView>(R.id.txtDurationType)

        val txtSiteVisitID = view.findViewById<TextView>(R.id.txtSiteVisitID)
        val txtLeadGenerationID = view.findViewById<TextView>(R.id.txtLeadGenerationID)
        val txtLeadNo = view.findViewById<TextView>(R.id.txtLeadNo)
        val txtVisitDate = view.findViewById<TextView>(R.id.txtVisitDate)
        val txtVisitTime = view.findViewById<TextView>(R.id.txtVisitTime)
        val txtNote1 = view.findViewById<TextView>(R.id.txtNote1)
        val txtNote2 = view.findViewById<TextView>(R.id.txtNote2)
        val txtCusNote = view.findViewById<TextView>(R.id.txtCusNote)
        val txtExpenseAmount = view.findViewById<TextView>(R.id.txtExpenseAmount)
        val txtRemarks = view.findViewById<TextView>(R.id.txtRemarks)

        if (ReprtMode.equals("2")){
            ll_ProjectListReport.visibility = View.VISIBLE
            ll_SiteVisitListReport.visibility = View.GONE
            txtProjectName!!.setText(jsonObject.getString("ProjectName"))
            txtCategory!!.setText(jsonObject.getString("Category"))
            txtLeadNumber!!.setText(jsonObject.getString("LeadNumber"))
            txtFinalAmount_R!!.setText(jsonObject.getString("FinalAmount_R"))
            txtStartDate!!.setText(jsonObject.getString("StartDate"))
            txtDueDate!!.setText(jsonObject.getString("DueDate"))
            txtStatus!!.setText(jsonObject.getString("Status"))
            txtDuration!!.setText(jsonObject.getString("Duration"))
            txtSubCategory!!.setText(jsonObject.getString("SubCategory"))
            txtShortName!!.setText(jsonObject.getString("ShortName"))
            txtCreateDate!!.setText(jsonObject.getString("CreateDate"))
            txtDurationType!!.setText(jsonObject.getString("DurationType"))
        }
        else if (ReprtMode.equals("1")){

            ll_ProjectListReport.visibility = View.GONE
            ll_SiteVisitListReport.visibility = View.VISIBLE

            txtSiteVisitID!!.setText(jsonObject.getString("SiteVisitID"))
            txtLeadGenerationID!!.setText(jsonObject.getString("LeadGenerationID"))
            txtLeadNo!!.setText(jsonObject.getString("LeadNo"))
            txtVisitDate!!.setText(jsonObject.getString("VisitDate"))
            txtVisitTime!!.setText(jsonObject.getString("VisitTime"))
            txtNote1!!.setText(jsonObject.getString("Note1"))
            txtNote2!!.setText(jsonObject.getString("Note2"))
            txtCusNote!!.setText(jsonObject.getString("CusNote"))
            txtExpenseAmount!!.setText(jsonObject.getString("ExpenseAmount"))
            txtRemarks!!.setText(jsonObject.getString("Remarks"))
        }




        imgClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getReportSitevisit(
        ReportMode: String,
        strFromdate: String,
        strTodate: String,
        strIdLead: String,
        strID_FIELD: String
    ) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportSitevisitProjectViewModel.getReportsitevisitProject(
                    this,
                    "4",
                    strFromdate,
                    strTodate,
                    strID_FIELD
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   10   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                var arrayFrom = strFromdate!!.split("-")
                                var arrayTo = strTodate!!.split("-")
                                var fromDate =
                                    arrayFrom[2] + "-" + arrayFrom[1] + "-" + arrayFrom[0]
                                var toDate = arrayTo[2] + "-" + arrayTo[1] + "-" + arrayTo[0]
                                report_date!!.text = "Report between " + fromDate + " and " + toDate


                                val jobjt = jObject.getJSONObject("ProjectReport")
                                reportsitevisitArrayList = jobjt.getJSONArray("SiteVisitList")
                                if (reportsitevisitArrayList.length() > 0) {


                                    val lLayout =
                                        GridLayoutManager(this@ProjectReportDetailActivity, 1)
                                    recySiteVisit!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter1 = ProjectSitevisitReportAdapter(
                                        applicationContext,
                                        reportsitevisitArrayList,
                                        ReportMode
                                    )
                                    recySiteVisit!!.adapter = adapter1
                                    adapter1.setClickListener(this@ProjectReportDetailActivity)


                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProjectReportDetailActivity,
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

    private fun getProjectListReport(
        ReportMode: String,
        strFromdate: String,
        strTodate: String,
        strIdCat: String
    ) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportStatusListProjectViewModel.getReportstatuslistProject(
                    this,
                    "1",
                    strFromdate,
                    strTodate,
                    strIdCat
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   100   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                var arrayFrom = strFromdate!!.split("-")
                                var arrayTo = strTodate!!.split("-")
                                var fromDate =
                                    arrayFrom[2] + "-" + arrayFrom[1] + "-" + arrayFrom[0]
                                var toDate = arrayTo[2] + "-" + arrayTo[1] + "-" + arrayTo[0]
                                report_date!!.text = "Report between " + fromDate + " and " + toDate


                                val jobjt = jObject.getJSONObject("ProjectListDetail")
                                projectListArrayList = jobjt.getJSONArray("ProjectLists")
                                if (projectListArrayList.length() > 0) {


                                    val lLayout =
                                        GridLayoutManager(this@ProjectReportDetailActivity, 1)
                                    recySiteVisit!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter1 = ProjectStatusListReportAdapter(
                                        applicationContext,
                                        projectListArrayList,
                                        ReportMode
                                    )
                                    recySiteVisit!!.adapter = adapter1
                                    adapter1.setClickListener(this@ProjectReportDetailActivity)

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProjectReportDetailActivity,
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

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}