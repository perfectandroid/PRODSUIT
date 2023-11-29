package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AreaListRepository.progressDialog
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class InventoryGraphActivity : AppCompatActivity(), View.OnClickListener {
    var TAG  ="InventoryGraphActivity"
    lateinit var context: Context
    lateinit var inventoryViewModel: InventoryMonthlySaleViewModel
    lateinit var productreorderLevelViewModel: ProductReorderLevelViewModel
    lateinit var stockListViewModel: StockListViewModel
    var ProductReorderLevelCount = 0



    var inventorysale = 0
    lateinit var inventoryMonthlySaleArrayList: JSONArray
    lateinit var inventorySaleSortArrayList: JSONArray


    //Monthly Sale BarChart
    private lateinit var monthlyBarChart: BarChart
    private var saleGraphListBar = ArrayList<MonthlySaleBar>()
    var recycColorMonthly: RecyclerView? = null

    //stock list category
    private lateinit var stockListBarChart: BarChart
    var recyColorsStockList: RecyclerView? = null
    var stockList = 0
    lateinit var stockListArrayList: JSONArray
    lateinit var stockListSortArrayList: JSONArray
    private var stockListGraphBar = ArrayList<StockListBar>()

    private var tvv_prl_slnmbr        : TextView?          = null
    private var tvv_prl_product       : TextView?          = null
    private var tvv_prl_reorderlevel  : TextView?          = null
    private var tvv_prl_currentstock  : TextView?          = null
    private var recyclr_colom         : RecyclerView?      = null
    lateinit var InventoryProductReorderLeveArray: JSONArray

    //Supplier Wisepurchase
    private lateinit var supplierwisepurchaseBarChart: BarChart
    var recysupplierwisepurchase      : RecyclerView?     = null
    lateinit var SupplierWisePurchaseArray: JSONArray
    lateinit var SupplierWisePurchasesortArraylist: JSONArray
    lateinit var supplierwisepurchaseViewModel: SupplierWisePurchaseViewModel
    private var supplierwisePurchaseBar = ArrayList<SupplierwisePurchaseBar>()
    var supplierwisePurchaseCount = 0
    var currentDate = ""
    var DashMode = ""
    var DashType = ""

    //sales comparison
    lateinit var salesComparisonViewModel: SalesComparisonViewModel
    var comparisonList = 0
    lateinit var salesComparisonArrayList: JSONArray
    lateinit var salesComparisonSortArrayList: JSONArray
    private var comparisonListGraphBar = ArrayList<ComparisonListBar>()
    private lateinit var comparisonListBarChart: BarChart
    var recy_comparisonColor: RecyclerView? = null

    //Tail

    var stockValueDataCount = 0
    lateinit var stockValueArrayList: JSONArray
    lateinit var stockValueDataViewModel: StockValueDataViewModel
    var tileValue: String? = "0"
    var tvv_tileName: TextView? = null
    var tvv_count: TextView? = null
    var tvv_countString: String? = null

    var TabMode         = 0 // 0 = Graph , 1 = Tile
    var ContinueMode    = 0 // 0 = First , 1 = Second
    var ChartMode       = 0 // 0 = First , 1 = Second
    private var actv_mode: AutoCompleteTextView? = null

    private var ll_monthlygraph               : LinearLayout? = null
    private var ll_stock                      : LinearLayout? = null
    private var ll_comparison                 : LinearLayout? = null
    private var ll_SupplierWisePurchase       : LinearLayout? = null
    private var ll_product_reorder            : LinearLayout? = null

    private var tvv_dash: TextView? = null
    private var tvv_tile: TextView? = null

    private var ll_Graph: LinearLayout? = null
    private var ll_Tile: LinearLayout? = null

    var chartModeCount    = 0
    var ID_ChartMode    :  String? = ""
    lateinit var chartTypeArrayList: JSONArray
    lateinit var crmservicewiseViewModel          : CRMservicewiseViewModel
    lateinit var crmStagewiseDetailsViewModel     : CRMStagewiseDetailsViewModel
    lateinit var crmTileDashBoardDetailsViewModel : CRMTileDashBoardDetailsViewModel
    lateinit var crmcomplaintwiseViewModel        : CRMcomplaintwiseViewModel
    lateinit var chartTypeViewModel               : ChartTypeViewModel


    var recyc_tile      : RecyclerView?     = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_inventory_graph)
        context = this@InventoryGraphActivity
        productreorderLevelViewModel  = ViewModelProvider(this).get(ProductReorderLevelViewModel::class.java)
        supplierwisepurchaseViewModel = ViewModelProvider(this).get(SupplierWisePurchaseViewModel::class.java)
        inventoryViewModel = ViewModelProvider(this).get(InventoryMonthlySaleViewModel::class.java)
        stockListViewModel = ViewModelProvider(this).get(StockListViewModel::class.java)
        salesComparisonViewModel = ViewModelProvider(this).get(SalesComparisonViewModel::class.java)
        stockValueDataViewModel = ViewModelProvider(this).get(StockValueDataViewModel::class.java)
        chartTypeViewModel = ViewModelProvider(this).get(ChartTypeViewModel::class.java)


        setRegViews()
        TabMode       = 0
        ContinueMode  = 0
        hideViews()

        inventorysale = 0
        getInventorySale()
