package com.perfect.prodsuit.View.Activity

import android.app.*
import android.content.Context
import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.tabs.TabLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimalRemover
import com.perfect.prodsuit.Model.ScoreBar
import com.perfect.prodsuit.Model.ScorePie
import com.perfect.prodsuit.Model.ScorePieProject
import com.perfect.prodsuit.Model.ScorePieSevice
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class DashBoardActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = "LeadNextActionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int = 0
    internal var month: Int = 0
    internal var day: Int = 0
    internal var hr: Int = 0
    internal var min: Int = 0
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    //  private var chipNavigationBar: ChipNavigationBar? = null

    lateinit var chartLineArrayList: JSONArray
    var lineData: LineData? = null
    var entryList: List<Map.Entry<*, *>> = ArrayList()

//    private var lineChart: LineChart? = null
//    private var scoreListLine = ArrayList<ScoreLine>()
//    lateinit var leadDashViewModel: LeadDashViewModel
//    lateinit var leadDashArrayList : JSONArray


    //Barchart
    private lateinit var barChart: BarChart
    private var scoreListBar = ArrayList<ScoreBar>()
    lateinit var chartBarArrayList: JSONArray
    lateinit var leadStagesDashViewModel: LeadStagesDashViewModel
    lateinit var leadStagesDashArrayList: JSONArray


    //  Lead  PiChart
    private lateinit var pieChart: PieChart
    private var scoreListPie = ArrayList<ScorePie>()
    lateinit var leadStatusDashViewModel: LeadStatusDashViewModel
    lateinit var leadStatusDashArrayList: JSONArray


    //    PiChartLead
    private lateinit var pieChartLead: PieChart
    private var scoreListPieLead = ArrayList<ScorePie>()
    lateinit var leadDashViewModel: LeadDashViewModel
    lateinit var leadDashArrayList: JSONArray


    //  Service  PiChart
    private lateinit var pieChartServices: PieChart
    private var scoreListPieServices = ArrayList<ScorePieSevice>()
    lateinit var serviceDashViewModel: ServiceDashViewModel
    lateinit var serviceDashArrayList: JSONArray

    //  Service Project PiChart
    private lateinit var pieChartProject: PieChart
    private var scoreListPieProject = ArrayList<ScorePieProject>()
    lateinit var serviceStatusDashViewModel: ServiceStatusDashViewModel
    lateinit var serviceStatusDashArrayList: JSONArray
    var service_count: TextView? = null
    var tv_leadTotal: TextView? = null
    var tv_leadStatusTotal: TextView? = null
    var tv_leadStageTotal: TextView? = null

    var tabLayout: TabLayout? = null
    var ll_leads: LinearLayout? = null
    var lltab: LinearLayout? = null
    var ll_service: LinearLayout? = null
    var ll_collection: LinearLayout? = null

    var dashBoardEmpty: LinearLayout? = null

    var recycLineChart: RecyclerView? = null
    var recycBarChart: RecyclerView? = null
    var recycPieChart: RecyclerView? = null

    var recycLineChartServices: RecyclerView? = null
    var recycPieChartProject: RecyclerView? = null

    //..........
    var iLead = ""
    var iService = ""
    var servicesecond: LinearLayout? = null
    var leadfirst: LinearLayout? = null

    var mainpage: LinearLayout? = null
    var firstpage: LinearLayout? = null
    var secondpage: LinearLayout? = null

    var mainHeadingDash: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_dash_board)
        context = this@DashBoardActivity

        leadDashViewModel = ViewModelProvider(this).get(LeadDashViewModel::class.java)
        leadStatusDashViewModel = ViewModelProvider(this).get(LeadStatusDashViewModel::class.java)
        leadStagesDashViewModel = ViewModelProvider(this).get(LeadStagesDashViewModel::class.java)

        serviceDashViewModel = ViewModelProvider(this).get(ServiceDashViewModel::class.java)
        serviceStatusDashViewModel =
            ViewModelProvider(this).get(ServiceStatusDashViewModel::class.java)

        setRegViews()
        //  bottombarnav()
        //    addTabItem()
        addDashBoardMenu()


