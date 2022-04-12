package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.BannerAdapter
import com.perfect.prodsuit.Viewmodel.BannerListViewModel
import com.perfect.prodsuit.Viewmodel.ChangeMpinViewModel
import me.relex.circleindicator.CircleIndicator
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    lateinit var context: Context
    lateinit var changempinViewModel: ChangeMpinViewModel
    lateinit var bannerListViewModel: BannerListViewModel
    private var progressDialog: ProgressDialog? = null
    private var drawer_layout: DrawerLayout? = null
    private var nav_view: NavigationView? = null
    private var btn_menu: ImageView? = null
    private var llservice: LinearLayout? = null
    private var ll_dashboard: LinearLayout? = null
    private var lllead: LinearLayout? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    private var mPager: ViewPager? = null
    private var indicator: CircleIndicator? = null
    private var currentPage = 0
    val XMENArray = ArrayList<String>()
    var XMEN = intArrayOf(0)
    internal var ll_reminder: LinearLayout? = null
    internal var ll_report: LinearLayout? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemain)
        setRegViews()
        bottombarnav()
        getBannerlist()
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@HomeActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@HomeActivity, ProfileActivity::class.java)
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

    private fun setRegViews() {
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        btn_menu = findViewById(R.id.btn_menu)
        lllead = findViewById(R.id.lllead)
        llservice = findViewById(R.id.llservice)
        ll_dashboard = findViewById(R.id.ll_dashboard)
        btn_menu!!.setOnClickListener(this)
        lllead!!.setOnClickListener(this)
        llservice!!.setOnClickListener(this)
        ll_dashboard!!.setOnClickListener(this)
        nav_view!!.setNavigationItemSelectedListener(this)
        nav_view!!.setItemIconTintList(null);
        mPager = findViewById(R.id.pager)
        indicator =findViewById(R.id.indicator)
        ll_reminder =findViewById(R.id.ll_reminder)
        ll_reminder!!.setOnClickListener(this)
        ll_report =findViewById(R.id.ll_report)
        ll_report!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_menu -> {
                if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout!!.openDrawer(GravityCompat.START)
                }
            }
            R.id.llservice -> {
                val i = Intent(this@HomeActivity, ServiceActivity::class.java)
                startActivity(i)

//                val i = Intent(this@HomeActivity, SiteVisitActivity::class.java)
//                startActivity(i)
//
//                val i = Intent(this@HomeActivity, CallRemarkActivity::class.java)
//                startActivity(i)

//                val i = Intent(this@HomeActivity, AccountDetailsActivity::class.java)
//                startActivity(i)
            }
            R.id.lllead -> {
                val i = Intent(this@HomeActivity, LeadActivity::class.java)
                startActivity(i)
            }
            R.id.ll_dashboard -> {
//                https://github.com/PhilJay/MPAndroidChart
                val i = Intent(this@HomeActivity, DashBoardActivity::class.java)
                startActivity(i)
            }
            R.id.ll_reminder -> {
                setReminder()
            }
            R.id.ll_report -> {
                val i = Intent(this@HomeActivity, ReportActivity::class.java)
                startActivity(i)            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                val i = Intent(this@HomeActivity, ProfileActivity::class.java)
                startActivity(i)
            }
            R.id.nav_changempin -> {
                changeMpin()
            }
            R.id.nav_about -> {
                val i = Intent(this@HomeActivity, AboutUsActivity::class.java)
                startActivity(i)
            }
            R.id.nav_contact -> {
                val i = Intent(this@HomeActivity, ContactUsActivity::class.java)
                startActivity(i)
            }
            R.id.nav_share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.app_name) + "\t" + "Invite You  \n\n For Android Users \n\n http://play.google.com/store/apps/details?id=" + getPackageName() + "\n"
                )
                startActivity(Intent.createChooser(shareIntent, "Invite this App to your friends"))
            }
            R.id.nav_logout -> {
                doLogout()
            }
            R.id.nav_quit -> {
                quit()
            }
        }
        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
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
                startActivity(Intent(this@HomeActivity, WelcomeActivity::class.java))
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

    private fun changeMpin() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(true)
            dialog1 .setContentView(R.layout.changepin_popup)
            val btnreset = dialog1 .findViewById(R.id.btnreset) as Button
            val btnSubmit = dialog1 .findViewById(R.id.btnSubmit) as Button
            val etxt_oldpin = dialog1 .findViewById(R.id.etxt_oldpin) as EditText
            val etxt_newpin = dialog1 .findViewById(R.id.etxt_newpin) as EditText
            val etxt_confirmnewpin = dialog1 .findViewById(R.id.etxt_confirmnewpin) as EditText
            btnSubmit.setOnClickListener {
                if (etxt_oldpin!!.text.toString() == null || etxt_oldpin!!.text.toString().isEmpty()) {
                    etxt_oldpin!!.setError("Please Enter Your Old mPin.")
                }
                else if (etxt_newpin!!.text.toString() == null || etxt_newpin!!.text.toString().isEmpty()) {
                    etxt_newpin!!.setError("Please Enter Your New mPin.")
                }
                else if (etxt_confirmnewpin!!.text.toString() == null || etxt_confirmnewpin!!.text.toString().isEmpty()) {
                    etxt_confirmnewpin!!.setError("Please Confirm Your New mPin.")
                }
                else if (etxt_newpin!!.text.toString() != etxt_confirmnewpin!!.text.toString()) {
                    val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
                    dialog.setMessage("New & Confirm mPin doesn't match")
                    dialog.setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, which ->
                        })
                    val alertDialog: AlertDialog = dialog.create()
                    alertDialog.show()
                }
                else if (etxt_oldpin!!.text.toString().isNotEmpty() && etxt_oldpin!!.text.toString().length!=6) {
                    etxt_oldpin.setError("Please Enter 6 digit mPin")
                }
                else if (etxt_newpin!!.text.toString().isNotEmpty() && etxt_newpin!!.text.toString().length!=6) {
                    etxt_newpin.setError("Please Enter 6 digit mPin")
                }
                else if (etxt_confirmnewpin!!.text.toString().isNotEmpty() && etxt_confirmnewpin!!.text.toString().length!=6) {
                    etxt_confirmnewpin.setError("Please Enter 6 digit mPin")
                }else{
                    dialog1 .dismiss()
                    changempinverficationcode(etxt_oldpin!!.text.toString(),etxt_newpin!!.text.toString())
                }
            }
            btnreset.setOnClickListener {
                etxt_oldpin.setText("")
                etxt_newpin.setText("")
                etxt_confirmnewpin.setText("")
            }
            dialog1.show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var strOldMPIN= ""
        var strNewMPIN= ""
    }

    private fun changempinverficationcode(oldPin: String, newPin: String) {
        context = this@HomeActivity
        changempinViewModel = ViewModelProvider(this).get(ChangeMpinViewModel::class.java)
        strOldMPIN = oldPin
        strNewMPIN = newPin
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                changempinViewModel.changeMpin(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                var jobj = jObject.getJSONObject("MPINDetails")

                                val builder = AlertDialog.Builder(
                                    this@HomeActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jobj.getString("ResponseMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@HomeActivity,
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

    private fun getBannerlist() {
        context = this@HomeActivity
        bannerListViewModel = ViewModelProvider(this).get(BannerListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                bannerListViewModel.getBannerlist(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jsonObj: JSONObject = jObject.getJSONObject("BannerDetails")

                                val jresult = jsonObj.getJSONArray("BannerDetailsList")

                                for (i in 0 until jresult!!.length()) {
                                    try {
                                        val json = jresult!!.getJSONObject(i)

                                        var s = ""+ json.getString("ImagePath")

                                        XMENArray!!.add(s)
                                        mPager!!.adapter = BannerAdapter(
                                            this@HomeActivity,
                                            XMENArray
                                        )
                                        indicator!!.setViewPager(mPager)
                                        val handler = Handler()
                                        val Update = Runnable {
                                            if (currentPage === jresult!!.length()) {
                                                currentPage = 0
                                            }
                                            mPager!!.setCurrentItem(currentPage++, true)
                                        }
                                        val swipeTimer = Timer()
                                        swipeTimer.schedule(object : TimerTask() {
                                            override fun run() {
                                                handler.post(Update)
                                            }
                                        }, 3000, 3000)
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                           else {
                                val builder = AlertDialog.Builder(
                                    this@HomeActivity,
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

    override fun onBackPressed() {
        quit()
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
        beginTime.set(2019, 11 - 1, 28, 9, 30)
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

}


