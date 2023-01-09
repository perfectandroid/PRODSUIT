package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.CalendarContract
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.BannerAdapter
import com.perfect.prodsuit.View.Adapter.NotificationAdapter
import com.perfect.prodsuit.Viewmodel.*
import me.relex.circleindicator.CircleIndicator
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var TAG = "HomeActivity"
    lateinit var context: Context
    lateinit var changempinViewModel: ChangeMpinViewModel
    lateinit var bannerListViewModel: BannerListViewModel
    private var progressDialog: ProgressDialog? = null
    private var drawer_layout: DrawerLayout? = null
    private var nav_view: NavigationView? = null
    private var btn_menu: ImageView? = null
    private var imgAttendance: ImageView? = null
    private var llservice: LinearLayout? = null
    private var rlnotification: RelativeLayout? = null
    private var ll_dashboard: LinearLayout? = null
    private var ll_agenda: LinearLayout? = null
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
    internal var tv_navName: TextView? = null
    internal var tv_navDateTime: TextView? = null
    internal var tv_navStatus: TextView? = null
    internal var tv_Name: TextView? = null
    internal var tv_DateTime: TextView? = null
    internal var txtv_notfcount: TextView? = null
    internal var tv_Status: TextView? = null
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
    val PERMISSION_REQUEST_WRITE_CALENDAR=2
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    val callbackId = 42
    val CALENDAR_PROJECTION=3
    val PERMISSION_ID = 42
    lateinit var notificationViewModel: NotificationViewModel
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var addresses: List<Address>? = null
    var geocoder: Geocoder? = null
    var address : String = ""
    var city : String = ""
    var state : String = ""
    var country : String = ""
    var postalCode : String = ""
    var knownName : String = ""
    var strLongitue : String = ""
    var strLatitude : String = ""
    var IsOnline : String = "2"
    var SubMode : String = ""

    lateinit var attendanceAddViewModel: AttendanceAddViewModel
    val UPDATE_INTERVAL = 1500L
    private val updateWidgetHandler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_homemain)

        attendanceAddViewModel = ViewModelProvider(this).get(AttendanceAddViewModel::class.java)

        setRegViews()
        bottombarnav()
        getBannerlist()
