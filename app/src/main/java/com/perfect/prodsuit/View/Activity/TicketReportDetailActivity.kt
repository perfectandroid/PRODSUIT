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
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ActionListTicketReportAdapter
import com.perfect.prodsuit.View.Adapter.FollowupTicketReportAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class TicketReportDetailActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "TicketReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var imback: ImageView? = null

    private var ReportMode:String?=""
    private var ID_Branch:String?=""
    private var strFromdate:String?=""
    private var strTodate:String?=""
    private var ID_Product:String?=""
    private var ID_NextAction:String?=""
    private var ID_ActionType:String?=""
    private var ID_Priority:String?=""
    private var ID_Status:String?=""
    private var GroupId:String?=""

    internal var ll_ActionList: LinearLayout? = null
    internal var ll_FollowUpTicket: LinearLayout? = null
    internal var ll_NewListTicket: LinearLayout? = null
    internal var ll_StatusListTicket: LinearLayout? = null

    lateinit var actionListReportViewModel: ActionListTicketReportViewModel
    lateinit var actionListReportArrayList : JSONArray
    var recyActionListReport  : RecyclerView? = null

    lateinit var followUpTicketReportViewModel: FollowUpTicketReportViewModel
    lateinit var followUpTicketReportArrayList : JSONArray
    var recyFollowUpTicketReport  : RecyclerView? = null

    lateinit var newListTicketReportViewModel: NewListTicketReportViewModel
    lateinit var newListTicketReportArrayList : JSONArray
    var recyNewListTicketReport  : RecyclerView? = null

    lateinit var statusListTicketReportViewModel: StatusListTicketReportViewModel
    lateinit var statusListTicketReportArrayList : JSONArray
    var recyStatusListTicketReport  : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_ticket_report_detail)
        context = this@TicketReportDetailActivity

        actionListReportViewModel = ViewModelProvider(this).get(ActionListTicketReportViewModel::class.java)
        followUpTicketReportViewModel = ViewModelProvider(this).get(FollowUpTicketReportViewModel::class.java)
        newListTicketReportViewModel = ViewModelProvider(this).get(NewListTicketReportViewModel::class.java)
        statusListTicketReportViewModel = ViewModelProvider(this).get(StatusListTicketReportViewModel::class.java)

        setRegViews()

        if (getIntent().hasExtra("ReportMode")) {
            ReportMode = intent.getStringExtra("ReportMode")
        }
        if (getIntent().hasExtra("ID_Branch")) {
            ID_Branch = intent.getStringExtra("ID_Branch")
        }
        if (getIntent().hasExtra("Fromdate")) {
            strFromdate = intent.getStringExtra("Fromdate")
        }
        if (getIntent().hasExtra("Todate")) {
            strTodate = intent.getStringExtra("Todate")
        }
        if (getIntent().hasExtra("ID_Product")) {
            ID_Product = intent.getStringExtra("ID_Product")
        }
        if (getIntent().hasExtra("ID_NextAction")) {
            ID_NextAction = intent.getStringExtra("ID_NextAction")
        }
        if (getIntent().hasExtra("ID_ActionType")) {
            ID_ActionType = intent.getStringExtra("ID_ActionType")
        }
        if (getIntent().hasExtra("ID_Priority")) {
            ID_Priority = intent.getStringExtra("ID_Priority")
        }
        if (getIntent().hasExtra("ID_Status")) {
            ID_Status = intent.getStringExtra("ID_Status")
        }
        if (getIntent().hasExtra("GroupId")) {
            GroupId = intent.getStringExtra("GroupId")
        }


        ll_ActionList!!.visibility = View.GONE
        ll_FollowUpTicket!!.visibility = View.GONE
        ll_NewListTicket!!.visibility = View.GONE
        ll_StatusListTicket!!.visibility = View.GONE

        Log.e(TAG,"ReportMode   107   "+ReportMode)

        if (ReportMode.equals("1")){
//            ActionListT
            getActionListTicketReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        }
        if (ReportMode.equals("1")){
//            FollowUpTicket
//            getFollowUpTicketReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        }

        if (ReportMode.equals("1")){
//            NewListTicket
            //   getNewListTicketReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        }

        if (ReportMode.equals("1")){
//            StatusList
            //  getStatusListReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)

        }



      //





    }



    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)

        ll_ActionList = findViewById(R.id.ll_ActionList)
        ll_FollowUpTicket = findViewById(R.id.ll_FollowUpTicket)
        ll_NewListTicket = findViewById(R.id.ll_NewListTicket)
        ll_StatusListTicket = findViewById(R.id.ll_StatusListTicket)

        recyActionListReport = findViewById(R.id.recyActionListReport)
        recyFollowUpTicketReport = findViewById(R.id.recyFollowUpTicketReport)
        recyNewListTicketReport = findViewById(R.id.recyNewListTicketReport)
        recyStatusListTicketReport = findViewById(R.id.recyStatusListTicketReport)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }


    private fun getActionListTicketReport(ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                          ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                actionListReportViewModel.getActionListTicketReport(this,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1701   "+msg.length)
                            Log.e(TAG,"msg   1702   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ActionListDetailsReport")
                                actionListReportArrayList = jobjt.getJSONArray("ActionList")
                                if (actionListReportArrayList.length()>0){
                                    Log.e(TAG,"msg   1703   "+actionListReportArrayList)
                                    ll_ActionList!!.visibility = View.VISIBLE
                                    try {
                                        val lLayout = GridLayoutManager(this@TicketReportDetailActivity, 1)
                                        recyActionListReport!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        // recyLeadGenReport!!.setHasFixedSize(true)
                                        val adapter = ActionListTicketReportAdapter(applicationContext, actionListReportArrayList)
                                        recyActionListReport!!.adapter = adapter
                                    }catch (e: Exception){
                                        Log.e(TAG,"msg   1704   "+e.toString())
                                    }


                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportDetailActivity,
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


    private fun getFollowUpTicketReport(ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                          ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                followUpTicketReportViewModel.getFollowUpTicketReport(this,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2461   "+msg.length)
                            Log.e(TAG,"msg   2462   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("LeadGenerateReport")
//                                followUpTicketReportArrayList = jobjt.getJSONArray("LeadGenerateReportList")
//                                if (followUpTicketReportArrayList.length()>0){
//                                    Log.e(TAG,"msg   2463   "+followUpTicketReportArrayList)
//                                    ll_FollowUpTicket!!.visibility = View.VISIBLE
//                                    try {
//                                        val lLayout = GridLayoutManager(this@TicketReportDetailActivity, 1)
//                                        recyFollowUpTicketReport!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        // recyLeadGenReport!!.setHasFixedSize(true)
//                                        val adapter = FollowupTicketReportAdapter(applicationContext, followUpTicketReportArrayList)
//                                        recyFollowUpTicketReport!!.adapter = adapter
//                                    }catch (e: Exception){
//                                        Log.e(TAG,"msg   2464   "+e.toString())
//                                    }
//
//
//                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportDetailActivity,
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

    private fun getNewListTicketReport(ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                        ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                newListTicketReportViewModel.getNewListTicketReport(this,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2461   "+msg.length)
                            Log.e(TAG,"msg   2462   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("LeadGenerateReport")
//                                newListTicketReportArrayList = jobjt.getJSONArray("LeadGenerateReportList")
//                                if (newListTicketReportArrayList.length()>0){
//                                    Log.e(TAG,"msg   2463   "+newListTicketReportArrayList)
//                                    ll_NewListTicket!!.visibility = View.VISIBLE
//                                    try {
//                                        val lLayout = GridLayoutManager(this@TicketReportDetailActivity, 1)
//                                        recyNewListTicketReport!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        // recyLeadGenReport!!.setHasFixedSize(true)
//                                        val adapter = LeadGenerateReportAdapter(applicationContext, newListTicketReportArrayList)
//                                        recyNewListTicketReport!!.adapter = adapter
//                                    }catch (e: Exception){
//                                        Log.e(TAG,"msg   2464   "+e.toString())
//                                    }
//
//
//                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportDetailActivity,
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

    private fun getStatusListReport(ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                       ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                statusListTicketReportViewModel.getStatusListReport(this,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   3961   "+msg.length)
                            Log.e(TAG,"msg   3962   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("LeadGenerateReport")
//                                statusListTicketReportArrayList = jobjt.getJSONArray("LeadGenerateReportList")
//                                if (statusListTicketReportArrayList.length()>0){
//                                    Log.e(TAG,"msg   3963   "+statusListTicketReportArrayList)
//                                    ll_StatusListTicket!!.visibility = View.VISIBLE
//                                    try {
//                                        val lLayout = GridLayoutManager(this@TicketReportDetailActivity, 1)
//                                        recyStatusListTicketReport!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        // recyLeadGenReport!!.setHasFixedSize(true)
//                                        val adapter = LeadGenerateReportAdapter(applicationContext, statusListTicketReportArrayList)
//                                        recyStatusListTicketReport!!.adapter = adapter
//                                    }catch (e: Exception){
//                                        Log.e(TAG,"msg   3964   "+e.toString())
//                                    }
//
//
//                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportDetailActivity,
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


}