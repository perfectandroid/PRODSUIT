package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

class ServiceReportActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG: String = "ServiceReportActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var tie_ReportName: TextInputEditText? = null
    var tie_Branch: TextInputEditText? = null
    var tie_FromDate: TextInputEditText? = null
    var tie_ToDate: TextInputEditText? = null
    var tie_EmployeeName: TextInputEditText? = null
    var tie_ComplaintService: TextInputEditText? = null
    var tie_ComplaintType: TextInputEditText? = null

    lateinit var reportNameViewModel: ReportNameViewModel
    lateinit var reportNameArrayList: JSONArray
    lateinit var reportNamesort: JSONArray
    private var dialogReportName: Dialog? = null
    var recyReportName: RecyclerView? = null

    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null

    var empUseBranch = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList : JSONArray
    lateinit var employeeAllSort : JSONArray
    private var dialogEmployeeAll : Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    lateinit var compServiceArrayList: JSONArray
    private var dialogCompService: Dialog? = null
    var recyCompService: RecyclerView? = null

    lateinit var serviceComplaintTypeViewModel: ServiceComplaintTypeViewModel
  //  lateinit var serviceComplaintViewModel: ServiceComplaintViewModel
    lateinit var complaintArrayList : JSONArray
    lateinit var complaintSort : JSONArray
    private var dialogComplaint : Dialog? = null
    var recyComplaint: RecyclerView? = null

    lateinit var serviceArrayList : JSONArray
    lateinit var serviceSort : JSONArray
    private var dialogService : Dialog? = null
    var recyService: RecyclerView? = null

    var btnSubmit: Button? = null
    var btnReset: Button? = null

    var complaintDet = 0
    var ReqMode: String? = ""
    var SubMode: String? = ""

    private var fromToDate: Int = 0
    private var ReportMode: String = ""
    private var ID_Branch: String = ""
    private var ID_Employee: String = ""

    private var ID_CompService: String = ""
    private var ID_ComplaintList: String = ""

    var FromDate: String = ""
    var ToDate: String = ""
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    private var dialogProdDet: Dialog? = null
    var recyProdDetail: RecyclerView? = null


    var flagSubCategory = ""
    var flagBrand = ""
    var flagProduct = ""

    var ID_CompCategory: String? = ""
    var ID_Category: String? = ""
    var ID_SubCategory: String? = ""
    var ID_Brand: String? = ""

    private var ID_Product: String = ""


    private var tie_Category: TextInputEditText? = null
    private var tie_SubCategory: TextInputEditText? = null
    private var tie_Brand: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null

    private var til_Category: TextInputLayout? = null
    private var til_SubCategory: TextInputLayout? = null
    private var til_Brand: TextInputLayout? = null
    private var til_Product: TextInputLayout? = null
    private var dialogCategory : Dialog? = null
    private var dialogSubCategory : Dialog? = null
    var recyCategory: RecyclerView? = null
    lateinit var categorySort : JSONArray
    lateinit var subCategorySort : JSONArray

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var subCategoryViewModel: SubCategoryViewModel
    lateinit var categoryArrayList : JSONArray
    lateinit var subCategoryArrayList : JSONArray

    var categoryDet = 0
    var subCategoryDet = 0
    var brandDet = 0

    lateinit var brandViewModel: BrandViewModel
    lateinit var brandArrayList : JSONArray
    lateinit var brandSort : JSONArray
    private var dialogBrand : Dialog? = null
    var recyBrand: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_report)
        context = this@ServiceReportActivity

        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        reportNameViewModel = ViewModelProvider(this).get(ReportNameViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        serviceComplaintTypeViewModel = ViewModelProvider(this).get(ServiceComplaintTypeViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        subCategoryViewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        brandViewModel = ViewModelProvider(this).get(BrandViewModel::class.java)
        //serviceComplaintViewModel = ViewModelProvider(this).get(ServiceComplaintViewModel::class.java)

        setRegViews()
//        getCrmFlags()

        loadLoginEmpDetails()

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        tie_ReportName = findViewById(R.id.tie_ReportName)
        tie_Branch = findViewById(R.id.tie_Branch)
        tie_FromDate = findViewById(R.id.tie_FromDate)
        tie_ToDate = findViewById(R.id.tie_ToDate)
        tie_EmployeeName = findViewById(R.id.tie_EmployeeName)
        tie_Product = findViewById(R.id.tie_Product)
        tie_ComplaintService = findViewById(R.id.tie_ComplaintService)
        tie_ComplaintType = findViewById(R.id.tie_ComplaintType)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        til_Category = findViewById<TextInputLayout>(R.id.til_Category)
        til_SubCategory = findViewById<TextInputLayout>(R.id.til_SubCategory)
        til_Brand = findViewById<TextInputLayout>(R.id.til_Brand)
        til_Product = findViewById<TextInputLayout>(R.id.til_Product)

        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_SubCategory = findViewById<TextInputEditText>(R.id.tie_SubCategory)
        tie_Brand = findViewById<TextInputEditText>(R.id.tie_Brand)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)

        imback!!.setOnClickListener(this)


        tie_ReportName!!.setOnClickListener(this)
        tie_Branch!!.setOnClickListener(this)
        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_EmployeeName!!.setOnClickListener(this)
        tie_ComplaintService!!.setOnClickListener(this)
        tie_ComplaintType!!.setOnClickListener(this)

        tie_Category!!.setOnClickListener(this)
        tie_SubCategory!!.setOnClickListener(this)
        tie_Brand!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)

        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)

        val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
        var isAdmin = IsAdminSP.getString("IsAdmin", null)

        val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
        var IsManager = IsManagerSP.getString("IsManager", null)

        if (isAdmin.equals("1") && IsManager.equals("0")) {
            tie_Branch!!.setOnClickListener(this)
            tie_EmployeeName!!.setOnClickListener(this)

        }
        else if (isAdmin.equals("0") && IsManager.equals("1")){
            tie_EmployeeName!!.setOnClickListener(this)

        }
    }

    private fun loadLoginEmpDetails() {

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)

        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        tie_EmployeeName!!.setText(UserNameSP.getString("UserName", null))

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
        tie_Branch!!.setText(BranchSP.getString("BranchName", null))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.tie_ReportName -> {

                getReportName()
            }
            R.id.tie_Branch -> {
                getBranch()
            }

            R.id.tie_EmployeeName -> {
                empUseBranch = 0
                getEmpByBranch()
            }

            R.id.tie_FromDate -> {

                openBottomSheet(tie_FromDate,tie_ToDate)
            }

            R.id.tie_ToDate -> {
                openBottomSheet(tie_FromDate,tie_ToDate)
            }

            R.id.tie_ComplaintService -> {
                getReportNameComplaintService()
            }

            R.id.tie_ComplaintType -> {

                if (ID_CompService.equals("")){
                    Config.snackBars(context,v,"Select Complaint/Service")
                }else{
                    Config.disableClick(v)
                    complaintDet = 0
                    ReqMode = "70"
                    SubMode = ""

                    Log.e(TAG,"2111   "+ID_CompService+"  "+ReqMode+"   "+SubMode)

                    getComplaints(ReqMode!!,SubMode!!)
                }


            }

            R.id.tie_Product -> {
                getProductDetail()
            }

