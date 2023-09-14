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
import androidx.lifecycle.Observer
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AssignedToAdapter
import com.perfect.prodsuit.Viewmodel.AssignedToWalkingViewModel
import com.perfect.prodsuit.Viewmodel.CreateWalkingCustomerViewModel
import com.perfect.prodsuit.Viewmodel.WalkExistViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WalkingCustomerActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG = "WalkingCustomerActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var til_CustomerName: TextInputLayout? = null
    private var til_Phone: TextInputLayout? = null
    private var til_AssignedTo: TextInputLayout? = null
    private var til_AssignedDate: TextInputLayout? = null
    private var til_Description: TextInputLayout? = null

    private var tie_CustomerName: TextInputEditText? = null
    private var tie_Phone: TextInputEditText? = null
    private var tie_AssignedTo: TextInputEditText? = null
    private var tie_AssignedDate: TextInputEditText? = null
    private var tie_Description: TextInputEditText? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    var dateSelectMode: Int = 0
    var strCustomer: String? = ""
    var strPhone: String? = ""
    var strAssignedDate: String? = ""
    var strDescription: String? = ""
    var ID_AssignedTo: String? = ""

    var assignedToCount: Int = 0
    lateinit var assignedToWalkingViewModel: AssignedToWalkingViewModel
    lateinit var assignedToList : JSONArray
    lateinit var assignedToSortList : JSONArray
    private var dialogassignedTo : Dialog? = null
    var recyassignedTo: RecyclerView? = null

    var saveCount: Int = 0
    lateinit var createWalkingCustomerViewModel: CreateWalkingCustomerViewModel
    var saveAttendanceMark = false

    var walkExistCount: Int = 0
    lateinit var walkExistViewModel: WalkExistViewModel
    lateinit var walkExistList : JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_walking_customer)
        assignedToWalkingViewModel = ViewModelProvider(this).get(AssignedToWalkingViewModel::class.java)
        createWalkingCustomerViewModel = ViewModelProvider(this).get(CreateWalkingCustomerViewModel::class.java)
        walkExistViewModel = ViewModelProvider(this).get(WalkExistViewModel::class.java)

        context = this@WalkingCustomerActivity
        setRegViews()
        defaultLoad()

        checkAttendance()

