package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimalRemover
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class ServiceGraphActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = "ServiceGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var pieChartRef: WeakReference<PieChart>? = null

    var SubMode     :  String? = ""
    var label       :  String? = ""

    var TransDate   :  String? = "17-11-2023"
    var DashMode    :  String? = ""
    var DashType    :  String? = ""

    var chartModeCount    = 0
    var crmservicewiseCount    = 0
    var crmCountOfWPACount    = 0
    var crmStagewiseCount      = 0
    var TicketOutstandingCount  = 0
    var TicketStatusCount  = 0
    var OutStandingCount  = 0
    var AmcDueStatusCount  = 0
    var crmcomplaintwiseCount  = 0
    var crmtop1oproductCount  = 0
    var crmslastatusCount  = 0
    var crmchannelstatusCount  = 0

    var TabMode    = 0 // 0 = Graph , 1 = Tile
    var ContinueMode    = 0 // 0 = First , 1 = Second
    var ChartMode    = 0 // 0 = First , 1 = Second

    var lemoStagWiseMode    = 0  // 0=more , 1 = less


    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null

    private var actv_mode: AutoCompleteTextView? = null

    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null

    private var ll_StagWise: LinearLayout? = null
    private var ll_ComplaintWise: LinearLayout? = null
    private var ll_ServiceWise: LinearLayout? = null
    private var ll_ServiceCountOfWPA: LinearLayout? = null
    private var ll_ServiceTop10Product: LinearLayout? = null
    private var ll_ServiceSlaStatus: LinearLayout? = null
    private var ll_ServiceChannelStatus: LinearLayout? = null

    private var tv_StagWiseRemark: TextView? = null
    private var tv_ComplaintRemark: TextView? = null
    private var tv_ServiceRemark: TextView? = null

    private var tv_ServiceCountOfWPA: TextView? = null
    private var tv_ServiceTop10Product: TextView? = null
    private var tv_ServiceSlaStatus: TextView? = null
    private var tv_ServiceChannelStatus: TextView? = null

    private var tv_crmtile_Outstanding: TextView? = null
    private var tv_crmtile_Status: TextView? = null
    private var tv_crmtile_outstandcount: TextView? = null
    private var tv_crmtile_amcDue: TextView? = null

    private var tv_crmtile_Outstanding_remark: TextView? = null
    private var tv_crmtile_Status_remark: TextView? = null
    private var tv_crmtile_outstandcount_remark: TextView? = null
    private var tv_crmtile_amcDue_remark: TextView? = null

    private var tvv_head_StagWise: TextView? = null

    private var tvv_lemo_StagWise: TextView? = null

    private var ll_StagWiseRecyc: LinearLayout? = null



    lateinit var chartTypeViewModel               : ChartTypeViewModel

    lateinit var crmservicewiseViewModel          : CRMservicewiseViewModel
    lateinit var crmStagewiseDetailsViewModel     : CRMStagewiseDetailsViewModel
    lateinit var crmcomplaintwiseViewModel        : CRMcomplaintwiseViewModel
    lateinit var crmCountOfWPAViewModel           : CRMCountOfWPAViewModel
    lateinit var crmTop10ProductViewModel         : CRMTop10ProductViewModel
    lateinit var crmSLAStatusViewModel            : CRMSLAStatusViewModel
    lateinit var crmChannelStatusViewModel        : CRMChannelStatusViewModel

    lateinit var crmTileDashBoardDetailsViewModel : CRMTileDashBoardDetailsViewModel
    lateinit var crmTileTicketStatusViewModel : CRMTileTicketStatusViewModel
    lateinit var crmTileOutstandingCountViewModel : CRMTileOutstandingCountViewModel
    lateinit var crmTileAmcDueStatusViewModel : CRMTileAmcDueStatusViewModel

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
    var recycComplaintWise: RecyclerView? = null

    //ServiceWiseChart BarChart
    private lateinit var ServiceWiseChart: BarChart
    private var modelCRMServiceBar = ArrayList<ModelCRMServiceBar>()
    lateinit var serviceWiseArrayList: JSONArray
    var recycServiceWise: RecyclerView? = null

    //Count of Warranty, Paid And AMC BarChart
    private lateinit var ServiceCountOfWPAChart: BarChart
    private var modelCRMCountOfWPABar = ArrayList<ModelCRMCountOfWPABar>()
    lateinit var countOfWPAArrayList: JSONArray
    var recycServiceCountOfWPA: RecyclerView? = null

    //Top 10 Products In CRM BarChart
    private lateinit var ServiceTop10ProductChart: BarChart
    private var modelCRMTop10 = ArrayList<ModelCRMTop10>()  // change key word in Model class
    lateinit var top10ProductArrayList: JSONArray
    var recycServiceTop10Product: RecyclerView? = null

    //SLA Status PieChart
    private lateinit var slaStatusPieChart: PieChart
    private var modelCRMSlaStatus = ArrayList<ModelCRMSlaStatus>()  // change key word in Model class
    lateinit var slaStatusArrayList: JSONArray
    var recycServiceSlaStatus: RecyclerView? = null

    private lateinit var ServiceSlaStatusChart: BarChart

    //CRMChannelWise In CRM BarChart
    private lateinit var ServiceChannelStatusChart: BarChart
    private var modeCRMChannelWise = ArrayList<ModeCRMChannelWise>()  // change key word in Model class
    lateinit var crmChannelWiseArrayList: JSONArray
    var recycServiceChannelStatus: RecyclerView? = null


    // Tile

    private var ll_crmtile_outstanding: LinearLayout? = null
    private var ll_crmtile_status: LinearLayout? = null
    private var ll_crmtile_outstandcount: LinearLayout? = null
    private var ll_crmtile_amcDue: LinearLayout? = null

    private var recyc_crmtile_Outstanding: RecyclerView? = null
    private var recyc_crmtile_Status: RecyclerView? = null
    private var recyc_crmtile_outstandcount: RecyclerView? = null
    private var recyc_crmtile_amcDue: RecyclerView? = null

    var crmOutstandingAdapter:  CrmOutstandingAdapter? = null
    var crmStatusAdapter:  CrmStatusAdapter? = null
    var crmOutstandingCountAdapter:  CrmOutstandingCountAdapter? = null
    var crmAmcDueStatusAdapter:  CrmAmcDueStatusAdapter? = null

    lateinit var crmOutstandingArrayList : JSONArray
    lateinit var crmStatusArrayList : JSONArray
    lateinit var crmoutstandingCountArrayList : JSONArray
    lateinit var crmAmcDueStatusArrayList : JSONArray

    var drawableMore : Drawable? = null
    var drawableLess : Drawable? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_graph)
        context = this@ServiceGraphActivity


        crmservicewiseViewModel          = ViewModelProvider(this).get(CRMservicewiseViewModel::class.java)
        crmStagewiseDetailsViewModel     = ViewModelProvider(this).get(CRMStagewiseDetailsViewModel::class.java)
        crmTileDashBoardDetailsViewModel = ViewModelProvider(this).get(CRMTileDashBoardDetailsViewModel::class.java)
        crmTileTicketStatusViewModel     = ViewModelProvider(this).get(CRMTileTicketStatusViewModel::class.java)
        crmTileOutstandingCountViewModel = ViewModelProvider(this).get(CRMTileOutstandingCountViewModel::class.java)
        crmTileAmcDueStatusViewModel = ViewModelProvider(this).get(CRMTileAmcDueStatusViewModel::class.java)
        crmcomplaintwiseViewModel        = ViewModelProvider(this).get(CRMcomplaintwiseViewModel::class.java)
        crmCountOfWPAViewModel         = ViewModelProvider(this).get(CRMCountOfWPAViewModel::class.java)
        crmTop10ProductViewModel         = ViewModelProvider(this).get(CRMTop10ProductViewModel::class.java)
        crmSLAStatusViewModel            = ViewModelProvider(this).get(CRMSLAStatusViewModel::class.java)
        crmChannelStatusViewModel        = ViewModelProvider(this).get(CRMChannelStatusViewModel::class.java)
        chartTypeViewModel               = ViewModelProvider(this).get(ChartTypeViewModel::class.java)

        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")

        Log.e(TAG,"3555   "+SubMode+"  :  "+label)
        getCurrentDate()

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



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        drawableMore = resources.getDrawable(R.drawable.dash_more, null)
        drawableLess = resources.getDrawable(R.drawable.dash_less, null)

        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        tv_StagWiseRemark      = findViewById<TextView>(R.id.tv_StagWiseRemark)
        tv_ComplaintRemark      = findViewById<TextView>(R.id.tv_ComplaintRemark)
        tv_ServiceRemark      = findViewById<TextView>(R.id.tv_ServiceRemark)

        tv_ServiceCountOfWPA      = findViewById<TextView>(R.id.tv_ServiceCountOfWPA)
        tv_ServiceTop10Product      = findViewById<TextView>(R.id.tv_ServiceTop10Product)
        tv_ServiceSlaStatus      = findViewById<TextView>(R.id.tv_ServiceSlaStatus)
        tv_ServiceChannelStatus      = findViewById<TextView>(R.id.tv_ServiceChannelStatus)

        tv_crmtile_Outstanding      = findViewById<TextView>(R.id.tv_crmtile_Outstanding)
        tv_crmtile_Status      = findViewById<TextView>(R.id.tv_crmtile_Status)
        tv_crmtile_outstandcount      = findViewById<TextView>(R.id.tv_crmtile_outstandcount)
        tv_crmtile_amcDue      = findViewById<TextView>(R.id.tv_crmtile_amcDue)


        tv_crmtile_Outstanding_remark      = findViewById<TextView>(R.id.tv_crmtile_Outstanding_remark)
        tv_crmtile_Status_remark      = findViewById<TextView>(R.id.tv_crmtile_Status_remark)
        tv_crmtile_outstandcount_remark      = findViewById<TextView>(R.id.tv_crmtile_outstandcount_remark)
        tv_crmtile_amcDue_remark      = findViewById<TextView>(R.id.tv_crmtile_amcDue_remark)

        tvv_head_StagWise      = findViewById<TextView>(R.id.tvv_head_StagWise)

        tvv_lemo_StagWise      = findViewById<TextView>(R.id.tvv_lemo_StagWise)

        ll_StagWiseRecyc      = findViewById<LinearLayout>(R.id.ll_StagWiseRecyc)

    //    tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)

        tvv_lemo_StagWise!!.setOnClickListener(this)
        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)

        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)

        ll_StagWise         = findViewById<LinearLayout>(R.id.ll_StagWise)
        ll_ComplaintWise    = findViewById<LinearLayout>(R.id.ll_ComplaintWise)
        ll_ServiceWise      = findViewById<LinearLayout>(R.id.ll_ServiceWise)
        ll_ServiceCountOfWPA      = findViewById<LinearLayout>(R.id.ll_ServiceCountOfWPA)
        ll_ServiceTop10Product      = findViewById<LinearLayout>(R.id.ll_ServiceTop10Product)
        ll_ServiceSlaStatus      = findViewById<LinearLayout>(R.id.ll_ServiceSlaStatus)
        ll_ServiceChannelStatus      = findViewById<LinearLayout>(R.id.ll_ServiceChannelStatus)


        StagWiseChart       = findViewById<BarChart>(R.id.StagWiseChart)
        ComplaintWiseChart  = findViewById<BarChart>(R.id.ComplaintWiseChart)
        ServiceWiseChart    = findViewById<BarChart>(R.id.ServiceWiseChart)
        ServiceCountOfWPAChart    = findViewById<BarChart>(R.id.ServiceCountOfWPAChart)
        ServiceTop10ProductChart    = findViewById<BarChart>(R.id.ServiceTop10ProductChart)
        ServiceChannelStatusChart    = findViewById<BarChart>(R.id.ServiceChannelStatusChart)

        slaStatusPieChart    = findViewById<PieChart>(R.id.slaStatusPieChart)
        ServiceSlaStatusChart    = findViewById<BarChart>(R.id.ServiceSlaStatusChart)

        recycStagWise       = findViewById<RecyclerView>(R.id.recycStagWise)
        recycServiceWise    = findViewById<RecyclerView>(R.id.recycServiceWise)
        recycComplaintWise    = findViewById<RecyclerView>(R.id.recycComplaintWise)
        recycServiceCountOfWPA    = findViewById<RecyclerView>(R.id.recycServiceCountOfWPA)
        recycServiceTop10Product    = findViewById<RecyclerView>(R.id.recycServiceTop10Product)
        recycServiceSlaStatus    = findViewById<RecyclerView>(R.id.recycServiceSlaStatus)
        recycServiceChannelStatus    = findViewById<RecyclerView>(R.id.recycServiceChannelStatus)

        // Tile

        ll_crmtile_outstanding         = findViewById<LinearLayout>(R.id.ll_crmtile_outstanding)
        ll_crmtile_status    = findViewById<LinearLayout>(R.id.ll_crmtile_status)
        ll_crmtile_outstandcount    = findViewById<LinearLayout>(R.id.ll_crmtile_outstandcount)
        ll_crmtile_amcDue    = findViewById<LinearLayout>(R.id.ll_crmtile_amcDue)

        recyc_crmtile_Outstanding    = findViewById<RecyclerView>(R.id.recyc_crmtile_Outstanding)
        recyc_crmtile_Status    = findViewById<RecyclerView>(R.id.recyc_crmtile_Status)
        recyc_crmtile_outstandcount    = findViewById<RecyclerView>(R.id.recyc_crmtile_outstandcount)
        recyc_crmtile_amcDue    = findViewById<RecyclerView>(R.id.recyc_crmtile_amcDue)

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
//            crmStagewiseCount   = 0
//            getCRMStagewiseData()

