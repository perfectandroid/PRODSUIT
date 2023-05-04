package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.MoreServiceAttendedAdapter
import com.perfect.prodsuit.View.Adapter.ReplacedProductAdapter
import com.perfect.prodsuit.View.Adapter.ReplacedProductSubAdapter
import com.perfect.prodsuit.View.Adapter.ServiceAttendedAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ServiceFollowUpNewActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListenerValue {

    var TAG = "ServiceFollowUpNewActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var card_start: CardView? = null
    private var card_stop: CardView? = null
    private var card_restart: CardView? = null

    private var tv_ServiceCost: TextView? = null
    private var tv_replaced_product_cost: TextView? = null
    private var tv_attendance: TextView? = null

    private var lin_service_cost: LinearLayout? = null
    private var lin_service_view: LinearLayout? = null
    private var lin_replacePoductCost: LinearLayout? = null
    private var lin_attendance: LinearLayout? = null

    private var lin_add_service: LinearLayout? = null

    var serviceAttendedMode: String? = "1"
    var replacedProductMode: String? = "1"
    var attendanceMode: String? = "1"


    var journeyType: Int = 0
    private val START_LOCATION = 100
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1

    var runningStatus: String? = ""
    var customer_service_register: String = ""

    private var ID_Branch = "";
    private var ID_Employee: String = ""

    lateinit var serviceFollowUpMappedServiceViewModel: ServiceFollowUpMappedServiceViewModel
    lateinit var serviceFollowUpMoreServiceViewModel: ServiceFollowUpMoreServiceViewModel
    lateinit var serviceFollowUpServiceTypeViewModel: ServiceFollowUpServiceTypeViewModel

    var serviceFollowUpServiceType = 0
    var jsonArrayServiceType: JSONArray = JSONArray()

    var serviceFollowUpMappedService = 0
    var jsonArrayMappedServiceAttended: JSONArray = JSONArray()
    var jsonArrayServiceAttended: JSONArray = JSONArray()
    private var recycler_service_cost: RecyclerView? = null


    var serviceFollowUpMoreService = 0
    var jsonArrayMoreServiceAttended: JSONArray = JSONArray()
    var sortMoreServiceAttended: JSONArray = JSONArray()
    var selectMoreServiceAttended: JSONArray = JSONArray()
    private var dialogMoreServiceAttendeSheet : Dialog? = null
    private var recyFollowupAttended: RecyclerView? = null

    val modelMoreServices = ArrayList<ModelMoreServices>()
    val modelMoreServicesTemp = ArrayList<ModelMoreServicesTemp>()

    var adapterServiceAttended: ServiceAttendedAdapter? = null

    val modelServiceAttended = ArrayList<ModelServiceAttended>()
    val modelServiceAttendedTemp = ArrayList<ModelServiceAttendedTemp>()

    ////////////

    lateinit var serviceFollowUpMappedReplacedProductViewModel: ServiceFollowUpMappedReplacedProductViewModel
    var serviceFollowUpReplacedProduct = 0
    var jsonArrayReplacedProductMap: JSONArray = JSONArray()

    val modelReplacedProduct = ArrayList<ModelReplacedProduct>()
    private var recycleView_replaceproduct: RecyclerView? = null
    var adapterReplacedProduct: ReplacedProductAdapter? = null

    lateinit var serviceFollowUpChangeModeViewModel: ServiceFollowUpChangeModeViewModel
    var serviceFollowUpChangeMmode = 0
    var jsonArrayChangeMode: JSONArray = JSONArray()

    lateinit var serviceFollowUpMoreReplacedProductsViewModel: ServiceFollowUpMoreReplacedProductsViewModel
    var serviceFollowUpMoreReplacedProduct = 0
    var jsonArrayReplacedProductMore: JSONArray = JSONArray()
    private var dialogRepPoduct: Dialog? = null

    var replaceProductPos = 0

    private var tv_submit: TextView? = null
    private var tv_cancel: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_follow_up_new)
        context = this@ServiceFollowUpNewActivity
        serviceFollowUpMappedServiceViewModel = ViewModelProvider(this).get(ServiceFollowUpMappedServiceViewModel::class.java)
        serviceFollowUpMoreServiceViewModel = ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        serviceFollowUpServiceTypeViewModel = ViewModelProvider(this).get(ServiceFollowUpServiceTypeViewModel::class.java)
        serviceFollowUpMappedReplacedProductViewModel = ViewModelProvider(this).get(ServiceFollowUpMappedReplacedProductViewModel::class.java)
        serviceFollowUpChangeModeViewModel = ViewModelProvider(this).get(ServiceFollowUpChangeModeViewModel::class.java)
        serviceFollowUpMoreReplacedProductsViewModel = ViewModelProvider(this).get(ServiceFollowUpMoreReplacedProductsViewModel::class.java)

        setRegViews()
        getSharedPrefValues()
        runningStatus = intent.getStringExtra("runningStatus")
        customer_service_register = intent.getStringExtra("customer_service_register").toString()

        setRunningStatus()

    }

    private fun getSharedPrefValues() {
        val FK_BranchCodeUserSP = this.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        card_start = findViewById<CardView>(R.id.card_start)
        card_stop = findViewById<CardView>(R.id.card_stop)
        card_restart = findViewById<CardView>(R.id.card_restart)

        tv_ServiceCost = findViewById<TextView>(R.id.tv_ServiceCost)
        tv_replaced_product_cost = findViewById<TextView>(R.id.tv_replaced_product_cost)
        tv_attendance = findViewById<TextView>(R.id.tv_attendance)
        tv_submit = findViewById<TextView>(R.id.tv_submit)
        tv_cancel = findViewById<TextView>(R.id.tv_cancel)

        lin_service_cost = findViewById<LinearLayout>(R.id.lin_service_cost)
        lin_service_view = findViewById<LinearLayout>(R.id.lin_service_view)
        lin_replacePoductCost = findViewById<LinearLayout>(R.id.lin_replacePoductCost)
        lin_attendance = findViewById<LinearLayout>(R.id.lin_attendance)

        lin_add_service = findViewById<LinearLayout>(R.id.lin_add_service)

        recycler_service_cost = findViewById<RecyclerView>(R.id.recycler_service_cost)
        recycleView_replaceproduct = findViewById<RecyclerView>(R.id.recycleView_replaceproduct)
        recycler_service_cost!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent?): Boolean {
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                return true
            }
        })
        recycleView_replaceproduct!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent?): Boolean {
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                return true
            }
        })


        card_start!!.setOnClickListener(this)
        card_stop!!.setOnClickListener(this)
        card_restart!!.setOnClickListener(this)

        tv_ServiceCost!!.setOnClickListener(this)
        tv_replaced_product_cost!!.setOnClickListener(this)
        tv_attendance!!.setOnClickListener(this)

        tv_submit!!.setOnClickListener(this)
        tv_cancel!!.setOnClickListener(this)

        lin_add_service!!.setOnClickListener(this)

    }

    private fun setRunningStatus() {
        if (runningStatus.equals("0")) {
            card_start?.visibility = View.VISIBLE
            card_stop?.visibility = View.GONE
            card_restart?.visibility = View.GONE
        } else if (runningStatus.equals("1")) {
            card_start?.visibility = View.GONE
            card_stop?.visibility = View.VISIBLE
            card_restart?.visibility = View.GONE
        } else {
            card_start?.visibility = View.GONE
            card_stop?.visibility = View.GONE
            card_restart?.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.card_start -> {
                journeyType = 0
                if (checkLocationPermission()) {
                    confirmBottomSheet(0)
                }
            }
            R.id.card_stop -> {
                journeyType = 1
                if (checkLocationPermission()) {
                    confirmBottomSheet(1)
                }
            }
            R.id.card_restart -> {
                journeyType = 2
                if (checkLocationPermission()) {
                    confirmBottomSheet(2)
                }
            }

            R.id.lin_add_service -> {
                Config.disableClick(v)
                if (sortMoreServiceAttended.length() > 0){
                    Log.e(TAG,"20261  ")
                    loadMoreServiceAttendedPopup(sortMoreServiceAttended)
                }
                else{
                    serviceFollowUpMoreService = 0
                    loadMoreServiceAttended()
                }

            }

            R.id.tv_ServiceCost -> {

                Config.disableClick(v)

                if (jsonArrayServiceType.length() == 0){
                    serviceFollowUpServiceType = 0
                    loadServiceType()
                }


                if (serviceAttendedMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                }
                else {
                    serviceAttendedMode = "0"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                }

                hideShowViews()
                if (jsonArrayMappedServiceAttended.length() == 0){
                    serviceFollowUpMappedService = 0
                    loadMappedeServiceAttended()
                }

            }

            R.id.tv_replaced_product_cost -> {

                if (replacedProductMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "0"
                    attendanceMode = "1"
                }
                hideShowViews()
                if (jsonArrayChangeMode.length() == 0){
                    serviceFollowUpChangeMmode = 0
                    loadChangeMode()
                }


                if (jsonArrayReplacedProductMap.length() == 0){
                    serviceFollowUpReplacedProduct = 0
                    loadMappedReplacedProducts()
                }
            }

            R.id.tv_attendance -> {

                if (attendanceMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "0"
                }
                hideShowViews()
            }

            R.id.tv_submit -> {
                Config.disableClick(v)
//                for (i in 0 until modelServiceAttended.size) {
//                    val ItemsAttend = modelServiceAttended[i]
//                    Log.e(TAG,"297   "+i
//                            +"\n"+"isChecked          : "+ItemsAttend.isChecked
//                            +"\n"+"SubProduct         : "+ItemsAttend.SubProduct
//                            +"\n"+"Service            : "+ItemsAttend.Service
//                            +"\n"+"ServiceCost        : "+ItemsAttend.ServiceCost
//                            +"\n"+"ServiceTypeId      : "+ItemsAttend.ServiceTypeId
//                            +"\n"+"ServiceTypeName    : "+ItemsAttend.ServiceTypeName
//                            +"\n"+"ServiceTaxAmount   : "+ItemsAttend.ServiceTaxAmount
//                            +"\n"+"ServiceNetAmount   : "+ItemsAttend.ServiceNetAmount)
//                }

                for (i in 0 until modelReplacedProduct.size) {
                    val ItemsAttend = modelReplacedProduct[i]
                    Log.e(TAG,"297   "+i
                            +"\n"+"isChecked       : "+ItemsAttend.isChecked
                            +"\n"+"ID_OLD_Product  : "+ItemsAttend.ID_OLD_Product
                            +"\n"+"OLD_Product     : "+ItemsAttend.OLD_Product
                            +"\n"+"SPDOldQuantity  : "+ItemsAttend.SPDOldQuantity
                            +"\n"+"Amount          : "+ItemsAttend.Amount
                            +"\n"+"ID_Mode         : "+ItemsAttend.ID_Mode
                            +"\n"+"ModeName        : "+ItemsAttend.ModeName
                            +"\n"+"ID_Product      : "+ItemsAttend.ID_Product
                            +"\n"+"Product         : "+ItemsAttend.Product
                            +"\n"+"Replaced_Qty    : "+ItemsAttend.Replaced_Qty
                            +"\n"+"ReplaceAmount   : "+ItemsAttend.ReplaceAmount
                            +"\n"+"Remarks         : "+ItemsAttend.Remarks
                            +"\n"+"isAdded         : "+ItemsAttend.isAdded)
                }


            }
        }
    }

    private fun hideShowViews() {
        lin_service_cost!!.visibility = View.GONE
        lin_replacePoductCost!!.visibility = View.GONE
        lin_attendance!!.visibility = View.GONE

        Log.e(TAG,"288    "+serviceAttendedMode+"  :  "+replacedProductMode+"  :  "+attendanceMode)

        if (serviceAttendedMode.equals("0")){
            lin_service_cost!!.visibility = View.VISIBLE
        }

        if (replacedProductMode.equals("0")){
            lin_replacePoductCost!!.visibility = View.VISIBLE
        }

        if (attendanceMode.equals("0")){
            lin_attendance!!.visibility = View.VISIBLE
        }

    }


    private fun checkLocationPermission(): Boolean {


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            return false
        } else {
            return true
        }


    }

    private fun fetchLocation() {
        startActivityForResult(
            Intent(
                this@ServiceFollowUpNewActivity,
                LocationActivity2::class.java
            ), START_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (journeyType == 0) {
                confirmBottomSheet(0)
            } else if (journeyType == 1) {
                confirmBottomSheet(1)
            } else {
                confirmBottomSheet(2)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == START_LOCATION) {
            if (resultCode == RESULT_OK) {
                val bundle = data!!.extras ?: return
                val latitude = bundle.getDouble("LATITUDE")
                val longitude = bundle.getDouble("LONGITUDE")
                val address = bundle.getString("ADDRESS")
                Log.v("dfsdfds34343f", "lat " + latitude)
                Log.v("dfsdfds34343f", "longitude " + longitude)
                Log.v("dfsdfds34343f", "address " + address)
                startStopWork(latitude, longitude, address)
            }
        }
    }


    private fun confirmBottomSheet(type: Int) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.confirm_popup, null)

        val btnNo = view.findViewById<TextView>(R.id.btnNo)
        val btnYes = view.findViewById<TextView>(R.id.btnYes)
        val tv_text = view.findViewById<TextView>(R.id.tv_text)

        if (type == 0) {
            tv_text.setText("do you really want to start your day?")
        } else if (type == 1) {
            tv_text.setText("do you really want to stop your day?")
        } else {
            tv_text.setText("do you really want to restart your day?")
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            dialog.dismiss()

            if (type == 0) {
                fetchLocation()
            } else if (type == 1) {
                fetchLocation()
            } else {
                fetchLocation()
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun startStopWork(latitude: Double, longitude: Double, address: String?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        val current = LocalDateTime.now().format(formatter)
        Log.v("dfsdfds34343f", "current " + current)

    }

    private fun loadMappedeServiceAttended() {
        //recyclerAttendance!!.adapter = null
        try {
//            serviceFollowUpMappedServiceViewModel =
//                ViewModelProvider(this).get(ServiceFollowUpMappedServiceViewModel::class.java)
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    serviceFollowUpMappedServiceViewModel.getServiceFollowUpMappedService(
                        this, customer_service_register, ID_Branch, ID_Employee)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            try {

                                val msg = serviceSetterGetter.message
                                if (msg!!.length > 0) {
                                   // Log.v("sdadasd3rffdfd", "msg")
                                if (serviceFollowUpMappedService == 0) {
                                    serviceFollowUpMappedService++
                                  //  Log.v("sdadasd3rffdfd", "det")
                                    val jObject = JSONObject(msg)
                                   // Log.v("sdadasd3rffdfd", "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAttendedDetails")
                                        jsonArrayMappedServiceAttended = jobjt.getJSONArray("ServiceAttendedDetailsList")
                                        Log.e(TAG, "jsonArrayMappedServiceAttended=  36811 " + jsonArrayMappedServiceAttended.toString())

                                        jsonArrayServiceAttended = JSONArray()
                                        modelServiceAttendedTemp.clear()
                                        Log.e(TAG,"jsonArrayMappedServiceAttended  368111 "+jsonArrayMappedServiceAttended.length())
                                        for (i in 0 until jsonArrayMappedServiceAttended.length()) {

                                            Log.e(TAG, "position  3681112 " + i)
                                            var jsonObject = jsonArrayMappedServiceAttended.getJSONObject(i)
                                            val jObject = JSONObject()

//                                            jObject.put("ID_ProductWiseServiceDetails", jsonObject.getString("ID_ProductWiseServiceDetails"))
//                                            jObject.put("SubProduct", jsonObject.getString("SubProduct"))
//                                            jObject.put("ID_Product", jsonObject.getString("ID_Product"))
//                                            jObject.put("ID_Services", jsonObject.getString("ID_Services"))
//                                            jObject.put("Service", jsonObject.getString("Service"))
//                                            jObject.put("ServiceCost", jsonObject.getString("ServiceCost"))
//                                            jObject.put("ServiceTaxAmount", jsonObject.getString("ServiceTaxAmount"))
//                                            jObject.put("ServiceNetAmount", jsonObject.getString("ServiceNetAmount"))
//                                            jObject.put("Remarks", jsonObject.getString("Remarks"))
//                                            jObject.put("isChecked","0")
//                                            jObject.put("isDelete", "0")
//                                            Log.e(TAG, "jObject  3681112 " + jObject)
                                           // jsonArrayServiceAttended.put(jObject)


                                            modelServiceAttended!!.add(ModelServiceAttended(jsonObject.getString("ID_ProductWiseServiceDetails"),jsonObject.getString("SubProduct")
                                                ,jsonObject.getString("ID_Product"),jsonObject.getString("ID_Services"),jsonObject.getString("Service"),jsonObject.getString("ServiceCost"),
                                                jsonObject.getString("ServiceTaxAmount"),jsonObject.getString("ServiceNetAmount"),jsonObject.getString("Remarks"),"0","0","1","",""))

//                                            modelServiceAttendedTemp!!.add(
//                                                ModelServiceAttendedTemp(jsonObject.getString("ID_ProductWiseServiceDetails"),jsonObject.getString("SubProduct")
//                                                ,jsonObject.getString("ID_Product"),jsonObject.getString("ID_Services"),jsonObject.getString("Service"),jsonObject.getString("ServiceCost"),
//                                                jsonObject.getString("ServiceTaxAmount"),jsonObject.getString("ServiceNetAmount"),jsonObject.getString("Remarks"),"0","0")
//                                            )
                                        }

//                                        Log.e(TAG, "jsonArrayServiceAttended= 36812  " + jsonArrayServiceAttended)
//                                        Log.e(TAG, "jsonArrayServiceAttended= 36812  " + jsonArrayServiceAttended.length())

                                        try {
                                            if (modelServiceAttended.size>0){
                                                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                                               // recycler_service_cost!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                //            recyCustomer!!.setHasFixedSize(true)
                                                //            val adapter = CountryDetailAdapter(this@LeadGenerationActivity, countryArrayList)
                                                recycler_service_cost!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                                adapterServiceAttended = ServiceAttendedAdapter(this@ServiceFollowUpNewActivity, modelServiceAttended,jsonArrayServiceType)
                                                recycler_service_cost!!.adapter = adapterServiceAttended
                                               // adapterServiceAttended.setClickListener(this@ServiceFollowUpNewActivity)

                                            }

                                        }
                                        catch (e : Exception){
                                            Log.e(TAG,"Exception   418   "+e.toString())
                                        }


                                    } else {
//
                                    }

                                }


                                } else {
                                }
                            } catch (e: Exception) {
                                Log.v("sdadasd3rffdfd", "ex3 " + e)
//                                Toast.makeText(
//                                    applicationContext,
//                                    "" + Config.SOME_TECHNICAL_ISSUES,
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }

                        })
                    progressDialog!!.dismiss()
                }
                false -> {
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }catch (e :Exception){
            Log.e(TAG,"Exception  385    "+e.toString())
        }

    }

    private fun loadServiceType() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpServiceTypeViewModel =
            ViewModelProvider(this).get(ServiceFollowUpServiceTypeViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(this, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                serviceFollowUpServiceTypeViewModel.getServiceFollowUpServiceType(
                    this,
                    customer_service_register,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpServiceType == 0) {
                                    serviceFollowUpServiceType++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg  580   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ServiceType")
                                        jsonArrayServiceType =
                                            jobjt.getJSONArray("ServiceTypeList")
                                    }
                                }
                            } else {

                            }
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    })
              //  progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun loadMoreServiceAttended() {
        //recyclerAttendance!!.adapter = null
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpMoreServiceViewModel.getServiceFollowUpMoreService(
                    this, customer_service_register, ID_Branch, ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.e(TAG,"msg   505   "+msg)
                                if (serviceFollowUpMoreService == 0) {
                                    serviceFollowUpMoreService++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("AddedService")
                                        jsonArrayMoreServiceAttended = jobjt.getJSONArray("AddedServiceList")
                                        if (jsonArrayMoreServiceAttended.length()>0){

                                            Log.e(TAG,"20262  ")
                                            Log.e(TAG,"2026  s")
                                            sortMoreServiceAttended = JSONArray()
                                            selectMoreServiceAttended = JSONArray()
                                            // Log.e(TAG,"jsonArrayMappedServiceAttended  368111 "+jsonArrayMappedServiceAttended.length())
                                            modelMoreServices.clear()
                                            modelMoreServicesTemp!!.clear()
                                            for (i in 0 until jsonArrayMoreServiceAttended.length()) {

                                                Log.e(TAG, "position  3681112 " + i)
                                                var jsonObject = jsonArrayMoreServiceAttended.getJSONObject(i)
                                                val jObject = JSONObject()

                                                jObject.put("ID_Services", jsonObject.getString("ID_Services"))
                                                jObject.put("Service", jsonObject.getString("Service"))
                                                jObject.put("isChecked","0")
                                                jObject.put("isCheckedTemp","0")
                                                Log.e(TAG, "jObject  3681112 " + jObject)
                                                sortMoreServiceAttended.put(jObject)

                                                modelMoreServices!!.add(ModelMoreServices(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),"0"))
                                                modelMoreServicesTemp!!.add(ModelMoreServicesTemp(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),"0"))

                                            }

                                            loadMoreServiceAttendedPopup(sortMoreServiceAttended)
                                        }


                                    }
                                }
                            } else {

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


    private fun loadMoreServiceAttendedPopup(sortMoreServiceAttended : JSONArray) {

        try {

            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!! .setContentView(R.layout.cs_more_service_attended_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended = dialogMoreServiceAttendeSheet!! .findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_submit) as TextView

         //   lnrHead_warranty_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_warranty_main) as LinearLayout




            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)



            Log.e(TAG,"20263  ")
            Log.e(TAG,"20268  s")
            selectMoreServiceAttended = JSONArray()
            selectMoreServiceAttended = sortMoreServiceAttended
            Log.e(TAG,"20264   sortMoreServiceAttended   "+sortMoreServiceAttended.length())
            Log.e(TAG,"20265   selectMoreServiceAttended   "+selectMoreServiceAttended.length())

            modelMoreServicesTemp.clear()
            for (i in 0 until modelMoreServices.size) {
                val ItemsModelTemp = modelMoreServices[i]
                modelMoreServicesTemp!!.add(ModelMoreServicesTemp(ItemsModelTemp.ID_Services,ItemsModelTemp.Service,ItemsModelTemp.isChecked))
            }

            modelServiceAttendedTemp.clear()
            for (i in 0 until modelServiceAttended.size) {
                val ItemsServiceTemp = modelServiceAttended[i]
                modelServiceAttendedTemp!!.add(ModelServiceAttendedTemp(ItemsServiceTemp.ID_ProductWiseServiceDetails,ItemsServiceTemp.SubProduct
                        ,ItemsServiceTemp.ID_Product,ItemsServiceTemp.ID_Services,ItemsServiceTemp.Service,ItemsServiceTemp.ServiceCost,
                    ItemsServiceTemp.ServiceTaxAmount,ItemsServiceTemp.ServiceNetAmount,ItemsServiceTemp.Remarks,ItemsServiceTemp.isChecked,
                    ItemsServiceTemp.isDelete,ItemsServiceTemp.isCheckedAdd,ItemsServiceTemp.ServiceTypeId,ItemsServiceTemp.ServiceTypeName))
            }

            Log.e(TAG,"590   sortMoreServiceAttended   "+sortMoreServiceAttended)
            if (sortMoreServiceAttended.length()>0){
                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                recyFollowupAttended!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                val adapter1 = MoreServiceAttendedAdapter(this@ServiceFollowUpNewActivity,modelMoreServices,modelMoreServicesTemp,modelServiceAttendedTemp)
                recyFollowupAttended!!.adapter = adapter1
                adapter1.setClickListener(this@ServiceFollowUpNewActivity)

            }

            tv_cancel!!.setOnClickListener {
                selectMoreServiceAttended = JSONArray()
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
                Log.e(TAG,"20269  s")
                Log.e(TAG,"610  selectMoreServiceAttended   "+selectMoreServiceAttended)

                addDataServiceAttended()
            }

//            recyServiceWarranty!!.setOnTouchListener(object : View.OnTouchListener {
//                override fun onTouch(v: View, event: MotionEvent?): Boolean {
//                    v.parent.requestDisallowInterceptTouchEvent(true)
//                    v.onTouchEvent(event)
//                    return true
//                }
//            })



            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadMappedReplacedProducts() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                serviceFollowUpMappedReplacedProductViewModel.getServiceFollowUpMappedService(
                    this,
                    customer_service_register,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpReplacedProduct == 0) {
                                    serviceFollowUpReplacedProduct++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ReplaceProductdetails")
                                        jsonArrayReplacedProductMap = jobjt.getJSONArray("ReplaceProductdetailsList")

                                        Log.e(TAG,"jsonArrayReplacedProductMap   837   "+jsonArrayReplacedProductMap)
                                        modelReplacedProduct.clear()
                                        if (jsonArrayReplacedProductMap.length()>0){

                                            for (i in 0 until jsonArrayReplacedProductMap.length()) {
                                                var jsonObject = jsonArrayReplacedProductMap.getJSONObject(i)

                                                modelReplacedProduct!!.add(ModelReplacedProduct("0",jsonObject.getString("ID_OLD_Product"),jsonObject.getString("OLD_Product"),
                                                    jsonObject.getString("SPDOldQuantity"),jsonObject.getString("Amount"),"0","",jsonObject.getString("ID_Product"),jsonObject.getString("Product"),
                                                    "0",jsonObject.getString("ReplaceAmount"),jsonObject.getString("Remarks"),"0"))
                                            }

                                        }
                                        Log.e(TAG,"modelReplacedProduct   8371   "+ modelReplacedProduct.size)
                                        try {
                                            if (modelReplacedProduct.size>0){
                                                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                                                recycleView_replaceproduct!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                                adapterReplacedProduct = ReplacedProductAdapter(this@ServiceFollowUpNewActivity, modelReplacedProduct,jsonArrayChangeMode)
                                                recycleView_replaceproduct!!.adapter = adapterReplacedProduct
                                                adapterReplacedProduct!!.setClickListener(this@ServiceFollowUpNewActivity)

                                            }

                                        }
                                        catch (e : Exception){
                                            Log.e(TAG,"Exception   418   "+e.toString())
                                        }

                                    }
                                }
                            } else {
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadChangeMode() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpChangeModeViewModel.getServiceFollowUpChangeMode(
                    this,
                    customer_service_register,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpChangeMmode == 0) {
                                    serviceFollowUpChangeMmode++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ChangemodeDetails")
                                        jsonArrayChangeMode =
                                            jobjt.getJSONArray("ChangemodeDetailsList")
                                    }
                                }
                            } else {

                            }
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
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

    private fun addDataServiceAttended() {
        // modelServiceAttended
        modelMoreServices.clear()
        for (i in 0 until modelMoreServicesTemp.size) {
            val ItemsModel = modelMoreServicesTemp[i]
            Log.e(TAG,"isChecked  76401  "+ItemsModel.isChecked)
            modelMoreServices!!.add(ModelMoreServices(ItemsModel.ID_Services,ItemsModel.Service,ItemsModel.isChecked))
        }

        // 02-05-2023
        modelServiceAttended.clear()

        for (i in 0 until modelServiceAttendedTemp.size) {
            val ItemsAttend3 = modelServiceAttendedTemp[i]
            Log.e(TAG,"77222  : "+ItemsAttend3.ID_Services+"  :  "+ItemsAttend3.Service+"  :  "+ItemsAttend3.isChecked)
            if (ItemsAttend3.isCheckedAdd.equals("1")){
                modelServiceAttended!!.add(ModelServiceAttended(ItemsAttend3.ID_ProductWiseServiceDetails,ItemsAttend3.SubProduct
                    ,ItemsAttend3.ID_Product,ItemsAttend3.ID_Services,ItemsAttend3.Service,ItemsAttend3.ServiceCost,
                    ItemsAttend3.ServiceTaxAmount,ItemsAttend3.ServiceNetAmount,ItemsAttend3.Remarks,ItemsAttend3.isChecked,
                    ItemsAttend3.isDelete,ItemsAttend3.isCheckedAdd,ItemsAttend3.ServiceTypeId,ItemsAttend3.ServiceTypeName))
            }


        }
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation)
        recycler_service_cost!!.layoutAnimation = controller
        val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
        recycler_service_cost!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        adapterServiceAttended = ServiceAttendedAdapter(this@ServiceFollowUpNewActivity, modelServiceAttended,jsonArrayServiceType)
        recycler_service_cost!!.scheduleLayoutAnimation()
        recycler_service_cost!!.adapter = adapterServiceAttended
        adapterServiceAttended!!.notifyDataSetChanged()


