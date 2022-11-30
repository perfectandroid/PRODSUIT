package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.OverDueListViewModel
import info.hoang8f.android.segmented.SegmentedGroup
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OverDueActivity : AppCompatActivity(), View.OnClickListener,ItemClickListener,
    RadioGroup.OnCheckedChangeListener {
    val TAG : String = "OverDueActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var overduelistViewModel: OverDueListViewModel
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

    private var messageType = "";
    private var messageDesc = "";
    private var cbWhat = "0";
    private var cbEmail = "0";
    private var cbMessage = "0";

    var sharedPreferences: SharedPreferences? =null
    var fab_Reminder: FloatingActionButton? = null
    internal var etdate: EditText? = null
    internal var ettime: EditText? = null
    internal var etdis: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_overdue)
        context = this@OverDueActivity
        sharedPreferences = context!!.getSharedPreferences("AgendaReminder", Context.MODE_PRIVATE)
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
            fab_Reminder = findViewById(R.id.fab_Reminder);

           imback!!.setOnClickListener(this)
            imgv_filter!!.setOnClickListener(this)
            imgv_sort!!.setOnClickListener(this)
            fab_Reminder!!.setOnClickListener(this)

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

                                        val editor = sharedPreferences!!.edit()
                                        editor.clear()
                                        editor.commit()

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
            R.id.fab_Reminder->{
                var strReminder: String=""
                var ii: Int=0
                val gson = Gson()
                val json = sharedPreferences!!.getString("Set", "")
                var lstChkArray = ArrayList<String>()
                if (json!!.isNotEmpty()){
                    val collectionType: Type = object : TypeToken<List<String?>?>() {}.getType()
                    val arrPackageData: List<String> = gson.fromJson(json, collectionType)
                    for (reminderText in arrPackageData) {
                        Log.e("lstChk_size   ","lstChk_size   "+reminderText)
                        if (ii==0){
                            ii++
                            strReminder = ii.toString()+" . "+reminderText
                        }else{
                            ii++
                            strReminder = strReminder+"\n"+"\n"+ii.toString()+" . "+reminderText
                        }
                        lstChkArray.add(reminderText)
                    }
                }

                if (!lstChkArray.isEmpty()){
                    //setReminder("You have set reminder for following defaulters accounts "+lstChkArray.toString())
                    Log.e(TAG,"strReminder   3411    "+strReminder)

                    setReminder("","",strReminder)
                }else{
                    Log.e(TAG,"strReminder   3412    Select")
                    Config.snackBars(context,v,"Select Atlest One Reminder")
                    //   Toast.makeText(context,"Select Account",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setReminder(ActionTypeName1 : String,EnquiryAbout1: String,descriptn: String) {
        try
        {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.reminder_agenda_popup, null)
            val btncancel = layout.findViewById(R.id.btncancel) as Button
            val btnsubmit = layout.findViewById(R.id.btnsubmit) as Button

            val tv_header = layout.findViewById(R.id.tv_header) as TextView
            etdate = layout.findViewById(R.id.etdate) as EditText
            ettime = layout.findViewById(R.id.ettime) as EditText
            etdis = layout.findViewById(R.id.etdis) as EditText

//            val desc = ActionTypeName1+"\n"+EnquiryAbout1+"\n"+Status1
            tv_header!!.setText("Reminder".toUpperCase())
            etdis!!.setText(descriptn)

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
            ettime!!.setOnClickListener(View.OnClickListener {
//                timeSelector()
                openBottomSheetTime()
            })
            etdate!!.setOnClickListener(View.OnClickListener {
//                dateSelector()
                sortFilter = 2
                openBottomSheet()
            })
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

    private fun openBottomSheetTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {

//                val hour: Int = time_Picker1!!.hour
//                val min: Int = time_Picker1!!.minute

                hr = time_Picker1!!.hour
                min = time_Picker1!!.minute

                val strTime = String.format(
                    "%02d:%02d %s", if (hr == 0) 12 else hr,
                    min, if (hr < 12) "AM" else "PM"
                )

                ettime!!.setText(strTime)


            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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

    private fun getCalendarId(context: Context): kotlin.Long? {

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
        if (data.equals("todocall")){

            val ALL_PERMISSIONS = 101

            val permissions = arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE
            )
            if (ContextCompat.checkSelfPermission(
                    this@OverDueActivity,
                    Manifest.permission.CALL_PHONE
                ) + ContextCompat.checkSelfPermission(
                    this@OverDueActivity,
                    Manifest.permission.RECORD_AUDIO
                )
                + ContextCompat.checkSelfPermission(
                    this@OverDueActivity,
                    Manifest.permission.READ_PHONE_STATE
                )
                + ContextCompat.checkSelfPermission(
                    this@OverDueActivity,
                    Manifest.permission.READ_CALL_LOG
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
            } else {
                val jsonObject = overdueArrayList.getJSONObject(position)
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

        if (data.equals("todoMessage")){
            val jsonObject = overdueArrayList.getJSONObject(position)
            Log.e("TAG","313  ID_LeadGenerate   :  "+jsonObject.getString("ID_LeadGenerate"))
            messagePopup()
        }
    }

    private fun messagePopup() {
        try {

            messageType = ""
            cbWhat = "0"
            cbEmail = "0"
            cbMessage = "0"

            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.send_message_popup)
            dialog1.window!!.attributes.gravity = Gravity.CENTER;

            val rbMessages = dialog1 .findViewById(R.id.rbMessages) as RadioButton
            val rbReminder = dialog1 .findViewById(R.id.rbReminder) as RadioButton
            val rbIntimation = dialog1 .findViewById(R.id.rbIntimation) as RadioButton

            val edt_message = dialog1 .findViewById(R.id.edt_message) as EditText

            val chk_whats = dialog1 .findViewById(R.id.chk_whats) as CheckBox
            val chk_Email = dialog1 .findViewById(R.id.chk_Email) as CheckBox
            val chk_Message = dialog1 .findViewById(R.id.chk_Message) as CheckBox

            val btnMssubmit = dialog1 .findViewById(R.id.btnMssubmit) as Button
            val btnMscancel = dialog1 .findViewById(R.id.btnMscancel) as Button

            val segmented2 = dialog1 .findViewById(R.id.segmented2) as SegmentedGroup
            segmented2.setTintColor(resources.getColor(R.color.color_msg_tab));
            segmented2.setOnCheckedChangeListener(this@OverDueActivity);

            rbMessages.isChecked  =true
            rbReminder.isChecked  =false
            rbIntimation.isChecked  =false

            chk_whats.setOnClickListener {
                if (chk_whats.isChecked){

                    cbWhat = "1"
                }else{
                    cbWhat = "0"
                }
            }

            chk_Email.setOnClickListener {
                if (chk_Email.isChecked){
                    cbEmail = "1"
                }else{
                    cbEmail = "0"
                }
            }

            chk_Message.setOnClickListener {

                if (chk_Message.isChecked){
                    cbMessage = "1"
                }else{
                    cbMessage = "0"
                }
            }

            btnMscancel.setOnClickListener {
                dialog1 .dismiss()
            }

            btnMssubmit.setOnClickListener {
                messageDesc = edt_message.text.toString()
                if (messageType.equals("")){

                }
                else if(messageDesc.equals("")){
                    Config.snackBars(context,it,"Please enter message")
//
                }
                else if (cbWhat.equals("0") && cbEmail.equals("0") && cbMessage.equals("0") ){
                    Config.snackBars(context,it,"Please select sending options")
//
                }
                else{
                    Log.e(TAG,"  927  messageType  "+messageType)
                    Log.e(TAG,"  927  messageDesc  "+messageDesc)
                    Log.e(TAG,"  927  HHHHH  "+cbWhat+"  :   "+cbEmail+"  :  "+cbMessage)

                    Config.Utils.hideSoftKeyBoard(context,it)
                    dialog1 .dismiss()
                    Toast.makeText(context,""+messageDesc,Toast.LENGTH_SHORT).show()
                }
            }

            dialog1.show()

//            val builder = AlertDialog.Builder(this)
//            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout = inflater1.inflate(R.layout.send_message_popup, null)
//
//            var  ss =inflater1.findViewById(android.R.id.segmented2).setTintColor(Color.DKGRAY)
//            builder.setView(layout)
//            val alertDialog = builder.create()
//            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
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
                if (sortFilter == 2){
                    etdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
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

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            R.id.rbMessages->{

                messageType = "Message"
                Log.e(TAG,"rbMessages")
            }
            R.id.rbReminder->{
                Log.e(TAG,"rbReminder")
                messageType = "Reminder"
            }
            R.id.rbIntimation->{
                Log.e(TAG,"rbIntimation")
                messageType = "Intimation"
            }
        }
    }

}