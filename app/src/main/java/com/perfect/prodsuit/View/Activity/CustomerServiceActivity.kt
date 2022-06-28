package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ServiceProductAdapter
import com.perfect.prodsuit.View.Adapter.ServiceSalesAdapter
import com.perfect.prodsuit.View.Adapter.ServiceWarrantyAdapter
import com.perfect.prodsuit.Viewmodel.ServiceProductViewModel
import com.perfect.prodsuit.Viewmodel.ServiceSalesViewModel
import com.perfect.prodsuit.Viewmodel.ServiceWarrantyViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CustomerServiceActivity : AppCompatActivity()  , View.OnClickListener {

    val TAG : String = "CustomerServiceActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var til_CustomerNo: TextInputLayout? = null

    var tie_CustomerNo: TextInputEditText? = null

    private var tabLayout : TabLayout? = null
    var llMainDetail: LinearLayout? = null
    var llBtn: LinearLayout? = null

    //Complaints
    var tie_Comp_FromDate: TextInputEditText? = null
    var tie_Comp_ToDate: TextInputEditText? = null
    var tie_Comp_FromTime: TextInputEditText? = null
    var tie_Comp_ToTime: TextInputEditText? = null

    private var fromToDate:Int = 0
    private var fromToTime:Int = 0

    lateinit var serviceWarrantyViewModel: ServiceWarrantyViewModel
    lateinit var serviceWarrantyArrayList : JSONArray

    lateinit var serviceProductViewModel: ServiceProductViewModel
    lateinit var serviceProductArrayList : JSONArray

    lateinit var serviceSalesViewModel: ServiceSalesViewModel
    lateinit var serviceSalesArrayList : JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_customer_service)
        context = this@CustomerServiceActivity

        serviceWarrantyViewModel = ViewModelProvider(this).get(ServiceWarrantyViewModel::class.java)
        serviceProductViewModel = ViewModelProvider(this).get(ServiceProductViewModel::class.java)
        serviceSalesViewModel = ViewModelProvider(this).get(ServiceSalesViewModel::class.java)

        setRegViews()
        addTabItem()

        til_CustomerNo!!.setEndIconOnClickListener {
            finish()
        }
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        til_CustomerNo      = findViewById(R.id.til_CustomerNo)
        tie_CustomerNo      = findViewById(R.id.tie_CustomerNo)



        tabLayout      = findViewById(R.id.tabLayout)
        llMainDetail = findViewById<LinearLayout>(R.id.llMainDetail)
        llBtn = findViewById<LinearLayout>(R.id.llBtn)

        til_CustomerNo!!.setOnClickListener(this)
    }


    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Complaint"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Warranty"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Product"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sales"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        getComplaints()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                  getComplaints()

                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                    getWarranty()

                }
                if (tab.position == 2){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()
                    getProduct()

                }
                if (tab.position == 3){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                    getSales()
                }
                /*  if (tab.position == 4){
                      Log.e(TAG,"onTabSelected  1131  "+tab.position)
                      llMainDetail!!.removeAllViews()
                   //   tv_actionType!!.visibility=View.GONE
                      recyAgendaDetail!!.visibility=View.GONE
                      getQuotationtails()
                  }*/
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })
    }

    private fun getComplaints() {

        llBtn!!.visibility = View.VISIBLE
        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_complaints, null, false)
        llMainDetail!!.addView(inflatedLayout);

        tie_Comp_FromDate= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_FromDate)
        tie_Comp_ToDate= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_ToDate)
        tie_Comp_FromTime= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_FromTime)
        tie_Comp_ToTime= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_ToTime)

        tie_Comp_FromDate!!.setOnClickListener(this)
        tie_Comp_ToDate!!.setOnClickListener(this)
        tie_Comp_FromTime!!.setOnClickListener(this)
        tie_Comp_ToTime!!.setOnClickListener(this)

    }

    private fun getWarranty() {
        llBtn!!.visibility = View.GONE
        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_warranty, null, false)
        llMainDetail!!.addView(inflatedLayout);

        var recyServiceWarranty = inflatedLayout.findViewById<RecyclerView>(R.id.recyServiceWarranty)

        var warranty = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceWarrantyViewModel.getServiceWarranty(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   182   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("WarrantyDetails")
                                serviceWarrantyArrayList = jobjt.getJSONArray("WarrantyDetailsList")
                                if (serviceWarrantyArrayList.length()>0){
                                    if (warranty == 0){
                                        warranty++
                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceWarranty!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceWarrantyAdapter(this@CustomerServiceActivity, serviceWarrantyArrayList)
                                        recyServiceWarranty!!.adapter = adapter
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@CustomerServiceActivity,
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

    private fun getProduct() {
        llBtn!!.visibility = View.GONE
        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_product, null, false)
        llMainDetail!!.addView(inflatedLayout);


        var recyServiceProduct = inflatedLayout.findViewById<RecyclerView>(R.id.recyServiceProduct)

        var product = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceProductViewModel.getServiceProduct(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   262   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ProductHistoryDetails")
                                serviceProductArrayList = jobjt.getJSONArray("ProductHistoryDetailsList")
                                if (serviceProductArrayList.length()>0){
                                    if (product == 0){
                                        product++
                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceProduct!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceProductAdapter(this@CustomerServiceActivity, serviceProductArrayList)
                                        recyServiceProduct!!.adapter = adapter
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@CustomerServiceActivity,
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

    private fun getSales() {
        llBtn!!.visibility = View.GONE
        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_sales, null, false)
        llMainDetail!!.addView(inflatedLayout);

        var recyServiceSales = inflatedLayout.findViewById<RecyclerView>(R.id.recyServiceSales)

        var sales = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceSalesViewModel.getServiceSales(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   335   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("SalesHistoryDetails")
                                serviceSalesArrayList = jobjt.getJSONArray("SalesHistoryDetailsList")
                                if (serviceSalesArrayList.length()>0){
                                    if (sales == 0){
                                        sales++
                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceSales!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceSalesAdapter(this@CustomerServiceActivity, serviceSalesArrayList)
                                        recyServiceSales!!.adapter = adapter
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@CustomerServiceActivity,
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Comp_FromDate->{
                fromToDate = 0
                openBottomSheet()
            }
            R.id.tie_Comp_ToDate->{
                fromToDate = 1
                openBottomSheet()
            }
            R.id.tie_Comp_FromTime->{
                fromToTime = 0
                openBottomSheetTime()
            }
            R.id.tie_Comp_ToTime->{
                fromToTime = 1
                openBottomSheetTime()
            }

        }
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                if (fromToDate == 0){
                    tie_Comp_FromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (fromToDate == 1){
                    tie_Comp_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomSheetTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                if (fromToTime == 0){
                    tie_Comp_FromTime!!.setText(output)
                }
                if (fromToTime == 1){
                    tie_Comp_ToTime!!.setText(output)
                }


//                val strTime = String.format(
//                    "%02d:%02d %s", if (hr == 0) 12 else hr,
//                    min, if (hr < 12) "AM" else "PM"
//                )
//
//                ettime!!.setText(strTime)


            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


}