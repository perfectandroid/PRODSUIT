package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
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
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.ModelDashExpenseChart
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AccountsGraphActivity : AppCompatActivity(), View.OnClickListener {


    val TAG : String = "AccountsGraphActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var SubMode     :  String? = ""
    var label       :  String? = ""

    var TransDate   :  String? = "08-11-2023"
    var DashMode    :  String? = ""
    var DashType    :  String? = ""

    private var tvv_dash        : TextView? = null
    private var tvv_tile        : TextView? = null
    private var tv_ExpenseChart : TextView? = null

    private var tvv_head_CashBalance : TextView? = null
    private var tvv_head_BankBalance : TextView? = null
    private var tvv_head_Expense : TextView? = null

    private var tv_CashBalanceRemark : TextView? = null
    private var tv_BankBalanceRemark : TextView? = null
    private var tv_ExpenseRemark : TextView? = null

    private var actv_mode: AutoCompleteTextView? = null

    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null

    var TabMode    = 0 // 0 = Graph , 1 = Tile
    var ContinueMode    = 0 // 0 = First , 1 = Second
    var ChartMode    = 0 // 0 = First , 1 = Second

    var chartModeCount    = 0
    lateinit var chartTypeViewModel               : ChartTypeViewModel
    lateinit var accCashBalanceViewModel          : AccCashBalanceViewModel
    lateinit var accBankBalanceViewModel          : AccBankBalanceViewModel
    lateinit var accExpenseChartViewModel         : AccExpenseChartViewModel

    lateinit var chartTypeArrayList: JSONArray
    var ID_ChartMode    :  String? = ""

//    Graph

    var cashBalanceCount  = 0
    var bankBalanceCount  = 0
    var expenseChartCount  = 0
    var supplierOutstandingCount  = 0

    private var ll_CashBalance  : LinearLayout? = null
    private var ll_BankBalance  : LinearLayout? = null
    private var ll_ExpenseChart : LinearLayout? = null

    private var recycCashBalance: RecyclerView? = null
    private var recycBankBalance: RecyclerView? = null
    private var recycExpense: RecyclerView? = null

    lateinit var cashBalanceArrayList  : JSONArray
    lateinit var bankBalanceArrayList  : JSONArray
    lateinit var expenseChartArrayList : JSONArray

    //Expense Chart
    private lateinit var ExpenseChart: BarChart
    private var modelDashExpenseChart = ArrayList<ModelDashExpenseChart>()


    //Accounts_Tile
    var accountTileCount  = 0
    lateinit var accountsTileViewModel: AccountsTileViewModel
    lateinit var accountsTileArrayList: JSONArray
    private var recyc_accountsTile: RecyclerView? = null
    var accountsTileAdapter:  AccountsTileAdapter? = null
    private var tv_acc_tileName: TextView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_accounts_graph)
        context = this@AccountsGraphActivity

        chartTypeViewModel                    = ViewModelProvider(this).get(ChartTypeViewModel::class.java)
        accCashBalanceViewModel               = ViewModelProvider(this).get(AccCashBalanceViewModel::class.java)
        accBankBalanceViewModel               = ViewModelProvider(this).get(AccBankBalanceViewModel::class.java)
        accExpenseChartViewModel               = ViewModelProvider(this).get(AccExpenseChartViewModel::class.java)

        accountsTileViewModel = ViewModelProvider(this).get(AccountsTileViewModel::class.java)

        setRegViews()

        SubMode = intent.getStringExtra("SubMode")
        label   = intent.getStringExtra("label")

        Log.e(TAG,"3555   "+SubMode+"  :  "+label)
      //  getCurrentDate()

        TabMode       = 0
        ContinueMode  = 0
        hideViews()

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

        tvv_dash        = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile        = findViewById<TextView>(R.id.tvv_tile)
        tv_ExpenseChart = findViewById<TextView>(R.id.tv_ExpenseChart)

        tvv_head_CashBalance = findViewById<TextView>(R.id.tvv_head_CashBalance)
        tvv_head_BankBalance = findViewById<TextView>(R.id.tvv_head_BankBalance)
        tvv_head_Expense = findViewById<TextView>(R.id.tvv_head_Expense)

        tv_CashBalanceRemark = findViewById<TextView>(R.id.tv_CashBalanceRemark)
        tv_BankBalanceRemark = findViewById<TextView>(R.id.tv_BankBalanceRemark)
        tv_ExpenseRemark = findViewById<TextView>(R.id.tv_ExpenseRemark)

