package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
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
import android.widget.*
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
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class TicketReportDetailActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener
 {

    val TAG: String = "TicketReportDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var imback: ImageView? = null
    private var tv_ReportName: TextView? = null
    private var Type: TextView? = null
    private var report_date: TextView? = null
    private var ReportMode: String? = ""
    private var ID_Branch: String? = ""
    private var ID_Employee: String? = ""
    private var strFromdate: String? = ""
    private var strTodate: String? = ""
    private var ID_Product: String? = ""
    private var ID_NextAction: String? = ""
    private var ID_ActionType: String? = ""
    private var ID_Priority: String? = ""
    private var ID_Status: String? = ""
    private var GroupId: String? = ""
    private var ID_CollectedBy: String? = ""
    private var ID_AssignedEmployee: String? = ""
    private var ID_Category: String? = ""

    internal var ll_ActionList: LinearLayout? = null
    internal var ll_Summary: LinearLayout? = null
    internal var ll_FollowUpTicket: LinearLayout? = null
    internal var ll_NewListTicket: LinearLayout? = null
    internal var ll_StatusListTicket: LinearLayout? = null

    lateinit var actionListReportViewModel: ActionListTicketReportViewModel
    lateinit var detailedReportViewModel: DetailedReportViewModel
    lateinit var actionListReportArrayList: JSONArray
    var recyActionListReport: RecyclerView? = null
    var recycleSummary: RecyclerView? = null

    lateinit var followUpTicketReportViewModel: FollowUpTicketReportViewModel
    lateinit var followUpTicketReportArrayList: JSONArray
    var recyFollowUpTicketReport: RecyclerView? = null

    lateinit var newListTicketReportViewModel: NewListTicketReportViewModel
    lateinit var newListTicketReportArrayList: JSONArray
    var recyNewListTicketReport: RecyclerView? = null

    lateinit var statusListTicketReportViewModel: StatusListTicketReportViewModel
    lateinit var statusListTicketReportArrayList: JSONArray
    var recyStatusListTicketReport: RecyclerView? = null

    var followList = 0
    var newList = 0
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_ticket_report_detail)
        context = this@TicketReportDetailActivity

        actionListReportViewModel =
            ViewModelProvider(this).get(ActionListTicketReportViewModel::class.java)
        followUpTicketReportViewModel =
            ViewModelProvider(this).get(FollowUpTicketReportViewModel::class.java)
        newListTicketReportViewModel =
            ViewModelProvider(this).get(NewListTicketReportViewModel::class.java)
        statusListTicketReportViewModel =
            ViewModelProvider(this).get(StatusListTicketReportViewModel::class.java)
        detailedReportViewModel = ViewModelProvider(this).get(DetailedReportViewModel::class.java)

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
        if (getIntent().hasExtra("ID_CollectedBy")) {
            ID_CollectedBy = intent.getStringExtra("ID_CollectedBy")
        }
        if (getIntent().hasExtra("ID_AssignedEmployee")) {
            ID_AssignedEmployee = intent.getStringExtra("ID_AssignedEmployee")
        }
        if (getIntent().hasExtra("ID_Category")) {
            ID_Category = intent.getStringExtra("ID_Category")
        }


        ll_ActionList!!.visibility = View.GONE
        ll_Summary!!.visibility = View.GONE
        ll_FollowUpTicket!!.visibility = View.GONE
        ll_NewListTicket!!.visibility = View.GONE
        ll_StatusListTicket!!.visibility = View.GONE

        Log.e(TAG, "ReportMode   107   " + ReportMode+"\n"+ID_Category)