//        setLineChart()
//        setBarchart()  //working
//        setPieChart()

    }

    private fun addDashBoardMenu() {
        val DashBordMenu = context.getSharedPreferences(Config.SHARED_PREF54, 0)
        val jsonObj = JSONObject(DashBordMenu.getString("ModuleList", ""))
        iLead = jsonObj!!.getString("LEAD")
        iService = jsonObj!!.getString("SERVICE")
        Log.i("rtretr", "iLead =" + iLead)
        Log.i("rtretr", "iService=" + iService)

        if (iLead.equals("true")) {
            leadfirst!!.visibility = View.VISIBLE
        }
        if (iService.equals("true")) {

            servicesecond!!.visibility = View.VISIBLE
        }
        if (iLead.equals("false") && iService.equals("false")) {


            val builder = AlertDialog.Builder(
                this@DashBoardActivity,
                R.style.MyDialogTheme
            )
            builder.setMessage("No Data Found")
            builder.setPositiveButton("OK") { dialogInterface, which ->
                finish()


            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } else {


            if (iLead.equals("true")) {
                Log.i("responseCheckApi", "leadsAPi")
                leadfirst!!.visibility = View.VISIBLE
            } else if (iService.equals("true")) {
                Log.i("responseCheckApi", "ServicesAPi")
                servicesecond!!.visibility = View.VISIBLE
            }

        }
    }


    private fun setRegViews() {
        mainHeadingDash = findViewById<TextView>(R.id.mainHeadingDash)
        mainpage = findViewById<LinearLayout>(R.id.mainpage)
        firstpage = findViewById<LinearLayout>(R.id.firstpage)
        secondpage = findViewById<LinearLayout>(R.id.secondpage)

        servicesecond = findViewById<LinearLayout>(R.id.servicesecond1)
        leadfirst = findViewById<LinearLayout>(R.id.leadfirst1)

        leadfirst!!.setOnClickListener(this)
        servicesecond!!.setOnClickListener(this)

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        // lineChart = findViewById<LineChart>(R.id.chart1);

        tabLayout = findViewById<TabLayout>(R.id.tabLayout);
        ll_leads = findViewById<LinearLayout>(R.id.ll_leads);
        lltab = findViewById<LinearLayout>(R.id.lltab);
        ll_service = findViewById<LinearLayout>(R.id.ll_service);
        ll_collection = findViewById<LinearLayout>(R.id.ll_collection);
        dashBoardEmpty = findViewById<LinearLayout>(R.id.dashBoardEmpty);

        barChart = findViewById<BarChart>(R.id.barChart);
        pieChart = findViewById<PieChart>(R.id.pieChart);
        pieChartLead = findViewById<PieChart>(R.id.pieChartLeads);

        recycLineChart = findViewById<RecyclerView>(R.id.recycLineChart)
        recycBarChart = findViewById<RecyclerView>(R.id.recycBarChart)
        recycPieChart = findViewById<RecyclerView>(R.id.recycPieChart)

        pieChartServices = findViewById<PieChart>(R.id.pieChartServices);
        pieChartProject = findViewById<PieChart>(R.id.pieChartProject);
        recycLineChartServices = findViewById<RecyclerView>(R.id.recycLineChartServices)
        recycPieChartProject = findViewById<RecyclerView>(R.id.recycPieChartProject)


        tv_leadTotal = findViewById<TextView>(R.id.tv_leadTotal)
        tv_leadStatusTotal = findViewById<TextView>(R.id.tv_leadStatusTotal)
        tv_leadStageTotal = findViewById<TextView>(R.id.tv_leadStageTotal)

        service_count = findViewById<TextView>(R.id.service_count)


    }

    private fun addTabItem() {
        //.........................314400
        val DashBordMenu = context.getSharedPreferences(Config.SHARED_PREF54, 0)
        val jsonObj = JSONObject(DashBordMenu.getString("ModuleList", ""))
        var iLead = jsonObj!!.getString("LEAD")
        var iService = jsonObj!!.getString("SERVICE")


        Log.i("response2211", "check =====" + jsonObj.toString())
        Log.i("response2211", "check lead=" + iLead)
        Log.i("response2211", "check Service=" + iService)

        if (iLead.equals("false") && iService.equals("false")) {
            dashBoardEmpty!!.visibility = View.VISIBLE

//            Toast.makeText(applicationContext, "No Data..", Toast.LENGTH_LONG)
//                .show()
            ll_service!!.visibility = View.GONE
            lltab!!.visibility = View.GONE
        }

        if (iLead.equals("true") && iService.equals("true")) {
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Leads"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Services"))

            // tabLayout!!.addTab(tabLayout!!.newTab().setText("Leads"))

            barChart!!.clear()
            pieChart!!.clear()
            pieChartLead!!.clear()

            recycLineChart!!.adapter = null
            recycBarChart!!.adapter = null
            recycPieChart!!.adapter = null

            pieChartProject!!.clear()
            recycPieChartProject!!.adapter = null

            ll_leads!!.visibility = View.VISIBLE
            ll_service!!.visibility = View.GONE
            ll_collection!!.visibility = View.GONE



            getLeadsDashBoard()
            getLeadStatusDashBoard()
            getLeadStagesDashBoard()
        } else {
            if (iLead.equals("true")) {
                tabLayout!!.addTab(tabLayout!!.newTab().setText("Leads"))

                barChart!!.clear()
                pieChart!!.clear()
                pieChartLead!!.clear()

                recycLineChart!!.adapter = null
                recycBarChart!!.adapter = null
                recycPieChart!!.adapter = null

                pieChartProject!!.clear()
                recycPieChartProject!!.adapter = null

                ll_leads!!.visibility = View.VISIBLE
                ll_service!!.visibility = View.GONE
                ll_collection!!.visibility = View.GONE



                getLeadsDashBoard()
                getLeadStatusDashBoard()
                getLeadStagesDashBoard()
            } else if (iService.equals("true")) {
                tabLayout!!.addTab(tabLayout!!.newTab().setText("Services"))

                ll_leads!!.visibility = View.GONE
                ll_service!!.visibility = View.VISIBLE
                ll_collection!!.visibility = View.GONE
//.............
                barChart!!.clear()
                // pieChart!!.clear()
                // pieChartLead!!.clear()
                pieChartServices.clear()
                pieChartProject.clear()
                recycLineChartServices!!.adapter = null
                recycPieChartProject!!.adapter = null
                //...............

                getServiceDashBoard()
                getServiceStatusDashBoard()
            }
        }


        //................................

//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Leads"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Services"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Collection"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE

//        tabLayout!!.getTabAt(0)?.orCreateBadge?.number = 5
//        tabLayout!!.getTabAt(1)?.orCreateBadge?.number = 8
//        tabLayout!!.getTabAt(2)?.orCreateBadge?.number = 10
//..............


