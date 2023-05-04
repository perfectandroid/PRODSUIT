package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.PickupDeliStandByproductAdapter
import com.perfect.prodsuit.View.Adapter.PickupDeliveryProductAdapter
import com.perfect.prodsuit.View.Adapter.ProdInformationAdapter
import com.perfect.prodsuit.View.Adapter.ProductDetailAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PickUpAndDeliveryUpdateActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListener {

    var TAG  ="PickUpAndDeliveryUpdateActivity"
    lateinit var context                        : Context
    private var progressDialog                  : ProgressDialog? = null

    private var tv_header                       : TextView? = null
    private var SubMode                         : String?   = ""
    private var productName                     : String?   = ""
    private var productId                       : String?   = ""
    private var pos                             : Int      = 0

    private var tv_TicketDetailsClick           : TextView? = null
    private var tv_CustomerDetailsClick         : TextView? = null
    private var tv_ProductDetailsClick          : TextView? = null
    private var tv_PickDeliveryInformationClick : TextView? = null
    private var tv_ProductInformationClick      : TextView? = null
    private var tv_TicketNumber                 : TextView? = null
    private var tv_RegisteredOn                 : TextView? = null
    private var tv_AddressLine1                 : TextView? = null
    private var tv_AddressLine2                 : TextView? = null
    private var tv_Post                         : TextView? = null
    private var tv_Area                         : TextView? = null
    private var tv_District                     : TextView? = null
    private var tv_State                        : TextView? = null
    private var tv_CustomerName                 : TextView? = null
    private var tv_tvProduct                    : TextView? = null
    private var tv_tvCategory                   : TextView? = null

    private var llTicketDetails                 : LinearLayout? = null
    private var llCustomerDetails               : LinearLayout? = null
    private var llProductDetails                : LinearLayout? = null
    private var llPickDeliveryInformation       : LinearLayout? = null

    var TicketDetailsMode                       : String? = "1"
    var CustomerDetailsMode                     : String? = "1"
    var ProductDetailsMode                      : String? = "1"
    var PickDeliveryInformationMode             : String? = "1"
    var ProductInformationMode                  : String? = "1"
    var productinfodetailscount                           = 0
    var ID_ProductDelivery                      : String? = ""


    private var til_PickDeliveryDate           : TextInputLayout? = null
    private var til_PickDeliveryTime           : TextInputLayout? = null

    private var tie_PickDeliveryDate           : TextInputEditText? = null
    private var tie_PickDeliveryTime           : TextInputEditText? = null
    private var tie_Remark                     : TextInputEditText? = null

    private var PRODUCT_INFORM                 : Int? = 100

    var prodInformationCount                          = 0
    var proddetail                                    = 0
    var productposition                               = ""
    var ID_Category                                   = "0"
    lateinit var ProdInformationViewModel             : PaymentMethodViewModel
    lateinit var prodInformationArrayList             : JSONArray
    lateinit var prodInformationArrayList1            : JSONArray
    lateinit var prodDetailArrayList                  : JSONArray
    lateinit var addproductDetailArrayList               : JSONArray
    lateinit var prodaddingArraylist                  : JSONArray
    lateinit var pickupdeliveryupdatedetailsviewmodel : PickUpDeliveryUpdateDetailsViewModel
    lateinit var pickupDeliveryUpdateDetailsArrayList : JSONArray
    lateinit var prodDetailSort                       : JSONArray
    lateinit var productinfodetailsviewmodel          : ProductInfoDetailsViewModel
    lateinit var productDetailViewModel               : ProductDetailViewModel
    lateinit var pickupdeliveryproductviewmodel       : PickupDeliveryProductViewModel
    lateinit var pickupdelistandbyproductviewmodel    : PickupDeliStandByProductViewModel
    private  var dialogProdInformation                : Dialog?       = null
    private  var dialogProdDet                        : Dialog?       = null
    var recyProdInformation                           : RecyclerView? = null
    var recyProdDetail                                : RecyclerView? = null
    var tv_Pop_StandByTotal                           : TextView?     = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery_update)
        context = this@PickUpAndDeliveryUpdateActivity

        pickupdeliveryupdatedetailsviewmodel = ViewModelProvider(this).get(PickUpDeliveryUpdateDetailsViewModel::class.java)
        productinfodetailsviewmodel          = ViewModelProvider(this).get(ProductInfoDetailsViewModel::class.java)
        productDetailViewModel               = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        pickupdeliveryproductviewmodel       = ViewModelProvider(this).get(PickupDeliveryProductViewModel::class.java)
        pickupdelistandbyproductviewmodel    = ViewModelProvider(this).get(PickupDeliStandByProductViewModel::class.java)

        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
        ID_ProductDelivery = intent.getStringExtra("ID_ProductDelivery")
        Log.e(TAG,"000111222255  "+ID_ProductDelivery)
        setRegViews()
        getUpdateStstusDetails()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header                       = findViewById(R.id.tv_header)
        tv_TicketDetailsClick           = findViewById<TextView>(R.id.tv_TicketDetailsClick)
        tv_CustomerDetailsClick         = findViewById<TextView>(R.id.tv_CustomerDetailsClick)
        tv_ProductDetailsClick          = findViewById<TextView>(R.id.tv_ProductDetailsClick)
        tv_PickDeliveryInformationClick = findViewById<TextView>(R.id.tv_PickDeliveryInformationClick)
        tv_ProductInformationClick      = findViewById<TextView>(R.id.tv_ProductInformationClick)

        tv_TicketNumber = findViewById(R.id.tv_TicketNumber)
        tv_RegisteredOn = findViewById(R.id.tv_RegisteredOn)
        tv_CustomerName = findViewById(R.id.tv_CustomerName)
        tv_AddressLine1 = findViewById(R.id.tv_AddressLine1)
        tv_AddressLine2 = findViewById(R.id.tv_AddressLine2)
        tv_Area         = findViewById(R.id.tv_Area)
        tv_Post         = findViewById(R.id.tv_Post)
        tv_District     = findViewById(R.id.tv_District)
        tv_State        = findViewById(R.id.tv_State)
        tv_tvProduct    = findViewById(R.id.tv_tvProduct)
        tv_tvCategory   = findViewById(R.id.tv_tvCategory)

        llTicketDetails           = findViewById<LinearLayout>(R.id.llTicketDetails)
        llCustomerDetails         = findViewById<LinearLayout>(R.id.llCustomerDetails)
        llProductDetails          = findViewById<LinearLayout>(R.id.llProductDetails)
        llPickDeliveryInformation = findViewById<LinearLayout>(R.id.llPickDeliveryInformation)

        til_PickDeliveryDate = findViewById<TextInputLayout>(R.id.til_PickDeliveryDate)
        til_PickDeliveryTime = findViewById<TextInputLayout>(R.id.til_PickDeliveryTime)

        tie_PickDeliveryDate = findViewById<TextInputEditText>(R.id.tie_PickDeliveryDate)
        tie_PickDeliveryTime = findViewById<TextInputEditText>(R.id.tie_PickDeliveryTime)
        tie_Remark           = findViewById<TextInputEditText>(R.id.tie_Remark)

        tv_TicketDetailsClick!!.setOnClickListener(this)
        tv_CustomerDetailsClick!!.setOnClickListener(this)
        tv_ProductDetailsClick!!.setOnClickListener(this)
        tv_PickDeliveryInformationClick!!.setOnClickListener(this)
        tv_ProductInformationClick!!.setOnClickListener(this)

        tie_PickDeliveryDate!!.setOnClickListener(this)
        tie_PickDeliveryTime!!.setOnClickListener(this)

        setLabel()
       // onTextChangedValues()
        getCurrentDate()