//        if (ReportMode.equals("1")){
////            ActionListT
//            getActionListTicketReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
//        }
        if (ReportMode.equals("2")) {
//            FollowUpTicket
            followList = 0
            getFollowUpTicketReport(
                ReportMode,
                ID_Branch,
                ID_Employee,
                strFromdate,
                strTodate,
                ID_Product,
                ID_NextAction,
                ID_ActionType,
                ID_Priority,
                ID_Status,
                GroupId
            )
        }

        if (ReportMode.equals("5")) {
//            NewListTicket
            newList = 0
            getNewListTicketReport(
                ReportMode,
                ID_Branch,
                ID_Employee,
                strFromdate,
                strTodate,
                ID_Product,
                ID_NextAction,
                ID_ActionType,
                ID_Priority,
                ID_Status,
                GroupId
            )
        }
        if (ReportMode.equals("6")) {
            newList = 0
            getDetailedReport(
                ReportMode,
                ID_Branch,
                ID_Employee,
                strFromdate,
                strTodate,
                ID_Product,
                ID_NextAction,
                ID_ActionType,
                ID_Priority,
                ID_Status,
                GroupId,
                ID_AssignedEmployee,
                ID_CollectedBy,
                ID_Category
            )

        }


//
//        if (ReportMode.equals("4")){
////            StatusList
//              getStatusListReport(ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
//
//        }


        //

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)

        Type = findViewById(R.id.Type)
        tv_ReportName = findViewById(R.id.tv_ReportName)
        ll_ActionList = findViewById(R.id.ll_ActionList)
        ll_Summary = findViewById(R.id.ll_Summary)
        ll_FollowUpTicket = findViewById(R.id.ll_FollowUpTicket)
        ll_NewListTicket = findViewById(R.id.ll_NewListTicket)
        ll_StatusListTicket = findViewById(R.id.ll_StatusListTicket)
        report_date = findViewById(R.id.report_date)
        recyActionListReport = findViewById(R.id.recyActionListReport)
        recycleSummary = findViewById(R.id.recycleSummary)
        recyFollowUpTicketReport = findViewById(R.id.recyFollowUpTicketReport)
        recyNewListTicketReport = findViewById(R.id.recyNewListTicketReport)
        recyStatusListTicketReport = findViewById(R.id.recyStatusListTicketReport)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }


    private fun getActionListTicketReport(
        ReportMode: String?,
        ID_Branch: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?
    ) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                actionListReportViewModel.getActionListTicketReport(
                    this,
                    ReportMode,
                    ID_Branch,
                    strFromdate,
                    strTodate,
                    ID_Product,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Status,
                    GroupId
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   1701   " + msg.length)
                            Log.e(TAG, "msg   1702   " + msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ActionListDetailsReport")
                                actionListReportArrayList = jobjt.getJSONArray("ActionList")
                                if (actionListReportArrayList.length() > 0) {

                                    ll_ActionList!!.visibility = View.VISIBLE
                                    try {
                                        val lLayout =
                                            GridLayoutManager(this@TicketReportDetailActivity, 1)
                                        recyActionListReport!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        // recyLeadGenReport!!.setHasFixedSize(true)
                                        val adapter = ActionListTicketReportAdapter(
                                            applicationContext,
                                            actionListReportArrayList
                                        )
                                        recyActionListReport!!.adapter = adapter
                                    } catch (e: Exception) {
                                        Log.e(TAG, "msg   1704   " + e.toString())
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }


    private fun getDetailedReport(
        ReportMode: String?,
        ID_Branch: String?,
        ID_Employee: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?,
        ID_AssignedEmployee: String?,
        ID_CollectedBy: String?,
        ID_Category: String?
    ) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                detailedReportViewModel.getDetailedReport(
                    this,
                    ReportMode,
                    ID_Branch,
                    strFromdate,
                    strTodate,
                    ID_Product,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Status,
                    GroupId, ID_AssignedEmployee, ID_CollectedBy, ID_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (laedGen == 0) {
                                laedGen++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   1701   " + msg.length)
                                Log.e(TAG, "msg   1702   " + msg)
                                Log.v("sfsdfsdfdsfdd","msg "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("SummaryWiseReport")
                                    actionListReportArrayList =
                                        jobjt.getJSONArray("SummaryLeadList")
                                    if (actionListReportArrayList.length() > 0) {

                                        Log.e(TAG, "msg   1703   " + actionListReportArrayList)
                                        var arrayFrom = strFromdate!!.split("-")
                                        var arrayTo = strTodate!!.split("-")
                                        var fromDate =
                                            arrayFrom[2] + "-" + arrayFrom[1] + "-" + arrayFrom[0]
                                        var toDate =
                                            arrayTo[2] + "-" + arrayTo[1] + "-" + arrayTo[0]
                                        Log.e(TAG, "msg   1703   " + actionListReportArrayList)
                                        if (GroupId.equals("5")) {
                                            Type?.setText("Employee")
                                        } else if (GroupId.equals("2")) {
                                            Type?.setText("Assigned")
                                        } else if (GroupId.equals("7")) {
                                            Type?.setText("Category")
                                        } else if (GroupId.equals("4")) {
                                            Type?.setText("Product")
                                        } else {
                                            Type?.setText("")
                                        }
                                        report_date!!.text =
                                            "Report between " + fromDate + " and " + toDate
                                        ll_Summary!!.visibility = View.VISIBLE
                                        try {
                                            val lLayout =
                                                GridLayoutManager(
                                                    this@TicketReportDetailActivity,
                                                    1
                                                )
                                            recycleSummary!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            Log.e(TAG,"PASSVALUE"+strFromdate+"\n"+strTodate+"\n"+ID_Category)
                                            val adapter = SummaryReportAdapter(
                                                applicationContext,
                                                actionListReportArrayList,strFromdate,
                                                        strTodate,
                                                        ID_Product,
                                                        ID_Category,
                                                        ID_Branch,
                                                        ID_Employee,
                                            )
                                            recycleSummary!!.adapter = adapter
                                        } catch (e: Exception) {
                                            Log.e(TAG, "msg   1704   " + e.toString())
                                        }
                                    }

                                } else {
                                    ll_Summary!!.visibility = View.GONE
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }
    }


    private fun getFollowUpTicketReport(
        ReportMode: String?,
        ID_Branch: String?,
        ID_Employee: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?
    ) {

//        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                followUpTicketReportViewModel.getFollowUpTicketReport(
                    this,
                    ReportMode,
                    ID_Branch,
                    ID_Employee,
                    strFromdate,
                    strTodate,
                    ID_Product,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Status,
                    GroupId
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (followList == 0) {
                                followList++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   2701   " + msg.length)
                                Log.e(TAG, "msg   2702   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("FollowUpListDetailsReport")
                                    followUpTicketReportArrayList =
                                        jobjt.getJSONArray("FollowUpListDetails")
                                    if (followUpTicketReportArrayList.length() > 0) {
                                        Log.e(TAG, "msg   2703   " + followUpTicketReportArrayList)
                                        ll_FollowUpTicket!!.visibility = View.VISIBLE
                                        report_date!!.visibility = View.VISIBLE
                                        var arrayFrom = strFromdate!!.split("-")
                                        var arrayTo = strTodate!!.split("-")

                                        Log.i("fgfdjkgh", "date 2=" + strFromdate)
                                        Log.i("fgfdjkgh", "date 1=" + arrayFrom[1])
                                        Log.i("fgfdjkgh", "date 0=" + arrayFrom[0])
                                        var fromDate =
                                            arrayFrom[2] + "-" + arrayFrom[1] + "-" + arrayFrom[0]
                                        var toDate =
                                            arrayTo[2] + "-" + arrayTo[1] + "-" + arrayTo[0]
                                        report_date!!.text =
                                            "Report between " + fromDate + " and " + toDate
                                        try {
                                            val lLayout = GridLayoutManager(
                                                this@TicketReportDetailActivity,
                                                1
                                            )
                                            recyFollowUpTicketReport!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            val adapter = FollowupTicketReportAdapter(
                                                applicationContext,
                                                followUpTicketReportArrayList
                                            )
                                            recyFollowUpTicketReport!!.adapter = adapter
                                            adapter.setClickListener(this@TicketReportDetailActivity)
                                        } catch (e: Exception) {
                                            Log.e(TAG, "msg   2704   " + e.toString())
                                        }


                                    }

                                } else {
                                    report_date!!.visibility = View.GONE
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
                            }


                        } else {
                            report_date!!.visibility = View.GONE
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }

    private fun getNewListTicketReport(
        ReportMode: String?,
        ID_Branch: String?,
        ID_Employee: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?
    ) {

        //  var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                newListTicketReportViewModel.getNewListTicketReport(
                    this,
                    ReportMode,
                    ID_Branch,
                    ID_Employee,
                    strFromdate,
                    strTodate,
                    ID_Product,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Status,
                    GroupId
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (newList == 0) {
                                    newList++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   344 1  " + msg.length)
                                    Log.e(TAG, "msg   3442   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("NewListDetailsReport")
                                        newListTicketReportArrayList =
                                            jobjt.getJSONArray("NewListDetails")
                                        if (newListTicketReportArrayList.length() > 0) {
                                            Log.e(
                                                TAG,
                                                "msg   3443   " + newListTicketReportArrayList
                                            )
                                            ll_NewListTicket!!.visibility = View.VISIBLE
                                            report_date!!.visibility = View.VISIBLE
                                            var arrayFrom = strFromdate!!.split("-")
                                            var arrayTo = strTodate!!.split("-")
                                            var fromDate =
                                                arrayFrom[2] + "-" + arrayFrom[1] + "-" + arrayFrom[0]
                                            var toDate =
                                                arrayTo[2] + "-" + arrayTo[1] + "-" + arrayTo[0]
                                            report_date!!.text =
                                                "Report between " + fromDate + " and " + toDate
                                            try {
                                                val lLayout = GridLayoutManager(
                                                    this@TicketReportDetailActivity,
                                                    1
                                                )
                                                recyNewListTicketReport!!.layoutManager =
                                                    lLayout as RecyclerView.LayoutManager?
                                                // recyLeadGenReport!!.setHasFixedSize(true)
                                                val adapter1 = NewListTicketReportAdapter(
                                                    applicationContext,
                                                    newListTicketReportArrayList
                                                )
                                                recyNewListTicketReport!!.adapter = adapter1
                                                adapter1.setClickListener(this@TicketReportDetailActivity)

                                            } catch (e: Exception) {
                                                Log.e(TAG, "msg   3444   " + e.toString())
                                            }

                                        }

                                    } else {
                                        report_date!!.visibility = View.GONE
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
                                }

                            } else {
                                report_date!!.visibility = View.GONE
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        } catch (e: Exception) {
                            report_date!!.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }

    private fun getStatusListReport(
        ReportMode: String?,
        ID_Branch: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?
    ) {

        var laedGen = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                statusListTicketReportViewModel.getStatusListReport(
                    this,
                    ReportMode,
                    ID_Branch,
                    strFromdate,
                    strTodate,
                    ID_Product,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Status,
                    GroupId
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {


                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   3961   " + msg.length)
                                Log.e(TAG, "msg   3962   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("StatusListDetailsReport")
                                    statusListTicketReportArrayList =
                                        jobjt.getJSONArray("StatusListDetails")
                                    if (statusListTicketReportArrayList.length() > 0) {
                                        Log.e(
                                            TAG,
                                            "msg   3963   " + statusListTicketReportArrayList
                                        )
                                        ll_StatusListTicket!!.visibility = View.VISIBLE
                                        try {
                                            val lLayout = GridLayoutManager(
                                                this@TicketReportDetailActivity,
                                                1
                                            )
                                            recyStatusListTicketReport!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            // recyLeadGenReport!!.setHasFixedSize(true)
                                            val adapter = StatusListTicketReportAdapter(
                                                applicationContext,
                                                statusListTicketReportArrayList
                                            )
                                            recyStatusListTicketReport!!.adapter = adapter
                                        } catch (e: Exception) {
                                            Log.e(TAG, "msg   3964   " + e.toString())
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }



    override fun onClick(position: Int, data: String) {
        Log.e(TAG, "newListClick   5091")
        if (data.equals("newListClick")) {
            Log.e(TAG, "newListClick   5091")
            openBottomSheetReport(newListTicketReportArrayList, position)
        }

        if (data.equals("followListClick")) {

            Log.e(TAG, "followListClick  5092")
            openBottomSheetReport(followUpTicketReportArrayList, position)

        }


    }


    private fun openBottomSheetReport(jsonArray: JSONArray, position: Int) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_report_followup, null)

        var jsonObject = jsonArray.getJSONObject(position)

        val ll_NewDisable = view.findViewById<LinearLayout>(R.id.ll_NewDisable)
        val ll_followDisable = view.findViewById<LinearLayout>(R.id.ll_followDisable)

        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val txtLeadNo = view.findViewById<TextView>(R.id.txtLeadNo)
        val txtLeadDate = view.findViewById<TextView>(R.id.txtLeadDate)
        val txtCustomer = view.findViewById<TextView>(R.id.txtCustomer)
        val txtProduct = view.findViewById<TextView>(R.id.txtProduct)

        val txtAction = view.findViewById<TextView>(R.id.txtAction)
        val txtActionType = view.findViewById<TextView>(R.id.txtActionType)
        val txtCommitedDate = view.findViewById<TextView>(R.id.txtCommitedDate)
        val txtAssignee = view.findViewById<TextView>(R.id.txtAssignee)
        val txtCompletedDate = view.findViewById<TextView>(R.id.txtCompletedDate)
        val txtDueDays = view.findViewById<TextView>(R.id.txtDueDays)

        val txtPriority = view.findViewById<TextView>(R.id.txtPriority)
        val txtLeadFrom = view.findViewById<TextView>(R.id.txtLeadFrom)
        val txtLeadSource = view.findViewById<TextView>(R.id.txtLeadSource)
        val txtCollectedBy = view.findViewById<TextView>(R.id.txtCollectedBy)
        val txtCurrentAssignee = view.findViewById<TextView>(R.id.txtCurrentAssignee)
        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)

        val txtRemarks = view.findViewById<TextView>(R.id.txtRemarks)

        txtLeadNo!!.setText(jsonObject.getString("LeadNo"))
        txtLeadDate!!.setText(jsonObject.getString("LeadDate"))
        txtCustomer!!.setText(jsonObject.getString("Customer"))
        txtProduct!!.setText(jsonObject.getString("Product"))

        if (ReportMode.equals("2")) {
            ll_NewDisable.visibility = View.GONE
            ll_followDisable.visibility = View.VISIBLE
            txtAction!!.setText(jsonObject.getString("NextAction"))
            txtActionType!!.setText(jsonObject.getString("FollowUpMethod"))
            txtCommitedDate!!.setText(jsonObject.getString("LeadDate"))
            txtAssignee!!.setText(jsonObject.getString("AssignedTo"))
            txtCompletedDate!!.setText(jsonObject.getString("CompletedDate"))
            txtDueDays!!.setText(jsonObject.getString("DueDays"))
        } else if (ReportMode.equals("5")) {
            ll_NewDisable.visibility = View.VISIBLE
            ll_followDisable.visibility = View.GONE

            txtPriority!!.setText(jsonObject.getString("Priority"))
            txtLeadFrom!!.setText(jsonObject.getString("LeadFrom"))
            txtLeadSource!!.setText(jsonObject.getString("LeadByName"))
            //txtCollectedBy!!.setText(jsonObject.getString("CollectedBy"))
            txtCollectedBy!!.setText(jsonObject.getString("CollectedBy"))
            txtCurrentAssignee!!.setText(jsonObject.getString("AssignedTo"))
            txtStatus!!.setText(jsonObject.getString("CurrentStatus"))
        }



        txtRemarks!!.setText(jsonObject.getString("Remarks"))

        imgClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}