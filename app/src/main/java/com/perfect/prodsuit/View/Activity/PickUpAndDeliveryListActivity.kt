package com.perfect.prodsuit.View.Activity

//import com.perfect.prodsuit.Viewmodel.LocationUpdationPckupViewModel
import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.NotificationAdapter
import com.perfect.prodsuit.View.Adapter.PickupDeliveryListAdapter
import com.perfect.prodsuit.Viewmodel.LocationUpdateViewModel
import com.perfect.prodsuit.Viewmodel.PickDeliveryListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PickUpAndDeliveryListActivity : AppCompatActivity(), View.OnClickListener, ClickListener {

    var TAG                    = "PickUpAndDeliveryListActivity"
    lateinit var context       : Context
    private var progressDialog : ProgressDialog? = null


    private var SubMode     : String?            = ""
    private var tv_header   : TextView?          = null
    private var imgv_filter : ImageView?         = null
    private var tie_pDate   : TextInputEditText? = null
    private var tv_listCount: TextView?          = null
    private var submode                          = ""
    private var mobile                           = ""
    lateinit var pickup_and_deliverysort      : JSONArray
    lateinit var pickup_and_deliveryListArray : JSONArray



    var FK_Area            : String? = ""
    var ID_Employee        : String? = ""
    var strFromDate        : String? = ""
    var strToDate          : String? = ""
    var strCustomer        : String? = ""
    var strMobile          : String? = ""
    var strProduct         : String? = ""
    var strTicketNo        : String? = ""
    var status_id          : String? = ""
    var ID_ProductDelivery : String? = ""

    var          pickDeliveryCount        = 0
    var          loctionupdationcount     = 0
    lateinit var pickUpDeliveryViewModel : PickDeliveryListViewModel
    lateinit var locationUpdateViewModel : LocationUpdateViewModel
    lateinit var pickUpDeliveryArrayList : JSONArray
    var          recyPickUpDelivery      : RecyclerView? = null

    var filterTicketNumber : String? = ""
    var filterCustomer     : String? = ""
    var filterMobile       : String? = ""
    var filterDate         : String? = ""
    var filterModule         : String? = ""
    var filterArea         : String? = ""
    var strlatitude        : String? = ""
    var strlongitude       : String? = ""
    var clicksubmit        = ""
    private var SELECT_LOCATION: Int? = 103
    private val START_LOCATION = 100
    var ID_ImageLocation: String?= ""
    var FK_Master: String?= ""
    var mapLatitude: String?= ""
    var mapLongitude: String?= ""
    var TransMode: String?= ""
    var updateLatitude: String?= ""
    var updateLongitude: String?= ""
    var updateAddress: String?= ""
    var updateLocCount = 0
    var swipe: SwipeRefreshLayout?=null
    var adapter: NotificationAdapter? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery_list)
        context = this@PickUpAndDeliveryListActivity

        pickUpDeliveryViewModel = ViewModelProvider(this).get(PickDeliveryListViewModel::class.java)
        locationUpdateViewModel = ViewModelProvider(this).get(LocationUpdateViewModel::class.java)

        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
