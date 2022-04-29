package com.perfect.prodsuit.View.Activity

import android.app.*
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.*
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.perfect.prodsuit.Model.Score
import com.perfect.prodsuit.Viewmodel.LeadDashViewModel
import com.perfect.prodsuit.Viewmodel.LeadStagesDashViewModel
import com.perfect.prodsuit.Viewmodel.LeadStatusDashViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class DashBoardActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "LeadNextActionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
  //  private var chipNavigationBar: ChipNavigationBar? = null
    private var lineChart: LineChart? = null
    var lineData: LineData? = null
    var entryList: List<Map.Entry<*, *>> = ArrayList()

    lateinit var leadDashViewModel: LeadDashViewModel
    lateinit var leadDashArrayList : JSONArray

    lateinit var leadStatusDashViewModel: LeadStatusDashViewModel
    lateinit var leadStatusDashArrayList : JSONArray

    lateinit var leadStagesDashViewModel: LeadStagesDashViewModel
    lateinit var leadStagesDashArrayList : JSONArray



    //Barchart
    private lateinit var barChart: BarChart
    private var scoreList = ArrayList<Score>()

//    PiChart
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        context = this@DashBoardActivity

        leadDashViewModel = ViewModelProvider(this).get(LeadDashViewModel::class.java)
        leadStatusDashViewModel = ViewModelProvider(this).get(LeadStatusDashViewModel::class.java)
        leadStagesDashViewModel = ViewModelProvider(this).get(LeadStagesDashViewModel::class.java)

        setRegViews()
      //  bottombarnav()

//        getLeadsDashBoard()
//        getLeadStatusDashBoard()
        getLeadStagesDashBoard()

        setLineChart()
        setBarchart()  //working
        setPieChart()

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
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   100   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
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
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   100   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
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

    private fun getLeadStagesDashBoard() {

    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        lineChart = findViewById<LineChart>(R.id.chart1);
        barChart = findViewById<BarChart>(R.id.barChart);
        pieChart = findViewById<PieChart>(R.id.pieChart);


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }


    private fun setLineChart() {

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


        //add animation
        lineChart!!.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLACK

        ///////////////


        val entries: ArrayList<Entry> = ArrayList()

        scoreList = getScoreList1()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.score.toFloat()))
        }


        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setCircleColor(Color.RED)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.enableDashedLine(20f,0f,0f)
        val data = LineData(lineDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.RED)
        lineChart!!.data = data


        lineChart!!.invalidate()


    }

    private fun setBarchart() {
//        https://intensecoder.com/bar-chart-tutorial-in-android-using-kotlin/
        scoreList.clear()
        scoreList = getScoreList()

        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)


        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false


        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.WHITE


//        barChart.legend.textSize = 15f
//        barChart.legend.textColor = Color.RED



        /////////////////////

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.RED)
        barChart.data = data



        barChart.invalidate()

    }

    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("John", 56))
        scoreList.add(Score("Rey", 75))
        scoreList.add(Score("Steve", 85))
        scoreList.add(Score("Kevin", 45))
        scoreList.add(Score("Jeff", 63))

        return scoreList
    }

    private fun getScoreList1(): ArrayList<Score> {
        scoreList.add(Score("John", 10))
        scoreList.add(Score("Rey", 45))
        scoreList.add(Score("Steve", 55))
        scoreList.add(Score("Kevin", 65))
        scoreList.add(Score("Jeff", 85))

        return scoreList
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun setPieChart() {

//        https://intensecoder.com/piechart-tutorial-using-mpandroidchart-in-kotlin/

        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true

        ////

        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(72f, "Android"))
        dataEntries.add(PieEntry(26f, "Ios"))
        dataEntries.add(PieEntry(2f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Mobile OS Market share"



        pieChart.invalidate()
    }


}