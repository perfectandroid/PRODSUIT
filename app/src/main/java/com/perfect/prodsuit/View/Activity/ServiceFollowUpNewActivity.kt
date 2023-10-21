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
import android.location.LocationManager
import android.nfc.cardemulation.CardEmulation
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.View.ServiceBillTypeViewModel
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ServiceFollowUpNewActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListenerValue, ItemClickListener {

    var TAG = "ServiceFollowUpNewActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null


    var ReqMode: String? = ""
    var SubMode: String? = ""

    private var card_start: CardView? = null
    private var card_hold: CardView? = null
    private var card_stop: CardView? = null
    private var card_restart: CardView? = null
    private var crdv_Service: CardView? = null
    private var crdv_warrantyamc: CardView? = null

    private var tv_ServiceCost: TextView? = null
    private var tv_replaced_product_cost: TextView? = null
    private var tv_attendance: TextView? = null
    private var tv_Action_Taken: TextView? = null

    private var lin_service_cost: LinearLayout? = null
    private var lin_service_view: LinearLayout? = null
    private var lin_replacePoductCost: LinearLayout? = null
    private var lin_attendance: LinearLayout? = null
    private var lin_Action_Taken: LinearLayout? = null
    private var ll_servicehist: LinearLayout? = null
    private var ll_WarantyAMC: LinearLayout? = null
    private var ll_ClosedTicket: LinearLayout? = null




    private var lin_add_service: LinearLayout? = null
    private var lin_add_replaced_product: LinearLayout? = null

    var serviceAttendedMode: String? = "1"
    var replacedProductMode: String? = "1"
    var attendanceMode: String? = "1"
    var actionTakenMode: String? = "1"



    private val START_LOCATION = 100
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1

    var runningStatus: String? = ""
    var journeyType:  String? = "0"
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
    private var recycler_service_cost: FullLenghRecyclertview? = null


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
    private var recyFollowupComponent: RecyclerView? = null



    val modelReplacedProduct = ArrayList<ModelReplacedProduct>()
    val modelReplacedProductTemp = ArrayList<ModelReplacedProductTemp>()
    private var recycleView_replaceproduct: FullLenghRecyclertview? = null
    var adapterReplacedProduct: ReplacedProductAdapter? = null

    lateinit var serviceFollowUpChangeModeViewModel: ServiceFollowUpChangeModeViewModel
    var serviceFollowUpChangeMmode = 0
    var jsonArrayChangeMode: JSONArray = JSONArray()

    lateinit var serviceFollowUpMoreReplacedProductsViewModel: ServiceFollowUpMoreReplacedProductsViewModel
    var serviceFollowUpMoreReplacedProduct = 0
    var jsonArrayReplacedProductMore: JSONArray = JSONArray()
    private var dialogRepPoduct: Dialog? = null

    var replaceProductPos = 0

    lateinit var serviceFollowProductListViewModel: ServiceFollowProductListViewModel
    var jsonArrayServiceFollowProductList: JSONArray = JSONArray()
    val modelServiceFollowProductList = ArrayList<ModelServiceFollowProductList>()
    val modelServiceFollowProductListTemp = ArrayList<ModelServiceFollowProductListTemp>()
    var serviceFollowUpProductList = 0
    private var dialogserviceFollowUpProductSheet : Dialog? = null




    lateinit var serviceFollowUpAttendanceListViewModel: ServiceFollowUpAttendanceListViewModel
    var serviceFollowUpAttendance = 0
    var serviceFollowUpAttendanceArrayList: JSONArray = JSONArray()
    val modelFollowUpAttendance = ArrayList<ModelFollowUpAttendance>()

    private var recyclerAttendance: RecyclerView? = null
    var adapterServiceFollowAttendance: ServiceFollowAttendanceAdapter? = null


    private var tie_Customer_Note: TextInputEditText? = null
    private var tie_Employee_Note: TextInputEditText? = null
    private var tie_Visited_Date: TextInputEditText? = null
    private var tie_Security_Amount: TextInputEditText? = null
    private var tie_Action: TextInputEditText? = null
    private var tie_Payment_Method: TextInputEditText? = null

    private var tie_Lead_Action: TextInputEditText? = null
    private var tie_Action_Type: TextInputEditText? = null
    private var tie_Follow_Date: TextInputEditText? = null
    private var tie_Assigned_To: TextInputEditText? = null
    private var tie_Bill_Type: TextInputEditText? = null

    private var til_Visited_Date: TextInputLayout? = null
    private var til_Action: TextInputLayout? = null
    private var til_Lead_Action: TextInputLayout? = null
    private var til_Action_Type: TextInputLayout? = null
    private var til_Follow_Date: TextInputLayout? = null
    private var til_Bill_Type: TextInputLayout? = null
    private var linear_afa: LinearLayout? = null

    lateinit var serviceFollowUpActionViewModel: ServiceFollowUpActionViewModel
    lateinit var serviceFollowUpActionArrayList : JSONArray
    lateinit var serviceFollowUpActionSort : JSONArray
    private var dialogServiceFollowupAction : Dialog? = null
    var recyserviceFollowupAction: RecyclerView? = null
    var serviceFollowUpAction = 0

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null
    var followUpAction = 0

    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList: JSONArray
    lateinit var followUpTypeSort: JSONArray
    private var dialogFollowupType: Dialog? = null
    var recyFollowupType: RecyclerView? = null
    var followUpType = 0

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null
    var employeeMode = 0

    var ID_Action_Status: String? = ""
    var ID_Action: String? = ""

    var ID_LeadAction_Status: String? = ""
    var ID_LeadAction: String? = ""
    var ID_ActionType: String? = ""
    var ID_AssignedTo: String? = ""


    private var dialogPaymentSheet : Dialog? = null
    private var edtPayMethod: EditText? = null
    private var edtPayRefNo: EditText? = null
    private var edtPayAmount: EditText? = null

    private var txtPayBalAmount: TextView? = null
    private var img_PayAdd: ImageView? = null
    private var img_PayRefresh: ImageView? = null
    private var btnApply: Button? = null
    var arrAddUpdate: String? = "0"
    var strBalance: String? = "27412.25"
    var strInsAmount: String? = "0.00"
    var strFine: String? = "0.00"
    var arrPosition: Int? = 0
    var applyMode = 0

    var arrPayment = JSONArray()
    internal var recyPaymentList: RecyclerView? = null
    internal var ll_paymentlist: LinearLayout? = null
    var adapterPaymentList : PaymentListAdapter? = null

    private var txt_pay_method: TextView? = null
    private var txt_pay_Amount: TextView? = null
    private var txt_bal_Amount: TextView? = null

    var paymentCount = 0
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    lateinit var paymentMethodeArrayList: JSONArray
    private var dialogPaymentMethod: Dialog? = null
    var recyPaymentMethod: RecyclerView? = null

    lateinit var serviceBillTypeViewModel: ServiceBillTypeViewModel
    lateinit var billTypeArrayList: JSONArray
    private var dialogBillType: Dialog? = null
    var recyBillType: RecyclerView? = null
    var billTypeCount = 0


    var ID_Billtype: String? = ""
    var ID_PaymentMethod: String? = ""
    var totRplacement = 0.0f
    var totPayAmount = 0.0f

    var dateMode = 0 // 0 = VisitedDate , 1 = FollowUpDate

    private var tv_Msubmit: TextView? = null
    private var tv_Mcancel: TextView? = null


        // SAVE
    var saveServiceAttendedArray = JSONArray()
    var saveReplacedeProductArray = JSONArray()
    var saveAttendedEmployeeArray = JSONArray()
    var savePaymentDetailArray = JSONArray()

    var strCustomerNote: String? = ""
    var strEmployeeNote: String? = ""
    var strVisitedDate: String? = ""
    var strFollowUpDate: String? = ""
    var strReplacementAmount: String? = ""
    var strTotalAmount: String? = ""
    internal var jsonObjectList: JSONObject? = null
    val PERMISSION_ID = 42

    lateinit var saveServiceFollowUpViewModel: SaveServiceFollowUpViewModel
    var saveCount = 0

    lateinit var followupStatusUpdateViewModel: FollowupStatusUpdateViewModel
    var statusCount = 0
    var saveAttendanceMark = false

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
        serviceFollowUpAttendanceListViewModel = ViewModelProvider(this).get(ServiceFollowUpAttendanceListViewModel::class.java)
        serviceFollowUpActionViewModel = ViewModelProvider(this).get(ServiceFollowUpActionViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        serviceFollowProductListViewModel = ViewModelProvider(this).get(ServiceFollowProductListViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceBillTypeViewModel = ViewModelProvider(this).get(ServiceBillTypeViewModel::class.java)
        saveServiceFollowUpViewModel = ViewModelProvider(this).get(SaveServiceFollowUpViewModel::class.java)
        followupStatusUpdateViewModel = ViewModelProvider(this).get(FollowupStatusUpdateViewModel::class.java)


        setRegViews()
        getSharedPrefValues()
        checkAttendance()
        try {
            runningStatus = intent.getStringExtra("runningStatus")
            customer_service_register = intent.getStringExtra("customer_service_register").toString()
            jsonObjectList = JSONObject(intent.getStringExtra("jsonObject").toString());
        }catch (e : Exception){

        }

        setRunningStatus()

    }

    private fun getSharedPrefValues() {
        val FK_BranchCodeUserSP = this.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()

        ID_AssignedTo= FK_EmployeeSP.getString("FK_Employee", null).toString()
        tie_Assigned_To!!.setText(UserNameSP.getString("UserName", null))

    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        card_start = findViewById<CardView>(R.id.card_start)
        card_hold = findViewById<CardView>(R.id.card_hold)
        card_stop = findViewById<CardView>(R.id.card_stop)
        card_restart = findViewById<CardView>(R.id.card_restart)

        crdv_Service = findViewById<CardView>(R.id.crdv_Service)
        crdv_warrantyamc = findViewById<CardView>(R.id.crdv_warrantyamc)



        tv_ServiceCost = findViewById<TextView>(R.id.tv_ServiceCost)
        tv_replaced_product_cost = findViewById<TextView>(R.id.tv_replaced_product_cost)
        tv_attendance = findViewById<TextView>(R.id.tv_attendance)
        tv_Action_Taken = findViewById<TextView>(R.id.tv_Action_Taken)
        tv_Msubmit = findViewById<TextView>(R.id.tv_Msubmit)
        tv_Mcancel = findViewById<TextView>(R.id.tv_Mcancel)

        lin_service_cost = findViewById<LinearLayout>(R.id.lin_service_cost)
        lin_service_view = findViewById<LinearLayout>(R.id.lin_service_view)
        lin_replacePoductCost = findViewById<LinearLayout>(R.id.lin_replacePoductCost)
        lin_attendance = findViewById<LinearLayout>(R.id.lin_attendance)
        lin_Action_Taken = findViewById<LinearLayout>(R.id.lin_Action_Taken)
        ll_servicehist = findViewById<LinearLayout>(R.id.ll_servicehist)
        ll_WarantyAMC= findViewById<LinearLayout>(R.id.ll_WarantyAMC)
        ll_ClosedTicket= findViewById<LinearLayout>(R.id.ll_ClosedTicket)

        lin_add_service = findViewById<LinearLayout>(R.id.lin_add_service)
        lin_add_replaced_product = findViewById<LinearLayout>(R.id.lin_add_replaced_product)

        tie_Customer_Note = findViewById<TextInputEditText>(R.id.tie_Customer_Note)
        tie_Employee_Note = findViewById<TextInputEditText>(R.id.tie_Employee_Note)
        tie_Visited_Date = findViewById<TextInputEditText>(R.id.tie_Visited_Date)
        tie_Security_Amount = findViewById<TextInputEditText>(R.id.tie_Security_Amount)
        tie_Action = findViewById<TextInputEditText>(R.id.tie_Action)
        tie_Payment_Method = findViewById<TextInputEditText>(R.id.tie_Payment_Method)

        tie_Lead_Action = findViewById<TextInputEditText>(R.id.tie_Lead_Action)
        tie_Action_Type = findViewById<TextInputEditText>(R.id.tie_Action_Type)
        tie_Follow_Date = findViewById<TextInputEditText>(R.id.tie_Follow_Date)
        tie_Assigned_To = findViewById<TextInputEditText>(R.id.tie_Assigned_To)
        tie_Bill_Type = findViewById<TextInputEditText>(R.id.tie_Bill_Type)

        til_Visited_Date = findViewById<TextInputLayout>(R.id.til_Visited_Date)
        til_Action = findViewById<TextInputLayout>(R.id.til_Action)
        til_Lead_Action = findViewById<TextInputLayout>(R.id.til_Lead_Action)
        til_Action_Type = findViewById<TextInputLayout>(R.id.til_Action_Type)
        til_Follow_Date = findViewById<TextInputLayout>(R.id.til_Follow_Date)
        til_Bill_Type = findViewById<TextInputLayout>(R.id.til_Bill_Type)

        til_Visited_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Lead_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Action_Type!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Follow_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        linear_afa = findViewById<LinearLayout>(R.id.linear_afa)



        recycler_service_cost = findViewById<FullLenghRecyclertview>(R.id.recycler_service_cost)
        recycleView_replaceproduct = findViewById<FullLenghRecyclertview>(R.id.recycleView_replaceproduct)
        recyclerAttendance = findViewById<RecyclerView>(R.id.recyclerAttendance)
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
        recyclerAttendance!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent?): Boolean {
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                return true
            }
        })



        tie_Visited_Date!!.setOnClickListener(this)
        tie_Security_Amount!!.setOnClickListener(this)
        tie_Action!!.setOnClickListener(this)
        tie_Payment_Method!!.setOnClickListener(this)
        tie_Lead_Action!!.setOnClickListener(this)
        tie_Action_Type!!.setOnClickListener(this)
        tie_Follow_Date!!.setOnClickListener(this)
        tie_Assigned_To!!.setOnClickListener(this)
        tie_Bill_Type!!.setOnClickListener(this)

        card_start!!.setOnClickListener(this)
        card_hold!!.setOnClickListener(this)
        card_stop!!.setOnClickListener(this)
        card_restart!!.setOnClickListener(this)

        tv_ServiceCost!!.setOnClickListener(this)
        tv_replaced_product_cost!!.setOnClickListener(this)
        tv_attendance!!.setOnClickListener(this)
        tv_Action_Taken!!.setOnClickListener(this)

        tv_Msubmit!!.setOnClickListener(this)
        tv_Mcancel!!.setOnClickListener(this)

        lin_add_service!!.setOnClickListener(this)
        lin_add_replaced_product!!.setOnClickListener(this)

        ll_servicehist!!.setOnClickListener(this)
        ll_WarantyAMC!!.setOnClickListener(this)
        ll_ClosedTicket!!.setOnClickListener(this)

        ll_WarantyAMC!!.visibility=View.GONE
        ll_ClosedTicket!!.visibility=View.GONE
        ll_servicehist!!.visibility=View.GONE

