package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProjectSitevisitReportAdapter
import com.perfect.prodsuit.View.Adapter.ProjectStatusListReportAdapter
import com.perfect.prodsuit.Viewmodel.ReportSitevisitProjectViewModel
import com.perfect.prodsuit.Viewmodel.ReportStatusListProjectViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectReportDetailActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var imback: ImageView? = null
    private var tv_ReportName: TextView? = null
    private var report_date: TextView? = null
    private var ReportMode:String?=""
    private var strFromdate:String?=""
    private var strTodate:String?=""
    private var ID_Leadno:String?=""

  /*  internal var ll_SiteVisit: LinearLayout? = null
    internal var ll_OutStanding: LinearLayout? = null
    internal var ll_Service: LinearLayout? = null*/

    lateinit var reportSitevisitProjectViewModel: ReportSitevisitProjectViewModel
    lateinit var reportsitevisitArrayList: JSONArray
    var recySiteVisit : RecyclerView? = null

    lateinit var reportStatusListProjectViewModel: ReportStatusListProjectViewModel
//    lateinit var reportsitevisitArrayList: JSONArray
//    var recySiteVisit : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_report_detail)
        setRegViews()
        context = this@ProjectReportDetailActivity


        reportSitevisitProjectViewModel = ViewModelProvider(this).get(ReportSitevisitProjectViewModel::class.java)

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

        if (ReportMode.equals("1")){
            getReportSitevisit(ReportMode!!,strFromdate!!,strTodate!!, ID_Leadno!!)
        }
        if (ReportMode.equals("2")){
            getProjectStatusListReport(ReportMode!!,strFromdate!!,strTodate!!, ID_Leadno!!)
        }

    }

    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)
        tv_ReportName = findViewById(R.id.tv_ReportName)
        report_date= findViewById(R.id.report_date)
/*        ll_SiteVisit = findViewById(R.id.ll_SiteVisit)
        ll_OutStanding = findViewById(R.id.ll_OutStanding)
        ll_Service = findViewById(R.id.ll_Service)
        ll_SiteVisit = findViewById(R.id.ll_SiteVisit)*/
        recySiteVisit = findViewById(R.id.recySiteVisit)
  /*      recyOutStanding = findViewById(R.id.recyOutStanding)
        recyService = findViewById(R.id.recyService)*/
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("serviceReportClick")) {
            Log.e(TAG,"serviceReportClick   5091")
            if (ReportMode.equals("1")){
                //openBottomSheetReport(newListReportArrayList,position,ReportMode!!)
            }

            if (ReportMode.equals("2")){
               // openBottomSheetReport(outstandingListReportArrayList,position,ReportMode!!)
            }


        }

    }

    private fun getReportSitevisit(ReportMode: String, strFromdate: String, strTodate: String, strIdLead: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportSitevisitProjectViewModel.getReportsitevisitProject(this,"4", strFromdate, strTodate, strIdLead)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   10   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                var arrayFrom=strFromdate!!.split("-")
                                var arrayTo=strTodate!!.split("-")
                                var fromDate=arrayFrom[2]+"-"+arrayFrom[1]+"-"+arrayFrom[0]
                                var toDate=arrayTo[2]+"-"+arrayTo[1]+"-"+arrayTo[0]
                                report_date!!.text="Report between "+fromDate+" and "+toDate



                                val jobjt = jObject.getJSONObject("ProjectReport")
                                reportsitevisitArrayList = jobjt.getJSONArray("SiteVisitList")
                                if (reportsitevisitArrayList.length() > 0) {


                                    val lLayout = GridLayoutManager(this@ProjectReportDetailActivity, 1)
                                    recySiteVisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    val adapter1 = ProjectSitevisitReportAdapter(applicationContext, reportsitevisitArrayList,ReportMode)
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

    private fun getProjectStatusListReport(ReportMode: String, strFromdate: String, strTodate: String, strIdLead: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportStatusListProjectViewModel.getReportstatuslistProject(this,"12", strFromdate, strTodate, strIdLead)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   100   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                var arrayFrom=strFromdate!!.split("-")
                                var arrayTo=strTodate!!.split("-")
                                var fromDate=arrayFrom[2]+"-"+arrayFrom[1]+"-"+arrayFrom[0]
                                var toDate=arrayTo[2]+"-"+arrayTo[1]+"-"+arrayTo[0]
                                report_date!!.text="Report between "+fromDate+" and "+toDate



                                val jobjt = jObject.getJSONObject("ProjectReportDetail")
                                reportsitevisitArrayList = jobjt.getJSONArray("ProjectStatusList")
                                if (reportsitevisitArrayList.length() > 0) {


                                    val lLayout = GridLayoutManager(this@ProjectReportDetailActivity, 1)
                                    recySiteVisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    val adapter1 = ProjectStatusListReportAdapter(applicationContext, reportsitevisitArrayList,ReportMode)
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
}