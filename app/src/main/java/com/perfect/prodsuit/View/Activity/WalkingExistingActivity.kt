package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.ModelWalkingExist
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AssignedToAdapter
import com.perfect.prodsuit.View.Adapter.ProductCategoryAdapter
import com.perfect.prodsuit.View.Adapter.ProductDetailAdapter
import com.perfect.prodsuit.View.Adapter.WalkingExistingAdapter
import com.perfect.prodsuit.Viewmodel.AssignedToWalkingViewModel
import com.perfect.prodsuit.Viewmodel.CreateWalkingCustomerViewModel
import com.perfect.prodsuit.Viewmodel.ProductCategoryViewModel
import com.perfect.prodsuit.Viewmodel.ProductDetailViewModel
import com.perfect.prodsuit.Viewmodel.WalkingUpdateViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WalkingExistingActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG = "WalkingExistingActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var jsonObj: JSONObject? = null
    var strPhone: String? = null
    var strCustomer: String? = ""
    var strDescription: String? = ""

    internal var recyDetail: RecyclerView? = null
    lateinit var walkExistList : JSONArray
    val modelWalkingExist = ArrayList<ModelWalkingExist>()
    var walkingExistingAdapter: WalkingExistingAdapter? = null

    private var tie_UpAssignedTo: TextInputEditText? = null
    private var tie_UpAssignedDate: TextInputEditText? = null

    private var btnCancels: Button? = null
    private var btnSubmit: Button? = null

    var dateSelectMode: Int = 0

    var assignedToCount: Int = 0
    var assignedToMode: Int = 0
    lateinit var assignedToWalkingViewModel: AssignedToWalkingViewModel
    lateinit var assignedToList : JSONArray
    lateinit var assignedToSortList : JSONArray
    private var dialogassignedTo : Dialog? = null
    var recyassignedTo: RecyclerView? = null

    lateinit var walkingUpdateViewModel: WalkingUpdateViewModel
    private var array_walkingUpdate = JSONArray()

    private var til_CustomerName: TextInputLayout? = null
    private var til_Phone: TextInputLayout? = null
    private var til_AssignedTo: TextInputLayout? = null
    private var til_AssignedDate: TextInputLayout? = null
    private var til_Description: TextInputLayout? = null

    private var tie_CustomerName: TextInputEditText? = null
    private var tie_Phone: TextInputEditText? = null
    private var tie_AssignedTo: TextInputEditText? = null
    private var tie_AssignedDate: TextInputEditText? = null
    private var tie_Description: TextInputEditText? = null


    var ID_AssignedTo: String? = ""
    var strAssignedDate: String? = ""

    var ID_UpAssignedTo: String? = ""
    var strUpAssignedDate: String? = ""

    var saveAttendanceMark = false
    var saveCount: Int = 0
    lateinit var createWalkingCustomerViewModel: CreateWalkingCustomerViewModel
    private var tie_Attachvoice: TextInputEditText? = null
    private var til_AttachVoice: TextInputLayout? = null

    private var RECORD_PLAY: Int? = 1038
    var voiceData: String? = ""
    var   VoiceLabel: String? = ""
    private var voicedataByte : ByteArray? =null

    // Add Product 23-01-2024

    private var til_Category: TextInputLayout? = null
    private var til_Product: TextInputLayout? = null
    private var til_Project: TextInputLayout? = null

    private var tie_Category: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null
    private var tie_Project: TextInputEditText? = null


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
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    // Add Product 23-01-2024

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walking_existing)
        context = this@WalkingExistingActivity

        assignedToWalkingViewModel = ViewModelProvider(this).get(AssignedToWalkingViewModel::class.java)
        walkingUpdateViewModel = ViewModelProvider(this).get(WalkingUpdateViewModel::class.java)
        createWalkingCustomerViewModel = ViewModelProvider(this).get(CreateWalkingCustomerViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        setRegViews()

        val AUDIO_SP = context.getSharedPreferences(Config.SHARED_PREF76, 0)
        if (AUDIO_SP.getString("AudioClipEnabled", null).equals("true")){
            til_AttachVoice!!.visibility=View.VISIBLE
        }else{
            til_AttachVoice!!.visibility=View.GONE
        }

        defaultLoad()
        checkAttendance()
        try {
            var strPhone: String? = intent.getStringExtra("strPhone")
            var jsonObject: String? = intent.getStringExtra("jsonObject")
            jsonObj = JSONObject(jsonObject)
            tie_Phone!!.setText(strPhone)
            Log.e(TAG,"jsonObj  37777   "+jsonObj)
            showList(jsonObj!!)
        }catch (e: Exception){

        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun showList(jsonObj: JSONObject) {
        val jobjt = jsonObj.getJSONObject("WalkingCustomerList")
        walkExistList = jobjt.getJSONArray("WalkingCustomerDetails")
        Log.e(TAG,"jsonObj  377771   "+jsonObj)
        modelWalkingExist.clear()
        for (i in 0 until walkExistList.length()) {

            var jsonObject = walkExistList.getJSONObject(i)
            modelWalkingExist!!.add(ModelWalkingExist(jsonObject.getString("Customer"),jsonObject.getString("Mobile")
                ,jsonObject.getString("Product"),jsonObject.getString("Action"),jsonObject.getString("AssignedTo"),jsonObject.getString("ID_LeadGenerateProduct")
                ,jsonObject.getString("FollowUpDate"),jsonObject.getString("FK_Employee"),jsonObject.getString("ID_Users"),jsonObject.getString("LeadNo")))

        }

        if (modelWalkingExist.size>0){
            Log.e(TAG,"modelWalkingExist  377772   "+modelWalkingExist[0].FollowUpDate)
            recyDetail!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
            walkingExistingAdapter = WalkingExistingAdapter(this@WalkingExistingActivity, modelWalkingExist)
            recyDetail!!.adapter = walkingExistingAdapter
            walkingExistingAdapter!!.setClickListener(this@WalkingExistingActivity)
        }


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        tie_Attachvoice = findViewById<TextInputEditText>(R.id.tie_Attachvoice)
        til_AttachVoice = findViewById<TextInputLayout>(R.id.til_AttachVoice)
        tie_CustomerName = findViewById<TextInputEditText>(R.id.tie_CustomerName)
        tie_Phone = findViewById<TextInputEditText>(R.id.tie_Phone)
        tie_AssignedDate = findViewById<TextInputEditText>(R.id.tie_AssignedDate)
        tie_AssignedTo = findViewById<TextInputEditText>(R.id.tie_AssignedTo)
        tie_Description = findViewById<TextInputEditText>(R.id.tie_Description)

        til_CustomerName = findViewById<TextInputLayout>(R.id.til_CustomerName)
        til_Phone = findViewById<TextInputLayout>(R.id.til_Phone)
        til_AssignedDate = findViewById<TextInputLayout>(R.id.til_AssignedDate)
        til_AssignedTo = findViewById<TextInputLayout>(R.id.til_AssignedTo)
        til_Description = findViewById<TextInputLayout>(R.id.til_Description)

        til_Category = findViewById<TextInputLayout>(R.id.til_Category)
        til_Product = findViewById<TextInputLayout>(R.id.til_Product)
        til_Project = findViewById<TextInputLayout>(R.id.til_Project)

        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)
        tie_Project = findViewById<TextInputEditText>(R.id.tie_Project)

        recyDetail = findViewById(R.id.recyDetail)
        btnCancels = findViewById(R.id.btnCancels)
        btnSubmit = findViewById(R.id.btnSubmit)

        tie_AssignedDate!!.setOnClickListener(this)
        tie_AssignedTo!!.setOnClickListener(this)
        btnCancels!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        tie_Attachvoice!!.setOnClickListener(this)

        tie_Category!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        tie_Project!!.setOnClickListener(this)

        til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_AssignedDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_AssignedTo!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
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

            R.id.tie_AssignedTo->{
                Config.disableClick(v)
                assignedToMode = 0
                assignedToCount = 0
                getAssignedTo()
            }

            R.id.tie_AssignedDate->{
                dateSelectMode = 0
                openBottomSheet()
            }

            R.id.btnCancels->{
                finish()
            }
            R.id.btnSubmit->{
                Config.disableClick(v)
                checkAttendance()
                if (saveAttendanceMark){
                    validation(v)
                }
            }

            R.id.tie_Attachvoice->{
                val intent = Intent(this@WalkingExistingActivity, VoiceRecordingActivity::class.java)
                startActivityForResult(intent, RECORD_PLAY!!);
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
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@WalkingExistingActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!!.setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val etsearch = dialogProdCat!!.findViewById(R.id.etsearch) as EditText

            prodCategorySort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCategorySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@WalkingExistingActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductCategoryAdapter(this@WalkingExistingActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@WalkingExistingActivity)

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

                    Log.e(TAG, "prodCategorySort               7103    " + prodCategorySort)
                    val adapter =
                        ProductCategoryAdapter(this@WalkingExistingActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@WalkingExistingActivity)
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

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@WalkingExistingActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText

            prodDetailSort = JSONArray()
            for (k in 0 until prodDetailArrayList.length()) {
                val jsonObject = prodDetailArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodDetailSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@WalkingExistingActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@WalkingExistingActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@WalkingExistingActivity)

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
                                jsonObject.getString("ProdBarcode")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@WalkingExistingActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@WalkingExistingActivity)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RECORD_PLAY)
        {
            if (data != null) {



                try {
                    val random2 = Random().nextInt(10) + 1
                    VoiceLabel="Voice_"+getCurrentTimeStamp()

                    voiceData = data.getStringExtra("voicedata")
                    voicedataByte=data.getByteArrayExtra("voicedatabyte")
                    Log.i("response8888","voiceData:"+voiceData+'\n')
                    Log.i("response8888","voicedataByte:"+voicedataByte+'\n')
                  //  Log.i("response8888","voicedataByte string:"+voicedataByte.toString()+'\n')
                    tie_Attachvoice!!.setText(VoiceLabel)
                }
                catch (e: Exception) {

                }


            }
        }
    }

    private fun getCurrentTimeStamp(): String {
        val  currentDateTime= LocalDateTime.now()
        val formatter= DateTimeFormatter.ofPattern("yyyMMddHHmmss")
        return  currentDateTime.format(formatter)

    }

    private fun validation(v : View) {

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


            Log.i("response232","assignDate="+assignDate)
           Log.i("response232","strAssignedDate="+strAssignedDate)
            Log.i("response232","strCustomer="+strCustomer)
            Log.i("response232","strPhone="+strPhone)
            Log.i("response232","strDescription="+strDescription)

            if (strCustomer.equals("")){
                Config.snackBars(context,v,"Enter Customer Name")
            }
            else if(ID_AssignedTo.equals("")){
                Config.snackBars(context,v,"Select Assigned To")
            }
            else if(assignDate.equals("")){
                Config.snackBars(context,v,"Select Assigned Date")
            }
//            else if(voicedataByte!!.equals("")){
//                Config.snackBars(context,v,"Select Voice Data")
//            }
//            else if (voiceData!!.equals("")) {
//                Config.snackBars(context, v, "Select Voice Data")
//            }

            else{

             getArraList(v)
            }
        }catch (e : Exception){
            Log.i("response232","ex="+e)
        }


    }

    private fun getArraList(v: View) {
        array_walkingUpdate = JSONArray()


        Log.e(TAG,"25555500    array_walkingUpdate:  "+array_walkingUpdate.length())
        Log.e(TAG,"25555511   modelWalkingExist:  "+modelWalkingExist.size)
        for (k in 0 until modelWalkingExist.size) {

            Log.e(TAG,"14777"+k+"    Customer:  "+modelWalkingExist[k].Customer)
            Log.e(TAG,"14777"+k+"    Mobile:  "+modelWalkingExist[k].Mobile)
            Log.e(TAG,"14777"+k+"    Product:  "+modelWalkingExist[k].Product)
            Log.e(TAG,"14777"+k+"    Action:  "+modelWalkingExist[k].Action)
            Log.e(TAG,"14777"+k+"    AssignedTo:  "+modelWalkingExist[k].AssignedTo)
            Log.e(TAG,"14777"+k+"    ID_LeadGenerateProduct:  "+modelWalkingExist[k].ID_LeadGenerateProduct)
            Log.e(TAG,"14777"+k+"    FollowUpDate:  "+modelWalkingExist[k].FollowUpDate)
            Log.e(TAG,"14777"+k+"    FK_Employee:  "+modelWalkingExist[k].FK_Employee)
            Log.e(TAG,"14777"+k+"    ID_Users:  "+modelWalkingExist[k].ID_Users)
            Log.e(TAG,"14777"+k+"    LeadNo:  "+modelWalkingExist[k].LeadNo)

            //   {"ID_LeadGenerateProduct":"0","FK_Employee":"2","FollowUpDate":"14-09-2023"}

            val strrfollowup = modelWalkingExist[k].FollowUpDate
            var folloupdate = ""
            if (!strrfollowup.equals("")){
                val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val currentDateFormate = inputFormat.parse(strrfollowup)
                folloupdate = outputFormat.format(currentDateFormate)
            }
            val jObject = JSONObject()
            jObject.put("ID_LeadGenerateProduct", (modelWalkingExist[k].ID_LeadGenerateProduct))
            jObject.put("FK_Employee", (modelWalkingExist[k].FK_Employee))
            jObject.put("FollowUpDate", folloupdate)

            array_walkingUpdate.put(jObject)
        }

        Log.e(TAG,"array_walkingUpdate   18222   "+array_walkingUpdate)
        saveCount = 0
        saveWalkingCustomer()
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("clickLead")){
            Log.e(TAG,"clickLead  10000   "+modelWalkingExist[position].FollowUpDate)
            updateBottomSheet(position,modelWalkingExist)
        }

        if (data.equals("AssignedToClick")) {
            dialogassignedTo!!.dismiss()

            if (assignedToMode == 0){
                val jsonObject = assignedToSortList.getJSONObject(position)

                tie_AssignedTo!!.setText(jsonObject!!.getString("EmpName"))
                ID_AssignedTo = jsonObject.getString("ID_Employee")


            }
            else if (assignedToMode == 1){
                val jsonObject = assignedToSortList.getJSONObject(position)

                tie_UpAssignedTo!!.setText(jsonObject!!.getString("EmpName"))
                ID_UpAssignedTo = jsonObject.getString("ID_Employee")
            }

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

    private fun updateBottomSheet(position: Int, modelWalkingExist: ArrayList<ModelWalkingExist>) {
        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.walking_edit_bottomsheet, null)

            tie_UpAssignedTo = view.findViewById<TextInputEditText>(R.id.tie_UpAssignedTo)
            tie_UpAssignedDate = view.findViewById<TextInputEditText>(R.id.tie_UpAssignedDate)

            val btnCancel = view.findViewById<Button>(R.id.btnCancel)
            val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")

            val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


            val date1 = dateFormat.parse(currentDate1)
            val date2 = dateFormat.parse(modelWalkingExist[position].FollowUpDate)

            val timeInMillis1 = date1?.time ?: 0
            val timeInMillis2 = date2?.time ?: 0

            tie_UpAssignedTo!!.setText(modelWalkingExist[position].AssignedTo)
            ID_UpAssignedTo = modelWalkingExist[position].FK_Employee
            Log.e(TAG,"178883   "+timeInMillis1+"   :  "+timeInMillis2)
            Log.e(TAG,"178883   "+currentDate1+"   :  "+modelWalkingExist[position].FollowUpDate)
            if (timeInMillis1 >= timeInMillis2) {
                // date1 is greater than or equal to date2
                // Your code here
                Log.e(TAG,"178881   "+currentDate1)
                tie_UpAssignedDate!!.setText(currentDate1)
                strUpAssignedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            } else {
                // date1 is less than date2
                // Your code here
                Log.e(TAG,"178882   "+modelWalkingExist[position].FollowUpDate)

                tie_UpAssignedDate!!.setText(modelWalkingExist[position].FollowUpDate)
                val inputPattern = "dd-MM-yyyy"
                val outputPattern = "yyyy-MM-dd"
                val inputFormat = SimpleDateFormat(inputPattern)
                val outputFormat = SimpleDateFormat(outputPattern)

                var date: Date? = null
                var str: String? = null

                try {
                    date = inputFormat.parse(modelWalkingExist[position].FollowUpDate)
                    strUpAssignedDate = outputFormat.format(date)
                } catch (e: Exception) {
                    Log.e(TAG,"147852   "+e.toString())
                    e.printStackTrace()
                }

                Log.e(TAG,"147852   "+strUpAssignedDate)
                //strUpAssignedDate = SimpleDateFormat("yyyy-MM-dd", modelWalkingExist[position].FollowUpDate))
            }




            tie_UpAssignedTo!!.setOnClickListener {
                Config.disableClick(it)
                assignedToMode = 1
                assignedToCount = 0
                getAssignedTo()
            }

            tie_UpAssignedDate!!.setOnClickListener {
                dateSelectMode = 1
                openBottomSheet()
            }

            btnCancel.setOnClickListener {
                dialog .dismiss()
                ID_UpAssignedTo = ""
                strUpAssignedDate = ""
                tie_UpAssignedDate!!.setText("")
                tie_UpAssignedTo!!.setText("")
            }
            btnUpdate.setOnClickListener {
                dialog.dismiss()

                var assignDate = tie_UpAssignedDate!!.text.toString()
                var assignTo = tie_UpAssignedTo!!.text.toString()
                modelWalkingExist[position].FollowUpDate =  assignDate
                modelWalkingExist[position].AssignedTo =  assignTo
                modelWalkingExist[position].FK_Employee =  ID_UpAssignedTo!!
                walkingExistingAdapter!!.notifyItemChanged(position)
                //ApprovalListDetailActivity
                //   successBottomSheet()

            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()

        }catch (e: Exception){
            Log.e(TAG,"7771  Exception   "+e.toString())
        }
    }

    private fun successBottomSheet() {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.succes_bottomsheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCancelable(false)
            dialog1!!.setCanceledOnTouchOutside(false)
            dialog1!!.getBehavior().setDraggable(false);

            var tv_succesmsg = view.findViewById<TextView>(R.id.tv_succesmsg)
            var tv_gotit = view.findViewById<TextView>(R.id.tv_gotit)

            tv_succesmsg!!.setText("Success")
            tv_gotit!!.setOnClickListener {
                dialog1.dismiss()
            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"7772  Exception   "+e.toString())
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

                if (dateSelectMode == 0){
                    tie_AssignedDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strAssignedDate =  strYear + "-" + strMonth + "-" + strDay
                }
                else if (dateSelectMode == 1){
                    tie_UpAssignedDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strUpAssignedDate =  strYear + "-" + strMonth + "-" + strDay
                }



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
                assignedToWalkingViewModel.getAssignedToWalking(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (assignedToCount == 0){
                                assignedToCount++
                                Log.e(TAG,"msg   2280   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    val jobjt = jObject.getJSONObject("EmployeeDetails")
                                    assignedToList = jobjt.getJSONArray("EmployeeDetailsList")
                                    if (assignedToList.length()>0){

                                        assignedToPopup(assignedToList)
                                    }
                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingExistingActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun assignedToPopup(assignedToList: JSONArray) {

        try {

            dialogassignedTo = Dialog(this)
            dialogassignedTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogassignedTo!! .setContentView(R.layout.assignedto_popup)
            dialogassignedTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyassignedTo = dialogassignedTo!! .findViewById(R.id.recyassignedTo) as RecyclerView
            val etsearch = dialogassignedTo!! .findViewById(R.id.etsearch) as EditText

            assignedToSortList = JSONArray()
            for (k in 0 until assignedToList.length()) {
                val jsonObject = assignedToList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                assignedToSortList.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@WalkingExistingActivity, 1)
            recyassignedTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = AssignedToAdapter(this@WalkingExistingActivity, assignedToSortList)
            recyassignedTo!!.adapter = adapter
            adapter.setClickListener(this@WalkingExistingActivity)


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
                        val jsonObject = assignedToList.getJSONObject(k)
                        //if (textlength <= jsonObject.getString("EmpName").length) {
                        if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                            || jsonObject.getString("DepartmentName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                            || jsonObject.getString("DesignationName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                            || jsonObject.getString("Branch")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                            assignedToSortList.put(jsonObject)
                        }

                        //}
                    }

                    Log.e(TAG,"assignedToSortList               7103    "+assignedToSortList)
                    val adapter = AssignedToAdapter(this@WalkingExistingActivity, assignedToSortList)
                    recyassignedTo!!.adapter = adapter
                    adapter.setClickListener(this@WalkingExistingActivity)
                }
            })

            dialogassignedTo!!.show()
            dialogassignedTo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    private fun defaultLoad() {

        try {

            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)

            tie_AssignedDate!!.setText(""+sdfDate1.format(newDate))

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
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

    private fun saveWalkingCustomer() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                createWalkingCustomerViewModel.CreateWalkingCustomer(this,strCustomer!!,strPhone!!,ID_AssignedTo!!,strAssignedDate!!,voiceData!!,VoiceLabel!!,strDescription!!,array_walkingUpdate!!,ID_Category!!,
                    ID_Product!!,
                    strProjectName!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (saveCount == 0){
                                saveCount++
                                Log.e(TAG,"msg   4060   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {

                                    Log.i("response1212","in")
                                    tie_Attachvoice!!.setText("")
                                    voiceData= ""
                                  //  voicedataByte!!.fill(0)

                                    successPopup(jObject)
                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingExistingActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun successPopup(jobjt: JSONObject) {
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

            tv_succesmsg!!.setText(jobjt.getString("EXMessage"))

            tv_succesok!!.setOnClickListener {
                suceessDialog!!.dismiss()
                finish()

            }

            suceessDialog!!.show()
            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }catch (e: Exception){

        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}