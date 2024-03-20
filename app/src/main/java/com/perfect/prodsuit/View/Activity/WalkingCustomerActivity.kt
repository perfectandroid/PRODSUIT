package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.CalllogModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AssignedToAdapter
import com.perfect.prodsuit.View.Adapter.CallLogListAdapter
import com.perfect.prodsuit.View.Adapter.ProductCategoryAdapter
import com.perfect.prodsuit.View.Adapter.ProductDetailAdapter
import com.perfect.prodsuit.Viewmodel.AssignedToWalkingViewModel
import com.perfect.prodsuit.Viewmodel.CreateWalkingCustomerViewModel
import com.perfect.prodsuit.Viewmodel.ProductCategoryViewModel
import com.perfect.prodsuit.Viewmodel.ProductDetailViewModel
import com.perfect.prodsuit.Viewmodel.WalkExistViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class WalkingCustomerActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG = "WalkingCustomerActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var til_CustomerName: TextInputLayout? = null
    private var til_Phone: TextInputLayout? = null
    private var til_AssignedTo: TextInputLayout? = null
    private var til_AssignedDate: TextInputLayout? = null
    private var til_AttachVoice: TextInputLayout? = null
    private var til_Description: TextInputLayout? = null

    private var til_Category: TextInputLayout? = null
    private var til_Product: TextInputLayout? = null
    private var til_Project: TextInputLayout? = null

    private var tie_CustomerName: TextInputEditText? = null
    private var tie_Phone: TextInputEditText? = null
    private var tie_AssignedTo: TextInputEditText? = null
    private var tie_AssignedDate: TextInputEditText? = null
    private var tie_Attachvoice: TextInputEditText? = null
    private var tie_Attachvoice1: TextInputEditText? = null
    private var tie_Attachvoice2: TextInputEditText? = null
    private var tie_Description: TextInputEditText? = null

    private var tie_Category: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null
    private var tie_Project: TextInputEditText? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null
    private var imcontactlog: ImageView? = null
    private var lvCustno: ListView? = null

    var dateSelectMode: Int = 0
    var strCustomer: String? = ""
    var strPhone: String? = ""
    var strAssignedDate: String? = ""
    var strDescription: String? = ""
    var ID_AssignedTo: String? = ""
    var leadByMobileNo: String? = "[]"

    var assignedToCount: Int = 0
    lateinit var assignedToWalkingViewModel: AssignedToWalkingViewModel
    lateinit var assignedToList: JSONArray
    lateinit var assignedToSortList: JSONArray
    private var dialogassignedTo: Dialog? = null
    var recyassignedTo: RecyclerView? = null

    var saveCount: Int = 0
    lateinit var createWalkingCustomerViewModel: CreateWalkingCustomerViewModel
    var saveAttendanceMark = false

    var walkExistCount: Int = 0
    lateinit var walkExistViewModel: WalkExistViewModel
    lateinit var walkExistList: JSONArray

    // Add Product 23-01-2024

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var productDetailViewModel: ProductDetailViewModel

    lateinit var prodCategoryArrayList: JSONArray
    lateinit var prodCategorySort: JSONArray

    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray

    var recyProdCategory: RecyclerView? = null
    var recyProdDetail: RecyclerView? = null

    private var dialogProdCat: Dialog? = null
    private var dialogProdDet: Dialog? = null

    var prodcategory = 0
    var proddetail = 0

    var ID_Category: String? = "0"
    var ID_Product: String? = ""
    var checkProject: String = "1"
    var strProjectName: String = ""

    // Add Product 23-01-2024

    private var array_walkingUpdate = JSONArray()


    private var array_sort = ArrayList<CalllogModel>()
    private var calllogArrayList = ArrayList<CalllogModel>()
    private var sadapter: CallLogListAdapter? = null
    private var textlength = 0
    private var etxtsearch:EditText? =null
    private var tv_callhistory:TextView? =null

    private var RECORD_PLAY: Int? = 1038
    var voiceData: String? = ""
    var   VoiceLabel: String? = ""
    var voiceData2: String? = ""
    private var voicedataByte: ByteArray? = null
    private var voicedataByte2: ByteArray? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_walking_customer)
        assignedToWalkingViewModel =
            ViewModelProvider(this).get(AssignedToWalkingViewModel::class.java)
        createWalkingCustomerViewModel =
            ViewModelProvider(this).get(CreateWalkingCustomerViewModel::class.java)
        walkExistViewModel = ViewModelProvider(this).get(WalkExistViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        context = this@WalkingCustomerActivity

//        val intent = intent
//
//        val from = intent.getStringExtra("from")
//
//        Log.i("response1212","from=="+from)


        setRegViews()
        // Log.i("response2323","okkkkk")
        defaultLoad()
        checkAttendance()

        til_Phone!!.setEndIconOnClickListener {
            Config.disableClick(it)
            strPhone = tie_Phone!!.text.toString()
            Config.Utils.hideSoftKeyBoard(context, it)

            if (strPhone.equals("") || strPhone!!.length < 10) {
                Config.snackBars(context, it, "Enter Minimum 10 digit Phone Number")
            } else {
                walkExistCount = 0
                getExistingCustomerData()
            }
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        val imVoice = findViewById<ImageView>(R.id.imVoice)
        imVoice!!.setOnClickListener(this)

        tie_CustomerName = findViewById<TextInputEditText>(R.id.tie_CustomerName)
        tie_Phone = findViewById<TextInputEditText>(R.id.tie_Phone)
        tie_AssignedDate = findViewById<TextInputEditText>(R.id.tie_AssignedDate)
        tie_Attachvoice = findViewById<TextInputEditText>(R.id.tie_Attachvoice)
        tie_Attachvoice1 = findViewById<TextInputEditText>(R.id.tie_Attachvoice1)
        tie_Attachvoice2 = findViewById<TextInputEditText>(R.id.tie_Attachvoice2)
        tie_AssignedTo = findViewById<TextInputEditText>(R.id.tie_AssignedTo)
        tie_Description = findViewById<TextInputEditText>(R.id.tie_Description)

        til_CustomerName = findViewById<TextInputLayout>(R.id.til_CustomerName)
        til_Phone = findViewById<TextInputLayout>(R.id.til_Phone)
        til_AssignedDate = findViewById<TextInputLayout>(R.id.til_AssignedDate)
        til_AttachVoice = findViewById(R.id.til_AttachVoice)
        til_AssignedTo = findViewById<TextInputLayout>(R.id.til_AssignedTo)
        til_Description = findViewById<TextInputLayout>(R.id.til_Description)

        til_Category = findViewById<TextInputLayout>(R.id.til_Category)
        til_Product = findViewById<TextInputLayout>(R.id.til_Product)
        til_Project = findViewById<TextInputLayout>(R.id.til_Project)

        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)
        tie_Project = findViewById<TextInputEditText>(R.id.tie_Project)



        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        imcontactlog = findViewById<ImageView>(R.id.imcontactlog)



        tie_AssignedDate!!.setOnClickListener(this)
        tie_Attachvoice!!.setOnClickListener(this)
        tie_Attachvoice1!!.setOnClickListener(this)
        tie_Attachvoice2!!.setOnClickListener(this)
        tie_AssignedTo!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        imcontactlog!!.setOnClickListener(this)

        tie_Category!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        tie_Project!!.setOnClickListener(this)

        til_CustomerName!!.defaultHintTextColor =
            ContextCompat.getColorStateList(this, R.color.color_mandatory)
        til_AssignedDate!!.defaultHintTextColor =
            ContextCompat.getColorStateList(this, R.color.color_mandatory)
        til_AssignedTo!!.defaultHintTextColor =
            ContextCompat.getColorStateList(this, R.color.color_mandatory)

//314400

        val AUDIO_SP = context.getSharedPreferences(Config.SHARED_PREF76, 0)
        if (AUDIO_SP.getString("AudioClipEnabled", null).equals("true")){
            til_AttachVoice!!.visibility=View.VISIBLE
        }else{
            til_AttachVoice!!.visibility=View.GONE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }

            R.id.imVoice -> {
                Config.disableClick(v)
                val intent =
                    Intent(this@WalkingCustomerActivity, VoiceRecordingActivity::class.java)
                intent.putExtra("name11", "uuuuu")
                startActivity(intent)
                //  startActivityForResult(intent, RECORD_PLAY!!);

                //   startActivity(Intent(this@WalkingCustomerActivity, VoiceRecordingActivity::class.java))

            }

            R.id.tie_Category -> {
                Config.disableClick(v)
                prodcategory = 0
                getCategory()
            }

            R.id.tie_Product -> {
                Config.disableClick(v)
                proddetail = 0
                getProductDetail(ID_Category!!)
            }

            R.id.tie_AssignedTo -> {
                Config.disableClick(v)
                assignedToCount = 0
                getAssignedTo()
            }

            R.id.tie_AssignedDate -> {
                Config.disableClick(v)
                dateSelectMode = 0
                openBottomSheet()
            }

            R.id.tie_Attachvoice -> {
                Config.disableClick(v)
                val intent =
                    Intent(this@WalkingCustomerActivity, VoiceRecordingActivity::class.java)
                startActivityForResult(intent, RECORD_PLAY!!);
            }

            R.id.tie_Attachvoice1 -> {
                Log.i("wewewe", "voiceData " + voiceData)
                Log.i("wewewe", "voiceData len===:" + voiceData!!.length)
                //  voicedataByte=voiceData!!.toByteArray()

              //  playByteArray(voicedataByte!!)
                playString(voiceData)

            }

            R.id.tie_Attachvoice2 -> {

//                val bytes = "Hello!".toByteArray()
//                val string = String(bytes)
               voiceData2 = voicedataByte.toString()
                Log.i("voice1212","v=="+voiceData2)
                voicedataByte2 = voiceData2!!.toByteArray()

                Log.i("response999222", "voicedataByte playing:" + voicedataByte + '\n')
                Log.i("weee", "voicedataByte playing:" + voicedataByte + '\n')
                playByteArray(voicedataByte2!!)


            }

            R.id.btnReset -> {
                resetData()
            }

            R.id.imcontactlog -> {
                Config.disableClick(v)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_CALL_LOG
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_CALL_LOG),
                        101
                    )
                } else {
                    getCallLogDetail(context)
                }
            }

            R.id.btnSubmit -> {


                checkAttendance()
                if (saveAttendanceMark) {
                    array_walkingUpdate = JSONArray()
                    validation(v)
                }

            }
        }
    }

    private fun getCategory() {
        var ReqMode = "13"
        var SubMode = "0"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this, ReqMode!!, SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (prodcategory == 0) {
                                    prodcategory++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        prodCategoryArrayList = jobjt.getJSONArray("CategoryList")
                                        if (prodCategoryArrayList.length() > 0) {
                                            productCategoryPopup(prodCategoryArrayList)

                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@WalkingCustomerActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!!.setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val etsearch = dialogProdCat!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogProdCat!! .findViewById(R.id.txt_nodata) as TextView

            prodCategorySort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCategorySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@WalkingCustomerActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductCategoryAdapter(this@WalkingCustomerActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@WalkingCustomerActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodCategorySort = JSONArray()

                    for (k in 0 until prodCategoryArrayList.length()) {
                        val jsonObject = prodCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodCategorySort.put(jsonObject)
                            }

                        }
                    }

                    if (prodCategorySort.length() <= 0){
                        recyProdCategory!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyProdCategory!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "prodCategorySort               7103    " + prodCategorySort)
                    val adapter =
                        ProductCategoryAdapter(this@WalkingCustomerActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@WalkingCustomerActivity)
                }
            })

            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail(ID_Category: String) {
//         var proddetail = 0

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, ID_Category)!!.observe(
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
                                        prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                        if (prodDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            productDetailPopup(prodDetailArrayList)
//                                             }

                                        }

                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@WalkingCustomerActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogProdDet!! .findViewById(R.id.txt_nodata) as TextView

            prodDetailSort = JSONArray()
            for (k in 0 until prodDetailArrayList.length()) {
                val jsonObject = prodDetailArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodDetailSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@WalkingCustomerActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@WalkingCustomerActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@WalkingCustomerActivity)

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
                        //if (textlength <= jsonObject.getString("ProductName").length) {
                        if (textlength > 0) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) ||
                                jsonObject.getString("ProdBarcode")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()))
                            {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    if (prodDetailSort.length() <= 0){
                        recyProdDetail!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyProdDetail!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@WalkingCustomerActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@WalkingCustomerActivity)
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

    private fun playString(voiceData: String?) {

        val decodedBytes = Base64.getDecoder().decode(voiceData)
        Log.i("response999222", "out decodedBytes:=" + decodedBytes)

        val  tempAudio=File.createTempFile("tempAudio",null,applicationContext.cacheDir)
        tempAudio.writeBytes(decodedBytes)
        val mediaPlayer=MediaPlayer()
        mediaPlayer.setDataSource(tempAudio.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()

    }

    private fun playByteArray(mp3SoundByteArray: ByteArray) {
        try {
            val Mytemp = File.createTempFile("TCL", "mp3", cacheDir)
            Mytemp.deleteOnExit()
            val fos = FileOutputStream(Mytemp)
            fos.write(mp3SoundByteArray)
            fos.close()
            var mediaPlayer: MediaPlayer? = null
            //   var mediaPlayer = MediaPlayer()
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.fromFile(Mytemp));
            mediaPlayer.start();


//            val MyFile = FileInputStream(Mytemp)
//            mediaPlayer.setDataSource(Mytemp.getf)
//            mediaPlayer.prepare()
//            mediaPlayer.start()
        } catch (ex: IOException) {
            val s = ex.toString()
            ex.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("response2323", "requestCode:" + requestCode)
        if (requestCode == RECORD_PLAY) {
            if (data != null) {


                try {

                    VoiceLabel="Voice_"+getCurrentTimeStamp()
                    val random2 = Random().nextInt(10) + 1

                    voiceData = data.getStringExtra("voicedata")
                    voicedataByte = data.getByteArrayExtra("voicedatabyte")
                    Log.i("response999222", "voiceData===:" + voiceData + '\n')
                    Log.i("response999222", "voicedataByte:" + voicedataByte + '\n')
                    Log.i(
                        "response999222",
                        "voicedataByte string:" + voicedataByte.toString() + '\n'
                    )
                    Log.i("weee", "value ="+voicedataByte)
                    Log.i("weee", "value string ="+voicedataByte.toString())
                    tie_Attachvoice!!.setText(VoiceLabel)
                } catch (e: Exception) {

                }


            }
        }

    }

    private fun getCurrentTimeStamp(): String {
        val  currentDateTime=LocalDateTime.now()
        val formatter=DateTimeFormatter.ofPattern("yyyMMddHHmmss")
        return  currentDateTime.format(formatter)

    }

//    private fun getCustnumber() {
//        try {
//            val builder = AlertDialog.Builder(this@WalkingCustomerActivity)
//            val inflater1 =
//                this@WalkingCustomerActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout = inflater1.inflate(R.layout.custlog_popup, null)
//            lvCustno = layout.findViewById(R.id.lvCustno)
//            builder.setView(layout)
//            val alertDialog = builder.create()
//            // displayLog()
//            getCallLogDetail(alertDialog, context)
//            alertDialog.show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
    private fun getCallLogDetail(context: Context) {
        array_sort = ArrayList<CalllogModel>()
        calllogArrayList = ArrayList<CalllogModel>()
        val resolver = context.contentResolver
        val cur: Cursor? = resolver.query(
            CallLog.Calls.CONTENT_URI, null,
            null, null, CallLog.Calls.DATE + " DESC"
        )
        val name: Int = cur!!.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val number: Int = cur!!.getColumnIndex(CallLog.Calls.NUMBER)
        val type: Int = cur.getColumnIndex(CallLog.Calls.TYPE)
        val date: Int = cur.getColumnIndex(CallLog.Calls.DATE)
        val duration: Int = cur.getColumnIndex(CallLog.Calls.DURATION)
        if (cur!!.moveToFirst()) {
            do {
                calllogArrayList.add(
                    CalllogModel(
                        cur.getString(name),
                        cur.getString(number),
                        cur.getString(type),
                        cur.getString(duration).toInt(),
                        cur.getString(date)
                    )
                )
                array_sort.add(
                    CalllogModel(
                        cur.getString(name),
                        cur.getString(number),
                        cur.getString(type),
                        cur.getString(duration).toInt(),
                        cur.getString(date)
                    )
                )
            } while (cur.moveToNext())
        }
        if(array_sort.isEmpty()){
            val builder = AlertDialog.Builder(
                this@WalkingCustomerActivity,
                R.style.MyDialogTheme
            )
            builder.setMessage("Your phone's call log is empty")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }else {

            // getCustnumber()

            try {
                val builder = AlertDialog.Builder(this@WalkingCustomerActivity)
                val inflater1 =
                    this@WalkingCustomerActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layout = inflater1.inflate(R.layout.custlog_popup, null)
                lvCustno = layout.findViewById(R.id.lvCustno)
                etxtsearch  = layout.findViewById(R.id.etsearch)
                tv_callhistory  = layout.findViewById(R.id.tv_callhistory)
                builder.setView(layout)
                val alertDialog = builder.create()

                if (array_sort.size <= 0){
                    Log.e(TAG,"839991  Call History is empty")
                    tv_callhistory!!.visibility = View.VISIBLE
                }else{
                    tv_callhistory!!.visibility = View.GONE
                    Log.e(TAG,"839992  Call History")
                }
                sadapter = CallLogListAdapter(this@WalkingCustomerActivity, array_sort)
                lvCustno!!.setAdapter(sadapter)
                lvCustno!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    Config.Utils.hideSoftKeyBoard(this@WalkingCustomerActivity, view)
                    array_sort.get(position).number
                    tie_CustomerName!!.setText(array_sort[position].name)
                    tie_Phone!!.setText(array_sort[position].number!!.replace("+91", ""))
                    alertDialog.dismiss()
                })

                etxtsearch!!.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        //  lvCustno!!.setVisibility(View.VISIBLE)
                        textlength = etxtsearch!!.text.length
                        array_sort.clear()
                        for (i in calllogArrayList.indices) {
                            if (textlength <= calllogArrayList[i].number!!.length) {
                                if (calllogArrayList[i].number!!.toLowerCase().trim().contains(
                                        etxtsearch!!.text.toString().toLowerCase().trim { it <= ' ' })
                                ) {
                                    array_sort.add(calllogArrayList[i])
                                }
                            }
                        }

                        if (array_sort.size <= 0){
                            Log.e(TAG,"839991  Call History is empty")
                            tv_callhistory!!.visibility = View.VISIBLE
                        }else{
                            tv_callhistory!!.visibility = View.GONE
                            Log.e(TAG,"839992  Call History")
                        }
                        sadapter = CallLogListAdapter(this@WalkingCustomerActivity, array_sort)
                        lvCustno!!.setAdapter(sadapter)

                    }
                })

                // getCallLogDetail(alertDialog,context)
                alertDialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    private fun getCallLogDetail(alertDialog: AlertDialog, context: Context) {
//        array_sort = ArrayList<CalllogModel>()
//        calllogArrayList = ArrayList<CalllogModel>()
//        val resolver = context.contentResolver
//        val cur: Cursor? = resolver.query(
//            CallLog.Calls.CONTENT_URI, null,
//            null, null, CallLog.Calls.DATE + " DESC"
//        )
//        val name: Int = cur!!.getColumnIndex(CallLog.Calls.CACHED_NAME)
//        val number: Int = cur!!.getColumnIndex(CallLog.Calls.NUMBER)
//        val type: Int = cur.getColumnIndex(CallLog.Calls.TYPE)
//        val date: Int = cur.getColumnIndex(CallLog.Calls.DATE)
//        val duration: Int = cur.getColumnIndex(CallLog.Calls.DURATION)
//        if (cur!!.moveToFirst()) {
//            do {
//                calllogArrayList.add(
//                    CalllogModel(
//                        cur.getString(name),
//                        cur.getString(number),
//                        cur.getString(type),
//                        cur.getString(duration).toInt(),
//                        cur.getString(date)
//                    )
//                )
//                array_sort.add(
//                    CalllogModel(
//                        cur.getString(name),
//                        cur.getString(number),
//                        cur.getString(type),
//                        cur.getString(duration).toInt(),
//                        cur.getString(date)
//                    )
//                )
//            } while (cur.moveToNext())
//        }
//        sadapter = CallLogListAdapter(this@WalkingCustomerActivity, array_sort)
//        lvCustno!!.setAdapter(sadapter)
//        lvCustno!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
//            Config.Utils.hideSoftKeyBoard(this@WalkingCustomerActivity, view)
//            tie_CustomerName!!.setText(array_sort[position].name)
//            tie_Phone!!.setText(array_sort[position].number)
//            alertDialog.dismiss()
//        })
//    }

    private fun resetData() {

        tie_CustomerName!!.setText("")
        tie_Phone!!.setText("")
        tie_AssignedTo!!.setText("")
        tie_Description!!.setText("")
        tie_Attachvoice!!.setText("")
        ID_AssignedTo = ""
        voiceData = ""

        ID_Category = "0"
        ID_Product = "0"
        checkProject = "1"
        tie_Category!!.setText("")
        tie_Product!!.setText("")
        tie_Project!!.setText("")

        til_Product!!.visibility = View.VISIBLE
        til_Project!!.visibility = View.GONE



   //     voicedataByte!!.fill(0)
//        voicedataByte!!.fill(0)
        defaultLoad()
    }

    private fun defaultLoad() {

        try {

            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())

            Log.e(TAG, "DATE TIME  196  " + currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG, "newDate  196  " + newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)

            tie_AssignedDate!!.setText("" + sdfDate1.format(newDate))

        } catch (e: Exception) {

            Log.e(TAG, "Exception 196  " + e.toString())
        }
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        date_Picker1.setMinDate(System.currentTimeMillis());
        date_Picker1.minDate = System.currentTimeMillis()


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

                tie_AssignedDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getAssignedTo() {
        var ReqMode = "107"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                assignedToWalkingViewModel.getAssignedToWalking(this, ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (assignedToCount == 0) {
                                assignedToCount++
                                Log.e(TAG, "msg   2280   " + msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    val jobjt = jObject.getJSONObject("EmployeeDetails")
                                    assignedToList = jobjt.getJSONArray("EmployeeDetailsList")
                                    if (assignedToList.length() > 0) {

                                        assignedToPopup(assignedToList)
                                    }
                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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

                    })
                progressDialog!!.dismiss()
            }

            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun assignedToPopup(assignedToList: JSONArray) {

        try {

            dialogassignedTo = Dialog(this)
            dialogassignedTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogassignedTo!!.setContentView(R.layout.assignedto_popup)
            dialogassignedTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyassignedTo = dialogassignedTo!!.findViewById(R.id.recyassignedTo) as RecyclerView
            val etsearch = dialogassignedTo!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogassignedTo!! .findViewById(R.id.txt_nodata) as TextView
            assignedToSortList = JSONArray()
            for (k in 0 until assignedToList.length()) {
                val jsonObject = assignedToList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                assignedToSortList.put(jsonObject)
            }
            if (assignedToSortList.length() <= 0){
                recyassignedTo!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }

            val lLayout = GridLayoutManager(this@WalkingCustomerActivity, 1)
            recyassignedTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = AssignedToAdapter(this@WalkingCustomerActivity, assignedToSortList)
            recyassignedTo!!.adapter = adapter
            adapter.setClickListener(this@WalkingCustomerActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    assignedToSortList = JSONArray()
                    for (k in 0 until assignedToList.length()) {
                        var jsonObject = assignedToList.getJSONObject(k)
                     //   if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                                || jsonObject.getString("DepartmentName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                                || jsonObject.getString("DesignationName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                                || jsonObject.getString("Branch")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                                assignedToSortList.put(jsonObject)
                            }

                       // }
                    }
                    if (assignedToSortList.length() <= 0){
                        recyassignedTo!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyassignedTo!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }
                    Log.e(TAG, "assignedToSortList               7103    " + assignedToSortList)
                    val adapter =
                        AssignedToAdapter(this@WalkingCustomerActivity, assignedToSortList)
                    recyassignedTo!!.adapter = adapter
                    adapter.setClickListener(this@WalkingCustomerActivity)
                }
            })

            dialogassignedTo!!.show()
            dialogassignedTo!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1394    " + e.toString())

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("AssignedToClick")) {
            dialogassignedTo!!.dismiss()
            val jsonObject = assignedToSortList.getJSONObject(position)

            tie_AssignedTo!!.setText(jsonObject!!.getString("EmpName"))
            Log.i("responseWlk", "id=" + jsonObject.getString("ID_Employee"))
            ID_AssignedTo = jsonObject.getString("ID_Employee")
        }

        if (data.equals("prodcategory")) {
            dialogProdCat!!.dismiss()
//             val jsonObject = prodCategoryArrayList.getJSONObject(position)
            val jsonObject = prodCategorySort.getJSONObject(position)
            Log.e(TAG, "ID_Category   " + jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))
            ID_Product = ""
            tie_Product!!.setText("")
            tie_Project!!.setText("")


            Log.i("resperr","check data side="+ checkProject)
            Log.e(TAG,"12121212   Project   "+jsonObject.getString("Project"))
            if (jsonObject.getString("Project").equals("0")) {
                til_Product!!.visibility = View.VISIBLE
                til_Project!!.visibility = View.GONE
                checkProject ="1"  // <--gone

            } else if (jsonObject.getString("Project").equals("1")) {
                til_Product!!.visibility = View.GONE
                til_Project!!.visibility = View.VISIBLE

                checkProject ="0"  // <-- visible

            }


        }

        if (data.equals("proddetails")) {
            dialogProdDet!!.dismiss()
//             val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
            ID_Category = jsonObject.getString("FK_Category")
            ID_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))

        }


    }

    private fun validation(v: View) {

        try {
            var assignDate = tie_AssignedDate!!.text.toString()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val newDate1: Date = sdf.parse(assignDate)
            val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
            strAssignedDate = sdfDate1.format(newDate1)
            strCustomer = tie_CustomerName!!.text.toString()
            strPhone = tie_Phone!!.text.toString()
            strDescription = tie_Description!!.text.toString()
            strProjectName = tie_Project!!.text.toString()

            if (strCustomer.equals("")) {
                Config.snackBars(context, v, "Enter Customer Name")
            } else if (ID_AssignedTo.equals("")) {
                Config.snackBars(context, v, "Select Assigned To")
            } else if (assignDate.equals("")) {
                Config.snackBars(context, v, "Select Assigned Date")
            }
//            else if (voicedataByte!!.equals("")) {
//                Config.snackBars(context, v, "Select Voice Data")
//            }
//            else if (voiceData!!.equals("")) {
//                Config.snackBars(context, v, "Select Voice Data")
//            }
            else {
                saveCount = 0
                saveWalkingCustomer()
            }
        } catch (e: Exception) {

        }


    }

    private fun saveWalkingCustomer() {

//        voiceData2 = Base64.getEncoder().encodeToString(voicedataByte)
//        voicedataByte2 = Base64.getDecoder().decode(voiceData2)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                createWalkingCustomerViewModel.CreateWalkingCustomer(
                    this,
                    strCustomer!!,
                    strPhone!!,
                    ID_AssignedTo!!,
                    strAssignedDate!!,
                    voiceData!!,
                    VoiceLabel!!,
                    strDescription!!,
                    array_walkingUpdate!!,
                    ID_Category!!,
                    ID_Product!!,
                    strProjectName!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (saveCount == 0) {
                                saveCount++
                                Log.e(TAG, "msg   4060   " + msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    successPopup(jObject)
                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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

                    })
                progressDialog!!.dismiss()
            }

            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun successPopup(jobjt: JSONObject) {
        try {

            val suceessDialog = Dialog(this)
            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            suceessDialog!!.setCancelable(false)
            suceessDialog!!.setContentView(R.layout.success_service_popup)
            suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            suceessDialog!!.setCancelable(false)

            val tv_succesmsg = suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
            val tv_succesok = suceessDialog!!.findViewById(R.id.tv_succesok) as TextView

            tv_succesmsg!!.setText(jobjt.getString("EXMessage"))

            tv_succesok!!.setOnClickListener {
                suceessDialog!!.dismiss()
                resetData()

            }

            suceessDialog!!.show()
            suceessDialog!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );

        } catch (e: Exception) {

        }
    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance) {
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status", "")
            if (status.equals("0") || status.equals("")) {
                Common.punchingRedirectionConfirm(this, "", "")
            } else if (status.equals("1")) {
                saveAttendanceMark = true
            }

        } else {
            saveAttendanceMark = true
        }
    }

    private fun getExistingCustomerData() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                walkExistViewModel.getWalkExist(this, strPhone!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (walkExistCount == 0) {
                                walkExistCount++
                                Log.e(TAG, "msg   52777   " + msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    // successPopup(jObject)

                                    val jobjt = jObject.getJSONObject("WalkingCustomerList")
                                    walkExistList = jobjt.getJSONArray("WalkingCustomerDetails")
                                    val i = Intent(
                                        this@WalkingCustomerActivity,
                                        WalkingExistingActivity::class.java
                                    )
                                    i.putExtra("jsonObject", jObject.toString())
                                    i.putExtra("strPhone", strPhone)
                                    startActivity(i)

                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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

                    })
                progressDialog!!.dismiss()
            }

            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getCustnumber()
            getCallLogDetail(context)
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}