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
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.ProfileViewModel
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener,ItemClickListener {
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
    private var crdv_1: CardView? = null
    private var crdv_2: CardView? = null
    private var crdv_3: CardView? = null
    private var crdv_4: CardView? = null
    private var imback: ImageView? = null
    private var tv_dob: TextView? = null
    private var tv_name: TextView? = null
    private var tv_Status: TextView? = null
    private var tv_DateTime: TextView? = null
    private var tvdescriptn3: TextView? = null
    private var tv_address: TextView? = null
    private var tv_gender: TextView? = null
    private var tv_name1: TextView? = null
    private var tv_email: TextView? = null
    private var tv_address1: TextView? = null
    private var tv_email1: TextView? = null
    private var tv_mob2: TextView? = null
    private var tv_mob: TextView? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    lateinit var context: Context
    lateinit var profileViewModel: ProfileViewModel
    private var progressDialog: ProgressDialog? = null
    private var ll_address: LinearLayout? = null
    private var ll_dob: LinearLayout? = null
    private var ll_email: LinearLayout? = null
    private var ll_mob: LinearLayout? = null

    private var img_techpartner: ImageView? = null
//    private var img_logo: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_profile)
        setRegViews()
        context = this@ProfileActivity
        bottombarnav()
        getCalendarId(context)
        setTechnologyPartner()
        getProfile()

        val LOGIN_DATETIMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF30, 0)
        tv_DateTime!!.text = LOGIN_DATETIMESP.getString("LOGIN_DATETIME", "")
    }

    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)

//        tv_name= findViewById(R.id.tv_nme)
        tv_Status= findViewById(R.id.tv_Status)
        tv_DateTime= findViewById(R.id.tv_DateTime)
        tv_dob = findViewById(R.id.tv_dob)
        tvdescriptn3 = findViewById(R.id.tvdescriptn3)
        tv_address = findViewById(R.id.tv_address)
        tv_email = findViewById(R.id.tv_email)
        tv_mob = findViewById(R.id.tv_mob)

        tv_address1 = findViewById(R.id.tv_address1)
        tv_name1 = findViewById(R.id.tv_name1)
        tv_email1 = findViewById(R.id.tv_email1)
        tv_mob2 = findViewById(R.id.tv_mob2)
        img_techpartner= findViewById(R.id.img_techpartner)

        ll_address = findViewById(R.id.ll_address)
        ll_dob = findViewById(R.id.ll_dob)
        ll_email = findViewById(R.id.ll_email)
        ll_mob = findViewById(R.id.ll_mobb)

        crdv_1 = findViewById(R.id.crdv_1)
        crdv_2 = findViewById(R.id.crdv_2)
        crdv_3 = findViewById(R.id.crdv_3)
        crdv_4 = findViewById(R.id.crdv_4)


    }

    private fun setTechnologyPartner() {

        try {
            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
            val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
            var IMAGEURL = IMAGE_URLSP.getString("IMAGE_URL","")

            val TechnologyPartnerImage  = IMAGEURL + TechnologyPartnerImageSP.getString("TechnologyPartnerImage", "")
            PicassoTrustAll.getInstance(this@ProfileActivity)!!.load(TechnologyPartnerImage).error(R.drawable.svg_trans).into(img_techpartner)

        }catch (e : Exception){

        }



    }

    private fun getProfile() {

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                profileViewModel.getProfiledata(this)!!.observe(
                    this,
                    Observer { profileSetterGetter ->
                        val msg = profileSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                //   var jobj = jObject.getJSONObject("UserLoginDetails")
                                val jobjt = jObject.getJSONObject("EmployeeProfileDetails")
                                var name = jobjt.getString("Name")
                                var address = jobjt.getString("Address")
                                var mob = jobjt.getString("MobileNumber")
                                var email = jobjt.getString("Email")
                                var dob = jobjt.getString("EmpDOB")
                               //
                                // var dob = jobjt.getString("Email")
                                var dateTime = jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
                                val LoginMode = jobjt.getString("LoginMode")

                                Log.e("ss","eeee11111     "+LoginMode)

                                if (LoginMode.equals("0")){
                                    tv_Status!!.setText(jobjt.getString("LoginStatus"))
                                    tv_Status!!.setTextColor(resources.getColor(R.color.greydark))
                                    tv_Status!!.background = resources.getDrawable(R.drawable.rounded_offline)
                                    tv_Status!!.getCompoundDrawables()[0].setTint(resources.getColor(R.color.greydark))
                                }
                                else{
                                    tv_Status!!.setText(jobjt.getString("LoginStatus"))
                                    tv_Status!!.setTextColor(resources.getColor(R.color.green))
                                    tv_Status!!.background = resources.getDrawable(R.drawable.rounded_online)
                                    tv_Status!!.getCompoundDrawables()[0].setTint(resources.getColor(R.color.green))
                                }

                                Log.e("ss","rrrrrrrr     "+name)
                                Log.e("ss","rrrrrrrr     "+LoginMode)

                                tv_DateTime!!.setText(dateTime)
                                if(name!=null||name!="")
                                {
                                    tv_name1!!.visibility=View.VISIBLE
                                    crdv_1!!.visibility=View.VISIBLE
                                    tv_name1!!.text=name
                                }
                                else
                                {
                                    tv_name1!!.visibility=View.GONE
                                }

                                 if(address!=null||address!="")
                                {
                                    tv_address1!!.visibility=View.VISIBLE
                                    crdv_1!!.visibility=View.VISIBLE
                                    tv_address1!!.text=address

                                }
                                 else
                                 {
                                     crdv_1!!.visibility=View.GONE
                                     ll_address!!.visibility=View.GONE
                                 }
                                if(mob!=null||mob!="")
                                {
                                    ll_mob!!.visibility=View.VISIBLE
                                    crdv_4!!.visibility=View.VISIBLE
                                    tv_mob2!!.text=mob

                                }
                                else
                                {
                                    crdv_4!!.visibility=View.GONE
                                    ll_mob!!.visibility=View.GONE
                                }
                                if(email!=null&&!email.isEmpty())
                                {
                                    crdv_3!!.visibility=View.VISIBLE
                                    tv_email1!!.visibility=View.VISIBLE
                                    tv_email1!!.text=email

                                }
                                else
                                {
                                    crdv_3!!.visibility=View.GONE
                                    tv_email1!!.visibility=View.GONE
                                }
//                              if(dob!=null||dob!="")
//                                {
//                                    crdv_2!!.visibility=View.VISIBLE
//                                    ll_dob!!.visibility=View.VISIBLE
//                                    tv_dob!!.text=dob
//
//                                }
//                              else
//                              {
//                                  crdv_2!!.visibility=View.GONE
//                                  ll_dob!!.visibility=View.GONE
//                              }




                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ProfileActivity,
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
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@ProfileActivity, HomeActivity::class.java)
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
                startActivity(Intent(this@ProfileActivity, WelcomeActivity::class.java))
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

    override fun onClick(position: Int, data: String) {
        TODO("Not yet implemented")
    }
}