//        getStckListCategory()
//        getProductReorderLevel()
//        getStockValueData()
//        getSalesComparisonList()
//        supplierwisePurchaseCount = 0
//        getSupplierWisePurchase()



    }

    private fun setRegViews() {
        monthlyBarChart             = findViewById<BarChart>(R.id.MonthlybarChart)
        stockListBarChart           = findViewById<BarChart>(R.id.stockListBarChart)
        supplierwisepurchaseBarChart= findViewById<BarChart>(R.id.supplierwisepurchaseBarChart)
        recysupplierwisepurchase    = findViewById<RecyclerView>(R.id.recysupplierwisepurchase)
        recycColorMonthly           = findViewById<RecyclerView>(R.id.recyColors)
        recyColorsStockList         = findViewById<RecyclerView>(R.id.recyColorsStockList)
        recyclr_colom               = findViewById(R.id.recyclr_colom)

        actv_mode= findViewById<AutoCompleteTextView>(R.id.actv_mode)

        ll_monthlygraph             = findViewById(R.id.ll_monthlygraph)
        ll_stock                    = findViewById(R.id.ll_stock)
        ll_comparison               = findViewById(R.id.ll_comparison)
        ll_SupplierWisePurchase     = findViewById(R.id.ll_SupplierWisePurchase)
        ll_product_reorder          = findViewById(R.id.ll_product_reorder)
        tvv_count                   = findViewById(R.id.tvv_count)
        tvv_tileName                = findViewById(R.id.tvv_tileName)

        tvv_dash = findViewById<TextView>(R.id.tvv_dash)
        tvv_tile = findViewById<TextView>(R.id.tvv_tile)

        ll_Graph            = findViewById<LinearLayout>(R.id.ll_Graph)
        ll_Tile             = findViewById<LinearLayout>(R.id.ll_Tile)
        comparisonListBarChart = findViewById<BarChart>(R.id.comparisonListBarChart)
        recy_comparisonColor = findViewById<RecyclerView>(R.id.recy_comparisonColor)
        val imback = findViewById<ImageView>(R.id.imback)


        imback!!.setOnClickListener(this)
        tvv_dash!!.setOnClickListener(this)
        tvv_tile!!.setOnClickListener(this)
        actv_mode!!.setOnClickListener(this)
//        tvv_prl_slnmbr              = findViewById(R.id.tvv_prl_slnmbr)
//        tvv_prl_product             = findViewById(R.id.tvv_prl_product)
//        tvv_prl_reorderlevel        = findViewById(R.id.tvv_prl_reorderlevel)
//        tvv_prl_currentstock        = findViewById(R.id.tvv_prl_currentstock)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        currentDate = sdf.format(Date())
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
                                if (jObject.getString("StatusCode") == "-2") {

                                    val jobjt = jObject.getJSONObject("checkDetails")
                                    chartTypeArrayList = jobjt.getJSONArray("checkDetailsList")
                                    if (chartTypeArrayList.length() > 0){
                                        if (ChartMode == 0){
                                            val jsonObject = chartTypeArrayList.getJSONObject(0)
                                            ID_ChartMode = jsonObject.getString("ID_Mode")
                                            actv_mode!!.setText(jsonObject.getString("Mode_Name"))
                                            Log.e(TAG,"ID_ChartMode  253331   "+ID_ChartMode)
//                                            Log.e(TAG,"ID_ChartMode  253331   "+actv_mode)

                                            ll_monthlygraph!!.visibility = View.VISIBLE
                                            inventorysale   = 0
                                            getInventorySale()

                                        }else{
                                            showChartDrop(chartTypeArrayList)
                                        }
                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@InventoryGraphActivity,
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

                ll_monthlygraph!!.visibility         = View.GONE
                ll_stock!!.visibility                = View.GONE
                ll_comparison!!.visibility           = View.GONE
                ll_SupplierWisePurchase!!.visibility = View.GONE
                ll_product_reorder!!.visibility      = View.GONE


                if (ID_ChartMode.equals("1")){
                    ll_monthlygraph!!.visibility = View.VISIBLE
                    inventorysale   = 0
                    getInventorySale()
                }
                else if (ID_ChartMode.equals("2")){
                    ll_stock!!.visibility = View.VISIBLE
                    stockList = 0
                    getStckListCategory()
                }
                else if (ID_ChartMode.equals("3")){
                    ll_comparison!!.visibility = View.VISIBLE
                    comparisonList = 0
                    getSalesComparisonList()
                }else if (ID_ChartMode.equals("4")){
                    ll_SupplierWisePurchase!!.visibility = View.VISIBLE
                    supplierwisePurchaseCount = 0
                    getSupplierWisePurchase()
                }else if (ID_ChartMode.equals("5")){
                    ll_product_reorder!!.visibility = View.VISIBLE
                    ProductReorderLevelCount = 0
                    getProductReorderLevel()
                }
                Log.e(TAG,"ID_ChartMode  253332   "+ID_ChartMode)
            }

        }
    }
    private fun getInventorySale() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                inventoryViewModel.getInventoryMothlySale(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message

                        try {
                            if (msg!!.length > 0) {

                                if (inventorysale == 0){
                                    inventorysale++
                                    val jObject = JSONObject(msg)
                                //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "-2") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("InventoryMonthlySaleGraph")
                                        inventoryMonthlySaleArrayList=jobjt.getJSONArray("InventoryMonthlySaleGraphList")
                                    //    Log.e(TAG, "ArrayList==   "+inventoryMonthlySaleArrayList)

                                        if (inventoryMonthlySaleArrayList.length() > 0) {


                                            inventorySaleSortArrayList = JSONArray()
                                            for (k in 0 until inventoryMonthlySaleArrayList.length()) {
                                                val jsonObject = inventoryMonthlySaleArrayList.getJSONObject(k)
                                                inventorySaleSortArrayList.put(jsonObject)
                                            }
                                        }
                                        Log.e(TAG, "inventorySaleSortArrayList==   "+inventorySaleSortArrayList)


                                       setMonthlyBarchart()    //setBarChart

                                        val lLayout = GridLayoutManager(this@InventoryGraphActivity, 2)
                                        recycColorMonthly!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = MonthlyBarChartAdapter(this@InventoryGraphActivity, inventorySaleSortArrayList)
                                        recycColorMonthly!!.adapter = adapter


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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

    @SuppressLint("SuspiciousIndentation")
    private fun getStckListCategory() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockListViewModel.getStockListCategory(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                Log.e(TAG, "msg stock==   "+msg)
                        try {
                            if (msg!!.length > 0) {

                                if (stockList == 0){
                                    stockList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("InventoryStockListCategory")
                                        stockListArrayList=jobjt.getJSONArray("InventoryStockListCategoryDetails")
                                            Log.e(TAG, "stockListArrayList==   "+stockListArrayList)


                                        try {


                                        if (stockListArrayList.length() > 0) {

                                            stockListSortArrayList = JSONArray()
                                            for (k in 0 until stockListArrayList.length()) {
                                                val jsonObject = stockListArrayList.getJSONObject(k)
                                                stockListSortArrayList.put(jsonObject)
                                            }

                                            setStockListBarchart()

                                            val lLayout = GridLayoutManager(this@InventoryGraphActivity, 2)
                                            recyColorsStockList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = StockValueBarChartAdapter(this@InventoryGraphActivity, stockListSortArrayList)
                                            recyColorsStockList!!.adapter = adapter

                                        }
                                        Log.e(TAG, "stockListSortArrayList==   "+stockListSortArrayList)
                                        }
                                        catch (e:Exception)
                                        {
                                            Log.e("exceptionStock344",""+e.toString())
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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

    private fun getSupplierWisePurchase() {
        DashMode = "28"
        DashType = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                supplierwisepurchaseViewModel.getSupplierWisePurchase(this,currentDate!!,DashMode!!,DashType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (supplierwisePurchaseCount == 0) {
                                    supplierwisePurchaseCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("InventorySupplierWisePurchase")
                                        SupplierWisePurchaseArray = jobjt.getJSONArray("InventorySupplierWisePurchaseList")
                                        Log.e(TAG, "SupplierWisePurchaseArray   "+SupplierWisePurchaseArray)


                                        try {
                                            if (SupplierWisePurchaseArray.length() > 0) {

                                                SupplierWisePurchasesortArraylist = JSONArray()
                                                for (k in 0 until SupplierWisePurchaseArray.length()) {
                                                    val jsonObject = SupplierWisePurchaseArray.getJSONObject(k)

                                                    SupplierWisePurchasesortArraylist.put(jsonObject)
                                                }

                                                setSupplierWisePurchaseBarchart()

                                                val lLayout = GridLayoutManager(this@InventoryGraphActivity, 2)
                                                recysupplierwisepurchase!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = SupplierWisePurchaseAdapter(this@InventoryGraphActivity, SupplierWisePurchasesortArraylist)
                                                recysupplierwisepurchase!!.adapter = adapter

                                            }
                                            Log.e(TAG, "SupplierWisePurchaseArray==   "+SupplierWisePurchasesortArraylist)
                                        }
                                        catch (e:Exception)
                                        {
                                            Log.e("exceptionStock344",""+e.toString())
                                        }




                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
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

    private fun getProductReorderLevel() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productreorderLevelViewModel.getproductreorderLevel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (ProductReorderLevelCount == 0) {
                                    ProductReorderLevelCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("InventoryProductReorderLevel")
                                        InventoryProductReorderLeveArray = jobjt.getJSONArray("InventoryProductReorderLevelList")
                                        Log.e(TAG, "InventoryProductReorderLeveArray   "+InventoryProductReorderLeveArray)


                                        val lLayout = GridLayoutManager(this@InventoryGraphActivity, 1)
                                        recyclr_colom!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ProductReorderLevelAdapter(this@InventoryGraphActivity, InventoryProductReorderLeveArray)
                                        recyclr_colom!!.adapter = adapter


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
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

    private fun getStockValueData() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockValueDataViewModel.getStockValueData(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock Value==   "+msg)
                        try {
                            if (msg!!.length > 0) {

                                if (stockValueDataCount == 0){
                                    stockValueDataCount++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("StockValueData")
//                                        tileValue=jobjt.getString("StockValue")

                                        Log.e(TAG, "353353 tileValue==   "+tileValue)
                                        Log.e(TAG, "353353 tileValue==   "+jobjt.getString("StockValue"))

                                        tvv_count!!.text=  jobjt.getString("StockValue")


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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

    private fun getSalesComparisonList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                salesComparisonViewModel.getSalesComparison(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        Log.e(TAG, "msg stock==   "+msg)
                        try {
                            if (msg!!.length > 0) {

                                if (comparisonList == 0){
                                    comparisonList++
                                    val jObject = JSONObject(msg)
                                    //    Log.e(TAG, "msg   InventoryGraph   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("SalesComparison")
                                        salesComparisonArrayList=jobjt.getJSONArray("SalesComparisonData")
                                        //  Log.e(TAG, "salesComparisonArrayList==   "+salesComparisonArrayList)


                                        try {


                                            if (salesComparisonArrayList.length() > 0) {


                                                salesComparisonSortArrayList = JSONArray()
                                                for (k in 0 until salesComparisonArrayList.length()) {
                                                    val jsonObject =
                                                        salesComparisonArrayList.getJSONObject(k)

                                                    salesComparisonSortArrayList.put(jsonObject)
                                                }
                                                Log.e(TAG, "salesComparisonSortArrayList==   "+salesComparisonSortArrayList)
                                                setComparisonBarchart() // set comparison barchart here

                                                val lLayout = GridLayoutManager(this@InventoryGraphActivity, 2)
                                                recy_comparisonColor!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = SalesComparisonBarChartAdapter(this@InventoryGraphActivity, salesComparisonSortArrayList)
                                                recy_comparisonColor!!.adapter = adapter

                                            }

                                        }
                                        catch (e:Exception)
                                        {
                                            Log.e("exceptionStock344",""+e.toString())
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@InventoryGraphActivity,
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


    private fun setComparisonBarchart() {
        comparisonListGraphBar.clear()
        comparisonListGraphBar = getSalesComparisonBarList()


        Log.e(TAG, "comparisonListGraphBar44==   "+comparisonListGraphBar)

        comparisonListBarChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = comparisonListBarChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)



        //remove right y-axis
        comparisonListBarChart.axisRight.isEnabled = false
        //remove legend
        comparisonListBarChart.legend.isEnabled = false
        comparisonListBarChart!!.setScaleEnabled(true)
        //remove description label
        comparisonListBarChart.description.isEnabled = false


        //add animation
        comparisonListBarChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar3()

        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 10f
        xAxis.textColor = Color.BLACK


        val yAxis: YAxis = comparisonListBarChart.getAxisLeft()

        yAxis.valueFormatter = MyAxisFormatterBar4()



        //  yAxis.textColor = Color.RED

        //    yAxis.setGranularityEnabled(true)
        //   yAxis.setGranularity(0.1f)
        //  yAxis.granularity = 0.1f
        //  val left: YAxis = chart.getAxisLeft()
        //  yAxis.valueFormatter = MyAxisFormatterBar4()

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.barchart_colors1))
        colors.add(resources.getColor(R.color.barchart_colors2))


        /////////////////////////////////////

        Log.e(TAG, "entriesList5555==   "+comparisonListGraphBar)



        val entries: ArrayList<BarEntry> = ArrayList()
//        entries.add(BarEntry(1f,(127479630.21f)/1000))
//        entries.add(BarEntry(2f,(127479630.21f)/1000))
        // entries.add(BarEntry(2f,1.12747963f))

        for (i in comparisonListGraphBar.indices) {
            val score = comparisonListGraphBar[i]

            // Log.e(TAG, "entriesList5555== score   "+score)
            entries.add(BarEntry(i.toFloat(),score.Value.toFloat()/1000))
        }


        Log.e(TAG, "entriesList5555==   "+entries)


        val barDataSet = BarDataSet(entries, "Label")
        Log.e(TAG, "entriesList5555pppp==   "+barDataSet)
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        //   barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)

        comparisonListBarChart.data = data



        comparisonListBarChart.invalidate()
    }


    private fun setStockListBarchart() {
        stockListGraphBar.clear()
        stockListGraphBar = getStockBarList()
        stockListBarChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = stockListBarChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        stockListBarChart.axisRight.isEnabled = false
        //remove legend
        stockListBarChart.legend.isEnabled = false
        stockListBarChart!!.setScaleEnabled(true)
        //remove description label
        stockListBarChart.description.isEnabled = false


        //add animation
        stockListBarChart.animateY(1000)

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
        for (i in stockListGraphBar.indices) {
            val score = stockListGraphBar[i]
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
        stockListBarChart.data = data


        stockListBarChart.invalidate()

    }

    private fun getStockBarList(): ArrayList<StockListBar> {
        for (i in 0 until stockListSortArrayList.length())
        {
            var jsonObject = stockListSortArrayList.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            stockListGraphBar.add(StockListBar("",jsonObject.getString("Count").toFloat().toInt()))
        }

        return stockListGraphBar

    }

    private fun getSalesComparisonBarList(): ArrayList<ComparisonListBar> {
        for (i in 0 until salesComparisonSortArrayList.length())
        {
            var jsonObject = salesComparisonSortArrayList.getJSONObject(i)



            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            comparisonListGraphBar.add(ComparisonListBar("",jsonObject.getString("Value").toFloat()))
        }

        return comparisonListGraphBar

    }


    private fun setMonthlyBarchart() {
        saleGraphListBar.clear()
        saleGraphListBar = getSaleBarList()
    //   Log.e(TAG, "saleGraphListBar==   "+saleGraphListBar)


        monthlyBarChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = monthlyBarChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        monthlyBarChart.axisRight.isEnabled = false
        //remove legend
        monthlyBarChart.legend.isEnabled = false
        monthlyBarChart!!.setScaleEnabled(true)
        //remove description label
        monthlyBarChart.description.isEnabled = false


        //add animation
        monthlyBarChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE

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

        /////////////////////////////////////

        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in saleGraphListBar.indices) {
            val score = saleGraphListBar[i]
            entries.add(BarEntry(i.toFloat(), score.BarAmount.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Month")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        monthlyBarChart.data = data


        monthlyBarChart.invalidate()

    }

    private fun getSaleBarList(): ArrayList<MonthlySaleBar> {
        for (i in 0 until inventorySaleSortArrayList.length())
        {
            var jsonObject = inventorySaleSortArrayList.getJSONObject(i)

          //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            saleGraphListBar.add(MonthlySaleBar("",jsonObject.getString("Amount").toFloat().toInt()))
        }

        return saleGraphListBar

    }

    private fun setSupplierWisePurchaseBarchart() {
        supplierwisePurchaseBar.clear()
        supplierwisePurchaseBar = getSupplierwiseList()
        //   Log.e(TAG, "saleGraphListBar==   "+saleGraphListBar)


        supplierwisepurchaseBarChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = supplierwisepurchaseBarChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        supplierwisepurchaseBarChart.axisRight.isEnabled = false
        //remove legend
        supplierwisepurchaseBarChart.legend.isEnabled = false
        supplierwisepurchaseBarChart!!.setScaleEnabled(true)
        //remove description label
        supplierwisepurchaseBarChart.description.isEnabled = false


        //add animation
        supplierwisepurchaseBarChart.animateY(1000)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatterBar3()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLUE

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
        for (i in supplierwisePurchaseBar.indices) {
            val score = supplierwisePurchaseBar[i]
            entries.add(BarEntry(i.toFloat(), score.Amount.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "SupplierName")
        // barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.setColors(colors)
        //barDataSet.setValueFormatter(DecimalRemover())
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        val data = BarData(barDataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)
        data.setDrawValues(false)
        supplierwisepurchaseBarChart.data = data


        supplierwisepurchaseBarChart.invalidate()

    }

    private fun getSupplierwiseList(): ArrayList<SupplierwisePurchaseBar> {
        for (i in 0 until SupplierWisePurchasesortArraylist.length())
        {
            var jsonObject = SupplierWisePurchasesortArraylist.getJSONObject(i)

            //  saleGraphListBar.add(MonthlySaleBar(jsonObject.getString("Month").toString(),jsonObject.getString("Amount").toInt()))
            supplierwisePurchaseBar.add(SupplierwisePurchaseBar("",jsonObject.getString("Amount").toFloat().toInt()))
        }

        return supplierwisePurchaseBar

    }

    inner class MyAxisFormatterBar : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < saleGraphListBar.size) {
                saleGraphListBar[index].BarMonth
            } else {
                ""
            }
        }
    }

    inner class MyAxisFormatterBar2 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < stockListGraphBar.size) {
                stockListGraphBar[index].CatName
            } else {
                ""
            }
        }
    }
    inner class MyAxisFormatterBar3 : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < supplierwisePurchaseBar.size) {
                supplierwisePurchaseBar[index].SuppName
            } else {
                ""
            }
        }
    }


    inner class MyAxisFormatterBar4 : IndexAxisValueFormatter() {
        private var mSuffix = arrayOf(
            "", "k", "m", "b", "t"
        )
        private var mMaxLength = 5
        private val mFormat: DecimalFormat = DecimalFormat("###E00")
        private var mText = ""
        init {
            //            mFormat = DecimalFormat("#,###")
        }
        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
//            val index = value.toInt()
//            Log.d("TAG", "getAxisLabel: index $index")
//            return if (index < comparisonListGraphBar.size) {
//                comparisonListGraphBar[index].Label
//            } else {
//                ""
//            }

            //..........

//            if (value > 0) {
//                val df = DecimalFormat("#,###");
//                df.roundingMode = RoundingMode.UNNECESSARY
//                return df.format(value);
//            } else {
//                return "";
//            }
//
            //................
            Log.e(TAG, "combined4545  value="+value)
            if (value > 0) {

                return makePretty(value.toDouble()) + mText
            } else {
                return "";
            }

        }

        private fun makePretty(number: Double): String {
            var r = mFormat.format(number)
            Log.e(TAG, "combined4545 r="+r)
            val numericValue1 = Character.getNumericValue(r[r.length - 1])
            val numericValue2 = Character.getNumericValue(r[r.length - 2])
            val combined = Integer.valueOf(numericValue2.toString() + "" + numericValue1)
            Log.e(TAG, "combined4545="+combined)
            r = r.replace("E[0-9][0-9]".toRegex(), mSuffix[combined / 3])
            while (r.length > mMaxLength || r.matches("[0-9]+\\.[a-z]".toRegex())) {
                r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
            }
            return r
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
                getStockValueData()
            }


        }else if (TabMode == 1){
            ContinueMode = 1
            ll_Tile!!.visibility = View.VISIBLE
            tvv_dash!!.setBackgroundResource(R.drawable.btn_shape_reset)
            tvv_tile!!.setBackgroundResource(R.drawable.btn_dash)

        }

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imback -> {
                finish()
            }
            R.id.actv_mode -> {
                ChartMode = 1
                chartModeCount = 0
                getChartModeData()
            }
            R.id.tvv_dash->{
                TabMode = 0
                hideViews()
            }
            R.id.tvv_tile->{
                TabMode = 1
                hideViews()
            }
        }
    }

}