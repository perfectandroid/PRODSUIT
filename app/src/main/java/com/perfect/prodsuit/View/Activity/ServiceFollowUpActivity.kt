package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
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
import org.json.JSONArray
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
    private var tabLayout: TabLayout? = null
    lateinit var jsonArray: JSONArray
    lateinit var jsonArray2: JSONArray
    lateinit var jsonArrayMappedServiceCost: JSONArray
    lateinit var jsonArrayMoreServiceCost: JSONArray
    lateinit var jsonArrayReplacedProductMap: JSONArray
    lateinit var jsonArrayReplacedProductMore: JSONArray
    var adapter: ServiceCostAdapter? = null
    var adapterReplacedProductCost: ReplacedProductCostAdapter? = null
    var adapterAttendanceServiceFollowUp: AttendanceServiceFollowUpAdapter? = null
    var imgBack: ImageView? = null
    var img_record: ImageView? = null
    val serviceCostArrayList = ArrayList<ServiceCostModelMain>()
    val replacedProductCostArrayList = ArrayList<ReplacedProductCostModel>()
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
        getSupportActionBar()?.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.shape_header))
        getSupportActionBar()?.setCustomView(R.layout.toolbar)
        getSupportActionBar()?.setDisplayShowCustomEnabled(true)
        runningStatus=intent.getStringExtra("runningStatus")
        if(runningStatus.equals("0"))
        {
            card_start?.visibility=View.VISIBLE
            card_stop?.visibility=View.GONE
            card_restart?.visibility=View.GONE
        }
        else if(runningStatus.equals("1"))
        {
            card_start?.visibility=View.GONE
            card_stop?.visibility=View.VISIBLE
            card_restart?.visibility=View.GONE
        }
        else
        {
            card_start?.visibility=View.GONE
            card_stop?.visibility=View.GONE
            card_restart?.visibility=View.VISIBLE
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
        loadArrays()
        //loadServiceCost()
        //loadReplacedProductCost()
        loadAttendance()
        // loadHistory()
        adapter = ServiceCostAdapter(this, jsonArray, serviceCostArrayList)
        recycler_service_cost!!.adapter = adapter
        adapterReplacedProductCost =
            ReplacedProductCostAdapter(this, jsonArray, replacedProductCostArrayList)
        recycleView_replaceproduct!!.adapter = adapterReplacedProductCost
        adapterAttendanceServiceFollowUp =
            AttendanceServiceFollowUpAdapter(this, jsonArray, attendanceFollowUpArrayList)
        recyclerAttendance!!.adapter = adapterAttendanceServiceFollowUp

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            finish()
        }
        if (item.getItemId() === R.id.history) {
            val intent = Intent(this, ServiceFollowUpHistory::class.java)
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
        val serviceCostArrayListMapped = ArrayList<ServiceCostModelMain>()
        val e11 =
            ServiceCostModelMain("Battery Check", "Battery Check2", "55555", "Paid Service","","","remark1", "false")
        val e21 = ServiceCostModelMain(
            "Prevention Services",
            "Prevention Services2",
            "350",
            "Paid Service","","",
            "remark2",
            "false"
        )
        val e31 = ServiceCostModelMain("Service Test3", "Service Test3", "1122","Paid Service","","", "remark3", "false")
        val e41 = ServiceCostModelMain("Service Test4", "Service Test4", "7867","Paid Service","","", "remark3", "false")
        val e51 = ServiceCostModelMain("Service Test5", "Service Test5", "4444","AMC Service","","", "remark3", "false")
        serviceCostArrayListMapped.add(e11)
        serviceCostArrayListMapped.add(e21)
        serviceCostArrayListMapped.add(e41)
        serviceCostArrayListMapped.add(e31)
        serviceCostArrayListMapped.add(e51)
        val gson1 = Gson()
        val listString1 = gson1.toJson(
            serviceCostArrayListMapped,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        jsonArrayMappedServiceCost = JSONArray(listString1)

        val serviceCostArrayListMore = ArrayList<ServiceCostModelMain>()
        val e2 = ServiceCostModelMain(
            "Prevention Services",
            "Prevention Services2",
            "888",
            "AMC Service","","",
            "remark2",
            "false"
        )
        val e3 =
            ServiceCostModelMain(
                "Prevention Services",
                "Prevention Services3",
                "2553",
                "AMC Service","","",
                "remark3",
                "false"
            )
        val e4 =
            ServiceCostModelMain(
                "Service Testing",
                "Prevention Services6",
                "433",
                "AMC Service","","",
                "remark3",
                "false"
            )
        val e5 =
            ServiceCostModelMain(
                "Service Testing",
                "Prevention Services5",
                "7777",
                "AMC Service","","",
                "remark3",
                "false"
            )
        val e6 =
            ServiceCostModelMain(
                "Service Testing",
                "Prevention Services4",
                "6766",
                "AMC Service","","",
                "remark3",
                "false"
            )
        serviceCostArrayListMore.add(e2)
        serviceCostArrayListMore.add(e3)
        serviceCostArrayListMore.add(e4)
        serviceCostArrayListMore.add(e5)
        serviceCostArrayListMore.add(e6)
        val gson = Gson()
        val listString = gson.toJson(
            serviceCostArrayListMore,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        jsonArrayMoreServiceCost = JSONArray(listString)

        val replacedProductCostArrayList = ArrayList<ReplacedProductCostModel>()
        val e122 = ReplacedProductCostModel(
            "Solar Light",
            "250",
            "25",
            "PickUp",
            "120",
            "",
            "Remark1",
            "false"
        )
        val e222 = ReplacedProductCostModel(
            "UPSS - 600 VA",
            "315",
            "2",
            "Replace",
            "15",
            "",
            "Remark2",
            "false"
        )
        replacedProductCostArrayList.add(e122)
        replacedProductCostArrayList.add(e222)
        val gson22 = Gson()
        val listString22 = gson22.toJson(
            replacedProductCostArrayList,
            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
        )
        jsonArrayReplacedProductMap = JSONArray(listString22)

        val replacedProductCostArrayList22 = ArrayList<ReplacedProductCostModel>()
        val e133 = ReplacedProductCostModel(
            "Solar Light",
            "250",
            "25",
            "PickUp",
            "120",
            "",
            "Remark1",
            "false"
        )
        val e233 = ReplacedProductCostModel(
            "UPS - 600 VA",
            "315",
            "2",
            "Replace",
            "15",
            "",
            "Remark2",
            "false"
        )
        val e333 = ReplacedProductCostModel(
            "UPBS - 600 VA",
            "5454",
            "23",
            "Replace",
            "153",
            "",
            "Remark3",
            "false"
        )
        replacedProductCostArrayList22.add(e133)
        replacedProductCostArrayList22.add(e233)
        replacedProductCostArrayList22.add(e333)
        val gson33 = Gson()
        val listString33 = gson33.toJson(
            replacedProductCostArrayList22,
            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
        )
        jsonArrayReplacedProductMore = JSONArray(listString33)

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
                    jsonArrayMappedServiceCost,
                    jsonArrayMoreServiceCost
                )
            }
            R.id.tv_moreServices -> {
                openAlertDialogForMoreServices(
                    jsonArrayMappedServiceCost,
                    jsonArrayMoreServiceCost
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
        var replacedProductCostArrayList: ArrayList<ReplacedProductCostModel> =
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
            serviceCostArrayListMapped.add(
                i,
                ServiceCostModelMain(
                    obj!!.getString("Components"),
                    obj!!.getString("ServiceName"),
                    obj!!.getString("serviceCost"),
                    obj!!.getString("serviceType"),
                    obj!!.getString("taxAmount"),
                    obj!!.getString("netAmount"),
                    obj!!.getString("remark"),
                    obj!!.getString("isChecked")
                )
            )
        }
        val serviceCostArrayListMore = ArrayList<ServiceCostModelMain>()
        for (i in 0 until jsonArrayMore.length()) {
            val obj = jsonArrayMore.getJSONObject(i)
            serviceCostArrayListMore.add(
                i,
                ServiceCostModelMain(
                    obj!!.getString("Components"),
                    obj!!.getString("ServiceName"),
                    obj!!.getString("serviceCost"),
                    obj!!.getString("serviceType"),
                    obj!!.getString("taxAmount"),
                    obj!!.getString("netAmount"),
                    obj!!.getString("remark"),
                    obj!!.getString("isChecked")
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
                    if (textlength <= jsonObject.getString("ServiceName").length) {
                        if (jsonObject.getString("ServiceName")!!.toLowerCase().trim().contains(
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
                            obj!!.getString("Components"),
                            obj!!.getString("ServiceName"),
                            obj!!.getString("serviceCost"),
                            obj!!.getString("serviceType"),
                            obj!!.getString("taxAmount"),
                            obj!!.getString("netAmount"),
                            obj!!.getString("remark"),
                            obj!!.getString("isChecked")
                        )
                    )
                }

                for (k in 0 until jsonArrayMore.length()) {
                    val jsonObject = jsonArrayMore.getJSONObject(k)
                    if (textlength <= jsonObject.getString("ServiceName").length) {
                        if (jsonObject.getString("ServiceName")!!.toLowerCase().trim().contains(
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
                            obj!!.getString("Components"),
                            obj!!.getString("ServiceName"),
                            obj!!.getString("serviceCost"),
                            obj!!.getString("serviceType"),
                            obj!!.getString("taxAmount"),
                            obj!!.getString("netAmount"),
                            obj!!.getString("remark"),
                            obj!!.getString("isChecked")
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
            jsonArrayMappedServiceCost = adapter!!.getServiceCostJson()
            jsonArrayMoreServiceCost = adapter2!!.getServiceCostJson()
            serviceCostArrayList.clear()
            for (item in serviceCostArrayListFinal) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.ServiceName;
                Log.v("Check_Duplicate_values", "value " + value)
                for (item2 in serviceCostArrayList) {
                    var value2 = item2.ServiceName;
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
                var value = item.ServiceName;
                Log.v("Check_Duplicate_values", "value " + value)
                for (item2 in serviceCostArrayList) {
                    var value2 = item2.ServiceName;
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
            replacedProductCostArrayList1.add(
                i,
                ReplacedProductCostModel(
                    obj!!.getString("components"),
                    obj!!.getString("amount"),
                    obj!!.getString("quantity"),
                    obj!!.getString("changeMode"),
                    obj!!.getString("buyBackAmount"),
                    obj!!.getString("product"),
                    obj!!.getString("remark"),
                    obj!!.getString("isChecked")
                )
            )
        }
        var replacedProductCostArrayList2 = ArrayList<ReplacedProductCostModel>()
        for (i in 0 until jsonArrayMore.length()) {
            val obj = jsonArrayMore.getJSONObject(i)
            replacedProductCostArrayList2.add(
                i,
                ReplacedProductCostModel(
                    obj!!.getString("components"),
                    obj!!.getString("amount"),
                    obj!!.getString("quantity"),
                    obj!!.getString("changeMode"),
                    obj!!.getString("buyBackAmount"),
                    obj!!.getString("product"),
                    obj!!.getString("remark"),
                    obj!!.getString("isChecked")
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
                    if (textlength <= jsonObject.getString("components").length) {
                        if (jsonObject.getString("components")!!.toLowerCase().trim().contains(
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
                            obj!!.getString("components"),
                            obj!!.getString("amount"),
                            obj!!.getString("quantity"),
                            obj!!.getString("changeMode"),
                            obj!!.getString("buyBackAmount"),
                            obj!!.getString("product"),
                            obj!!.getString("remark"),
                            obj!!.getString("isChecked")
                        )
                    )
                }

                for (k in 0 until jsonArrayMore.length()) {
                    val jsonObject = jsonArrayMore.getJSONObject(k)
                    if (textlength <= jsonObject.getString("components").length) {
                        if (jsonObject.getString("components")!!.toLowerCase().trim().contains(
                                edt_replace_product!!.text.toString().toLowerCase().trim()
                            )
                        ) {
                            replaceMoreSort.put(jsonObject)
                        }
                    }
                }
                var replacedProductCostArrayList24 = ArrayList<ReplacedProductCostModel>()
                for (i in 0 until replaceMoreSort.length()) {
                    val obj = replaceMoreSort.getJSONObject(i)
                    replacedProductCostArrayList24.add(
                        i,
                        ReplacedProductCostModel(
                            obj!!.getString("components"),
                            obj!!.getString("amount"),
                            obj!!.getString("quantity"),
                            obj!!.getString("changeMode"),
                            obj!!.getString("buyBackAmount"),
                            obj!!.getString("product"),
                            obj!!.getString("remark"),
                            obj!!.getString("isChecked")
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
            var replacedProductCostArrayListFinal2: ArrayList<ReplacedProductCostModel> =
                adapter2!!.getReplaceProductCost()
            var sumOfArray =
                replacedProductCostArrayListFinal1.size + replacedProductCostArrayListFinal2.size
//            if (sumOfArray == 0) {
//                Config.snackBars(this, it, "Please check any component from list")
//            } else {
            jsonArrayReplacedProductMap = adapter1!!.getReplaceProductCostJson()
            jsonArrayReplacedProductMore = adapter2!!.getReplaceProductCostJson()
            replacedProductCostArrayList.clear()
            Log.v("adadaad3333", "s1 " + replacedProductCostArrayListFinal1.size)
            Log.v("adadaad3333", "s2 " + replacedProductCostArrayListFinal2.size)
            for (item in replacedProductCostArrayListFinal1) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.components;
                Log.v("dsfsdf333rfff", "value " + value)
                for (item2 in replacedProductCostArrayList) {
                    var value2 = item2.components;
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
                    Log.v("dsfsdf333rfff", "pres " + i)
                    replacedProductCostArrayList.removeAt(i2)
                    replacedProductCostArrayList.add(item)
                } else {
                    Log.v("dsfsdf333rfff", "abs")
                    replacedProductCostArrayList.add(item)
                }

            }
            Log.v("dsfsdf333rfff", "s1 " + replacedProductCostArrayList.size)
            for (item in replacedProductCostArrayListFinal2) {
                var i = 0
                var i2 = 0
                var present = 0
                var value = item.components;
                Log.v("dfgdfg444rr", "value " + value)
                for (item2 in replacedProductCostArrayList) {
                    var value2 = item2.components;
                    Log.v("dfgdfg444rr", "value2 " + value2)
                    if (value == value2) {
                        present = 1
                        i2 = i
                        break
                    } else {
                        present = 0
                    }

                    Log.v("dfgdfg444rr", "i " + i)
                    i = i + 1
                }
                if (present == 1) {
                    Log.v("dfgdfg444rr", "pres " + i)
                    replacedProductCostArrayList.removeAt(i2)
                    replacedProductCostArrayList.add(item)
                } else {
                    Log.v("dfgdfg444rr", "abs")
                    replacedProductCostArrayList.add(item)
                }

            }

            Log.v("dfgdfg444rr", "siZe--" + replacedProductCostArrayList.size)
            // serviceCostArrayList1= (serviceCostArrayList+serviceCostArrayListFinal) as ArrayList<ServiceCostModelMain>
            val gson = Gson()
            val listString = gson.toJson(
                replacedProductCostArrayList,
                object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
            )
            var jsonArray = JSONArray(listString)
            lin_moreReplaced.visibility = View.GONE

            setreplacedProductCostRecycler(jsonArray)
            // }

        })
    }

    /*  private fun openAlertDialogForMoreServices(
          jsonArrayMapped: JSONArray,
          jsonArrayMore: JSONArray
      ) {
          val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
          val inflater = this.layoutInflater
          val dialogView: View = inflater.inflate(R.layout.alert_more_services, null)
          dialogBuilder.setView(dialogView)
          val alertDialog = dialogBuilder.create()
          var tv_addServices: TextView = dialogView.findViewById<TextView>(R.id.tv_addServices)
          var tv_mapServices: TextView = dialogView.findViewById<TextView>(R.id.tv_mapServices)
          var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
          var tv_submit: TextView = dialogView.findViewById<TextView>(R.id.tv_submit)
          var recycleView: RecyclerView = dialogView.findViewById(R.id.recycleView)
          var recycleView2: RecyclerView = dialogView.findViewById(R.id.recycleView2)
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
              serviceCostArrayListMapped.add(
                  i,
                  ServiceCostModelMain(
                      obj!!.getString("Components"),
                      obj!!.getString("ServiceName"),
                      obj!!.getString("serviceCost"),
                      obj!!.getString("remark"),
                      obj!!.getString("isChecked")
                  )
              )
          }
          val serviceCostArrayListMore = ArrayList<ServiceCostModelMain>()
          for (i in 0 until jsonArrayMore.length()) {
              val obj = jsonArrayMore.getJSONObject(i)
              serviceCostArrayListMore.add(
                  i,
                  ServiceCostModelMain(
                      obj!!.getString("Components"),
                      obj!!.getString("ServiceName"),
                      obj!!.getString("serviceCost"),
                      obj!!.getString("remark"),
                      obj!!.getString("isChecked")
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
              if(isMapPressed) {
                  isMapPressed=false
                  recycleView.visibility = View.GONE
              }
              else
              {
                  isMapPressed=true
                  recycleView.visibility = View.VISIBLE
                  recycleView2.visibility = View.GONE
              }
          })
          tv_addServices.setOnClickListener(View.OnClickListener {
              if(isMoreClicked) {
                  isMoreClicked=false
                  recycleView2.visibility = View.GONE
              }
              else
              {
                  isMoreClicked=true
                  recycleView2.visibility = View.VISIBLE
                  recycleView.visibility = View.GONE
              }
          })
          tv_cancel.setOnClickListener(View.OnClickListener {
              alertDialog.dismiss()
          })
          tv_submit.setOnClickListener(View.OnClickListener {
              var serviceCostArrayListFinal: ArrayList<ServiceCostModelMain> =
                  adapter!!.getServiceCost()
              var serviceCostArrayListFinal2: ArrayList<ServiceCostModelMain> =
                  adapter2!!.getServiceCost()
              var sumOfArray = serviceCostArrayListFinal.size + serviceCostArrayListFinal2.size
              if (sumOfArray == 0) {
                  Config.snackBars(this, dialogView, "Please check any service from list")
              } else {
                  jsonArrayMappedServiceCost=adapter!!.getServiceCostJson()
                  jsonArrayMoreServiceCost=adapter2!!.getServiceCostJson()
                  serviceCostArrayList.clear()
                  for (item in serviceCostArrayListFinal) {
                      var i = 0
                      var i2 = 0
                      var present = 0
                      var value = item.ServiceName;
                      Log.v("Check_Duplicate_values", "value " + value)
                      for (item2 in serviceCostArrayList) {
                          var value2 = item2.ServiceName;
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
                      var value = item.ServiceName;
                      Log.v("Check_Duplicate_values", "value " + value)
                      for (item2 in serviceCostArrayList) {
                          var value2 = item2.ServiceName;
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
                  alertDialog.dismiss()
              }

          })
          val window: Window? = alertDialog.window
          window?.setBackgroundDrawableResource(android.R.color.transparent)
          alertDialog.setCancelable(false)
          alertDialog.show()
          window?.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
      }*/

    /* private fun openAlertDialogForMoreComponents(jsonArray: JSONArray) {
         val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
         val inflater = this.layoutInflater
         val dialogView: View = inflater.inflate(R.layout.alert_more_components, null)
         dialogBuilder.setView(dialogView)
         val alertDialog = dialogBuilder.create()
         var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
         var tv_submit: TextView = dialogView.findViewById<TextView>(R.id.tv_submit)
         var recycleView: RecyclerView = dialogView.findViewById(R.id.recycleView)
         recycleView!!.setLayoutManager(
             LinearLayoutManager(
                 this,
                 LinearLayoutManager.VERTICAL,
                 false
             )
         )
         var replacedProductCostArrayList1 = ArrayList<ReplacedProductCostModel>()
         for (i in 0 until jsonArray.length()) {
             val obj = jsonArray.getJSONObject(i)
             replacedProductCostArrayList1.add(
                 i,
                 ReplacedProductCostModel(
                     obj!!.getString("components"),
                     obj!!.getString("amount"),
                     obj!!.getString("quantity"),
                     obj!!.getString("changeMode"),
                     obj!!.getString("buyBackAmount"),
                     obj!!.getString("remark"),
                     obj!!.getString("isChecked")
                 )
             )
         }
         var adapter: ReplacedProductCostAdapterSecondary =
             ReplacedProductCostAdapterSecondary(this, jsonArray, replacedProductCostArrayList1)
         recycleView!!.adapter = adapter

         tv_cancel.setOnClickListener(View.OnClickListener {
             alertDialog.dismiss()
         })

         tv_submit.setOnClickListener(View.OnClickListener {

             var replacedProductCostArrayListFinal: ArrayList<ReplacedProductCostModel> =
                 adapter!!.getReplaceProductCost()
             if (replacedProductCostArrayListFinal.size == 0) {
 //                Toast.makeText(this, "Please check any component from list", Toast.LENGTH_SHORT)
 //                    .show()
                 Config.snackBars(this, dialogView, "Please check any component from list")
             } else {
 //            for (item in replacedProductCostArrayListFinal) {
 //                Log.v("dfasdasdsdsdss", "components " + item.components)
 //                Log.v("dfasdasdsdsdss", "amount " + item.amount)
 //                Log.v("dfasdasdsdsdss", "quantity " + item.quantity)
 //                Log.v("dfasdasdsdsdss", "changeMode " + item.changeMode)
 //                Log.v("dfasdasdsdsdss", "buyBackAmount " + item.buyBackAmount)
 //                Log.v("dfasdasdsdsdss", "remark " + item.remark)
 //                Log.v("dfasdasdsdsdss", "isChecked " + item.isChecked)
 //            }
                 for (item in replacedProductCostArrayListFinal) {
                     var i = 0
                     var i2 = 0
                     var present = 0
                     var value = item.components;
                     Log.v("Check_Duplicate_values", "value " + value)
                     for (item2 in replacedProductCostArrayList) {
                         var value2 = item2.components;
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
                         replacedProductCostArrayList.removeAt(i2)
                         replacedProductCostArrayList.add(item)
                     } else {
                         Log.v("Check_Duplicate_values", "abs")
                         replacedProductCostArrayList.add(item)
                     }

                 }

                 Log.v("Check_Duplicate_values", "siZe--" + serviceCostArrayList.size)
                 // serviceCostArrayList1= (serviceCostArrayList+serviceCostArrayListFinal) as ArrayList<ServiceCostModelMain>
                 val gson = Gson()
                 val listString = gson.toJson(
                     replacedProductCostArrayList,
                     object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
                 )
                 var jsonArray = JSONArray(listString)
                 setreplacedProductCostRecycler(jsonArray)
                 alertDialog.dismiss()
             }

         })

         alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
         alertDialog.setCancelable(false)
         alertDialog.show()
     }*/

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

    private fun loadAttendance() {
        val attendanceFollowUpArrayList = ArrayList<AttendanceFollowUpModel>()
        val e1 = AttendanceFollowUpModel("David", "Customer service ", "Employee", "false")
        val e2 = AttendanceFollowUpModel("John", "Development", "Manager", "false")
        val e3 = AttendanceFollowUpModel("Kiran", "Customer service ", "Employee", "false")
        attendanceFollowUpArrayList.add(e1)
        attendanceFollowUpArrayList.add(e2)
        attendanceFollowUpArrayList.add(e3)
        val gson = Gson()
        val listString = gson.toJson(
            attendanceFollowUpArrayList,
            object : TypeToken<ArrayList<AttendanceFollowUpModel?>?>() {}.type
        )
        jsonArray = JSONArray(listString)
        setAttendancetRecycler(jsonArray)
    }

    private fun setServiceCostRecycler(jsonArray: JSONArray) {
        if (jsonArray.length() > 0) {
            serviceCostArrayList.clear()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                serviceCostArrayList.add(
                    i,
                    ServiceCostModelMain(
                        obj!!.getString("Components"),
                        obj!!.getString("ServiceName"),
                        obj!!.getString("serviceCost"),
                        obj!!.getString("serviceType"),
                        obj!!.getString("taxAmount"),
                        obj!!.getString("netAmount"),
                        obj!!.getString("remark"),
                        obj!!.getString("isChecked")
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
            adapter = ServiceCostAdapter(this, jsonArray, serviceCostArrayList)
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
            replacedProductCostArrayList.clear()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                replacedProductCostArrayList.add(
                    i,
                    ReplacedProductCostModel(
                        obj!!.getString("components"),
                        obj!!.getString("amount"),
                        obj!!.getString("quantity"),
                        obj!!.getString("changeMode"),
                        obj!!.getString("buyBackAmount"),
                        obj!!.getString("product"),
                        obj!!.getString("remark"),
                        obj!!.getString("isChecked")
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
                ReplacedProductCostAdapter(this, jsonArray, replacedProductCostArrayList)
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
                    obj!!.getString("name"),
                    obj!!.getString("department"),
                    obj!!.getString("role"),
                    obj!!.getString("isChecked")
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

}