package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity.Companion.checkProject
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TicketReportActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG: String = "TicketReportActivity"
    private var progressDialog: ProgressDialog? = null
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null
    internal var yr: Int = 0
    internal var month: Int = 0
    internal var day: Int = 0
    internal var hr: Int = 0
    internal var min: Int = 0
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var chipNavigationBar: ChipNavigationBar? = null
    var date_Picker: DatePicker? = null
    var txtok: TextView? = null
    var date_Picker1: DatePicker? = null
    var txtok1: TextView? = null
    var txtfromDate: TextView? = null
    var txttoDate: TextView? = null
    var ll_Fromdate: LinearLayout? = null
    var llfromdate: LinearLayout? = null
    var ll_Todate: LinearLayout? = null
    var lltodate: LinearLayout? = null
    var imclose: ImageView? = null
    var im_close: ImageView? = null
    lateinit var context: Context

    var tie_ReportName: TextInputEditText? = null
    var tie_Category: TextInputEditText? = null
    var tie_Branch: TextInputEditText? = null
    var tie_FromDate: TextInputEditText? = null
    var tie_ToDate: TextInputEditText? = null
    var tie_EmployeeName: TextInputEditText? = null
    var tie_CollectedBy: TextInputEditText? = null
    var tie_AssignedTo: TextInputEditText? = null
    var tie_Product: TextInputEditText? = null
    var tie_FollowUpAction: TextInputEditText? = null
    var tie_FollowUpType: TextInputEditText? = null
    var tie_Priority: TextInputEditText? = null
    var tie_Status: TextInputEditText? = null
    var tie_Grouping: TextInputEditText? = null

    var til_FollowUpAction: TextInputLayout? = null
    var til_FollowUpType: TextInputLayout? = null
    var til_Status: TextInputLayout? = null
    var til_Priority: TextInputLayout? = null
    var til_CollectedBy: TextInputLayout? = null
    var til_Group: TextInputLayout? = null
    var til_AssignedTo: TextInputLayout? = null
    var til_EmployeeName: TextInputLayout? = null
    var til_Category: TextInputLayout? = null

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

    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    private var dialogProdDet: Dialog? = null
    var recyProdDetail: RecyclerView? = null

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList: JSONArray
    lateinit var followUpActionSort: JSONArray
    private var dialogFollowupAction: Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList: JSONArray
    lateinit var followUpTypeSort: JSONArray
    private var dialogFollowupType: Dialog? = null
    var recyFollowupType: RecyclerView? = null

    var empUseBranch = 0
    var empUseMode = "0"
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList: JSONArray
    lateinit var employeeAllSort: JSONArray
    private var dialogEmployeeAll: Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    lateinit var leadByViewModel: LeadByViewModel
    lateinit var leadByArrayList: JSONArray
    var dialogLeadBy: Dialog? = null
    var recyLeadby: RecyclerView? = null
    lateinit var leadBySort: JSONArray

    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var prodPriorityArrayList: JSONArray
    lateinit var prodPrioritySort: JSONArray
    private var dialogProdPriority: Dialog? = null
    var recyProdPriority: RecyclerView? = null

    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var prodStatusArrayList: JSONArray
    lateinit var prodStatusSort: JSONArray
    private var dialogProdStatus: Dialog? = null
    var recyProdStatus: RecyclerView? = null

    lateinit var groupingViewModel: GroupingViewModel
    lateinit var groupingArrayList: JSONArray
    lateinit var groupingSort: JSONArray
    private var dialogGrouping: Dialog? = null
    var recyGrouping: RecyclerView? = null


    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategoryArrayList: JSONArray
    private var dialogProdCat: Dialog? = null
    var recyProdCategory: RecyclerView? = null
    lateinit var prodCategorySort: JSONArray
    var ID_Category: String? = ""

    private var fromToDate: Int = 0
    private var ReportMode: String = ""
    private var ID_Branch: String = ""
    private var ID_Product: String = ""
    private var ID_NextAction: String = ""
    private var ID_ActionType: String = ""
    private var ID_Priority: String = ""
    private var ID_CollectedBy: String = ""
    private var ID_Employee: String = ""
    private var ID_AssignedEmployee: String = ""
    private var ID_Status: String = ""
    private var GroupId: String = ""
    var prodcategory = 0

    var countLeadBy = 0

    var btnSubmit: Button? = null
    var btnReset: Button? = null

    var FromDate: String = ""
    var ToDate: String = ""
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_ticketreport)

        context = this@TicketReportActivity
        reportNameViewModel = ViewModelProvider(this).get(ReportNameViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        leadByViewModel = ViewModelProvider(this).get(LeadByViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)
        groupingViewModel = ViewModelProvider(this).get(GroupingViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)

        setRegViews()
        bottombarnav()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        txttoDate!!.text = currentDate
        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)

        Log.e(TAG, "  1741   " + currentDate)
        Log.e(TAG, "  1742   " + currentDate)

        val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime1 = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())

        loadLoginEmpDetails()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


    }

    private fun loadLoginEmpDetails() {

        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        ID_AssignedEmployee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        tie_EmployeeName!!.setText(UserNameSP.getString("UserName", null))
        tie_AssignedTo!!.setText(UserNameSP.getString("UserName", null))

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
        tie_Branch!!.setText(BranchSP.getString("BranchName", null))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        til_AssignedTo = findViewById(R.id.til_AssignedTo)
        til_Group = findViewById(R.id.til_Grouping)
        til_CollectedBy = findViewById(R.id.til_CollectedBy)
        til_EmployeeName = findViewById(R.id.til_EmployeeName)
        til_Category = findViewById(R.id.til_Category)
        til_Status = findViewById(R.id.til_Status)
        til_Priority = findViewById(R.id.til_Priority)
        imclose = findViewById(R.id.imclose)
        im_close = findViewById(R.id.im_close)
        llfromdate = findViewById(R.id.llfromdate)
        ll_Fromdate = findViewById(R.id.ll_Fromdate)
        txtfromDate = findViewById(R.id.txtfromDate)
        txttoDate = findViewById(R.id.txttoDate)
        lltodate = findViewById(R.id.lltodate)
        date_Picker = findViewById(R.id.date_Picker)
        txtok = findViewById(R.id.txtok)
        ll_Todate = findViewById(R.id.ll_Todate)
        date_Picker1 = findViewById(R.id.date_Picker1)
        txtok1 = findViewById(R.id.txtok1)
        tie_ReportName = findViewById(R.id.tie_ReportName)
        tie_Branch = findViewById(R.id.tie_Branch)
        tie_FromDate = findViewById(R.id.tie_FromDate)
        tie_ToDate = findViewById(R.id.tie_ToDate)
        tie_EmployeeName = findViewById(R.id.tie_EmployeeName)
        tie_CollectedBy = findViewById(R.id.tie_CollectedBy)
        tie_Category = findViewById(R.id.tie_Category)
        tie_AssignedTo = findViewById(R.id.tie_AssignedTo)
        tie_Product = findViewById(R.id.tie_Product)
        tie_FollowUpAction = findViewById(R.id.tie_FollowUpAction)
        tie_FollowUpType = findViewById(R.id.tie_FollowUpType)
        tie_Priority = findViewById(R.id.tie_Priority)
        tie_Status = findViewById(R.id.tie_Status)
        tie_Grouping = findViewById(R.id.tie_Grouping)
        til_FollowUpAction = findViewById(R.id.til_FollowUpAction)
        til_FollowUpType = findViewById(R.id.til_FollowUpType)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        txtok1!!.setOnClickListener(this)
        llfromdate!!.setOnClickListener(this)
        lltodate!!.setOnClickListener(this)
        imclose!!.setOnClickListener(this)
        txtok!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        im_close!!.setOnClickListener(this)
        tie_ReportName!!.setOnClickListener(this)
        val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
        var isAdmin = IsAdminSP.getString("IsAdmin", null)

        val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
        var IsManager = IsManagerSP.getString("IsManager", null)

        if (isAdmin.equals("1") && IsManager.equals("0")) {
            tie_Branch!!.setOnClickListener(this)
            tie_EmployeeName!!.setOnClickListener(this)
            tie_AssignedTo!!.setOnClickListener(this)
            tie_CollectedBy!!.setOnClickListener(this)
        }
        else if (isAdmin.equals("0") && IsManager.equals("1")){
            tie_EmployeeName!!.setOnClickListener(this)
            tie_AssignedTo!!.setOnClickListener(this)
            tie_CollectedBy!!.setOnClickListener(this)
        }
        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        tie_FollowUpAction!!.setOnClickListener(this)
        tie_FollowUpType!!.setOnClickListener(this)
        tie_Priority!!.setOnClickListener(this)
        tie_Status!!.setOnClickListener(this)
        tie_Grouping!!.setOnClickListener(this)
        tie_Category!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                Config.disableClick(v)
                finish()
            }
            R.id.llfromdate -> {

                ll_Fromdate!!.visibility = View.VISIBLE
            }
            R.id.im_close -> {
                ll_Fromdate!!.visibility = View.GONE
            }
            R.id.lltodate -> {
                ll_Todate!!.visibility = View.VISIBLE
            }
            R.id.imclose -> {
                ll_Todate!!.visibility = View.GONE
            }
            R.id.txtok1 -> {
                Config.disableClick(v)
                date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
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
                txttoDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                ll_Todate!!.visibility = View.GONE
            }
            R.id.txtok -> {
                Config.disableClick(v)
                date_Picker!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
                txtfromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                ll_Fromdate!!.visibility = View.GONE
            }
            R.id.tie_Category->{
                Config.disableClick(v)
                prodcategory = 0
                getCategory()
            }

            R.id.tie_ReportName -> {
                Config.disableClick(v)
                getReportName()
            }

            R.id.tie_Branch -> {
                Config.disableClick(v)
                getBranch()
            }

            R.id.tie_FromDate -> {
                Config.disableClick(v)
                openBottomSheet(tie_FromDate, tie_ToDate)
            }

            R.id.tie_ToDate -> {
                Config.disableClick(v)
                openBottomSheet(tie_FromDate, tie_ToDate)
            }

            R.id.tie_CollectedBy -> {
                Config.disableClick(v)
                getCollectedBy()
            }

            R.id.tie_EmployeeName -> {
                Config.disableClick(v)

                empUseMode = "0"
                empUseBranch = 0
                getEmpByBranch(0)
            }
            R.id.tie_AssignedTo -> {
                Config.disableClick(v)
                empUseMode = "1"
                empUseBranch = 0
                getEmpByBranch(1)
            }
            R.id.tie_Product -> {
                Config.disableClick(v)
                getProductDetail()
            }

            R.id.tie_FollowUpAction -> {
                Config.disableClick(v)
                getFollowupAction()
            }

            R.id.tie_FollowUpType -> {
                Config.disableClick(v)
                getFollowupType()
            }
            R.id.tie_Priority -> {
                Config.disableClick(v)
                getProductPriority()
            }
            R.id.tie_Status -> {
                Config.disableClick(v)
                getProductStatus()
            }
            R.id.tie_Grouping -> {
                Config.disableClick(v)
                getGrouping()
            }

            R.id.btnSubmit -> {
                Config.disableClick(v)
                validateData(v)
            }
            R.id.btnReset -> {
                Config.disableClick(v)
                resetData()

            }


        }
    }


    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@TicketReportActivity, HomeActivity::class.java)
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
        try {
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
                alertDialog.dismiss()
            }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.show()
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
//                                             if (prodcategory == 0){
//                                                 prodcategory++
                                            productCategoryPopup(prodCategoryArrayList)
