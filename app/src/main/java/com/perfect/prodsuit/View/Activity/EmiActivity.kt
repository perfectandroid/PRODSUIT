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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmiTypeAdapter
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.Viewmodel.EmiTypeViewModel
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import org.json.JSONArray
import org.json.JSONObject

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

    private var temp_Customer: String = ""
    private var temp_Product: String = ""
    private var temp_EmiType: String = ""
    private var temp_ID_EmiType: String = ""
    private var temp_ID_CollectedBy: String = ""
    private var temp_CollectedBy: String = ""



    var ID_CollectedBy: String? = ""
    var ID_EmiType: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi)
        context = this@EmiActivity

        emiTypeViewModel = ViewModelProvider(this).get(EmiTypeViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        setRegViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filterManage = findViewById<ImageView>(R.id.imgv_filterManage)

        ll_todo = findViewById<LinearLayout>(R.id.ll_todo)
        ll_overdue = findViewById<LinearLayout>(R.id.ll_overdue)
        ll_upcoming = findViewById<LinearLayout>(R.id.ll_upcoming)

        imgv_filterManage!!.setOnClickListener(this)
        ll_todo!!.setOnClickListener(this)
        ll_overdue!!.setOnClickListener(this)
        ll_upcoming!!.setOnClickListener(this)

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
                val i = Intent(this@EmiActivity, EmiToDoListActivity::class.java)
                startActivity(i)
            }
            R.id.ll_overdue->{

            }
            R.id.ll_upcoming->{

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
        }
    }



    private fun filterBottomFilter() {
        try {
            val dialog = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.emi_count_filter_sheet, null)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog!!.setCanceledOnTouchOutside(false)

            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            tie_Customer= view.findViewById(R.id.tie_Customer) as TextInputEditText
            tie_Product= view.findViewById(R.id.tie_Product) as TextInputEditText
            tie_EmiType= view.findViewById(R.id.tie_EmiType) as TextInputEditText
            tie_CollectedBy= view.findViewById(R.id.tie_CollectedBy) as TextInputEditText

            tie_EmiType!!.setOnClickListener(this)
            tie_CollectedBy!!.setOnClickListener(this)


            tie_Customer!!.setText(temp_Customer)
            tie_Product!!.setText(temp_Product)
            ID_EmiType  = temp_ID_EmiType
            tie_EmiType!!.setText(temp_EmiType)
            ID_CollectedBy  = temp_ID_CollectedBy
            tie_CollectedBy!!.setText(temp_CollectedBy)

            txtSearch!!.setOnClickListener {

                temp_Customer = tie_Customer!!.text.toString()
                temp_Product = tie_Product!!.text.toString()
                temp_ID_EmiType  = ID_EmiType!!
                temp_EmiType  = tie_EmiType!!.text.toString()
                temp_ID_CollectedBy  = ID_CollectedBy!!
                temp_CollectedBy  = tie_CollectedBy!!.text.toString()
                dialog.dismiss()
            }

            txtReset!!.setOnClickListener {
                dialog.dismiss()
            }



            dialog.setCancelable(false)
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
                                    } else {
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

            val lLayout = GridLayoutManager(this@EmiActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
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
                                    } else {
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
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
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
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
    }

}