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

class ServiceReportDetailActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ServiceReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var imback: ImageView? = null
    private var tv_ReportName: TextView? = null
    private var report_date: TextView? = null
    private var ReportMode:String?=""
    private var ID_Branch:String?=""
    private var ID_Employee:String?=""
    private var strFromdate:String?=""
    private var strTodate:String?=""
    private var ID_Product:String?=""
    private var ID_CompService:String?=""
    private var ID_ComplaintList:String?=""

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
        context = this@ServiceReportDetailActivity

        serviceNewListReportViewModel = ViewModelProvider(this).get(ServiceNewListReportViewModel::class.java)
        serviceOutstandingListReportViewModel = ViewModelProvider(this).get(ServiceOutstandingListReportViewModel::class.java)
        serviceListReportViewModel = ViewModelProvider(this).get(ServiceListReportViewModel::class.java)

        setRegViews()

        if (getIntent().hasExtra("ReportName")) {
            tv_ReportName!!.setText(intent.getStringExtra("ReportName"))
        }
        if (getIntent().hasExtra("ReportMode")) {
            ReportMode = intent.getStringExtra("ReportMode")
        }
        if (getIntent().hasExtra("ID_Branch")) {
            ID_Branch = intent.getStringExtra("ID_Branch")
        }
        if (getIntent().hasExtra("ID_Employee")) {
            ID_Employee = intent.getStringExtra("ID_Employee")
        }
        if (getIntent().hasExtra("Fromdate")) {
            strFromdate = intent.getStringExtra("Fromdate")
        }
        if (getIntent().hasExtra("Todate")) {
            strTodate = intent.getStringExtra("Todate")
        }
        if (getIntent().hasExtra("ID_Product")) {
          //  ID_Product = intent.getStringExtra("ID_Product")
        }
        if (getIntent().hasExtra("ID_CompService")) {
            ID_CompService = intent.getStringExtra("ID_CompService")
        }
        if (getIntent().hasExtra("ID_ComplaintList")) {
            ID_ComplaintList = intent.getStringExtra("ID_ComplaintList")
        }

        if (ReportMode.equals("1")){
            //New List
            newList = 0
            getNewListReport(ReportMode!!,ID_Branch!!,ID_Employee!!,strFromdate!!,strTodate!!,ID_Product!!,ID_CompService!!,ID_ComplaintList!!)
        }
        if (ReportMode.equals("3")){
            //Outstanding
            outstandingList = 0
            getOutstandingListReport(ReportMode!!,ID_Branch!!,ID_Employee!!,strFromdate!!,strTodate!!,ID_Product!!,ID_CompService!!,ID_ComplaintList!!)
        }
        if (ReportMode.equals("6")){
            //Service List
            serviceList = 0
            getServiceListReport(ReportMode!!,ID_Branch!!,ID_Employee!!,strFromdate!!,strTodate!!,ID_Product!!,ID_CompService!!,ID_ComplaintList!!)
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

    private fun getNewListReport(ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String,
                                 ID_CompService: String, ID_ComplaintList: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceNewListReportViewModel.getserviceNewList(this,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (newList == 0){
                                    newList++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2702   "+msg.length)
                                    if (jObject.getString("StatusCode") == "0") {
                                        report_date!!.visibility = View.VISIBLE
                                        ll_NewList!!.visibility = View.VISIBLE
                                        var arrayFrom=strFromdate!!.split("-")
                                        var arrayTo=strTodate!!.split("-")
                                        var fromDate=arrayFrom[2]+"-"+arrayFrom[1]+"-"+arrayFrom[0]
                                        var toDate=arrayTo[2]+"-"+arrayTo[1]+"-"+arrayTo[0]
                                        report_date!!.text="Report between "+fromDate+" and "+toDate

                                        val jobjt = jObject.getJSONObject("NewListDetails")
                                        newListReportArrayList = jobjt.getJSONArray("NewListDetailsList")

                                        Log.e(TAG,"NEW LIST  1553   "+newListReportArrayList)
                                        try {
                                            val lLayout = GridLayoutManager(this@ServiceReportDetailActivity, 1)
                                            recyNewList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            val adapter1 = ServiceNewListReportAdapter(applicationContext, newListReportArrayList,ReportMode)
                                            recyNewList!!.adapter = adapter1
                                             adapter1.setClickListener(this@ServiceReportDetailActivity)

                                        }catch (e: Exception){
                                            Log.e(TAG,"msg   3444   "+e.toString())
                                        }
                                    }
                                    else {
                                        report_date!!.visibility=View.GONE
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

//        var strNewlist ="{\n" +
//                "  \"NewListDetails\": {\n" +
//                "    \"NewListDetailsList\": [\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0551\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0552\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0553\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0554\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0555\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0556\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0557\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0558\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      }\n" +
//                "    ],\n" +
//                "    \"ResponseCode\": \"0\",\n" +
//                "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                "  },\n" +
//                "  \"StatusCode\": 0,\n" +
//                "  \"EXMessage\": \"Transaction Verified\"\n" +
//                "}"
//
//
//        Log.e(TAG,"NEW LIST  155   "+strNewlist)



    }

    private fun getOutstandingListReport(ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String,
                                 ID_CompService: String, ID_ComplaintList: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceOutstandingListReportViewModel.getserviceOutstandingList(this,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (newList == 0){
                                    newList++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2702   "+msg.length)
                                    if (jObject.getString("StatusCode") == "0") {
                                        report_date!!.visibility = View.VISIBLE
                                        ll_OutStanding!!.visibility = View.VISIBLE
                                        var arrayFrom=strFromdate!!.split("-")
                                        var arrayTo=strTodate!!.split("-")
                                        var fromDate=arrayFrom[2]+"-"+arrayFrom[1]+"-"+arrayFrom[0]
                                        var toDate=arrayTo[2]+"-"+arrayTo[1]+"-"+arrayTo[0]
                                        report_date!!.text="Report between "+fromDate+" and "+toDate

                                        val jobjt = jObject.getJSONObject("NewListDetails")
                                        outstandingListReportArrayList = jobjt.getJSONArray("NewListDetailsList")

                                        Log.e(TAG,"NEW LIST  1553   "+outstandingListReportArrayList)
                                        try {
                                            val lLayout = GridLayoutManager(this@ServiceReportDetailActivity, 1)
                                            recyOutStanding!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            val adapter1 = ServiceNewListReportAdapter(applicationContext, outstandingListReportArrayList,ReportMode)
                                            recyOutStanding!!.adapter = adapter1
                                            adapter1.setClickListener(this@ServiceReportDetailActivity)

                                        }catch (e: Exception){
                                            Log.e(TAG,"msg   3444   "+e.toString())
                                        }
                                    }
                                    else {
                                        report_date!!.visibility=View.GONE
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

//        var strNewlist ="{\n" +
//                "  \"NewListDetails\": {\n" +
//                "    \"NewListDetailsList\": [\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0551\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0552\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0553\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0554\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0555\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0556\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0557\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0558\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      }\n" +
//                "    ],\n" +
//                "    \"ResponseCode\": \"0\",\n" +
//                "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                "  },\n" +
//                "  \"StatusCode\": 0,\n" +
//                "  \"EXMessage\": \"Transaction Verified\"\n" +
//                "}"
//
//
//        Log.e(TAG,"NEW LIST  155   "+strNewlist)



    }

    private fun getServiceListReport(ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String,
                                         ID_CompService: String, ID_ComplaintList: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceListReportViewModel.getserviceList(this,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (outstandingList == 0){
                                    outstandingList++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2702   "+msg.length)
                                    if (jObject.getString("StatusCode") == "0") {
                                        report_date!!.visibility = View.VISIBLE
                                        ll_Service!!.visibility = View.VISIBLE
                                        var arrayFrom=strFromdate!!.split("-")
                                        var arrayTo=strTodate!!.split("-")
                                        var fromDate=arrayFrom[2]+"-"+arrayFrom[1]+"-"+arrayFrom[0]
                                        var toDate=arrayTo[2]+"-"+arrayTo[1]+"-"+arrayTo[0]
                                        report_date!!.text="Report between "+fromDate+" and "+toDate

                                        val jobjt = jObject.getJSONObject("NewListDetails")
                                        serviceListReportArrayList = jobjt.getJSONArray("NewListDetailsList")

                                        Log.e(TAG,"NEW LIST  1553   "+serviceListReportArrayList)
                                        try {
                                            val lLayout = GridLayoutManager(this@ServiceReportDetailActivity, 1)
                                            recyService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            val adapter1 = ServiceNewListReportAdapter(applicationContext, serviceListReportArrayList,ReportMode)
                                            recyService!!.adapter = adapter1
                                            adapter1.setClickListener(this@ServiceReportDetailActivity)

                                        }catch (e: Exception){
                                            Log.e(TAG,"msg   3444   "+e.toString())
                                        }
                                    }
                                    else {
                                        report_date!!.visibility=View.GONE
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

//        var strNewlist ="{\n" +
//                "  \"NewListDetails\": {\n" +
//                "    \"NewListDetailsList\": [\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0551\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0552\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0553\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0554\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0555\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0556\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0557\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"TicketNo\": \"TKT-0558\",\n" +
//                "        \"TicketDate\": \"04/04/2023\",\n" +
//                "        \"Customer\": \"Mallika\",\n" +
//                "        \"Product\": \"Solar Panel\",\n" +
//                "        \"Complaint\": \"Server Issue\",\n" +
//                "        \"CurrentStatus\": \"Pending\",\n" +
//                "        \"Description\": \"\"\n" +
//                "      }\n" +
//                "    ],\n" +
//                "    \"ResponseCode\": \"0\",\n" +
//                "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                "  },\n" +
//                "  \"StatusCode\": 0,\n" +
//                "  \"EXMessage\": \"Transaction Verified\"\n" +
//                "}"
//
//
//        Log.e(TAG,"NEW LIST  155   "+strNewlist)



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
                openBottomSheetReport(newListReportArrayList,position,ReportMode!!)
            }

            if (ReportMode.equals("3")){
                openBottomSheetReport(outstandingListReportArrayList,position,ReportMode!!)
            }

            if (ReportMode.equals("6")){
                openBottomSheetReport(serviceListReportArrayList,position,ReportMode!!)
            }

        }

    }

    private fun openBottomSheetReport(jsonArray : JSONArray,position : Int,ReportMode : String) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_report_service, null)

        var jsonObject = jsonArray.getJSONObject(position)

//        val ll_NewDisable = view.findViewById<LinearLayout>(R.id.ll_NewDisable)
//        val ll_followDisable = view.findViewById<LinearLayout>(R.id.ll_followDisable)
//
//        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
//        val txtLeadNo = view.findViewById<TextView>(R.id.txtLeadNo)
//        val txtLeadDate = view.findViewById<TextView>(R.id.txtLeadDate)
//        val txtCustomer = view.findViewById<TextView>(R.id.txtCustomer)
//        val txtProduct = view.findViewById<TextView>(R.id.txtProduct)
//
//        val txtAction = view.findViewById<TextView>(R.id.txtAction)
//        val txtActionType = view.findViewById<TextView>(R.id.txtActionType)
//        val txtCommitedDate = view.findViewById<TextView>(R.id.txtCommitedDate)
//        val txtAssignee = view.findViewById<TextView>(R.id.txtAssignee)
//        val txtCompletedDate = view.findViewById<TextView>(R.id.txtCompletedDate)
//        val txtDueDays = view.findViewById<TextView>(R.id.txtDueDays)
//
//        val txtPriority = view.findViewById<TextView>(R.id.txtPriority)
//        val txtLeadFrom = view.findViewById<TextView>(R.id.txtLeadFrom)
//        val txtLeadSource = view.findViewById<TextView>(R.id.txtLeadSource)
//        val txtCollectedBy = view.findViewById<TextView>(R.id.txtCollectedBy)
//        val txtCurrentAssignee = view.findViewById<TextView>(R.id.txtCurrentAssignee)
//        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
//
//        val txtRemarks = view.findViewById<TextView>(R.id.txtRemarks)
//
//        txtLeadNo!!.setText(jsonObject.getString("LeadNo"))
//        txtLeadDate!!.setText(jsonObject.getString("LeadDate"))
//        txtCustomer!!.setText(jsonObject.getString("Customer"))
//        txtProduct!!.setText(jsonObject.getString("Product"))
//
//        if (ReportMode.equals("2")){
//            ll_NewDisable.visibility = View.GONE
//            ll_followDisable.visibility = View.VISIBLE
//            txtAction!!.setText(jsonObject.getString("NextAction"))
//            txtActionType!!.setText(jsonObject.getString("FollowUpMethod"))
//            txtCommitedDate!!.setText(jsonObject.getString("LeadDate"))
//            txtAssignee!!.setText(jsonObject.getString("AssignedTo"))
//            txtCompletedDate!!.setText(jsonObject.getString("CompletedDate"))
//            txtDueDays!!.setText(jsonObject.getString("DueDays"))
//        }
//        else if (ReportMode.equals("5")){
//            ll_NewDisable.visibility = View.VISIBLE
//            ll_followDisable.visibility = View.GONE
//
//            txtPriority!!.setText(jsonObject.getString("Priority"))
//            txtLeadFrom!!.setText(jsonObject.getString("LeadFrom"))
//            txtLeadSource!!.setText(jsonObject.getString("LeadByName"))
//            txtCollectedBy!!.setText(jsonObject.getString("CollectedBy"))
//            txtCurrentAssignee!!.setText(jsonObject.getString("AssignedTo"))
//            txtStatus!!.setText(jsonObject.getString("CurrentStatus"))
//        }
//
//
//
//        txtRemarks!!.setText(jsonObject.getString("Remarks"))
//
//        imgClose.setOnClickListener {
//            dialog.dismiss()
//        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


}