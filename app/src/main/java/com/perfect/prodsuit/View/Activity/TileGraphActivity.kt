package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
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
import com.db.funnel_meterchartview.FunnelChartData
import com.db.funnel_meterchartview.FunnelChartView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimalRemover
import com.perfect.prodsuit.Helper.FullLenghRecyclertview
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AreaListRepository
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class TileGraphActivity : AppCompatActivity() , View.OnClickListener,
    ItemClickListener {
    lateinit var context: Context
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    lateinit var topRevenueArrayList: JSONArray
    lateinit var emptargetamtArrayList: JSONArray
    lateinit var leadstagecountwiseListBarList: JSONArray
    lateinit var leadcountscenarioList: JSONArray
    lateinit var avgconvsnleadArrayList: JSONArray
    lateinit var leadActivityViewModels: leadActivityViewModel
    lateinit var leadCountFollowupViewModel: LeadCountFollowupViewModel
//........
    var ll_labelAll: LinearLayout? = null
    var noDataLeadTile:Boolean=false
    var noDataLeadOutStanding:Boolean=false
    var noDataLeadAvg:Boolean=false
    var noDataLeadCount:Boolean=false
    //............

    var leadActivityText: TextView? = null
    var mainHeadingDash: TextView? = null
    var txt_chartlabel: TextView? = null
    var txt_chartremark: TextView? = null

    //all x, y axis

    var emp_xaxis: TextView? = null
    var emp_yaxis: TextView? = null
    var ll_emp_XY: LinearLayout? = null

    var top10_xaxis: TextView? = null
    var top10_yaxis: TextView? = null
    var ll_top10_XY: LinearLayout? = null

    var leadsource_xaxis: TextView? = null
    var leadsource_yaxis: TextView? = null
    var ll_leadSource_XY: LinearLayout? = null

    var toprevenue_xaxis: TextView? = null
    var toprevenue_yaxis: TextView? = null
    var ll_toprevenue_XY: LinearLayout? = null
    var ll_ExpenseGain: LinearLayout? = null


    var empAVG_xaxis: TextView? = null
    var empAVG_yaxis: TextView? = null
    var ll_empAVG_XY: LinearLayout? = null

    var EmpTarg_xaxis: TextView? = null
    var EmpTarg_yaxis: TextView? = null
    var ll_EmpTarg_XY: LinearLayout? = null
   var ll_ActualTarg: LinearLayout? = null



    var txtv_leadTileCount: TextView? = null

    var txt_leadcountscenario: TextView? = null
    var txtv_leadOutstandCount: TextView? = null


    private var tvv_lemo_StagWise: TextView? = null
    var drawableMore : Drawable? = null
    var drawableLess : Drawable? = null

    var lemoStagWiseMode    = 0
    var leadcountscenario   = 0

    lateinit var leadActivityBarList: JSONArray
    var leadstagecountwiseList = 0
    var toprevenuelist = 0

    var top10productlist=0
    var hideforecastmode=0
    var leadstagewiswforecast=0
    var leadstagecountwiseText: TextView? = null
    var txtv_EmpWiseRemark: TextView? = null
    var txtv_empamtRemrk: TextView? = null
    var txtv_leadScenarioCount: TextView? = null

    var recycleemployeewiseAvg: FullLenghRecyclertview? = null
    var rclv_toprevenue: FullLenghRecyclertview? = null
    var recycTopRevenue: FullLenghRecyclertview? = null
    var rclrvw_leadcountscenario: RecyclerView? = null


    private lateinit var top_revenuebarchart: BarChart

    var employeewiseAvgText: TextView? = null
    var employeewiseavgconvesionList = 0
    lateinit var employeewiseAvgViewModel: EmployeewiseAvgViewModel
    lateinit var employeewiseTargetAmountViewModel: EmployeewiseTargetAmountViewModel
    lateinit var leadstagecountwiseViewModel:LeadstagecountwiseViewModel
    lateinit var employeewiseAvgBarList: JSONArray
    internal var etdis: EditText? = null
    internal var tile1: LinearLayout? = null
    internal var tile2: LinearLayout? = null
    internal var ll_rmkbl: LinearLayout? = null

    internal var ll_leadstagewiseforecast: LinearLayout? = null

    internal var ll_leadcount: LinearLayout? = null
    internal var ll_top10more: LinearLayout? = null
    internal var ll_lleadsrcemre: LinearLayout? = null
    internal var ll_toprevenue: LinearLayout? = null
    internal var ll_empwiseavgm: LinearLayout? = null
    internal var ll_empwiseamtt: LinearLayout? = null

     internal var ll_empwisebarchart: LinearLayout? = null



    internal var ll_empwseamt: LinearLayout? = null
    var rclv_empwiseamt: FullLenghRecyclertview? = null
    var recycEmpWiseTarg: FullLenghRecyclertview? = null
    private lateinit var empwiseamt_barchart: BarChart


    lateinit var leadSourseChart: HorizontalBarChart
    var leadSourseSubText: TextView? = null
    var txtv_LeadForecastRemark: TextView? = null
    var txtv_leadcountscenariormrk: TextView? = null


    lateinit var leadSourseBarList: JSONArray
    var recycleLeadSourse: FullLenghRecyclertview? = null
    var leadSourseList = 0
    lateinit var leadcountscenariosort: JSONArray
    lateinit var leadSourseViewModel: LeadSourseViewModel
    lateinit var topRevenueViewModel: TopRevenueViewModel
    private var actv_mode: AutoCompleteTextView? = null
    var card_leadActivity: LinearLayout? = null
    var card_employeewiseAvg: LinearLayout? = null
    var ID_ChartMode    :  String? = ""
    var chartNme    :  String? = ""
    var dashname:  String? = ""
    var card_leadstagecountwise: LinearLayout? = null
    lateinit var employeewiseAvgChart: HorizontalBarChart
    var card_leadSourse: LinearLayout? = null
    internal var ll_tile1: LinearLayout? = null
    internal var ll_leadstage: LinearLayout? = null
    lateinit var chartTypeArrayList: JSONArray
    internal var ll_tile2: LinearLayout? = null
    internal var ll_tiles: LinearLayout? = null
    internal var firstpage: LinearLayout? = null
    internal var ll_empwise: LinearLayout? = null
    var leadActivityList = 0
    var empwiselist=0
    var employeewisetargetamtList=0
    lateinit var chartTypeViewModel: ChartTypeViewModel
    private var tvv_dash: TextView? = null
    private var txtv_leadoutstand: TextView? = null
    private var txtv_avglead: TextView? = null
    private var txtv_lead: TextView? = null
    private var txt_tileChartlead: TextView? = null
    private var txtv_leadChartAvg: TextView? = null
    private var txtv_leadChartoutstand: TextView? = null
    private var tvv_tile: TextView? = null
    private var txtv_top10Remrk: TextView? = null

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
    lateinit var averageLeadConversionModel: AvgLeadConversionViewModel
    lateinit var leadStagesForecastDashViewModel: LeadStagesForecastDashViewModel
    lateinit var top10LeadViewModel: Top10LeadViewModel
    var rclrvw_lead: RecyclerView? = null
    var rclv_barchart: RecyclerView? = null
    var rclv_top10: RecyclerView? = null
    var rclv_leadStagewiseforecast: RecyclerView? = null
    var recycleleadActivity: FullLenghRecyclertview? = null

    var crdv_empwse: CardView? = null
    var crdv_avgconvsnlead: CardView? = null
    var crdv_leadcountscenario: CardView? = null

    var rclrvw_avgleadconvsn: RecyclerView? = null
    private lateinit var pieChart: PieChart

    var recycPieChart: RecyclerView? = null
    var crdv_lead: CardView? = null
    var card_toprevenue: LinearLayout? = null

    var crdv_leadoutstand: CardView? = null
    lateinit var dashSort : JSONArray
    var rclrvw_leadoutstand: RecyclerView? = null

    var leadcountmode = 0
    var top10mode = 0
    var leadsourcemode = 0
    var toprevenuemode = 0
    var empwise_avgmode = 0
    var empwse_amtmode = 0


    var dashmoduleCount = 0
    var leadoutstandtile = 0
    var avgconvsnlead=0
    var empwisecount = 0
    var dataList: ArrayList<BarData> = ArrayList()
    var mDataList: ArrayList<BarData> = ArrayList()
    lateinit var ledaTileArrayList: JSONArray
    lateinit var leadTileSort: JSONArray
    lateinit var leadOutstandArrayList: JSONArray
    lateinit var leadStatusArrayList: JSONArray
    lateinit var top10ProductsArrayList: JSONArray
    lateinit var leadStagewiseArrayList: JSONArray

    lateinit var leadStagewiseeSort: JSONArray
    var recycleleadstagecountwise: FullLenghRecyclertview? = null
    lateinit var top10productSort: JSONArray
    var graphlist = ArrayList<String>()
    var SubMode     :  String? = ""
    var label       :  String? = ""


    var rclv_dashboard: RecyclerView? = null
    private var til_dash: TextInputLayout? = null
    private var tie_dash: TextInputEditText? = null


    lateinit var leadOutstandSort: JSONArray
    lateinit var avgLeadConvsnSort: JSONArray


    private lateinit var barChart: BarChart
    private lateinit var top10_barchart: BarChart

    private var scoreListBar = ArrayList<EmployeewiseBarLead>()
    private var top10ListBar = ArrayList<Top10BarLead>()
    private var empwiseAmtBar = ArrayList<EmpwiseAmtBarLead>()
    private var empwiseAmtBar1 = ArrayList<EmpwiseAmtBarLead1>()
    private var topRevenueBar = ArrayList<TopRevenueBarLead>()
    private var topRevenueBar1 = ArrayList<TopRevenueBarLead1>()
    lateinit var chartBarArrayList: JSONArray
      lateinit var empwiseArrayList: JSONArray
    private var scoreListPie = ArrayList<ScorePie>()
    lateinit var leadStagesDashViewModel: LeadStagesDashViewModel
    lateinit var leadStagesDashArrayList: JSONArray
    var LeadTileCount  = 0
    var LeadAvgCount  = 0
    var LeadTileOutstandCount  = 0
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

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
        top10LeadViewModel= ViewModelProvider(this).get(Top10LeadViewModel::class.java)
        leadSourseViewModel = ViewModelProvider(this).get(LeadSourseViewModel::class.java)
        employeewiseAvgViewModel = ViewModelProvider(this).get(EmployeewiseAvgViewModel::class.java)
        leadstagecountwiseViewModel = ViewModelProvider(this).get(LeadstagecountwiseViewModel::class.java)
        leadActivityViewModels= ViewModelProvider(this).get(leadActivityViewModel::class.java)
        averageLeadConversionModel= ViewModelProvider(this).get(AvgLeadConversionViewModel::class.java)
        topRevenueViewModel= ViewModelProvider(this).get(TopRevenueViewModel::class.java)
        leadCountFollowupViewModel= ViewModelProvider(this).get(LeadCountFollowupViewModel::class.java)
        employeewiseTargetAmountViewModel= ViewModelProvider(this).get(EmployeewiseTargetAmountViewModel::class.java)
        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")

        Log.i("Dashmodule",SubMode+"\n"+label)

        mainHeadingDash!!.text=label+" DASHBOARD"

        TabMode       = 0
        ContinueMode  = 0

        context = this@TileGraphActivity
        dashmoduleCount = 0
        hideViews()
       // getChartModeData()




        // getLeadStatusDashBoard()
        //  getLeadStagesDashBoard()
        //  getLeadSourceDashBoard()





        // ll_tile1!!.setBackgroundColor(getResources().getColor(R.color.tileclick));


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun getLeadOutstandTile() {
        noDataLeadOutStanding=false
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


                                if (LeadTileOutstandCount == 0){
                                    LeadTileOutstandCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadoutstandtile   " + msg)
                                    if (jObject.getString("StatusCode") == "0")
                                    {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")
                                        var chartname =   jobjt.getString("ChartName")
                                        var remark =   jobjt.getString("Reamrk")
                                        txtv_leadChartoutstand!!.text=chartname
                                        txtv_leadoutstand!!.text=remark

                                        leadOutstandArrayList = jobjt.getJSONArray("LeadTileData")
                                        if (leadOutstandArrayList.length() > 0) {
                                            var totalleadoutstand: Int = 0
                                            crdv_leadoutstand!!.visibility=View.VISIBLE
                                            leadOutstandSort = JSONArray()
                                            for (k in 0 until leadOutstandArrayList.length()) {
                                                val jsonObject = leadOutstandArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                val jsonObject1 = leadOutstandArrayList.getJSONObject(0)
                                                var totleadoutstand: String =
                                                    jsonObject1.optString("Value")
                                                totalleadoutstand = Integer.parseInt(totleadoutstand)


                                                leadOutstandSort.put(jsonObject)
                                            }

                                            System.out.println("Total Leadoutstand "+totalleadoutstand)
                                            txtv_leadOutstandCount!!.visibility=View.VISIBLE
                                            txtv_leadOutstandCount!!.text=totalleadoutstand.toString()

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
                                        else {
                                            crdv_leadoutstand!!.visibility=View.GONE
                                           /* val builder = AlertDialog.Builder(
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

                                        noDataLeadOutStanding=true

                                        if ( noDataLeadTile && noDataLeadOutStanding && noDataLeadAvg && noDataLeadCount )
                                        {
                                            val builder = AlertDialog.Builder(
                                                this@TileGraphActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage("No Active Tile Settings")
                                            builder.setPositiveButton("Ok") {
                                                    dialogInterface, which ->


//                                                ll_Graph!!.visibility = View.VISIBLE
//                                                tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
//                                                tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)


                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }

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
                           /* Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()*/
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

    private fun getLeadAvgConvrsn() {
        noDataLeadAvg=false
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                averageLeadConversionModel.getAvgLeadConvrsn(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (LeadAvgCount == 0){
                                    LeadAvgCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   avgleadconvrsn   " + msg)
                                    if (jObject.getString("StatusCode") == "0")
                                    {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")
                                        var chartname =   jobjt.getString("ChartName")
                                        var remark =   jobjt.getString("Reamrk")
                                        txtv_leadChartAvg!!.text=chartname
                                        txtv_avglead!!.text=remark

                                        avgconvsnleadArrayList = jobjt.getJSONArray("LeadTileData")
                                        if (avgconvsnleadArrayList.length() > 0) {

                                            crdv_avgconvsnlead!!.visibility=View.VISIBLE
                                            avgLeadConvsnSort = JSONArray()
                                            for (k in 0 until avgconvsnleadArrayList.length()) {
                                                val jsonObject = avgconvsnleadArrayList.getJSONObject(k)

                                                // reportNamesort.put(k,jsonObject)
                                                avgLeadConvsnSort.put(jsonObject)
                                            }


                                            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                            rclrvw_avgleadconvsn!!.setLayoutManager(
                                                LinearLayoutManager(
                                                    this,
                                                    RecyclerView.HORIZONTAL,
                                                    false
                                                )
                                            )
                                            // rclrvw_lead!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = LeadAvgConsnListAdapter(this@TileGraphActivity,avgLeadConvsnSort)
                                            rclrvw_avgleadconvsn!!.adapter = adapter
                                            adapter.setClickListener(this@TileGraphActivity)






                                        }
                                        else {
                                            crdv_avgconvsnlead!!.visibility=View.GONE
                                           /* val builder = AlertDialog.Builder(
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
                                    } else
                                    {
                                        noDataLeadAvg=true

                                        if ( noDataLeadTile && noDataLeadOutStanding && noDataLeadAvg && noDataLeadCount )
                                        {
                                            val builder = AlertDialog.Builder(
                                                this@TileGraphActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage("No Active Tile Settings")
                                            builder.setPositiveButton("Ok") {
                                                    dialogInterface, which ->


//                                                ll_Graph!!.visibility = View.VISIBLE
//                                                tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
//                                                tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)


                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }
                                    }
                                    //   progressDialog!!.dismiss()
                                }

                            } else {
                          /*  Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()*/
                            }
                        } catch (e: Exception) {
                           /* Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()*/
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
        ll_labelAll= findViewById<LinearLayout>(R.id.ll_labelAll)
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        emp_xaxis= findViewById<TextView>(R.id.emp_xaxis)
        emp_yaxis= findViewById<TextView>(R.id.emp_yaxis)
        ll_emp_XY= findViewById<LinearLayout>(R.id.ll_emp_XY)


        top10_xaxis= findViewById<TextView>(R.id.top10_xaxis)
        top10_yaxis= findViewById<TextView>(R.id.top10_yaxis)
        ll_top10_XY= findViewById<LinearLayout>(R.id.ll_top10_XY)

        leadsource_xaxis= findViewById<TextView>(R.id.leadsource_xaxis)
        leadsource_yaxis= findViewById<TextView>(R.id.leadsource_yaxis)
        ll_leadSource_XY= findViewById<LinearLayout>(R.id.ll_leadSource_XY)


        empAVG_xaxis= findViewById<TextView>(R.id.empAVG_xaxis)
        empAVG_yaxis= findViewById<TextView>(R.id.empAVG_yaxis)
        ll_empAVG_XY= findViewById<LinearLayout>(R.id.ll_empAVG_XY)

        toprevenue_xaxis= findViewById<TextView>(R.id.toprevenue_xaxis)
        toprevenue_yaxis= findViewById<TextView>(R.id.toprevenue_yaxis)

        ll_toprevenue_XY= findViewById<LinearLayout>(R.id.ll_toprevenue_XY)
        ll_ExpenseGain= findViewById<LinearLayout>(R.id.ll_ExpenseGain)


        EmpTarg_xaxis= findViewById<TextView>(R.id.EmpTarg_xaxis)
        EmpTarg_yaxis= findViewById<TextView>(R.id.EmpTarg_yaxis)
        ll_EmpTarg_XY= findViewById<LinearLayout>(R.id.ll_EmpTarg_XY)
        ll_ActualTarg= findViewById<LinearLayout>(R.id.ll_ActualTarg)




        drawableMore = resources.getDrawable(R.drawable.dash_more, null)
        drawableLess = resources.getDrawable(R.drawable.dash_less, null)

        ll_leadcount= findViewById<LinearLayout>(R.id.ll_leadcount)
        ll_top10more= findViewById<LinearLayout>(R.id.ll_top10more)
        ll_lleadsrcemre= findViewById<LinearLayout>(R.id.ll_lleadsrcemre)
        ll_toprevenue= findViewById<LinearLayout>(R.id.ll_toprevenue)
        ll_empwiseavgm= findViewById<LinearLayout>(R.id.ll_empwiseavgm)
        ll_empwiseamtt= findViewById<LinearLayout>(R.id.ll_empwiseamtt)

        txtv_leadScenarioCount= findViewById<TextView>(R.id.txtv_leadScenarioCount)

        txt_leadcountscenario= findViewById<TextView>(R.id.txt_leadcountscenario)
        ll_empwisebarchart = findViewById<LinearLayout>(R.id.ll_empwisebarchart)
        txtv_leadTileCount= findViewById<TextView>(R.id.txtv_leadTileCount)



        txtv_leadoutstand= findViewById<TextView>(R.id.txtv_leadoutstand)
        txtv_avglead= findViewById<TextView>(R.id.txtv_avglead)
        txtv_lead= findViewById<TextView>(R.id.txtv_lead)
        txtv_leadOutstandCount= findViewById<TextView>(R.id.txtv_leadOutstandCount)


        txtv_leadcountscenariormrk= findViewById<TextView>(R.id.txtv_leadcountscenariormrk)

        crdv_leadcountscenario= findViewById<CardView>(R.id.crdv_leadcountscenario)

        ll_leadstagewiseforecast= findViewById<LinearLayout>(R.id.ll_leadstagewiseforecast)
        tvv_lemo_StagWise      = findViewById<TextView>(R.id.tvv_lemo_StagWise)
        tvv_lemo_StagWise!!.setOnClickListener(this)

        txt_chartlabel= findViewById<TextView>(R.id.txt_chartlabel)
        txt_chartremark= findViewById<TextView>(R.id.txt_chartremark)

        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)
        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        mainHeadingDash = findViewById<TextView>(R.id.mainHeadingDash)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)
        leadSourseSubText = findViewById(R.id.leadSourseSubText)
        recycleLeadSourse = findViewById(R.id.recycleLeadSourse)
        recycleemployeewiseAvg=findViewById(R.id.recycleemployeewiseAvg)
        employeewiseAvgText=findViewById(R.id.employeewiseAvgText)
        leadstagecountwiseText=findViewById(R.id.leadstagecountwiseText)
        txtv_EmpWiseRemark=findViewById(R.id.txtv_EmpWiseRemark)
        txtv_empamtRemrk=findViewById(R.id.txtv_empamtRemrk)
        recycleleadstagecountwise=findViewById(R.id.recycleleadstagecountwise)
        rclrvw_leadcountscenario=findViewById(R.id.rclrvw_leadcountscenario)
        recycleleadActivity=findViewById(R.id.recycleleadActivity)
        leadActivityText=findViewById(R.id.leadActivityText)
        txtv_top10Remrk=findViewById(R.id.txtv_top10Remrk)

        rclv_empwiseamt=findViewById(R.id.rclv_empwiseamt)
        recycEmpWiseTarg=findViewById(R.id.recycEmpWiseTarg)
        ll_empwseamt            = findViewById<LinearLayout>(R.id.ll_empwseamt)
        empwiseamt_barchart = findViewById<BarChart>(R.id.empwiseamt_barchart)

        crdv_lead = findViewById<CardView>(R.id.crdv_lead)
        crdv_leadoutstand = findViewById<CardView>(R.id.crdv_leadoutstnd)
        card_leadSourse = findViewById(R.id.card_leadSourse)
        card_employeewiseAvg=findViewById(R.id.card_employeewiseAvg)
        card_leadActivity=findViewById(R.id.card_leadActivity)
        card_leadstagecountwise=findViewById(R.id.card_leadstagecountwise)
        crdv_avgconvsnlead=findViewById(R.id.crdv_avgconvsnlead)
        card_toprevenue=findViewById(R.id.card_toprevenue)
        txtv_LeadForecastRemark=findViewById(R.id.txtv_LeadForecastRemark)


        rclrvw_avgleadconvsn=findViewById(R.id.rclrvw_avgleadconvsn)
        rclv_toprevenue=findViewById(R.id.rclv_toprevenue)
        recycTopRevenue=findViewById(R.id.recycTopRevenue)
        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        txt_tileChartlead = findViewById<TextView>(R.id.txt_tileChartlead)
        txtv_leadChartAvg = findViewById<TextView>(R.id.txtv_leadChartAvg)
        txtv_leadChartoutstand = findViewById<TextView>(R.id.txtv_leadChartoutstand)



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
        rclv_top10= findViewById<RecyclerView>(R.id.rclv_top10)
         rclv_barchart = findViewById<RecyclerView>(R.id.rclv_barchart)
        // rclv_leadStatus= findViewById<RecyclerView>(R.id.rclv_leadStatus)
        pieChart = findViewById<PieChart>(R.id.leadstage_forecsast);
      //  recycPieChart = findViewById<RecyclerView>(R.id.recycPieChart)
        barChart = findViewById<BarChart>(R.id.empwise_chart)
        top10_barchart = findViewById<BarChart>(R.id.top10_barchart)
        top_revenuebarchart= findViewById<BarChart>(R.id.top_revenuebarchart)

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

               /* getLeadTile()
                getLeadOutstandTile()
                getLeadAvgConvrsn()*/
            }
            R.id.actv_mode->{
                ChartMode      = 1
                chartModeCount = 0
                getChartModeData()
            }
            R.id.tvv_lemo_StagWise->{
               if(ID_ChartMode.equals("2"))
                {
                    if (lemoStagWiseMode == 0){
                        lemoStagWiseMode = 1
                        hideStageWise()
                    }else{
                        lemoStagWiseMode = 0
                        hideStageWise()
                    }


                }
                if(ID_ChartMode.equals("3"))
                {


                        if (hideforecastmode == 0){
                            hideforecastmode = 1
                            hideLeadForecast()

                        }else{
                            hideforecastmode = 0
                            hideLeadForecast()
                        }


                }
                 if(ID_ChartMode.equals("4"))
                {
                    if (leadcountmode == 0){
                        leadcountmode = 1
                   //     hideLeadCount()

                    }else{
                        leadcountmode = 0
                   //     hideLeadCount()
                    }
                    hideLeadCount()
                }
                if(ID_ChartMode.equals("5"))
                {
                    if (top10mode == 0){
                        top10mode = 1
                        hidetop10()

                    }else{
                        top10mode = 0
                        hidetop10()
                    }
                }
                if(ID_ChartMode.equals("6"))
                {
                    if (leadsourcemode == 0){
                        leadsourcemode = 1
                        hideLeadSource()

                    }else{
                        leadsourcemode = 0
                        hideLeadSource()
                    }
                }


                if(ID_ChartMode.equals("7"))
                {
                    if (toprevenuemode == 0){
                        toprevenuemode = 1
                        hideTopRevenue()

                    }else{
                        toprevenuemode = 0
                        hideTopRevenue()
                    }
                }
                if(ID_ChartMode.equals("8"))
                {
                    if (empwise_avgmode == 0){
                        empwise_avgmode = 1
                        hideEmpwiseavgconsn()

                    }else{
                        empwise_avgmode = 0
                        hideEmpwiseavgconsn()
                    }
                }
                if(ID_ChartMode.equals("9"))
                {
                    if (empwse_amtmode == 0){
                        empwse_amtmode = 1
                        hideempwseamt()

                    }else{
                        empwse_amtmode = 0
                        hideempwseamt()
                    }
                }

                Log.i("CHART", ID_ChartMode!!)

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
        noDataLeadTile=false
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


                                if (LeadTileCount == 0){
                                    LeadTileCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadtile   " + msg)
                                    if (jObject.getString("StatusCode") == "0")
                                    {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")
                                        var remark =   jobjt.getString("Reamrk")
                                        var chartname =   jobjt.getString("ChartName")
                                        txt_tileChartlead!!.text=chartname
                                        txtv_lead!!.text=remark


                                        ledaTileArrayList = jobjt.getJSONArray("LeadTileData")
                                        if (ledaTileArrayList.length() > 0) {
                                            var totallead: Int = 0
                                            crdv_lead!!.visibility=View.VISIBLE
                                            leadTileSort = JSONArray()
                                            for (k in 0 until ledaTileArrayList.length()) {
                                                val jsonObject = ledaTileArrayList.getJSONObject(k)
                                                val jsonObject1 = ledaTileArrayList.getJSONObject(0)
                                                // reportNamesort.put(k,jsonObject)
                                                var totleadcount: String =
                                                    jsonObject1.optString("Value")
                                                totallead =  Integer.parseInt(totleadcount)
                                                leadTileSort.put(jsonObject)
                                            }

                                            System.out.println("Total Count "+totallead)
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

                                            txtv_leadTileCount!!.visibility=View.VISIBLE
                                            txtv_leadTileCount!!.text=totallead.toString()

                                            /* val lLayout1 = GridLayoutManager(this@TileGraphActivity, 1)
                                             rclrvw_leadoutstand!!.layoutManager = lLayout1 as RecyclerView.LayoutManager?*/


                                        }
                                        else {
                                            crdv_lead!!.visibility=View.GONE


                                           /* val builder = AlertDialog.Builder(
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
                                        noDataLeadTile=true

                                        if ( noDataLeadTile && noDataLeadOutStanding && noDataLeadAvg && noDataLeadCount)
                                        {
                                            val builder = AlertDialog.Builder(
                                                this@TileGraphActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage("No Active Tile Settings")
                                            builder.setPositiveButton("Ok") {
                                                    dialogInterface, which ->



                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }
                                      /*  val builder = AlertDialog.Builder(
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
                            /*Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()*/
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

    private fun getLeadCountScenario() {
        noDataLeadCount=false
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadCountFollowupViewModel.getLeadCountFollowupCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {


                                if (leadcountscenario == 0){
                                    leadcountscenario++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadcountscenario   " + msg)
                                   if (jObject.getString("StatusCode") == "0")
                                   {
                                        val jobjt = jObject.getJSONObject("TileLeadDashBoardDetails")
                                        var remark =   jobjt.getString("Reamrk")
                                        var chartname =   jobjt.getString("ChartName")
                                       txt_leadcountscenario!!.text=chartname
                                       txtv_leadcountscenariormrk!!.text=remark
                                       leadcountscenarioList = jobjt.getJSONArray("LeadTileData")
                                        if (leadcountscenarioList.length() > 0) {

                                            crdv_leadcountscenario!!.visibility=View.VISIBLE
                                            leadcountscenariosort = JSONArray()

                                            var total: Int = 0

                                            for (k in 0 until leadcountscenarioList.length()) {
                                                val jsonObject = leadcountscenarioList.getJSONObject(k)
                                                val jsonObject1 = leadcountscenarioList.getJSONObject(0)
                                                var totcount: String =
                                                    jsonObject1.optString("Value")
                                               // total = total + Integer.parseInt(totcount)
                                                total = Integer.parseInt(totcount)
                                                // reportNamesort.put(k,jsonObject)
                                                leadcountscenariosort.put(jsonObject)
                                            }

                                            System.out.println("Total Count "+total)

                                            txtv_leadScenarioCount!!.visibility=View.VISIBLE
                                            txtv_leadScenarioCount!!.text=total.toString()


                                            val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                            rclrvw_leadcountscenario!!.setLayoutManager(
                                                LinearLayoutManager(
                                                    this,
                                                    RecyclerView.HORIZONTAL,
                                                    false
                                                )
                                            )
                                            // rclrvw_lead!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = LeadCountAdapter(this@TileGraphActivity,leadcountscenariosort,remark)
                                            rclrvw_leadcountscenario!!.adapter = adapter
                                            adapter.setClickListener(this@TileGraphActivity)



                                            /* val lLayout1 = GridLayoutManager(this@TileGraphActivity, 1)
                                             rclrvw_leadoutstand!!.layoutManager = lLayout1 as RecyclerView.LayoutManager?*/


                                        }
                                        else {
                                            crdv_leadcountscenario!!.visibility=View.GONE
                                            /* val builder = AlertDialog.Builder(
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
                                       noDataLeadCount=true
                                       if ( noDataLeadTile && noDataLeadOutStanding && noDataLeadAvg && noDataLeadCount)
                                       {
                                           val builder = AlertDialog.Builder(
                                               this@TileGraphActivity,
                                               R.style.MyDialogTheme
                                           )
                                           builder.setMessage("No Active Tile Settings")
                                           builder.setPositiveButton("Ok") {
                                                   dialogInterface, which ->


//                                                ll_Graph!!.visibility = View.VISIBLE
//                                                tvv_dash!!.setBackgroundResource(R.drawable.btn_dash)
//                                                tvv_tile!!.setBackgroundResource(R.drawable.btn_shape_reset)


                                           }
                                           val alertDialog: AlertDialog = builder.create()
                                           alertDialog.setCancelable(false)
                                           alertDialog.show()
                                       }
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
                            /*Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()*/
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
       // getLeadTile()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onClick(position: Int, data: String) {
        TODO("Not yet implemented")
    }
    private fun getEmployeewiseChart() {
        empwiselist = 0
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
                                if (empwiselist == 0) {
                                    empwiselist++
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   190 empwise   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("EmployeeWiseTaegetInPercentage")
                                    empwiseArrayList =
                                        jobjt.getJSONArray("EmployeeWiseTaegetDetails")
                                    val remark =
                                        jobjt.getString("Reamrk")

                                    val xAxis =
                                        jobjt.getString("XAxis")
                                    val yAxis =
                                        jobjt.getString("YAxis")

                                    ll_labelAll!!.visibility=View.VISIBLE
                                    txtv_EmpWiseRemark!!.visibility=View.GONE
                                    txt_chartlabel!!.visibility=View.VISIBLE
                                    txt_chartremark!!.visibility=View.VISIBLE
                                    ll_emp_XY!!.visibility=View.VISIBLE
                                   // txtv_EmpWiseRemark!!.setText(remark)
                                    txt_chartlabel!!.text="EMPLOYEE WISE TARGET LIST"
                                    txt_chartremark!!.text=remark
                                    emp_xaxis!!.text=xAxis
                                    emp_yaxis!!.text=yAxis

                                  //  tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  empwise   " + empwiseArrayList)
                                    if (empwiseArrayList.length() > 0){
                                        setBarchart()
                                        rclv_barchart!!.visibility=View.VISIBLE
                                        lemoStagWiseMode = 0
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                        hideStageWise()


                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
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
                                        ll_labelAll!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
                                        ll_emp_XY!!.visibility=View.GONE
                                        rclv_barchart!!.visibility=View.GONE
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
                                    rclv_barchart!!.visibility=View.GONE
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

        colorsStage.add(resources.getColor(R.color.leadstages_color1))
        colorsStage.add(resources.getColor(R.color.leadstages_color2))
        colorsStage.add(resources.getColor(R.color.leadstages_color3))
        colorsStage.add(resources.getColor(R.color.leadstages_color4))
        colorsStage.add(resources.getColor(R.color.leadstages_color5))
        colorsStage.add(resources.getColor(R.color.leadstages_color6))
        colorsStage.add(resources.getColor(R.color.leadstages_color7))
        colorsStage.add(resources.getColor(R.color.leadstages_color8))
        colorsStage.add(resources.getColor(R.color.leadstages_color9))
        colorsStage.add(resources.getColor(R.color.leadstages_color10))
        colorsStage.add(resources.getColor(R.color.leadstages_color11))
        colorsStage.add(resources.getColor(R.color.leadstages_color12))
        colorsStage.add(resources.getColor(R.color.leadstages_color10))
        colorsStage.add(resources.getColor(R.color.mylead_Color))
        colorsStage.add(resources.getColor(R.color.barchart_colors24))
        colorsStage.add(resources.getColor(R.color.barchart_colors10))
        colorsStage.add(resources.getColor(R.color.barchart_colors21))
        colorsStage.add(resources.getColor(R.color.barchart_colors23))
        colorsStage.add(resources.getColor(R.color.barchart_colors3))
        colorsStage.add(resources.getColor(R.color.barchart_colors12))
        colorsStage.add(resources.getColor(R.color.barchart_colors24))
        colorsStage.add(resources.getColor(R.color.mylead_light_Color))
        colorsStage.add(resources.getColor(R.color.mylead_light_Color1))
        colorsStage.add(resources.getColor(R.color.barchart_colors28))
        colorsStage.add(resources.getColor(R.color.barchart_colors18))
        colorsStage.add(resources.getColor(R.color.barchart_colors2))
        colorsStage.add(resources.getColor(R.color.barchart_colors15))
        colorsStage.add(resources.getColor(R.color.barchart_colors50))
        colorsStage.add(resources.getColor(R.color.colorService))
        colorsStage.add(resources.getColor(R.color.leadbar5))
        colorsStage.add(resources.getColor(R.color.colorPrimary))
        colorsStage.add(resources.getColor(R.color.colorAccent))
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

        val entries: ArrayList<BarEntry> = ArrayList()
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
    private fun settop10chart() {
        top10ListBar.clear()
        top10ListBar = getTop10List()

        top10_barchart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = top10_barchart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        top10_barchart.axisRight.isEnabled = false
        //remove legend
        top10_barchart.legend.isEnabled = false
        top10_barchart!!.setScaleEnabled(true)
        //remove description label
        top10_barchart.description.isEnabled = false


        //add animation
        top10_barchart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar3()
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

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in top10ListBar.indices) {
            val score = top10ListBar[i]
            entries.add(BarEntry(i.toFloat(), score.topcount.toFloat()))
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
        top10_barchart.data = data


        top10_barchart.invalidate()


    }

    private fun settoprevenuechartNew()
    {
        topRevenueBar1.clear()
        topRevenueBar1 = getTopRevenueList1()

        top_revenuebarchart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = top_revenuebarchart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        top_revenuebarchart.axisRight.isEnabled = false
        //remove legend
        top_revenuebarchart.legend.isEnabled = false
        top_revenuebarchart!!.setScaleEnabled(true)
        //remove description label
        top_revenuebarchart.description.isEnabled = false


        //add animation
        top_revenuebarchart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar1()
        xAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);

        /////////////////////////////////////

        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in topRevenueBar1.indices) {
            val score = topRevenueBar1[i]
            entries1.add(BarEntry(i.toFloat(), score.MediaAmount.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in topRevenueBar1.indices) {
            val score = topRevenueBar1[i]
            entries2.add(BarEntry(i.toFloat(), score.LeadAmount.toFloat()))
        }

        val barDataSet1 = BarDataSet(entries1, "data1")
        barDataSet1.setColors(resources.getColor(R.color.expense_color))
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet1.valueFormatter = DefaultValueFormatter(0)

        val barDataSet2 = BarDataSet(entries2, "data2")
        barDataSet2.setColors(resources.getColor(R.color.gain_color))
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
        top_revenuebarchart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        top_revenuebarchart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        top_revenuebarchart.invalidate()

    }

    private fun getTopRevenueList1(): ArrayList<TopRevenueBarLead1> {
        for (i in 0 until topRevenueArrayList.length())
        {
            var jsonObject = topRevenueArrayList.getJSONObject(i)
            topRevenueBar1.add(TopRevenueBarLead1(jsonObject.getString("MediaName"),jsonObject.getString("MediaAmount"),jsonObject.getString("LeadAmount")))
        }

        return topRevenueBar1
    }

    inner class MyAxisFormatterBar1 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < topRevenueBar1.size) {
                topRevenueBar1[index].MediaName
            } else {
                ""
            }
        }
    }
    private fun settoprevenuechart() {
        topRevenueBar.clear()
        topRevenueBar = getTopRevenueList()

        top_revenuebarchart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = top_revenuebarchart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        top_revenuebarchart.axisRight.isEnabled = false
        //remove legend
        top_revenuebarchart.legend.isEnabled = false
        top_revenuebarchart!!.setScaleEnabled(true)
        //remove description label
        top_revenuebarchart.description.isEnabled = false


        //add animation
        top_revenuebarchart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar4()
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

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in topRevenueBar.indices) {
            val score = topRevenueBar[i]
            entries.add(BarEntry(i.toFloat(), score.topcount.toFloat()))
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
        top_revenuebarchart.data = data


        top_revenuebarchart.invalidate()


    }

    private fun setempwiswtargetamtNew()
    {
        empwiseAmtBar1.clear()
        empwiseAmtBar1 = getEmpAmtList1()

        empwiseamt_barchart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = empwiseamt_barchart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        empwiseamt_barchart.axisRight.isEnabled = false
        //remove legend
        empwiseamt_barchart.legend.isEnabled = false
        empwiseamt_barchart!!.setScaleEnabled(true)
        //remove description label
        empwiseamt_barchart.description.isEnabled = false


        //add animation
        empwiseamt_barchart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar22()
        xAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE
        xAxis.setAxisMinimum(-0.5f);



        val entries1: ArrayList<BarEntry> = ArrayList()
        for (i in empwiseAmtBar1.indices) {
            val score = empwiseAmtBar1[i]
            entries1.add(BarEntry(i.toFloat(), score.ActualAmount.toFloat()))
        }


        val entries2: ArrayList<BarEntry> = ArrayList()
        for (i in empwiseAmtBar1.indices) {
            val score = empwiseAmtBar1[i]
            entries2.add(BarEntry(i.toFloat(), score.TargetAmount.toFloat()))
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
        baraData.barWidth=0.2f
        //      projectDelayedChart.data=baraData

//        val data = BarData(barDataSet1)
//
//        data.setValueTextSize(15f)
//        data.setValueTextColor(Color.BLACK)
        baraData.setDrawValues(false)
        empwiseamt_barchart.data = baraData

        //   projectDelayedChart.isDragEnabled=true
        //   projectDelayedChart.setVisibleXRangeMaximum(3.0f)

        var barspace=0.0f
        var groupspace=0.3f
        // projectDelayedChart.getAxis().axisMinimum= 0F
        empwiseamt_barchart.groupBars(0F,groupspace,barspace)

//        projectDelayedChart.setFitBars(true);
//        projectDelayedChart.setVisibleXRangeMaximum(3f);

        empwiseamt_barchart.invalidate()



    }

    inner class MyAxisFormatterBar22 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < empwiseAmtBar1.size) {
                empwiseAmtBar1[index].EmpFName
            } else {
                ""
            }
        }
    }

    private fun setempwiswtargetamt() {
        empwiseAmtBar.clear()
        empwiseAmtBar = getEmpAmtList()

        empwiseamt_barchart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = empwiseamt_barchart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        empwiseamt_barchart.axisRight.isEnabled = false
        //remove legend
        empwiseamt_barchart.legend.isEnabled = false
        empwiseamt_barchart!!.setScaleEnabled(true)
        //remove description label
        empwiseamt_barchart.description.isEnabled = false


        //add animation
        empwiseamt_barchart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar9()
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
        for (i in empwiseAmtBar.indices) {
            val empamt = empwiseAmtBar[i]
            entries.add(BarEntry(i.toFloat(), empamt.empwisecount.toFloat()))
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
        empwiseamt_barchart.data = data


        empwiseamt_barchart.invalidate()


    }
    private fun getScoreList(): java.util.ArrayList<EmployeewiseBarLead> {
        for (i in 0 until empwiseArrayList.length())
        {
            var jsonObject = empwiseArrayList.getJSONObject(i)
            scoreListBar.add(EmployeewiseBarLead(jsonObject.getString("EmpFName"),jsonObject.getString("ActualPercentage")))
        }

        return scoreListBar
    }


    private fun getEmpAmtList1(): ArrayList<EmpwiseAmtBarLead1> {
        for (i in 0 until emptargetamtArrayList.length())
        {
            var jsonObject = emptargetamtArrayList.getJSONObject(i)
            empwiseAmtBar1.add(EmpwiseAmtBarLead1(jsonObject.getString("EmpFName"),
                jsonObject.getString("ActualPercentage"),
                jsonObject.getString("ActualAmount"),
                jsonObject.getString("TargetAmount")
            )
            )
        }

        return empwiseAmtBar1
    }

    private fun getEmpAmtList(): ArrayList<EmpwiseAmtBarLead> {
        for (i in 0 until emptargetamtArrayList.length())
        {
            var jsonObject = emptargetamtArrayList.getJSONObject(i)
            empwiseAmtBar.add(EmpwiseAmtBarLead(jsonObject.getString("EmpFName"),jsonObject.getString("TargetAmount")))
        }

        return empwiseAmtBar
    }
    private fun getTop10List(): ArrayList<Top10BarLead> {
        for (i in 0 until top10ProductsArrayList.length())
        {
            var jsonObject = top10ProductsArrayList.getJSONObject(i)
            top10ListBar.add(Top10BarLead(jsonObject.getString("Productname"),jsonObject.getString("TotalCount")))
        }

        return top10ListBar
    }
    private fun getTopRevenueList(): ArrayList<TopRevenueBarLead> {
        for (i in 0 until topRevenueArrayList.length())
        {
            var jsonObject = topRevenueArrayList.getJSONObject(i)
            topRevenueBar.add(TopRevenueBarLead(jsonObject.getString("MediaName"),jsonObject.getString("LeadAmount")))
        }

        return topRevenueBar
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getLeadStagewiseforecast() {
        leadstagewiswforecast= 0
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

                                rclv_leadStagewiseforecast!!.visibility=View.VISIBLE
                                if (leadstagewiswforecast == 0) {
                                    leadstagewiswforecast++
                                val jObject = JSONObject(msg)
                                    hideforecastmode = 0
                                    hideLeadForecast()
                                Log.e(TAG, "msg   190   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("Leadstagewiseforcast")
                                        leadStagewiseArrayList =
                                            jobjt.getJSONArray("LeadstagewiseforcastList")
                                    val remark =
                                        jobjt.getString("Reamrk")
                                    ll_labelAll!!.visibility=View.VISIBLE
                                    txtv_LeadForecastRemark!!.visibility=View.GONE
                                    txt_chartlabel!!.visibility=View.VISIBLE
                                    txt_chartremark!!.visibility=View.VISIBLE
                                  //  txtv_LeadForecastRemark!!.setText(remark)
                                    txt_chartlabel!!.text="LEAD PROGRESS REPORT"
                                    txt_chartremark!!.text=remark
                                        //    tv_leadStatusTotal!!.setText(jobjt.getString("TotalCount"))
                                     //   Log.e(TAG, "array  125   " + leadStagewiseArrayList)

                                        setPieChart(leadStagewiseArrayList)
                                    leadcountmode=0
                                    hideLeadCount()

                                    //                                    val recycBarChart =
    //                                        findViewById(R.id.recycBarChart) as RecyclerView
                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
                                    rclv_leadStagewiseforecast!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter = BarChartAdapter(
                                            this@TileGraphActivity,
                                            leadStagewiseArrayList
                                        )
                                    rclv_leadStagewiseforecast!!.adapter = adapter

//
                                } else {
                                    ll_labelAll!!.visibility=View.GONE
                                    txt_chartlabel!!.visibility=View.GONE
                                    txt_chartremark!!.visibility=View.GONE
                                    rclv_leadStagewiseforecast!!.visibility=View.GONE
                                    txtv_LeadForecastRemark!!.visibility=View.GONE
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
                            } }else {
                                rclv_leadStagewiseforecast!!.visibility=View.GONE
                                txtv_LeadForecastRemark!!.visibility=View.GONE
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
            return ""
           /* return if (index < scoreListBar.size) {
                scoreListBar[index].empname
            } else {
                ""
            }*/
        }
    }
    inner class MyAxisFormatterBar3 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return ""
           /* return if (index < top10ListBar.size) {
                top10ListBar[index].topname
            } else {
                ""
            }*/
        }
    }
    inner class MyAxisFormatterBar4 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return ""
           /* return if (index < topRevenueBar.size) {
                topRevenueBar[index].topname
            } else {
                ""
            }*/
        }
    }
    inner class MyAxisFormatterBar9 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return ""
           /* return if (index < topRevenueBar.size) {
                topRevenueBar[index].topname
            } else {
                ""
            }*/
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

            LeadTileCount  = 0
            LeadAvgCount  = 0
            LeadTileOutstandCount  = 0

            getLeadTile()
            getLeadOutstandTile()
            getLeadAvgConvrsn()
            getLeadCountScenario()
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
                                            ID_ChartMode = jsonObject.getString("DashMode")
                                            chartNme=jsonObject.getString("DashBoardName")
                                            actv_mode!!.setText(jsonObject.getString("DashBoardName"))
                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)





                                            ll_Piechart!!.visibility = View.GONE
                                            ll_barchart!!.visibility = View.GONE
                                            ll_top10!!.visibility = View.GONE
                                            card_leadSourse!!.visibility = View.GONE
                                            card_leadstagecountwise!!.visibility = View.GONE
                                            card_employeewiseAvg!!.visibility = View.GONE
                                            card_leadActivity!!.visibility = View.GONE
                                            card_toprevenue!!.visibility = View.GONE
                                            ll_empwseamt!!.visibility = View.GONE
                                            ll_labelAll!!.visibility=View.GONE

                                           /* card_leadActivity!!.visibility = View.VISIBLE
                                            leadActivityText!!.visibility=View.VISIBLE*/


                                          //  crmStagewiseCount   = 0


                                            if (ID_ChartMode.equals("2")){
                                                ll_barchart!!.visibility=View.VISIBLE
                                                empwiselist = 0
                                                getEmployeewiseChart()
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE

                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("3")){
                                                ll_Piechart!!.visibility=View.VISIBLE
                                                leadstagewiswforecast=0
                                                // crmservicewiseCount = 0
                                                getLeadStagewiseforecast()
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("6")) {

                                                card_leadSourse!!.visibility = View.VISIBLE
                                                getLeadSource()
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                leadSourseList = 0
                                                dashname = jsonObject.getString("DashBoardName")


                                            }
                                            else if (ID_ChartMode.equals("4")) {
                                                card_leadstagecountwise!!.visibility = View.VISIBLE
                                                leadstagecountwiseList = 0
                                                getLeadStageCountWise()
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("8")) {

                                                card_employeewiseAvg!!.visibility = View.VISIBLE
                                                getEmployeeWiseAvgConversion()
                                                employeewiseavgconvesionList=0
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("1")) {

                                                card_leadActivity!!.visibility = View.VISIBLE
                                                leadActivityList = 0
                                                getLeadActivity()
                                                tvv_lemo_StagWise!!.visibility=View.GONE


                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("5")){
                                                ll_top10!!.visibility=View.VISIBLE
                                                top10productlist=0
                                                getTop10Products()
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("7")){

                                                card_toprevenue!!.visibility = View.VISIBLE
                                                getTopRevenue()
                                                toprevenuelist=0
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")

                                            }
                                            else if (ID_ChartMode.equals("9")){
                                                ll_empwseamt!!.visibility = View.VISIBLE
                                                getEmpwiseAmountTarget()
                                                employeewisetargetamtList=0
                                                tvv_lemo_StagWise!!.visibility=View.VISIBLE
                                                dashname = jsonObject.getString("DashBoardName")


                                            }






                                        /*
                                        * */
                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }
                                   else
                                   {
                                       val builder = AlertDialog.Builder(
                                           this@TileGraphActivity,
                                           R.style.MyDialogTheme
                                       )
                                       builder.setMessage("No Active Chart Settings")
                                       builder.setPositiveButton("Ok") {
                                               dialogInterface, which ->


                                       }
                                       val alertDialog: AlertDialog = builder.create()
                                       alertDialog.setCancelable(false)
                                       alertDialog.show()
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
            modeTypeID[i] = objects.getString("DashMode");
            //   ID_ChartMode = objects.getString("ID_Mode")

            Log.e(TAG, "00000111   " + ID_ChartMode)
            Log.e(TAG, "85456214   " + modeType)

            val adapter = CustomAdapter(this, R.layout.custom_dropdown_item, modeType)
         //   val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
                ID_ChartMode = modeTypeID[position]
                chartNme = modeType[position]


             /*   ll_barchart!!.visibility=View.VISIBLE
                ll_Piechart!!.visibility=View.GONE
                ll_top10!!.visibility=View.GONE
                ll_empwseamt!!.visibility = View.GONE

*/
                ll_labelAll!!.visibility=View.GONE
                leadcountmode = 0
                if (ID_ChartMode.equals("2")){
                    ll_barchart!!.visibility=View.VISIBLE
                    ll_Piechart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    empwiselist = 0

                    getEmployeewiseChart()


                }
                else if (ID_ChartMode.equals("3")){
                    ll_Piechart!!.visibility=View.VISIBLE
                    ll_barchart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    leadstagewiswforecast = 0
                    getLeadStagewiseforecast()

                }
                else if (ID_ChartMode.equals("6")) {
                    ll_Piechart!!.visibility = View.GONE
                    ll_barchart!!.visibility = View.GONE
                    ll_top10!!.visibility = View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    card_leadSourse!!.visibility = View.VISIBLE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    getLeadSource()
                    leadSourseList = 0


                }
                else if (ID_ChartMode.equals("4")) {
                    ll_Piechart!!.visibility = View.GONE
                    ll_barchart!!.visibility = View.GONE
                    ll_top10!!.visibility = View.GONE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.VISIBLE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    leadstagecountwiseList = 0
                    hideLeadCount()
                    getLeadStageCountWise()

                }
                else if (ID_ChartMode.equals("8")) {
                    ll_Piechart!!.visibility = View.GONE
                    ll_barchart!!.visibility = View.GONE
                    ll_top10!!.visibility = View.GONE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.VISIBLE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    getEmployeeWiseAvgConversion()
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    employeewiseavgconvesionList=0

                }
                else if (ID_ChartMode.equals("1")) {
                    ll_Piechart!!.visibility = View.GONE
                    ll_barchart!!.visibility = View.GONE
                    ll_top10!!.visibility = View.GONE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.VISIBLE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    tvv_lemo_StagWise!!.visibility=View.GONE
                    getLeadActivity()
                    leadActivityList = 0

                }
                else if (ID_ChartMode.equals("5")){
                    ll_Piechart!!.visibility=View.GONE
                    ll_barchart!!.visibility=View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    ll_top10!!.visibility=View.VISIBLE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.GONE
                    getTop10Products()

                }
                else if (ID_ChartMode.equals("7")){
                    ll_Piechart!!.visibility=View.GONE
                    ll_barchart!!.visibility=View.GONE
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    ll_top10!!.visibility=View.GONE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.VISIBLE
                    ll_empwseamt!!.visibility = View.GONE
                    getTopRevenue()
                    toprevenuelist=0


                }
                else if (ID_ChartMode.equals("9")){
                    ll_empwiseamtt!!.visibility = View.GONE
                    ll_Piechart!!.visibility=View.GONE
                    ll_barchart!!.visibility=View.GONE
                    ll_top10!!.visibility=View.GONE
                    card_leadSourse!!.visibility = View.GONE
                    card_leadstagecountwise!!.visibility = View.GONE
                    card_employeewiseAvg!!.visibility = View.GONE
                    card_leadActivity!!.visibility = View.GONE
                    card_toprevenue!!.visibility = View.GONE
                    ll_empwseamt!!.visibility = View.VISIBLE
                    getEmpwiseAmountTarget()
                    tvv_lemo_StagWise!!.visibility=View.VISIBLE
                    employeewisetargetamtList=0


                }
                Log.e(TAG,"ID_ChartMode  253332   "+position)
            }

        }
    }


    private fun getTop10Products() {
        top10productlist=0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                top10LeadViewModel.getLeadTop10Products(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                if (top10productlist == 0) {
                                    top10productlist++
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   top10 products   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("Top10ProductsinLead")
                                    top10ProductsArrayList =
                                        jobjt.getJSONArray("Top10ProductsinLeadlist")

                                    val remark =
                                        jobjt.getString("Reamrk")
                                    val XAxis =
                                        jobjt.getString("XAxis")
                                    val YAxis =
                                        jobjt.getString("YAxis")
                                 //   txtv_top10Remrk!!.setText(remark)
                                    ll_labelAll!!.visibility=View.VISIBLE
                                    txtv_top10Remrk!!.visibility=View.GONE
                                    txt_chartlabel!!.visibility=View.VISIBLE
                                    txt_chartremark!!.visibility=View.VISIBLE
                                    ll_top10_XY!!.visibility=View.VISIBLE
                                    txt_chartlabel!!.text="TOP 10 ENQUIRY PRODUCTS IN LEAD"
                                    txt_chartremark!!.text=remark
                                    top10_xaxis!!.text=XAxis
                                    top10_yaxis!!.text=YAxis
                                    top10mode=0
                                    hidetop10()

                                    //  tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  top10   " + top10ProductsArrayList)
                                    if (top10ProductsArrayList.length() > 0){
                                        settop10chart()
//                                    val recycPieChart =
//                                        findViewById(R.id.recycPieChart) as RecyclerView
                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
                                        rclv_top10!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter = Top10LeadAdapter(
                                            this@TileGraphActivity,
                                            top10ProductsArrayList
                                        )
                                        rclv_top10!!.adapter = adapter
                                    }
                                    else
                                    {
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
                                        ll_top10_XY!!.visibility=View.GONE
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
                                    ll_labelAll!!.visibility=View.GONE
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
                            }} else {
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
    private fun getTopRevenue() {
        toprevenuelist= 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                topRevenueViewModel.getRevenue(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                if (toprevenuelist == 0) {
                                    toprevenuelist++

                                    val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   toprevenue   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    toprevenuemode=0
                                    hideTopRevenue()
                                    val jobjt = jObject.getJSONObject("ExpenseVSGain")
                                    topRevenueArrayList =
                                        jobjt.getJSONArray("ExpenseVSGainList")
                                    val remark =
                                        jobjt.getString("Reamrk")


                                    val XAxis =
                                        jobjt.getString("XAxis")
                                    val YAxis =
                                        jobjt.getString("YAxis")
                                    ll_labelAll!!.visibility=View.VISIBLE
                                    txt_chartlabel!!.visibility=View.VISIBLE
                                    txt_chartremark!!.visibility=View.VISIBLE
                                    ll_toprevenue_XY!!.visibility=View.VISIBLE
                                    ll_ExpenseGain!!.visibility=View.VISIBLE
                                    txt_chartlabel!!.text="TOP REVENUE GENERATED CAMPAIGN"
                                    txt_chartremark!!.text=remark

                                    toprevenue_xaxis!!.text=XAxis
                                    toprevenue_yaxis!!.text=YAxis

                                    //  tv_leadStageTotal!!.setText(jobjt.getString("TotalCount"))
                                    Log.e(TAG, "array  top10   " + topRevenueArrayList)
                                    if (topRevenueArrayList.length() > 0){

                                        settoprevenuechartNew()  //new chart
 //                                       settoprevenuechart()  //old chart




//                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
//                                        rclv_toprevenue!!.layoutManager =
//                                            lLayout as RecyclerView.LayoutManager?
//                                        val adapter = TopRevenueAdapter(
//                                            this@TileGraphActivity,
//                                            topRevenueArrayList
//                                        )
//                                        rclv_toprevenue!!.adapter = adapter


                                        //............

                                        val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                        recycTopRevenue!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = TopRevenueTableAdapter(this@TileGraphActivity, topRevenueArrayList)
                                        recycTopRevenue!!.adapter = adapter
                                        //............
                                    }
                                    else
                                    {
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
                                        ll_toprevenue_XY!!.visibility=View.GONE
                                        ll_ExpenseGain!!.visibility=View.GONE
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
                                    ll_labelAll!!.visibility=View.GONE
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
                        } }catch (e: Exception) {
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
    private fun getLeadSource() {

        leadSourseList = 0
        card_leadSourse!!.visibility = View.VISIBLE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                AreaListRepository.progressDialog = ProgressDialog(context, R.style.Progress)
                AreaListRepository.progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                AreaListRepository.progressDialog!!.setCancelable(false)
                AreaListRepository.progressDialog!!.setIndeterminate(true)
                AreaListRepository.progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                AreaListRepository.progressDialog!!.show()
                leadSourseViewModel.getLeadSourse(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (leadSourseList == 0) {
                                    leadSourseList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt =
                                            jObject.getJSONObject("LeadSource")
                                        val remark =
                                            jobjt.getString("Reamrk")

                                        val XAxis =
                                            jobjt.getString("XAxis")
                                        val YAxis =
                                            jobjt.getString("YAxis")

                                     //   leadSourseSubText!!.setText(remark)
                                        ll_labelAll!!.visibility=View.VISIBLE
                                        leadSourseSubText!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.VISIBLE
                                        txt_chartremark!!.visibility=View.VISIBLE
                                        ll_leadSource_XY!!.visibility=View.VISIBLE
                                        txt_chartlabel!!.text="LEAD SOURCE"
                                        txt_chartremark!!.text=remark

                                        leadsource_xaxis!!.text=XAxis
                                        leadsource_yaxis!!.text=YAxis

                                        leadSourseBarList =
                                            jobjt.getJSONArray("LeadSourceList")
                                        Log.e(TAG, "stockListArrayList==   " + leadSourseBarList)
                                        Log.e(TAG, "remark==   " + remark)
                                        try {
                                            if (leadSourseBarList.length() > 0) {
                                                recycleLeadSourse!!.visibility=View.VISIBLE
                                                leadsourcemode=0
                                                hideLeadSource()
                                                setLeadSourseBar(leadSourseBarList, remark)
                                                val lLayout =
                                                    GridLayoutManager(this@TileGraphActivity, 2)
                                                recycleLeadSourse!!.layoutManager =
                                                    lLayout as RecyclerView.LayoutManager?
                                                val adapter = LeadSourseChartAdapter(
                                                    this@TileGraphActivity,
                                                    leadSourseBarList,
                                                    colorgroup()
                                                )
                                                recycleLeadSourse!!.adapter = adapter
                                            }
                                        } catch (e: Exception) {
                                            Log.e("exceptionStock344", "" + e.toString())
                                        }
                                    } else {

                                        ll_labelAll!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
                                        ll_leadSource_XY!!.visibility=View.GONE
                                        recycleLeadSourse!!.visibility=View.GONE
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
                                recycleLeadSourse!!.visibility=View.VISIBLE
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {   Log.e(TAG, "exception==   " + e)
                        }

                    })
                AreaListRepository.progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun setLeadSourseBar(leadSourseBarList: JSONArray, remark: String) {
        try {
            leadSourseChart = findViewById(R.id.leadSourse)
            val entries = mutableListOf<BarEntry>()
            val labels = mutableListOf<String>()
            for (i in 0 until leadSourseBarList.length()) {
                val jsonObject = leadSourseBarList.getJSONObject(i)
                val TotalCount = jsonObject.getString("TotalCount")
                val LeadFrom = jsonObject.getString("LeadFrom")
                entries.add(BarEntry(i.toFloat(), TotalCount.toFloat()))
                labels.add(LeadFrom)
            }
            val dataSet = BarDataSet(entries, "Lead From")
            val colors = colorgroup()
            dataSet.setColors(colors)
            val data = BarData(dataSet)
            leadSourseChart.data = data
            val description = Description()
            description.text = ""
            leadSourseChart.description = description

            //remove legend
            leadSourseChart.legend.isEnabled = false
            leadSourseChart!!.setScaleEnabled(false)
            // Customize X-axis
            val xAxis: XAxis = leadSourseChart.xAxis

            // Set position of X-axis at the bottom of the chart
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            // Set your custom labels here
//            val labels = listOf("Label1", "Label2", "Label3")
            xAxis.valueFormatter = LabelFormatter(labels) // Custom formatter for X-axis labels

            // Ensure that the label count and granularity match the number of entries
            xAxis.labelCount = entries.size
            xAxis.granularity = 1f

            // Enable X-axis grid lines
            xAxis.setDrawGridLines(true)
            xAxis.setDrawLabels(true)

            // Invalidate the chart to refresh it with the new settings
            leadSourseChart.invalidate()
        } catch (e: java.lang.Exception) {

        }
    }


    private fun colorgroup(): java.util.ArrayList<Int> {
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

        return colors
    }
    private class LabelFormatter(private val labels: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            // Ensure the index is within bounds
            val index = value.toInt().coerceIn(0, labels.size - 1)
            return labels[index]
        }
    }

    private fun getEmployeeWiseAvgConversion() {

        employeewiseavgconvesionList = 0
        card_employeewiseAvg!!.visibility=View.VISIBLE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                AreaListRepository.progressDialog = ProgressDialog(context, R.style.Progress)
                AreaListRepository.progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                AreaListRepository.progressDialog!!.setCancelable(false)
                AreaListRepository.progressDialog!!.setIndeterminate(true)
                AreaListRepository.progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                AreaListRepository.progressDialog!!.show()
                employeewiseAvgViewModel.getEmpAvgConversion(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (employeewiseavgconvesionList == 0) {
                                    employeewiseavgconvesionList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt =
                                            jObject.getJSONObject("EmployeeWiseConversionTime")
                                        val remark =
                                            jobjt.getString("Reamrk")

                                        val XAxis =
                                            jobjt.getString("XAxis")
                                        val YAxis =
                                            jobjt.getString("YAxis")
                                        ll_labelAll!!.visibility=View.VISIBLE
                                        //employeewiseAvgText!!.setText(remark)
                                        employeewiseAvgText!!.visibility=View.GONE
                                        empwise_avgmode=0
                                        hideEmpwiseavgconsn()
                                        txt_chartlabel!!.visibility=View.VISIBLE
                                        txt_chartremark!!.visibility=View.VISIBLE
                                        ll_empAVG_XY!!.visibility=View.VISIBLE
                                        txt_chartlabel!!.text="EMPLOYEE WISE AVERAGE CONVERSION"
                                        txt_chartremark!!.text=remark
                                        empAVG_xaxis!!.text=XAxis
                                        empAVG_yaxis!!.text=YAxis

                                        employeewiseAvgBarList =
                                            jobjt.getJSONArray("EmployeeWiseConversionTimeList")
                                        Log.e(TAG, "stockListArrayList==   " + employeewiseAvgBarList)
                                        Log.e(TAG, "remark==   " + remark)
                                        try {
                                            if (employeewiseAvgBarList.length() > 0) {
                                                setEmployeeAvgConversionBar(employeewiseAvgBarList,remark)
                                                val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
                                                recycleemployeewiseAvg!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = EmployeeAvgConversionAdapter(this@TileGraphActivity, employeewiseAvgBarList,colorgroup())
                                                recycleemployeewiseAvg!!.adapter = adapter


                                            }
                                        } catch (e: Exception) {
                                            Log.e("exceptionStock344", "" + e.toString())
                                        }
                                    } else {
                                        ll_labelAll!!.visibility=View.GONE
                                        ll_empAVG_XY!!.visibility=View.GONE
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

                            Log.e(TAG, "exception==   " + e)
                        }

                    })
                AreaListRepository.progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun getEmpwiseAmountTarget() {

        employeewisetargetamtList = 0
        ll_empwseamt!!.visibility=View.VISIBLE
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
                employeewiseTargetAmountViewModel.getEmployeewisetargetAmountChart(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg empwiseamount==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (employeewisetargetamtList == 0) {
                                    employeewisetargetamtList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt =
                                            jObject.getJSONObject("EmployeeWiseTaegetInPercentage")
//                                        val remark =
//                                            jobjt.getString("Reamrk")
//                                        employeewiseAvgText!!.setText(remark)

                                        val remark =
                                            jobjt.getString("Reamrk")

                                        val XAxis =
                                            jobjt.getString("XAxis")
                                        val YAxis =
                                            jobjt.getString("YAxis")
                                        ll_labelAll!!.visibility=View.VISIBLE
                                        txt_chartlabel!!.text="EMPLOYEE WISE TARGET(IN AMOUNT)"
                                        txt_chartremark!!.text=remark
                                      //  txtv_empamtRemrk!!.setText(remark)
                                     //   txtv_empamtRemrk!!.visibility=View.GONE
                                        ll_EmpTarg_XY!!.visibility=View.VISIBLE
                                        ll_ActualTarg!!.visibility=View.VISIBLE
                                        empwse_amtmode=0
                                        hideempwseamt()


                                        EmpTarg_xaxis!!.text=XAxis
                                        EmpTarg_yaxis!!.text=YAxis

                                        emptargetamtArrayList =
                                            jobjt.getJSONArray("EmployeeWiseTaegetDetails")
                                        Log.e(TAG, "empwiseamtArrayList==   " + emptargetamtArrayList)
                                      //  Log.e(TAG, "remark==   " + remark)
                                        try {
                                            if (emptargetamtArrayList.length() > 0) {
                                                setempwiswtargetamtNew()
                                           //     setempwiswtargetamt()
                                               // setEmployeeAvgConversionBar(emptargetamtArrayList,remark)
                                             //   setEmployeeAvgConversionBar(emptargetamtArrayList)


//                                                val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
//                                                rclv_empwiseamt!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                                val adapter = EmployeewiseAmountAdapter(this@TileGraphActivity, emptargetamtArrayList)
//                                                rclv_empwiseamt!!.adapter = adapter

                                                //............

                                                val lLayout = GridLayoutManager(this@TileGraphActivity, 1)
                                                recycEmpWiseTarg!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = EmpWiseTargTableAdapter(this@TileGraphActivity, emptargetamtArrayList)
                                                recycEmpWiseTarg!!.adapter = adapter
                                                //............


                                            }
                                        } catch (e: Exception) {
                                            Log.e("exceptionStock344", "" + e.toString())
                                        }
                                    } else {
                                        ll_labelAll!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
                                        ll_EmpTarg_XY!!.visibility=View.GONE
                                        ll_ActualTarg!!.visibility=View.GONE
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

                            Log.e(TAG, "exception==   " + e)
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

    private fun setEmployeeAvgConversionBar(employeewiseAvgBarList: JSONArray, remark: String) {
        try {
            employeewiseAvgChart = findViewById(R.id.employeewiseAvgChart)
            val entries = mutableListOf<BarEntry>()
            val labels = mutableListOf<String>()
            for (i in 0 until employeewiseAvgBarList.length()) {
                val jsonObject = employeewiseAvgBarList.getJSONObject(i)
                val TotalCount = jsonObject.getString("Conversion")
                val LeadFrom = jsonObject.getString("EmployeeName")
                entries.add(BarEntry(i.toFloat(), TotalCount.toFloat()))
                labels.add(LeadFrom)
            }
            val dataSet = BarDataSet(entries, "Employee")
            val colors = colorgroup()
            dataSet.setColors(colors)
            val data = BarData(dataSet)
            employeewiseAvgChart.data = data
            val description = Description()
            description.text = ""
            employeewiseAvgChart.description = description
            // Customize X-axis
            val xAxis: XAxis = employeewiseAvgChart.xAxis

            // Set position of X-axis at the bottom of the chart
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            // Set your custom labels here
//            val labels = listOf("Label1", "Label2", "Label3")
            xAxis.valueFormatter = LabelFormatter(labels) // Custom formatter for X-axis labels

            // Ensure that the label count and granularity match the number of entries
            xAxis.labelCount = entries.size
            xAxis.granularity = 1f

            // Enable X-axis grid lines
            xAxis.setDrawGridLines(true)

            // Enable X-axis labels
            xAxis.setDrawLabels(true)

            // Invalidate the chart to refresh it with the new settings
            employeewiseAvgChart.invalidate()
        }
        catch (e:java.lang.Exception)
        {

        }

    }
    private fun getLeadStageCountWise() {

        leadstagecountwiseList = 0
        card_leadstagecountwise!!.visibility=View.VISIBLE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                AreaListRepository.progressDialog = ProgressDialog(context, R.style.Progress)
                AreaListRepository.progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                AreaListRepository.progressDialog!!.setCancelable(false)
                AreaListRepository.progressDialog!!.setIndeterminate(true)
                AreaListRepository.progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                AreaListRepository.progressDialog!!.show()
                leadstagecountwiseViewModel.getleadstagecountwise(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (leadstagecountwiseList == 0) {
                                    leadstagecountwiseList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt =
                                            jObject.getJSONObject("Leadstagecountwisefrorecast")
                                        val remark =
                                            jobjt.getString("Reamrk")
                                       // leadstagecountwiseText!!.setText(remark)
                                        ll_labelAll!!.visibility=View.VISIBLE
                                        leadstagecountwiseText!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.VISIBLE
                                        txt_chartremark!!.visibility=View.VISIBLE
                                        txt_chartlabel!!.text="LEAD FUNNEL"
                                        txt_chartremark!!.text=remark
                                        leadstagecountwiseListBarList =
                                            jobjt.getJSONArray("LeadstagecountwisefrorecastData")
                                        Log.e(TAG, "stockListArrayList==   " + leadstagecountwiseListBarList)
                                        Log.e(TAG, "remark==   " + remark)
                                        try {
                                            if (leadstagecountwiseListBarList.length() > 0) {
                                                setFunnelData(leadstagecountwiseListBarList)
                                                hideLeadCount()
                                                val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
                                                recycleleadstagecountwise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = LeadStageCountWiseAdapter(this@TileGraphActivity, leadstagecountwiseListBarList,colorgroup())
                                                recycleleadstagecountwise!!.adapter = adapter


                                            }
                                        } catch (e: Exception) {
                                        }
                                    } else {
                                        ll_labelAll!!.visibility=View.GONE
                                        txt_chartlabel!!.visibility=View.GONE
                                        txt_chartremark!!.visibility=View.GONE
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

                            Log.e(TAG, "exception==   " + e)
                        }

                    })
                AreaListRepository.progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setFunnelData(leadstagecountwiseListBarList: JSONArray) {
        val funnelChart: FunnelChartView = findViewById(R.id.funnelChart)
        val mDataSet = ArrayList<FunnelChartData>()
        val colorgroup=colorgroup()
        for (i in 0 until leadstagecountwiseListBarList.length()) {
            val jsonObject = leadstagecountwiseListBarList.getJSONObject(i)
            val StageName = jsonObject.getString("StageName")
            val TotalCount = jsonObject.getString("TotalCount")
            Log.v("array","code  "+colorgroup+"\n"+"#"+colorgroup.get(i).hashCode())
           // mDataSet.add(FunnelChartData("#"+colorgroup.get(i).hashCode(), StageName))

            val hexColorCode = String.format("#%06X", 0xFFFFFF and colorgroup.get(i))
            mDataSet.add(FunnelChartData(hexColorCode, StageName))
        }
        funnelChart.setmDataSet(mDataSet)
    }

    private fun getLeadActivity() {

        leadActivityList = 0
        card_leadActivity!!.visibility=View.VISIBLE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                AreaListRepository.progressDialog = ProgressDialog(context, R.style.Progress)
                AreaListRepository.progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                AreaListRepository.progressDialog!!.setCancelable(false)
                AreaListRepository.progressDialog!!.setIndeterminate(true)
                AreaListRepository.progressDialog!!.setIndeterminateDrawable(
                    context.resources.getDrawable(
                        R.drawable.progress
                    )
                )
                AreaListRepository.progressDialog!!.show()
                leadActivityViewModels.getleadActivity(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (leadActivityList == 0) {
                                    leadActivityList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt =
                                            jObject.getJSONObject("LeadActivites")
                                        val remark =
                                            jobjt.getString("Reamrk")
                                        ll_labelAll!!.visibility=View.VISIBLE
                                        leadActivityText!!.visibility=View.VISIBLE
                                        leadActivityText!!.visibility=View.GONE
                                       // leadActivityText!!.setText(remark)

                                        txt_chartlabel!!.visibility=View.VISIBLE
                                        txt_chartremark!!.visibility=View.VISIBLE
                                        txt_chartlabel!!.text="LEAD ACTIVITIES"
                                        txt_chartremark!!.text=remark

                                        leadActivityBarList =
                                            jobjt.getJSONArray("LeadActivitesList")
                                        Log.e(TAG, "stockListArrayList==   " + leadActivityBarList)
                                        Log.e(TAG, "remark==   " + remark)
                                        try {
                                            if (leadActivityBarList.length() > 0) {
//                                                setEmployeeAvgConversionBar(leadActivityBarList,remark)
                                                val lLayout = GridLayoutManager(this@TileGraphActivity, 2)
                                                recycleleadActivity!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = LeadActivityAdapter(this@TileGraphActivity, leadActivityBarList,colorgroup())
                                                recycleleadActivity!!.adapter = adapter


                                            }
                                        } catch (e: Exception) {
                                            Log.e("exceptionStock344", "" + e.toString())
                                        }
                                    } else {
                                        ll_labelAll!!.visibility=View.GONE
                                        txt_chartlabel!!.text=""
                                        txt_chartremark!!.text=""
                                        leadActivityText!!.visibility=View.GONE
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

                            Log.e(TAG, "exception==   " + e)
                        }

                    })
                AreaListRepository.progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun hideStageWise() {
        if (lemoStagWiseMode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_empwisebarchart!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_empwisebarchart!!.visibility = View.VISIBLE
        }
    }

    private fun hideLeadForecast() {
        if (hideforecastmode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_leadstagewiseforecast!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_leadstagewiseforecast!!.visibility = View.VISIBLE
        }

    }

    private fun hideLeadCount() {
        if (leadcountmode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_leadcount!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_leadcount!!.visibility = View.VISIBLE
        }

    }
    private fun hidetop10() {
        if (top10mode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_top10more!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_top10more!!.visibility = View.VISIBLE
        }

    }
    private fun hideLeadSource() {
        if (leadsourcemode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_lleadsrcemre!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_lleadsrcemre!!.visibility = View.VISIBLE
        }

    }
    private fun hideTopRevenue() {
        if (toprevenuemode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_toprevenue!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_toprevenue!!.visibility = View.VISIBLE
        }

    }
    private fun hideEmpwiseavgconsn() {
        if (empwise_avgmode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_empwiseavgm!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_empwiseavgm!!.visibility = View.VISIBLE
        }

    }
    private fun hideempwseamt() {
        if (empwse_amtmode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_empwiseamtt!!.visibility = View.GONE
        }else {
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_empwiseamtt!!.visibility = View.VISIBLE
        }

    }


}
