package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONObject
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.View.Adapter.AccountDetailAdapter
import com.perfect.prodsuit.View.Adapter.LeadHistoryAdapter
import com.perfect.prodsuit.Viewmodel.LeadHistoryViewModel
import org.json.JSONArray
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AccountDetailsActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

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
    val TAG : String = "AccountDetailsActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null

    var llHistory: LinearLayout? = null

    var recyAccountDetail: RecyclerView? = null
    var recyHistory: RecyclerView? = null
    lateinit var jsonArray : JSONArray

    lateinit var leadHistoryViewModel: LeadHistoryViewModel
    lateinit var leadHistoryArrayList : JSONArray

    private var fab_main : FloatingActionButton? = null
    private var fab1 : FloatingActionButton? = null
    private var fab2 : FloatingActionButton? = null
    private var fab3 : FloatingActionButton? = null
    private var fab4 : FloatingActionButton? = null
    private var fab5 : FloatingActionButton? = null

    private var fab_open : Animation? = null
    private var fab_close : Animation? = null
    private var fab_clock : Animation? = null
    private var fab_anticlock : Animation? = null

    private var txtHistory : TextView? = null
    private var txtNxtAction : TextView? = null
    private var txtNewAction : TextView? = null
    private var txtFollowupDeatils : TextView? = null
    private var txtLeadInfo : TextView? = null


    private var isOpen  : Boolean? = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_account_details)
        context = this@AccountDetailsActivity

        leadHistoryViewModel = ViewModelProvider(this).get(LeadHistoryViewModel::class.java)

        setRegViews()
        bottombarnav()

       // getAccountDetails()
        fabOpenClose()

//
//        fab_main!!.setOnClickListener {
//
//            fabOpenClose()
//        }


    }

    private fun fabOpenClose() {
        if (isOpen!!) {

            fab1!!.startAnimation(fab_close);
            fab2!!.startAnimation(fab_close);
            fab3!!.startAnimation(fab_close);
            fab4!!.startAnimation(fab_close);
            fab5!!.startAnimation(fab_close);
            txtHistory!!.startAnimation(fab_close)
            txtNxtAction!!.startAnimation(fab_close)
            txtNewAction!!.startAnimation(fab_close)
            txtFollowupDeatils!!.startAnimation(fab_close)
            txtLeadInfo!!.startAnimation(fab_close)



            fab_main!!.startAnimation(fab_anticlock);
            fab1!!.setClickable(false);
            fab2!!.setClickable(false);
            fab3!!.setClickable(false);
            fab4!!.setClickable(false);
            fab5!!.setClickable(false);
            isOpen = false;
        } else {

            fab1!!.startAnimation(fab_open);
            fab2!!.startAnimation(fab_open);
            fab3!!.startAnimation(fab_open);
            fab4!!.startAnimation(fab_open);
            fab5!!.startAnimation(fab_open);

            txtHistory!!.startAnimation(fab_open)
            txtNxtAction!!.startAnimation(fab_open)
            txtNewAction!!.startAnimation(fab_open)
            txtFollowupDeatils!!.startAnimation(fab_open)
            txtLeadInfo!!.startAnimation(fab_open)

            fab_main!!.startAnimation(fab_clock);
            fab1!!.setClickable(true);
            fab2!!.setClickable(true);
            fab3!!.setClickable(true);
            fab4!!.setClickable(true);
            fab5!!.setClickable(true);
            isOpen = true;
        }
    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        llHistory = findViewById<LinearLayout>(R.id.llHistory)

        recyAccountDetail = findViewById<RecyclerView>(R.id.recyAccountDetail)
        recyHistory = findViewById<RecyclerView>(R.id.recyHistory)

        imback!!.setOnClickListener(this)

        fab_main = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);
        fab5 = findViewById(R.id.fab5);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        txtHistory = findViewById(R.id.txtHistory);
        txtNxtAction = findViewById(R.id.txtNxtAction);
        txtNewAction = findViewById(R.id.txtNewAction);
        txtFollowupDeatils = findViewById(R.id.txtFollowupDeatils);
        txtLeadInfo = findViewById(R.id.txtLeadInfo);

        fab_main!!.setOnClickListener(this)
        fab1!!.setOnClickListener(this)
        fab2!!.setOnClickListener(this)
        fab3!!.setOnClickListener(this)
        fab4!!.setOnClickListener(this)
        fab5!!.setOnClickListener(this)


    }

    private fun getAccountDetails() {

        val arrayList = ArrayList<String>()
        arrayList.add("Lead Info")
        arrayList.add("Follow Up Details")
        arrayList.add("Next Action")
        arrayList.add("New Action")
        arrayList.add("History")
        jsonArray = JSONArray()
        val detailObj = JSONObject()
        for (i in 0 until arrayList.size) {
            val jObject = JSONObject()
            val ii = i+1
            jObject.put("id", ii);
            jObject.put("name", arrayList.get(i));
            jObject.put("image", R.drawable.applogo);
            jsonArray!!.put(jObject)
        }

        Log.e(TAG,"arrayList   8311   "+arrayList)
        Log.e(TAG,"jsonArray   8312   "+jsonArray)

//        detailObj.put("Accounts", jsonArray);
//
//        Log.e(TAG,"detailObj   "+detailObj)

        recyAccountDetail!!.setLayoutManager(LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false))
        val adapter = AccountDetailAdapter(this@AccountDetailsActivity, jsonArray)
        recyAccountDetail!!.adapter = adapter
        adapter.setClickListener(this@AccountDetailsActivity)
    }


    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@AccountDetailsActivity, HomeActivity::class.java)
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
        values.put(CalendarContract.Events.DESCRIPTION, "[ $descriptn ]")
        values.put(CalendarContract.Events.CALENDAR_ID, 1)
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
                startActivity(Intent(this@AccountDetailsActivity, WelcomeActivity::class.java))
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.fab->{
                fabOpenClose()
            }
            R.id.fab1->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
            }
            R.id.fab2->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
            }
            R.id.fab3->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
            }
            R.id.fab4->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE

                messagePopup()
            }
            R.id.fab5->{
                isOpen = true
                fabOpenClose()

                getHistory("1")
            }

        }
    }

    private fun messagePopup() {
        try {
            val builder = AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.send_message_popup, null)
            builder.setView(layout)
            val alertDialog = builder.create()

            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"data  197  "+data)
        llHistory!!.visibility = View.VISIBLE
        getHistory("1")
    }

    private fun getHistory(PrductOnly: String) {
        llHistory!!.visibility = View.GONE
        var leadHisory = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadHistoryViewModel.getLeadHistory(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   231   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadHistoryDetails")
                                leadHistoryArrayList = jobjt.getJSONArray("LeadHistoryDetailsList")
                                if (leadHistoryArrayList.length()>0){
                                    if (leadHisory == 0){
                                        llHistory!!.visibility = View.VISIBLE
                                        leadHisory++
//                                        productCategoryPopup(leadHistoryArrayList)

                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                        recyHistory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        recyCustomer!!.setHasFixedSize(true)
                                        val adapter = LeadHistoryAdapter(this@AccountDetailsActivity, leadHistoryArrayList)
                                        recyHistory!!.adapter = adapter
                                       // adapter.setClickListener(this@AccountDetailsActivity)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AccountDetailsActivity,
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
}