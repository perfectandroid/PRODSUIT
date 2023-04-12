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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmiListAdapter
import com.perfect.prodsuit.View.Adapter.NewListTicketReportAdapter
import com.perfect.prodsuit.View.Adapter.ServiceNewListReportAdapter
import com.perfect.prodsuit.Viewmodel.EmiListViewModel
import com.perfect.prodsuit.Viewmodel.FollowUpTicketReportViewModel
import com.perfect.prodsuit.Viewmodel.ServiceNewListReportViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceReportDetailActivity : AppCompatActivity() , View.OnClickListener{

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_report_detail)
        context = this@ServiceReportDetailActivity

        serviceNewListReportViewModel = ViewModelProvider(this).get(ServiceNewListReportViewModel::class.java)

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
        }
        if (ReportMode.equals("6")){
            //Service List
            serviceList = 0
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
                                            val adapter1 = ServiceNewListReportAdapter(applicationContext, newListReportArrayList)
                                            recyNewList!!.adapter = adapter1
                                            // adapter1.setClickListener(this@ServiceReportDetailActivity)

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
}