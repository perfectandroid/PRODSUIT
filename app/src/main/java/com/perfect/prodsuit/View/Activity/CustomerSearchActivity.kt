package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.View.Adapter.CustomerAdapter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.CustomerAddViewModel
import com.perfect.prodsuit.Viewmodel.CustomerSearchViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CustomerSearchActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

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
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    var edt_customer: EditText? = null
    var img_search: ImageView? = null
    var edt_name: EditText? = null
    var edt_phone: EditText? = null
    var edt_email: EditText? = null
    var edt_address: EditText? = null
    var btnSubmit: Button? = null
    var btnReset: Button? = null
    var recyCustomer: RecyclerView? = null
    private var CUSTOMER_SEARCH: Int? = 101
    lateinit var customersearchViewModel: CustomerSearchViewModel
    lateinit var customerAddViewModel: CustomerAddViewModel
    val TAG: String = "CustomerSearchActivity"
    lateinit var customerArrayList : JSONArray
    companion object {
        var strCustomer = ""
        var strName = ""
        var strEmail = ""
        var strPhone = ""
        var strAddress = ""
        var ID_Customer = ""

        private lateinit var networkChangeReceiver: NetworkChangeReceiver
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_customer_search)
        context = this@CustomerSearchActivity
        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)
        customerAddViewModel = ViewModelProvider(this).get(CustomerAddViewModel::class.java)
        setRegViews()
        bottombarnav()

        getCalendarId(context)

        strCustomer = ""
        strName = ""
        strEmail = ""
        strPhone = ""
        strAddress = ""
        ID_Customer = ""

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        edt_customer = findViewById<EditText>(R.id.edt_customer)
        img_search = findViewById<ImageView>(R.id.img_search)

        edt_name = findViewById<EditText>(R.id.edt_name)
        edt_email = findViewById<EditText>(R.id.edt_email)
        edt_phone = findViewById<EditText>(R.id.edt_phone)
        edt_address = findViewById<EditText>(R.id.edt_address)

        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
     //   recyCustomer = findViewById<RecyclerView>(R.id.recyCustomer)

        imback!!.setOnClickListener(this)
        img_search!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.img_search->{
                try {
                    strCustomer = edt_customer!!.text.toString()
                    if (strCustomer.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Enter Customer", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                    }else{
                        getCustomerSearch()
                    }
                }catch (e  :Exception){
                    Log.e("TAG","Exception  64   "+e.toString())
                }
           }
            R.id.btnReset->{
                edt_name!!.setText("")
                edt_email!!.setText("")
                edt_phone!!.setText("")
                edt_address!!.setText("")
            }
            R.id.btnSubmit->{
               validations(v)
            }
        }
    }

    private fun validations(v: View) {

        strName     =  edt_name!!.text.toString()
        strEmail    =  edt_email!!.text.toString()
        strPhone    =  edt_phone!!.text.toString()
        strAddress  =  edt_address!!.text.toString()

        if (strName.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Name", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strPhone.equals("") || strPhone.length != 10){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Mobile", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strEmail.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Email", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strAddress.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Address", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else{
            addCustomer()
        }

    }

    private fun addCustomer() {

        val intent = Intent()
        intent.putExtra("Customer_Mode", "0")
        intent.putExtra("ID_Customer", "")
        intent.putExtra("Name", strName)
        intent.putExtra("Address", strAddress)
        intent.putExtra("Email", strEmail)
        intent.putExtra("MobileNumber", strPhone)
        setResult(CUSTOMER_SEARCH!!, intent)
        finish()
    }

    private fun getCustomerSearch() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                customersearchViewModel.getCustomer(this,strName,"1")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e("TAG","msg   105   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
                                if (customerArrayList.length()>0){
                                    Log.e("TAG","msg   1052   "+msg)
//                                    val lLayout = GridLayoutManager(this@CustomerSearchActivity, 1)
//                                    recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                    recyCustomer!!.setHasFixedSize(true)
//                                    val adapter = CustomerAdapter(this@CustomerSearchActivity, customerArrayList)
//                                    recyCustomer!!.adapter = adapter
//                                    adapter.setClickListener(this@CustomerSearchActivity)
//                                    Log.e(TAG,"msg   10522   "+msg)

                                    customerSearchPopup(customerArrayList)
                                }
                            }
                            else if (jObject.getString("StatusCode") == "105"){
                                Config.logoutTokenMismatch(context,jObject)
                            }
                            else {
                                val builder = AlertDialog.Builder(
                                    this@CustomerSearchActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun customerSearchPopup(customerArrayList: JSONArray) {
        try {

            val dialog2 = Dialog(this)
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog2 .setContentView(R.layout.customersearch_popup)
            dialog2.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyCustomer = dialog2 .findViewById(R.id.recyCustomer) as RecyclerView

            val lLayout = GridLayoutManager(this@CustomerSearchActivity, 1)
            recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = CustomerAdapter(this@CustomerSearchActivity, customerArrayList)
            recyCustomer!!.adapter = adapter
            adapter.setClickListener(this@CustomerSearchActivity)

            dialog2.show()
            dialog2.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@CustomerSearchActivity, HomeActivity::class.java)
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
                startActivity(Intent(this@CustomerSearchActivity, WelcomeActivity::class.java))
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("customer")){
            val jsonObject = customerArrayList.getJSONObject(position)
            val intent = Intent()
            intent.putExtra("Customer_Mode", "1")
            intent.putExtra("ID_Customer", jsonObject.getString("ID_Customer"))
            intent.putExtra("Name", jsonObject.getString("Name"))
            intent.putExtra("Address", jsonObject.getString("Address"))
            intent.putExtra("Email", jsonObject.getString("Email"))
            intent.putExtra("MobileNumber", jsonObject.getString("MobileNumber"))
            setResult(CUSTOMER_SEARCH!!, intent)
            finish()
        }
    }
    private fun getCalendarId(context: Context): Long? {

        try
        {
            val permissions = true
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
            Log.e("calcursor", calCursor.toString())
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
                    Log.e(TAG,"CALID : "+calID.toLong())
                    return calID.toLong()
                }
            }


        }
        catch(e:SecurityException)
        {
            Log.e(TAG,"Error"+e.toString())
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

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}