package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.internal.it
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.Model.ServiceFollowUpListModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProductAdapter
import com.perfect.prodsuit.View.Adapter.ServiceFollowUpListAdapter
import com.perfect.prodsuit.Viewmodel.ProductDetailViewModel
import com.perfect.prodsuit.Viewmodel.ServiceFollowUpInfoViewModel
import com.perfect.prodsuit.Viewmodel.ServiceFollowUpListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ServiceFollowUpListActivity : AppCompatActivity(), ItemClickListenerData,
    View.OnClickListener,OnMapReadyCallback {

    var TAG = "ServiceFollowUpListActivity"
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var imageView: ImageView
    private lateinit var img_search: ImageView
    private lateinit var imback: ImageView
    private lateinit var lin_map: LinearLayout
    private lateinit var tv_address: TextView
    private lateinit var tv_cancel: LinearLayout
    private lateinit var tv_nodata: TextView
    var journeyType: Int = 0
    lateinit var context: Context
    private lateinit var currentLocation: Location
    private lateinit var cons_location: ConstraintLayout
    private var ID_Branch = "";
    private var ID_Employee: String = ""
    var latitude = ""
    var city = ""
    var longitude = ""
    var type = 0
    var dialogCustSearch: Dialog? = null
    var strFromDate = ""
    var strToDate = ""
    var code = ""
    var proddetail = 0
    private val START_LOCATION = 100
    var statusposition = 0
    private var progressDialog: ProgressDialog? = null
    lateinit var prodDetailArrayList: JSONArray
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var jsonArray: JSONArray
    private val REQUEST_CODE = 101
    lateinit var serviceFollowUpListViewModel: ServiceFollowUpListViewModel
    lateinit var serviceFollowUpInfoViewModel: ServiceFollowUpInfoViewModel
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    var serviceFollowUpDet = 0
    var serviceFollowUpInfo = 0
    lateinit var serviceFollowUpArrayList: JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        context = this@ServiceFollowUpListActivity
        val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        setContentView(R.layout.activity_service_follow_up_list2)
        setId()
        //loadData()
        getServiceFollowUpList()
        setListners()
        Log.v("fssdfdsfdd", "" + ProdsuitApplication.encryptStart("22"));
    }

    private fun setListners() {
        lin_map.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
        imageView.setOnClickListener(this)
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            serviceFollowUpDet = 0
            getServiceFollowUpList()
//            val handler = Handler()
//            handler.postDelayed(object : Runnable {
//                override fun run() {
//                    swipeRefreshLayout.isRefreshing = false
//                }
//            }, 2000)
        })
        img_search.setOnClickListener(this)
        imback.setOnClickListener(this)
    }


    private fun setId() {
        tv_nodata = findViewById(R.id.tv_nodata)
        tv_address = findViewById(R.id.tv_address)
        lin_map = findViewById(R.id.lin_map)
        tv_cancel = findViewById(R.id.tv_cancel)
        cons_location = findViewById(R.id.cons_location)
        img_search = findViewById<ImageView>(R.id.img_filter)
        imback = findViewById<ImageView>(R.id.imback)
        imageView = findViewById<ImageView>(R.id.img_filter)
        recyclerView = findViewById(R.id.recycler)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
    }

    private fun loadData() {
        val serviceFollowArrayList = ArrayList<ServiceFollowUpListModel>()
        val e1 =
            ServiceFollowUpListModel(
                "TKT145874",
                "21/07/2022",
                "John",
                "9895314400",
                "High",
                "Open",
                "21/07/2022",
                "76.94756468242483",
                "8.591705686530284",
                "145",
                "Laptop",
                "0"
            )
        val e2 =
            ServiceFollowUpListModel(
                "TKT457814",
                "25/07/2022",
                "John",
                "9874568954",
                "Medium",
                "Pending",
                "21/07/2022",
                "76.94756468242483",
                "8.591705686530284",
                "135",
                "Laptop",
                "1"
            )
        val e3 =
            ServiceFollowUpListModel(
                "TKT145874",
                "31/07/2022",
                "John",
                "9874568954",
                "High",
                "Open",
                "21/07/2022",
                "76.94756468242483",
                "8.591705686530284",
                "1478",
                "Solar Fan panel",
                "2"
            )
        val e4 =
            ServiceFollowUpListModel(
                "TKT145874",
                "21/15/2025",
                "John",
                "9874568954",
                "Medium",
                "Pending",
                "21/07/2022",
                "76.94756468242483",
                "8.591705686530284",
                "14754",
                "Solar Fan panel",
                "1"
            )
        serviceFollowArrayList.add(e1)
        serviceFollowArrayList.add(e2)
        serviceFollowArrayList.add(e3)
        serviceFollowArrayList.add(e4)
        val gson = Gson()
        val listString = gson.toJson(
            serviceFollowArrayList,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        jsonArray = JSONArray(listString)

        setServiceFollowRecycler(jsonArray)
    }

    private fun getServiceFollowUpList() {
        Log.v("fsfsfds", "branch4 " + ID_Branch)
        recyclerView!!.adapter = null
        context = this@ServiceFollowUpListActivity
        serviceFollowUpListViewModel =
            ViewModelProvider(this).get(ServiceFollowUpListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpListViewModel.getServiceFollowUplist(
                    this,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.v("fsfsfds", "msg")
                                if (serviceFollowUpDet == 0) {
                                    serviceFollowUpDet++
                                    Log.v("fsfsfds", "det")
                                    val jObject = JSONObject(msg)
                                    Log.v("fsfsfds", "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        swipeRefreshLayout.visibility = View.VISIBLE
                                        tv_nodata.visibility = View.GONE
                                        val jobjt =
                                            jObject.getJSONObject("ServiceFollowUpdetails")
                                        serviceFollowUpArrayList =
                                            jobjt.getJSONArray("ServiceFollowUpdetailsList")
                                        Log.v(
                                            "fsfsfds",
                                            "overdueArrayList= " + serviceFollowUpArrayList.toString()
                                        )
                                        setServiceFollowRecycler(serviceFollowUpArrayList)
                                    } else {
                                        swipeRefreshLayout.visibility = View.GONE
                                        swipeRefreshLayout.isRefreshing = false
                                        tv_nodata.visibility = View.VISIBLE
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpListActivity,
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
                                swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
                            swipeRefreshLayout.visibility = View.GONE
                            swipeRefreshLayout.isRefreshing = false
                            tv_nodata.visibility = View.VISIBLE
                            Log.v("fsfsfds", "ex3 " + e)
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

    private fun setServiceFollowRecycler(jsonArray2: JSONArray) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.visibility = View.VISIBLE
        recyclerView!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        var adapter = ServiceFollowUpListAdapter(this, jsonArray2)
        recyclerView!!.adapter = adapter
        adapter.addItemClickListener(this)
    }

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {
        if (data.equals("followUp")) {
//            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
//            val intent = Intent(this, ServiceFollowUpActivity::class.java)
//            val runningStatus = 0
//            intent.putExtra("runningStatus", runningStatus)
//            intent.putExtra("customer_service_register", customer_service_register)
//            startActivity(intent)

            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
            val intent = Intent(this, ServiceFollowUpNewActivity::class.java)
            val runningStatus = 0
            intent.putExtra("runningStatus", runningStatus)
            intent.putExtra("customer_service_register", customer_service_register)
            startActivity(intent)
        }
        if (data.equals("info")) {
            serviceFollowUpInfo=0;
            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
            Log.v("dsfdfdfddd", "customer_service_register  " + customer_service_register)
            loadInfo(customer_service_register)
            // openAlertDialogForMoreInfo()
        }
        if (data.equals("Product")) {
            dialogCustSearch!!.dismiss()
        }
        if (data.equals("call")) {
            var mobile = jsonObject.getString("Mobile")
            performCall(mobile)
        }
        if (data.equals("location")) {
//            val jsonObject = jsonArray.getJSONObject(position)
//            longitude = jsonObject.getString("longitude")
//            latitude = jsonObject.getString("latitude")
            longitude="76.94756468242483"
            latitude="8.591705686530284"
            loadLocation()
        }
        if (data.equals("start")) {
            journeyType = 0
            if (checkLocationPermission()) {
                confirmBottomSheet(0)
            }
        }
        if (data.equals("stop")) {
            journeyType = 1
            if (checkLocationPermission()) {
                confirmBottomSheet(1)
            }
        }
        if (data.equals("restart")) {
            journeyType = 2
            if (checkLocationPermission()) {
                confirmBottomSheet(2)
            }
        }
        if (data.equals("tracking")) {
            val i = Intent(this, TrackerActivity::class.java)
            startActivity(i)
        }
    }

    private fun loadInfo(customerServiceRegister: String) {
        context = this@ServiceFollowUpListActivity
        serviceFollowUpInfoViewModel =
            ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpInfoViewModel.getServiceFollowUpInfo(
                    this, customerServiceRegister,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.e(TAG, "msg    "+msg)
                                if (serviceFollowUpInfo == 0) {
                                    serviceFollowUpInfo++
                                    Log.v("fsfsfds", "det")
                                    val jObject = JSONObject(msg)
                                    Log.v("dfsfrffff", "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        swipeRefreshLayout.visibility = View.VISIBLE
                                        tv_nodata.visibility = View.GONE
                                        val jobjt =
                                            jObject.getJSONObject("EmployeeWiseTicketSelect")
                                        openAlertDialogForMoreInfo(jobjt)
                                       // setServiceFollowRecycler(overdueArrayList)
                                    } else {
                                        swipeRefreshLayout.visibility = View.GONE
                                        swipeRefreshLayout.isRefreshing = false
                                        tv_nodata.visibility = View.VISIBLE
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpListActivity,
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
                                swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
                            swipeRefreshLayout.visibility = View.GONE
                            swipeRefreshLayout.isRefreshing = false
                            tv_nodata.visibility = View.VISIBLE
                            Log.v("fsfsfds", "ex3 " + e)
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

    private fun performCall(mobile: String) {
        val ALL_PERMISSIONS = 101
        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE
        )
        if (ContextCompat.checkSelfPermission(
                this@ServiceFollowUpListActivity,
                Manifest.permission.CALL_PHONE
            ) + ContextCompat.checkSelfPermission(
                this@ServiceFollowUpListActivity,
                Manifest.permission.RECORD_AUDIO
            )
            + ContextCompat.checkSelfPermission(
                this@ServiceFollowUpListActivity,
                Manifest.permission.READ_PHONE_STATE
            )
            + ContextCompat.checkSelfPermission(
                this@ServiceFollowUpListActivity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {

//            val jsonObject = todoArrayList.getJSONObject(position)
//
//            val mobileno = jsonObject.getString("LgCusMobile")
//            val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
//            val BroadCallEditer = BroadCallSP.edit()
//            BroadCallEditer.putString("BroadCall", "Yes")
//            BroadCallEditer.putString("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
//            BroadCallEditer.putString("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
//            BroadCallEditer.putString("FK_Employee", jsonObject.getString("FK_Employee"))
//            BroadCallEditer.putString("AssignedTo", jsonObject.getString("AssignedTo"))
//            BroadCallEditer.commit()

            intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobile))
            startActivity(intent)
        }
    }

    private fun loadLocation() {
        Log.v("fsdfsdds","location")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            this@ServiceFollowUpListActivity
        )
        Log.v("fsdfsdds","location1")
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.v("fsdfsdds","2")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            )
            return
        }
        Log.v("fsdfsdds","2.5")
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            Log.v("fsdfsdds","2.7")
            if (location != null) {
                Log.v("fsdfsdds","3")
                currentLocation = location

                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment!!.getMapAsync(this)
            }
            val supportMapFragment =
                (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
            supportMapFragment!!.getMapAsync(this)
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.lin_map -> {
                val mapUri =
                    Uri.parse("http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + city + ")")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
            R.id.img_filter -> {
                showFilterAlert()
            }
            R.id.imback -> {
                onBackPressed()
            }
            R.id.tv_cancel -> {
                cons_location.visibility = View.GONE
            }
        }
    }

    private fun getProductDetail(edtProduct: EditText) {
        var ID_Category = ""
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, ID_Category)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (proddetail == 0) {
                                    proddetail++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductDetailsList")
                                        prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                        if (prodDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            productSearchPopup(prodDetailArrayList, edtProduct)
                                            //  productSearch(edtProduct,prodDetailArrayList)
//                                             }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this,
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


    private fun productSearch(edtProduct: EditText, prodDetailArrayList: JSONArray) {
//        val productArrayList = ArrayList<ProductModel>()
//        val e1 = ProductModel("1", "4578122", "a1")
//        val e2 = ProductModel("2", "342343", "b1")
//        val e3 = ProductModel("3", "34333434", "c1")
//        val e4 = ProductModel("4", "5555", "d1")
//        val e5 = ProductModel("5", "45781344322", "e1")
//        productArrayList.add(e1)
//        productArrayList.add(e2)
//        productArrayList.add(e3)
//        productArrayList.add(e3)
//        productArrayList.add(e4)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e5)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e3)
//        productArrayList.add(e3)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e3)
//        productArrayList.add(e5)
//        productArrayList.add(e5)
//        val gson = Gson()
//        val listString = gson.toJson(
//            productArrayList,
//            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
//        )
//        var jsonArray = JSONArray(listString)
        //  productSearchPopup(jsonArray, edtProduct)
    }

    private fun showFilterAlert() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.alert_service_followup_filter, null)
        val btnNo = view.findViewById<TextView>(R.id.tv_cancel)
        val btnYes = view.findViewById<TextView>(R.id.tv_submit)
        val imgSearch = view.findViewById<ImageView>(R.id.img_search)
        val edt_fromDate = view.findViewById<EditText>(R.id.edt_fromDate)
        val edtTicket = view.findViewById<EditText>(R.id.edtTicket)
        val edtProduct = view.findViewById<EditText>(R.id.edtProduct)
        val edt_customer = view.findViewById<EditText>(R.id.edt_customer)
        val edt_toDate = view.findViewById<EditText>(R.id.edt_toDate)
        val edt_status = view.findViewById<AutoCompleteTextView>(R.id.edt_status)
        detailsShowingStatus(edt_status)
        edt_fromDate.setOnClickListener(View.OnClickListener {
            openBottomSheet(0, edt_fromDate)
        })
        edt_toDate.setOnClickListener(View.OnClickListener {
            openBottomSheet(0, edt_toDate)
        })
        edtProduct.setOnClickListener {
            // productSearch(edtProduct)
            Config.disableClick(it)
            proddetail = 0
            getProductDetail(edtProduct)
        }
        btnNo.setOnClickListener {
            edtTicket.text = null
            edtProduct.text = null
            edt_customer.text = null
            edt_fromDate.text = null
            edt_toDate.text = null
            strFromDate = ""
            strToDate = ""
        }
        btnYes.setOnClickListener {
            var jsonArrayFilterd: JSONArray = JSONArray()
            var ticketNumber: String = ""
            var product: String = ""
            var date: String = ""
            var customer: String = ""
            var tickerNumber: String = ""
            var status: String = ""

            ticketNumber = edtTicket.text.toString()
            product = edtProduct.text.toString()
            date = edt_fromDate.text.toString()
            customer = edt_customer.text.toString()
            status = edt_status.text.toString()
            if (status.equals("Choose"))
            {
                status=""
            }
            var x = 0
            for (i in 0 until serviceFollowUpArrayList.length()) {
                val item = serviceFollowUpArrayList.getJSONObject(i)
                Log.v("fdfddefe", "i" + i)
                if (item.getString("Ticket").toLowerCase().contains(ticketNumber.toLowerCase()) &&
                    //item.getString("product").toLowerCase().contains(product.toLowerCase()) &&
                    item.getString("Customer").toLowerCase().contains(customer.toLowerCase()) &&
                    item.getString("CurrentStatus").toLowerCase().contains(status.toLowerCase())
                ) {
                    jsonArrayFilterd.put(item)
                }
            }
            Log.v("fdfddefe", "json size=" + jsonArrayFilterd.length())
            setServiceFollowRecycler(jsonArrayFilterd)
        }
        dialog!!.setContentView(view)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun detailsShowingStatus(edt_status: AutoCompleteTextView) {
        val searchType =
            arrayOf<String>("Choose","New", "Pending", "On-Hold", "Completed")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        edt_status!!.setAdapter(adapter)
        edt_status!!.setText(searchType.get(0), false)
        edt_status!!.setOnClickListener {
            edt_status!!.showDropDown()
        }
        edt_status!!.setOnItemClickListener { parent, view, position, id ->
            statusposition = position
        }
    }

    private fun productSearchPopup(customerArrayList: JSONArray, edtProduct: EditText) {
        try {
            dialogCustSearch = Dialog(this)
            dialogCustSearch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCustSearch!!.setContentView(R.layout.productsearch_popup)
            dialogCustSearch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyCustomer = dialogCustSearch!!.findViewById(R.id.recyCustomer) as RecyclerView
            val actv_namcode =
                dialogCustSearch!!.findViewById(R.id.actv_namcode) as AutoCompleteTextView
            val edt_product = dialogCustSearch!!.findViewById(R.id.edt_product) as EditText
            detailsShowing(actv_namcode)
            val lLayout = GridLayoutManager(this, 1)
            recyCustomer!!.layoutManager = lLayout
            val adapter = ProductAdapter(customerArrayList, edtProduct, code)
            recyCustomer!!.adapter = adapter
            adapter.setClickListener(this)
            edt_product.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    var productSort = JSONArray()
                    if (type == 0) {
                        //name
                        val textlength = edt_product!!.text.length
                        for (k in 0 until customerArrayList.length()) {
                            val jsonObject = customerArrayList.getJSONObject(k)
                            if (textlength <= jsonObject.getString("ProductName").length) {
                                if (jsonObject.getString("ProductName")!!.toLowerCase().trim()
                                        .contains(
                                            edt_product!!.text.toString().toLowerCase().trim()
                                        )
                                ) {
                                    productSort.put(jsonObject)
                                }
                            }
                        }
                    } else {
                        //code
                        val textlength = edt_product!!.text.length

                        for (k in 0 until customerArrayList.length()) {
                            val jsonObject = customerArrayList.getJSONObject(k)
                            if (textlength <= jsonObject.getString("ID_Product").length) {
                                if (jsonObject.getString("ID_Product")!!.toLowerCase().trim()
                                        .contains(
                                            edt_product!!.text.toString().toLowerCase().trim()
                                        )
                                ) {
                                    productSort.put(jsonObject)
                                }

                            }
                        }

                    }
                    val adapter = ProductAdapter(productSort, edtProduct, code)
                    recyCustomer!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUpListActivity)
                }
            })
            dialogCustSearch!!.show()
            dialogCustSearch!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogCustSearch!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun detailsShowing(actv_namcode: AutoCompleteTextView) {
        //actv_nammob
        val searchType = arrayOf<String>("Name", "Code")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        actv_namcode!!.setAdapter(adapter)
        actv_namcode!!.setText(searchType.get(0), false)
        actv_namcode!!.setOnClickListener {
            actv_namcode!!.showDropDown()
        }
        actv_namcode!!.setOnItemClickListener { parent, view, position, id ->
            type = position
        }
    }

    private fun openBottomSheet(i: Int, edt_Date: EditText) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_date, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1.maxDate = System.currentTimeMillis()
        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
                if (i == 0) {
                    edt_Date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strFromDate = strYear + "-" + strMonth + "-" + strDay
                } else {
                    edt_Date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strToDate = strYear + "-" + strMonth + "-" + strDay
                }
            } catch (e: Exception) {

            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openAlertDialogForMoreInfo(jobjt: JSONObject) {
//        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView: View = inflater.inflate(R.layout.alert_more_info, null)
//        dialogBuilder.setView(dialogView)
//        val alertDialog = dialogBuilder.create()

        var dialogView = Dialog(this)
        dialogView!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogView!! .setContentView(R.layout.alert_more_info)
        dialogView!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

        val window: Window? = dialogView!!.getWindow()
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

//        var imAdpBack: ImageView = dialogView.findViewById<ImageView>(R.id.imAdpBack)
//        var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
//        var tv_ticket: TextView = dialogView.findViewById<TextView>(R.id.tv_ticket)
//        var tv_prod_regOn: TextView = dialogView.findViewById<TextView>(R.id.tv_prod_regOn)
//        var tv_visit_on: TextView = dialogView.findViewById<TextView>(R.id.tv_visit_on)
//        var tv_CustomerName: TextView = dialogView.findViewById<TextView>(R.id.tv_CustomerName)
//        var tv_phone: TextView = dialogView.findViewById<TextView>(R.id.tv_phone)
//        var tv_location: TextView = dialogView.findViewById<TextView>(R.id.tv_location)
//        var tv_address: TextView = dialogView.findViewById<TextView>(R.id.tv_address)
//        var tv_product_name: TextView = dialogView.findViewById<TextView>(R.id.tv_product_name)
//        var tv_category: TextView = dialogView.findViewById<TextView>(R.id.tv_category)
//        var tv_service: TextView = dialogView.findViewById<TextView>(R.id.tv_service)
//        var tv_description: TextView = dialogView.findViewById<TextView>(R.id.tv_description)
//        tv_description.setMovementMethod(ScrollingMovementMethod())
//        tv_cancel.setOnClickListener(View.OnClickListener {
//            dialogView.dismiss()
//        })
//
//        imAdpBack.setOnClickListener(View.OnClickListener {
//            dialogView.dismiss()
//        })
//        tv_ticket.text=jobjt.getString("Ticket")
//        tv_prod_regOn.text=jobjt.getString("RegisterdOn")
//        tv_visit_on.text=jobjt.getString("VisitOn")
//        tv_CustomerName.text=jobjt.getString("CustomerName")
//        tv_phone.text=jobjt.getString("Mobile")
//        tv_address.text=jobjt.getString("Address1")+","+jobjt.getString("Address2")+","+jobjt.getString("Address3")
//        tv_product_name.text=jobjt.getString("ProductName")
//        tv_category.text=jobjt.getString("Category")
//        tv_service.text=jobjt.getString("Service")
//        tv_description.text=jobjt.getString("Description")

       // dialogView.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        cons_location.visibility = View.VISIBLE
        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
        Log.v("sfsfsf33rff", "latitude " + latitude.toDouble())
        Log.v("sfsfsf33rff", "longitude " + longitude.toDouble())
        val geocoder: Geocoder
        var addresses: List<Address?>
        geocoder = Geocoder(this@ServiceFollowUpListActivity, Locale.getDefault())
        Log.v("sfsfsf33rff", "geocoder " + geocoder)
        addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1);
        if (addresses != null) {
            try {
                Log.v("sfsfsf33rff", "addresses " + addresses.toString())
                city = addresses.get(0)!!.getAddressLine(0);
                tv_address.setText(city)
                Log.v("sfsfsf33rff", "city " + city)
                val markerOptions = MarkerOptions().position(latLng).title(city)
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                googleMap.addMarker(markerOptions)
            } catch (e: Exception) {
                Config.snackBars(
                    this,
                    getWindow().getDecorView().findViewById(android.R.id.content),
                    "No location data available"
                )
            }
        } else {
            Config.snackBars(
                this,
                getWindow().getDecorView().findViewById(android.R.id.content),
                "No location data available"
            )
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

    private fun fetchLocation() {
        startActivityForResult(
            Intent(
                this@ServiceFollowUpListActivity,
                LocationActivity2::class.java
            ), START_LOCATION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == START_LOCATION) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
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

    private fun startStopWork(latitude: Double, longitude: Double, address: String?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        val current = LocalDateTime.now().format(formatter)
        Log.v("dfsdfds34343f", "current " + current)

    }
}