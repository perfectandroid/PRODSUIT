package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
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
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.MonthlySaleBar
import com.perfect.prodsuit.Model.ScoreBar
import com.perfect.prodsuit.Model.StockListBar
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AreaListRepository.progressDialog
import com.perfect.prodsuit.View.Adapter.LeadTileOutstandListAdapter
import com.perfect.prodsuit.View.Adapter.MonthlyBarChartAdapter
import com.perfect.prodsuit.View.Adapter.PieChartAdapter
import com.perfect.prodsuit.Viewmodel.InventoryMonthlySaleViewModel
import com.perfect.prodsuit.Viewmodel.LeadTileViewModel
import com.perfect.prodsuit.Viewmodel.StockListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class InventoryGraphActivity : AppCompatActivity() {
    var TAG  ="InventoryGraphActivity"
    lateinit var context: Context
    lateinit var inventoryViewModel: InventoryMonthlySaleViewModel
    lateinit var stockListViewModel: StockListViewModel



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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_inventory_graph)
        context = this@InventoryGraphActivity
        inventoryViewModel = ViewModelProvider(this).get(InventoryMonthlySaleViewModel::class.java)
        stockListViewModel = ViewModelProvider(this).get(StockListViewModel::class.java)
        setRegViews()
     //   getInventorySale()
        getStckListCategory()


    }


    private fun setRegViews() {
        monthlyBarChart = findViewById<BarChart>(R.id.MonthlybarChart)
        stockListBarChart = findViewById<BarChart>(R.id.stockListBarChart)
        recycColorMonthly = findViewById<RecyclerView>(R.id.recyColors)
        recyColorsStockList = findViewById<RecyclerView>(R.id.recyColorsStockList)
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
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG, "success and inside   ")
                                        val jobjt = jObject.getJSONObject("InventoryMonthlySaleGraph")
                                        inventoryMonthlySaleArrayList=jobjt.getJSONArray("InventoryMonthlySaleGraphList")
                                    //    Log.e(TAG, "ArrayList==   "+inventoryMonthlySaleArrayList)

                                        if (inventoryMonthlySaleArrayList.length() > 0) {


                                            inventorySaleSortArrayList = JSONArray()
                                            for (k in 0 until inventoryMonthlySaleArrayList.length()) {
                                                val jsonObject =
                                                    inventoryMonthlySaleArrayList.getJSONObject(k)

                                                inventorySaleSortArrayList.put(jsonObject)
                                            }
                                        }
                                        Log.e(TAG, "inventorySaleSortArrayList==   "+inventorySaleSortArrayList)


                                       setMonthlyBarchart()    //setBarChart



                                        val lLayout = GridLayoutManager(this@InventoryGraphActivity, 2)

                                        recycColorMonthly!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapter = MonthlyBarChartAdapter(
                                            this@InventoryGraphActivity,
                                            inventorySaleSortArrayList
                                        )
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
                                                val jsonObject =
                                                    stockListArrayList.getJSONObject(k)

                                                stockListSortArrayList.put(jsonObject)
                                            }

                                            setStockListBarchart()



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



}