//        tv_StagWiseRemark      = findViewById<TextView>(R.id.tv_StagWiseRemark)
//        tv_ComplaintRemark      = findViewById<TextView>(R.id.tv_ComplaintRemark)
//        tv_ServiceRemark      = findViewById<TextView>(R.id.tv_ServiceRemark)
//
//        tv_ServiceCountOfWPA      = findViewById<TextView>(R.id.tv_ServiceCountOfWPA)
//        tv_ServiceTop10Product      = findViewById<TextView>(R.id.tv_ServiceTop10Product)
//        tv_ServiceSlaStatus      = findViewById<TextView>(R.id.tv_ServiceSlaStatus)
//        tv_ServiceChannelStatus      = findViewById<TextView>(R.id.tv_ServiceChannelStatus)
//
//        tv_crmtile_Outstanding      = findViewById<TextView>(R.id.tv_crmtile_Outstanding)
//        tv_crmtile_Status      = findViewById<TextView>(R.id.tv_crmtile_Status)
//        tv_crmtile_outstandcount      = findViewById<TextView>(R.id.tv_crmtile_outstandcount)
//        tv_crmtile_amcDue      = findViewById<TextView>(R.id.tv_crmtile_amcDue)
//
//
//        tv_crmtile_Outstanding_remark      = findViewById<TextView>(R.id.tv_crmtile_Outstanding_remark)
//        tv_crmtile_Status_remark      = findViewById<TextView>(R.id.tv_crmtile_Status_remark)
//        tv_crmtile_outstandcount_remark      = findViewById<TextView>(R.id.tv_crmtile_outstandcount_remark)
//        tv_crmtile_amcDue_remark      = findViewById<TextView>(R.id.tv_crmtile_amcDue_remark)


        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)

        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)

        ll_CashBalance      = findViewById<LinearLayout>(R.id.ll_CashBalance)
        ll_BankBalance      = findViewById<LinearLayout>(R.id.ll_BankBalance)
        ll_ExpenseChart     = findViewById<LinearLayout>(R.id.ll_ExpenseChart)

        tv_acc_tileName = findViewById<TextView>(R.id.tv_acc_tileName)
        recyc_accountsTile    = findViewById<RecyclerView>(R.id.recyc_accountsTile)

//        ll_ServiceWise      = findViewById<LinearLayout>(R.id.ll_ServiceWise)
//        ll_ServiceCountOfWPA      = findViewById<LinearLayout>(R.id.ll_ServiceCountOfWPA)
//        ll_ServiceTop10Product      = findViewById<LinearLayout>(R.id.ll_ServiceTop10Product)
//        ll_ServiceSlaStatus      = findViewById<LinearLayout>(R.id.ll_ServiceSlaStatus)
//        ll_ServiceChannelStatus      = findViewById<LinearLayout>(R.id.ll_ServiceChannelStatus)
//
//
//        StagWiseChart       = findViewById<BarChart>(R.id.StagWiseChart)
//        ComplaintWiseChart  = findViewById<BarChart>(R.id.ComplaintWiseChart)
//        ServiceWiseChart    = findViewById<BarChart>(R.id.ServiceWiseChart)
//        ServiceCountOfWPAChart    = findViewById<BarChart>(R.id.ServiceCountOfWPAChart)
//        ServiceTop10ProductChart    = findViewById<BarChart>(R.id.ServiceTop10ProductChart)
//        ServiceChannelStatusChart    = findViewById<BarChart>(R.id.ServiceChannelStatusChart)

         ExpenseChart    = findViewById<BarChart>(R.id.ExpenseChart)

        recycCashBalance       = findViewById<RecyclerView>(R.id.recycCashBalance)
        recycBankBalance    = findViewById<RecyclerView>(R.id.recycBankBalance)
        recycExpense    = findViewById<RecyclerView>(R.id.recycExpense)
//        recycComplaintWise    = findViewById<RecyclerView>(R.id.recycComplaintWise)
//        recycServiceCountOfWPA    = findViewById<RecyclerView>(R.id.recycServiceCountOfWPA)
//        recycServiceTop10Product    = findViewById<RecyclerView>(R.id.recycServiceTop10Product)
//        recycServiceSlaStatus    = findViewById<RecyclerView>(R.id.recycServiceSlaStatus)
//        recycServiceChannelStatus    = findViewById<RecyclerView>(R.id.recycServiceChannelStatus)

        // Tile

