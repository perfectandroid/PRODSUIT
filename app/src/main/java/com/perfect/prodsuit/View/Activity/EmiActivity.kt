package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EmiActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG: String = "EmiActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    internal var ll_todo: LinearLayout? = null
    internal var ll_overdue: LinearLayout? = null
    internal var ll_upcoming: LinearLayout? = null

    private var imgv_filterManage: ImageView? = null
    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null

    private var tie_Customer: TextInputEditText? = null
    private var tie_Product: TextInputEditText? = null
    private var tie_EmiType: TextInputEditText? = null
    private var tie_CollectedBy: TextInputEditText? = null

    private var tie_Finance_PlanType: TextInputEditText? = null
    private var tie_As_On_Date: TextInputEditText? = null
    private var tie_Category: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_Demand: TextInputEditText? = null

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null
    var empMediaDet = 0

    var emiTypeCount = 0
    lateinit var emiTypeViewModel: EmiTypeViewModel
    lateinit var emiTypeArrayList: JSONArray
    private var dialogEmiType: Dialog? = null
    var recyEmiType: RecyclerView? = null

    private var ID_FinancePlanType: String = ""
    private var AsOnDate: String = ""
    private var ID_Category: String = ""
    private var ID_Area: String = ""
    private var Demand: String = "30"


    private var temp_Customer: String = ""
    private var temp_Product: String = ""
    private var temp_EmiType: String = ""
    private var temp_ID_EmiType: String = ""
    private var temp_ID_CollectedBy: String = ""
    private var temp_CollectedBy: String = ""

    private var temp_ID_Finance_PlanType: String = ""
    private var temp_Finance_PlanType: String = ""
    private var temp_As_On_Date: String = ""
    private var temp_ID_Category: String = ""
    private var temp_Category: String = ""
    private var temp_ID_Area: String = ""
    private var temp_Area: String = ""
    private var temp_Demand: String = "30"



    var ID_CollectedBy: String? = ""
    var ID_EmiType: String? = ""

    lateinit var emiCountViewModel: EmiCountViewModel
    lateinit var emiCountArrayList: JSONArray
    var recyEmiCount: RecyclerView? = null
    var emiCount = 0

    lateinit var financePlanTypeViewModel: FinancePlanTypeViewModel
    lateinit var financePlanTypeArrayList: JSONArray
    lateinit var financePlanTypeSort: JSONArray
    private var dialogFinancePlanType: Dialog? = null
    var recyFinancePlanType: RecyclerView? = null
    var financePlanTypeCount = 0

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategoryArrayList: JSONArray
    lateinit var prodCategorySort: JSONArray
    private var dialogProdCat: Dialog? = null
    var recyProdCategory: RecyclerView? = null
    var prodcategory = 0

    lateinit var areaViewModel: AreaViewModel
    lateinit var areaArrayList: JSONArray
    lateinit var areaSort: JSONArray
    private var dialogArea: Dialog? = null
    var areaDet = 0

    private var tv_ToDo_Count: TextView? = null
    private var tv_OverDue_Count: TextView? = null
    private var tv_Demand_Count: TextView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi)
        context = this@EmiActivity

        emiTypeViewModel = ViewModelProvider(this).get(EmiTypeViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        emiCountViewModel = ViewModelProvider(this).get(EmiCountViewModel::class.java)
        financePlanTypeViewModel = ViewModelProvider(this).get(FinancePlanTypeViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        areaViewModel = ViewModelProvider(this).get(AreaViewModel::class.java)

        setRegViews()
        emiCount = 0
        getEmiCounts()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filterManage = findViewById<ImageView>(R.id.imgv_filterManage)

        ll_todo = findViewById<LinearLayout>(R.id.ll_todo)
        ll_overdue = findViewById<LinearLayout>(R.id.ll_overdue)
        ll_upcoming = findViewById<LinearLayout>(R.id.ll_upcoming)

        tv_ToDo_Count = findViewById<TextView>(R.id.tv_ToDo_Count)
        tv_OverDue_Count = findViewById<TextView>(R.id.tv_OverDue_Count)
        tv_Demand_Count = findViewById<TextView>(R.id.tv_Demand_Count)

        imgv_filterManage!!.setOnClickListener(this)
        ll_todo!!.setOnClickListener(this)
        ll_overdue!!.setOnClickListener(this)
        ll_upcoming!!.setOnClickListener(this)

        if (AsOnDate.equals("")){
            getCurrentDate()
        }

    }

    private fun getCurrentDate() {

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            val newDate: Date = sdf.parse(currentDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            AsOnDate = sdfDate2.format(newDate)

            Log.e(TAG,"AsOnDate 196  "+AsOnDate)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgv_filterManage->{
                filterBottomFilter()
            }
            R.id.ll_todo->{
                Log.i("response999", "ll_todo clicked==")
               // Toast.makeText(this, "ll_todo", Toast.LENGTH_SHORT).show()
                val i = Intent(this@EmiActivity, EmiToDoListActivity::class.java)
                i.putExtra("SubMode","1")
                i.putExtra("ID_FinancePlanType",ID_FinancePlanType)
                i.putExtra("AsOnDate",AsOnDate)
                i.putExtra("ID_Category",ID_Category)
                i.putExtra("ID_Area",ID_Area)
                i.putExtra("Demand",Demand)
                startActivity(i)
            }
            R.id.ll_overdue->{
                Log.i("response999", "ll_overdue clicked==")
                val i = Intent(this@EmiActivity, EmiToDoListActivity::class.java)
                i.putExtra("SubMode","2")
                i.putExtra("ID_FinancePlanType",ID_FinancePlanType)
                i.putExtra("AsOnDate",AsOnDate)
                i.putExtra("ID_Category",ID_Category)
                i.putExtra("ID_Area",ID_Area)
                i.putExtra("Demand",Demand)
                startActivity(i)
            }
            R.id.ll_upcoming->{
                val i = Intent(this@EmiActivity, EmiToDoListActivity::class.java)
                i.putExtra("SubMode","3")
                i.putExtra("ID_FinancePlanType",ID_FinancePlanType)
                i.putExtra("AsOnDate",AsOnDate)
                i.putExtra("ID_Category",ID_Category)
                i.putExtra("ID_Area",ID_Area)
                i.putExtra("Demand",Demand)
                startActivity(i)
            }
            R.id.tie_EmiType->{

                Log.e(TAG,"tie_EmiType")
//                Config.disableClick(v)
//                emiTypeCount = 0
//                getEmiType()
            }
            R.id.tie_CollectedBy->{
                Log.e(TAG,"tie_CollectedBy")
                Config.disableClick(v)
                empMediaDet = 0
                getChannelEmp()
            }

            R.id.tie_Finance_PlanType->{
                Log.e(TAG,"tie_Finance_PlanType")
                Config.disableClick(v)
                financePlanTypeCount = 0
                getfinancePlanType()
            }

            R.id.tie_Category->{
                Log.e(TAG,"tie_Category")
                Config.disableClick(v)
                prodcategory = 0
                getCategory()
            }

            R.id.tie_Area->{
                Log.e(TAG,"tie_Area")
                Config.disableClick(v)
                areaDet = 0
                getArea()
            }

            R.id.tie_As_On_Date->{
                Log.e(TAG,"tie_As_On_Date")
                Config.disableClick(v)
                openBottomDate()
            }

        }
    }

    private fun getfinancePlanType() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                financePlanTypeViewModel.getFinancePlanType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (financePlanTypeCount == 0) {
                                    financePlanTypeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FinancePlanTypeDetails")
                                        financePlanTypeArrayList = jobjt.getJSONArray("FinancePlanTypeDetailsList")
                                        if (financePlanTypeArrayList.length() > 0) {

                                            Log.e(TAG,"financePlanTypeArrayList  2221  "+financePlanTypeArrayList)
                                            financePlanTypePopup(financePlanTypeArrayList)


                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiActivity,
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

    private fun financePlanTypePopup(financePlanTypeArrayList: JSONArray) {
        try {

            dialogFinancePlanType = Dialog(this)
            dialogFinancePlanType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFinancePlanType!!.setContentView(R.layout.finance_plan_type_popup)
            dialogFinancePlanType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFinancePlanType = dialogFinancePlanType!!.findViewById(R.id.recyFinancePlanType) as RecyclerView
            val etsearch = dialogFinancePlanType!!.findViewById(R.id.etsearch) as EditText

            financePlanTypeSort = JSONArray()
            for (k in 0 until financePlanTypeArrayList.length()) {
                val jsonObject = financePlanTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                financePlanTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recyFinancePlanType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FinancePlanTypeAdapter(this@EmiActivity, financePlanTypeSort)
            recyFinancePlanType!!.adapter = adapter
            adapter.setClickListener(this@EmiActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    financePlanTypeSort = JSONArray()

                    for (k in 0 until financePlanTypeArrayList.length()) {
                        val jsonObject = financePlanTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("FinanceName").length) {
                            if (jsonObject.getString("FinanceName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                financePlanTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeSort               7103    " + financePlanTypeSort)
                    val adapter = FinancePlanTypeAdapter(this@EmiActivity, financePlanTypeSort)
                    recyFinancePlanType!!.adapter = adapter
                    adapter.setClickListener(this@EmiActivity)
                }
            })

            dialogFinancePlanType!!.show()
            dialogFinancePlanType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCategory() {
//         var prodcategory = 0
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
                productCategoryViewModel.getProductCategory(this,ReqMode!!,SubMode!!)!!.observe(
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
                                            this@EmiActivity,
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

            prodCategorySort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCategorySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductCategoryAdapter(this@EmiActivity, prodCategoryArrayList)
            val adapter = ProductCategoryAdapter(this@EmiActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@EmiActivity)

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
                        ProductCategoryAdapter(this@EmiActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@EmiActivity)
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

    private fun getArea() {
//        var areaDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                areaViewModel.getArea(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (areaDet == 0) {
                                    areaDet++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AreaDetails")
                                        areaArrayList = jobjt.getJSONArray("AreaDetailsList")

                                        if (areaArrayList.length() > 0) {

                                            areaDetailPopup(areaArrayList)

                                        }


                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiActivity,
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
                        } catch (e: Exception) {

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

    private fun areaDetailPopup(areaArrayList: JSONArray) {

        try {

            dialogArea = Dialog(this)
            dialogArea!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogArea!!.setContentView(R.layout.area_list_popup)
            dialogArea!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycArea = dialogArea!!.findViewById(R.id.recycArea) as RecyclerView
            val etsearch = dialogArea!!.findViewById(R.id.etsearch) as EditText

            areaSort = JSONArray()
            for (k in 0 until areaArrayList.length()) {
                val jsonObject = areaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                areaSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recycArea!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@EmiActivity, areaArrayList)
            val adapter = AreaDetailAdapter(this@EmiActivity, areaSort)
            recycArea!!.adapter = adapter
            adapter.setClickListener(this@EmiActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    areaSort = JSONArray()

                    for (k in 0 until areaArrayList.length()) {
                        val jsonObject = areaArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Area").length) {
                            if (jsonObject.getString("Area")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                areaSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "areaSort               7103    " + areaSort)
                    val adapter = AreaDetailAdapter(this@EmiActivity, areaSort)
                    recycArea!!.adapter = adapter
                    adapter.setClickListener(this@EmiActivity)
                }
            })

            dialogArea!!.show()
            dialogArea!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogArea!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

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
                tie_As_On_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                AsOnDate = ""+strYear+"-"+strMonth+"-"+strDay

            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    private fun filterBottomFilter() {
        try {
            val dialog = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.emi_count_filter_sheet, null)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog!!.setCanceledOnTouchOutside(true)


            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            tie_Customer= view.findViewById(R.id.tie_Customer) as TextInputEditText
            tie_Product= view.findViewById(R.id.tie_Product) as TextInputEditText
            tie_EmiType= view.findViewById(R.id.tie_EmiType) as TextInputEditText
            tie_CollectedBy= view.findViewById(R.id.tie_CollectedBy) as TextInputEditText

            tie_Finance_PlanType= view.findViewById(R.id.tie_Finance_PlanType) as TextInputEditText
            tie_As_On_Date= view.findViewById(R.id.tie_As_On_Date) as TextInputEditText
            tie_Category= view.findViewById(R.id.tie_Category) as TextInputEditText
            tie_Area= view.findViewById(R.id.tie_Area) as TextInputEditText
            tie_Demand= view.findViewById(R.id.tie_Demand) as TextInputEditText

            tie_EmiType!!.setOnClickListener(this)
            tie_CollectedBy!!.setOnClickListener(this)

            tie_Finance_PlanType!!.setOnClickListener(this)
            tie_As_On_Date!!.setOnClickListener(this)
            tie_Category!!.setOnClickListener(this)
            tie_Area!!.setOnClickListener(this)


            tie_Customer!!.setText(temp_Customer)
            tie_Product!!.setText(temp_Product)
            ID_EmiType  = temp_ID_EmiType
            tie_EmiType!!.setText(temp_EmiType)
            ID_CollectedBy  = temp_ID_CollectedBy
            tie_CollectedBy!!.setText(temp_CollectedBy)

            tie_Finance_PlanType!!.setText(temp_Finance_PlanType)
            tie_As_On_Date!!.setText(temp_As_On_Date)
            tie_Category!!.setText(temp_Category)
            tie_Area!!.setText(temp_Area)
            tie_Demand!!.setText(temp_Demand)

            ID_FinancePlanType = temp_ID_Finance_PlanType
            ID_Category = temp_ID_Category
            ID_Area = temp_ID_Area




            if (tie_As_On_Date!!.text.toString().equals("")){
                try {
                    val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                    val currentDate = sdf.format(Date())
                    Log.e(TAG,"DATE TIME  196  "+currentDate)
                    val newDate: Date = sdf.parse(currentDate)
                    Log.e(TAG,"newDate  196  "+newDate)
                    val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")

                    tie_As_On_Date!!.setText(""+sdfDate1.format(newDate))


                }catch (e: Exception){

                    Log.e(TAG,"Exception 196  "+e.toString())
                }
            }




            txtSearch!!.setOnClickListener {

                temp_Customer = tie_Customer!!.text.toString()
                temp_Product = tie_Product!!.text.toString()
                temp_ID_EmiType  = ID_EmiType!!
                temp_EmiType  = tie_EmiType!!.text.toString()
                temp_ID_CollectedBy  = ID_CollectedBy!!
                temp_CollectedBy  = tie_CollectedBy!!.text.toString()

                temp_Finance_PlanType = tie_Finance_PlanType!!.text.toString()
                temp_As_On_Date = tie_As_On_Date!!.text.toString()
                temp_Category = tie_Category!!.text.toString()
                temp_Area = tie_Area!!.text.toString()
                temp_Demand = tie_Demand!!.text.toString()

                Demand = tie_Demand!!.text.toString()


                temp_ID_Finance_PlanType = ID_FinancePlanType
                temp_ID_Category = ID_Category
                temp_ID_Area  =ID_Area

          //      ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand

                dialog.dismiss()
                emiCount = 0
                getEmiCounts()
            }

            txtReset!!.setOnClickListener {

                tie_Customer!!.setText("")
                tie_Product!!.setText("")
                ID_EmiType = ""
                tie_EmiType!!.setText("")
                ID_CollectedBy = ""
                tie_CollectedBy!!.setText("")

                tie_Finance_PlanType!!.setText("")
                tie_As_On_Date!!.setText("")
                tie_Category!!.setText("")
                tie_Area!!.setText("")
                tie_Demand!!.setText("30")

                ID_FinancePlanType =""
                ID_Category =""
                ID_Area  = ""
              //  dialog.dismiss()

                if (tie_As_On_Date!!.text.toString().equals("")){
                    try {
                        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                        val currentDate = sdf.format(Date())
                        Log.e(TAG,"DATE TIME  196  "+currentDate)
                        val newDate: Date = sdf.parse(currentDate)
                        Log.e(TAG,"newDate  196  "+newDate)
                        val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")

                        tie_As_On_Date!!.setText(""+sdfDate1.format(newDate))
                        temp_As_On_Date = sdfDate1.format(newDate);
                        val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
                        AsOnDate = sdfDate2.format(newDate)

                    }catch (e: Exception){

                        Log.e(TAG,"Exception 196  "+e.toString())
                    }
                }
            }



            dialog.setCancelable(true)
            dialog!!.setContentView(view)
            dialog.show()

        }catch (e: Exception){
            Log.e(TAG,"171  Exception   "+e.toString())
        }
    }

    private fun getChannelEmp() {
        var ID_Department = "0"
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
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (empMediaDet == 0) {
                                    empMediaDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiActivity,
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

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!!.setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!!.findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!!.findViewById(R.id.etsearch) as EditText

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@EmiActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@EmiActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@EmiActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSort = JSONArray()

                    for (k in 0 until employeeArrayList.length()) {
                        val jsonObject = employeeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeSort               7103    " + employeeSort)
                    val adapter = EmployeeAdapter(this@EmiActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@EmiActivity)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmiType() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiTypeViewModel.getEmiType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (emiTypeCount == 0) {
                                    emiTypeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        emiTypeArrayList = jobjt.getJSONArray("CategoryList")
                                        if (emiTypeArrayList.length() > 0) {
                                            emiTypePop(emiTypeArrayList)

                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiActivity,
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

    private fun emiTypePop(emiTypeArrayList: JSONArray) {
        try {

            dialogEmiType = Dialog(this)
            dialogEmiType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmiType!!.setContentView(R.layout.emi_type_popup)
            dialogEmiType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmiType = dialogEmiType!!.findViewById(R.id.recyEmiType) as RecyclerView

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recyEmiType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@EmiActivity, employeeArrayList)
            val adapter = EmiTypeAdapter(this@EmiActivity, emiTypeArrayList)
            recyEmiType!!.adapter = adapter
            adapter.setClickListener(this@EmiActivity)


            dialogEmiType!!.show()
            dialogEmiType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmiCounts() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiCountViewModel.getEmiCount(this,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (emiCount == 0) {
                                    emiCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EMICollectionReportCount")
                                        tv_ToDo_Count!!.setText(jobjt.getString("ToDoList"))
                                        tv_OverDue_Count!!.setText(jobjt.getString("OverDue"))
                                        tv_Demand_Count!!.setText(jobjt.getString("Upcoming"))
//                                        val jobjt = jObject.getJSONObject("EMICollectionReportCount")
//                                        emiCountArrayList = jobjt.getJSONArray("EMICollectionReportCountList")
//                                        if (emiCountArrayList.length() > 0) {
//
//                                            tv_ToDo_Count!!.setText(""+emiCountArrayList.getJSONObject(0).getString("Count"))
//                                            tv_OverDue_Count!!.setText(""+emiCountArrayList.getJSONObject(1).getString("Count"))
//                                            tv_Demand_Count!!.setText(""+emiCountArrayList.getJSONObject(2).getString("Count"))
////                                            Log.e(TAG,"EMI COUNT  464  "+emiCountArrayList)
//
//                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        tv_ToDo_Count!!.setText("0")
                                        tv_OverDue_Count!!.setText("0")
                                        tv_Demand_Count!!.setText("0")
//                                        val builder = AlertDialog.Builder(
//                                            this@EmiActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_CollectedBy = jsonObject.getString("ID_Employee")
            tie_CollectedBy!!.setText(jsonObject.getString("EmpName"))

        }

        if (data.equals("emiType")){
            dialogEmiType!!.dismiss()
            val jsonObject = emiTypeArrayList.getJSONObject(position)
            ID_EmiType= jsonObject.getString("ID_Category")
            tie_EmiType!!.setText(jsonObject.getString("CategoryName"))
        }

        if (data.equals("FinancePlanClick")){
            dialogFinancePlanType!!.dismiss()
            val jsonObject = financePlanTypeSort.getJSONObject(position)
            ID_FinancePlanType= jsonObject.getString("ID_FinancePlanType")
            tie_Finance_PlanType!!.setText(jsonObject.getString("FinanceName"))
        }

        if (data.equals("prodcategory")) {
            dialogProdCat!!.dismiss()
            val jsonObject = prodCategorySort.getJSONObject(position)
            Log.e(TAG, "ID_Category   " + jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))

        }

        if (data.equals("areadetail")) {
            dialogArea!!.dismiss()
            val jsonObject = areaSort.getJSONObject(position)
            Log.e(TAG, "jsonObject  5465    " + jsonObject)


            ID_Area = jsonObject.getString("FK_Area")
            tie_Area!!.setText(jsonObject.getString("Area"))
        }



    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}