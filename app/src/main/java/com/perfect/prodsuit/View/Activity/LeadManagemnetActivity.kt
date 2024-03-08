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
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.AgendaCountViewModel
import com.perfect.prodsuit.Viewmodel.BranchViewModel
import com.perfect.prodsuit.Viewmodel.EmpByBranchViewModel
import com.perfect.prodsuit.Viewmodel.LeadAllDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LeadManagemnetActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "LeadManagemnetActivity"
    private var progressDialog: ProgressDialog? = null
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
    private var ID_Employee = ""
    private var ID_Employee1 = ""
    private var ID_employee2 = ""
    private var emp_name = ""
    private var emp_name2 = ""
    private var UserName = ""
    private var chipNavigationBar: ChipNavigationBar? = null
    private var lltodolist: LinearLayout? = null
    private var lloverdue: LinearLayout? = null
    private var lloverUpcoming: LinearLayout? = null
    private var llmyLead: LinearLayout? = null
    private var tie_Employee: TextInputEditText? = null
    private var tie_Branch: TextInputEditText? = null
    lateinit var employeeArrayList : JSONArray
    private var dialogEmployeeAllDetails : Dialog? = null
    private var recyEmployeeAllDetails : RecyclerView? = null
    lateinit var employeeAllDetailSort : JSONArray
    var leadDetailsAll = 0

    private var ID_Branch = ""

    private var ID_Branch1 = ""
    private var branchName1 = ""

    private var ID_Branch2 = ""
    private var branchName2 = ""



    private var tv_todo_count: TextView? = null
    private var tv_overdue_count: TextView? = null
    private var tv_upcoming_count: TextView? = null
    private var tv_my_leadCount: TextView? = null
    private var imgv_filterManage: ImageView? = null

    private var tv_today: TextView? = null
    private var tv_pending: TextView? = null
    private var tv_upcoming: TextView? = null
    private var tv_mylead: TextView? = null

    lateinit var context: Context

    lateinit var agendaCountViewModel: AgendaCountViewModel
    lateinit var leadAllDetailsViewModel: LeadAllDetailsViewModel

    lateinit var branchViewModel: BranchViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var branchsort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null
    var branch = 0

    var empUseBranch = 0
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    lateinit var employeeAllArrayList: JSONArray
    lateinit var employeeAllSort: JSONArray
    private var dialogEmployeeAll: Dialog? = null
    var recyEmployeeAll: RecyclerView? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_leadmanagement)
        setRegViews()
        context = this@LeadManagemnetActivity
        agendaCountViewModel = ViewModelProvider(this).get(AgendaCountViewModel::class.java)
        leadAllDetailsViewModel = ViewModelProvider(this).get(LeadAllDetailsViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)

        bottombarnav()
        getCalendarId(context)
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        ID_Employee1 = FK_EmployeeSP.getString("FK_Employee", null).toString()

        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        UserName = UserNameSP.getString("UserName", null).toString()

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()

        ID_Branch1 = FK_BranchSP.getString("FK_Branch", null).toString()
        branchName1 = BranchSP.getString("BranchName", null).toString()

        Log.e(TAG,"")
        getCounts()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        lltodolist = findViewById(R.id.lltodolist)
        lloverdue = findViewById(R.id.lloverdue)
        lloverUpcoming = findViewById(R.id.lloverUpcoming)
        llmyLead = findViewById(R.id.llmyLead)
        imgv_filterManage = findViewById(R.id.imgv_filterManage)

        tv_todo_count = findViewById(R.id.tv_todo_count)
        tv_overdue_count = findViewById(R.id.tv_overdue_count)
        tv_upcoming_count = findViewById(R.id.tv_upcoming_count)
        tv_my_leadCount = findViewById(R.id.tv_my_leadCount)

        tv_today = findViewById(R.id.tv_today)
        tv_pending = findViewById(R.id.tv_pending)
        tv_upcoming = findViewById(R.id.tv_upcoming)
        tv_mylead = findViewById(R.id.tv_mylead)


        lltodolist!!.setOnClickListener(this)
        lloverdue!!.setOnClickListener(this)
        lloverUpcoming!!.setOnClickListener(this)
        llmyLead!!.setOnClickListener(this)
        imgv_filterManage!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.lltodolist->{
                val i = Intent(this@LeadManagemnetActivity, TodoListActivity::class.java)
                i.putExtra("SubMode","1")
                i.putExtra("headerTitle",tv_today!!.text.toString())
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("EmpName",emp_name)
                i.putExtra("UserName",UserName)
                Log.e(TAG,"88888    "+ID_Employee)
                Log.e(TAG,"456321    "+UserName)
//                i.putExtra("EmpName",)
                startActivity(i)
            }
            R.id.lloverdue->{
                val i = Intent(this@LeadManagemnetActivity, OverDueActivity::class.java)
                i.putExtra("SubMode","2")
                i.putExtra("headerTitle",tv_pending!!.text.toString())
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("EmpName",emp_name)
                i.putExtra("UserName",UserName)
                Log.e(TAG,"606060    "+emp_name)
                Log.e(TAG,"606060    "+ID_Employee)
                startActivity(i)
            }
            R.id.lloverUpcoming->{
                val i = Intent(this@LeadManagemnetActivity, UpcomingtaskActivity::class.java)
                i.putExtra("SubMode","3")
                i.putExtra("headerTitle",tv_upcoming!!.text.toString())
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("EmpName",emp_name)
                i.putExtra("UserName",UserName)
                Log.e(TAG,"88552233    "+emp_name)
                startActivity(i)
            }
            R.id.llmyLead->{
                val i = Intent(this@LeadManagemnetActivity, MyLeadActivity::class.java)
                i.putExtra("SubMode","4")
                i.putExtra("headerTitle",tv_mylead!!.text.toString())
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("EmpName",emp_name)
                i.putExtra("UserName",UserName)
                Log.e(TAG,"88552233    "+emp_name)
                startActivity(i)
            }
            R.id.imgv_filterManage->{
                filterBottomDataManagment()
//                getEmployeeAllD()
            }
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@LeadManagemnetActivity, HomeActivity::class.java)
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
            /* val ll_ok = layout.findViewById(R.id.ll_ok) as LinearLayout
             val ll_cancel = layout.findViewById(R.id.ll_cancel) as LinearLayout
             etdate = layout.findViewById(R.id.etdate) as TextView
             ettime = layout.findViewById(R.id.ettime) as TextView
             val etdis = layout.findViewById(R.id.etdis) as EditText*/
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

    private fun filterBottomDataManagment() {

        try {
            val dialog = BottomSheetDialog(this)
            val layout1 = layoutInflater.inflate(R.layout.filter_manage, null)

            val ll_admin_staff = layout1.findViewById(R.id.ll_admin_staff) as LinearLayout

            val txtCancel = layout1.findViewById(R.id.txtCancel) as TextView
            val txtSubmit = layout1.findViewById(R.id.txtSubmit) as TextView
            val refresh = layout1.findViewById(R.id.refresh) as ImageView


            tie_Employee = layout1.findViewById(R.id.tie_Employee) as TextInputEditText
            tie_Branch = layout1.findViewById(R.id.tie_Branch) as TextInputEditText

            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin = IsAdminSP.getString("IsAdmin", null)

            val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
            var IsManager = IsManagerSP.getString("IsManager", null)

            if (isAdmin.equals("1") && IsManager.equals("0")) {
                ll_admin_staff!!.visibility = View.VISIBLE
//                tie_Branch!!.isEnabled = true
//                tie_Employee!!.isEnabled = true
            }
            else if (isAdmin.equals("0") && IsManager.equals("1")){
                ll_admin_staff!!.visibility = View.VISIBLE
//                tie_Branch!!.isEnabled = false
//                tie_Employee!!.isEnabled = true
            }else{
//                tie_Branch!!.isEnabled = false
//                tie_Employee!!.isEnabled = false
            }


//            Log.e(TAG, "isAdmin 796  " + isAdmin)
//            if (isAdmin.equals("1")) {
//                ll_admin_staff!!.visibility = View.VISIBLE
//                tie_Employee!!.isEnabled = true
//            }else{
//                tie_Employee!!.isEnabled = false
//            }

            val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
            val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
            val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
            val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

//            ID_Branch  = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
//            tie_Branch !!.setText( BranchNameSP.getString("BranchName", null))
//            ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
            if (emp_name.equals("")){

                tie_Employee!!.setText(UserName)
            }else{
                tie_Employee!!.setText(emp_name)
            }

            if (ID_employee2.equals("")){
                ID_Employee  =ID_Employee1
            }else{
                ID_Employee  =ID_employee2
            }

            if (ID_Branch2.equals("")){
                ID_Branch = ID_Branch1
            }else{
                ID_Branch = ID_Branch2
            }

            if (branchName2.equals("")){
                tie_Branch!!.setText(branchName1)
            }else{
                tie_Branch!!.setText(branchName2)
            }

            txtCancel.setOnClickListener {
                dialog.dismiss()
                ID_Branch2 = ""
                branchName2 = ""
                ID_employee2 = ""
                emp_name = ""
            }

            txtSubmit.setOnClickListener {
            //    dialog.dismiss()

                if (ID_Branch.equals("")){
                    Toast.makeText(applicationContext, "Select Branch", Toast.LENGTH_SHORT).show()

                }else if (ID_Employee.equals("")){
                    Toast.makeText(applicationContext, "Select Employee", Toast.LENGTH_SHORT).show()
                }else{
                    dialog.dismiss()

                    getCounts()
                }

//                if (ID_employee2.equals("")){
//                    Toast.makeText(applicationContext, "Select Employee", Toast.LENGTH_SHORT).show()
//
//                }
//
//                else{

//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//
//                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//                UserName = UserNameSP.getString("UserName", null).toString()

                    Log.e(TAG,"002  "+ID_Employee)
                    Log.e(TAG,"927  "+emp_name)
                 /*   dialog.dismiss()
                    getCounts()*/
//                }

            }

            refresh.setOnClickListener {
//                dialog.dismiss()

                ID_Branch2 = ""
                branchName2 = ""

                ID_Branch = ID_Branch1
                tie_Branch!!.setText(branchName1)

                    ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
                    emp_name2 = ""
                    ID_employee2 = ""
                    val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
                    tie_Employee!!.setText(UserNameSP.getString("UserName", null))
                    Log.e(TAG,"002  "+ID_Employee)
//                getCounts()
            }


//            ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
//            tie_Branch!!.setText(BranchNameSP.getString("BranchName", null))
//            ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
//            tie_Employee!!.setText(UserNameSP.getString("UserName", null))
//            ID_Lead_Details = ""
//            tie_LeadDetails!!.setText("")
//            tie_LeadValue!!.setText("")
//            til_LeadValue!!.setHint("")

            tie_Branch!!.setOnClickListener(View.OnClickListener {
                Config.disableClick(it)

                if (isAdmin.equals("1") && IsManager.equals("0")) {
                    branch = 0
                    getBranch()
                }

            })


            tie_Employee!!.setOnClickListener(View.OnClickListener {
                Config.disableClick(it)
//                ID_Employee = ""
//                leadDetailsAll = 0
//                getEmployeeAllD()
                Log.e(TAG," 796   tie_Employee"+ID_Employee)


                if (isAdmin.equals("1") && IsManager.equals("0")) {
                    empUseBranch = 0
                    getEmpByBranch(0)
                }
                else if (isAdmin.equals("0") && IsManager.equals("1")){
                    empUseBranch = 0
                    getEmpByBranch(0)
                }

            })





            dialog.setCancelable(false)
            dialog!!.setContentView(layout1)
            dialog.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }

    }


    private fun getBranch() {
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
                                        this@LeadManagemnetActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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


            val lLayout = GridLayoutManager(this@LeadManagemnetActivity, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@TicketReportActivity, branchArrayList)
            val adapter = BranchAdapter(this@LeadManagemnetActivity, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@LeadManagemnetActivity)

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
                    val adapter = BranchAdapter(this@LeadManagemnetActivity, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@LeadManagemnetActivity)
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
                startActivity(Intent(this@LeadManagemnetActivity, WelcomeActivity::class.java))
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


    private fun getCounts() {
        var countAgenda = 0
        Log.e(TAG,"455521      " + ID_Employee)
        Log.e(TAG,"552255      " + UserName)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaCountViewModel.getAgendaCount(this,ID_Branch,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            progressDialog!!.dismiss()

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   167   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("PendingCountDetails")
                                tv_todo_count!!.setText(jobjt.getString("Todolist"))
                                tv_overdue_count!!.setText(jobjt.getString("OverDue"))
                                tv_upcoming_count!!.setText(jobjt.getString("UpComingWorks"))
                                tv_my_leadCount!!.setText(jobjt.getString("MyLeads"))

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadManagemnetActivity,
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
                            progressDialog!!.dismiss()
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

    private fun getEmployeeAllD() {
        var countAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadAllDetailsViewModel.getLeadAllDetails(this,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            progressDialog!!.dismiss()
                            if (leadDetailsAll == 0) {
                                leadDetailsAll++

                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   167   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("EmployeeAllDetails")
                                    employeeArrayList = jobjt.getJSONArray("EmployeeAllDetailsList")
                                    if (employeeArrayList.length() > 0) {
                                        Log.e("TAG", "msg   1052   " + msg)

                                        employeeAllPopup(employeeArrayList)
//                                    customerSearchPopup(employeeArrayList)
                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@LeadManagemnetActivity,
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
                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun employeeAllPopup(employeeAllArrayList: JSONArray) {
        try {

            dialogEmployeeAllDetails = Dialog(this)
            dialogEmployeeAllDetails!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAllDetails!! .setContentView(R.layout.employeealldetails_popup)
            dialogEmployeeAllDetails!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAllDetails = dialogEmployeeAllDetails!! .findViewById(R.id.recyEmployeeAllDetails) as RecyclerView
            val etsearch = dialogEmployeeAllDetails!! .findViewById(R.id.etsearch) as EditText


            employeeAllDetailSort = JSONArray()
            for (k in 0 until employeeAllArrayList.length()) {
                val jsonObject = employeeAllArrayList.getJSONObject(k)
                employeeAllDetailSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@LeadManagemnetActivity, 1)
            recyEmployeeAllDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = EmployeeAllDetailAdapter(this@LeadManagemnetActivity, employeeAllDetailSort)
            recyEmployeeAllDetails!!.adapter = adapter
            adapter.setClickListener(this@LeadManagemnetActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    val textlength = etsearch!!.text.length
                    employeeAllDetailSort = JSONArray()

                    for (k in 0 until employeeAllArrayList.length()) {
                        val jsonObject = employeeAllArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeAllDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllDetailSort               7103    "+employeeAllDetailSort)
                    val adapter = EmployeeAllDetailAdapter(this@LeadManagemnetActivity, employeeAllDetailSort)
                    recyEmployeeAllDetails!!.adapter = adapter
                    adapter.setClickListener(this@LeadManagemnetActivity)
                }
            })

            dialogEmployeeAllDetails!!.show()
            dialogEmployeeAllDetails!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"741  onRestart ")
        getCounts()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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
                                            this@LeadManagemnetActivity,
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

            if (i == 0) {
                val lLayout = GridLayoutManager(this@LeadManagemnetActivity, 1)
                recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
                val adapter = EmployeeAllAdapter(this@LeadManagemnetActivity, employeeAllSort)
                recyEmployeeAll!!.adapter = adapter
                adapter.setClickListener(this@LeadManagemnetActivity)
            } else {
                val lLayout = GridLayoutManager(this@LeadManagemnetActivity, 1)
                recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
                val adapter = AssignedListAdapter(this@LeadManagemnetActivity, employeeAllSort)
                recyEmployeeAll!!.adapter = adapter
                adapter.setClickListener(this@LeadManagemnetActivity)
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

                    Log.e(TAG, "employeeAllSort               7103    " + employeeAllSort)
                    val adapter = EmployeeAllAdapter(this@LeadManagemnetActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@LeadManagemnetActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
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

            ID_Branch2 = jsonObject.getString("ID_Branch")
            branchName2= jsonObject.getString("BranchName")

            emp_name2 = ""
            ID_employee2 = ""

            ID_Employee = ""
            tie_Employee!!.setText("")

        }

        if (data.equals("employeeAll")) {
            dialogEmployeeAll!!.dismiss()
//            val jsonObject = employeeAllArrayList.getJSONObject(position)
            val jsonObject = employeeAllSort.getJSONObject(position)

            tie_Employee!!.setText(jsonObject.getString("EmpName"))
            ID_Employee = jsonObject.getString("ID_Employee")
            emp_name = jsonObject.getString("EmpName")
            ID_employee2 = jsonObject.getString("ID_Employee")
            Log.e(TAG,"iddddd "+jsonObject.getString("ID_Employee"))


        }

        if (data.equals("EmpName")){
            dialogEmployeeAllDetails!!.dismiss()
            val jsonObject = employeeAllDetailSort.getJSONObject(position)
//            Toast.makeText(this, ""+jsonObject.getString("EmpName"), Toast.LENGTH_SHORT).show()
            tie_Employee!!.setText(jsonObject.getString("EmpName"))
            ID_Employee = jsonObject.getString("ID_Employee")
            emp_name = jsonObject.getString("EmpName")
            ID_employee2 = jsonObject.getString("ID_Employee")
            Log.e(TAG,"iddddd "+jsonObject.getString("ID_Employee"))
        }

    }




}