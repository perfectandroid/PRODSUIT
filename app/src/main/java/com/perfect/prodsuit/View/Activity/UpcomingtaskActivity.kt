package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.View.Adapter.UpcmngtaskListAdapter
import com.perfect.prodsuit.Viewmodel.ActivitySortLeadMngmntViewModel
import com.perfect.prodsuit.Viewmodel.LeadMangeFilterViewModel
import com.perfect.prodsuit.Viewmodel.UpcomingtasksListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpcomingtaskActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var upcomingtaskslistViewModel: UpcomingtasksListViewModel
    private var rv_upcmngtasklist: RecyclerView?=null
    lateinit var upcmngtaskArrayList : JSONArray
    private var SubMode:String?=""
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var etxt_date1: EditText? = null
    internal var etxt_name1: EditText? = null
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    internal var etxt_date: EditText? = null
    internal var etxt_Name: EditText? = null
    lateinit var leadMangeFilterViewModel: LeadMangeFilterViewModel
    lateinit var activitySortLeadMngmntViewModel: ActivitySortLeadMngmntViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_upcomingtask)
        setRegViews()
        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
        getUpcomingtasksList()
    }
    companion object {
        var submode = ""
    }
    private fun setRegViews() {
        rv_upcmngtasklist = findViewById(R.id.rv_upcmngtasklist)
        val imback = findViewById<ImageView>(R.id.imback)
        val imgv_filter= findViewById<ImageView>(R.id.imgv_filter)
        val imgv_sort= findViewById<ImageView>(R.id.imgv_sort)

        imback!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)
        imgv_sort!!.setOnClickListener(this)
    }

    private fun getUpcomingtasksList() {
        context = this@UpcomingtaskActivity
        upcomingtaskslistViewModel = ViewModelProvider(this).get(UpcomingtasksListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                upcomingtaskslistViewModel.getUpcomingtasklist(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                    upcmngtaskArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                    val lLayout = GridLayoutManager(this@UpcomingtaskActivity, 1)
                                    rv_upcmngtasklist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_upcmngtasklist!!.setHasFixedSize(true)
                                    val adapter = TodoListAdapter(applicationContext, upcmngtaskArrayList,SubMode!!)
                                    rv_upcmngtasklist!!.adapter = adapter
                                    adapter.setClickListener(this@UpcomingtaskActivity)

                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@UpcomingtaskActivity,
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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.imgv_filter -> {
                filterData()
            }
            R.id.imgv_sort -> {
                sortData()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("todolist")){
            val jsonObject = upcmngtaskArrayList.getJSONObject(position)
            val i = Intent(this@UpcomingtaskActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }
    private fun sortData() {

        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.sort_popup, null)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button

            val checkbox_asc = layout1.findViewById<CheckBox>(R.id.checkbox_asc) as CheckBox
            val checkbox_dsc = layout1.findViewById<CheckBox>(R.id.checkbox_dsc)  as CheckBox

            val checkbox_date = layout1.findViewById<CheckBox>(R.id.checkbox_Date)  as CheckBox
            val checkbox_nme = layout1.findViewById<CheckBox>(R.id.checkbox_name)  as CheckBox

            etxt_date1 = layout1.findViewById<EditText>(R.id.etxt_date) as EditText
            etxt_name1 = layout1.findViewById<EditText>(R.id.etxt_name)  as EditText


            etxt_date1!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date1!!.setOnClickListener(View.OnClickListener { dateSelector1() })

            if (checkbox_asc.isChecked)
            {
                OverDueActivity.criteria ="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false
            }
            if (checkbox_dsc.isChecked){
                OverDueActivity.criteria ="2"
                checkbox_asc.isChecked=false
                checkbox_dsc.isChecked=true
            }
            if (checkbox_date.isChecked)
            {
                OverDueActivity.date =etxt_date1!!.text.toString()

            }
            else
            {
                OverDueActivity.date =""
            }
            if (checkbox_nme.isChecked)
            {
                OverDueActivity.date =etxt_name1!!.text.toString()

            }
            else
            {
                OverDueActivity.name =""
            }
            if(!checkbox_asc.isChecked()&& !checkbox_dsc.isChecked)
            {
                Toast.makeText(applicationContext,"Please select a value",Toast.LENGTH_LONG).show()
            }
            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                OverDueActivity.criteria ="1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
                OverDueActivity.criteria ="2"
            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                if(etxt_date1!!.text.toString().equals("") && etxt_name1!!.text.toString().equals(""))
                {
                    Toast.makeText(applicationContext,"Please enter a value",Toast.LENGTH_LONG).show()
                }
                else
                {
                    getSortList()
                    alertDialogSort.dismiss()
                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }

    private fun getUpcomingtasksList1() {
        submode ="3"
        context = this@UpcomingtaskActivity
        leadMangeFilterViewModel = ViewModelProvider(this).get(LeadMangeFilterViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadMangeFilterViewModel.getLeadMangfilter(this)!!.observe(
                    this,
                    Observer { leadmangfilterSetterGetter ->
                        val msg = leadmangfilterSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                upcmngtaskArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                val lLayout = GridLayoutManager(this@UpcomingtaskActivity, 1)
                                rv_upcmngtasklist!!.layoutManager =
                                    lLayout as RecyclerView.LayoutManager?
                                rv_upcmngtasklist!!.setHasFixedSize(true)
                                val adapter = TodoListAdapter(applicationContext, upcmngtaskArrayList,SubMode!!)
                                rv_upcmngtasklist!!.adapter = adapter
                                adapter.setClickListener(this@UpcomingtaskActivity)

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@UpcomingtaskActivity,
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

    private fun dateSelector() {
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
                    etxt_date!!.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun filterData() {

        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.filter_popup, null)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button
            etxt_date  = layout1.findViewById<EditText>(R.id.etxt_date)
            etxt_Name  = layout1.findViewById<EditText>(R.id.etxt_Name)

            etxt_date!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date!!.setOnClickListener(View.OnClickListener { dateSelector() })

            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                OverDueActivity.name = etxt_Name!!.text.toString()
                OverDueActivity.nxtactndate = etxt_date!!.text.toString()
                getUpcomingtasksList1()
                alertDialogSort.dismiss()
            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }

    private fun getSortList() {

       submode ="3"
        context = this@UpcomingtaskActivity

        activitySortLeadMngmntViewModel = ViewModelProvider(this).get(
            ActivitySortLeadMngmntViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                activitySortLeadMngmntViewModel.getSortlist(this)!!.observe(
                    this,
                    Observer { sortleadmngeSetterGetter ->
                        val msg = sortleadmngeSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                upcmngtaskArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                val lLayout = GridLayoutManager(this@UpcomingtaskActivity, 1)
                                rv_upcmngtasklist!!.layoutManager =
                                    lLayout as RecyclerView.LayoutManager?
                                rv_upcmngtasklist!!.setHasFixedSize(true)
                                val adapter = TodoListAdapter(applicationContext, upcmngtaskArrayList,SubMode!!)
                                rv_upcmngtasklist!!.adapter = adapter
                                adapter.setClickListener(this@UpcomingtaskActivity)

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@UpcomingtaskActivity,
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

    fun dateSelector1() {
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
                    etxt_date1!!.setText(dayOfMonth.toString() + "-" + (monthOfYear) + "-" + year)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}