//        getDetailList()

    }

    private fun getUpdateStstusDetails() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdeliveryupdatedetailsviewmodel.getPickUpDeliveryUpdateDetails(this,SubMode!!,ID_ProductDelivery!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (prodInformationCount == 0){
                                    prodInformationCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt    = jObject.getJSONObject("UpdateDeliverStatusDetails")
                                        val adres2   = jobjt.getString("Address2")
                                        val post     = jobjt.getString("Post")
                                        val Address1 = jobjt.getString("Address1")
                                        val Area     = jobjt.getString("Area")
                                        val District = jobjt.getString("District")
                                        val State    = jobjt.getString("State")

                                        tv_TicketNumber!!.setText(jobjt.getString("CSRTickno"))
                                        tv_RegisteredOn!!.setText(jobjt.getString("CSRRegDate"))
                                        tv_CustomerName!!.setText(jobjt.getString("Customer"))
                                        tv_tvProduct!!.setText(jobjt.getString("Product"))
                                        tv_tvCategory!!.setText(jobjt.getString("Category"))

                                        if (Address1!!.equals("")){
                                            tv_AddressLine1!!.visibility = View.GONE
                                        }else{
                                            tv_AddressLine1!!.setText(Address1)
                                        }
                                        if (adres2.equals("")){
                                            tv_AddressLine2!!.visibility = View.GONE
                                        }else{
                                            tv_AddressLine2!!.setText(adres2)
                                        }
                                        if (post.equals("")){
                                            tv_Post!!.visibility = View.GONE
                                        }else{
                                            tv_Post!!.setText(post)
                                        }
                                        if (Area.equals("")){
                                            tv_Area!!.visibility = View.GONE
                                        }else{
                                            tv_Area!!.setText(Area)
                                        }
                                        if (District.equals("")){
                                            tv_District!!.visibility = View.GONE
                                        }else{
                                            tv_District!!.setText(District)
                                        }
                                        if(State.equals("")){
                                            tv_State!!.visibility = View.GONE
                                        }else{
                                            tv_State!!.setText(State)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryUpdateActivity,
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
                        }catch (e :Exception){
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



    private fun setLabel() {

        if (SubMode.equals("1")){
            tv_header!!.text = "Pick up"
            tv_PickDeliveryInformationClick!!.text = "Pickup Information"
            til_PickDeliveryDate!!.hint = "Pick Up Date *"
            til_PickDeliveryTime!!.hint = "Pick up Time *"

        }
        if (SubMode.equals("2")){
            tv_header!!.text = "Delivery"
            tv_PickDeliveryInformationClick!!.text = "Delivery Information"
            til_PickDeliveryDate!!.hint = "Delivery Date *"
            til_PickDeliveryTime!!.hint = "Delivery Time *"
        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tv_TicketDetailsClick->{
                llTicketDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "0"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_CustomerDetailsClick->{
                llCustomerDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "0"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_ProductDetailsClick->{
                llProductDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "0"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_PickDeliveryInformationClick->{
                llPickDeliveryInformation!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "0"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_ProductInformationClick->{
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "0"
                hideViews()

//                val intent = Intent(this@PickUpAndDeliveryUpdateActivity, ProductInformationActivity::class.java)
//                startActivityForResult(intent, PRODUCT_INFORM!!);

                productinfodetailscount = 0
                getProductInformationDetails(v)
//                ProductInformationBottom(v)

            }

            R.id.tie_PickDeliveryDate->{
                Config.disableClick(v)
               // dateMode = 0
                openBottomDate()
            }
            R.id.tie_PickDeliveryTime->{
                Config.disableClick(v)
               // timeMode = 0
                openBottomTime()
            }

        }
    }


    private fun hideViews() {
        if (TicketDetailsMode.equals("1")) {
            llTicketDetails!!.visibility = View.GONE
        }
        if (CustomerDetailsMode.equals("1")) {
            llCustomerDetails!!.visibility = View.GONE
        }
        if (ProductDetailsMode.equals("1")) {
            llProductDetails!!.visibility = View.GONE
        }
        if (PickDeliveryInformationMode.equals("1")) {
            llPickDeliveryInformation!!.visibility = View.GONE
        }
    }

    private fun getCurrentDate() {


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


            tie_PickDeliveryDate!!.setText(""+sdfDate1.format(newDate))
            tie_PickDeliveryTime!!.setText(""+sdfTime1.format(newDate))

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

//    private fun getDetailList() {
//        var gridList = Config.getHomeGrid(this@PickUpAndDeliveryUpdateActivity)
//        Log.e(TAG,"ActionType   44  "+gridList)
//        val jObject = JSONObject(gridList)
//        val jobjt = jObject.getJSONObject("homeGridType")
//        val tempArrayList = jobjt.getJSONArray("homeGridDetails")
//
//        prodInformationArrayList  = JSONArray()
//        for (i in 0 until tempArrayList.length()) {
//            val jsonObject = tempArrayList.getJSONObject(i)
//            val jObject = JSONObject()
//            jObject.put("isSelected","0")
//            jObject.put("prodName",jsonObject.getString("grid_name"))
//            jObject.put("prodQuantity","")
//            jObject.put("isStatndBy","0")
//            jObject.put("standByProduct","")
//            jObject.put("standByQuantity","")
//            jObject.put("standByAmount","")
//            jObject.put("remarks","")
//            prodInformationArrayList!!.put(jObject)
//        }
//
//      //  prodInformationArrayList = jobjt.getJSONArray("homeGridDetails")
//    }

    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

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

                tie_PickDeliveryDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)


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

                tie_PickDeliveryTime!!.setText(output)

            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getProductInformationDetails(v: View) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productinfodetailsviewmodel.getProductInfoDetails(this,SubMode!!, ID_ProductDelivery!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (productinfodetailscount == 0) {
                                    productinfodetailscount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("PickUPProductInformationDetails")
                                        prodInformationArrayList = jobjt.getJSONArray("PickUPProductInformationDetailsList")
                                        if (prodInformationArrayList.length() > 0) {

//                                            prodInformationArrayList!!.put(pos,jobjt)
                                            ProductInformationBottom(prodInformationArrayList)

                                        }
                                    } else {
//                                        val builder = AlertDialog.Builder(
//                                            this@PickUpAndDeliveryUpdateActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()

                                        ProductInformationBottom(prodInformationArrayList)
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                                progressDialog!!.dismiss()
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

    private fun ProductInformationBottom(prodInformationArrayList : JSONArray) {
        try {

            dialogProdInformation = Dialog(this)
            dialogProdInformation!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdInformation!! .setContentView(R.layout.product_information_sheet)
            dialogProdInformation!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogProdInformation!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            var imback          = dialogProdInformation!!.findViewById(R.id.imback)               as ImageView
            recyProdInformation = dialogProdInformation!! .findViewById(R.id.recyProdInformation) as RecyclerView
            tv_Pop_StandByTotal = dialogProdInformation!! .findViewById(R.id.tv_Pop_StandByTotal) as TextView

            imback.setOnClickListener {
                dialogProdInformation!!.dismiss()
            }


//            var gridList = Config.getHomeGrid(this@PickUpAndDeliveryUpdateActivity)
//            Log.e(TAG,"ActionType   44  "+gridList)
//            val jObject = JSONObject(gridList)
//            val jobjt = jObject.getJSONObject("homeGridType")
//            val tempArrayList = jobjt.getJSONArray("homeGridDetails")
//
//
//            prodInformationArrayList = jobjt.getJSONArray("homeGridDetails")

            Log.e(TAG,"prodInformationArrayList   396   "+prodInformationArrayList)

            if (prodInformationArrayList.length()>0){
                val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
                recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList)
                recyProdInformation!!.adapter = adapterHome
                adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)


//                setAmount()
            }


            dialogProdInformation!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail() {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdeliveryproductviewmodel.getPickupDeliveryProduct(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (proddetail == 0) {
                                    proddetail++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductDetailsList")
                                        addproductDetailArrayList = jobjt.getJSONArray("ProductList")
                                        if (addproductDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            productDetailPopup(addproductDetailArrayList)
//                                             }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryUpdateActivity,
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

    private fun productDetailPopup(addproductDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText

//            prodDetailSort = JSONArray()
//            for (k in 0 until prodDetailArrayList.length()) {
//                val jsonObject = prodDetailArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                prodDetailSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
            val adapter = PickupDeliveryProductAdapter(this@PickUpAndDeliveryUpdateActivity, addproductDetailArrayList)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodDetailSort = JSONArray()

                    for (k in 0 until addproductDetailArrayList.length()) {
                        val jsonObject = addproductDetailArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103" + prodDetailSort)
                    val adapter = PickupDeliveryProductAdapter(this@PickUpAndDeliveryUpdateActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)
                }
            })

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun getPickupDeliStandByProductDetails() {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdelistandbyproductviewmodel.getPickupDeliStandByProduct(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (proddetail == 0) {
                                    proddetail++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("StandByProductDetails")
                                        prodDetailArrayList = jobjt.getJSONArray("StandByProductDetailsList")
                                        if (prodDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            PickupDeliStandByproductDetailPopup(prodDetailArrayList)
//                                             }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryUpdateActivity,
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

    private fun PickupDeliStandByproductDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText

//            prodDetailSort = JSONArray()
//            for (k in 0 until prodDetailArrayList.length()) {
//                val jsonObject = prodDetailArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                prodDetailSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
            val adapter = PickupDeliStandByproductAdapter(this@PickUpAndDeliveryUpdateActivity, prodDetailArrayList)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodDetailSort = JSONArray()

                    for (k in 0 until prodDetailArrayList.length()) {
                        val jsonObject = prodDetailArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103" + prodDetailSort)
                    val adapter = PickupDeliStandByproductAdapter(this@PickUpAndDeliveryUpdateActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)
                }
            })

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"123456  "+data)
        if (data.equals("changeAmount")){
            Log.e(TAG,"545463465464  "+position)
            setAmount()

        }

        if (data.equals("PickupDeliStandByproduct")){
            dialogProdDet!!.dismiss()
            val jsonObject3 = prodDetailArrayList.getJSONObject(position)

            Log.e(TAG,"llllllllllllll   "+jsonObject3)
            Log.e(TAG,"llllllllllllll   "+prodInformationArrayList)


//            prodInformationArrayList  = JSONArray()
            val jObject = JSONObject()
            Log.e(TAG,"prodInformationArrayList size1        " +prodInformationArrayList.toString())

            Log.e(TAG,"prodInformationArrayList size2        " +prodInformationArrayList.toString())
            val jsonObject2 = prodInformationArrayList.getJSONObject(pos)

            //{"ID_Product":"340","ProdName":"Current","ProvideStandBy":"1","Quantity":"1.000","Product":"","SPQuantity":"","SPAmount":"0.00","Remarks":"","isSelected":"1"}


//            jObject.put("ProductName",jsonObject.getString("ProductName"))
            jObject.put("ID_Product",jsonObject2.getString("ID_Product"))
            jObject.put("ProdName",jsonObject2.getString("ProdName"))
            jObject.put("ProvideStandBy",jsonObject2.getString("ProvideStandBy"))
            jObject.put("Quantity",jsonObject2.getString("Quantity"))
            jObject.put("Product",jsonObject3.getString("ProductName"))
            jObject.put("SPQuantity",jsonObject2.getString("SPQuantity"))
            jObject.put("SPAmount",jsonObject2.getString("SPAmount"))
            jObject.put("Remarks",jsonObject2.getString("Remarks"))
            jObject.put("isSelected",("1"))

//            prodInformationArrayList.remove(pos)
            prodInformationArrayList!!.put(pos,jObject)
            Log.e(TAG,"prodInformationArrayList size3        " +prodInformationArrayList.toString())

            Log.e(TAG,"errrrrrrrrrrrr        " +position)
            Log.e(TAG,"eeeeeeeeeeeeeeeeeee   " +jObject)
//
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("ID_Product"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("ProductName"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("ProvideStandBy"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("Quantity"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("Product"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("SPQuantity"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("SPAmount"))
//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("Remarks"))
//            adapterHome
            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList)
            recyProdInformation!!.adapter = adapterHome
            adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

//            Log.e(TAG,"llllllllllllll   "+prodInformationArrayList)
//            prodInformationArrayList  = JSONArray()
//            for (i in 0 until prodDetailArrayList.length()) {
//                val jsonObject = prodDetailArrayList.getJSONObject(i)
//                val jObject = JSONObject()
//                jObject.put("ID_Product",jsonObject.getString("ID_Product"))
//                jObject.put("ProductName",jsonObject.getString("ProductName"))
//                prodInformationArrayList!!.put(jObject)
//
//                Log.e(TAG,"llllllllllllll   "+prodInformationArrayList)
//            }




            Log.e(TAG,"iddddd "+productName)
            Log.e(TAG,"iddddd "+productId)
        }

        if (data.equals("ProductName")){
            proddetail = 0
            pos = position
            Log.e(TAG,"idhssss "+position)
//            getProductDetail()
            getPickupDeliStandByProductDetails()


        }
    }

    private fun setAmount() {
        Log.e(TAG,"1234561 prodInformationArrayList  "+prodInformationArrayList)
        var standbyTotal ="0.00"
        for (i in 0 until prodInformationArrayList.length()) {
            val jsonObject = prodInformationArrayList.getJSONObject(i)
            if (jsonObject.getString("ProvideStandBy").equals("1")){
                // Selected
                Log.e(TAG,"42101 prodInformationArrayList  "+i)
                var standbyAmount = "0.00"
                if (jsonObject.getString("SPAmount").equals(".")){
                    standbyAmount = "0.00"
                }
                else if (!jsonObject.getString("SPAmount").equals("")){
                    standbyAmount = jsonObject.getString("SPAmount")
                }
//                    Log.e(TAG,"standbyTotal  42102   "+standbyTotal+"   :   "+standbyAmount +"  :  "+jsonObject.getString("prodName"))
//                    Log.e(TAG,"standbyTotal  42103   "+DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat())+"   :   "+DecimelFormatters.set2DecimelPlace(standbyAmount.toFloat()))
                standbyTotal = (DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat()+standbyAmount.toFloat()))

                tv_Pop_StandByTotal!!.text = standbyTotal

            }

        }
        Log.e(TAG,"standbyTotal  4313   "+standbyTotal)


//            txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))

//                    && jsonObject.getString("ID_BranchType").equals("")
    }

}