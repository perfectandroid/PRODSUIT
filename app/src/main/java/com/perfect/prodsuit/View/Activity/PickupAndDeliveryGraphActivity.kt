package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.VehicleDetailsBarModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.PickUpDeliveryOrderTrackTileAdapter
import com.perfect.prodsuit.View.Adapter.VehicleDetailAdapter
import com.perfect.prodsuit.Viewmodel.PickupAndDeliveryOrderTrackingTileViewModel
import com.perfect.prodsuit.Viewmodel.PickupAndDeliveryVehicleDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class PickupAndDeliveryGraphActivity : AppCompatActivity(), View.OnClickListener {


    val TAG: String = "PickupAndDeliveryGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var drawableMore : Drawable? = null
    var drawableLess : Drawable? = null

    var SubMode     :  String? = ""
    var label       :  String? = ""

    var DashMode    :  String? = ""
    var DashType    :  String? = ""

    lateinit var pickupAndDeliveryVehicleDetailsViewModel: PickupAndDeliveryVehicleDetailsViewModel
    lateinit var vehicleDetailsArrayList: JSONArray
    private var  vehicleDetailsCount = 0
    var recycprojectDelayed: RecyclerView? = null
    private lateinit var vehicleDetailChart: BarChart
    //private var projectDelayListBar = ArrayList<ProjectDelayBarModel>()
    private var vehicleDetailsBarModel = ArrayList<VehicleDetailsBarModel>()

    lateinit var pickupAndDeliveryOrderTrackingTileViewModel: PickupAndDeliveryOrderTrackingTileViewModel
    private var  trackingTileCount = 0
    lateinit var orderTrackingTileArrayList: JSONArray


    var TabMode = 0 // 0 = Graph , 1 = Tile
    var ContinueMode = 0 // 0 = First , 1 = Second
    var ChartMode = 0 // 0 = First , 1 = Second
    var chartModeCount    = 0
    var vehicleDetailListMode    = 0

    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null
    private var tvv_lemo_VehicleDetails: TextView? = null

    private var tv_VehicleDetailRemark: TextView? = null
    private var VehicleDetail_xaxis: TextView? = null
    private var VehicleDetail_yaxis: TextView? = null


    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null

    private var ll_vehicleDetailRecyc: LinearLayout? = null
    private var ll_VehicleDetail_XY: LinearLayout? = null
    private var ll_VehicleDetailColor: LinearLayout? = null


    private var ll_OrderTrackingTile: LinearLayout? = null
    private var OrderTrackingTile: LinearLayout? = null
    private var tv_nameOrderTrackingTle: TextView? = null
    private var tv_remarkOrderTrackingTle: TextView? = null
    private var recycler_OrderTracking_tile: RecyclerView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pickup_and_delivery_graph)
        context = this@PickupAndDeliveryGraphActivity

        pickupAndDeliveryVehicleDetailsViewModel = ViewModelProvider(this).get(PickupAndDeliveryVehicleDetailsViewModel::class.java)
        pickupAndDeliveryOrderTrackingTileViewModel = ViewModelProvider(this).get(PickupAndDeliveryOrderTrackingTileViewModel::class.java)

        setRegViews()

        vehicleDetailsCount = 0
        getVehicleDetailsGraph()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback_project)
        imback!!.setOnClickListener(this)

        ll_Graph = findViewById(R.id.ll_Graph)
        ll_Tile  = findViewById(R.id.ll_Tile)

        ll_vehicleDetailRecyc  = findViewById(R.id.ll_vehicleDetailRecyc)
        ll_VehicleDetail_XY  = findViewById(R.id.ll_VehicleDetail_XY)
        ll_VehicleDetailColor  = findViewById(R.id.ll_VehicleDetailColor)

        tvv_dash = findViewById(R.id.tvv_dash)
        tvv_tile = findViewById(R.id.tvv_tile)
        tvv_lemo_VehicleDetails = findViewById(R.id.tvv_lemo_VehicleDetails)

        tv_VehicleDetailRemark = findViewById(R.id.tv_VehicleDetailRemark)
        VehicleDetail_xaxis = findViewById(R.id.VehicleDetail_xaxis)
        VehicleDetail_yaxis = findViewById(R.id.VehicleDetail_yaxis)

        recycprojectDelayed                  = findViewById(R.id.recycprojectDelayed)
        vehicleDetailChart = findViewById(R.id.vehicleDetailChart)

        ll_OrderTrackingTile = findViewById(R.id.ll_OrderTrackingTile)
        OrderTrackingTile = findViewById(R.id.OrderTrackingTile)
        tv_nameOrderTrackingTle = findViewById(R.id.tv_nameOrderTrackingTle)
        tv_remarkOrderTrackingTle = findViewById(R.id.tv_remarkOrderTrackingTle)
        recycler_OrderTracking_tile = findViewById(R.id.recycler_OrderTracking_tile)

        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)
        tvv_lemo_VehicleDetails!!.setOnClickListener(this)
    }

    private fun getVehicleDetailsGraph() {
        ll_VehicleDetail_XY!!.visibility = View.GONE
        ll_VehicleDetailColor!!.visibility = View.GONE

        var TransDate = Common.getCurrentDateNTime("1")
        DashMode = "37"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupAndDeliveryVehicleDetailsViewModel.getPickupAndDeliveryVehicleDetailsData(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (vehicleDetailsCount == 0) {
                                vehicleDetailsCount++

                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    vehicleDetailsArrayList = JSONArray()
                                    ll_Graph!!.visibility = View.VISIBLE
                                    ll_VehicleDetail_XY!!.visibility = View.VISIBLE
                                    ll_VehicleDetailColor!!.visibility = View.VISIBLE
                                    Log.e(TAG,"jObject  12001  "+jObject)
                                    val jsonObj: JSONObject = jObject.getJSONObject("VehicleDetails")
                                    vehicleDetailsArrayList = jsonObj.getJSONArray("VehicleDetailsList")
                                    Log.e(TAG,"jresult  12002  "+vehicleDetailsArrayList)
                                    tv_VehicleDetailRemark!!.setText(jsonObj.getString("Reamrk"))
                                    VehicleDetail_xaxis!!.setText(jsonObj.getString("XAxis"))
                                    VehicleDetail_yaxis!!.setText(jsonObj.getString("YAxis"))

                                    if (vehicleDetailsArrayList.length() > 0) {
                                        hideVehicleDetailMore()
                                        setVehicleDetailsBarchart()


                                        val lLayout = GridLayoutManager(this@PickupAndDeliveryGraphActivity, 1)
                                        recycprojectDelayed!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = VehicleDetailAdapter(this@PickupAndDeliveryGraphActivity, vehicleDetailsArrayList)
                                        recycprojectDelayed!!.adapter = adapter
                                    }


                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {

                                    val builder = AlertDialog.Builder(
                                        this@PickupAndDeliveryGraphActivity,
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
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
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

    private fun setVehicleDetailsBarchart() {
        vehicleDetailsBarModel.clear()
        vehicleDetailsBarModel = getProjectDelayBarList()

        vehicleDetailChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = vehicleDetailChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis

        vehicleDetailChart.axisRight.isEnabled = false
        //remove legend
        vehicleDetailChart.legend.isEnabled = false
        vehicleDetailChart!!.setScaleEnabled(true)
        //remove description label
        vehicleDetailChart.description.isEnabled = false


        //add animation
        vehicleDetailChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar66()
        xAxis.setDrawLabels(false)
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
        for (i in vehicleDetailsBarModel.indices) {
            val score = vehicleDetailsBarModel[i]
            entries1.add(BarEntry(i.toFloat(), score.Pickup.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in vehicleDetailsBarModel.indices) {
            val score = vehicleDetailsBarModel[i]
            entries2.add(BarEntry(i.toFloat(), score.Delivery.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(resources.getColor(R.color.expense_color1))
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(resources.getColor(R.color.gain_color1))
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

        val baraData= BarData(barDataSet1,barDataSet2)
        baraData.barWidth=0.3f

        baraData.setDrawValues(false)
        vehicleDetailChart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        vehicleDetailChart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        vehicleDetailChart.invalidate()

    }

    private fun getProjectDelayBarList(): ArrayList<VehicleDetailsBarModel> {
        for (i in 0 until vehicleDetailsArrayList.length())
        {
            var jsonObject = vehicleDetailsArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            vehicleDetailsBarModel.add(VehicleDetailsBarModel(jsonObject.getString("Vehicle"),jsonObject.getString("Pickup").toFloat().toInt(),jsonObject.getString("Delivery").toFloat().toInt()))
        }

        return vehicleDetailsBarModel

    }

    inner class MyAxisFormatterBar66 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < vehicleDetailsBarModel.size) {
                vehicleDetailsBarModel[index].Vehicle
            } else {
                ""
            }
        }
    }

    private fun getOrderTrackingTile() {

        ll_OrderTrackingTile!!.visibility = View.GONE
        var TransDate = Common.getCurrentDateNTime("1")
        DashMode = "14"
        DashType = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupAndDeliveryOrderTrackingTileViewModel.getPickupAndDeliveryOrderTrackingTileData(this,TransDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (trackingTileCount == 0) {
                                trackingTileCount++
                                orderTrackingTileArrayList = JSONArray()
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {


                                    val jobjt = jObject.getJSONObject("OrderTrackingTileDashBoard")

                                    orderTrackingTileArrayList=jobjt.getJSONArray("PickUpTileData")
                                    if (orderTrackingTileArrayList.length() > 0){
                                        ll_OrderTrackingTile!!.visibility = View.VISIBLE
                                        var ChartName =   jobjt.getString("ChartName")
                                        var remark =   jobjt.getString("Reamrk")
                                        tv_nameOrderTrackingTle!!.text = ChartName
                                        tv_remarkOrderTrackingTle!!.text = remark


                                        val lLayout = GridLayoutManager(this@PickupAndDeliveryGraphActivity, 3)
                                        recycler_OrderTracking_tile!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter1 = PickUpDeliveryOrderTrackTileAdapter(this@PickupAndDeliveryGraphActivity, orderTrackingTileArrayList,remark)
                                        recycler_OrderTracking_tile!!.adapter = adapter1

                                    }


                                Log.e(TAG,"jObject  3699   "+jObject)

                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {

                                    val builder = AlertDialog.Builder(
                                        this@PickupAndDeliveryGraphActivity,
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
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
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



    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback_project -> {
                finish()
            }

            R.id.tvv_dash -> {
                vehicleDetailListMode = 0
                TabMode = 0
                hideViews()
            }

            R.id.tvv_tile -> {
                TabMode = 1
                hideViews()
            }
            R.id.tvv_lemo_VehicleDetails -> {
                if (vehicleDetailListMode == 0){
                    vehicleDetailListMode = 1
                }else{
                    vehicleDetailListMode = 0
                }

                hideVehicleDetailMore()
            }


        }

    }

    private fun hideVehicleDetailMore() {
        if (vehicleDetailListMode == 0){
            tvv_lemo_VehicleDetails!!.setText("More")
            tvv_lemo_VehicleDetails!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_vehicleDetailRecyc!!.visibility = View.GONE
        }else{
            tvv_lemo_VehicleDetails!!.setText("Less")
            tvv_lemo_VehicleDetails!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_vehicleDetailRecyc!!.visibility = View.VISIBLE
        }
    }

    private fun hideViews() {

        ll_Graph!!.visibility = View.GONE
        ll_Tile!!.visibility  = View.GONE

        if (TabMode == 0) {
          //  ll_Graph!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)

            vehicleDetailsCount = 0
            getVehicleDetailsGraph()


        } else if (TabMode == 1) {
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

            trackingTileCount = 0
            getOrderTrackingTile()

        }


    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }


}