//        ll_crmtile_outstanding         = findViewById<LinearLayout>(R.id.ll_crmtile_outstanding)
//        ll_crmtile_status    = findViewById<LinearLayout>(R.id.ll_crmtile_status)
//        ll_crmtile_outstandcount    = findViewById<LinearLayout>(R.id.ll_crmtile_outstandcount)
//        ll_crmtile_amcDue    = findViewById<LinearLayout>(R.id.ll_crmtile_amcDue)
//
//        recyc_crmtile_Outstanding    = findViewById<RecyclerView>(R.id.recyc_crmtile_Outstanding)
//        recyc_crmtile_Status    = findViewById<RecyclerView>(R.id.recyc_crmtile_Status)
//        recyc_crmtile_outstandcount    = findViewById<RecyclerView>(R.id.recyc_crmtile_outstandcount)
//        recyc_crmtile_amcDue    = findViewById<RecyclerView>(R.id.recyc_crmtile_amcDue)

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

            accountTileCount=0
            getAccountsTileData()

//            TicketOutstandingCount = 0
//            getTicketOutstandingData()
//
//            TicketStatusCount = 0
//            getTicketStatusData()
//
//            OutStandingCount = 0
//            getOutStandingCountData()
//
//            AmcDueStatusCount = 0
//            getAmcDueStatusData()

        }

    }

    private fun getAccountsTileData() {

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
                accountsTileViewModel.getAccountsTile(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg Accounts tile Value==   " + msg)
                        try {
                            if (msg!!.length > 0) {

                                if (accountTileCount == 0) {
                                    accountTileCount++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt =
                                            jObject.getJSONObject("AccountTileData")
                                        var remark = jobjt.getString("Reamrk")
                                        var tileName = jobjt.getString("ChartName")

                                        tv_acc_tileName!!.text=tileName.toString()

                                        accountsTileArrayList =
                                            jobjt.getJSONArray("AccountTileDataList")
                                        Log.e(
                                            TAG,
                                            "AccountTileDataList 4343  =  " + accountsTileArrayList
                                        )

                                        if (accountsTileArrayList.length() > 0)
                                        {
                                            val lLayout =
                                                GridLayoutManager(this@AccountsGraphActivity, 1)

                                            recyc_accountsTile!!.layoutManager = lLayout
                                            accountsTileAdapter = AccountsTileAdapter(this@AccountsGraphActivity, accountsTileArrayList)
                                            recyc_accountsTile!!.adapter = accountsTileAdapter



                                        }





                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@AccountsGraphActivity,
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

    private fun getChartModeData() {
        var ReqMode = ""
        var SubMode = "5"
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

                                            ll_CashBalance!!.visibility = View.GONE
                                            ll_BankBalance!!.visibility = View.GONE
                                            ll_ExpenseChart!!.visibility = View.GONE

                                            if (ID_ChartMode.equals("30")){
                                               // ll_CashBalance!!.visibility = View.VISIBLE
                                                tvv_head_CashBalance!!.setText(jsonObject.getString("DashBoardName"))
                                                cashBalanceCount   = 0
                                                getAccCashBalance()
                                            }
                                            else if (ID_ChartMode.equals("31")){
                                             //   ll_BankBalance!!.visibility = View.VISIBLE
                                                tvv_head_BankBalance!!.setText(jsonObject.getString("DashBoardName"))
                                                bankBalanceCount = 0
                                                getAccBankBalance()
                                            }
                                            else if (ID_ChartMode.equals("32")){
//                                                ll_BankBalance!!.visibility = View.VISIBLE
                                                expenseChartCount = 0
                                                getAccExpenseChart()
                                            }
                                            else if (ID_ChartMode.equals("33")){
//                                                ll_BankBalance!!.visibility = View.VISIBLE
//                                                bankBalanceCount = 0
//                                                getAccBankBalance()
                                            }

                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AccountsGraphActivity,
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

            val adapter = CustomAdapter(this, R.layout.custom_dropdown_item, modeType)
          //  val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, modeType)
            actv_mode!!.setAdapter(adapter)
            actv_mode!!.showDropDown()

            actv_mode!!.setOnItemClickListener { parent, view, position, id ->
                ID_ChartMode = modeTypeID[position]
                DashMode = modeDashMode[position]
                Log.e(TAG, "0000011122   " + ID_ChartMode)

                ll_CashBalance!!.visibility = View.GONE
                ll_BankBalance!!.visibility = View.GONE
                ll_ExpenseChart!!.visibility = View.GONE

                if (ID_ChartMode.equals("30")){
                    tvv_head_CashBalance!!.setText(modeType[position])
                    cashBalanceCount   = 0
                    getAccCashBalance()
                }
                else if (ID_ChartMode.equals("31")){
                    tvv_head_BankBalance!!.setText(modeType[position])
                    bankBalanceCount = 0
                    getAccBankBalance()
                }

                else if (ID_ChartMode.equals("32")){
                 //   ll_BankBalance!!.visibility = View.VISIBLE
                    expenseChartCount = 0
                    getAccExpenseChart()
                }


//                ll_StagWise!!.visibility = View.GONE
//                ll_ComplaintWise!!.visibility = View.GONE
//                ll_ServiceWise!!.visibility = View.GONE
//                ll_ServiceCountOfWPA!!.visibility = View.GONE
//                ll_ServiceTop10Product!!.visibility = View.GONE
//                ll_ServiceSlaStatus!!.visibility = View.GONE
//                ll_ServiceChannelStatus!!.visibility = View.GONE
//
//
//                if (ID_ChartMode.equals("10")){
//                    ll_StagWise!!.visibility = View.VISIBLE
//                    crmStagewiseCount   = 0
//                    getCRMStagewiseData()
//                }
//                else if (ID_ChartMode.equals("11")){
//                    ll_ComplaintWise!!.visibility = View.VISIBLE
//                    crmcomplaintwiseCount = 0
//                    getCRMcomplaintwiseData()
//                }
//                else if (ID_ChartMode.equals("12")){
//                    ll_ServiceWise!!.visibility = View.VISIBLE
//                    crmservicewiseCount = 0
//                    getCRMservicewiseData()
//                }
//                else if (ID_ChartMode.equals("13")){
//                    ll_ServiceCountOfWPA!!.visibility = View.VISIBLE
//                    crmCountOfWPACount = 0
//                    getCRMCountOfWPAData()
//                }
//                else if (ID_ChartMode.equals("14")){
//                    ll_ServiceTop10Product!!.visibility = View.VISIBLE
//                    crmtop1oproductCount = 0
//                    getCRMTop10Product()
//                }
//                else if (ID_ChartMode.equals("15")){
//                    ll_ServiceSlaStatus!!.visibility = View.VISIBLE
//                    crmslastatusCount = 0
//                    getCRMSlaStatus()
//                }
//
//                else if (ID_ChartMode.equals("16")){
////                  SLA CHANNEL
//                    ll_ServiceChannelStatus!!.visibility = View.VISIBLE
//                    crmchannelstatusCount = 0
//                    getCRMChanelStatus()
//                }

                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }


    // Graph

    private fun getAccCashBalance() {

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
                accCashBalanceViewModel.getAccCashBalance(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (cashBalanceCount == 0) {
                                cashBalanceCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   100055   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("CashBalance")
                                    cashBalanceArrayList=jobjt.getJSONArray("CashBalanceList")
                                   // tv_ServiceChannelStatus!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"39123 cashBalanceArrayList  "+cashBalanceArrayList)
                                    tv_CashBalanceRemark!!.setText(jobjt.getString("Reamrk"))
                                    if (cashBalanceArrayList.length() > 0){
                                        ll_CashBalance!!.visibility = View.VISIBLE
                                        val lLayout = GridLayoutManager(this@AccountsGraphActivity, 1)
                                        recycCashBalance!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = AccountCashBalanceDashAdapter(this@AccountsGraphActivity, cashBalanceArrayList)
                                        recycCashBalance!!.adapter = adapter
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AccountsGraphActivity,
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

    private fun getAccBankBalance() {

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
                accBankBalanceViewModel.getAccBankBalance(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (bankBalanceCount == 0) {
                                bankBalanceCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   516661   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("BankBalance")
                                    bankBalanceArrayList=jobjt.getJSONArray("BankBalanceList")
                                    Log.e(TAG,"msg   516663   "+jobjt.getString("Reamrk"))
                                     tv_BankBalanceRemark!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"516662 bankBalanceArrayList  "+bankBalanceArrayList)

                                    if (bankBalanceArrayList.length() > 0){
                                        ll_BankBalance!!.visibility = View.VISIBLE
                                        val lLayout = GridLayoutManager(this@AccountsGraphActivity, 1)
                                        recycBankBalance!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = AccountBankBalanceDashAdapter(this@AccountsGraphActivity, bankBalanceArrayList)
                                        recycBankBalance!!.adapter = adapter
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AccountsGraphActivity,
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

    private fun getAccExpenseChart() {
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                accExpenseChartViewModel.getAccExpenseChart(this,TransDate!!,ID_ChartMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (expenseChartCount == 0) {
                                expenseChartCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   516663   "+msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    val jobjt = jObject.getJSONObject("ExpenseChart")
                                    expenseChartArrayList=jobjt.getJSONArray("ExpenseChartList")
                                    // tv_ServiceChannelStatus!!.setText(jobjt.getString("Reamrk"))
                                    Log.e(TAG,"516662 expenseChartArrayList  "+expenseChartArrayList)

                                    if (expenseChartArrayList.length() > 0){
                                        ll_ExpenseChart!!.visibility = View.VISIBLE
//                                        setExpenseBarchart()

                                        val lLayout = GridLayoutManager(this@AccountsGraphActivity, 1)
                                        recycExpense!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = AccountExpenseDashAdapter(this@AccountsGraphActivity, expenseChartArrayList)
                                        recycExpense!!.adapter = adapter
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AccountsGraphActivity,
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

    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < modelDashExpenseChart.size) {
                modelDashExpenseChart[index].Branch
            } else {
                ""
            }
        }
    }

    private fun setExpenseBarchart() {
        modelDashExpenseChart.clear()
        modelDashExpenseChart = getDashExpenseChart()  // Change Model data

        ExpenseChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = ExpenseChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        ExpenseChart.axisRight.isEnabled = false
        //remove legend
        ExpenseChart.legend.isEnabled = false
        ExpenseChart!!.setScaleEnabled(true)
        //remove description label
        ExpenseChart.description.isEnabled = false


        //add animation
        ExpenseChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +325f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLACK

        val yAxis: YAxis = ExpenseChart.getAxis(YAxis.AxisDependency.LEFT)
        yAxis.setStartAtZero(false)

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
        for (i in modelDashExpenseChart.indices) {
            val score = modelDashExpenseChart[i]
            val formattedNumber = String.format("%.2f", score.ExpenseAmount.toFloat())
            Log.e(TAG,"840001     "+i.toFloat()+"  :   "+score.ExpenseAmount.toFloat())
            Log.e(TAG,"840002     "+i.toFloat()+"  :   "+formattedNumber.toFloat())
            Log.e(TAG,"840003     "+i.toFloat()+"  :   "+score.ExpenseAmount)

            val value = 8.8578938E8.toFloat()
            Log.e(TAG,"840004     "+i.toFloat()+"  :   "+value)
           // entries.add(BarEntry(i.toFloat(), score.ExpenseAmount.toFloat()))
           // entries.add(BarEntry(i.toFloat(), score.ExpenseAmount.toFloat()))
        }

//        entries.add(BarEntry(1f, -5f))
//        entries.add(BarEntry(2f, 8f))
//        entries.add(BarEntry(3f, -10f))
//        entries.add(BarEntry(4f, 8f))
//        entries.add(BarEntry(5f, -6f))

        entries.add(BarEntry(1f, 85257.85F))
        entries.add(BarEntry(2f, 3.887987719f))
        entries.add(BarEntry(3f, -92970.34f))
        entries.add(BarEntry(4f, 100.0f))
        entries.add(BarEntry(5f, -6f))



        val barDataSet = BarDataSet(entries, "Category")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        ExpenseChart.data = data
        ExpenseChart.setFitBars(true)


        ExpenseChart.invalidate()
    }

    private fun getDashExpenseChart(): ArrayList<ModelDashExpenseChart> {
        for (i in 0 until expenseChartArrayList.length())
        {
            var jsonObject = expenseChartArrayList.getJSONObject(i)
            modelDashExpenseChart.add(ModelDashExpenseChart("",jsonObject.getString("ExpenseAmount")))
        }

        return modelDashExpenseChart
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