//            crmtop1oproductCount = 0
//            getCRMTop10Product()

        }else if (TabMode == 1){
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

            TicketOutstandingCount = 0
            getTicketOutstandingData()

            TicketStatusCount = 0
            getTicketStatusData()

            OutStandingCount = 0
            getOutStandingCountData()

            AmcDueStatusCount = 0
            getAmcDueStatusData()

        }

    }



    private fun getChartModeData() {
        var ReqMode = ""
        var SubMode = "2"
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

                                            ll_StagWise!!.visibility = View.GONE
                                            ll_ComplaintWise!!.visibility = View.GONE
                                            ll_ServiceWise!!.visibility = View.GONE
                                            ll_ServiceCountOfWPA!!.visibility = View.GONE
                                            ll_ServiceTop10Product!!.visibility = View.GONE
                                            ll_ServiceSlaStatus!!.visibility = View.GONE
                                            ll_ServiceChannelStatus!!.visibility = View.GONE

                                            if (ID_ChartMode.equals("10")){

                                                tvv_head_StagWise!!.setText(jsonObject.getString("DashBoardName"))
                                                crmStagewiseCount   = 0
                                                getCRMStagewiseData()
                                            }
                                            else if (ID_ChartMode.equals("11")){
                                                ll_ComplaintWise!!.visibility = View.VISIBLE
                                                crmcomplaintwiseCount = 0
                                                getCRMcomplaintwiseData()
                                            }
                                            else if (ID_ChartMode.equals("12")){
                                                ll_ServiceWise!!.visibility = View.VISIBLE
                                                crmservicewiseCount = 0
                                                getCRMservicewiseData()
                                            }
                                            else if (ID_ChartMode.equals("13")){
                                                ll_ServiceCountOfWPA!!.visibility = View.VISIBLE
                                                crmCountOfWPACount = 0
                                                getCRMCountOfWPAData()
                                            }

                                            else if (ID_ChartMode.equals("14")){
                                                ll_ServiceTop10Product!!.visibility = View.VISIBLE
                                                crmtop1oproductCount = 0
                                                getCRMTop10Product()
                                            }
                                            else if (ID_ChartMode.equals("15")){
                                                ll_ServiceSlaStatus!!.visibility = View.VISIBLE
                                                crmslastatusCount = 0
                                                getCRMSlaStatus()
                                            }
                                            else if (ID_ChartMode.equals("16")){
//                                                SLA CHANNEL
                                                ll_ServiceChannelStatus!!.visibility = View.VISIBLE
                                                crmchannelstatusCount = 0
                                                getCRMChanelStatus()
                                            }



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
        var modeDashMode = Array<String>(chartTypeArrayList.length()) { "" }
        for (i in 0 until chartTypeArrayList.length()) {
            val objects: JSONObject = chartTypeArrayList.getJSONObject(i)


            modeType[i] = objects.getString("DashBoardName");
            modeTypeID[i] = objects.getString("DashMode");
            modeDashMode[i] = objects.getString("DashMode");
         //   ID_ChartMode = objects.getString("ID_Mode")

            Log.e(TAG, "00000111   " + ID_ChartMode)
            Log.e(TAG, "85456214   " + modeType)


            val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
              ID_ChartMode = modeTypeID[position]
              DashMode = modeDashMode[position]
                Log.e(TAG, "0000011122   " + ID_ChartMode)
                ll_StagWise!!.visibility = View.GONE
                ll_ComplaintWise!!.visibility = View.GONE
                ll_ServiceWise!!.visibility = View.GONE
                ll_ServiceCountOfWPA!!.visibility = View.GONE
                ll_ServiceTop10Product!!.visibility = View.GONE
                ll_ServiceSlaStatus!!.visibility = View.GONE
                ll_ServiceChannelStatus!!.visibility = View.GONE


                if (ID_ChartMode.equals("10")){
                    ll_StagWise!!.visibility = View.VISIBLE
                    crmStagewiseCount   = 0
                    getCRMStagewiseData()
                }
                else if (ID_ChartMode.equals("11")){
                    ll_ComplaintWise!!.visibility = View.VISIBLE
                    crmcomplaintwiseCount = 0
                    getCRMcomplaintwiseData()
                }
                else if (ID_ChartMode.equals("12")){
                    ll_ServiceWise!!.visibility = View.VISIBLE
                    crmservicewiseCount = 0
                    getCRMservicewiseData()
                }
                else if (ID_ChartMode.equals("13")){
                    ll_ServiceCountOfWPA!!.visibility = View.VISIBLE
                    crmCountOfWPACount = 0
                    getCRMCountOfWPAData()
                }
                else if (ID_ChartMode.equals("14")){
                    ll_ServiceTop10Product!!.visibility = View.VISIBLE
                    crmtop1oproductCount = 0
                    getCRMTop10Product()
                }
                else if (ID_ChartMode.equals("15")){
                    ll_ServiceSlaStatus!!.visibility = View.VISIBLE
                    crmslastatusCount = 0
                    getCRMSlaStatus()
                }

                else if (ID_ChartMode.equals("16")){
//                  SLA CHANNEL
                    ll_ServiceChannelStatus!!.visibility = View.VISIBLE
                    crmchannelstatusCount = 0
                    getCRMChanelStatus()
                }

                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }

    private fun getCRMservicewiseData() {
      //  DashMode = "13"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmservicewiseViewModel.getCRMservicewise(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
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
                                    tv_ServiceRemark!!.setText(jobjt.getString("Reamrk"))
                                    if (serviceWiseArrayList.length() > 0){

                                        setServiceBarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycServiceWise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmServiceChartAdapter(this@ServiceGraphActivity, serviceWiseArrayList)
                                        recycServiceWise!!.adapter = adapter
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

    private fun getCRMStagewiseData() {
       // DashMode = "10"
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
                                Log.e(TAG,"msg   16441   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMStagewiseDetails")
                                    stageWiseArrayList=jobjt.getJSONArray("CRMStagewiseDetailsList")
                                    tv_StagWiseRemark!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"3911 stageWiseArrayList  "+stageWiseArrayList)

                                    if (stageWiseArrayList.length() > 0){
                                        ll_StagWise!!.visibility = View.VISIBLE
                                        setStageBarchart()
                                        lemoStagWiseMode = 0

                                        hideStageWise()

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

    private fun hideStageWise() {
        if (lemoStagWiseMode == 0){
            tvv_lemo_StagWise!!.setText("More")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableMore, null)
            ll_StagWiseRecyc!!.visibility = View.GONE
        }else{
            tvv_lemo_StagWise!!.setText("Less")
            tvv_lemo_StagWise!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableLess, null)
            ll_StagWiseRecyc!!.visibility = View.VISIBLE
        }
    }

    private fun getCRMCountOfWPAData() {

       // DashMode = "11"
       // TransDate = "08-11-2023"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmCountOfWPAViewModel.getCRMCountOfWPA(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmCountOfWPACount == 0) {
                                crmCountOfWPACount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   70444   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMCountofWarrantyPaidandAMC")
                                    countOfWPAArrayList=jobjt.getJSONArray("CRMCountofWarrantyPaidandAMCList")
                                    tv_ServiceCountOfWPA!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"70444 countOfWPAArrayList  "+countOfWPAArrayList)

                                    if (countOfWPAArrayList.length() > 0){

                                        setCountOfWPABarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycServiceCountOfWPA!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmCountOfWPAChartAdapter(this@ServiceGraphActivity, countOfWPAArrayList)
                                        recycServiceCountOfWPA!!.adapter = adapter
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
        // DashMode = "11"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmcomplaintwiseViewModel.getCRMcomplaintwise(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
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
                                    tv_ComplaintRemark!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"3912 complaintWiseArrayList  "+complaintWiseArrayList)

                                    if (complaintWiseArrayList.length() > 0){

                                        setComplaintBarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycComplaintWise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmComplaintChartAdapter(this@ServiceGraphActivity, complaintWiseArrayList)
                                        recycComplaintWise!!.adapter = adapter
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

    private fun getCRMTop10Product() {
//        DashMode = "11"
      //  TransDate = "08-11-2023"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmTop10ProductViewModel.getCRMTop10Products(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmtop1oproductCount == 0) {
                                crmtop1oproductCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   8488   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMTop10Products")
                                    top10ProductArrayList=jobjt.getJSONArray("CRMTop10ProductsList")
                                    tv_ServiceTop10Product!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"8544 top10ProductArrayList  "+top10ProductArrayList)

                                    if (top10ProductArrayList.length() > 0){

                                        setTop10ProductBarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycServiceTop10Product!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmTop10ProductChartAdapter(this@ServiceGraphActivity, top10ProductArrayList)
                                        recycServiceTop10Product!!.adapter = adapter
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

    private fun getCRMSlaStatus() {
        DashMode = "15"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmSLAStatusViewModel.getCRMSLAStatusData(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmslastatusCount == 0) {
                                crmslastatusCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1644231   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMSLAViolationStatus")
                                    slaStatusArrayList=jobjt.getJSONArray("CRMSLAViolationStatusList")
                                    tv_ServiceSlaStatus!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"1644232 slaStatusArrayList  "+slaStatusArrayList)

                                    if (slaStatusArrayList.length() > 0){

//                                        setSlaStatusPiechart()
                                        setSlaStatusPiechart1()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycServiceSlaStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmSlaStatusChartAdapter(this@ServiceGraphActivity, slaStatusArrayList)
                                        recycServiceSlaStatus!!.adapter = adapter
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



    private fun getCRMChanelStatus() {

//        DashMode = "11"
//        TransDate = "08-11-2023"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                crmChannelStatusViewModel.getCRMChannelStatusData(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (crmchannelstatusCount == 0) {
                                crmchannelstatusCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   100055   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMChannelWise")
                                    crmChannelWiseArrayList=jobjt.getJSONArray("CRMChannelWiseList")
                                    tv_ServiceChannelStatus!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"39123 slaStatusArrayList  "+crmChannelWiseArrayList)

                                    if (crmChannelWiseArrayList.length() > 0){

                                        setChannelStatusBarchart()

                                        val lLayout = GridLayoutManager(this@ServiceGraphActivity, 2)
                                        recycServiceChannelStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CrmChannelWiseChartAdapter(this@ServiceGraphActivity, crmChannelWiseArrayList)
                                        recycServiceChannelStatus!!.adapter = adapter
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


    // Tile


    private fun getTicketOutstandingData() {
        var DashMode2 = "4"
        DashType = "1"
        ll_crmtile_outstanding!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                crmTileDashBoardDetailsViewModel.getCRMTileDashBoardDetails(this,TransDate!!,DashMode2!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg  Outstandin  27661   "+msg)
                        if (msg!!.length > 0) {
                            if (TicketOutstandingCount == 0) {
                                TicketOutstandingCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   88441   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMTileDashBoardDetails")
                                    tv_crmtile_Outstanding!!.setText(jobjt.getString("ChartName"))
                                    tv_crmtile_Outstanding_remark!!.setText(jobjt.getString("Reamrk"))
                                    crmOutstandingArrayList = jobjt.getJSONArray("CRMTileDashBoardDetailsList")
                                    if (crmOutstandingArrayList.length() > 0){
                                        ll_crmtile_outstanding!!.visibility = View.VISIBLE
                                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                        recyc_crmtile_Outstanding!!.layoutManager = layoutManager
                                        crmOutstandingAdapter = CrmOutstandingAdapter(this@ServiceGraphActivity, crmOutstandingArrayList)
                                        recyc_crmtile_Outstanding!!.adapter = crmOutstandingAdapter
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

    private fun getTicketStatusData() {
        var DashMode1 = "5"
        DashType = "1"
        ll_crmtile_status!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                crmTileTicketStatusViewModel.getCRMTileTicketStatusData(this,TransDate!!,DashMode1!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg Status  276622   "+msg)
                        if (msg!!.length > 0) {
                            if (TicketStatusCount == 0) {
                                TicketStatusCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   88442   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMTileDashBoardDetails")
                                    tv_crmtile_Status!!.setText(jobjt.getString("ChartName"))
                                    tv_crmtile_Status_remark!!.setText(jobjt.getString("Reamrk"))
                                    crmStatusArrayList = jobjt.getJSONArray("CRMTileDashBoardDetailsList")
                                    if (crmStatusArrayList.length() > 0){
                                        ll_crmtile_status!!.visibility = View.VISIBLE
                                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                        recyc_crmtile_Status!!.layoutManager = layoutManager
                                        crmStatusAdapter = CrmStatusAdapter(this@ServiceGraphActivity, crmStatusArrayList)
                                        recyc_crmtile_Status!!.adapter = crmStatusAdapter
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

    private fun getOutStandingCountData() {
        var DashMode2 = "6"
        DashType = "1"
//        TransDate = "08-11-2023"
        ll_crmtile_outstandcount!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                crmTileOutstandingCountViewModel.getCRMTileOutstandingCountData(this,TransDate!!,DashMode2!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg Status  1003332   "+msg)
                        if (msg!!.length > 0) {
                            if (OutStandingCount == 0) {
                                OutStandingCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   88443   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMTileDashBoardDetails")
                                    tv_crmtile_outstandcount!!.setText(jobjt.getString("ChartName"))
                                    tv_crmtile_outstandcount_remark!!.setText(jobjt.getString("Reamrk"))
                                    crmoutstandingCountArrayList = jobjt.getJSONArray("CRMTileDashBoardDetailsList")
                                    if (crmoutstandingCountArrayList.length() > 0){
                                        ll_crmtile_outstandcount!!.visibility = View.VISIBLE
                                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                        recyc_crmtile_outstandcount!!.layoutManager = layoutManager
                                        crmOutstandingCountAdapter = CrmOutstandingCountAdapter(this@ServiceGraphActivity, crmoutstandingCountArrayList)
                                        recyc_crmtile_outstandcount!!.adapter = crmOutstandingCountAdapter
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

    private fun getAmcDueStatusData() {
        var DashMode3 = "7"
        DashType = "1"
//        TransDate = "08-11-2023"
        ll_crmtile_amcDue!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                crmTileAmcDueStatusViewModel.getCRMTileAmcDueStatuData(this,TransDate!!,DashMode3!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG,"msg Status  1003331   "+msg)
                        if (msg!!.length > 0) {
                            if (AmcDueStatusCount == 0) {
                                AmcDueStatusCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   88444   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CRMTileDashBoardDetails")
                                    tv_crmtile_amcDue!!.setText(jobjt.getString("ChartName"))
                                    tv_crmtile_amcDue_remark!!.setText(jobjt.getString("Reamrk"))
                                    crmAmcDueStatusArrayList = jobjt.getJSONArray("CRMTileDashBoardDetailsList")
                                    Log.e(TAG,"msg   884441   "+crmAmcDueStatusArrayList)
                                    if (crmAmcDueStatusArrayList.length() > 0){
                                        ll_crmtile_amcDue!!.visibility = View.VISIBLE
                                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                        recyc_crmtile_amcDue!!.layoutManager = layoutManager
                                        crmAmcDueStatusAdapter = CrmAmcDueStatusAdapter(this@ServiceGraphActivity, crmAmcDueStatusArrayList)
                                        recyc_crmtile_amcDue!!.adapter = crmAmcDueStatusAdapter
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



    ///////////////////
    private fun setStageBarchart() {
        modelCRMStageBar.clear()
        modelCRMStageBar = getStagWiseList()

        StagWiseChart.axisLeft.setDrawGridLines(false)
        StagWiseChart.setTouchEnabled(true)
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
           // modelCRMStageBar.add(ModelCRMStageBar(jsonObject.getString("StatusName"),jsonObject.getString("StatusCount")))
            modelCRMStageBar.add(ModelCRMStageBar("",jsonObject.getString("StatusCount")))
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

    private fun setServiceBarchart() {
        modelCRMServiceBar.clear()
        modelCRMServiceBar = getServiceWiseList()

        ServiceWiseChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ServiceWiseChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ServiceWiseChart.axisRight.isEnabled = false
        //remove legend
        ServiceWiseChart.legend.isEnabled = false
        ServiceWiseChart!!.setScaleEnabled(true)
        //remove description label
        ServiceWiseChart.description.isEnabled = false


        //add animation
        ServiceWiseChart.animateY(1000)

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
        for (i in modelCRMServiceBar.indices) {
            val score = modelCRMServiceBar[i]
            entries.add(BarEntry(i.toFloat(), score.TotalCount.toFloat()))
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
        ServiceWiseChart.data = data


        ServiceWiseChart.invalidate()


    }

    private fun getServiceWiseList(): ArrayList<ModelCRMServiceBar> {
        for (i in 0 until serviceWiseArrayList.length())
        {
            var jsonObject = serviceWiseArrayList.getJSONObject(i)
            // modelCRMStageBar.add(ModelCRMStageBar(jsonObject.getString("StatusName"),jsonObject.getString("StatusCount")))
            modelCRMServiceBar.add(ModelCRMServiceBar("",jsonObject.getString("TotalCount"),jsonObject.getString("ActualPercent")))
        }

        return modelCRMServiceBar
    }


    private fun setComplaintBarchart() {
        modelCRMComplaintBar.clear()
        modelCRMComplaintBar = getComplaintWiseList()

        ComplaintWiseChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ComplaintWiseChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ComplaintWiseChart.axisRight.isEnabled = false
        //remove legend
        ComplaintWiseChart.legend.isEnabled = false
        ComplaintWiseChart!!.setScaleEnabled(true)
        //remove description label
        ComplaintWiseChart.description.isEnabled = false


        //add animation
        ComplaintWiseChart.animateY(1000)

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
        for (i in modelCRMComplaintBar.indices) {
            val score = modelCRMComplaintBar[i]
            entries.add(BarEntry(i.toFloat(), score.ComplaintCount.toFloat()))
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
        ComplaintWiseChart.data = data


        ComplaintWiseChart.invalidate()


    }

    private fun getComplaintWiseList(): ArrayList<ModelCRMComplaintBar> {
        for (i in 0 until complaintWiseArrayList.length())
        {
            var jsonObject = complaintWiseArrayList.getJSONObject(i)
            // modelCRMStageBar.add(ModelCRMStageBar(jsonObject.getString("StatusName"),jsonObject.getString("StatusCount")))
            modelCRMComplaintBar.add(ModelCRMComplaintBar("",jsonObject.getString("ComplaintCount")))
        }

        return modelCRMComplaintBar
    }

    private fun setCountOfWPABarchart() {

        modelCRMCountOfWPABar.clear()
        modelCRMCountOfWPABar = getCountOfWPA()

        ServiceCountOfWPAChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ServiceCountOfWPAChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ServiceCountOfWPAChart.axisRight.isEnabled = false
        //remove legend
        ServiceCountOfWPAChart.legend.isEnabled = false
        ServiceCountOfWPAChart!!.setScaleEnabled(true)
        //remove description label
        ServiceCountOfWPAChart.description.isEnabled = false


        //add animation
        ServiceCountOfWPAChart.animateY(1000)

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
        for (i in modelCRMCountOfWPABar.indices) {
            val score = modelCRMCountOfWPABar[i]
            entries.add(BarEntry(i.toFloat(), score.TotalCount.toFloat()))
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
        ServiceCountOfWPAChart.data = data


        ServiceCountOfWPAChart.invalidate()
    }

    private fun getCountOfWPA(): ArrayList<ModelCRMCountOfWPABar> {
        for (i in 0 until countOfWPAArrayList.length())
        {
            var jsonObject = countOfWPAArrayList.getJSONObject(i)
            // modelCRMStageBar.add(ModelCRMStageBar(jsonObject.getString("StatusName"),jsonObject.getString("StatusCount")))
            modelCRMCountOfWPABar.add(ModelCRMCountOfWPABar("",jsonObject.getString("TotalCount")))
        }

        return modelCRMCountOfWPABar
    }

    private fun setTop10ProductBarchart() {

        modelCRMTop10.clear()
        modelCRMTop10 = gettop10ProductList()  // Change Model data

        ServiceTop10ProductChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ServiceTop10ProductChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ServiceTop10ProductChart.axisRight.isEnabled = false
        //remove legend
        ServiceTop10ProductChart.legend.isEnabled = false
        ServiceTop10ProductChart!!.setScaleEnabled(true)
        //remove description label
        ServiceTop10ProductChart.description.isEnabled = false


        //add animation
        ServiceTop10ProductChart.animateY(1000)

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
        for (i in modelCRMTop10.indices) {
            val score = modelCRMTop10[i]
            entries.add(BarEntry(i.toFloat(), score.Count.toFloat()))
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
        ServiceTop10ProductChart.data = data


        ServiceTop10ProductChart.invalidate()


    }

    private fun gettop10ProductList(): ArrayList<ModelCRMTop10> {
        for (i in 0 until top10ProductArrayList.length())
        {
            var jsonObject = top10ProductArrayList.getJSONObject(i)
            modelCRMTop10.add(ModelCRMTop10("",jsonObject.getString("Count")))
        }

        return modelCRMTop10
    }

    private fun setSlaStatusPiechart1() {


        modelCRMSlaStatus.clear()
        modelCRMSlaStatus = getSlaStatus()  // Change Model data

        ServiceSlaStatusChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ServiceSlaStatusChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ServiceSlaStatusChart.axisRight.isEnabled = false
        //remove legend
        ServiceSlaStatusChart.legend.isEnabled = false
        ServiceSlaStatusChart!!.setScaleEnabled(true)
        //remove description label
        ServiceSlaStatusChart.description.isEnabled = false


        //add animation
        ServiceSlaStatusChart.animateY(1000)

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
        for (i in modelCRMSlaStatus.indices) {
            val score = modelCRMSlaStatus[i]
            entries.add(BarEntry(i.toFloat(), score.Count.toFloat()))
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
        ServiceSlaStatusChart.data = data


        ServiceSlaStatusChart.invalidate()
    }

    private fun setSlaStatusPiechart() {
        pieChartRef = WeakReference(slaStatusPieChart)
        val slaStatusPieChart = pieChartRef?.get()
        modelCRMSlaStatus.clear()
        modelCRMSlaStatus = getSlaStatus()
        Log.v("asdasdssss", "size  " + modelCRMSlaStatus.size)

        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "%"
        slaStatusPieChart!!.setUsePercentValues(false)
        slaStatusPieChart.description.text = ""
        slaStatusPieChart.isDrawHoleEnabled = true
        slaStatusPieChart.setTouchEnabled(false)
        slaStatusPieChart.setDrawEntryLabels(false)

        //adding padding
//        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        //pieChart.setUsePercentValues(false)
//        val layout: LinearLayout = findViewById(R.id.id_lin2)
//        val width: Int = layout.getWidth()
//        val params: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
//        params.height = width
//        params.width = width
//        layout.layoutParams = params
        slaStatusPieChart.isRotationEnabled = true
        slaStatusPieChart.setRotationAngle(0f)
        slaStatusPieChart.animateY(1400, Easing.EaseInOutQuad)
        slaStatusPieChart.setDrawEntryLabels(false)
        slaStatusPieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        slaStatusPieChart.legend.isWordWrapEnabled = true
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        for (i in 0 until modelCRMSlaStatus.size) {
            val score = modelCRMSlaStatus[i]
            //typeAmountMap[""] = score.Piescore
            pieEntries.add(PieEntry(score.Count.toFloat(), "%"))

        }

        val colorsStage: ArrayList<Int> = ArrayList()


        colorsStage.add(resources.getColor(R.color.leadstages_color1))
        colorsStage.add(resources.getColor(R.color.leadstages_color2))
        colorsStage.add(resources.getColor(R.color.leadstages_color3))

        colorsStage.add(resources.getColor(R.color.leadstages_color4))
        colorsStage.add(resources.getColor(R.color.leadstages_color5))
        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color1))
//        colorsStage.add(resources.getColor(R.color.leadstages_color2))
//        colorsStage.add(resources.getColor(R.color.leadstages_color3))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color4))
//        colorsStage.add(resources.getColor(R.color.leadstages_color5))
//        colorsStage.add(resources.getColor(R.color.leadstages_color6))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color7))
//        colorsStage.add(resources.getColor(R.color.leadstages_color8))
//        colorsStage.add(resources.getColor(R.color.leadstages_color9))
//
//        colorsStage.add(resources.getColor(R.color.leadstages_color10))
//        colorsStage.add(resources.getColor(R.color.leadstages_color11))
//        colorsStage.add(resources.getColor(R.color.leadstages_color12))

        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.setValueFormatter(DecimalRemover())
        pieDataSet.valueTextSize = 12f
        pieDataSet.setColors(colorsStage)
        val pieData = PieData(pieDataSet)
        // pieData.setValueFormatter(PercentFormatter())
        //    pieData.setValueFormatter(DecimalRemover(DecimalFormat("########")))
        pieData.setDrawValues(true)

        val l: Legend = slaStatusPieChart.getLegend()
        l.isEnabled = false
//
        slaStatusPieChart.data = pieData
//        slaStatusPieChart.setOnClickListener(View.OnClickListener {
//            //ShowEnalargeGraph(pieEntries, 2)
//        })

        slaStatusPieChart.invalidate()


    }

    private fun getSlaStatus(): ArrayList<ModelCRMSlaStatus> {

        for (i in 0 until slaStatusArrayList.length()) {
            //apply your logic
            var jsonObject = slaStatusArrayList.getJSONObject(i)
            modelCRMSlaStatus.add(ModelCRMSlaStatus("", jsonObject.getString("Count")))
        }

        return modelCRMSlaStatus
    }

    private fun setChannelStatusBarchart() {

        modeCRMChannelWise.clear()
        modeCRMChannelWise = getChanenelStus()  // Change Model data

        ServiceChannelStatusChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ServiceChannelStatusChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ServiceChannelStatusChart.axisRight.isEnabled = false
        //remove legend
        ServiceChannelStatusChart.legend.isEnabled = false
        ServiceChannelStatusChart!!.setScaleEnabled(true)
        //remove description label
        ServiceChannelStatusChart.description.isEnabled = false


        //add animation
        ServiceChannelStatusChart.animateY(1000)

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
        for (i in modeCRMChannelWise.indices) {
            val score = modeCRMChannelWise[i]
            entries.add(BarEntry(i.toFloat(), score.ChannelCount.toFloat()))
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
        ServiceChannelStatusChart.data = data


        ServiceChannelStatusChart.invalidate()

    }

    private fun getChanenelStus(): ArrayList<ModeCRMChannelWise> {
        for (i in 0 until crmChannelWiseArrayList.length())
        {
            var jsonObject = crmChannelWiseArrayList.getJSONObject(i)
            modeCRMChannelWise.add(ModeCRMChannelWise("",jsonObject.getString("ChannelCount")))
        }

        return modeCRMChannelWise
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

            R.id.tvv_lemo_StagWise->{

                if (lemoStagWiseMode == 0){
                    lemoStagWiseMode = 1
                }else{
                    lemoStagWiseMode = 0
                }

                hideStageWise()
            }
        }
    }
}