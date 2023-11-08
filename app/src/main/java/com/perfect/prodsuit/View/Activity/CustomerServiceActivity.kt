package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class CustomerServiceActivity : AppCompatActivity()  , View.OnClickListener , ItemClickListener {

    val TAG : String = "CustomerServiceActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var customerservicecountViewModel: CustomerservicecountViewModel
    private var SELECT_LOCATION: Int? = 103

    private var tabLayout : TabLayout? = null
//    var llMainDetail: LinearLayout? = null
//    var llBtn: LinearLayout? = null

//    //Complaints
//    var tie_Comp_FromDate: TextInputEditText? = null
//    var tie_Comp_ToDate: TextInputEditText? = null
//    var tie_Comp_FromTime: TextInputEditText? = null
//    var tie_Comp_ToTime: TextInputEditText? = null


    lateinit var serviceWarrantyViewModel: ServiceWarrantyViewModel
    lateinit var serviceWarrantyArrayList : JSONArray
    var recyServiceWarranty: RecyclerView? = null
    var ll_history_details: LinearLayout? = null

    lateinit var serviceProductHistoryViewModel: ServiceProductHistoryViewModel
    lateinit var serviceProductHistoryArrayList : JSONArray
    var recyServiceProductHistory: RecyclerView? = null

    lateinit var serviceSalesViewModel: ServiceSalesViewModel
    lateinit var serviceSalesArrayList : JSONArray
    var recyServiceSalesHistory: RecyclerView? = null

    lateinit var customerDueViewModel: CustomerDueViewModel
    lateinit var customerDueArrayList : JSONArray
    var recyServiceCustomerdue: RecyclerView? = null


    var ID_CustomerWiseProductDetails=""

    var warrantycount=""
    var servicehistorycount=""
    var salescount=""
    var customerduecount=""
    var card_details: CardView? = null

    private var imDetails: ImageView? = null

    private var tv_customerClick: TextView? = null
    private var tv_complaintClick: TextView? = null
    private var tv_contactClick: TextView? = null
    private var tv_requestedClick: TextView? = null
    private var tv_attendedClick: TextView? = null
    private var tv_locationClick: TextView? = null

    private var lnrHead_customer: LinearLayout? = null
    private var lnrHead_complaint: LinearLayout? = null
    private var lnrHead_contact: LinearLayout? = null
    private var lnrHead_requested: LinearLayout? = null
    private var lnrHead_attended: LinearLayout? = null
    private var lnrHead_location: LinearLayout? = null

    // Customer Details
    private var tie_Date: TextInputEditText? = null
    private var tie_Time: TextInputEditText? = null
    private var tie_CustomerName: TextInputEditText? = null
    private var tie_MobileNo: TextInputEditText? = null
    private var tie_Address: TextInputEditText? = null
    private var tie_Priority: TextInputEditText? = null
    private var tie_Channel: TextInputEditText? = null
    private var tie_EmpOrMedia: TextInputEditText? = null

    private var img_addusrpop: ImageView? = null

    private var til_Date: TextInputLayout? = null
    private var til_Time: TextInputLayout? = null
    private var til_CustomerName: TextInputLayout? = null
    private var til_MobileNo: TextInputLayout? = null
    private var til_Address: TextInputLayout? = null
    private var til_Priority: TextInputLayout? = null
    private var til_EmpOrMedia: TextInputLayout? = null

//    private var til_Address: TextInputLayout? = null

    //Complaints

    private var tie_CompCategory: TextInputEditText? = null
    private var tie_Category: TextInputEditText? = null
    private var tie_Company: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null
    private var tie_Service: TextInputEditText? = null
    private var tie_Complaint: TextInputEditText? = null
    private var tie_Description: TextInputEditText? = null

    private var til_CompCategory: TextInputLayout? = null
    private var til_Category: TextInputLayout? = null
    private var til_Company: TextInputLayout? = null
    private var til_Product: TextInputLayout? = null
    private var til_Service: TextInputLayout? = null
    private var til_Complaint: TextInputLayout? = null
    private var til_Description: TextInputLayout? = null

    //  CONTACT DETAILS

    private var tie_ContactNo: TextInputEditText? = null
    private var tie_Landmark: TextInputEditText? = null

    // Requested Visit Date and Time

    private var tie_FromDate: TextInputEditText? = null
    private var tie_ToDate: TextInputEditText? = null
    private var tie_FromTime: TextInputEditText? = null
    private var tie_ToTime: TextInputEditText? = null
    private var tie_Location: TextInputEditText? = null

    // ATTENDED DETAILS

    private var tie_Status: TextInputEditText? = null
    private var tie_Attendedby: TextInputEditText? = null


    // ADD NEW CUSTOMER
    private var tie_CN_Name: TextInputEditText? = null
    private var tie_CN_Mobile: TextInputEditText? = null
    private var tie_CN_HouseName: TextInputEditText? = null
    private var tie_CN_Place: TextInputEditText? = null
    private var tie_CN_Pincode: TextInputEditText? = null
    private var tie_CN_Country: TextInputEditText? = null
    private var tie_CN_State: TextInputEditText? = null
    private var tie_CN_District: TextInputEditText? = null
    private var tie_CN_Area: TextInputEditText? = null
    private var tie_CN_Post: TextInputEditText? = null

    private var til_CN_Name: TextInputLayout? = null
    private var til_CN_Mobile: TextInputLayout? = null
    private var til_CN_HouseName: TextInputLayout? = null
    private var til_CN_Place: TextInputLayout? = null
    private var til_CN_Pincode: TextInputLayout? = null
    private var til_CN_Country: TextInputLayout? = null
    private var til_CN_State: TextInputLayout? = null
    private var til_CN_District: TextInputLayout? = null
    private var til_CN_Area: TextInputLayout? = null
    private var til_CN_Post: TextInputLayout? = null

    lateinit var leadGenerateDefaultvalueViewModel: LeadGenerationDefaultvalueViewModel
    var defaultCount = 0
    var FK_Place = ""
    var FK_Country = ""
    var FK_States = ""
    var FK_District = ""
    var FK_Area = ""
    var FK_Post = ""
    var pincodeCount = 0
    var countryCount = 0
    var stateCount = 0
    var districtCount = 0
    var areaCount = 0
    var postCount = 0
    var prioritymode = 0

    lateinit var pinCodeSearchViewModel: PinCodeSearchViewModel
    lateinit var pinCodeArrayList: JSONArray
    private var dialogPinCode: Dialog? = null
    var recyPinCode: RecyclerView? = null

    lateinit var countryViewModel: CountryViewModel
    lateinit var countryArrayList: JSONArray
    lateinit var countrySort: JSONArray
    private var dialogCountry: Dialog? = null
    var recyCountry: RecyclerView? = null
    var FK_Cust = ""
    var FK_OtherCustomer = ""
    var Prodid = ""
    lateinit var stateViewModel: StateViewModel
    lateinit var stateArrayList: JSONArray
    lateinit var stateSort: JSONArray
    private var dialogState: Dialog? = null
    var recyState: RecyclerView? = null

    lateinit var districtViewModel: DistrictViewModel
    lateinit var districtArrayList: JSONArray
    lateinit var districtSort: JSONArray
    private var dialogDistrict: Dialog? = null
    var recyDistrict: RecyclerView? = null


    lateinit var areaViewModel: AreaViewModel
    lateinit var areaArrayList: JSONArray
    lateinit var areaSort: JSONArray
    private var dialogArea: Dialog? = null
     var recycArea: RecyclerView? = null

    lateinit var postViewModel: PostViewModel
    lateinit var postArrayList: JSONArray
    lateinit var postSort: JSONArray
    private var dialogPost: Dialog? = null
    var recyPost: RecyclerView? = null

    var strCnName = ""
    var strCnMobile = ""
    var strCnHouseName  = ""
    var strCnPlace = ""
    var strCnPinCode = ""



    var custDetailMode: String? = "0"
    var complaintMode: String? = "1"
    var contDetailMode: String? = "1"
    var requestedMode: String? = "1"
    var attDetailMode: String? = "1"
    var locDetailMode: String? = "1"

    var warrantyMode: String? = "1"
    var prodHistMode: String? = "1"
    var saleHistMode: String? = "1"
    var custDueMode: String? = "1"

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null
    private var btnMore: Button? = null

    var dateMode = 0 // 0 = RegDate , 1 = Req FromDate , 2 = Req To date
    var timeMode = 0 // 0 = Req FromTime , 1 = Req ToTime
    var custDet = 0
    var priorityDet = 0
    var channelDet = 0
    var empMediaDet = 0
    var CompcategoryDet = 0
    var categoryDet = 0
    var companyDet = 0
    var productDet = 0
    var serviceDet = 0
    var complaintDet = 0
    var followUpAction = 0
    var saveOrupdate = 0

    var warrantyDet = 0
    var productHistDet = 0
    var salesHistDet = 0
    var cutDueDet = 0
    var custServiceCount = 0
    var categoryCount = 0

    var SubModeSearch: String? = ""
    var strCustomer: String? = ""


    var custNameMode = 0 // 0 Search , 1 =Clear
    var employeeMode = 0 // 0 Employee , 1 Attended By

    lateinit var customerListViewModel: CustomerListViewModel
    lateinit var customerArrayList: JSONArray
    lateinit var customerSort : JSONArray
    var dialogCustSearch: Dialog? = null

    lateinit var servicePriorityViewModel: ServicePriorityViewModel
    lateinit var servPriorityArrayList : JSONArray
    lateinit var servPrioritySort : JSONArray
    private var dialogServPriority : Dialog? = null
    var recyServPriority: RecyclerView? = null

    lateinit var commonViewModel: CommonViewModel
    lateinit var channelArrayList : JSONArray
    lateinit var channelSort : JSONArray
    private var dialogChannel : Dialog? = null
    var recyChannel: RecyclerView? = null

    lateinit var productCategoryViewModel: ProductCategoryViewModel

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null

    lateinit var serviceMediaViewModel: ServiceMediaViewModel
    lateinit var serviceMediaArrayList: JSONArray
    lateinit var serviceMediaSort: JSONArray
    private var dialogServiceMedia: Dialog? = null
    var recyServiceMedia: RecyclerView? = null

    lateinit var compCategoryArrayList : JSONArray
    lateinit var compCategorySort : JSONArray
    private var dialogcompCategory : Dialog? = null
    var recycompCategory: RecyclerView? = null

    lateinit var categoryArrayList : JSONArray
    lateinit var categorySort : JSONArray
    private var dialogCategory : Dialog? = null
    var recyCategory: RecyclerView? = null

    lateinit var companyArrayList : JSONArray
    lateinit var companySort : JSONArray
    private var dialogCompany : Dialog? = null
    var recyCompany: RecyclerView? = null

    lateinit var serviceProductViewModel: ServiceProductViewModel
    lateinit var productArrayList : JSONArray
    lateinit var productSort : JSONArray
    private var dialogProduct : Dialog? = null
    var recyProduct: RecyclerView? = null

    lateinit var serviceViewModel: ServiceViewModel
    lateinit var serviceArrayList : JSONArray
    lateinit var serviceSort : JSONArray
    private var dialogService : Dialog? = null
    var recyService: RecyclerView? = null

    lateinit var serviceComplaintViewModel: ServiceComplaintViewModel
    lateinit var complaintArrayList : JSONArray
    lateinit var complaintSort : JSONArray
    private var dialogComplaint : Dialog? = null
    var recyComplaint: RecyclerView? = null

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    lateinit var customerServiceRegisterViewModel: CustomerServiceRegisterViewModel



    var Customer_Type: String? = ""
    var ID_Customer: String? = ""
    var ID_Priority: String? = ""
    var ID_Channel: String? = ""
    var ID_Employee: String? = ""
    var ID_ServiceMedia: String? = ""
    var ID_EmpMedia: String? = ""
    var ID_CompCategory: String? = ""
    var ID_Category: String? = ""
    var ID_Company: String? = ""
    var ID_Product: String? = ""
    var ID_Services: String? = ""
    var ID_ComplaintList: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""

    var ReqMode: String? = ""
    var SubMode: String? = ""

    var strDate: String? = ""
    var strTime: String? = ""
    var strCustomerName: String? = ""
    var strMobileNo: String? = ""
    var strAddress: String? = ""
    var strPriority: String? = ""
    var strChannel: String? = ""
    var strChannelSub: String? = ""

    var strCategory: String? = ""
    var strCompany: String? = ""
    var strProduct: String? = ""
    var strService: String? = ""
    var strComplaint: String? = ""
    var strDescription: String? = ""

    var strContactNo: String? = ""
    var strLandMark: String? = ""

    var strFromDate: String? = ""
    var strToDate: String? = ""
    var strFromTime: String? = ""
    var strToTime: String? = ""

    var strStatus: String? = ""
    var strAttendedBy: String? = ""

    var strUserAction: String? = ""

    private var dialogDetailSheet : Dialog? = null
    private var dialogWarrantySheet : Dialog? = null
    private var dialogAddUserSheet : Dialog? = null


    private var lnrHead_warranty_main : LinearLayout? = null
    private var lnrHead_warranty_sub : LinearLayout? = null
    private var lnrHead_product_main : LinearLayout? = null
    private var lnrHead_product_sub : LinearLayout? = null
    private var lnrHead_sales_main : LinearLayout? = null
    private var lnrHead_sales_sub : LinearLayout? = null
    private var lnrHead_customerdue_main : LinearLayout? = null
    private var lnrHead_customerdue_sub : LinearLayout? = null

    private var ll_tab_warranty : LinearLayout? = null
    private var ll_tab_production : LinearLayout? = null
    private var ll_tab_sales : LinearLayout? = null
    private var ll_tab_customerdue : LinearLayout? = null

    private var horizontalScroll : HorizontalScrollView? = null

    private var card_warranty : CardView? = null
    private var card_production : CardView? = null
    private var card_sales : CardView? = null
    private var card_customerdue : CardView? = null

    private var txtNext : TextView? = null
    private var txt_Warning : TextView? = null

    private var tv_warranty_count : TextView? = null
    private var tv_product_count : TextView? = null
    private var tv_sales_count : TextView? = null
    private var tv_customerdue_count : TextView? = null

    var locAddress: String? = ""
    var locCity: String? = ""
    var locState: String? = ""
    var locCountry: String? = ""
    var locpostalCode: String? = ""
    var locKnownName: String? = ""
    var strLatitude: String? = ""
    var strLongitue: String? = ""
    var strLocationAddress: String? = ""
    var saveAttendanceMark = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_customer_service)
        context = this@CustomerServiceActivity

        serviceWarrantyViewModel = ViewModelProvider(this).get(ServiceWarrantyViewModel::class.java)
        serviceProductHistoryViewModel = ViewModelProvider(this).get(ServiceProductHistoryViewModel::class.java)
        serviceProductViewModel = ViewModelProvider(this).get(ServiceProductViewModel::class.java)
        serviceSalesViewModel = ViewModelProvider(this).get(ServiceSalesViewModel::class.java)
        customerDueViewModel = ViewModelProvider(this).get(CustomerDueViewModel::class.java)

        customerListViewModel = ViewModelProvider(this).get(CustomerListViewModel::class.java)
        servicePriorityViewModel = ViewModelProvider(this).get(ServicePriorityViewModel::class.java)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        serviceComplaintViewModel = ViewModelProvider(this).get(ServiceComplaintViewModel::class.java)
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceMediaViewModel = ViewModelProvider(this).get(ServiceMediaViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        customerServiceRegisterViewModel = ViewModelProvider(this).get(CustomerServiceRegisterViewModel::class.java)

        leadGenerateDefaultvalueViewModel = ViewModelProvider(this).get(LeadGenerationDefaultvalueViewModel::class.java)

        pinCodeSearchViewModel = ViewModelProvider(this).get(PinCodeSearchViewModel::class.java)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        stateViewModel = ViewModelProvider(this).get(StateViewModel::class.java)
        districtViewModel = ViewModelProvider(this).get(DistrictViewModel::class.java)
        areaViewModel = ViewModelProvider(this).get(AreaViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        setRegViews()

        Log.i("TAG Cust","132"+tie_CustomerName!!.text.toString())


        custDetailMode = "0"
        hideViews()
        checkAttendance()

        priorityDet = 0
        prioritymode = 0
        getProductPriority()

        ReqMode = "66"
        SubMode = "20"
        categoryCount = 0
        getCompCategory(ReqMode!!,SubMode!!)

        til_CustomerName!!.setEndIconOnClickListener {
           // finish()
            Config.disableClick(it)


          //  if (custNameMode == 0){
                custNameMode = 0
                custDet = 0
                ReqMode = "73"
                SubModeSearch = "1"
                strCustomer = tie_CustomerName!!.text.toString()
                if (strCustomer!!.length > 2){
                    getCustomerSearch()
                }else{
                    til_CustomerName!!.setError("Enter at least three characters to search")
                  //  til_CustomerName!!.setErrorIconDrawable(null)
                    custDetailMode = "0"
                    complaintMode  = "1"
                    contDetailMode = "1"
                    requestedMode  = "1"
                    attDetailMode  = "1"
                    locDetailMode  = "1"

                    hideViews()
                }

//            }else{
//                custNameMode = 0
//                til_CustomerName!!.setEndIconDrawable(context.resources.getDrawable(R.drawable.search_24))
//                tie_CustomerName!!.isEnabled = true
//                tie_MobileNo!!.isEnabled = true
//                tie_Address!!.isEnabled = true
//
//                ID_Customer = ""
//                Customer_Type = ""
//                tie_CustomerName!!.setText("")
//                tie_MobileNo!!.setText("")
//                tie_Address!!.setText("")
//            }
        }



    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imDetails = findViewById<ImageView>(R.id.imDetails)
        imback!!.setOnClickListener(this)
        imDetails!!.setOnClickListener(this)

        //Main Header
        tv_customerClick = findViewById<TextView>(R.id.tv_customerClick)
        tv_complaintClick = findViewById<TextView>(R.id.tv_complaintClick)
        tv_contactClick = findViewById<TextView>(R.id.tv_contactClick)
        tv_requestedClick = findViewById<TextView>(R.id.tv_requestedClick)
        tv_attendedClick = findViewById<TextView>(R.id.tv_attendedClick)
        tv_locationClick = findViewById<TextView>(R.id.tv_locationClick)

        lnrHead_customer = findViewById<LinearLayout>(R.id.lnrHead_customer)
        lnrHead_complaint = findViewById<LinearLayout>(R.id.lnrHead_complaint)
        lnrHead_contact = findViewById<LinearLayout>(R.id.lnrHead_contact)
        lnrHead_requested = findViewById<LinearLayout>(R.id.lnrHead_requested)
        lnrHead_attended = findViewById<LinearLayout>(R.id.lnrHead_attended)
        lnrHead_location = findViewById<LinearLayout>(R.id.lnrHead_location)

        til_Address = findViewById<TextInputLayout>(R.id.til_Address)
        til_EmpOrMedia = findViewById<TextInputLayout>(R.id.til_EmpOrMedia)


        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnMore = findViewById<Button>(R.id.btnMore)


        tv_customerClick!!.setOnClickListener(this)
        tv_complaintClick!!.setOnClickListener(this)
        tv_contactClick!!.setOnClickListener(this)
        tv_requestedClick!!.setOnClickListener(this)
        tv_attendedClick!!.setOnClickListener(this)
        tv_locationClick!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnMore!!.setOnClickListener(this)


        // Customer Details
        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        tie_Time = findViewById<TextInputEditText>(R.id.tie_Time)
        tie_CustomerName = findViewById<TextInputEditText>(R.id.tie_CustomerName)
        tie_MobileNo = findViewById<TextInputEditText>(R.id.tie_MobileNo)
        tie_Address = findViewById<TextInputEditText>(R.id.tie_Address)
        tie_Priority = findViewById<TextInputEditText>(R.id.tie_Priority)
        tie_Channel = findViewById<TextInputEditText>(R.id.tie_Channel)
        tie_EmpOrMedia = findViewById<TextInputEditText>(R.id.tie_EmpOrMedia)

        img_addusrpop = findViewById<ImageView>(R.id.img_addusrpop)

        til_Date = findViewById<TextInputLayout>(R.id.til_Date)
        til_Time = findViewById<TextInputLayout>(R.id.til_Time)
        til_CustomerName = findViewById<TextInputLayout>(R.id.til_CustomerName)
        til_MobileNo = findViewById<TextInputLayout>(R.id.til_MobileNo)
        til_Address = findViewById<TextInputLayout>(R.id.til_Address)
        til_Priority = findViewById<TextInputLayout>(R.id.til_Priority)


        tie_Date!!.setOnClickListener(this)
        tie_Time!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Channel!!.setOnClickListener(this)
        tie_EmpOrMedia!!.setOnClickListener(this)
        img_addusrpop!!.setOnClickListener(this)

        //Complaints

        tie_CompCategory = findViewById<TextInputEditText>(R.id.tie_CompCategory)
        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_Company = findViewById<TextInputEditText>(R.id.tie_Company)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)
        tie_Service = findViewById<TextInputEditText>(R.id.tie_Service)
        tie_Complaint = findViewById<TextInputEditText>(R.id.tie_Complaint)
        tie_Description = findViewById<TextInputEditText>(R.id.tie_Description)

        til_CompCategory = findViewById<TextInputLayout>(R.id.til_CompCategory)
        til_Category = findViewById<TextInputLayout>(R.id.til_Category)
        til_Company = findViewById<TextInputLayout>(R.id.til_Company)
        til_Product = findViewById<TextInputLayout>(R.id.til_Product)
        til_Service = findViewById<TextInputLayout>(R.id.til_Service)
        til_Complaint = findViewById<TextInputLayout>(R.id.til_Complaint)
        til_Description = findViewById<TextInputLayout>(R.id.til_Description)


        tie_CompCategory!!.setOnClickListener(this)
        tie_Category!!.setOnClickListener(this)
        tie_Company!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        tie_Service!!.setOnClickListener(this)
        tie_Complaint!!.setOnClickListener(this)


        //  CONTACT DETAILS

        tie_ContactNo = findViewById<TextInputEditText>(R.id.tie_ContactNo)
        tie_Landmark = findViewById<TextInputEditText>(R.id.tie_Landmark)


        // Requested Visit Date and Time

        tie_FromDate = findViewById<TextInputEditText>(R.id.tie_FromDate)
        tie_ToDate = findViewById<TextInputEditText>(R.id.tie_ToDate)
        tie_FromTime = findViewById<TextInputEditText>(R.id.tie_FromTime)
        tie_ToTime = findViewById<TextInputEditText>(R.id.tie_ToTime)
        tie_Location = findViewById<TextInputEditText>(R.id.tie_Location)

        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_FromTime!!.setOnClickListener(this)
        tie_ToTime!!.setOnClickListener(this)
        tie_Location!!.setOnClickListener(this)

        // ATTENDED DETAILS
        tie_Status = findViewById<TextInputEditText>(R.id.tie_Status)
        tie_Attendedby = findViewById<TextInputEditText>(R.id.tie_Attendedby)

        tie_Status!!.setOnClickListener(this)
        tie_Attendedby!!.setOnClickListener(this)





        til_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Time!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_MobileNo!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
    //    til_Address!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Priority!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_CompCategory!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Category!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        til_Complaint!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Service!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
      //  til_Description!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)


        onTextChangedValues()
        getCurrentDate()

    }

    private fun getCurrentDate() {
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val currentDate = sdf.format(Date())
//        tie_Date!!.setText(currentDate)
//        tie_FromDate!!.setText(currentDate)

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)


            tie_Date!!.setText(""+sdfDate1.format(newDate))
            tie_FromDate!!.setText(""+sdfDate1.format(newDate))
          //  strVisitDate = sdfDate2.format(newDate)

            tie_Time!!.setText(""+sdfTime1.format(newDate))
          //  strVisitTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun onTextChangedValues() {

       // til_Date!!.setHintTextColor(context.resources.getColor(R.color.colorPrimary))



        // customer Details
        tie_Date!!.addTextChangedListener(watcher);
        tie_Time!!.addTextChangedListener(watcher);
        tie_CustomerName!!.addTextChangedListener(watcher);
        tie_MobileNo!!.addTextChangedListener(watcher);
        tie_Address!!.addTextChangedListener(watcher);
        tie_Priority!!.addTextChangedListener(watcher);

        //Complaint
        tie_CompCategory!!.addTextChangedListener(watcher);
        tie_Category!!.addTextChangedListener(watcher);
        tie_Product!!.addTextChangedListener(watcher);
        tie_Service!!.addTextChangedListener(watcher);
        tie_Complaint!!.addTextChangedListener(watcher);
        tie_Description!!.addTextChangedListener(watcher);

    }


