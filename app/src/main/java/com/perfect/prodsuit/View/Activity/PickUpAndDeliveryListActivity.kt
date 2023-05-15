package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.PickupDeliveryListAdapter
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.PickDeliveryListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
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
    lateinit var pickUpDeliveryViewModel : PickDeliveryListViewModel
    lateinit var pickUpDeliveryArrayList : JSONArray
    var          recyPickUpDelivery      : RecyclerView? = null

    var filterTicketNumber : String? = ""
    var filterCustomer     : String? = ""
    var filterMobile       : String? = ""
    var filterDate         : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery_list)
        context = this@PickUpAndDeliveryListActivity

        pickUpDeliveryViewModel = ViewModelProvider(this).get(PickDeliveryListViewModel::class.java)

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
    }

    private fun setRegViews() {
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

                pickup_and_deliverysort = JSONArray()

                for (k in 0 until pickUpDeliveryArrayList.length()) {
                    val jsonObject = pickUpDeliveryArrayList.getJSONObject(k)
                    if ((jsonObject.getString("ReferenceNo")!!.toLowerCase().trim().contains(filterTicketNumber!!))
                        && (jsonObject.getString("CustomerName")!!.toLowerCase().trim().contains(filterCustomer!!))
                        && (jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(filterMobile!!))
                        && (jsonObject.getString("PickUpTime")!!.toLowerCase().trim().contains(filterDate!!))){
                           Log.e(TAG,"8569745    "+filterDate)
                           pickup_and_deliverysort.put(jsonObject)
                    }else{
                        //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                    }

                }
                dialog1.dismiss()
                val adapter = PickupDeliveryListAdapter(this@PickUpAndDeliveryListActivity, pickup_and_deliverysort,SubMode!!)
                recyPickUpDelivery!!.adapter = adapter
                adapter.setClickListener(this@PickUpAndDeliveryListActivity)


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
                tie_pDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                Log.e(TAG,"tie_pDate   "+strDay+"-"+strMonth+"-"+strYear)

            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(true)
        dialog!!.setContentView(view)

        dialog.show()

    }

    private fun validatedata(dialog1: BottomSheetDialog){


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
                pickUpDeliveryViewModel.getPickDeliveryList(this,submode,ID_Employee!!,strCustomer!!,strFromDate!!,strToDate!!,strMobile!!,strProduct!!,strTicketNo!!,FK_Area!!,status_id!!)!!.observe(
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onClick(position: Int, data: String, view: View) {

//        Log.e(TAG, "caall   11104   " + position)
        if (data.equals("pickupDelivery")){
            Config.disableClick(view)
            var jsonObject: JSONObject? = pickUpDeliveryArrayList.getJSONObject(position)
            ID_ProductDelivery = jsonObject!!.getString("ID_ProductDelivery")
            val i = Intent(this@PickUpAndDeliveryListActivity, PickUpAndDeliveryUpdateActivity::class.java)
            i.putExtra("SubMode",SubMode)
            i.putExtra("ID_ProductDelivery",ID_ProductDelivery)
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
            checkLocationPermission(view)
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

                //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)
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
    }
}