//        barChart!!.clear()
//        pieChart!!.clear()
//        pieChartLead!!.clear()
//
//        recycLineChart!!.adapter  =null
//        recycBarChart!!.adapter  =null
//        recycPieChart!!.adapter  =null
//
//        pieChartProject!!.clear()
//        recycPieChartProject!!.adapter  =null
//
//        ll_leads!!.visibility = View.VISIBLE
//        ll_service!!.visibility = View.GONE
//        ll_collection!!.visibility = View.GONE
//
//
//
//        getLeadsDashBoard()
//        getLeadStatusDashBoard()
//        getLeadStagesDashBoard()

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG, "onTabSelected  389  " + tab.position)
                Log.i("response2211yy", "tab pos=" + tab.position)
                if (tab.position == 0) {
                    Log.e(TAG, "onTabSelected  3891  " + tab.position)
                    Log.i("response2211yy", "tab pos=" + tab.position)





                    pieChartProject!!.clear()
                    recycPieChartProject!!.adapter = null



                    barChart!!.clear()
                    pieChart!!.clear()
                    pieChartLead!!.clear()

                    recycLineChart!!.adapter = null
                    recycBarChart!!.adapter = null
                    recycPieChart!!.adapter = null

                    ll_leads!!.visibility = View.VISIBLE
                    ll_service!!.visibility = View.GONE
                    ll_collection!!.visibility = View.GONE
                    getLeadsDashBoard()
                    getLeadStatusDashBoard()
                    getLeadStagesDashBoard()

                }
                if (tab.position == 1) {
                    Log.e(TAG, "onTabSelected  3892  " + tab.position)
                    Log.i("response221122", "tab pos=" + tab.position)

                    ll_leads!!.visibility = View.GONE
                    ll_service!!.visibility = View.VISIBLE
                    ll_collection!!.visibility = View.GONE
//.............
                    barChart!!.clear()
                    // pieChart!!.clear()
                    // pieChartLead!!.clear()
                    pieChartServices.clear()
                    pieChartProject.clear()
                    recycLineChartServices!!.adapter = null
                    recycPieChartProject!!.adapter = null
                    //...............

                    getServiceDashBoard()
                    getServiceStatusDashBoard()

                }
                if (tab.position == 2) {
                    Log.e(TAG, "onTabSelected  3893  " + tab.position)
                    Log.i("response221122", "tab pos=" + tab.position)
                    ll_leads!!.visibility = View.GONE
                    ll_service!!.visibility = View.GONE
                    ll_collection!!.visibility = View.VISIBLE

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG, "onTabUnselected  162  " + tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG, "onTabReselected  165  " + tab.position)
            }
        })
    }


    private fun getLeadsDashBoard() {
        var leadDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadDashViewModel.getLeadDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   100   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    Log.v("asdasdssss", "in")
                                    // val ss = "[{\"Count\": 10,\"Fileds\": \"Hot\"},{\"Count\": 25,\"Fileds\": \"Cool\"},{\"Count\": 55,\"Fileds\": \"Warm\"}]"
                                    //  chartLineArrayList = JSONArray(ss)

                                    val jobjt = jObject.getJSONObject("LeadsDashBoardDetails")
                                    leadDashArrayList =
                                        jobjt.getJSONArray("LeadsDashBoardDetailsList")
                                    Log.v("asdasdssss", "size  " + leadDashArrayList.length())
                                    tv_leadTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  125   " + leadDashArrayList)

                                    // setLineChart()
                                    setPieChartLead()
//                                    val recycLineChart =
//                                        findViewById(R.id.recycLineChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                    recycLineChart!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter =
                                        LineChartAdapter(this@DashBoardActivity, leadDashArrayList)
                                    recycLineChart!!.adapter = adapter
                                    //  adapter.setClickListener(this@DashBoardActivity)


                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@DashBoardActivity,
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
                        } catch (e: java.lang.Exception) {
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }


    }

    private fun getLeadStatusDashBoard() {
        var leadStatusDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadStatusDashViewModel.getLeadStatusDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   190   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("LeadsDashBoardDetails")
                                    leadStatusDashArrayList =
                                        jobjt.getJSONArray("LeadsDashBoardDetailsList")
                                    tv_leadStatusTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  125   " + leadStatusDashArrayList)

                                    setPieChart()
//                                    val recycBarChart =
//                                        findViewById(R.id.recycBarChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                    recycBarChart!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = BarChartAdapter(
                                        this@DashBoardActivity,
                                        leadStatusDashArrayList
                                    )
                                    recycBarChart!!.adapter = adapter