//                                             }

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TicketReportActivity,
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

            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductCategoryAdapter(this@LeadGenerationActivity, prodCategoryArrayList)
            val adapter = ProductCategoryAdapter(this@TicketReportActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

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
                        ProductCategoryAdapter(this@TicketReportActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
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


    fun timeSelector() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            this,
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
            val datePickerDialog = DatePickerDialog(
                this,
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
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCancelable(false)
            dialog1.setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1.findViewById(R.id.btnYes) as Button
            val btn_No = dialog1.findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1.dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@TicketReportActivity, WelcomeActivity::class.java))
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
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCancelable(false)
            dialog1.setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1.findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1.findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1.dismiss()
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

    private fun getCalendarId(context: Context): Long? {

        try
        {
            val permissions = true
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
            Log.e("calcursor", calCursor.toString())
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
                    Log.e(TAG,"CALID : "+calID.toLong())
                    return calID.toLong()
                }
            }


        }
        catch(e:SecurityException)
        {
            Log.e(TAG,"Error"+e.toString())
        }
        return null



    }

    fun addEvent(
        iyr: Int,
        imnth: Int,
        iday: Int,
        ihour: Int,
        imin: Int,
        descriptn: String,
        Title: String
    ) {
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
        if (calendarId != null) {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Reminder set successfully.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    private fun getReportName() {
        var reportName = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportNameViewModel.getReportName(this, SubMode)!!.observe(
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
                                    this@TicketReportActivity,
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

    private fun getEmpByBranch(i: Int) {
//         var branch = 0
        var SubMode = "1"
        Log.v("sfsdfsdfdf", "branch" + ID_Branch)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                empByBranchViewModel.getEmpByBranch(this, ID_Branch,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (empUseBranch == 0) {
                                    empUseBranch++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeAllArrayList =
                                            jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length() > 0) {

                                            employeeAllPopup(employeeAllArrayList, i)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TicketReportActivity,
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
            Log.i("responsewewe", "7102    " + reportNameArrayList)


            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyReportName!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ReportNameAdapter(this@TicketReportActivity, reportNamesort, "Lead")
            recyReportName!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

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
                    val adapter =
                        ReportNameAdapter(this@TicketReportActivity, reportNamesort, "Lead")
                    recyReportName!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
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
                                        this@TicketReportActivity,
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


            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@TicketReportActivity, branchArrayList)
            val adapter = BranchAdapter(this@TicketReportActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

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
                    val adapter = BranchAdapter(this@TicketReportActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
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


    private fun openBottomSheet(dateField1: TextInputEditText?, dateField2: TextInputEditText?) {
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
            Log.v("sdfsfdfdddd", "FromDate " + FromDate)
            Log.v("sdfsfdfdddd", "ToDate " + ToDate)
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
            Log.v("sdfsfdfdddd", "FromDate " + FromDate)
            Log.v("sdfsfdfdddd", "ToDate " + ToDate)
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
            rad_last_month!!.isChecked = false
            rad_last_3_month!!.isChecked = true
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " + FromDate)
            Log.v("sdfsfdfdddd", "ToDate " + ToDate)
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
            rad_last_month!!.isChecked = false
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = true
            rad_last_12_month!!.isChecked = false
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " + FromDate)
            Log.v("sdfsfdfdddd", "ToDate " + ToDate)
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
            rad_last_month!!.isChecked = false
            rad_last_3_month!!.isChecked = false
            rad_last_6_month!!.isChecked = false
            rad_last_12_month!!.isChecked = true
            FromDate = sdf.format(FirstDay)
            ToDate = sdf.format(LastDay)
            Log.v("sdfsfdfdddd", "FromDate " + FromDate)
            Log.v("sdfsfdfdddd", "ToDate " + ToDate)
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
            if (edt_fromDate.text.toString() == "" && edt_toDate.text.toString() != "") {
                val builder = AlertDialog.Builder(
                    this@TicketReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else if (edt_fromDate.text.toString() != "" && edt_toDate.text.toString() == "") {
                val builder = AlertDialog.Builder(
                    this@TicketReportActivity,
                    R.style.MyDialogTheme
                )
                builder.setMessage("Please Fill Both Fields")
                builder.setPositiveButton("Ok") { dialogInterface, which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
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

    private fun getCollectedBy() {
        Log.d(TAG, "getCollectedBy: in")
        var countLeadBy = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                Log.d(TAG, "getCollectedBy: have Connection")
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
                            Log.e(TAG, "msg   228   " + msg.length)
                            Log.e(TAG, "msg   228   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CollectedByUsersList")
                                leadByArrayList = jobjt.getJSONArray("CollectedByUsers")
                                if (leadByArrayList.length() > 0) {
                                    if (countLeadBy == 0) {
                                        countLeadBy++
                                        leadByPopup(leadByArrayList)
                                    }
                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportActivity,
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
                productDetailViewModel.getProductDetail(this, ID_Category!!)!!.observe(
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
                                        this@TicketReportActivity,
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


            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductDetailAdapter(this@TicketReportActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@TicketReportActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)


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
                    val adapter = ProductDetailAdapter(this@TicketReportActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
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
                followUpActionViewModel.getFollowupAction(this, SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   82   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                    followUpActionArrayList =
                                        jobjt.getJSONArray("FollowUpActionDetailsList")
                                    if (followUpActionArrayList.length() > 0) {
                                        if (followUpAction == 0) {
                                            followUpAction++
                                            followUpActionPopup(followUpActionArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@TicketReportActivity,
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

    /*  private fun emplyeeDetailPopup() {
          try {
              dialogLeadBy = Dialog(this)
              dialogLeadBy!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
              dialogLeadBy!!.setContentView(R.layout.lead_by_popup)
              dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
              recyLeadby = dialogLeadBy!!.findViewById(R.id.recyLeadby) as RecyclerView
              val etsearch = dialogLeadBy!!.findViewById(R.id.etsearch) as EditText
              employeeDetailSort = JSONArray()
              for (k in 0 until employeeDetailArrayList.length()) {
                  val jsonObject = employeeDetailArrayList.getJSONObject(k)
                  // reportNamesort.put(k,jsonObject)
                  employeeDetailSort.put(jsonObject)
              }
              val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
              recyEmplyeeDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
  //            recyCustomer!!.setHasFixedSize(true)
  //            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
              val adapter = EmployeeBranchWiseAdapter(this@TicketReportActivity, employeeDetailSort)
              recyEmplyeeDetail!!.adapter = adapter
              adapter.setClickListener(this@TicketReportActivity)
              etsearch!!.addTextChangedListener(object : TextWatcher {
                  override fun afterTextChanged(p0: Editable?) {
                  }

                  override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  }

                  override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                      //  list_view!!.setVisibility(View.VISIBLE)
                      val textlength = etsearch!!.text.length
                      employeeDetailSort = JSONArray()
                      for (k in 0 until employeeDetailArrayList.length()) {
                          val jsonObject = employeeDetailArrayList.getJSONObject(k)
                          if (textlength <= jsonObject.getString("Name").length) {
                              if (jsonObject.getString("Name")!!.toLowerCase().trim()
                                      .contains(etsearch!!.text.toString().toLowerCase().trim())
                              ) {
                                  employeeDetailSort.put(jsonObject)
                              }
                          }
                      }
                      Log.e(TAG, "leadBySort               7103    " + employeeDetailSort)
                      val adapter =
                          EmployeeBranchWiseAdapter(this@TicketReportActivity, employeeDetailSort)
                      recyEmplyeeDetail!!.adapter = adapter
                      adapter.setClickListener(this@TicketReportActivity)
                  }
              })
              dialogLeadBy!!.show()
              dialogLeadBy!!.getWindow()!!.setLayout(
                  ViewGroup.LayoutParams.MATCH_PARENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT
              );
          } catch (e: Exception) {
              e.printStackTrace()
          }
      }*/

    private fun leadByPopup(leadByArrayList: JSONArray) {

        try {

            dialogLeadBy = Dialog(this)
            dialogLeadBy!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadBy!!.setContentView(R.layout.lead_by_popup)
            dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadby = dialogLeadBy!!.findViewById(R.id.recyLeadby) as RecyclerView
            val etsearch = dialogLeadBy!!.findViewById(R.id.etsearch) as EditText

            leadBySort = JSONArray()
            for (k in 0 until leadByArrayList.length()) {
                val jsonObject = leadByArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadBySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyLeadby!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            val adapter = CollectedByAdapter(this@TicketReportActivity, leadBySort)
            recyLeadby!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadBySort = JSONArray()

                    for (k in 0 until leadByArrayList.length()) {
                        val jsonObject = leadByArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                leadBySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "leadBySort               7103    " + leadBySort)
                    val adapter = LeadByAdapter(this@TicketReportActivity, leadBySort)
                    recyLeadby!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
                }
            })

            dialogLeadBy!!.show()
            dialogLeadBy!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!!.setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction =
                dialogFollowupAction!!.findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!!.findViewById(R.id.etsearch) as EditText

            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@TicketReportActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@TicketReportActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpActionSort = JSONArray()

                    for (k in 0 until followUpActionArrayList.length()) {
                        val jsonObject = followUpActionArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("NxtActnName").length) {
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "followUpActionSort               7103    " + followUpActionSort)
                    val adapter =
                        FollowupActionAdapter(this@TicketReportActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)

                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   82   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                    followUpTypeArrayList =
                                        jobjt.getJSONArray("FollowUpTypeDetailsList")
                                    if (followUpTypeArrayList.length() > 0) {
                                        if (followUpType == 0) {
                                            followUpType++
                                            followupTypePopup(followUpTypeArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@TicketReportActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!!.setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType =
                dialogFollowupType!!.findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!!.findViewById(R.id.etsearch) as EditText

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                followUpTypeSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupTypeAdapter(this@TicketReportActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@TicketReportActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "followUpTypeSort               7103    " + followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@TicketReportActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun employeeAllPopup(employeeAllArrayList: JSONArray, i: Int) {
        try {

            dialogEmployeeAll = Dialog(this)
            dialogEmployeeAll!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAll!!.setContentView(R.layout.employeeall_popup)
            dialogEmployeeAll!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAll = dialogEmployeeAll!!.findViewById(R.id.recyEmployeeAll) as RecyclerView
            val etsearch = dialogEmployeeAll!!.findViewById(R.id.etsearch) as EditText


            employeeAllSort = JSONArray()
            for (k in 0 until employeeAllArrayList.length()) {
                val jsonObject = employeeAllArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeAllSort.put(jsonObject)
            }

            Log.e(TAG,"2087771  "+i)

//            if (i == 0) {
            if (empUseMode.equals("0")) {
                Log.e(TAG,"2087772  "+i)
                val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
                recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
                val adapter = EmployeeAllAdapter(this@TicketReportActivity, employeeAllSort)
                recyEmployeeAll!!.adapter = adapter
                adapter.setClickListener(this@TicketReportActivity)
            } else if (empUseMode.equals("1")) {
                Log.e(TAG,"2087773  "+i)
                val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
                recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
                val adapter = AssignedListAdapter(this@TicketReportActivity, employeeAllSort)
                recyEmployeeAll!!.adapter = adapter
                adapter.setClickListener(this@TicketReportActivity)
            }

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
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeAllSort.put(jsonObject)
                            }

                        }
                    }

                    if (empUseMode.equals("0")) {
                        Log.e(TAG, "employeeAllSort               7103    " + employeeAllSort)
                        val adapter = EmployeeAllAdapter(this@TicketReportActivity, employeeAllSort)
                        recyEmployeeAll!!.adapter = adapter
                        adapter.setClickListener(this@TicketReportActivity)
                    } else if (empUseMode.equals("1")) {
                        Log.e(TAG, "employeeAllSort               7103    " + employeeAllSort)
                        val adapter = AssignedListAdapter(this@TicketReportActivity, employeeAllSort)
                        recyEmployeeAll!!.adapter = adapter
                        adapter.setClickListener(this@TicketReportActivity)
                    }

                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   353   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                    prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                    if (prodPriorityArrayList.length() > 0) {
                                        if (prodpriority == 0) {
                                            prodpriority++
                                            productPriorityPopup(prodPriorityArrayList)
                                        }

                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@TicketReportActivity,
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

    private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

        try {

            dialogProdPriority = Dialog(this)
            dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdPriority!!.setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority =
                dialogProdPriority!!.findViewById(R.id.recyProdPriority) as RecyclerView
            recyProdPriority =
                dialogProdPriority!!.findViewById(R.id.recyProdPriority) as RecyclerView
            val etsearch = dialogProdPriority!!.findViewById(R.id.etsearch) as EditText

            prodPrioritySort = JSONArray()
            for (k in 0 until prodPriorityArrayList.length()) {
                val jsonObject = prodPriorityArrayList.getJSONObject(k)
                prodPrioritySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@TicketReportActivity, prodPriorityArrayList)
            val adapter = ProductPriorityAdapter(this@TicketReportActivity, prodPrioritySort)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodPrioritySort = JSONArray()

                    for (k in 0 until prodPriorityArrayList.length()) {
                        val jsonObject = prodPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("PriorityName").length) {
                            if (jsonObject.getString("PriorityName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodPrioritySort               7103    " + prodPrioritySort)
                    val adapter =
                        ProductPriorityAdapter(this@TicketReportActivity, prodPrioritySort)
                    recyProdPriority!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
                }
            })

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
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
                productStatusViewModel.getProductStatus(this, ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   333   " + msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("StatusDetailsList")
                                    prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                    if (prodStatusArrayList.length() > 0) {
                                        if (prodstatus == 0) {
                                            prodstatus++
                                            productStatusPopup(prodStatusArrayList)
                                        }

                                    }

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@TicketReportActivity,
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

    private fun productStatusPopup(prodStatusArrayList: JSONArray) {

        try {

            dialogProdStatus = Dialog(this)
            dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdStatus!!.setContentView(R.layout.product_status_popup)
            dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdStatus = dialogProdStatus!!.findViewById(R.id.recyProdStatus) as RecyclerView
            val etsearch = dialogProdStatus!!.findViewById(R.id.etsearch) as EditText

            prodStatusSort = JSONArray()
            for (k in 0 until prodStatusArrayList.length()) {
                val jsonObject = prodStatusArrayList.getJSONObject(k)
                prodStatusSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductStatusAdapter(this@TicketReportActivity, prodStatusArrayList)
            val adapter = ProductStatusAdapter(this@TicketReportActivity, prodStatusSort)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodStatusSort = JSONArray()

                    for (k in 0 until prodStatusArrayList.length()) {
                        val jsonObject = prodStatusArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("StatusName").length) {
                            if (jsonObject.getString("StatusName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodStatusSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodStatusSort               7103    " + prodStatusSort)
                    val adapter = ProductStatusAdapter(this@TicketReportActivity, prodStatusSort)
                    recyProdStatus!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
                }
            })

            dialogProdStatus!!.show()
            dialogProdStatus!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getGrouping() {
        var grouping = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                groupingViewModel.getGrouping(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   333   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("Grouping")
                                groupingArrayList = jobjt.getJSONArray("GroupList")
                                if (groupingArrayList.length() > 0) {
                                    if (grouping == 0) {
                                        grouping++
                                        groupingPopup(groupingArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TicketReportActivity,
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

    private fun groupingPopup(groupingArrayList: JSONArray) {
        try {

            dialogGrouping = Dialog(this)
            dialogGrouping!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogGrouping!!.setContentView(R.layout.grouping_popup)
            dialogGrouping!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyGrouping = dialogGrouping!!.findViewById(R.id.recyGrouping) as RecyclerView
            val etsearch = dialogGrouping!!.findViewById(R.id.etsearch) as EditText

            groupingSort = JSONArray()
            for (k in 0 until groupingArrayList.length()) {
                val jsonObject = groupingArrayList.getJSONObject(k)
                groupingSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@TicketReportActivity, 1)
            recyGrouping!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = GroupingAdapter(this@TicketReportActivity, groupingArrayList)
            val adapter = GroupingAdapter(this@TicketReportActivity, groupingSort)
            recyGrouping!!.adapter = adapter
            adapter.setClickListener(this@TicketReportActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    groupingSort = JSONArray()

                    for (k in 0 until groupingArrayList.length()) {
                        val jsonObject = groupingArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("GroupName").length) {
                            if (jsonObject.getString("GroupName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                groupingSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "groupingSort               7103    " + groupingSort)
                    val adapter = GroupingAdapter(this@TicketReportActivity, groupingSort)
                    recyGrouping!!.adapter = adapter
                    adapter.setClickListener(this@TicketReportActivity)
                }
            })

            dialogGrouping!!.show()
            dialogGrouping!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }


    override fun onClick(position: Int, data: String) {

        if (data.equals("reportname")) {
            resetData()
            dialogReportName!!.dismiss()
            //  val jsonObject = reportNameArrayList.getJSONObject(position)
            val jsonObject = reportNamesort.getJSONObject(position)
            Log.e(TAG, "ReportMode   " + jsonObject.getString("ReportMode"))
            ReportMode = jsonObject.getString("ReportMode")
            tie_ReportName!!.setText(jsonObject.getString("ReportName"))

//            if (ReportMode.equals("1")) {
////                ActionListT
//                til_FollowUpAction!!.visibility = View.VISIBLE
//                til_FollowUpType!!.visibility = View.VISIBLE
//            }
            if (ReportMode.equals("2")) {
//                FollowUpTicket
//                til_FollowUpAction!!.visibility = View.VISIBLE
//                til_FollowUpType!!.visibility = View.VISIBLE
            }
//            if (ReportMode.equals("4")) {
////                StatusList
//                til_FollowUpAction!!.visibility = View.GONE
//                til_FollowUpType!!.visibility = View.GONE
//            }
            if (ReportMode.equals("5")) {
//                NewListTicket
//                til_FollowUpAction!!.visibility = View.GONE
                til_FollowUpType!!.visibility = View.GONE
                til_Status!!.visibility = View.GONE

            }

            if (ReportMode.equals("6")) {
//                Lead Summary
                til_Priority!!.visibility = View.GONE
                til_Status!!.visibility = View.GONE
                til_EmployeeName!!.visibility = View.GONE
                til_AssignedTo!!.visibility = View.VISIBLE
//                til_CollectedBy!!.visibility = View.VISIBLE
                til_Group!!.visibility = View.VISIBLE
                til_Category!!.visibility = View.VISIBLE
            }

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
            ID_AssignedEmployee = ""
            tie_AssignedTo!!.setText("")
            ID_CollectedBy = ""
            tie_CollectedBy!!.setText("")


        }

        if (data.equals("prodcategory")) {
            dialogProdCat!!.dismiss()
//             val jsonObject = prodCategoryArrayList.getJSONObject(position)
            val jsonObject = prodCategorySort.getJSONObject(position)
            Log.e(TAG, "ID_Category   " + jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))
//                                  Log.i("resperr","check data side="+checkProject)
//            if (jsonObject.getString("Project").equals("0")) {
//                ll_product_qty!!.visibility = View.VISIBLE
//                edtProjectName!!.visibility = View.GONE
//                checkProject="1"  // <--gone
//
//            } else if (jsonObject.getString("Project").equals("1")) {
//                ll_product_qty!!.visibility = View.GONE
//                edtProjectName!!.visibility = View.VISIBLE
//
//                checkProject="0"  // <-- visible
//
//            }
        }

        if (data.equals("proddetails")) {
            dialogProdDet!!.dismiss()
//            val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
            ID_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))
        }

        if (data.equals("followupaction")) {
            dialogFollowupAction!!.dismiss()
            // val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG, "ID_NextAction   " + jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            tie_FollowUpAction!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("followuptype")) {
            dialogFollowupType!!.dismiss()
//            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_FollowUpType!!.setText(jsonObject.getString("ActnTypeName"))


        }

        if (data.equals("leadby")) {
            dialogLeadBy!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = leadBySort.getJSONObject(position)

            Log.e(TAG, "jsonObject   " + jsonObject)
            ID_CollectedBy = jsonObject.getString("ID_CollectedBy")
            tie_CollectedBy!!.setText(jsonObject.getString("Name"))


        }

        if (data.equals("prodpriority")) {
            dialogProdPriority!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = prodPrioritySort.getJSONObject(position)
            Log.e(TAG, "ID_Priority   " + jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            tie_Priority!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("prodstatus")) {
            dialogProdStatus!!.dismiss()
            //  val jsonObject = prodStatusArrayList.getJSONObject(position)
            val jsonObject = prodStatusSort.getJSONObject(position)
            Log.e(TAG, "ID_Status   " + jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            tie_Status!!.setText(jsonObject.getString("StatusName"))

        }

        if (data.equals("employeeAll")) {

            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"2701110   "+jsonObject)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_EmployeeName!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("AssiggnedList")) {
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"2701111   "+jsonObject)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_AssignedEmployee = jsonObject.getString("ID_Employee")
            tie_AssignedTo!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("grouping")) {
            dialogGrouping!!.dismiss()
//            val jsonObject = groupingArrayList.getJSONObject(position)
            val jsonObject = groupingSort.getJSONObject(position)
            Log.e(TAG, "GroupId   " + jsonObject.getString("GroupMode"))
            GroupId = jsonObject.getString("GroupMode")
            tie_Grouping!!.setText(jsonObject.getString("GroupName"))

        }


    }

    private fun resetData() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)

        tie_ReportName!!.setText("")
        tie_Branch!!.setText("")
        tie_Product!!.setText("")
        tie_FollowUpAction!!.setText("")
        tie_FollowUpType!!.setText("")
        tie_Priority!!.setText("")
        tie_Status!!.setText("")
        tie_Grouping!!.setText("")
        tie_Category!!.setText("")

        ReportMode = ""
        ID_Branch = ""
        ID_Product = ""
        ID_NextAction = ""
        ID_ActionType = ""
        ID_Priority = ""
        ID_Status = ""
        GroupId = ""
        ID_Category = ""

        til_Priority!!.visibility = View.VISIBLE
        til_Status!!.visibility = View.VISIBLE
        til_EmployeeName!!.visibility = View.VISIBLE
        til_AssignedTo!!.visibility = View.GONE
        til_CollectedBy!!.visibility = View.GONE
        til_Group!!.visibility = View.GONE
        til_Category!!.visibility = View.GONE

        loadLoginEmpDetails()

    }

    private fun validateData(v: View) {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val fromDa = sdf.parse(tie_FromDate!!.text.toString());
        val toDa = sdf.parse(tie_ToDate!!.text.toString());
        if (ReportMode.equals("")) {
            Config.snackBars(context, v, "Select Report Name")
        } else if (ReportMode.equals("6")) {
            if (ID_Branch.equals("")) {
                Config.snackBars(context, v, "Select Branch")
            } else if (tie_FromDate!!.text.toString().equals("")) {
                Config.snackBars(context, v, "Select From Date")
            } else if (tie_ToDate!!.text.toString().equals("")) {
                Config.snackBars(context, v, "Select To Date")
            } else if (fromDa.after(toDa)) {
                Config.snackBars(context, v, "Check Selected Date Range")
            }
            else if (GroupId.equals("")) {
                Config.snackBars(context, v, "Select Summary Type")
              //  Config.snackBars(context, v, "Select Group")
            }
            else {
                PassData()
            }
        } else {
            if (ID_Branch.equals("")) {
                Config.snackBars(context, v, "Select Branch")
            } else if (ID_Employee.equals("")) {
                Config.snackBars(context, v, "Select Employee")
            } else if (tie_FromDate!!.text.toString().equals("")) {
                Config.snackBars(context, v, "Select From Date")
            } else if (tie_ToDate!!.text.toString().equals("")) {
                Config.snackBars(context, v, "Select To Date")
            } else if (fromDa.after(toDa)) {
                Config.snackBars(context, v, "Check Selected Date Range")
            } else {
                PassData()
            }
        }

//        else if (ID_Product.equals("")){
//            Config.snackBars(context,v,"Select Product")
//        }
//        else if (ReportMode.equals("4") || ReportMode.equals("5")){
//            if (ID_Priority.equals("")){
//                Config.snackBars(context,v,"Select Priority")
//            }
//            else if (ID_Status.equals("")){
//                Config.snackBars(context,v,"Select Status")
//            }
//            else if (GroupId.equals("")){
//                Config.snackBars(context,v,"Select Grouping")
//            }
//            else{
//                PassData()
//            }
//        }
//        else{
//            if (ID_NextAction.equals("")){
//                Config.snackBars(context,v,"Select Followup Action ")
//            }
//            else if (ID_ActionType.equals("")){
//                Config.snackBars(context,v,"Select Followup Type")
//            }
//            else if (ID_Priority.equals("")){
//                Config.snackBars(context,v,"Select Priority")
//            }
//            else if (ID_Status.equals("")){
//                Config.snackBars(context,v,"Select Status")
//            }
//            else if (GroupId.equals("")){
//                Config.snackBars(context,v,"Select Grouping")
//            }
//            else {
//                PassData()
//            }
//        }


    }

    private fun PassData() {

        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dateFrom = inputFormat.parse(tie_FromDate!!.text.toString())
        val strFromDate = outputFormat.format(dateFrom)
        val dateTo = inputFormat.parse(tie_ToDate!!.text.toString())
        val strToDate = outputFormat.format(dateTo)

        Log.e(TAG, "strFromDate   " + strFromDate + "    " + strToDate)
        Log.e(TAG, "sadasdasdsads   " + ID_Category)


        intent = Intent(applicationContext, TicketReportDetailActivity::class.java)
        intent.putExtra("ReportName", tie_ReportName!!.text.toString())
        intent.putExtra("ReportMode", ReportMode)
        intent.putExtra("ID_Branch", ID_Branch)
        intent.putExtra("ID_Employee", ID_Employee)
        intent.putExtra("Fromdate", strFromDate)
        intent.putExtra("Todate", strToDate)
        intent.putExtra("ID_Product", ID_Product)
        intent.putExtra("ID_NextAction", ID_NextAction)
        intent.putExtra("ID_ActionType", ID_ActionType)
        intent.putExtra("ID_Priority", ID_Priority)
        intent.putExtra("ID_Status", ID_Status)
        intent.putExtra("GroupId", GroupId)
        intent.putExtra("ID_CollectedBy", ID_CollectedBy)
        intent.putExtra("ID_AssignedEmployee", ID_AssignedEmployee)
        intent.putExtra("ID_Category", ID_Category)

        startActivity(intent)
    }

    private fun datePicker(dateField: TextView) {
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
                    FromDate = strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }
                if (fromToDate == 1) {
                    tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    ToDate = strDay + "-" + strMonth + "-" + strYear
                    dateField!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()

    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}