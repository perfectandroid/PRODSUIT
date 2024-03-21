package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.ServicePriorityAdapter
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.ServiceAssignDetailsViewModel
import com.perfect.prodsuit.Viewmodel.ServicePriorityViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DeliveryAssignActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    val TAG : String = "DeliveryAssignActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var tv_header: TextView? = null

    private var tv_TicketClick: TextView? = null
    private var tv_ServiceClick: TextView? = null
    private var tv_ProductClick: TextView? = null

    private var lnrHead_Ticket: LinearLayout? = null
    private var lnrHead_Service: LinearLayout? = null
    private var lnrHead_Product: LinearLayout? = null

    var ticketMode: String? = "0"
    var serviceMode: String? = "1"
    var productMode: String? = "1"
    var TicketDate: String? = ""

    // Ticket Information
    private var tv_Ticket: TextView? = null
    private var tv_LandMark: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_ContactNo: TextView? = null
    private var tv_Address: TextView? = null
    private var tv_Mobile: TextView? = null

    // Service Information
    private var tv_RequestedDate: TextView? = null
    private var tv_RequestedTime: TextView? = null
    private var tv_ProductName: TextView? = null
    private var tv_ProductComplaint: TextView? = null
    private var tv_Description: TextView? = null

    //Delivery Information
    private var tie_DeliveryDate: TextInputEditText? = null
    private var tie_DeliveryTime: TextInputEditText? = null
    private var tie_Priority: TextInputEditText? = null
    private var tie_VehicleDetails: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null

    private var til_DeliveryDate: TextInputLayout? = null
    private var til_DeliveryTime: TextInputLayout? = null
    private var til_Priority: TextInputLayout? = null
    private var til_Employee: TextInputLayout? = null

    var strDeliveryDate : String?= ""
    var strDeliveryTime : String?= ""
    var strVehicleDetails : String?= ""

    var dateMode = 0
    var timeMode = 0
    var priorityMode = 0
    var employee = 0

    lateinit var servicePriorityViewModel: ServicePriorityViewModel
    lateinit var servPriorityArrayList : JSONArray
    lateinit var servPrioritySort : JSONArray
    private var dialogServPriority : Dialog? = null
    var recyServPriority: RecyclerView? = null

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList : JSONArray
    lateinit var employeeSort : JSONArray
    private var dialogEmployee : Dialog? = null
    var recyEmployee: RecyclerView? = null

    var serAssignCount = 0
    lateinit var serviceAssignDetailViewModel: ServiceAssignDetailsViewModel
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var ID_Priority : String?= ""
    var ID_Employee : String?= ""

    var strTicket: String? = ""
    var strLandmark: String? = ""
    var strCustomer: String? = ""
    var strContactNo: String? = ""
    var strAddress: String? = ""
    var strMobile: String? = ""
    var strReqDate: String? = ""
    var strReqTime: String? = ""
    var strProductname: String? = ""
    var strProductComplaint: String? = ""
    var strProductDescription: String? = ""
    var strPriorityName: String? = ""
    var strPEmployeeName: String? = ""

    var PickDelMode: String? = ""
    var modeWisePDHeader: String? = ""
    var modeWisePDInformation: String? = ""
    var modeWisePDDate: String? = ""
    var modeWisePDTime: String? = ""


    var btnSave: Button? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_delivery_assign)
        context = this@DeliveryAssignActivity

        serviceAssignDetailViewModel = ViewModelProvider(this).get(ServiceAssignDetailsViewModel::class.java)
        servicePriorityViewModel = ViewModelProvider(this).get(ServicePriorityViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        PickDelMode = intent.getStringExtra("PickDelMode")
        ID_CustomerServiceRegister = intent.getStringExtra("ID_CustomerServiceRegister")

        setRegViews()
        ticketMode = "0"
        hideViews()

        serAssignCount = 0
        getServiceAssignDetails()


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById<TextView>(R.id.tv_header)

        tv_TicketClick = findViewById<TextView>(R.id.tv_TicketClick)
        tv_ServiceClick = findViewById<TextView>(R.id.tv_ServiceClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)

        tv_TicketClick!!.setOnClickListener(this)
        tv_ServiceClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)

        lnrHead_Ticket = findViewById<LinearLayout>(R.id.lnrHead_Ticket)
        lnrHead_Service = findViewById<LinearLayout>(R.id.lnrHead_Service)
        lnrHead_Product = findViewById<LinearLayout>(R.id.lnrHead_Product)

        // Ticket Information

        tv_Ticket = findViewById<TextView>(R.id.tv_Ticket)
        tv_LandMark = findViewById<TextView>(R.id.tv_LandMark)
        tv_Customer = findViewById<TextView>(R.id.tv_Customer)
        tv_ContactNo = findViewById<TextView>(R.id.tv_ContactNo)
        tv_Address = findViewById<TextView>(R.id.tv_Address)
        tv_Mobile = findViewById<TextView>(R.id.tv_Mobile)

        // Service Information

        tv_RequestedDate = findViewById<TextView>(R.id.tv_RequestedDate)
        tv_RequestedTime = findViewById<TextView>(R.id.tv_RequestedTime)
        tv_ProductName = findViewById<TextView>(R.id.tv_ProductName)
        tv_ProductComplaint = findViewById<TextView>(R.id.tv_ProductComplaint)
        tv_Description = findViewById<TextView>(R.id.tv_Description)


        //Delivery Information

        tie_DeliveryDate = findViewById<TextInputEditText>(R.id.tie_DeliveryDate)
        tie_DeliveryTime = findViewById<TextInputEditText>(R.id.tie_DeliveryTime)
        tie_Priority = findViewById<TextInputEditText>(R.id.tie_Priority)
        tie_VehicleDetails = findViewById<TextInputEditText>(R.id.tie_VehicleDetails)
        tie_Employee = findViewById<TextInputEditText>(R.id.tie_Employee)

        til_DeliveryDate = findViewById<TextInputLayout>(R.id.til_DeliveryDate)
        til_DeliveryTime = findViewById<TextInputLayout>(R.id.til_DeliveryTime)
        til_Priority = findViewById<TextInputLayout>(R.id.til_Priority)
        til_Employee = findViewById<TextInputLayout>(R.id.til_Employee)

        tie_DeliveryDate!!.setOnClickListener(this)
        tie_DeliveryTime!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_VehicleDetails!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)

        btnSave = findViewById<Button>(R.id.btnSave)
        btnSave!!.setOnClickListener(this)



        modeWiseLabel()


        til_DeliveryDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_DeliveryTime!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Priority!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        onTextChangedValues()
        getCurrentdateTime()


    }

    private fun modeWiseLabel() {
        if (PickDelMode.equals("1")){

            modeWisePDInformation = "Delivery Information"
            modeWisePDDate = "Delivery Date"
            modeWisePDTime = "Delivery Time"

            tv_header!!.text = "Delivery Assign"
            tv_ProductClick!!.text = "Delivery Information"
            til_DeliveryDate!!.hint = "Delivery Date *"
            til_DeliveryTime!!.hint = "Delivery Time *"
        }
        if (PickDelMode.equals("2")){

            modeWisePDInformation = "PickUp Information"
            modeWisePDDate = "PickUp Date"
            modeWisePDTime = "PickUp Time"

            tv_header!!.text = "PickUp Assign"
            tv_ProductClick!!.text = "PickUp Information"
            til_DeliveryDate!!.hint = "PickUp Date *"
            til_DeliveryTime!!.hint = "PickUp Time *"
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tv_TicketClick->{
                ticketMode = "0"
                serviceMode  = "1"
                productMode = "1"
                hideViews()
            }

            R.id.tv_ServiceClick->{
                ticketMode = "1"
                serviceMode  = "0"
                productMode = "1"
                hideViews()
            }

            R.id.tv_ProductClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
                hideViews()
            }

            R.id.tie_DeliveryDate->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
            }
            R.id.tie_DeliveryTime->{
                timeMode = 0
                openBottomTime()
            }
            R.id.tie_Priority->{
                priorityMode = 0
                getServicePriority()
            }

            R.id.tie_Employee->{
                Config.disableClick(v)
                employee = 0
                getEmployee()

            }
            R.id.btnSave->{
                Config.disableClick(v)
                saveValidation(v)
            }

        }
    }


    private fun getServiceAssignDetails() {
        var ReqMode = "76"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignDetailViewModel.getServiceAssignDetail(
                    this,
                    ReqMode,
                    ID_CustomerServiceRegister!!,
                    FK_CustomerserviceregisterProductDetails!!,
                    TicketDate

                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.e(TAG,"ServiceAssignDetails  2751   "+msg)

                                if (serAssignCount == 0){
                                    serAssignCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAssignDetails")
                                        Log.e(TAG,"FromDate  2752   "+jobjt.getString("FromDate"))
                                        //     var saDetailArrayList = jobjt.getJSONArray("EmployeeRoleDetailsList")
                                        //     Log.e(TAG,"saDetailArrayList  2753   "+saDetailArrayList)

                                        //  rrrrrrrr

                                        strTicket = jobjt.getString("Ticket")
                                        strLandmark = jobjt.getString("Landmark")
                                        strCustomer = jobjt.getString("Customer")
                                        strContactNo = jobjt.getString("OtherMobile")
                                        strAddress = jobjt.getString("Address")
                                        strMobile = jobjt.getString("Mobile")
                                        strReqDate = jobjt.getString("FromDate")+" - "+jobjt.getString("ToDate")
                                        strReqTime = jobjt.getString("FromTime")+" - "+jobjt.getString("ToTime")
                                        strProductname = jobjt.getString("Productname")
                                        strProductComplaint = jobjt.getString("ProductComplaint")
                                        strProductDescription = jobjt.getString("ProductDescription")
                                        strPriorityName = jobjt.getString("PriorityName")


                                        tv_Ticket!!.setText(""+strTicket)
                                        tv_LandMark!!.setText(""+strLandmark)
                                        tv_Customer!!.setText(""+strCustomer)
                                        tv_ContactNo!!.setText(""+strContactNo)
                                        tv_Address!!.setText(""+strAddress)
                                        tv_Mobile!!.setText(""+strMobile)

                                        // Service Information

                                        tv_RequestedDate!!.setText(""+strReqDate)
                                        tv_RequestedTime!!.setText(""+strReqTime)

                                        // Product Details
                                        tv_ProductName!!.setText(""+strProductname)
                                        tv_ProductComplaint!!.setText(""+strProductComplaint)
                                        tv_Description!!.setText(""+strProductDescription)

                                        ID_Priority = jobjt.getString("Priority")
                                        tie_Priority!!.setText(""+strPriorityName)

                                        ticketMode = "0"
                                        hideViews()


//                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
//                                        if (employeeArrayList.length()>0){
//
//                                            employeePopup(employeeArrayList)
//
//
//                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@DeliveryAssignActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            finish()
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
                        }catch (e:Exception){
//                            Toast.makeText(
//                                applicationContext,
//                                ""+Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                            val builder = AlertDialog.Builder(
                                this@DeliveryAssignActivity,
                                R.style.MyDialogTheme
                            )
                            builder.setMessage(Config.PLEASE_TRY_AGAIN)
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                finish()
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun getCurrentdateTime() {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            tie_DeliveryDate!!.setText(""+sdfDate1.format(newDate))
            strDeliveryDate = sdfDate2.format(newDate)

            tie_DeliveryTime!!.setText(""+sdfTime1.format(newDate))
            strDeliveryTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
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
                tie_DeliveryDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                strDeliveryDate = ""+strYear+"-"+strMonth+"-"+strDay
//                if (dateMode == 0){
//                    tie_VisitDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
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

                tie_DeliveryTime!!.setText(output)
                val sdfTime2 = SimpleDateFormat("HH:mm",Locale.US)
                strDeliveryTime = inputDateFormat.format(date)
//                strVisitTime = input

//                val sdfTime = SimpleDateFormat("h:mm a")
//                val outputTimeFormat = SimpleDateFormat("HH:mm:ss")
//                var fromT: Date? = null
//                fromT = sdfTime.parse(output)
//                strVisitTime = outputTimeFormat.format(fromT)


//                if (timeMode == 0){
//                    tie_VisitTime!!.setText(output)
//                }
//                if (timeMode == 1){
//                    tie_ToTime!!.setText(output)
//                }


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

    private fun getServicePriority() {
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
                                if (priorityMode == 0){
                                    priorityMode++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   353   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        servPriorityArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (servPriorityArrayList.length()>0){

                                            servicePriorityPopup(servPriorityArrayList)


                                        }

                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@DeliveryAssignActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun servicePriorityPopup(servPriorityArrayList: JSONArray) {

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


            val lLayout = GridLayoutManager(this@DeliveryAssignActivity, 1)
            recyServPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServicePriorityAdapter(this@DeliveryAssignActivity, servPrioritySort)
            recyServPriority!!.adapter = adapter
            adapter.setClickListener(this@DeliveryAssignActivity)


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
                    val adapter = ServicePriorityAdapter(this@DeliveryAssignActivity, servPrioritySort)
                    recyServPriority!!.adapter = adapter
                    adapter.setClickListener(this@DeliveryAssignActivity)
                }
            })

            dialogServPriority!!.show()
            dialogServPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getEmployee() {
        //   var employee = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employee == 0){
                                    employee++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length()>0){

                                            employeePopup(employeeArrayList)


                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@DeliveryAssignActivity,
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
                        }catch (e:Exception){
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!! .setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!! .findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!! .findViewById(R.id.etsearch) as EditText

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@DeliveryAssignActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@DeliveryAssignActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@DeliveryAssignActivity)

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
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+employeeSort)
                    val adapter = EmployeeAdapter(this@DeliveryAssignActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@DeliveryAssignActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun hideViews() {
        lnrHead_Ticket!!.visibility = View.VISIBLE
        lnrHead_Service!!.visibility = View.VISIBLE
        lnrHead_Product!!.visibility = View.VISIBLE

        if (ticketMode.equals("1")) {
            lnrHead_Ticket!!.visibility = View.GONE
        }
        if (serviceMode.equals("1")) {
            lnrHead_Service!!.visibility = View.GONE
        }
        if (productMode.equals("1")) {
            lnrHead_Product!!.visibility = View.GONE
        }

    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("servpriority")){
            dialogServPriority!!.dismiss()
            val jsonObject = servPrioritySort.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("Code"))
            ID_Priority = jsonObject.getString("Code")
            tie_Priority!!.setText(jsonObject.getString("Description"))
            strPriorityName = jsonObject.getString("Description")

        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
//            val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))
            strPEmployeeName = jsonObject.getString("EmpName")


        }

    }

    private fun onTextChangedValues() {


        // customer Details
        tie_DeliveryDate!!.addTextChangedListener(watcher)
        tie_DeliveryTime!!.addTextChangedListener(watcher)
        tie_Priority!!.addTextChangedListener(watcher)
        tie_Employee!!.addTextChangedListener(watcher);


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
                editable === tie_DeliveryDate!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_DeliveryDate!!.text.toString().equals("")){
                        til_DeliveryDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_DeliveryDate!!.isErrorEnabled = false
                        til_DeliveryDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_DeliveryTime!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_DeliveryTime!!.text.toString().equals("")){
                        til_DeliveryTime!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_DeliveryTime!!.isErrorEnabled = false
                        til_DeliveryTime!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
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

                editable === tie_Employee!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    //  til_CompCategory!!.isErrorEnabled = false
                    if (tie_Employee!!.text.toString().equals("")){
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Employee!!.isErrorEnabled = false
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }


            }

        }
    }

    private fun saveValidation(v: View) {

        strVehicleDetails = tie_VehicleDetails!!.text.toString()

        if (strDeliveryDate.equals("")){
            til_DeliveryDate!!.setError("Select "+modeWisePDDate);
            til_DeliveryDate!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"

            hideViews()
        }
        else if (strDeliveryTime.equals("")){
            til_DeliveryTime!!.setError("Select "+modeWisePDTime);
            til_DeliveryTime!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"

            hideViews()
        }
        else if (ID_Priority.equals("")){
            til_Priority!!.setError("Select Priority");
            til_Priority!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"
            hideViews()
        }
        else if (ID_Employee.equals("")){

            til_Employee!!.setError("Select Employee");
            til_Employee!!.setErrorIconDrawable(null)
            ticketMode = "1"
            serviceMode  = "1"
            productMode = "0"

            hideViews()

        }
        else{


            Log.e(TAG,"saveValidation  887"
                    +"\n"+"strDeliveryDate      "+strDeliveryDate
                    +"\n"+"strDeliveryTime      "+strDeliveryTime
                    +"\n"+"ID_Priority          "+ID_Priority
                    +"\n"+"strVehicleDetails    "+strVehicleDetails
                    +"\n"+"ID_Employee          "+ID_Employee)

            serviceDeliveryConfirm()
        }

    }

    private fun serviceDeliveryConfirm() {
        try {

            val dialogConfirmPop = Dialog(this)
            dialogConfirmPop!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirmPop!!.setContentView(R.layout.service_delivery_confirmation)
            dialogConfirmPop!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


            ////////////////////////

            val ll_Ticket = dialogConfirmPop!!.findViewById(R.id.ll_Ticket) as LinearLayout
            val ll_LandMark = dialogConfirmPop!!.findViewById(R.id.ll_LandMark) as LinearLayout
            val ll_Customer = dialogConfirmPop!!.findViewById(R.id.ll_Customer) as LinearLayout
            val ll_ContactNo = dialogConfirmPop!!.findViewById(R.id.ll_ContactNo) as LinearLayout
            val ll_Address = dialogConfirmPop!!.findViewById(R.id.ll_Address) as LinearLayout
            val ll_Mobile = dialogConfirmPop!!.findViewById(R.id.ll_Mobile) as LinearLayout



            val ll_ReqDate = dialogConfirmPop!!.findViewById(R.id.ll_ReqDate) as LinearLayout
            val ll_ReqTime = dialogConfirmPop!!.findViewById(R.id.ll_ReqTime) as LinearLayout
            val ll_ProductName = dialogConfirmPop!!.findViewById(R.id.ll_ProductName) as LinearLayout
            val ll_ProductComplaint = dialogConfirmPop!!.findViewById(R.id.ll_ProductComplaint) as LinearLayout
            val ll_Description = dialogConfirmPop!!.findViewById(R.id.ll_Description) as LinearLayout
            val ll_VisitDate = dialogConfirmPop!!.findViewById(R.id.ll_VisitDate) as LinearLayout
            val ll_VisitTime = dialogConfirmPop!!.findViewById(R.id.ll_VisitTime) as LinearLayout
            val ll_Priority = dialogConfirmPop!!.findViewById(R.id.ll_Priority) as LinearLayout
            val ll_Remark = dialogConfirmPop!!.findViewById(R.id.ll_Remark) as LinearLayout
            val ll_Employee = dialogConfirmPop!!.findViewById(R.id.ll_Employee) as LinearLayout


            ////////////////////////


            val tvp_Information = dialogConfirmPop!!.findViewById(R.id.tvp_Information) as TextView
            val tvp_VisitDateLabel = dialogConfirmPop!!.findViewById(R.id.tvp_VisitDateLabel) as TextView
            val tvp_VisitTimeLabel = dialogConfirmPop!!.findViewById(R.id.tvp_VisitTimeLabel) as TextView

            val tvp_Ticket = dialogConfirmPop!!.findViewById(R.id.tvp_Ticket) as TextView
            val tvp_LandMark = dialogConfirmPop!!.findViewById(R.id.tvp_LandMark) as TextView
            val tvp_Customer = dialogConfirmPop!!.findViewById(R.id.tvp_Customer) as TextView
            val tvp_ContactNo = dialogConfirmPop!!.findViewById(R.id.tvp_ContactNo) as TextView
            val tvp_Address = dialogConfirmPop!!.findViewById(R.id.tvp_Address) as TextView
            val tvp_Mobile = dialogConfirmPop!!.findViewById(R.id.tvp_Mobile) as TextView
            val tvp_ReqDate = dialogConfirmPop!!.findViewById(R.id.tvp_ReqDate) as TextView
            val tvp_ReqTime = dialogConfirmPop!!.findViewById(R.id.tvp_ReqTime) as TextView
            val tvp_ProductName = dialogConfirmPop!!.findViewById(R.id.tvp_ProductName) as TextView
            val tvp_ProductComplaint = dialogConfirmPop!!.findViewById(R.id.tvp_ProductComplaint) as TextView
            val tvp_Description = dialogConfirmPop!!.findViewById(R.id.tvp_Description) as TextView
            val tvp_VisitDate = dialogConfirmPop!!.findViewById(R.id.tvp_VisitDate) as TextView
            val tvp_VisitTime = dialogConfirmPop!!.findViewById(R.id.tvp_VisitTime) as TextView
            val tvp_Priority = dialogConfirmPop!!.findViewById(R.id.tvp_Priority) as TextView
            val tvp_Remark = dialogConfirmPop!!.findViewById(R.id.tvp_Remark) as TextView
            val tvp_Employee = dialogConfirmPop!!.findViewById(R.id.tvp_Employee) as TextView


            ////////////////////////////

            if (strTicket.equals("")) {
                ll_Ticket!!.visibility = View.GONE
            }

            if (strLandmark.equals("")) {
                ll_LandMark!!.visibility = View.GONE
            }
            if (strCustomer.equals("")) {
                ll_Customer!!.visibility = View.GONE
            }
            if (strContactNo.equals("")) {
                ll_ContactNo!!.visibility = View.GONE
            }
            if (strAddress.equals("")) {
                ll_Address!!.visibility = View.GONE
            }
            if (strMobile.equals("")) {
                ll_Mobile!!.visibility = View.GONE
            }
            if (strReqDate.equals("")) {
                ll_ReqDate!!.visibility = View.GONE
            }
            if (strReqTime.equals("")) {
                ll_ReqTime!!.visibility = View.GONE
            }
            if (strProductname.equals("")) {
                ll_ProductName!!.visibility = View.GONE
            }
            if (strProductComplaint.equals("")) {
                ll_ProductComplaint!!.visibility = View.GONE
            }
            if (strProductDescription.equals("")) {
                ll_Description!!.visibility = View.GONE
            }
            if (strDeliveryDate.equals("")) {
                ll_VisitDate!!.visibility = View.GONE
            }
            if (strDeliveryTime.equals("")) {
                ll_VisitTime!!.visibility = View.GONE
            }
            if (strPriorityName.equals("")) {
                ll_Priority!!.visibility = View.GONE
            }
            if (strVehicleDetails.equals("")) {
                ll_Remark!!.visibility = View.GONE
            }
            if (ID_Employee.equals("")) {
                ll_Employee!!.visibility = View.GONE
            }

            tvp_Information!!.setText(modeWisePDInformation)
            tvp_VisitDateLabel!!.setText(modeWisePDDate)
            tvp_VisitTimeLabel!!.setText(modeWisePDTime)

            tvp_Ticket!!.setText(strTicket)
            tvp_LandMark!!.setText(strLandmark)
            tvp_Customer!!.setText(strCustomer)
            tvp_ContactNo!!.setText(strContactNo)
            tvp_Address!!.setText(strAddress)
            tvp_Mobile!!.setText(strMobile)
            tvp_ReqDate!!.setText(strReqDate)
            tvp_ReqTime!!.setText(strReqTime)
            tvp_ProductName!!.setText(strProductname)
            tvp_ProductComplaint!!.setText(strProductComplaint)
            tvp_Description!!.setText(strProductDescription)
            tvp_VisitDate!!.setText(tie_DeliveryDate!!.text.toString())
            tvp_VisitTime!!.setText(tie_DeliveryTime!!.text.toString())
            tvp_Priority!!.setText(strPriorityName)
            tvp_Remark!!.setText(strVehicleDetails)
            tvp_Employee!!.setText(strPEmployeeName)


            ///////////


            val btnCancel = dialogConfirmPop!!.findViewById(R.id.btnCancel) as Button
            val btnOk = dialogConfirmPop!!.findViewById(R.id.btnOk) as Button

            Log.e(TAG, "")


            btnCancel.setOnClickListener {
                dialogConfirmPop.dismiss()
            }

            btnOk.setOnClickListener {
                dialogConfirmPop.dismiss()

            }


            dialogConfirmPop!!.show()
            dialogConfirmPop!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}