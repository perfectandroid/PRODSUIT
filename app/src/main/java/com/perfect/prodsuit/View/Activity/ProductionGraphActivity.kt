package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

class ProductionGraphActivity : AppCompatActivity(), View.OnClickListener {

    var completed_xaxis: TextView? = null
    var completed_yaxis: TextView? = null
    var ll_completed_XY: LinearLayout? = null

    var materialShortage_xaxis: TextView? = null
    var materialShortage_yaxis: TextView? = null
    var ll_materialShortage_XY: LinearLayout? = null
    var ll_materialShortageColor: LinearLayout? = null

    var upcommingstock_xaxis: TextView? = null
    var upcommingstock_yaxis: TextView? = null
    var ll_upcommingstock_XY: LinearLayout? = null

    val TAG: String = "ProductionGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null
    private var ll_UpcomingStock: LinearLayout? = null

    var TabMode = 0 // 0 = Graph , 1 = Tile
    var ContinueMode = 0 // 0 = First , 1 = Second
    var ChartMode = 0 // 0 = First , 1 = Second
    var chartModeCount    = 0
    var TransDate = ""
    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null
    private var actv_mode: AutoCompleteTextView? = null

    var drawableMore : Drawable? = null
    var drawableLess : Drawable? = null

    var SubMode     :  String? = ""
    var label       :  String? = ""

    var DashMode    :  String? = ""
    var DashType    :  String? = ""

    //  upcomingstock
    var upcomingstockCount = 0
    lateinit var upcomingstockViewModel     : UpcomingStockViewModel
    lateinit var upcomingstockArrayList     : JSONArray
    var recyUpcomingStock: RecyclerView?   = null
    private lateinit var UpcomingStockChart : BarChart
    private var upcomingstockBar = ArrayList<UpcomingStockBar>()
    private var ll_recyUpcomingStock: LinearLayout? = null
    private var tvv_head_UpcomingStock: TextView? = null
    private var tvv_lemo_UpcomingStock: TextView? = null
    private var tv_UpcomingStockRemark: TextView? = null
    var lemoupcomingstockMode    = 0  // 0=more , 1 = less
    private var card_tile: CardView? = null
    private var ll_tile: LinearLayout? = null

    //  chartType
    lateinit var chartTypeViewModel               : ChartTypeViewModel
    lateinit var chartTypeArrayList               : JSONArray
    var ID_ChartMode                              :  String? = ""


    //  completedproducts
    var completedproductsCount = 0
    lateinit var completedproductsViewModel     : CompletedProductsViewModel
    lateinit var completedproductsArrayList     : JSONArray
    var recyCompletedProducts: RecyclerView?   = null
    private lateinit var CompletedProductsChart : BarChart
    private var completedproductsBar = ArrayList<CompletedProductsBar>()
    private var ll_recyCompletedProducts: LinearLayout? = null
    private var ll_CompletedProducts: LinearLayout? = null
    private var tvv_head_CompletedProducts: TextView? = null
    private var tvv_lemo_CompletedProducts: TextView? = null
    private var tv_CompletedProductsRemark: TextView? = null
    var completedproductsMode    = 0  // 0=more , 1 = less

    //  Material Shortage
    var materialshortageCount = 0
    lateinit var materialshortageViewModel     : MaterialShortageViewModel
    lateinit var materialshortageArrayList     : JSONArray
    var recyMaterialShortage: RecyclerView?   = null
    private lateinit var MaterialShortageChart : BarChart
    private var materialshortageBar = ArrayList<MaterialShortageBar>()
    private var ll_recyMaterialShortage: LinearLayout? = null
    private var ll_MaterialShortage: LinearLayout? = null
    private var ll_materialrecycview: LinearLayout? = null
    private var tvv_head_MaterialShortage: TextView? = null
    private var tvv_lemo_MaterialShortage: TextView? = null
    private var tv_MaterialShortageRemark: TextView? = null
    var materialshortageMode    = 0  // 0=more , 1 = less