//                                val jobjt = jObject.getJSONObject("FollowUpActionDetails")
//                                followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
//                                if (followUpActionArrayList.length()>0){
//                                    if (followUpAction == 0){
//                                        followUpAction++
//                                        followUpActionPopup(followUpActionArrayList)
//                                    }
//
//                                }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@DashBoardActivity,
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
                            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show()
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

    private fun getLeadStagesDashBoard() {
        var leadStagesDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadStagesDashViewModel.getLeadStagesDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   190   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("LeadsDashBoardDetails")
                                    leadStagesDashArrayList =
                                        jobjt.getJSONArray("LeadsDashBoardDetailsList")
                                    tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  264   " + leadStagesDashArrayList)

                                    // setPieChart()
                                    setBarchart()
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                    recycPieChart!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = PieChartAdapter(
                                        this@DashBoardActivity,
                                        leadStagesDashArrayList
                                    )
                                    recycPieChart!!.adapter = adapter

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@DashBoardActivity,
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
                            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show()
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


    private fun getServiceDashBoard() {
//        var leadDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceDashViewModel.getServiceDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   100   " + msg)
                                Log.i("response1122", "MSG===" + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    Log.v("asdasdssss", "in")
                                    val jobjt = jObject.getJSONObject("ServiceDashBoardDetails")
                                    val count = jobjt.getString("TotalCount")
                                    Log.i(
                                        "response112233",
                                        "count===" + jobjt.getString("TotalCount")
                                    )

                                    //  val df = DecimalFormat("#")
                                    // println("Foobar: ${df.format(100.10)}")
                                    //  service_count!!.setText(df.format(jobjt.getString("TotalCount").toInt()))

                                    serviceDashArrayList = jobjt.getJSONArray("ServiceStages")
                                    Log.v("asdasdssss", "size  " + serviceDashArrayList.length())
                                    Log.e(TAG, "array  125   " + serviceDashArrayList)

                                    Log.i("response1122", "size===" + serviceDashArrayList.length())
                                    Log.i("response1122", "serviceArray===" + serviceDashArrayList)

//                                    setPieChartLead()
                                    setPieChartService()

////                                    val recycLineChart =
////                                        findViewById(R.id.recycLineChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                    recycLineChartServices!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = PieChartServiceAdapter(
                                        this@DashBoardActivity,
                                        serviceDashArrayList
                                    )
                                    recycLineChartServices!!.adapter = adapter
                                    //  adapter.setClickListener(this@DashBoardActivity)


                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@DashBoardActivity,
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
                        } catch (e: java.lang.Exception) {
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setPieChartService() {
        Log.v("asdasdssss", "setPieChartLead  ")
//        https://intensecoder.com/piechart-tutorial-using-mpandroidchart-in-kotlin/

        scoreListPieServices.clear()
        scoreListPieServices = getScoreListService()
        Log.v("asdasdssss", "size  " + scoreListPieServices.size)


        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "%"
        pieChartServices.setUsePercentValues(false)


        pieChartServices.description.text = ""
        pieChartServices.isDrawHoleEnabled = true
        pieChartServices.setTouchEnabled(false)
        pieChartServices.setDrawEntryLabels(false)
        //adding padding
        // pieChartLead.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChartServices.setUsePercentValues(false)
        pieChartServices.isRotationEnabled = true
        pieChartServices.setRotationAngle(0f)
        pieChartServices.animateY(1400, Easing.EaseInOutQuad)
        pieChartServices.setDrawEntryLabels(false)
        pieChartServices.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChartServices.legend.isWordWrapEnabled = true
        val typeAmountMap: MutableMap<String, Float> = HashMap()

        for (i in 0 until scoreListPieServices.size) {
            val score = scoreListPieServices[i]
            Log.v("dsfsdfd333", "ss  " + score.Piescore)
            Log.e(TAG, "Piescore  594   " + score.Piescore.toFloat())
            typeAmountMap[""] = score.Piescore
            pieEntries.add(PieEntry(score.Piescore.toFloat(), "%"))

        }

        val colorsStage: ArrayList<Int> = ArrayList()
        colorsStage.add(resources.getColor(R.color.line_color1))
        colorsStage.add(resources.getColor(R.color.line_color2))
        colorsStage.add(resources.getColor(R.color.line_color3))

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet!!.valueFormatter = PercentFormatter()
        pieDataSet.valueTextSize = 12f
        pieDataSet.setColors(colorsStage)
        pieDataSet.setValueFormatter(DecimalRemover())
        val pieData = PieData(pieDataSet)
        // pieData.setValueFormatter(PercentFormatter())
        //    pieData.setValueFormatter(DecimalRemover(DecimalFormat("########")))
        pieData.setDrawValues(true)

        val l: Legend = pieChartServices.getLegend()
        l.isEnabled = false

        pieChartServices.data = pieData
        pieChartServices.setOnClickListener(View.OnClickListener {
            ShowEnalargeGraph2(pieEntries, 1)
        })

        pieChartServices.invalidate()
    }

    private fun getScoreListService(): ArrayList<ScorePieSevice> {

        for (i in 0 until serviceDashArrayList.length()) {
            //apply your logic
            var jsonObject = serviceDashArrayList.getJSONObject(i)
            Log.v("asdasdssss", "size2  " + jsonObject.getString("Count"))
            Log.e(TAG, "422  Count   " + jsonObject.getString("Count"))
            Log.i("response1122", "count=" + jsonObject.getString("Count"))
            scoreListPieServices.add(ScorePieSevice("", jsonObject.getString("Value").toFloat()))
        }

        return scoreListPieServices
    }


    private fun getServiceStatusDashBoard() {
        var leadStatusDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceStatusDashViewModel.getServiceStatusDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   503   " + msg)
                                Log.i("response112211", "MSG===" + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("ServiceDashBoardDetails")
                                    serviceStatusDashArrayList =
                                        jobjt.getJSONArray("Services")
//                                    tv_leadStatusTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  5032   " + serviceStatusDashArrayList)
//
                                    setPieChartProject()
////                                    val recycBarChart =
////                                        findViewById(R.id.recycBarChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                    recycPieChartProject!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = BarChartServiceAdapter(
                                        this@DashBoardActivity,
                                        serviceStatusDashArrayList
                                    )
                                    recycPieChartProject!!.adapter = adapter

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@DashBoardActivity,
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
                            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show()
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


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                onBackPressed()
            }
            R.id.leadfirst1 -> {
                Config.disableClick(v)
                mainHeadingDash!!.setText("LEAD")
                mainpage!!.visibility = View.GONE
                firstpage!!.visibility = View.VISIBLE
                secondpage!!.visibility = View.GONE

                barChart!!.clear()
                pieChart!!.clear()
                pieChartLead!!.clear()

                recycLineChart!!.adapter = null
                recycBarChart!!.adapter = null
                recycPieChart!!.adapter = null

                pieChartProject!!.clear()
                recycPieChartProject!!.adapter = null

//                ll_leads!!.visibility = View.VISIBLE
//                ll_service!!.visibility = View.GONE
//                ll_collection!!.visibility = View.GONE



                getLeadsDashBoard()
                getLeadStatusDashBoard()
                getLeadStagesDashBoard()



            }
            R.id.servicesecond1 -> {
                Config.disableClick(v)
                mainpage!!.visibility = View.GONE
                firstpage!!.visibility = View.GONE
                secondpage!!.visibility = View.VISIBLE

                mainHeadingDash!!.setText("SERVICES")
//                ll_leads!!.visibility = View.GONE
//                ll_service!!.visibility = View.VISIBLE
//                ll_collection!!.visibility = View.GONE
//.............
                barChart!!.clear()
                // pieChart!!.clear()
                // pieChartLead!!.clear()
                pieChartServices.clear()
                pieChartProject.clear()
                recycLineChartServices!!.adapter = null
                recycPieChartProject!!.adapter = null
                //...............

                getServiceDashBoard()
                getServiceStatusDashBoard()
            }

        }
    }


    /* private fun setLineChart() {

 //        https://intensecoder.com/line-chart-tutorial-using-mpandroidchart-in-kotlin/


         lineChart!!.axisLeft.setDrawGridLines(false)
         val xAxis: XAxis = lineChart!!.xAxis
         xAxis.setDrawGridLines(false)
         xAxis.setDrawAxisLine(false)

         //remove right y-axis
         lineChart!!.axisRight.isEnabled = false

         //remove legend
         lineChart!!.legend.isEnabled = false


         //remove description label
         lineChart!!.description.isEnabled = false

         lineChart!!.setScaleEnabled(false)


         //add animation
         lineChart!!.animateX(1000, Easing.EaseInSine)

         // to draw label on xAxis
         xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
         xAxis.valueFormatter = MyAxisFormatterLine()
         xAxis.setDrawLabels(true)
         xAxis.granularity = 1f
         xAxis.labelRotationAngle = +90f
         xAxis.textSize = 15f
         xAxis.textColor = Color.BLACK

         ///////////////


         val entries1: ArrayList<Entry> = ArrayList()
         scoreListLine.clear()
         scoreListLine = getScoreList1()
         Log.e(TAG,"scoreListLine  281    "+scoreListLine)

         //you can replace this data object with  your custom object
         for (i in scoreListLine.indices) {
             val score1 = scoreListLine[i]
             entries1.add(Entry(i.toFloat(), score1.Linescore.toFloat()))
             Log.e(TAG,"Linename 281  "+score1.Linename)
         }

 //        val colors: ArrayList<Int> = ArrayList()
 //        colors.add(Color.parseColor("#676666"))
 //        colors.add(Color.parseColor("#E91E1E"))
 //        colors.add(Color.parseColor("#4CAF50"))


         val colors: ArrayList<Int> = ArrayList()
         colors.add(resources.getColor(R.color.line_color1))
         colors.add(resources.getColor(R.color.line_color2))
         colors.add(resources.getColor(R.color.line_color3))


         val lineDataSet = LineDataSet(entries1, "")
         lineDataSet.setCircleColor(Color.RED)
         lineDataSet.setDrawCircleHole(true)
        // lineDataSet.colors = colors
         lineDataSet.setCircleSize(8f);
         lineDataSet.circleColors = colors
         lineDataSet.disableDashedLine()
         lineDataSet.circleHoleRadius = 2f
         lineDataSet.setValueFormatter(DecimalRemover())
       //  lineDataSet.enableDashedLine(20f,0f,0f)
         val data = LineData(lineDataSet)
         data.setValueTextSize(12f)
         data.setValueTextColor(Color.BLACK)
         lineChart!!.data = data



         lineChart!!.invalidate()


     }*/

    private fun setBarchart() {
//        https://intensecoder.com/bar-chart-tutorial-in-android-using-kotlin/
        scoreListBar.clear()
        scoreListBar = getScoreList()

        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)


        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false
        barChart!!.setScaleEnabled(false)
        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(1000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatterBar()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.WHITE


//        barChart.legend.textSize = 15f
//        barChart.legend.textColor = Color.RED

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))
        colors.add(resources.getColor(R.color.leadstages_color3))

        /////////////////////

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in scoreListBar.indices) {
            val score = scoreListBar[i]
            entries.add(BarEntry(i.toFloat(), score.Barscore.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)  //314400

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        barChart.data = data
        barChart.setOnClickListener(View.OnClickListener {
            ShowEnalargeGraphBar(entries)
        })


        barChart.invalidate()

    }

    private fun getScoreList(): ArrayList<ScoreBar> {


        //   chartBarArrayList

//        scoreListBar.add(Score("John", 56))
//        scoreListBar.add(Score("Rey", 75))
//        scoreListBar.add(Score("Steve", 85))
//        scoreListBar.add(Score("Kevin", 45))
//        scoreListBar.add(Score("Jeff", 63))

        for (i in 0 until leadStagesDashArrayList.length()) {
            //apply your logic
            var jsonObject = leadStagesDashArrayList.getJSONObject(i)
            Log.e(TAG, "422  Count   " + jsonObject.getString("Count"))
            scoreListBar.add(ScoreBar("", jsonObject.getString("Count").toFloat().toInt()))
        }

        return scoreListBar
    }

    /* private fun getScoreList1(): ArrayList<ScoreLine> {


 //        scoreListLine.add(ScoreLine("", 10))
 //        scoreListLine.add(ScoreLine("", 45))
 //        scoreListLine.add(ScoreLine("", 55))

         for (i in 0 until leadDashArrayList.length()) {
             //apply your logic
             var jsonObject = leadDashArrayList.getJSONObject(i)
             Log.e(TAG,"404  Count   "+jsonObject.getString("Count"))
             scoreListLine.add(ScoreLine("", jsonObject.getString("Count").toInt()))
         }

 //        scoreList.add(Score("HOT", 10))
 //        scoreList.add(Score("COOL", 45))
 //        scoreList.add(Score("WARM", 55))


         return scoreListLine
     }*/

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

//        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//            val index = value.toInt()
//            Log.d("TAG", "getAxisLabel: index $index")
//            return if (index < scoreList.size) {
//                scoreList[index].name
//            } else {
//                ""
//            }
//        }
    }

    /* inner class MyAxisFormatterLine : IndexAxisValueFormatter() {

         override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                 val index = value.toInt()
             Log.d("TAG", "getAxisLabel: index $index")
             return if (index < scoreListLine.size) {
                 scoreListLine[index].Linename
             } else {
                 ""
             }
         }
     }*/

    inner class MyAxisFormatterBar : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < scoreListBar.size) {
                scoreListBar[index].Barname
            } else {
                ""
            }
        }
    }


    private fun setPieChart() {
        scoreListPie.clear()
        scoreListPie = getScoreList2()
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "%"
        pieChart.setUsePercentValues(false)
        pieChart.description.text = ""
        pieChart.isDrawHoleEnabled = true
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)

        //adding padding
