package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ServiceNewListReportAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class ProjectReportDetailActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ServiceReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var imback: ImageView? = null
    private var tv_ReportName: TextView? = null
    private var report_date: TextView? = null
    private var ReportMode:String?=""
    private var strFromdate:String?=""
    private var strTodate:String?=""
    private var ID_Leadno:String?=""
    var newList = 0
    var outstandingList = 0
    var serviceList = 0

    internal var ll_NewList: LinearLayout? = null
    internal var ll_OutStanding: LinearLayout? = null
    internal var ll_Service: LinearLayout? = null

    lateinit var serviceNewListReportViewModel: ServiceNewListReportViewModel
    lateinit var newListReportArrayList : JSONArray
    var recyNewList  : RecyclerView? = null

    lateinit var serviceListReportViewModel: ServiceListReportViewModel
    lateinit var serviceListReportArrayList : JSONArray
    var recyService  : RecyclerView? = null

    lateinit var serviceOutstandingListReportViewModel: ServiceOutstandingListReportViewModel
    lateinit var outstandingListReportArrayList : JSONArray
    var recyOutStanding  : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_report_detail)
        setRegViews()
        context = this@ProjectReportDetailActivity

        serviceNewListReportViewModel = ViewModelProvider(this).get(ServiceNewListReportViewModel::class.java)
        serviceOutstandingListReportViewModel = ViewModelProvider(this).get(ServiceOutstandingListReportViewModel::class.java)
        serviceListReportViewModel = ViewModelProvider(this).get(ServiceListReportViewModel::class.java)

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
            //New List
            newList = 0
          //  getNewListReport(ReportMode!!,ID_Branch!!,ID_Employee!!,strFromdate!!,strTodate!!,ID_CompService!!,ID_ComplaintList!!)
        }
        if (ReportMode.equals("2")){
            //Outstanding
            outstandingList = 0
          //  getOutstandingListReport(ReportMode!!,ID_Branch!!,ID_Employee!!,strFromdate!!,strTodate!!,ID_CompService!!,ID_ComplaintList!!)
        }

    }

    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_ReportName = findViewById(R.id.tv_ReportName)
        report_date= findViewById(R.id.report_date)

        ll_NewList = findViewById(R.id.ll_NewList)
        ll_OutStanding = findViewById(R.id.ll_OutStanding)
        ll_Service = findViewById(R.id.ll_Service)

        recyNewList = findViewById(R.id.recyNewList)
        recyOutStanding = findViewById(R.id.recyOutStanding)
        recyService = findViewById(R.id.recyService)

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

}