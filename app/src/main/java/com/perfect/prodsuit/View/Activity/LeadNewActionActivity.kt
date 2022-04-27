package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class LeadNewActionActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {
    val TAG : String = "LeadNewActionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var llCategory: LinearLayout? = null
    private var llProduct: LinearLayout? = null
    private var llAction: LinearLayout? = null
    private var llFollowUpDate: LinearLayout? = null
    private var llDepartment: LinearLayout? = null
    private var llEmployee: LinearLayout? = null
    private var ll_Todate: LinearLayout? = null

    private var imDateclose: ImageView? = null
    var date_Picker1: DatePicker? = null

    private var txtCategory: TextView? = null
    private var txtProduct: TextView? = null
    private var txtAction: TextView? = null
    private var txtActionType: TextView? = null
    private var txtFollowUpDate: TextView? = null
    private var txtDepartment: TextView? = null
    private var txtEmployee: TextView? = null
    private var txtok1: TextView? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategoryArrayList : JSONArray
    var recyProdCategory: RecyclerView? = null
    private var dialogProdCat : Dialog? = null

    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList : JSONArray
    var recyProdDetail: RecyclerView? = null
    private var dialogProdDet : Dialog? = null

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var employeeViewModel: EmployeeViewModel

    lateinit var followUpActionArrayList : JSONArray
    lateinit var departmentArrayList : JSONArray
    lateinit var employeeArrayList : JSONArray

    private var dialogFollowupAction : Dialog? = null
    private var dialogDepartment : Dialog? = null
    private var dialogEmployee : Dialog? = null

    var recyFollowupAction: RecyclerView? = null
    var recyDeaprtment: RecyclerView? = null
    var recyEmployee: RecyclerView? = null

    lateinit var saveNewActionViewModel: SaveNewActionViewModel

    companion object{
        var ID_NextAction : String = ""
        var ID_Category : String?= ""
        var ID_Product : String?= ""
        var dateMode : String?= "1"  // GONE
        var ID_Department : String = ""
        var ID_Employee : String = ""
        var strDate : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lead_new_action)
        context = this@LeadNewActionActivity

        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        saveNewActionViewModel = ViewModelProvider(this).get(SaveNewActionViewModel::class.java)

        setRegViews()
        ResetData()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llCategory = findViewById<LinearLayout>(R.id.llCategory)
        llProduct = findViewById<LinearLayout>(R.id.llProduct)
        llAction = findViewById<LinearLayout>(R.id.llAction)
        llFollowUpDate = findViewById<LinearLayout>(R.id.llFollowUpDate)
        llDepartment = findViewById<LinearLayout>(R.id.llDepartment)
        llEmployee = findViewById<LinearLayout>(R.id.llEmployee)
        ll_Todate = findViewById<LinearLayout>(R.id.ll_Todate)

        imDateclose = findViewById<ImageView>(R.id.imDateclose)
        date_Picker1 = findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1!!.minDate = Calendar.getInstance().timeInMillis

        txtCategory = findViewById<TextView>(R.id.txtCategory)
        txtProduct = findViewById<TextView>(R.id.txtProduct)
        txtAction = findViewById<TextView>(R.id.txtAction)
        txtFollowUpDate = findViewById<TextView>(R.id.txtFollowUpDate)
        txtDepartment = findViewById<TextView>(R.id.txtDepartment)
        txtEmployee = findViewById<TextView>(R.id.txtEmployee)
        txtok1 = findViewById<TextView>(R.id.txtok1)

        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        llCategory!!.setOnClickListener(this)
        llProduct!!.setOnClickListener(this)
        llAction!!.setOnClickListener(this)
        llFollowUpDate!!.setOnClickListener(this)
        llDepartment!!.setOnClickListener(this)
        llEmployee!!.setOnClickListener(this)
        txtok1!!.setOnClickListener(this)

        imDateclose!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)


    }

    private fun ResetData() {
        ID_NextAction = ""
        ID_Category   = ""
        ID_Product = ""
        dateMode = "1"
        ID_Department = ""
        ID_Employee = ""
        strDate = ""

        txtCategory!!.setText("")
        txtProduct!!.setText("")
        txtAction!!.setText("")
        txtFollowUpDate!!.setText("")
        txtDepartment!!.setText("")
        txtEmployee!!.setText("")
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llCategory->{
                  getCategory()
            }
             R.id.llProduct->{

                 if (ID_Category.equals("")){
                     val snackbar: Snackbar = Snackbar.make(v, "Select Category", Snackbar.LENGTH_LONG)
                     snackbar.setActionTextColor(Color.WHITE)
                     snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                     snackbar.show()

                 }
                 else{
                     getProductDetail(ID_Category!!)
                 }
            }
            R.id.llAction->{
                getFollowupAction()
            }
            R.id.llFollowUpDate->{
                if (LeadNextActionActivity.dateMode.equals("0")){
                    ll_Todate!!.visibility = View.GONE
                    LeadNextActionActivity.dateMode = "1"
                }else{
                    ll_Todate!!.visibility = View.VISIBLE
                    LeadNextActionActivity.dateMode = "0"
                }
            }

            R.id.imDateclose->{
                ll_Todate!!.visibility = View.GONE
                LeadNextActionActivity.dateMode = "1"
            }
            R.id.txtok1->{
                try {
                    date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
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
                    txtFollowUpDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    ll_Todate!!.visibility=View.GONE
                    dateMode = "1"
                    strDate = strDay+"-"+strMonth+"-"+strYear
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }

            }
            R.id.llDepartment->{
                getDepartment()
            }
            R.id.llEmployee->{
                if (ID_Department.equals("")){

                    val snackbar: Snackbar = Snackbar.make(v, "Select Department", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                }else{
                    getEmployee()
                }
            }
            R.id.btnReset->{
                ResetData()
            }

            R.id.btnSubmit->{
                Config.Utils.hideSoftKeyBoard(context,v)
                NewActionValidations(v)
            }
        }
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
                                    this@LeadNewActionActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!! .setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!! .findViewById(R.id.recyProdCategory) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNewActionActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ProductCategoryAdapter(this@LeadNewActionActivity, prodCategoryArrayList)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@LeadNewActionActivity)

            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail(ID_Category: String) {
        var proddetail = 0
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
                                    this@LeadNewActionActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!! .setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!! .findViewById(R.id.recyProdDetail) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNewActionActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ProductDetailAdapter(this@LeadNewActionActivity, prodDetailArrayList)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@LeadNewActionActivity)

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getFollowupAction() {
        var followUpAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   125   "+msg)
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
                                    this@LeadNewActionActivity,
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

            val lLayout = GridLayoutManager(this@LeadNewActionActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = FollowupActionAdapter(this@LeadNewActionActivity, followUpActionArrayList)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@LeadNewActionActivity)

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
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
                                    this@LeadNewActionActivity,
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

            val lLayout = GridLayoutManager(this@LeadNewActionActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = DepartmentAdapter(this@LeadNewActionActivity, departmentArrayList)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@LeadNewActionActivity)

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
                                    this@LeadNewActionActivity,
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

            val lLayout = GridLayoutManager(this@LeadNewActionActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = EmployeeAdapter(this@LeadNewActionActivity, employeeArrayList)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@LeadNewActionActivity)

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
            txtCategory!!.setText(jsonObject.getString("CategoryName"))
        }

        if (data.equals("proddetails")){
            dialogProdDet!!.dismiss()
            val jsonObject = prodDetailArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Product   "+jsonObject.getString("ID_Product"))
            ID_Product = jsonObject.getString("ID_Product")
            txtProduct!!.setText(jsonObject.getString("ProductName"))
        }

        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
            val jsonObject = followUpActionArrayList.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            txtAction!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
            val jsonObject = departmentArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            txtDepartment!!.setText(jsonObject.getString("DeptName"))


        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
            val jsonObject = employeeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            txtEmployee!!.setText(jsonObject.getString("EmpName"))


        }
    }

    private fun NewActionValidations(v: View) {

        if (ID_Category!!.equals("")){
            Config.snackBars(context,v,"Select Category")
        }
        else if (ID_Product!!.equals("")){
            Config.snackBars(context,v,"Select Product")
        }
        else if (ID_NextAction!!.equals("")){
            Config.snackBars(context,v,"Select Action")
        }
        else if (strDate!!.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if (ID_Department!!.equals("")){
            Config.snackBars(context,v,"Select Department")
        }
        else if (ID_Employee!!.equals("")){
            Config.snackBars(context,v,"Select Employee")
        }
        else{
            Log.e(TAG,"NewActionValidations  733"
                    +"\n"+"ID_Category      : "+ ID_Category
                    +"\n"+"ID_Product       : "+ ID_Product
                    +"\n"+"ID_NextAction    : "+ ID_NextAction
                    +"\n"+"strDate          : "+ strDate
                    +"\n"+"ID_Department    : "+ ID_Department
                    +"\n"+"ID_Employee      : "+ ID_Employee)

            saveNewtAction()
        }

    }

    private fun saveNewtAction() {
        var saveNexr = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                saveNewActionViewModel.saveNewAction(this,
                    ID_Category!!,
                    ID_Product!!, ID_NextAction,"", strDate!!, ID_Department, ID_Employee
                )!!.observe(
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
                                    if (saveNexr == 0){
                                        saveNexr++
                                        employeePopup(employeeArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadNewActionActivity,
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
}