//        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        //pieChart.setUsePercentValues(false)
//        val layout: LinearLayout = findViewById(R.id.id_lin2)
//        val width: Int = layout.getWidth()
//        val params: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
//        params.height = width
//        params.width = width
//        layout.layoutParams = params
        pieChart.isRotationEnabled = true
        pieChart.setRotationAngle(0f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        for (i in 0 until scoreListPie.size) {
            val score = scoreListPie[i]
            Log.v("dfsdererer", "values " + score.Piescore)
            Log.e(TAG, "Piescore  594   " + score.Piescore.toFloat())
            //typeAmountMap[""] = score.Piescore
            pieEntries.add(PieEntry(score.Piescore.toFloat(), "%"))

        }

        val colorsStage: ArrayList<Int> = ArrayList()
        colorsStage.add(resources.getColor(R.color.leadstatus_color1))
        colorsStage.add(resources.getColor(R.color.leadstatus_color2))
        colorsStage.add(resources.getColor(R.color.leadstatus_color3))

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.setValueFormatter(DecimalRemover())
        pieDataSet.valueTextSize = 12f
        pieDataSet.setColors(colorsStage)
        val pieData = PieData(pieDataSet)
        // pieData.setValueFormatter(PercentFormatter())
        //    pieData.setValueFormatter(DecimalRemover(DecimalFormat("########")))
        pieData.setDrawValues(true)

        val l: Legend = pieChart.getLegend()
        l.isEnabled = false

        pieChart.data = pieData
        pieChart.setOnClickListener(View.OnClickListener {
            ShowEnalargeGraph(pieEntries, 2)
        })


        pieChart.invalidate()
    }

    private fun ShowEnalargeGraph2(pieEntries: ArrayList<PieEntry>, graphType: Int) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_pie_chart, null)
        dialogBuilder.setView(dialogView)
        var heading: TextView = dialogView.findViewById<TextView>(R.id.heading)
        pieChart = dialogView.findViewById<PieChart>(R.id.pieChartLeads)
        if (graphType == 1) {
            heading.text = "SERVICE"
            pieChart.setUsePercentValues(false)
            pieChart.description.text = ""
            pieChart.isDrawHoleEnabled = true
            pieChart.isRotationEnabled = true
            pieChart.setTouchEnabled(true)       //<-----------
            pieChart.setDrawEntryLabels(false)
            pieChart.setRotationAngle(0f)
            pieChart.animateY(1400, Easing.EaseInOutQuad)
            // enable rotation of the chart by touch
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true)
            pieChart.setHighlightPerTapEnabled(true)
            pieChart.isRotationEnabled = true
            pieChart.setDrawEntryLabels(false)
            pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.isWordWrapEnabled = true
            val colorsStage: ArrayList<Int> = ArrayList()
            colorsStage.add(resources.getColor(R.color.line_color1))
            colorsStage.add(resources.getColor(R.color.line_color2))
            colorsStage.add(resources.getColor(R.color.line_color3))
            val pieDataSet = PieDataSet(pieEntries, "")
            pieDataSet.valueTextSize = 12f
            pieDataSet.setValueFormatter(DecimalRemover())
            pieDataSet.setColors(colorsStage)
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            val l: Legend = pieChart.getLegend()
            l.isEnabled = false
            pieChart.data = pieData
            pieChart.invalidate()
            val recyceChart =
                dialogView.findViewById(R.id.recycleView) as RecyclerView
            val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter =
                EnlargeLineChartAdapter(this@DashBoardActivity, serviceDashArrayList)
            recyceChart!!.adapter = adapter
        } else if (graphType == 2) {
            heading.text = "SERVICE STAGES"
            pieChart.setUsePercentValues(false)
            pieChart.description.text = ""
            pieChart.isDrawHoleEnabled = true
            pieChart.setTouchEnabled(true)   //<-----------
            pieChart.setDrawEntryLabels(false)
            pieChart.isRotationEnabled = true
            pieChart.setRotationAngle(0f)
            pieChart.animateY(1400, Easing.EaseInOutQuad)
            pieChart.setDrawEntryLabels(false)
            pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.isWordWrapEnabled = true
            val colorsStage: ArrayList<Int> = ArrayList()
            colorsStage.add(resources.getColor(R.color.leadstatus_color1))
            colorsStage.add(resources.getColor(R.color.leadstatus_color2))
            colorsStage.add(resources.getColor(R.color.leadstatus_color3))
            colorsStage.add(resources.getColor(R.color.leadstatus_color4))
            val pieDataSet = PieDataSet(pieEntries, "")
            pieDataSet.valueTextSize = 12f
            pieDataSet.setColors(colorsStage)
            pieDataSet.setValueFormatter(DecimalRemover())
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            val l: Legend = pieChart.getLegend()
            l.isEnabled = false
            pieChart.data = pieData
            pieChart.invalidate()
            val recyceChart =
                dialogView.findViewById(R.id.recycleView) as RecyclerView
            val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter = EnlargeBarChartAdapter(
                this@DashBoardActivity,
                serviceStatusDashArrayList
            )
            recyceChart!!.adapter = adapter
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }

    private fun ShowEnalargeGraph(pieEntries: ArrayList<PieEntry>, graphType: Int) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_pie_chart, null)
        dialogBuilder.setView(dialogView)
        var heading: TextView = dialogView.findViewById<TextView>(R.id.heading)
        pieChart = dialogView.findViewById<PieChart>(R.id.pieChartLeads)
        if (graphType == 1) {
            heading.text = "LEADS"
            pieChart.setUsePercentValues(false)
            pieChart.description.text = ""
            pieChart.isDrawHoleEnabled = true
            pieChart.isRotationEnabled = true
            pieChart.setTouchEnabled(true)
            pieChart.setDrawEntryLabels(false)
            pieChart.setRotationAngle(0f)
            pieChart.animateY(1400, Easing.EaseInOutQuad)
            // enable rotation of the chart by touch
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true)
            pieChart.setHighlightPerTapEnabled(true)
            pieChart.isRotationEnabled = true
            pieChart.setDrawEntryLabels(false)
            pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.isWordWrapEnabled = true
            val colorsStage: ArrayList<Int> = ArrayList()
            colorsStage.add(resources.getColor(R.color.line_color1))
            colorsStage.add(resources.getColor(R.color.line_color2))
            colorsStage.add(resources.getColor(R.color.line_color3))
            val pieDataSet = PieDataSet(pieEntries, "")
            pieDataSet.valueTextSize = 12f
            pieDataSet.setValueFormatter(DecimalRemover())
            pieDataSet.setColors(colorsStage)
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            val l: Legend = pieChart.getLegend()
            l.isEnabled = false
            pieChart.data = pieData
            pieChart.invalidate()
            val recyceChart =
                dialogView.findViewById(R.id.recycleView) as RecyclerView
            val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter =
                EnlargeLineChartAdapter(this@DashBoardActivity, leadDashArrayList)
            recyceChart!!.adapter = adapter
        } else if (graphType == 2) {
            heading.text = "LEAD STATUS"
            pieChart.setUsePercentValues(false)
            pieChart.description.text = ""
            pieChart.isDrawHoleEnabled = true
            pieChart.setTouchEnabled(true)
            pieChart.setDrawEntryLabels(false)
            pieChart.isRotationEnabled = true
            pieChart.setRotationAngle(0f)
            pieChart.animateY(1400, Easing.EaseInOutQuad)
            pieChart.setDrawEntryLabels(false)
            pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.isWordWrapEnabled = true
            val colorsStage: ArrayList<Int> = ArrayList()
            colorsStage.add(resources.getColor(R.color.leadstatus_color1))
            colorsStage.add(resources.getColor(R.color.leadstatus_color2))
            colorsStage.add(resources.getColor(R.color.leadstatus_color3))
            val pieDataSet = PieDataSet(pieEntries, "")
            pieDataSet.valueTextSize = 12f
            pieDataSet.setColors(colorsStage)
            pieDataSet.setValueFormatter(DecimalRemover())
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            val l: Legend = pieChart.getLegend()
            l.isEnabled = false
            pieChart.data = pieData
            pieChart.invalidate()
            val recyceChart =
                dialogView.findViewById(R.id.recycleView) as RecyclerView
            val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter = EnlargeBarChartAdapter(
                this@DashBoardActivity,
                leadStatusDashArrayList
            )
            recyceChart!!.adapter = adapter
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }

    private fun ShowEnalargeGraphBar(entries: ArrayList<BarEntry>) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_bar_chart, null)
        dialogBuilder.setView(dialogView)
        var heading: TextView = dialogView.findViewById<TextView>(R.id.heading)
        barChart = dialogView.findViewById<BarChart>(R.id.barChart)
        heading.text = "LEAD STAGES"
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)


        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false
        barChart!!.setScaleEnabled(false)
        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(1000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatterBar()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.WHITE


//        barChart.legend.textSize = 15f
//        barChart.legend.textColor = Color.RED

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))
        colors.add(resources.getColor(R.color.leadstages_color3))

        /////////////////////

        val barDataSet = BarDataSet(entries, "")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)//314400
        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        barChart.data = data
        barChart.invalidate()
        val recycleView =
            dialogView.findViewById(R.id.recycleView) as RecyclerView
        val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
        recycleView!!.layoutManager =
            lLayout as RecyclerView.LayoutManager?
        val adapter = PieChartAdapter(
            this@DashBoardActivity,
            leadStagesDashArrayList
        )
        recycleView!!.adapter = adapter

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }

    private fun setPieChartLead() {
        Log.v("asdasdssss", "setPieChartLead  ")
//        https://intensecoder.com/piechart-tutorial-using-mpandroidchart-in-kotlin/

        scoreListPieLead.clear()
        scoreListPieLead = getScoreList3()
        Log.v("asdasdssss", "size  " + scoreListPieLead.size)


        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "%"
        pieChartLead.setUsePercentValues(false)
        // Gets linearlayout
        // Gets linearlayout
//        val layout: LinearLayout = findViewById(R.id.id_lin1)
//        val width: Int = layout.getWidth()
//        val params: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
//        params.height = width
//        params.width = width
//        layout.layoutParams = params

        pieChartLead.description.text = ""
        pieChartLead.isDrawHoleEnabled = true
        pieChartLead.setTouchEnabled(false)
        pieChartLead.setDrawEntryLabels(false)
        //adding padding
        // pieChartLead.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChartLead.setUsePercentValues(false)
        pieChartLead.isRotationEnabled = true
        pieChartLead.setRotationAngle(0f)
        pieChartLead.animateY(1400, Easing.EaseInOutQuad)
        pieChartLead.setDrawEntryLabels(false)
        pieChartLead.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChartLead.legend.isWordWrapEnabled = true
        val typeAmountMap: MutableMap<String, Float> = HashMap()

        for (i in 0 until scoreListPieLead.size) {
            val score = scoreListPieLead[i]
            Log.v("dsfsdfd333", "ss  " + score.Piescore)
            Log.e(TAG, "Piescore  594   " + score.Piescore.toFloat())
            typeAmountMap[""] = score.Piescore
            pieEntries.add(PieEntry(score.Piescore.toFloat(), label))

        }

        val colorsStage: ArrayList<Int> = ArrayList()
        colorsStage.add(resources.getColor(R.color.line_color1))
        colorsStage.add(resources.getColor(R.color.line_color2))
        colorsStage.add(resources.getColor(R.color.line_color3))

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet!!.valueFormatter = PercentFormatter()
        pieDataSet.valueTextSize = 12f
        pieDataSet.setColors(colorsStage)
        pieDataSet.setValueFormatter(DecimalRemover())
        val pieData = PieData(pieDataSet)
        // pieData.setValueFormatter(PercentFormatter())
        //    pieData.setValueFormatter(DecimalRemover(DecimalFormat("########")))
        pieData.setDrawValues(true)

        val l: Legend = pieChartLead.getLegend()
        l.isEnabled = false

        pieChartLead.data = pieData
        pieChartLead.setOnClickListener(View.OnClickListener {
            ShowEnalargeGraph(pieEntries, 1)
        })

        pieChartLead.invalidate()
    }

    private fun getScoreList2(): ArrayList<ScorePie> {

        for (i in 0 until leadStatusDashArrayList.length()) {
            //apply your logic
            var jsonObject = leadStatusDashArrayList.getJSONObject(i)
            Log.e(TAG, "422  Count   " + jsonObject.getString("Count"))
            scoreListPie.add(ScorePie("", jsonObject.getString("Value").toFloat()))
        }

        return scoreListPie
    }

    private fun getScoreList3(): ArrayList<ScorePie> {

        for (i in 0 until leadDashArrayList.length()) {
            //apply your logic
            var jsonObject = leadDashArrayList.getJSONObject(i)
            Log.v("asdasdssss", "size2  " + jsonObject.getString("Count"))
            Log.e(TAG, "422  Count   " + jsonObject.getString("Count"))
            scoreListPieLead.add(ScorePie("", jsonObject.getString("Value").toFloat()))
        }

        return scoreListPieLead
    }


    //SERVICE

    private fun setPieChartProject() {
        scoreListPieProject.clear()
        scoreListPieProject = getScoreListService2()
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "%"
        pieChartProject.setUsePercentValues(false)
        pieChartProject.description.text = ""
        pieChartProject.isDrawHoleEnabled = true
        pieChartProject.setTouchEnabled(false)
        pieChartProject.setDrawEntryLabels(false)

        pieChartProject.isRotationEnabled = true
        pieChartProject.setRotationAngle(0f)
        pieChartProject.animateY(1400, Easing.EaseInOutQuad)
        pieChartProject.setDrawEntryLabels(false)
        pieChartProject.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChartProject.legend.isWordWrapEnabled = true
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        for (i in 0 until scoreListPieProject.size) {
            val score = scoreListPieProject[i]
            Log.v("dfsdererer", "values " + score.Piescore)
            Log.e(TAG, "Piescore  594   " + score.Piescore.toFloat())
            //typeAmountMap[""] = score.Piescore
            pieEntries.add(PieEntry(score.Piescore.toFloat(), "%"))

        }

        val colorsStage: ArrayList<Int> = ArrayList()
        colorsStage.add(resources.getColor(R.color.leadstatus_color1))
        colorsStage.add(resources.getColor(R.color.leadstatus_color2))
        colorsStage.add(resources.getColor(R.color.leadstatus_color3))
        colorsStage.add(resources.getColor(R.color.leadstatus_color4))

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.setValueFormatter(DecimalRemover())
        pieDataSet.valueTextSize = 12f
        pieDataSet.setColors(colorsStage)
        val pieData = PieData(pieDataSet)
        // pieData.setValueFormatter(PercentFormatter())
        //    pieData.setValueFormatter(DecimalRemover(DecimalFormat("########")))
        pieData.setDrawValues(true)

        val l: Legend = pieChartProject.getLegend()
        l.isEnabled = false

        pieChartProject.data = pieData
        pieChartProject.setOnClickListener(View.OnClickListener {
            ShowEnalargeGraph2(pieEntries, 2)
        })


        pieChartProject.invalidate()
    }

    private fun getScoreListService2(): ArrayList<ScorePieProject> {
        for (i in 0 until serviceStatusDashArrayList.length()) {
            //apply your logic
            var jsonObject = serviceStatusDashArrayList.getJSONObject(i)
            Log.e(TAG, "422  Count   " + jsonObject.getString("Count"))
            scoreListPieProject.add(ScorePieProject("", jsonObject.getString("Value").toFloat()))
        }

        return scoreListPieProject
    }


    override fun onBackPressed() {


        if ((mainpage!!.getVisibility() == View.GONE)) {

            mainpage!!.visibility = View.VISIBLE
            firstpage!!.visibility = View.GONE
            secondpage!!.visibility = View.GONE
            mainHeadingDash!!.setText("DASHBOARD")

            barChart!!.clear()
            // pieChart!!.clear()
            // pieChartLead!!.clear()
            pieChartServices.clear()
            pieChartProject.clear()
            barChart!!.clear()
            pieChart!!.clear()
            pieChartLead!!.clear()

        } else {

            super.onBackPressed()
        }

    }


}