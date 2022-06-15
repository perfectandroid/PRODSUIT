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
        val imSort= findViewById<ImageView>(R.id.imSort)
        imback!!.setOnClickListener(this)
        imSort!!.setOnClickListener(this)
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
            R.id.imSort -> {
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


}