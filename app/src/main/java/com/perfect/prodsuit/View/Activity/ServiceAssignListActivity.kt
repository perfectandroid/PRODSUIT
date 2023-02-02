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
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.View.Adapter.DepartmentAdapter
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.FollowupActionAdapter
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.FollowUpActionViewModel
import com.perfect.prodsuit.Viewmodel.ServiceListViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceAssignListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG  ="ServiceAssignListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var serviceListViewModel: ServiceListViewModel
    lateinit var serviceListArrayList: JSONArray
    lateinit var serviceListSort: JSONArray
    var recyServiceList: RecyclerView? = null
    private var tv_listCount: TextView? = null
    private var imgv_filter: ImageView? = null

    var serviceList = 0

    var SubMode : String?= ""
    var ID_Branch : String?= ""
    var FK_Area : String?= ""
    var ID_Employee : String?= ""
    var strFromDate : String?= ""
    var strToDate : String?= ""
    var strCustomer : String?= ""
    var strMobile : String?= ""
    var strTicketNo : String?= ""
    var strDueDays : String?= ""

    private var til_Status: TextInputLayout? = null
    private var til_AttendedBy: TextInputLayout? = null

    private var tie_Status: TextInputEditText? = null
    private var tie_AttendedBy: TextInputEditText? = null

    private var tv_Ticket: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_Name: TextView? = null
    private var tv_Complaint: TextView? = null
    private var txtReset: TextView? = null
    private var txtUpdate: TextView? = null

    private var tie_TicketNumber: TextInputEditText? = null
    private var tie_Branch: TextInputEditText? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_DueDays: TextInputEditText? = null

    private var txtFilterReset: TextView? = null
    private var txtFilterSearch: TextView? = null

    var filterTicketNumber: String? = ""
    var filterBranch : String? = ""
    var filterCustomer : String? = ""
    var filterMobile : String? = ""
    var filterArea   : String? = ""
    var filterDueDays : String? = ""


    var statusCount = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    var attendCount = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null

    var ReqMode: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign_list)
        context = this@ServiceAssignListActivity
        serviceListViewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)

        setRegViews()

        SubMode   = intent.getStringExtra("SubMode")
        ID_Branch   = intent.getStringExtra("ID_Branch")
        FK_Area     = intent.getStringExtra("FK_Area")
        ID_Employee = intent.getStringExtra("ID_Employee")
        strFromDate = intent.getStringExtra("strFromDate")
        strToDate   = intent.getStringExtra("strToDate")
        strCustomer = intent.getStringExtra("strCustomer")
        strMobile   = intent.getStringExtra("strMobile")
        strTicketNo = intent.getStringExtra("strTicketNo")
        strDueDays  = intent.getStringExtra("strDueDays")


        serviceList = 0
        getServiceNewList()

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)
        recyServiceList!!.adapter = null
        tv_listCount = findViewById<TextView>(R.id.tv_listCount)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Status->{
                Config.disableClick(v)
                statusCount = 0
                ReqMode = "17"
                getStatus("2")
            }

            R.id.tie_AttendedBy->{
                Config.disableClick(v)
                attendCount = 0
                getChannelEmp()
            }
            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomSheet()

            }


        }
    }

    private fun filterBottomSheet() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.sal_filter_local, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            tie_TicketNumber    = view.findViewById<TextInputEditText>(R.id.tie_TicketNumber)
            tie_Branch          = view.findViewById<TextInputEditText>(R.id.tie_Branch)
            tie_Customer        = view.findViewById<TextInputEditText>(R.id.tie_Customer)
            tie_Mobile          = view.findViewById<TextInputEditText>(R.id.tie_Mobile)
            tie_Area            = view.findViewById<TextInputEditText>(R.id.tie_Area)
            tie_DueDays         = view.findViewById<TextInputEditText>(R.id.tie_DueDays)

            txtFilterReset = view.findViewById<TextView>(R.id.txtFilterReset)
            txtFilterSearch = view.findViewById<TextView>(R.id.txtFilterSearch)

            tie_TicketNumber!!.setText(""+filterTicketNumber)
            tie_Branch!!.setText(""+filterBranch)
            tie_Customer!!.setText(""+filterCustomer)
            tie_Mobile!!.setText(""+filterMobile)
            tie_Area!!.setText(""+filterArea)
            tie_DueDays!!.setText(""+filterDueDays)

            txtFilterSearch!!.setOnClickListener {

                filterTicketNumber = tie_TicketNumber!!.text!!.toString().toLowerCase().trim()
                filterBranch       = tie_Branch!!.text!!.toString().toLowerCase().trim()
                filterCustomer     = tie_Customer!!.text!!.toString().toLowerCase().trim()
                filterMobile       = tie_Mobile!!.text!!.toString().toLowerCase().trim()
                filterArea         = tie_Area!!.text!!.toString().toLowerCase().trim()
                filterDueDays      = tie_DueDays!!.text!!.toString().toLowerCase().trim()

                serviceListSort = JSONArray()

//                        || jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strCustomer)
//                        || jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(strMobile)
//                        || jsonObject.getString("Area")!!.toLowerCase().trim().contains(strArea)
//                        || jsonObject.getString("Due")!!.toLowerCase().trim().contains(strDueDays)
                for (k in 0 until serviceListArrayList.length()) {
                    val jsonObject = serviceListArrayList.getJSONObject(k)
                  //  if (textlength <= jsonObject.getString("TicketNo").length) {
                        if ((jsonObject.getString("TicketNo")!!.toLowerCase().trim().contains(filterTicketNumber!!))
                            && (jsonObject.getString("Branch")!!.toLowerCase().trim().contains(filterBranch!!))
                            && (jsonObject.getString("Customer")!!.toLowerCase().trim().contains(filterCustomer!!))
                            && (jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(filterMobile!!))
                            && (jsonObject.getString("Area")!!.toLowerCase().trim().contains(filterArea!!))
                            && (jsonObject.getString("Due")!!.toLowerCase().trim().contains(filterDueDays!!))){
                         //   Log.e(TAG,"2161    "+strTicketNumber+"   "+strCustomer)
                            serviceListSort.put(jsonObject)
                        }else{
                          //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                        }

                   // }
                }
                dialog1.dismiss()
                val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListSort,SubMode!!)
                recyServiceList!!.adapter = adapter
                adapter.setClickListener(this@ServiceAssignListActivity)

                tv_listCount!!.setText(""+serviceListSort.length())

            }

            txtFilterReset!!.setOnClickListener {
                tie_TicketNumber!!.setText("")
                tie_Branch!!.setText("")
                tie_Customer!!.setText("")
                tie_Mobile!!.setText("")
                tie_Area!!.setText("")
                tie_DueDays!!.setText("")
            }



