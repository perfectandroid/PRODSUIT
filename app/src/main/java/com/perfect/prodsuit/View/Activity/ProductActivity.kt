package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import android.view.*
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import java.text.ParseException
import java.util.*
import java.text.SimpleDateFormat


class ProductActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    val TAG: String = "ProductActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    private var SELECT_PRODUCT: Int? = 102
    var edt_category: EditText? = null
    var edt_product: EditText? = null
    var edt_status: EditText? = null
    var edt_priority: EditText? = null
    var edt_date: EditText? = null
    var edt_action: EditText? = null
    var edt_type: EditText? = null
    var edt_barnchtype: EditText? = null
    var edt_branch: EditText? = null
    var edt_department: EditText? = null
    var edt_Employee: EditText? = null
    var edt_qty: EditText? = null
    var edt_feedback: EditText? = null
    var btnReset: Button? = null
    var btnSubmit: Button? = null
    var img_search: ImageView? = null
    var llfollowup: LinearLayout? = null
    var llNeedTransfer: LinearLayout? = null
    var recyProdCategory: RecyclerView? = null
    var recyProdDetail: RecyclerView? = null
    var recyProdStatus: RecyclerView? = null
    var recyProdPriority: RecyclerView? = null
    var recyFollowupAction: RecyclerView? = null
    var recyFollowupType: RecyclerView? = null
    var recyBranchType: RecyclerView? = null
    var recyBranch: RecyclerView? = null
    var recyDeaprtment: RecyclerView? = null
    var recyEmployee: RecyclerView? = null
    var switchTransfer: Switch? = null
    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var branchTypeViewModel: BranchTypeViewModel
    lateinit var branchViewModel: BranchViewModel
    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var prodCategoryArrayList : JSONArray
    lateinit var prodDetailArrayList : JSONArray
    lateinit var prodStatusArrayList : JSONArray
    lateinit var prodPriorityArrayList : JSONArray
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpTypeArrayList : JSONArray
    lateinit var branchTypeArrayList : JSONArray
    lateinit var branchArrayList : JSONArray
    lateinit var departmentArrayList : JSONArray
    lateinit var employeeArrayList : JSONArray
    private var dialogProdCat : Dialog? = null
    private var dialogProdDet : Dialog? = null
    private var dialogProdStatus : Dialog? = null
    private var dialogProdPriority : Dialog? = null
    private var dialogFollowupAction : Dialog? = null
    private var dialogFollowupType : Dialog? = null
    private var dialogBranchType : Dialog? = null
    private var dialogBranch : Dialog? = null
    private var dialogDepartment : Dialog? = null
    private var dialogEmployee : Dialog? = null

    companion object {
        var ID_Category : String?= ""
        var ID_Product : String?= ""
        var ID_Status : String?= ""
        var ID_Priority : String?= ""
        var strProdName : String = ""
        var ID_NextAction : String = ""
        var ID_ActionType : String = ""
        var ID_BranchType : String = ""
        var ID_Branch : String = ""
        var ID_Department : String = ""
        var ID_Employee : String = ""
        var strQty : String = ""
        var strFeedback : String = ""
        var strFollowupdate : String = ""
        var strNeedCheck : String = "0"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_product)
        context = this@ProductActivity
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        branchTypeViewModel = ViewModelProvider(this).get(BranchTypeViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        setRegViews()
        bottombarnav()
        getCalendarId(context)
        clearData()
        resetData()
        switchTransfer!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                llNeedTransfer!!.visibility = View.VISIBLE
                edt_barnchtype!!.setText("")
                edt_branch!!.setText("")
                edt_department!!.setText("")
                edt_Employee!!.setText("")
                strNeedCheck = "1"
            } else {
                llNeedTransfer!!.visibility = View.GONE
                edt_barnchtype!!.setText("")
                edt_branch!!.setText("")
                edt_department!!.setText("")
                edt_Employee!!.setText("")
                strNeedCheck = "0"
            }
        }
    }

    private fun clearData() {
        ID_Category = ""
        ID_Product = ""
        strProdName = ""
        strQty = ""
        ID_Priority = ""
        strFeedback = ""
        ID_Status = ""
        ID_NextAction = ""
        ID_ActionType = ""
        strFollowupdate = ""
        strNeedCheck = "0"
        ID_BranchType = ""
        ID_Branch = ""
        ID_Department = ""
        ID_Employee = ""
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        img_search = findViewById<ImageView>(R.id.img_search)
        edt_category = findViewById<EditText>(R.id.edt_category)
        edt_product = findViewById<EditText>(R.id.edt_product)
        edt_status = findViewById<EditText>(R.id.edt_status)
        edt_priority = findViewById<EditText>(R.id.edt_priority)
        edt_date = findViewById<EditText>(R.id.edt_date)
        edt_action = findViewById<EditText>(R.id.edt_action)
        edt_type = findViewById<EditText>(R.id.edt_type)
        edt_barnchtype = findViewById<EditText>(R.id.edt_barnchtype)
        edt_branch = findViewById<EditText>(R.id.edt_branch)
        edt_department = findViewById<EditText>(R.id.edt_department)
        edt_Employee = findViewById<EditText>(R.id.edt_Employee)
        edt_qty = findViewById<EditText>(R.id.edt_qty)
        edt_feedback = findViewById<EditText>(R.id.edt_feedback)
        llfollowup = findViewById<LinearLayout>(R.id.llfollowup)
        llNeedTransfer = findViewById<LinearLayout>(R.id.llNeedTransfer)
        switchTransfer = findViewById<Switch>(R.id.switchTransfer)
        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        imback!!.setOnClickListener(this)
        img_search!!.setOnClickListener(this)
        edt_category!!.setOnClickListener(this)
        edt_product!!.setOnClickListener(this)
        edt_status!!.setOnClickListener(this)
        edt_priority!!.setOnClickListener(this)
        edt_date!!.setOnClickListener(this)
        edt_action!!.setOnClickListener(this)
        edt_type!!.setOnClickListener(this)
        edt_barnchtype!!.setOnClickListener(this)
        edt_branch!!.setOnClickListener(this)
        edt_department!!.setOnClickListener(this)
        edt_Employee!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.edt_category->{
               getCategory()
            }
            R.id.img_search->{
                strProdName = edt_product!!.text.toString()
                if (ID_Category.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Select Category", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }
                else if (strProdName.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Enter Product", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }
                else{
                    getProductDetail(strProdName)
                }
            }
            R.id.edt_product->{
                if (ID_Category.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Select Category", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }else{
                    getProductDetail(strProdName)
                }
            }
            R.id.edt_priority->{
                getProductPriority()
            }
            R.id.edt_status->{
                getProductStatus()
            }
            R.id.edt_date->{

                datePickerPopup()

            }
            R.id.edt_action->{

                getFollowupAction()
            }
            R.id.edt_type->{

                  getFollowupType()
            }

            R.id.edt_barnchtype->{

                getBranchType()
            }

            R.id.edt_branch->{

                getBranch()
            }

            R.id.edt_department->{

                getDepartment()
            }

            R.id.edt_Employee->{

                getEmployee()
            }

            R.id.btnReset->{

               resetData()
            }
            R.id.btnSubmit->{
                Config.Utils.hideSoftKeyBoard(this@ProductActivity,v)
              validations(v)
            }
        }
    }

    private fun resetData() {
        edt_category!!.setText("")
        edt_product!!.setText("")
        edt_qty!!.setText("")
        edt_priority!!.setText("")
        edt_feedback!!.setText("")
        edt_status!!.setText("")
        edt_action!!.setText("")
        edt_type!!.setText("")
        edt_date!!.setText("")
        edt_barnchtype!!.setText("")
        edt_branch!!.setText("")
        edt_department!!.setText("")
        edt_Employee!!.setText("")
        switchTransfer!!.isChecked = false
        llfollowup!!.visibility = View.GONE
        llNeedTransfer!!.visibility = View.GONE
        ID_Category = ""
        ID_Product = ""
        strProdName = ""
        strQty = ""
        ID_Priority = ""
        strFeedback = ""
        ID_Status = ""
        ID_NextAction = ""
        ID_ActionType = ""
        strFollowupdate = ""
        strNeedCheck = "0"
        ID_BranchType = ""
        ID_Branch = ""
        ID_Department = ""
        ID_Employee = ""
    }

    private fun validations(v: View) {
        strQty = edt_qty!!.text.toString()
        strFeedback = edt_feedback!!.text.toString()
        if (ID_Category.equals("")){
            warningMessage(v,"Select Category")
        }
        else if (ID_Product.equals("")){
            warningMessage(v,"Select Product")
        }
        else if (strQty.equals("")){
            warningMessage(v,"Enter quantity")
        }
        else if (ID_Priority.equals("")){
            warningMessage(v,"Select Priority")
        }
        else if (ID_Status.equals("")){
            warningMessage(v,"Select Status")
        }
        else{
            if (ID_Status.equals("1")){
                validations1(v)
            }else{
                PassDatas()
            }
        }
    }

    private fun validations1(v: View) {
        strFollowupdate = edt_date!!.text.toString()
        if (ID_NextAction.equals("")){
            warningMessage(v,"Select Action")
        }
        else if (ID_ActionType.equals("")){
            warningMessage(v,"Select Type")
        }
        else if (strFollowupdate.equals("")){
            warningMessage(v,"Select date")
        }else{
            if (strNeedCheck.equals("1")){
                Log.e(TAG,"need Transfer")
                validations2(v)
            }else{
                Log.e(TAG,"No Transfer")
                PassDatas()
            }
        }
    }

    private fun validations2(v: View) {
        if (ID_BranchType.equals("")){
            warningMessage(v,"Select Branch Type")
        }
        else if (ID_Branch.equals("")){
            warningMessage(v,"Select Branch")
        }
        else if (ID_Department.equals("")){
            warningMessage(v,"Select Department")
        }
        else if (ID_Employee.equals("")){
            warningMessage(v,"Select Employee")
        }
        else{
            PassDatas()
        }
    }

    private fun PassDatas() {
        val intent = Intent()
        intent.putExtra("ID_Category", ID_Category)
        intent.putExtra("ID_Product", ID_Product)
        intent.putExtra("strProdName", strProdName)
        intent.putExtra("strQty", strQty)
        intent.putExtra("ID_Priority", ID_Priority)
        intent.putExtra("strFeedback", strFeedback)
        intent.putExtra("ID_Status", ID_Status)
        intent.putExtra("ID_NextAction", ID_NextAction)
        intent.putExtra("ID_ActionType", ID_ActionType)
        intent.putExtra("strFollowupdate", strFollowupdate)
        intent.putExtra("strNeedCheck", strNeedCheck)
        intent.putExtra("ID_BranchType", strNeedCheck)
        intent.putExtra("ID_Branch", ID_Branch)
        intent.putExtra("ID_Department", ID_Department)
        intent.putExtra("ID_Employee", ID_Employee)
        setResult(SELECT_PRODUCT!!, intent)
        finish()
    }

    private fun warningMessage(v: View ,message: String) {
        val snackbar: Snackbar = Snackbar.make(v, ""+message, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
        snackbar.show()
    }

    private fun getCategory() {
        var prodcategory = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                prodCategoryArrayList = jobjt.getJSONArray("CategoryList")
                                if (prodCategoryArrayList.length()>0){
                                    if (prodcategory == 0){
                                        prodcategory++
                                        productCategoryPopup(prodCategoryArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {
            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!! .setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!! .findViewById(R.id.recyProdCategory) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductCategoryAdapter(this@ProductActivity, prodCategoryArrayList)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail(strProdName: String) {
        var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, ID_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   227   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("ProductDetailsList")
                                prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                if (prodDetailArrayList.length()>0){
                                    if (proddetail == 0){
                                        proddetail++
                                        productDetailPopup(prodDetailArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {
        try {
            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!! .setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!! .findViewById(R.id.recyProdDetail) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductDetailAdapter(this@ProductActivity, prodDetailArrayList)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
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
                                    this@ProductActivity,
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
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductPriorityAdapter(this@ProductActivity, prodPriorityArrayList)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductStatus() {
        var prodstatus = 0
        var ReqMode = "15"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   333   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("StatusDetailsList")
                                prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                if (prodStatusArrayList.length()>0){
                                    if (prodstatus == 0){
                                        prodstatus++
                                        productStatusPopup(prodStatusArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun productStatusPopup(prodStatusArrayList: JSONArray) {
        try {
            dialogProdStatus = Dialog(this)
            dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdStatus!! .setContentView(R.layout.product_status_popup)
            dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdStatus = dialogProdStatus!! .findViewById(R.id.recyProdStatus) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductStatusAdapter(this@ProductActivity, prodStatusArrayList)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogProdStatus!!.show()
            dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFollowupAction() {
        var followUpAction = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                if (followUpActionArrayList.length()>0){
                                    if (followUpAction == 0){
                                        followUpAction++
                                        followUpActionPopup(followUpActionArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {
        try {
            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FollowupActionAdapter(this@ProductActivity, followUpActionArrayList)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFollowupType() {
        var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                if (followUpTypeArrayList.length()>0){
                                    if (followUpType == 0){
                                        followUpType++
                                        followupTypePopup(followUpTypeArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {
        try {
            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FollowupTypeAdapter(this@ProductActivity, followUpTypeArrayList)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
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
                edt_date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
            }
            txtcancel.setOnClickListener {
                dialogDate.dismiss()
            }
            dialogDate!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBranchType() {
        var branchType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchTypeViewModel.getBranchType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   979   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("BranchTypeDetails")
                                branchTypeArrayList = jobjt.getJSONArray("BranchTypeDetailsList")
                                if (branchTypeArrayList.length()>0){
                                    if (branchType == 0){
                                        branchType++
                                        branchTypePopup(branchTypeArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun branchTypePopup(branchTypeArrayList: JSONArray) {
        try {
            dialogBranchType = Dialog(this)
            dialogBranchType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranchType!! .setContentView(R.layout.branchtype_popup)
            dialogBranchType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranchType = dialogBranchType!! .findViewById(R.id.recyBranchType) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyBranchType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = BranchTypeAdapter(this@ProductActivity, branchTypeArrayList)
            recyBranchType!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogBranchType!!.show()
            dialogBranchType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBranch() {
        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this,"0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1062   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("BranchDetails")
                                branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                if (branchArrayList.length()>0){
                                    if (branch == 0){
                                        branch++
                                        branchPopup(branchArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun branchPopup(branchArrayList: JSONArray) {
        try {
            dialogBranch = Dialog(this)
            dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranch!! .setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!! .findViewById(R.id.recyBranch) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = BranchAdapter(this@ProductActivity, branchArrayList)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogBranch!!.show()
            dialogBranch!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1132   "+e.toString())
        }
    }

    private fun getDepartment() {
        var department = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                departmentViewModel.getDepartment(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1142   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("DepartmentDetails")
                                departmentArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                if (departmentArrayList.length()>0){
                                    if (department == 0){
                                        department++
                                        departmentPopup(departmentArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun departmentPopup(departmentArrayList: JSONArray) {
        try {
            dialogDepartment = Dialog(this)
            dialogDepartment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDepartment!! .setContentView(R.layout.department_popup)
            dialogDepartment!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDeaprtment = dialogDepartment!! .findViewById(R.id.recyDeaprtment) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = DepartmentAdapter(this@ProductActivity, departmentArrayList)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogDepartment!!.show()
            dialogDepartment!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmployee() {
        var employee = 0
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
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1224   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("EmployeeDetails")
                                employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                if (employeeArrayList.length()>0){
                                    if (employee == 0){
                                        employee++
                                        employeePopup(employeeArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProductActivity,
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

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {
            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!! .setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!! .findViewById(R.id.recyEmployee) as RecyclerView
            val lLayout = GridLayoutManager(this@ProductActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = EmployeeAdapter(this@ProductActivity, employeeArrayList)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ProductActivity)
            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                        val i = Intent(this@ProductActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.reminder -> {
                        setReminder()
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

    private fun setReminder() {
        try
        {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reminder_setter_popup, null)
            val btncancel = layout.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout.findViewById(R.id.btnsubmit) as Button
            etdate = layout.findViewById(R.id.etdate) as EditText
            ettime = layout.findViewById(R.id.ettime) as EditText
            etdis = layout.findViewById(R.id.etdis) as EditText
            etdate!!.setKeyListener(null)
            ettime!!.setKeyListener(null)
            builder.setView(layout)
            val alertDialog = builder.create()
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val sdf1 = SimpleDateFormat("hh:mm a")
            val sdf2 = SimpleDateFormat("hh:mm")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            etdate!!.setText(sdf.format(c.time))
            ettime!!.setText(sdf1.format(c.time))
            val s = sdf2.format(c.time)
            val split = s.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val strhr = split[0]
            val strmin = split[1]
            hr = Integer.parseInt(strhr)
            min = Integer.parseInt(strmin)
            ettime!!.setOnClickListener(View.OnClickListener { timeSelector() })
            etdate!!.setOnClickListener(View.OnClickListener { dateSelector() })
            btncancel.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                alertDialog.dismiss() }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun timeSelector() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val strDate = String.format(
                            "%02d:%02d %s", if (hourOfDay == 0) 12 else hourOfDay,
                            minute, if (hourOfDay < 12) "am" else "pm"
                    )
                    ettime!!.setText(strDate)
                    hr = hourOfDay
                    min = minute
                }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }

    fun dateSelector() {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        yr = year
                        month = monthOfYear
                        day = dayOfMonth
                        etdate!!.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    }, mYear, mMonth, mDay
            )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
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
                startActivity(Intent(this@ProductActivity, WelcomeActivity::class.java))
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("prodcategory")){
            dialogProdCat!!.dismiss()
            val jsonObject = prodCategoryArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Category   "+jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            edt_category!!.setText(jsonObject.getString("CategoryName"))
        }
        if (data.equals("proddetails")){
            dialogProdDet!!.dismiss()
            val jsonObject = prodDetailArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Product   "+jsonObject.getString("ID_Product"))
            ID_Product = jsonObject.getString("ID_Product")
            edt_product!!.setText(jsonObject.getString("ProductName"))
        }
        if (data.equals("prodpriority")){
            dialogProdPriority!!.dismiss()
            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            edt_priority!!.setText(jsonObject.getString("PriorityName"))
        }
        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
            val jsonObject = prodStatusArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            edt_status!!.setText(jsonObject.getString("StatusName"))
            edt_action!!.setText("")
            ID_NextAction=""
            edt_type!!.setText("")
            ID_ActionType = ""
            if (jsonObject.getString("ID_Status").equals("1")){
                llfollowup!!.visibility  =View.VISIBLE
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = sdf.format(Date())
                edt_date!!.setText(currentDate)
                switchTransfer!!.isChecked = false
            }else{
                llfollowup!!.visibility  =View.GONE
                switchTransfer!!.isChecked = false
            }
        }
        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
            val jsonObject = followUpActionArrayList.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            edt_action!!.setText(jsonObject.getString("NxtActnName"))
        }
        if (data.equals("followuptype")){
            dialogFollowupType!!.dismiss()
            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            edt_type!!.setText(jsonObject.getString("ActnTypeName"))
        }
        if (data.equals("branchtype")){
            dialogBranchType!!.dismiss()
            val jsonObject = branchTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_BranchType   "+jsonObject.getString("ID_BranchType"))
            ID_BranchType = jsonObject.getString("ID_BranchType")
            edt_barnchtype!!.setText(jsonObject.getString("BranchTypeName"))
        }
        if (data.equals("branch")){
            dialogBranch!!.dismiss()
            val jsonObject = branchArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Branch   "+jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            edt_branch!!.setText(jsonObject.getString("BranchName"))
        }
        if (data.equals("department")){
            dialogDepartment!!.dismiss()
            val jsonObject = departmentArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            edt_department!!.setText(jsonObject.getString("DeptName"))
        }
        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
            val jsonObject = employeeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            edt_Employee!!.setText(jsonObject.getString("EmpName"))
        }
    }
    private fun getCalendarId(context: Context): Long? {

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR),
                1
            )
        }

        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

        var calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )
        if (calCursor != null && calCursor.count <= 0) {
            calCursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }
        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calName: String
                val calID: String
                val nameCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calName = calCursor.getString(nameCol)
                calID = calCursor.getString(idCol)

                //    Log.d("Calendar name = $calName Calendar ID = $calID")

                calCursor.close()
                return calID.toLong()
            }
        }
        return null




    }
    fun addEvent(iyr: Int, imnth: Int, iday: Int, ihour: Int, imin: Int, descriptn: String, Title: String) {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                1
            )
        }
        val cr = contentResolver
        val beginTime = Calendar.getInstance()
        beginTime.set(2022, 11 - 1, 28, 9, 30)
        val endTime = Calendar.getInstance()
        endTime.set(iyr, imnth, iday, ihour, imin)
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, endTime.timeInMillis)
        values.put(CalendarContract.Events.DTEND, endTime.timeInMillis)
        values.put(CalendarContract.Events.TITLE, Title)
        values.put(CalendarContract.Events.DESCRIPTION, descriptn)


        val calendarId = getCalendarId(context)
        Log.i("Calender", calendarId.toString())
        if(calendarId != null) {
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId)
        }


        val tz = TimeZone.getDefault()
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.id)
        values.put(CalendarContract.Events.EVENT_LOCATION, "India")





        try {
            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
            val reminders = ContentValues()
            reminders.put(CalendarContract.Reminders.EVENT_ID, uri!!.lastPathSegment)
            reminders.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_ALERT
            )
            reminders.put(CalendarContract.Reminders.MINUTES, 10)
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders)
        }catch (e: Exception){
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Reminder set successfully.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }
}