    var jobcardsCount = 0
    lateinit var jobcardsViewModel     : JobcardsViewModel
    private var tv_Production_remark: TextView? = null
    private var tv_Production: TextView? = null
    lateinit var productionTileDashBoardArrayList     : JSONArray
    var recyc_Production: RecyclerView?   = null
    private var ll_production: LinearLayout? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_production_graph)
        context = this@ProductionGraphActivity

        upcomingstockViewModel = ViewModelProvider(this).get(UpcomingStockViewModel::class.java)
        completedproductsViewModel = ViewModelProvider(this).get(CompletedProductsViewModel::class.java)
        materialshortageViewModel = ViewModelProvider(this).get(MaterialShortageViewModel::class.java)
        jobcardsViewModel = ViewModelProvider(this).get(JobcardsViewModel::class.java)
        chartTypeViewModel = ViewModelProvider(this).get(ChartTypeViewModel::class.java)
        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")


        Log.e(TAG,"rrrr12221  "+SubMode)
        TabMode = 0
        ContinueMode = 0
        hideViews()
    }

    private fun setRegViews() {

        completed_xaxis = findViewById<TextView>(R.id.completed_xaxis)
        completed_yaxis = findViewById<TextView>(R.id.completed_yaxis)
        ll_completed_XY = findViewById<LinearLayout>(R.id.ll_completed_XY)

        materialShortage_xaxis = findViewById<TextView>(R.id.materialShortage_xaxis)
        materialShortage_yaxis = findViewById<TextView>(R.id.materialShortage_yaxis)
        ll_materialShortage_XY = findViewById<LinearLayout>(R.id.ll_materialShortage_XY)
        ll_materialShortageColor = findViewById<LinearLayout>(R.id.ll_materialShortageColor)

        upcommingstock_xaxis = findViewById<TextView>(R.id.upcommingstock_xaxis)
        upcommingstock_yaxis = findViewById<TextView>(R.id.upcommingstock_yaxis)
        ll_upcommingstock_XY = findViewById<LinearLayout>(R.id.ll_upcommingstock_XY)

        val imback = findViewById<ImageView>(R.id.imback_project)
        imback!!.setOnClickListener(this)
        drawableMore = resources.getDrawable(R.drawable.dash_more, null)
        drawableLess = resources.getDrawable(R.drawable.dash_less, null)
        ll_Graph = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile  = findViewById<LinearLayout>(R.id.ll_Tile)

        tvv_dash  = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile  = findViewById<TextView>(R.id.tvv_tile)
        actv_mode = findViewById(R.id.actv_mode)


        recyUpcomingStock        = findViewById(R.id.recyUpcomingStock)
        UpcomingStockChart       = findViewById(R.id.UpcomingStockChart)
        ll_UpcomingStock         = findViewById(R.id.ll_UpcomingStock)
        ll_recyUpcomingStock     = findViewById(R.id.ll_recyUpcomingStock)
        tvv_head_UpcomingStock   = findViewById(R.id.tvv_head_UpcomingStock)
        tvv_lemo_UpcomingStock   = findViewById(R.id.tvv_lemo_UpcomingStock)
        tv_UpcomingStockRemark   = findViewById(R.id.tv_UpcomingStockRemark)
        tv_UpcomingStockRemark   = findViewById(R.id.tv_UpcomingStockRemark)
        ll_CompletedProducts     = findViewById(R.id.ll_CompletedProducts)
        ll_recyCompletedProducts     = findViewById(R.id.ll_recyCompletedProducts)
        tvv_head_CompletedProducts     = findViewById(R.id.tvv_head_CompletedProducts)

        recyMaterialShortage         = findViewById(R.id.recyMaterialShortage)
        MaterialShortageChart        = findViewById(R.id.MaterialShortageChart)
        tvv_lemo_MaterialShortage    = findViewById(R.id.tvv_lemo_MaterialShortage)
        tv_MaterialShortageRemark    = findViewById(R.id.tv_MaterialShortageRemark)
        recyCompletedProducts        = findViewById(R.id.recyCompletedProducts)
        CompletedProductsChart       = findViewById(R.id.CompletedProductsChart)
        tvv_lemo_CompletedProducts   = findViewById(R.id.tvv_lemo_CompletedProducts)
        ll_recyMaterialShortage      = findViewById(R.id.ll_recyMaterialShortage)
        tvv_head_MaterialShortage    = findViewById(R.id.tvv_head_MaterialShortage)
        ll_MaterialShortage          = findViewById(R.id.ll_MaterialShortage)
        ll_materialrecycview          = findViewById(R.id.ll_materialrecycview)
        tv_CompletedProductsRemark   = findViewById(R.id.tv_CompletedProductsRemark)


        card_tile   = findViewById(R.id.card_tile)
        tv_Production_remark   = findViewById(R.id.tv_Production_remark)
        tv_Production   = findViewById(R.id.tv_Production)
        recyc_Production   = findViewById(R.id.recyc_Production)
        ll_production   = findViewById(R.id.ll_production)

        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)
        actv_mode!!.setOnClickListener(this)
        tvv_lemo_UpcomingStock!!.setOnClickListener(this)
        tvv_lemo_CompletedProducts!!.setOnClickListener(this)
        tvv_lemo_MaterialShortage!!.setOnClickListener(this)


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

            TransDate = sdfDate2.format(newDate)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
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
                getChartModeData()
