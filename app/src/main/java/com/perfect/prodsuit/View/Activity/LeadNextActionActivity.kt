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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class LeadNextActionActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "LeadNextActionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null


    private var tie_Action: TextInputEditText? = null
    private var tie_ActionType: TextInputEditText? = null
    private var tie_FollowDate: TextInputEditText? = null
    private var tie_LeadType: TextInputEditText? = null
    private var tie_Department: TextInputEditText? = null
    private var tie_Employee: TextInputEditText? = null



    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var departmentViewModel: DepartmentViewModel
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var saveNextActionViewModel: SaveNextActionViewModel

    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpTypeArrayList : JSONArray
    lateinit var prodPriorityArrayList : JSONArray
    lateinit var departmentArrayList : JSONArray
    lateinit var employeeArrayList : JSONArray

    private var dialogFollowupAction : Dialog? = null
    private var dialogFollowupType : Dialog? = null
    private var dialogProdPriority : Dialog? = null
    private var dialogDepartment : Dialog? = null
    private var dialogEmployee : Dialog? = null

    var recyFollowupAction: RecyclerView? = null
    var recyFollowupType: RecyclerView? = null
    var recyProdPriority: RecyclerView? = null
    var recyDeaprtment: RecyclerView? = null
    var recyEmployee: RecyclerView? = null

    companion object{
        var ID_NextAction : String = ""
        var ID_ActionType : String = ""
        var dateMode : String?= "1"  // GONE
        var ID_Priority : String?= ""
        var ID_Department : String = ""
        var ID_Employee : String = ""
        var strDate : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lead_next_action)
        context = this@LeadNextActionActivity

        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        saveNextActionViewModel = ViewModelProvider(this).get(SaveNextActionViewModel::class.java)

        setRegViews()
        ResetData()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_Action = findViewById<TextInputEditText>(R.id.tie_Action)
        tie_ActionType = findViewById<TextInputEditText>(R.id.tie_ActionType)
        tie_FollowDate = findViewById<TextInputEditText>(R.id.tie_FollowDate)
        tie_LeadType = findViewById<TextInputEditText>(R.id.tie_LeadType)
        tie_Department = findViewById<TextInputEditText>(R.id.tie_Department)
        tie_Employee = findViewById<TextInputEditText>(R.id.tie_Employee)




        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        tie_Action!!.setOnClickListener(this)
        tie_ActionType!!.setOnClickListener(this)
        tie_FollowDate!!.setOnClickListener(this)
        tie_LeadType!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

    }

    private fun ResetData() {

        ID_NextAction = ""
        ID_NextAction = ""
        dateMode = "1"
        ID_Priority = ""
        ID_Department = ""
        ID_Employee = ""
        strDate = ""

        tie_Action!!.setText("")
        tie_ActionType!!.setText("")
        tie_FollowDate!!.setText("")
        tie_LeadType!!.setText("")
        tie_Department!!.setText("")
        tie_Employee!!.setText("")
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Action->{
                getFollowupAction()
            }
            R.id.tie_ActionType->{
                getFollowupType()
            }
            R.id.tie_FollowDate->{
//                DateType = 0
                openBottomSheet()
            }

            R.id.tie_LeadType->{
                getProductPriority()
            }
            R.id.tie_Department->{
                getDepartment()
            }
            R.id.tie_Employee->{
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

                validations(v)
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
                                    this@LeadNextActionActivity,
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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNextActionActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = FollowupActionAdapter(this@LeadNextActionActivity, followUpActionArrayList)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@LeadNextActionActivity)

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
                                    this@LeadNextActionActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNextActionActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = FollowupTypeAdapter(this@LeadNextActionActivity, followUpTypeArrayList)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@LeadNextActionActivity)

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                                    this@LeadNextActionActivity,
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

            val lLayout = GridLayoutManager(this@LeadNextActionActivity, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ProductPriorityAdapter(this@LeadNextActionActivity, prodPriorityArrayList)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@LeadNextActionActivity)

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                                    this@LeadNextActionActivity,
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

    private fun departmentPopup(departmentArrayList: JSONArray) {
        try {

            dialogDepartment = Dialog(this)
            dialogDepartment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDepartment!! .setContentView(R.layout.department_popup)
            dialogDepartment!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyDeaprtment = dialogDepartment!! .findViewById(R.id.recyDeaprtment) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNextActionActivity, 1)
            recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = DepartmentAdapter(this@LeadNextActionActivity, departmentArrayList)
            recyDeaprtment!!.adapter = adapter
            adapter.setClickListener(this@LeadNextActionActivity)

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
                                    this@LeadNextActionActivity,
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

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!! .setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!! .findViewById(R.id.recyEmployee) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadNextActionActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = EmployeeAdapter(this@LeadNextActionActivity, employeeArrayList)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@LeadNextActionActivity)

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
            tie_Action!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("followuptype")){
            dialogFollowupType!!.dismiss()
            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))


        }
        if (data.equals("prodpriority")){
            dialogProdPriority!!.dismiss()
            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            tie_LeadType!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("department")){
            dialogDepartment!!.dismiss()
            val jsonObject = departmentArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
            ID_Department = jsonObject.getString("ID_Department")
            tie_Department!!.setText(jsonObject.getString("DeptName"))


        }

        if (data.equals("employee")){
            dialogEmployee!!.dismiss()
            val jsonObject = employeeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmpName"))


        }
    }

    private fun validations(v: View) {

        if (ID_NextAction.equals("")){
            Config.snackBars(context,v,"Select Action")
        }
        else if (ID_ActionType.equals("")){
            Config.snackBars(context,v,"Select Action Type")
        }
        else if (strDate.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if (ID_Priority.equals("")){
            Config.snackBars(context,v,"Select Lead")
        }
        else if (ID_Department.equals("")){
            Config.snackBars(context,v,"Select Department")
        }
        else if (ID_Employee.equals("")){
            Config.snackBars(context,v,"Select Employee")
        }
        else{
            Log.e(TAG,"SAVE NEXT ACTION"
                    +"\n"+"ID_NextAction   : "+ID_NextAction
                    +"\n"+"ID_ActionType   : "+ID_ActionType
                    +"\n"+"strDate         : "+strDate
                    +"\n"+"ID_Priority     : "+ID_Priority
                    +"\n"+"ID_Department   : "+ID_Department
                    +"\n"+"ID_Employee     : "+ID_Employee)

          //  Config.snackBars(context,v,"Save API not implement")

            saveNextAction()
        }
    }

    private fun saveNextAction() {
        var saveNexr = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                saveNextActionViewModel.saveNextAction(this, ID_NextAction,ID_ActionType,strDate,ID_Priority!!,ID_Department,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message

                        Log.e(TAG,"msg   1224   "+msg)
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   12241   "+msg)
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
                                    this@LeadNextActionActivity,
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

    private fun openBottomSheet() {
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

                tie_FollowDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                strDate = strDay+"-"+strMonth+"-"+strYear



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

}

