package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ServiceFollowUpActivity : AppCompatActivity(), View.OnClickListener {
    private var tv_ServiceCost: TextView? = null
    private var tv_replaced_product_cost: TextView? = null
    private var tv_next: TextView? = null
    private var isMoreClicked: Boolean = false
    private var tv_moreServices: TextView? = null
    private var tv_moreComponents: TextView? = null
    private var tv_ticketDetails: TextView? = null
    private var tv_attendance: TextView? = null
    private var tv_CustClick: TextView? = null
    private var tv_ProductDetails: TextView? = null
    private var lin_ticketDetails: LinearLayout? = null
    private var lin_add_service: LinearLayout? = null
    private var lin_CustClick: LinearLayout? = null
    private var lin_ProductDetails: LinearLayout? = null
    private var lin_service_cost: LinearLayout? = null
    private var lin_replacePoductCost: LinearLayout? = null
    private var lin_add_replaced_product: LinearLayout? = null
    private var lin_attendance: LinearLayout? = null
    private var lin_bottom_menu: LinearLayout? = null
    private var card_start: CardView? = null
    private var card_stop: CardView? = null
    private var card_restart: CardView? = null
    private lateinit var recycler_service_cost: RecyclerView
    private lateinit var recycleView_replaceproduct: RecyclerView
    private lateinit var recyclerAttendance: RecyclerView
    private lateinit var lin_moreservice: ConstraintLayout
    var ticketDetailsMode: String? = "0"
    var CustMode: String? = "1"
    var journeyType: Int = 0
    var productMode: String? = "1"
    var runningStatus: String? = ""
    var FK_Customer: String = ""
    var FK_CustomerOthers: String = ""
    var FK_Product: String = ""
    var customer_service_register: String = ""
    private var tabLayout: TabLayout? = null
    var jsonArray2: JSONArray = JSONArray()
    var jsonArrayMappedServiceAttended: JSONArray = JSONArray()
    var jsonArrayMoreServiceAttended: JSONArray = JSONArray()
    var jsonArrayServiceType: JSONArray = JSONArray()
    var jsonArrayChangeMode: JSONArray = JSONArray()
    var jsonArrayReplacedProductMap: JSONArray = JSONArray()
    var jsonArrayReplacedProductMore: JSONArray = JSONArray()
    var adapter: ServiceCostAdapter? = null
    var adapterReplacedProductCost: ReplacedProductCostAdapter? = null
    var adapterAttendanceServiceFollowUp: AttendanceServiceFollowUpAdapter? = null
    var imgBack: ImageView? = null
    var img_record: ImageView? = null
    val serviceCostArrayList = ArrayList<ServiceCostModelMain>()
    val replacedProductCostArrayList = ArrayList<ReplacedProductCostModel>()
    val replacedProductCostArrayListFinal = ArrayList<ReplacedProductCostModelFinal>()
    val attendanceFollowUpArrayList = ArrayList<AttendanceFollowUpModel>()
    var actv_action: AutoCompleteTextView? = null
    var isMapPressed: Boolean = false
    var isServiceCostClicked: Boolean = false
    var isAttendanceClicked: Boolean = false
    var isReplacedProductClicked: Boolean = false
    var isServiceDataSet: Boolean = false
    var isReplaceDataSet: Boolean = false
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private val START_LOCATION = 100
    lateinit var serviceFollowUpAttendanceListViewModel: ServiceFollowUpAttendanceListViewModel
    lateinit var serviceFollowUpMappedServiceViewModel: ServiceFollowUpMappedServiceViewModel
    lateinit var serviceFollowUpMappedReplacedProductViewModel: ServiceFollowUpMappedReplacedProductViewModel
    lateinit var serviceFollowUpMoreServiceViewModel: ServiceFollowUpMoreServiceViewModel
    lateinit var serviceFollowUpServiceTypeViewModel: ServiceFollowUpServiceTypeViewModel
    lateinit var serviceFollowUpChangeModeViewModel: ServiceFollowUpChangeModeViewModel
    lateinit var serviceFollowUpMoreReplacedProductsViewModel: ServiceFollowUpMoreReplacedProductsViewModel
    lateinit var serviceFollowUpInfoViewModel: ServiceFollowUpInfoViewModel
    private var progressDialog: ProgressDialog? = null
    private var ID_Branch = "";
    private var ID_Employee: String = ""
    lateinit var serviceFollowUpAttendanceArrayList: JSONArray
    var serviceFollowUpAttendance = 0
    var serviceFollowUpMappedService = 0
    var serviceFollowUpMappedReplacedProduct = 0
    var serviceFollowUpMoreService = 0
    var serviceFollowUpServiceType = 0
    var serviceFollowUpChangeMmode = 0
    var serviceFollowUpInfo = 0
    var serviceFollowUpMoreReplacedProduct = 0

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_service_follow_up)
        setId()
        getSupportActionBar()?.openOptionsMenu()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.back_arro);
        getSupportActionBar()?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.shape_header
            )
        )
        getSupportActionBar()?.setCustomView(R.layout.toolbar)
        getSupportActionBar()?.setDisplayShowCustomEnabled(true)
        runningStatus = intent.getStringExtra("runningStatus")
        customer_service_register = intent.getStringExtra("customer_service_register").toString()
        val FK_BranchCodeUserSP = this.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
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
        card_start!!.setOnClickListener(this)
        card_stop!!.setOnClickListener(this)
        card_restart!!.setOnClickListener(this)
        tv_ticketDetails!!.setOnClickListener(this)
        tv_CustClick!!.setOnClickListener(this)
        tv_ProductDetails!!.setOnClickListener(this)
        tv_moreServices!!.setOnClickListener(this)
        img_record!!.setOnClickListener(this)
        tv_moreComponents!!.setOnClickListener(this)
        lin_add_service!!.setOnClickListener(this)
        imgBack!!.setOnClickListener(this)
        tv_next!!.setOnClickListener(this)
        tv_ServiceCost!!.setOnClickListener(this)
        lin_add_replaced_product!!.setOnClickListener(this)
        tv_replaced_product_cost!!.setOnClickListener(this)
        tv_attendance!!.setOnClickListener(this)
        hideViews()
        // addTabItem()
        //loadServiceCost()
        //loadReplacedProductCost()
        loadAttendance()
        loadMappedeServiceAttended()
        loadMoreServiceAttended()
        loadMappedReplacedProducts()
        loadMoreReplacedProducts()
        loadServiceType()
        loadChangeMode();
        loadInfo(customer_service_register)
        // loadHistory()
        adapter = ServiceCostAdapter(this, jsonArray2, serviceCostArrayList, jsonArrayServiceType)
        recycler_service_cost!!.adapter = adapter
        adapterReplacedProductCost =
            ReplacedProductCostAdapter(
                this,
                jsonArray2,
                replacedProductCostArrayListFinal,
                jsonArrayChangeMode
            )
        recycleView_replaceproduct!!.adapter = adapterReplacedProductCost
        adapterAttendanceServiceFollowUp =
            AttendanceServiceFollowUpAdapter(this, jsonArray2, attendanceFollowUpArrayList)
        recyclerAttendance!!.adapter = adapterAttendanceServiceFollowUp


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            finish()
        }
        if (item.getItemId() === R.id.history) {
            val intent = Intent(this, ServiceFollowUpHistory::class.java)
            intent.putExtra("customer_service_register", customer_service_register)
            intent.putExtra("FK_Customer", FK_Customer)
            intent.putExtra("FK_CustomerOthers", FK_CustomerOthers)
            intent.putExtra("FK_Product", FK_Product)
            startActivity(intent)
        }
        if (item.getItemId() === R.id.amc) {
            val intent = Intent(this, ServiceFollowUpAmcListActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.follow_up, menu)
        if (menu is MenuBuilder) {
            val m: MenuBuilder = menu as MenuBuilder
            m.setOptionalIconsVisible(true)
        }
        return true
    }

    private fun loadArrays() {
//

//        val replacedProductCostArrayList = ArrayList<ReplacedProductCostModel>()
//        val e122 = ReplacedProductCostModel(
//            "Solar Light",
//            "250",
//            "25",
//            "PickUp",
//            "120",
//            "",
//            "Remark1",
//            "false"
//        )
//        val e222 = ReplacedProductCostModel(
//            "UPSS - 600 VA",
//            "315",
//            "2",
//            "Replace",
//            "15",
//            "",
//            "Remark2",
//            "false"
//        )
//        replacedProductCostArrayList.add(e122)
//        replacedProductCostArrayList.add(e222)
//        val gson22 = Gson()
//        val listString22 = gson22.toJson(
//            replacedProductCostArrayList,
//            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
//        )
//        jsonArrayReplacedProductMap = JSONArray(listString22)

//        val replacedProductCostArrayList22 = ArrayList<ReplacedProductCostModel>()
//        val e133 = ReplacedProductCostModel(
//            "Solar Light",
//            "250",
//            "25",
//            "PickUp",
//            "120",
//            "",
//            "Remark1",
//            "false",
//            ""
//        )
//        val e233 = ReplacedProductCostModel(
//            "UPS - 600 VA",
//            "315",
//            "2",
//            "Replace",
//            "15",
//            "",
//            "Remark2",
//            "false",
//            ""
//        )
//        val e333 = ReplacedProductCostModel(
//            "UPBS - 600 VA",
//            "5454",
//            "23",
//            "Replace",
//            "153",
//            "",
//            "Remark3",
//            "false",
//            ""
//        )
//        replacedProductCostArrayList22.add(e133)
//        replacedProductCostArrayList22.add(e233)
//        replacedProductCostArrayList22.add(e333)
//        val gson33 = Gson()
//        val listString33 = gson33.toJson(
//            replacedProductCostArrayList22,
//            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
//        )
//        jsonArrayReplacedProductMore = JSONArray(listString33)

    }


    private fun setId() {
        card_start = findViewById<CardView>(R.id.card_start)
        card_stop = findViewById<CardView>(R.id.card_stop)
        card_restart = findViewById<CardView>(R.id.card_restart)
        img_record = findViewById<ImageView>(R.id.img_record)
        lin_moreservice = findViewById<ConstraintLayout>(R.id.lin_moreservice)
        tv_attendance = findViewById<TextView>(R.id.tv_attendance)
        tv_replaced_product_cost = findViewById<TextView>(R.id.tv_replaced_product_cost)
        tv_ServiceCost = findViewById<TextView>(R.id.tv_ServiceCost)
        lin_add_service = findViewById<LinearLayout>(R.id.lin_add_service)
        lin_add_replaced_product = findViewById<LinearLayout>(R.id.lin_add_replaced_product)
        tv_ticketDetails = findViewById<TextView>(R.id.tv_ticketDetails)
        tv_CustClick = findViewById<TextView>(R.id.tv_CustClick)
        tv_ProductDetails = findViewById<TextView>(R.id.tv_ProductDetails)
        tv_next = findViewById<TextView>(R.id.tv_next)
        tv_moreServices = findViewById<TextView>(R.id.tv_moreServices)
        tv_moreComponents = findViewById<TextView>(R.id.tv_moreComponents)
        lin_ticketDetails = findViewById<LinearLayout>(R.id.lin_ticketdetails)
        lin_CustClick = findViewById<LinearLayout>(R.id.lin_CustClick)
        lin_ProductDetails = findViewById<LinearLayout>(R.id.lin_ProductDetails)
        lin_service_cost = findViewById(R.id.lin_service_cost)
        lin_replacePoductCost = findViewById(R.id.lin_replacePoductCost)
        lin_attendance = findViewById(R.id.lin_attendance)
        lin_bottom_menu = findViewById(R.id.lin_bottom_menu)
        tabLayout = findViewById(R.id.tabLayout);
        recycler_service_cost = findViewById(R.id.recycler_service_cost)
        recycleView_replaceproduct = findViewById(R.id.recycleView_replaceproduct)
        recyclerAttendance = findViewById(R.id.recyclerAttendance)
        imgBack = findViewById(R.id.imback)

    }


    override fun onClick(v: View) {
        when (v.id) {
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
            R.id.img_record -> {
                val intent = Intent(this, ServiceFollowUpHistory::class.java)
                startActivity(intent)
            }
            R.id.tv_attendance -> {
                if (isAttendanceClicked) {
                    isAttendanceClicked = false
                    lin_attendance!!.visibility = View.GONE
                } else {
                    isAttendanceClicked = true
                    tv_moreServices!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.VISIBLE
                    lin_bottom_menu!!.visibility = View.VISIBLE
                }
            }
            R.id.tv_ServiceCost -> {
                if (isServiceCostClicked) {
                    isServiceCostClicked = false
                    lin_service_cost!!.visibility = View.GONE
                    tv_moreServices!!.visibility = View.GONE
                } else {
                    isServiceCostClicked = true
                    if (isServiceDataSet) {
                        tv_moreServices!!.visibility = View.VISIBLE
                    }
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.VISIBLE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.VISIBLE
                }
            }

            R.id.tv_replaced_product_cost -> {
                if (isReplacedProductClicked) {
                    isReplacedProductClicked = false
                    lin_replacePoductCost!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.GONE
                } else {
                    isReplacedProductClicked = true
                    tv_moreServices!!.visibility = View.GONE
                    if (isReplaceDataSet) {
                        tv_moreComponents!!.visibility = View.VISIBLE
                    }
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.VISIBLE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.VISIBLE
                }
            }
            R.id.tv_next -> {
                saveData(v)
            }
            R.id.imback -> {
                onBackPressed()
            }
            R.id.tv_ticketDetails -> {
                if (ticketDetailsMode.equals("0")) {
                    lin_ticketDetails!!.visibility = View.GONE
                    ticketDetailsMode = "1"
                } else {
                    lin_ticketDetails!!.visibility = View.VISIBLE
                    ticketDetailsMode = "0"
                    CustMode = "1"
                    productMode = "1"
                    hideViews()
                }
            }
            R.id.tv_CustClick -> {
                if (CustMode.equals("0")) {
                    lin_CustClick!!.visibility = View.GONE
                    CustMode = "1"
                } else {
                    lin_CustClick!!.visibility = View.VISIBLE
                    ticketDetailsMode = "1"
                    CustMode = "0"
                    productMode = "1"
                    hideViews()
                }
            }
            R.id.tv_ProductDetails -> {
                if (productMode.equals("0")) {
                    lin_ProductDetails!!.visibility = View.GONE
                    productMode = "1"
                } else {
                    lin_ProductDetails!!.visibility = View.VISIBLE
                    ticketDetailsMode = "1"
                    CustMode = "1"
                    productMode = "0"
                    hideViews()
                }
            }
            R.id.lin_add_service -> {
                openAlertDialogForMoreServices(
                    jsonArrayMappedServiceAttended,
                    jsonArrayMoreServiceAttended
                )
            }
            R.id.tv_moreServices -> {
                Log.v(
                    "sdfsdfdsfdfdfddd",
                    "jsonArrayMappedServiceAttended " + jsonArrayMappedServiceAttended
                )
                Log.v(
                    "sdfsdfdsfdfdfddd",
                    "jsonArrayMoreServiceAttended " + jsonArrayMoreServiceAttended
                )
                openAlertDialogForMoreServices(
                    jsonArrayMappedServiceAttended,
                    jsonArrayMoreServiceAttended
                )
            }
            R.id.lin_add_replaced_product -> {
                openAlertDialogForMoreReplacedProduct(
                    jsonArrayReplacedProductMap,
                    jsonArrayReplacedProductMore
                )
            }
            R.id.tv_moreComponents -> {

                openAlertDialogForMoreReplacedProduct(
                    jsonArrayReplacedProductMap,
                    jsonArrayReplacedProductMore
                )
            }
        }
    }


    private fun saveData(
        v: View
    ) {
        var serviceCostArrayListFinal: ArrayList<ServiceCostModelMain> =
            adapter!!.getServiceCost()
        val gson = Gson()
        val listString = gson.toJson(
            serviceCostArrayListFinal,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        var jsonArrayServiceCost = JSONArray(listString)
        var replacedProductCostArrayList: ArrayList<ReplacedProductCostModel>? =
            adapterReplacedProductCost!!.getReplaceProductCost()
        val gson1 = Gson()
        val listString1 = gson1.toJson(
            replacedProductCostArrayList,
            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
        )
        var jsonArrayreplacedProductCost = JSONArray(listString1)
        var attendanceFollowUpArrayList: ArrayList<AttendanceFollowUpModel> =
            adapterAttendanceServiceFollowUp!!.getAttendance()
        val gson2 = Gson()
        val listString2 = gson2.toJson(
            attendanceFollowUpArrayList,
            object : TypeToken<ArrayList<AttendanceFollowUpModel?>?>() {}.type
        )
        var jsonArrayattendanceFollowUp = JSONArray(listString2)
        if (jsonArrayattendanceFollowUp.length() == 0) {
            Config.snackBars(this, v, "Please mark attended person")
        } else {

            var intent = Intent(this, ServiceFollowUpSummary::class.java)
            intent.putExtra("jsonArrayServiceCost", jsonArrayServiceCost.toString());
            intent.putExtra(
                "jsonArrayreplacedProductCost",
                jsonArrayreplacedProductCost.toString()
            );
            intent.putExtra("jsonArrayAttendance", jsonArrayattendanceFollowUp.toString());
            startActivity(intent)
        }
    }

    private fun openAlertDialogForMoreServices(
        jsonArrayMapped: JSONArray,
        jsonArrayMore: JSONArray
    ) {
        Log.v("sfsf44refd", "openAlertDialogForMoreServices")
        var tv_addServices: TextView = findViewById<TextView>(R.id.tv_addServices2)
        var tv_mapServices: TextView = findViewById<TextView>(R.id.tv_mapServices)
        var tv_cancel: TextView = findViewById<TextView>(R.id.tv_cancel)
        var tv_submit: TextView = findViewById<TextView>(R.id.tv_submit2)
        var edt_product: EditText = findViewById<EditText>(R.id.edt_product)
        var recycleView: RecyclerView = findViewById(R.id.recycleView)
        var recycleView2: RecyclerView = findViewById(R.id.recycleView2)
        lin_moreservice.visibility = View.VISIBLE
        edt_product.setText("")
        recycleView!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        recycleView2!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        val serviceCostArrayListMapped = ArrayList<ServiceCostModelMain>()
        for (i in 0 until jsonArrayMapped.length()) {
            val obj = jsonArrayMapped.getJSONObject(i)
            var isChecked = ""
            try {
                isChecked = obj!!.getString("isChecked")
            } catch (e: Exception) {
                isChecked = "false"
            }
            serviceCostArrayListMapped.add(
                i,
                ServiceCostModelMain(
                    "",
                    obj!!.getString("ID_ProductWiseServiceDetails"),
                    obj!!.getString("SubProduct"),
                    obj!!.getString("ID_Product"),
                    obj!!.getString("ID_Services"),
                    obj!!.getString("Service"),
                    obj!!.getString("ServiceCost"),
                    obj!!.getString("ServiceTaxAmount"),
                    obj!!.getString("ServiceNetAmount"),
                    obj!!.getString("Remarks"),
                    isChecked,
                    ""
                )
            )
        }
        val serviceCostArrayListMore = ArrayList<ServiceCostModelMain>()
        for (i in 0 until jsonArrayMore.length()) {
            val obj = jsonArrayMore.getJSONObject(i)
            var isChecked = ""
            try {
                isChecked = obj!!.getString("isChecked")
            } catch (e: Exception) {
                isChecked = "false"
            }
            serviceCostArrayListMore.add(
                i,
                ServiceCostModelMain(
                    "",
                    "",
                    "",
                    "",
                    obj!!.getString("ID_Services"),
                    obj!!.getString("Service"),
                    "",
                    "",
                    "",
                    "",
                    isChecked,
                    "",
                )
            )
        }
        var adapter: ServiceCostAdapterSecondary =
            ServiceCostAdapterSecondary(this, jsonArrayMapped, serviceCostArrayListMapped)
        recycleView!!.adapter = adapter
        var adapter2: ServiceCostAdapterThird =
            ServiceCostAdapterThird(this, jsonArrayMore, serviceCostArrayListMore)
        recycleView2!!.adapter = adapter2
        tv_mapServices.setOnClickListener(View.OnClickListener {
            if (isMapPressed) {
                isMapPressed = false
                recycleView.visibility = View.GONE
            } else {
                isMapPressed = true
                recycleView.visibility = View.VISIBLE
                recycleView2.visibility = View.GONE
            }
        })
        tv_addServices.setOnClickListener(View.OnClickListener {
            if (isMoreClicked) {
                isMoreClicked = false
                recycleView2.visibility = View.GONE
            } else {
                isMoreClicked = true
                recycleView2.visibility = View.VISIBLE
                recycleView.visibility = View.GONE
            }
        })
        tv_cancel.setOnClickListener(View.OnClickListener {
            Log.v("sfsf44refd", "cancel")
            lin_moreservice.visibility = View.GONE
        })
        edt_product.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var serviceMappedSort = JSONArray()
                var serviceMoreSort = JSONArray()
                val textlength = edt_product!!.text.length
                for (k in 0 until jsonArrayMapped.length()) {
                    val jsonObject = jsonArrayMapped.getJSONObject(k)
                    if (textlength <= jsonObject.getString("Service").length) {
                        if (jsonObject.getString("Service")!!.toLowerCase().trim().contains(
                                edt_product!!.text.toString().toLowerCase().trim()
                            )
                        ) {
                            serviceMappedSort.put(jsonObject)
                        }
                    }
                }
                val serviceCostArrayListMapped2 = ArrayList<ServiceCostModelMain>()
                for (i in 0 until serviceMappedSort.length()) {
                    val obj = serviceMappedSort.getJSONObject(i)
                    serviceCostArrayListMapped2.add(
                        i,
                        ServiceCostModelMain(
                            "",
                            obj!!.getString("ID_ProductWiseServiceDetails"),
                            obj!!.getString("SubProduct"),
                            obj!!.getString("ID_Product"),
                            obj!!.getString("ID_Services"),
                            obj!!.getString("Service"),
                            obj!!.getString("ServiceCost"),
                            obj!!.getString("ServiceTaxAmount"),
                            obj!!.getString("ServiceNetAmount"),
                            obj!!.getString("Remarks"),
                            "",
                            ""
                        )
                    )
                }

                for (k in 0 until jsonArrayMore.length()) {
                    val jsonObject = jsonArrayMore.getJSONObject(k)
                    if (textlength <= jsonObject.getString("Service").length) {
                        if (jsonObject.getString("Service")!!.toLowerCase().trim().contains(
                                edt_product!!.text.toString().toLowerCase().trim()
                            )
                        ) {
                            serviceMoreSort.put(jsonObject)
                        }
                    }
                }
                val serviceCostArrayListMore = ArrayList<ServiceCostModelMain>()
                for (i in 0 until serviceMoreSort.length()) {
                    val obj = serviceMoreSort.getJSONObject(i)
                    serviceCostArrayListMore.add(
                        i,
                        ServiceCostModelMain(
                            "",
                            "",
                            "",
                            "",
                            obj!!.getString("ID_Services"),
                            obj!!.getString("Service"),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                        )
                    )
                }
                var adapter: ServiceCostAdapterSecondary =
                    ServiceCostAdapterSecondary(
                        this@ServiceFollowUpActivity,
                        serviceMappedSort,
                        serviceCostArrayListMapped2
                    )
                recycleView!!.adapter = adapter
                var adapter2: ServiceCostAdapterThird =
                    ServiceCostAdapterThird(
                        this@ServiceFollowUpActivity,
                        serviceMoreSort,
                        serviceCostArrayListMore
                    )
                recycleView2!!.adapter = adapter2
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        tv_submit.setOnClickListener(View.OnClickListener {
            var serviceCostArrayListFinal: ArrayList<ServiceCostModelMain> =
                adapter!!.getServiceCost()
            var serviceCostArrayListFinal2: ArrayList<ServiceCostModelMain> =
                adapter2!!.getServiceCost()
            var sumOfArray = serviceCostArrayListFinal.size + serviceCostArrayListFinal2.size
//            if (sumOfArray == 0) {
//                Config.snackBars(this, it, "Please check any service from list")
//            } else {
            lin_moreservice.visibility = View.GONE
            jsonArrayMappedServiceAttended = adapter!!.getServiceCostJson()
            jsonArrayMoreServiceAttended = adapter2!!.getServiceCostJson()
            Log.v(
                "adadasd3r3ffffdfd",
                "jsonArrayMappedServiceAttended " + jsonArrayMappedServiceAttended.toString()
            )
            Log.v(
                "adadasd3r3ffffdfd",
                "jsonArrayMoreServiceAttended " + jsonArrayMoreServiceAttended.toString()
            )
            serviceCostArrayList.clear()
            for (item in serviceCostArrayListFinal) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.ID_ProductWiseServiceDetails;
                Log.v("Check_Duplicate_values", "value " + value)
                for (item2 in serviceCostArrayList) {
                    var value2 = item2.ID_ProductWiseServiceDetails;
                    Log.v("Check_Duplicate_values", "value2 " + value2)
                    if (value == value2) {
                        present = 1
                        i2 = i
                        break
                    } else {
                        present = 0
                    }

                    Log.v("Check_Duplicate_values", "i " + i)
                    i = i + 1
                }
                if (present == 1) {
                    Log.v("Check_Duplicate_values", "pres " + i)
                    serviceCostArrayList.removeAt(i2)
                    serviceCostArrayList.add(item)
                } else {
                    Log.v("Check_Duplicate_values", "abs")
                    serviceCostArrayList.add(item)
                }

            }

            for (item in serviceCostArrayListFinal2) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.ID_Services;
                Log.v("Check_Duplicate_values", "value " + value)
                for (item2 in serviceCostArrayList) {
                    var value2 = item2.ID_Services;
                    Log.v("Check_Duplicate_values", "value2 " + value2)
                    if (value == value2) {
                        present = 1
                        i2 = i
                        break
                    } else {
                        present = 0
                    }

                    Log.v("Check_Duplicate_values", "i " + i)
                    i = i + 1
                }
                if (present == 1) {
                    Log.v("Check_Duplicate_values", "pres " + i)
                    serviceCostArrayList.removeAt(i2)
                    serviceCostArrayList.add(item)
                } else {
                    Log.v("Check_Duplicate_values", "abs")
                    serviceCostArrayList.add(item)
                }
            }
            val gson = Gson()
            val listString = gson.toJson(
                serviceCostArrayList,
                object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
            )
            var jsonArray = JSONArray(listString)
            setServiceCostRecycler(jsonArray)
            //  }

        })
    }

    private fun openAlertDialogForMoreReplacedProduct(
        jsonArrayMapped: JSONArray,
        jsonArrayMore: JSONArray
    ) {
        var lin_moreReplaced: ConstraintLayout = findViewById(R.id.lin_more_replace_product)
        var tv_mapRelated: TextView = findViewById<TextView>(R.id.tv_mapRelated)
        var tv_addReplacedMore: TextView = findViewById<TextView>(R.id.tv_addReplacedMore)
        var tv_cancel: TextView = findViewById<TextView>(R.id.tv_cancel3)
        var tv_submit: TextView = findViewById<TextView>(R.id.tv_submit3)
        var edt_replace_product: EditText = findViewById<EditText>(R.id.edt_replace_product)
        var recycleView: RecyclerView = findViewById(R.id.recycleView3)
        var recycleView2: RecyclerView = findViewById(R.id.recycleView4)
        lin_moreReplaced.visibility = View.VISIBLE
        edt_replace_product.setText("")
        recycleView!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        recycleView2!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        var replacedProductCostArrayList1 = ArrayList<ReplacedProductCostModel>()
        for (i in 0 until jsonArrayMapped.length()) {
            val obj = jsonArrayMapped.getJSONObject(i)
            var isChecked = ""
            try {
                isChecked = obj!!.getString("isChecked")
            } catch (e: Exception) {
                isChecked = "false"
            }
            replacedProductCostArrayList1.add(
                i,
                ReplacedProductCostModel(
                    obj!!.getString("ID_OLD_Product"),
                    obj!!.getString("OLD_Product"),
                    obj!!.getString("SPDOldQuantity"),
                    obj!!.getString("Amount"),
                    obj!!.getString("ReplaceAmount"),
                    obj!!.getString("Remarks"),
                    obj!!.getString("ID_Product"),
                    obj!!.getString("Product"),
                    isChecked
                )
            )

        }
        var replacedProductCostArrayList2 = ArrayList<MoreReplacedProductCostModel>()
        for (i in 0 until jsonArrayMore.length()) {
            val obj = jsonArrayMore.getJSONObject(i)
            var isChecked = ""
            try {
                isChecked = obj!!.getString("isChecked")
            } catch (e: Exception) {
                isChecked = "false"
            }
            replacedProductCostArrayList2.add(
                i,
                MoreReplacedProductCostModel(
                    obj!!.getString("ID_Product"),
                    obj!!.getString("Code"),
                    obj!!.getString("Name"),
                    obj!!.getString("MRPs"),
                    obj!!.getString("MRP1R"),
                    obj!!.getString("SalesPrice1R"),
                    obj!!.getString("SalePrice"),
                    obj!!.getString("CurrentStock1R"),
                    obj!!.getString("StockId"),
                    obj!!.getString("TaxAmount"),
                    obj!!.getString("StandbyStock"),
                    obj!!.getString("TotalCount"),
                    isChecked
                )
            )
        }
        var adapter1: ReplacedProductCostAdapterSecondary =
            ReplacedProductCostAdapterSecondary(
                this,
                jsonArrayMapped,
                replacedProductCostArrayList1
            )
        recycleView!!.adapter = adapter1
        var adapter2: ReplacedProductCostAdapterThird =
            ReplacedProductCostAdapterThird(this, jsonArrayMore, replacedProductCostArrayList2)
        recycleView2!!.adapter = adapter2
        tv_mapRelated.setOnClickListener(View.OnClickListener {
            if (isMapPressed) {
                isMapPressed = false
                recycleView.visibility = View.GONE
            } else {
                isMapPressed = true
                recycleView.visibility = View.VISIBLE
                recycleView2.visibility = View.GONE
            }
        })
        tv_addReplacedMore.setOnClickListener(View.OnClickListener {
            if (isMoreClicked) {
                isMoreClicked = false
                recycleView2.visibility = View.GONE
            } else {
                isMoreClicked = true
                recycleView2.visibility = View.VISIBLE
                recycleView.visibility = View.GONE
            }
        })
        tv_cancel.setOnClickListener(View.OnClickListener {
            lin_moreReplaced.visibility = View.GONE
        })
        edt_replace_product.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var replaceMappedSort = JSONArray()
                var replaceMoreSort = JSONArray()
                val textlength = edt_replace_product!!.text.length
                for (k in 0 until jsonArrayMapped.length()) {
                    val jsonObject = jsonArrayMapped.getJSONObject(k)
                    if (textlength <= jsonObject.getString("ID_OLD_Product").length) {
                        if (jsonObject.getString("ID_OLD_Product")!!.toLowerCase().trim().contains(
                                edt_replace_product!!.text.toString().toLowerCase().trim()
                            )
                        ) {
                            replaceMappedSort.put(jsonObject)
                        }
                    }
                }
                var replacedProductCostArrayList23 = ArrayList<ReplacedProductCostModel>()
                for (i in 0 until replaceMappedSort.length()) {
                    val obj = replaceMappedSort.getJSONObject(i)
                    replacedProductCostArrayList23.add(
                        i,
                        ReplacedProductCostModel(
                            obj!!.getString("ID_OLD_Product"),
                            obj!!.getString("OLD_Product"),
                            obj!!.getString("SPDOldQuantity"),
                            obj!!.getString("Amount"),
                            obj!!.getString("ReplaceAmount"),
                            obj!!.getString("Remarks"),
                            obj!!.getString("ID_Product"),
                            obj!!.getString("Product"),
                            ""
                        )
                    )
                }

                for (k in 0 until jsonArrayMore.length()) {
                    val jsonObject = jsonArrayMore.getJSONObject(k)
                    if (textlength <= jsonObject.getString("ID_Product").length) {
                        if (jsonObject.getString("ID_Product")!!.toLowerCase().trim().contains(
                                edt_replace_product!!.text.toString().toLowerCase().trim()
                            )
                        ) {
                            replaceMoreSort.put(jsonObject)
                        }
                    }
                }
                var replacedProductCostArrayList24 = ArrayList<MoreReplacedProductCostModel>()
                for (i in 0 until replaceMoreSort.length()) {
                    val obj = replaceMoreSort.getJSONObject(i)
                    replacedProductCostArrayList24.add(
                        i,
                        MoreReplacedProductCostModel(
                            obj!!.getString("ID_Product"),
                            obj!!.getString("Code"),
                            obj!!.getString("Name"),
                            obj!!.getString("MRPs"),
                            obj!!.getString("MRP1R"),
                            obj!!.getString("SalesPrice1R"),
                            obj!!.getString("SalePrice"),
                            obj!!.getString("CurrentStock1R"),
                            obj!!.getString("StockId"),
                            obj!!.getString("TaxAmount"),
                            obj!!.getString("StandbyStock"),
                            obj!!.getString("TotalCount"),
                            ""
                        )
                    )
                }
                var adapter: ReplacedProductCostAdapterSecondary =
                    ReplacedProductCostAdapterSecondary(
                        this@ServiceFollowUpActivity,
                        replaceMappedSort,
                        replacedProductCostArrayList23
                    )
                recycleView!!.adapter = adapter
                var adapter2: ReplacedProductCostAdapterThird =
                    ReplacedProductCostAdapterThird(
                        this@ServiceFollowUpActivity,
                        replaceMoreSort,
                        replacedProductCostArrayList24
                    )
                recycleView2!!.adapter = adapter2
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        tv_submit.setOnClickListener(View.OnClickListener {

            var replacedProductCostArrayListFinal1: ArrayList<ReplacedProductCostModel> =
                adapter1!!.getReplaceProductCost()
            var replacedProductCostArrayListFinal2: ArrayList<MoreReplacedProductCostModel> =
                adapter2!!.getReplaceProductCost()
            var sumOfArray =
                replacedProductCostArrayListFinal1.size + replacedProductCostArrayListFinal2.size
            jsonArrayReplacedProductMap = adapter1!!.getReplaceProductCostJson()
            jsonArrayReplacedProductMore = adapter2!!.getReplaceProductCostJson()
            replacedProductCostArrayList.clear()
            Log.v("adadaad3333", "s1 " + replacedProductCostArrayListFinal1.size)
            Log.v("adadaad3333", "s2 " + replacedProductCostArrayListFinal2.size)
            replacedProductCostArrayListFinal.clear()
            for (item in replacedProductCostArrayListFinal1) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.ID_OLD_Product;
                Log.v("dsfsdf333rfff", "value " + value)
                for (item2 in replacedProductCostArrayListFinal) {
                    var value2 = item2.id;
                    Log.v("dsfsdf333rfff", "value2 " + value2)
                    if (value == value2) {
                        present = 1
                        i2 = i
                        break
                    } else {
                        present = 0
                    }
                    Log.v("dsfsdf333rfff", "i " + i)
                    i = i + 1
                }
                if (present == 1) {
                    replacedProductCostArrayListFinal.removeAt(i2)
                    var replacedProductCostModelFinal = ReplacedProductCostModelFinal(
                        item.ID_OLD_Product,
                        item.OLD_Product,
                        item.SPDOldQuantity,
                        "",
                        "",
                        item.Product,
                        "",
                        item.ReplaceAmount,
                        "",
                        ""
                    )
                    replacedProductCostArrayListFinal.add(replacedProductCostModelFinal)
//                    replacedProductCostArrayList.removeAt(i2)
//                    replacedProductCostArrayList.add(item)
                } else {
                    var replacedProductCostModelFinal = ReplacedProductCostModelFinal(
                        item.ID_OLD_Product,
                        item.OLD_Product,
                        item.SPDOldQuantity,
                        "",
                        "",
                        item.Product,
                        "",
                        item.ReplaceAmount,
                        "",
                        ""
                    )
                    replacedProductCostArrayListFinal.add(replacedProductCostModelFinal)
//                    replacedProductCostArrayList.add(item)
                }
            }
            for (item in replacedProductCostArrayListFinal2) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.ID_Product;
                Log.v("sdasd3ertggfgf", "value " + value)
//                Log.v("dfsdfdsfdeee","checked "+item.isChecked);
                for (item2 in replacedProductCostArrayListFinal) {
                    var value2 = item2.id;
                    Log.v("sdasd3ertggfgf", "value2 " + value2)
                    if (value == value2) {
                        present = 1
                        i2 = i
                        break
                    } else {
                        present = 0
                    }
                    i = i + 1
                }
                if (present == 1) {
                    replacedProductCostArrayListFinal.removeAt(i2)
                    var replacedProductCostModelFinal = ReplacedProductCostModelFinal(
                        item.ID_Product,
                        item.Name,
                        item.TotalCount,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        item.isChecked
                    )
                    replacedProductCostArrayListFinal.add(replacedProductCostModelFinal)

//                    replacedProductCostArrayList.removeAt(i2)
//                    replacedProductCostArrayList.add(item)
                } else {
                    var replacedProductCostModelFinal = ReplacedProductCostModelFinal(
                        item.ID_Product,
                        item.Name,
                        item.TotalCount,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        item.isChecked
                    )
                    replacedProductCostArrayListFinal.add(replacedProductCostModelFinal)
                }

            }
            val gson = Gson()
            val listString = gson.toJson(
                replacedProductCostArrayListFinal,
                object : TypeToken<ArrayList<ReplacedProductCostModelFinal?>?>() {}.type
            )
            var jsonArray = JSONArray(listString)
            lin_moreReplaced.visibility = View.GONE

            setreplacedProductCostRecycler(jsonArray)
            // }

        })
    }

    private fun hideViews() {

        if (ticketDetailsMode.equals("1")) {
            lin_ticketDetails!!.visibility = View.GONE
        }
        if (CustMode.equals("1")) {
            lin_CustClick!!.visibility = View.GONE
        }
        if (productMode.equals("1")) {
            lin_ProductDetails!!.visibility = View.GONE
        }

    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Service Cost"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Replaced Product Cost"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Attendance"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Summary"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("History"))
        //setting first layouts
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        tv_moreServices!!.visibility = View.VISIBLE
        tv_moreComponents!!.visibility = View.GONE
        lin_service_cost!!.visibility = View.VISIBLE
        lin_replacePoductCost!!.visibility = View.GONE
        lin_attendance!!.visibility = View.GONE
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    //service cost
                    tv_moreServices!!.visibility = View.VISIBLE
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.VISIBLE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.VISIBLE

                }
                if (tab.position == 1) {
                    //replaced product cost
                    tv_moreServices!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.VISIBLE
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.VISIBLE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.VISIBLE
                }
                if (tab.position == 2) {
                    //attendance
                    tv_moreServices!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.VISIBLE
                    lin_bottom_menu!!.visibility = View.VISIBLE

                }
                if (tab.position == 3) {
                    //summary
                    tv_moreServices!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.VISIBLE

                }
                if (tab.position == 4) {
                    //history
                    tv_moreServices!!.visibility = View.GONE
                    tv_moreComponents!!.visibility = View.GONE
                    lin_service_cost!!.visibility = View.GONE
                    lin_replacePoductCost!!.visibility = View.GONE
                    lin_attendance!!.visibility = View.GONE
                    lin_bottom_menu!!.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    /*private fun loadServiceCost() {

        val serviceCostArrayList = ArrayList<ServiceCostModelMain>()
        val e1 =
            ServiceCostModelMain("Battery Check", "Battery Check2", "55555", "remark1", "true")
        val e2 = ServiceCostModelMain(
            "Prevention Services",
            "Prevention Services2",
            "350",
            "remark2",
            "true"
        )
        val e3 = ServiceCostModelMain("Service Test", "Service Test2", "333", "remark3", "false")
        serviceCostArrayList.add(e1)
//        serviceCostArrayList.add(e2)
//        serviceCostArrayList.add(e3)
        val gson = Gson()
        val listString = gson.toJson(
            serviceCostArrayList,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        jsonArrayMappedServiceCost = JSONArray(listString)
        setServiceCostRecycler(jsonArrayMappedServiceCost)
    }

    private fun loadReplacedProductCost() {
        val replacedProductCostArrayList = ArrayList<ReplacedProductCostModel>()
        val e1 = ReplacedProductCostModel(
            "Solar Light",
            "250",
            "25",
            "PickUp",
            "120",
            "Remark1",
            "false"
        )
        val e2 = ReplacedProductCostModel(
            "UPS - 600 VA",
            "315",
            "2",
            "Replace",
            "15",
            "Remark2",
            "true"
        )
        replacedProductCostArrayList.add(e1)
        replacedProductCostArrayList.add(e2)
        val gson = Gson()
        val listString = gson.toJson(
            replacedProductCostArrayList,
            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
        )
        jsonArray = JSONArray(listString)


        setreplacedProductCostRecycler(jsonArray)

    }*/

//    private fun loadAttendance() {
//        val attendanceFollowUpArrayList = ArrayList<AttendanceFollowUpModel>()
//        val e1 = AttendanceFollowUpModel("David", "Customer service ", "Employee", "false")
//        val e2 = AttendanceFollowUpModel("John", "Development", "Manager", "false")
//        val e3 = AttendanceFollowUpModel("Kiran", "Customer service ", "Employee", "false")
//        attendanceFollowUpArrayList.add(e1)
//        attendanceFollowUpArrayList.add(e2)
//        attendanceFollowUpArrayList.add(e3)
//        val gson = Gson()
//        val listString = gson.toJson(
//            attendanceFollowUpArrayList,
//            object : TypeToken<ArrayList<AttendanceFollowUpModel?>?>() {}.type
//        )
//        jsonArray = JSONArray(listString)
//        setAttendancetRecycler(jsonArray)
//    }

    private fun setServiceCostRecycler(jsonArray: JSONArray) {
        if (jsonArray.length() > 0) {
            serviceCostArrayList.clear()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                serviceCostArrayList.add(
                    i,
                    ServiceCostModelMain(
                        obj!!.getString("Components"),
                        obj!!.getString("ID_ProductWiseServiceDetails"),
                        obj!!.getString("SubProduct"),
                        obj!!.getString("ID_Product"),
                        obj!!.getString("ID_Services"),
                        obj!!.getString("Service"),
                        obj!!.getString("ServiceCost"),
                        obj!!.getString("ServiceTaxAmount"),
                        obj!!.getString("ServiceNetAmount"),
                        obj!!.getString("Remarks"),
                        obj!!.getString("isChecked"),
                        obj!!.getString("serviceType")
                    )
                )
            }
            isServiceDataSet = true
            tv_moreServices?.visibility = View.VISIBLE
            lin_add_service?.visibility = View.GONE
            recycler_service_cost.visibility = View.VISIBLE
            recycler_service_cost!!.setLayoutManager(
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
            adapter =
                ServiceCostAdapter(this, jsonArray, serviceCostArrayList, jsonArrayServiceType)
            recycler_service_cost!!.adapter = adapter
        } else {
            isServiceDataSet = false
            tv_moreServices?.visibility = View.GONE
            recycler_service_cost.visibility = View.GONE
            lin_add_service?.visibility = View.VISIBLE
        }
    }

    private fun setreplacedProductCostRecycler(jsonArray: JSONArray) {
        if (jsonArray.length() > 0) {
            replacedProductCostArrayListFinal.clear()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                replacedProductCostArrayListFinal.add(
                    i,
                    ReplacedProductCostModelFinal(
                        obj!!.getString("id"),
                        obj!!.getString("Component"),
                        obj!!.getString("Quantity"),
                        obj!!.getString("changeMode"),
                        obj!!.getString("buyBackAmount"),
                        obj!!.getString("replacedProduct"),
                        obj!!.getString("replacedQuantity"),
                        obj!!.getString("remarks"),
                        obj!!.getString("replacedAmount"),
                        ""
                    )
                )
            }
            isReplaceDataSet = true
            tv_moreComponents?.visibility = View.VISIBLE
            lin_add_replaced_product?.visibility = View.GONE
            recycleView_replaceproduct.visibility = View.VISIBLE
            recycleView_replaceproduct!!.setLayoutManager(
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
            adapterReplacedProductCost =
                ReplacedProductCostAdapter(this, jsonArray, replacedProductCostArrayListFinal,jsonArrayChangeMode)
            recycleView_replaceproduct!!.adapter = adapterReplacedProductCost
        } else {
            isReplaceDataSet = false
            tv_moreComponents?.visibility = View.GONE
            recycleView_replaceproduct.visibility = View.GONE
            lin_add_replaced_product?.visibility = View.VISIBLE
        }
    }

    private fun setAttendancetRecycler(jsonArray: JSONArray) {

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            attendanceFollowUpArrayList.add(
                i,
                AttendanceFollowUpModel(
                    obj!!.getString("ID_Employee"),
                    obj!!.getString("EmployeeName"),
                    obj!!.getString("ID_CSAEmployeeType"),
                    obj!!.getString("Attend"),
                    obj!!.getString("DepartmentID"),
                    obj!!.getString("Department"),
                    obj!!.getString("Role"),
                    obj!!.getString("Designation"),
                    "false"
                )
            )
        }

        recyclerAttendance.setLayoutManager(GridLayoutManager(this, 2))
        adapterAttendanceServiceFollowUp =
            AttendanceServiceFollowUpAdapter(this, jsonArray, attendanceFollowUpArrayList)
        recyclerAttendance!!.adapter = adapterAttendanceServiceFollowUp

    }

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
        view.visibility = View.GONE
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
                this@ServiceFollowUpActivity,
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

    private fun startStopWork(latitude: Double, longitude: Double, address: String?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        val current = LocalDateTime.now().format(formatter)
        Log.v("dfsdfds34343f", "current " + current)

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

    private fun loadAttendance() {
        recyclerAttendance!!.adapter = null
        serviceFollowUpAttendanceListViewModel =
            ViewModelProvider(this).get(ServiceFollowUpAttendanceListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpAttendanceListViewModel.getServiceFollowUpAttendance(
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
                                if (serviceFollowUpAttendance == 0) {
                                    serviceFollowUpAttendance++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("Attendancedetails")
                                        serviceFollowUpAttendanceArrayList =
                                            jobjt.getJSONArray("AttendancedetailsList")
                                        setAttendancetRecycler(serviceFollowUpAttendanceArrayList)
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
                                //swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
//                            swipeRefreshLayout.visibility = View.GONE
//                            swipeRefreshLayout.isRefreshing = false
//                            tv_nodata.visibility = View.VISIBLE
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

    private fun loadMappedeServiceAttended() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpMappedServiceViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMappedServiceViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                serviceFollowUpMappedServiceViewModel.getServiceFollowUpMappedService(
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
                                Log.v("sdadasd3rffdfd", "msg")
                                if (serviceFollowUpMappedService == 0) {
                                    serviceFollowUpMappedService++
                                    Log.v("sdadasd3rffdfd", "det")
                                    val jObject = JSONObject(msg)
                                    Log.v(
                                        "sdadasd3rffdfd",
                                        "st code " + jObject.getString("StatusCode")
                                    )
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ServiceAttendedDetails")
                                        jsonArrayMappedServiceAttended =
                                            jobjt.getJSONArray("ServiceAttendedDetailsList")
                                        Log.v(
                                            "sdadasd3rffdfd",
                                            "AttendancedetailsList= " + jsonArrayMappedServiceAttended.toString()
                                        )
                                    } else {
//
                                    }

                                }


                            } else {
                            }
                        } catch (e: Exception) {
                            Log.v("sdadasd3rffdfd", "ex3 " + e)
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

    private fun loadMappedReplacedProducts() {
        serviceFollowUpMappedReplacedProductViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMappedReplacedProductViewModel::class.java)
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
                                if (serviceFollowUpMappedReplacedProduct == 0) {
                                    serviceFollowUpMappedReplacedProduct++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ReplaceProductdetails")
                                        jsonArrayReplacedProductMap =
                                            jobjt.getJSONArray("ReplaceProductdetailsList")
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

    private fun loadMoreServiceAttended() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpMoreServiceViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpMoreServiceViewModel.getServiceFollowUpMoreService(
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
                                if (serviceFollowUpMoreService == 0) {
                                    serviceFollowUpMoreService++
                                    val jObject = JSONObject(msg)
                                    Log.v(
                                        "dfdsf33fff",
                                        "st code " + jObject.getString("StatusCode")
                                    )
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("AddedService")
                                        jsonArrayMoreServiceAttended =
                                            jobjt.getJSONArray("AddedServiceList")
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

    private fun loadServiceType() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpServiceTypeViewModel =
            ViewModelProvider(this).get(ServiceFollowUpServiceTypeViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
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

    private fun loadChangeMode() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpChangeModeViewModel =
            ViewModelProvider(this).get(ServiceFollowUpChangeModeViewModel::class.java)
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

    private fun loadMoreReplacedProducts() {
        //recyclerAttendance!!.adapter = null
        serviceFollowUpMoreReplacedProductsViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMoreReplacedProductsViewModel::class.java)
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
                                        val jobjt =
                                            jObject.getJSONObject("PopUpProductdetails")
                                        jsonArrayReplacedProductMore =
                                            jobjt.getJSONArray("PopUpProductdetailsList")
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

    private fun loadInfo(customerServiceRegister: String) {
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
                                Log.v("fsfsfds", "msg")
                                if (serviceFollowUpInfo == 0) {
                                    serviceFollowUpInfo++
                                    Log.v("fsfsfds", "det")
                                    val jObject = JSONObject(msg)
                                    Log.v("dfsfrffff", "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("EmployeeWiseTicketSelect")
                                        FK_CustomerOthers = jobjt.getString("FK_CustomerOthers")
                                        FK_Customer = jobjt.getString("FK_Customer")
                                        FK_Product = jobjt.getString("FK_Product")
                                        Log.v(
                                            "dfsfsdfsdfd",
                                            "FK_CustomerOthers " + FK_CustomerOthers
                                        );
                                        Log.v("dfsfsdfsdfd", "FK_Customer " + FK_Customer);
                                    } else {

                                    }

                                }


                            } else {

                            }
                        } catch (e: Exception) {

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

}