package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimalRemover
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.EmployeewiseBarLead
import com.perfect.prodsuit.Model.ModelCRMStageBar
import com.perfect.prodsuit.Model.ScoreBar
import com.perfect.prodsuit.Model.ScorePie
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class TileGraphActivity : AppCompatActivity() , View.OnClickListener,
    ItemClickListener {
    lateinit var context: Context
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var tile1: LinearLayout? = null
    internal var tile2: LinearLayout? = null
    private var actv_mode: AutoCompleteTextView? = null
    var ID_ChartMode    :  String? = ""
    internal var ll_tile1: LinearLayout? = null
    internal var ll_leadstage: LinearLayout? = null
    lateinit var chartTypeArrayList: JSONArray
    internal var ll_tile2: LinearLayout? = null
    internal var ll_tiles: LinearLayout? = null
    internal var firstpage: LinearLayout? = null
    internal var ll_empwise: LinearLayout? = null
    lateinit var chartTypeViewModel: ChartTypeViewModel
    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null
    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null
    var TabMode    = 0
    var ChartMode    = 0
    var chartModeCount    = 0
    var ContinueMode    = 0
    internal var ll_Piechart: LinearLayout? = null
    internal var ll_barchart: LinearLayout? = null
    internal var ll_top10: LinearLayout? = null

    private var dialogCategory : Dialog? = null
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
    lateinit var leadStatusDashViewModel: LeadStatusDashViewModel
    lateinit var leadStagesForecastDashViewModel: LeadStagesForecastDashViewModel
    var rclrvw_lead: RecyclerView? = null
    var rclv_barchart: RecyclerView? = null
    var rclv_leadStatus: RecyclerView? = null
    var rclv_leadStagewiseforecast: RecyclerView? = null

    var crdv_empwse: CardView? = null
    var crdv_leadstgefrcst: CardView? = null

    private lateinit var pieChart: PieChart

    var recycPieChart: RecyclerView? = null
    lateinit var dashSort : JSONArray
    var rclrvw_leadoutstand: RecyclerView? = null
    var dashmoduleCount = 0
    var leadoutstandtile = 0
    var empwisecount = 0
    var dataList: ArrayList<BarData> = ArrayList()
    var mDataList: ArrayList<BarData> = ArrayList()
    lateinit var ledaTileArrayList: JSONArray
    lateinit var leadTileSort: JSONArray
    lateinit var leadOutstandArrayList: JSONArray
    lateinit var leadStatusArrayList: JSONArray
    lateinit var leadStagewiseArrayList: JSONArray
    lateinit var leadStagewiseeSort: JSONArray
    var graphlist = ArrayList<String>()
    var SubMode     :  String? = ""
    var label       :  String? = ""


    var rclv_dashboard: RecyclerView? = null
    private var til_dash: TextInputLayout? = null
    private var tie_dash: TextInputEditText? = null


    lateinit var leadOutstandSort: JSONArray

    private lateinit var barChart: BarChart
    private var scoreListBar = ArrayList<EmployeewiseBarLead>()
    lateinit var chartBarArrayList: JSONArray
      lateinit var empwiseArrayList: JSONArray
    private var scoreListPie = ArrayList<ScorePie>()
    lateinit var leadStagesDashViewModel: LeadStagesDashViewModel
    lateinit var leadStagesDashArrayList: JSONArray

    var TAG  ="TileGraphActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_tilegraph)
        context = this@TileGraphActivity
        leadTileViewModel = ViewModelProvider(this).get(LeadTileViewModel::class.java)
        leadOutstandViewModel = ViewModelProvider(this).get(LeadOutstandViewModel::class.java)
        employeewiseViewModel = ViewModelProvider(this).get(EmployeewiseViewModel::class.java)
        leadStatusDashViewModel = ViewModelProvider(this).get(LeadStatusDashViewModel::class.java)
        leadStagesForecastDashViewModel= ViewModelProvider(this).get(LeadStagesForecastDashViewModel::class.java)
        chartTypeViewModel               = ViewModelProvider(this).get(ChartTypeViewModel::class.java)
        leadStagesDashViewModel = ViewModelProvider(this).get(LeadStagesDashViewModel::class.java)
        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")

        Log.i("Dashmodule",SubMode+"\n"+label)

        TabMode       = 0
        ContinueMode  = 0

        context = this@TileGraphActivity
        dashmoduleCount = 0
        hideViews()
      //  getChartModeData()




        // getLeadStatusDashBoard()
        //  getLeadStagesDashBoard()
        //  getLeadSourceDashBoard()





        // ll_tile1!!.setBackgroundColor(getResources().getColor(R.color.tileclick));


    }

    private fun getLeadOutstandTile() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
               /* progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()*/
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
                                    if (jObject.getString("StatusCode") == "-2") {
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
                                 //   progressDialog!!.dismiss()
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
              //  progressDialog!!.dismiss()
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

        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)
        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)


        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        ll_Piechart= findViewById<LinearLayout>(R.id.ll_Piechart)
        ll_barchart= findViewById<LinearLayout>(R.id.ll_barchart)
        ll_top10= findViewById<LinearLayout>(R.id.ll_top10)

        actv_mode!!.setOnClickListener(this)

      /*  ll_empwise= findViewById<LinearLayout>(R.id.ll_empwise)
        ll_leadstage=findViewById<LinearLayout>(R.id.ll_leadstage)

        tile1 = findViewById<LinearLayout>(R.id.tile1)
        tile2 = findViewById<LinearLayout>(R.id.tile2)*/
      //  firstpage= findViewById<LinearLayout>(R.id.firstpage)

       /* ll_tile1 = findViewById<LinearLayout>(R.id.ll_tile1)
        ll_tile2 = findViewById<LinearLayout>(R.id.ll_tile2)
        ll_tiles= findViewById<LinearLayout>(R.id.ll_tiles)
*/


       // crdv_empwse= findViewById<CardView>(R.id.crdv_empwse)
      //  crdv_leadstgefrcst= findViewById<CardView>(R.id.crdv_leadstgefrcst)

     /*   til_dash = findViewById<TextInputLayout>(R.id.til_dash)
        tie_dash = findViewById<TextInputEditText>(R.id.tie_dash)*/

        // progressChart= findViewById<ChartProgressBar>(R.id.progressChart)


        //    ll_tile1!!.setOnClickListener(this)
        //  ll_tile2!!.setOnClickListener(this)
       // ll_tiles!!.setOnClickListener(this)

    //    tile1!!.setOnClickListener(this)
    //    tile2!!.setOnClickListener(this)

     //   firstpage!!.visibility=View.VISIBLE
     //   ll_tiles!!.visibility=View.GONE


        rclrvw_lead = findViewById<RecyclerView>(R.id.rclrvw_lead)
        rclrvw_leadoutstand = findViewById<RecyclerView>(R.id.rclrvw_leadoutstand)

        rclv_leadStagewiseforecast= findViewById<RecyclerView>(R.id.rclv_leadStagewiseforecast)
         rclv_barchart = findViewById<RecyclerView>(R.id.rclv_barchart)
        // rclv_leadStatus= findViewById<RecyclerView>(R.id.rclv_leadStatus)
        pieChart = findViewById<PieChart>(R.id.leadstage_forecsast);
      //  recycPieChart = findViewById<RecyclerView>(R.id.recycPieChart)
        barChart = findViewById<BarChart>(R.id.empwise_chart)


      //  tie_dash!!.setOnClickListener(this)
        //  setProgresschart()

        /* graphlist.add("Employee Wise Average Conversion")
         graphlist.add("Lead Source")
         graphlist.add("Lead Stage Count Wise Forecast");*/

        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)

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
                getLeadTile()
                getLeadOutstandTile()
            }
            R.id.actv_mode->{
                ChartMode      = 1
                chartModeCount = 0
                getChartModeData()
            }
        }
    }

    /* private fun dashboardPopup(graphlist: ArrayList<String>) {
         try {

             dialogCategory = Dialog(this)
             dialogCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogCategory!! .setContentView(R.layout.dashboard_popup)
             dialogCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             rclv_dashboard = dialogCategory!! .findViewById(R.id.rclv_dashboard) as RecyclerView
             val etsearch = dialogCategory!! .findViewById(R.id.etsearch) as EditText

             dashSort = JSONArray()
             for (k in 0 until graphlist.size) {
                // val jsonObject = categoryArrayList.getJSONObject(k)
                 // reportNamesort.put(k,jsonObject)
                 dashSort.put(graphlist)
             }


             val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
             rclv_dashboard!!.layoutManager = lLayout as RecyclerView.LayoutManager?
 //            recyCustomer!!.setHasFixedSize(true)
 //            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
             val adapter = CategoryAdapter(this@TileGraphActivity, dashSort)
             rclv_dashboard!!.adapter = adapter
             adapter.setClickListener(this@TileGraphActivity)


             etsearch!!.addTextChangedListener(object : TextWatcher {
                 override fun afterTextChanged(p0: Editable?) {
                 }

                 override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 }

                 override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                     //  list_view!!.setVisibility(View.VISIBLE)
                     val textlength = etsearch!!.text.length
                     dashSort = JSONArray()

                     for (k in 0 until graphlist.size) {
                       //  val jsonObject = categoryArrayList.getJSONObject(k)
                         if (textlength <= jsonObject.getString("CategoryName").length) {
                             if (jsonObject.getString("CategoryName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                 categorySort.put(jsonObject)
                             }

                         }
                     }

                     Log.e(TAG,"categorySort               7103    "+categorySort)
                     val adapter = CategoryAdapter(this@CustomerServiceActivity, categorySort)
                     recyCategory!!.adapter = adapter
                     adapter.setClickListener(this@CustomerServiceActivity)
                 }
             })

             dialogCategory!!.show()
             dialogCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
             Log.e(TAG,"Exception  1394    "+e.toString())

         }
     }*/

    private fun getLeadTile() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
               /* progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()*/
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
                                    if (jObject.getString("StatusCode") == "-2") {
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
                                  //  progressDialog!!.dismiss()
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
               // progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
       // getLeadTile()
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
                                    if (empwiseArrayList.length() > 0){
                                        setBarchart()
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                        rclv_barchart!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter = BarChartLeadAdapter(
                                            this@TileGraphActivity,
                                            empwiseArrayList
                                        )
                                        rclv_barchart!!.adapter = adapter
                                    }
                                    else
                                    {

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
                                    // setPieChart()


                                } else {
                                    /*val builder = AlertDialog.Builder(
                                        this@TileGraphActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()*/
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

    /*   private fun setBarchart() {
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

           val colors: java.util.ArrayList<Int> = java.util.ArrayList()
           colors.add(resources.getColor(R.color.leadstages_color1))
           colors.add(resources.getColor(R.color.leadstages_color2))
           colors.add(resources.getColor(R.color.leadstages_color3))

           /////////////////////

           val entries: java.util.ArrayList<BarEntry> = java.util.ArrayList()
           for (i in scoreListBar.indices) {
               val score = scoreListBar[i]
               entries.add(BarEntry(i.toFloat(), score.Barscore.toFloat()))
           }

           val barDataSet = BarDataSet(entries, "")
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

       }*/

    /* private fun getScoreList(): ArrayList<ScoreBar> {



         for (i in 0 until empwiseArrayList.length()) {
             //apply your logic
             var jsonObject = empwiseArrayList.getJSONObject(i)
             Log.e(TAG, "422  Count   " + jsonObject.getString("EmpFName"))
             scoreListBar.add(ScoreBar("", jsonObject.getString("TargetAmount").toDouble().toInt()))
         }

         return scoreListBar
     }*/
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
    /* private fun getLeadStatusDashBoard() {
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
                                     leadStatusArrayList =
                                         jobjt.getJSONArray("LeadsDashBoardDetailsList")
                                     //    tv_leadStatusTotal!!.setText(jobjt.getString("TotalCount"))
                                     Log.e(TAG, "array  125   " + leadStatusArrayList)

                                     setPieChart()
 //                                    val recycBarChart =
 //                                        findViewById(R.id.recycBarChart) as RecyclerView
                                     val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                     rclv_leadStatus!!.layoutManager =
                                         lLayout as RecyclerView.LayoutManager?
                                     val adapter = BarChartAdapter(
                                         this@TileGraphActivity,
                                         leadStatusArrayList
                                     )
                                     rclv_leadStatus!!.adapter = adapter

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

     }*/
    private fun setPieChart(leadStagewiseArrayList: JSONArray) {
        scoreListPie.clear()
        scoreListPie = getScoreList2(leadStagewiseArrayList)
        Log.v("asdasdssss", "size  " + scoreListPie.size)

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
            //ShowEnalargeGraph(pieEntries, 2)
        })


        pieChart.invalidate()
    }
    private fun getScoreList2(leadStagewiseArrayList: JSONArray): ArrayList<ScorePie> {

        for (i in 0 until leadStagewiseArrayList.length()) {
            //apply your logic
            var jsonObject = leadStagewiseArrayList.getJSONObject(i)
            Log.v("asdasdssss", "size2  " + jsonObject.getString("Percentage"))
            Log.e(TAG, "422  Count   " + jsonObject.getString("Percentage"))
            Log.i("response1122", "count=" + jsonObject.getString("Percentage"))
            Log.e(TAG, "422  Percentage   " + jsonObject.getString("Percentage"))
            scoreListPie.add(ScorePie("", jsonObject.getString("Percentage").toFloat()))
        }

        return scoreListPie
    }
    private fun ShowEnalargeGraph(pieEntries: java.util.ArrayList<PieEntry>, graphType: Int) {
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
            val colorsStage: java.util.ArrayList<Int> = java.util.ArrayList()
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
            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter =
                EnlargeLineChartAdapter(this@TileGraphActivity, leadStatusArrayList)
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
            val colorsStage: java.util.ArrayList<Int> = java.util.ArrayList()
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
            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
            recyceChart!!.layoutManager =
                lLayout as RecyclerView.LayoutManager?
            val adapter = EnlargeBarChartAdapter(
                this@TileGraphActivity,
                leadStatusArrayList
            )
            recyceChart!!.adapter = adapter
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
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
                                    //tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  264   " + leadStagesDashArrayList)

                                    // setPieChart()
                                    setBarchart()
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                    val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                    recycPieChart!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    val adapter = PieChartAdapter(
                                        this@TileGraphActivity,
                                        leadStagesDashArrayList
                                    )
                                    recycPieChart!!.adapter = adapter

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
        barChart!!.setScaleEnabled(true)
        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +325f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLACK

        //colors
        val colors: java.util.ArrayList<Int> = java.util.ArrayList()
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

        val entries: java.util.ArrayList<BarEntry> = java.util.ArrayList()
        for (i in scoreListBar.indices) {
            val score = scoreListBar[i]
            entries.add(BarEntry(i.toFloat(), score.empscore.toFloat()))
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
        barChart.data = data


        barChart.invalidate()


    }


    private fun getScoreList(): java.util.ArrayList<EmployeewiseBarLead> {
        for (i in 0 until empwiseArrayList.length())
        {
            var jsonObject = empwiseArrayList.getJSONObject(i)
            scoreListBar.add(EmployeewiseBarLead(jsonObject.getString("EmpFName"),jsonObject.getString("ActualPercentage")))
        }

        return scoreListBar
    }

    private fun getLeadStagewiseforecast() {
        var leadStatusDash = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadStagesForecastDashViewModel.getLeadStagesForecastDashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   190   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("Leadstagewiseforcast")
                                        leadStagewiseArrayList =
                                            jobjt.getJSONArray("LeadstagewiseforcastList")
                                        //    tv_leadStatusTotal!!.setText(jobjt.getString("TotalCount"))
                                        Log.e(TAG, "array  125   " + leadStagewiseArrayList)

                                        setPieChart(leadStagewiseArrayList)
    //                                    val recycBarChart =
    //                                        findViewById(R.id.recycBarChart) as RecyclerView
                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                    rclv_leadStagewiseforecast!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter = BarChartAdapter(
                                            this@TileGraphActivity,
                                            leadStagewiseArrayList
                                        )
                                    rclv_leadStagewiseforecast!!.adapter = adapter

//
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
    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

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
           // ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)
            getLeadTile()
            getLeadOutstandTile()
        }

    }

    private fun getChartModeData() {
       var ReqMode = ""
      //  var SubMode = ""
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
                                Log.e(TAG,"msg   dashboardname   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("DashBoardNameDetails")
                                   chartTypeArrayList = jobjt.getJSONArray("DashBoardNameDetailsList")
                                   if (chartTypeArrayList.length() > 0){
                                        if (ChartMode == 0){
                                           val jsonObject = chartTypeArrayList.getJSONObject(0)
                                            ID_ChartMode = jsonObject.getString("ModuleId")
                                            actv_mode!!.setText(jsonObject.getString("DashBoardName"))
                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)

                                            ll_barchart!!.visibility = View.VISIBLE
                                            ll_Piechart!!.visibility=View.GONE
                                            ll_top10!!.visibility=View.GONE
                                          //  crmStagewiseCount   = 0
                                            getEmployeewiseChart()

                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
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


            modeType[i] = objects.getString("DashBoardName");
            modeTypeID[i] = objects.getString("ModuleId");
            //   ID_ChartMode = objects.getString("ID_Mode")

            Log.e(TAG, "00000111   " + ID_ChartMode)
            Log.e(TAG, "85456214   " + modeType)


            val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
                ID_ChartMode = modeTypeID[position]


                ll_barchart!!.visibility=View.VISIBLE
                ll_Piechart!!.visibility=View.GONE
                ll_top10!!.visibility=View.GONE


                if (position == 0){
                    ll_barchart!!.visibility=View.VISIBLE
                    ll_Piechart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                 //   crmStagewiseCount   = 0
                    getEmployeewiseChart()
                }
                else if (position == 1){
                    ll_Piechart!!.visibility=View.VISIBLE
                    ll_barchart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                   // crmservicewiseCount = 0
                    getLeadStagewiseforecast()
                }
                else if (position == 2 ){
                    ll_Piechart!!.visibility=View.GONE
                    ll_barchart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE

                }
                else
                {
                    ll_Piechart!!.visibility=View.GONE
                    ll_barchart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                }
                Log.e(TAG,"ID_ChartMode  253332   "+position)
            }

        }
    }
}