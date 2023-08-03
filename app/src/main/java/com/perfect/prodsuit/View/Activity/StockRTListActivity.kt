package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.StockRTListAdapter
import com.perfect.prodsuit.Viewmodel.StockRTListViewModel
import org.json.JSONArray
import org.json.JSONObject

class StockRTListActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG : String = "StockRTListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var imback : ImageView? = null
    var imgClosing : ImageView? = null
    var tv_header : TextView? = null
    var etsearch : EditText? = null

    lateinit var stockRTListViewModel: StockRTListViewModel
    lateinit var stockRTArrayList: JSONArray
    lateinit var stockRTSort: JSONArray
    private var recycStockRT: RecyclerView? = null
    var stockCount = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_stock_rtlist)
        context = this@StockRTListActivity

        stockRTListViewModel = ViewModelProvider(this).get(StockRTListViewModel::class.java)

        setRegViews()
        getDetail()

        stockCount = 0
        getStckList()


    }



    private fun setRegViews() {

        imback = findViewById(R.id.imback)
        imgClosing = findViewById(R.id.imgClosing)
        tv_header = findViewById(R.id.tv_header)
        etsearch = findViewById(R.id.etsearch)

        recycStockRT = findViewById(R.id.recycStockRT)

        imback!!.setOnClickListener(this)
        imgClosing!!.setOnClickListener(this)

    }

    private fun getDetail() {
        try {
            val intent = intent
            var headerTitle = intent!!.getStringExtra("headerTitle")
            tv_header!!.setText(headerTitle)

        }catch (e: Exception){

        }

    }

    private fun getStckList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockRTListViewModel.getStockRTList(this, "0","0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (stockCount == 0) {
                                    stockCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("StockRTDetails")
                                        stockRTArrayList = jobjt.getJSONArray("StockRTList")
                                        if (stockRTArrayList.length() > 0) {
                                            Log.e(TAG, "msg   999102   " + stockRTArrayList)
                                            stockRTList(stockRTArrayList)
//                                            val lLayout = GridLayoutManager(this@StockRTListActivity)
//                                            recycStockRT!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ProductSimilarAdapter(this@StockRTListActivity, prodDetailArrayList,modelg,jresult,strName!!,strMRP!!,strSalPrice!!,strCurQty!!)
//                                            recycStockRT!!.adapter = adapter
//
//                                            //    adapter.setClickListener(this@ProductEnquiryDetailActivity)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockRTListActivity,
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

    private fun stockRTList(stockRTArrayList: JSONArray) {
        try {
            stockRTSort = JSONArray()
            for (k in 0 until stockRTArrayList.length()) {
                val jsonObject = stockRTArrayList.getJSONObject(k)
                stockRTSort.put(jsonObject)
            }

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stockRTSort = JSONArray()

                    for (k in 0 until stockRTArrayList.length()) {
                        val jsonObject = stockRTArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmployeesFrom").length) {
                            if (jsonObject.getString("EmployeesFrom")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) || jsonObject.getString("DepartmentFrom")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) ) {
                                stockRTSort.put(jsonObject)
                            }

                        }
                    }

                    val adapter = StockRTListAdapter(this@StockRTListActivity, stockRTSort)
                    recycStockRT!!.adapter = adapter
                    adapter.setClickListener(this@StockRTListActivity)
                }
            })

            val lLayout = GridLayoutManager(this@StockRTListActivity, 1)
            recycStockRT!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = StockRTListAdapter(this@StockRTListActivity, stockRTSort)
            recycStockRT!!.adapter = adapter
            adapter.setClickListener(this@StockRTListActivity)

        }catch (e: Exception){

        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgClosing->{
                finish()
            }

        }
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("stockrtclicks")) {
            val intent = Intent()
            //  intent.putExtra("strLongitue", "123")
            setResult(Config.CODE_STOCK_LIST!!, intent)
            finish()
        }

    }

    override fun onBackPressed() {
       // super.onBackPressed()
    }

}