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
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.FullLenghRecyclertview
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelStockTransferDetails
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class StockTransferActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "StockTransferActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var tie_Date : TextInputEditText? = null
    var tie_Request : TextInputEditText? = null
    var tie_FromBranch : TextInputEditText? = null
    var tie_FromDepartment : TextInputEditText? = null
    var tie_FromEmployee : TextInputEditText? = null

    var tie_ToBranch : TextInputEditText? = null
    var tie_ToDepartment : TextInputEditText? = null
    var tie_ToEmployee : TextInputEditText? = null

    var tie_StockMode : TextInputEditText? = null
    var tie_Product : TextInputEditText? = null
    var tie_Quantity : TextInputEditText? = null
    var tie_StandByQuantity : TextInputEditText? = null

    var img_EmpAdd : ImageView? = null
    var img_EmpClear : ImageView? = null
    
    var btnReset : Button? = null
    var btnSubmit : Button? = null

//    var tie_ToBranch : TextInputLayout? = null

    var tv_FromClick : TextView? = null
    var tv_ToClick : TextView? = null

    var ll_from : LinearLayout? = null
    var ll_to : LinearLayout? = null
    var ll_stocklist : LinearLayout? = null

    var recyStockDetails : FullLenghRecyclertview? = null
    var stockAdapter : StockDetailAdapter? = null

    var showFrom = 1
    var showTo = 0
    var depModeFromTo = 0 // 0 = From , 1 = To
    var empModeFromTo = 0 // 0 = From , 1 = To
    var modAddorEdit = 0  // 0 = Add , 1 = Edit
    var modEditPosition = 0  // 0 = Add , 1 = Edit

    var   strDate = ""
    var FK_BranchFrom: String? = ""
    var BranchNameFrom: String? = ""
    var FK_DepartmentFrom: String? = ""
    var DepartmentFrom: String? = ""

    var FK_BranchTo: String = ""
    var FK_BranchTemp: String? = ""
    var FK_DepartmentTo: String? = ""
    var FK_DepartmntTemp: String? = ""

    var FK_EmployeeFrom: String? = ""
    var FK_EmployeeTo: String? = ""

    var FK_StockMode: String? = ""
    var FK_Product: String? = ""

    var requestCount = 0
    var depCount = 0
    var branchCount = 0
    var employeeCount = 0
    var stockCount = 0
    var productCount = 0



    lateinit var branchInventoryViewModel: BranchInventoryViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null

    lateinit var stockRequestViewModel: StockRequestViewModel
    lateinit var stockRequestArrayList: JSONArray
    lateinit var stockRequestsort: JSONArray
    private var dialogStockRequest: Dialog? = null
    var recyStockRequest: RecyclerView? = null

    lateinit var departmentInvertoryViewModel: DepartmentInvertoryViewModel
    lateinit var departmentArrayListFrom : JSONArray
    lateinit var departmentSortFrom : JSONArray
    private var dialogDepartmentFrom : Dialog? = null
    var recyDeaprtmentFrom: RecyclerView? = null


    lateinit var departmentArrayListTo : JSONArray
    lateinit var departmentSortTo : JSONArray
    private var dialogDepartmentTo : Dialog? = null
    var recyDeaprtmentTo: RecyclerView? = null

    lateinit var employeeInventoryViewModel: EmployeeInventoryViewModel
    lateinit var employeeArrayListFrom : JSONArray
    lateinit var employeeSortFrom : JSONArray
    private var dialogEmployeeFrom : Dialog? = null
    var recyEmployeeFrom: RecyclerView? = null

    lateinit var employeeArrayListTo : JSONArray
    lateinit var employeeSortTo : JSONArray
    private var dialogEmployeeTo : Dialog? = null
    var recyEmployeeTo: RecyclerView? = null


    lateinit var stockModeViewModel: StockModeViewModel
    lateinit var stockModeArrayList: JSONArray
    lateinit var stockModesort: JSONArray
    private var dialogStockMode: Dialog? = null
    var recyStockMode: RecyclerView? = null

    lateinit var productStockViewModel: ProductStockViewModel
    lateinit var productArrayList: JSONArray
    lateinit var productsort: JSONArray
    private var dialogProduct: Dialog? = null
    var recyProduct: RecyclerView? = null

    var strQuantity: String? = ""
    var strStandQuantity: String? = ""
    val modelStockTransferDetails = ArrayList<ModelStockTransferDetails>()
    private var dialogConfirm : Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_stock_transfer)
        context = this@StockTransferActivity

        stockRequestViewModel = ViewModelProvider(this).get(StockRequestViewModel::class.java)
        departmentInvertoryViewModel = ViewModelProvider(this).get(DepartmentInvertoryViewModel::class.java)
        branchInventoryViewModel = ViewModelProvider(this).get(BranchInventoryViewModel::class.java)
        employeeInventoryViewModel = ViewModelProvider(this).get(EmployeeInventoryViewModel::class.java)
        stockModeViewModel = ViewModelProvider(this).get(StockModeViewModel::class.java)
        productStockViewModel = ViewModelProvider(this).get(ProductStockViewModel::class.java)

        setRegViews()

    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_Date = findViewById(R.id.tie_Date)
        tie_Request = findViewById(R.id.tie_Request)
        tie_FromBranch = findViewById(R.id.tie_FromBranch)
        tie_FromDepartment = findViewById(R.id.tie_FromDepartment)
        tie_FromEmployee = findViewById(R.id.tie_FromEmployee)

        tie_ToBranch = findViewById(R.id.tie_ToBranch)
        tie_ToDepartment = findViewById(R.id.tie_ToDepartment)
        tie_ToEmployee = findViewById(R.id.tie_ToEmployee)

        tie_StockMode = findViewById(R.id.tie_StockMode)
        tie_Product = findViewById(R.id.tie_Product)
        tie_Quantity = findViewById(R.id.tie_Quantity)
        tie_StandByQuantity = findViewById(R.id.tie_StandByQuantity)

        img_EmpAdd = findViewById(R.id.img_EmpAdd)
        img_EmpClear = findViewById(R.id.img_EmpClear)
        
        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)

        tv_FromClick = findViewById(R.id.tv_FromClick)
        tv_ToClick = findViewById(R.id.tv_ToClick)
        ll_from = findViewById(R.id.ll_from)
        ll_to = findViewById(R.id.ll_to)
        ll_stocklist = findViewById(R.id.ll_stocklist)

        recyStockDetails = findViewById(R.id.recyStockDetails)

        tie_Date!!.setOnClickListener(this)
        tie_Request!!.setOnClickListener(this)
        tv_FromClick!!.setOnClickListener(this)
        tv_ToClick!!.setOnClickListener(this)

        tie_FromDepartment!!.setOnClickListener(this)
        tie_FromEmployee!!.setOnClickListener(this)

        tie_ToBranch!!.setOnClickListener(this)
        tie_ToDepartment!!.setOnClickListener(this)
        tie_ToEmployee!!.setOnClickListener(this)
        tie_StockMode!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)

        img_EmpAdd!!.setOnClickListener(this)
        img_EmpClear!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        showFrom = 1

        hideShowFromTo()
        getCurrentDate()
        getSharedData()
        changeListner()


    }



    private fun getSharedData() {
        try {
            val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
            val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)

            val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
            val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)

            FK_BranchFrom = FK_BranchSP.getString("FK_Branch", "0")
            BranchNameFrom =  BranchNameSP.getString("BranchName", "0")
            tie_FromBranch!!.setText(BranchNameFrom)

            FK_DepartmentFrom = FK_DepartmentSP.getString("FK_Department","0")
            DepartmentFrom = DepartmentSP.getString("Department","0")
            tie_FromDepartment!!.setText(DepartmentFrom)

        }catch (e : Exception){

        }
    }

    private fun changeListner() {
        DecimelFormatters.setDecimelPlace(tie_Quantity!!)
        DecimelFormatters.setDecimelPlace(tie_StandByQuantity!!)

    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.imback->{
                finish()
            }


            R.id.tie_Date->{
                Config.disableClick(v)
                openBottomDate()
            }
            R.id.tie_Request->{
                Log.e(TAG,"tie_Request   2471   ")
                Config.disableClick(v)
                requestCount = 0
                getRequest()

            }



            R.id.tv_FromClick->{

                showFrom = 1
                showTo = 0
                hideShowFromTo()
            }

            R.id.tv_ToClick->{

                showFrom = 0
                showTo = 1
                hideShowFromTo()
            }

            R.id.tie_FromDepartment->{

                depModeFromTo = 0
                FK_BranchTemp = FK_BranchFrom
                depCount = 0
                Config.disableClick(v)
                getDepartmentInventory(FK_BranchTemp)
            }

            R.id.tie_FromEmployee->{

                if (FK_BranchFrom.equals("")){


                }
                else if (FK_DepartmentFrom.equals("")){

                }else{
                    empModeFromTo = 0
                    FK_BranchTemp = FK_BranchFrom
                    FK_DepartmntTemp = FK_DepartmentFrom
                    employeeCount = 0
                    Config.disableClick(v)
                    getEmployeeInventory(FK_BranchTemp!!,FK_DepartmntTemp!!)
                }

            }

            R.id.tie_ToBranch->{
                branchCount = 0
                Config.disableClick(v)
                getBranchInventory()
            }

            R.id.tie_ToDepartment->{

                depModeFromTo = 1
                FK_BranchTemp = FK_BranchTo
                depCount = 0
                Config.disableClick(v)
                getDepartmentInventory(FK_BranchTemp)

            }

            R.id.tie_ToEmployee->{

                if (FK_BranchTo.equals("")){


                }
                else if (FK_DepartmentTo.equals("")){

                }else{
                    empModeFromTo = 1
                    FK_BranchTemp = FK_BranchTo
                    FK_DepartmntTemp = FK_DepartmentTo
                    employeeCount = 0
                    Config.disableClick(v)
                    getEmployeeInventory(FK_BranchTemp!!,FK_DepartmntTemp!!)
                }

            }

            R.id.tie_StockMode->{

                stockCount = 0
                Config.disableClick(v)
                getStockMode()

            }
            R.id.tie_Product->{

                if (!FK_StockMode.equals("")){
                    productCount = 0
                    Config.disableClick(v)
                    getProduct(FK_StockMode!!)
                }else{

                }


            }

            R.id.img_EmpAdd->{

                Config.disableClick(v)
                validateStockDetails(v)

            }
            R.id.img_EmpClear->{
                clearDetails()
            }

            R.id.btnReset->{
              clearAllData(v)
            }

            R.id.btnSubmit->{
               saveValidation(v)
            }

        }

    }


    private fun saveValidation(v: View) {

        if (strDate.equals("")){

            Config.snackBarWarning(context,v,"Select Date")
        }
        else if (FK_BranchFrom.equals("")){
            Config.snackBarWarning(context,v,"Select Branch From")
        }
        else if (FK_DepartmentFrom.equals("")){
            Config.snackBarWarning(context,v,"Select Department From")
        }
        else if (FK_BranchTo.equals("")){
            Config.snackBarWarning(context,v,"Select Branch To")
        }
        else if (FK_DepartmentTo.equals("")){
            Config.snackBarWarning(context,v,"Select Department To")
        }
        else if (FK_BranchFrom.equals(FK_BranchTo) && FK_DepartmentFrom.equals(FK_DepartmentTo)){
            Config.snackBarWarning(context,v,"Please fill mandatory fields , From and To Detail should not be same")
        }
        else if (modelStockTransferDetails.size == 0){
            Config.snackBarWarning(context,v,"Please fill mandatory fields, Atleast one stock Transfer details")
        }
        else{

            Log.e(TAG,"4367567 hjvchkjvchkjvckj")
            confirmationScreen(v)
        }

    }

    private fun confirmationScreen(v : View) {
        try {


            dialogConfirm = Dialog(this)
            dialogConfirm!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirm!! .setContentView(R.layout.confirm_stock_transfer)
            dialogConfirm!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            var ll_femployee = dialogConfirm!! .findViewById(R.id.ll_femployee) as LinearLayout
            var ll_temployee = dialogConfirm!! .findViewById(R.id.ll_temployee) as LinearLayout

            var tvc_date = dialogConfirm!! .findViewById(R.id.tvc_date) as TextView
            var tvc_fbranch = dialogConfirm!! .findViewById(R.id.tvc_fbranch) as TextView
            var tvc_fdepartment = dialogConfirm!! .findViewById(R.id.tvc_fdepartment) as TextView
            var tvc_femployee = dialogConfirm!! .findViewById(R.id.tvc_femployee) as TextView
            var tvc_tbranch = dialogConfirm!! .findViewById(R.id.tvc_tbranch) as TextView
            var tvc_tdepartment = dialogConfirm!! .findViewById(R.id.tvc_tdepartment) as TextView
            var tvc_temployee = dialogConfirm!! .findViewById(R.id.tvc_temployee) as TextView

            var recyConfirmStock = dialogConfirm!! .findViewById(R.id.recyConfirmStock) as RecyclerView



            var btnCancel = dialogConfirm!! .findViewById(R.id.btnCancel) as Button
            var btnOk = dialogConfirm!! .findViewById(R.id.btnOk) as Button

            tvc_date!!.setText(tie_Date!!.text.toString())
            tvc_fbranch!!.setText(tie_FromBranch!!.text.toString())
            tvc_fdepartment!!.setText(tie_FromDepartment!!.text.toString())
            tvc_femployee!!.setText(tie_FromEmployee!!.text.toString())
            tvc_tbranch!!.setText(tie_ToBranch!!.text.toString())
            tvc_tdepartment!!.setText(tie_ToDepartment!!.text.toString())
            tvc_temployee!!.setText(tie_ToEmployee!!.text.toString())

            if (FK_EmployeeFrom.equals("")){
                ll_femployee!!.visibility  =View.GONE
            }
            if (FK_EmployeeTo.equals("")){
                ll_temployee!!.visibility  =View.GONE
            }

            if (modelStockTransferDetails.size>0){
              
                val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
                recyConfirmStock!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                var confirmAdapter = StockTransConfirmAdapter(this@StockTransferActivity,modelStockTransferDetails)
                recyConfirmStock!!.adapter = confirmAdapter

            }

            btnCancel!!.setOnClickListener {
                dialogConfirm!!.dismiss()
            }

            val window: Window? = dialogConfirm!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            dialogConfirm!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun hideShowFromTo() {

        ll_from!!.visibility = View.GONE
        ll_to!!.visibility = View.GONE

//        tv_FromClick!!.setBackgroundColor(resources.getColor(R.color.tab_inactive, null))
//        tv_ToClick!!.setBackgroundColor(resources.getColor(R.color.tab_inactive, null))
        tv_FromClick!!.setBackgroundResource(R.drawable.shape_gradient1)
        tv_ToClick!!.setBackgroundResource(R.drawable.shape_gradient1)

        if (showFrom == 1){
            ll_from!!.visibility = View.VISIBLE
//            tv_FromClick!!.setBackgroundColor(resources.getColor(R.color.tab_active, null))
            tv_FromClick!!.setBackgroundResource(R.drawable.shape_gradient2)


        }
        if (showTo == 1){
            ll_to!!.visibility = View.VISIBLE
//            tv_ToClick!!.setBackgroundColor(resources.getColor(R.color.tab_active, null))
            tv_ToClick!!.setBackgroundResource(R.drawable.shape_gradient2)
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

            tie_Date!!.setText(""+sdfDate1.format(newDate))
            strDate = sdfDate2.format(newDate)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

//        if (dateMode == 0){
//            date_Picker1.maxDate = System.currentTimeMillis()
//        }else if (dateMode == 1){
//
//            date_Picker1.minDate = System.currentTimeMillis()
//        }
//        else if (dateMode == 2){
//            date_Picker1.minDate = System.currentTimeMillis()
//        }

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

                tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                strDate = ""+strYear+"-"+strMonth+"-"+strDay

//                if (dateMode == 0){
//
//
//                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                    checkCurrDate(""+strDay+"-"+strMonth+"-"+strYear)
//                }else if (dateMode == 1){
//
//                    tie_FromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }
//                else if (dateMode == 2){
//                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getRequest() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                var ID_BranchType = "2"
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockRequestViewModel.getStockRequest(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (requestCount == 0){
                                    requestCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2471   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("StockRequestDetails")
                                        stockRequestArrayList = jobjt.getJSONArray("StockRequestList")
                                        if (stockRequestArrayList.length() > 0) {

                                            requestPopup(stockRequestArrayList)

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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

    private fun requestPopup(stockRequestArrayList: JSONArray) {
        try {

            dialogStockRequest = Dialog(this)
            dialogStockRequest!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogStockRequest!!.setContentView(R.layout.stock_request_popup)
            dialogStockRequest!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyStockRequest = dialogStockRequest!!.findViewById(R.id.recyStockRequest) as RecyclerView
            val etsearch = dialogStockRequest!!.findViewById(R.id.etsearch) as EditText

            stockRequestsort = JSONArray()
            for (k in 0 until stockRequestArrayList.length()) {
                val jsonObject = stockRequestArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                stockRequestsort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyStockRequest!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = BranchAdapter(this@StockTransferActivity, stockRequestArrayList)
            val adapter = StockRequestAdapter(this@StockTransferActivity, stockRequestsort)
            recyStockRequest!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stockRequestsort = JSONArray()

                    for (k in 0 until stockRequestArrayList.length()) {
                        val jsonObject = stockRequestArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Branch").length) {
                            if (jsonObject.getString("Branch")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                stockRequestsort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "branchSort               7103    " + stockRequestsort)
                    val adapter = StockRequestAdapter(this@StockTransferActivity, stockRequestsort)
                    recyStockRequest!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogStockRequest!!.show()
            dialogStockRequest!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getBranchInventory() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                var ID_BranchType = "2"
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchInventoryViewModel.getBranchInventory(this,ID_BranchType!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (branchCount == 0){
                                    branchCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2471   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BranchDetails")
                                        branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                        if (branchArrayList.length() > 0) {


                                            branchPopup(branchArrayList)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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
                // reportNamesort.put(k,jsonObject)
                branchsort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = BranchAdapter(this@StockTransferActivity, branchArrayList)
            val adapter = BranchAdapter(this@StockTransferActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

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

                    Log.e(TAG, "branchSort               7103    " + branchsort)
                    val adapter = BranchAdapter(this@StockTransferActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
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

    private fun getDepartmentInventory(FK_BranchTemp: String?) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                departmentInvertoryViewModel.getDepartmentInvetory(this,FK_BranchTemp!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (depCount == 0){
                                    depCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1142   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DepartmentDetails")

                                        if (depModeFromTo == 0){
                                            departmentArrayListFrom = jobjt.getJSONArray("DepartmentDetailsList")
                                            if (departmentArrayListFrom.length()>0){
                                                departmentPopupFrom(departmentArrayListFrom)
                                            }
                                        }

                                        if (depModeFromTo == 1){
                                            departmentArrayListTo = jobjt.getJSONArray("DepartmentDetailsList")
                                            if (departmentArrayListTo.length()>0){
                                                departmentPopupTo(departmentArrayListTo)
                                            }
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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

    private fun departmentPopupFrom(departmentArrayListFrom: JSONArray) {
        try {

            dialogDepartmentFrom = Dialog(this)
            dialogDepartmentFrom!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDepartmentFrom!! .setContentView(R.layout.department_popup)
            dialogDepartmentFrom!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDeaprtmentFrom = dialogDepartmentFrom!! .findViewById(R.id.recyDeaprtment) as RecyclerView
            val etsearch = dialogDepartmentFrom!! .findViewById(R.id.etsearch) as EditText

            departmentSortFrom = JSONArray()
            for (k in 0 until departmentArrayListFrom.length()) {
                val jsonObject = departmentArrayListFrom.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                departmentSortFrom.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyDeaprtmentFrom!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@StockTransferActivity, departmentSortFrom)
            recyDeaprtmentFrom!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    departmentSortFrom = JSONArray()

                    for (k in 0 until departmentArrayListFrom.length()) {
                        val jsonObject = departmentArrayListFrom.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                departmentSortFrom.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"departmentSort               7103    "+departmentSortFrom)
                    val adapter = DepartmentAdapter(this@StockTransferActivity, departmentSortFrom)
                    recyDeaprtmentFrom!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogDepartmentFrom!!.show()
            dialogDepartmentFrom!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun departmentPopupTo(departmentArrayListTo: JSONArray) {
        try {

            dialogDepartmentTo = Dialog(this)
            dialogDepartmentTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDepartmentTo!! .setContentView(R.layout.department_popup)
            dialogDepartmentTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDeaprtmentTo = dialogDepartmentTo!! .findViewById(R.id.recyDeaprtment) as RecyclerView
            val etsearch = dialogDepartmentTo!! .findViewById(R.id.etsearch) as EditText

            departmentSortTo = JSONArray()
            for (k in 0 until departmentArrayListTo.length()) {
                val jsonObject = departmentArrayListTo.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                departmentSortTo.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyDeaprtmentTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = DepartmentAdapter(this@FollowUpActivity, departmentArrayList)
            val adapter = DepartmentAdapter(this@StockTransferActivity, departmentSortTo)
            recyDeaprtmentTo!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    departmentSortTo = JSONArray()

                    for (k in 0 until departmentArrayListTo.length()) {
                        val jsonObject = departmentArrayListTo.getJSONObject(k)
                        if (textlength <= jsonObject.getString("DeptName").length) {
                            if (jsonObject.getString("DeptName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                departmentSortTo.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"departmentSort               7103    "+departmentSortTo)
                    val adapter = DepartmentAdapter(this@StockTransferActivity, departmentSortTo)
                    recyDeaprtmentTo!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogDepartmentTo!!.show()
            dialogDepartmentTo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmployeeInventory(FK_BranchTemp: String, FK_DepartmntTemp: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeInventoryViewModel.getEmployeeInventory(this, FK_BranchTemp!!,FK_DepartmntTemp!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employeeCount == 0){
                                    employeeCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        if (empModeFromTo == 0){
                                            employeeArrayListFrom = jobjt.getJSONArray("EmployeeDetailsList")
                                            if (employeeArrayListFrom.length()>0){

                                                employeePopupFrom(employeeArrayListFrom)

                                            }
                                        }

                                        if (empModeFromTo == 1){
                                            employeeArrayListTo = jobjt.getJSONArray("EmployeeDetailsList")
                                            if (employeeArrayListTo.length()>0){

                                                employeePopupTo(employeeArrayListTo)

                                            }
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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
                        }catch (e:Exception){
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

    private fun employeePopupFrom(employeeArrayListFrom: JSONArray) {
        try {

            dialogEmployeeFrom = Dialog(this)
            dialogEmployeeFrom!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeFrom!! .setContentView(R.layout.employee_popup)
            dialogEmployeeFrom!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeFrom = dialogEmployeeFrom!! .findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployeeFrom!! .findViewById(R.id.etsearch) as EditText

            employeeSortFrom = JSONArray()
            for (k in 0 until employeeArrayListFrom.length()) {
                val jsonObject = employeeArrayListFrom.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSortFrom.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyEmployeeFrom!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayListFrom)
            val adapter = EmployeeAdapter(this@StockTransferActivity, employeeSortFrom)
            recyEmployeeFrom!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSortFrom = JSONArray()

                    for (k in 0 until employeeArrayListFrom.length()) {
                        val jsonObject = employeeArrayListFrom.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeSortFrom.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+employeeSortFrom)
                    val adapter = EmployeeAdapter(this@StockTransferActivity, employeeSortFrom)
                    recyEmployeeFrom!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogEmployeeFrom!!.show()
            dialogEmployeeFrom!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun employeePopupTo(employeeArrayListTo: JSONArray) {
        try {

            dialogEmployeeTo = Dialog(this)
            dialogEmployeeTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeTo!! .setContentView(R.layout.employee_popup)
            dialogEmployeeTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeTo = dialogEmployeeTo!! .findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployeeTo!! .findViewById(R.id.etsearch) as EditText

            employeeSortTo = JSONArray()
            for (k in 0 until employeeArrayListTo.length()) {
                val jsonObject = employeeArrayListTo.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSortTo.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyEmployeeTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayListFrom)
            val adapter = EmployeeAdapter(this@StockTransferActivity, employeeSortTo)
            recyEmployeeTo!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSortTo = JSONArray()

                    for (k in 0 until employeeArrayListTo.length()) {
                        val jsonObject = employeeArrayListTo.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeSortTo.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+employeeSortTo)
                    val adapter = EmployeeAdapter(this@StockTransferActivity, employeeSortTo)
                    recyEmployeeTo!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogEmployeeTo!!.show()
            dialogEmployeeTo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStockMode() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockModeViewModel.getStockMode(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (stockCount == 0){
                                    stockCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   2471   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BranchDetails")
                                        stockModeArrayList = jobjt.getJSONArray("BranchDetailsList")
                                        if (stockModeArrayList.length() > 0) {
                                            stockModePopup(stockModeArrayList)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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

    private fun stockModePopup(stockModeArrayList: JSONArray) {
        try {

            dialogStockMode = Dialog(this)
            dialogStockMode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogStockMode!! .setContentView(R.layout.stock_mode_popup)
            dialogStockMode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyStockMode = dialogStockMode!! .findViewById(R.id.recyStockMode) as RecyclerView
            val etsearch = dialogStockMode!! .findViewById(R.id.etsearch) as EditText

            stockModesort = JSONArray()
            for (k in 0 until stockModeArrayList.length()) {
                val jsonObject = stockModeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                stockModesort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyStockMode!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayListFrom)
            val adapter = StockModeAdapter(this@StockTransferActivity, stockModesort)
            recyStockMode!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stockModesort = JSONArray()

                    for (k in 0 until stockModeArrayList.length()) {
                        val jsonObject = stockModeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                stockModesort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+stockModesort)
                    val adapter = StockModeAdapter(this@StockTransferActivity, stockModesort)
                    recyStockMode!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogStockMode!!.show()
            dialogStockMode!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProduct(FK_StockMode: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStockViewModel.getProductStock(this,FK_StockMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (productCount == 0){
                                    productCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1037   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProductDetailsList")

                                        productArrayList = jobjt.getJSONArray("ProductList")
                                        if (productArrayList.length()>0){
                                            productPopup(productArrayList)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockTransferActivity,
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

    private fun productPopup(productArrayList: JSONArray) {
        try {

            dialogProduct = Dialog(this)
            dialogProduct!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProduct!! .setContentView(R.layout.product_stock_popup)
            dialogProduct!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProduct = dialogProduct!! .findViewById(R.id.recyProduct) as RecyclerView
            val etsearch = dialogProduct!! .findViewById(R.id.etsearch) as EditText

            productsort = JSONArray()
            for (k in 0 until productArrayList.length()) {
                val jsonObject = productArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                productsort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
            recyProduct!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAdapter(this@FollowUpActivity, employeeArrayListFrom)
            val adapter = ProductStockAdapter(this@StockTransferActivity, productsort)
            recyProduct!!.adapter = adapter
            adapter.setClickListener(this@StockTransferActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    productsort = JSONArray()

                    for (k in 0 until productArrayList.length()) {
                        val jsonObject = productArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                productsort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeSort               7103    "+productsort)
                    val adapter = ProductStockAdapter(this@StockTransferActivity, productsort)
                    recyProduct!!.adapter = adapter
                    adapter.setClickListener(this@StockTransferActivity)
                }
            })

            dialogProduct!!.show()
            dialogProduct!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            Log.e(TAG,"1146   "+e.toString())
            e.printStackTrace()
        }
    }

    private fun validateStockDetails(v : View) {

        strQuantity = tie_Quantity!!.text.toString()
        strStandQuantity = tie_StandByQuantity!!.text.toString()

        if (strQuantity.equals("") || strQuantity.equals(".")){
            strQuantity = "0"
        }

        if (strStandQuantity.equals("") || strStandQuantity.equals(".")){
            strStandQuantity = "0"
        }

        if (FK_StockMode.equals("")){

        }
        else if (FK_Product.equals("")){

        }else if (strQuantity!!.toFloat() <= 0){

        }else{

            if (modAddorEdit == 0){
                var hasId =  hasStockOrProduct(modelStockTransferDetails!!,FK_StockMode!!,FK_Product!!)
                if (hasId){

                    modelStockTransferDetails.add(ModelStockTransferDetails(FK_StockMode!!,tie_StockMode!!.text.toString(),FK_Product!!,tie_Product!!.text.toString(),strQuantity!!,strStandQuantity!!))
                    if (modelStockTransferDetails.size>0){
                        ll_stocklist!!.visibility =View.VISIBLE
                        val lLayout = GridLayoutManager(this@StockTransferActivity, 1)
                        recyStockDetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                        stockAdapter = StockDetailAdapter(this@StockTransferActivity,modelStockTransferDetails)
                        recyStockDetails!!.adapter = stockAdapter
                        stockAdapter!!.setClickListener(this@StockTransferActivity)

                        // adapter1.setClickListener(this@StockTransferActivity)
                    }else{
                        ll_stocklist!!.visibility =View.GONE
                    }
                    clearDetails()
                }
                else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }
            }

            else{

                Log.e(TAG,"4122   modEditPosition   "+modEditPosition)
                var hasId =  hasAll(modelStockTransferDetails!!,FK_StockMode!!,FK_Product!!,modEditPosition!!)
                if (hasId){
                    var empModel = modelStockTransferDetails[modEditPosition]
                    empModel.FK_StockMode = FK_StockMode!!
                    empModel.StockMode = tie_StockMode!!.text.toString()
                    empModel.FK_Product = FK_Product!!
                    empModel.Product = tie_Product!!.text.toString()
                    empModel.Quantity = tie_Quantity!!.text.toString()
                    empModel.StatndByQuantity = tie_StandByQuantity!!.text.toString()
                    stockAdapter!!.notifyItemChanged(modEditPosition)

                    clearDetails()
                }
                else{
                    Config.snackBars(context,v,"Duplicate entry found")
                }

            }




        }

    }

    private fun hasStockOrProduct(modelStockTransferDetails: ArrayList<ModelStockTransferDetails>, FK_StockMode: String, FK_Product: String): Boolean {
        for (i in 0 until modelStockTransferDetails.size) {  // iterate through the JsonArray
            if (modelStockTransferDetails.get(i).FK_StockMode == FK_StockMode && modelStockTransferDetails.get(i).FK_Product == FK_Product) return false
        }
        return true
    }

    private fun hasAll(modelStockTransferDetails: ArrayList<ModelStockTransferDetails>, FK_StockMode: String, FK_Product: String,modEditPosition : Int): Boolean {
        for (i in 0 until modelStockTransferDetails.size) {  // iterate through the JsonArray
            if (modEditPosition != i){
                if (modelStockTransferDetails.get(i).FK_StockMode == FK_StockMode && modelStockTransferDetails.get(i).FK_Product == FK_Product) return false
            }
        }
        return true
    }

    private fun clearDetails() {
        FK_StockMode = ""
        FK_Product = ""
        tie_StockMode!!.setText("")
        tie_Product!!.setText("")
        tie_Quantity!!.setText("")
        tie_StandByQuantity!!.setText("")

        modAddorEdit = 0


    }

    private fun clearAllData(v: View) {

        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.delete_confirmation, null)

            val btnNo = view.findViewById<Button>(R.id.btnNo)
            val btnYes = view.findViewById<Button>(R.id.btnYes)
            val tv_delete_msg = view.findViewById<TextView>(R.id.tv_delete_msg)

            tv_delete_msg!!.setText("Do you want to delete transfer details?")

            btnNo.setOnClickListener {
                dialog .dismiss()

            }
            btnYes.setOnClickListener {
                dialog.dismiss()
                deleteAll(v)

            }
            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()
        }catch (e: Exception){

        }

    }

    private fun deleteAll(v: View) {

        getCurrentDate()
        getSharedData()

        FK_EmployeeFrom = ""
        tie_FromEmployee!!.setText("")
        FK_BranchTo = ""
        tie_ToBranch!!.setText("")
        FK_DepartmentTo = ""
        tie_ToDepartment!!.setText("")
        FK_EmployeeTo = ""
        tie_ToEmployee!!.setText("")


        FK_StockMode = ""
        FK_Product = ""
        tie_StockMode!!.setText("")
        tie_Product!!.setText("")
        tie_Quantity!!.setText("")
        tie_StandByQuantity!!.setText("")

        modAddorEdit = 0

        modelStockTransferDetails.clear()
        recyProduct!!.adapter = null
        ll_stocklist!!.visibility = View.GONE


    }


    override fun onClick(position: Int, data: String) {

        if (data.equals("stockrequest")) {
            dialogStockRequest!!.dismiss()

            val jsonObject = stockRequestsort.getJSONObject(position)


            FK_BranchFrom = "1"
            BranchNameFrom =  jsonObject.getString("Branch")
            tie_FromBranch!!.setText(BranchNameFrom)

            FK_BranchTo = "1"
            tie_ToBranch!!.setText(jsonObject.getString("Branch"))

            FK_DepartmentFrom = "2"
            DepartmentFrom = jsonObject.getString("Department")
            tie_FromDepartment!!.setText(DepartmentFrom)

            FK_DepartmentTo = "3"
            tie_ToDepartment!!.setText(jsonObject.getString("Department"))

            FK_EmployeeFrom = "2"
            tie_FromEmployee!!.setText(jsonObject.getString("Employees"))

            FK_EmployeeTo = "3"
            tie_ToEmployee!!.setText(jsonObject.getString("EmployeeTo"))

        }

        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
//             val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchsort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            FK_BranchTo = jsonObject.getString("ID_Branch")
            tie_ToBranch!!.setText(jsonObject.getString("BranchName"))

            FK_DepartmentTo = ""
            tie_ToDepartment!!.setText("")

            //   ID_Employee = ""
            tie_ToEmployee!!.setText("")


        }

        if (data.equals("department")){

            if (depModeFromTo == 0){

                dialogDepartmentFrom!!.dismiss()
                val jsonObject = departmentSortFrom.getJSONObject(position)
                Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
                FK_DepartmentFrom = jsonObject.getString("ID_Department")
                tie_FromDepartment!!.setText(jsonObject.getString("DeptName"))

               // ID_Employee = ""
                tie_FromEmployee!!.setText("")

            }

            if (depModeFromTo == 1){

                dialogDepartmentTo!!.dismiss()
                val jsonObject = departmentSortTo.getJSONObject(position)
                Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
                FK_DepartmentTo = jsonObject.getString("ID_Department")
                tie_ToDepartment!!.setText(jsonObject.getString("DeptName"))

             //   ID_Employee = ""
                tie_ToEmployee!!.setText("")

            }

        }

        if (data.equals("employee")){


            if (empModeFromTo == 0){

                dialogEmployeeFrom!!.dismiss()
                val jsonObject = employeeSortFrom.getJSONObject(position)
                Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
                FK_EmployeeFrom = jsonObject.getString("ID_Employee")
                tie_FromEmployee!!.setText(jsonObject.getString("EmpName"))
            }

            if (empModeFromTo == 1){
                dialogEmployeeTo!!.dismiss()
                val jsonObject = employeeSortTo.getJSONObject(position)
                Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
                FK_EmployeeTo = jsonObject.getString("ID_Employee")
                tie_ToEmployee!!.setText(jsonObject.getString("EmpName"))
            }

        }

        if (data.equals("stockmode")){

            dialogStockMode!!.dismiss()
            val jsonObject = stockModesort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            FK_StockMode = jsonObject.getString("ID_Branch")
            tie_StockMode!!.setText(jsonObject.getString("BranchName"))

        }

        if (data.equals("productstock")){

            dialogProduct!!.dismiss()
            val jsonObject = productsort.getJSONObject(position)
            FK_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))

        }

        if (data.equals("deleteStocks")){

            Log.e(TAG,"14644  "+position)
            modelStockTransferDetails!!.removeAt(position)
            stockAdapter!!.notifyItemRemoved(position)
            if (modelStockTransferDetails.size == 0){
                ll_stocklist!!.visibility =View.GONE
            }

        }

        if (data.equals("editStocks")){

            Log.e(TAG,"1476  "+position)
            modAddorEdit = 1
            modEditPosition = position
            FK_StockMode = modelStockTransferDetails[position].FK_StockMode
            tie_StockMode!!.setText(modelStockTransferDetails[position].StockMode)
            FK_Product = modelStockTransferDetails[position].FK_Product
            tie_Product!!.setText(modelStockTransferDetails[position].Product)
            tie_Quantity!!.setText(modelStockTransferDetails[position].Quantity)
            tie_StandByQuantity!!.setText(modelStockTransferDetails[position].StatndByQuantity)


        }

    }


}