//        getNotfCount()
        getCalendarId(context)
        SubMode = "2"
        AddAttendanceApi(strLatitude,strLongitue,address)

    }

    private var updateWidgetRunnable: Runnable = Runnable {
        run {
            //Update UI
            // Re-run it after the update interval
            getNotfCount()
            updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
        }

    }

    override fun onResume() {
        super.onResume()
        getNotfCount()
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
    }


    // REMOVE callback if app in background
    override fun onPause() {
        super.onPause()
        updateWidgetHandler.removeCallbacks(updateWidgetRunnable);
    }

    private fun getNotfCount() {

        context = this@HomeActivity
        notificationViewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(this, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                notificationViewModel.getNotificaationlist(this)!!.observe(
                    this,
                    Observer { notificationSetterGetter ->
                        val msg = notificationSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("NotificationDetails")
                                var count =jobjt.getString("count")
                                Log.i("Array size", count)
                                txtv_notfcount!!.text=count


                            } else {
                                Log.e(TAG,"187777   "+jObject.getString("StatusCode"))
                                txtv_notfcount!!.text="0"
//                                val builder = AlertDialog.Builder(
//                                    this@HomeActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()

                            }
                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
           //     progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }


        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(i: Int) {
                when (i) {
//                    R.id.home -> {
////                        val i = Intent(this@HomeActivity, HomeActivity::class.java)
////                        startActivity(i)
//                        chipNavigationBar!!.setItemSelected(R.id.home, true)
//                    }
                    R.id.reminder -> {
                        setReminder()

                    }
                    R.id.logout -> {
                       // doLogout()
                        LogoutBottomSheet()
                    }
                    R.id.quit -> {
                       // quit()
                        QuitBottomSheet()
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
        rlnotification= findViewById(R.id.rlnotification)
        ll_dashboard = findViewById(R.id.ll_dashboard)
        ll_agenda = findViewById(R.id.ll_agenda)
        imgAttendance = findViewById(R.id.imgAttendance)
        tv_Name = findViewById(R.id.tv_Name)
        tv_DateTime = findViewById(R.id.tv_DateTime)
        tv_Status = findViewById(R.id.tv_Status)
        txtv_notfcount= findViewById(R.id.txtv_notfcount)

        val headerView: View = nav_view!!.getHeaderView(0)
        tv_navName = headerView!!.findViewById(R.id.tv_navName)
        tv_navDateTime = headerView!!.findViewById(R.id.tv_navDateTime)
        tv_navStatus = headerView!!.findViewById(R.id.tv_navStatus)

        btn_menu!!.setOnClickListener(this)
        lllead!!.setOnClickListener(this)
        llservice!!.setOnClickListener(this)
        ll_dashboard!!.setOnClickListener(this)
        ll_agenda!!.setOnClickListener(this)
        nav_view!!.setNavigationItemSelectedListener(this)
        nav_view!!.setItemIconTintList(null);
        mPager = findViewById(R.id.pager)
        indicator =findViewById(R.id.indicator)
        ll_reminder =findViewById(R.id.ll_reminder)
        ll_reminder!!.setOnClickListener(this)
        ll_report =findViewById(R.id.ll_report)
        ll_report!!.setOnClickListener(this)
        rlnotification!!.setOnClickListener(this)
        imgAttendance!!.setOnClickListener(this)
      //  txtv_notfcount!!.setOnClickListener(this)
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
            R.id.imgAttendance -> {
                Log.e("HomeActivity","imgAttendance    161  ")
               if (checkAndRequestPermissions()){
                   Log.e("HomeActivity","imgAttendance   Granted  161  ")
                   mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                 //  getLocation()

                   attendancConfirmPopup(v)
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
            R.id.ll_agenda -> {
//                https://github.com/PhilJay/MPAndroidChart
                val i = Intent(this@HomeActivity, AgendaActivity::class.java)
                startActivity(i)
            }
            R.id.ll_dashboard -> {
//                https://github.com/PhilJay/MPAndroidChart
                val i = Intent(this@HomeActivity, DashBoardActivity::class.java)
                startActivity(i)
            }
            R.id.ll_reminder -> {
                val i = Intent(this@HomeActivity, ExpenseActivity::class.java)
                startActivity(i)
            }
            R.id.ll_report -> {
//                val i = Intent(this@HomeActivity, ReportActivity::class.java)
//                startActivity(i)
                val i = Intent(this@HomeActivity, TicketReportActivity::class.java)
                startActivity(i)
            }
            R.id.rlnotification -> {
                val i = Intent(this@HomeActivity, NotificationActivity::class.java)
                startActivity(i)
            }
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
//                doLogout()
                LogoutBottomSheet()
            }
            R.id.nav_quit -> {
//                quit()
                QuitBottomSheet()
            }
        }
        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
    }

    private fun LogoutBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.logout_popup, null)

        val btnNo = view.findViewById<Button>(R.id.btnNo)
        val btnYes = view.findViewById<Button>(R.id.btnYes)

        btnNo.setOnClickListener {
            dialog .dismiss()
            chipNavigationBar!!.setItemSelected(R.id.home, true)
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
           // dologoutchanges()
            Config.logOut(context)
            startActivity(Intent(this@HomeActivity, SplashActivity::class.java))
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }
    private fun QuitBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.quit_popup, null)

        val btnNo = view.findViewById<Button>(R.id.btn_No)
        val btnYes = view.findViewById<Button>(R.id.btn_Yes)

        btnNo.setOnClickListener {
            dialog .dismiss()
            chipNavigationBar!!.setItemSelected(R.id.home, true)
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            finish()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity()
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
                chipNavigationBar!!.setItemSelected(R.id.home, true)
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
//                dologoutchanges()
                Config.logOut(context)
                startActivity(Intent(this@HomeActivity, SplashActivity::class.java))
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

        val companyCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF17, 0)
        val companyCodeEditer = companyCodeSP.edit()
        companyCodeEditer.putString("companyCode", "")
        companyCodeEditer.commit()

        val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
        val commonAppEditer = commonAppSP.edit()
        commonAppEditer.putString("commonApp", "")
        commonAppEditer.commit()
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
                chipNavigationBar!!.setItemSelected(R.id.home, true)
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
                    changempinverficationcode(etxt_oldpin!!.text.toString(), etxt_newpin!!.text.toString())
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
                changempinViewModel.changeMpin(this,strOldMPIN, strNewMPIN)!!.observe(
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
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
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
        var bannerDetail = 0
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
                                    if (jresult.length() > 0) {
                                        if (bannerDetail == 0) {
                                            bannerDetail++

                                            for (i in 0 until jresult!!.length()) {
                                                try {
                                                    val json = jresult!!.getJSONObject(i)
                                                    var s = "" + json.getString("ImagePath")

                                                    XMENArray!!.add(s)
                                                    mPager!!.adapter = BannerAdapter(
                                                            this@HomeActivity,
                                                            XMENArray
                                                    )
                                                    indicator!!.setViewPager(mPager)


                                                } catch (e: Exception) {

                                                }
                                            }
                                            //  mPager!!.setPageTransformer(true, CubeInScalingAnimation())
                                            val handler = Handler()
                                            val Update = Runnable {
                                                //Log.e("TAG","currentPage  438   "+currentPage+"   "+jresult!!.length())
                                                if (currentPage == jresult!!.length()) {
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


//                                        for (i in 0 until jresult!!.length()) {
//                                            try {
//                                                val json = jresult!!.getJSONObject(i)
//
//                                                var s = ""+ json.getString("ImagePath")
//
//                                                XMENArray!!.add(s)
//                                                mPager!!.adapter = BannerAdapter(
//                                                    this@HomeActivity,
//                                                    XMENArray
//                                                )
//                                                indicator!!.setViewPager(mPager)
//                                                val handler = Handler()
//                                                val Update = Runnable {
//                                                    Log.e("TAG","currentPage  438   "+currentPage+"   "+jresult!!.length())
//                                                    if (currentPage == jresult!!.length()) {
//                                                        currentPage = 0
//                                                    }
//                                                    mPager!!.setCurrentItem(currentPage++, true)
//                                                }
//                                                val swipeTimer = Timer()
//                                                swipeTimer.schedule(object : TimerTask() {
//                                                    override fun run() {
//                                                        handler.post(Update)
//                                                    }
//                                                }, 3000, 3000)
//                                            } catch (e: JSONException) {
//                                                e.printStackTrace()
//                                            }
//
//                                        }
                                        }
                                    }

                                } else {
//                                    val builder = AlertDialog.Builder(
//                                            this@HomeActivity,
//                                            R.style.MyDialogTheme
//                                    )
//                                    builder.setMessage(jObject.getString("EXMessage"))
//                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    }
//                                    val alertDialog: AlertDialog = builder.create()
//                                    alertDialog.setCancelable(false)
//                                    alertDialog.show()
                                }
                            } else {
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
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
//        quit()
        QuitBottomSheet()
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
                chipNavigationBar!!.setItemSelected(R.id.home, true)
                alertDialog.dismiss() }
            btnsubmit.setOnClickListener {
                Config.Utils.hideSoftKeyBoard(this, it)
                addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                alertDialog.dismiss()
            }
            alertDialog.setCancelable(false)
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

    private fun attendancConfirmPopup(v : View ){
        try {

            val dialogCountry = Dialog(this)
            dialogCountry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCountry!! .setContentView(R.layout.attendance_popup)
            dialogCountry!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val btn_online_Yes = dialogCountry .findViewById(R.id.btn_online_Yes) as Button
            val btn_online_No = dialogCountry .findViewById(R.id.btn_online_No) as Button
            btn_online_No.setOnClickListener {
                dialogCountry .dismiss()
            }
            btn_online_Yes.setOnClickListener {
                dialogCountry.dismiss()
                SubMode = "1"
                getLocation(v)
            }

            dialogCountry!!.show()
            dialogCountry!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogCountry!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun checkAndRequestPermissions(): Boolean {
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarsePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(v : View) {

//        address      = ""
//        city         = ""
//        state        = ""
//        country      = ""
//        postalCode   = ""
//        knownName    = ""
//        strLongitue  = ""
//        strLatitude  = ""

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
//                        txtLongitude!!.text = location.longitude.toString()
//                        txtLatitude!!.text = location.latitude.toString()

                        Log.e("HOEMACTIVITY","longitude  819    "+location.longitude)
                        Log.e("HOEMACTIVITY","latitude   819    "+location.latitude)


                        geocoder = Geocoder(this, Locale.getDefault())
                        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
                        address = addresses!!.get(0).getAddressLine(0)
                        city = addresses!!.get(0).locality
                        state = addresses!!.get(0).adminArea
                        country = addresses!!.get(0).countryName
                        postalCode = addresses!!.get(0).postalCode
                        knownName = addresses!!.get(0).featureName
                        strLongitue = location.longitude.toString()
                        strLatitude = location.latitude.toString()

                        validLocation(v)


                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun validLocation(v : View) {

        if (strLatitude.equals("") || strLongitue.equals("")){
            Config.snackBars(context,v,"Location Not Found")
        }else{

            AddAttendanceApi(strLatitude,strLongitue,address)
        }
    }



    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {


            try {
                var location: Location = locationResult.lastLocation
                Log.e("HOEMACTIVITY","longitude  8191    "+location.longitude)
                Log.e("HOEMACTIVITY","latitude   8191    "+location.latitude)
                geocoder = Geocoder(this@HomeActivity, Locale.getDefault())
                addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
                address = addresses!!.get(0).getAddressLine(0)
                city = addresses!!.get(0).locality
                state = addresses!!.get(0).adminArea
                country = addresses!!.get(0).countryName
                postalCode = addresses!!.get(0).postalCode
                knownName = addresses!!.get(0).featureName
                strLongitue = location.longitude.toString()
                strLatitude = location.latitude.toString()
            }catch (e : Exception){
                Log.e("HOEMACTIVITY","Exception  8191    "+e.toString())
            }



        }
    }


    private fun AddAttendanceApi(strLatitude: String, strLongitue: String, address: String) {

        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        val LOGIN_DATETIMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF30, 0)
        tv_Name!!.text = UserNameSP.getString("UserName", "")
        tv_navName!!.text = UserNameSP.getString("UserName", "")
        tv_DateTime!!.text = LOGIN_DATETIMESP.getString("LOGIN_DATETIME", "")
        tv_navDateTime!!.text = LOGIN_DATETIMESP.getString("LOGIN_DATETIME", "")

//        var addAttendan = 0
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//
//                attendanceAddViewModel.AddAttendance(this,IsOnline!!,strLatitude!!,strLongitue!!,address!!,SubMode)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//
//
//                            val jObject = JSONObject(msg)
//                            Log.e("HOMEACTIVITY","msg   1026   "+msg.length)
//                            Log.e("HOMEACTIVITY","msg   1026   "+msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("UpdateUserLoginStatus")
//
//                                tv_Name!!.text = jobjt.getString("Name")
//                                tv_navName!!.text = jobjt.getString("Name")
////                                tv_DateTime!!.text = "On Duty from "+jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
//                                tv_Status!!.text = jobjt.getString("LoginStatus")
//
//                                if (jobjt.getString("LoginMode").equals("1")){
//                                    imgAttendance!!.setImageResource(R.drawable.finger_online)
//                                    tv_DateTime!!.text = jobjt.getString("DutyStatus")+" \n "+jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
//                                    tv_navStatus!!.text = jobjt.getString("DutyStatus")
//                                    tv_navDateTime!!.text = jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
//                                    tv_navStatus!!.getCompoundDrawables()[0].setTint(resources.getColor(R.color.green))
//                                }else{
//                                    imgAttendance!!.setImageResource(R.drawable.finger_offline)
//                                    tv_DateTime!!.text = jobjt.getString("DutyStatus")+" "+jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
//                                    tv_navStatus!!.text = jobjt.getString("DutyStatus")
//                                    tv_navDateTime!!.text = jobjt.getString("LoginDate")+" "+jobjt.getString("LoginTime")
//                                    tv_navStatus!!.getCompoundDrawables()[0].setTint(resources.getColor(R.color.greydark))
//                                }
////                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
////                                if (leadFromArrayList.length()>0){
////                                    if (countLeadFrom == 0){
////                                        countLeadFrom++
////                                        leadFromPopup(leadFromArrayList)
////                                    }
////
////                                }
//
//                                val LS_LocLatitudeSP = context.getSharedPreferences(Config.SHARED_PREF17, 0)
//                                val LS_LocLatitudeEditer = LS_LocLatitudeSP.edit()
//                                LS_LocLatitudeEditer.putString("LocLatitude", jobjt.getString("LocLatitude"))
//                                LS_LocLatitudeEditer.commit()
//
//                                val LS_LocLongitudeSP = context.getSharedPreferences(Config.SHARED_PREF18, 0)
//                                val LS_LocLongitudeEditer = LS_LocLongitudeSP.edit()
//                                LS_LocLongitudeEditer.putString("LocLongitude", jobjt.getString("LocLongitude"))
//                                LS_LocLongitudeEditer.commit()
//
//                                val LS_LocationNameSP = context.getSharedPreferences(Config.SHARED_PREF19, 0)
//                                val LS_LocationNameEditer = LS_LocationNameSP.edit()
//                                LS_LocationNameEditer.putString("LocationName",jobjt.getString("LocationName"))
//                                LS_LocationNameEditer.commit()
//
//                                val LS_FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF20, 0)
//                                val LS_FK_EmployeeEditer = LS_FK_EmployeeSP.edit()
//                                LS_FK_EmployeeEditer.putString("FK_Employee", jobjt.getString("FK_Employee"))
//                                LS_FK_EmployeeEditer.commit()
//
//                                val LS_NameSP = context.getSharedPreferences(Config.SHARED_PREF21, 0)
//                                val LS_NameEditer = LS_NameSP.edit()
//                                LS_NameEditer.putString("Name", jobjt.getString("Name"))
//                                LS_NameEditer.commit()
//
//                                val LS_AddressSP = context.getSharedPreferences(Config.SHARED_PREF22, 0)
//                                val LS_AddressEditer = LS_AddressSP.edit()
//                                LS_AddressEditer.putString("Address", jobjt.getString("Address"))
//                                LS_AddressEditer.commit()
//
//                                val LS_LoginDateSP = context.getSharedPreferences(Config.SHARED_PREF23, 0)
//                                val LS_LoginDateEditer = LS_LoginDateSP.edit()
//                                LS_LoginDateEditer.putString("LoginDate", jobjt.getString("LoginDate"))
//                                LS_LoginDateEditer.commit()
//
//                                val LS_LoginTimeSP = context.getSharedPreferences(Config.SHARED_PREF24, 0)
//                                val LS_LoginTimeEditer = LS_LoginTimeSP.edit()
//                                LS_LoginTimeEditer.putString("LoginTime", jobjt.getString("LoginTime"))
//                                LS_LoginTimeEditer.commit()
//
//                                val LS_LoginModeSP = context.getSharedPreferences(Config.SHARED_PREF25, 0)
//                                val LS_LoginModeEditer = LS_LoginModeSP.edit()
//                                LS_LoginModeEditer.putString("LoginMode", jobjt.getString("LoginMode"))
//                                LS_LoginModeEditer.commit()
//
//                                val LS_LoginStauatsSP = context.getSharedPreferences(Config.SHARED_PREF26, 0)
//                                val LS_LoginStauatsEditer = LS_LoginStauatsSP.edit()
//                                LS_LoginStauatsEditer.putString("LoginStatus", jobjt.getString("LoginStatus"))
//                                LS_LoginStauatsEditer.commit()
//
//                                val LS_DutyStatusSP = context.getSharedPreferences(Config.SHARED_PREF27, 0)
//                                val LS_DutyStatusEditer = LS_DutyStatusSP.edit()
//                                LS_DutyStatusEditer.putString("DutyStatus", jobjt.getString("DutyStatus"))
//                                LS_DutyStatusEditer.commit()
//
//                            } else {
//                                val builder = AlertDialog.Builder(
//                                    this@HomeActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//
//        }

    }

}


