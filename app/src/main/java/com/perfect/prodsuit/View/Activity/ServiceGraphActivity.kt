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
import android.widget.*
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
import com.perfect.prodsuit.Repository.CRMTileDashBoardDetailsRepository
import com.perfect.prodsuit.View.Adapter.CrmStageChartAdapter
import com.perfect.prodsuit.View.Adapter.LeadHistoryAdapter
import com.perfect.prodsuit.View.Adapter.MonthlyBarChartAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ServiceGraphActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = "ServiceGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var SubMode     :  String? = ""
    var label       :  String? = ""

    var TransDate   :  String? = "17-11-2023"
    var DashMode    :  String? = ""
    var DashType    :  String? = ""

    var chartModeCount    = 0
    var crmservicewiseCount    = 0
    var crmStagewiseCount      = 0
    var TicketOutstandingCount  = 0
    var TicketStatusCount  = 0
    var crmcomplaintwiseCount  = 0

    var TabMode    = 0 // 0 = Graph , 1 = Tile
    var ContinueMode    = 0 // 0 = First , 1 = Second
    var ChartMode    = 0 // 0 = First , 1 = Second

    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null

    private var actv_mode: AutoCompleteTextView? = null

    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null

    private var ll_StagWise: LinearLayout? = null
    private var ll_ComplaintWise: LinearLayout? = null
    private var ll_ServiceWise: LinearLayout? = null


    lateinit var crmservicewiseViewModel          : CRMservicewiseViewModel
    lateinit var crmStagewiseDetailsViewModel     : CRMStagewiseDetailsViewModel
    lateinit var crmTileDashBoardDetailsViewModel : CRMTileDashBoardDetailsViewModel
    lateinit var crmcomplaintwiseViewModel        : CRMcomplaintwiseViewModel
    lateinit var chartTypeViewModel               : ChartTypeViewModel

    lateinit var chartTypeArrayList: JSONArray
    var ID_ChartMode    :  String? = ""

    //StagWiseChart BarChart
    private lateinit var StagWiseChart: BarChart
    private var modelCRMStageBar = ArrayList<ModelCRMStageBar>()
    lateinit var stageWiseArrayList: JSONArray
    var recycStagWise: RecyclerView? = null

    //ComplaintWiseChart BarChart
    private lateinit var ComplaintWiseChart: BarChart
    private var modelCRMComplaintBar = ArrayList<ModelCRMComplaintBar>()
    lateinit var complaintWiseArrayList: JSONArray

    //ServiceWiseChart BarChart
    private lateinit var ServiceWiseChart: BarChart
    private var modelCRMServiceBar = ArrayList<ModelCRMServiceBar>()
    lateinit var serviceWiseArrayList: JSONArray



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_graph)
        context = this@ServiceGraphActivity


        crmservicewiseViewModel          = ViewModelProvider(this).get(CRMservicewiseViewModel::class.java)
        crmStagewiseDetailsViewModel     = ViewModelProvider(this).get(CRMStagewiseDetailsViewModel::class.java)
        crmTileDashBoardDetailsViewModel = ViewModelProvider(this).get(CRMTileDashBoardDetailsViewModel::class.java)
        crmcomplaintwiseViewModel        = ViewModelProvider(this).get(CRMcomplaintwiseViewModel::class.java)
        chartTypeViewModel               = ViewModelProvider(this).get(ChartTypeViewModel::class.java)

        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")

        Log.e(TAG,"3555   "+SubMode+"  :  "+label)

        TabMode       = 0
        ContinueMode  = 0
        hideViews()

