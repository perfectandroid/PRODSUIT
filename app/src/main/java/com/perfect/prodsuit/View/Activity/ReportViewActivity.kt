package com.perfect.prodsuit.View.Activity


import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.DashrrporttypeModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DashReporttypeListAdapter
import com.perfect.prodsuit.Viewmodel.DashboardreportListViewModel
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportViewActivity : AppCompatActivity() , View.OnClickListener {

    val TAG : String = "ReportViewActivity"
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
    var btnReset: Button? = null

    var tie_FromDate: TextInputEditText? = null
    var tie_ToDate: TextInputEditText? = null
    var tie_DashReport: TextInputEditText? = null
    private var fromToDate:Int = 0

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
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_reportview)
        setRegViews()
        context = this@ReportViewActivity
        bottombarnav()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtfromDate!!.text = currentDate
        tie_FromDate!!.setText(currentDate)
        tie_ToDate!!.setText(currentDate)
        txttoDate!!.text = currentDate
        getCalendarId(context)
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imclose = findViewById(R.id.imclose)
        im_close = findViewById(R.id.im_close)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
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
        tie_FromDate = findViewById(R.id.tie_FromDate)
        tie_ToDate = findViewById(R.id.tie_ToDate)
        tie_DashReport = findViewById(R.id.tie_DashReport)

        tie_FromDate!!.setOnClickListener(this)
        tie_ToDate!!.setOnClickListener(this)
        tie_DashReport!!.setOnClickListener(this)
        txtok1!!.setOnClickListener(this)
        llfromdate!!.setOnClickListener(this)
        lltodate!!.setOnClickListener(this)
        imclose!!.setOnClickListener(this)
        txtok!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        im_close!!.setOnClickListener(this)
        llDashboardreporttype!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)


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
//                intent = Intent(applicationContext, ReportViewDetailsActivity::class.java)
//                intent.putExtra("Fromdate", txtfromDate!!.text.toString())
//                intent.putExtra("Todate", txttoDate!!.text.toString())
//                intent.putExtra("DashboardTypeId", strDashboardTypeId)
//                intent.putExtra("DashboardTypeName", strDashboardType)

                validateData(v)