//    private fun getComplaints() {
//
//        llBtn!!.visibility = View.VISIBLE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_complaints, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//
//        tie_Comp_FromDate= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_FromDate)
//        tie_Comp_ToDate= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_ToDate)
//        tie_Comp_FromTime= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_FromTime)
//        tie_Comp_ToTime= inflatedLayout.findViewById<TextInputEditText>(R.id.tie_Comp_ToTime)
//
//        tie_Comp_FromDate!!.setOnClickListener(this)
//        tie_Comp_ToDate!!.setOnClickListener(this)
//        tie_Comp_FromTime!!.setOnClickListener(this)
//        tie_Comp_ToTime!!.setOnClickListener(this)
//
//    }
//




    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.imDetails->{
                Config.disableClick(v)
              // detailBottomSheet()

                var custname=tie_CustomerName!!.text.toString()
                if (ID_Customer!!.equals("")){

                }else{
                    detailPopupSheet(custname)
                }

            }
            R.id.tv_customerClick->{

                 custDetailMode = "0"
                 complaintMode  = "1"
                 contDetailMode = "1"
                 requestedMode  = "1"
                 attDetailMode  = "1"
                locDetailMode  = "1"

                hideViews()
            }

            R.id.tv_complaintClick->{
                custDetailMode = "1"
                complaintMode  = "0"
                contDetailMode = "1"
                requestedMode  = "1"
                attDetailMode  = "1"
                locDetailMode  = "1"

                ReqMode = "66"
                SubMode = "20"
                categoryCount = 0
                getCompCategory(ReqMode!!,SubMode!!)

                hideViews()
            }

            R.id.tv_contactClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "0"
                requestedMode  = "1"
                attDetailMode  = "1"
                locDetailMode  = "1"

                hideViews()
            }

            R.id.tv_requestedClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "1"
                requestedMode  = "0"
                attDetailMode  = "1"
                locDetailMode  = "1"

                hideViews()
            }
            R.id.tv_attendedClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "1"
                requestedMode  = "1"
                attDetailMode  = "0"
                locDetailMode  = "0"

                hideViews()
            }

            R.id.tv_locationClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "1"
                requestedMode  = "1"
                attDetailMode  = "1"
                locDetailMode  = "0"

                hideViews()
            }

            R.id.tie_Date->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
            }
            R.id.tie_Time->{
                Config.disableClick(v)
                timeMode = 2
                openBottomTime()
            }

            R.id.tie_Priority->{
                Config.disableClick(v)
                priorityDet = 0
                prioritymode = 1
                getProductPriority()

            }

            R.id.tie_Channel->{
                Config.disableClick(v)
                channelDet = 0
                ReqMode = "66"
                SubMode = "22"
                getChannel(ReqMode!!,SubMode!!)

            }

            R.id.tie_EmpOrMedia->{
                Config.disableClick(v)
                empMediaDet = 0
                if (ID_Channel.equals("5")){
                    employeeMode = 0
                    getChannelEmp()
                }
                if (ID_Channel.equals("6")){
                    ReqMode = "74"
                   getServiceMedia()
                }


            }
            R.id.img_addusrpop->{
                Config.disableClick(v)
                addNewUserBottom()
            }

            R.id.tie_CompCategory->{
                Config.disableClick(v)
                CompcategoryDet = 0
                ReqMode = "66"
                SubMode = "20"
                categoryCount = 1
                getCompCategory(ReqMode!!,SubMode!!)

            }

            R.id.tie_Category->{
                Config.disableClick(v)
                if(ID_CompCategory.equals("")){
                    til_CompCategory!!.setError("Select Complaint Category");
                    til_CompCategory!!.setErrorIconDrawable(null)
                }
                else{
                    categoryDet = 0
                    ReqMode = "77"
                    getCategory(ReqMode!!,ID_CompCategory!!)
                }


            }

            R.id.tie_Company->{
                Config.disableClick(v)
                companyDet = 0
                ReqMode = "67"
                SubMode = ""
                getCompany(ReqMode!!,SubMode!!)

            }

            R.id.tie_Product->{
                Config.disableClick(v)

                if (ID_Category.equals("")){
                    til_Category!!.setError("Select Category");
                    til_Category!!.setErrorIconDrawable(null)
                }else{
                    productDet = 0
                    ReqMode = "68"
                    SubMode = ""
                    getProduct(ReqMode!!,SubMode!!)
                }


            }

            R.id.tie_Service->{
                Config.disableClick(v)
                serviceDet = 0
                ReqMode = "69"
                SubMode = ""
                if (!ID_Category.equals("")){
                    getServices(ReqMode!!,SubMode!!)
                }else{
                  //  Config.snackBars(context,v,"Select Product")
                    til_Category!!.setError("Select Category");
                    til_Category!!.setErrorIconDrawable(null)
                }
            }

            R.id.tie_Complaint->{
                Config.disableClick(v)
                complaintDet = 0
                ReqMode = "70"
                SubMode = ""
                if (!ID_Category.equals("")){
                    getComplaints(ReqMode!!,SubMode!!)
                }else{
                   // Config.snackBars(context,v,"Select Product")
                    til_Category!!.setError("Select Category");
                    til_Category!!.setErrorIconDrawable(null)
                }
            }

            R.id.tie_Status->{
                Config.disableClick(v)
                followUpAction = 0
                ReqMode = "17"
                SubMode = "2"
                getFollowupAction()
            }

            R.id.tie_Attendedby->{
                Config.disableClick(v)
                empMediaDet = 0
                employeeMode = 1
                getChannelEmp()
            }


            R.id.tie_FromDate->{
                Config.disableClick(v)
                dateMode = 1
                openBottomDate()
            }

            R.id.tie_ToDate->{
                Config.disableClick(v)
                dateMode = 2
                openBottomDate()
            }

            R.id.tie_FromTime->{
                Config.disableClick(v)
                timeMode = 0
                openBottomTime()
            }

            R.id.tie_ToTime->{
                Config.disableClick(v)
                timeMode = 1
                openBottomTime()
            }

            R.id.tie_CN_Country->{
                Config.disableClick(v)
                countryCount = 0
                getCountry()

            }

            R.id.tie_Location->{

                Log.e(TAG,"1031  tie_Location ")
                Config.disableClick(v)
                val intent = Intent(this@CustomerServiceActivity, LocationPickerActivity::class.java)
                intent.putExtra("mode","1")
                startActivityForResult(intent, SELECT_LOCATION!!);

            }

            R.id.tie_CN_State->{
                Config.disableClick(v)
                if (FK_Country.equals("")) {
                    til_CN_Country!!.setError("Select Country");
                    til_CN_Country!!.setErrorIconDrawable(null)
                }else{
                    stateCount = 0
                    getState()
                }


            }
            R.id.tie_CN_District->{
                Config.disableClick(v)
                if (FK_States.equals("")) {
                    til_CN_State!!.setError("Select State");
                    til_CN_State!!.setErrorIconDrawable(null)
                }else{
                    districtCount = 0
                    getDistrict()
                }


            }

            R.id.tie_CN_Area->{
                Config.disableClick(v)
                if (FK_District.equals("")) {
                    til_CN_District!!.setError("Select District");
                    til_CN_District!!.setErrorIconDrawable(null)
                } else {
                    areaCount = 0
                    getArea()
                }


            }

            R.id.tie_CN_Post->{
                Config.disableClick(v)
                if (FK_Area.equals("")) {
                    til_CN_Area!!.setError("Select Area");
                    til_CN_Area!!.setErrorIconDrawable(null)
                } else {
                    postCount = 0
                    getPost()
                }


            }