//        adapterServiceAttended!!.notifyDataSetChanged()
    }

    override fun onClick(position: Int, data: String, value: String) {

        if (data.equals("MoreServiceAttended")) {
            try {

                Log.e(TAG,"202610  s")
                val jObject12 = JSONObject()
                val jsonObject12 = selectMoreServiceAttended.getJSONObject(position)
                jObject12.put("ID_Services", jsonObject12.getString("ID_Services"))
                jObject12.put("Service", jsonObject12.getString("Service"))
                jObject12.put("isChecked",value)
                jObject12.put("isCheckedTemp","0")
                jObject12.put("select","1")

                selectMoreServiceAttended.put(position,jObject12)


//                val jsonObject = sortMoreServiceAttended.getJSONObject(position)
//                var moreId = jsonObject.getString("ID_Services")
//                if (selectMoreServiceAttended.length()>0){
//                    var hasId = hasID_Services(selectMoreServiceAttended,"ID_Services",moreId!!)
//                    Log.e(TAG,"636123     "+hasId)
//                    if (hasId == true){
//                        val jObject = JSONObject()
//                        jObject.put("ID_Services", jsonObject.getString("ID_Services"))
//                        jObject.put("Service", jsonObject.getString("Service"))
//                        jObject.put("isChecked",value)
//                        selectMoreServiceAttended.put(jObject)
//                    }else{
//                        Log.e(TAG,"Lsst  6369  :  "+selectMoreServiceAttended)
//                        for (i in 0 until selectMoreServiceAttended.length()) {
//                            var jsonObject1 = selectMoreServiceAttended.getJSONObject(i)
//                            var moreIdSelect = jsonObject1.getString("ID_Services")
//                            if (moreIdSelect.equals(moreId)){
//                                val jObject = JSONObject()
//                                jObject.put("ID_Services", jsonObject.getString("ID_Services"))
//                                jObject.put("Service", jsonObject.getString("Service"))
//                                jObject.put("isChecked",value)
//                                selectMoreServiceAttended.put(i,jObject)
//                            }
//                        }
//
//                    }
//
//                }else{
//                    val jObject = JSONObject()
//                    jObject.put("ID_Services", jsonObject.getString("ID_Services"))
//                    jObject.put("Service", jsonObject.getString("Service"))
//                    jObject.put("isChecked",value)
//                    selectMoreServiceAttended.put(jObject)
//                }


             //   Log.e(TAG,"Lsst  63691  :  "+jsonObject.getString("ID_Services"))
                Log.e(TAG,"202611  s")
                Log.e(TAG,"Lsst  6971  :  "+selectMoreServiceAttended)
                Log.e(TAG,"Lsst  6972  :  "+sortMoreServiceAttended)

            }
            catch (e:Exception){

            }
        }

        if (data.equals("MoreServiceClick")) {
            try {
                val ItemsModel = modelMoreServicesTemp[position]
                Log.e(TAG," 759  "+ItemsModel.Service+"  :  "+ItemsModel.isChecked)

//                var hasId = containsName(modelServiceAttendedTemp,ItemsModel.Service)



                var isTrue = false
                var pos = 0
                for (i in 0 until modelServiceAttendedTemp.size) {
                    var ItemsAttend = modelServiceAttendedTemp[i]
                    if (ItemsAttend.ID_Services.equals(ItemsModel.ID_Services)){
                        isTrue = true
                        pos = i
                    }
                }

                Log.e(TAG,"7722  :  "+isTrue+"  :  "+pos)
                if (isTrue){
                    var ItemsAttend1 = modelServiceAttendedTemp[pos]
                    ItemsAttend1.isCheckedAdd =ItemsModel.isChecked
                }else{
                    if (ItemsModel.isChecked.equals("1")){
//                        val ItemsModel3 = ModelServiceAttendedTemp("",""
//                            ,"",ItemsModel.ID_Services,ItemsModel.Service,"", "","","","1","1")
//                        modelServiceAttendedTemp!!.add(ItemsModel3)

                        modelServiceAttendedTemp!!.add(ModelServiceAttendedTemp("",""
                            ,"",ItemsModel.ID_Services,ItemsModel.Service,"",
                            "","","","0","1",ItemsModel.isChecked,"",""))

                    }
                }

                Log.e(TAG,"77221  :  "+modelServiceAttendedTemp.size)


                for (i in 0 until modelServiceAttendedTemp.size) {
                    var ItemsAttend3 = modelServiceAttendedTemp[i]
                    Log.e(TAG,"77222  : "+ItemsAttend3.ID_Services+"  :  "+ItemsAttend3.Service+"  :  "+ItemsAttend3.isChecked)



                }


                Log.e(TAG,"7591   ?:  "+modelServiceAttendedTemp.size)

            }catch (e:Exception){

            }

        }

        if (data.equals("ReplaceProdClick")) {
            try {
                Log.e(TAG,"1132   s"+position)

            }catch (e:Exception){

            }

        }

        if (data.equals("popUpProducts")) {
            try {

                replaceProductPos = position
                Log.e(TAG,"1142   s"+position)
                if (jsonArrayReplacedProductMore.length() == 0){
                    serviceFollowUpMoreReplacedProduct = 0
                    loadMoreReplacedProducts()
                }else{
                    replaceProductPop(jsonArrayReplacedProductMore)
                }


            }catch (e:Exception){

            }

        }

        if (data.equals("popUpProductsSelect")) {
            try {

                dialogRepPoduct!!.dismiss()
                Log.e(TAG,"11421   s"+position)
                val ItemsModel = modelReplacedProduct[replaceProductPos]
                val jsonObject = jsonArrayReplacedProductMore.getJSONObject(position)
                Log.e(TAG,"114221   :  "+ItemsModel.Product)

                Log.e(TAG,"11421   s"+ItemsModel.OLD_Product)

                ItemsModel.ID_Product = jsonObject!!.getString("ID_Product")
                ItemsModel.Product =  jsonObject!!.getString("Name")
//
//                for (k in 0 until modelReplacedProduct.size) {
//                    val ItemsModel1 = modelReplacedProduct[k]
//
//                    Log.e(TAG,"114222   :  "+ItemsModel1.Product)
//                }

                adapterReplacedProduct!!.notifyItemChanged(replaceProductPos)


            }catch (e:Exception){

            }

        }


    }



    private fun loadMoreReplacedProducts() {
        //recyclerAttendance!!.adapter = null

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpMoreReplacedProductsViewModel.getServiceFollowUpMoreReplacedProduct(
                    this,
                    customer_service_register,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpMoreReplacedProduct == 0) {
                                    serviceFollowUpMoreReplacedProduct++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("PopUpProductdetails")
                                        jsonArrayReplacedProductMore = jobjt.getJSONArray("PopUpProductdetailsList")
                                        Log.e(TAG,"1190   "+jsonArrayReplacedProductMore)

                                        replaceProductPop(jsonArrayReplacedProductMore)
                                    }
                                }
                            } else {

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

    private fun replaceProductPop(jsonArrayReplacedProductMore: JSONArray) {

        try {

            dialogRepPoduct = Dialog(context)
            dialogRepPoduct!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogRepPoduct!!.setContentView(R.layout.pop_replaced_product_sub)
            dialogRepPoduct!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycCountry = dialogRepPoduct!!.findViewById(R.id.recycCountry) as RecyclerView
            val etsearch = dialogRepPoduct!!.findViewById(R.id.etsearch) as EditText

//            countrySort = JSONArray()
//            for (k in 0 until modelReplacedProduct.length()) {
//                val jsonObject = countryArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                countrySort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(context, 1)
            recycCountry!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = CountryDetailAdapter(this@LeadGenerationActivity, countryArrayList)
            val adapter = ReplacedProductSubAdapter(context, jsonArrayReplacedProductMore)
            recycCountry!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)




            dialogRepPoduct!!.show()
            dialogRepPoduct!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogRepPoduct!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}