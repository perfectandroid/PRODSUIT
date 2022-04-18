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
import com.perfect.prodsuit.View.Adapter.DepartmentAdapter
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.FollowupActionAdapter
import com.perfect.prodsuit.Viewmodel.DepartmentViewModel
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.FollowUpActionViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class LeadNewActionActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {
    val TAG : String = "LeadNewActionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var llProject: LinearLayout? = null
    private var llAction: LinearLayout? = null
    private var llFollowUpDate: LinearLayout? = null
    private var llDepartment: LinearLayout? = null
    private var llEmployee: LinearLayout? = null
    private var ll_Todate: LinearLayout? = null

    private var imDateclose: ImageView? = null
    var date_Picker1: DatePicker? = null

    private var txtAction: TextView? = null
    private var txtActionType: TextView? = null
    private var txtFollowUpDate: TextView? = null
    private var txtDepartment: TextView? = null
    private var txtEmployee: TextView? = null
    private var txtok1: TextView? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

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

    companion object{
        var ID_NextAction : String = ""
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

        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        setRegViews()
        ResetData()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llProject = findViewById<LinearLayout>(R.id.llProject)
        llAction = findViewById<LinearLayout>(R.id.llAction)
        llFollowUpDate = findViewById<LinearLayout>(R.id.llFollowUpDate)
        llDepartment = findViewById<LinearLayout>(R.id.llDepartment)
        llEmployee = findViewById<LinearLayout>(R.id.llEmployee)
        ll_Todate = findViewById<LinearLayout>(R.id.ll_Todate)

        imDateclose = findViewById<ImageView>(R.id.imDateclose)
        date_Picker1 = findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1!!.minDate = Calendar.getInstance().timeInMillis

        txtAction = findViewById<TextView>(R.id.txtAction)
        txtFollowUpDate = findViewById<TextView>(R.id.txtFollowUpDate)
        txtDepartment = findViewById<TextView>(R.id.txtDepartment)
        txtEmployee = findViewById<TextView>(R.id.txtEmployee)
        txtok1 = findViewById<TextView>(R.id.txtok1)

        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

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
        dateMode = "1"
        ID_Department = ""
        ID_Employee = ""
        strDate = ""

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
                employeeViewModel.getEmployee(this, LeadGenerationActivity.ID_Department)!!.observe(
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
}