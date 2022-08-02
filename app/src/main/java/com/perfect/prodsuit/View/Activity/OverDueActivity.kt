package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.ActivitySortLeadMngmntViewModel
import com.perfect.prodsuit.Viewmodel.LeadMangeFilterViewModel
import com.perfect.prodsuit.Viewmodel.OverDueListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OverDueActivity : AppCompatActivity(), View.OnClickListener,ItemClickListener {
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var overduelistViewModel: OverDueListViewModel
    lateinit var leadMangeFilterViewModel: LeadMangeFilterViewModel
    lateinit var activitySortLeadMngmntViewModel: ActivitySortLeadMngmntViewModel
    private var rv_overduelist: RecyclerView?=null
    lateinit var overdueArrayList : JSONArray
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
    private var strCriteria:String?="1"
    private var mMinute:Int = 0
    internal var etxt_date: EditText? = null
    internal var etxt_Name: EditText? = null
    internal var etxt_date1: EditText? = null
    internal var etxt_name1: EditText? = null
    internal var sortFilter:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_overdue)
        setRegViews()
        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
         name = ""
         date = ""
         criteria = ""
         submode = "2"

        getOverdueList()
    }

    companion object {
        var name = ""
        var name1 = ""
        var date = ""
        var criteria = ""
        var submode = "2"
    }
        private fun setRegViews() {
        rv_overduelist = findViewById(R.id.rv_overduelist)
           val imback = findViewById<ImageView>(R.id.imback)
           val imgv_filter= findViewById<ImageView>(R.id.imgv_filter)
            val imgv_sort= findViewById<ImageView>(R.id.imgv_sort)

           imback!!.setOnClickListener(this)
            imgv_filter!!.setOnClickListener(this)
            imgv_sort!!.setOnClickListener(this)

    }

    private fun getOverdueList() {
        var overDueDet = 0
        context = this@OverDueActivity
        overduelistViewModel = ViewModelProvider(this).get(OverDueListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                overduelistViewModel.getOverduelist(this,submode!!, name!!, criteria!!,date!!)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->

                            try {

                                val msg = serviceSetterGetter.message
                                if (msg!!.length > 0) {

                                    if (overDueDet == 0){
                                        overDueDet++

                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            //   var jobj = jObject.getJSONObject("UserLoginDetails")
                                            val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                            overdueArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                            Log.e("OverDueActivity","overdueArrayList 69  "+overdueArrayList)
                                            val lLayout = GridLayoutManager(this@OverDueActivity, 1)
                                            rv_overduelist!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            rv_overduelist!!.setHasFixedSize(true)
                                            val adapter = TodoListAdapter(applicationContext, overdueArrayList,SubMode!!)
                                            rv_overduelist!!.adapter = adapter
                                            adapter.setClickListener(this@OverDueActivity)
                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@OverDueActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage(jObject.getString("EXMessage"))
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                onBackPressed()
                                                finish()
                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }

                                    }

                                } else {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                }
                            }catch (e: Exception){
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
    private fun sortData() {

        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.sort_popup, null)
            builder1.setCancelable(false)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button

            val checkbox_asc = layout1.findViewById<CheckBox>(R.id.checkbox_asc) as CheckBox
            val checkbox_dsc = layout1.findViewById<CheckBox>(R.id.checkbox_dsc)  as CheckBox

            val checkbox_date = layout1.findViewById<CheckBox>(R.id.checkbox_Date)  as CheckBox
            val checkbox_nme = layout1.findViewById<CheckBox>(R.id.checkbox_name)  as CheckBox

             etxt_date1 = layout1.findViewById<EditText>(R.id.etxt_date) as EditText
             etxt_name1 = layout1.findViewById<EditText>(R.id.etxt_name)  as EditText
             etxt_date1!!.setKeyListener(null)


          //  etxt_date1!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            // etxt_date!!.setText(sdf.format(c.time))

            etxt_date1!!.setOnClickListener(View.OnClickListener {
//                dateSelector1()
                sortFilter = 1
                openBottomSheet()
            })

            if (checkbox_asc.isChecked)
            {
                criteria="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false
                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)


            }
            if (checkbox_dsc.isChecked){
                criteria="2"
                checkbox_asc.isChecked=false
                checkbox_dsc.isChecked=true

                val image = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_dsc.setButtonDrawable(image1)
            }


            checkbox_date.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image2 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_date.setButtonDrawable(image2)
                    date=etxt_date1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_date.setButtonDrawable(image5)
                    date=""
                }
            })

            checkbox_nme.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image4 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_nme.setButtonDrawable(image4)
                    name=etxt_name1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_nme.setButtonDrawable(image5)
                    name=""
                }
            })



            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)

                criteria="1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
                criteria="2"

                val image = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_dsc.setButtonDrawable(image1)
            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                date =etxt_date1!!.text.toString()
                name =etxt_name1!!.text.toString()
                Log.i("Detail",date+"\n"+name)

                if(date.equals("")&& name.equals("") )
                {
                    Toast.makeText(applicationContext, "Please enter a value", Toast.LENGTH_LONG)
                        .show()
                }

                else
                {

                    getOverdueList()
                    alertDialogSort.dismiss()
//                   if(!(date.equals("")))
//                   {
//                       if (!(checkbox_date.isChecked)){
//                           Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                               .show()
//                       }
//                       else
//                       {
//                           getSortList()
//                           alertDialogSort.dismiss()
//                       }
//
//                   }
//                    else if(!(name.equals("")))
//                    {
//                        if (!(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else
//                        {
//                            getSortList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//                    else if(!(date.equals(""))&& !(name.equals(""))){
//
//
//                       if (!(checkbox_date.isChecked)&&!(checkbox_nme.isChecked)){
//
//
//
//                           Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                               .show()
//                       }
//                       if (!(checkbox_nme.isChecked)){
//                           Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                               .show()
//                       }
//                      else if (!(checkbox_date.isChecked)&&(checkbox_nme.isChecked)){
//                           Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                               .show()
//                       }
//
//                       else
//                       {
//                           getSortList()
//                           alertDialogSort.dismiss()
//                       }
//
//                   }

                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }



    override fun onClick(position: Int, data: String) {
        if (data.equals("todolist")){
            val jsonObject = overdueArrayList.getJSONObject(position)
            val i = Intent(this@OverDueActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }
    private fun filterData() {

        try {
            val builder1 = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout1 = inflater1.inflate(R.layout.filter_popup, null)
            builder1.setCancelable(false)

            val btncancel = layout1.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout1.findViewById(R.id.btnsubmit) as Button
             etxt_date  = layout1.findViewById<EditText>(R.id.etxt_date)
             etxt_Name  = layout1.findViewById<EditText>(R.id.etxt_Name)
            criteria = ""

            etxt_date!!.setKeyListener(null)

            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
           // etxt_date!!.setText(sdf.format(c.time))

            etxt_date!!.setOnClickListener(View.OnClickListener {
//                dateSelector()
                sortFilter = 0
                openBottomSheet()
            })

            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

                btncancel.setOnClickListener {

              alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {



                name = etxt_Name!!.text.toString()
                date = etxt_date!!.text.toString()

                if(etxt_date!!.text.toString().equals("") && etxt_Name!!.text.toString().equals("")) {
                    Toast.makeText(applicationContext, "Please select a value", Toast.LENGTH_LONG)
                        .show()
                }
                else {
                    getOverdueList()
//                    getOverdueList1()
                    alertDialogSort.dismiss()
                }
          }

            alertDialogSort.show()

        }catch (e: Exception){

        }


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
                    month = (monthOfYear+1)
                    day = dayOfMonth
                    val days = (if (day < 10) "0$day" else day)
                    val mnth = (if (month < 10) "0$month" else month)

                    etxt_date!!.setText(days.toString() + "-" + (mnth) + "-" + year)
                }, mYear, mMonth, mDay
            )
           // datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun getOverdueList1() {
       // submode="2"
        context = this@OverDueActivity
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
                                //   var jobj = jObject.getJSONObject("UserLoginDetails")
                                val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                overdueArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                Log.e("Filter1","overdueArrayList 69  "+overdueArrayList)
                                val lLayout = GridLayoutManager(this@OverDueActivity, 1)
                                rv_overduelist!!.layoutManager =
                                    lLayout as RecyclerView.LayoutManager?
                                rv_overduelist!!.setHasFixedSize(true)
                                val adapter = TodoListAdapter(applicationContext, overdueArrayList,SubMode!!)
                                rv_overduelist!!.adapter = adapter
                                adapter.setClickListener(this@OverDueActivity)
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@OverDueActivity,
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

    private fun getSortList() {

            submode="2"
            context = this@OverDueActivity

        activitySortLeadMngmntViewModel = ViewModelProvider(this).get(ActivitySortLeadMngmntViewModel::class.java)
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
                                    //   var jobj = jObject.getJSONObject("UserLoginDetails")
                                    val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                    overdueArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                    Log.e("Filter1","overdueArrayList 69  "+overdueArrayList)
                                    val lLayout = GridLayoutManager(this@OverDueActivity, 1)
                                    rv_overduelist!!.layoutManager =
                                        lLayout as RecyclerView.LayoutManager?
                                    rv_overduelist!!.setHasFixedSize(true)
                                    val adapter = TodoListAdapter(applicationContext, overdueArrayList,SubMode!!)
                                    rv_overduelist!!.adapter = adapter
                                    adapter.setClickListener(this@OverDueActivity)
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@OverDueActivity,
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
                    month = (monthOfYear+1)
                    day = dayOfMonth
                  //  etxt_date1!!.setText(dayOfMonth.toString() + "-" + (monthOfYear) + "-" + year)

                    val days = (if (day < 10) "0$day" else day)
                    val mnth = (if (month < 10) "0$month" else month)

                    etxt_date1!!.setText(days.toString() + "-" + (mnth) + "-" + year)

                }, mYear, mMonth, mDay
            )
        //    datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()



        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1!!.minDate = System.currentTimeMillis() - 1000

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day1: Int = date_Picker1!!.getDayOfMonth()
                val mon1: Int = date_Picker1!!.getMonth()
                val month1: Int = mon1+1
                val year1: Int = date_Picker1!!.getYear()
                var strDay = day1.toString()
                var strMonth = month1.toString()
                var strYear = year1.toString()

                yr = year1
                month =  month1
                day= day1


                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                //  etxt_date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)

                if (sortFilter == 0){
                    etxt_date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (sortFilter == 1){
                    etxt_date1!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }


            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

}