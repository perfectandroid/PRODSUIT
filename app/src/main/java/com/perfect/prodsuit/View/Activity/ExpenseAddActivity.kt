package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.ExpensetypelistModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ExpensetypeListAdapter
import com.perfect.prodsuit.Viewmodel.ExpenseAddViewModel
import com.perfect.prodsuit.Viewmodel.ExpenseTypeViewModel
import org.json.JSONObject
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAddActivity : AppCompatActivity() , View.OnClickListener {

    val TAG : String = "ExpenseAddActivity"
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
    var date_Picker: DatePicker? = null
    var txtok: TextView? = null
    var txtexpensetype: TextView? = null
    var etamount: TextView? = null
    var txtfromDate: TextView? = null
    var ll_Fromdate: LinearLayout? = null
    var llfromdate: LinearLayout? = null
    var llexpensetype: LinearLayout? = null
    var im_close: ImageView? = null
    var btnSubmit: Button? = null
    var btnReset: Button? = null

    var tie_Date: TextInputEditText? = null
    var tie_ExpenseType: TextInputEditText? = null
    var tie_ExpenseAmount: TextInputEditText? = null

    private var textlength = 0
    private var etxtsearch: EditText? =null
    private var list_view: ListView?=null
    private var array_sort =ArrayList<ExpensetypelistModel>()
    private var expensetypeArrayList = ArrayList<ExpensetypelistModel>()
    private var sadapter: ExpensetypeListAdapter? = null

    lateinit var context: Context
    lateinit var expenseTypeViewModel: ExpenseTypeViewModel
    lateinit var expenseAddViewModel: ExpenseAddViewModel

    private var strExpenseType           : String?                   = ""
    private var strExpenseTypeId        : String?                   = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_addexpense)
        setRegViews()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        tie_Date!!.setText(currentDate)
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        etamount = findViewById(R.id.etamount)
        im_close = findViewById(R.id.im_close)
        llexpensetype = findViewById(R.id.llexpensetype)
        txtexpensetype = findViewById(R.id.txtexpensetype)
        btnSubmit = findViewById(R.id.btnSubmit)
        llfromdate = findViewById(R.id.llfromdate)
        ll_Fromdate = findViewById(R.id.ll_Fromdate)
        txtfromDate = findViewById(R.id.txtfromDate)
        date_Picker = findViewById(R.id.date_Picker)
        txtok = findViewById(R.id.txtok)

        tie_Date = findViewById(R.id.tie_Date)
        tie_ExpenseType = findViewById(R.id.tie_ExpenseType)
        tie_ExpenseAmount = findViewById(R.id.tie_ExpenseAmount)


        tie_Date!!.setOnClickListener(this)
        tie_ExpenseType!!.setOnClickListener(this)
        tie_ExpenseAmount!!.setOnClickListener(this)

        llfromdate!!.setOnClickListener(this)
        btnReset = findViewById(R.id.btnReset)
        btnReset!!.setOnClickListener(this)
       // imclose!!.setOnClickListener(this)
        txtok!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        im_close!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        llexpensetype!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llfromdate->{
                ll_Fromdate!!.visibility=View.VISIBLE
            }
            R.id.im_close->{
                ll_Fromdate!!.visibility=View.GONE
            }

            R.id.btnReset->{
                reset()
            }

            R.id.btnSubmit->{
               // addExpense()
                validateDateExpense(v)
            }
            R.id.llexpensetype->{
                getExpenseType()
            }
            R.id.tie_Date->{
                openBottomSheet()
            }
            R.id.tie_ExpenseType->{
                getExpenseType()
            }
            R.id.tie_ExpenseAmount->{

            }
            R.id.txtok->{
                date_Picker!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }
                txtfromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                ll_Fromdate!!.visibility=View.GONE
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

                tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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

    private fun getExpenseType() {
        try {
            val builder = AlertDialog.Builder(this)
            val inflater1 =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reporttype_popup, null)
            list_view = layout.findViewById(R.id.list_view)
            etxtsearch  = layout.findViewById(R.id.etsearch)
            val tv_popuptitle = layout.findViewById(R.id.tv_popuptitle) as TextView
            tv_popuptitle.setText("Choose Expense Type")
            builder.setView(layout)
            val alertDialog = builder.create()
            getExpenseList(alertDialog)
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getExpenseList(dialog: AlertDialog) {
        try {
            context = this@ExpenseAddActivity
            expenseTypeViewModel = ViewModelProvider(this).get(ExpenseTypeViewModel::class.java)
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    expenseTypeViewModel.getExpenseType(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    var jobj = jObject.getJSONObject("ExpenseType")
                                    if (jobj.getString("ExpenseTypeList") == "null") {
                                    }
                                    else {


                                        val jarray = jobj.getJSONArray("ExpenseTypeList")
                                        array_sort = java.util.ArrayList<ExpensetypelistModel>()
                                        expensetypeArrayList = ArrayList<ExpensetypelistModel>()
                                        for (k in 0 until jarray.length()) {
                                            val jsonObject = jarray.getJSONObject(k)

                                            expensetypeArrayList.add(
                                                ExpensetypelistModel(
                                                    jsonObject.getString("ID_Expense"),
                                                    jsonObject.getString("ExpenseName")
                                                )
                                            )
                                            array_sort.add(
                                                ExpensetypelistModel(
                                                    jsonObject.getString("ID_Expense"),
                                                    jsonObject.getString("ExpenseName")
                                                )
                                            )
                                        }
                                        sadapter = ExpensetypeListAdapter(this@ExpenseAddActivity, array_sort)
                                        list_view!!.setAdapter(sadapter)
                                        list_view!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                                            Config.Utils.hideSoftKeyBoard(this@ExpenseAddActivity,view)
                                            array_sort.get(position).ExpenseName
                                            txtexpensetype!!.text = array_sort[position].ExpenseName
                                            tie_ExpenseType!!.setText(array_sort[position].ExpenseName)
                                            strExpenseType=array_sort[position].ExpenseName
                                            strExpenseTypeId=array_sort[position].ID_Expense
                                            dialog.dismiss()
                                        })
                                    }
                                    etxtsearch!!.addTextChangedListener(object : TextWatcher {
                                        override fun afterTextChanged(p0: Editable?) {
                                        }

                                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                        }

                                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                                            list_view!!.setVisibility(View.VISIBLE)
                                            textlength = etxtsearch!!.text.length
                                            array_sort.clear()
                                            for (i in expensetypeArrayList.indices) {
                                                if (textlength <= expensetypeArrayList[i].ExpenseName!!.length) {
                                                    if (expensetypeArrayList[i].ExpenseName!!.toLowerCase().trim().contains(
                                                            etxtsearch!!.text.toString().toLowerCase().trim { it <= ' ' })
                                                    ) {
                                                        array_sort.add(expensetypeArrayList[i])
                                                    }
                                                }
                                            }
                                            sadapter = ExpensetypeListAdapter(this@ExpenseAddActivity, array_sort)
                                            list_view!!.adapter = sadapter
                                        }
                                    })
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@ExpenseAddActivity,
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
                            else {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var strExtypeid= ""
        var strExamount= ""
    }

    private fun validateDateExpense(v : View) {

        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val date = inputFormat.parse(tie_Date!!.text.toString())
        val strDate = outputFormat.format(date)
        strExtypeid= strExpenseTypeId!!
        strExamount= tie_ExpenseAmount!!.text.toString()

        if (strDate.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if (strExtypeid.equals("")){
            Config.snackBars(context,v,"Select Expense Type")
        }
        else if (strExamount.equals("")){
            Config.snackBars(context,v,"Enter Expense Amount")
        }
        else{
            addExpense(strDate)
        }



    }

    private fun addExpense(strDate : String) {
        try {

//            strExtypeid= strExpenseTypeId!!
//            strExamount= etamount!!.text.toString()

            context = this@ExpenseAddActivity
            expenseAddViewModel = ViewModelProvider(this).get(ExpenseAddViewModel::class.java)
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    expenseAddViewModel.addExpenselist(this,strDate,strExtypeid,strExamount)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    var jobj = jObject.getJSONObject("UpdateExpenseDetails")
                                    val builder = AlertDialog.Builder(
                                        this@ExpenseAddActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jobj.getString("ResponseMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        reset()
                                        onBackPressed()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@ExpenseAddActivity,
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
                            else {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reset() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        txtexpensetype!!.text = ""
        etamount!!.text = ""
        strExpenseType=""
        strExpenseTypeId=""

        tie_Date!!.setText(currentDate)
        tie_ExpenseType!!.setText("")
        tie_ExpenseAmount!!.setText("")


    }

}