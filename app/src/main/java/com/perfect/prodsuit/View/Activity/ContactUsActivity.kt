package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Base64
import android.util.Log
import androidx.lifecycle.Observer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.CompanyLogoViewModel
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ContactUsActivity : AppCompatActivity() , View.OnClickListener {

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
    private var chipNavigationBar: ChipNavigationBar? = null

    private var et_name: EditText? = null
    private var et_subject: EditText? = null
    private var et_msg: EditText? = null
    private var imback: ImageView? = null
    private var btnOk: Button? = null

    private var txt_name: TextView? = null
    private var txt_mobile: TextView? = null
    private var txt_email: TextView? = null
    private var txt_address: TextView? = null

    private var img_CompanyLogo: ImageView? = null
    private var img_technology: ImageView? = null
    lateinit var companyLogoViewModel: CompanyLogoViewModel
    var logo: String?=""

    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        setRegViews()
        context = this@ContactUsActivity
        bottombarnav()
        getCalendarId(context)

        setTechnologyPartner()
        getCompanyLogo()


    }

    private fun setRegViews() {
        et_name = findViewById(R.id.et_name)
        et_subject = findViewById(R.id.et_subject)
        et_msg = findViewById(R.id.et_msg)

        txt_name = findViewById(R.id.txt_name)
        txt_mobile = findViewById(R.id.txt_mobile)
        txt_email = findViewById(R.id.txt_email)
        txt_address = findViewById(R.id.txt_address)

        img_CompanyLogo = findViewById(R.id.img_CompanyLogo)
        img_technology = findViewById(R.id.img_technology)


        imback = findViewById(R.id.imback)
        btnOk = findViewById(R.id.btnOk)
        btnOk!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)

        val ResellerNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF32, 0)
        txt_name!!.text =  ResellerNameSP.getString("ResellerName", "")

        val ContactNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF33, 0)
        txt_mobile!!.text =  ContactNumberSP.getString("ContactNumber", "")

        val ContactEmailSP = applicationContext.getSharedPreferences(Config.SHARED_PREF34, 0)
        txt_email!!.text =  ContactEmailSP.getString("ContactEmail", "")

        val ContactAddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF35, 0)
        txt_address!!.text =  ContactAddressSP.getString("ContactAddress", "")

    }

    private fun setTechnologyPartner() {

        try {
            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
            val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
            var IMAGEURL = IMAGE_URLSP.getString("IMAGE_URL","")

            val TechnologyPartnerImage  = IMAGEURL + TechnologyPartnerImageSP.getString("TechnologyPartnerImage", "")
            PicassoTrustAll.getInstance(this@ContactUsActivity)!!.load(TechnologyPartnerImage).error(R.drawable.svg_trans).into(img_technology)

//            val AppIconImageCode  = IMAGEURL + AppIconImageCodeSP.getString("AppIconImageCode", "")
//            PicassoTrustAll.getInstance(this@HomeActivity)!!.load(AppIconImageCode).error(R.drawable.svg_trans).into(img_logo)
        }catch (e : Exception){

        }



    }

    private fun getCompanyLogo() {

        var bannerDetail = 0
        context = this@ContactUsActivity
        companyLogoViewModel = ViewModelProvider(this).get(CompanyLogoViewModel::class.java)


        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                companyLogoViewModel.getCompanylogoType(this)!!.observe(
                    this,
                    Observer { expenseSetterGetter ->
                        val msg = expenseSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            //  val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                            Log.e("TAG","msg  303   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CompanyLogDetails")


                                var count =jobjt.getString("CompanyLogo")
                                var type =jobjt.getString("DisplayType")
                                var companyname =jobjt.getString("CompanyName")

                                logo = jobjt!!.getString("CompanyLogo")
                                Log.e("DIL 731 ", count)

                                if(type.equals("0"))
                                {
                                    // tv_Status!!.visibility=View.GONE;
                                    if(!logo.equals(""))
                                    {
                                        val decodedString = Base64.decode(logo, Base64.DEFAULT)
                                        ByteArrayToBitmap(decodedString)
                                        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                                        val stream = ByteArrayOutputStream()
                                        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                        Glide.with(this) .load(stream.toByteArray()).into(img_CompanyLogo!!)

                                    }
                                }
//                                else
//                                {
//                                    if(!logo.equals(""))
//                                    {
//
//                                        val decodedString = Base64.decode(logo, Base64.DEFAULT)
//                                        ByteArrayToBitmap(decodedString)
//                                        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//                                        val stream = ByteArrayOutputStream()
//                                        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                                        Glide.with(this) .load(stream.toByteArray()).into(imv_2!!)
//
//
//                                    }
//                                  // tv_Status!!.visibility=View.VISIBLE;
//                                   // tv_Status!!.text=companyname
//                                }


                            }
                        } else {

                        }
                    })

            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun ByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.btnOk->{
                validation()
                et_msg!!.setText("")
                et_name!!.setText("")
                et_subject!!.setText("")

                Toast.makeText(applicationContext,"Work in progress",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMail(subject: String, msg: String){
        val to = "pssappfeedback@gmail.com"
        val subject = subject
        val message = msg
        val intent = Intent(Intent.ACTION_SEND)
        val addressees = arrayOf(to)
        intent.putExtra(Intent.EXTRA_EMAIL, addressees)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.setType("message/rfc822")
        startActivity(Intent.createChooser(intent, "Send Email using:"))
    }

    private fun validation(){
        if (et_name!!.text.toString() == null || et_name!!.text.toString().isEmpty()) {
            et_name!!.setError("Please Enter Your Name.")
        }
        else if (et_subject!!.text.toString() == null || et_subject!!.text.toString().isEmpty()) {
            et_subject!!.setError("Please Enter Your Subject.")
        }
        else if (et_msg!!.text.toString() == null || et_msg!!.text.toString().isEmpty()) {
            et_msg!!.setError("Please Enter Your Message.")
        }else{
            sendMail("ProdSuit - "+et_subject!!.text.toString(),et_msg!!.text.toString()+"\n\n"+et_name!!.text.toString())
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@ContactUsActivity, HomeActivity::class.java)
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
                startActivity(Intent(this@ContactUsActivity, WelcomeActivity::class.java))
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
}