//            R.id.tie_Comp_FromDate->{
//                fromToDate = 0
//                openBottomSheet()
//            }
//            R.id.tie_Comp_ToDate->{
//                fromToDate = 1
//                openBottomSheet()
//            }
//            R.id.tie_Comp_FromTime->{
//                fromToTime = 0
//                openBottomSheetTime()
//            }
//            R.id.tie_Comp_ToTime->{
//                fromToTime = 1
//                openBottomSheetTime()
//            }


            R.id.btnSubmit->{
              //  til_Address!!.setError("You need to enter a name");
                checkAttendance()
                if (saveAttendanceMark){
                    validation()
                }


       //   dateTimevalidations()


//
//                strFromTime = tie_FromTime!!.text.toString()
//                strToTime = tie_ToTime!!.text.toString()
//                val inputTimeFormat = SimpleDateFormat("h:mm a")
//                val outputTimeFormat = SimpleDateFormat("HH:mm")
//
//                val c = Calendar.getInstance()
//                val formattedDate: String = inputTimeFormat.format(c.time)
//                Log.e(TAG,"formattedDate   1302   "+formattedDate)
//
//                var date: Date? = null
//                date = inputTimeFormat.parse(formattedDate)
//                val strFromTime1 = outputTimeFormat.format(date)
//                Log.e(TAG,"DATE   1302   "+strFromTime1)
//
//                var date1: Date? = null
//                date1 = inputTimeFormat.parse(strToTime)
//                val strToTime1 = outputTimeFormat.format(date1)
//                Log.e(TAG,"DATE   1302   "+strToTime1)
//
//                if(strFromTime1.compareTo(strToTime1) <= 0) {
//                    Log.e(TAG,"  8984   "+strFromTime1+"  <=  "+strToTime1)
//                }
//                else{
//                    Log.e(TAG,"  8985   "+strFromTime1+"  :  "+strToTime1)
//                }




            }

            R.id.btnReset->{
                //  til_Address!!.setError("You need to enter a name");

                resetData()
            }

            R.id.btnMore->{
                Config.disableClick(v)
              //  detailBottomSheet()
                var custname=tie_CustomerName!!.text.toString()
                if (ID_Customer!!.equals("")){

                    Config.snackBarWarning(context,v,"Select Customer")

                }else{
                    detailPopupSheet(custname)
                }
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

    private fun dateTimevalidations() {

        strFromDate = tie_FromDate!!.text.toString()
        strToDate = tie_ToDate!!.text.toString()
        strFromTime = tie_FromTime!!.text.toString()
        strToTime = tie_ToTime!!.text.toString()


        val sdfDate = SimpleDateFormat("dd-MM-yyyy")
        val sdfTime = SimpleDateFormat("h:mm a")
        val outputTimeFormat = SimpleDateFormat("HH:mm")

        val c = Calendar.getInstance()
        val currentDate = sdfDate.format(Date())
        val strcurrentDate1 = sdfDate.parse(currentDate)
        val strFromDate1 = sdfDate.parse(strFromDate)
        val currentTime = sdfTime.format(c.time)
        var time: Date? = null
        time = sdfTime.parse(currentTime)
        val strCurrentTime1 = outputTimeFormat.format(time)
        Log.e(TAG," currentTime  961   "+currentTime)
        Log.e(TAG,"DATE   961   "+strCurrentTime1)


        if (!strFromDate.equals("") && !strToDate.equals("") && !strFromTime.equals("") && !strToTime.equals("")) {

            val strFromDate1 = sdfDate.parse(strFromDate)
            val strToDate1 = sdfDate.parse(strToDate)

            var fromT: Date? = null
            fromT = sdfTime.parse(strFromTime)
            val strFromTime1 = outputTimeFormat.format(fromT)
            Log.e(TAG,"DATE   1302   "+strFromTime1)

            var toT: Date? = null
            toT = sdfTime.parse(strToTime)
            val strToTime1 = outputTimeFormat.format(toT)
            Log.e(TAG,"DATE   1302   "+strToTime1)


            Log.e(TAG,"All Data  945")

            if (strFromDate.equals(currentDate) && strToDate.equals(currentDate)){

                if ((strCurrentTime1.compareTo(strFromTime1) <= 0) && (strFromTime1.compareTo(strToTime1) <= 0)){
                    Log.e(TAG,"All Data  9451  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"All Data  9452  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)

                }

            }
            else if(strFromDate.equals(currentDate) && strFromDate1.before(strToDate1)){
                if (strCurrentTime1.compareTo(strFromTime1) <= 0){
                    Log.e(TAG,"All Data  9453  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)

                }
                else{
                    Log.e(TAG,"All Data  9454  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)

                }
            }
            else if(strFromDate1.before(strToDate1) || strFromDate1.equals(strToDate1)){
                Log.e(TAG,"All Data  9455  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
            else{
                Log.e(TAG,"All Data  9456  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)

            }

        }

        else if (!strFromDate.equals("") && !strToDate.equals("") && !strFromTime.equals("") && strToTime.equals("")) {

            val strFromDate1 = sdfDate.parse(strFromDate)
            val strToDate1 = sdfDate.parse(strToDate)

            var fromT: Date? = null
            fromT = sdfTime.parse(strFromTime)
            val strFromTime1 = outputTimeFormat.format(fromT)
            Log.e(TAG,"DATE   1302   "+strFromTime1)


            Log.e(TAG,"To Time Empty  984")

            if (strFromDate.equals(currentDate) && strToDate.equals(currentDate)){

                if (strCurrentTime1.compareTo(strFromTime1) <= 0){
                    Log.e(TAG,"To Time Empty  9841  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"To Time Empty  9842  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }

            }
            else if(strFromDate.equals(currentDate) && strFromDate1.before(strToDate1)){
                if (strCurrentTime1.compareTo(strFromTime1) <= 0){
                    Log.e(TAG,"To Time Empty  9843  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"To Time Empty  9844  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
            }
            else if(strFromDate1.before(strToDate1) || strFromDate1.equals(strToDate1)){
                Log.e(TAG,"To Time Empty  9845  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
            else{
                Log.e(TAG,"To Time Empty  9846  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }

        }

        else if (!strFromDate.equals("") && !strToDate.equals("") && strFromTime.equals("") && !strToTime.equals("")) {

            val strFromDate1 = sdfDate.parse(strFromDate)
            val strToDate1 = sdfDate.parse(strToDate)

            var toT: Date? = null
            toT = sdfTime.parse(strToTime)
            val strToTime1 = outputTimeFormat.format(toT)
            Log.e(TAG,"DATE   1302   "+strToTime1)

            Log.e(TAG,"From Time Empty  1023")

            if (strFromDate.equals(currentDate) && strToDate.equals(currentDate)){

                if (strCurrentTime1.compareTo(strToTime1) <= 0){
                    Log.e(TAG,"From Time Empty  10231  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"From Time Empty  10232  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }

            }
            else if(strFromDate.equals(currentDate) && strFromDate1.before(strToDate1)){
                if (strCurrentTime1.compareTo(strToTime1) <= 0){
                    Log.e(TAG,"From Time Empty  10233  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"From Time Empty  10234  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
            }
            else if(strFromDate1.before(strToDate1) || strFromDate1.equals(strToDate1)){
                Log.e(TAG,"From Time Empty  10235  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
            else{
                Log.e(TAG,"From Time Empty  10236  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }

        }

        else if (!strFromDate.equals("") && strToDate.equals("") && !strFromTime.equals("") && !strToTime.equals("")) {




            var fromT: Date? = null
            fromT = sdfTime.parse(strFromTime)
            val strFromTime1 = outputTimeFormat.format(fromT)
            Log.e(TAG,"DATE   1302   "+strFromTime1)

            var toT: Date? = null
            toT = sdfTime.parse(strToTime)
            val strToTime1 = outputTimeFormat.format(toT)
            Log.e(TAG,"DATE   1302   "+strToTime1)

            Log.e(TAG,"DATE   1302   "+strFromTime1)


            Log.e(TAG,"To date Empty  1065")

          if(strFromDate.equals(currentDate)){

              if ((strCurrentTime1.compareTo(strFromTime1) <= 0) && (strFromTime1.compareTo(strToTime1) <= 0)){
                  Log.e(TAG,"To date Empty  10651  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
              }
              else{
                  Log.e(TAG,"To date Empty  10652  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
              }

            }
              else if(strcurrentDate1.before(strFromDate1)){
              Log.e(TAG,"To date Empty  106523  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
              }
            else{
              Log.e(TAG,"To date Empty  106524  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }

        }

        else if (!strFromDate.equals("") && !strToDate.equals("") && strFromTime.equals("") && strToTime.equals("")) {

            val strFromDate1 = sdfDate.parse(strFromDate)
            val strToDate1 = sdfDate.parse(strToDate)
            Log.e(TAG,"From & To Time Empty 1097")
           if(strFromDate1.before(strToDate1) || strFromDate1.equals(strToDate1)){
                Log.e(TAG,"From & To Time Empty 10971  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
            else{
                Log.e(TAG,"From & To Time Empty 10972  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
        }

        else if (!strFromDate.equals("") && strToDate.equals("") && !strFromTime.equals("") && strToTime.equals("")) {

            val strFromDate1 = sdfDate.parse(strFromDate)

            var fromT: Date? = null
            fromT = sdfTime.parse(strFromTime)
            val strFromTime1 = outputTimeFormat.format(fromT)
            Log.e(TAG,"DATE   1302   "+strFromTime1)

            Log.e(TAG,"To date & Time Empty  1106")

            if(strFromDate.equals(currentDate)){
                if (strCurrentTime1.compareTo(strFromTime1) <= 0){
                    Log.e(TAG,"To date & Time Empty  11061  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"To date & Time Empty  11062  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
            }
            else if(strcurrentDate1.before(strFromDate1)){
                Log.e(TAG,"To date & Time Empty  11063  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
            else{
                Log.e(TAG,"To date & Time Empty  11064  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }
        }

        else if (!strFromDate.equals("") && strToDate.equals("") && strFromTime.equals("") && !strToTime.equals("")) {


            var toT: Date? = null
            toT = sdfTime.parse(strToTime)
            val strToTime1 = outputTimeFormat.format(toT)
            Log.e(TAG,"DATE   1302   "+strToTime1)

            Log.e(TAG,"To date & From Time Empty  1135")

            if(strFromDate.equals(currentDate)){
                if (strCurrentTime1.compareTo(strToTime1) <= 0){
                    Log.e(TAG,"To date & From Time Empty  11351  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
                else{
                    Log.e(TAG,"To date & From Time Empty  11352  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
                }
            }
            else{
                Log.e(TAG,"To date & From Time Empty  11353  "+strFromDate +" : "+strToDate+" : "+strFromTime+" : "+strToTime)
            }


        }

        else {


        }




    }


    private fun resetData() {
        custDetailMode = "0"
        complaintMode  = "1"
        contDetailMode = "1"
        requestedMode  = "1"
        attDetailMode  = "1"
        hideViews()

        // Customer Details

        ID_Customer = ""
        ID_Priority = ""
        ID_Channel = ""
        ID_Employee = ""
        ID_EmpMedia = ""
        ID_ServiceMedia = ""

        tie_Date!!.setText("")
        tie_Time!!.setText("")
        tie_CustomerName!!.setText("")
        tie_MobileNo!!.setText("")
        tie_Address!!.setText("")
        tie_Priority!!.setText("")
        tie_Channel!!.setText("")
        tie_EmpOrMedia!!.setText("")

        tie_CustomerName!!.isEnabled = true
      //  tie_MobileNo!!.isEnabled = true
        tie_Address!!.isEnabled = true

        til_EmpOrMedia!!.visibility = View.GONE

        // Complaint
        ID_CompCategory = ""
        ID_Category = ""
        ID_Company = ""
        ID_Product = ""
        ID_Services = ""
        ID_ComplaintList = ""

        tie_CompCategory!!.setText("")
        tie_Category!!.setText("")
        tie_Company!!.setText("")
        tie_Product!!.setText("")
        tie_Service!!.setText("")
        tie_Complaint!!.setText("")
        tie_Description!!.setText("")

        til_Company!!.visibility = View.GONE
        til_Service!!.visibility = View.GONE
        til_Complaint!!.visibility = View.GONE

        // Contact Details
        tie_ContactNo!!.setText("")
        tie_Landmark!!.setText("")

        // Requested Date Time
        tie_FromDate!!.setText("")
        tie_ToDate!!.setText("")
        tie_FromTime!!.setText("")
        tie_ToTime!!.setText("")

        // Attended Details
        ID_Status = ""
        ID_AttendedBy = ""

        tie_Status!!.setText("")
        tie_Attendedby!!.setText("")

        getCurrentDate()

    }

    private fun validation() {
        strDate = tie_Date!!.text.toString()
        strTime = tie_Time!!.text.toString()
        strCustomerName = tie_CustomerName!!.text.toString()
        strMobileNo = tie_MobileNo!!.text.toString()
        strAddress = tie_Address!!.text.toString()
        strPriority = tie_Priority!!.text.toString()
        strChannel = tie_Channel!!.text.toString()
        strChannelSub = tie_EmpOrMedia!!.text.toString()





        if (strDate.equals("")){
            til_Date!!.setError("Select Date");
            til_Date!!.setErrorIconDrawable(null);
            custDetailMode = "0"
            complaintMode  = "1"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else if(strTime.equals("")){
            til_Time!!.setError("Select Time");
            til_Time!!.setErrorIconDrawable(null);
            custDetailMode = "0"
            complaintMode  = "1"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else if (ID_Customer.equals("") && strCustomerName.equals("")){
//            && strCustomerName.equals("")
           // til_CustomerName!!.setError("Select Customer ")
            til_CustomerName!!.setError("Enter or Select Customer ")
            til_CustomerName!!.setErrorIconDrawable(null)
            custDetailMode = "0"
            complaintMode  = "1"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else if (strMobileNo!!.length < 10){
            til_MobileNo!!.setError("Enter Mobile Number");
            til_MobileNo!!.setErrorIconDrawable(null)
            custDetailMode = "0"
            complaintMode  = "1"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
//        else if (strAddress.equals("")){
//            til_Address!!.setError("Enter House Name");
//            til_Address!!.setErrorIconDrawable(null)
//            custDetailMode = "0"
//            complaintMode  = "1"
//            contDetailMode = "1"
//            requestedMode  = "1"
//            attDetailMode  = "1"
//
//            hideViews()
//        }
        else if (ID_Priority.equals("")){
            til_Priority!!.setError("Select Priority");
            til_Priority!!.setErrorIconDrawable(null)
            custDetailMode = "0"
            complaintMode  = "1"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else{
          Log.e(TAG,"Validation   9371"
                  +"\n"+"Date           :  "+strDate
                  +"\n"+"strTime        :  "+strTime
                  +"\n"+"Customer Name  :  "+strCustomerName
                  +"\n"+"Customer ID    :  "+ID_Customer
                  +"\n"+"Mobile Number  :  "+strMobileNo
                  +"\n"+"Address        :  "+strAddress
                  +"\n"+"ID Priority    :  "+ID_Priority
                  +"\n"+"ID Channel     :  "+ID_Channel
                  +"\n"+"ID Employee    :  "+ID_Employee
                  +"\n"+"ID EmpMedia    :  "+ID_EmpMedia
                  +"\n"+"ID Media       :  "+ID_ServiceMedia)

            validation1()

        }
    }

    private fun validation1() {
        Log.e(TAG,"Validation   1")
      //  strCategory = tie_CompCategory!!.text.toString()
        strCategory = tie_Category!!.text.toString()
        strCompany = tie_Company!!.text.toString()
        strProduct = tie_Product!!.text.toString()
        strService = tie_Service!!.text.toString()
        strComplaint = tie_Complaint!!.text.toString()
        strDescription = tie_Description!!.text.toString()


        if (ID_CompCategory.equals("")){
            Log.e(TAG,"Validation   2")
            til_CompCategory!!.setError("Select Complaint Category");
            til_CompCategory!!.setErrorIconDrawable(null)

            custDetailMode = "1"
            complaintMode  = "0"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else if (ID_Category.equals("")){
            Log.e(TAG,"Validation   3")

            til_Category!!.setError("Select Category");
            til_Category!!.setErrorIconDrawable(null)

            custDetailMode = "1"
            complaintMode  = "0"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
//        else if (ID_Product.equals("")){
//            til_Product!!.setError("Select Product");
//            til_Product!!.setErrorIconDrawable(null)
//
//            custDetailMode = "1"
//            complaintMode  = "0"
//            contDetailMode = "1"
//            requestedMode  = "1"
//            attDetailMode  = "1"
//
//            hideViews()
//        }
        else if ((ID_CompCategory.equals("1") || ID_CompCategory.equals("2")) && ID_Services.equals("")){
            Log.e(TAG,"Validation   4")
            til_Service!!.setError("Select Service");
            til_Service!!.setErrorIconDrawable(null)

            custDetailMode = "1"
            complaintMode  = "0"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()
        }
        else if ((ID_CompCategory.equals("3") || ID_CompCategory.equals("4")) && ID_ComplaintList.equals("")){
            Log.e(TAG,"Validation   5")
            til_Complaint!!.setError("Select Complaint");
            til_Complaint!!.setErrorIconDrawable(null)

            custDetailMode = "1"
            complaintMode  = "0"
            contDetailMode = "1"
            requestedMode  = "1"
            attDetailMode  = "1"

            hideViews()

        }
//        else if (strDescription.equals("")){
//            til_Description!!.setError("Enter Description");
//            til_Description!!.setErrorIconDrawable(null)
//
//            custDetailMode = "1"
//            complaintMode  = "0"
//            contDetailMode = "1"
//            requestedMode  = "1"
//            attDetailMode  = "1"
//
//            hideViews()
//
//        }
        else{
            Log.e(TAG,"Validation   9372"
                    +"\n"+"ID_CompCategory   :  "+ID_CompCategory
                    +"\n"+"ID_Category       :  "+ID_Category
                    +"\n"+"ID_Company        :  "+ID_Company
                    +"\n"+"ID_Company        :  "+ID_Company
                    +"\n"+"ID_Product        :  "+ID_Product
                    +"\n"+"ID_Services       :  "+ID_Services
                    +"\n"+"ID_ComplaintList  :  "+ID_ComplaintList
                    +"\n"+"strDescription    :  "+strDescription)

            strContactNo = tie_ContactNo!!.text.toString()
            strLandMark = tie_Landmark!!.text.toString()

            strFromDate = tie_FromDate!!.text.toString()
            strToDate = tie_ToDate!!.text.toString()
            strFromTime = tie_FromTime!!.text.toString()
            strToTime = tie_ToTime!!.text.toString()

            strStatus = tie_Status!!.text.toString()
            strAttendedBy = tie_Attendedby!!.text.toString()


            confirmationPopup()

        }
        Log.e(TAG,"Validation   6")
        Log.e(TAG,"Validation   1"+ID_CompCategory)
        Log.e(TAG,"Validation   1"+ID_ComplaintList)
        Log.e(TAG,"Validation   1"+ID_Services)
    }

    private fun confirmationPopup() {
        try {

            val dialogConfirm = Dialog(this)
            dialogConfirm!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirm!! .setContentView(R.layout.service_register_confirm)
            dialogConfirm!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogConfirm.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialogConfirm!!.setCancelable(false)

            // Customer Details
            val tv_conf_date = dialogConfirm!!.findViewById(R.id.tv_conf_date) as TextView
            val tv_conf_time = dialogConfirm!!.findViewById(R.id.tv_conf_time) as TextView
            val tv_conf_name = dialogConfirm!!.findViewById(R.id.tv_conf_name) as TextView
            val tv_conf_mobile = dialogConfirm!!.findViewById(R.id.tv_conf_mobile) as TextView
            val tv_conf_address = dialogConfirm!!.findViewById(R.id.tv_conf_address) as TextView
            val tv_conf_priority = dialogConfirm!!.findViewById(R.id.tv_conf_priority) as TextView
            val tv_conf_channel = dialogConfirm!!.findViewById(R.id.tv_conf_channel) as TextView
            val tv_conf_sub = dialogConfirm!!.findViewById(R.id.tv_conf_sub) as TextView
            val tv_conf_sub_head = dialogConfirm!!.findViewById(R.id.tv_conf_sub_head) as TextView

            val ll_conf_channel = dialogConfirm!!.findViewById(R.id.ll_conf_channel) as LinearLayout
            val ll_conf_channel_sub = dialogConfirm!!.findViewById(R.id.ll_conf_channel_sub) as LinearLayout


            // Customer Details

            tv_conf_date!!.setText(""+strDate)
            tv_conf_time!!.setText(""+strTime)
            tv_conf_name!!.setText(""+strCustomerName)
            tv_conf_mobile!!.setText(""+strMobileNo)
            tv_conf_address!!.setText(""+strAddress)
            tv_conf_priority!!.setText(""+strPriority)
            tv_conf_channel!!.setText(""+strChannel)
            tv_conf_sub!!.setText(""+strChannelSub)
            tv_conf_sub_head!!.setText(""+strChannel)

            if (ID_Channel!!.equals("")){
                Log.e(TAG,"ID_Channel   11541   "+ID_Channel!!.length)
                ll_conf_channel!!.visibility = View.GONE
            }

            if (ID_EmpMedia.equals("")){
                ll_conf_channel_sub.visibility =View.GONE
            }


            // Complaints

            val tv_comp_category = dialogConfirm!!.findViewById(R.id.tv_comp_category) as TextView
            val tv_comp_company = dialogConfirm!!.findViewById(R.id.tv_comp_company) as TextView
            val tv_comp_product = dialogConfirm!!.findViewById(R.id.tv_comp_product) as TextView
            val tv_comp_service = dialogConfirm!!.findViewById(R.id.tv_comp_service) as TextView
            val tv_comp_complaint = dialogConfirm!!.findViewById(R.id.tv_comp_complaint) as TextView
            val tv_comp_desc = dialogConfirm!!.findViewById(R.id.tv_comp_desc) as TextView

            val ll_comp_company = dialogConfirm!!.findViewById(R.id.ll_comp_company) as LinearLayout
            val ll_comp_product = dialogConfirm!!.findViewById(R.id.ll_comp_product) as LinearLayout
            val ll_comp_service = dialogConfirm!!.findViewById(R.id.ll_comp_service) as LinearLayout
            val ll_comp_complaint = dialogConfirm!!.findViewById(R.id.ll_comp_complaint) as LinearLayout


            tv_comp_category!!.setText(""+strCategory)
            tv_comp_company!!.setText(""+strCompany)
            tv_comp_product!!.setText(""+strProduct)
            tv_comp_service!!.setText(""+strService)
            tv_comp_complaint!!.setText(""+strComplaint)
            tv_comp_desc!!.setText(""+strDescription)

            if (ID_Company!!.equals("")){
                ll_comp_company!!.visibility = View.GONE
            }
            if (ID_Product!!.equals("")){
                ll_comp_product!!.visibility = View.GONE
            }
            if (ID_Services!!.equals("")){
                ll_comp_service!!.visibility = View.GONE
            }
            if (ID_ComplaintList!!.equals("")){
                ll_comp_complaint!!.visibility = View.GONE
            }

            // Contact Details

            val tv_cont_no = dialogConfirm!!.findViewById(R.id.tv_cont_no) as TextView
            val tv_cont_landmark = dialogConfirm!!.findViewById(R.id.tv_cont_landmark) as TextView

            val ll_contact = dialogConfirm!!.findViewById(R.id.ll_contact) as LinearLayout
            val ll_cont_no = dialogConfirm!!.findViewById(R.id.ll_cont_no) as LinearLayout
            val ll_cont_landmark = dialogConfirm!!.findViewById(R.id.ll_cont_landmark) as LinearLayout

            tv_cont_no!!.setText(""+strContactNo)
            tv_cont_landmark!!.setText(""+strLandMark)

            if (strContactNo!!.equals("") && strLandMark!!.equals("")){
                ll_contact!!.visibility = View.GONE
            }
            if (strContactNo!!.equals("")){
                ll_cont_no!!.visibility = View.GONE
            }

            if (strLandMark!!.equals("")){
                ll_cont_landmark!!.visibility = View.GONE
            }

            // Requested date Time

            val tv_req_fromdate = dialogConfirm!!.findViewById(R.id.tv_req_fromdate) as TextView
            val tv_req_todate = dialogConfirm!!.findViewById(R.id.tv_req_todate) as TextView
            val tv_req_fromtime = dialogConfirm!!.findViewById(R.id.tv_req_fromtime) as TextView
            val tv_req_totime = dialogConfirm!!.findViewById(R.id.tv_req_totime) as TextView

            tv_req_fromdate!!.setText(""+strFromDate)
            tv_req_todate!!.setText(""+strToDate)
            tv_req_fromtime!!.setText(""+strFromTime)
            tv_req_totime!!.setText(""+strToTime)

            // Attended details

            val tv_attend_status = dialogConfirm!!.findViewById(R.id.tv_attend_status) as TextView
            val tv_attend_by = dialogConfirm!!.findViewById(R.id.tv_attend_by) as TextView

            val ll_attend = dialogConfirm!!.findViewById(R.id.ll_attend) as LinearLayout
            val ll_attend_status = dialogConfirm!!.findViewById(R.id.ll_attend_status) as LinearLayout
            val ll_attend_by = dialogConfirm!!.findViewById(R.id.ll_attend_by) as LinearLayout

            tv_attend_status!!.setText(""+strStatus)
            tv_attend_by!!.setText(""+strAttendedBy)

            if (strStatus!!.equals("") && strAttendedBy!!.equals("")){
                ll_attend!!.visibility = View.GONE
            }
            if (strStatus!!.equals("")){
                ll_attend_status!!.visibility = View.GONE
            }

            if (strAttendedBy!!.equals("")){
                ll_attend_by!!.visibility = View.GONE
            }

            val btnConfirmCancel = dialogConfirm!!.findViewById(R.id.btnConfirmCancel) as Button
            val btnConfirmSubmit = dialogConfirm!!.findViewById(R.id.btnConfirmSubmit) as Button

            btnConfirmCancel!!.setOnClickListener {
                Config.disableClick(it)
                dialogConfirm.dismiss()
            }

            btnConfirmSubmit!!.setOnClickListener {
                Config.disableClick(it)
                dialogConfirm.dismiss()

                val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val inputTimeFormat = SimpleDateFormat("h:mm a")
                val outputTimeFormat = SimpleDateFormat("HH:mm")

                Log.e(TAG,"DATE   13021   ")

                if (strDate!!.equals("")){
//                    strDate = "0000-00-00 00:00:00"
                    strDate = ""
                }else{
                    var date: Date? = null
                    date = inputFormat.parse(strDate)
                    strDate = outputFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strDate)
                }

                if (strTime!!.equals("")){
                    strTime = "00:00"
                }else{
                    var date: Date? = null
                    date = inputTimeFormat.parse(strTime)
                    strTime = outputTimeFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strTime)

                }

                if (strFromDate!!.equals("")){
//                    strFromDate = "0000-00-00 00:00:00"
                    strFromDate = ""
                }else{
                    var date: Date? = null
                    date = inputFormat.parse(strFromDate)
                    strFromDate = outputFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strFromDate)
                }

                if (strToDate!!.equals("")){
                  //  strToDate = "0000-00-00 00:00:00"
                    strToDate = ""
                }else{
                    var date: Date? = null
                    date = inputFormat.parse(strToDate)
                    strToDate = outputFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strToDate)
                }



                if (strFromTime!!.equals("")){
                    strFromTime = "00:00"
                }
                else{
                    var date: Date? = null
                    date = inputTimeFormat.parse(strFromTime)
                    strFromTime = outputTimeFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strFromTime)
                }

                if (strToTime!!.equals("")){
                    strToTime = "00:00"
                }
                else{
                    var date: Date? = null
                    date = inputTimeFormat.parse(strToTime)
                    strToTime = outputTimeFormat.format(date)
                    Log.e(TAG,"DATE   1302   "+strToTime)
                }

                Log.e(TAG,"Validation   9373"
                        +"\n"+"Customer Type        :  "+Customer_Type
                        +"\n"+"FK_Customer          :  "+ID_Customer
                        +"\n"+"strCustomerName      :  "+strCustomerName
                        +"\n"+"CSRChannelID         :  "+ID_Channel
                        +"\n"+"CSRPriority          :  "+ID_Priority
                        +"\n"+"CSRPCategory         :  "+ID_Category
                        +"\n"+"FK_OtherCompany      :  "+ID_Company
                        +"\n"+"FK_ComplaintList     :  "+ID_ComplaintList
                        +"\n"+"FK_ServiceList       :  "+ID_Services
                        +"\n"+"CSRChannelSubID      :  "+ID_EmpMedia
                        +"\n"+"ID_ComplaintList     :  "+ID_ComplaintList

                        +"\n"+"Status               :  "+ID_Status
                        +"\n"+"AttendedBy           :  "+ID_AttendedBy
                        +"\n"+"CusName              :  "+strCustomerName
                        +"\n"+"CusMobile            :  "+strMobileNo
                        +"\n"+"CusAddress           :  "+strAddress
                        +"\n"+"CSRContactNo         :  "+strContactNo
                        +"\n"+"CSRLandmark          :  "+strLandMark

                        +"\n"+"CSRServiceFromDate   :  "+strFromDate
                        +"\n"+"CSRServiceToDate     :  "+strToDate
                        +"\n"+"CSRServicefromtime   :  "+strFromTime
                        +"\n"+"CSRServicetotime     :  "+strToTime

                        +"\n"+"FK_Product           :  "+ID_Product
                        +"\n"+"CSRODescription      :  "+strDescription
                        +"\n"+"TicketDate           :  "+strDate)

                strUserAction = "1"
                saveOrupdate = 0
                saveCustomerService()
            }


            dialogConfirm!!.show()
            dialogConfirm!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveCustomerService() {

//        FK_Place = ""
//        FK_Country = ""
//        FK_States = ""
//        FK_District = ""
//        FK_Area = ""
//        FK_Post = ""
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                customerServiceRegisterViewModel.getcusServRegister(this,strUserAction!!,Customer_Type!!,ID_Customer!!,ID_Channel!!,ID_Priority!!,ID_Category!!,
                    ID_Company!!,ID_ComplaintList!!,ID_Services!!,ID_EmpMedia!!,ID_Status!!,ID_AttendedBy!!,strCustomerName!!,strMobileNo!!,strAddress!!,strContactNo!!,
                    strLandMark!!,strFromDate!!,strToDate!!,strFromTime!!,strToTime!!,ID_Product!!,strDescription!!,strDate!!,strTime!!,
                    FK_Country!!,FK_States!!,FK_District!!,FK_Area!!,FK_Post!!,FK_Place!!,ID_CompCategory!!,strLongitue!!,strLatitude!!,strLocationAddress!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (saveOrupdate == 0){
                                    saveOrupdate++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1453   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UpdateCustomerServiceRegister")
                                        try {

                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!! .setContentView(R.layout.success_service_popup)
                                            suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
                                            suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                            suceessDialog!!.setCancelable(false)

                                            val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
                                            val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView
                                            tv_succesmsg!!.setText(jObject.getString("EXMessage"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        } catch (e: Exception) {
                                            e.printStackTrace()
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


    private fun hideViews() {

        lnrHead_customer!!.visibility = View.VISIBLE
        lnrHead_complaint!!.visibility = View.VISIBLE
        lnrHead_contact!!.visibility = View.VISIBLE
        lnrHead_requested!!.visibility = View.VISIBLE
        lnrHead_attended!!.visibility = View.VISIBLE
        lnrHead_location!!.visibility = View.VISIBLE

        if (custDetailMode.equals("1")) {
            lnrHead_customer!!.visibility = View.GONE
        }

        if (complaintMode.equals("1")) {
            lnrHead_complaint!!.visibility = View.GONE
        }

        if (contDetailMode.equals("1")) {
            lnrHead_contact!!.visibility = View.GONE
        }

        if (requestedMode.equals("1")) {
            lnrHead_requested!!.visibility = View.GONE
        }

        if (attDetailMode.equals("1")) {
            lnrHead_attended!!.visibility = View.GONE
        }

        if (locDetailMode.equals("1")) {
            lnrHead_location!!.visibility = View.GONE
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
        else if (dateMode == 2){
            date_Picker1.minDate = System.currentTimeMillis()
        }

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


                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    checkCurrDate(""+strDay+"-"+strMonth+"-"+strYear)
                }else if (dateMode == 1){

                    tie_FromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                else if (dateMode == 2){
                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
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

    private fun futureDateDisable(outTime : String,dialog : BottomSheetDialog) {

        try {


            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)

            var curDate = tie_Date!!.text.toString()

            if (sdfDate1.format(newDate).equals(curDate)){
                Log.e(TAG,"Change date 2195   "+curDate+"  "+outTime)
                Log.e(TAG,"Change date 2195   "+sdfTime1.format(newDate))
//                tie_Time!!.setText(""+sdfTime1.format(newDate))
                val format = SimpleDateFormat("hh:mm a", Locale.US)
                val date1: Date = format.parse(outTime)
                val date2: Date = format.parse(sdfTime1.format(newDate))

                if (date1.compareTo(date2) > 0) {
                    // time1 is greater than or equal to time2
                    // Handle the logic for this case
                    Log.e(TAG,"Change date 2195 gdyretyreyre  ")
                    txt_Warning!!.visibility = View.VISIBLE
                } else {
                    // time1 is less than time2
                    // Handle the logic for this case
                    txt_Warning!!.visibility = View.GONE
                    tie_Time!!.setText(outTime)
                    dialog.dismiss()
                }
            }else{
                txt_Warning!!.visibility = View.GONE
                tie_Time!!.setText(outTime)
                dialog.dismiss()
            }



        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun checkCurrDate(curDate : String) {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {


            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)

            if (sdfDate1.format(newDate).equals(curDate)){
                Log.e(TAG,"Change date 2196   "+curDate)
                tie_Time!!.setText(""+sdfTime1.format(newDate))
            }


//            tie_Date!!.setText(""+sdfDate1.format(newDate))
//            tie_FromDate!!.setText(""+sdfDate1.format(newDate))
//            //  strVisitDate = sdfDate2.format(newDate)
//
//            tie_Time!!.setText(""+sdfTime1.format(newDate))
//            //  strVisitTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun openBottomTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        Log.e(TAG,"openBottomTime 2246  ")
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        txt_Warning = view.findViewById<TextView>(R.id.txt_Warning)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        txt_Warning!!.visibility = View.GONE
//        time_Picker1.setHour(17);
//        time_Picker1!!.currentMinute = (System.currentTimeMillis() - 1000).toInt()
//        time_Picker1!!.currentHour = (System.currentTimeMillis() - 1000).toInt()

//        val currentTime = Calendar.getInstance()
//        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
//        val currentMinute = currentTime.get(Calendar.MINUTE)
//
//        time_Picker1.hour = currentHour
//        time_Picker1.minute = currentMinute


//        time_Picker1.setHour(9);



        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
//            dialog.dismiss()
            try {

//                if (timeMode == 2){
//                  futureDateDisable(time_Picker1)
//                }

                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                if (timeMode == 0){
                    tie_FromTime!!.setText(output)
                    dialog.dismiss()
                }
                if (timeMode == 1){
                    tie_ToTime!!.setText(output)
                    dialog.dismiss()
                }
                if (timeMode == 2){
                   // tie_Time!!.setText(output)
                    futureDateDisable(output,dialog)
                }


//                if (fromToTime == 0){
//                    tie_Comp_FromTime!!.setText(output)
//                }
//                if (fromToTime == 1){
//                    tie_Comp_ToTime!!.setText(output)
//                }



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



    private fun getCustomerSearch() {
//         var custDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                customerListViewModel.getCustomerList(this, strCustomer!!, ReqMode!!,SubModeSearch!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (custDet == 0) {
                                    custDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   105   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ServiceCustomerDetails")
                                        customerArrayList = jobjt.getJSONArray("ServiceCustomerList")

                                        if (customerArrayList.length() > 0) {
                                            Log.e(TAG, "msg   1052   " + msg+"\n")

                                            customerSearchPopup(customerArrayList)



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
                                }
                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
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

    private fun customerSearchPopup(customerArrayList: JSONArray) {
        try {

            dialogCustSearch = Dialog(this)
            dialogCustSearch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCustSearch!!.setContentView(R.layout.customersearch_popup)
            dialogCustSearch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyCustomer = dialogCustSearch!!.findViewById(R.id.recyCustomer) as RecyclerView
            val llsearch = dialogCustSearch!!.findViewById(R.id.llsearch) as LinearLayout
            val etsearch = dialogCustSearch!! .findViewById(R.id.etsearch) as EditText

            llsearch!!.visibility =View.VISIBLE

            customerSort = JSONArray()
            for (k in 0 until customerArrayList.length()) {
                val jsonObject = customerArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                customerSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = CustomerListAdapter(this@CustomerServiceActivity, customerSort)
            recyCustomer!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    customerSort = JSONArray()

                    for (k in 0 until customerArrayList.length()) {
                        val jsonObject = customerArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                customerSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllSort               7103    "+customerSort)
                    val adapter = CustomerListAdapter(this@CustomerServiceActivity, customerSort)
                    recyCustomer!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
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

    private fun getProductPriority() {
//        var prodpriority = 0
        Log.e(TAG,"ffffffffffffffff 1== "+prioritymode)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                servicePriorityViewModel.getServicePriority(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (priorityDet == 0){
                                    priorityDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   353   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        servPriorityArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (servPriorityArrayList.length()>0){

                                            Log.e(TAG,"ffffffffffffffff "+prioritymode)
                                            if (prioritymode == 0){
                                                var jsonObject1 = servPriorityArrayList.getJSONObject(2)
                                                tie_Priority!!.setText(jsonObject1.getString("Description"))
                                                ID_Priority = jsonObject1.getString("Code")

                                            }else{
                                                productPriorityPopup(servPriorityArrayList)
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

    private fun productPriorityPopup(servPriorityArrayList: JSONArray) {

        try {

            dialogServPriority = Dialog(this)
            dialogServPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServPriority!! .setContentView(R.layout.service_priority_popup)
            dialogServPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyServPriority = dialogServPriority!! .findViewById(R.id.recyServPriority) as RecyclerView
            val etsearch = dialogServPriority!! .findViewById(R.id.etsearch) as EditText

            servPrioritySort = JSONArray()
            for (k in 0 until servPriorityArrayList.length()) {
                val jsonObject = servPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                servPrioritySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyServPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServicePriorityAdapter(this@CustomerServiceActivity, servPrioritySort)
            recyServPriority!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    servPrioritySort = JSONArray()

                    for (k in 0 until servPriorityArrayList.length()) {
                        val jsonObject = servPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                servPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"servPrioritySort               7103    "+servPrioritySort)
                    val adapter = ServicePriorityAdapter(this@CustomerServiceActivity, servPrioritySort)
                    recyServPriority!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogServPriority!!.show()
            dialogServPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getChannel(ReqMode : String,SubMode : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                commonViewModel.getCommonViewModel(this,ReqMode,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (channelDet == 0){
                                    channelDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1095   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        channelArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (channelArrayList.length()>0){

                                           // productPriorityPopup(prodPriorityArrayList)
                                            channelPopup(channelArrayList)



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

    private fun channelPopup(channelArrayList: JSONArray) {

        try {

            dialogChannel = Dialog(this)
            dialogChannel!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogChannel!! .setContentView(R.layout.channel_popup)
            dialogChannel!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyChannel = dialogChannel!! .findViewById(R.id.recyChannel) as RecyclerView
            val etsearch = dialogChannel!! .findViewById(R.id.etsearch) as EditText

            channelSort = JSONArray()
            for (k in 0 until channelArrayList.length()) {
                val jsonObject = channelArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                channelSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyChannel!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ChannelAdapter(this@CustomerServiceActivity, channelSort)
            recyChannel!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    channelSort = JSONArray()

                    for (k in 0 until channelArrayList.length()) {
                        val jsonObject = channelArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                channelSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"channelSort               7103    "+channelSort)
                    val adapter = ChannelAdapter(this@CustomerServiceActivity, channelSort)
                    recyChannel!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogChannel!!.show()
            dialogChannel!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getChannelEmp() {
        var ID_Department = "0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, ID_Department)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (empMediaDet == 0) {
                                    empMediaDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


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

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@CustomerServiceActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@CustomerServiceActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

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
                    val adapter = EmployeeAdapter(this@CustomerServiceActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
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

    private fun getServiceMedia() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceMediaViewModel.getserviceMedia(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (empMediaDet == 0){
                                    empMediaDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1557   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("MediaDetails")
                                        serviceMediaArrayList = jobjt.getJSONArray("MediaList")
                                        if (serviceMediaArrayList.length()>0){

                                            serviceMediaPopup(serviceMediaArrayList)

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

    private fun serviceMediaPopup(serviceMediaArrayList: JSONArray) {
        try {

            dialogServiceMedia = Dialog(this)
            dialogServiceMedia!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServiceMedia!!.setContentView(R.layout.service_media_popup)
            dialogServiceMedia!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyServiceMedia = dialogServiceMedia!!.findViewById(R.id.recyServiceMedia) as RecyclerView
            val etsearch = dialogServiceMedia!!.findViewById(R.id.etsearch) as EditText

            serviceMediaSort = JSONArray()
            for (k in 0 until serviceMediaArrayList.length()) {
                val jsonObject = serviceMediaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                serviceMediaSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyServiceMedia!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@CustomerServiceActivity, serviceMediaArrayList)
            val adapter = ServiceMediaAdapter(this@CustomerServiceActivity, serviceMediaSort)
            recyServiceMedia!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    serviceMediaSort = JSONArray()

                    for (k in 0 until serviceMediaArrayList.length()) {
                        val jsonObject = serviceMediaArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                serviceMediaSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "serviceMediaSort               7103    " + serviceMediaSort)
                    val adapter = ServiceMediaAdapter(this@CustomerServiceActivity, serviceMediaSort)
                    recyServiceMedia!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogServiceMedia!!.show()
            dialogServiceMedia!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCompCategory(ReqMode : String,SubMode : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                commonViewModel.getCommonViewModel(this,ReqMode,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (CompcategoryDet == 0){
                                    CompcategoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1278   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        compCategoryArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (compCategoryArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            Log.e(TAG,"bbbbbbbb   "+categoryCount)

                                            if (categoryCount == 0){
                                                var jsonObject1 = compCategoryArrayList.getJSONObject(2)
                                                tie_CompCategory!!.setText(jsonObject1.getString("Description"))
                                                ID_CompCategory = jsonObject1.getString("Code")


                                                ID_Category = ""
                                                tie_Category!!.setText("")
                                                tie_Product!!.setText("")
                                                ID_Product = ""
                                                tie_Company!!.setText("")
                                                ID_Company = ""
                                                tie_Service!!.setText("")
                                                ID_Services = ""
                                                tie_Complaint!!.setText("")
                                                ID_ComplaintList = ""

                                                if (ID_CompCategory.equals("1")){
                                                    til_Company!!.visibility = View.GONE
                                                    til_Service!!.visibility = View.VISIBLE
                                                    til_Complaint!!.visibility = View.GONE
                                                }
                                                else if (ID_CompCategory.equals("2")){
                                                    til_Company!!.visibility = View.VISIBLE
                                                    til_Service!!.visibility = View.VISIBLE
                                                    til_Complaint!!.visibility = View.GONE
                                                }
                                                else if (ID_CompCategory.equals("3")){
                                                    til_Company!!.visibility = View.GONE
                                                    til_Service!!.visibility = View.GONE
                                                    til_Complaint!!.visibility = View.VISIBLE
                                                }
                                                else if (ID_CompCategory.equals("4")){
                                                    til_Company!!.visibility = View.VISIBLE
                                                    til_Service!!.visibility = View.GONE
                                                    til_Complaint!!.visibility = View.VISIBLE
                                                }

                                            }else{
                                                compCategoryPopup(compCategoryArrayList)
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

    private fun compCategoryPopup(compCategoryArrayList: JSONArray) {


        try {

            dialogcompCategory = Dialog(this)
            dialogcompCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogcompCategory!! .setContentView(R.layout.comp_category_popup)
            dialogcompCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recycompCategory = dialogcompCategory!! .findViewById(R.id.recycompCategory) as RecyclerView
            val etsearch = dialogcompCategory!! .findViewById(R.id.etsearch) as EditText

            compCategorySort = JSONArray()
            for (k in 0 until compCategoryArrayList.length()) {
                val jsonObject = compCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                compCategorySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycompCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = CompnayCategoryAdapter(this@CustomerServiceActivity, compCategorySort)
            recycompCategory!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    compCategorySort = JSONArray()

                    for (k in 0 until compCategoryArrayList.length()) {
                        val jsonObject = compCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                compCategorySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"compCategorySort               7103    "+compCategorySort)
                    val adapter = CompnayCategoryAdapter(this@CustomerServiceActivity, compCategorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogcompCategory!!.show()
            dialogcompCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }

    private fun getCategory(ReqMode : String,ID_CompCategory : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this,ReqMode,ID_CompCategory)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (categoryDet == 0){
                                    categoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1278   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        categoryArrayList = jobjt.getJSONArray("CategoryList")
                                        if (categoryArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            categoryPopup(categoryArrayList)



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

    private fun categoryPopup(categoryArrayList: JSONArray) {


        try {

            dialogCategory = Dialog(this)
            dialogCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCategory!! .setContentView(R.layout.category_popup)
            dialogCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCategory = dialogCategory!! .findViewById(R.id.recyCategory) as RecyclerView
            val etsearch = dialogCategory!! .findViewById(R.id.etsearch) as EditText

            categorySort = JSONArray()
            for (k in 0 until categoryArrayList.length()) {
                val jsonObject = categoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                categorySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = CategoryAdapter(this@CustomerServiceActivity, categorySort)
            recyCategory!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    categorySort = JSONArray()

                    for (k in 0 until categoryArrayList.length()) {
                        val jsonObject = categoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                categorySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"categorySort               7103    "+categorySort)
                    val adapter = CategoryAdapter(this@CustomerServiceActivity, categorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogCategory!!.show()
            dialogCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }

    private fun getCompany(ReqMode: String, SubMode: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                commonViewModel.getCommonViewModel(this,ReqMode,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (companyDet == 0){
                                    companyDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1438   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        companyArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (companyArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            companyPopup(companyArrayList)



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

    private fun companyPopup(companyArrayList: JSONArray) {

        try {

            dialogCompany = Dialog(this)
            dialogCompany!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCompany!! .setContentView(R.layout.company_popup)
            dialogCompany!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCompany = dialogCompany!! .findViewById(R.id.recyCompany) as RecyclerView
            val etsearch = dialogCompany!! .findViewById(R.id.etsearch) as EditText

            companySort = JSONArray()
            for (k in 0 until companyArrayList.length()) {
                val jsonObject = companyArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                companySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyCompany!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = CompanyAdapter(this@CustomerServiceActivity, companySort)
            recyCompany!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    companySort = JSONArray()

                    for (k in 0 until companyArrayList.length()) {
                        val jsonObject = companyArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                companySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"companySort               1556    "+companySort)
                    val adapter = CompanyAdapter(this@CustomerServiceActivity, companySort)
                    recyCompany!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogCompany!!.show()
            dialogCompany!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    private fun getProduct(ReqMode: String, SubMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceProductViewModel.getServiceProduct(this,ReqMode,SubMode,Customer_Type!!,ID_Customer!!,ID_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (productDet == 0){
                                    productDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1590   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ServiceProduct")
                                        productArrayList = jobjt.getJSONArray("ServiceProductDetailsList")
                                        if (productArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            productPopup(productArrayList)



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

    private fun productPopup(productArrayList: JSONArray) {


        try {

            dialogProduct = Dialog(this)
            dialogProduct!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProduct!! .setContentView(R.layout.service_product_popup)
            dialogProduct!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProduct = dialogProduct!! .findViewById(R.id.recyProduct) as RecyclerView
            val etsearch = dialogProduct!! .findViewById(R.id.etsearch) as EditText

            productSort = JSONArray()
            for (k in 0 until productArrayList.length()) {
                val jsonObject = productArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                productSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyProduct!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceProductAdapter(this@CustomerServiceActivity, productSort)
            recyProduct!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    productSort = JSONArray()

                    for (k in 0 until productArrayList.length()) {
                        val jsonObject = productArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                productSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"productSort               1556    "+productSort)
                    val adapter = ServiceProductAdapter(this@CustomerServiceActivity, productSort)
                    recyProduct!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogProduct!!.show()
            dialogProduct!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }




    }

    private fun getServices(ReqMode: String, SubMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceViewModel.getServices(this,ReqMode!!,SubMode!!,ID_Product!!,ID_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceDet == 0){
                                    serviceDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1920   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ServiceDetails")
                                        serviceArrayList = jobjt.getJSONArray("ServiceDetailsList")
                                        if (serviceArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            servicePopup(serviceArrayList)



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

    private fun servicePopup(serviceArrayList: JSONArray) {
        try {

            dialogService = Dialog(this)
            dialogService!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogService!! .setContentView(R.layout.service_popup)
            dialogService!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyService = dialogService!! .findViewById(R.id.recyService) as RecyclerView
            val etsearch = dialogService!! .findViewById(R.id.etsearch) as EditText

            serviceSort = JSONArray()
            for (k in 0 until serviceArrayList.length()) {
                val jsonObject = serviceArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                serviceSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceAdapter(this@CustomerServiceActivity, serviceSort)
            recyService!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    serviceSort = JSONArray()

                    for (k in 0 until serviceArrayList.length()) {
                        val jsonObject = serviceArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ServicesName").length) {
                            if (jsonObject.getString("ServicesName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                serviceSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"serviceSort               1556    "+serviceSort)
                    val adapter = ServiceAdapter(this@CustomerServiceActivity, serviceSort)
                    recyService!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogService!!.show()
            dialogService!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    private fun getComplaints(ReqMode: String, SubMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceComplaintViewModel.getserviceComplaintData(this,ReqMode!!,SubMode!!,ID_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (complaintDet == 0){
                                    complaintDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1920   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ComplaintsDetails")
                                        complaintArrayList = jobjt.getJSONArray("ComplaintDetailsList")
                                        if (complaintArrayList.length()>0){

                                            complaintPopup(complaintArrayList)


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

    private fun complaintPopup(complaintArrayList: JSONArray) {

        try {

            dialogComplaint = Dialog(this)
            dialogComplaint!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogComplaint!! .setContentView(R.layout.service_complaint_popup)
            dialogComplaint!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyComplaint = dialogComplaint!! .findViewById(R.id.recyComplaint) as RecyclerView
            val etsearch = dialogComplaint!! .findViewById(R.id.etsearch) as EditText

            complaintSort = JSONArray()
            for (k in 0 until complaintArrayList.length()) {
                val jsonObject = complaintArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                complaintSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyComplaint!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceComplaintAdapter(this@CustomerServiceActivity, complaintSort)
            recyComplaint!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    complaintSort = JSONArray()

                    for (k in 0 until complaintArrayList.length()) {
                        val jsonObject = complaintArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ComplaintName").length) {
                            if (jsonObject.getString("ComplaintName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                complaintSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"complaintSort               2203    "+complaintSort)
                    val adapter = ServiceComplaintAdapter(this@CustomerServiceActivity, complaintSort)
                    recyComplaint!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogComplaint!!.show()
            dialogComplaint!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

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

                                            followUpActionPopup(followUpActionArrayList)


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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!! .findViewById(R.id.etsearch) as EditText


            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@CustomerServiceActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


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
                    val adapter = FollowupActionAdapter(this@CustomerServiceActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun addNewUserBottom() {
        try {

            dialogAddUserSheet = Dialog(this)
            dialogAddUserSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAddUserSheet!! .setContentView(R.layout.cs_add_new_user_sheet)
            dialogAddUserSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            val window: Window? = dialogAddUserSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent)
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


//            // ADD NEW CUSTOME
            tie_CN_Name = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Name)
            tie_CN_Mobile = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Mobile)
            tie_CN_HouseName = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_HouseName)
            tie_CN_Place = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Place)
            tie_CN_Pincode = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Pincode)
            tie_CN_Country = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Country)
            tie_CN_State = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_State)
            tie_CN_District = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_District)
            tie_CN_Area = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Area)
            tie_CN_Post = dialogAddUserSheet!!.findViewById<TextInputEditText>(R.id.tie_CN_Post)

            til_CN_Name = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Name)
            til_CN_Mobile = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Mobile)
            til_CN_HouseName = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_HouseName)
            til_CN_Place = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Place)
            til_CN_Pincode = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Pincode)
            til_CN_Country = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Country)
            til_CN_State = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_State)
            til_CN_District = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_District)
            til_CN_Area = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Area)
            til_CN_Post = dialogAddUserSheet!!.findViewById<TextInputLayout>(R.id.til_CN_Post)

            til_CN_Name!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_CN_Mobile!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        //    til_CN_HouseName!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_CN_Country!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_CN_State!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_CN_District!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

            tie_CN_Name!!.addTextChangedListener(watcher);
            tie_CN_Mobile!!.addTextChangedListener(watcher);
            tie_CN_HouseName!!.addTextChangedListener(watcher);
            tie_CN_Country!!.addTextChangedListener(watcher);
            tie_CN_State!!.addTextChangedListener(watcher);
            tie_CN_District!!.addTextChangedListener(watcher);

            FK_Place = ""
            FK_Country = ""
            FK_States = ""
            FK_District = ""
            FK_Area = ""
            FK_Post = ""

            tie_CN_Name!!.setText("")
            tie_CN_Mobile!!.setText("")
            tie_CN_HouseName!!.setText("")
            tie_CN_Place!!.setText("")
            tie_CN_Pincode!!.setText("")
            tie_CN_Country!!.setText("")
            tie_CN_State!!.setText("")
            tie_CN_District!!.setText("")
            tie_CN_Area!!.setText("")
            tie_CN_Post!!.setText("")

            tie_CN_Country!!.setOnClickListener(this)
            tie_CN_State!!.setOnClickListener(this)
            tie_CN_District!!.setOnClickListener(this)
            tie_CN_Area!!.setOnClickListener(this)
            tie_CN_Post!!.setOnClickListener(this)

            var txtNewUserCancel = dialogAddUserSheet!!.findViewById<TextView>(R.id.txtNewUserCancel)
            var txtNewUserSubmit = dialogAddUserSheet!!.findViewById<TextView>(R.id.txtNewUserSubmit)
            var txtNewUserReset = dialogAddUserSheet!!.findViewById<TextView>(R.id.txtNewUserReset)

            tie_CN_Pincode!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    tie_CN_Area!!.setText("")
                    tie_CN_Post!!.setText("")
                    FK_Area = ""
                    FK_Post = ""
                }

                override fun afterTextChanged(editable: Editable) {

                }
            })


            til_CN_Pincode!!.setEndIconOnClickListener {
                try {
                    strCnPinCode = tie_CN_Pincode!!.text.toString()
                    if (strCnPinCode.equals("")) {
                        til_CN_Pincode!!.setError("Enter Pincode");
                        til_CN_Pincode!!.setErrorIconDrawable(null)
                    } else {
                        Config.disableClick(it)
                        pincodeCount = 0
                        getPinCodeSearch(strCnPinCode)
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Exception  64   " + e.toString())
                }
            }

            txtNewUserReset!!.setOnClickListener {
//                dialogAddUserSheet!!.dismiss()
                tie_CN_Name!!.addTextChangedListener(watcher)
                tie_CN_Mobile!!.addTextChangedListener(watcher)
                tie_CN_HouseName!!.addTextChangedListener(watcher)
                tie_CN_Country!!.addTextChangedListener(watcher)
                tie_CN_State!!.addTextChangedListener(watcher)
                tie_CN_District!!.addTextChangedListener(watcher)

                FK_Place    = ""
                FK_Country  = ""
                FK_States   = ""
                FK_District = ""
                FK_Area     = ""
                FK_Post     = ""

                tie_CN_Name!!.setText("")
                tie_CN_Mobile!!.setText("")
                tie_CN_HouseName!!.setText("")
                tie_CN_Place!!.setText("")
                tie_CN_Pincode!!.setText("")
                tie_CN_Country!!.setText("")
                tie_CN_State!!.setText("")
                tie_CN_District!!.setText("")
                tie_CN_Area!!.setText("")
                tie_CN_Post!!.setText("")
            }

            txtNewUserSubmit!!.setOnClickListener {
              addUserValidation()
            }



            defaultCount = 0
            getDefaultValueSettings()

            dialogAddUserSheet!!.show()

        }catch (e: Exception){

            Log.e(TAG,"Exception 3620   "+e.toString())
        }

    }

    private fun getPinCodeSearch(strPincode: String) {
//        var pinCodeDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pinCodeSearchViewModel.getPincode(this, strPincode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                            Log.e(TAG, "msg   210811   " + msg)
                            if (msg!!.length > 0) {
                                if (pincodeCount == 0) {
                                    pincodeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   210812   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("PincodeDetails")
                                        pinCodeArrayList = jobjt.getJSONArray("PincodeDetailsList")
//                                        val jobjt = jObject.getJSONObject("PincodeDetails")
//
//                                        FK_Country = jobjt.getString("FK_Country")
//                                        FK_States = jobjt.getString("FK_States")
//                                        FK_District = jobjt.getString("FK_District")
//                                        FK_Area = jobjt.getString("FK_Area")
//                                        FK_Place = jobjt.getString("FK_Place")
//                                        FK_Post = jobjt.getString("FK_Post")
//
//                                        tie_CN_Place!!.setText(jobjt.getString("Place"))
//                                        tie_CN_Country!!.setText(jobjt.getString("Country"))
//                                        tie_CN_State!!.setText(jobjt.getString("States"))
//                                        tie_CN_District!!.setText(jobjt.getString("District"))
//                                        tie_CN_Area!!.setText("")
//                                        tie_CN_Post!!.setText(jobjt.getString("Post"))
//
//                                        Log.e(TAG, "Post  21082   " + jobjt.getString("Post"))

                                        if (pinCodeArrayList.length() == 1){

                                            val jsonObject = pinCodeArrayList.getJSONObject(0)

                                            FK_Country = jsonObject.getString("FK_Country")
                                            FK_States = jsonObject.getString("FK_States")
                                            FK_District = jsonObject.getString("FK_District")
                                            FK_Area = jsonObject.getString("FK_Area")
                                            FK_Place = jsonObject.getString("FK_Place")
                                            FK_Post = jsonObject.getString("FK_Post")
                                            FK_Area = jsonObject.getString("FK_Area")

                                            tie_CN_Place!!.setText(jsonObject.getString("Place"))
                                            tie_CN_Country!!.setText(jsonObject.getString("Country"))
                                            tie_CN_State!!.setText(jsonObject.getString("States"))
                                            tie_CN_District!!.setText(jsonObject.getString("District"))
                                            tie_CN_Area!!.setText(jsonObject.getString("Area"))
                                            tie_CN_Post!!.setText(jsonObject.getString("Post"))

                                            Log.e(TAG, "Post  21082   " + jsonObject.getString("Post"))


                                        }
                                        else{
                                            Log.e(TAG, "Post  210823   "+pinCodeArrayList )
                                            pincodeDetailPopup(pinCodeArrayList)
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
                                }


                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()

                            }
                            //  }


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

    private fun pincodeDetailPopup(pinCodeArrayList: JSONArray) {
        try {

            dialogPinCode = Dialog(this)
            dialogPinCode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPinCode!! .setContentView(R.layout.pincodedetail_popup)
            dialogPinCode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyPincodeDetails = dialogPinCode!! .findViewById(R.id.recyPincodeDetails) as RecyclerView

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyPincodeDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = PicodeDetailAdapter(this@CustomerServiceActivity, pinCodeArrayList)
            recyPincodeDetails!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            dialogPinCode!!.show()
            dialogPinCode!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogPinCode!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addUserValidation() {

        strCnName = tie_CN_Name!!.text.toString()
        strMobileNo = tie_CN_Mobile!!.text.toString()
        strCnHouseName = tie_CN_HouseName!!.text.toString()
        strCnPlace = tie_CN_Place!!.text.toString()
        strCnPinCode = tie_CN_Pincode!!.text.toString()
        strMobileNo!!.length < 10
        if (strCnName.equals("")){
            til_CN_Name!!.setError("Enter Name");
            til_CN_Name!!.setErrorIconDrawable(null)
        }
        else if(strMobileNo!!.length < 10){
            til_CN_Mobile!!.setError("Enter Mobile");
            til_CN_Mobile!!.setErrorIconDrawable(null)
        }
//        else if(strCnHouseName.equals("")){
//            til_CN_HouseName!!.setError("Enter House Name");
//            til_CN_HouseName!!.setErrorIconDrawable(null)
//        }
        else if(FK_Country.equals("")){
            til_CN_Country!!.setError("Select Country");
            til_CN_Country!!.setErrorIconDrawable(null)
        }
        else if(FK_States.equals("")){
            til_CN_State!!.setError("Select State");
            til_CN_State!!.setErrorIconDrawable(null)
        }
        else if(FK_District.equals("")){
            til_CN_District!!.setError("Select District");
            til_CN_District!!.setErrorIconDrawable(null)
        }
        else{
            Log.e(TAG,"Success")

            tie_CustomerName!!.setText(tie_CN_Name!!.text.toString())
            tie_MobileNo!!.setText(tie_CN_Mobile!!.text.toString())
            tie_Address!!.setText(tie_CN_HouseName!!.text.toString())

            dialogAddUserSheet!!.dismiss()
        }
    }

    private fun detailPopupSheet(custname: String) {

        try {

            warrantyMode = "1"
            prodHistMode = "1"
            saleHistMode = "1"
            custDueMode = "1"
            ID_CustomerWiseProductDetails = ""

            dialogDetailSheet = Dialog(this)
            dialogDetailSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDetailSheet!! .setContentView(R.layout.cs_detail_bottom_sheet)
            dialogDetailSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val warcount = context.getSharedPreferences(Config.SHARED_PREF50, 0)
            val warrantycountEditer = warcount.edit()
            warrantycountEditer.putString("WarrantyCount", warrantycount)
            warrantycountEditer.commit()

            val servcount = context.getSharedPreferences(Config.SHARED_PREF51, 0)
            val servcountEditer = servcount.edit()
            servcountEditer.putString("ServiceHistoryCount", "0")
            servcountEditer.commit()

            val salecount = context.getSharedPreferences(Config.SHARED_PREF52, 0)
            val salecountEditer = salecount.edit()
            salecountEditer.putString("SalesCount", "0")
            salecountEditer.commit()

            val custduecount = context.getSharedPreferences(Config.SHARED_PREF53, 0)
            val custduecountEditer = custduecount.edit()
            custduecountEditer.putString("CustomerDueCount", "0")
            custduecountEditer.commit()

            if(custname.equals(""))
            {

            }
            else
            {
//                System.out.println("shname "+tie_CustomerName!!.text.toString())
//                val CustidSP = context.getSharedPreferences(Config.SHARED_PREF47, 0)
//                FK_Cust = CustidSP.getString("Customerid", "")!!
//
//                val OtherCustidSP = context.getSharedPreferences(Config.SHARED_PREF48, 0)
//                FK_OtherCustomer = OtherCustidSP.getString("CusMode", "")!!
//
//
//                val ProductidSP = context.getSharedPreferences(Config.SHARED_PREF49, 0)
//                Prodid = ProductidSP.getString("Productid", "")!!



//                System.out.println("Shared 1 "+FK_Cust)
//                System.out.println("Shared 2 "+FK_OtherCustomer)
//                System.out.println("Shared 3 "+Prodid)
//                System.out.println("CHECKVALUE "+custname)

//                Log.e(TAG,"4190  "+
//                        "\n"+"    "+ID_Customer+
//                        "\n"+"Customer_Type    "+Customer_Type+
//                        "\n"+"ID_Product    "+ID_Product)


                custServiceCount = 0
                getCustomerserviceCount(ID_Customer!!,Customer_Type!!,ID_Product!!)

                Log.e(TAG,"ddddddddq ")

            }


            val warcountSP = context.getSharedPreferences(Config.SHARED_PREF50, 0)
            var warcount1 = warcountSP.getString("WarrantyCount", "")!!

            val servhistSP = context.getSharedPreferences(Config.SHARED_PREF51, 0)
            var servhistcount1 = servhistSP.getString("ServiceHistoryCount", "")!!

            val salecntSP = context.getSharedPreferences(Config.SHARED_PREF52, 0)
            var salcount1 = salecntSP.getString("SalesCount", "")!!

            val custdueSP = context.getSharedPreferences(Config.SHARED_PREF53, 0)
            var custdue1 = custdueSP.getString("CustomerDueCount", "")!!

            Log.i("countdetails", warcount1+"\n"+servhistcount1+"\n"+salcount1+"\n"+custdue1)

        //    recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView

            lnrHead_warranty_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_warranty_main) as LinearLayout
            lnrHead_warranty_sub = dialogDetailSheet!! .findViewById(R.id.lnrHead_warranty_sub) as LinearLayout
            lnrHead_product_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_product_main) as LinearLayout
            lnrHead_product_sub = dialogDetailSheet!! .findViewById(R.id.lnrHead_product_sub) as LinearLayout
            lnrHead_sales_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_sales_main) as LinearLayout
            lnrHead_sales_sub = dialogDetailSheet!! .findViewById(R.id.lnrHead_sales_sub) as LinearLayout
            lnrHead_customerdue_main = dialogDetailSheet!! .findViewById(R.id.lnrHead_customerdue_main) as LinearLayout
            lnrHead_customerdue_sub = dialogDetailSheet!! .findViewById(R.id.lnrHead_customerdue_sub) as LinearLayout

            horizontalScroll = dialogDetailSheet!! .findViewById(R.id.horizontalScroll) as HorizontalScrollView

            ll_tab_warranty = dialogDetailSheet!! .findViewById(R.id.ll_tab_warranty) as LinearLayout
            ll_tab_production = dialogDetailSheet!! .findViewById(R.id.ll_tab_production) as LinearLayout
            ll_tab_sales = dialogDetailSheet!! .findViewById(R.id.ll_tab_sales) as LinearLayout
            ll_tab_customerdue = dialogDetailSheet!! .findViewById(R.id.ll_tab_customerdue) as LinearLayout

            card_warranty = dialogDetailSheet!! .findViewById(R.id.card_warranty) as CardView
            card_production = dialogDetailSheet!! .findViewById(R.id.card_production) as CardView
            card_sales = dialogDetailSheet!! .findViewById(R.id.card_sales) as CardView
            card_customerdue = dialogDetailSheet!! .findViewById(R.id.card_customerdue) as CardView


            txtNext = dialogDetailSheet!! .findViewById(R.id.txtNext) as TextView

            tv_warranty_count = dialogDetailSheet!! .findViewById(R.id.tv_warranty_count) as TextView
            tv_product_count = dialogDetailSheet!! .findViewById(R.id.tv_product_count) as TextView
            tv_sales_count = dialogDetailSheet!! .findViewById(R.id.tv_sales_count) as TextView
            tv_customerdue_count = dialogDetailSheet!! .findViewById(R.id.tv_customerdue_count) as TextView

            tv_warranty_count!!.text=warcount1
            tv_product_count!!.text=servhistcount1
            tv_sales_count!!.text=salcount1
            tv_customerdue_count!!.text=custdue1

//            warrantyMode = "0"
//            prodHistMode = "1"
//            saleHistMode = "1"
//            custDueMode = "1"
//            hideMoreViews()



            recyServiceWarranty = dialogDetailSheet!! .findViewById(R.id.recyServiceWarranty)
            recyServiceProductHistory = dialogDetailSheet!! .findViewById(R.id.recyServiceProductHistory)
            recyServiceSalesHistory = dialogDetailSheet!! .findViewById(R.id.recyServiceSalesHistory)
            recyServiceCustomerdue = dialogDetailSheet!! .findViewById(R.id.recyServiceCustomerdue)

            val window: Window? = dialogDetailSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


            recyServiceWarranty!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            recyServiceProductHistory!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            recyServiceSalesHistory!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            recyServiceCustomerdue!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })


//            lnrHead_warranty_main!!.setOnClickListener {
//                warrantyMode = "0"
//                prodHistMode = "1"
//                saleHistMode = "1"
//                custDueMode = "1"
//
//                if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                    warrantyDet = 0
//                    getWarranty()
//                }
//
//            }
//            lnrHead_product_main!!.setOnClickListener {
//                warrantyMode = "1"
//                prodHistMode = "0"
//                saleHistMode = "1"
//                custDueMode = "1"
//
//                if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                    productHistDet = 0
//                    getProductHistory()
//                }
//
//            }
//            lnrHead_sales_main!!.setOnClickListener {
//                warrantyMode = "1"
//                prodHistMode = "1"
//                saleHistMode = "0"
//                custDueMode = "1"
//
//                if (!ID_Customer.equals("")){
//                    salesHistDet = 0
//                    getSalesHistory()
//                }
//
//            }
//
//            lnrHead_customerdue_main!!.setOnClickListener {
//                warrantyMode = "1"
//                prodHistMode = "1"
//                saleHistMode = "1"
//                custDueMode = "0"
//
//                if (!ID_Customer.equals("")){
//                    cutDueDet = 0
//                    Log.e(TAG,"4117  getCustomerDueDetails")
//                    try {
//                        getCustomerDueDetails()
//                    }catch (e: Exception){
//                        Log.e(TAG,"Exception 4136  "+e.toString())
//                    }
//
//                }
//
//            }


            ll_tab_warranty!!.setOnClickListener {
                warrantyMode = "0"
                prodHistMode = "1"
                saleHistMode = "1"
                custDueMode = "1"

//                if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                    warrantyDet = 0
//                    getWarranty()
//                }

                hideMoreViews()


            }
            ll_tab_production!!.setOnClickListener {
                warrantyMode = "1"
                prodHistMode = "0"
                saleHistMode = "1"
                custDueMode = "1"

//                if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                    productHistDet = 0
//                    getProductHistory()
//                }
                hideMoreViews()
            }
            ll_tab_sales!!.setOnClickListener {
                warrantyMode = "1"
                prodHistMode = "1"
                saleHistMode = "0"
                custDueMode = "1"
//
//                if (!ID_Customer.equals("")){
//                    salesHistDet = 0
//                    getSalesHistory()
//                }
                hideMoreViews()
            }

            ll_tab_customerdue!!.setOnClickListener {
                warrantyMode = "1"
                prodHistMode = "1"
                saleHistMode = "1"
                custDueMode = "0"

//                if (!ID_Customer.equals("")){
//                    cutDueDet = 0
//                    Log.e(TAG,"4117  getCustomerDueDetails")
//                    try {
//                        getCustomerDueDetails()
//                    }catch (e: Exception){
//                        Log.e(TAG,"Exception 4136  "+e.toString())
//                    }
//
//                }

                hideMoreViews()

            }

//            txtNext!!.setOnClickListener {
//
//                if (warrantyMode.equals("0")){
//                    warrantyMode = "1"
//                    prodHistMode = "0"
//                    saleHistMode = "1"
//                    custDueMode = "1"
//                }
//                else if (prodHistMode.equals("0")){
//                    warrantyMode = "1"
//                    prodHistMode = "1"
//                    saleHistMode = "0"
//                    custDueMode = "1"
//                }
//                else if (saleHistMode.equals("0")){
//                    warrantyMode = "1"
//                    prodHistMode = "1"
//                    saleHistMode = "1"
//                    custDueMode = "0"
//                }
//                else if (custDueMode.equals("0")){
//
//                }
//
//
//                hideMoreViews()
//
//
//            }

            dialogDetailSheet!!.show()
           // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideMoreViews() {

//        ll_tab_warranty!!.visibility = View.VISIBLE
//        ll_tab_production!!.visibility = View.VISIBLE
//        ll_tab_sales!!.visibility = View.VISIBLE
//        ll_tab_customerdue!!.visibility = View.VISIBLE

        card_warranty!!.visibility = View.GONE
        card_production!!.visibility = View.GONE
        card_sales!!.visibility = View.GONE
        card_customerdue!!.visibility = View.GONE

       Log.e(TAG,"COUNTS   4465   "+ horizontalScroll!!.childCount)

        ll_tab_warranty!!.setBackgroundResource(R.drawable.shape_rectangle_border)
        ll_tab_production!!.setBackgroundResource(R.drawable.shape_rectangle_border)
        ll_tab_sales!!.setBackgroundResource(R.drawable.shape_rectangle_border)
        ll_tab_customerdue!!.setBackgroundResource(R.drawable.shape_rectangle_border)

        if (warrantyMode.equals("0")){
            ll_tab_warranty!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg)
         //   card_warranty!!.requestFocus()
           // horizontalScroll!!.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
         //   horizontalScroll!!.scrollTo(ll_tab_warranty!!.getRight(), ll_tab_warranty!!.getTop())
          //  horizontalScroll!!.smoothScrollTo(0, ll_tab_warranty!!.getBottom())
            card_warranty!!.visibility = View.VISIBLE
//            if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                warrantyDet = 0
//                getWarranty()
//            }

            if (!ID_Customer.equals("")){
//                warrantyDet = 0
//                getWarranty()
            }

        }

        if (prodHistMode.equals("0")){
            ll_tab_production!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg)

            card_production!!.visibility = View.VISIBLE
         //   horizontalScroll!!.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
          //  horizontalScroll!!.scrollTo(ll_tab_production!!.getRight(), ll_tab_production!!.getTop())
         //   horizontalScroll!!.smoothScrollTo(1, ll_tab_production!!.getBottom())
          //  card_production!!.requestFocus()
//            if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                productHistDet = 0
//                getProductHistory()
//            }

            if (!ID_Customer.equals("")){
                Log.e(TAG,"qqqqqqq 1 ")
                productHistDet = 0
                getProductHistory()
            }
        }

        if (saleHistMode.equals("0")){
            ll_tab_sales!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg)
            card_sales!!.visibility = View.VISIBLE
         //   card_sales!!.requestFocus()
           // horizontalScroll!!.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
           // horizontalScroll!!.scrollTo(ll_tab_sales!!.getRight(), ll_tab_sales!!.getTop())
          //  horizontalScroll!!.smoothScrollTo(2, ll_tab_sales!!.getBottom())
            if (!ID_Customer.equals("")){
                salesHistDet = 0
                getSalesHistory()
            }
        }

        if (custDueMode.equals("0")){
            ll_tab_customerdue!!.setBackgroundResource(R.drawable.shape_rectangle_border_with_bg)
            card_customerdue!!.visibility = View.VISIBLE
         //   card_customerdue!!.requestFocus()
            //horizontalScroll!!.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
          //  horizontalScroll!!.smoothScrollTo(3, ll_tab_customerdue!!.getBottom())
           // horizontalScroll!!.scrollTo(ll_tab_customerdue!!.getRight(), ll_tab_customerdue!!.getTop())
            if (!ID_Customer.equals("")){
                cutDueDet = 0
                Log.e(TAG,"4117  getCustomerDueDetails")
                try {
                    getCustomerDueDetails()
                }catch (e: Exception){
                    Log.e(TAG,"Exception 4136  "+e.toString())
                }

            }
        }

    }

    private fun getCustomerserviceCount(ID_Customer: String, Customer_Type: String, ID_Product: String) {
     //   ID_Customer!!,Customer_Type!!,ID_Product!!
            context = this@CustomerServiceActivity
            customerservicecountViewModel = ViewModelProvider(this).get(CustomerservicecountViewModel::class.java)
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    customerservicecountViewModel.getCustomerserviceCount(this,ID_Customer,Customer_Type,ID_Product)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            Log.e(TAG,"4848  ")
                            if (msg!!.length > 0) {

                                if (custServiceCount == 0) {
                                    custServiceCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   count 1  " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt =
                                        jObject.getJSONObject("CustomerServiceRegisterCount")
                                    warrantycount = jobjt.getString("WarrantyCount")
                                    servicehistorycount = jobjt.getString("ServiceHistoryCount")
                                    salescount = jobjt.getString("SalesCount")
                                    customerduecount = jobjt.getString("CustomerDueCount")

                                    val warcount =
                                        context.getSharedPreferences(Config.SHARED_PREF50, 0)
                                    val warrantycountEditer = warcount.edit()
                                    warrantycountEditer.putString("WarrantyCount", warrantycount)
                                    warrantycountEditer.commit()


                                    val servcount =
                                        context.getSharedPreferences(Config.SHARED_PREF51, 0)
                                    val servcountEditer = servcount.edit()
                                    servcountEditer.putString(
                                        "ServiceHistoryCount",
                                        servicehistorycount
                                    )
                                    servcountEditer.commit()

                                    val salecount =
                                        context.getSharedPreferences(Config.SHARED_PREF52, 0)
                                    val salecountEditer = salecount.edit()
                                    salecountEditer.putString("SalesCount", salescount)
                                    salecountEditer.commit()

                                    val custduecount =
                                        context.getSharedPreferences(Config.SHARED_PREF53, 0)
                                    val custduecountEditer = custduecount.edit()
                                    custduecountEditer.putString(
                                        "CustomerDueCount",
                                        customerduecount
                                    )
                                    custduecountEditer.commit()


                                    tv_warranty_count!!.text = warrantycount
                                    tv_product_count!!.text = servicehistorycount
                                    tv_sales_count!!.text = salescount
                                    tv_customerdue_count!!.text = customerduecount

                                    warrantyMode = "1"
                                    prodHistMode = "0"
                                    saleHistMode = "1"
                                    custDueMode = "1"
                                    hideMoreViews()


                                } else {

                                }
                            }
                            } else {

                            }
                        })
                    progressDialog!!.dismiss()
                }
                false -> {
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                        .show()
                }
            }


//         var custDet = 0






    }

    private fun detailBottomSheet() {
        try {

            warrantyMode = "1"
            prodHistMode = "1"
            saleHistMode = "1"


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.cs_detail_bottom_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

//            dialog1!!.behavior.isFitToContents=true
//            dialog1!!.behavior.state= BottomSheetBehavior.SAVE_FIT_TO_CONTENTS



//            dialog1!!.setCancelable(false)
         //   view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


            tabLayout = view.findViewById(R.id.tabLayout)
            recyServiceWarranty = view.findViewById(R.id.recyServiceWarranty)
            recyServiceProductHistory = view.findViewById(R.id.recyServiceProductHistory)
            recyServiceSalesHistory = view.findViewById(R.id.recyServiceSalesHistory)
            ll_history_details = view.findViewById(R.id.ll_history_details)
            card_details = view.findViewById(R.id.card_details)

//            val displayMetrics = DisplayMetrics()
//            windowManager.defaultDisplay.getMetrics(displayMetrics)
//            val heightS: Int = displayMetrics.heightPixels
//          //  card_details!!.layoutParams.height = (height-50)
////            val layoutParams = card_details!!.getLayoutParams() as LinearLayout.LayoutParams
////            layoutParams.height = heightS.toInt()
////            layoutParams.width = MATCH_PARENT
////            card_details!!.setLayoutParams(layoutParams)
//
//            dialog1!!.behavior.peekHeight = heightS




            recyServiceWarranty!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            recyServiceProductHistory!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            recyServiceSalesHistory!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent?): Boolean {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    v.onTouchEvent(event)
                    return true
                }
            })

            ll_history_details!!.visibility =View.VISIBLE
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Warranty"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Product"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Sales"))
            tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE

            ll_history_details!!.visibility = View.VISIBLE
            recyServiceWarranty!!.visibility = View.GONE
            recyServiceProductHistory!!.visibility = View.GONE
            recyServiceSalesHistory!!.visibility = View.GONE


            if (!ID_Product.equals("") && !ID_Customer.equals("")){

                ll_history_details!!.visibility = View.VISIBLE
                recyServiceWarranty!!.visibility = View.GONE
                recyServiceProductHistory!!.visibility = View.GONE
                recyServiceSalesHistory!!.visibility = View.GONE
                recyServiceWarranty!!.adapter = null
//
//                warrantyDet = 0
//                getWarranty()
            }



            tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    Log.e(TAG,"onTabSelected  113  "+tab.position)
                    if (tab.position == 0){
                        Log.e(TAG,"onTabSelected  1131  "+tab.position)

                        ll_history_details!!.visibility = View.VISIBLE
                        recyServiceWarranty!!.visibility = View.GONE
                        recyServiceProductHistory!!.visibility = View.GONE
                        recyServiceSalesHistory!!.visibility = View.GONE
                        recyServiceWarranty!!.adapter = null

//                        if (!ID_Product.equals("") && !ID_Customer.equals("")){
//                            warrantyDet = 0
//                            getWarranty()
//                        }

//                        if (!ID_Customer.equals("")){
//                            warrantyDet = 0
//                            getWarranty()
//                        }

                    }
                    if (tab.position == 1){
                        Log.e(TAG,"onTabSelected  1131  "+tab.position)
                        ll_history_details!!.visibility = View.VISIBLE
                        recyServiceWarranty!!.visibility = View.GONE
                        recyServiceProductHistory!!.visibility = View.GONE
                        recyServiceSalesHistory!!.visibility = View.GONE
                        recyServiceSalesHistory!!.adapter = null
                        if (!ID_Product.equals("") && !ID_Customer.equals("")){
                            Log.e(TAG,"qqqqqqq 2 ")
                            productHistDet = 0
                            getProductHistory()
                        }

                    }
                    if (tab.position == 2){
                        ll_history_details!!.visibility = View.VISIBLE
                        recyServiceWarranty!!.visibility = View.GONE
                        recyServiceProductHistory!!.visibility = View.GONE
                        recyServiceProductHistory!!.adapter = null
                        if (!ID_Customer.equals("")){
                            salesHistDet = 0
                            getSalesHistory()
                        }



                    }

                }
                override fun onTabUnselected(tab: TabLayout.Tab) {
                    Log.e(TAG,"onTabUnselected  162  "+tab.position)
                }
                override fun onTabReselected(tab: TabLayout.Tab) {
                    Log.e(TAG,"onTabReselected  165  "+tab.position)
                }
            })



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }




    private fun getProductHistory() {
//        llBtn!!.visibility = View.GONE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_product, null, false)
//        llMainDetail!!.addView(inflatedLayout);


//        var recyServiceProduct = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceProduct)
//        recyServiceProduct.adapter = null
//        var product = 0

    //    ll_history_details!!.visibility = View.VISIBLE
        lnrHead_warranty_sub!!.visibility = View.GONE
        lnrHead_product_sub!!.visibility = View.VISIBLE
        lnrHead_sales_sub!!.visibility = View.GONE
        lnrHead_customerdue_sub!!.visibility = View.GONE

        recyServiceWarranty!!.visibility = View.GONE
        recyServiceProductHistory!!.visibility = View.GONE
        recyServiceSalesHistory!!.visibility = View.GONE
        recyServiceCustomerdue!!.visibility = View.GONE
        recyServiceProductHistory!!.adapter = null


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                serviceProductHistoryViewModel.getServiceProductHistory(this,ID_Product!!,Customer_Type!!,ID_Customer!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (productHistDet == 0){
                                productHistDet++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   3554   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ProductHistory")
                                    serviceProductHistoryArrayList = jobjt.getJSONArray("ProductHistoyList")
                                    if (serviceProductHistoryArrayList.length()>0){
                                        tv_product_count!!.setText(""+serviceProductHistoryArrayList.length())
                                   //     ll_history_details!!.visibility = View.GONE
                                        recyServiceProductHistory!!.visibility = View.VISIBLE

                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceProductHistory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceProductHistoryAdapter(this@CustomerServiceActivity, serviceProductHistoryArrayList)
                                        recyServiceProductHistory!!.adapter = adapter


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
                            }


                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
//                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun getSalesHistory() {
//        llBtn!!.visibility = View.GONE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_sales, null, false)
//        llMainDetail!!.addView(inflatedLayout);

//        var recyServiceSales = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceSales)
//        recyServiceSales.adapter = null
//        var sales = 0

       // ll_history_details!!.visibility = View.VISIBLE


        lnrHead_warranty_sub!!.visibility = View.GONE
        lnrHead_product_sub!!.visibility = View.GONE
        lnrHead_sales_sub!!.visibility = View.VISIBLE
        lnrHead_customerdue_sub!!.visibility = View.GONE

        recyServiceWarranty!!.visibility = View.GONE
        recyServiceProductHistory!!.visibility = View.GONE
        recyServiceSalesHistory!!.visibility = View.GONE
        recyServiceCustomerdue!!.visibility = View.GONE
        recyServiceSalesHistory!!.adapter = null


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceSalesViewModel.getServiceSales(this,ID_Product!!,Customer_Type!!,ID_Customer!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (salesHistDet == 0){
                                salesHistDet++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   335   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("SalesHistory")
                                    serviceSalesArrayList = jobjt.getJSONArray("SalesHistoryList")
                                    if (serviceSalesArrayList.length()>0){
                                        tv_sales_count!!.setText(""+serviceSalesArrayList.length())
                                        recyServiceSalesHistory!!.visibility = View.VISIBLE
                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceSalesHistory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceSalesAdapter(this@CustomerServiceActivity, serviceSalesArrayList)
                                        recyServiceSalesHistory!!.adapter = adapter
                                        adapter.setClickListener(this@CustomerServiceActivity)


                                    }
                                } else {
//                                val builder = AlertDialog.Builder(
//                                    this@CustomerServiceActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
                                }

                            }


                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
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

    private fun getCustomerDueDetails() {

        lnrHead_warranty_sub!!.visibility = View.GONE
        lnrHead_product_sub!!.visibility = View.GONE
        lnrHead_sales_sub!!.visibility = View.GONE
        lnrHead_customerdue_sub!!.visibility = View.VISIBLE

        recyServiceWarranty!!.visibility = View.GONE
        recyServiceProductHistory!!.visibility = View.GONE
        recyServiceSalesHistory!!.visibility = View.GONE
        recyServiceCustomerdue!!.visibility = View.GONE
        recyServiceCustomerdue!!.adapter = null


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                customerDueViewModel.getCustomerDue(this,ID_Customer!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (cutDueDet == 0){
                                cutDueDet++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   5109   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("CustomerDueDetils")
                                    customerDueArrayList = jobjt.getJSONArray("CustomerDueDetilsList")
                                    if (customerDueArrayList.length()>0){
                                        tv_customerdue_count!!.setText(""+customerDueArrayList.length())
                                        recyServiceCustomerdue!!.visibility = View.VISIBLE
                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceCustomerdue!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = CustomerDueAdapter(this@CustomerServiceActivity, customerDueArrayList)
                                        recyServiceCustomerdue!!.adapter = adapter

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

                            }

                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
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



    override fun onClick(position: Int, data: String) {

        if (data.equals("customerList")) {
            dialogCustSearch!!.dismiss()
        //    {"ServiceCustomerDetails":{"ServiceCustomerList":[{"Customer_ID":"5","Name":"Youshaf A","Mobile":"04902318508","Address":"Thaif Mahal","CusMode":"0"}
            val jsonObject = customerSort.getJSONObject(position)

            Log.e(TAG,"jsonObject  5161   "+jsonObject)

            tie_CustomerName!!.setText(jsonObject!!.getString("Name"))
            ID_Customer = jsonObject.getString("Customer_ID")
            Customer_Type = jsonObject.getString("CusMode")

            tie_MobileNo!!.setText(jsonObject!!.getString("Mobile"))
            tie_Address!!.setText(jsonObject!!.getString("Address"))

//            if (ID_Customer.equals("")){
//                til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
//            }else{
//                til_CustomerName!!.isErrorEnabled = false
//                til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
//            }
//
//           // tie_CustomerName!!.isEnabled = false
//            tie_MobileNo!!.isEnabled = false
//            tie_Address!!.isEnabled = false
//
//          //  til_CustomerName!!.setEndIconDrawable(com.google.android.material.R.drawable.abc_ic_clear_material)
//       //     til_CustomerName!!.setEndIconDrawable(context.resources.getDrawable(R.drawable.svg_clear))
//            ID_CompCategory = ""
//            ID_Category = ""
//            ID_Company = ""
//            ID_Product = ""
//            ID_Services = ""
//            ID_ComplaintList = ""
//
//            tie_CompCategory!!.setText("")
//            tie_Category!!.setText("")
//            tie_Company!!.setText("")
//            tie_Product!!.setText("")
//            tie_Service!!.setText("")
//            tie_Complaint!!.setText("")
//            tie_Description!!.setText("")
//
//            til_Company!!.visibility = View.GONE
//            til_Service!!.visibility = View.GONE
//            til_Complaint!!.visibility = View.GONE

        }

        if (data.equals("servpriority")){
            dialogServPriority!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = servPrioritySort.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("Code"))
            ID_Priority = jsonObject.getString("Code")
            tie_Priority!!.setText(jsonObject.getString("Description"))


        }

        if (data.equals("channel")) {
            dialogChannel!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = channelSort.getJSONObject(position)
            Log.e(TAG,"Code   "+jsonObject.getString("Code"))
            ID_Channel = jsonObject.getString("Code")
            tie_Channel!!.setText(jsonObject.getString("Description"))

            ID_Employee = ""
            ID_ServiceMedia = ""
            ID_EmpMedia = ""
            tie_EmpOrMedia!!.setText("")

            if (ID_Channel.equals("5") || ID_Channel.equals("6")){
                til_EmpOrMedia!!.visibility  = View.VISIBLE
                til_EmpOrMedia!!.setHint(jsonObject.getString("Description"))

                if (ID_Channel.equals("5")){
                    til_EmpOrMedia!!.setStartIconDrawable(context.resources.getDrawable(R.drawable.icon_svg_employee));
                }
                if (ID_Channel.equals("6")){
                    til_EmpOrMedia!!.setStartIconDrawable(context.resources.getDrawable(R.drawable.icon_svg_media));
                }

            }
            else{
                til_EmpOrMedia!!.visibility  = View.GONE
            }



            // Code = 5  => Employee
            // Code = 6  => Media
        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            if (employeeMode == 0){
                ID_Employee = jsonObject.getString("ID_Employee")
                ID_EmpMedia = jsonObject.getString("ID_Employee")
                tie_EmpOrMedia!!.setText(jsonObject.getString("EmpName"))
            }

            if (employeeMode == 1){
                ID_AttendedBy = jsonObject.getString("ID_Employee")
                tie_Attendedby!!.setText(jsonObject.getString("EmpName"))
            }



        }

        if (data.equals("pincodedetails")) {


            dialogPinCode!!.dismiss()
            val jsonObject = pinCodeArrayList.getJSONObject(position)

            FK_Country = jsonObject.getString("FK_Country")
            FK_States = jsonObject.getString("FK_States")
            FK_District = jsonObject.getString("FK_District")
            FK_Area = jsonObject.getString("FK_Area")
            FK_Place = jsonObject.getString("FK_Place")
            FK_Post = jsonObject.getString("FK_Post")
            FK_Area = jsonObject.getString("FK_Area")

            tie_CN_Place!!.setText(jsonObject.getString("Place"))
            tie_CN_Country!!.setText(jsonObject.getString("Country"))
            tie_CN_State!!.setText(jsonObject.getString("States"))
            tie_CN_District!!.setText(jsonObject.getString("District"))
            tie_CN_Area!!.setText(jsonObject.getString("Area"))
            tie_CN_Post!!.setText(jsonObject.getString("Post"))

        }

        if (data.equals("compCategory")) {

            dialogcompCategory!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = compCategorySort.getJSONObject(position)
            Log.e(TAG,"Code   "+jsonObject.getString("Code"))

            ID_CompCategory = jsonObject.getString("Code")
            tie_CompCategory!!.setText(jsonObject.getString("Description"))

            ID_Category = ""
            tie_Category!!.setText("")
            tie_Product!!.setText("")
            ID_Product = ""
            tie_Company!!.setText("")
            ID_Company = ""
            tie_Service!!.setText("")
            ID_Services = ""
            tie_Complaint!!.setText("")
            ID_ComplaintList = ""

            if (ID_CompCategory.equals("1")){
                til_Company!!.visibility = View.GONE
                til_Service!!.visibility = View.VISIBLE
                til_Complaint!!.visibility = View.GONE
            }
            else if (ID_CompCategory.equals("2")){
                til_Company!!.visibility = View.VISIBLE
                til_Service!!.visibility = View.VISIBLE
                til_Complaint!!.visibility = View.GONE
            }
            else if (ID_CompCategory.equals("3")){
                til_Company!!.visibility = View.GONE
                til_Service!!.visibility = View.GONE
                til_Complaint!!.visibility = View.VISIBLE
            }
            else if (ID_CompCategory.equals("4")){
                til_Company!!.visibility = View.VISIBLE
                til_Service!!.visibility = View.GONE
                til_Complaint!!.visibility = View.VISIBLE
            }


        }

        if (data.equals("category")) {

            dialogCategory!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = categorySort.getJSONObject(position)
            Log.e(TAG,"ID_Category   "+jsonObject.getString("ID_Category"))

            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))

            tie_Product!!.setText("")
            ID_Product = ""
            tie_Company!!.setText("")
            ID_Company = ""
            tie_Service!!.setText("")
            ID_Services = ""
            tie_Complaint!!.setText("")
            ID_ComplaintList = ""

//            if (ID_Category.equals("1")){
//                til_Company!!.visibility = View.GONE
//                til_Service!!.visibility = View.VISIBLE
//                til_Complaint!!.visibility = View.GONE
//            }
//            else if (ID_Category.equals("2")){
//                til_Company!!.visibility = View.VISIBLE
//                til_Service!!.visibility = View.VISIBLE
//                til_Complaint!!.visibility = View.GONE
//            }
//            else if (ID_Category.equals("3")){
//                til_Company!!.visibility = View.GONE
//                til_Service!!.visibility = View.GONE
//                til_Complaint!!.visibility = View.VISIBLE
//            }
//            else if (ID_Category.equals("4")){
//                til_Company!!.visibility = View.VISIBLE
//                til_Service!!.visibility = View.GONE
//                til_Complaint!!.visibility = View.VISIBLE
//            }



//            if (ID_Category.equals("2") || ID_Category.equals("4")){
//                til_Company!!.visibility = View.VISIBLE
//            }else{
//                til_Company!!.visibility = View.GONE
//            }

        }

        if (data.equals("company")) {

            dialogCompany!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = companySort.getJSONObject(position)
            Log.e(TAG,"Code   "+jsonObject.getString("Code"))

            ID_Company = jsonObject.getString("Code")
            tie_Company!!.setText(jsonObject.getString("Description"))

        }

        if (data.equals("serviceProduct")) {

            dialogProduct!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = productSort.getJSONObject(position)
            Log.e(TAG,"ID_Product   "+jsonObject.getString("ID_Product"))

            ID_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))

        }

        if (data.equals("service")) {

            dialogService!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = serviceSort.getJSONObject(position)
            Log.e(TAG,"ID_Services   "+jsonObject.getString("ID_Services"))

            ID_Services = jsonObject.getString("ID_Services")
            tie_Service!!.setText(jsonObject.getString("ServicesName"))

        }

        if (data.equals("compalint")) {

            dialogComplaint!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = complaintSort.getJSONObject(position)
            Log.e(TAG,"ID_ComplaintList   "+jsonObject.getString("ID_ComplaintList"))

            ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
            tie_Complaint!!.setText(jsonObject.getString("ComplaintName"))

        }

        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
//            val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_Status = jsonObject.getString("ID_NextAction")
            tie_Status!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("serviceMedia")){
            dialogServiceMedia!!.dismiss()
            val jsonObject = serviceMediaSort.getJSONObject(position)
            Log.e(TAG,"ID_FIELD   "+jsonObject.getString("ID_FIELD"))
            ID_ServiceMedia = jsonObject.getString("ID_FIELD")
            ID_EmpMedia = jsonObject.getString("ID_FIELD")
            tie_EmpOrMedia!!.setText(jsonObject.getString("Name"))

        }

        if (data.equals("countrydetail")) {
            dialogCountry!!.dismiss()
            val jsonObject = countrySort.getJSONObject(position)
            Log.e(TAG, "FK_Country   " + jsonObject.getString("FK_Country"))
            FK_Country = jsonObject.getString("FK_Country")
            tie_CN_Country!!.setText(jsonObject.getString("Country"))


            FK_States = ""
            FK_District = ""
            FK_Area = ""
            FK_Post = ""

            tie_CN_State!!.setText("")
            tie_CN_District!!.setText("")
            tie_CN_Area!!.setText("")
            tie_CN_Post!!.setText("")
            tie_CN_Place!!.setText("")



        }

        if (data.equals("statedetail")) {
            dialogState!!.dismiss()
            val jsonObject = stateSort.getJSONObject(position)
            Log.e(TAG, "FK_States   " + jsonObject.getString("FK_States"))
            FK_States = jsonObject.getString("FK_States")
            tie_CN_State!!.setText(jsonObject.getString("States"))

            FK_District = ""
            FK_Area = ""
            FK_Post = ""

            tie_CN_District!!.setText("")
            tie_CN_Area!!.setText("")
            tie_CN_Post!!.setText("")
            tie_CN_Place!!.setText("")


        }

        if (data.equals("districtdetail")) {
            dialogDistrict!!.dismiss()

            val jsonObject = districtSort.getJSONObject(position)
            Log.e(TAG, "FK_District   " + jsonObject.getString("FK_District"))
            FK_District = jsonObject.getString("FK_District")
            tie_CN_District!!.setText(jsonObject.getString("District"))


            FK_Area = ""
            FK_Post = ""

            tie_CN_Area!!.setText("")
            tie_CN_Post!!.setText("")
            tie_CN_Place!!.setText("")

        }

        if (data.equals("areadetail")) {
            dialogArea!!.dismiss()
            val jsonObject = areaSort.getJSONObject(position)
            Log.e(TAG, "jsonObject  5465    " + jsonObject)


            FK_Area = jsonObject.getString("FK_Area")
            tie_CN_Area!!.setText(jsonObject.getString("Area"))
            FK_Post = ""

            tie_CN_Post!!.setText("")
            tie_CN_Place!!.setText("")


        }

        if (data.equals("postdetail")) {
            dialogPost!!.dismiss()
            val jsonObject = postSort.getJSONObject(position)
            Log.e(TAG, "FK_Post  3672    " + jsonObject.getString("FK_Post"))
            Log.e(TAG, "PinCode  3672    " + jsonObject.getString("PinCode"))
            FK_Post = jsonObject.getString("FK_Post")
            tie_CN_Post!!.setText(jsonObject.getString("PostName"))
            tie_CN_Pincode!!.setText(jsonObject.getString("PinCode"))

        }

        if (data.equals("salesHistoryClick")) {
            val jsonObject = serviceSalesArrayList.getJSONObject(position)
            Log.e(TAG,"InvoiceDate  5563   "+jsonObject.getString("InvoiceDate"))
            ID_CustomerWiseProductDetails = jsonObject.getString("ID_CustomerWiseProductDetails")
          //  detailPopupSheet(custname)
            warrantyPopupSheet()

        }

    }

    private fun warrantyPopupSheet() {
        try {


            dialogWarrantySheet = Dialog(this)
            dialogWarrantySheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogWarrantySheet!! .setContentView(R.layout.cs_warranty_detail_popup)
            dialogWarrantySheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


            recyServiceWarranty = dialogWarrantySheet!! .findViewById(R.id.recyServiceWarranty)

            warrantyDet = 0
            getWarranty()


            val window: Window? = dialogWarrantySheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


            dialogWarrantySheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getWarranty() {

//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_warranty, null, false)
//
////        var recyServiceWarranty = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceWarranty)
////        recyServiceWarranty.adapter = null

//        var warranty = 0
        //  ll_history_details!!.visibility = View.VISIBLE
//        lnrHead_warranty_sub!!.visibility = View.VISIBLE
//        lnrHead_product_sub!!.visibility = View.GONE
//        lnrHead_sales_sub!!.visibility = View.GONE
//        lnrHead_customerdue_sub!!.visibility = View.GONE
//
//        recyServiceWarranty!!.visibility = View.GONE
//        recyServiceProductHistory!!.visibility = View.GONE
//        recyServiceSalesHistory!!.visibility = View.GONE
//        recyServiceCustomerdue!!.visibility = View.GONE
        recyServiceWarranty!!.adapter = null

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                serviceWarrantyViewModel.getServiceWarranty(this,ID_Product!!,Customer_Type!!,ID_Customer!!,ID_CustomerWiseProductDetails!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   55531   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                Log.e(TAG,"msg   55532   "+msg)
                                val jobjt = jObject.getJSONObject("WarrantyDetails")
                                serviceWarrantyArrayList = jobjt.getJSONArray("WarrantyDetailsList")
                                Log.e(TAG,"msg   55533   "+serviceWarrantyArrayList)
                                if (serviceWarrantyArrayList.length()>0){
                                    if (warrantyDet == 0){
                                        warrantyDet++
                                        tv_warranty_count!!.setText(""+serviceWarrantyArrayList.length())
                                        //  ll_history_details!!.visibility = View.GONE
                                        recyServiceWarranty!!.visibility = View.VISIBLE

                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
                                        recyServiceWarranty!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceWarrantyAdapter(this@CustomerServiceActivity, serviceWarrantyArrayList)
                                        recyServiceWarranty!!.adapter = adapter
                                    }

                                }
                            } else {
//                                val builder = AlertDialog.Builder(
//                                    this@CustomerServiceActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
                            }


                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
                //  progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

//    ADD NEE CUSTOMER

    private fun getDefaultValueSettings() {

    when (Config.ConnectivityUtils.isConnected(this)) {
        true -> {
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
            progressDialog!!.show()

            leadGenerateDefaultvalueViewModel.getLeadGenerationDefaultvalue(this)!!.observe(
                this,
                Observer { serviceSetterGetter ->
                    val msg = serviceSetterGetter.message
                    Log.v("hkhkhkjjj", "in")
                    if (msg!!.length > 0) {

                        val jObject = JSONObject(msg)
                        Log.e(TAG, "msg   14781   " + msg.length)
                        Log.e(TAG, "msg   14782   " + msg)
                        if (jObject.getString("StatusCode") == "0") {


                            val jobjt = jObject.getJSONObject("LeadGenerationDefaultvalueSettings")
                            FK_Country = jobjt.getString("FK_Country")
                            FK_States = jobjt.getString("FK_States")
                            FK_District = jobjt.getString("FK_District")

                            tie_CN_Country!!.setText(jobjt.getString("Country"))
                            tie_CN_State!!.setText(jobjt.getString("StateName"))
                            tie_CN_District!!.setText(jobjt.getString("DistrictName"))


                        } else {
//                            val builder = AlertDialog.Builder(
//                                this@CustomerServiceActivity,
//                                R.style.MyDialogTheme
//                            )
//                            builder.setMessage(jObject.getString("EXMessage"))
//                            builder.setPositiveButton("Ok") { dialogInterface, which ->
//                            }
//                            val alertDialog: AlertDialog = builder.create()
//                            alertDialog.setCancelable(false)
//                            alertDialog.show()
                        }
                    } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
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

    private fun getCountry() {
//        var countryDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                countryViewModel.getCountry(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (countryCount == 0) {
                                    countryCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2252   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CountryDetails")
                                        countryArrayList = jobjt.getJSONArray("CountryDetailsList")

                                        if (countryArrayList.length() > 0) {
//                                            if (countryDet == 0){
//                                                countryDet++
                                            countryListPopup(countryArrayList)
//                                            }

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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                                }
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

    private fun countryListPopup(countryArrayList: JSONArray) {
        try {

            dialogCountry = Dialog(this)
            dialogCountry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCountry!!.setContentView(R.layout.country_list_popup)
            dialogCountry!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycCountry = dialogCountry!!.findViewById(R.id.recycCountry) as RecyclerView
            val etsearch = dialogCountry!!.findViewById(R.id.etsearch) as EditText

            countrySort = JSONArray()
            for (k in 0 until countryArrayList.length()) {
                val jsonObject = countryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                countrySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycCountry!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = CountryDetailAdapter(this@CustomerServiceActivity, countryArrayList)
            val adapter = CountryDetailAdapter(this@CustomerServiceActivity, countrySort)
            recycCountry!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    countrySort = JSONArray()

                    for (k in 0 until countryArrayList.length()) {
                        val jsonObject = countryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Country").length) {
                            if (jsonObject.getString("Country")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                countrySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "countrySort               7103    " + countrySort)
                    val adapter = CountryDetailAdapter(this@CustomerServiceActivity, countrySort)
                    recycCountry!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogCountry!!.show()
            dialogCountry!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogCountry!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getState() {

//        var stateDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stateViewModel.getState(this, FK_Country!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            Log.e(TAG, "msg   2338   " + msg)
                            if (msg!!.length > 0) {
                                if (stateCount == 0) {
                                    stateCount++
                                    val jObject = JSONObject(msg)

                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("StatesDetails")
                                        stateArrayList = jobjt.getJSONArray("StatesDetailsList")

                                        if (stateArrayList.length() > 0) {
//                                            if (stateDet == 0){
//                                                stateDet++
                                            stateDetailPopup(stateArrayList)
//                                            }

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
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun stateDetailPopup(stateArrayList: JSONArray) {

        try {

            dialogState = Dialog(this)
            dialogState!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogState!!.setContentView(R.layout.state_list_popup)
            dialogState!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycState = dialogState!!.findViewById(R.id.recycState) as RecyclerView
            val etsearch = dialogState!!.findViewById(R.id.etsearch) as EditText

            stateSort = JSONArray()
            for (k in 0 until stateArrayList.length()) {
                val jsonObject = stateArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                stateSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycState!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = StateDetailAdapter(this@CustomerServiceActivity, stateArrayList)
            val adapter = StateDetailAdapter(this@CustomerServiceActivity, stateSort)
            recycState!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stateSort = JSONArray()

                    for (k in 0 until stateArrayList.length()) {
                        val jsonObject = stateArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("States").length) {
                            if (jsonObject.getString("States")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                stateSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "stateSort               7103    " + stateSort)
                    val adapter = StateDetailAdapter(this@CustomerServiceActivity, stateSort)
                    recycState!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogState!!.show()
            dialogState!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogState!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getDistrict() {
//        var distDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                districtViewModel.getDistrict(this, FK_States)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (districtCount == 0) {
                                    districtCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2286   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("DistrictDetails")
                                        districtArrayList =
                                            jobjt.getJSONArray("DistrictDetailsList")

                                        if (districtArrayList.length() > 0) {
//                                            if (distDet == 0){
//                                                distDet++
                                            districtDetailPopup(districtArrayList)
//                                            }

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
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun districtDetailPopup(districtArrayList: JSONArray) {

        try {

            dialogDistrict = Dialog(this)
            dialogDistrict!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDistrict!!.setContentView(R.layout.district_list_popup)
            dialogDistrict!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycDistrict = dialogDistrict!!.findViewById(R.id.recycDistrict) as RecyclerView
            val etsearch = dialogDistrict!!.findViewById(R.id.etsearch) as EditText

            districtSort = JSONArray()
            for (k in 0 until districtArrayList.length()) {
                val jsonObject = districtArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                districtSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycDistrict!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DistrictDetailAdapter(this@CustomerServiceActivity, districtArrayList)
            val adapter = DistrictDetailAdapter(this@CustomerServiceActivity, districtSort)
            recycDistrict!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    districtSort = JSONArray()

                    for (k in 0 until districtArrayList.length()) {
                        val jsonObject = districtArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("District").length) {
                            if (jsonObject.getString("District")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                districtSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "districtSort               7103    " + districtSort)
                    val adapter = DistrictDetailAdapter(this@CustomerServiceActivity, districtSort)
                    recycDistrict!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogDistrict!!.show()
            dialogDistrict!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogDistrict!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getArea() {
//        var areaDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                areaViewModel.getArea(this, FK_District)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (areaCount == 0) {
                                    areaCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AreaDetails")
                                        areaArrayList = jobjt.getJSONArray("AreaDetailsList")

                                        if (areaArrayList.length() > 0) {
//                                            if (postDet == 0){
//                                                postDet++
                                            areaDetailPopup(areaArrayList)
//                                            }

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
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun areaDetailPopup(areaArrayList: JSONArray) {

        try {

            dialogArea = Dialog(this)
            dialogArea!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogArea!!.setContentView(R.layout.area_list_popup)
            dialogArea!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recycArea = dialogArea!!.findViewById(R.id.recycArea) as RecyclerView
            val etsearch = dialogArea!!.findViewById(R.id.etsearch) as EditText

            areaSort = JSONArray()
            for (k in 0 until areaArrayList.length()) {
                val jsonObject = areaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                areaSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycArea!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@CustomerServiceActivity, areaArrayList)
            val adapter = AreaDetailAdapter(this@CustomerServiceActivity, areaSort)
            recycArea!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    areaSort = JSONArray()

                    for (k in 0 until areaArrayList.length()) {
                        val jsonObject = areaArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Area").length) {
                            if (jsonObject.getString("Area")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                areaSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "areaSort               7103    " + areaSort)
                    val adapter = AreaDetailAdapter(this@CustomerServiceActivity, areaSort)
                    recycArea!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogArea!!.show()
            dialogArea!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogArea!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getPost() {
//        var postDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                postViewModel.getPost(this, FK_Area)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (postCount == 0) {
                                    postCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("PostDetails")
                                        postArrayList = jobjt.getJSONArray("PostDetailsList")

                                        if (postArrayList.length() > 0) {
//                                            if (postDet == 0){
//                                                postDet++
                                            postDetailPopup(postArrayList)
//                                            }

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
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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

    private fun postDetailPopup(postArrayList: JSONArray) {

        try {

            dialogPost = Dialog(this)
            dialogPost!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPost!!.setContentView(R.layout.post_list_popup)
            dialogPost!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycPost = dialogPost!!.findViewById(R.id.recycPost) as RecyclerView
            val etsearch = dialogPost!!.findViewById(R.id.etsearch) as EditText

            postSort = JSONArray()
            for (k in 0 until postArrayList.length()) {
                val jsonObject = postArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                postSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recycPost!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@CustomerServiceActivity, postArrayList)
            val adapter = PostDetailAdapter(this@CustomerServiceActivity, postSort)
            recycPost!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    postSort = JSONArray()

                    for (k in 0 until postArrayList.length()) {
                        val jsonObject = postArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("PostName").length) {
                            if (jsonObject.getString("PostName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                postSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "postSort               7103    " + postSort)
                    val adapter = PostDetailAdapter(this@CustomerServiceActivity, postSort)
                    recycPost!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogPost!!.show()
            dialogPost!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogPost!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
                editable === tie_Date!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Date!!.text.toString().equals("")){
                        til_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Date!!.isErrorEnabled = false
                        til_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_Time!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Time!!.text.toString().equals("")){
                        til_Time!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Time!!.isErrorEnabled = false
                        til_Time!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_CustomerName!!.editableText -> {
                    Log.e(TAG,"283021    ")
//                    if (tie_CustomerName!!.text.toString().equals("")){
                    if (ID_Customer.equals("") && (tie_CustomerName!!.text.toString()).equals("")){
                        til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CustomerName!!.isErrorEnabled = false
                        til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_MobileNo!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    if (tie_MobileNo!!.text!!.length > 9 ){
                        til_MobileNo!!.isErrorEnabled = false
                        til_MobileNo!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                    else{
                        til_MobileNo!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }


                }
                editable === tie_Address!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    if (tie_Address!!.text.toString().equals("")){
                        til_Address!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }else{
                        til_Address!!.isErrorEnabled = false
                        til_Address!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_Priority!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    if (tie_Priority!!.text.toString().equals("")){
                        til_Priority!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Priority!!.isErrorEnabled = false
                        til_Priority!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CompCategory!!.editableText -> {
                    Log.e(TAG,"283022    ")
                  //  til_CompCategory!!.isErrorEnabled = false
                    if (tie_CompCategory!!.text.toString().equals("")){
                        til_CompCategory!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CompCategory!!.isErrorEnabled = false
                        til_CompCategory!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_Category!!.editableText -> {
                    Log.e(TAG,"283022    ")
                   // til_Category!!.isErrorEnabled = false
                    if (tie_Category!!.text.toString().equals("")){
                        til_Category!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Category!!.isErrorEnabled = false
                        til_Category!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }


                editable === tie_Product!!.editableText -> {
                    Log.e(TAG,"283022    ")
                  //  til_Product!!.isErrorEnabled = false
                }

                editable === tie_Service!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    //til_Service!!.isErrorEnabled = false
                    if (tie_Service!!.text.toString().equals("")){
                        til_Service!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Service!!.isErrorEnabled = false
                        til_Service!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_Complaint!!.editableText -> {
                    Log.e(TAG,"283022    ")
                   // til_Complaint!!.isErrorEnabled = false
                    if (tie_Complaint!!.text.toString().equals("")){
                        til_Complaint!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Complaint!!.isErrorEnabled = false
                        til_Complaint!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_Description!!.editableText -> {
                    Log.e(TAG,"283022    ")
                   // til_Description!!.isErrorEnabled = false
                    if (tie_Description!!.text.toString().equals("")){
                     //   til_Description!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                        til_Description!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }else{
                        til_Description!!.isErrorEnabled = false
                        til_Description!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }


                editable === tie_CN_Name!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_Name!!.text.toString().equals("")){
                        til_CN_Name!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_Name!!.isErrorEnabled = false
                        til_CN_Name!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_Mobile!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_Mobile!!.text.toString().length < 10){
                        til_CN_Mobile!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_Mobile!!.isErrorEnabled = false
                        til_CN_Mobile!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_HouseName!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_HouseName!!.text.toString().equals("")){
                        til_CN_HouseName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }else{
                        til_CN_HouseName!!.isErrorEnabled = false
                        til_CN_HouseName!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_Pincode!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_Pincode!!.text.toString().equals("")){
                        til_CN_Pincode!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_Pincode!!.isErrorEnabled = false
                        til_CN_Pincode!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

                editable === tie_CN_Country!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_Country!!.text.toString().equals("")){
                        til_CN_Country!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_Country!!.isErrorEnabled = false
                        til_CN_Country!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_State!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_State!!.text.toString().equals("")){
                        til_CN_State!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_State!!.isErrorEnabled = false
                        til_CN_State!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_District!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_District!!.text.toString().equals("")){
                        til_CN_District!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_District!!.isErrorEnabled = false
                        til_CN_District!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

                editable === tie_CN_Area!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    // til_Description!!.isErrorEnabled = false
                    if (tie_CN_Area!!.text.toString().equals("")){
                        til_CN_Area!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_CN_Area!!.isErrorEnabled = false
                        til_CN_Area!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

            }



        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult  256   " + requestCode + "   " + resultCode + "  " + data)


        if (requestCode == SELECT_LOCATION) {
            if (data != null) {

                try {
                    locAddress = data.getStringExtra("address")
                    locCity = data.getStringExtra("city")
                    locState = data.getStringExtra("state")
                    locCountry = data.getStringExtra("country")
                    locpostalCode = data.getStringExtra("postalCode")
                    locKnownName = data.getStringExtra("knownName")
                    strLatitude = data.getStringExtra("strLatitude")
                    strLongitue = data.getStringExtra("strLongitue")

                    strLocationAddress = ""
                    if (!locAddress.equals("")) {
                        strLocationAddress = locAddress!!
                    }
                    if (!locCity.equals("")) {
                        strLocationAddress = strLocationAddress + "," + locCity!!
                    }
                    if (!locState.equals("")) {
                        strLocationAddress = strLocationAddress + "," + locState!!
                    }
                    if (!locCountry.equals("")) {
                        strLocationAddress = strLocationAddress + "," + locCountry!!
                    }
                    if (!locpostalCode.equals("")) {
                        strLocationAddress = strLocationAddress + "," + locpostalCode!!
                    }

                    tie_Location!!.setText(strLocationAddress)
                } catch (e: Exception) {

                }

            }

        }

    }




}