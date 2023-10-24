package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.ProductWiseComplaintViewModel
import com.perfect.prodsuit.Viewmodel.ServiceDetailsViewModel
import com.perfect.prodsuit.Viewmodel.ServiceFollowUpInfoViewModel
import com.perfect.prodsuit.Viewmodel.ServiceFollowUpMoreServiceViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceFollowUPActiivty : AppCompatActivity(), View.OnClickListener,ItemClickListener {

    var TAG                                             = "ServiceFollowUPActiivty"
    lateinit var context                              : Context
    lateinit var servicedetailsViewModel              : ServiceDetailsViewModel
    lateinit var productwisecomplaintViewModel        : ProductWiseComplaintViewModel
    lateinit var serviceFollowUpInfoViewModel         : ServiceFollowUpInfoViewModel
    lateinit var serviceFollowUpMoreServiceViewModel  : ServiceFollowUpMoreServiceViewModel
    internal var jsonObjectList                       : JSONObject?     = null
    private var tv_followupticket                     : TextView?       = null
    private var txt_next                              : TextView?       = null
    private var imv_infofollowup                      : ImageView?      = null
    private var imback                                : ImageView?      = null
    private var imv_filterfollowup                    : ImageView?      = null
    private var imv_scannerfollowup                   : ImageView?      = null
    private var rcyler_followup                       : RecyclerView?   = null
    private var rcyler_service3                       : RecyclerView?   = null
    private var recyCompService                       : RecyclerView?   = null
    private var ll_service_Attended                   : LinearLayout?   = null
    private var ll_Service3                           : LinearLayout?   = null
    private var progressDialog                        : ProgressDialog? = null
    private var add_Product                           : FloatingActionButton?= null
    private var dialogMoreServiceAttendeSheet         : Dialog?         = null
    private var recyclr_Product                       : RecyclerView?   = null
    private var recyFollowupAttended                  : RecyclerView?   = null
    var adapterServiceAttended                        : ServiceAttendedAdapter? = null
    var serviceFollowUpInfoObjectList                 : JSONObject      = JSONObject()
    var serviceFollowUpInfoObjectList_2               : JSONObject      = JSONObject()
    var jsonArrayServiceType                          : JSONArray       = JSONArray()
    var jsonArrayServicelist                          : JSONArray       = JSONArray()
    var jsonsubproductArrayServiceType                : JSONArray       = JSONArray()
    var complaintfollowup                             : JSONArray       = JSONArray()
    var jsonArrayMoreServiceAttended                  : JSONArray       = JSONArray()
    var sortMoreServiceAttended                       : JSONArray       = JSONArray()
    var selectMoreServiceAttended                     : JSONArray       = JSONArray()
    var runningStatus                                 : String?         = ""
    var FK_Product                                    : String?         = ""
    var NameCriteria                                  : String?         = ""
    var leadShow                                      : String?         = ""
    var ID_Customerserviceregister                    : String          = ""
    var ID_CustomerserviceregisterProductDetails      : String          = ""
    var ID_Category                                   : String          = ""
    var ID_Product                                    : String          = ""
    var BindProduct                                   : String          = ""
    val modelServiceAttendedTemp                                        = ArrayList<ModelServiceAttendedTemp>()
    val modelServiceAttended                                            = ArrayList<ModelServiceAttended>()
    val modelMoreServices                                               = ArrayList<ModelMoreServices>()
    val modelServicesListDetails                                        = ArrayList<ServiceDetailsFullListModel>()
    val modelServicesSubListDetails                                     = ArrayList<ServiceDetailsSubListModel>()
    val modelMoreServicesTemp                                           = ArrayList<ModelMoreServicesTemp>()
    val serviceTab3MainModel                                            = ArrayList<ServiceTab3MainModel>()
    val serviceTab3SubModel                                             = ArrayList<ServiceTab3SubModel>()
    var serviceFollowUpServiceType                                      = 0
    var serviceFollowUpInfo                                             = 0
    var serviceFollowUpMoreService                                      = 0
    var productwisecomplaintcouny                                       = 0

    var db_helper: DBHelper? = null
    var compAdapter:  complaint_service_followup? = null
    var servDetadapter : ServiceDetailsAdapter? = null
    var modEditPosition = 0
    var modChanged = 0


    // 24-10-2023 Ranjith
    var modeTab = 0  // 0 - Service Attended , 1 = Part Replaced ,2 = Service , 3 =Attendance , 4 = Action taken
    var serviceTab3Adapter : ServiceTab3Adapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_follow_upactiivty)
        context = this@ServiceFollowUPActiivty

        servicedetailsViewModel = ViewModelProvider(this).get(ServiceDetailsViewModel::class.java)
        serviceFollowUpInfoViewModel = ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        serviceFollowUpMoreServiceViewModel = ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        productwisecomplaintViewModel = ViewModelProvider(this).get(ProductWiseComplaintViewModel::class.java)

        db_helper = DBHelper(this,null)
        try {
            runningStatus = intent.getStringExtra("runningStatus")
            ID_Customerserviceregister = intent.getStringExtra("customer_service_register").toString()
            ID_CustomerserviceregisterProductDetails = intent.getStringExtra("ID_CustomerserviceregisterProductDetails").toString()
            jsonObjectList = JSONObject(intent.getStringExtra("jsonObject").toString());

            ID_Product = jsonObjectList!!.getString("FK_Product")
            ID_Category = jsonObjectList!!.getString("FK_Category")
        }catch (e : Exception){

        }
        modeTab = 0
        loadlayout()
        leadShow = "1"
        setRegviews()
        serviceFollowUpInfo = 0
        loadInfo(ID_Customerserviceregister,ID_CustomerserviceregisterProductDetails)
    }

    private fun loadlayout() {
       ll_service_Attended!!.visibility = View.GONE
        ll_Service3!!.visibility = View.GONE

        if (modeTab == 0){
            ll_service_Attended!!.visibility = View.VISIBLE
        }
        if (modeTab == 2){
            ll_Service3!!.visibility = View.VISIBLE
        }
    }

    private fun setRegviews() {
        rcyler_followup      = findViewById(R.id.rcyler_followup)
        rcyler_service3      = findViewById(R.id.rcyler_service3)
        imv_filterfollowup   = findViewById(R.id.imv_filterfollowup)
        imv_scannerfollowup  = findViewById(R.id.imv_scannerfollowup)
        imv_infofollowup     = findViewById(R.id.imv_infofollowup)
        tv_followupticket    = findViewById(R.id.tv_followupticket)
        txt_next    = findViewById(R.id.txt_next)
        add_Product          = findViewById(R.id.add_Product)
        imback               = findViewById(R.id.imback)
        recyCompService               = findViewById(R.id.recyCompService)

        ll_service_Attended      = findViewById(R.id.ll_service_Attended)
        ll_Service3      = findViewById(R.id.ll_Service3)

        imv_infofollowup!!.setOnClickListener(this)
        imv_scannerfollowup!!.setOnClickListener(this)
        imv_filterfollowup!!.setOnClickListener(this)
        add_Product!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        txt_next!!.setOnClickListener(this)


    }


    private fun loadInfo(customerServiceRegister: String,ID_CustomerserviceregisterProductDetails: String) {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_BranchSP   = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        val ID_Branch     = FK_BranchSP.getString("FK_Branch",null)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpInfoViewModel.getServiceFollowUpInfo(
                    this, customerServiceRegister,ID_CustomerserviceregisterProductDetails,
                    ID_Branch!!,
                    ID_Employee!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.v("fsfsfds", "msg")
                                if (serviceFollowUpInfo == 0) {
                                    serviceFollowUpInfo++
                                    Log.e(TAG, "det")
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        serviceFollowUpInfoObjectList = jObject.getJSONObject("EmployeeWiseTicketSelect")

                                        FK_Product = serviceFollowUpInfoObjectList.getString("FK_Product")
                                        NameCriteria = serviceFollowUpInfoObjectList.getString("CusNumber")
                                        tv_followupticket!!.setText(serviceFollowUpInfoObjectList.getString("Ticket"))

//                                        Log.e(TAG, "leadShow")

                                            serviceFollowUpServiceType = 0
                                            getServiceDetails(FK_Product!!,NameCriteria!!)



                                    } else {

                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
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
                                //  swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {

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

    private fun openAlertDialogForMoreInfo(jobjt: JSONObject) {


        var dialogView = Dialog(this)
        dialogView!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogView!! .setContentView(R.layout.alert_more_info)
        dialogView!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


        val window: Window? = dialogView.getWindow()
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
        var tv_ticket: TextView = dialogView.findViewById<TextView>(R.id.tv_ticket)
        var tv_prod_regOn: TextView = dialogView.findViewById<TextView>(R.id.tv_prod_regOn)
        var tv_visit_on: TextView = dialogView.findViewById<TextView>(R.id.tv_visit_on)
        var tv_CustomerName: TextView = dialogView.findViewById<TextView>(R.id.tv_CustomerName)
        var tv_phone: TextView = dialogView.findViewById<TextView>(R.id.tv_phone)
        var tv_location: TextView = dialogView.findViewById<TextView>(R.id.tv_location)
        var tv_address: TextView = dialogView.findViewById<TextView>(R.id.tv_address)
        var tv_product_name: TextView = dialogView.findViewById<TextView>(R.id.tv_product_name)
        var tv_category: TextView = dialogView.findViewById<TextView>(R.id.tv_category)
        var tv_service: TextView = dialogView.findViewById<TextView>(R.id.tv_service)
        var tv_description: TextView = dialogView.findViewById<TextView>(R.id.tv_description)
        //   tv_description.setMovementMethod(ScrollingMovementMethod())
        tv_cancel.setOnClickListener(View.OnClickListener {
            dialogView.dismiss()
        })
        tv_ticket.text=jobjt.getString("Ticket")
        tv_prod_regOn.text=jobjt.getString("RegisterdOn")
        tv_visit_on.text=jobjt.getString("VisitOn")
        tv_CustomerName.text=jobjt.getString("CustomerName")
        tv_phone.text=jobjt.getString("Mobile")
        tv_address.text=jobjt.getString("Address1")+","+jobjt.getString("Address2")+","+jobjt.getString("Address3")
        tv_product_name.text=jobjt.getString("ProductName")
        tv_category.text=jobjt.getString("Category")
        tv_service.text=jobjt.getString("Service")
        tv_description.text=jobjt.getString("Description")


        // alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogView.show()
    }

//    private fun getServiceDetails(FK_Product: String, NameCriteria: String) {
//
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                servicedetailsViewModel.getServiceDetails(
//                    this,FK_Product,NameCriteria)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//                            val jObject = JSONObject(msg)
//                            Log.e(TAG,"msg   353   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("ServiceDetails")
//                                jsonArrayServiceType = jobjt.getJSONArray("ServiceAttendedList")
//
//                                Log.e(TAG,"jsonArrayServiceType  889 "+jsonArrayServiceType)
//
////                                jsonsubproductArrayServiceType = jobjt.getJSONArray("ServiceAttendedListDet")
////                                Log.e(TAG,"jsonsubproductArrayServiceType  8888 "+jsonsubproductArrayServiceType)
//
//                                if (jsonArrayServiceType.length()>0){
//                                    if (serviceFollowUpServiceType == 0){
//                                        serviceFollowUpServiceType++
//
//                                        modelServicesListDetails.clear()
//                                        for (i in 0 until jsonArrayServiceType.length()) {
//                                            var jsonObject = jsonArrayServiceType.getJSONObject(i)
//
//                                            jsonsubproductArrayServiceType = jsonArrayServiceType!!.getJSONObject(i).getJSONArray("ServiceAttendedListDet")
//                                            Log.e(TAG,"jsonsubproductArrayServiceType  8888 "+jsonsubproductArrayServiceType)
////                                            modelServicesListDetails!!.add(ServiceDetailsFullListModel((0),jsonObject.getString("FK_Product"),jsonObject.getString("Product"),jsonObject.getString("BindProduct")))
//                                        }
//
//                                    }
//
//                                }
//
//                            } else {
//                                val builder = AlertDialog.Builder(
//                                    this@ServiceFollowUPActiivty,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
//                            }
//                        } else {
////                            Toast.makeText(
////                                applicationContext,
////                                "Some Technical Issues.",
////                                Toast.LENGTH_LONG
////                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//    }


    private fun getServiceDetails(FK_Product: String, NameCriteria: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                servicedetailsViewModel.getServiceDetails(
                    this,FK_Product,NameCriteria
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
                                        val jobjt = jObject.getJSONObject("ServiceDetails")
                                        jsonArrayServiceType = jobjt.getJSONArray("ServiceAttendedList")

//                                       db_helper!!.insertServiceDetails(jsonArrayServiceType)

//                                        jsonsubproductArrayServiceType = jsonArrayServiceType!!.getJSONObject(0).getJSONArray("ServiceAttendedListDet")
////                                        jsonsubproductArrayServiceType = jobjt.getJSONArray("ServiceAttendedListDet")
////                                        BindProduct = jsonArrayServiceType!!.getJSONObject(0).getString("BindProduct")
//                                        Log.e(TAG, "vvvvvvvvv  1" +jsonArrayServiceType)
//                                        Log.e(TAG, "vvvvvvvvv  2  " +jsonsubproductArrayServiceType.length())
//                                        Log.e(TAG, "vvvvvvvvv  3" +jsonArrayServiceType)
//
//                                        if (jsonArrayServiceType.length() > 0) {
//
//                                            for (i in 0 until jsonArrayServiceType.length()) {
//                                                var jsonObject = jsonArrayServiceType.getJSONObject(i)

                                        for (i in 0 until jsonArrayServiceType.length()) {
                                            var jsonObject = jsonArrayServiceType.getJSONObject(i)
                                            Log.e(TAG," 388...1  "+jsonObject.getString("Product"))


                                            modelServicesListDetails!!.add(ServiceDetailsFullListModel("0","-1",jsonObject.getString("MasterProduct"),
                                                jsonObject.getString("FK_Product"),jsonObject.getString("Product"),"-2",
                                                jsonObject.getString("BindProduct"),jsonObject.getString("ComplaintProduct"),jsonObject.getString("Warranty"),
                                                jsonObject.getString("ServiceWarrantyExpireDate"),jsonObject.getString("ReplacementWarrantyExpireDate"),
                                                jsonObject.getString("ID_CustomerWiseProductDetails"),jsonObject.getString("ServiceWarrantyExpired"),
                                                jsonObject.getString("ReplacementWarrantyExpired"),"0","","",false))

                                            var ServiceAttendedListDet = jsonObject.getJSONArray("ServiceAttendedListDet")

                                            for (j in 0 until ServiceAttendedListDet.length()) {
                                                var jsonObjectSub = ServiceAttendedListDet.getJSONObject(j)
                                                Log.e(TAG," 388...2  "+jsonObjectSub.getString("Product"))

                                                modelServicesListDetails!!.add(ServiceDetailsFullListModel("1",jsonObjectSub.getString("FK_Category"),jsonObjectSub.getString("MasterProduct"),
                                                    jsonObjectSub.getString("FK_Product"),jsonObjectSub.getString("Product"),jsonObjectSub.getString("SLNo"),
                                                    jsonObjectSub.getString("BindProduct"),jsonObjectSub.getString("ComplaintProduct"),jsonObjectSub.getString("Warranty"),
                                                    jsonObjectSub.getString("ServiceWarrantyExpireDate"),jsonObjectSub.getString("ReplacementWarrantyExpireDate"),
                                                    jsonObjectSub.getString("ID_CustomerWiseProductDetails"),jsonObjectSub.getString("ServiceWarrantyExpired"),
                                                    jsonObjectSub.getString("ReplacementWarrantyExpired"),"0","","",false))


//                                                jObject.put("FK_Category",jsonObjectSub.getString("FK_Category"))
//                                                jObject.put("MasterProduct",jsonObjectSub.getString("MasterProduct"))
//                                                jObject.put("FK_Product",jsonObjectSub.getString("FK_Product"))
//                                                jObject.put("Product",jsonObjectSub.getString("Product"))
//                                                jObject.put("SLNo",jsonObjectSub.getString("SLNo"))
//                                                jObject.put("BindProduct",jsonObjectSub.getString("BindProduct"))
//                                                jObject.put("ComplaintProduct",jsonObjectSub.getString("ComplaintProduct"))
//                                                jObject.put("Warranty",jsonObjectSub.getString("Warranty"))
//                                                jObject.put("ServiceWarrantyExpireDate",jsonObjectSub.getString("ServiceWarrantyExpireDate"))
//                                                jObject.put("ReplacementWarrantyExpireDate",jsonObjectSub.getString("ReplacementWarrantyExpireDate"))
//                                                jObject.put("ID_CustomerWiseProductDetails",jsonObjectSub.getString("ID_CustomerWiseProductDetails"))
//                                                jObject.put("ServiceWarrantyExpired",jsonObjectSub.getString("ServiceWarrantyExpired"))
//                                                jObject.put("ReplacementWarrantyExpired",jsonObjectSub.getString("ReplacementWarrantyExpired"))
//
////                                                var FK_Category_sub = jsonObjectSub.getString("FK_Category")
////                                                var MasterProduct_sub = jsonObjectSub.getString("MasterProduct")
////                                                var ID_Product = jsonObjectSub.getString("FK_Product")
////                                                var FK_Product_sub = jsonObjectSub.getString("Product")
////                                                var SLNo_sub = jsonObjectSub.getString("SLNo")
////                                                var BindProduct_sub = jsonObjectSub.getString("BindProduct")
////                                                var ComplaintProduct_sub = jsonObjectSub.getString("ComplaintProduct")
////                                                var Warranty_sub = jsonObjectSub.getString("Warranty")
////                                                var ServiceWarrantyExpireDate_sub = jsonObjectSub.getString("ServiceWarrantyExpireDate")
////                                                var ReplacementWarrantyExpireDate_sub = jsonObjectSub.getString("ReplacementWarrantyExpireDate")
////                                                var ID_CustomerWiseProductDetails_sub = jsonObjectSub.getString("ID_CustomerWiseProductDetails")
////                                                var ServiceWarrantyExpired_sub = jsonObjectSub.getString("ServiceWarrantyExpired")
////                                                var ReplacementWarrantyExpired_sub = jsonObjectSub.getString("ReplacementWarrantyExpired")
////                                                var writeSub =
////                                                    dbWrite.execSQL("INSERT INTO servicedetailsublist (FK_Product_parent,FK_Category,MasterProduct,FK_Product,FK_Product_sub,SLNo,BindProduct,ComplaintProduct,Warranty,ServiceWarrantyExpireDate,ReplacementWarrantyExpireDate,ID_CustomerWiseProductDetails,ServiceWarrantyExpired,ReplacementWarrantyExpired) VALUES ('$FK_Product','$FK_Category_sub','$MasterProduct_sub','$ID_Product','$FK_Product_sub','$SLNo_sub','$BindProduct_sub','$ComplaintProduct_sub','$Warranty_sub','$ServiceWarrantyExpireDate_sub','$ReplacementWarrantyExpireDate_sub','$ID_CustomerWiseProductDetails_sub','$ServiceWarrantyExpired_sub','$ReplacementWarrantyExpired_sub')")
////                                                Log.e(TAG, "writeSub  " + writeSub)
//                                                jsonArrayServicelist.put(jObject)
//
//                                                Log.e(TAG, "jsonArrayServicelist  " + jsonArrayServicelist)
                                            }
                                        }

                                        for (j in 0 until modelServicesListDetails.size) {
                                            Log.e(TAG,"447..1   "+""+j+"  "+modelServicesListDetails.get(j).Product)
                                        }



////                                                jsonsubproductArrayServiceType = jsonArrayServiceType!!.getJSONObject(i).getJSONArray("ServiceAttendedListDet")
////
////                                                Log.e(TAG, "vvvvvvvvvv  1" +jsonsubproductArrayServiceType)
////                                                val jObject = JSONObject()
//
////                                                modelServicesListDetails!!.add(ServiceDetailsFullListModel((0),jsonObject.getString("FK_Product"),jsonObject.getString("Product"),jsonObject.getString("BindProduct")))
////                                                modelServicesSubListDetails!!.add(ServiceDetailsSubListModel(jsonObject.getString("FK_Category"),jsonObject.getString("Product"),"FK_Product"))
//
////                                                Log.e(TAG, "ddddddddd  2")
                                                val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                                                rcyler_followup!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                servDetadapter = ServiceDetailsAdapter(this@ServiceFollowUPActiivty, modelServicesListDetails)
                                                rcyler_followup!!.adapter = servDetadapter
                                                servDetadapter!!.setClickListener(this@ServiceFollowUPActiivty)


//
//
////                                                val gson = Gson()
////                                                val myList: List<ServiceDetailsFullListModel> = gson.fromJson(jsonArrayServiceType, List::class.java)
//                                            }
//                                        }

                                    }else{
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme)
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
//                                Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG,"392002 +"+e)
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

    private fun getProductWiseComplaint(FK_Category: String,FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productwisecomplaintViewModel.getProductWiseComplaint(
                    this,FK_Product,FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (productwisecomplaintcouny == 0) {
                                    productwisecomplaintcouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ComplaintListDetails")
                                        complaintfollowup = jobjt.getJSONArray("ComplaintListDetailsList")
                                        Log.e(TAG, "dssadd  1"+msg )
                                        if (complaintfollowup.length() > 0) {

                                            if (modChanged == 1){
                                                productWisePopup()
                                            }else{
                                                val jsonObject = complaintfollowup.getJSONObject(0)
                                                Log.e(TAG,"95444     "+jsonObject)

                                                var empModel = modelServicesListDetails[modEditPosition]

                                                empModel.ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
                                                empModel.ComplaintName = jsonObject.getString("ComplaintName")
                                                Log.e(TAG,"954441     "+ empModel.ComplaintName)

                                                servDetadapter!!.notifyItemChanged(modEditPosition)
                                            }



//                                            Log.e(TAG, "dssadd  2" )
//                                            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
//                                            recyCompService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = complaint_service_followup(this@ServiceFollowUPActiivty, complaintfollowup!!)
//                                            recyCompService!!.adapter = adapter
////                                            adapter.setClickListener(this@ServiceFollowUPActiivty)
                                        }

                                    }else{
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme)
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
//                                Toast.makeText(
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



    private fun loadMoreServiceAttended() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_BranchSP   = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        val ID_Branch     = FK_BranchSP.getString("FK_Branch",null)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpMoreServiceViewModel.getServiceFollowUpMoreService(
                    this, ID_Customerserviceregister, ID_Branch!!, ID_Employee!!)!!.observe(
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
                modelServiceAttendedTemp!!.add(
                    ModelServiceAttendedTemp(ItemsServiceTemp.ID_ProductWiseServiceDetails,ItemsServiceTemp.SubProduct
                    ,ItemsServiceTemp.ID_Product,ItemsServiceTemp.ID_Services,ItemsServiceTemp.Service,ItemsServiceTemp.ServiceCost,
                    ItemsServiceTemp.ServiceTaxAmount,ItemsServiceTemp.ServiceNetAmount,ItemsServiceTemp.Remarks,ItemsServiceTemp.isChecked,
                    ItemsServiceTemp.isDelete,ItemsServiceTemp.isCheckedAdd,ItemsServiceTemp.ServiceTypeId,ItemsServiceTemp.ServiceTypeName)
                )
            }

            Log.e(TAG,"590   sortMoreServiceAttended   "+sortMoreServiceAttended)
            if (modelMoreServicesTemp.size>0){
                val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                recyFollowupAttended!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                val adapter1 = MoreServiceAttendedAdapter(this@ServiceFollowUPActiivty,modelMoreServices,modelMoreServicesTemp,modelServiceAttendedTemp)
                recyFollowupAttended!!.adapter = adapter1
//                adapter1.setClickListener(this@ServiceFollowUPActiivty)

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
        recyclr_Product!!.layoutAnimation = controller
        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        rcyler_followup!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        adapterServiceAttended = ServiceAttendedAdapter(this@ServiceFollowUPActiivty, modelServiceAttended,jsonArrayServiceType)
        rcyler_followup!!.scheduleLayoutAnimation()
        rcyler_followup!!.adapter = adapterServiceAttended
        adapterServiceAttended!!.notifyDataSetChanged()


//        adapterServiceAttended!!.notifyDataSetChanged()
    }


    private fun filterBottomData() {

        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.service_followup_filter, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(true)



            val txtCancel       = view.findViewById(R.id.txtReset) as TextView
            val txtSubmit       = view.findViewById(R.id.txtSearch) as TextView

            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin   = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)




            txtCancel.setOnClickListener {
//                loadLoginEmpDetails("1")
            }

            txtSubmit.setOnClickListener {

//                validateData(dialog1)

            }


            dialog1!!.setContentView(view)
            dialog1.show()

            dialog1.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }

    }




    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imback -> {
                onBackPressed()
            }
            R.id.imv_infofollowup -> {
//                leadShow = "0"
//                serviceFollowUpInfo = 0
//                loadInfo(ID_Customerserviceregister,ID_CustomerserviceregisterProductDetails)
                openAlertDialogForMoreInfo(serviceFollowUpInfoObjectList)
            }
            R.id.imv_filterfollowup -> {
                filterBottomData()
            }
            R.id.add_Product -> {
                loadMoreServiceAttended()
            }
            R.id.txt_next -> {
               valiadateServiceAttended()
                modeTab = modeTab+1
                loadlayout()
            }

            R.id.txt_previous -> {
                modeTab = modeTab-1
                loadlayout()
                ll_service_Attended!!.visibility = View.VISIBLE
                ll_Service3!!.visibility = View.GONE
            }





        }
    }



    private fun productWisePopup() {
        Log.e(TAG,"864  ")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!! .setContentView(R.layout.cs_more_service_attended_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended = dialogMoreServiceAttendeSheet!! .findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            compAdapter = complaint_service_followup(this@ServiceFollowUPActiivty, complaintfollowup!!)
            recyFollowupAttended!!.adapter = compAdapter
            compAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

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


    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"864  ")
        if (data.equals("productwise_cmplt")){

            modEditPosition = position
            modChanged = 1

//            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG,"test entered   ")

            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product

            productwisecomplaintcouny = 0
            getProductWiseComplaint(FK_Category!!,FK_Product!!)
        }

        if (data.equals("chkproductwise_cmplt")){

            modEditPosition = position
            modChanged = 0

//            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG,"test entered   ")

            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product

            productwisecomplaintcouny = 0
            getProductWiseComplaint(FK_Category!!,FK_Product!!)
        }




        if (data.equals("CompServiceFollowUp")){
            Log.e(TAG,"95444     "+position)
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG,"95444     "+jsonObject)

            var empModel = modelServicesListDetails[modEditPosition]

            empModel.ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
            empModel.ComplaintName = jsonObject.getString("ComplaintName")

//            modelServicesListDetails.get(position).ID_ComplaintList =jsonObject.getString("ID_ComplaintList")
//            modelServicesListDetails.get(position).ComplaintName=jsonObject.getString("ComplaintName")
            Log.e(TAG,"954441     "+ empModel.ComplaintName)

            servDetadapter!!.notifyItemChanged(modEditPosition)

//            rcyler_followup!!.adapter!!.notifyItemChanged(position)
//            servDetadapter!!.notifyItemChanged(position)
        }


    }


    private fun valiadateServiceAttended() {


        var hasId =  hasTrue(modelServicesListDetails!!)
        Log.e(TAG,"1016661     "+hasId)
        if (hasId){
            Log.e(TAG,"10000666666   Checked Box MArked")
            ll_service_Attended!!.visibility = View.GONE
            ll_Service3!!.visibility = View.VISIBLE


            // 24-10-2023 Ranjith
            serviceTab3MainModel.clear()
            Log.e(TAG,"1034441     "+modelServicesListDetails.size)
            Log.e(TAG,"1034442     "+serviceTab3MainModel.size)
            for (i in 0 until modelServicesListDetails.size) {
                var empModel = modelServicesListDetails[i]
                if (empModel.isChekecd){
                    serviceTab3MainModel!!.add(ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","",
                        "","","","","","","",""))
                }

            }

            Log.e(TAG,"1034443     "+serviceTab3MainModel.size)
            if (serviceTab3MainModel.size > 0){
                val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                rcyler_service3!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                serviceTab3Adapter = ServiceTab3Adapter(this@ServiceFollowUPActiivty, serviceTab3MainModel)
                rcyler_service3!!.adapter = serviceTab3Adapter
                serviceTab3Adapter!!.setClickListener(this@ServiceFollowUPActiivty)

            }
            // 24-10-2023 Ranjith
        }else{
            Log.e(TAG,"10000666666   No Checked Box MArked")
        }

    }

    private fun hasTrue(modelServicesListDetails: ArrayList<ServiceDetailsFullListModel>): Boolean {

        var isChecked = false
        for (i in 0 until modelServicesListDetails.size) {  // iterate through the JsonArray
            Log.e(TAG,"101666     "+modelServicesListDetails.get(i).isChekecd+"  :   "+modelServicesListDetails.get(i).ID_ComplaintList)

            if (modelServicesListDetails.get(i).isChekecd){
                isChecked = true
                if (modelServicesListDetails.get(i).ID_ComplaintList.equals("0")){
                    isChecked = false
                    break
                }
            }

        }
        return isChecked
    }


}