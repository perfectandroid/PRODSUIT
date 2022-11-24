package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TodoListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var todolistViewModel: TodoListViewModel
    private var rv_todolist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray
    private var SubMode:String?=""
    lateinit var leadMangeFilterViewModel: LeadMangeFilterViewModel
    lateinit var activitySortLeadMngmntViewModel: ActivitySortLeadMngmntViewModel
    internal var etxt_date: EditText? = null
    internal var etxt_Name: EditText? = null
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
    internal var sortFilter:Int = 0
    internal var etxt_date1: EditText? = null
    internal var etxt_name1: EditText? = null

    var name = ""
    var submode = "1"
    var date = ""
    var criteria = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_todolist)
        setRegViews()
        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }

        name = ""
        submode = "1"
        date = ""
        criteria = ""
        getTodoList()
    }
    companion object {
        var name = ""
        var submode = "1"
        var date = ""
        var criteria = ""
    }
    private fun setRegViews() {
        rv_todolist = findViewById(R.id.rv_todolist)
        val imback = findViewById<ImageView>(R.id.imback)


        val imgv_filter= findViewById<ImageView>(R.id.imgv_filter)
        val imgv_sort= findViewById<ImageView>(R.id.imgv_sort)

        imback!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)
        imgv_sort!!.setOnClickListener(this)
    }

    private fun getTodoList() {
        var toDoDet = 0
        context = this@TodoListActivity
        todolistViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                todolistViewModel.getTodolist(this, submode!!, name!!, criteria!!,date!!)!!.observe(
                        this,
                        Observer { todolistSetterGetter ->
                            try {
                                val msg = todolistSetterGetter.message
                                if (msg!!.length > 0) {

                                    if (toDoDet == 0){
                                        toDoDet++

                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                            todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                            val lLayout = GridLayoutManager(this@TodoListActivity, 1)
                                            rv_todolist!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            rv_todolist!!.setHasFixedSize(true)
                                            val adapter = TodoListAdapter(applicationContext, todoArrayList,SubMode!!)
                                            rv_todolist!!.adapter = adapter
                                            adapter.setClickListener(this@TodoListActivity)
                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@TodoListActivity,
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



    private fun getTodoList1() {
        submode ="1"
        context = this@TodoListActivity
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
                                todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                val lLayout = GridLayoutManager(this@TodoListActivity, 1)
                                rv_todolist!!.layoutManager =
                                    lLayout as RecyclerView.LayoutManager?
                                rv_todolist!!.setHasFixedSize(true)
                                val adapter = TodoListAdapter(applicationContext, todoArrayList,SubMode!!)
                                rv_todolist!!.adapter = adapter
                                adapter.setClickListener(this@TodoListActivity)
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TodoListActivity,
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("todolist")){
            val jsonObject = todoArrayList.getJSONObject(position)
            val i = Intent(this@TodoListActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
        if (data.equals("todocall")){

            val ALL_PERMISSIONS = 101

            val permissions = arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE
            )
            if (ContextCompat.checkSelfPermission(
                    this@TodoListActivity,
                    Manifest.permission.CALL_PHONE
                ) + ContextCompat.checkSelfPermission(
                    this@TodoListActivity,
                    Manifest.permission.RECORD_AUDIO
                )
                + ContextCompat.checkSelfPermission(
                    this@TodoListActivity,
                    Manifest.permission.READ_PHONE_STATE
                )
                + ContextCompat.checkSelfPermission(
                    this@TodoListActivity,
                    Manifest.permission.READ_CALL_LOG
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
            } else {
                val jsonObject = todoArrayList.getJSONObject(position)
                Log.e("TODO"," 289     jsonObject    "+jsonObject)
                val mobileno = jsonObject.getString("LgCusMobile")
                val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
                val BroadCallEditer = BroadCallSP.edit()
                BroadCallEditer.putString("BroadCall", "Yes")
                BroadCallEditer.putString("ID_LeadGenerate", jsonObject.getString("ID_LeadGenerate"))
                BroadCallEditer.putString("ID_LeadGenerateProduct", jsonObject.getString("ID_LeadGenerateProduct"))
                BroadCallEditer.commit()


                Log.e("TODO","8001   "+mobileno)
                Log.e("TODO","8001   "+jsonObject.getString("ID_LeadGenerate"))
                Log.e("TODO","8002   "+jsonObject.getString("ID_LeadGenerateProduct"))

                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)

//                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
//                startActivity(intent)

            }
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
//                    getTodoList1()
                    getTodoList()
                    alertDialogSort.dismiss()
                }
            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }
    /*private fun sortData() {

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
            checkbox_asc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
            checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio1));
            etxt_date1!!.setOnClickListener(View.OnClickListener { dateSelector1() })

            if (checkbox_asc.isChecked)
            {
                criteria ="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                checkbox_asc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
                checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio1));
            }
            if (checkbox_dsc.isChecked){
                criteria ="2"
                checkbox_asc.isChecked=false
                checkbox_dsc.isChecked=true
              //  checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
                checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
                checkbox_asc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio1));
            }
            if (checkbox_date.isChecked)
            {
                OverDueActivity.date =etxt_date1!!.text.toString()
                checkbox_date.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sortchkbx));
            }
            else
            {
                date =""
            }
            if (checkbox_nme.isChecked)
            {
                date =etxt_name1!!.text.toString()
                checkbox_nme.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sortchkbx));
            }
            else
            {
                name =""
            }
            if(!checkbox_asc.isChecked()&& !checkbox_dsc.isChecked)
            {
                Toast.makeText(applicationContext,"Please select a value",Toast.LENGTH_LONG).show()
            }
            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                criteria ="1"
                checkbox_asc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
                checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio1));
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
                criteria ="2"
                checkbox_dsc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radiosort));
                checkbox_asc.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio1));
            })


            checkbox_date.setOnClickListener(View.OnClickListener {



                checkbox_date.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sortchkbx));

            })
            checkbox_nme.setOnClickListener(View.OnClickListener {

                checkbox_nme.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sortchkbx));

            })







            builder1.setView(layout1)
            val alertDialogSort = builder1.create()

            btncancel.setOnClickListener {

                alertDialogSort.dismiss() }
            btnsubmit.setOnClickListener {

                if(!(etxt_date1!!.text.toString().equals("")) && !(etxt_name1!!.text.toString().equals("")))
                {
                    getSortList()
                    alertDialogSort.dismiss()

                }
                else if(!(etxt_date1!!.text.toString().equals(""))|| !(etxt_name1!!.text.toString().equals("")))
                {
                    getSortList()
                    alertDialogSort.dismiss()
                }
                else
                {
                    Toast.makeText(applicationContext,"Please enter a value",Toast.LENGTH_LONG).show()
                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }*/

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
            criteria ="1"


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
                criteria ="1"
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false
                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)


            }
            if (checkbox_dsc.isChecked){
                criteria ="2"
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
                    OverDueActivity.date =etxt_date1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_date.setButtonDrawable(image5)
                    OverDueActivity.date =""
                }
            })

            checkbox_nme.setOnClickListener(View.OnClickListener { v ->
                val checked = (v as CheckBox).isChecked
                // Check which checkbox was clicked
                if (checked) {
                    val image4 = resources.getDrawable(R.drawable.ic_ticked)
                    checkbox_nme.setButtonDrawable(image4)
                    name =etxt_name1!!.text.toString()
                } else {
                    val image5 = resources.getDrawable(R.drawable.ic_unticked)
                    checkbox_nme.setButtonDrawable(image5)
                    name =""
                }
            })



            checkbox_asc.setOnClickListener(View.OnClickListener {
                checkbox_asc.isChecked=true
                checkbox_dsc.isChecked=false

                val image = resources.getDrawable(R.drawable.ic_chkboxascdsc)
                checkbox_asc.setButtonDrawable(image)
                val image1 = resources.getDrawable(R.drawable.ic_chkbxascdesc_light)
                checkbox_dsc.setButtonDrawable(image1)

                criteria ="1"
            })
            checkbox_dsc.setOnClickListener(View.OnClickListener {
                checkbox_dsc.isChecked=true
                checkbox_asc.isChecked=false
                criteria ="2"

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

                    getTodoList()
                    alertDialogSort.dismiss()


//                    if(!(date.equals("")))
//                    {
//                        if (!(checkbox_date.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else
//                        {
////                            getSortList()
//                            getTodoList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//                    else if(!(name.equals("")))
//                    {
//                        if (!(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else
//                        {
////                            getSortList()
//                            getTodoList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//                    else if(!(date.equals(""))&& !(name.equals(""))){
//
//
//                        if (!(checkbox_date.isChecked)&&!(checkbox_nme.isChecked)){
//
//
//
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        if (!(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        else if (!(checkbox_date.isChecked)&&(checkbox_nme.isChecked)){
//                            Toast.makeText(applicationContext, "Please select checkbox", Toast.LENGTH_LONG)
//                                .show()
//                        }
//
//                        else
//                        {
////                            getSortList()
//                            getTodoList()
//                            alertDialogSort.dismiss()
//                        }
//
//                    }
//
                }



            }

            alertDialogSort.show()

        }catch (e: Exception){

        }


    }

    private fun getSortList() {

       submode ="1"
        context = this@TodoListActivity

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
                                //   var jobj = jObject.getJSONObject("UserLoginDetails")
                                val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                val lLayout = GridLayoutManager(this@TodoListActivity, 1)
                                rv_todolist!!.layoutManager =
                                    lLayout as RecyclerView.LayoutManager?
                                rv_todolist!!.setHasFixedSize(true)
                                val adapter = TodoListAdapter(applicationContext, todoArrayList,SubMode!!)
                                rv_todolist!!.adapter = adapter
                                adapter.setClickListener(this@TodoListActivity)
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@TodoListActivity,
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