//            R.id.tie_Category -> {
//                Config.disableClick(v)
//                categoryDet = 0
//                ReqMode = "77"
//                getCategory(ReqMode!!,ID_CompCategory!!)
//            }
//
//            R.id.tie_SubCategory -> {
//                Config.disableClick(v)
//                if(ID_Category.equals("")){
//                    til_Category!!.setError("Select Category");
//                    til_Category!!.setErrorIconDrawable(null)
//                }
//                else{
//                    subCategoryDet = 0
//                    ReqMode = "125"
//                    getSubCategory(ReqMode!!,ID_Category!!)
//                }
//            }
//
//            R.id.tie_Brand->{
//
//                Config.disableClick(v)
//                brandDet = 0
//                ReqMode = "126"
//                getBrands(ReqMode!!)
//
//            }

            R.id.btnSubmit -> {
                validateData(v)
            }
            R.id.btnReset -> {

                resetData()

            }

        }
    }

    private fun getCrmFlags() {

        try {

            val CRMDetailsSP = context.getSharedPreferences(Config.SHARED_PREF79, 0)
            val strCRMDetails = CRMDetailsSP.getString("CRMDetails", "")
            Log.e(Config.TAG,"139666    "+strCRMDetails)

            val jsonArray: JSONArray = JSONArray(strCRMDetails)

            if (jsonArray.length()!= 0){

                for (k in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(k)
                    if (jsonObject.getString("PSField").equals("10")){
//                Sub Category
                        flagSubCategory = jsonObject.getString("PSValue")
                        Log.e(Config.TAG,"13966610    "+flagSubCategory)
                    }
                    else if (jsonObject.getString("PSField").equals("11")){
//                Brand
                        flagBrand = jsonObject.getString("PSValue")
                        Log.e(Config.TAG,"13966611    "+flagBrand)
                    }
                    else if (jsonObject.getString("PSField").equals("12")){
//                Product
                        flagProduct = jsonObject.getString("PSValue")
                        Log.e(Config.TAG,"13966612    "+flagProduct)
                    }
                }

//            flagSubCategory = Config.getCrmFlags(context,"10")
//            flagBrand       = Config.getCrmFlags(context,"11")
//            flagProduct     = Config.getCrmFlags(context,"12")

                Log.e(TAG,"getFlags  57222   flagSubCategory :  "+flagSubCategory+"  flagBrand :   "+flagBrand+"   flagProduct  :   "+flagProduct)
                if (flagSubCategory.equals("0")){
                    til_SubCategory!!.visibility = View.GONE
                }
                else if (flagSubCategory.equals("1")){
                    til_SubCategory!!.visibility = View.VISIBLE
                }

                if (flagBrand.equals("0")){
                    til_Brand!!.visibility = View.GONE
                }
                else if (flagBrand.equals("1")){
                    til_Brand!!.visibility = View.VISIBLE
                }

                if (flagProduct.equals("0")){
                    til_Product!!.visibility = View.GONE
                }
                else if (flagProduct.equals("1")){
                    til_Product!!.visibility = View.VISIBLE
                }
            }


        }catch (e:Exception){
            Log.e(TAG,"57222  Exception   "+e.toString())
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
                                            this@ServiceReportActivity,
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


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = CategoryAdapter(this@ServiceReportActivity, categorySort)
            recyCategory!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


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
                    val adapter = CategoryAdapter(this@ServiceReportActivity, categorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogCategory!!.show()
            dialogCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }

    private fun getSubCategory(ReqMode : String,ID_Category : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                subCategoryViewModel.getSubCategory(this,ReqMode,ID_Category)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (subCategoryDet == 0){
                                    subCategoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   3572   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("SubCategoryDetailsList")
                                        subCategoryArrayList = jobjt.getJSONArray("SubCategoryList")
                                        if (subCategoryArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            subCategoryPopup(subCategoryArrayList)



                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportActivity,
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

    private fun subCategoryPopup(subCategoryArrayList: JSONArray) {

        try {

            dialogSubCategory = Dialog(this)
            dialogSubCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogSubCategory!! .setContentView(R.layout.category_popup)
            dialogSubCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCategory = dialogSubCategory!! .findViewById(R.id.recyCategory) as RecyclerView
            val etsearch = dialogSubCategory!! .findViewById(R.id.etsearch) as EditText
            val tv_header = dialogSubCategory!! .findViewById(R.id.tv_header) as TextView

            tv_header.setText("Sub Category")

            subCategorySort = JSONArray()
            for (k in 0 until subCategoryArrayList.length()) {
                val jsonObject = subCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                subCategorySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = SubCategoryAdapter(this@ServiceReportActivity, subCategorySort)
            recyCategory!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    subCategorySort = JSONArray()

                    for (k in 0 until subCategoryArrayList.length()) {
                        val jsonObject = subCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("SubCatName").length) {
                            if (jsonObject.getString("SubCatName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                subCategorySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"categorySort               7103    "+subCategorySort)
                    val adapter = SubCategoryAdapter(this@ServiceReportActivity, subCategorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogSubCategory!!.show()
            dialogSubCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }

    private fun getBrands(ReqMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                brandViewModel.getBrands(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (brandDet == 0){
                                    brandDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   3747   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("BrandDetails")
                                        brandArrayList = jobjt.getJSONArray("BrandDetailsList")
                                        if (brandArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            brandPopup(brandArrayList)

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportActivity,
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

    private fun brandPopup(brandArrayList: JSONArray) {

        try {

            dialogBrand = Dialog(this)
            dialogBrand!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBrand!! .setContentView(R.layout.brand_popup)
            dialogBrand!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBrand = dialogBrand!! .findViewById(R.id.recyBrand) as RecyclerView
            val etsearch = dialogBrand!! .findViewById(R.id.etsearch) as EditText
            val tv_header = dialogBrand!! .findViewById(R.id.tv_header) as TextView


            brandSort = JSONArray()
            for (k in 0 until brandArrayList.length()) {
                val jsonObject = brandArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                brandSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyBrand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = BrandAdapter(this@ServiceReportActivity, brandSort)
            recyBrand!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    brandSort = JSONArray()

                    for (k in 0 until brandArrayList.length()) {
                        val jsonObject = brandArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BrandName").length) {
                            if (jsonObject.getString("BrandName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                brandSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"brandSort               7103    "+brandSort)
                    val adapter = BrandAdapter(this@ServiceReportActivity, brandSort)
                    recyBrand!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogBrand!!.show()
            dialogBrand!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    private fun getProductDetail() {
        var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   227   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("ProductDetailsList")
                                    prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                    if (prodDetailArrayList.length() > 0) {
                                        if (proddetail == 0) {
                                            proddetail++
                                            productDetailPopup(prodDetailArrayList)
                                        }

                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceReportActivity,
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
                prodDetailSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductDetailAdapter(this@TicketReportActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@ServiceReportActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


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

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@ServiceReportActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
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

    private fun resetData() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)

        tie_ReportName!!.setText("")
        tie_Branch!!.setText("")
        tie_ComplaintService!!.setText("")
        tie_ComplaintType!!.setText("")

        ReportMode = ""
        ID_Branch = ""
        ID_CompService = ""
        ID_ComplaintList = ""

//        ID_CompService = ""

        tie_Category!!.setText("")
        tie_SubCategory!!.setText("")
        tie_Product!!.setText("")
        tie_Brand!!.setText("")

        ID_Category = ""
        ID_SubCategory = ""
        ID_Brand = ""
        ID_Product = ""



        loadLoginEmpDetails()

    }


    private fun validateData(v: View) {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val fromDa = sdf.parse(tie_FromDate!!.text.toString());
        val toDa = sdf.parse(tie_ToDate!!.text.toString());

        if (ReportMode.equals("")) {
            Config.snackBars(context, v, "Select Report Name")
        } else if (ID_Branch.equals("")) {
            Config.snackBars(context, v, "Select Branch")
        }
        else if (ID_Employee.equals("")) {
            Config.snackBars(context, v, "Select Employee")
        }else if (tie_FromDate!!.text.toString().equals("")) {
            Config.snackBars(context, v, "Select From Date")
        } else if (tie_ToDate!!.text.toString().equals("")) {
            Config.snackBars(context, v, "Select To Date")
        } else if (fromDa.after(toDa)) {
            Config.snackBars(context, v, "Check Selected Date Range")
        } else {
            PassData()
        }

    }

    private fun PassData() {

        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dateFrom = inputFormat.parse(tie_FromDate!!.text.toString())
        val strFromDate = outputFormat.format(dateFrom)
        val dateTo = inputFormat.parse(tie_ToDate!!.text.toString())
        val strToDate = outputFormat.format(dateTo)

        Log.e(TAG, "strFromDate   " + strFromDate + "    " + strToDate)


        intent = Intent(applicationContext, ServiceReportDetailActivity::class.java)
        intent.putExtra("ReportName", tie_ReportName!!.text.toString())
        intent.putExtra("ReportMode", ReportMode)
        intent.putExtra("ID_Branch", ID_Branch)
        intent.putExtra("ID_Employee", ID_Employee)
        intent.putExtra("Fromdate", strFromDate)
        intent.putExtra("Todate", strToDate)
        intent.putExtra("ID_Product", ID_Product)
        intent.putExtra("ID_CompService", ID_CompService)
        intent.putExtra("ID_ComplaintList", ID_ComplaintList)

        startActivity(intent)
    }


    private fun getReportName() {
        var reportName = 0
        var SubMode = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportNameViewModel.getReportName(this,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   1062   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("ReportNameDetails")
                                reportNameArrayList = jobjt.getJSONArray("ReportNameDetailsList")
                                if (reportNameArrayList.length() > 0) {
                                    if (reportName == 0) {
                                        reportName++
                                        reportNamePopup(reportNameArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ServiceReportActivity,
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

    private fun reportNamePopup(reportNameArrayList: JSONArray) {
        try {

            dialogReportName = Dialog(this)
            dialogReportName!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReportName!!.setContentView(R.layout.report_name_popup)
            dialogReportName!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyReportName = dialogReportName!!.findViewById(R.id.recyReportName) as RecyclerView
            val etsearch = dialogReportName!!.findViewById(R.id.etsearch) as EditText

            reportNamesort = JSONArray()
            for (k in 0 until reportNameArrayList.length()) {
                val jsonObject = reportNameArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                reportNamesort.put(jsonObject)
            }

            Log.e(TAG, "reportNamesort               7101    " + reportNamesort)
            Log.e(TAG, "reportNameArrayList      7102    " + reportNameArrayList)


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyReportName!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ReportNameAdapter(this@ServiceReportActivity, reportNamesort,"Service")
            recyReportName!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    reportNamesort = JSONArray()

                    for (k in 0 until reportNameArrayList.length()) {
                        val jsonObject = reportNameArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ReportName").length) {
                            if (jsonObject.getString("ReportName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                reportNamesort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "reportNamesort               7103    " + reportNamesort)
                    val adapter = ReportNameAdapter(this@ServiceReportActivity, reportNamesort,"Service")
                    recyReportName!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogReportName!!.show()
            dialogReportName!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getBranch() {
        var branch = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this, "0",SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   1062   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("BranchDetails")
                                    branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                    if (branchArrayList.length() > 0) {
                                        if (branch == 0) {
                                            branch++
                                            branchPopup(branchArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceReportActivity,
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

    private fun branchPopup(branchArrayList: JSONArray) {

        try {

            dialogBranch = Dialog(this)
            dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranch!!.setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!!.findViewById(R.id.recyBranch) as RecyclerView
            val etsearch = dialogBranch!!.findViewById(R.id.etsearch) as EditText

            branchsort = JSONArray()
            for (k in 0 until branchArrayList.length()) {
                val jsonObject = branchArrayList.getJSONObject(k)
                branchsort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = BranchAdapter(this@ServiceReportActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    branchsort = JSONArray()

                    for (k in 0 until branchArrayList.length()) {
                        val jsonObject = branchArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BranchName").length) {
                            if (jsonObject.getString("BranchName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                branchsort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "branchsort               7103    " + branchsort)
                    val adapter = BranchAdapter(this@ServiceReportActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogBranch!!.show()
            dialogBranch!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getEmpByBranch() {
//         var branch = 0
        Log.v("sfsdfsdfdf","branch"+ID_Branch)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                empByBranchViewModel.getEmpByBranch(this, ID_Branch)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (empUseBranch == 0){
                                    empUseBranch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeAllArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length()>0){

                                            employeeAllPopup(employeeAllArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportActivity,
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

    private fun employeeAllPopup(employeeAllArrayList: JSONArray) {
        try {

            dialogEmployeeAll = Dialog(this)
            dialogEmployeeAll!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAll!! .setContentView(R.layout.employeeall_popup)
            dialogEmployeeAll!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAll = dialogEmployeeAll!! .findViewById(R.id.recyEmployeeAll) as RecyclerView
            val etsearch = dialogEmployeeAll!! .findViewById(R.id.etsearch) as EditText


            employeeAllSort = JSONArray()
            for (k in 0 until employeeAllArrayList.length()) {
                val jsonObject = employeeAllArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeAllSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
            val adapter = EmployeeAllAdapter(this@ServiceReportActivity, employeeAllSort)
            recyEmployeeAll!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeAllSort = JSONArray()

                    for (k in 0 until employeeAllArrayList.length()) {
                        val jsonObject = employeeAllArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeAllSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllSort               7103    "+employeeAllSort)
                    val adapter = EmployeeAllAdapter(this@ServiceReportActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openBottomSheet(dateField1:TextInputEditText?,dateField2:TextInputEditText?) {
        // BottomSheet
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_date_chooser, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val edt_fromDate = view.findViewById<EditText>(R.id.edt_fromDate)
        val edt_toDate = view.findViewById<EditText>(R.id.edt_toDate)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val rad_this_month = view.findViewById<RadioButton>(R.id.rad_this_month)
        val rad_last_month = view.findViewById<RadioButton>(R.id.rad_last_month)
        val rad_last_3_month = view.findViewById<RadioButton>(R.id.rad_last_3_month)
        val rad_last_6_month = view.findViewById<RadioButton>(R.id.rad_last_6_month)
        val rad_last_12_month = view.findViewById<RadioButton>(R.id.rad_last_12_month)
        rad_this_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")

            val calendar: Calendar = Calendar.getInstance()
            // calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar.getTime()
            rad_this_month!!.isChecked = true
            rad_last_month!!.isChecked = false
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = false


            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")

            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked = true
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = false

            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_3_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -3)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =true
            rad_last_6_month!!.isChecked =false
            rad_last_12_month!!.isChecked =false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_6_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -6)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =false
            rad_last_6_month!!.isChecked =true
            rad_last_12_month!!.isChecked =false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        rad_last_12_month.setOnClickListener(View.OnClickListener {
            FromDate = ""
            ToDate = ""
            edt_fromDate!!.setText("")
            edt_toDate!!.setText("")
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -12)
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val FirstDay: Date = calendar.getTime()
            val calendar1: Calendar = Calendar.getInstance()
            calendar1.add(Calendar.MONTH, -1)
            calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH))
            val LastDay: Date = calendar1.getTime()
            rad_this_month!!.isChecked = false
            rad_last_month!!.isChecked =false
            rad_last_3_month!!.isChecked =false
            rad_last_6_month!!.isChecked =false
            rad_last_12_month!!.isChecked =true
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " +FromDate)
            Log.v("sdfsfdfdddd", "ToDate " +ToDate)
        })
        edt_fromDate.setOnClickListener(View.OnClickListener {
            fromToDate = 0
            datePicker(edt_fromDate)
        })
        edt_toDate.setOnClickListener(View.OnClickListener {
            fromToDate = 1
            datePicker(edt_toDate)
        })

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            if(edt_fromDate.text.toString()==""&&edt_toDate.text.toString()!="")
            {
                val builder = AlertDialog.Builder(
                    this@ServiceReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            else if(edt_fromDate.text.toString()!=""&&edt_toDate.text.toString()=="")
            {
                val builder = AlertDialog.Builder(
                    this@ServiceReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            else
            {
                dialog.dismiss()
                dateField1!!.setText(FromDate)
                dateField2!!.setText(ToDate)
            }

//                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
//                val day: Int = date_Picker1!!.getDayOfMonth()
//                val mon: Int = date_Picker1!!.getMonth()
//                val month: Int = mon + 1
//                val year: Int = date_Picker1!!.getYear()
//                var strDay = day.toString()
//                var strMonth = month.toString()
//                var strYear = year.toString()
//                if (strDay.length == 1) {
//                    strDay = "0" + day
//                }
//                if (strMonth.length == 1) {
//                    strMonth = "0" + strMonth
//                }
//
//                if (fromToDate == 0) {
//                    tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                }
//                if (fromToDate == 1) {
//                    tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun datePicker(dateField:TextView) {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

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

                if (fromToDate == 0) {
                    tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    FromDate=strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" +strDay + "-" + strMonth + "-" + strYear)
                }
                if (fromToDate == 1) {
                    tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    ToDate=strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" +strDay + "-" + strMonth + "-" + strYear)
                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()

    }

    private fun getReportNameComplaintService() {

        var compServiceList = Config.getCompliantOrService(this@ServiceReportActivity)
        Log.e(TAG,"compServiceList   680  "+compServiceList)
        compServiceArrayList  = JSONArray()
        val jObject = JSONObject(compServiceList)
        val jobjt = jObject.getJSONObject("compServiceType")
        compServiceArrayList = jobjt.getJSONArray("compServiceDetails")
        Log.e(TAG,"compServiceArrayList   6801  "+compServiceArrayList)

        compServicePopup(compServiceArrayList)
    }

    private fun compServicePopup(compServiceArrayList: JSONArray) {

        try {

            Log.e(TAG, "Click " )
            dialogCompService = Dialog(this)
            dialogCompService!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCompService!!.setContentView(R.layout.complaint_service_popup)
            dialogCompService!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCompService = dialogCompService!!.findViewById(R.id.recyCompService) as RecyclerView



            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyCompService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ComplaintServiceAdapter(this@ServiceReportActivity, compServiceArrayList)
            recyCompService!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)

            dialogCompService!!.show()
            dialogCompService!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getComplaints(ReqMode: String, SubMode: String) {
        val ID_Category ="0"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceComplaintTypeViewModel.getserviceComplaintData(this,ReqMode!!,SubMode!!,/*ID_Category*/ID_CompService!!)!!.observe(
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

                                        val jobjt = jObject.getJSONObject("ComplaintService")

                                        if (ID_CompService.equals("1")){
                                            complaintArrayList = jobjt.getJSONArray("ComplaintList")
                                            if (complaintArrayList.length()>0){

                                                complaintPopup(complaintArrayList)

                                            }
                                        }else if (ID_CompService.equals("2")){
                                            serviceArrayList = jobjt.getJSONArray("ServiceList")
                                            if (serviceArrayList.length()>0){

                                                servicePopup(serviceArrayList)
                                            }
                                        }



                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceReportActivity,
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


            Log.e(TAG,"complaintSort    2203234    "+complaintSort)
            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyComplaint!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceComplaintTypeAdapter(this@ServiceReportActivity, complaintSort)
            recyComplaint!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


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
                        if (textlength <= jsonObject.getString("CompntName").length) {
                            if (jsonObject.getString("CompntName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                complaintSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"complaintSort     dfgsdgsd          2203    "+complaintSort)
                    val adapter = ServiceComplaintAdapter(this@ServiceReportActivity, complaintSort)
                    recyComplaint!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogComplaint!!.show()
            dialogComplaint!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

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


            val lLayout = GridLayoutManager(this@ServiceReportActivity, 1)
            recyService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceAdapter1(this@ServiceReportActivity, serviceSort)
            recyService!!.adapter = adapter
            adapter.setClickListener(this@ServiceReportActivity)


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
                        if (textlength <= jsonObject.getString("ServiceName").length) {
                            if (jsonObject.getString("ServiceName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                serviceSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"serviceSort               1556    "+serviceSort)
                    val adapter = ServiceAdapter1(this@ServiceReportActivity, serviceSort)
                    recyService!!.adapter = adapter
                    adapter.setClickListener(this@ServiceReportActivity)
                }
            })

            dialogService!!.show()
            dialogService!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("reportname")) {
           // resetData()
            dialogReportName!!.dismiss()
            //  val jsonObject = reportNameArrayList.getJSONObject(position)
            val jsonObject = reportNamesort.getJSONObject(position)
            Log.e(TAG, "ReportMode   " + jsonObject.getString("ReportMode"))
            ReportMode = jsonObject.getString("ReportMode")
            tie_ReportName!!.setText(jsonObject.getString("ReportName"))

////            if (ReportMode.equals("1")) {
//////                ActionListT
////                til_FollowUpAction!!.visibility = View.VISIBLE
////                til_FollowUpType!!.visibility = View.VISIBLE
////            }
//            if (ReportMode.equals("2")) {
////                FollowUpTicket
////                til_FollowUpAction!!.visibility = View.VISIBLE
////                til_FollowUpType!!.visibility = View.VISIBLE
//            }
////            if (ReportMode.equals("4")) {
//////                StatusList
////                til_FollowUpAction!!.visibility = View.GONE
////                til_FollowUpType!!.visibility = View.GONE
////            }
//            if (ReportMode.equals("5")) {
////                NewListTicket
////                til_FollowUpAction!!.visibility = View.GONE
////                til_FollowUpType!!.visibility = View.GONE
        }

        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
            //   val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchsort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))

            ID_Employee = ""
            tie_EmployeeName!!.setText("")


        }

        if (data.equals("employeeAll")){
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_EmployeeName!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("CompService")) {
            dialogCompService!!.dismiss()
            val jsonObject = compServiceArrayList.getJSONObject(position)

            ID_CompService = jsonObject.getString("compService_id")
            tie_ComplaintService!!.setText(jsonObject.getString("compService_name"))

            ID_ComplaintList = ""
            tie_ComplaintType!!.setText("")


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

            ID_SubCategory = ""
            tie_SubCategory!!.setText("")
            ID_Brand = ""
            tie_Brand!!.setText("")


        }

        if (data.equals("SubCategoryClick")) {
            dialogSubCategory!!.dismiss()
            val jsonObject = subCategorySort.getJSONObject(position)
            Log.e(TAG,"ID_SubCategory   "+jsonObject.getString("ID_SubCategory"))

            ID_SubCategory = jsonObject.getString("ID_SubCategory")
            tie_SubCategory!!.setText(jsonObject.getString("SubCatName"))

            ID_Brand = ""
            tie_Brand!!.setText("")
        }

        if (data.equals("BrandClick")) {
            dialogBrand!!.dismiss()
            val jsonObject = brandSort.getJSONObject(position)
            Log.e(TAG,"ID_Brand   "+jsonObject.getString("ID_Brand"))

            ID_Brand = jsonObject.getString("ID_Brand")
            tie_Brand!!.setText(jsonObject.getString("BrandName"))
        }

        if (data.equals("compalint")) {

            dialogComplaint!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = complaintSort.getJSONObject(position)
            Log.e(TAG,"ID_ComplaintList   "+jsonObject.getString("ID_ComplaintList"))
            ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
            tie_ComplaintType!!.setText(jsonObject.getString("CompntName"))

        }

        if (data.equals("service")) {

            dialogService!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = serviceSort.getJSONObject(position)
            Log.e(TAG,"ID_Service   "+jsonObject.getString("ID_Service"))

            ID_ComplaintList = jsonObject.getString("ID_Service")
            tie_ComplaintType!!.setText(jsonObject.getString("ServiceName"))

        }

        if (data.equals("proddetails")) {
            dialogProdDet!!.dismiss()
//            val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
            ID_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))
        }

    }


}