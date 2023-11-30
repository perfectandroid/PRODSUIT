package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AreaListRepository
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ProjectGraphActivity : AppCompatActivity(), View.OnClickListener  {
    val TAG: String = "ProjectGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var TabMode = 0 // 0 = Graph , 1 = Tile
    var ContinueMode = 0 // 0 = First , 1 = Second
    var ChartMode = 0 // 0 = First , 1 = Second
    var chartModeCount    = 0
    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null

    //project status tile
    var projectStatusCount = 0
    lateinit var projectTileViewModel: ProjectTileViewModel
    lateinit var projectStatusArrayList: JSONArray
    var recycler_project_tile: RecyclerView? = null

    //billing status
    var recycler_billing_status: RecyclerView? = null
    var billingStatusCount = 0
    lateinit var projectBillStatusViewModel: ProjectBillStatusViewModel
    lateinit var projectBillArrayList: JSONArray

    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null


    lateinit var chartTypeViewModel               : ChartTypeViewModel
    lateinit var chartTypeArrayList: JSONArray
    var ID_ChartMode    :  String? = ""

    private var actv_mode: AutoCompleteTextView? = null
    private var ll_projectDelayed: LinearLayout? = null

    //project delayed
    var projectdelayCount      = 0
    lateinit var projectDelayedViewModel: ProjectDelayedViewModel
    lateinit var projectDelayArrayList: JSONArray
    private lateinit var projectDelayedChart: BarChart
    var recycStagWise: RecyclerView? = null
    private var projectDelayListBar = ArrayList<ProjectDelayBarModel>()


    //Top10Project
    var Top10ProjectCount = 0
    lateinit var top10projectViewModel: Top10ProjectViewModel
    private var top10projectBar = ArrayList<Top10ProjectBar>()
    lateinit var Top10ProjectArrayList: JSONArray
    var recyctop10Project: RecyclerView? = null
    private lateinit var top10ProjectChart: BarChart

    //ExpenseAnalysis
    var ExpenseAnalysisCount                  = 0
    private var ll_ExpenseAnalysis            : LinearLayout? = null
    private lateinit var ExpenseAnalysisChart : BarChart
    private var expenseanalysisBar            = ArrayList<ExpenseAnalysisBar>()
    lateinit var ExpenseAnalysisArrayList     : JSONArray
    var recycExpenseAnalysis: RecyclerView?   = null
    lateinit var expenseanalysisViewModel     : ExpenseAnalysisViewModel


    //UpcomingStageDueDates
    var UpcomingStageDueDatesCount                  = 0
    lateinit var upcomingstageDueDatesViewModel     : UpcomingStageDueDatesViewModel
    var recyclr_Upcoming_Stage_Due_Dates            : RecyclerView?   = null
    lateinit var upcomingdatesArrayList             : JSONArray
    var TransDate                                   = ""

    //costmaterialusageAllocated
    var costmaterialusageAllocatedCount                  = 0
    private var costmaterialusageAllocatedUsedBar            = ArrayList<CostMaterialUsageAllocatedUsedBar>()
    lateinit var costmaterialUsageAllocatedUsedViewModel     : CostMaterialUsageAllocatedUsedViewModel
    lateinit var CostMaterialUsageArrayList                  : JSONArray
    var recycCostMaterialUsageAllocate                       : RecyclerView?   = null
    private lateinit var CostMaterialUsageAllocateChart      : BarChart


    //totalstagewise
    var totalstagewiseCount                       = 0
    private var totalstagewisedueBar              = ArrayList<TotalStagewiseDueBar>()
    lateinit var totalstagewiseDueBarViewModel    : TotalStagewiseDueBarViewModel
    lateinit var totalstagewiseDueArrayList       : JSONArray
    var recyTotalStagewiseDue                     : RecyclerView?   = null
    private lateinit var TotalStagewiseDueChart   : BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_project_graph)
        context = this@ProjectGraphActivity

        projectTileViewModel       = ViewModelProvider(this).get(ProjectTileViewModel::class.java)
        top10projectViewModel      = ViewModelProvider(this).get(Top10ProjectViewModel::class.java)
        projectBillStatusViewModel = ViewModelProvider(this).get(ProjectBillStatusViewModel::class.java)

        chartTypeViewModel                       = ViewModelProvider(this).get(ChartTypeViewModel::class.java)
        projectDelayedViewModel                  = ViewModelProvider(this).get(ProjectDelayedViewModel::class.java)
        expenseanalysisViewModel                 = ViewModelProvider(this).get(ExpenseAnalysisViewModel::class.java)
        upcomingstageDueDatesViewModel           = ViewModelProvider(this).get(UpcomingStageDueDatesViewModel::class.java)
        costmaterialUsageAllocatedUsedViewModel  = ViewModelProvider(this).get(CostMaterialUsageAllocatedUsedViewModel::class.java)
        totalstagewiseDueBarViewModel            = ViewModelProvider(this).get(TotalStagewiseDueBarViewModel::class.java)


        setRegViews()
        TabMode = 0
        ContinueMode = 0
        hideViews()


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback_project)
        imback!!.setOnClickListener(this)
        ll_Graph = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile  = findViewById<LinearLayout>(R.id.ll_Tile)

        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        recycler_project_tile   = findViewById<RecyclerView>(R.id.recycler_project_tile)
        recycler_billing_status = findViewById<RecyclerView>(R.id.recycler_billing_status)
        actv_mode               = findViewById<AutoCompleteTextView>(R.id.actv_mode)

        ll_projectDelayed          = findViewById<LinearLayout>(R.id.ll_projectDelayed)
        projectDelayedChart        = findViewById(R.id.projectDelayedChart)
        ExpenseAnalysisChart       = findViewById(R.id.ExpenseAnalysisChart)
        recycExpenseAnalysis       = findViewById(R.id.recycExpenseAnalysis)
        recyclr_Upcoming_Stage_Due_Dates       = findViewById(R.id.recyclr_Upcoming_Stage_Due_Dates)
        recycCostMaterialUsageAllocate         = findViewById(R.id.recycCostMaterialUsageAllocate)
        CostMaterialUsageAllocateChart         = findViewById(R.id.CostMaterialUsageAllocateChart)
        TotalStagewiseDueChart                 = findViewById(R.id.TotalStagewiseDueChart)
        recyTotalStagewiseDue                  = findViewById(R.id.recyTotalStagewiseDue)

        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)
        actv_mode!!.setOnClickListener(this)
    }


    private fun hideViews() {

        ll_Graph!!.visibility = View.GONE
        ll_Tile!!.visibility  = View.GONE

        if (TabMode == 0) {
            ll_Graph!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)

            if (ContinueMode == 0) {
                ChartMode = 0
                chartModeCount = 0
//                getChartModeData()
//                getTop10Project()
//                getProjectDelayed()
//                getExpenseAnalysis()
//                getUpcomingStageDueDates()
//                getCostMaterialUsageAllocatedUsed()
                getTotalStagewiseDue()
            }


        } else if (TabMode == 1) {
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

            projectStatusCount = 0
            getProjectStatusTile()

            billingStatusCount = 0
            getBillingStatus()

        }
        getCurrentDate()

    }


    private fun getCurrentDate() {

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)

            TransDate = sdfDate1.format(newDate)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun getChartModeData() {
        var ReqMode = ""
        var SubMode = ""
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                chartTypeViewModel.getChartType(this,ReqMode!!,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (chartModeCount == 0) {
                                chartModeCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1777   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("checkDetails")
                                    chartTypeArrayList = jobjt.getJSONArray("checkDetailsList")
                                    if (chartTypeArrayList.length() > 0){
                                        if (ChartMode == 0){
                                            val jsonObject = chartTypeArrayList.getJSONObject(0)
                                            ID_ChartMode = jsonObject.getString("ID_Mode")
                                            actv_mode!!.setText(jsonObject.getString("Mode_Name"))
//                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)
//
                                            ll_projectDelayed!!.visibility = View.VISIBLE
                                            projectdelayCount   = 0
                                            getProjectDelayed()

                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ProjectGraphActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getTop10Project() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                top10projectViewModel.getTop10Project(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg TopProject   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (Top10ProjectCount == 0) {
                                    Top10ProjectCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   project bill   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("Top10Projects")
                                        var remark =   jobjt.getString("Reamrk")
                                        Top10ProjectArrayList=jobjt.getJSONArray("Top10ProjectsList")
                                        Log.e(TAG, "Top10ProjectArrayList 43434  =  "+Top10ProjectArrayList)

                                        if (Top10ProjectArrayList.length() > 0) {

                                            setTop10ProjectBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProjectGraphActivity, 2)
                                            recyctop10Project!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = Top10ProjectAdapter(this@ProjectGraphActivity, Top10ProjectArrayList)
                                            recyctop10Project!!.adapter = adapter

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun setTop10ProjectBarchart() {
        top10projectBar.clear()
        top10projectBar = getTop10Projecte()
        //   Log.e(TAG, "saleGraphListBar==   "+saleGraphListBar)


        top10ProjectChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = top10ProjectChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        top10ProjectChart.axisRight.isEnabled = false
        //remove legend
        top10ProjectChart.legend.isEnabled = false
        top10ProjectChart!!.setScaleEnabled(true)
        //remove description label
        top10ProjectChart.description.isEnabled = false


        //add animation
        top10ProjectChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE

//colors
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))
        colors.add(resources.getColor(R.color.leadstages_color3))

        colors.add(resources.getColor(R.color.leadstages_color4))
        colors.add(resources.getColor(R.color.leadstages_color5))
        colors.add(resources.getColor(R.color.leadstages_color6))

        colors.add(resources.getColor(R.color.leadstages_color7))
        colors.add(resources.getColor(R.color.leadstages_color8))
        colors.add(resources.getColor(R.color.leadstages_color9))

        colors.add(resources.getColor(R.color.leadstages_color10))
        colors.add(resources.getColor(R.color.leadstages_color11))
        colors.add(resources.getColor(R.color.leadstages_color12))

        /////////////////////////////////////

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in top10projectBar.indices) {
            val score = top10projectBar[i]
            entries.add(BarEntry(i.toFloat(), score.Project.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Project")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        top10ProjectChart.data = data


        top10ProjectChart.invalidate()

    }

    private fun getExpenseAnalysis() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                expenseanalysisViewModel.getExpenseAnalysis(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project Delay Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (ExpenseAnalysisCount == 0) {
                                    ExpenseAnalysisCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   projectDelayed   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt =
                                            jObject.getJSONObject("ProjectExpenseAnalysis")
                                        var remark =   jobjt.getString("Reamrk")

                                        ExpenseAnalysisArrayList=jobjt.getJSONArray("ProjectExpenseAnalysisList")


                                        if (ExpenseAnalysisArrayList.length() > 0) {
                                            Log.e(TAG, "projectDelayedArrayList 43434  =  "+ExpenseAnalysisArrayList)
                                            setExpenseAnalysisBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProjectGraphActivity, 1)
                                            recycExpenseAnalysis!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ExpenseAnalysisListAdapter(this@ProjectGraphActivity, ExpenseAnalysisArrayList)
                                            recycExpenseAnalysis!!.adapter = adapter

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "fffff" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "gggggg   "+e.toString() )
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

    private fun setExpenseAnalysisBarchart() {
        expenseanalysisBar.clear()
        expenseanalysisBar = getExpenseAnalysisBarList()

        ExpenseAnalysisChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ExpenseAnalysisChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ExpenseAnalysisChart.axisRight.isEnabled = false
        //remove legend
        ExpenseAnalysisChart.legend.isEnabled = false
        ExpenseAnalysisChart!!.setScaleEnabled(true)
        //remove description label
        ExpenseAnalysisChart.description.isEnabled = false


        //add animation
        ExpenseAnalysisChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar1()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))

        /////////////////////////////////////

        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in expenseanalysisBar.indices) {
            val score = expenseanalysisBar[i]
            entries1.add(BarEntry(i.toFloat(), score.ProjectAmount.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in expenseanalysisBar.indices) {
            val score = expenseanalysisBar[i]
            entries2.add(BarEntry(i.toFloat(), score.Expense.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(Color.BLUE)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(Color.RED)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet2.valueFormatter = DefaultValueFormatter(0)

        Log.e(TAG, "entries 43434  =  " + entries1)
        Log.e(TAG, "entries 43434  =  " + entries2)

        val baraData=BarData(barDataSet1,barDataSet2)
        baraData.barWidth=0.1f
        //      projectDelayedChart.data=baraData

//        val data = BarData(barDataSet1)
//
//        data.setValueTextSize(15f)
//        data.setValueTextColor(Color.BLACK)
//        data.setDrawValues(false)
        ExpenseAnalysisChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        ExpenseAnalysisChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        ExpenseAnalysisChart.invalidate()




    }

    inner class MyAxisFormatterBar1 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < expenseanalysisBar.size) {
                expenseanalysisBar[index].Project
            } else {
                ""
            }
        }
    }


    private fun getExpenseAnalysisBarList(): ArrayList<ExpenseAnalysisBar> {
        for (i in 0 until ExpenseAnalysisArrayList.length())
        {
            var jsonObject = ExpenseAnalysisArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            expenseanalysisBar.add(ExpenseAnalysisBar("",jsonObject.getString("ProjectAmount").toFloat().toInt(),jsonObject.getString("Expense").toFloat().toInt()))
        }

        return expenseanalysisBar

    }



    private fun getUpcomingStageDueDates() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                upcomingstageDueDatesViewModel.getUpcomingStageDueDates(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (UpcomingStageDueDatesCount == 0) {
                                    UpcomingStageDueDatesCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "-2") {

                                        val jobjt = jObject.getJSONObject("UpcomingStageDueDates")
                                        upcomingdatesArrayList = jobjt.getJSONArray("PUpcomingStageDueDatesList")
//                                        Log.e(TAG, "InventoryProductReorderLeveArray   "+InventoryProductReorderLeveArray)
//                                        upcomingdatesArrayList.put(jobjt)

                                        Log.e(TAG, "upcomingdatesArrayList   "+upcomingdatesArrayList)
                                        val lLayout = GridLayoutManager(this@ProjectGraphActivity, 1)
                                        recyclr_Upcoming_Stage_Due_Dates!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = Upcoming_Due_Date_Adapter(this@ProjectGraphActivity, upcomingdatesArrayList)
                                        recyclr_Upcoming_Stage_Due_Dates!!.adapter = adapter


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
    }


    private fun getCostMaterialUsageAllocatedUsed() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                costmaterialUsageAllocatedUsedViewModel.getCostMaterialUsageAllocatedUsed(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project Delay Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (costmaterialusageAllocatedCount == 0) {
                                    costmaterialusageAllocatedCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   CostMaterialUsage   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt =
                                            jObject.getJSONObject("CostofMaterialUsageAllocatedandUsed")
                                        var remark =   jobjt.getString("Reamrk")

                                        CostMaterialUsageArrayList=jobjt.getJSONArray("PCostofMaterialUsageAllocatedandUsedList")


                                        if (CostMaterialUsageArrayList.length() > 0) {
                                            Log.e(TAG, "CostMaterialUsage 43434  =  "+CostMaterialUsageArrayList)
                                            setCostMaterialUsageAllocatedUsedBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProjectGraphActivity, 1)
                                            recycCostMaterialUsageAllocate!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = CostofMaterialUsageAllocatedandUsedAdapter(this@ProjectGraphActivity, CostMaterialUsageArrayList)
                                            recycCostMaterialUsageAllocate!!.adapter = adapter

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "fffff" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "gggggg   "+e.toString() )
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

    private fun setCostMaterialUsageAllocatedUsedBarchart() {
        costmaterialusageAllocatedUsedBar.clear()
        costmaterialusageAllocatedUsedBar = getCostMaterialUsageAllocatedUsedBarList()

        CostMaterialUsageAllocateChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = CostMaterialUsageAllocateChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        CostMaterialUsageAllocateChart.axisRight.isEnabled = false
        //remove legend
        CostMaterialUsageAllocateChart.legend.isEnabled = false
        CostMaterialUsageAllocateChart!!.setScaleEnabled(true)
        //remove description label
        CostMaterialUsageAllocateChart.description.isEnabled = false


        //add animation
        CostMaterialUsageAllocateChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))

        /////////////////////////////////////

        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in costmaterialusageAllocatedUsedBar.indices) {
            val score = costmaterialusageAllocatedUsedBar[i]
            entries1.add(BarEntry(i.toFloat(), score.Allocated.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in costmaterialusageAllocatedUsedBar.indices) {
            val score = costmaterialusageAllocatedUsedBar[i]
            entries2.add(BarEntry(i.toFloat(), score.Usage.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(Color.BLUE)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(Color.RED)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet2.valueFormatter = DefaultValueFormatter(0)

        Log.e(TAG, "entries 43434  =  " + entries1)
        Log.e(TAG, "entries 43434  =  " + entries2)

        val baraData=BarData(barDataSet1,barDataSet2)
        baraData.barWidth=0.1f
        //      projectDelayedChart.data=baraData

//        val data = BarData(barDataSet1)
//
//        data.setValueTextSize(15f)
//        data.setValueTextColor(Color.BLACK)
//        data.setDrawValues(false)
        CostMaterialUsageAllocateChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        CostMaterialUsageAllocateChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        CostMaterialUsageAllocateChart.invalidate()




    }

    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < costmaterialusageAllocatedUsedBar.size) {
                costmaterialusageAllocatedUsedBar[index].Project
            } else {
                ""
            }
        }
    }


    private fun getCostMaterialUsageAllocatedUsedBarList(): ArrayList<CostMaterialUsageAllocatedUsedBar> {
        for (i in 0 until CostMaterialUsageArrayList.length())
        {
            var jsonObject = CostMaterialUsageArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            costmaterialusageAllocatedUsedBar.add(CostMaterialUsageAllocatedUsedBar("",jsonObject.getString("Allocated").toFloat().toInt(),jsonObject.getString("Usage").toFloat().toInt()))
        }

        return costmaterialusageAllocatedUsedBar

    }



    private fun getTotalStagewiseDue() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                totalstagewiseDueBarViewModel.getTotalStagewiseDue(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project Delay Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (totalstagewiseCount == 0) {
                                    totalstagewiseCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   CostMaterialUsage   " + msg)
                                    if (jObject.getString("StatusCode") == "-2") {

                                        val jobjt =
                                            jObject.getJSONObject("TotalStagewiseDue")
                                        var remark =   jobjt.getString("Reamrk")

                                        totalstagewiseDueArrayList=jobjt.getJSONArray("TotalStagewiseDueList")


                                        if (totalstagewiseDueArrayList.length() > 0) {
                                            Log.e(TAG, "CostMaterialUsage 43434  =  "+totalstagewiseDueArrayList)
                                            setTotalStagewiseDueBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProjectGraphActivity, 1)
                                            recyTotalStagewiseDue!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = TotalStagewiseDueAdapter(this@ProjectGraphActivity, totalstagewiseDueArrayList)
                                            recyTotalStagewiseDue!!.adapter = adapter

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "fffff" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "gggggg   "+e.toString() )
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

    private fun setTotalStagewiseDueBarchart() {
        totalstagewisedueBar.clear()
        totalstagewisedueBar = getTotalStagewiseDueBarList()

        TotalStagewiseDueChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = TotalStagewiseDueChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        TotalStagewiseDueChart.axisRight.isEnabled = false
        //remove legend
        TotalStagewiseDueChart.legend.isEnabled = false
        TotalStagewiseDueChart!!.setScaleEnabled(true)
        //remove description label
        TotalStagewiseDueChart.description.isEnabled = false


        //add animation
        TotalStagewiseDueChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar3()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))

        /////////////////////////////////////

        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in totalstagewisedueBar.indices) {
            val score = totalstagewisedueBar[i]
            entries1.add(BarEntry(i.toFloat(), score.TotalCount.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in totalstagewisedueBar.indices) {
            val score = totalstagewisedueBar[i]
            entries2.add(BarEntry(i.toFloat(), score.TotalPercentage.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(Color.BLUE)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(Color.RED)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet2.valueFormatter = DefaultValueFormatter(0)

        Log.e(TAG, "entries 43434  =  " + entries1)
        Log.e(TAG, "entries 43434  =  " + entries2)

        val baraData=BarData(barDataSet1,barDataSet2)
        baraData.barWidth=0.1f
        //      projectDelayedChart.data=baraData

//        val data = BarData(barDataSet1)
//
//        data.setValueTextSize(15f)
//        data.setValueTextColor(Color.BLACK)
//        data.setDrawValues(false)
        TotalStagewiseDueChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        TotalStagewiseDueChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        TotalStagewiseDueChart.invalidate()

    }

    inner class MyAxisFormatterBar3 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < totalstagewisedueBar.size) {
                totalstagewisedueBar[index].Stages
            } else {
                ""
            }
        }
    }

    private fun getTotalStagewiseDueBarList(): ArrayList<TotalStagewiseDueBar> {
        for (i in 0 until totalstagewiseDueArrayList.length())
        {
            var jsonObject = totalstagewiseDueArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            totalstagewisedueBar.add(TotalStagewiseDueBar("",jsonObject.getString("TotalCount").toFloat().toInt(),jsonObject.getString("TotalPercentage").toFloat().toInt()))
        }

        return totalstagewisedueBar

    }



    private fun getProjectDelayed() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                progressDialog!!.show()
                projectDelayedViewModel.getProjectDealyedStatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project Delay Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (projectdelayCount == 0) {
                                    projectdelayCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   projectDelayed   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt =
                                            jObject.getJSONObject("ProjectDelayedStatus")
                                        var remark =   jobjt.getString("Reamrk")

                                        projectDelayArrayList=jobjt.getJSONArray("ProjectDelayedStatusList")


                                        if (projectDelayArrayList.length() > 0) {
                                            Log.e(TAG, "projectDelayedArrayList 43434  =  "+projectDelayArrayList)

                                            setProjectDelayBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProjectGraphActivity, 2)
                                            recycStagWise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = MonthlyBarChartAdapter(this@ProjectGraphActivity, projectDelayArrayList)
                                            recycStagWise!!.adapter = adapter



                                        }




                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "fffff" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "gggggg   "+e.toString() )
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

    private fun setProjectDelayBarchart() {
        projectDelayListBar.clear()
        projectDelayListBar = getProjectDelayBarList()

        projectDelayedChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = projectDelayedChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        projectDelayedChart.axisRight.isEnabled = false
        //remove legend
        projectDelayedChart.legend.isEnabled = false
        projectDelayedChart!!.setScaleEnabled(true)
        //remove description label
        projectDelayedChart.description.isEnabled = false


        //add animation
        projectDelayedChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar66()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +70f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))

        /////////////////////////////////////

        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in projectDelayListBar.indices) {
            val score = projectDelayListBar[i]
            entries1.add(BarEntry(i.toFloat(), score.ActualPeriod.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in projectDelayListBar.indices) {
            val score = projectDelayListBar[i]
            entries2.add(BarEntry(i.toFloat(), score.CurrentPeriod.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(Color.BLUE)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(Color.RED)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet2.valueFormatter = DefaultValueFormatter(0)

        Log.e(
            TAG,
            "entries 43434  =  " + entries1
        )
        Log.e(
            TAG,
            "entries 43434  =  " + entries2
        )

        val baraData=BarData(barDataSet1,barDataSet2)
        baraData.barWidth=0.1f
        //      projectDelayedChart.data=baraData

//        val data = BarData(barDataSet1)
//
//        data.setValueTextSize(15f)
//        data.setValueTextColor(Color.BLACK)
//        data.setDrawValues(false)
        projectDelayedChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        projectDelayedChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        projectDelayedChart.invalidate()




    }

    inner class MyAxisFormatterBar66 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < projectDelayListBar.size) {
                projectDelayListBar[index].Project
            } else {
                ""
            }
        }
    }


    private fun getProjectDelayBarList(): ArrayList<ProjectDelayBarModel> {
        for (i in 0 until projectDelayArrayList.length())
        {
            var jsonObject = projectDelayArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            projectDelayListBar.add(ProjectDelayBarModel(jsonObject.getString("Project"),jsonObject.getString("ActualPeriod").toFloat().toInt(),jsonObject.getString("CurrentPeriod").toFloat().toInt()))
        }

        return projectDelayListBar

    }



    private fun getTop10Projecte(): ArrayList<Top10ProjectBar> {
        for (i in 0 until Top10ProjectArrayList.length())
        {
            var jsonObject = Top10ProjectArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            top10projectBar.add(Top10ProjectBar("",jsonObject.getString("Amount").toFloat().toInt()))
        }

        return top10projectBar

    }

    inner class MyAxisFormatterBar : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < top10projectBar.size) {
                top10projectBar[index].Project
            } else {
                ""
            }
        }
    }

    private fun getBillingStatus() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                progressDialog!!.show()
                projectBillStatusViewModel.getProjectBillStatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project tile Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (billingStatusCount == 0) {
                                    billingStatusCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   project bill   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt =
                                            jObject.getJSONObject("ProjectTileDashBoardDetails")
                                        var remark =   jobjt.getString("Reamrk")

                                        projectBillArrayList=jobjt.getJSONArray("ProjectTileDashBoardDetailsList")
                                        Log.e(TAG, "projectBillArrayList 43434  =  "+projectBillArrayList)


                                        val lLayout =
                                            GridLayoutManager(this@ProjectGraphActivity, 4)

                                        recycler_billing_status!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter1 = ProjectBillStatusAdapter(
                                            this@ProjectGraphActivity,
                                            projectBillArrayList,remark
                                        )
                                        recycler_billing_status!!.adapter = adapter1


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun showChartDrop(chartTypeArrayList: JSONArray) {

        var modeType = Array<String>(chartTypeArrayList.length()) { "" }
        var modeTypeID = Array<String>(chartTypeArrayList.length()) { "" }
        for (i in 0 until chartTypeArrayList.length()) {
            val objects: JSONObject = chartTypeArrayList.getJSONObject(i)


            modeType[i] = objects.getString("Mode_Name");
            modeTypeID[i] = objects.getString("ID_Mode");
            //   ID_ChartMode = objects.getString("ID_Mode")

            Log.e(TAG, "00000111   " + ID_ChartMode)
            Log.e(TAG, "85456214   " + modeType)


            val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
                ID_ChartMode = modeTypeID[position]

                ll_projectDelayed!!.visibility = View.GONE
//                ll_ComplaintWise!!.visibility = View.GONE
//                ll_ServiceWise!!.visibility = View.GONE


                if (ID_ChartMode.equals("1")){
                    ll_projectDelayed!!.visibility = View.VISIBLE
                    projectdelayCount   = 0
                    getProjectDelayed()
                }
                else if (ID_ChartMode.equals("2")){
//                    ll_ServiceWise!!.visibility = View.VISIBLE
//                    crmservicewiseCount = 0
//                    getCRMservicewiseData()
                }
                else if (ID_ChartMode.equals("3")){
//                    ll_ComplaintWise!!.visibility = View.VISIBLE
//                    crmcomplaintwiseCount = 0
//                    getCRMcomplaintwiseData()
                }
                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }

    private fun getProjectStatusTile() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                progressDialog!!.show()
                projectTileViewModel.getProjectTile(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg project tile Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (projectStatusCount == 0) {
                                    projectStatusCount++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProjectTileDashBoardDetails")
                                        var remark = jobjt.getString("Reamrk")

                                        projectStatusArrayList =
                                            jobjt.getJSONArray("ProjectTileDashBoardDetailsList")
                                        Log.e(
                                            TAG,
                                            "projectStatusArrayList 4343  =  " + projectStatusArrayList
                                        )

//                                        recycler_project_tile!!.setLayoutManager(
//                                            LinearLayoutManager(
//                                                this,
//                                                RecyclerView.HORIZONTAL,
//                                                false
//                                            )
//                                        )
//
//                                        val adapter = ProjectTileStatusAdapter(this@ProjectGraphActivity,projectStatusArrayList,remark)
//                                        recycler_project_tile!!.adapter = adapter
//                                     //   adapter.setClickListener(this@ProjectGraphActivity)


                                        val lLayout =
                                            GridLayoutManager(this@ProjectGraphActivity, 3)

                                        recycler_project_tile!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter1 = ProjectTileStatusAdapter(
                                            this@ProjectGraphActivity,
                                            projectStatusArrayList, remark
                                        )
                                        recycler_project_tile!!.adapter = adapter1


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectGraphActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback_project -> {
                finish()
            }

            R.id.tvv_dash -> {
                TabMode = 0
                hideViews()
            }

            R.id.tvv_tile -> {
                TabMode = 1
                hideViews()
            }
            R.id.actv_mode->{
                ChartMode      = 1
                chartModeCount = 0
                getChartModeData()
            }

        }
    }

}