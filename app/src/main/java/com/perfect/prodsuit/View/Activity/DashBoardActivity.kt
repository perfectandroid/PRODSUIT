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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.perfect.prodsuit.Model.Score
import com.perfect.prodsuit.Model.ScoreLine
import com.perfect.prodsuit.View.Adapter.DistrictDetailAdapter
import com.perfect.prodsuit.View.Adapter.LineChartAdapter
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
    private var scoreListLine = ArrayList<ScoreLine>()
    lateinit var chartLineArrayList : JSONArray
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

        getLeadsDashBoard()
//        getLeadStatusDashBoard()
//        getLeadStagesDashBoard()

//        setLineChart()
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

                               // val ss = "[{\"Count\": 10,\"Fileds\": \"Hot\"},{\"Count\": 25,\"Fileds\": \"Cool\"},{\"Count\": 55,\"Fileds\": \"Warm\"}]"
                               //  chartLineArrayList = JSONArray(ss)

                                val jobjt = jObject.getJSONObject("LeadsDashBoardDetails")
                                chartLineArrayList = jobjt.getJSONArray("LeadsDashBoardDetailsList")
                                Log.e(TAG,"array  125   "+chartLineArrayList)

                                setLineChart()
                                val recycLineChart = findViewById(R.id.recycLineChart) as RecyclerView
                                val lLayout = GridLayoutManager(this@DashBoardActivity, 1)
                                recycLineChart!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                val adapter = LineChartAdapter(this@DashBoardActivity, chartLineArrayList)
                                recycLineChart!!.adapter = adapter
                              //  adapter.setClickListener(this@DashBoardActivity)


                                //   chartLineArrayList = ss.
//                                chartLineArrayList.
//                                chartLineArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
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
      //  lineDataSet.enableDashedLine(20f,0f,0f)
        val data = LineData(lineDataSet)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
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

    private fun getScoreList1(): ArrayList<ScoreLine> {


//        scoreListLine.add(ScoreLine("", 10))
//        scoreListLine.add(ScoreLine("", 45))
//        scoreListLine.add(ScoreLine("", 55))

        for (i in 0 until chartLineArrayList.length()) {
            //apply your logic
            var jsonObject = chartLineArrayList.getJSONObject(i)
            Log.e(TAG,"404  Count   "+jsonObject.getString("Count"))
            scoreListLine.add(ScoreLine("", jsonObject.getString("Count").toInt()))
        }

//        scoreList.add(Score("HOT", 10))
//        scoreList.add(Score("COOL", 45))
//        scoreList.add(Score("WARM", 55))


        return scoreListLine
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

    inner class MyAxisFormatterLine : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < scoreListLine.size) {
                scoreListLine[index].Linename
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