//            ll_StafAdmin = view.findViewById<LinearLayout>(R.id.ll_StafAdmin)


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

            Log.e(TAG,"Exception  239   "+e.toString())
        }
    }

    private fun getServiceNewList() {
        recyServiceList!!.adapter = null
        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceListViewModel.getServiceList(this,SubMode!!,ID_Branch!!,FK_Area!!,ID_Employee!!,strFromDate!!,strToDate!!,strCustomer!!,strMobile!!,strTicketNo!!,strDueDays!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serviceList == 0) {
                                    serviceList++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAssignNewDetails")
                                        serviceListArrayList = jobjt.getJSONArray("ServiceAssignNewList")
                                        if (serviceListArrayList.length() > 0) {
                                            imgv_filter!!.visibility  =View.VISIBLE

                                            serviceListSort = JSONArray()
                                            for (k in 0 until serviceListArrayList.length()) {
                                                val jsonObject = serviceListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                serviceListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+serviceListSort.length())
                                            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
                                            recyServiceList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListSort,SubMode!!)
                                            recyServiceList!!.adapter = adapter
                                            adapter.setClickListener(this@ServiceAssignListActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("ServiceList")) {
            val jsonObject = serviceListArrayList.getJSONObject(position)
            val ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
            val i = Intent(this@ServiceAssignListActivity, ServiceAssignActivity::class.java)
            i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
            startActivity(i)
        }
        if (data.equals("ServiceEdit")) {

//            val i = Intent(this@ServiceAssignListActivity, ServiceAssignActivity::class.java)
//            startActivity(i)

            serviceEditBottom()
        }
        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
//            val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_Status = jsonObject.getString("ID_NextAction")
            tie_Status!!.setText(jsonObject.getString("NxtActnName"))
        }
        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))

            ID_AttendedBy = jsonObject.getString("ID_Employee")
            tie_AttendedBy!!.setText(jsonObject.getString("EmpName"))
        }




    }

    private fun serviceEditBottom() {
        try {


            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.sa_edit_bottom_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            tv_Ticket = view.findViewById<TextView>(R.id.tv_Ticket)
            tv_Customer = view.findViewById<TextView>(R.id.tv_Customer)
            tv_Name = view.findViewById<TextView>(R.id.tv_Name)
            tv_Customer = view.findViewById<TextView>(R.id.tv_Customer)
            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtUpdate = view.findViewById<TextView>(R.id.txtUpdate)

            til_Status = view.findViewById<TextInputLayout>(R.id.til_Status)
            til_AttendedBy = view.findViewById<TextInputLayout>(R.id.til_AttendedBy)

            tie_Status = view.findViewById<TextInputEditText>(R.id.tie_Status)
            tie_AttendedBy = view.findViewById<TextInputEditText>(R.id.tie_AttendedBy)

            tie_Status!!.addTextChangedListener(watcher);
            tie_AttendedBy!!.addTextChangedListener(watcher);

            tie_Status!!.setOnClickListener(this)
            tie_AttendedBy!!.setOnClickListener(this)
            resetEditdata()

            txtReset!!.setOnClickListener {
                resetEditdata()
            }
            txtUpdate!!.setOnClickListener {
                validateEdit(dialog1)
            }

            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    private fun validateEdit(dialog1: BottomSheetDialog) {

        if (ID_Status.equals("")){
            til_Status!!.setError("Select Status")
            til_Status!!.setErrorIconDrawable(null)
        }
        else if (ID_AttendedBy.equals("")){
            til_AttendedBy!!.setError("Select  Attended By")
            til_AttendedBy!!.setErrorIconDrawable(null)
        }
        else{
            dialog1.dismiss()
            Toast.makeText(applicationContext, "Successs", Toast.LENGTH_SHORT).show()
        }

    }

    private fun resetEditdata() {
        ID_Status = ""
        tie_Status!!.setText("")
        ID_AttendedBy = ""
        tie_AttendedBy!!.setText("")
    }

    private fun getStatus(statusSubmode: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this,statusSubmode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (statusCount == 0){
                                    statusCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   82   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length()>0){

                                            statusPopup(followUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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

    private fun statusPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!! .findViewById(R.id.etsearch) as EditText


            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@ServiceAssignListActivity, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignListActivity)


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
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpActionSort               7103    "+followUpActionSort)
                    val adapter = FollowupActionAdapter(this@ServiceAssignListActivity, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignListActivity)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
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
                                if (attendCount == 0) {
                                    attendCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceAssignListActivity,
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

            val lLayout = GridLayoutManager(this@ServiceAssignListActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@ServiceAssignListActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ServiceAssignListActivity)

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
                    val adapter = EmployeeAdapter(this@ServiceAssignListActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ServiceAssignListActivity)
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

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {
                editable === tie_Status!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_Status!!.isErrorEnabled = false
                }
                editable === tie_AttendedBy!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    til_AttendedBy!!.isErrorEnabled = false
                }

            }

        }
    }


}