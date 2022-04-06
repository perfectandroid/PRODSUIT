package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.View.Adapter.LeadByAdapter
import com.perfect.prodsuit.View.Adapter.LeadFromAdapter
import com.perfect.prodsuit.View.Adapter.LeadThroughAdapter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.MediaTypeAdapter
import com.perfect.prodsuit.Viewmodel.LeadByViewModel
import com.perfect.prodsuit.Viewmodel.LeadFromViewModel
import com.perfect.prodsuit.Viewmodel.LeadThroughViewModel
import com.perfect.prodsuit.Viewmodel.MediaTypeViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class LeadGenerationActivity : AppCompatActivity() , View.OnClickListener , ItemClickListener {

    val TAG : String = "LeadGenerationActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    private var llCustomer: LinearLayout? = null
    private var llLeadFrom: LinearLayout? = null
    private var llleadthrough: LinearLayout? = null
    private var llleadby: LinearLayout? = null
    private var llproduct: LinearLayout? = null
    private var llmediatype: LinearLayout? = null
    private var lldate: LinearLayout? = null
    private var lllocation: LinearLayout? = null

    private var txtcustomer: TextView? = null
    private var txtleadfrom: TextView? = null
    private var txtleadthrough: TextView? = null
    private var txtleadby: TextView? = null
    private var txtproduct: TextView? = null
    private var txtMediatype: TextView? = null
    private var txtDate: TextView? = null
    private var txtLocation: TextView? = null

    private var CUSTOMER_SEARCH: Int? = 101
    private var SELECT_PRODUCT: Int? = 102
    private var SELECT_LOCATION: Int? = 103

    lateinit var leadThroughViewModel: LeadThroughViewModel
    lateinit var leadFromViewModel: LeadFromViewModel
    lateinit var leadByViewModel: LeadByViewModel
    lateinit var mediaTypeViewModel: MediaTypeViewModel

    var recyLeadFrom: RecyclerView? = null
    var recyLeadThrough: RecyclerView? = null
    var recyLeadby: RecyclerView? = null
    var recyMediaType: RecyclerView? = null

    private var imgvupload1: ImageView? = null
    private var imgvupload2: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    private var strImage: String? = null
    private var destination: File? = null
    private var image1 = ""
    private var image2 = ""
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    lateinit var leadFromArrayList : JSONArray
    lateinit var leadThroughArrayList : JSONArray
    lateinit var leadByArrayList : JSONArray
    lateinit var mediaTypeArrayList : JSONArray


    var dialogLeadFrom : Dialog? = null
    var dialogLeadThrough : Dialog? = null
    var dialogLeadBy : Dialog? = null
    var dialogMediaType : Dialog? = null

    companion object {
        var ID_LeadFrom : String?= ""
        var ID_LeadThrough : String?= ""
        var ID_CollectedBy : String?= ""
        var ID_MediaMaster : String?= ""

        var Customer_Mode : String?= ""
        var ID_Customer : String?= ""
        var Customer_Name : String?= ""
        var Customer_Mobile : String?= ""
        var Customer_Email : String?= ""
        var Customer_Address : String?= ""


        var locAddress : String?= ""
        var locCity : String?= ""
        var locState : String?= ""
        var locCountry : String?= ""
        var locpostalCode : String?= ""
        var locKnownName : String?= ""
        var strLatitude : String?= ""
        var strLongitue : String?= ""




    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_leadgeneration)
        context = this@LeadGenerationActivity

        leadFromViewModel = ViewModelProvider(this).get(LeadFromViewModel::class.java)
        leadThroughViewModel = ViewModelProvider(this).get(LeadThroughViewModel::class.java)
        leadByViewModel = ViewModelProvider(this).get(LeadByViewModel::class.java)
        mediaTypeViewModel = ViewModelProvider(this).get(MediaTypeViewModel::class.java)

        setRegViews()
        bottombarnav()
        clearData()


    }

    private fun clearData() {
        ID_LeadFrom  = ""
        ID_LeadThrough = ""
        ID_CollectedBy = ""
        ID_MediaMaster = ""

        Customer_Mode = ""
        ID_Customer = ""
        Customer_Name = ""
        Customer_Mobile = ""
        Customer_Email = ""
        Customer_Address = ""

        locAddress = ""
        locCity = ""
        locState = ""
        locCountry = ""
        locpostalCode = ""
        locKnownName = ""
        strLatitude = ""
        strLongitue = ""
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        llCustomer = findViewById<LinearLayout>(R.id.llCustomer)
        llLeadFrom = findViewById<LinearLayout>(R.id.llLeadFrom)
        llleadthrough = findViewById<LinearLayout>(R.id.llleadthrough)
        llleadby = findViewById<LinearLayout>(R.id.llleadby)
        llproduct = findViewById<LinearLayout>(R.id.llproduct)
        llmediatype = findViewById<LinearLayout>(R.id.llmediatype)
        lldate = findViewById<LinearLayout>(R.id.lldate)
        lllocation = findViewById<LinearLayout>(R.id.lllocation)

        txtcustomer = findViewById<TextView>(R.id.txtcustomer)
        txtleadfrom = findViewById<TextView>(R.id.txtleadfrom)
        txtleadthrough = findViewById<TextView>(R.id.txtleadthrough)
        txtleadby = findViewById<TextView>(R.id.txtleadby)
        txtproduct = findViewById<TextView>(R.id.txtproduct)
        txtMediatype = findViewById<TextView>(R.id.txtMediatype)
        txtDate = findViewById<TextView>(R.id.txtDate)
        txtLocation = findViewById<TextView>(R.id.txtLocation)

        imback!!.setOnClickListener(this)
        llCustomer!!.setOnClickListener(this)
        llLeadFrom!!.setOnClickListener(this)
        llleadthrough!!.setOnClickListener(this)
        llleadby!!.setOnClickListener(this)
        llproduct!!.setOnClickListener(this)
        llmediatype!!.setOnClickListener(this)
        lldate!!.setOnClickListener(this)
        lllocation!!.setOnClickListener(this)

        imgvupload1 = findViewById(R.id.imgv_upload1)
        imgvupload2 = findViewById(R.id.imgv_upload2)

        imgvupload1!!.setOnClickListener(this)
        imgvupload2!!.setOnClickListener(this)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtDate!!.setText(currentDate)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llCustomer->{
//                val intent = Intent(this@LeadGenerationActivity, CustomerSearchActivity::class.java)
//                CUSTOMER_SEARCH?.let { startActivityForResult(intent, it) } // Activity is started with requestCode 2
                val intent = Intent(this@LeadGenerationActivity, CustomerSearchActivity::class.java)
                startActivityForResult(intent, CUSTOMER_SEARCH!!);
            }
            R.id.llleadthrough->{

                if (ID_LeadFrom.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Select Lead From", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                }else{
                    getLeadThrough(v)
                }


            }
            R.id.llLeadFrom->{

                getLeadFrom(v)
            }

            R.id.imgv_upload1->{
                try
                {
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationActivity,v)
                    strImage="1"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }
            R.id.imgv_upload2->{
                try {
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationActivity,v)
                    strImage="2"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }

            R.id.llleadby->{


                getLeadBy(v)

            }

            R.id.llproduct->{


                val intent = Intent(this@LeadGenerationActivity, ProductActivity::class.java)
                startActivityForResult(intent, SELECT_PRODUCT!!);

            }

            R.id.llmediatype->{
                getMediaType()
            }
            R.id.lldate->{
                datePickerPopup()
            }

            R.id.lllocation->{

                val intent = Intent(this@LeadGenerationActivity, LocationPickerActivity::class.java)
                startActivityForResult(intent, SELECT_LOCATION!!);
            }
        }
    }

    private fun datePickerPopup() {
        try {

            val dialogDate = Dialog(this)
            dialogDate!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDate!! .setContentView(R.layout.dialog_datepicker)
            dialogDate!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogDate.setCancelable(false)

            val date_Picker = dialogDate!! .findViewById(R.id.date_Picker) as DatePicker
            val txtcancel = dialogDate!! .findViewById(R.id.txtcancel) as TextView
            val txtok = dialogDate!! .findViewById(R.id.txtok) as TextView

            date_Picker.minDate = Calendar.getInstance().timeInMillis

            txtok.setOnClickListener {
                dialogDate.dismiss()
                val day: Int = date_Picker.getDayOfMonth()
                val mon: Int = date_Picker.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker.getYear()



                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()

                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                txtDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)


            }

            txtcancel.setOnClickListener {
                dialogDate.dismiss()
            }

            dialogDate!!.show()
            // dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getLeadBy(v: View) {

        var countLeadBy = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadByViewModel.getLeadBy(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   228   "+msg.length)
                            Log.e(TAG,"msg   228   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CollectedByUsersList")
                                leadByArrayList = jobjt.getJSONArray("CollectedByUsers")
                                if (leadByArrayList.length()>0){
                                    if (countLeadBy == 0){
                                        countLeadBy++
                                        leadByPopup(leadByArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadByPopup(leadByArrayList: JSONArray) {

        try {

            dialogLeadBy = Dialog(this)
            dialogLeadBy!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadBy!! .setContentView(R.layout.lead_by_popup)
            dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadby = dialogLeadBy!! .findViewById(R.id.recyLeadby) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadby!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            recyLeadby!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadBy!!.show()
            dialogLeadBy!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLeadFrom(v: View) {
        var countLeadFrom = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadFromViewModel.getLeadFrom(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   91   "+msg.length)
                            Log.e(TAG,"msg   91   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadFromDetailsList")
                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
                                if (leadFromArrayList.length()>0){
                                    if (countLeadFrom == 0){
                                        countLeadFrom++
                                        leadFromPopup(leadFromArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadFromPopup(leadFromArrayList: JSONArray) {

        try {

            dialogLeadFrom = Dialog(this)
            dialogLeadFrom!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadFrom!! .setContentView(R.layout.lead_from_popup)
            dialogLeadFrom!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadFrom = dialogLeadFrom!! .findViewById(R.id.recyLeadFrom) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadFrom!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadFromAdapter(this@LeadGenerationActivity, leadFromArrayList)
            recyLeadFrom!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadFrom!!.show()
            dialogLeadFrom!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLeadThrough(v: View) {
        var countLeadThrough = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadThroughViewModel.getLeadThrough(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   267   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadThroughDetailsList")
                                leadThroughArrayList = jobjt.getJSONArray("LeadThroughDetails")
                                if (leadThroughArrayList.length()>0){
                                    if (countLeadThrough == 0){
                                        countLeadThrough++
                                        leadThroghPopup(leadThroughArrayList)
                                    }


                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadThroghPopup(leadThroughArrayList: JSONArray) {

        try {

            dialogLeadThrough = Dialog(this)
            dialogLeadThrough!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadThrough!! .setContentView(R.layout.lead_through_popup)
            dialogLeadThrough!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadThrough = dialogLeadThrough!! .findViewById(R.id.recyLeadThrough) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadThrough!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadThroughAdapter(this@LeadGenerationActivity, leadThroughArrayList)
            recyLeadThrough!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadThrough!!.show()
            dialogLeadThrough!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getMediaType() {
        var countMediatype = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                mediaTypeViewModel.getMediaType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   510   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("MediaTypeDetails")
                                mediaTypeArrayList = jobjt.getJSONArray("MediaTypeDetailsList")
                                if (mediaTypeArrayList.length()>0){
                                    if (countMediatype == 0){
                                        countMediatype++
                                        mediaTypePopup(mediaTypeArrayList)
                                    }


                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun mediaTypePopup(mediaTypeArrayList: JSONArray) {

        try {

            dialogMediaType = Dialog(this)
            dialogMediaType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMediaType!! .setContentView(R.layout.mediatype_popup)
            dialogMediaType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyMediaType = dialogMediaType!! .findViewById(R.id.recyMediaType) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyMediaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = MediaTypeAdapter(this@LeadGenerationActivity, mediaTypeArrayList)
            recyMediaType!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogMediaType!!.show()
            dialogMediaType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@LeadGenerationActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@LeadGenerationActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@LeadGenerationActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }

            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG","onActivityResult  256   "+requestCode+ "   "+resultCode+ "  "+data)
        if (requestCode == CUSTOMER_SEARCH){
            if (data!=null){
                txtcustomer!!.text = data!!.getStringExtra("Name")

                Customer_Mode     = data!!.getStringExtra("Customer_Mode")
                ID_Customer       = data!!.getStringExtra("ID_Customer")
                Customer_Name     = data!!.getStringExtra("Name")
                Customer_Mobile   = data!!.getStringExtra("MobileNumber")
                Customer_Email    = data!!.getStringExtra("Email")
                Customer_Address  = data!!.getStringExtra("Address")
            }

        }

        if (requestCode == SELECT_LOCATION){
            if (data!=null){
            //    txtcustomer!!.text = data!!.getStringExtra("Name")
                if (data.getStringExtra("address").equals("")){
                    txtLocation!!.setText(data.getStringExtra("address"))
                }else{
                    txtLocation!!.setText(data.getStringExtra("city"))
                }

                locAddress      = data.getStringExtra("address")
                locCity         = data.getStringExtra("city")
                locState        = data.getStringExtra("state")
                locCountry      = data.getStringExtra("country")
                locpostalCode   = data.getStringExtra("postalCode")
                locKnownName    = data.getStringExtra("knownName")
                strLatitude     = data.getStringExtra("strLatitude")
                strLongitue     = data.getStringExtra("strLongitue")
            }

        }
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if(strImage.equals("1")) {

                        //   val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        imgvupload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        if (image1 != null) {
                        }
                    }
                    if(strImage.equals("2")) {

                        //  val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                        imgvupload2!!.setImageURI(contentURI)
                        image2 = getRealPathFromURI(selectedImageUri)
                        if (image2 != null) {
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@LeadGenerationActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (requestCode == CAMERA) {
            try {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                else {

                    val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                    val bytes = ByteArrayOutputStream()
                    thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                    destination = File(
                        (Environment.getExternalStorageDirectory()).toString() + "/" +
                                getString(R.string.app_name),
                        "IMG_" + System.currentTimeMillis() + ".jpg"
                    )
                    val fo: FileOutputStream

                    try {
                        if (!destination!!.getParentFile().exists()) {
                            destination!!.getParentFile().mkdirs()
                        }
                        if (!destination!!.exists()) {
                            destination!!.createNewFile()
                        }
                        fo = FileOutputStream(destination)
                        fo.write(bytes.toByteArray())
                        fo.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    if (strImage.equals("1")) {
                        image1 = destination!!.getAbsolutePath()
                        destination = File(image1)


                        val myBitmap = BitmapFactory.decodeFile(destination.toString())
                        //  val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        if (imgvupload1 != null) {
                            imgvupload1!!.setImageBitmap(myBitmap)
                        }
                        imgvupload1!!.setImageBitmap(myBitmap)

                        if (image1 != null) {

                        }
                    }
                    if (strImage.equals("2")) {
                        image2 = destination!!.getAbsolutePath()
                        destination = File(image2)

                        val myBitmap = BitmapFactory.decodeFile(destination.toString())
                        //   val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                        if (imgvupload2 != null) {
                            imgvupload2!!.setImageBitmap(myBitmap)
                        }
                        imgvupload2!!.setImageBitmap(myBitmap)

                        if (image2 != null) {

                        }
                    }

                }
            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@LeadGenerationActivity, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select From")
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(pictureDialogItems   ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun checkCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;

    }

    private fun takePhotoFromCamera() {
        val photo =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photo, CAMERA)
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
    }
    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (getContentResolver() != null) {
            val cursor = getContentResolver().query(uri, null, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor!!.getString(idx)
                cursor!!.close()
            }
        }
        return path
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("leadfrom")){
            dialogLeadFrom!!.dismiss()
            val jsonObject = leadFromArrayList.getJSONObject(position)
            Log.e(TAG,"ID_LeadFrom   "+jsonObject.getString("ID_LeadFrom"))
            ID_LeadFrom = jsonObject.getString("ID_LeadFrom")
            txtleadfrom!!.text = jsonObject.getString("LeadFromName")
        }
        if (data.equals("leadthrough")){
            dialogLeadThrough!!.dismiss()
            val jsonObject = leadThroughArrayList.getJSONObject(position)
            Log.e(TAG,"ID_LeadThrough   "+jsonObject.getString("ID_LeadThrough"))
            ID_LeadThrough = jsonObject.getString("ID_LeadThrough")
            txtleadthrough!!.text = jsonObject.getString("LeadThroughName")

        }

        if (data.equals("leadby")){
            dialogLeadBy!!.dismiss()
            val jsonObject = leadByArrayList.getJSONObject(position)
            Log.e(TAG,"ID_CollectedBy   "+jsonObject.getString("ID_CollectedBy"))
            ID_CollectedBy = jsonObject.getString("ID_CollectedBy")
            txtleadby!!.text = jsonObject.getString("Name")

        }
        if (data.equals("mediatype")){
            dialogMediaType!!.dismiss()
            val jsonObject = mediaTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_MediaMaster   "+jsonObject.getString("ID_MediaMaster"))
            ID_MediaMaster = jsonObject.getString("ID_MediaMaster")
            txtMediatype!!.text = jsonObject.getString("MdaName")

        }
    }
}