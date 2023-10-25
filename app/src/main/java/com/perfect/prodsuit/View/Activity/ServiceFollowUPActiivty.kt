package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class ServiceFollowUPActiivty : AppCompatActivity(), View.OnClickListener,ItemClickListener {

    var TAG                                             = "ServiceFollowUPActiivty"
    lateinit var context                              : Context
    lateinit var servicedetailsViewModel              : ServiceDetailsViewModel
    lateinit var productwisecomplaintViewModel        : ProductWiseComplaintViewModel
    lateinit var serviceFollowUpInfoViewModel         : ServiceFollowUpInfoViewModel
    lateinit var serviceFollowUpMoreServiceViewModel  : ServiceFollowUpMoreServiceViewModel
    lateinit var addServiceViewModel                  : AddServiceViewModel
    internal var jsonObjectList                       : JSONObject?     = null
    private var tv_followupticket                     : TextView?       = null
    private var txt_next                              : TextView?       = null
    private var txt_previous                          : TextView?       = null
    private var vw_previous                           : View?       = null
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
    lateinit var actionTakenViewModel: ActionTakenViewModel
    lateinit var leadActionViewModel: LeadActionViewModel
    lateinit var otherChargesViewViewModel: OtherChargesViewViewModel
    lateinit var pickupdelibilltypeviewmodel: PickupDeliBillTypeViewModel
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    private var rcyler_actionTaken: RecyclerView? = null
    private var ll_action_taken: LinearLayout? = null
    var actionTypeActionList: JSONArray = JSONArray()
    var leadActionList: JSONArray = JSONArray()
    var otherChargeList: JSONArray = JSONArray()
    val actionTakenSelected = ArrayList<ActionTakenMainModel>()
    var otherChargesFinalList = ArrayList<OtherChargesMainModel>()
    var actionTakenActioncouny = 0

    var db_helper: DBHelper? = null
    var compAdapter: complaint_service_followup? = null
    var actionTakeActionFilter: ActionTakeActionFilter? = null
    var leadActionAdapter: LeadActionAdapter? = null
    var servDetadapter: ServiceDetailsAdapter? = null
    var actionTakenAdapter: ActionTakenAdapter? = null
    var modEditPosition = 0
    var modChanged = 0
    var billTypecount = 0
    var paymentCount = 0
    var billtype=""


    // 24-10-2023 Ranjith
    var modeTab = 0  // 0 - Service Attended , 1 = Part Replaced ,2 = Service , 3 =Attendance , 4 = Action taken
    var serviceTab3Adapter : ServiceTab3Adapter? = null
    var FK_Product_Pos : Int?         = 0
    var FK_Product_ID : String?         = ""
    var addserviceMode              = 0
    lateinit var addserviceArrayList: JSONArray
    private var dialogAddserviceSheet : Dialog? = null
    val addServiceDetailMode        = ArrayList<AddServiceDetailMode>()
    lateinit var serviceFollowUpServiceTypeViewModel: ServiceFollowUpServiceTypeViewModel

    private var dialogServType : Dialog? = null

    private var serviceDetailsArray = JSONArray()
    private var serviceIncentiveArray = JSONArray()

    private var DateType: Int = 0
    private var tie_DateAttended: TextInputEditText? = null
    private var edttotalSecurityAmount: TextInputEditText? = null
    private var edtcomponentCharge: TextInputEditText? = null
    private var edttotalServiceCost: TextInputEditText? = null
    private var edtdiscountAmount: TextInputEditText? = null
    private var tie_Selectbilltype: AutoCompleteTextView? = null
    private var edt_other_charges: TextView? = null
    private var tv_payment_type: TextView? = null
    var otherChargesAdapterAdapter: OtherChargesAdapterAdapter? = null
    lateinit var billtypeArrayList: JSONArray
    var pickupdeliStatusCount = 0
    lateinit var paymentMethodeArrayList: JSONArray
    private var dialogPaymentMethod: Dialog? = null
    var recyPaymentMethod: RecyclerView? = null
    private var dialogPaymentSheet: Dialog? = null
    private var edtPayMethod: EditText? = null
    private var edtPayRefNo: EditText? = null
    private var edtPayAmount: EditText? = null
    private var txt_pay_method: TextView? = null
    private var txt_pay_Amount: TextView? = null
    private var txt_bal_Amount: TextView? = null
    private var txtPayBalAmount: TextView? = null
    private var img_PayAdd: ImageView? = null
    private var img_PayRefresh: ImageView? = null
    private var btnApply: Button? = null
    var arrPayment = JSONArray()
    var arrPaymentFinal = JSONArray()
    var arrOtherChargeFinal = JSONArray()
    internal var recyPaymentList: RecyclerView? = null
    internal var ll_paymentlist: LinearLayout? = null
    var adapterPaymentList: PaymentListAdapter? = null
    var arrAddUpdate: String? = "0"
    var applyMode = 0
    var ID_PaymentMethod: String? = ""
    var arrPosition: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_follow_upactiivty)
        context = this@ServiceFollowUPActiivty

        servicedetailsViewModel = ViewModelProvider(this).get(ServiceDetailsViewModel::class.java)
        serviceFollowUpInfoViewModel =
            ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        serviceFollowUpMoreServiceViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        productwisecomplaintViewModel =
            ViewModelProvider(this).get(ProductWiseComplaintViewModel::class.java)
        actionTakenViewModel = ViewModelProvider(this).get(ActionTakenViewModel::class.java)
        leadActionViewModel = ViewModelProvider(this).get(LeadActionViewModel::class.java)
        otherChargesViewViewModel =
            ViewModelProvider(this).get(OtherChargesViewViewModel::class.java)
        pickupdelibilltypeviewmodel =
            ViewModelProvider(this).get(PickupDeliBillTypeViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        serviceFollowUpInfoViewModel = ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        serviceFollowUpMoreServiceViewModel = ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        productwisecomplaintViewModel = ViewModelProvider(this).get(ProductWiseComplaintViewModel::class.java)
        addServiceViewModel = ViewModelProvider(this).get(AddServiceViewModel::class.java)
        serviceFollowUpServiceTypeViewModel = ViewModelProvider(this).get(ServiceFollowUpServiceTypeViewModel::class.java)


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
        setRegviews()
        modeTab = 0
        loadlayout()
        leadShow = "1"

        serviceFollowUpInfo = 0
        loadInfo(ID_Customerserviceregister,ID_CustomerserviceregisterProductDetails)
    }

    private fun loadlayout() {
        ll_service_Attended!!.visibility = View.GONE
        ll_Service3!!.visibility = View.GONE
        ll_action_taken!!.visibility = View.GONE

//        0 - Service Attended , 1 = Part Replaced ,2 = Service , 3 =Attendance , 4 = Action taken
Log.v("adasdasds","modeTab "+modeTab)
        if (modeTab == 0){
            Log.v("adasdasds","modeTab0 ")
            ll_service_Attended!!.visibility = View.VISIBLE
            txt_previous!!.visibility = View.GONE
            vw_previous!!.visibility = View.GONE
            txt_next!!.visibility = View.VISIBLE
        }
        if (modeTab == 1){

            Log.v("adasdasds","modeTab1 ")
            ll_Service3!!.visibility = View.VISIBLE
            txt_previous!!.visibility = View.VISIBLE
            vw_previous!!.visibility = View.VISIBLE
            txt_next!!.visibility = View.VISIBLE
        }
        if (modeTab == 4) {

            Log.v("adasdasds","modeTab4 ")
            ll_action_taken!!.visibility = View.VISIBLE
            loadActionTaken()
        }
        if (modeTab == 5) {

            Log.v("adasdasds","modeTab5 ")
            finalSave()
        }
    }

    private fun finalSave() {
        val Actionproductdetails = JSONArray()
        for (obj in actionTakenSelected) {
            val jsonObject = JSONObject()
            jsonObject.put("ID_Product", obj.FK_Product)
            jsonObject.put("ID_NextAction", obj.actionStatus)
            jsonObject.put("ID_NextActionLead", obj.leadActionStatus)
            jsonObject.put("FK_ActionType", "")
            jsonObject.put("FK_EmployeeAssign", "")
            jsonObject.put("NextActionDate", "")
            jsonObject.put("ProvideStandBy", obj.ProvideStandBy)
            jsonObject.put("CustomerNote", obj.Customer_note)
            jsonObject.put("EmployeeNote", obj.Employee_note)
            jsonObject.put("SecurityAmount", obj.securityAmount)
            jsonObject.put("OfferPrice", obj.buyBackAmount)
            jsonObject.put("ID_CustomerWiseProductDetails", obj.ID_CustomerWiseProductDetails)
            jsonObject.put("FK_ProductNumberingDetails", "0")
            Actionproductdetails.put(jsonObject)
        }
        Log.v("sdfsdfsd","Actionproductdetails "+Actionproductdetails.toString())
        Log.v("sdfsdfsd", "payment  " + arrPaymentFinal.toString())
        Log.v("sdfsdfsd", "other  " + arrOtherChargeFinal.toString())

        var attendedDate=tie_DateAttended!!.text
        var totalSecurityAmount=edttotalSecurityAmount!!.text
        var componentCharge=edtcomponentCharge!!.text
        var totalServiceCost=edttotalServiceCost!!.text
        var discountAmount=edtdiscountAmount!!.text
        var edtnetAmount=tie_DateAttended!!.text




    }

    private fun setRegviews() {
        rcyler_followup      = findViewById(R.id.rcyler_followup)
        rcyler_service3      = findViewById(R.id.rcyler_service3)
        imv_filterfollowup   = findViewById(R.id.imv_filterfollowup)
        imv_scannerfollowup  = findViewById(R.id.imv_scannerfollowup)
        imv_infofollowup     = findViewById(R.id.imv_infofollowup)
        tv_followupticket    = findViewById(R.id.tv_followupticket)
        txt_next    = findViewById(R.id.txt_next)
        txt_previous    = findViewById(R.id.txt_previous)
        vw_previous    = findViewById(R.id.vw_previous)
        add_Product          = findViewById(R.id.add_Product)
        imback               = findViewById(R.id.imback)
        recyCompService               = findViewById(R.id.recyCompService)

        ll_service_Attended      = findViewById(R.id.ll_service_Attended)
        ll_Service3      = findViewById(R.id.ll_Service3)

        tie_Selectbilltype = findViewById(R.id.tie_Selectbilltype) as AutoCompleteTextView
        tv_payment_type = findViewById(R.id.tv_payment_type)
        edttotalSecurityAmount = findViewById(R.id.edttotalSecurityAmount)
        edtcomponentCharge = findViewById(R.id.edtcomponentCharge)
        edttotalServiceCost = findViewById(R.id.edttotalServiceCost)
        edtdiscountAmount = findViewById(R.id.edtdiscountAmount)
        tie_DateAttended = findViewById(R.id.tie_DateAttended)
        edt_other_charges = findViewById(R.id.edt_other_charges)
        rcyler_followup = findViewById(R.id.rcyler_followup)
        rcyler_actionTaken = findViewById(R.id.rcyler_actionTaken)
        rcyler_service3 = findViewById(R.id.rcyler_service3)
        imv_filterfollowup = findViewById(R.id.imv_filterfollowup)
        imv_scannerfollowup = findViewById(R.id.imv_scannerfollowup)
        imv_infofollowup = findViewById(R.id.imv_infofollowup)
        tv_followupticket = findViewById(R.id.tv_followupticket)
        txt_next = findViewById(R.id.txt_next)
        add_Product = findViewById(R.id.add_Product)
        imback = findViewById(R.id.imback)
        recyCompService = findViewById(R.id.recyCompService)
        ll_action_taken = findViewById(R.id.ll_action_taken)
        ll_service_Attended = findViewById(R.id.ll_service_Attended)
        ll_Service3 = findViewById(R.id.ll_Service3)
        imv_infofollowup!!.setOnClickListener(this)
        imv_scannerfollowup!!.setOnClickListener(this)
        imv_filterfollowup!!.setOnClickListener(this)
        add_Product!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        txt_next!!.setOnClickListener(this)
        txt_previous!!.setOnClickListener(this)


    }

    private fun getBillType() {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdelibilltypeviewmodel.getPickupDeliBillType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (billTypecount == 0) {
                                    billTypecount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("BillType")
                                        billtypeArrayList = jobjt.getJSONArray("BillTypeList")
                                        if (billtypeArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            showbilltype(billtypeArrayList)

//                                             }

                                        }

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
                                progressDialog!!.dismiss()
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
                            Log.e(TAG, "catch   " + e)
                        }
                        progressDialog!!.dismiss()
                    })

            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun showbilltype(billtypeArrayList: JSONArray) {

        var searchType = Array<String>(billtypeArrayList.length()) { "" }
        for (i in 0 until billtypeArrayList.length()) {
            val objects: JSONObject = billtypeArrayList.getJSONObject(i)
            searchType[i] = objects.getString("BTName");
            var FK_BillType = objects.getString("ID_BillType")

            Log.e(TAG, "00000111   " + FK_BillType)
            Log.e(TAG, "85456214   " + searchType)

            if (pickupdeliStatusCount == 0) {
                pickupdeliStatusCount++

                val adapter =
                    ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                tie_Selectbilltype!!.setAdapter(adapter)
                tie_Selectbilltype!!.showDropDown()
                //   tie_Selectstatus!!.setText(searchType.get(0), false)
//            tie_Selectstatus!!.setOnClickListener {
                pickupdeliStatusCount = 0
                tie_Selectbilltype!!.showDropDown()
                Log.e(TAG, "7778889999   " + pickupdeliStatusCount)
//            }
                tie_Selectbilltype!!.setOnItemClickListener { parent, view, position, id ->
                    billtype = searchType[position]
                }
            }
        }
    }


    private fun loadInfo(
        customerServiceRegister: String,
        ID_CustomerserviceregisterProductDetails: String
    ) {
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
        dialogView!!.setContentView(R.layout.alert_more_info)
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
        tv_ticket.text = jobjt.getString("Ticket")
        tv_prod_regOn.text = jobjt.getString("RegisterdOn")
        tv_visit_on.text = jobjt.getString("VisitOn")
        tv_CustomerName.text = jobjt.getString("CustomerName")
        tv_phone.text = jobjt.getString("Mobile")
        tv_address.text =
            jobjt.getString("Address1") + "," + jobjt.getString("Address2") + "," + jobjt.getString(
                "Address3"
            )
        tv_product_name.text = jobjt.getString("ProductName")
        tv_category.text = jobjt.getString("Category")
        tv_service.text = jobjt.getString("Service")
        tv_description.text = jobjt.getString("Description")


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
                    this, FK_Product, NameCriteria
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
                                        jsonArrayServiceType =
                                            jobjt.getJSONArray("ServiceAttendedList")

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

    private fun loadActionTaken() {
        Log.v("sdfsdfds","modelServicesListDetails "+modelServicesListDetails.toString())
        actionTakenSelected.clear()
        for (i in 0 until modelServicesListDetails.size) {
            var empModel = modelServicesListDetails[i]
            if (empModel.isChekecd) {
                actionTakenSelected!!.add(
                    ActionTakenMainModel(
                        empModel.FK_Product, empModel.Product, "", "",
                        "", "", "false", "", "", "", "",empModel.ID_CustomerWiseProductDetails
                    )
                )
            }

        }
        Log.v("sfsdfdfd", "act size  " + actionTakenSelected.size)
        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        rcyler_actionTaken!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//        actionTakenAdapter = ActionTakenAdapter(this@ServiceFollowUPActiivty, actionTakenSelected)
        actionTakenAdapter = ActionTakenAdapter(
            this@ServiceFollowUPActiivty,
            actionTakenSelected,
            object : ActionTakenAdapter.TextChangedListener {
                override fun onTextChanged(position: Int, field: String, newText: String) {
                    Log.v("sfsdfdfd", "position  " + position)
                    Log.v("sfsdfdfd", "field  " + field)
                    Log.v("sfsdfdfd", "newText  " + newText)
                    if (field.equals("customer_note")) {
                        var empModel = actionTakenSelected[position]
                        empModel.Customer_note = newText
                       // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("employee_note")) {
                        var empModel = actionTakenSelected[position]
                        empModel.Employee_note = newText
                       // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("security_amount")) {
                        var empModel = actionTakenSelected[position]
                        empModel.securityAmount = newText
                       // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("buy_back")) {
                        var empModel = actionTakenSelected[position]
                        empModel.buyBackAmount = newText
                        //actionTakenAdapter!!.notifyItemChanged(position)
                    }


                }
            })
        rcyler_actionTaken!!.adapter = actionTakenAdapter
        actionTakenAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

        tie_DateAttended!!.setOnClickListener(View.OnClickListener {
            DateType = 0
            openBottomSheet()
        })
        edt_other_charges!!.setOnClickListener(View.OnClickListener {
            DateType = 0
            actionTakenActioncouny = 0
            getOtherCharges()
        })
        tie_Selectbilltype!!.setOnClickListener(View.OnClickListener {
            billTypecount = 0
            getBillType()
        })

        tv_payment_type!!.setOnClickListener(View.OnClickListener {
            paymentMethodPopup()
        })


    }

    private fun getPaymentList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                paymentMethodeViewModel.getPaymentMethod(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (paymentCount == 0) {
                                    paymentCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpPaymentMethod")
                                        paymentMethodeArrayList =
                                            jobjt.getJSONArray("FollowUpPaymentMethodList")
                                        if (paymentMethodeArrayList.length() > 0) {

                                            payMethodPopup(paymentMethodeArrayList)

                                        }
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

    private fun payMethodPopup(paymentMethodeArrayList: JSONArray) {
        try {

            dialogPaymentMethod = Dialog(this)
            dialogPaymentMethod!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPaymentMethod!!.setContentView(R.layout.payment_method_popup)
            dialogPaymentMethod!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyPaymentMethod =
                dialogPaymentMethod!!.findViewById(R.id.recyPaymentMethod) as RecyclerView
            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = PayMethodAdapter(this@ServiceFollowUPActiivty, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)
            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun paymentMethodPopup() {
        try {

            dialogPaymentSheet = Dialog(this)
            dialogPaymentSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPaymentSheet!!.setContentView(R.layout.emi_payment_bottom_sheet)
            dialogPaymentSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogPaymentSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //  dialogPaymentSheet!!.setCancelable(false)

            edtPayMethod = dialogPaymentSheet!!.findViewById(R.id.edtPayMethod) as EditText
            edtPayRefNo = dialogPaymentSheet!!.findViewById(R.id.edtPayRefNo) as EditText
            edtPayAmount = dialogPaymentSheet!!.findViewById(R.id.edtPayAmount) as EditText

            txtPayBalAmount = dialogPaymentSheet!!.findViewById(R.id.txtPayBalAmount) as TextView

            txt_pay_method = dialogPaymentSheet!!.findViewById(R.id.txt_pay_method) as TextView
            txt_pay_Amount = dialogPaymentSheet!!.findViewById(R.id.txt_pay_Amount) as TextView
            txt_bal_Amount = dialogPaymentSheet!!.findViewById(R.id.txt_bal_Amount) as TextView

            edtPayMethod!!.addTextChangedListener(watcher)
            edtPayAmount!!.addTextChangedListener(watcher)
            txtPayBalAmount!!.addTextChangedListener(watcher)

//            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
//            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
//            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText


            DecimelFormatters.setDecimelPlace(edtPayAmount!!)


//            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))


            img_PayAdd = dialogPaymentSheet!!.findViewById(R.id.img_PayAdd) as ImageView
            img_PayRefresh = dialogPaymentSheet!!.findViewById(R.id.img_PayRefresh) as ImageView
            btnApply = dialogPaymentSheet!!.findViewById(R.id.btnApply) as Button

            ll_paymentlist = dialogPaymentSheet!!.findViewById(R.id.ll_paymentlist) as LinearLayout
            recyPaymentList =
                dialogPaymentSheet!!.findViewById(R.id.recyPaymentList) as RecyclerView


            // txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())

            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    Log.e(TAG, "9451  ")
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                Log.e(TAG, "9452  ")
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG, "9453  ")
                Log.e(TAG, "payAmnt         475    " + payAmnt)
                //  txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))
                ///////////////////////////////////////////          /     txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((DecimalToWordsConverter.commaRemover(tv_NetAmount!!.text.toString()).toFloat()) - pay.toFloat()))
                Log.e(TAG, "9454  ")
            } else {
                Log.e(TAG, "9455  ")
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                ///////////////////////////////////////             txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())
                Log.e(TAG, "9456  ")
            }

            edtPayMethod!!.setOnClickListener {
                Config.disableClick(it)
                paymentCount = 0
                getPaymentList()

            }

            img_PayAdd!!.setOnClickListener {
                validateAddPayment(it)
            }

            img_PayRefresh!!.setOnClickListener {
                arrAddUpdate = "0"
                edtPayMethod!!.setText("")
                edtPayRefNo!!.setText("")
                edtPayAmount!!.setText("")

                if (arrPayment.length() > 0) {
                    var payAmnt = 0.00
                    for (i in 0 until arrPayment.length()) {
                        //apply your logic
                        val jsonObject = arrPayment.getJSONObject(i)
                        payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                    }
                    /////////////////////////                 txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - payAmnt.toFloat()))
                } else {
                    /////////////////////////////                  txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())
                }
            }

            btnApply!!.setOnClickListener {
                val payAmnt =
                    DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                if ((payAmnt.toFloat()).equals("0.00".toFloat())) {
                    Log.e(TAG, "801 payAmnt  0.00  " + payAmnt.toFloat())
                    applyMode = 1
                    dialogPaymentSheet!!.dismiss()
                    for (i in 0 until arrPayment.length()) {
                        var empModel = arrPayment.getJSONObject(i)
                        val jsonObject = JSONObject()
                        jsonObject.put("PaymentMethod", empModel.getString("MethodID"))
                        jsonObject.put("PAmount", empModel.getString("Method"))
                        jsonObject.put("Refno", empModel.getString("RefNo"))
                        arrPaymentFinal.put(jsonObject)
                    }
                    Log.v("sdfsdfsdfds", "arr  " + arrPaymentFinal.toString())
                } else {
                    Log.e(TAG, "801 payAmnt  0.0clhghfoij    " + payAmnt.toFloat())
                    Config.snackBarWarning(context, it, "Balance Amount should be zero")
                }
            }


            dialogPaymentSheet!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "1012 Exception  " + e.toString())
        }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG, "28301    " + outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG, "28302    ")

            when {
                editable === edtPayMethod!!.editableText -> {
                    Log.e(TAG, "283021    ")
                    if (edtPayMethod!!.text.toString().equals("")) {
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                    } else {
                        //   til_DeliveryDate!!.isErrorEnabled = false
                        txt_pay_method!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.black
                            )
                        )
                    }


                }

                editable === edtPayAmount!!.editableText -> {

                    try {
                        var strAmnt = ""
                        Log.e(TAG, "283021    " + edtPayAmount!!.text.toString())
                        edtPayAmount!!.removeTextChangedListener(this)
                        if (edtPayAmount!!.text.toString().equals(".")) {
                            strAmnt = "0.00"
                        } else if (edtPayAmount!!.text.toString().equals("")) {
                            strAmnt = "0.00"
                        } else {
                            strAmnt =
                                DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())
                        }

                        val edtAmt = DecimelFormatters.set2DecimelPlace(strAmnt!!.toFloat())
                        var txt = edtPayAmount!!.text.toString()
                        // Log.e(TAG,"2830213    "+  DecimalToWordsConverter.getDecimelFormateForEditText(txt))

                        //  edtPayAmount!!.setText(DecimalToWordsConverter.getDecimelFormateForEditText(txt))
                        if (edtPayAmount!!.text.toString().equals("")) {
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                        } else {
                            //   til_DeliveryDate!!.isErrorEnabled = false
                            edtPayAmount!!.setText(
                                DecimalToWordsConverter.getDecimelFormateForEditText(
                                    strAmnt!!
                                )
                            )
                            txt_pay_Amount!!.setTextColor(
                                ContextCompat.getColorStateList(
                                    context,
                                    R.color.black
                                )
                            )
                        }
                        edtPayAmount!!.setSelection(edtPayAmount!!.length())
                        edtPayAmount!!.addTextChangedListener(this)
                    } catch (e: Exception) {
                        Log.e(TAG, "283021 dtrg   " + e.toString())
                    }


                }

                editable === txtPayBalAmount!!.editableText -> {
                    Log.e(TAG, "283021    ")
                    val payAmnt = DecimelFormatters.set2DecimelPlace(
                        DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())
                            .toFloat()
                    )
                    if ((payAmnt.toFloat()).equals("0.00".toFloat())) {
                        Log.e(TAG, "801 payAmnt  0.00  " + payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.black
                            )
                        )
                    } else {
                        Log.e(TAG, "801 payAmnt  0.0clhghfoij    " + payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.color_mandatory
                            )
                        )

                    }

                }


            }

        }
    }

    private fun validateAddPayment(view: View) {
        var balAmount =
            (DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())).toFloat()
        //  var payAmount = edtPayAmount!!.text.toString()
        var payAmount = DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())


        Log.e(TAG, "110   balAmount   : " + balAmount)
        Log.e(TAG, "110   payAmount   : " + payAmount)
        var hasId = hasPayMethod(arrPayment, "MethodID", ID_PaymentMethod!!)

        if (ID_PaymentMethod.equals("")) {
            Log.e(TAG, "110   Valid   : Select Payment Method")
            edtPayMethod!!.setError("Select Payment Method", null)
            txt_pay_method!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(context, view, "Select Payment Method")
        } else if (hasId == false && arrAddUpdate.equals("0")) {
            txt_pay_method!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(context, view, "PaymentMethod Already exits")
        } else if (payAmount.equals("")) {
//            else if (edtPayAmount!!.text.toString().equals("")){
            txt_pay_Amount!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Log.e(TAG, "110   Valid   : Enter Amount")
            Config.snackBarWarning(context, view, "Enter Amount")

        } else if (balAmount < payAmount.toFloat()) {
            Log.e(TAG, "110   Valid   : Enter Amount DD")
            txt_pay_Amount!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(
                context,
                view,
                "Amount should be less than or equal to Bal. Amount"
            )
        } else {

            if (arrAddUpdate.equals("0")) {

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount - payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt
                val jObject = JSONObject()
                jObject.put("MethodID", ID_PaymentMethod)
                jObject.put("Method", edtPayMethod!!.text.toString())
                jObject.put("RefNo", edtPayRefNo!!.text.toString())
                // jObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jObject.put("Amount", DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))
                arrPayment!!.put(jObject)
            }
            if (arrAddUpdate.equals("1")) {

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount - payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt

                val jsonObject = arrPayment.getJSONObject(arrPosition!!)
                jsonObject.put("MethodID", ID_PaymentMethod)
                jsonObject.put("Method", edtPayMethod!!.text.toString())
                jsonObject.put("RefNo", edtPayRefNo!!.text.toString())
                //   jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jsonObject.put("Amount", DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))

                arrAddUpdate = "0"


            }

            applyMode = 0
            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
            } else {
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
            }


        }

        Log.e(TAG, "110  arrPayment  :  " + arrPayment)
    }

    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@ServiceFollowUPActiivty, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@ServiceFollowUPActiivty)
    }

    private fun loadOtherCharge() {
        TODO("Not yet implemented")
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker = view.findViewById<DatePicker>(R.id.date_Picker1)

        date_Picker.maxDate = System.currentTimeMillis()


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }


                if (DateType == 0) {
                    tie_DateAttended!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getProductWiseComplaint(FK_Category: String, FK_Product: String) {
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


    private fun getActionTakenAction(FK_Category: String, FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                actionTakenViewModel.getActionTakenAction(
                    this, FK_Product, FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        actionTypeActionList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (actionTypeActionList.length() > 0) {

                                            actionTakenActionPopUp()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
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

    private fun getLeadAction(FK_Category: String, FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadActionViewModel.getActionTakenAction(
                    this, FK_Product, FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        leadActionList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (leadActionList.length() > 0) {

                                            LeadActionPopup()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
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

    private fun getOtherCharges() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherChargesViewViewModel.getActionTakenAction(
                    this
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("OtherChargeDetails")
                                        otherChargeList =
                                            jobjt.getJSONArray("OtherChargeDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (otherChargeList.length() > 0) {

                                            otherChargesPopup()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
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

                if (modeTab == 0){
                    valiadateServiceAttended()
                }
                else if (modeTab == 1){
                   validateService3()
                }


            }

            R.id.txt_previous -> {
                modeTab = modeTab-1
                loadlayout()
//                ll_service_Attended!!.visibility = View.VISIBLE
//                ll_Service3!!.visibility = View.GONE
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

    private fun actionTakenActionPopUp() {
        Log.e(TAG, "864  ")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.action_taken_action_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            actionTakeActionFilter =
                ActionTakeActionFilter(this@ServiceFollowUPActiivty, actionTypeActionList!!)
            recyFollowupAttended!!.adapter = actionTakeActionFilter
            actionTakeActionFilter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun LeadActionPopup() {
        Log.e(TAG, "864  ")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.action_taken_action_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            leadActionAdapter = LeadActionAdapter(this@ServiceFollowUPActiivty, leadActionList!!)
            recyFollowupAttended!!.adapter = leadActionAdapter
            leadActionAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun otherChargesPopup() {
        Log.e(TAG, "864  ")
        try {
            otherChargesFinalList.clear()
            for (i in 0 until otherChargeList.length()) {
                var empModel = otherChargeList.getJSONObject(i)
                otherChargesFinalList!!.add(
                    OtherChargesMainModel(
                        empModel.getString("ID_OtherChargeType"),
                        empModel.getString("OctyName"),
                        empModel.getString("OctyTransType"),
                        ""
                    )
                )
            }
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.other_charges_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)
            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            otherChargesAdapterAdapter =
                OtherChargesAdapterAdapter(this@ServiceFollowUPActiivty, otherChargesFinalList!!)
            recyFollowupAttended!!.adapter = otherChargesAdapterAdapter
            otherChargesAdapterAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
                otherChargesFinalList = otherChargesAdapterAdapter!!.mList
                var totalAmount=0
                for (i in 0 until otherChargesFinalList.size) {
                    val empModel = otherChargesFinalList[i]
                    val jsonObject = JSONObject()
                    jsonObject.put("ID_OtherChargeType", empModel.ID_OtherChargeType)
                    jsonObject.put("OctyTransType", empModel.OctyTransType)
                    if (empModel.OctyAmount.equals("")) {
                        jsonObject.put("OctyAmount", "0")
                    } else {
                        jsonObject.put("OctyAmount", empModel.OctyAmount)
                    }
                    jsonObject.put("TransTypeID", empModel.OctyTransType)
                    arrOtherChargeFinal.put(jsonObject)
                }
                Log.v("sfsdfsdfsd", "list" + arrOtherChargeFinal.toString())
            }
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
        if (data.equals("action_taken_action")) {
            modEditPosition = position
            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product
            actionTakenActioncouny = 0
            getActionTakenAction(FK_Category!!, FK_Product!!)
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

        if (data.equals("actionTakenActionFilter")) {
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = actionTypeActionList.getJSONObject(position)
            var empModel = actionTakenSelected[modEditPosition]
            empModel.actionName = jsonObject.getString("NxtActnName")
            empModel.actionStatus = jsonObject.getString("Status")
            if (!rcyler_actionTaken!!.isComputingLayout && rcyler_actionTaken!!.scrollState == SCROLL_STATE_IDLE) {
                actionTakenAdapter!!.notifyItemChanged(modEditPosition)
            }

        }
        if (data.equals("leadActionAdapterClick")) {
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = leadActionList.getJSONObject(position)
            var empModel = actionTakenSelected[modEditPosition]
            empModel.leadAction = jsonObject.getString("NxtActnName")
            empModel.leadActionStatus = jsonObject.getString("Status")
            actionTakenAdapter!!.notifyItemChanged(modEditPosition)
        }
        if (data.equals("check_click")) {
            var empModel = actionTakenSelected[position]
            Log.v("sdfsdfdsfds","check value1 "+empModel.ProvideStandBy)
            if (empModel.ProvideStandBy.equals("true")) {
                empModel.ProvideStandBy = "false"
            } else {
                empModel.ProvideStandBy = "true"
            }
        }


        if (data.equals("lead_action")) {
            modEditPosition = position
            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product
            actionTakenActioncouny = 0
            getLeadAction(FK_Category!!, FK_Product!!)
        }
        if (data.equals("paymentMethod")) {
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod = jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
        }
        if (data.equals("deleteArrayList")) {


            val jsonObject = arrPayment.getJSONObject(position)
            var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
            var Amount = (jsonObject!!.getString("Amount")).toFloat()

            arrPayment.remove(position)
            adapterPaymentList!!.notifyItemRemoved(position)

            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
            } else {
                ll_paymentlist!!.visibility = View.GONE
            }
            applyMode = 0
            txtPayBalAmount!!.text = (balAmount + Amount).toString()
        }
        if (data.equals("editArrayList")) {
            try {
                arrAddUpdate = "1"
                arrPosition = position
                val jsonObject = arrPayment.getJSONObject(position)
                ID_PaymentMethod = jsonObject!!.getString("MethodID")
                edtPayMethod!!.setText("" + jsonObject!!.getString("Method"))
                edtPayRefNo!!.setText("" + jsonObject!!.getString("RefNo"))
                edtPayAmount!!.setText("" + jsonObject!!.getString("Amount"))


                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG, "payAmnt         475    " + payAmnt)
                //////////////               txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat() + (jsonObject!!.getString("Amount").toFloat())))

            }
            catch (e:Exception)
            {
            }
        }
//        24-10-2023 Ranjith
        if (data.equals("addService3Click")){

            FK_Product_Pos  =  position
            FK_Product_ID  =  serviceTab3MainModel[position].FK_Product
            Log.e(TAG,"10491   "+FK_Product_ID)
            addserviceMode = 0
            loadServiceList()

        }

        if (data.equals("addServiceType3Click")){

            modEditPosition = position
            serviceFollowUpServiceType = 0
            loadServiceType()
//            FK_Product_Pos  =  position
//            FK_Product_ID  =  serviceTab3MainModel[position].FK_Product
//            Log.e(TAG,"10491   "+FK_Product_ID)
//            addserviceMode = 0
//            loadServiceList()

        }

        if (data.equals("serviceTypeClick")){

            dialogServType!!.dismiss()
            var jsonObj = jsonArrayServiceType.getJSONObject(position)
            var empModel = serviceTab3MainModel[modEditPosition]

            empModel.ID_ServiceType = jsonObj.getString("ServiceTypeId")
            empModel.ServiceType = jsonObj.getString("ServiceTypeName")

            serviceTab3Adapter!!.notifyItemChanged(modEditPosition)

        }


//        24-10-2023 Ranjith

    }

    private fun loadServiceType() {
        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        var ID_Branch =  FK_BranchSP.getString("FK_Branch", "0")
        var ID_Employee = FK_EmployeeSP.getString("FK_Employee", "0")
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
                    "0",
                    ID_Branch!!,
                    ID_Employee!!
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

                                        if (jsonArrayServiceType.length()>0){
                                            serviceTypePop(jsonArrayServiceType)
                                        }
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

    private fun serviceTypePop(jsonArrayServiceType: JSONArray) {

        try {
            dialogServType = Dialog(context)
            dialogServType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServType!!.setContentView(R.layout.pop_service_type)
            dialogServType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val recycServiceType = dialogServType!!.findViewById(R.id.recycServiceType) as RecyclerView

            val lLayout = GridLayoutManager(context, 1)
            recycServiceType!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = ServiceTypeAdapter(context, jsonArrayServiceType)
            recycServiceType!!.adapter = adapter
            adapter!!.setClickListener(this@ServiceFollowUPActiivty)

            dialogServType!!.show()
            dialogServType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogServType!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }catch (e: Exception){

        }

    }


    private fun loadServiceList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                addServiceViewModel.getAddService(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.e("10701    ", "msg")
                                if (addserviceMode == 0) {
                                    addserviceMode++
                                    val jObject = JSONObject(msg)
                                    Log.e("10702    ", ""+jObject)
                                    Log.e(TAG, "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AddedService")
                                        addserviceArrayList= jobjt.getJSONArray("AddedServiceList")
                                        addServiceDetailMode.clear()
                                        if (addserviceArrayList.length() > 0){
                                            for (i in 0 until addserviceArrayList.length()) {
                                               var  jsonObject = addserviceArrayList.getJSONObject(i)
                                                addServiceDetailMode.add(AddServiceDetailMode(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),
                                                    jsonObject.getString("FK_TaxGroup"),jsonObject.getString("TaxPercentage"),(jsonObject.getString("ServiceChargeIncludeTax").toBoolean()),false))
                                            }

                                        }

                                        if (addServiceDetailMode.size > 0){
                                            addServicePopup()
                                        }

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

    private fun addServicePopup() {
        try {

            dialogAddserviceSheet = Dialog(this)
            dialogAddserviceSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAddserviceSheet!! .setContentView(R.layout.servicelist_popup)
            dialogAddserviceSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogAddserviceSheet!!.setCancelable(false)

            var recycAdpService = dialogAddserviceSheet!! .findViewById(R.id.recycAdpService) as RecyclerView
            var tv_cancel = dialogAddserviceSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_apply = dialogAddserviceSheet!! .findViewById(R.id.tv_apply) as TextView

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recycAdpService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ServiceDetailAdapter(this@ServiceFollowUPActiivty, addServiceDetailMode!!)
            recycAdpService!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

            val window: Window? = dialogAddserviceSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            tv_cancel!!.setOnClickListener {
                dialogAddserviceSheet!!.dismiss()
            }

            tv_apply!!.setOnClickListener {
                var hasId =  hasServiceTrue(addServiceDetailMode!!)
                if (hasId){

//                    var hasIds1 = hasServiceTrue1(addServiceDetailMode!!)
//
//                    if (hasIds1){
//                        Log.e(TAG,"1308   "+"True")
//
//                    }else{
//                        Log.e(TAG,"1308   "+"Fasle")
//                    }

                    var posAdd = 0
                    for (i in 0 until serviceTab3MainModel.size) {
                        var empModel = serviceTab3MainModel[i]
                        if (empModel.FK_Product.equals(FK_Product_ID)){
                            posAdd = i+1
                        }
                    }

                    for (i in 0 until addServiceDetailMode.size) {
                        if (addServiceDetailMode[i].isChecked){
                            Log.e(TAG,"10492   "+FK_Product_Pos)
                            var empModel = serviceTab3MainModel[FK_Product_Pos!!]
                            var empModelSub = addServiceDetailMode[i]

                            Log.e(TAG,"117111   "+addServiceDetailMode[i].Service)
                            serviceTab3MainModel!!.add(posAdd,ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","1",
                                empModelSub.ID_Services,empModelSub.Service,"0","","0.00","0.00",
                                "0.00","",false,empModelSub.FK_TaxGroup,empModelSub.TaxPercentage,empModelSub.ServiceChargeIncludeTax))
                            serviceTab3Adapter!!.notifyItemInserted(posAdd)
                            posAdd++

                        }
                    }
                    dialogAddserviceSheet!!.dismiss()



                }



//                rcyler_service3!!.adapter = serviceTab3Adapter

            }

            dialogAddserviceSheet!!.show()

        }catch (e : Exception){

        }

    }

    private fun hasServiceTrue(addServiceDetailMode: ArrayList<AddServiceDetailMode>): Boolean {

        var isChecked = false
        for (i in 0 until addServiceDetailMode.size) {  // iterate through the JsonArray
            Log.e(TAG,"101666     "+addServiceDetailMode.get(i).isChecked)

            if (addServiceDetailMode.get(i).isChecked){
                isChecked = true
            }

        }
        return isChecked
    }

    private fun hasServiceTrue1(addServiceDetailMode: ArrayList<AddServiceDetailMode>): Boolean {

        var isChecked = true
        for (i in 0 until serviceTab3MainModel.size) {  // iterate through the JsonArray
            Log.e(TAG,"12344    "+FK_Product_ID)
            Log.e(TAG,"12344    "+serviceTab3MainModel.get(i).FK_Product)
            for (j in 0 until addServiceDetailMode.size) {
                if (serviceTab3MainModel.get(i).FK_Product.equals(FK_Product_ID) && serviceTab3MainModel.get(i).ID_Service.equals(addServiceDetailMode.get(j).ID_Services)){
                    isChecked = false
                }
            }

        }
        return isChecked
    }



    private fun valiadateServiceAttended() {


        var hasId =  hasTrue(modelServicesListDetails!!)
        Log.e(TAG,"1016661     "+hasId)
        if (hasId){
            Log.e(TAG,"10000666666   Checked Box MArked")
//            ll_service_Attended!!.visibility = View.GONE
//            ll_Service3!!.visibility = View.VISIBLE

            modeTab = modeTab+1
            Log.e(TAG,"1016661     modeTab "+modeTab)
            loadlayout()

            // 24-10-2023 Ranjith
            serviceTab3MainModel.clear()
            Log.e(TAG,"1034441     "+modelServicesListDetails.size)
            Log.e(TAG,"1034442     "+serviceTab3MainModel.size)
            for (i in 0 until modelServicesListDetails.size) {
                var empModel = modelServicesListDetails[i]
                if (empModel.isChekecd){
                    serviceTab3MainModel!!.add(ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","",
                        "0","","0","","0.00","0.00","0.00","",false,"0","0",false))
                }

            }

            serviceDetailsArray = JSONArray()
            for (i in 0 until modelServicesListDetails.size) {

                if (modelServicesListDetails[i].isChekecd){
                    Log.e(TAG,"1416661  FK_Product                    "+modelServicesListDetails[i].FK_Product)
                    Log.e(TAG,"1416662  Product                       "+modelServicesListDetails[i].Product)
                    Log.e(TAG,"1416663  ID_ComplaintList              "+modelServicesListDetails[i].ID_ComplaintList)
                    Log.e(TAG,"1416664  ID_CustomerWiseProductDetails "+modelServicesListDetails[i].ID_CustomerWiseProductDetails)

                    val jObject = JSONObject()
                    jObject.put("ID_Product", (modelServicesListDetails[i].FK_Product))
                    jObject.put("Product", (modelServicesListDetails[i].Product))
                    jObject.put("ID_ComplaintList", (modelServicesListDetails[i].ID_ComplaintList))
                    jObject.put("Description", (modelServicesListDetails[i].Description))
                    jObject.put("ID_CustomerWiseProductDetails", (modelServicesListDetails[i].ID_CustomerWiseProductDetails))
                    jObject.put("FK_ProductNumberingDetails", ("0"))

                    serviceDetailsArray.put(jObject)
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
            Toast.makeText(applicationContext,"No Check box marked",Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateService3() {

        var hasId =  hasTrue3(serviceTab3MainModel!!)
        if (hasId){
            Log.e(TAG,"145888   Checked Box MArked")
            serviceIncentiveArray = JSONArray()
            for (i in 0 until serviceTab3MainModel.size) {

                if (serviceTab3MainModel[i].isChecked){


                    val jObject = JSONObject()
                    jObject.put("ID_Service", (serviceTab3MainModel[i].ID_Service))
                    jObject.put("ID_MasterProduct", (serviceTab3MainModel[i].FK_Product))
                    jObject.put("ServiceMod", (serviceTab3MainModel[i].ID_ServiceType))
                    jObject.put("ServiceCost", (serviceTab3MainModel[i].ServiceCost))
                    jObject.put("ServiceTax", (serviceTab3MainModel[i].TaxAmount))
                    jObject.put("ServiceNetAmount", (serviceTab3MainModel[i].NetAmount))

                    serviceIncentiveArray.put(jObject)

                }
            }
            modeTab=4
            loadlayout()
            Log.e(TAG,"143777772     "+serviceIncentiveArray)
        }else{
            Log.e(TAG,"145888   No Checked Box MArked")
        }

    }

    private fun hasTrue3(serviceTab3MainModel: ArrayList<ServiceTab3MainModel>): Boolean {
        var isChecked = true
        for (i in 0 until serviceTab3MainModel.size) {
            if (serviceTab3MainModel.get(i).isChecked){
                isChecked = true
                if (serviceTab3MainModel.get(i).ID_ServiceType.equals("0") || serviceTab3MainModel.get(i).ID_ServiceType.equals("")){
                    isChecked = false
                    Toast.makeText(applicationContext,"Select Service Type",Toast.LENGTH_SHORT).show()
                    break
                }
            }
        }
        return isChecked
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

    override fun onBackPressed() {
        if (modeTab == 0){
            finish()
        }else{
            modeTab = modeTab-1
            loadlayout()
        }
    }
}