//                getUpcomingStock()
//                getCompletedProducts()
//                getMaterialShortage()
            }


        } else if (TabMode == 1) {
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

            jobcardsCount = 0
            getJobcards()
//
//            billingStatusCount = 0
//            getBillingStatus()

        }
        getCurrentDate()

    }



    private fun getChartModeData() {
        var ReqMode = ""
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

                                    val jobjt = jObject.getJSONObject("DashBoardNameDetails")
                                    chartTypeArrayList = jobjt.getJSONArray("DashBoardNameDetailsList")


                                    if (chartTypeArrayList.length() > 0){

                                        if (ChartMode == 0){
                                            val jsonObject = chartTypeArrayList.getJSONObject(0)
                                            ID_ChartMode = jsonObject.getString("DashMode")
                                            actv_mode!!.setText(jsonObject.getString("DashBoardName"))

                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)
                                            DashMode =  jsonObject.getString("DashMode")

                                            ll_MaterialShortage!!.visibility = View.GONE
                                            ll_CompletedProducts!!.visibility = View.GONE
                                            ll_UpcomingStock!!.visibility = View.GONE


                                            if (ID_ChartMode.equals("34")){

                                                ll_UpcomingStock!!.visibility = View.VISIBLE
                                                tvv_head_UpcomingStock!!.setText(jsonObject.getString("DashBoardName"))
                                                upcomingstockCount   = 0
                                                getUpcomingStock()
                                            }
                                            else if (ID_ChartMode.equals("35")){
//                                                ll_MaterialShortage!!.visibility = View.VISIBLE
                                                tvv_head_MaterialShortage!!.setText(jsonObject.getString("DashBoardName"))
                                                materialshortageCount = 0
                                                getMaterialShortage()
                                            }
                                            else if (ID_ChartMode.equals("36")){
//                                                ll_CompletedProducts!!.visibility = View.VISIBLE
                                                tvv_head_CompletedProducts!!.setText(jsonObject.getString("DashBoardName"))
                                                completedproductsCount = 0
                                                getCompletedProducts()
                                            }

                                            Log.e(TAG,"drop down 7877   "+chartTypeArrayList)
                                        }else{
                                            Log.e(TAG,"drop down 7877   "+chartTypeArrayList)
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ProductionGraphActivity,
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

        Log.e(TAG,"drop down 7877   "+chartTypeArrayList)

        var modeType = Array<String>(chartTypeArrayList.length()) { "" }
        var modeTypeID = Array<String>(chartTypeArrayList.length()) { "" }
        var modeDashMode = Array<String>(chartTypeArrayList.length()) { "" }
        for (i in 0 until chartTypeArrayList.length()) {
            val objects: JSONObject = chartTypeArrayList.getJSONObject(i)


            modeType[i] = objects.getString("DashBoardName");
            modeTypeID[i] = objects.getString("DashMode");
            modeDashMode[i] = objects.getString("DashMode");
            //   ID_ChartMode = objects.getString("ID_Mode")

            Log.e(TAG, "00000111   " + ID_ChartMode)
            Log.e(TAG, "85456214   " + modeType)

            val adapter = CustomAdapter(this, R.layout.custom_dropdown_item, modeType)
            // val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
                ID_ChartMode = modeTypeID[position]
                DashMode = modeDashMode[position]

                Log.e(TAG, "0000011122   " + ID_ChartMode)
                ll_MaterialShortage!!.visibility = View.GONE
                ll_CompletedProducts!!.visibility = View.GONE
                ll_UpcomingStock!!.visibility = View.GONE
                lemoupcomingstockMode = 0
                materialshortageMode = 0
                completedproductsMode = 0

                if (ID_ChartMode.equals("34")){
                    // ll_StagWise!!.visibility = View.VISIBLE
                    tvv_head_UpcomingStock!!.setText(modeType[position])
                    upcomingstockCount   = 0
                    getUpcomingStock()
                }
                else if (ID_ChartMode.equals("35")){
                    //  ll_ComplaintWise!!.visibility = View.VISIBLE
                    tvv_head_MaterialShortage!!.setText(modeType[position])
                    materialshortageCount = 0
                    getMaterialShortage()
                }
                else if (ID_ChartMode.equals("36")){
                    //   ll_ServiceWise!!.visibility = View.VISIBLE
                    tvv_head_CompletedProducts!!.setText(modeType[position])
                    completedproductsCount = 0
                    getCompletedProducts()
                }

                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }


    //////////////////UpcomingStock\\\\\\\\\\\\\\\\\

    private fun getUpcomingStock() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                upcomingstockViewModel.getUpcomingStock(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   "+msg)
                        try {
                            if (msg!!.length > 0) {

                                if (upcomingstockCount == 0){
                                    upcomingstockCount++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        ll_upcommingstock_XY!!.visibility = View.VISIBLE
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("ProductionUpcomingStock")
                                        tv_UpcomingStockRemark!!.setText(jobjt.getString("Reamrk"))
                                        upcommingstock_xaxis!!.setText(jobjt.getString("XAxis"))
                                        upcommingstock_yaxis!!.setText(jobjt.getString("YAxis"))

                                        upcomingstockArrayList=jobjt.getJSONArray("ProductionUpcomingStockList")
                                        Log.e(TAG, "upcomingstockArrayList==   "+upcomingstockArrayList)


                                        try {
                                            if (upcomingstockArrayList.length() > 0) {

                                                ll_UpcomingStock!!.visibility = View.VISIBLE
                                                lemoupcomingstockMode = 0
                                                hidelemoupcomingstock()

                                                setUpcomingStockListBarchart()

                                                val lLayout = GridLayoutManager(this@ProductionGraphActivity, 2)
                                                recyUpcomingStock!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = UpcomingStockAdapter(this@ProductionGraphActivity, upcomingstockArrayList)
                                                recyUpcomingStock!!.adapter = adapter
                                                Log.e(TAG, "stockListSortArrayList==2   "+upcomingstockArrayList)
                                            }

                                        }
                                        catch (e:Exception)
                                        {
                                            Log.e("exceptionStock344",""+e.toString())
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProductionGraphActivity,
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
                                "sdsds" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG,"dscfavcdsafvcada  "+e.toString())
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


    private fun setUpcomingStockListBarchart() {
        upcomingstockBar.clear()
        upcomingstockBar = getUpcomingStockBarList()
        UpcomingStockChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = UpcomingStockChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        UpcomingStockChart.axisRight.isEnabled = false
        //remove legend
        UpcomingStockChart.legend.isEnabled = false
        UpcomingStockChart!!.setScaleEnabled(true)
        //remove description label
        UpcomingStockChart.description.isEnabled = false


        //add animation
        UpcomingStockChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
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
        for (i in upcomingstockBar.indices) {
            val score = upcomingstockBar[i]
            entries.add(BarEntry(i.toFloat(), score.Quantity.toFloat()))
        }



        val barDataSet = BarDataSet(entries, "Product")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        UpcomingStockChart.data = data


        UpcomingStockChart.invalidate()

    }

    private fun getUpcomingStockBarList(): ArrayList<UpcomingStockBar> {
        for (i in 0 until upcomingstockArrayList.length())
        {
            var jsonObject = upcomingstockArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            upcomingstockBar.add(UpcomingStockBar("",jsonObject.getString("Quantity").toFloat().toInt()))
        }

        return upcomingstockBar

    }

    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < upcomingstockBar.size) {
                upcomingstockBar[index].Product
            } else {
                ""
            }
        }
    }

    private fun hidelemoupcomingstock() {
        if (lemoupcomingstockMode == 0){
            tvv_lemo_UpcomingStock!!.setText("More")
            tvv_lemo_UpcomingStock!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_recyUpcomingStock!!.visibility = View.GONE
        }else{
            tvv_lemo_UpcomingStock!!.setText("Less")
            tvv_lemo_UpcomingStock!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_recyUpcomingStock!!.visibility = View.VISIBLE
        }
    }


    ////////////////CompletedProducts\\\\\\\\\\\\

    private fun getCompletedProducts() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                completedproductsViewModel.getCompletedProducts(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   "+msg)
                        try {
                            if (msg!!.length > 0) {

                                if (completedproductsCount == 0){
                                    completedproductsCount++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        ll_completed_XY!!.visibility = View.VISIBLE
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("ProductionCompletedProducts")
                                        tv_CompletedProductsRemark!!.setText(jobjt.getString("Reamrk"))
                                        completed_xaxis!!.setText(jobjt.getString("XAxis"))
                                        completed_yaxis!!.setText(jobjt.getString("YAxis"))
                                        completedproductsArrayList=jobjt.getJSONArray("ProductionCompletedProductsList")

                                        try {
                                            if (completedproductsArrayList.length() > 0) {
                                                ll_CompletedProducts!!.visibility = View.VISIBLE

                                                completedproductsMode = 0
                                                hidelemocompletedProducts()

                                                setCompletedProductsBarchart()

                                                val lLayout = GridLayoutManager(this@ProductionGraphActivity, 2)
                                                recyCompletedProducts!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = CompletedProductsAdapter(this@ProductionGraphActivity, completedproductsArrayList)
                                                recyCompletedProducts!!.adapter = adapter
                                                Log.e(TAG, "completedproductsArrayList==1   "+completedproductsArrayList)

                                            }
                                            Log.e(TAG, "completedproductsArrayList==   "+completedproductsArrayList)
                                        }
                                        catch (e:Exception)
                                        {
                                            Log.e("exceptionStock344",""+e.toString())
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProductionGraphActivity,
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
                                "ffffffffgg" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG,"ggggjklghjbtuybgu "+e.toString())
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


    private fun setCompletedProductsBarchart() {
        completedproductsBar.clear()
        completedproductsBar = getCompletedProductsBarList()
        CompletedProductsChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = CompletedProductsChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        CompletedProductsChart.axisRight.isEnabled = false
        //remove legend
        CompletedProductsChart.legend.isEnabled = false
        CompletedProductsChart!!.setScaleEnabled(true)
        //remove description label
        CompletedProductsChart.description.isEnabled = false


        //add animation
        CompletedProductsChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar3()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
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
        for (i in completedproductsBar.indices) {
            val score = completedproductsBar[i]
            entries.add(BarEntry(i.toFloat(), score.Quantity.toFloat()))
        }



        val barDataSet = BarDataSet(entries, "Product")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        CompletedProductsChart.data = data


        CompletedProductsChart.invalidate()

    }

    private fun getCompletedProductsBarList(): ArrayList<CompletedProductsBar> {
        for (i in 0 until completedproductsArrayList.length())
        {
            var jsonObject = completedproductsArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            completedproductsBar.add(CompletedProductsBar("",jsonObject.getString("Quantity").toFloat().toInt()))
        }

        return completedproductsBar

    }

    inner class MyAxisFormatterBar3 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < completedproductsBar.size) {
                completedproductsBar[index].Product
            } else {
                ""
            }
        }
    }

    private fun hidelemocompletedProducts() {
        if (completedproductsMode == 0){
            tvv_lemo_CompletedProducts!!.setText("More")
            tvv_lemo_CompletedProducts!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_recyCompletedProducts!!.visibility = View.GONE
        }else{
            tvv_lemo_CompletedProducts!!.setText("Less")
            tvv_lemo_CompletedProducts!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_recyCompletedProducts!!.visibility = View.VISIBLE
        }
    }




    /////////////////MaterialShortage\\\\\\\\\\\\\\\\\

    private fun getMaterialShortage() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialshortageViewModel.getMaterialShortage(this,TransDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg materialshortageArrayList==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (materialshortageCount == 0) {
                                    materialshortageCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   materialshortageArrayList   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        ll_materialShortage_XY!!.visibility = View.VISIBLE
                                        ll_materialShortageColor!!.visibility = View.VISIBLE

                                        val jobjt =
                                            jObject.getJSONObject("ProductionMaterialShortage")
                                        tv_MaterialShortageRemark!!.setText(jobjt.getString("Reamrk"))
                                        materialShortage_xaxis!!.setText(jobjt.getString("XAxis"))
                                        materialShortage_yaxis!!.setText(jobjt.getString("YAxis"))

                                        materialshortageArrayList=jobjt.getJSONArray("ProductionMaterialShortageList")

                                        if (materialshortageArrayList.length() > 0) {
                                            Log.e(TAG, "materialshortageArrayList 43434  =  "+materialshortageArrayList)
                                            ll_MaterialShortage!!.visibility = View.VISIBLE

                                            materialshortageMode = 0
                                            hidelemomaterialshortage()
                                            setgetMaterialShortageBarchart()  //...........barchart here

                                            val lLayout = GridLayoutManager(this@ProductionGraphActivity, 1)
                                            recyMaterialShortage!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = MaterialShortageAdapter(this@ProductionGraphActivity, materialshortageArrayList)
                                            recyMaterialShortage!!.adapter = adapter

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProductionGraphActivity,
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

    private fun setgetMaterialShortageBarchart() {
        materialshortageBar.clear()
        materialshortageBar = getMaterialShortagesBarList()

        MaterialShortageChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = MaterialShortageChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        MaterialShortageChart.axisRight.isEnabled = false
        //remove legend
        MaterialShortageChart.legend.isEnabled = false
        MaterialShortageChart!!.setScaleEnabled(true)
        //remove description label
        MaterialShortageChart.description.isEnabled = false


        //add animation
        MaterialShortageChart.animateY(1000)

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
        for (i in materialshortageBar.indices) {
            val score = materialshortageBar[i]
            entries1.add(BarEntry(i.toFloat(), score.ActualQuantity.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in materialshortageBar.indices) {
            val score = materialshortageBar[i]
            entries2.add(BarEntry(i.toFloat(), score.ShortageQuantity.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(resources.getColor(R.color.expense_color1))
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(resources.getColor(R.color.gain_color1))
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
        baraData.setDrawValues(false)
        MaterialShortageChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        MaterialShortageChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        MaterialShortageChart.invalidate()




    }

    inner class MyAxisFormatterBar1 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < materialshortageBar.size) {
                materialshortageBar[index].Product
            } else {
                ""
            }
        }
    }


    private fun getMaterialShortagesBarList(): ArrayList<MaterialShortageBar> {
        for (i in 0 until materialshortageArrayList.length())
        {
            var jsonObject = materialshortageArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            materialshortageBar.add(MaterialShortageBar("",jsonObject.getString("ActualQuantity").toFloat().toInt(),jsonObject.getString("ShortageQuantity").toFloat().toInt()))
        }

        return materialshortageBar

    }

    private fun hidelemomaterialshortage() {
        if (materialshortageMode == 0){
            tvv_lemo_MaterialShortage!!.setText("More")
            tvv_lemo_MaterialShortage!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_materialrecycview!!.visibility = View.GONE
        }else{
            tvv_lemo_MaterialShortage!!.setText("Less")
            tvv_lemo_MaterialShortage!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_materialrecycview!!.visibility = View.VISIBLE
        }
    }

    //////////////////////Job cards\\\\\\\\\\\\\\\\\\\\\

    private fun getJobcards() {
        var DashMode = "13"
        var DashType = "1"
        ll_production!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                jobcardsViewModel.getJobcards(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg Status  276622   "+msg)
                        if (msg!!.length > 0) {
                            if (jobcardsCount == 0) {
                                jobcardsCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   88442   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("ProductionTileDashBoard")
                                    tv_Production!!.setText(jobjt.getString("ChartName"))
                                    tv_Production_remark!!.setText(jobjt.getString("Reamrk"))
                                    productionTileDashBoardArrayList = jobjt.getJSONArray("ProductionTileDashBoardList")
                                    if (productionTileDashBoardArrayList.length() > 0){
                                        ll_production!!.visibility = View.VISIBLE
                                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                        recyc_Production!!.layoutManager = layoutManager
                                        val adapter = ProductionAdapter(this@ProductionGraphActivity, productionTileDashBoardArrayList)
                                        recyc_Production!!.adapter = adapter
                                    }


                                } else {
//                                    val builder = AlertDialog.Builder(
//                                        this@ServiceGraphActivity,
//                                        R.style.MyDialogTheme
//                                    )
//                                    builder.setMessage(jObject.getString("EXMessage"))
//                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    }
//                                    val alertDialog: AlertDialog = builder.create()
//                                    alertDialog.setCancelable(false)
//                                    alertDialog.show()
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
                //  progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
                Log.e(TAG,"1155  ")
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imback_project -> {
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

            R.id.tvv_lemo_UpcomingStock->{

                if (lemoupcomingstockMode == 0){
                    lemoupcomingstockMode = 1
                }else{
                    lemoupcomingstockMode = 0
                }
                hidelemoupcomingstock()

            }
            R.id.tvv_lemo_CompletedProducts->{

                if (completedproductsMode == 0){
                    completedproductsMode = 1
                }else{
                    completedproductsMode = 0
                }
                hidelemocompletedProducts()

            }
            R.id.tvv_lemo_MaterialShortage->{

                if (materialshortageMode == 0){
                    materialshortageMode = 1
                }else{
                    materialshortageMode = 0
                }
                hidelemomaterialshortage()

            }
        }
    }


//    private fun hidelemoupcomingstock() {
//        if (lemoupcomingstockMode == 0){
//            tvv_lemo_UpcomingStock!!.setText("More")
//            tvv_lemo_UpcomingStock!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
//            ll_recyUpcomingStock!!.visibility = View.GONE
//        }else{
//            tvv_lemo_UpcomingStock!!.setText("Less")
//            tvv_lemo_UpcomingStock!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
//            ll_recyUpcomingStock!!.visibility = View.VISIBLE
//        }
//    }
}