//            SubMode = submode
            submode = SubMode.toString()
            Log.e(TAG,"000111222255  "+submode)
        }


        FK_Area     = intent.getStringExtra("FK_Area")
        ID_Employee = intent.getStringExtra("ID_Employee")
        strFromDate = intent.getStringExtra("strFromDate")
        strToDate   = intent.getStringExtra("strToDate")
        strCustomer = intent.getStringExtra("strCustomer")
        strMobile   = intent.getStringExtra("strMobile")
        strProduct  = intent.getStringExtra("strProduct")
        strTicketNo = intent.getStringExtra("strTicketNo")
        status_id   = intent.getStringExtra("status_id")
        setRegViews()

        swipe?.setOnRefreshListener {
            getPickUpDeliveryList()
            adapter?.notifyDataSetChanged()
            swipe?.isRefreshing=false
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        swipe=findViewById(R.id.swipeRefreshLayout)
        val imback = findViewById<ImageView>(R.id.imback)
        imgv_filter = findViewById(R.id.imgv_filter)
        imback!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)


        tv_header = findViewById(R.id.tv_header)
        recyPickUpDelivery = findViewById(R.id.recyPickUpDelivery)
        tv_listCount  = findViewById(R.id.tv_listCount)
        setHeader()
        pickDeliveryCount = 0
        getPickUpDeliveryList()

    }



    private fun setHeader() {

        if (SubMode.equals("1")){
            tv_header!!.text = "Pick up"
        }
        if (SubMode.equals("2")){
            tv_header!!.text = "Delivery"
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomData()
            }

        }
    }

    private fun filterBottomData() {

        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.pick_up_filter, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(true)


            val txtReset          = view.findViewById(R.id.txtReset)           as TextView
            val txtSearch         = view.findViewById(R.id.txtSearch)          as TextView
//            val tie_pEmployee     = view.findViewById(R.id.tie_pEmployee)     as TextInputEditText
            val tie_pCustomer     = view.findViewById(R.id.tie_pCustomer)      as TextInputEditText
            val tie_pTicketNumber = view.findViewById(R.id.tie_pTicketNumber)  as TextInputEditText
                tie_pDate         = view.findViewById(R.id.tie_pDate)          as TextInputEditText
            val tie_Mobile1       = view.findViewById(R.id.tie_Mobile1)        as TextInputEditText
            val tie_Modulefilter       = view.findViewById(R.id.tie_Modulefilter)        as TextInputEditText
            val tie_Areafilter       = view.findViewById(R.id.tie_Areafilter)            as TextInputEditText


            if(SubMode!!.equals("1")){
                tie_pDate!!.setHint("Pickup Date")
                tie_pDate!!.setHintTextColor(resources.getColor(R.color.black))
            }
            if(SubMode!!.equals("2")){
                tie_pDate!!.setHint("Delivery Date")
                tie_pDate!!.setHintTextColor(resources.getColor(R.color.black))
            }

            tie_pCustomer!!.setText(""+filterCustomer)
            tie_Mobile1!!.setText(""+filterMobile)
            tie_pDate!!.setText(""+filterDate)
            tie_pTicketNumber!!.setText(""+filterTicketNumber)

            tie_pDate!!.setOnClickListener {
                openBottomSheet()

            }

            txtReset.setOnClickListener {

                tie_pCustomer!!.setText("")
                tie_pTicketNumber!!.setText("")
                tie_pDate!!.setText("")
                tie_Mobile1!!.setText("")

            }

            txtSearch.setOnClickListener {

                filterCustomer     = tie_pCustomer!!.text!!.toString().toLowerCase().trim()
                filterMobile       = tie_Mobile1!!.text!!.toString().toLowerCase().trim()
                filterDate         = tie_pDate!!.text!!.toString().toLowerCase().trim()
                filterTicketNumber = tie_pTicketNumber!!.text!!.toString().toLowerCase().trim()
                filterArea         = tie_Areafilter!!.text!!.toString().toLowerCase().trim()
                filterModule       = tie_Modulefilter!!.text!!.toString().toLowerCase().trim()


                pickup_and_deliverysort = JSONArray()
                for (k in 0 until pickUpDeliveryArrayList.length()) {
                    val jsonObject = pickUpDeliveryArrayList.getJSONObject(k)

                    Log.e(TAG,"7788899999 @   "+jsonObject.getString("PickUpTime"))
//                    Log.e(TAG,"7788899999 #    "+filterDate)

//                    val dateee = "05/11/2023 12:00AM"
                    val filterDate2 = jsonObject.getString("PickUpTime")
                    val inputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy hh:mma")

//                    Log.e(TAG,"778888888888 #    "+inputFormat)

                    val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val date = outputFormat.format(inputFormat.parse(filterDate2))

                    Log.e(TAG,"778888888888 #    "+date)

                    if ((jsonObject.getString("ReferenceNo")!!.toLowerCase().trim().contains(filterTicketNumber!!))
                        && (jsonObject.getString("CustomerName")!!.toLowerCase().trim().contains(filterCustomer!!))
                        && (jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(filterMobile!!))
                        && (jsonObject.getString("Area")!!.toLowerCase().trim().contains(filterArea!!))
                        && (jsonObject.getString("Module")!!.toLowerCase().trim().contains(filterModule!!))
                        && date.toLowerCase().trim().contains(filterDate!!)){

                           pickup_and_deliverysort.put(jsonObject)

                    }else{
                        //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                    }
                    Log.e(TAG,"444444555555 #    "+date)
                    Log.e(TAG,"444444555555 *    "+filterDate)
                    Log.e(TAG,"4444445555551 *    "+pickup_and_deliverysort)
                }
                dialog1.dismiss()
                val adapter = PickupDeliveryListAdapter(this@PickUpAndDeliveryListActivity, pickup_and_deliverysort,SubMode!!)
                recyPickUpDelivery!!.adapter = adapter
                adapter.setClickListener(this@PickUpAndDeliveryListActivity)

                tv_listCount!!.setText(""+pickup_and_deliverysort.length())
            }


            dialog1!!.setContentView(view)
            dialog1.show()

            dialog1.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }



    }

    private fun validate(dialog1: BottomSheetDialog){

            val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                var date: Date? = null
                date = inputFormat.parse(strFromDate)
                strFromDate = outputFormat.format(date)
                Log.e(TAG,"DATE   1302   "+strFromDate)


            dialog1.dismiss()


    }

    private fun openBottomSheet(){

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)


        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker = view.findViewById<DatePicker>(R.id.date_Picker1)


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()


            try {
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }
                tie_pDate!!.setText(""+strDay+"/"+strMonth+"/"+strYear)
                Log.e(TAG,"tie_pDate   "+strDay+"/"+strMonth+"/"+strYear)

            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(true)
        dialog!!.setContentView(view)

        dialog.show()

    }

    private fun getPickUpDeliveryList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickUpDeliveryViewModel.getPickDeliveryList(this,submode,strCustomer!!,strFromDate!!,strToDate!!,strMobile!!,strProduct!!,strTicketNo!!,FK_Area!!,status_id!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (pickDeliveryCount == 0) {
                                    pickDeliveryCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   104   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("PickUpDeliveryDetails")
                                        pickUpDeliveryArrayList = jobjt.getJSONArray("PickUpDeliveryDetailsList")
                                        if (pickUpDeliveryArrayList.length() > 0) {
                                            pickup_and_deliverysort = JSONArray()
                                            for (k in 0 until pickUpDeliveryArrayList.length()) {
                                                val jsonObject = pickUpDeliveryArrayList.getJSONObject(k)
                                                pickup_and_deliverysort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+pickup_and_deliverysort.length())

                                            val lLayout = GridLayoutManager(this@PickUpAndDeliveryListActivity, 1)
                                            recyPickUpDelivery!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = PickupDeliveryListAdapter(this@PickUpAndDeliveryListActivity, pickUpDeliveryArrayList,SubMode!!)
                                            recyPickUpDelivery!!.adapter = adapter
                                            adapter.setClickListener(this@PickUpAndDeliveryListActivity)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryListActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    override fun onClick(position: Int, data: String, view: View) {

//        Log.e(TAG, "caall   11104   " + position)
        if (data.equals("pickupDelivery")){
            Config.disableClick(view)
            var jsonObject: JSONObject? = pickup_and_deliverysort.getJSONObject(position)
            ID_ProductDelivery = jsonObject!!.getString("ID_ProductDelivery")
            TransMode = jsonObject!!.getString("TransMode")
            val i = Intent(this@PickUpAndDeliveryListActivity, PickUpAndDeliveryUpdateActivity::class.java)
            i.putExtra("SubMode",SubMode)
            i.putExtra("ID_ProductDelivery",ID_ProductDelivery)
            i.putExtra("TransMode",TransMode)
            Log.e(TAG, "ID_ProductDelivery   55412222   " + ID_ProductDelivery)
            startActivity(i)
        }

        if (data.equals("pickDelCall")){
            Config.disableClick(view)
             var jsonObject: JSONObject? = pickUpDeliveryArrayList.getJSONObject(position)
             val mobileno = jsonObject!!.getString("Mobile")
            Log.e(TAG, "caall   11104   " + mobileno)
            if (mobileno.equals("")){
                Config.snackBarWarning(context,view,"Invalid mobile number")
            }
            else{
                callFunction(mobileno)
            }

        }

        if (data.equals("pickDelLocation")){
            Config.disableClick(view)
            var jsonObject: JSONObject? = pickUpDeliveryArrayList.getJSONObject(position)

//            strlatitude  = jsonObject!!.getString("LocLattitude")
//            strlongitude = jsonObject!!.getString("LocLongitude")

            ID_ImageLocation = jsonObject!!.getString("FK_ImageLocation")
            FK_Master = jsonObject!!.getString("FK_Master")
//            mapLatitude = jsonObject!!.getString("LocLattitude")
//            mapLongitude = jsonObject!!.getString("LocLongitude")
            strlatitude  = jsonObject!!.getString("LocLattitude")
            strlongitude = jsonObject!!.getString("LocLongitude")

//            checkLocationPermission(view)

            Log.e(TAG, "location1122   " + strlatitude + "    " +strlongitude)

            if (strlatitude.equals("") || strlongitude.equals("")){
                showSnackBar("Location Not Found", this)
                val i = Intent(this@PickUpAndDeliveryListActivity, LocationViewActivity::class.java)
                i.putExtra("mode","0")
                i.putExtra("longitude",strlongitude)
                i.putExtra("latitude",strlatitude)
                startActivityForResult(i, SELECT_LOCATION!!)
//                startActivity(i)
            }else{
                //loadLocation()
                val i = Intent(this@PickUpAndDeliveryListActivity, LocationViewActivity::class.java)
                i.putExtra("mode","1")
                i.putExtra("longitude",strlongitude)
                i.putExtra("latitude",strlatitude)
                startActivityForResult(i, SELECT_LOCATION!!)
//                startActivity(i)
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == START_LOCATION) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
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

        if (requestCode == SELECT_LOCATION) {

            if (data != null) {

                try {
                    Log.e(TAG,"address  1174   "+data.getStringExtra("address"))
                    updateLatitude = data.getStringExtra("mapLatitude")
                    updateLongitude = data.getStringExtra("mapLongitude")
                    updateAddress = data.getStringExtra("address")
                    updateLocCount = 0

                    Log.v("dfsdfds34343f", "kkdkkdkd " + updateLatitude)
                    Log.v("dfsdfds34343f", "wyeyeyye " + updateLongitude)

                    updateLocation()
                }catch (e: Exception){

                }
            }
        }
    }

    private fun startStopWork(latitude: Double, longitude: Double, address: String?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        val current = LocalDateTime.now().format(formatter)
        Log.v("dfsdfds34343f", "current " + current)

    }

    private fun showSnackBar(message: String, activity: PickUpAndDeliveryListActivity) {

        if (null != activity && null != message) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun updateLocation() {
//        TransMode = "CUSA"
        when (Config.ConnectivityUtils.isConnected(this)) {

            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                locationUpdateViewModel.getLocationUpdate(this,TransMode!!, ID_ImageLocation!!, FK_Master!!,updateLatitude!!,updateLongitude!!,updateAddress!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (updateLocCount == 0) {
                                    updateLocCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg 118855669944   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
//                                        val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                        customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                        if (customerArrayList.length() > 0) {
//                                            Log.e(TAG, "msg   1052   " + msg)
//
//                                            customerSearchPopup(customerArrayList)
//
//
//                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryListActivity,
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
                            progressDialog!!.dismiss()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG).show()
                progressDialog!!.dismiss()
            }
        }
    }


    private fun callFunction(mobileno: String) {
        val ALL_PERMISSIONS = 101

        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE
        )
        if (ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.CALL_PHONE
            ) + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.RECORD_AUDIO
            )
            + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.READ_PHONE_STATE
            )
            + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
//
//                //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
//                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
//                startActivity(intent)

            if (mobileno.equals("")){
                Toast.makeText(applicationContext,""+Config.INVALID_MOBILE,Toast.LENGTH_SHORT).show()
            }else{
//                    intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
//                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileno))
//                startActivity(intent)

                if (mobileno.equals("")){
                    Toast.makeText(applicationContext,""+Config.INVALID_MOBILE,Toast.LENGTH_SHORT).show()
                }else{
//                    intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                    intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileno))
                    startActivity(intent)
                }

            }
        }

    }

    private fun checkLocationPermission(v: View) {
        val ALL_PERMISSIONS = 102

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val i = Intent(this@PickUpAndDeliveryListActivity, LocationCollectionActivity::class.java)
            startActivity(i)

        }
    }

    override fun onRestart() {
        super.onRestart()
        pickDeliveryCount = 0
        getPickUpDeliveryList()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}