//        crmservicewiseCount = 0
//        getCRMservicewiseData()
//
//        crmStagewiseCount   = 0
//        getCRMStagewiseData()
//
//        TicketOutstandingCount = 0
//        getTicketOutstandingData()
//
////        TicketStatusCount = 0
////        getTicketStatusData()
//
//        crmcomplaintwiseCount = 0
//        getCRMcomplaintwiseData()

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)

        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)

        ll_StagWise         = findViewById<LinearLayout>(R.id.ll_StagWise)
        ll_ComplaintWise    = findViewById<LinearLayout>(R.id.ll_ComplaintWise)
        ll_ServiceWise      = findViewById<LinearLayout>(R.id.ll_ServiceWise)

        StagWiseChart       = findViewById<BarChart>(R.id.StagWiseChart)
        ComplaintWiseChart  = findViewById<BarChart>(R.id.ComplaintWiseChart)
        ServiceWiseChart    = findViewById<BarChart>(R.id.ServiceWiseChart)

        recycStagWise    = findViewById<RecyclerView>(R.id.recycStagWise)

        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)
        actv_mode!!.setOnClickListener(this)

    }

    private fun hideViews() {

        ll_Graph!!.visibility = View.GONE
        ll_Tile!!.visibility = View.GONE

        if (TabMode == 0){
            ll_Graph!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)

            if (ContinueMode == 0){
                ChartMode      = 0
                chartModeCount = 0
                getChartModeData()
            }


        }else if (TabMode == 1){
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

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
                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)

                                            ll_StagWise!!.visibility = View.VISIBLE
                                            crmStagewiseCount   = 0
                                            getCRMStagewiseData()

                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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

                ll_StagWise!!.visibility = View.GONE
                ll_ComplaintWise!!.visibility = View.GONE
                ll_ServiceWise!!.visibility = View.GONE


                if (ID_ChartMode.equals("1")){
                    ll_StagWise!!.visibility = View.VISIBLE
                    crmStagewiseCount   = 0
                    getCRMStagewiseData()
                }
                else if (ID_ChartMode.equals("2")){
                    ll_ServiceWise!!.visibility = View.VISIBLE
                    crmservicewiseCount = 0
                    getCRMservicewiseData()
                }
                else if (ID_ChartMode.equals("3")){
                    ll_ComplaintWise!!.visibility = View.VISIBLE
                    crmcomplaintwiseCount = 0
                    getCRMcomplaintwiseData()
                }
                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }

    private fun getCRMservicewiseData() {
        DashMode = "13"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmservicewiseViewModel.getCRMservicewise(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (crmservicewiseCount == 0) {
                                crmservicewiseCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1066   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("CRMservicewise")
                                    serviceWiseArrayList=jobjt.getJSONArray("CRMservicewiseList")

                                    Log.e(TAG,"3913 serviceWiseArrayList  "+serviceWiseArrayList)
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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

    private fun getCRMStagewiseData() {
        DashMode = "10"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmStagewiseDetailsViewModel.getCRMStagewiseDetails(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmStagewiseCount == 0) {
                                crmStagewiseCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1644   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMStagewiseDetails")
                                    stageWiseArrayList=jobjt.getJSONArray("CRMStagewiseDetailsList")

                                    Log.e(TAG,"3911 stageWiseArrayList  "+stageWiseArrayList)

                                    if (stageWiseArrayList.length() > 0){

                                        setStageBarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycStagWise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmStageChartAdapter(this@ServiceGraphActivity, stageWiseArrayList)
                                        recycStagWise!!.adapter = adapter
                                    }


                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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



    private fun getCRMcomplaintwiseData() {
        DashMode = "11"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmcomplaintwiseViewModel.getCRMcomplaintwise(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmcomplaintwiseCount == 0) {
                                crmcomplaintwiseCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1644   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMcomplaintwise")
                                    complaintWiseArrayList=jobjt.getJSONArray("CRMcomplaintwiseList")

                                    Log.e(TAG,"3912 complaintWiseArrayList  "+complaintWiseArrayList)

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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

    private fun getTicketOutstandingData() {
        DashMode = "4"
        DashType = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmTileDashBoardDetailsViewModel.getCRMTileDashBoardDetails(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg   27661   "+msg)
                        if (msg!!.length > 0) {
                            if (TicketOutstandingCount == 0) {
                                TicketOutstandingCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   27662   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {



                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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
//                            Log.e(TAG,"1154  ")
                        }
                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
                Log.e(TAG,"1155  ")
            }
        }
    }

    private fun getTicketStatusData() {
        DashMode = "5"
        DashType = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmTileDashBoardDetailsViewModel.getCRMTileDashBoardDetails(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (TicketStatusCount == 0) {
                                TicketStatusCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   3299   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceGraphActivity,
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


    private fun setStageBarchart() {
        modelCRMStageBar.clear()
        modelCRMStageBar = getStagWiseList()

        StagWiseChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = StagWiseChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        StagWiseChart.axisRight.isEnabled = false
        //remove legend
        StagWiseChart.legend.isEnabled = false
        StagWiseChart!!.setScaleEnabled(true)
        //remove description label
        StagWiseChart.description.isEnabled = false


        //add animation
        StagWiseChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +325f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLACK

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
        for (i in modelCRMStageBar.indices) {
            val score = modelCRMStageBar[i]
            entries.add(BarEntry(i.toFloat(), score.StatusCount.toFloat()))
        }



        val barDataSet = BarDataSet(entries, "Category")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        StagWiseChart.data = data


        StagWiseChart.invalidate()


    }

    private fun getStagWiseList(): ArrayList<ModelCRMStageBar> {
        for (i in 0 until stageWiseArrayList.length())
        {
            var jsonObject = stageWiseArrayList.getJSONObject(i)
            modelCRMStageBar.add(ModelCRMStageBar(jsonObject.getString("StatusName"),jsonObject.getString("StatusCount")))
        }

        return modelCRMStageBar
    }

    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < modelCRMStageBar.size) {
                modelCRMStageBar[index].StatusName
            } else {
                ""
            }
        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tvv_dash->{
                TabMode = 0
                hideViews()
            }
            R.id.tvv_tile->{
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