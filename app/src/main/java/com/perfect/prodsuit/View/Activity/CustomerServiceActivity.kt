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
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.ServiceMediaRepository
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CustomerServiceActivity : AppCompatActivity()  , View.OnClickListener , ItemClickListener {

    val TAG : String = "CustomerServiceActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context


//    private var tabLayout : TabLayout? = null
//    var llMainDetail: LinearLayout? = null
//    var llBtn: LinearLayout? = null

//    //Complaints
//    var tie_Comp_FromDate: TextInputEditText? = null
//    var tie_Comp_ToDate: TextInputEditText? = null
//    var tie_Comp_FromTime: TextInputEditText? = null
//    var tie_Comp_ToTime: TextInputEditText? = null



    lateinit var serviceWarrantyViewModel: ServiceWarrantyViewModel
    lateinit var serviceWarrantyArrayList : JSONArray

    lateinit var serviceProductViewModel: ServiceProductViewModel
    lateinit var serviceProductArrayList : JSONArray

    lateinit var serviceSalesViewModel: ServiceSalesViewModel
    lateinit var serviceSalesArrayList : JSONArray

    private var tv_customerClick: TextView? = null
    private var tv_complaintClick: TextView? = null
    private var tv_contactClick: TextView? = null
    private var tv_requestedClick: TextView? = null
    private var tv_attendedClick: TextView? = null

    private var lnrHead_customer: LinearLayout? = null
    private var lnrHead_complaint: LinearLayout? = null
    private var lnrHead_contact: LinearLayout? = null
    private var lnrHead_requested: LinearLayout? = null
    private var lnrHead_attended: LinearLayout? = null

    // Customer Details
    private var tie_Date: TextInputEditText? = null
    private var tie_CustomerName: TextInputEditText? = null
    private var tie_MobileNo: TextInputEditText? = null
    private var tie_Address: TextInputEditText? = null
    private var tie_Priority: TextInputEditText? = null
    private var tie_Channel: TextInputEditText? = null
    private var tie_EmpOrMedia: TextInputEditText? = null

    private var til_CustomerName: TextInputLayout? = null
    private var til_MobileNo: TextInputLayout? = null
    private var til_Address: TextInputLayout? = null
    private var til_EmpOrMedia: TextInputLayout? = null
//    private var til_Address: TextInputLayout? = null
//    private var til_Address: TextInputLayout? = null

    //Complaints

    private var tie_Category: TextInputEditText? = null
    private var tie_Company: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null
    private var tie_Service: TextInputEditText? = null
    private var tie_Complaint: TextInputEditText? = null
    private var tie_Description: TextInputEditText? = null

    private var til_Company: TextInputLayout? = null
    private var til_Service: TextInputLayout? = null
    private var til_Complaint: TextInputLayout? = null

    //  CONTACT DETAILS

    private var tie_ContactNo: TextInputEditText? = null
    private var tie_Landmark: TextInputEditText? = null

    // Requested Visit Date and Time

    private var tie_FromDate: TextInputEditText? = null
    private var tie_ToDate: TextInputEditText? = null
    private var tie_FromTime: TextInputEditText? = null
    private var tie_ToTime: TextInputEditText? = null

    // ATTENDED DETAILS

    private var tie_Status: TextInputEditText? = null
    private var tie_Attendedby: TextInputEditText? = null

    var custDetailMode: String? = "0"
    var complaintMode: String? = "1"
    var contDetailMode: String? = "1"
    var requestedMode: String? = "1"
    var attDetailMode: String? = "1"

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    var dateMode = 0 // 0 = RegDate , 1 = Req FromDate , 2 = Req To date
    var timeMode = 0 // 0 = Req FromTime , 1 = Req ToTime
    var custDet = 0
    var priorityDet = 0
    var channelDet = 0
    var empMediaDet = 0
    var categoryDet = 0
    var companyDet = 0
    var productDet = 0
    var serviceDet = 0
    var complaintDet = 0
    var followUpAction = 0

    var SubModeSearch: String? = ""
    var strCustomer: String? = ""


    var custNameMode = 0 // 0 Search , 1 =Clear
    var employeeMode = 0 // 0 Employee , 1 Attended By

    lateinit var customersearchViewModel: CustomerSearchViewModel
    lateinit var customerArrayList: JSONArray
    lateinit var customerSort : JSONArray
    var dialogCustSearch: Dialog? = null

    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var prodPriorityArrayList : JSONArray
    lateinit var prodPrioritySort : JSONArray
    private var dialogProdPriority : Dialog? = null
    var recyProdPriority: RecyclerView? = null

    lateinit var commonViewModel: CommonViewModel
    lateinit var channelArrayList : JSONArray
    lateinit var channelSort : JSONArray
    private var dialogChannel : Dialog? = null
    var recyChannel: RecyclerView? = null

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

    lateinit var categoryArrayList : JSONArray
    lateinit var categorySort : JSONArray
    private var dialogCategory : Dialog? = null
    var recyCategory: RecyclerView? = null

    lateinit var companyArrayList : JSONArray
    lateinit var companySort : JSONArray
    private var dialogCompany : Dialog? = null
    var recyCompany: RecyclerView? = null

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

    var Customer_Type: String? = ""
    var ID_Customer: String? = ""
    var ID_Priority: String? = ""
    var ID_Channel: String? = ""
    var ID_Employee: String? = ""
    var ID_ServiceMedia: String? = ""
    var ID_Category: String? = ""
    var ID_Company: String? = ""
    var ID_Product: String? = ""
    var ID_Services: String? = ""
    var ID_ComplaintList: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""

    var ReqMode: String? = ""
    var SubMode: String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_customer_service)
        context = this@CustomerServiceActivity

        serviceWarrantyViewModel = ViewModelProvider(this).get(ServiceWarrantyViewModel::class.java)
        serviceProductViewModel = ViewModelProvider(this).get(ServiceProductViewModel::class.java)
        serviceSalesViewModel = ViewModelProvider(this).get(ServiceSalesViewModel::class.java)

        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        serviceComplaintViewModel = ViewModelProvider(this).get(ServiceComplaintViewModel::class.java)
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        serviceMediaViewModel = ViewModelProvider(this).get(ServiceMediaViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)

        setRegViews()


        custDetailMode = "0"
        hideViews()

        til_CustomerName!!.setEndIconOnClickListener {
           // finish()
            Config.disableClick(it)


            if (custNameMode == 0){
                custNameMode = 1
                custDet = 0
                SubModeSearch = "1"
                strCustomer = tie_CustomerName!!.text.toString()
                getCustomerSearch()
            }else{
                custNameMode = 0
                til_CustomerName!!.setEndIconDrawable(context.resources.getDrawable(R.drawable.search_24))
                tie_CustomerName!!.isEnabled = true
                tie_MobileNo!!.isEnabled = true
                tie_Address!!.isEnabled = true

                ID_Customer = ""
                Customer_Type = ""
                tie_CustomerName!!.setText("")
                tie_MobileNo!!.setText("")
                tie_Address!!.setText("")
            }
        }




    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        //Main Header
        tv_customerClick = findViewById<TextView>(R.id.tv_customerClick)
        tv_complaintClick = findViewById<TextView>(R.id.tv_complaintClick)
        tv_contactClick = findViewById<TextView>(R.id.tv_contactClick)
        tv_requestedClick = findViewById<TextView>(R.id.tv_requestedClick)
        tv_attendedClick = findViewById<TextView>(R.id.tv_attendedClick)

        lnrHead_customer = findViewById<LinearLayout>(R.id.lnrHead_customer)
        lnrHead_complaint = findViewById<LinearLayout>(R.id.lnrHead_complaint)
        lnrHead_contact = findViewById<LinearLayout>(R.id.lnrHead_contact)
        lnrHead_requested = findViewById<LinearLayout>(R.id.lnrHead_requested)
        lnrHead_attended = findViewById<LinearLayout>(R.id.lnrHead_attended)

        til_Address = findViewById<TextInputLayout>(R.id.til_Address)
        til_EmpOrMedia = findViewById<TextInputLayout>(R.id.til_EmpOrMedia)


        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)


        tv_customerClick!!.setOnClickListener(this)
        tv_complaintClick!!.setOnClickListener(this)
        tv_contactClick!!.setOnClickListener(this)
        tv_requestedClick!!.setOnClickListener(this)
        tv_attendedClick!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)


        // Customer Details
        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        tie_CustomerName = findViewById<TextInputEditText>(R.id.tie_CustomerName)
        tie_MobileNo = findViewById<TextInputEditText>(R.id.tie_MobileNo)
        tie_Address = findViewById<TextInputEditText>(R.id.tie_Address)
        tie_Priority = findViewById<TextInputEditText>(R.id.tie_Priority)
        tie_Channel = findViewById<TextInputEditText>(R.id.tie_Channel)
        tie_EmpOrMedia = findViewById<TextInputEditText>(R.id.tie_EmpOrMedia)

        til_CustomerName = findViewById<TextInputLayout>(R.id.til_CustomerName)
        til_MobileNo = findViewById<TextInputLayout>(R.id.til_MobileNo)


        tie_Date!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Channel!!.setOnClickListener(this)
        tie_EmpOrMedia!!.setOnClickListener(this)

        //Complaints

        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_Company = findViewById<TextInputEditText>(R.id.tie_Company)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)
        tie_Service = findViewById<TextInputEditText>(R.id.tie_Service)
        tie_Complaint = findViewById<TextInputEditText>(R.id.tie_Complaint)
        tie_Description = findViewById<TextInputEditText>(R.id.tie_Description)

        til_Company = findViewById<TextInputLayout>(R.id.til_Company)
        til_Service = findViewById<TextInputLayout>(R.id.til_Service)
        til_Complaint = findViewById<TextInputLayout>(R.id.til_Complaint)


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

        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_FromTime!!.setOnClickListener(this)
        tie_ToTime!!.setOnClickListener(this)

        // ATTENDED DETAILS
        tie_Status = findViewById<TextInputEditText>(R.id.tie_Status)
        tie_Attendedby = findViewById<TextInputEditText>(R.id.tie_Attendedby)

        tie_Status!!.setOnClickListener(this)
        tie_Attendedby!!.setOnClickListener(this)

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
//    private fun getWarranty() {
//        llBtn!!.visibility = View.GONE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_warranty, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//
//        var recyServiceWarranty = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceWarranty)
//        recyServiceWarranty.adapter = null
//
//        var warranty = 0
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                serviceWarrantyViewModel.getServiceWarranty(this)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//                            val jObject = JSONObject(msg)
//                            Log.e(TAG,"msg   182   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("WarrantyDetails")
//                                serviceWarrantyArrayList = jobjt.getJSONArray("WarrantyDetailsList")
//                                if (serviceWarrantyArrayList.length()>0){
//                                    if (warranty == 0){
//                                        warranty++
//                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
//                                        recyServiceWarranty!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapter = ServiceWarrantyAdapter(this@CustomerServiceActivity, serviceWarrantyArrayList)
//                                        recyServiceWarranty!!.adapter = adapter
//                                    }
//
//                                }
//                            } else {
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
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//
//    }
//
//    private fun getProduct() {
//        llBtn!!.visibility = View.GONE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_product, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//
//
//        var recyServiceProduct = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceProduct)
//        recyServiceProduct.adapter = null
//        var product = 0
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                serviceProductViewModel.getServiceProduct(this)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//                            val jObject = JSONObject(msg)
//                            Log.e(TAG,"msg   262   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("ProductHistoryDetails")
//                                serviceProductArrayList = jobjt.getJSONArray("ProductHistoryDetailsList")
//                                if (serviceProductArrayList.length()>0){
//                                    if (product == 0){
//                                        product++
//                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
//                                        recyServiceProduct!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapter = ServiceProductAdapter(this@CustomerServiceActivity, serviceProductArrayList)
//                                        recyServiceProduct!!.adapter = adapter
//                                    }
//
//                                }
//                            } else {
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
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//
//    }
//
//    private fun getSales() {
//        llBtn!!.visibility = View.GONE
//        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_sales, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//
//        var recyServiceSales = inflatedLayout.findViewById<FullLenghRecyclertview>(R.id.recyServiceSales)
//        recyServiceSales.adapter = null
//        var sales = 0
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                serviceSalesViewModel.getServiceSales(this)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//                            val jObject = JSONObject(msg)
//                            Log.e(TAG,"msg   335   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("SalesHistoryDetails")
//                                serviceSalesArrayList = jobjt.getJSONArray("SalesHistoryDetailsList")
//                                if (serviceSalesArrayList.length()>0){
//                                    if (sales == 0){
//                                        sales++
//                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
//                                        recyServiceSales!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapter = ServiceSalesAdapter(this@CustomerServiceActivity, serviceSalesArrayList)
//                                        recyServiceSales!!.adapter = adapter
//                                    }
//
//                                }
//                            } else {
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
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//
//    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tv_customerClick->{

                 custDetailMode = "0"
                 complaintMode  = "1"
                 contDetailMode = "1"
                 requestedMode  = "1"
                 attDetailMode  = "1"

                hideViews()
            }

            R.id.tv_complaintClick->{
                custDetailMode = "1"
                complaintMode  = "0"
                contDetailMode = "1"
                requestedMode  = "1"
                attDetailMode  = "1"

                hideViews()
            }

            R.id.tv_contactClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "0"
                requestedMode  = "1"
                attDetailMode  = "1"

                hideViews()
            }

            R.id.tv_requestedClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "1"
                requestedMode  = "0"
                attDetailMode  = "1"

                hideViews()
            }
            R.id.tv_attendedClick->{
                custDetailMode = "1"
                complaintMode  = "1"
                contDetailMode = "1"
                requestedMode  = "1"
                attDetailMode  = "0"

                hideViews()
            }

            R.id.tie_Date->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
            }

            R.id.tie_Priority->{
                Config.disableClick(v)
                priorityDet = 0
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

            R.id.tie_Category->{
                Config.disableClick(v)
                categoryDet = 0
                ReqMode = "66"
                SubMode = "20"
                getCategory(ReqMode!!,SubMode!!)

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
                productDet = 0
                ReqMode = "68"
                SubMode = ""
                getProduct(ReqMode!!,SubMode!!)

            }

            R.id.tie_Service->{
                Config.disableClick(v)
                serviceDet = 0
                ReqMode = "69"
                SubMode = ""
                if (!ID_Product.equals("")){
                    getServices(ReqMode!!,SubMode!!)
                }else{
                    Config.snackBars(context,v,"Select Product")
                }
            }

            R.id.tie_Complaint->{
                Config.disableClick(v)
                complaintDet = 0
                ReqMode = "70"
                SubMode = ""
                if (!ID_Product.equals("")){
                    getComplaints(ReqMode!!,SubMode!!)
                }else{
                    Config.snackBars(context,v,"Select Product")
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
            }

            R.id.btnReset->{
                //  til_Address!!.setError("You need to enter a name");
                tie_CustomerName!!.isEnabled = true
                tie_MobileNo!!.isEnabled = false
                tie_Address!!.isEnabled = false

                resetData()
            }


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
        ID_ServiceMedia = ""

        tie_Date!!.setText("")
        tie_CustomerName!!.setText("")
        tie_MobileNo!!.setText("")
        tie_Address!!.setText("")
        tie_Priority!!.setText("")
        tie_Channel!!.setText("")
        tie_EmpOrMedia!!.setText("")


        // Complaint
        ID_Category = ""
        ID_Company = ""
        ID_Product = ""
        ID_Services = ""
        ID_ComplaintList = ""

        tie_Category!!.setText("")
        tie_Company!!.setText("")
        tie_Product!!.setText("")
        tie_Service!!.setText("")
        tie_Complaint!!.setText("")
        tie_Description!!.setText("")

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

    }


    private fun hideViews() {

        lnrHead_customer!!.visibility = View.VISIBLE
        lnrHead_complaint!!.visibility = View.VISIBLE
        lnrHead_contact!!.visibility = View.VISIBLE
        lnrHead_requested!!.visibility = View.VISIBLE
        lnrHead_attended!!.visibility = View.VISIBLE

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

    private fun openBottomTime() {

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

                if (timeMode == 0){
                    tie_FromTime!!.setText(output)
                }
                if (timeMode == 1){
                    tie_ToTime!!.setText(output)
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
                customersearchViewModel.getCustomer(this, strCustomer!!, SubModeSearch!!)!!.observe(
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
                                        val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                        customerArrayList = jobjt.getJSONArray("CustomerDetails")

                                        if (customerArrayList.length() > 0) {
                                            Log.e(TAG, "msg   1052   " + msg)

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
            val adapter = CustomerAdapter(this@CustomerServiceActivity, customerSort)
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
                        if (textlength <= jsonObject.getString("CusName").length) {
                            if (jsonObject.getString("CusName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                customerSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllSort               7103    "+customerSort)
                    val adapter = CustomerAdapter(this@CustomerServiceActivity, customerSort)
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
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productPriorityViewModel.getProductPriority(this)!!.observe(
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

                                        val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                        prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                        if (prodPriorityArrayList.length()>0){

                                            productPriorityPopup(prodPriorityArrayList)


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

    private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

        try {

            dialogProdPriority = Dialog(this)
            dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdPriority!! .setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority = dialogProdPriority!! .findViewById(R.id.recyProdPriority) as RecyclerView
            val etsearch = dialogProdPriority!! .findViewById(R.id.etsearch) as EditText

            prodPrioritySort = JSONArray()
            for (k in 0 until prodPriorityArrayList.length()) {
                val jsonObject = prodPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodPrioritySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ProductPriorityAdapter(this@CustomerServiceActivity, prodPrioritySort)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@CustomerServiceActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodPrioritySort = JSONArray()

                    for (k in 0 until prodPriorityArrayList.length()) {
                        val jsonObject = prodPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("PriorityName").length) {
                            if (jsonObject.getString("PriorityName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                prodPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"prodPrioritySort               7103    "+prodPrioritySort)
                    val adapter = ProductPriorityAdapter(this@CustomerServiceActivity, prodPrioritySort)
                    recyProdPriority!!.adapter = adapter
                    adapter.setClickListener(this@CustomerServiceActivity)
                }
            })

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
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
                                if (categoryDet == 0){
                                    categoryDet++
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
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, serviceMediaArrayList)
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

    private fun getCategory(ReqMode : String,SubMode : String) {
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
                                if (categoryDet == 0){
                                    categoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1278   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        categoryArrayList = jobjt.getJSONArray("CommonPopupList")
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
                        if (textlength <= jsonObject.getString("Description").length) {
                            if (jsonObject.getString("Description")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
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
                serviceProductViewModel.getServiceProduct(this,ReqMode,SubMode,Customer_Type!!,ID_Customer!!)!!.observe(
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
                serviceViewModel.getServices(this,ReqMode!!,SubMode!!,ID_Product!!)!!.observe(
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
                serviceComplaintViewModel.getserviceComplaintData(this,ReqMode!!,SubMode!!,ID_Product!!)!!.observe(
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("customer")) {
            dialogCustSearch!!.dismiss()
            val jsonObject = customerSort.getJSONObject(position)
            tie_CustomerName!!.setText(jsonObject!!.getString("CusName"))
            ID_Customer = jsonObject.getString("ID_Customer")
            Customer_Type = jsonObject.getString("Customer_Type")

            tie_MobileNo!!.setText(jsonObject!!.getString("CusPhnNo"))
            tie_Address!!.setText(jsonObject!!.getString("CusAddress1"))

            tie_CustomerName!!.isEnabled = false
            tie_MobileNo!!.isEnabled = false
            tie_Address!!.isEnabled = false

          //  til_CustomerName!!.setEndIconDrawable(com.google.android.material.R.drawable.abc_ic_clear_material)
            til_CustomerName!!.setEndIconDrawable(context.resources.getDrawable(R.drawable.svg_clear))
//            if(SubModeSearch=="1") {
//                edt_customer!!.setText(jsonObject!!.getString("CusName"))
//            }
//            else
//            {
//                edt_customer!!.setText(jsonObject!!.getString("CusPhnNo"))
//            }
//
//            // custDetailMode = "1"
//            LeadGenerationActivity.Customer_Mode = "1"  // SEARCH
//            LeadGenerationActivity.ID_Customer = jsonObject.getString("ID_Customer")
//            LeadGenerationActivity.Customer_Type = jsonObject.getString("Customer_Type")
//            LeadGenerationActivity.Customer_Name = jsonObject.getString("CusName")
//            LeadGenerationActivity.Customer_Mobile = jsonObject.getString("CusPhnNo")
//            LeadGenerationActivity.Customer_Email = jsonObject.getString("CusEmail")
//            LeadGenerationActivity.Customer_Address1 = jsonObject.getString("CusAddress1")
//            LeadGenerationActivity.Customer_Address2 = jsonObject.getString("CusAddress2")
//
//            // llCustomerDetail!!.visibility = View.GONE
//            actv_namTitle!!.setText(jsonObject.getString("CusNameTitle"))
//            edtCustname!!.setText(jsonObject.getString("CusName"))
//            edtCustphone!!.setText(jsonObject.getString("CusPhnNo"))
//            edtCustemail!!.setText(jsonObject.getString("CusEmail"))
//            edtCustaddress1!!.setText(jsonObject.getString("CusAddress1"))
//            edtCustaddress2!!.setText(jsonObject.getString("CusAddress2"))
//            edtWhatsApp!!.setText(jsonObject.getString("CusMobileAlternate"))
//
//
//
//            LeadGenerationActivity.FK_Country = jsonObject.getString("CountryID")
//            LeadGenerationActivity.FK_States = jsonObject.getString("StatesID")
//            LeadGenerationActivity.FK_District = jsonObject.getString("DistrictID")
//            LeadGenerationActivity.FK_Post = jsonObject.getString("PostID")
//            LeadGenerationActivity.FK_Area = jsonObject.getString("FK_Area")
//
//            edtPincode!!.setText(jsonObject.getString("Pincode"))
//            edtCountry!!.setText(jsonObject.getString("CntryName"))
//            edtState!!.setText(jsonObject.getString("StName"))
//            edtDistrict!!.setText(jsonObject.getString("DtName"))
//            edtArea!!.setText(jsonObject.getString("Area"))
//            edtPost!!.setText(jsonObject.getString("PostName"))
//            edtPincode!!.setText(jsonObject.getString("Pincode"))


        }

        if (data.equals("prodpriority")){
            dialogProdPriority!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = prodPrioritySort.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            tie_Priority!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("channel")) {
            dialogChannel!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = channelSort.getJSONObject(position)
            Log.e(TAG,"Code   "+jsonObject.getString("Code"))
            ID_Channel = jsonObject.getString("Code")
            tie_Channel!!.setText(jsonObject.getString("Description"))

            tie_EmpOrMedia!!.setText("")

            if (ID_Channel.equals("5") || ID_Channel.equals("6")){
                til_EmpOrMedia!!.visibility  = View.VISIBLE
                til_EmpOrMedia!!.setHint(jsonObject.getString("Description"))
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
                tie_EmpOrMedia!!.setText(jsonObject.getString("EmpName"))
            }

            if (employeeMode == 1){
                ID_AttendedBy = jsonObject.getString("ID_Employee")
                tie_Attendedby!!.setText(jsonObject.getString("EmpName"))
            }



        }

        if (data.equals("category")) {

            dialogCategory!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = categorySort.getJSONObject(position)
            Log.e(TAG,"Code   "+jsonObject.getString("Code"))

            ID_Category = jsonObject.getString("Code")
            tie_Category!!.setText(jsonObject.getString("Description"))

            tie_Company!!.setText("")
            ID_Company = ""
            tie_Service!!.setText("")
            ID_Services = ""
            tie_Complaint!!.setText("")
            ID_ComplaintList = ""

            if (ID_Category.equals("1")){
                til_Company!!.visibility = View.GONE
                til_Service!!.visibility = View.VISIBLE
                til_Complaint!!.visibility = View.GONE
            }
            else if (ID_Category.equals("2")){
                til_Company!!.visibility = View.VISIBLE
                til_Service!!.visibility = View.VISIBLE
                til_Complaint!!.visibility = View.GONE
            }
            else if (ID_Category.equals("3")){
                til_Company!!.visibility = View.GONE
                til_Service!!.visibility = View.GONE
                til_Complaint!!.visibility = View.VISIBLE
            }
            else if (ID_Category.equals("4")){
                til_Company!!.visibility = View.VISIBLE
                til_Service!!.visibility = View.GONE
                til_Complaint!!.visibility = View.VISIBLE
            }



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
            tie_EmpOrMedia!!.setText(jsonObject.getString("Name"))

        }

    }




}