//        tie_Visited_Date!!.addTextChangedListener(watcher);
//        tie_Action!!.addTextChangedListener(watcher);
//        tie_Lead_Action!!.addTextChangedListener(watcher);
//        tie_Action_Type!!.addTextChangedListener(watcher);
//        tie_Follow_Date!!.addTextChangedListener(watcher);


        onTextChangedValues()

//        til_Lead_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
//        til_Lead_Action!!.setError("Enter or Select Customer ")

    }

    private fun onTextChangedValues() {
        tie_Visited_Date!!.addTextChangedListener(watcher);
        tie_Action!!.addTextChangedListener(watcher);
        tie_Lead_Action!!.addTextChangedListener(watcher);
        tie_Action_Type!!.addTextChangedListener(watcher);
        tie_Follow_Date!!.addTextChangedListener(watcher);

        getCurrentDate()
    }

    private fun getCurrentDate() {

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
//            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
//            val sdfTime1 = SimpleDateFormat("hh:mm aa")
//            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            tie_Visited_Date!!.setText(""+sdfDate1.format(newDate))
            tie_Follow_Date!!.setText(""+sdfDate1.format(newDate))
//            tie_FromDate!!.setText(""+sdfDate1.format(newDate))
//            //  strVisitDate = sdfDate2.format(newDate)
//
//            tie_Time!!.setText(""+sdfTime1.format(newDate))
//            //  strVisitTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun setRunningStatus() {
        if (runningStatus.equals("") || runningStatus.equals("0") || runningStatus.equals("4")) {
            card_start?.visibility = View.VISIBLE
            card_hold?.visibility = View.GONE
            card_stop?.visibility = View.GONE
            card_restart?.visibility = View.GONE
        }
        else if (runningStatus.equals("1")) {
            card_start?.visibility = View.GONE
            card_hold?.visibility = View.VISIBLE
            card_stop?.visibility = View.VISIBLE
            card_restart?.visibility = View.GONE
        }
        else if (runningStatus.equals("2")) {
            card_start?.visibility = View.GONE
            card_hold?.visibility = View.GONE
            card_stop?.visibility = View.VISIBLE
            card_restart?.visibility = View.VISIBLE
        }
        else if (runningStatus.equals("3")) {
            card_start?.visibility = View.GONE
            card_hold?.visibility = View.VISIBLE
            card_stop?.visibility = View.VISIBLE
            card_restart?.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.card_start -> {
                checkAttendance()
                if (saveAttendanceMark){
                    journeyType = "1"
                    if (checkLocationPermission()) {
                        confirmBottomSheet(journeyType)
                    }
                }

            }
            R.id.card_hold -> {
                checkAttendance()
                if (saveAttendanceMark){
                    journeyType = "2"
                    if (checkLocationPermission()) {
                        confirmBottomSheet(journeyType)
                    }
                }

            }

            R.id.card_restart -> {
                checkAttendance()
                if (saveAttendanceMark){
                    journeyType = "3"
                    if (checkLocationPermission()) {
                        confirmBottomSheet(journeyType)
                    }
                }

            }
            R.id.card_stop -> {
                checkAttendance()
                if (saveAttendanceMark){
                    journeyType = "4"
                    if (checkLocationPermission()) {
                        confirmBottomSheet(journeyType)
                    }
                }

            }


            R.id.lin_add_service -> {
                Config.disableClick(v)
                if (modelMoreServices.size > 0){
                    Log.e(TAG,"20261  ")
                    loadMoreServiceAttendedPopup(sortMoreServiceAttended)
                }
                else{
                    serviceFollowUpMoreService = 0
                    loadMoreServiceAttended()
                }

            }

            R.id.lin_add_replaced_product -> {
                Config.disableClick(v)

                if (modelServiceFollowProductList.size > 0){
                    Log.e(TAG,"4151  ")
                    loadMoreServiceFollowProductPopup(modelServiceFollowProductList)
                }
                else{
                    serviceFollowUpProductList = 0
                    loadServiceFollowUpProductList()
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
                    actionTakenMode = "1"
                }
                else {
                    serviceAttendedMode = "0"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }

                hideShowViews()
                if (modelServiceAttended.size == 0){
                    serviceFollowUpMappedService = 0
                    loadMappedeServiceAttended()
                }

            }

            R.id.tv_replaced_product_cost -> {

                if (replacedProductMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "0"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }
                hideShowViews()
                if (jsonArrayChangeMode.length() == 0){
                    serviceFollowUpChangeMmode = 0
                    loadChangeMode(0)
                }


                if (modelReplacedProduct.size == 0){
                    serviceFollowUpReplacedProduct = 0
                    loadMappedReplacedProducts()
                }
            }

            R.id.tv_attendance -> {

                if (attendanceMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "0"
                    actionTakenMode = "1"
                }
                hideShowViews()

                if (serviceFollowUpAttendanceArrayList.length() == 0){
                    serviceFollowUpReplacedProduct = 0
                    loadAttendance()
                }
            }

            R.id.tv_Action_Taken -> {

                if (actionTakenMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "0"
                }
                hideShowViews()

            }

            R.id.tie_Visited_Date -> {
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
            }

            R.id.tie_Action -> {
                Config.disableClick(v)
                serviceFollowUpAction = 0
                getServiceFollowupAction()
            }

            R.id.tie_Lead_Action -> {
                Config.disableClick(v)
                followUpAction = 0
                ReqMode = "17"
                SubMode = "1"
                getFollowupAction()
            }

            R.id.tie_Action_Type -> {
                  Config.disableClick(v)
                  followUpType = 0
                  getFollowupType()
            }

            R.id.tie_Assigned_To -> {
                Config.disableClick(v)
                employeeMode = 0
                getEmployee()
            }

            R.id.tie_Follow_Date -> {
                Config.disableClick(v)
                dateMode = 1
                openBottomDate()
            }

            R.id.tie_Bill_Type -> {
                Config.disableClick(v)
                billTypeCount = 0
                getBillType()

            }

            R.id.tie_Payment_Method -> {
                Config.disableClick(v)
                paymentMethodPopup()

            }


            R.id.tv_Msubmit -> {
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

//                for (i in 0 until modelReplacedProduct.size) {
//                    val ItemsAttend = modelReplacedProduct[i]
//                    Log.e(TAG,"297   "+i
//                            +"\n"+"isChecked       : "+ItemsAttend.isChecked
//                            +"\n"+"ID_OLD_Product  : "+ItemsAttend.ID_OLD_Product
//                            +"\n"+"OLD_Product     : "+ItemsAttend.OLD_Product
//                            +"\n"+"SPDOldQuantity  : "+ItemsAttend.SPDOldQuantity
//                            +"\n"+"Amount          : "+ItemsAttend.Amount
//                            +"\n"+"ID_Mode         : "+ItemsAttend.ID_Mode
//                            +"\n"+"ModeName        : "+ItemsAttend.ModeName
//                            +"\n"+"ID_Product      : "+ItemsAttend.ID_Product
//                            +"\n"+"Product         : "+ItemsAttend.Product
//                            +"\n"+"Replaced_Qty    : "+ItemsAttend.Replaced_Qty
//                            +"\n"+"ReplaceAmount   : "+ItemsAttend.ReplaceAmount
//                            +"\n"+"Remarks         : "+ItemsAttend.Remarks
//                            +"\n"+"isAdded         : "+ItemsAttend.isAdded)
//                }

              //  validatReplacedProduct(v)

                Log.e(TAG,"validatServiceAttended   778")

                checkAttendance()
                if (saveAttendanceMark){
                    if (runningStatus.equals("") || runningStatus.equals("0") || runningStatus.equals("4")){
                        journeyType = "1"
                        if (checkLocationPermission()) {
                            confirmBottomSheet(journeyType)
                        }
                    }else if (runningStatus.equals("2")){
                        journeyType = "3"
                        if (checkLocationPermission()) {
                            confirmBottomSheet(journeyType)
                        }
                    }else{
                        validatServiceAttended(v)
                    }
                }


            }

            R.id.tv_Mcancel -> {

               cancelBottom()
            }
            R.id.ll_WarantyAMC -> {

                val i = Intent(this@ServiceFollowUpNewActivity, WarrantyAMCActivity::class.java)
                startActivity(i)

            }
            R.id.ll_servicehist -> {

//                val i = Intent(this@ServiceFollowUpNewActivity, ServiceHistoryActivity::class.java)
//                startActivity(i)
            }
            R.id.ll_ClosedTicket -> {

                val i = Intent(this@ServiceFollowUpNewActivity, ClosedTicketActivity::class.java)
                startActivity(i)

            }
        }
    }


    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance){
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status","")
            if (status.equals("0") || status.equals("")){
                Common.punchingRedirectionConfirm(this,"","")
            }
            else if (status.equals("1")){
                saveAttendanceMark = true
            }

        }else{
            saveAttendanceMark = true
        }
    }

    private fun validatServiceAttended(v: View) {

        try {
            Log.e(TAG,"validatServiceAttended  693   ")
            saveServiceAttendedArray =  JSONArray()
            for (i in 0 until modelServiceAttended.size) {
                val ItemsModel = modelServiceAttended[i]
                if (ItemsModel.isChecked.equals("1")){

                    val jsonObject1 = JSONObject()
                    jsonObject1.put("ID_Services",ItemsModel.ID_Services)
                    jsonObject1.put("Remarks",ItemsModel.Remarks)
                    jsonObject1.put("ServiceCost",ItemsModel.ServiceCost)
                    jsonObject1.put("ServiceTaxAmount",ItemsModel.ServiceTaxAmount)
                    jsonObject1.put("ServiceNetAmount",ItemsModel.ServiceNetAmount)
                    jsonObject1.put("ServiceType",ItemsModel.ServiceTypeId)
                    saveServiceAttendedArray.put(jsonObject1)
                }
            }
            validatReplacedProduct(v)

        }catch (e:Exception){

        }

    }

    private fun validatReplacedProduct(v: View) {

        try {
            Log.e(TAG,"validatReplacedProduct  692   ")
            saveReplacedeProductArray =  JSONArray()
            var isProdct: Boolean = true
            for (i in 0 until modelReplacedProduct.size) {
                val ItemsModel = modelReplacedProduct[i]
                if (ItemsModel.isChecked.equals("1")){

                    if (!ItemsModel.ID_Product.equals("0") && ItemsModel.Replaced_Qty.equals("0")){
                        isProdct = false
                    }
                }
            }
            totRplacement = 0.0f
            if (isProdct!!){
                //  validateAttendedPerson(v)
                for (i in 0 until modelReplacedProduct.size) {
                    val ItemsModel = modelReplacedProduct[i]
                    if (ItemsModel.isChecked.equals("1")){
                        val jsonObject1 = JSONObject()

                        jsonObject1.put("ID_Product",ItemsModel.ID_OLD_Product)
                        jsonObject1.put("Quantity",ItemsModel.SPDOldQuantity)
                        jsonObject1.put("ReplaceAmount",ItemsModel.ReplaceAmount)
                        jsonObject1.put("RpdProductId",ItemsModel.ID_Product)
                        jsonObject1.put("RpdProductQty",ItemsModel.Replaced_Qty)
                        jsonObject1.put("RpdMRP",ItemsModel.MRPs)
                        jsonObject1.put("RpdProductAmount",ItemsModel.ReplaceAmount)
                        jsonObject1.put("Remarks",ItemsModel.Remarks)
                        jsonObject1.put("ID_Mode",ItemsModel.ID_Mode)
                        jsonObject1.put("RpdStockId",ItemsModel.StockId)
                        saveReplacedeProductArray.put(jsonObject1)

                        totRplacement = totRplacement+(ItemsModel.Amount).toFloat()
                        Log.e(TAG,"saveReplacedeProductArray  9610   "+totRplacement)
                    }
                }

                strTotalAmount = totRplacement.toString()
                Log.e(TAG,"saveReplacedeProductArray  6923   "+saveReplacedeProductArray)

                validateAttendedPerson(v)

            }else{

                serviceAttendedMode = "1"
                replacedProductMode = "0"
                attendanceMode = "1"
                actionTakenMode = "1"

                hideShowViews()
                if (jsonArrayChangeMode.length() == 0){
                    serviceFollowUpChangeMmode = 0
                    loadChangeMode(0)
                }

                if (modelReplacedProduct.size == 0){
                    serviceFollowUpReplacedProduct = 0
                    loadMappedReplacedProducts()
                }

                Config.snackBars(context,v,"Please Enter Replaced Quantity")
            }

            Log.e(TAG,"isProdct  6921   "+isProdct)
        }catch (e:Exception){

        }

    }

    private fun validateAttendedPerson(v: View) {

        try {
            Log.e(TAG,"validateAttendedPerson  686   ")
            saveAttendedEmployeeArray = JSONArray()
            for (i in 0 until modelFollowUpAttendance.size) {
                val ItemsModel = modelFollowUpAttendance[i]

                if (ItemsModel.isChecked.equals("1")){
                    val jsonObject1 = JSONObject()
                    jsonObject1.put("ID_Employee",ItemsModel.ID_Employee)
                    jsonObject1.put("EmployeeType",ItemsModel.ID_CSAEmployeeType)
                    saveAttendedEmployeeArray.put(jsonObject1)
                }
            }

            Log.e(TAG,"validateAttendedPerson  686   "+saveAttendedEmployeeArray)
            if (saveAttendedEmployeeArray.length() == 0){
                if (attendanceMode.equals("0")){
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "1"
                    actionTakenMode = "1"
                }else{
                    serviceAttendedMode = "1"
                    replacedProductMode = "1"
                    attendanceMode = "0"
                    actionTakenMode = "1"
                }
                hideShowViews()
                if (serviceFollowUpAttendanceArrayList.length() == 0){
                    serviceFollowUpReplacedProduct = 0
                    loadAttendance()
                }
                Config.snackBars(context,v,"Please mark Attended Person")
            }else{


                validatePaymentDetails(v)
            }

        }catch (e:Exception){

        }



    }

    private fun validatePaymentDetails(v: View) {


        try {
            totPayAmount = 0.0f
            savePaymentDetailArray = JSONArray()
            for (i in 0 until arrPayment.length()) {
               var jsonObject = arrPayment.getJSONObject(i)
                val jsonObject1 = JSONObject()
                jsonObject1.put("PaymentMethod",jsonObject.getString("MethodID"))
                jsonObject1.put("PAmount",jsonObject.getString("Amount"))
                jsonObject1.put("Refno",jsonObject.getString("RefNo"))

                savePaymentDetailArray.put(jsonObject1)

                totPayAmount = totPayAmount + jsonObject.getString("Amount").toFloat()
            }
        }catch (e: Exception){

        }

        validateActionTaken(v)

//
//        Log.e(TAG,"saveServiceAttendedArray    8241   :  "+saveServiceAttendedArray)
//        Log.e(TAG,"saveReplacedeProductArray   8242   :  "+saveReplacedeProductArray)
//        Log.e(TAG,"saveAttendedEmployeeArray   8243   :  "+saveAttendedEmployeeArray)
//        Log.e(TAG,"savePaymentDetailArray      8244   :  "+savePaymentDetailArray)

    }

    private fun validateActionTaken(v: View) {

        strCustomerNote = tie_Customer_Note!!.text.toString()
        strEmployeeNote = tie_Employee_Note!!.text.toString()
        strVisitedDate = tie_Visited_Date!!.text.toString()
        strFollowUpDate = tie_Follow_Date!!.text.toString()
        strReplacementAmount = tie_Security_Amount!!.text.toString()

        Log.e(TAG,"ID_Action_Status    8911   :  "+ID_Action_Status)
        Log.e(TAG,"ID_LeadAction    8911   :  "+ID_LeadAction)
        Log.e(TAG,"ID_LeadAction_Status    8911   :  "+ID_LeadAction_Status)
        Log.e(TAG,"ID_ActionType    8911   :  "+ID_ActionType)
        if (strVisitedDate.equals("")){
            Config.snackBars(context,v,"Select Visited Date")
        }
        else if (ID_Action.equals("")){
            Config.snackBars(context,v,"Select Action")
            til_Action!!.setError("Select Action")
            til_Action!!.setErrorIconDrawable(null);
            if (actionTakenMode.equals("0")){
                serviceAttendedMode = "1"
                replacedProductMode = "1"
                attendanceMode = "1"
                actionTakenMode = "1"
            }else{
                serviceAttendedMode = "1"
                replacedProductMode = "1"
                attendanceMode = "1"
                actionTakenMode = "0"
            }
            hideShowViews()
        }
        else if (!ID_Action_Status.equals("9")){
            validatePaymentMethod(v)
        }else{
            if (ID_LeadAction.equals("0")){
            //    Config.snackBars(context,v,"Select Lead Action")
                til_Lead_Action!!.setError("Select Lead Action")
                til_Lead_Action!!.setErrorIconDrawable(null);
            }else{

                if (ID_LeadAction_Status.equals("1")){
                    if (ID_ActionType.equals("0")){
                        //Config.snackBars(context,v,"Select Action Type")
                        til_Action_Type!!.setError("Select Action Type")
                        til_Action_Type!!.setErrorIconDrawable(null);
                    }
                    else if (strFollowUpDate.equals("")){
                       // Config.snackBars(context,v,"Select Followup Date")
                        til_Follow_Date!!.setError("Select Action Type")
                        til_Follow_Date!!.setErrorIconDrawable(null);
                    }else{
                        validatePaymentMethod(v)
                    }
                }else{
                    validatePaymentMethod(v)
                }
            }
        }
    }

    private fun validatePaymentMethod(v: View) {

        Log.e(TAG,"totRplacement   9611   "+totRplacement)
        Log.e(TAG,"totPayAmount    9612   "+totPayAmount)

        if (totRplacement > 0){

            if (ID_Billtype.equals("0")){
                Config.snackBars(context,v,"Select Bill type")
            }else if (totPayAmount != totRplacement){
                Config.snackBars(context,v,"In Payment Method Balance Amt. Should be Zero")

            }else{
                confirmationPopup()
            }
        }else{
            confirmationPopup()
        }





//        1.Starting date :Visited Date
//        2.Replacement amount :security amount
//        3.Fk_next action:Action
//                4.Nextactionlead:Follow up Date
//        5.fk_employeelead:Assigned To
//        6.totalamount:replacementtotal

    }

    private fun confirmationPopup() {
//        Log.e(TAG,"8241  "
//                +"\n CustomerNotes  :  "+strCustomerNote
//                +"\n EmployeeNote  :  "+strEmployeeNote
//                +"\n StartingDate  :  "+strVisitedDate
//                +"\n TotalAmount  :  "+strTotalAmount
//                +"\n ReplaceAmount  :  "+strReplacementAmount
//                +"\n FK_NextAction  :  "+ID_Action
//                +"\n FK_NextActionLead  :  "+strFollowUpDate
//                +"\n FK_NextActionLead  :  "+ID_AssignedTo
//                +"\n FK_BillType  :  "+ID_Billtype)
//
//
//
//        Log.e(TAG,"saveServiceAttendedArray    8241   :  "+saveServiceAttendedArray)
//        Log.e(TAG,"saveReplacedeProductArray   8242   :  "+saveReplacedeProductArray)
//        Log.e(TAG,"saveAttendedEmployeeArray   8243   :  "+saveAttendedEmployeeArray)
//        Log.e(TAG,"savePaymentDetailArray      8244   :  "+savePaymentDetailArray)
//

        saveCount = 0
        saveServiceFollowUp()


//        try {
//
//            var dialogConfirmSheet = Dialog(this)
//            dialogConfirmSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialogConfirmSheet!! .setContentView(R.layout.confirm_service_followup_sheet)
//            dialogConfirmSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
//
//            val window: Window? = dialogConfirmSheet!!.getWindow()
//            window!!.setBackgroundDrawableResource(android.R.color.transparent);
//            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//            dialogConfirmSheet!!.setCancelable(true)
//
//            var ll_pop1 = dialogPaymentSheet!! .findViewById(R.id.ll_pop1) as LinearLayout
//            var ll_pop2 = dialogPaymentSheet!! .findViewById(R.id.ll_pop2) as LinearLayout
//            var ll_pop3 = dialogPaymentSheet!! .findViewById(R.id.ll_pop3) as LinearLayout
//
//            var recyc_pop1 = dialogPaymentSheet!! .findViewById(R.id.recyc_pop1) as FullLenghRecyclertview
//            var recyc_pop2 = dialogPaymentSheet!! .findViewById(R.id.recyc_pop2) as FullLenghRecyclertview
//            var recyc_pop3 = dialogPaymentSheet!! .findViewById(R.id.recyc_pop3) as FullLenghRecyclertview
//
//            if (saveServiceAttendedArray.length()>0){
//                ll_pop1.visibility = View.VISIBLE
//
//
//            }
//            if (saveReplacedeProductArray.length()>0){
//                ll_pop2.visibility = View.VISIBLE
//            }
//            if (saveAttendedEmployeeArray.length()>0){
//                ll_pop3.visibility = View.VISIBLE
//            }
//
//
//
//
//
//            dialogConfirmSheet!!.show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e(TAG,"801 Exception  "+e.toString())
//        }



    }

    private fun saveServiceFollowUp() {
//        customer_service_register
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                try {
                    Log.e(TAG,"1027  ")
                    saveServiceFollowUpViewModel.saveServiceFollowUp(
                        this, customer_service_register,strCustomerNote!!,strEmployeeNote!!,strVisitedDate!!,strTotalAmount!!,
                        strReplacementAmount!!,ID_Action!!,ID_LeadAction!!,strFollowUpDate!!,ID_AssignedTo!!,ID_Billtype!!,
                        saveServiceAttendedArray!!,saveReplacedeProductArray!!,saveAttendedEmployeeArray!!,savePaymentDetailArray!!)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            try {
                                val msg = serviceSetterGetter.message
                                if (msg!!.length > 0) {
                                    if (serviceFollowUpProductList == 0) {
                                        serviceFollowUpProductList++
                                        Log.e(TAG,"1041  "+msg)
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            try {
                                                val jobjt = jObject.getJSONObject("UpdateServiceFollowUp")
                                                val suceessDialog = Dialog(this)
                                                suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                suceessDialog!!.setCancelable(false)
                                                suceessDialog!! .setContentView(R.layout.success_service_popup)
                                                suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
                                                suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                                suceessDialog!!.setCancelable(false)

                                                val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
                                                val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView

                                                tv_succesmsg!!.setText(jobjt.getString("Message"))

                                                tv_succesok!!.setOnClickListener {
                                                    suceessDialog!!.dismiss()
                                                    onBackPressed()

                                                }

                                                suceessDialog!!.show()
                                                suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                            }catch (e: Exception){
                                                val builder = AlertDialog.Builder(
                                                    this@ServiceFollowUpNewActivity,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage(e.toString())
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->

                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()
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
                }catch (e : Exception){
                    Log.e(TAG,"1027 Exception "+e.toString())
                }

            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        if (dateMode == 0){
            date_Picker1.maxDate = System.currentTimeMillis()
        }else if (dateMode == 1){

            date_Picker1.minDate = System.currentTimeMillis()
        }
//        else if (dateMode == 2){
//            date_Picker1.minDate = System.currentTimeMillis()
//        }

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



                if (dateMode == 0){
                    tie_Visited_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }else if (dateMode == 1){
                    tie_Follow_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
//                else if (dateMode == 2){
//                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun hideShowViews() {
        lin_service_cost!!.visibility = View.GONE
        lin_replacePoductCost!!.visibility = View.GONE
        lin_attendance!!.visibility = View.GONE
        lin_Action_Taken!!.visibility = View.GONE

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

        if (actionTakenMode.equals("0")){
            lin_Action_Taken!!.visibility = View.VISIBLE
        }



    }

    private fun cancelBottom() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_cancel, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        txtCancel.setOnClickListener {
            dialog.dismiss()
        }

        txtSubmit.setOnClickListener {
            dialog.dismiss()
            onBackPressed()

        }

        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
            if (journeyType.equals("1")) {
                confirmBottomSheet(journeyType)
            }
            else if (journeyType.equals("2")) {
                confirmBottomSheet(journeyType)
            }
            else if (journeyType.equals("3")) {
                confirmBottomSheet(journeyType)
            }
            else if (journeyType.equals("4")) {
                confirmBottomSheet(journeyType)
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


    private fun confirmBottomSheet(type: String?) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.confirm_popup, null)

        val btnNo = view.findViewById<TextView>(R.id.btnNo)
        val btnYes = view.findViewById<TextView>(R.id.btnYes)
        val tv_text = view.findViewById<TextView>(R.id.tv_text)

        if (type.equals("1")) {
            tv_text.setText("do you really want to start your day?")
        }
        else if (type.equals("2")) {
            tv_text.setText("do you really want to hold your day?")
        }
        else if (type.equals("3")) {
            tv_text.setText("do you really want to resume your day?")
        }
        else if (type.equals("4")) {
            tv_text.setText("do you really want to stop your day?")
        }
//        else {
//            tv_text.setText("do you really want to restart your day?")
//        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            dialog.dismiss()

            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    fetchLocation()
                }
                else{
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
            else{
                requestPermissions()
            }




//            if (type == 0) {
//                fetchLocation()
//            } else if (type == 1) {
//                fetchLocation()
//            } else {
//                fetchLocation()
//            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun startStopWork(latitude: Double, longitude: Double, address: String?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        val current = LocalDateTime.now().format(formatter)
        Log.e("dfsdfds34343f", "current " + current)

//        runningStatus
//        Default =0
//        Start = 1
//        Hold = 2
//        Resume = 3
//        Stop = 4

//        if (runningStatus.equals("0")){
//            runningStatus = "1"
//        }
//        else if (runningStatus.equals("1")){
//            runningStatus = "2"
//        }else{
//            runningStatus = "0"
//        }



//        "TransMode":"CUSA",
//        "FK_Master":"351",
//        "LocLatitude":"11.247589511",
//        "LocLongitude":"75.834220611",
//        "Address":"HiLITE Business Park, 5th floor Hilite Business Park, Poovangal, Pantheeramkavu, Kerala 673014, India,Pantheeramkavu,Kerala,India,673014",
//        "LocationEnteredDate":"2023-05-31",
//        "LocationEnteredTime":"10:43:00",
//        "Status":"1"



        var curDate = ""
        var curTime = ""

        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm:ss")

            curDate = sdfDate1.format(newDate)
            curTime = sdfTime1.format(newDate)

            statusCount = 0
            updateFollowUpStatus(latitude.toString(),longitude.toString(),address.toString(),curDate,curTime,journeyType!!)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }

    //

//        if (journeyType.equals("1")){
//            runningStatus = "1"
//        }
//        else if (journeyType.equals("2")){
//            runningStatus = "2"
//        }
//        else if (journeyType.equals("3")){
//            runningStatus = "3"
//        }
//        else if (journeyType.equals("4")){
//            runningStatus = "4"
//        }
//
//
//        setRunningStatus()

//        0106

    }

    private fun updateFollowUpStatus(latitude : String,longitude : String,address : String,curDate : String,curTime : String,journeyType : String) {
//        customer_service_register
        var  TransMode = "CUSV"
       // customer_service_register
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                try {
                    Log.e(TAG,"1027  ")
                    followupStatusUpdateViewModel.getFollowupStatusUpdate(
                        this, TransMode,customer_service_register,latitude!!,longitude!!,address!!,curDate!!, curTime!!,journeyType!!)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            try {
                                val msg = serviceSetterGetter.message
                                if (msg!!.length > 0) {
                                    if (statusCount == 0) {
                                        statusCount++
                                        Log.e(TAG,"1041  "+msg)
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            try {
                                                val jobjt = jObject.getJSONObject("UpdateFollowupStatusDetails")

                                                successAlert(jobjt)


                                            }catch (e: Exception){
                                                val builder = AlertDialog.Builder(
                                                    this@ServiceFollowUpNewActivity,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage(e.toString())
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->

                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()
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
                }catch (e : Exception){
                    Log.e(TAG,"1027 Exception "+e.toString())
                }

            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun successAlert(jobjt: JSONObject) {

        val suceessDialog = Dialog(this)
        suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        suceessDialog!!.setCancelable(false)
        suceessDialog!! .setContentView(R.layout.success_service_popup)
        suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
        suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        suceessDialog!!.setCancelable(false)

        val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
        val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView

        tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))

        tv_succesok!!.setOnClickListener {
            suceessDialog!!.dismiss()
            runningStatus = jobjt.getString("FK_Status")
            setRunningStatus()
        }

        suceessDialog!!.show()
        suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                                                //            val adapter = CountryDetailAdapter(this@ServiceFollowUpNewActivity, countryArrayList)
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
            if (modelMoreServicesTemp.size>0){
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
                                                    "0",jsonObject.getString("ReplaceAmount"),jsonObject.getString("Remarks"),"1",true,true,true,true,"0","0"))
                                            }

                                        }
                                        Log.e(TAG,"modelReplacedProduct   8371   "+ modelReplacedProduct.size)
                                        try {
                                          //  if (modelReplacedProduct.size>0){
                                                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                                                recycleView_replaceproduct!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                                adapterReplacedProduct = ReplacedProductAdapter(this@ServiceFollowUpNewActivity, modelReplacedProduct,jsonArrayChangeMode)
                                                recycleView_replaceproduct!!.adapter = adapterReplacedProduct
                                                adapterReplacedProduct!!.setClickListener(this@ServiceFollowUpNewActivity)

                                           // }

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

    private fun loadChangeMode(mode : Int) {
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

                                    if (mode == 1){
                                        adapterReplacedProduct!!.addChangeMode(jsonArrayChangeMode)
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

    private fun loadServiceFollowUpProductList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                serviceFollowProductListViewModel.getServiceFollowUpProductList(
                    this, ID_Branch)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpProductList == 0) {
                                    serviceFollowUpProductList++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("MoreComponentDetails")

                                        jsonArrayServiceFollowProductList = jobjt.getJSONArray("MoreComponentDetailsList")

                                        Log.e(TAG,"jsonArrayServiceFollowProductList   83722   "+jsonArrayServiceFollowProductList)
                                        modelServiceFollowProductList.clear()
                                        if (jsonArrayServiceFollowProductList.length()>0){

                                            for (i in 0 until jsonArrayServiceFollowProductList.length()) {
                                                var jsonObject = jsonArrayServiceFollowProductList.getJSONObject(i)

                                                modelServiceFollowProductList!!.add(ModelServiceFollowProductList("0",jsonObject.getString("FK_Product"),
                                                    jsonObject.getString("ProductName"), "0"))
                                            }

                                        }

                                     //   loadMoreServiceAttendedPopup(sortMoreServiceAttended)
                                        loadMoreServiceFollowProductPopup(modelServiceFollowProductList)
                                        Log.e(TAG,"modelServiceFollowProductList   1302   "+ modelServiceFollowProductList.size)


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

    private fun loadMoreServiceFollowProductPopup(modelServiceFollowProductList: ArrayList<ModelServiceFollowProductList>) {

        try {

            dialogserviceFollowUpProductSheet = Dialog(this)
            dialogserviceFollowUpProductSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogserviceFollowUpProductSheet!! .setContentView(R.layout.cs_more_service_follow_product)
            dialogserviceFollowUpProductSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogserviceFollowUpProductSheet!!.setCancelable(false)

            recyFollowupComponent = dialogserviceFollowUpProductSheet!! .findViewById(R.id.recyFollowupComponent) as RecyclerView
            var tv_cancel = dialogserviceFollowUpProductSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogserviceFollowUpProductSheet!! .findViewById(R.id.tv_submit) as TextView

            //   lnrHead_warranty_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_warranty_main) as LinearLayout




            val window: Window? = dialogserviceFollowUpProductSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)



            modelServiceFollowProductListTemp.clear()
            for (i in 0 until modelServiceFollowProductList.size) {
                val ItemsModelTemp = modelServiceFollowProductList[i]
                modelServiceFollowProductListTemp!!.add(ModelServiceFollowProductListTemp(ItemsModelTemp.isChecked,ItemsModelTemp.FK_Product,ItemsModelTemp.ProductName,ItemsModelTemp.isAdded))
            }



            modelReplacedProductTemp.clear()
            for (i in 0 until modelReplacedProduct.size) {
                val ItemsServiceTemp = modelReplacedProduct[i]
                modelReplacedProductTemp!!.add(ModelReplacedProductTemp(ItemsServiceTemp.isChecked,ItemsServiceTemp.ID_OLD_Product,ItemsServiceTemp.OLD_Product,ItemsServiceTemp.SPDOldQuantity,
                    ItemsServiceTemp.Amount,ItemsServiceTemp.ID_Mode,ItemsServiceTemp.ModeName,ItemsServiceTemp.ID_Product,ItemsServiceTemp.Product,ItemsServiceTemp.Replaced_Qty,
                    ItemsServiceTemp.ReplaceAmount,ItemsServiceTemp.Remarks,ItemsServiceTemp.isAdded,ItemsServiceTemp.boolSecAmnt,ItemsServiceTemp.boolRepProd,
                    ItemsServiceTemp.boolRepQty,ItemsServiceTemp.boolRepAmnt,ItemsServiceTemp.MRPs,ItemsServiceTemp.StockId))
            }

            Log.e(TAG,"1372   sortMoreServiceAttended   "+modelServiceFollowProductList)
            if (modelServiceFollowProductList.size>0){
                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                recyFollowupComponent!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                val adapter1 = MoreServiceFollowProductAdapter(this@ServiceFollowUpNewActivity,modelServiceFollowProductList,modelServiceFollowProductListTemp)
                recyFollowupComponent!!.adapter = adapter1
                adapter1.setClickListener(this@ServiceFollowUpNewActivity)

            }

            tv_cancel!!.setOnClickListener {
               // selectMoreServiceAttended = JSONArray()
                dialogserviceFollowUpProductSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogserviceFollowUpProductSheet!!.dismiss()
                Log.e(TAG,"13721  s")
                Log.e(TAG,"13722  modelServiceFollowProductList   "+modelServiceFollowProductList)

            //    addDataServiceAttended()
                addDataServiceFollowUpProduct()
            }

            dialogserviceFollowUpProductSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addDataServiceFollowUpProduct() {
        modelServiceFollowProductList.clear()
        for (i in 0 until modelServiceFollowProductListTemp.size) {
            val ItemsModel = modelServiceFollowProductListTemp[i]
            Log.e(TAG,"isChecked  1046  "+ItemsModel.isChecked)
            modelServiceFollowProductList!!.add(ModelServiceFollowProductList(ItemsModel.isChecked,ItemsModel.FK_Product,ItemsModel.ProductName,ItemsModel.isAdded))
        }

        Log.e(TAG,"")

        modelReplacedProduct.clear()
        for (i in 0 until modelReplacedProductTemp.size) {
            val ItemsAttend3 = modelReplacedProductTemp[i]
            Log.e(TAG,"77222  : "+ItemsAttend3.isAdded+"  :  "+ItemsAttend3.ID_OLD_Product+"  :  "+ItemsAttend3.OLD_Product)
            if (ItemsAttend3.isAdded.equals("1")){
                modelReplacedProduct!!.add(ModelReplacedProduct(ItemsAttend3.isChecked,ItemsAttend3.ID_OLD_Product
                    ,ItemsAttend3.OLD_Product,ItemsAttend3.SPDOldQuantity,ItemsAttend3.Amount,ItemsAttend3.ID_Mode,
                    ItemsAttend3.ModeName,ItemsAttend3.ID_Product,ItemsAttend3.Product,ItemsAttend3.Replaced_Qty,
                    ItemsAttend3.ReplaceAmount,ItemsAttend3.Remarks,ItemsAttend3.isAdded,ItemsAttend3.boolSecAmnt,
                    ItemsAttend3.boolRepProd,ItemsAttend3.boolRepQty,ItemsAttend3.boolRepAmnt,ItemsAttend3.MRPs,ItemsAttend3.StockId))
            }


        }


        for (i in 0 until modelReplacedProduct.size) {
            val ItemsAttend4 = modelReplacedProduct[i]
            Log.e(TAG,"772221  : "+ItemsAttend4.isAdded+"  :  "+ItemsAttend4.ID_OLD_Product+"  :  "+ItemsAttend4.OLD_Product)

        }



        try {
            if (modelReplacedProduct.size>0){
                val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                recycleView_replaceproduct!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                adapterReplacedProduct = ReplacedProductAdapter(this@ServiceFollowUpNewActivity, modelReplacedProduct,jsonArrayChangeMode)
                recycleView_replaceproduct!!.adapter = adapterReplacedProduct
                adapterReplacedProduct!!.setClickListener(this@ServiceFollowUpNewActivity)
                adapterReplacedProduct!!.notifyDataSetChanged()

            }

        }
        catch (e : Exception){
            Log.e(TAG,"Exception   418   "+e.toString())
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
//            val adapter = CountryDetailAdapter(this@ServiceFollowUpNewActivity, countryArrayList)
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

    private fun loadAttendance() {

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
                                        val jobjt = jObject.getJSONObject("Attendancedetails")
                                        serviceFollowUpAttendanceArrayList = jobjt.getJSONArray("AttendancedetailsList")
                                        Log.e(TAG,"1337  "+serviceFollowUpAttendanceArrayList)
                                      //  setAttendancetRecycler(serviceFollowUpAttendanceArrayList)

                                        if (serviceFollowUpAttendanceArrayList.length()>0){

                                            for (i in 0 until serviceFollowUpAttendanceArrayList.length()) {
                                                var jsonObject = serviceFollowUpAttendanceArrayList.getJSONObject(i)
                                                modelFollowUpAttendance!!.add(ModelFollowUpAttendance("0",jsonObject.getString("ID_Employee"),
                                                    jsonObject.getString("EmployeeName"),jsonObject.getString("ID_CSAEmployeeType"),jsonObject.getString("Attend"),
                                                    jsonObject.getString("DepartmentID"),jsonObject.getString("Department"),jsonObject.getString("Role"),
                                                    jsonObject.getString("Designation")))
                                            }


                                        }

                                        if (modelFollowUpAttendance.size>0){

                                            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
                                            recyclerAttendance!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                            adapterServiceFollowAttendance = ServiceFollowAttendanceAdapter(this@ServiceFollowUpNewActivity, modelFollowUpAttendance)
                                            recyclerAttendance!!.adapter = adapterServiceFollowAttendance
                                          //  adapterServiceFollowAttendance!!.setClickListener(this@ServiceFollowUpNewActivity)


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

    private fun getServiceFollowupAction() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpActionViewModel.getServiceFollowupAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpAction == 0){
                                    serviceFollowUpAction++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1748   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        serviceFollowUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (serviceFollowUpActionArrayList.length()>0){

                                            serviceFollowUpActionPopup(serviceFollowUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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

    private fun serviceFollowUpActionPopup(serviceFollowUpActionArrayList: JSONArray) {
        try {

            dialogServiceFollowupAction = Dialog(this)
            dialogServiceFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServiceFollowupAction!! .setContentView(R.layout.followup_action)
            dialogServiceFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyserviceFollowupAction = dialogServiceFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogServiceFollowupAction!! .findViewById(R.id.etsearch) as EditText


            serviceFollowUpActionSort = JSONArray()
            for (k in 0 until serviceFollowUpActionArrayList.length()) {
                val jsonObject = serviceFollowUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                serviceFollowUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyserviceFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, serviceFollowUpActionArrayList)
            val adapter = ServiceFollowupActionAdapter(this@ServiceFollowUpNewActivity, serviceFollowUpActionSort)
            recyserviceFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    serviceFollowUpActionSort = JSONArray()

                    for (k in 0 until serviceFollowUpActionArrayList.length()) {
                        val jsonObject = serviceFollowUpActionArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("NxtActnName").length) {
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                serviceFollowUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"serviceFollowUpActionSort               7103    "+serviceFollowUpActionSort)
                    val adapter = ServiceFollowupActionAdapter(this@ServiceFollowUpNewActivity, serviceFollowUpActionSort)
                    recyserviceFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUpNewActivity)
                }
            })

            dialogServiceFollowupAction!!.show()
            dialogServiceFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFollowupAction() {
//        var followUpAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (followUpAction == 0){
                                    followUpAction++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length()>0){

                                            followUpLeadActionPopup(followUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
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

    private fun followUpLeadActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_lead_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!! .findViewById(R.id.etsearch) as EditText


            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@ServiceFollowUpNewActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpActionSort = JSONArray()

                    for (k in 0 until followUpActionArrayList.length()) {
                        val jsonObject = followUpActionArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("NxtActnName").length) {
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpActionSort               7103    "+followUpActionSort)
                    val adapter = FollowupActionAdapter(this@ServiceFollowUpNewActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUpNewActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getFollowupType() {
//         var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (followUpType == 0) {
                                    followUpType++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                        followUpTypeArrayList =
                                            jobjt.getJSONArray("FollowUpTypeDetailsList")
                                        if (followUpTypeArrayList.length() > 0) {
//                                             if (followUpType == 0){
//                                                 followUpType++
                                            followupTypePopup(followUpTypeArrayList)
//                                             }

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!!.setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!!.findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!!.findViewById(R.id.etsearch) as EditText

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupTypeAdapter(this@ServiceFollowUpNewActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@ServiceFollowUpNewActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "followUpTypeSort               7103    " + followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@ServiceFollowUpNewActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUpNewActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getBillType() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceBillTypeViewModel.getBillType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (billTypeCount == 0) {
                                    billTypeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BillTyep")
                                        billTypeArrayList = jobjt.getJSONArray("BillTyepList")
                                        if (billTypeArrayList.length() > 0) {
//
                                            billTypePopup(billTypeArrayList)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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

    private fun billTypePopup(billTypeArrayList: JSONArray) {

        try {

            dialogBillType = Dialog(this)
            dialogBillType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBillType!!.setContentView(R.layout.bill_types_popup)
            dialogBillType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBillType = dialogBillType!!.findViewById(R.id.recyBillType) as RecyclerView
            val etsearch = dialogBillType!!.findViewById(R.id.etsearch) as EditText

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyBillType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@ServiceFollowUpNewActivity, employeeArrayList)
            val adapter = BillTypeAdapter(this@ServiceFollowUpNewActivity, billTypeArrayList)
            recyBillType!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)



            dialogBillType!!.show()
            dialogBillType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmployee() {
//         var employee = 0
        val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
        var ID_Department = FK_DepartmentSP.getString("FK_Department","0")
        Log.e(TAG,"ID_Department  2394   "+ID_Department)

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, ID_Department!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (employeeMode == 0) {
                                    employeeMode++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList =
                                            jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!!.setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!!.findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!!.findViewById(R.id.etsearch) as EditText

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@ServiceFollowUpNewActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@ServiceFollowUpNewActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSort = JSONArray()

                    for (k in 0 until employeeArrayList.length()) {
                        val jsonObject = employeeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeSort               7103    " + employeeSort)
                    val adapter = EmployeeAdapter(this@ServiceFollowUpNewActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUpNewActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(
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
            dialogPaymentSheet!! .setContentView(R.layout.emi_payment_bottom_sheet)
            dialogPaymentSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogPaymentSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            //  dialogPaymentSheet!!.setCancelable(false)

            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText

            txtPayBalAmount = dialogPaymentSheet!! .findViewById(R.id.txtPayBalAmount) as TextView

            txt_pay_method = dialogPaymentSheet!! .findViewById(R.id.txt_pay_method) as TextView
            txt_pay_Amount = dialogPaymentSheet!! .findViewById(R.id.txt_pay_Amount) as TextView
            txt_bal_Amount = dialogPaymentSheet!! .findViewById(R.id.txt_bal_Amount) as TextView

            edtPayMethod!!.addTextChangedListener(watcher)
            edtPayAmount!!.addTextChangedListener(watcher)
            txtPayBalAmount!!.addTextChangedListener(watcher)

//            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
//            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
//            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText


            DecimelFormatters.setDecimelPlace(edtPayAmount!!)



//            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))


            img_PayAdd = dialogPaymentSheet!! .findViewById(R.id.img_PayAdd) as ImageView
            img_PayRefresh = dialogPaymentSheet!! .findViewById(R.id.img_PayRefresh) as ImageView
            btnApply = dialogPaymentSheet!! .findViewById(R.id.btnApply) as Button

            ll_paymentlist = dialogPaymentSheet!! .findViewById(R.id.ll_paymentlist) as LinearLayout
            recyPaymentList = dialogPaymentSheet!! .findViewById(R.id.recyPaymentList) as RecyclerView



         //    txtPayBalAmount!!.setText(""+tie_Security_Amount!!.text.toString())

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG,"payAmnt         475    "+payAmnt)
                Log.e(TAG,"tie_Security_Amount    475    "+tie_Security_Amount!!.text.toString())
                txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((tie_Security_Amount!!.text.toString().toFloat()) - pay.toFloat()))
            }else{
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                txtPayBalAmount!!.setText(""+tie_Security_Amount!!.text.toString())
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

                if (arrPayment.length() > 0){
                    var payAmnt = 0.00
                    for (i in 0 until arrPayment.length()) {
                        //apply your logic
                        val jsonObject = arrPayment.getJSONObject(i)
                        payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                    }
                    txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((tie_Security_Amount!!.text.toString().toFloat()) - payAmnt.toFloat()))
                }else{
                    txtPayBalAmount!!.setText(""+tie_Security_Amount!!.text.toString())
                }
            }

            btnApply!!.setOnClickListener {
                val payAmnt = DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                if ((payAmnt.toFloat()).equals("0.00".toFloat())){
                    Log.e(TAG,"801 payAmnt  0.00  "+payAmnt.toFloat())
                    applyMode = 1
                    dialogPaymentSheet!!.dismiss()
                }
                else{
                    Log.e(TAG,"801 payAmnt  0.0clhghfoij    "+payAmnt.toFloat())
                    Config.snackBarWarning(context,it,"Balance Amount should be zero")
                }
            }


            dialogPaymentSheet!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"801 Exception  "+e.toString())
        }
    }



    private fun validateAddPayment(view: View) {

        var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
        var payAmount = edtPayAmount!!.text.toString()

        Log.e(TAG,"110   balAmount   : "+balAmount)
        Log.e(TAG,"110   payAmount   : "+payAmount)
        var hasId = hasPayMethod(arrPayment,"MethodID",ID_PaymentMethod!!)



        if (ID_PaymentMethod.equals("")){
            Log.e(TAG,"110   Valid   : Select Payment Method")
            edtPayMethod!!.setError("Select Payment Method",null)
            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"Select Payment Method")
        }
        else if (hasId == false && arrAddUpdate.equals("0")){
            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"PaymentMethod Already exits")
        }
        else if (edtPayAmount!!.text.toString().equals("") || payAmount.toFloat() == 0f){
            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Log.e(TAG,"110   Valid   : Enter Amount")
        //    Config.snackBarWarning(context,view,"Enter Amount")
            Config.snackBarWarning(context,view,"The amount paid must be greater than 0")

        }else if (balAmount.toFloat() < payAmount.toFloat() ){
            Log.e(TAG,"110   Valid   : Enter Amount DD")
            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"Amount should be less than or equal to Bal. Amount")
        }else{

            if (arrAddUpdate.equals("0")){

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount.toFloat()- payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt
                val jObject = JSONObject()
                jObject.put("MethodID",ID_PaymentMethod)
                jObject.put("Method",edtPayMethod!!.text.toString())
                jObject.put("RefNo",edtPayRefNo!!.text.toString())
                jObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                arrPayment!!.put(jObject)
            }
            if (arrAddUpdate.equals("1")){

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount.toFloat()- payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt

                val jsonObject = arrPayment.getJSONObject(arrPosition!!)
                jsonObject.put("MethodID",ID_PaymentMethod)
                jsonObject.put("Method",edtPayMethod!!.text.toString())
                jsonObject.put("RefNo",edtPayRefNo!!.text.toString())
                jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))

                arrAddUpdate = "0"



            }

            applyMode = 0
            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
            }else{
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
            }

        }

        Log.e(TAG,"110  arrPayment  :  "+arrPayment)
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@ServiceFollowUpNewActivity, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@ServiceFollowUpNewActivity)
    }

    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
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
                                        paymentMethodeArrayList = jobjt.getJSONArray("FollowUpPaymentMethodList")
                                        if (paymentMethodeArrayList.length() > 0) {

                                            payMethodPopup(paymentMethodeArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUpNewActivity,
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
            recyPaymentMethod = dialogPaymentMethod!!.findViewById(R.id.recyPaymentMethod) as RecyclerView

            val lLayout = GridLayoutManager(this@ServiceFollowUpNewActivity, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@ServiceFollowUpNewActivity, employeeArrayList)
            val adapter = PayMethodAdapter(this@ServiceFollowUpNewActivity, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUpNewActivity)



            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {

                editable === tie_Visited_Date!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Visited_Date!!.text.toString().equals("")){
                        til_Visited_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        //   til_DeliveryDate!!.isErrorEnabled = false
                        til_Visited_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.black)
                    }

                }

                editable === tie_Action!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Action!!.text.toString().equals("")){
                      //  tie_Action!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                        til_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Action!!.isErrorEnabled = false
                        til_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.black)
                    }

                }
                editable === tie_Lead_Action!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Lead_Action!!.text.toString().equals("")){
                        //  tie_Action!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                        til_Lead_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Lead_Action!!.isErrorEnabled = false
                        til_Lead_Action!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.black)
                    }

                }
                editable === tie_Action_Type!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Action_Type!!.text.toString().equals("")){
                        //  tie_Action!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                        til_Action_Type!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Action_Type!!.isErrorEnabled = false
                        til_Action_Type!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.black)
                    }

                }

                editable === tie_Follow_Date!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Follow_Date!!.text.toString().equals("")){
                        //  tie_Action!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                        til_Follow_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Follow_Date!!.isErrorEnabled = false
                        til_Follow_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.black)
                    }

                }

//                editable === edtPayMethod!!.editableText -> {
//                    Log.e(TAG,"283021    ")
//                    if (edtPayMethod!!.text.toString().equals("")){
////                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//                    }else{
//                        //   til_DeliveryDate!!.isErrorEnabled = false
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
//                    }
//
//                }
//
//                editable === edtPayAmount!!.editableText -> {
//                    Log.e(TAG,"283021    ")
//                    if (edtPayAmount!!.text.toString().equals("")){
////                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//                    }else{
//                        //   til_DeliveryDate!!.isErrorEnabled = false
//                        txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
//                    }
//
//                }
//
//                editable === txtPayBalAmount!!.editableText -> {
//                    Log.e(TAG,"283021    ")
//                    val payAmnt = DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
//                    if ((payAmnt.toFloat()).equals("0.00".toFloat())){
//                        Log.e(TAG,"801 payAmnt  0.00  "+payAmnt.toFloat())
//                        txt_bal_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
//                    }
//                    else{
//                        Log.e(TAG,"801 payAmnt  0.0clhghfoij    "+payAmnt.toFloat())
//                        txt_bal_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//
//                    }
//
//                }





            }

        }
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

        if (data.equals("MoreServiceFollowProductClick")) {
            try {
                val ItemsModel = modelServiceFollowProductListTemp[position]
                Log.e(TAG," 2359  "+ItemsModel.ProductName+"  :  "+ItemsModel.isAdded)

//                var hasId = containsName(modelServiceAttendedTemp,ItemsModel.Service)


                Log.e(TAG," 23590  "+modelReplacedProductTemp.size)
                var isTrue = false
                var pos = 0
                for (i in 0 until modelReplacedProductTemp.size) {
                    var ItemsAttend = modelReplacedProductTemp[i]
                    if (ItemsAttend.ID_OLD_Product.equals(ItemsModel.FK_Product)){
                        isTrue = true
                        pos = i
                    }
                }

                Log.e(TAG,"23591  :  "+isTrue+"  :  "+pos)
                if (isTrue){
                    var ItemsAttend1 = modelReplacedProductTemp[pos]
                    ItemsAttend1.isAdded =ItemsModel.isAdded
                }else{
                    if (ItemsModel.isAdded.equals("1")){
                        Log.e(TAG,"23592  :  "+isTrue+"  :  "+ItemsModel.isAdded)
                        modelReplacedProductTemp!!.add(
                            ModelReplacedProductTemp("0",ItemsModel.FK_Product,
                            ItemsModel.ProductName,"0","0","0","","0","","0","0","",
                                ItemsModel.isAdded,true,true,true,true,"0","0")
                        )

                    }
                }

                Log.e(TAG,"77221  :  "+modelReplacedProductTemp.size)


                for (i in 0 until modelReplacedProductTemp.size) {
                    var ItemsAttend3 = modelReplacedProductTemp[i]
                    Log.e(TAG,"23593  : "+ItemsAttend3.ID_OLD_Product+"  :  "+ItemsAttend3.OLD_Product+"  :  "+ItemsAttend3.isAdded)

                }


                Log.e(TAG,"23594   ?:  "+modelReplacedProductTemp.size)

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
                ItemsModel.Replaced_Qty =  "0"
                ItemsModel.ReplaceAmount =  jsonObject!!.getString("SalePrice")
                ItemsModel.MRPs =  jsonObject!!.getString("MRPs")
                ItemsModel.StockId =  jsonObject!!.getString("StockId")
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


        if (data.equals("BuyBackAmountChanged")) {
            try {

                var fSecurityAmount = 0.0f
                for (i in 0 until modelReplacedProduct.size) {
                    val ItemsModel = modelReplacedProduct[i]
                    Log.e(TAG,"modelReplacedProduct  2464   : "+ItemsModel.ID_Mode+" : "+ItemsModel.ModeName+" : "+ItemsModel.Amount+" : "+ItemsModel.isChecked)

                    if (ItemsModel.ID_Mode.equals("2") && ItemsModel.isChecked.equals("1")){
                        fSecurityAmount = fSecurityAmount + ItemsModel.Amount.toFloat()
                    }

                }
                Log.e(TAG,"modelReplacedProduct  24641   : "+fSecurityAmount)
                tie_Security_Amount!!.setText(fSecurityAmount.toString())
                if (fSecurityAmount > 0){
                    til_Bill_Type!!.visibility = View.VISIBLE
                    ID_Billtype = "0"
                    tie_Bill_Type!!.setText("")
                }else{
                    til_Bill_Type!!.visibility = View.INVISIBLE
                    ID_Billtype = "0"
                    tie_Bill_Type!!.setText("")
                }




            }catch (e:Exception){

            }

        }

        if (data.equals("changeModeClick")) {
            try {
                Log.e(TAG,"changeModeClick")
                serviceFollowUpChangeMmode = 0
                loadChangeMode(1)

            }catch (e:Exception){

            }

        }


    }

    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"1740  onClick ")

        if (data.equals("servicefollowupaction")){

            dialogServiceFollowupAction!!.dismiss()
            val jsonObject = serviceFollowUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_Action   "+jsonObject.getString("ID_NextAction"))
            ID_Action = jsonObject.getString("ID_NextAction")
            ID_Action_Status = jsonObject.getString("Status")
            tie_Action!!.setText(jsonObject.getString("NxtActnName"))

            if (ID_Action_Status.equals("9")){
                til_Lead_Action!!.visibility  =View.VISIBLE

                IDActionStatusChanged()

            }else{
                linear_afa!!.visibility  =View.GONE
                til_Lead_Action!!.visibility  =View.GONE

                IDActionStatusChanged()
            }


        }

        if (data.equals("followupaction")){

            dialogFollowupAction!!.dismiss()
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_LeadAction   "+jsonObject.getString("ID_NextAction"))
            ID_LeadAction = jsonObject.getString("ID_NextAction")
            ID_LeadAction_Status = jsonObject.getString("Status")
            tie_Lead_Action!!.setText(jsonObject.getString("NxtActnName"))

            if (ID_LeadAction_Status.equals("1")){
                linear_afa!!.visibility  =View.VISIBLE
            }else{
                linear_afa!!.visibility  =View.GONE
            }


        }

        if (data.equals("followuptype")) {
            dialogFollowupType!!.dismiss()
//             val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_Action_Type!!.setText(jsonObject.getString("ActnTypeName"))

        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_AssignedTo = jsonObject.getString("ID_Employee")
            tie_Assigned_To!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("billTypeClicks")){
            dialogBillType!!.dismiss()

            val jsonObject = billTypeArrayList.getJSONObject(position)
            ID_Billtype= jsonObject.getString("ID_BillType")
            tie_Bill_Type!!.setText(jsonObject.getString("BTName"))
        }


        if (data.equals("paymentMethod")){
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod= jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
        }

    }

    private fun IDActionStatusChanged() {
        try {

            ID_LeadAction = "0"
            ID_LeadAction_Status = "0"
            tie_Lead_Action!!.setText("")

            ID_ActionType ="0"
            tie_Action_Type!!.setText("")

            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")

            tie_Follow_Date!!.setText(""+sdfDate1.format(newDate))

            val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
            val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
            ID_AssignedTo= FK_EmployeeSP.getString("FK_Employee", null).toString()
            tie_Assigned_To!!.setText(UserNameSP.getString("UserName", null))

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }




}