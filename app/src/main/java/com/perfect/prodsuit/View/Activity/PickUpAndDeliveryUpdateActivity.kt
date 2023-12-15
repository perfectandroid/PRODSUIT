package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity.Companion.locCountry
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PickUpAndDeliveryUpdateActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListener,  LocationListener{

    var TAG = "PickUpAndDeliveryUpdateActivity"
    lateinit var context: Context
    lateinit var tie_Selectstatus: AutoCompleteTextView
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    lateinit var paymentMethodeArrayList: JSONArray
    private var progressDialog: ProgressDialog? = null

    private var tv_header: TextView? = null
    private var SubMode: String? = ""
    private var TransMode: String? = ""
    private var productName: String? = ""
    private var productId: String? = ""
    private var pos: Int = 0
    private var positions: Int = 0
    var arrAddUpdate: String? = "0"
    var paymentCount = 0
    var billTypecount = 0

    private var tv_TicketDetailsClick: TextView? = null
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    lateinit var tie_Selectbilltype: AutoCompleteTextView
    private var tv_CustomerDetailsClick: TextView? = null
    private var til_llbilltype: TextInputLayout? = null
    private var tv_ProductDetailsClick: TextView? = null
    private var tv_PickDeliveryInformationClick: TextView? = null
    private var tv_ProductInformationClick: TextView? = null
    private var tv_TicketNumber: TextView? = null
    private var tv_RegisteredOn: TextView? = null
    private var tv_AddressLine1: TextView? = null
    private var tv_AddressLine2: TextView? = null
    private var tvv_productname: TextView? = null
    private var tvv_categorys: TextView? = null
    private var tv_Post: TextView? = null
    private var tv_Area: TextView? = null
    private var tv_District: TextView? = null
    private var tv_State: TextView? = null
    private var tv_CustomerName: TextView? = null
    private var tv_tvProduct: TextView? = null
    private var tv_tvCategory: TextView? = null
    private var edtPayMethod: EditText? = null
    private var til_Selectstatus: TextInputLayout? = null
    private var lllocationpickup: LinearLayout? = null
    private var til_location: TextInputLayout? = null
    lateinit var tie_location: AutoCompleteTextView
//    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mMap: GoogleMap? = null
    var lat: Double? = null
    var longi: Double? = null
    private var edtPayRefNo: EditText? = null
    private var edtPayAmount: EditText? = null
    private var txtPayBalAmount: TextView? = null
    private var tv_header1: TextView? = null
    private var txt_pay_Amount: TextView? = null
    private var txt_pay_method: TextView? = null
    private var txt_bal_Amount: TextView? = null
    private var tie_vehicleNo: TextView? = null
    private var tvv_CustomersName: TextView? = null
    private var tv_ReferenceNo: TextView? = null
    private var tv_addressMobilenumber: TextView? = null
    private var tv_Country: TextView? = null
    private var tv_Mobileno: TextView? = null
    private var tie_Drivername: TextView? = null
    private var til_driver_Mobile: TextView? = null
    private var til_Ewaybill: TextView? = null
    private var btnApply: Button? = null
    private var img_PayRefresh: ImageView? = null
    private var recyPaymentList: RecyclerView? = null
    var adapterPaymentList: PaymentListAdapter? = null
    private var ll_paymentlist: LinearLayout? = null
    private var img_PayAdd: ImageView? = null
    private var llpaymentmethod: LinearLayout? = null
    private var llreference: LinearLayout? = null
    private var ll_Customername: LinearLayout? = null
    private var llregistration: LinearLayout? = null
    private var ll_mobileno: LinearLayout? = null
    private var ll_ticket: LinearLayout? = null
    private var view_mobile: View? = null
    private var ticket_view: View? = null
    private var customer_view: View? = null
    private var reference_view: View? = null
    private var registration_view: View? = null
    private var status_check: String? = null
    private var billtype: String? = null
    private var status_id = ""
    private var SELECT_LOCATION: Int? = 103
    private var SECTION: Int? = 101
    private var llTicketDetails: LinearLayout? = null
    private var llCustomerDetails: LinearLayout? = null
    private var llProductDetails: LinearLayout? = null
    private var llsubmit: LinearLayout? = null
    private var llPickDeliveryInformation: LinearLayout? = null
    private var ll_deliveryDetailslist: LinearLayout? = null
    private var ll_prodDetail_pickup: LinearLayout? = null

    var TicketDetailsMode: String? = "1"
    var llpaymentmethodCount: String? = ""
    var CustomerDetailsMode: String? = "1"
    var ProductDetailsMode: String? = "1"
    var PickDeliveryInformationMode: String? = "1"
    var ProductInformationMode: String? = "1"
    var productinfodetailscount = 0
    var productinfodetailsdeliverycount = 0
    var applyMode = 0
    var ID_ProductDelivery: String? = ""
    var PickDeliveryDate = ""
    var PickDeliveryTime = ""
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    var strName: String? = null
    private var remark: String? = ""
    private var StandByAmount: String? = ""
    private var FK_BillType: String? = ""
    private var tempremark = ""


    private var til_PickDeliveryDate: TextInputLayout? = null
    private var til_PickDeliveryTime: TextInputLayout? = null

    private var tie_PickDeliveryDate: TextInputEditText? = null
    private var tie_PickDeliveryTime: TextInputEditText? = null
    private var tvv_list_name: TextView? = null
    private var tie_Remark: TextInputEditText? = null

    private var PRODUCT_INFORM: Int? = 100

    var prodInformationCount = 0
    var proddetail = 0
    var prodpriority = 0
    var complainttype = 0
    var prodcomplaint = 0
    var productposition = ""
    var ID_Category = "0"
    var IsSelected = ""
//    var standbyTotal = "0.00"
    var pickupdeliStatusCount = 0
    lateinit var ProdInformationViewModel: PaymentMethodViewModel
    var prodInformationArrayList: JSONArray = JSONArray()
    var prodInformationDeliveryArrayList: JSONArray = JSONArray()
    var prodInformationArrayList2: JSONArray = JSONArray()
    var prodInformationDeliveryArrayList2: JSONArray = JSONArray()
    var prodPriorityArrayList: JSONArray = JSONArray()
    var complaintTypeArrayList: JSONArray = JSONArray()
    var prodDetailArrayList: JSONArray = JSONArray()
    var arrPayment = JSONArray()
    var arrayPaymentmethod = JSONArray()
    var Productdetails = JSONArray()
    var DeliveryComplaints = JSONArray()
    var jsonObject5 = JSONObject()
    var providesatndby : String = ""
    lateinit var addproductDetailArrayList: JSONArray
    lateinit var prodaddingArraylist: JSONArray
    lateinit var pickupdeliveryupdatedetailsviewmodel: PickUpDeliveryUpdateDetailsViewModel
    lateinit var billtypeArrayList: JSONArray
    lateinit var pickupDeliveryUpdateDetailsArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    lateinit var productinfodetailsviewmodel: ProductInfoDetailsViewModel
    lateinit var deliveryinformationViewModel: DeliveryInformationViewModel
    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var complaintTypeViewModel: ComplaintTypeViewModel
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var pickupdeliveryproductviewmodel: PickupDeliveryProductViewModel
    lateinit var pickupdelibilltypeviewmodel: PickupDeliBillTypeViewModel
    lateinit var pickupdelistandbyproductviewmodel: PickupDeliStandByProductViewModel
    lateinit var updatepickupanddeliveryviewmodel: UpdatePickUpAndDeliveryViewModel
    private var dialogProdInformation: Dialog? = null
    private var dialogProdDet: Dialog? = null
    private var dialogPayment: Dialog? = null
    private var dialogPaymentMethod: Dialog? = null
    private var dialogProdPriority: Dialog? = null
    private var dialogProdComplaint: Dialog? = null
    var ID_PaymentMethod: String? = ""
    var recyProdInformation: RecyclerView? = null
    var recyProdDetail: RecyclerView? = null
    var recyPaymentMethod: RecyclerView? = null
    var recyProdPriority: RecyclerView? = null
    var recylist: RecyclerView? = null
    var tv_Pop_StandByTotal: TextView? = null
    var lladdProduct: LinearLayout? = null
    var llbilltype: LinearLayout? = null
    var arrPosition: Int? = 0
    var standbytotal1 :String = ""
    var updatepickupanddeliveryCount = 0
    var FK_EmployeeStock = ""

    var tickrtmode: String? = "1"  // GONE
    var customermode: String? = "1"  // GONE
    var productdetailsmode: String? = "1" // GONE
    var productinformationmode: String? = "1" // GONE
    var productinformationmode1: String? = "1" // GONE


    var locAddress: String? = ""
    var locCity: String? = ""
    var locState: String? = ""
    var locCountry: String? = ""
    var locpostalCode: String? = ""
    var locKnownName: String? = ""
    var strLatitude: String? = ""
    var strLongitue: String? = ""
    var FK_Category: String? = ""

    var lm: LocationManager? = null
    var gps_enabled = false
    var network_enabled = false

    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    var mLastLocation: Location? = null
    private val REQUEST_TURN_ON_LOCATION_SETTINGS = 100
    private val OPEN_SETTINGS = 200

    var address: String = ""
    var city: String = ""
    var state: String = ""
    var country: String = ""
    var postalCode: String = ""
    var knownName: String = ""
    var locate = "0"

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8088
    var saveAttendanceMark = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_pick_up_and_delivery_update)
        context = this@PickUpAndDeliveryUpdateActivity

        pickupdeliveryupdatedetailsviewmodel = ViewModelProvider(this).get(PickUpDeliveryUpdateDetailsViewModel::class.java)
        productinfodetailsviewmodel = ViewModelProvider(this).get(ProductInfoDetailsViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        pickupdeliveryproductviewmodel = ViewModelProvider(this).get(PickupDeliveryProductViewModel::class.java)
        pickupdelistandbyproductviewmodel = ViewModelProvider(this).get(PickupDeliStandByProductViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        pickupdelibilltypeviewmodel = ViewModelProvider(this).get(PickupDeliBillTypeViewModel::class.java)
        deliveryinformationViewModel = ViewModelProvider(this).get(DeliveryInformationViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        complaintTypeViewModel = ViewModelProvider(this).get(ComplaintTypeViewModel::class.java)
        updatepickupanddeliveryviewmodel = ViewModelProvider(this).get(UpdatePickUpAndDeliveryViewModel::class.java)


        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
        if (getIntent().hasExtra("SubMode")) {
            TransMode = intent.getStringExtra("TransMode")
            Log.e(TAG, "000111222255  " + TransMode)
        }
        ID_ProductDelivery = intent.getStringExtra("ID_ProductDelivery")
        Log.e(TAG, "000111222255  " + ID_ProductDelivery)

//        lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        if (checkLocationEnabled()){
//            buildAlertMessageNoGps();
//        }
//        checkAndRequestPermissions()

        setRegViews()
        checkAttendance()
        getUpdateStstusDetails()
//        checkAndRequestPermissions()

//        locAddress = getStringExtra("address")
//        locCity = getStringExtra("city")
//        locState = getStringExtra("state")
//        locCountry = getStringExtra("country")
//        locpostalCode = getStringExtra("postalCode")
//        locKnownName = getStringExtra("knownName")
//        strLatitude = getStringExtra("strLatitude")
//        strLongitue = getStringExtra("strLongitue")
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById(R.id.tv_header)
        tv_TicketDetailsClick = findViewById<TextView>(R.id.tv_TicketDetailsClick)
        tv_CustomerDetailsClick = findViewById<TextView>(R.id.tv_CustomerDetailsClick)
        tv_ProductDetailsClick = findViewById<TextView>(R.id.tv_ProductDetailsClick)
        tv_PickDeliveryInformationClick =
            findViewById<TextView>(R.id.tv_PickDeliveryInformationClick)
        tv_ProductInformationClick = findViewById<TextView>(R.id.tv_ProductInformationClick)

        tv_TicketNumber = findViewById(R.id.tv_TicketNumber)
        tv_RegisteredOn = findViewById(R.id.tv_RegisteredOn)
        tv_CustomerName = findViewById(R.id.tv_CustomerName)
        tv_AddressLine1 = findViewById(R.id.tv_AddressLine1)
        tv_AddressLine2 = findViewById(R.id.tv_AddressLine2)
        tv_Area = findViewById(R.id.tv_Area)
        tv_Post = findViewById(R.id.tv_Post)
        tv_District = findViewById(R.id.tv_District)
        tv_State = findViewById(R.id.tv_State)
        tv_tvProduct = findViewById(R.id.tv_tvProduct)
        tv_tvCategory = findViewById(R.id.tv_tvCategory)
        tvv_productname = findViewById(R.id.tvv_productname)
        tvv_categorys = findViewById(R.id.tvv_categorys)
        ll_deliveryDetailslist = findViewById(R.id.ll_deliveryDetailslist)
        llreference = findViewById(R.id.llreference)
        ll_Customername = findViewById(R.id.ll_Customername)
        ll_mobileno = findViewById(R.id.ll_mobileno)
        view_mobile = findViewById(R.id.view_mobile)
        customer_view = findViewById(R.id.customer_view)
        reference_view = findViewById(R.id.reference_view)
        llregistration = findViewById(R.id.llregistration)
        registration_view = findViewById(R.id.registration_view)
        ll_ticket = findViewById(R.id.ll_ticket)
        ticket_view = findViewById(R.id.ticket_view)
        ll_prodDetail_pickup = findViewById(R.id.ll_prodDetail_pickup)
        tie_vehicleNo = findViewById(R.id.tie_vehicleNo)
        tie_Drivername = findViewById(R.id.tie_Drivername)
        til_driver_Mobile = findViewById(R.id.til_driver_Mobile)
        til_Ewaybill = findViewById(R.id.til_Ewaybill)
        tvv_CustomersName = findViewById(R.id.tvv_CustomersName)
        tv_Mobileno = findViewById(R.id.tv_Mobileno)
        tv_ReferenceNo = findViewById(R.id.tv_ReferenceNo)
        tv_addressMobilenumber = findViewById(R.id.tv_addressMobilenumber)
        tv_Country = findViewById(R.id.tv_Country)

        llTicketDetails = findViewById<LinearLayout>(R.id.llTicketDetails)
        llCustomerDetails = findViewById<LinearLayout>(R.id.llCustomerDetails)
        llProductDetails = findViewById<LinearLayout>(R.id.llProductDetails)
        llPickDeliveryInformation = findViewById<LinearLayout>(R.id.llPickDeliveryInformation)

        til_PickDeliveryDate = findViewById<TextInputLayout>(R.id.til_PickDeliveryDate)
        til_PickDeliveryTime = findViewById<TextInputLayout>(R.id.til_PickDeliveryTime)

        tie_PickDeliveryDate = findViewById<TextInputEditText>(R.id.tie_PickDeliveryDate)
        tie_PickDeliveryTime = findViewById<TextInputEditText>(R.id.tie_PickDeliveryTime)
        tie_Remark = findViewById<TextInputEditText>(R.id.tie_Remark)

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


    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient!!.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>

                        strLongitue = list[0].longitude.toString()
                        strLatitude = list[0].latitude.toString()
                        locAddress  = list[0].getAddressLine(0)
                        Log.i("response121212","latitude="+list[0].latitude)
                        Log.i("response121212","longitude="+list[0].longitude)
                        Log.i("response121212","country="+list[0].countryName)
                        Log.i("response121212","address="+list[0].getAddressLine(0))

                        Log.e(TAG,"address="+list[0].getAddressLine(0))

                    }
                }
            } else {
//                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
                buildAlertMessageNoGps()
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

//    private fun getLocationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            mLocationPermissionGranted = true
//        } else {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
//        }
//    }


    private fun getUpdateStstusDetails() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdeliveryupdatedetailsviewmodel.getPickUpDeliveryUpdateDetails(
                    this,
                    SubMode!!,
                    ID_ProductDelivery!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (prodInformationCount == 0) {
                                    prodInformationCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("UpdateDeliverStatusDetails")
                                        val adres2 = jobjt.getString("Address2")
                                        val post = jobjt.getString("Post")
                                        val Address1 = jobjt.getString("Address1")
                                        val Area = jobjt.getString("Area")
                                        val District = jobjt.getString("District")
                                        val State = jobjt.getString("State")
                                        val Country = jobjt.getString("Country")
                                        val MobileNumber = jobjt.getString("MobileNo")

                                        tv_TicketNumber!!.setText(jobjt.getString("CSRTickno"))
                                        tv_RegisteredOn!!.setText(jobjt.getString("CSRRegDate"))
                                        tv_CustomerName!!.setText(jobjt.getString("Customer"))
                                        tv_tvProduct!!.setText(jobjt.getString("Product"))
                                        tv_tvCategory!!.setText(jobjt.getString("Category"))
                                        tie_vehicleNo!!.setText(jobjt.getString("DelVehicleNo"))
                                        tie_Drivername!!.setText(jobjt.getString("DelDriverName"))
                                        til_driver_Mobile!!.setText(jobjt.getString("DelDriverMobileNo"))
                                        til_Ewaybill!!.setText(jobjt.getString("PdEWayBillNo"))
                                        tvv_CustomersName!!.setText(jobjt.getString("Customer"))
                                        tv_Mobileno!!.setText(jobjt.getString("MobileNo"))
                                        tv_ReferenceNo!!.setText(jobjt.getString("CSRTickno"))
//                                        tv_addressMobilenumber!!.setText(jobjt.getString("MobileNo"))
//                                        tv_Country!!.setText(jobjt.getString("tv_Country"))

                                        if (Address1!!.equals("")) {
                                            tv_AddressLine1!!.visibility = View.GONE
                                        } else {
                                            tv_AddressLine1!!.setText(Address1)
                                        }
                                        if (adres2.equals("")) {
                                            tv_AddressLine2!!.visibility = View.GONE
                                        } else {
                                            tv_AddressLine2!!.setText(adres2)
                                        }
                                        if (post.equals("")) {
                                            tv_Post!!.visibility = View.GONE
                                        } else {
                                            tv_Post!!.setText(post)
                                        }
                                        if (Area.equals("")) {
                                            tv_Area!!.visibility = View.GONE
                                        } else {
                                            tv_Area!!.setText(Area)
                                        }
                                        if (District.equals("")) {
                                            tv_District!!.visibility = View.GONE
                                        } else {
                                            tv_District!!.setText(District)
                                        }
                                        if (State.equals("")) {
                                            tv_State!!.visibility = View.GONE
                                        } else {
                                            tv_State!!.setText(State)
                                        }
                                        if (Country.equals("")){
                                            tv_Country!!.visibility = View.GONE
                                        }else{
                                            tv_Country!!.setText(Country)
                                        }
                                        if (MobileNumber.equals("")){
                                            tv_addressMobilenumber!!.visibility = View.GONE
                                        }else{
                                            tv_addressMobilenumber!!.setText(MobileNumber)
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


    private fun setLabel() {

        if (SubMode.equals("1")) {
            tv_header!!.text = "Pick up"
            tv_PickDeliveryInformationClick!!.text = "Pickup Note"
            til_PickDeliveryDate!!.hint       = "Pick Up Date *"
            til_PickDeliveryTime!!.hint       = "Pick up Time *"
            tv_ProductInformationClick!!.text = "PickUp Information"
            llreference!!.visibility          = View.GONE
            ll_Customername!!.visibility      = View.GONE
            ll_mobileno!!.visibility          = View.GONE
            view_mobile!!.visibility          = View.GONE
            customer_view!!.visibility        = View.GONE
            reference_view!!.visibility       = View.GONE
            llregistration!!.visibility       = View.VISIBLE
            registration_view!!.visibility    = View.VISIBLE
            ll_ticket!!.visibility            = View.VISIBLE
            ticket_view!!.visibility          = View.VISIBLE
            ll_prodDetail_pickup!!.visibility = View.VISIBLE
            ll_deliveryDetailslist!!.visibility  = View.GONE
//            tv_ProductInformationClick!!.text = "Product"

        }
        if (SubMode.equals("2")) {

            if (TransMode!!.equals("INDA")){
            tv_header!!.text = "Delivery"
            tv_PickDeliveryInformationClick!!.text = "Delivery Note"
            til_PickDeliveryDate!!.hint            = "Delivery Date *"
            til_PickDeliveryTime!!.hint            = "Delivery Time *"
            tv_ProductInformationClick!!.text      = "Delivery Information"
            tv_TicketDetailsClick!!.text           = "Sales Details"
            tv_CustomerDetailsClick!!.text         = "Delivery Address"
            tv_ProductDetailsClick!!.text          = "Delivery Details"
            llreference!!.visibility               = View.VISIBLE
            ll_Customername!!.visibility           = View.VISIBLE
            ll_mobileno!!.visibility               = View.VISIBLE
            view_mobile!!.visibility               = View.VISIBLE
            customer_view!!.visibility             = View.VISIBLE
            reference_view!!.visibility            = View.VISIBLE
            llregistration!!.visibility            = View.VISIBLE
            registration_view!!.visibility         = View.VISIBLE
            ll_ticket!!.visibility                 = View.GONE
            ticket_view!!.visibility               = View.GONE
            ll_deliveryDetailslist!!.visibility    = View.VISIBLE
            ll_prodDetail_pickup!!.visibility      = View.GONE
//            tvv_productname!!.text = "Vehicle No"


            }else if(TransMode!!.equals("CUSA")){

                tv_header!!.text = "Delivery"
                tv_PickDeliveryInformationClick!!.text = "Delivery Note"
                til_PickDeliveryDate!!.hint       = "Delivery Date *"
                til_PickDeliveryTime!!.hint       = "Delivery Time *"
                tv_ProductInformationClick!!.text = "Delivery Information"
                llreference!!.visibility          = View.GONE
                ll_Customername!!.visibility      = View.GONE
                ll_mobileno!!.visibility          = View.GONE
                view_mobile!!.visibility          = View.GONE
                customer_view!!.visibility        = View.GONE
                reference_view!!.visibility       = View.GONE
                llregistration!!.visibility       = View.VISIBLE
                registration_view!!.visibility    = View.VISIBLE
                ll_ticket!!.visibility            = View.VISIBLE
                ticket_view!!.visibility          = View.VISIBLE
                ll_prodDetail_pickup!!.visibility = View.VISIBLE
                ll_deliveryDetailslist!!.visibility  = View.GONE
            }
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }

            R.id.tv_TicketDetailsClick -> {
                llTicketDetails!!.visibility = View.VISIBLE
                TicketDetailsMode = "0"
                CustomerDetailsMode = "1"
                tickrtmode = "1"
                ProductDetailsMode = "1"
                PickDeliveryInformationMode = "1"
                ProductInformationMode = "1"
                hideViews()

            }
            R.id.tv_CustomerDetailsClick -> {
                llCustomerDetails!!.visibility = View.VISIBLE
                TicketDetailsMode = "1"
                CustomerDetailsMode = "0"
                customermode = "1"
                ProductDetailsMode = "1"
                PickDeliveryInformationMode = "1"
                ProductInformationMode = "1"
                hideViews()

            }
            R.id.tv_ProductDetailsClick -> {
                llProductDetails!!.visibility = View.VISIBLE
                TicketDetailsMode = "1"
                CustomerDetailsMode = "1"
                productdetailsmode = "1"
                ProductDetailsMode = "0"
                PickDeliveryInformationMode = "1"
                ProductInformationMode = "1"
                hideViews()

            }
            R.id.tv_PickDeliveryInformationClick -> {
                llPickDeliveryInformation!!.visibility = View.VISIBLE
                TicketDetailsMode = "1"
                CustomerDetailsMode = "1"
                ProductDetailsMode = "1"
                PickDeliveryInformationMode = "0"
                ProductInformationMode = "1"
                hideViews()
            }
            R.id.tv_ProductInformationClick -> {
                TicketDetailsMode = "1"
                CustomerDetailsMode = "1"
                ProductDetailsMode = "1"
                PickDeliveryInformationMode = "1"
                ProductInformationMode = "0"
                hideViews()

//                val intent = Intent(this@PickUpAndDeliveryUpdateActivity, ProductInformationActivity::class.java)
//                startActivityForResult(intent, PRODUCT_INFORM!!);

//                productinfodetailscount = 0
//                getProductInformationDetails(v)
//                ProductInformationBottom(v)
                ProductInformationBottom()
//                val intent = Intent(this@PickUpAndDeliveryUpdateActivity, LocationPickerActivity::class.java)
//                startActivityForResult(intent, SELECT_LOCATION!!)
//                IsSelected = "0"
                Log.e(TAG, "IsSelected196  " + IsSelected)

            }

            R.id.tie_PickDeliveryDate -> {
                Config.disableClick(v)
                // dateMode = 0
                openBottomDate()
            }
            R.id.tie_PickDeliveryTime -> {
                Config.disableClick(v)
                // timeMode = 0
                openBottomTime()
            }

        }
    }

    private fun checkLocationEnabled(): Boolean {

        var result = false


        try {
            gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!gps_enabled && !network_enabled) {
                result = true
            }

        } catch (ex: Exception) {
            result = false
        }

        return result

    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
            .setNegativeButton(
                "No"
            ) { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
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

            Log.e(TAG, "DATE TIME  196  " + currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG, "newDate  196  " + newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            tie_PickDeliveryDate!!.setText("" + sdfDate1.format(newDate))
            tie_PickDeliveryTime!!.setText("" + sdfTime1.format(newDate))

            PickDeliveryTime = "" + sdfTime1.format(newDate)
            PickDeliveryDate = "" + sdfDate1.format(newDate)

            Log.e(TAG, "Exception 196  " + PickDeliveryTime + PickDeliveryDate)

        } catch (e: Exception) {

            Log.e(TAG, "Exception 196  " + e.toString())
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

                tie_PickDeliveryDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                PickDeliveryDate = tie_PickDeliveryDate.toString()

                Log.e(TAG, "PickDeliveryDate   " + PickDeliveryDate)

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
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
                val input = "" + hr + ":" + min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                tie_PickDeliveryTime!!.setText(output)

                PickDeliveryTime = tie_PickDeliveryTime.toString()
                Log.e(TAG, "PickDeliveryTime   " + output)
            } catch (e: Exception) {
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getProductInformationDetails() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productinfodetailsviewmodel.getProductInfoDetails(
                    this,
                    SubMode!!,
                    ID_ProductDelivery!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        //  try {
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (productinfodetailscount == 0) {
                                productinfodetailscount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   2353   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt =
                                        jObject.getJSONObject("PickUPProductInformationDetails")
                                    prodInformationArrayList =
                                        jobjt.getJSONArray("PickUPProductInformationDetailsList")
                                    if (prodInformationArrayList.length() > 0) {

//                                                ProductInformationBottom(prodInformationArrayList)
                                        Log.e(
                                            TAG,
                                            "prodInformationArrayList   396   " + prodInformationArrayList
                                        )


                                        for (i in 0 until prodInformationArrayList.length()) {
                                            var jsonObject =
                                                prodInformationArrayList.getJSONObject(i)
                                            val jObject = JSONObject()

                                            jObject.put(
                                                "ID_Product",
                                                jsonObject.getString("ID_Product")
                                            )
                                            jObject.put(
                                                "ProdName",
                                                jsonObject.getString("ProdName")
                                            )
                                            jObject.put(
                                                "ProvideStandBy",
                                                jsonObject.getString("ProvideStandBy")
                                            )
                                            jObject.put(
                                                "Quantity",
                                                jsonObject.getString("Quantity")
                                            )
                                            jObject.put("Product", jsonObject.getString("Product"))
                                            jObject.put(
                                                "SPQuantity",
                                                jsonObject.getString("SPQuantity")
                                            )
                                            jObject.put(
                                                "SPAmount",
                                                jsonObject.getString("SPAmount")
                                            )
                                            jObject.put("Remarks", jsonObject.getString("Remarks"))
                                            jObject.put("isSelected", ("0"))
                                            jObject.put("isEnable", ("0"))
                                            jObject.put("SubMode", SubMode)

                                            Log.e(TAG, "wwwwwwwwwww 222 " + SubMode)
                                            prodInformationArrayList2.put(jObject)


                                        }
                                        Log.e(TAG, "hhhhhhhhhhhhhhh " + prodInformationArrayList2)

                                        val lLayout = GridLayoutManager(
                                            this@PickUpAndDeliveryUpdateActivity,
                                            1
                                        )
                                        recyProdInformation!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                        val adapterHome = ProdInformationAdapter(
                                            this@PickUpAndDeliveryUpdateActivity,
                                            prodInformationArrayList2
                                        )
                                        recyProdInformation!!.adapter = adapterHome
                                        adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

                                        Log.e(
                                            TAG,
                                            "dddddddddddddddddddd  " + prodInformationArrayList
                                        )


//                                        if (prodInformationArrayList.length() > 0) {
//                                            Log.e(TAG, "ProductInformationBottom               2")
//                                            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
//                                            recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList)
//                                            recyProdInformation!!.adapter = adapterHome
//                                            adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)
////                setAmount(
//                                        }

//                                            prodInformationArrayList!!.put(pos,jobjt)
                                    }
                                } else {
//                                        Log.e(TAG, "ProductInformationBottom               1125")
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


//                                        var prodInformationArrayList2             : JSONArray = JSONArray()
//                                        ProductInformationBottom(prodInformationArrayList2)
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
//                        } catch (e: Exception) {
//                            Log.e(TAG, "ProductInformationBottom               1"+e)
//                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getProductInformationDelivery() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                deliveryinformationViewModel.getDeliveryInformation(
                    this, SubMode!!, ID_ProductDelivery!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        //  try {
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (productinfodetailsdeliverycount == 0) {
                                productinfodetailsdeliverycount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   2353   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("DeliveryProductInformationDetails")
                                    prodInformationDeliveryArrayList = jobjt.getJSONArray("DeliveryProductInformationDetailsList")

                                    if (prodInformationDeliveryArrayList.length() > 0) {
                                        Log.e(TAG, "prodInformationDeliveryArrayList   558   " + prodInformationDeliveryArrayList)

                                        for (i in 0 until prodInformationDeliveryArrayList.length()) {
                                            var jsonObject = prodInformationDeliveryArrayList.getJSONObject(i)
                                            val jObject = JSONObject()

                                            FK_Category = jsonObject.getString("FK_Category")

                                            jObject.put("ID_ProductDelivery", jsonObject.getString("ID_ProductDelivery"))
                                            jObject.put("FK_Product", jsonObject.getString("FK_Product"))
                                            jObject.put("FK_Category", jsonObject.getString("FK_Category"))
                                            jObject.put("Product", jsonObject.getString("Product"))
                                            jObject.put("Quantity", jsonObject.getString("Quantity"))
                                            jObject.put("TransMode", jsonObject.getString("TransMode"))
                                            jObject.put("PriorityName", "")
                                            jObject.put("ID_Priority", "")
                                            jObject.put("ComplaintQty", "")
                                            jObject.put("ID_ComplaintList", "")
                                            jObject.put("ComplaintName", "")
                                            jObject.put("Description", "")
                                            jObject.put("isSelectedDelivery", ("0"))
//                                            jObject.put("isEnable", ("0"))
                                            jObject.put("SubMode", SubMode)


                                            Log.e(TAG, "wwwwwwwwwww eeeeee " + SubMode)
                                            prodInformationArrayList2.put(jObject)


                                        }
                                        Log.e(TAG, "fffffffffffffffffff " + prodInformationArrayList2)

                                        val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
                                        recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
                                        recyProdInformation!!.adapter = adapterHome
                                        adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

                                        Log.e(TAG, "dddddddddddddddddddd  " + prodInformationArrayList2)
                                    }
                                } else {

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
//                        } catch (e: Exception) {
//                            Log.e(TAG, "ProductInformationBottom               1"+e)
//                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun ProductInformationBottom() {
        Log.e(TAG, "ProductInformationBottom               0")
        try {

            Log.e(TAG, "ProductInformationBottom               1")
            dialogProdInformation = Dialog(this)
            dialogProdInformation!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdInformation!!.setContentView(R.layout.product_information_sheet)
            dialogProdInformation!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogProdInformation!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            var imback = dialogProdInformation!!.findViewById(R.id.imback) as ImageView
            recyProdInformation =
                dialogProdInformation!!.findViewById(R.id.recyProdInformation) as RecyclerView
            tv_Pop_StandByTotal =
                dialogProdInformation!!.findViewById(R.id.tv_Pop_StandByTotal) as TextView
            lladdProduct = dialogProdInformation!!.findViewById(R.id.lladdProduct) as LinearLayout
            llbilltype = dialogProdInformation!!.findViewById(R.id.llbilltype) as LinearLayout
            tie_Selectstatus =
                dialogProdInformation!!.findViewById(R.id.tie_Selectstatus) as AutoCompleteTextView
            llpaymentmethod =
                dialogProdInformation!!.findViewById(R.id.llpaymentmethod) as LinearLayout
            tie_Selectbilltype =
                dialogProdInformation!!.findViewById(R.id.tie_Selectbilltype) as AutoCompleteTextView
            llsubmit =
                dialogProdInformation!!.findViewById(R.id.llsubmit) as LinearLayout
            til_Selectstatus =
                dialogProdInformation!!.findViewById(R.id.til_Selectstatus) as TextInputLayout
            tie_location =
                dialogProdInformation!!.findViewById(R.id.tie_location) as AutoCompleteTextView
            tv_header1 = dialogProdInformation!!.findViewById(R.id.tv_header) as TextView
            val ll_standbytotal = dialogProdInformation!!.findViewById(R.id.ll_standbytotal) as LinearLayout

            if (SubMode!!.equals("1")) {
                tv_header1!!.text = "Pickup Information"
                ll_standbytotal.visibility = View.VISIBLE

                Log.e(TAG, "wwwwwwwwwww 111" + SubMode)
                if (prodInformationArrayList2.length() == 0) {
                    productinfodetailscount = 0
                    getProductInformationDetails()
                } else {

//                    if (StandByAmount.equals("0.00") || StandByAmount!!.equals("")) {
//                        llbilltype!!.visibility = View.VISIBLE
//
//                    } else {
//
//                        llbilltype!!.visibility = View.VISIBLE
//                        setAmount()
////                    Log.e(TAG, "standbytotal1           3"+standbytotal1)
//                    }

                    if (standbytotal1.equals("0.00")){
                        llbilltype!!.visibility = View.GONE
                    }else{
                        llbilltype!!.visibility = View.VISIBLE
                        setAmount()
                    }


                    val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
                    recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
                    recyProdInformation!!.adapter = adapterHome
                    adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

//                standbyTotal = (DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat() + StandByAmount!!.toFloat()))
//                standbyTotal = ""

                }
            }else if (SubMode!!.equals("2")) {

                llbilltype!!.visibility = View.GONE
                lladdProduct!!.visibility = View.GONE
                ll_standbytotal.visibility = View.GONE

//                if (TransMode!!.equals("INDA")){
//
//                }else if (TransMode!!.equals("CUSA")){
//
//                }
//                tv_header1!!.text = "Delivery Information"





                if (prodInformationArrayList2.length() == 0) {
                    productinfodetailsdeliverycount = 0
                    Log.e(TAG, "wwwwwwwwwww 121   " + SubMode)
                    getProductInformationDelivery()

                } else {

//                    if (standbytotal1.equals("0.00")){
//                        llbilltype!!.visibility = View.GONE
//                    }else{
//                        llbilltype!!.visibility = View.VISIBLE
//                        setAmount()
//                    }


                    val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
                    recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
                    recyProdInformation!!.adapter = adapterHome
                    adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

//                standbyTotal = (DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat() + StandByAmount!!.toFloat()))
//                standbyTotal = ""

                }
            }


            imback.setOnClickListener {
                dialogProdInformation!!.dismiss()
//                prodInformationArrayList2
            }

            lladdProduct!!.setOnClickListener {
                proddetail = 0
                getProductDetail()
            }

            llbilltype!!.setOnClickListener {

            }

            tie_Selectstatus!!.setOnClickListener {
                pickupdeliStatusCount = 0
                showMethod(tie_Selectstatus)
            }

            llpaymentmethod!!.setOnClickListener {
                llpaymentmethodCount = "0"
                payMethodbottomsheet()
            }

            tie_Selectbilltype!!.setOnClickListener {
                billTypecount = 0
                getBillType()
            }

            tie_location!!.setOnClickListener {

//                val intent = Intent(this@PickUpAndDeliveryUpdateActivity, LocationPickerActivity::class.java)
//                startActivityForResult(intent, SELECT_LOCATION!!)

            }

            llsubmit!!.setOnClickListener {
                Config.disableClick(it)

                if (isLocationEnabled().equals(false)) {
                    Log.e(TAG,"isLocationEnabled.......  ")
                    getLocation()
                    if (status_id.equals("")) {
                        Config.snackBarWarning(context, it, "Please Select Status")
                        Log.e(TAG,"status_id.......1  ")
                    }
                }else{
                    Log.e(TAG,"Validation.......1  ")
                    getLocation()
                    checkAttendance()
                    Validation(it)
                }
            }


//            var gridList = Config.getHomeGrid(this@PickUpAndDeliveryUpdateActivity)
//            Log.e(TAG,"ActionType   44  "+gridList)
//            val jObject = JSONObject(gridList)
//            val jobjt = jObject.getJSONObject("homeGridType")
//            val tempArrayList = jobjt.getJSONArray("homeGridDetails")
//
//
//            prodInformationArrayList = jobjt.getJSONArray("homeGridDetails")

//            Log.e(TAG,"prodInformationArrayList   396   "+prodInformationArrayList)
//
//            if (prodInformationArrayList.length()>0){
//                Log.e(TAG, "ProductInformationBottom               2")
//                val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
//                recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList)
//                recyProdInformation!!.adapter = adapterHome
//                adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)
//
//
////                setAmount()
//            }


//            if (prodInformationArrayList2.equals("")){

//            }


//            productinfodetailscount = 0
//            getProductInformationDetails()


            dialogProdInformation!!.show()
        } catch (e: Exception) {
            Log.e(TAG, "ProductInformationBottom           3")
            e.printStackTrace()
        }
    }

    private fun getProductPriority() {
        var prodpriority = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   353   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                if (prodPriorityArrayList.length()>0){
                                    if (prodpriority == 0){
                                        prodpriority++
                                            productPriorityPopup(prodPriorityArrayList)
                                    }

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

    private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

        try {


            dialogProdPriority = Dialog(this)
            dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdPriority!! .setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority = dialogProdPriority!! .findViewById(R.id.recyProdPriority) as RecyclerView


            Log.e(TAG,"complaint pop 112244  "+prodPriorityArrayList)

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ProductPriorityAdapter(this@PickUpAndDeliveryUpdateActivity, prodPriorityArrayList)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)



            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun getComplaintType() {
        var prodcomplaint = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                        complaintTypeViewModel.getComplaintType(this,FK_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   178   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("ProductComplaintsList")
                                complaintTypeArrayList = jobjt.getJSONArray("ProductComplaintsList")
                                if (complaintTypeArrayList.length()>0){
                                    if (prodcomplaint == 0){
                                        prodcomplaint++
                                        complaintTypePopup(complaintTypeArrayList)
                                    }

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

    private fun complaintTypePopup(complaintTypeArrayList: JSONArray) {

        try {

            Log.e(TAG,"complaint pop  "+complaintTypeArrayList)
            dialogProdComplaint = Dialog(this)
            dialogProdComplaint!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdComplaint!! .setContentView(R.layout.list_popup)
            dialogProdComplaint!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProdComplaint!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProdComplaint!! .findViewById(R.id.tvv_list_name) as TextView

            tvv_list_name!!.setText("Complaint Type")

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ComplaintTypeAdapter(this@PickUpAndDeliveryUpdateActivity, complaintTypeArrayList)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)

            Log.e(TAG,"complaint pop 889977  "+complaintTypeArrayList)

            dialogProdComplaint!!.show()
            dialogProdComplaint!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"complaint pop1111  "+e)
        }

    }



    override fun onLocationChanged(location: Location) {

        geocoder = Geocoder(this, Locale.getDefault())
        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
        address = addresses!!.get(0).getAddressLine(0)
        city = addresses!!.get(0).locality
        state = addresses!!.get(0).adminArea
        country = addresses!!.get(0).countryName
        postalCode = addresses!!.get(0).postalCode
        knownName = addresses!!.get(0).featureName
        strLongitue = location.longitude.toString()
        strLatitude = location.latitude.toString()
        val latLng = LatLng(location.latitude, location.longitude)


        Log.e("Dbg", "address111122222    " + latLng)

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }



    private fun payMethodbottomsheet() {
        try {

            dialogPayment = Dialog(this)
            dialogPayment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPayment!!.setContentView(R.layout.emi_payment_bottom_sheet)
            dialogPayment!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogPayment!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //  dialogPaymentSheet!!.setCancelable(false)

            edtPayMethod = dialogPayment!!.findViewById(R.id.edtPayMethod) as EditText
            edtPayRefNo = dialogPayment!!.findViewById(R.id.edtPayRefNo) as EditText
            edtPayAmount = dialogPayment!!.findViewById(R.id.edtPayAmount) as EditText

            txtPayBalAmount = dialogPayment!!.findViewById(R.id.txtPayBalAmount) as TextView

            txt_pay_method = dialogPayment!!.findViewById(R.id.txt_pay_method) as TextView
            txt_pay_Amount = dialogPayment!!.findViewById(R.id.txt_pay_Amount) as TextView
            txt_bal_Amount = dialogPayment!!.findViewById(R.id.txt_bal_Amount) as TextView

            edtPayMethod!!.addTextChangedListener(watcher)
            edtPayAmount!!.addTextChangedListener(watcher)
            txtPayBalAmount!!.addTextChangedListener(watcher)

//            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
//            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
//            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText


            DecimelFormatters.setDecimelPlace(edtPayAmount!!)


//            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))


            img_PayAdd = dialogPayment!!.findViewById(R.id.img_PayAdd) as ImageView
            img_PayRefresh = dialogPayment!!.findViewById(R.id.img_PayRefresh) as ImageView
            btnApply = dialogPayment!!.findViewById(R.id.btnApply) as Button

            ll_paymentlist = dialogPayment!!.findViewById(R.id.ll_paymentlist) as LinearLayout
            recyPaymentList = dialogPayment!!.findViewById(R.id.recyPaymentList) as RecyclerView


            // txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())

            if (arrPayment.length() > 0) {
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
                Log.e(TAG, "payAmnt         475    " + payAmnt)
                Log.e(TAG, "tv_NetAmount    475    " + tv_Pop_StandByTotal!!.text.toString())
                txtPayBalAmount!!.setText(
                    "" + DecimelFormatters.set2DecimelPlace(
                        (tv_Pop_StandByTotal!!.text.toString().toFloat()) - pay.toFloat()
                    )
                )

                Log.e(TAG, "txtPayBalAmount    114    " + txtPayBalAmount)
            } else {
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                txtPayBalAmount!!.setText("" + tv_Pop_StandByTotal!!.text.toString())

                Log.e(TAG, "txtPayBalAmount    115    " + txtPayBalAmount)
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
                    txtPayBalAmount!!.setText(
                        "" + DecimelFormatters.set2DecimelPlace(
                            (tv_Pop_StandByTotal!!.text.toString().toFloat()) - payAmnt.toFloat()
                        )
                    )
                    Log.e(TAG, "txtPayBalAmount    116    " + txtPayBalAmount)
                } else {
                    txtPayBalAmount!!.setText("" + tv_Pop_StandByTotal!!.text.toString())
                    Log.e(TAG, "txtPayBalAmount    117    " + txtPayBalAmount)
                }
            }

            btnApply!!.setOnClickListener {
                val payAmnt =
                    DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                if ((payAmnt.toFloat()).equals("0.00".toFloat())) {
                    Log.e(TAG, "801 payAmnt  0.00  " + payAmnt.toFloat())
                    applyMode = 1
                    dialogPayment!!.dismiss()
                } else {
                    Log.e(TAG, "801 payAmnt  0.0clhghfoij    " + payAmnt.toFloat())
                    Config.snackBarWarning(context, it, "Balance Amount should be zero")
                }
            }


            dialogPayment!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "801 Exception  " + e.toString())
        }
    }

    private fun validateAddPayment(view: View) {
        var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
        var payAmount = edtPayAmount!!.text.toString()

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


        } else if (edtPayAmount!!.text.toString().equals("")) {
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
                jObject.put(
                    "Amount",
                    DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat())
                )

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
                jsonObject.put("Amount", DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))

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
                    Log.e(TAG, "283021    ")
                    if (edtPayAmount!!.text.toString().equals("")) {
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                    } else {
                        //   til_DeliveryDate!!.isErrorEnabled = false
                        txt_pay_Amount!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.black
                            )
                        )
                    }

                }

                editable === txtPayBalAmount!!.editableText -> {
                    Log.e(TAG, "283021    ")
                    val payAmnt = DecimelFormatters.set2DecimelPlace(
                        txtPayBalAmount!!.text.toString().toFloat()
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


    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@PickUpAndDeliveryUpdateActivity, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@PickUpAndDeliveryUpdateActivity)
    }


    private fun showMethod(tie_Selectstatus: AutoCompleteTextView?) {

        val searchType = arrayOf<String>(
            "Please Select",
            "Open",
            "Closed",
        )

        if (pickupdeliStatusCount == 0) {
            pickupdeliStatusCount++

            val adapter = ArrayAdapter(this, R.layout.filter_status_spinner, searchType)
            tie_Selectstatus!!.setAdapter(adapter)
            //   tie_Selectstatus!!.setText(searchType.get(0), false)
//            tie_Selectstatus!!.setOnClickListener {
            pickupdeliStatusCount = 0
            tie_Selectstatus!!.showDropDown()
            Log.e(TAG, "7778889999   " + pickupdeliStatusCount)
//            }
            tie_Selectstatus!!.setOnItemClickListener { parent, view, position, id ->
                status_check = searchType[position]

                if (status_check.equals("Open")) {
                    status_id = "1"
//                showMethod(tie_Selectstatus)
                }
                if (status_check.equals("Closed"))
                    status_id = "2"
//                showMethod(tie_Selectstatus)
                Log.e(TAG, "5555444   " + status_id)
            }
        }
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

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter =
                PayMethodAdapter(this@PickUpAndDeliveryUpdateActivity, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryUpdateActivity)



            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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
                                        addproductDetailArrayList =
                                            jobjt.getJSONArray("ProductList")
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
            val adapter = PickupDeliveryProductAdapter(
                this@PickUpAndDeliveryUpdateActivity,
                addproductDetailArrayList
            )
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
                    val adapter = PickupDeliveryProductAdapter(
                        this@PickUpAndDeliveryUpdateActivity,
                        prodDetailSort
                    )
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
                            Log.e(TAG,"catch   "+e)
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
            FK_BillType = objects.getString("ID_BillType")

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

//                    if (status_check.equals("Open")) {
//                        status_id = "1"
////                showMethod(tie_Selectstatus)
//                    }
//                    if (status_check.equals("Closed"))
//                        status_id = "2"
//                showMethod(tie_Selectstatus)
                    Log.e(TAG, "5555444   " + status_id)
                }
            }
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

                                        val jobjt =
                                            jObject.getJSONObject("StandByProductDetails")
                                        prodDetailArrayList =
                                            jobjt.getJSONArray("StandByProductDetailsList")
                                        if (prodDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            PickupDeliStandByproductDetailPopup(
                                                prodDetailArrayList
                                            )
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
            val adapter = PickupDeliStandByproductAdapter(
                this@PickUpAndDeliveryUpdateActivity,
                prodDetailArrayList
            )
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
                    val adapter = PickupDeliStandByproductAdapter(
                        this@PickUpAndDeliveryUpdateActivity,
                        prodDetailSort
                    )
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


    private fun saveUpdatePickUpAndDelivery() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                updatepickupanddeliveryviewmodel.getUpdatePickUpAndDelivery(this, ID_ProductDelivery!!, PickDeliveryTime, PickDeliveryDate, remark!!, FK_BillType!!, Productdetails, arrayPaymentmethod!!,DeliveryComplaints!!, StandByAmount!!, status_id!!, strLongitue!!, strLatitude!!, locAddress!!)!!.observe(
                    this,
                    Observer { deleteleadSetterGetter ->
                        val msg = deleteleadSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                Log.e(TAG, "msg  1126     " + msg)
                                val jObject = JSONObject(msg)
                                if (updatepickupanddeliveryCount == 0) {
                                    updatepickupanddeliveryCount++
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UpdatePickUpAndDelivery")
                                        try {

                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!!.setContentView(R.layout.pickup_deli_update_success)
                                            suceessDialog!!.window!!.attributes.gravity =
                                                Gravity.CENTER_VERTICAL;

                                            val tv_succesmsg =
                                                suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
//                                            val tv_label = suceessDialog!! .findViewById(R.id.tv_label) as TextView
//                                            val tv_leadid = suceessDialog!! .findViewById(R.id.tv_leadid) as TextView
                                            val tv_succesok =
                                                suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                            //LeadNumber
                                            tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
//                                            tv_label!!.setText("Lead No : ")
//                                            tv_leadid!!.setText(jobjt.getString("LeadNo"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                val intent = Intent()
                                                intent.putExtra("MESSAGE", android.R.id.message)
                                                setResult(2, intent)
                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                            );
                                        } catch (e: Exception) {
                                            e.printStackTrace()
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

                            }

                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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


    override fun onClick(position: Int, data: String) {
        Log.e(TAG, "123456  " + data)
        if (data.equals("changeAmount")) {
            Log.e(TAG, "545463465464  " + position)
            jsonObject5 = prodInformationArrayList2.getJSONObject(position)
//            IsSelected = jsonObject5.getString("isSelected")
//            Log.e(TAG,"343434  "+IsSelected)

            Log.e(TAG, "5555555555555  " +jsonObject5.getString("ProvideStandBy"))
//            Log.e(TAG, "4444444444444  " +jsonObject5.getString("isSelected"))
            setAmount()

        }

        if (data.equals("PickupDeliStandByproduct")) {
            dialogProdDet!!.dismiss()
            setAmount()
            val jsonObject3 = prodDetailArrayList.getJSONObject(position)

            Log.e(TAG, "llllllllllllll   " + jsonObject3)
            Log.e(TAG, "llllllllllllll   " + prodInformationArrayList)


//            prodInformationArrayList  = JSONArray()
            val jObject = JSONObject()
            Log.e(TAG, "prodInformationArrayList size1        " + prodInformationArrayList2.toString())

//            FK_EmployeeStock = jsonObject3.getString("ProductName")

            Log.e(TAG, "prodInformationArrayList size2        " + prodInformationArrayList2.toString())
            val jsonObject2 = prodInformationArrayList2.getJSONObject(pos)
                    //{"ID_Product":"340","ProdName":"Current","ProvideStandBy":"1","Quantity":"1.000","Product":"","SPQuantity":"","SPAmount":"0.00","Remarks":"","isSelected":"1"}


//            jObject.put("ProductName",jsonObject.getString("ProductName"))
            jObject.put("ID_Product", jsonObject2.getString("ID_Product"))
            jObject.put("ProdName", jsonObject2.getString("ProdName"))
            jObject.put("ProvideStandBy", jsonObject2.getString("ProvideStandBy"))
            jObject.put("Quantity", jsonObject2.getString("Quantity"))
            jObject.put("Product", jsonObject3.getString("ProductName"))
            jObject.put("SPQuantity", jsonObject2.getString("SPQuantity"))
            jObject.put("SPAmount", jsonObject2.getString("SPAmount"))
            jObject.put("Remarks", jsonObject2.getString("Remarks"))
            jObject.put("isSelected", ("1"))
            jObject.put("isEnable", ("0"))
            jObject.put("SubMode", SubMode)

//            prodInformationArrayList.remove(pos)
            prodInformationArrayList2!!.put(pos, jObject)
            Log.e(TAG, "prodInformationArrayList size3        " + prodInformationArrayList2.toString())

            Log.e(TAG, "errrrrrrrrrrrr        " + position)
            Log.e(TAG, "eeeeeeeeeeeeeeeeeee   " + jObject)
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
            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
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

            setAmount()
            Log.e(TAG, "iddddd " + productName)
            Log.e(TAG, "iddddd " + productId)

        }


        if (data.equals("ProductName")) {

            proddetail = 0
            pos = position
            Log.e(TAG, "idhssss " + position)
//            getProductDetail()

            getPickupDeliStandByProductDetails()

//            val jsonObject = prodInformationArrayList.getJSONObject(position)
//            remark = jsonObject.getString("remark")
//
//            tempremark = remark.toString()
//            StandByAmount = jsonObject.getString("StandByAmount")
            setAmount()


        }

        if (data.equals("prodpriority")) {

            dialogProdPriority!!.dismiss()
            Log.e(TAG, "qqqqwwwww   " + prodPriorityArrayList)

            Log.e(TAG, "qqqqwwwww   " + position)
            val jsonObject3 = prodPriorityArrayList.getJSONObject(position)

            Log.e(TAG, "aaaaassss   " + jsonObject3)
            Log.e(TAG, "qqqqwwwww   " + prodInformationDeliveryArrayList)

            val jObject = JSONObject()
            val jsonObject2 = prodInformationArrayList2.getJSONObject(positions)


            jObject.put("ID_ProductDelivery", jsonObject2.getString("ID_ProductDelivery"))
            jObject.put("FK_Product", jsonObject2.getString("FK_Product"))
            jObject.put("FK_Category", jsonObject2.getString("FK_Category"))
            jObject.put("Product", jsonObject2.getString("Product"))
            jObject.put("Quantity", jsonObject2.getString("Quantity"))
            jObject.put("TransMode", jsonObject2.getString("TransMode"))
            jObject.put("PriorityName", jsonObject3.getString("PriorityName"))
            jObject.put("ID_Priority", jsonObject3.getString("ID_Priority"))
            jObject.put("ComplaintQty",jsonObject2.getString("ComplaintQty"))
            jObject.put("ID_ComplaintList",jsonObject2.getString("ID_ComplaintList"))
            jObject.put("ComplaintName",jsonObject2.getString("ComplaintName"))
            jObject.put("Description", jsonObject2.getString("Description"))
            jObject.put("isSelectedDelivery", jsonObject2.getString("isSelectedDelivery"))
            jObject.put("SubMode", SubMode)


            prodInformationArrayList2!!.put(positions, jObject)
            Log.e(TAG, "checkkkkkkkkkkk       " + prodInformationArrayList2.toString())

            Log.e(TAG, "dddddddd        " + position)
            Log.e(TAG, "fffdddd         " + jObject)


            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
            recyProdInformation!!.adapter = adapterHome
            adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

        }

        if (data.equals("PriorityCheck")) {

            prodpriority = 0
            positions = position
            Log.e(TAG, "dddd111111111111111111 " + positions)

            getProductPriority()


        }

        if (data.equals("ComplaintTypeclick")) {

            complainttype = 0
            positions = position
            Log.e(TAG, "dddd111111111111111111 " + positions)

            getComplaintType()


        }
        if (data.equals("ComplaintType")) {

            dialogProdComplaint!!.dismiss()

            Log.e(TAG, " ComplaintType qqqqwwwww   " + complaintTypeArrayList)
            Log.e(TAG, "ComplaintType qqqqwwwww   " + position)

            val jsonObject3 = complaintTypeArrayList.getJSONObject(position)

            Log.e(TAG, "ComplaintType aaaaassss   " + jsonObject3)
//            Log.e(TAG, "ComplaintType qqqqwwwww   " + prodInformationDeliveryArrayList)

            val jObject = JSONObject()
            val jsonObject2 = prodInformationArrayList2.getJSONObject(positions)

//
            jObject.put("ID_ProductDelivery", jsonObject2.getString("ID_ProductDelivery"))
            jObject.put("FK_Product", jsonObject2.getString("FK_Product"))
            jObject.put("FK_Category", jsonObject2.getString("FK_Category"))
            jObject.put("Product", jsonObject2.getString("Product"))
            jObject.put("Quantity", jsonObject2.getString("Quantity"))
            jObject.put("TransMode", jsonObject2.getString("TransMode"))
            jObject.put("PriorityName", jsonObject2.getString("PriorityName"))
            jObject.put("ID_Priority", jsonObject2.getString("ID_Priority"))
            jObject.put("ComplaintQty",jsonObject2.getString("ComplaintQty"))
            jObject.put("ID_ComplaintList",jsonObject3.getString("ID_ComplaintList"))
            jObject.put("ComplaintName",jsonObject3.getString("ComplaintName"))
            jObject.put("Description", jsonObject2.getString("Description"))
            jObject.put("isSelectedDelivery", jsonObject2.getString("isSelectedDelivery"))
            jObject.put("SubMode", SubMode)


            prodInformationArrayList2!!.put(positions, jObject)
            Log.e(TAG, "ComplaintType checkkkkkkkkkkk       " + prodInformationArrayList2.toString())

            Log.e(TAG, "ComplaintType dddddddd        " + position)
            Log.e(TAG, "ComplaintType fffdddd         " + jObject)


            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
            recyProdInformation!!.adapter = adapterHome
            adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)

        }

        if (data.equals("PickupDeliveryProduct")) {
            Log.e(TAG, "idhssss " + position)
            dialogProdDet!!.dismiss()
            val jsonObject4 = addproductDetailArrayList.getJSONObject(position)


            Log.e(TAG, "pppppppppppppppp   " + jsonObject4)


            val jObject = JSONObject()
            Log.e(TAG, "prodInformationArrayList size1        " + addproductDetailArrayList.toString())
            Log.e(TAG, "prodInformationArrayList size2        " + addproductDetailArrayList.toString())
            Log.e(TAG, "prodInformationArrayList size00        " + prodInformationArrayList2.toString())

            if (prodInformationArrayList2.length() == 0) {

                jObject.put("ID_Product", jsonObject4.getString("ID_Product"))
                jObject.put("ProdName", jsonObject4.getString("ProductName"))
                jObject.put("ProvideStandBy", ("0"))
                jObject.put("Quantity", ("1.00"))
                jObject.put("Product", "")
                jObject.put("SPQuantity", (""))
                jObject.put("SPAmount", (""))
                jObject.put("Remarks", (""))
                jObject.put("isSelected", ("1"))
                jObject.put("isEnable", ("0"))
                jObject.put("SubMode", SubMode)
//            jObject.put("Quantity", (""))

                prodInformationArrayList2!!.put(jObject)

            } else {

                val jsonObject2 = prodInformationArrayList2.getJSONObject(pos)

                jObject.put("ID_Product", jsonObject2.getString("ID_Product"))
                jObject.put("ProdName", jsonObject4.getString("ProductName"))
                jObject.put("ProvideStandBy", ("0"))
                jObject.put("Quantity", jsonObject2.getString("Quantity"))
                jObject.put("Product", "")
                jObject.put("SPQuantity", jsonObject2.getString("SPQuantity"))
                jObject.put("SPAmount", (""))
                jObject.put("Remarks", (""))
                jObject.put("isSelected", ("1"))
                jObject.put("isEnable", ("0"))
                jObject.put("SubMode", SubMode)

                prodInformationArrayList2!!.put(jObject)

            }



            Log.e(TAG, "prodInformationArrayList size3        " + prodInformationArrayList2.toString())

            Log.e(TAG, "errrrrrrrrrrrr        " + position)
            Log.e(TAG, "eeeeeeeeeeeeeeeeeee   " + jObject)

//            Log.e(TAG,"ggggggggg   "+jsonObject.getString("ID_Product"))

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
            recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList2)
            recyProdInformation!!.adapter = adapterHome
            adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)




            Log.e(TAG, "iddddd " + productName)
            Log.e(TAG, "iddddd " + productId)
            setAmount()

        }

        if (data.equals("paymentMethod")) {
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod = jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
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
                Log.e(TAG, "tv_NetAmount    475    " + tv_Pop_StandByTotal!!.text.toString())
                txtPayBalAmount!!.setText(
                    "" + DecimelFormatters.set2DecimelPlace(
                        (tv_Pop_StandByTotal!!.text.toString()
                            .toFloat()) - pay.toFloat() + (jsonObject!!.getString("Amount")
                            .toFloat())
                    )
                )

                Log.e(TAG, "txtPayBalAmount    117    " + txtPayBalAmount)

//                Log.e(TAG,"605   "+txtPayBalAmount!!.text.toString().toFloat())
//                var payAmnt = ((txtPayBalAmount!!.text.toString().toFloat()) + (jsonObject!!.getString("Amount").toFloat()))
//                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace(payAmnt))

            } catch (e: Exception) {

            }
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

    }

    private fun passvalue() {


        for (i in 0 until prodInformationArrayList2.length()) {
            var jsonObject = prodInformationArrayList2.getJSONObject(i)
            val jObject = JSONObject()

            Log.e(TAG, "mnmnmmnmnn  " + Productdetails)
            if (SubMode!!.equals("1")) {

                if (jsonObject5.getString("isSelected").equals("1")) {
//                if (IsSelected.equals("1")){
                    jObject.put(
                        "ID_Product",
                        ProdsuitApplication.encryptStart(jsonObject.getString("ID_Product"))
                    )
                    jObject.put(
                        "ProvideStandBy",
                        ProdsuitApplication.encryptStart(jsonObject.getString("ProvideStandBy"))
                    )
                    jObject.put(
                        "Quantity",
                        ProdsuitApplication.encryptStart(jsonObject.getString("Quantity"))
                    )
                    jObject.put(
                        "FK_StandByProduct",
                        ProdsuitApplication.encryptStart(jsonObject.getString("Product"))
                    )
                    jObject.put(
                        "StandByQuantity",
                        ProdsuitApplication.encryptStart(jsonObject.getString("SPQuantity"))
                    )
                    jObject.put(
                        "StandByAmount",
                        ProdsuitApplication.encryptStart(jsonObject.getString("SPAmount"))
                    )
                    for (i in 0 until prodDetailArrayList.length()) {
                        var jsonObject1 = prodDetailArrayList.getJSONObject(i)

//                        Log.e(TAG, "vhevk1  " + jsonObject1.getString("ID_Product") + "  " + jsonObject.getString("ID_Product"))
//                    Log.e(TAG, "vhevk2  " + )

                        if (jsonObject.getString("ID_Product")
                                .equals(jsonObject1.getString("ID_Product"))
                        ) {

                            jObject.put(
                                "FK_EmployeeStock",
                                ProdsuitApplication.encryptStart(jsonObject1.getString("ID_Product"))
                            )
                        } else {
                            jObject.put("FK_EmployeeStock", ("0"))
                        }

                    }
                    Productdetails.put(jObject)

                    Log.e(TAG, "1fffffffffffffffffffffffffffffff  " + Productdetails)

                }

                for (i in 0 until arrPayment.length()) {
                    var jsonObject = arrPayment.getJSONObject(i)
                    val jObject = JSONObject()
                    jObject.put(
                        "PaymentMethod",
                        ProdsuitApplication.encryptStart(jsonObject.getString("MethodID"))
                    )
                    jObject.put(
                        "PAmount",
                        ProdsuitApplication.encryptStart(jsonObject.getString("Amount"))
                    )
                    jObject.put(
                        "Refno",
                        ProdsuitApplication.encryptStart(jsonObject.getString("RefNo"))
                    )

                    arrayPaymentmethod.put(jObject)

                    Log.e(TAG, "1234561 arrayPaymentmethod  " + arrayPaymentmethod)
                }

                DeliveryComplaints = JSONArray()
                saveUpdatePickUpAndDelivery()


            }else if (SubMode!!.equals("2")){

                Productdetails     = JSONArray()
                arrayPaymentmethod = JSONArray()



                jObject.put("FK_Product", ProdsuitApplication.encryptStart(jsonObject.getString("FK_Product")))
                jObject.put("Qty", ProdsuitApplication.encryptStart(jsonObject.getString("ComplaintQty")))
                jObject.put("ComplaintID", ProdsuitApplication.encryptStart(jsonObject.getString("ID_ComplaintList")))
                jObject.put("Description", ProdsuitApplication.encryptStart(jsonObject.getString("Description")))
                jObject.put("Priority", ProdsuitApplication.encryptStart(jsonObject.getString("ID_Priority")))

                DeliveryComplaints.put(jObject)

                Log.e(TAG, "1234561 DeliveryComplaints  " + DeliveryComplaints)

                saveUpdatePickUpAndDelivery()
            }
        }
    }

    private fun Validation(view: View) {
        Log.e(TAG, "11111111 " + SubMode)
        try {
            Productdetails = JSONArray()
//        prodInformationArrayList2 = JSONArray()

//        val jsonObject3 = prodInformationArrayList2.getJSONObject(pos)

            if (status_id.equals("")){
                Config.snackBarWarning(context, view, "Please Select Status")
            }

            else if (prodInformationArrayList2.length() > 0) {

//            Log.e(TAG, "kkkkkkkkkkkkkkk  " + jsonObject3.getString("isSelected"))
                var isvalid = true

                for (i in 0 until prodInformationArrayList2.length()) {
                    var jsonObject = prodInformationArrayList2.getJSONObject(i)
//            val jObject = JSONObject()
                    Log.e(TAG, "assasasassaa  " + prodInformationArrayList2)
                    Log.e(TAG, "adadadddada submode  " + SubMode)


                    if (jsonObject.getString("SubMode").equals("1")) {

                        Log.e(TAG, "fdfdffd  " + jsonObject.getString("SubMode"))
                        Log.e(TAG, "rrttt  " + jsonObject.getString("isSelected"))

                        if (jsonObject.getString("isSelected").equals("1")) {
                            Log.e(TAG, "ffffffffffff  " + jsonObject.getString("isSelected"))
                            if (!jsonObject.getString("Quantity").equals("0")&&jsonObject.getString("ProvideStandBy").equals("1")) {
                                Log.e(TAG, "ProvideStandBy  " + jsonObject.getString("isSelected"))
//                                if (jsonObject.getString("ProvideStandBy").equals("1")) {

                                    if (jsonObject.getString("Product").equals("")) {

                                        isvalid = false
                                        Config.snackBarWarning(context, view, "StandByProduct is emplty")
                                    } else if (jsonObject.getString("SPQuantity").equals("")) {

                                        isvalid = false
                                        Config.snackBarWarning(context, view, "StandByQuantity is emplty")

                                    } else if (jsonObject.getString("SPAmount").equals("")) {
                                        isvalid = false
                                        Config.snackBarWarning(context, view, "StandByAmount is emplty")

                                    } else if (FK_BillType!!.equals("")) {
                                        isvalid = false
                                        Config.snackBarWarning(context, view, "Please Select Bill Type")

                                    } else if (llpaymentmethodCount!!.equals("")) {
                                        isvalid = false
                                        Config.snackBarWarning(context, view, "Please Select Payment Method")

                                    } else {
                                        passvalue()
                                        Log.e(TAG, "yyyyyyyyyyyyy  ")
                                    }

//                                }



                        }else if (!jsonObject.getString("Quantity").equals("0")){
                                isvalid = false
                                Config.snackBarWarning(context, view, "Quantity is emplty")
                            }

                            }


//                        else{
//                            validatestatus(view)
//                        }
                    }



                    if (jsonObject.getString("SubMode").equals("2")) {

                        if (prodInformationArrayList2.length() > 0)
                        {

                            Log.e(TAG, "prodInformationArrayList2 1234  " +prodInformationArrayList2 )
//                            if (jsonObject.getString("isSelected").equals("1")) {
                            if (jsonObject.getString("isSelectedDelivery").equals("1")) {

                                Log.e(TAG, "nnnnnnnnn  " + jsonObject.getString("isSelectedDelivery"))
//                                if (status_id!!.equals("")) {
//
//                                    isvalid = false
//                                    Log.e(TAG, "12  " + status_id)
//                                    Config.snackBarWarning(context, view, "Please Select Status")
//
//                                }
                                if (jsonObject.getString("ID_Priority").equals("")){

                                    isvalid = false
                                    Log.e(TAG, "123  " + jsonObject.getString("ID_Priority"))
                                    Config.snackBarWarning(context, view, "Please Select Priority")

                                }else if (jsonObject.getString("ComplaintQty").equals("")){

                                    isvalid = false
                                    Log.e(TAG, "1234  " + jsonObject.getString("ComplaintQty"))
                                    Config.snackBarWarning(context, view, "Please Enter Complaint Qty")

                                }else if (jsonObject.getString("ID_ComplaintList").equals("")){

                                    isvalid = false
                                    Log.e(TAG, "1234  " + jsonObject.getString("ID_ComplaintList"))
                                    Config.snackBarWarning(context, view, "Please Select Complaint Type")
                                }

                                else {
                                    Log.e(TAG, "bfbbfbbffb  " + status_id)
                                  //  passvalue()

                                }
                            }else {
//                                validatestatus(view)
                        }
                      }
                    }

//                if (jsonObject.getString("isSelected").equals("1")) {
//                    validatestatus(view)
//                }

                }
//            if (jsonObject3.getString("isSelected").equals("1")) {
//                    validatestatus(view)
//                }

                if (isvalid){
//                    passvalue()
                    Log.e(TAG,"12345")
                }
                else{
                    Log.e(TAG,"12345  2")
                }


            } else {
              //  validatestatus(view)
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "uuuuuuu  " + e.toString())
        }
//            Log.e(TAG, "5555555555555  " + jsonObject5.getString("ProvideStandBy"))
//           Log.e(TAG, "4444444444444  " + jsonObject5.getString("isSelected"))

    }

    private fun validatestatus(view: View) {


        if (status_id!!.equals("")) {

            Log.e(TAG, "rrrrrr  " + status_id)
            Config.snackBarWarning(context, view, "Please Select Status")
        } else {
            Log.e(TAG, "uuuuuuu  " + status_id)
//            passvalue()
            saveUpdatePickUpAndDelivery()

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



    private fun setAmount() {
        Log.e(TAG, "1234561 prodInformationArrayList  " + prodInformationArrayList)
        var standbyTotal = "0.00"
        for (i in 0 until prodInformationArrayList2.length()) {
            val jsonObject = prodInformationArrayList2.getJSONObject(i)
            if (jsonObject.getString("ProvideStandBy").equals("1")) {
                // Selected
                Log.e(TAG, "42101 prodInformationArrayList  " + i)
                StandByAmount = "0.00"
                if (jsonObject.getString("SPAmount").equals(".")) {
                    StandByAmount = "0.00"
                } else if (!jsonObject.getString("SPAmount").equals("")) {
                    StandByAmount = jsonObject.getString("SPAmount")
                }

//                if (StandByAmount.equals("0.00") || StandByAmount.equals("")) {
//                    llbilltype!!.visibility = View.GONE
//                } else {
//                    llbilltype!!.visibility = View.VISIBLE
//                }

//                if (standbytotal1 > 0.toString()){
//                    llbilltype!!.visibility = View.GONE
//                }else{
//                    llbilltype!!.visibility = View.VISIBLE
//                }

//                    Log.e(TAG,"standbyTotal  42102   "+standbyTotal+"   :   "+standbyAmount +"  :  "+jsonObject.getString("prodName"))
//                    Log.e(TAG,"standbyTotal  42103   "+DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat())+"   :   "+DecimelFormatters.set2DecimelPlace(standbyAmount.toFloat()))
                standbyTotal =
                    (DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat() + StandByAmount!!.toFloat()))

                standbytotal1 = standbyTotal

                tv_Pop_StandByTotal!!.text = standbytotal1

                if (standbytotal1.equals("0.00")){
                    llbilltype!!.visibility = View.GONE
                }else{
                    llbilltype!!.visibility = View.VISIBLE
                }

//                standbytotal1 = ""

            }
            if (jsonObject.getString("ProvideStandBy").equals("0")) {

                standbytotal1 = standbyTotal
                tv_Pop_StandByTotal!!.text = standbytotal1

                if (standbytotal1.equals("0.00")){
                    llbilltype!!.visibility = View.GONE
                }else{
                    llbilltype!!.visibility = View.VISIBLE
//                    tv_Pop_StandByTotal!!.text = "0.00"
//                    standbytotal1 = ""
                }

            }

        }
        Log.e(TAG, "standbyTotal  4313 11  " + standbytotal1)


//            txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))

//                    && jsonObject.getString("ID_BranchType").equals("")

//        if (edtPayAmount!!.text.toString().equals("")) { txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context, R.color.color_mandatory))
//            Log.e(TAG, "110   Valid   : Enter Amount")
//            Config.snackBarWarning(context, view, "Enter Amount")


    }

}