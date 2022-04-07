package com.perfect.prodsuit.View.Activity


import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.DashrrporttypeModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DashReporttypeListAdapter
import com.perfect.prodsuit.Viewmodel.DashboardreportListViewModel
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReportViewActivity : AppCompatActivity() , View.OnClickListener {

    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    var date_Picker: DatePicker? = null
    var txtok: TextView? = null
    var date_Picker1: DatePicker? = null
    var txtok1: TextView? = null
    var txtfromDate: TextView? = null
    var txttoDate: TextView? = null
    var txtdashboardtype: TextView? = null
    var ll_Fromdate: LinearLayout? = null
    var llfromdate: LinearLayout? = null
    var ll_Todate: LinearLayout? = null
    var lltodate: LinearLayout? = null
    var llDashboardreporttype: LinearLayout? = null
    var imclose: ImageView? = null
    var im_close: ImageView? = null
    var btnSubmit: Button? = null

    private var textlength = 0
    private var etxtsearch: EditText? =null
    private var list_view: ListView?=null
    private var array_sort =ArrayList<DashrrporttypeModel>()
    private var dashReportArrayList = ArrayList<DashrrporttypeModel>()
    private var sadapter: DashReporttypeListAdapter? = null

    lateinit var context: Context
    lateinit var dashboardreportListViewModel: DashboardreportListViewModel

    private var strDashboardType           : String?                   = ""
    private var strDashboardTypeId        : String?                   = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportview)
        setRegViews()
        bottombarnav()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        txttoDate!!.text = currentDate
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imclose = findViewById(R.id.imclose)
        im_close = findViewById(R.id.im_close)
        btnSubmit = findViewById(R.id.btnSubmit)
        llfromdate = findViewById(R.id.llfromdate)
        ll_Fromdate = findViewById(R.id.ll_Fromdate)
        txtfromDate = findViewById(R.id.txtfromDate)
        txttoDate = findViewById(R.id.txttoDate)
        lltodate = findViewById(R.id.lltodate)
        date_Picker = findViewById(R.id.date_Picker)
        txtok = findViewById(R.id.txtok)
        ll_Todate = findViewById(R.id.ll_Todate)
        date_Picker1 = findViewById(R.id.date_Picker1)
        llDashboardreporttype = findViewById(R.id.llDashboardreporttype)
        txtok1 = findViewById(R.id.txtok1)
        txtdashboardtype = findViewById(R.id.txtdashboardtype)
        txtok1!!.setOnClickListener(this)
        llfromdate!!.setOnClickListener(this)
        lltodate!!.setOnClickListener(this)
        imclose!!.setOnClickListener(this)
        txtok!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        im_close!!.setOnClickListener(this)
        llDashboardreporttype!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
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
            R.id.lltodate->{
                ll_Todate!!.visibility=View.VISIBLE
            }
            R.id.imclose->{
                ll_Todate!!.visibility=View.GONE
            }
            R.id.llDashboardreporttype->{
               getDashboardReportType()
            }
            R.id.btnSubmit->{
                intent = Intent(applicationContext, ReportViewDetailsActivity::class.java)
                intent.putExtra("Fromdate", txtfromDate!!.text.toString())
                intent.putExtra("Todate", txttoDate!!.text.toString())
                intent.putExtra("DashboardTypeId", strDashboardTypeId)
                startActivity(intent)
            }
            R.id.txtok1->{
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
                txttoDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                ll_Todate!!.visibility=View.GONE
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

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@ReportViewActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@ReportViewActivity, ProfileActivity::class.java)
                        startActivity(i)
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
                startActivity(Intent(this@ReportViewActivity, WelcomeActivity::class.java))
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

    private fun getDashboardReportType() {
        try {
            val builder = AlertDialog.Builder(this)
            val inflater1 =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reporttype_popup, null)
            list_view = layout.findViewById(R.id.list_view)
            etxtsearch  = layout.findViewById(R.id.etsearch)
            val tv_popuptitle = layout.findViewById(R.id.tv_popuptitle) as TextView
            tv_popuptitle.setText("Choose Dashboard Report")
            builder.setView(layout)
            val alertDialog = builder.create()
            getDashboardReportList(alertDialog)
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDashboardReportList(dialog: AlertDialog) {
        try {
            context = this@ReportViewActivity
            dashboardreportListViewModel = ViewModelProvider(this).get(DashboardreportListViewModel::class.java)
           when (Config.ConnectivityUtils.isConnected(this)) {
                    true -> {
                        progressDialog = ProgressDialog(this, R.style.Progress)
                        progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                        progressDialog!!.setCancelable(false)
                        progressDialog!!.setIndeterminate(true)
                        progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                        progressDialog!!.show()
                        dashboardreportListViewModel.getDashboardreportlist(this)!!.observe(
                            this,
                            Observer { serviceSetterGetter ->
                                val msg = serviceSetterGetter.message
                                if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        var jobj = jObject.getJSONObject("RoportSettingsList")
                                        if (jobj.getString("RoportSettings") == "null") {
                                        }
                                        else {
                                            val jarray = jobj.getJSONArray("RoportSettings")
                                            array_sort = java.util.ArrayList<DashrrporttypeModel>()
                                            dashReportArrayList = ArrayList<DashrrporttypeModel>()
                                            for (k in 0 until jarray.length()) {
                                                val jsonObject = jarray.getJSONObject(k)

                                                dashReportArrayList.add(
                                                    DashrrporttypeModel(
                                                        jsonObject.getString("ID_ReportSettings"),
                                                        jsonObject.getString("SettingsName")
                                                    )
                                                )
                                                array_sort.add(
                                                    DashrrporttypeModel(
                                                        jsonObject.getString("ID_ReportSettings"),
                                                        jsonObject.getString("SettingsName")
                                                    )
                                                )
                                            }
                                            sadapter = DashReporttypeListAdapter(this@ReportViewActivity, array_sort)
                                            list_view!!.setAdapter(sadapter)
                                            list_view!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                                                Config.Utils.hideSoftKeyBoard(this@ReportViewActivity,view)
                                                array_sort.get(position).SettingsName
                                                txtdashboardtype!!.text = array_sort[position].SettingsName
                                                strDashboardType=array_sort[position].SettingsName
                                                strDashboardTypeId=array_sort[position].ID_ReportSettings
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
                                                for (i in dashReportArrayList.indices) {
                                                    if (textlength <= dashReportArrayList[i].SettingsName!!.length) {
                                                        if (dashReportArrayList[i].SettingsName!!.toLowerCase().trim().contains(
                                                                etxtsearch!!.text.toString().toLowerCase().trim { it <= ' ' })
                                                        ) {
                                                            array_sort.add(dashReportArrayList[i])
                                                        }
                                                    }
                                                }
                                                sadapter = DashReporttypeListAdapter(this@ReportViewActivity, array_sort)
                                                list_view!!.adapter = sadapter
                                            }
                                        })
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@ReportViewActivity,
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

}