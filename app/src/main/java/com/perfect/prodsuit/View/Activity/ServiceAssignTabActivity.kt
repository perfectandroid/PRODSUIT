package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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
import com.perfect.prodsuit.Viewmodel.AreaViewModel
import com.perfect.prodsuit.Viewmodel.BranchViewModel
import com.perfect.prodsuit.Viewmodel.EmpByBranchViewModel
import com.perfect.prodsuit.Viewmodel.ServiceCountViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ServiceAssignTabActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener,
    View.OnFocusChangeListener {

    var TAG  ="ServiceAssignTabActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    lateinit var countlistSort: JSONArray
    private var card_new: CardView? = null
    private var card_ongoing: CardView? = null
    private var ll_onGoing: LinearLayout? = null
    private var ll_new: LinearLayout? = null
    private var tv_newCount: TextView? = null
    private var tv_OnGoingCount: TextView? = null
    var recyclervw_serviceassign: RecyclerView? = null
    private var imgv_filter: ImageView? = null

    private var ll_StafAdmin: LinearLayout? = null
    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null

    private var tie_Branch: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_FromDate: TextInputEditText? = null
    private var tie_ToDate: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_TicketNumber: TextInputEditText? = null
    private var tie_DueDays: TextInputEditText? = null

    private var til_Branch: TextInputLayout? = null
    private var til_FromDate: TextInputLayout? = null
    private var til_ToDate: TextInputLayout? = null


    private var ID_Branch: String = "0"
    private var ID_Employee: String = "0"
    private var FK_Area: String = "0"
    private var strFromDate: String = ""
    private var strToDate: String = ""
    private var strCustomer: String = ""


    private var strMobile: String = ""
    private var strTicketNo: String = ""
    private var strDueDays: String = ""

    var branchCount = 0
    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null

    var employeeCount = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList : JSONArray
    lateinit var employeeAllSort : JSONArray
    private var dialogEmployeeAll : Dialog? = null
    var recyEmployeeAll: RecyclerView? = null


    var serviceCount = 0
    lateinit var serviceCountViewModel: ServiceCountViewModel

    var dateMode = 0 // 0 = FromDate , 1 = To date

    var areaCount = 0
    lateinit var areaViewModel: AreaViewModel
    lateinit var areaArrayList: JSONArray
    lateinit var areaSort: JSONArray
    private var dialogArea: Dialog? = null
    var recycArea: RecyclerView? = null

    private var temp_ID_Branch: String = "0"
    private var temp_Branch: String = ""
    private var temp_ID_Employee: String = "0"
    private var temp_Employee: String = ""
    private var temp_FromDate: String = ""
    private var temp_ToDate: String = ""
    private var temp_FK_Area: String = "0"
    private var temp_Area: String = ""
    private var temp_Customer: String = ""
    private var temp_Mobile: String = ""
    private var temp_TicketNo: String = ""
    private var temp_DueDays: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign_tab1)
        context = this@ServiceAssignTabActivity
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        areaViewModel = ViewModelProvider(this).get(AreaViewModel::class.java)
        serviceCountViewModel = ViewModelProvider(this).get(ServiceCountViewModel::class.java)
        setRegViews()

        loadLoginEmpDetails("0")
        serviceCount = 0
        getserviceCounts()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        card_new = findViewById<CardView>(R.id.card_new)
        card_ongoing = findViewById<CardView>(R.id.card_ongoing)
        ll_new = findViewById<LinearLayout>(R.id.ll_new)
        ll_onGoing = findViewById<LinearLayout>(R.id.ll_onGoing)
        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)

        tv_newCount = findViewById<TextView>(R.id.tv_newCount)
        tv_OnGoingCount = findViewById<TextView>(R.id.tv_OnGoingCount)


        recyclervw_serviceassign = findViewById<RecyclerView>(R.id.recyclervw_serviceassign)

      //  card_new!!.setOnClickListener(this)
      //  card_ongoing!!.setOnClickListener(this)
    //    ll_new!!.setOnClickListener(this)
     //   ll_onGoing!!.setOnClickListener(this)
       // imgv_filter!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.card_new->{
                val i = Intent(this@ServiceAssignTabActivity, ServiceAssignListActivity::class.java)
                i.putExtra("SubMode","2")
                i.putExtra("ID_Branch",ID_Branch)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("strDueDays",strDueDays)
                startActivity(i)
            }
            R.id.card_ongoing->{
                val i = Intent(this@ServiceAssignTabActivity, ServiceOngoingListActivityActivity::class.java)
                i.putExtra("SubMode","3")
                i.putExtra("ID_Branch",ID_Branch)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("strDueDays",strDueDays)
                startActivity(i)
            }

            R.id.ll_new->{
                val i = Intent(this@ServiceAssignTabActivity, ServiceAssignListActivity::class.java)
                i.putExtra("SubMode","2")
                i.putExtra("ID_Branch",ID_Branch)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("strDueDays",strDueDays)
                startActivity(i)
            }
            R.id.ll_onGoing->{
                val i = Intent(this@ServiceAssignTabActivity, ServiceOngoingListActivityActivity::class.java)
                i.putExtra("SubMode","3")
                i.putExtra("ID_Branch",ID_Branch)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("strDueDays",strDueDays)
                startActivity(i)
            }


            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomSheet()
            }

            R.id.tie_Branch->{
                Config.disableClick(v)
                Log.e(TAG,"Branch  86")
                branchCount = 0
                getBranch()
            }
            R.id.tie_Employee->{
                Config.disableClick(v)
                Log.e(TAG,"Employee  86")
                employeeCount = 0
                getEmpByBranch()
            }

            R.id.tie_FromDate->{
                Config.disableClick(v)
                Log.e(TAG,"FROM DATE  86")
                dateMode = 0
                openBottomDate()
            }

            R.id.tie_ToDate->{
                Config.disableClick(v)
                Log.e(TAG,"TO DATE  86")
                dateMode = 1
                openBottomDate()
            }

            R.id.tie_Area->{
                Config.disableClick(v)
                Log.e(TAG,"TO DATE  86")
                areaCount = 0
                getArea()
            }




        }
    }

    private fun getserviceCounts() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceCountViewModel.getServiceCount(this,ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (serviceCount == 0){
                                    serviceCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceCountDetails")

                                       /* tv_newCount!!.setText(""+jobjt.getString("New"))
                                        tv_OnGoingCount!!.setText(""+jobjt.getString("OnGoing"))*/
                                        branchArrayList = jobjt.getJSONArray("ServiceCountList")
                                        if (branchArrayList.length() > 0) {

                                         //   imgv_filter!!.visibility  =View.VISIBLE

                                            countlistSort = JSONArray()
                                            for (k in 0 until branchArrayList.length()) {
                                                val jsonObject = branchArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                countlistSort.put(jsonObject)
                                            }

                                          //  tv_listCount!!.setText(""+serviceListSort.length())
                                            val lLayout = GridLayoutManager(this@ServiceAssignTabActivity, 1)
                                            recyclervw_serviceassign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ServiceCountAdapter(this@ServiceAssignTabActivity, countlistSort,ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)
                                            recyclervw_serviceassign!!.adapter = adapter
                                            adapter.setClickListener(this@ServiceAssignTabActivity)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignTabActivity,
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

    private fun filterBottomSheet() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.sa_filter_bottom_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)


            ll_StafAdmin = view.findViewById<LinearLayout>(R.id.ll_StafAdmin)
            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            til_Branch = view.findViewById<TextInputLayout>(R.id.til_Branch)
            til_FromDate = view.findViewById<TextInputLayout>(R.id.til_FromDate)
            til_ToDate = view.findViewById<TextInputLayout>(R.id.til_ToDate)

            tie_Branch = view.findViewById<TextInputEditText>(R.id.tie_Branch)
            tie_Employee = view.findViewById<TextInputEditText>(R.id.tie_Employee)
            tie_FromDate = view.findViewById<TextInputEditText>(R.id.tie_FromDate)
            tie_ToDate = view.findViewById<TextInputEditText>(R.id.tie_ToDate)
            tie_Area = view.findViewById<TextInputEditText>(R.id.tie_Area)
            tie_Customer = view.findViewById<TextInputEditText>(R.id.tie_Customer)
            tie_Mobile = view.findViewById<TextInputEditText>(R.id.tie_Mobile)
            tie_TicketNumber = view.findViewById<TextInputEditText>(R.id.tie_TicketNumber)
            tie_DueDays = view.findViewById<TextInputEditText>(R.id.tie_DueDays)

            tie_Branch!!.setOnClickListener(this)
            tie_Employee!!.setOnClickListener(this)
            tie_FromDate!!.setOnClickListener(this)
            tie_ToDate!!.setOnClickListener(this)
            tie_Area!!.setOnClickListener(this)

            onTextChangedValues()

            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)
            if (isAdmin.equals("1")){
                ll_StafAdmin!!.visibility  =View.VISIBLE
                tie_Branch!!.isEnabled  = true
                tie_Employee!!.isEnabled  = true
            }else{
                ll_StafAdmin!!.visibility  =View.GONE
                tie_Branch!!.isEnabled  = false
                tie_Employee!!.isEnabled  = false
            }

            Log.e(TAG,"ID_Branch  238   "+ID_Branch)
            Log.e(TAG,"temp_Employee  238   "+temp_Employee)

            if (ID_Branch.equals("")){
                loadLoginEmpDetails("1")
            }
            else{
                ID_Branch = temp_ID_Branch
                tie_Branch!!.setText(temp_Branch)
                ID_Employee = temp_ID_Employee
                tie_Employee!!.setText(temp_Employee)
                tie_FromDate!!.setText(temp_FromDate)
                tie_ToDate!!.setText(temp_ToDate)
                FK_Area = temp_FK_Area
                tie_Area!!.setText(temp_Area)
                tie_Customer!!.setText(temp_Customer)
                tie_Mobile!!.setText(temp_Mobile)
                tie_TicketNumber!!.setText(temp_TicketNo)
                tie_DueDays!!.setText(temp_DueDays)


            }

            txtReset!!.setOnClickListener {
                loadLoginEmpDetails("1")
            }
            txtSearch!!.setOnClickListener {

                validateData(dialog1)
            }





//            dialog1!!.setCancelable(false)
            //   view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


      //      tabLayout = view.findViewById(R.id.tabLayout)



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    private fun validateData(dialog1: BottomSheetDialog) {


        strFromDate = tie_FromDate!!.text.toString()
        strToDate = tie_ToDate!!.text.toString()
        strCustomer = tie_Customer!!.text.toString()
        strMobile = tie_Mobile!!.text.toString()
        strTicketNo = tie_TicketNumber!!.text.toString()
        strDueDays = tie_DueDays!!.text.toString()

        if (FK_Area.equals("")){
            FK_Area = "0"
        }


        if (ID_Branch.equals("")){
            ID_Branch = "0"
        }
        if (ID_Employee.equals("")){
            ID_Employee = "0"
        }

        if (!strFromDate.equals("") || !strToDate.equals("")){

            if (strFromDate.equals("")){
                til_FromDate!!.setError("Select From Date");
                til_FromDate!!.setErrorIconDrawable(null)
            }
            else if (strToDate.equals("")){
                til_ToDate!!.setError("Select To Date");
                til_ToDate!!.setErrorIconDrawable(null)
            }else{
                val sdfDate = SimpleDateFormat("dd-MM-yyyy")
                val strFromDate1 = sdfDate.parse(strFromDate)
                val strToDate1 = sdfDate.parse(strToDate)

                if(strFromDate1.equals(strToDate1) || strFromDate1.before(strToDate1)){
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    if (strFromDate!!.equals("")){
//                    strDate = "0000-00-00 00:00:00"
                        strFromDate = ""
                    }else{
                        var date: Date? = null
                        date = inputFormat.parse(strFromDate)
                        strFromDate = outputFormat.format(date)
                        Log.e(TAG,"DATE   1302   "+strFromDate)
                    }

                    if (strToDate!!.equals("")){
//                    strDate = "0000-00-00 00:00:00"
                        strToDate = ""
                    }else{
                        var date: Date? = null
                        date = inputFormat.parse(strToDate)
                        strToDate = outputFormat.format(date)
                        Log.e(TAG,"DATE   1302   "+strToDate)
                    }

                    dialog1.dismiss()
                    serviceCount = 0
                    getserviceCounts()
                }
                else{
                    til_ToDate!!.setError("To Date should be greater than or equal to From date");
                    til_ToDate!!.setErrorIconDrawable(null)
                }

            }
        }
        else{
            dialog1.dismiss()
            serviceCount = 0
            getserviceCounts()
        }


    }

    private fun onTextChangedValues() {

        tie_Customer!!.setOnFocusChangeListener(this);
        tie_Mobile!!.setOnFocusChangeListener(this);

        tie_Branch!!.addTextChangedListener(watcher)
        tie_Employee!!.addTextChangedListener(watcher)
        tie_FromDate!!.addTextChangedListener(watcher)
        tie_ToDate!!.addTextChangedListener(watcher)
        tie_Area!!.addTextChangedListener(watcher)
        tie_Customer!!.addTextChangedListener(watcher)
        tie_Mobile!!.addTextChangedListener(watcher)
        tie_TicketNumber!!.addTextChangedListener(watcher)
        tie_DueDays!!.addTextChangedListener(watcher)

    }

    private fun clearData() {

    }

    private fun loadLoginEmpDetails(mode : String) {

//        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//
//
//        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
//        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
//        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
//
//
//        temp_ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
//        temp_Branch = BranchSP.getString("BranchName", null).toString()
//        temp_ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//        temp_Employee = UserNameSP.getString("UserName", null).toString()


        ID_Employee ="0"
        ID_Branch = "0"


        temp_ID_Branch = "0"
        temp_Branch = ""
        temp_ID_Employee = "0"
        temp_Employee =""

        if (mode.equals("1")){

            tie_Branch!!.setText("")
            tie_Employee!!.setText("")
            tie_FromDate!!.setText("")
            tie_ToDate!!.setText("")
            tie_Area!!.setText("")
            tie_Customer!!.setText("")
            tie_Mobile!!.setText("")
            tie_TicketNumber!!.setText("")
            tie_DueDays!!.setText("")
        }

        FK_Area = "0"

        temp_FromDate = ""
        temp_ToDate = ""
        temp_Area = ""
        temp_FK_Area = "0"
        temp_Customer = ""
        temp_Mobile = ""
        temp_TicketNo = ""
        temp_DueDays = ""
    }

    private fun getBranch() {
//        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchViewModel.getBranch(this, "0")!!.observe(
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
                                        if (branchCount == 0) {
                                            branchCount++
                                            branchPopup(branchArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@ServiceAssignTabActivity,
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


            val lLayout = GridLayoutManager(this@ServiceAssignTabActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@ServiceAssignTabActivity, branchArrayList)
            val adapter = BranchAdapter(this@ServiceAssignTabActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignTabActivity)

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
                    val adapter = BranchAdapter(this@ServiceAssignTabActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignTabActivity)
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

                                if (employeeCount == 0){
                                    employeeCount++
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
                                            this@ServiceAssignTabActivity,
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

            val lLayout = GridLayoutManager(this@ServiceAssignTabActivity, 1)
            recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
            val adapter = EmployeeAllAdapter(this@ServiceAssignTabActivity, employeeAllSort)
            recyEmployeeAll!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignTabActivity)

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
                    val adapter = EmployeeAllAdapter(this@ServiceAssignTabActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignTabActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

                if (dateMode == 0){
                    tie_FromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    temp_FromDate = ""+strDay+"-"+strMonth+"-"+strYear
                }else if (dateMode == 1){

                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    temp_ToDate = ""+strDay+"-"+strMonth+"-"+strYear
                }
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

    private fun getArea() {
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
                                if (areaCount == 0) {
                                    areaCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AreaDetails")
                                        areaArrayList = jobjt.getJSONArray("AreaDetailsList")

                                        if (areaArrayList.length() > 0) {
//                                            if (postDet == 0){
//                                                postDet++
                                            areaDetailPopup(areaArrayList)
//                                            }

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignTabActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun areaDetailPopup(areaArrayList: JSONArray) {

        try {

            dialogArea = Dialog(this)
            dialogArea!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogArea!!.setContentView(R.layout.area_list_popup)
            dialogArea!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recycArea = dialogArea!!.findViewById(R.id.recycArea) as RecyclerView
            val etsearch = dialogArea!!.findViewById(R.id.etsearch) as EditText

            areaSort = JSONArray()
            for (k in 0 until areaArrayList.length()) {
                val jsonObject = areaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                areaSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignTabActivity, 1)
            recycArea!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@ServiceAssignTabActivity, areaArrayList)
            val adapter = AreaDetailAdapter(this@ServiceAssignTabActivity, areaSort)
            recycArea!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignTabActivity)

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
                    val adapter = AreaDetailAdapter(this@ServiceAssignTabActivity, areaSort)
                    recycArea!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignTabActivity)
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
            //   val jsonObject = branchArrayList.getJSONObject(position)
            val jsonObject = branchsort.getJSONObject(position)
            Log.e(TAG, "ID_Branch   " + jsonObject.getString("ID_Branch"))
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))

            temp_ID_Branch = jsonObject.getString("ID_Branch")
            temp_Branch = jsonObject.getString("BranchName")

            ID_Employee = ""
            tie_Employee!!.setText("")
            temp_ID_Employee = ""
            temp_Employee = ""

        }

        if (data.equals("employeeAll")){
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))

            temp_ID_Employee = jsonObject.getString("ID_Employee")
            temp_Employee = jsonObject.getString("EmpName")

        }

        if (data.equals("areadetail")) {
            dialogArea!!.dismiss()
            val jsonObject = areaSort.getJSONObject(position)
            Log.e(TAG, "jsonObject  5465    " + jsonObject)

            FK_Area = jsonObject.getString("FK_Area")
            tie_Area!!.setText(jsonObject.getString("Area"))




        }
    }

//    private fun setOnFocusChangeListener(textView: TextView) {
//        textView.setOnFocusChangeListener(object : View.OnFocusChangeListener {
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                if (!hasFocus) {
//
//                }
//            }
//        })
//    }




    override fun onFocusChange(v: View, hasFocus: Boolean) {

        Log.e(TAG,"9261  "+v.id)
        if (v.id === R.id.tie_Customer){
            Log.e(TAG,"92613  "+v.id)
        }
        if (v.id === R.id.tie_Mobile){
            Log.e(TAG,"92614  "+v.id)
        }

    }


    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)
            Log.e(TAG,"89921    ")
            Log.e(TAG,"92615  ")



        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {
                editable === tie_Branch!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_Branch!!.isErrorEnabled = false
                }
                editable === tie_Employee!!.editableText -> {
                    Log.e(TAG,"283021    ")
                  //  til_CustomerName!!.isErrorEnabled = false
                }
                editable === tie_FromDate!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_FromDate!!.isErrorEnabled = false
//                    if (tie_MobileNo!!.text!!.length > 9 ){
//                        til_MobileNo!!.isErrorEnabled = false
//                    }

                }
                editable === tie_ToDate!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    til_ToDate!!.isErrorEnabled = false
                    //til_Address!!.isErrorEnabled = false
                }
                editable === tie_Area!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    //til_Priority!!.isErrorEnabled = false
                }


                editable === tie_Customer!!.editableText -> {
                    Log.e(TAG,"283022    ")
                    Log.e(TAG,"89922    ")
                    //til_Category!!.isErrorEnabled = false
                    temp_Customer  = tie_Customer!!.text.toString()
                }


                editable === tie_Mobile!!.editableText -> {
                    Log.e(TAG,"283022    ")
                   // til_Product!!.isErrorEnabled = false
                    temp_Mobile  = tie_Mobile!!.text.toString()
                }

                editable === tie_TicketNumber!!.editableText -> {
                    Log.e(TAG,"283022    ")
                   // til_Service!!.isErrorEnabled = false
                    temp_TicketNo  = tie_TicketNumber!!.text.toString()
                }
                editable === tie_DueDays!!.editableText -> {
                    Log.e(TAG,"283022    ")
                  //  til_Complaint!!.isErrorEnabled = false
                    temp_DueDays  = tie_DueDays!!.text.toString()
                }

            }

        }
    }


    override fun onRestart() {
        super.onRestart()
        serviceCount = 0
        getserviceCounts()
    }



}