//        til_Phone!!.setEndIconOnClickListener {
//            Config.disableClick(it)
//            strPhone = tie_Phone!!.text.toString()
//
//            if (strPhone.equals("")){
//                Config.snackBars(context,it,"Enter Phone Number")
//            }else{
//                walkExistCount = 0
//                getExistingCustomerData()
//
//            }
//        }


    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_CustomerName = findViewById<TextInputEditText>(R.id.tie_CustomerName)
        tie_Phone = findViewById<TextInputEditText>(R.id.tie_Phone)
        tie_AssignedDate = findViewById<TextInputEditText>(R.id.tie_AssignedDate)
        tie_AssignedTo = findViewById<TextInputEditText>(R.id.tie_AssignedTo)
        tie_Description = findViewById<TextInputEditText>(R.id.tie_Description)

        til_CustomerName = findViewById<TextInputLayout>(R.id.til_CustomerName)
        til_Phone = findViewById<TextInputLayout>(R.id.til_Phone)
        til_AssignedDate = findViewById<TextInputLayout>(R.id.til_AssignedDate)
        til_AssignedTo = findViewById<TextInputLayout>(R.id.til_AssignedTo)
        til_Description = findViewById<TextInputLayout>(R.id.til_Description)



        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        tie_AssignedDate!!.setOnClickListener(this)
        tie_AssignedTo!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        til_CustomerName!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_AssignedDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_AssignedTo!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_AssignedTo->{
                Config.disableClick(v)
                assignedToCount = 0
                getAssignedTo()
            }

            R.id.tie_AssignedDate->{
                dateSelectMode = 0
                openBottomSheet()
            }

            R.id.btnReset->{
              resetData()
            }

            R.id.btnSubmit->{


                checkAttendance()
                if (saveAttendanceMark){
                    validation(v)
                }

            }
        }
    }

    private fun resetData() {

        tie_CustomerName!!.setText("")
        tie_Phone!!.setText("")
        tie_AssignedTo!!.setText("")
        tie_Description!!.setText("")
        ID_AssignedTo = ""
        defaultLoad()
    }

    private fun defaultLoad() {

        try {

            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val currentDate = sdf.format(Date())

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)

            tie_AssignedDate!!.setText(""+sdfDate1.format(newDate))

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        date_Picker1.setMinDate(System.currentTimeMillis());
        date_Picker1.minDate = System.currentTimeMillis()


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
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

                tie_AssignedDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    private fun getAssignedTo() {
        var ReqMode = "107"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                assignedToWalkingViewModel.getAssignedToWalking(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (assignedToCount == 0){
                                assignedToCount++
                                Log.e(TAG,"msg   2280   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    val jobjt = jObject.getJSONObject("EmployeeDetails")
                                    assignedToList = jobjt.getJSONArray("EmployeeDetailsList")
                                    if (assignedToList.length()>0){

                                        assignedToPopup(assignedToList)
                                    }
                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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

    private fun assignedToPopup(assignedToList: JSONArray) {

        try {

            dialogassignedTo = Dialog(this)
            dialogassignedTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogassignedTo!! .setContentView(R.layout.assignedto_popup)
            dialogassignedTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyassignedTo = dialogassignedTo!! .findViewById(R.id.recyassignedTo) as RecyclerView
            val etsearch = dialogassignedTo!! .findViewById(R.id.etsearch) as EditText

            assignedToSortList = JSONArray()
            for (k in 0 until assignedToList.length()) {
                val jsonObject = assignedToList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                assignedToSortList.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@WalkingCustomerActivity, 1)
            recyassignedTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = AssignedToAdapter(this@WalkingCustomerActivity, assignedToSortList)
            recyassignedTo!!.adapter = adapter
            adapter.setClickListener(this@WalkingCustomerActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    assignedToSortList = JSONArray()

                    for (k in 0 until assignedToList.length()) {
                        val jsonObject = assignedToList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                assignedToSortList.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"assignedToSortList               7103    "+assignedToSortList)
                    val adapter = AssignedToAdapter(this@WalkingCustomerActivity, assignedToSortList)
                    recyassignedTo!!.adapter = adapter
                    adapter.setClickListener(this@WalkingCustomerActivity)
                }
            })

            dialogassignedTo!!.show()
            dialogassignedTo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("AssignedToClick")) {
            dialogassignedTo!!.dismiss()
            val jsonObject = assignedToSortList.getJSONObject(position)

            tie_AssignedTo!!.setText(jsonObject!!.getString("EmpName"))
            ID_AssignedTo = jsonObject.getString("ID_Employee")
        }
    }

    private fun validation(v : View) {

        try {
            var assignDate = tie_AssignedDate!!.text.toString()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val newDate1: Date = sdf.parse(assignDate)
            val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
            strAssignedDate = sdfDate1.format(newDate1)
            strCustomer = tie_CustomerName!!.text.toString()
            strPhone = tie_Phone!!.text.toString()
            strDescription = tie_Description!!.text.toString()

            if (strCustomer.equals("")){
                Config.snackBars(context,v,"Enter Customer Name")
            }
            else if(ID_AssignedTo.equals("")){
                Config.snackBars(context,v,"Select Assigned To")
            }
            else if(assignDate.equals("")){
                Config.snackBars(context,v,"Select Assigned Date")
            }
            else{
                saveCount = 0
                saveWalkingCustomer()
            }
        }catch (e : Exception){

        }



    }

    private fun saveWalkingCustomer() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                createWalkingCustomerViewModel.CreateWalkingCustomer(this,strCustomer!!,strPhone!!,ID_AssignedTo!!,strAssignedDate!!,strDescription!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (saveCount == 0){
                                saveCount++
                                Log.e(TAG,"msg   4060   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    successPopup(jObject)
                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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

    private fun successPopup(jobjt: JSONObject) {
        try {

            val suceessDialog = Dialog(this)
            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            suceessDialog!!.setCancelable(false)
            suceessDialog!! .setContentView(R.layout.success_service_popup)
            suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            suceessDialog!!.setCancelable(false)

            val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
            val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView

            tv_succesmsg!!.setText(jobjt.getString("EXMessage"))

            tv_succesok!!.setOnClickListener {
                suceessDialog!!.dismiss()
                resetData()

            }

            suceessDialog!!.show()
            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }catch (e: Exception){

        }
    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance){
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status","")
            if (status.equals("0") || status.equals("")){
                Common.punchingRedirectionConfirm(this,"","")
            }
            else if (status.equals("1")){
                saveAttendanceMark = true
            }

        }else{
            saveAttendanceMark = true
        }
    }


    private fun getExistingCustomerData() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                walkExistViewModel.getWalkExist(this,strPhone!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (walkExistCount == 0){
                                walkExistCount++
                                Log.e(TAG,"msg   52777   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                   // successPopup(jObject)

//                                    val jobjt = jObject.getJSONObject("ExistCustomerDetails")
//                                    walkExistList = jobjt.getJSONArray("ExistCustomerDetailList")
                                    val i = Intent(this@WalkingCustomerActivity, WalkingExistingActivity::class.java)
                                    i.putExtra("jsonObject",jObject.toString())
                                    startActivity(i)

                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingCustomerActivity,
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