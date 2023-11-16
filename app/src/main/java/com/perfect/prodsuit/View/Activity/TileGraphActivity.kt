package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.EmployeewiseBar
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmpWiseAdapter
import com.perfect.prodsuit.View.Adapter.LeadTileListAdapter
import com.perfect.prodsuit.View.Adapter.LeadTileOutstandListAdapter
import com.perfect.prodsuit.Viewmodel.EmployeewiseViewModel
import com.perfect.prodsuit.Viewmodel.LeadOutstandViewModel
import com.perfect.prodsuit.Viewmodel.LeadTileViewModel
import org.json.JSONArray
import org.json.JSONObject

class TileGraphActivity : AppCompatActivity() , View.OnClickListener,
    ItemClickListener {
    lateinit var context: Context
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int =0
    internal var month:Int = 0
    private var progressDialog: ProgressDialog? = null
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    private var chipNavigationBar: ChipNavigationBar? = null
    lateinit var leadTileViewModel: LeadTileViewModel
    lateinit var leadOutstandViewModel: LeadOutstandViewModel
    lateinit var employeewiseViewModel: EmployeewiseViewModel
    var rclrvw_lead: RecyclerView? = null
    var rclv_barchart: RecyclerView? = null

    var rclrvw_leadoutstand: RecyclerView? = null
    var dashmoduleCount = 0
    var leadoutstandtile = 0
    var empwisecount = 0

    lateinit var ledaTileArrayList: JSONArray
    lateinit var leadTileSort: JSONArray
    lateinit var leadOutstandArrayList: JSONArray
    lateinit var leadOutstandSort: JSONArray

    private lateinit var barChart: BarChart
    private var scoreListBar = ArrayList<EmployeewiseBar>()
    lateinit var chartBarArrayList: JSONArray
    lateinit var empwiseArrayList: JSONArray


    var TAG  ="TileGraphActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_tilegraph)
        context = this@TileGraphActivity
        leadTileViewModel = ViewModelProvider(this).get(LeadTileViewModel::class.java)
        leadOutstandViewModel = ViewModelProvider(this).get(LeadOutstandViewModel::class.java)
        employeewiseViewModel = ViewModelProvider(this).get(EmployeewiseViewModel::class.java)

        setRegViews()

        context = this@TileGraphActivity
        dashmoduleCount = 0
        getLeadTile()
        getLeadOutstandTile()
      //  getEmployeewiseChart()

    }

    private fun getLeadOutstandTile() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadOutstandViewModel.getLeadOutstandTileCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (leadoutstandtile == 0){
                                    leadoutstandtile++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadoutstandtile   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")


                                        leadOutstandArrayList = jobjt.getJSONArray("LeadTileData")
                                        if (leadOutstandArrayList.length() > 0) {


                                            leadOutstandSort = JSONArray()
                                            for (k in 0 until leadOutstandArrayList.length()) {
                                                val jsonObject = leadOutstandArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                leadOutstandSort.put(jsonObject)
                                            }


                                            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                            rclrvw_leadoutstand!!.setLayoutManager(
                                                LinearLayoutManager(
                                                    this,
                                                    RecyclerView.HORIZONTAL,
                                                    false
                                                )
                                            )
                                            // rclrvw_lead!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = LeadTileOutstandListAdapter(this@TileGraphActivity,leadOutstandSort)
                                            rclrvw_leadoutstand!!.adapter = adapter
                                            adapter.setClickListener(this@TileGraphActivity)






                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TileGraphActivity,
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

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        rclrvw_lead = findViewById<RecyclerView>(R.id.rclrvw_lead)
        rclrvw_leadoutstand = findViewById<RecyclerView>(R.id.rclrvw_leadoutstand)
        rclv_barchart = findViewById<RecyclerView>(R.id.rclv_barchart)

        barChart = findViewById<BarChart>(R.id.barChart);

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    private fun getLeadTile() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadTileViewModel.getLeadTileCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (dashmoduleCount == 0){
                                    dashmoduleCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadtile   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")
                                        var remark =   jobjt.getString("Reamrk")

                                        ledaTileArrayList = jobjt.getJSONArray("LeadTileData")
                                        if (ledaTileArrayList.length() > 0) {


                                            leadTileSort = JSONArray()
                                            for (k in 0 until ledaTileArrayList.length()) {
                                                val jsonObject = ledaTileArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                leadTileSort.put(jsonObject)
                                            }


                                            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                            rclrvw_lead!!.setLayoutManager(
                                                LinearLayoutManager(
                                                    this,
                                                    RecyclerView.HORIZONTAL,
                                                    false
                                                )
                                            )
                                            // rclrvw_lead!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = LeadTileListAdapter(this@TileGraphActivity,leadTileSort,remark)
                                            rclrvw_lead!!.adapter = adapter
                                            adapter.setClickListener(this@TileGraphActivity)



                                            /* val lLayout1 = GridLayoutManager(this@TileGraphActivity, 1)
                                             rclrvw_leadoutstand!!.layoutManager = lLayout1 as RecyclerView.LayoutManager?*/


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TileGraphActivity,
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


    override fun onRestart() {
        super.onRestart()
        getLeadTile()
    }

    override fun onClick(position: Int, data: String) {
        TODO("Not yet implemented")
    }
    private fun getEmployeewiseChart() {
        var leadStagesDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeewiseViewModel.getEmployeewiseChart(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   190 empwise   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("EmployeeWiseTaegetInPercentage")
                                    empwiseArrayList =
                                        jobjt.getJSONArray("EmployeeWiseTaegetDetails")
                                  //  tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  empwise   " + empwiseArrayList)

                                    // setPieChart()
                                    setBarchart()
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                    rclv_barchart!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = EmpWiseAdapter(
                                        this@TileGraphActivity,
                                        empwiseArrayList
                                    )
                                    rclv_barchart!!.adapter = adapter

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@TileGraphActivity,
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


        barChart.legend.textSize = 15f
        barChart.legend.textColor = Color.RED

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.leadstages_color1))
        colors.add(resources.getColor(R.color.leadstages_color2))
        colors.add(resources.getColor(R.color.leadstages_color3))

        /////////////////////




        val entries: ArrayList<BarEntry> = ArrayList()
        val labels = ArrayList<String>()
        for (i in scoreListBar.indices) {
            val score = scoreListBar[i]
            entries.add(BarEntry(i.toFloat(), score.empscore.toFloat()))
            labels.add(score.empname)
        }

        val barDataSet = BarDataSet(entries, labels.toString())
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        barChart.data = data
        barChart.setOnClickListener(View.OnClickListener {
           // ShowEnalargeGraphBar(entries)
        })


        barChart.invalidate()

    }

    private fun getScoreList(): ArrayList<EmployeewiseBar> {


        //   chartBarArrayList

//        scoreListBar.add(Score("John", 56))
//        scoreListBar.add(Score("Rey", 75))
//        scoreListBar.add(Score("Steve", 85))
//        scoreListBar.add(Score("Kevin", 45))
//        scoreListBar.add(Score("Jeff", 63))

        for (i in 0 until empwiseArrayList.length()) {
            //apply your logic
            var jsonObject = empwiseArrayList.getJSONObject(i)
            Log.e(TAG, "422  Count   " + jsonObject.getString("EmpFName"))
            scoreListBar.add(EmployeewiseBar(jsonObject.getString("ActualPercentage").toFloat().toInt(), jsonObject.getString("EmpFName")))
        }

        return scoreListBar
    }
    inner class MyAxisFormatterBar : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < scoreListBar.size) {
                scoreListBar[index].empname
            } else {
                ""
            }
        }
    }


}