//                intent = Intent(applicationContext, ReportViewDetailsActivity::class.java)
//                intent.putExtra("Fromdate", tie_FromDate!!.text.toString())
//                intent.putExtra("Todate", tie_ToDate!!.text.toString())
//                intent.putExtra("DashboardTypeId", strDashboardTypeId)
//                intent.putExtra("DashboardTypeName", strDashboardType)
//                startActivity(intent)
            }
            R.id.btnReset->{
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = sdf.format(Date())
                tie_FromDate!!.setText(currentDate)
                tie_ToDate!!.setText(currentDate)
                tie_DashReport!!.setText("")
                strDashboardTypeId = ""
                strDashboardType = ""
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

            R.id.tie_FromDate->{
                fromToDate = 0
                openBottomSheet()
            }
            R.id.tie_ToDate->{
                fromToDate = 1
                openBottomSheet()
            }
            R.id.tie_DashReport->{
               getDashboardReportType()
            }
        }
    }

    private fun validateData(v : View) {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val fromDa = sdf.parse(tie_FromDate!!.text.toString());
        val toDa = sdf.parse(tie_ToDate!!.text.toString());
        if ( fromDa.after(toDa)) {
            Config.snackBars(context,v,"Check date")
        }
        else{
            Config.snackBars(context,v,"Success")
        }

        if (tie_FromDate!!.text.toString().equals("")){
            Config.snackBars(context,v,"Select From Date")
        }
        else if (tie_ToDate!!.text.toString().equals("")){
            Config.snackBars(context,v,"Select To Date")
        }
        else if (fromDa.after(toDa)){
            Config.snackBars(context,v,"Check Selected Date Range")
        }
        else if (strDashboardTypeId.equals("")){
            Config.snackBars(context,v,"Select Dashboard Report")
        }
        else{
            intent = Intent(applicationContext, ReportViewDetailsActivity::class.java)
            intent.putExtra("Fromdate", tie_FromDate!!.text.toString())
            intent.putExtra("Todate", tie_ToDate!!.text.toString())
            intent.putExtra("DashboardTypeId", strDashboardTypeId)
            intent.putExtra("DashboardTypeName", strDashboardType)
            startActivity(intent)
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
                    R.id.reminder -> {
                      setReminder()
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


    private fun setReminder() {
        try
        {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reminder_setter_popup, null)
            val btncancel = layout.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout.findViewById(R.id.btnsubmit) as Button
            etdate = layout.findViewById(R.id.etdate) as EditText
            ettime = layout.findViewById(R.id.ettime) as EditText
            etdis = layout.findViewById(R.id.etdis) as EditText
            /* val ll_ok = layout.findViewById(R.id.ll_ok) as LinearLayout
             val ll_cancel = layout.findViewById(R.id.ll_cancel) as LinearLayout
             etdate = layout.findViewById(R.id.etdate) as TextView
             ettime = layout.findViewById(R.id.ettime) as TextView
             val etdis = layout.findViewById(R.id.etdis) as EditText*/
            etdate!!.setKeyListener(null)
            ettime!!.setKeyListener(null)
            builder.setView(layout)
            val alertDialog = builder.create()
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val sdf1 = SimpleDateFormat("hh:mm a")
            val sdf2 = SimpleDateFormat("hh:mm")
            yr = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            etdate!!.setText(sdf.format(c.time))
            ettime!!.setText(sdf1.format(c.time))
            val s = sdf2.format(c.time)
            val split = s.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val strhr = split[0]
            val strmin = split[1]
            hr = Integer.parseInt(strhr)
            min = Integer.parseInt(strmin)
            ettime!!.setOnClickListener(View.OnClickListener { timeSelector() })
            etdate!!.setOnClickListener(View.OnClickListener { dateSelector() })
            btncancel.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                alertDialog.dismiss() }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

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
                                                tie_DashReport!!.setText(array_sort[position].SettingsName)
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
    private fun getCalendarId(context: Context): Long? {

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR),
                1
            )
        }

        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

        var calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )
        if (calCursor != null && calCursor.count <= 0) {
            calCursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }
        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calName: String
                val calID: String
                val nameCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calName = calCursor.getString(nameCol)
                calID = calCursor.getString(idCol)

                //    Log.d("Calendar name = $calName Calendar ID = $calID")

                calCursor.close()
                return calID.toLong()
            }
        }
        return null




    }
    fun addEvent(iyr: Int, imnth: Int, iday: Int, ihour: Int, imin: Int, descriptn: String, Title: String) {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                1
            )
        }
        val cr = contentResolver
        val beginTime = Calendar.getInstance()
        beginTime.set(2022, 11 - 1, 28, 9, 30)
        val endTime = Calendar.getInstance()
        endTime.set(iyr, imnth, iday, ihour, imin)
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, endTime.timeInMillis)
        values.put(CalendarContract.Events.DTEND, endTime.timeInMillis)
        values.put(CalendarContract.Events.TITLE, Title)
        values.put(CalendarContract.Events.DESCRIPTION, descriptn)


        val calendarId = getCalendarId(context)
        Log.i("Calender", calendarId.toString())
        if(calendarId != null) {
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId)
        }


        val tz = TimeZone.getDefault()
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.id)
        values.put(CalendarContract.Events.EVENT_LOCATION, "India")





        try {
            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
            val reminders = ContentValues()
            reminders.put(CalendarContract.Reminders.EVENT_ID, uri!!.lastPathSegment)
            reminders.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_ALERT
            )
            reminders.put(CalendarContract.Reminders.MINUTES, 10)
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders)
        }catch (e: Exception){
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Reminder set successfully.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

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

                if (fromToDate == 0){
                    tie_FromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }
                if (fromToDate == 1){
                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }


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