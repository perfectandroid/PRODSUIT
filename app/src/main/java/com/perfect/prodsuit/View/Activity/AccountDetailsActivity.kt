package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.CalendarContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import info.hoang8f.android.segmented.SegmentedGroup
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AccountDetailsActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener,
    OnMapReadyCallback, RadioGroup.OnCheckedChangeListener {


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
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    var llHistory: LinearLayout? = null
    var ll_call: LinearLayout? = null
    var ll_meet: LinearLayout? = null
    var llMainDetail: LinearLayout? = null
    lateinit var locationViewModel: LocationViewModel
    lateinit var activitylistViewModel: ActivityListViewModel
    lateinit var followuptypeViewModel: FollowUpTypeViewModel
    var llMessages: LinearLayout? = null
    var llLocation: LinearLayout? = null
    var llImages: LinearLayout? = null
    var recyAccountDetail: RecyclerView? = null
    var recyHistory: RecyclerView? = null
    lateinit var jsonArray : JSONArray
    var jsonObj: JSONObject? = null
    lateinit var leadHistoryViewModel: LeadHistoryViewModel
    lateinit var leadInfoViewModel: LeadInfoViewModel
    lateinit var infoViewModel: InfoViewModel
    lateinit var quotationlistViewModel: QuotationListViewModel
    lateinit var documentViewModel: DocumentListViewModel
    lateinit var historyActViewModel: HistoryActViewModel
    lateinit var imageViewModel: ImageViewModel
    lateinit var documentDetailViewModel: DocumentDetailViewModel
    lateinit var viewDocumentViewModel: ViewDocumentViewModel
    lateinit var notelistViewModel: NoteListViewModel
    lateinit var deleteLeadViewModel:DeleteLeadViewModel
    lateinit var leadHistoryArrayList : JSONArray
    lateinit var leadInfoArrayList : JSONArray
    lateinit var infoArrayList : JSONArray
    lateinit var agendaActionArrayList : JSONArray
    lateinit var quotationArrayList : JSONArray
    lateinit var historyActArrayList : JSONArray
    lateinit var documentDetailArrayList : JSONArray
    lateinit var documentArrayList : JSONArray
    lateinit var reasonArrayList : JSONArray
    lateinit var leadDeleteReasonViewModel: LeadDeleteReasonViewModel
    private var fab_main : FloatingActionButton? = null
    private var fabAddNote : FloatingActionButton? = null
    private var fabAddActivities : FloatingActionButton? = null
    private var fabAddNextAction : FloatingActionButton? = null
    private var fabAddNewAction : FloatingActionButton? = null
    private var fabAddDocument : FloatingActionButton? = null
    private var fabAddQuotation : FloatingActionButton? = null
    private var fabdlteLead : FloatingActionButton? = null
    private var fabCloseLead : FloatingActionButton? = null
    private var rltv_subfab : RelativeLayout? = null
    private var fab_open : Animation? = null
    private var fab_close : Animation? = null
    private var fab_clock : Animation? = null
    private var fab_anticlock : Animation? = null
    private var txtCloseLead : TextView? = null
    private var txtEditLead : TextView? = null
    private var txtAddQuotation : TextView? = null
    private var txtAddDocument : TextView? = null
    private var txtAddActivities : TextView? = null
    private var txtAddNextAction : TextView? = null
    private var txtAddNewAction : TextView? = null
    private var txtAddNote : TextView? = null
    private var txtName : TextView? = null
    private var txtAddress : TextView? = null
    private var txtPhone : TextView? = null
    private var txtEmail : TextView? = null
    private var txtLeadNo : TextView? = null
    private var txtCategory : TextView? = null
    private var txtProduct : TextView? = null
    private var txtTargetDate : TextView? = null
    private var txtAction : TextView? = null
    private var tabLayout : TabLayout? = null
    var recyActionType: RecyclerView? = null
    lateinit var activityArrayList : JSONArray
    lateinit var followupDetailArrayList : JSONArray
    lateinit var followupDetailSort : JSONArray
    var recyAgendaDetail: RecyclerView? = null
    var SubMode : String?= ""
    var llCall : LinearLayout? = null
    var ll_msg : LinearLayout? = null
    var llMessage : LinearLayout? = null
    var llMeeting : LinearLayout? = null
    var imActCall : ImageView? = null
    var imActMessage : ImageView? = null
    var imActMeeting : ImageView? = null
    var recyActCall  : RecyclerView? = null
    var recyActMessage  : RecyclerView? = null
    var recyActMeeting   : RecyclerView? = null
    private var isOpen  : Boolean? = true
    lateinit var context: Context
    var latitude=""
    var landmark: String?=""
    var landmark2: String?=""
    var followuptypeAction : Dialog? = null
    lateinit var agendaDetailViewModel: AgendaDetailViewModel
    var longitude=""
    var jobjt:JSONObject?=null
    var agendaTypeClick1 : String?= "0"
    lateinit var agendaActionViewModel: AgendaActionViewModel
    private var Id_leadgenrteprod: String? = null
    var agendaTypeClick : String?= "0"
    var rv_activity:RecyclerView?=null
    var tv_actionType:TextView?=null
    var imgv_1:ImageView?=null
    var imgv_2:ImageView?=null
    var crdv_1:CardView?=null
    var crdv_2:CardView?=null

    private var messageType = "";
    private var messageDesc = "";
    private var cbWhat = "0";
    private var cbEmail = "0";
    private var cbMessage = "0";

    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    var ID_LeadDocumentDetails : String = ""
    var DocumentImageFormat : String = ""
    private var destination: File? = null

    private lateinit var mMap: GoogleMap
    internal var mCurrLocationMarker: Marker? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var currentLocation: Location
    companion object{
        var ID_LeadGenerateProduct :String = ""
        var ID_LeadGenerate :String = ""
        var LeadNo :String = ""
        var LgCusMobile :String = ""
        var LgCusEmail  :String = ""
        var strid= ""
        var ID_ActionType : String?= ""
    }

    var ID_Reason: String?= ""
    var tie_DeleteReason:TextInputEditText?=null
    var recyDelateReason :RecyclerView?=null
    var tv_leadNumber :TextView?=null
    var leadDelete = 0
    var deleteLead = 0

    var AssignedToID : String?= ""
    var AssignedTo : String?= ""

    private var rltv_Info : RelativeLayout? = null
    private var ll_location : LinearLayout? = null
    private var ll_history : LinearLayout? = null
    private var ll_images : LinearLayout? = null
    private var rltv_document : RelativeLayout? = null

    var imageDet = 0
    var documentDet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_account_details)

        context = this@AccountDetailsActivity
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        leadHistoryViewModel = ViewModelProvider(this).get(LeadHistoryViewModel::class.java)
        leadDeleteReasonViewModel = ViewModelProvider(this).get(LeadDeleteReasonViewModel::class.java)
        leadInfoViewModel = ViewModelProvider(this).get(LeadInfoViewModel::class.java)
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        documentViewModel = ViewModelProvider(this).get(DocumentListViewModel::class.java)
        quotationlistViewModel = ViewModelProvider(this).get(QuotationListViewModel::class.java)
        historyActViewModel = ViewModelProvider(this).get(HistoryActViewModel::class.java)
        activitylistViewModel = ViewModelProvider(this).get(ActivityListViewModel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        notelistViewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        agendaActionViewModel = ViewModelProvider(this).get(AgendaActionViewModel::class.java)
        agendaDetailViewModel = ViewModelProvider(this).get(AgendaDetailViewModel::class.java)
        followuptypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        imageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
        documentDetailViewModel = ViewModelProvider(this).get(DocumentDetailViewModel::class.java)
        viewDocumentViewModel = ViewModelProvider(this).get(ViewDocumentViewModel::class.java)
        deleteLeadViewModel= ViewModelProvider(this).get(DeleteLeadViewModel::class.java)

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        Log.e(TAG,"jsonObj  123456 "+jsonObj)

        ID_LeadGenerateProduct = jsonObj!!.getString("ID_LeadGenerateProduct")
        ID_LeadGenerate = jsonObj!!.getString("ID_LeadGenerate")
        LeadNo = jsonObj!!.getString("LeadNo")
        AssignedToID = jsonObj!!.getString("FK_Employee")
        AssignedTo = jsonObj!!.getString("AssignedTo")

        setRegViews()
        bottombarnav()
        fabOpenClose()
        getLeadInfoetails()
        getCalendarId(context)
        addTabItem()


    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Info"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("History"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Location"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Images"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Documents"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        rltv_Info!!.visibility = View.VISIBLE
        getInfoetails()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
               Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
               //     llMainDetail!!.removeAllViews()

                   rltv_Info!!.visibility = View.VISIBLE
                    ll_location!!.visibility = View.GONE
                    ll_history!!.visibility = View.GONE
                    ll_images!!.visibility = View.GONE
                    rltv_document!!.visibility = View.GONE
                   getInfoetails()

                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
              //      llMainDetail!!.removeAllViews()
                    rltv_Info!!.visibility = View.GONE
                    ll_location!!.visibility = View.GONE
                    ll_history!!.visibility = View.VISIBLE
                    ll_images!!.visibility = View.GONE
                    rltv_document!!.visibility = View.GONE
                    getActivityDetails1()
//                    getActivitylist("0")

                }
                if (tab.position == 2){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                 //   llMainDetail!!.removeAllViews()
                    rltv_Info!!.visibility = View.GONE
                    ll_location!!.visibility = View.VISIBLE
                    ll_history!!.visibility = View.GONE
                    ll_images!!.visibility = View.GONE
                    rltv_document!!.visibility = View.GONE
                    getLocation()

                }
                if (tab.position == 3){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                 //   llMainDetail!!.removeAllViews()
                    rltv_Info!!.visibility = View.GONE
                    ll_location!!.visibility = View.GONE
                    ll_history!!.visibility = View.GONE
                    ll_images!!.visibility = View.VISIBLE
                    rltv_document!!.visibility = View.GONE

                    imageDet = 0
                    getImages()
                }
                if (tab.position == 4){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
               //     llMainDetail!!.removeAllViews()

                    rltv_Info!!.visibility = View.GONE
                    ll_location!!.visibility = View.GONE
                    ll_history!!.visibility = View.GONE
                    ll_images!!.visibility = View.GONE
                    rltv_document!!.visibility = View.VISIBLE
                    documentDet = 0
                    getDocuments()


                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })
    }




    private fun fabOpenClose() {
        if (isOpen!!) {

            fabdlteLead!!.visibility = View.GONE
            txtEditLead!!.visibility = View.GONE
            fabAddActivities!!.visibility = View.GONE
            txtAddActivities!!.visibility = View.GONE
            fabAddNote!!.visibility = View.GONE
            txtAddNote!!.visibility = View.GONE
            rltv_subfab!!.visibility = View.GONE

            fabAddNote!!.startAnimation(fab_close);
            fabAddActivities!!.startAnimation(fab_close);
            fabAddNextAction!!.startAnimation(fab_close);
            fabAddNewAction!!.startAnimation(fab_close);
            fabAddDocument!!.startAnimation(fab_close);
            fabAddQuotation!!.startAnimation(fab_close);
            fabdlteLead!!.startAnimation(fab_close);
            fabCloseLead!!.startAnimation(fab_close);
            txtEditLead!!.startAnimation(fab_close)
            txtCloseLead!!.startAnimation(fab_close)
            txtAddQuotation!!.startAnimation(fab_close)
            txtAddDocument!!.startAnimation(fab_close)
            txtAddActivities!!.startAnimation(fab_close)
            txtAddNextAction!!.startAnimation(fab_close)
            txtAddNewAction!!.startAnimation(fab_close)
            txtAddNote!!.startAnimation(fab_close)
            fab_main!!.startAnimation(fab_anticlock);
            fabAddNote!!.setClickable(false);
            fabAddActivities!!.setClickable(false);
            fabAddNextAction!!.setClickable(false);
            fabAddNewAction!!.setClickable(false);
            fabAddDocument!!.setClickable(false);
            fabAddQuotation!!.setClickable(false);
            fabdlteLead!!.setClickable(false);
            fabCloseLead!!.setClickable(false);
            isOpen = false;


        } else {

            fabdlteLead!!.visibility = View.VISIBLE
            txtEditLead!!.visibility = View.VISIBLE
            fabAddActivities!!.visibility = View.VISIBLE
            txtAddActivities!!.visibility = View.VISIBLE
            fabAddNote!!.visibility = View.VISIBLE
            txtAddNote!!.visibility = View.VISIBLE
            rltv_subfab!!.visibility = View.VISIBLE

            fabAddNote!!.startAnimation(fab_open);
            fabAddActivities!!.startAnimation(fab_open);
            fabAddNextAction!!.startAnimation(fab_open);
            fabAddNewAction!!.startAnimation(fab_open);
            fabAddDocument!!.startAnimation(fab_open);
            fabAddQuotation!!.startAnimation(fab_open);
            fabdlteLead!!.startAnimation(fab_open);
            fabCloseLead!!.startAnimation(fab_open);
            txtCloseLead!!.startAnimation(fab_open)
            txtEditLead!!.startAnimation(fab_open)
            txtAddQuotation!!.startAnimation(fab_open)
            txtAddDocument!!.startAnimation(fab_open)
            txtAddActivities!!.startAnimation(fab_open)
            txtAddNextAction!!.startAnimation(fab_open)
            txtAddNewAction!!.startAnimation(fab_open)
            txtAddNote!!.startAnimation(fab_open)
            fab_main!!.startAnimation(fab_clock);
            fabAddNote!!.setClickable(true);
            fabAddActivities!!.setClickable(true);
            fabAddNextAction!!.setClickable(true);
            fabAddNewAction!!.setClickable(true);
            fabAddDocument!!.setClickable(true);
            fabAddQuotation!!.setClickable(true);
            fabdlteLead!!.setClickable(true);
            fabCloseLead!!.setClickable(true);
            isOpen = true;
        }
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        ll_call = findViewById<LinearLayout>(R.id.ll_call)
        ll_meet = findViewById<LinearLayout>(R.id.ll_meet)
        llHistory = findViewById<LinearLayout>(R.id.llHistory)
        ll_msg = findViewById<LinearLayout>(R.id.ll_msg)
        llMainDetail = findViewById<LinearLayout>(R.id.llMainDetail)
        llMessages = findViewById<LinearLayout>(R.id.llMessages)
        llLocation = findViewById<LinearLayout>(R.id.llLocation)
        llImages = findViewById<LinearLayout>(R.id.llImages)
        recyAccountDetail = findViewById<RecyclerView>(R.id.recyAccountDetail)
        recyHistory = findViewById<RecyclerView>(R.id.recyHistory)
        recyAgendaDetail = findViewById(R.id.recyAgendaDetail)
        imback!!.setOnClickListener(this)
        fab_main = findViewById(R.id.fab);
        fabAddNote = findViewById(R.id.fabAddNote);
        fabAddActivities = findViewById(R.id.fabAddActivities);
        fabAddNextAction = findViewById(R.id.fabAddNextAction);
        fabAddNewAction = findViewById(R.id.fabAddNewAction);
        fabAddDocument = findViewById(R.id.fabAddDocument);
        fabAddQuotation = findViewById(R.id.fabAddQuotation);
        fabdlteLead = findViewById(R.id.fabdlteLead);
        fabCloseLead = findViewById(R.id.fabCloseLead);
        rltv_subfab = findViewById(R.id.rltv_subfab);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        txtCloseLead = findViewById(R.id.txtCloseLead);
        txtEditLead = findViewById(R.id.txtEditLead);
        txtAddQuotation = findViewById(R.id.txtAddQuotation);
        txtAddDocument = findViewById(R.id.txtAddDocument);
        txtAddActivities = findViewById(R.id.txtAddActivities);
        txtAddNextAction = findViewById(R.id.txtAddNextAction);
        txtAddNewAction = findViewById(R.id.txtAddNewAction);
        txtAddNote = findViewById(R.id.txtAddNote);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtLeadNo = findViewById(R.id.txtLeadNo);
        txtCategory = findViewById(R.id.txtCategory);
        txtProduct = findViewById(R.id.txtProduct);
        txtTargetDate = findViewById(R.id.txtTargetDate);
        txtAction = findViewById(R.id.txtAction);


        rltv_Info = findViewById(R.id.rltv_Info);
        ll_location = findViewById(R.id.ll_location);
        ll_history = findViewById(R.id.ll_history);
        ll_images = findViewById(R.id.ll_images);
        rltv_document = findViewById(R.id.rltv_document);


        tabLayout = findViewById(R.id.tabLayout);
        fab_main!!.setOnClickListener(this)
        fabAddNote!!.setOnClickListener(this)
        fabAddActivities!!.setOnClickListener(this)
        fabAddNextAction!!.setOnClickListener(this)
        fabAddNewAction!!.setOnClickListener(this)
        fabAddDocument!!.setOnClickListener(this)
        fabAddQuotation!!.setOnClickListener(this)
        fabdlteLead!!.setOnClickListener(this)
        fabCloseLead!!.setOnClickListener(this)
        llMessages!!.setOnClickListener(this)
        llLocation!!.setOnClickListener(this)
        llImages!!.setOnClickListener(this)
        ll_msg!!.setOnClickListener(this)
        txtEditLead!!.setOnClickListener(this)
        txtAddActivities!!.setOnClickListener(this)
        txtAddNote!!.setOnClickListener(this)

     //   tv_actionType!!.setOnClickListener(this)

        ll_call!!.setOnClickListener(this)
        ll_meet!!.setOnClickListener(this)

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
            R.id.fabAddNote->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE

//                val i = Intent(this@AccountDetailsActivity, AddNoteActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
//                startActivity(i)

                val i = Intent(this@AccountDetailsActivity, AddDocumentActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                startActivity(i)

            }
            R.id.txtAddNote->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE

//                val i = Intent(this@AccountDetailsActivity, AddNoteActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
//                startActivity(i)

                val i = Intent(this@AccountDetailsActivity, AddDocumentActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                startActivity(i)

            }
            R.id.fabAddActivities->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
//                val i = Intent(this@AccountDetailsActivity, SiteVisitActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
//                startActivity(i)

                val i = Intent(this@AccountDetailsActivity, FollowUpActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                i.putExtra("ID_LeadGenerate",ID_LeadGenerate)
                i.putExtra("FK_Employee",AssignedToID)
                i.putExtra("AssignedTo",AssignedTo)
                i.putExtra("ActionMode","0")
                startActivityForResult(i,2)
              //  startActivity(i)

            }

            R.id.txtAddActivities->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
//                val i = Intent(this@AccountDetailsActivity, SiteVisitActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
//                startActivity(i)

                val i = Intent(this@AccountDetailsActivity, FollowUpActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                i.putExtra("ID_LeadGenerate",ID_LeadGenerate)
                i.putExtra("FK_Employee",AssignedToID)
                i.putExtra("AssignedTo",AssignedTo)
                i.putExtra("ActionMode","0")
//                startActivity(i)
                startActivityForResult(i,2)

            }
            R.id.fabAddNextAction->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
                val i = Intent(this@AccountDetailsActivity, LeadNextActionActivity::class.java)
                startActivity(i)
            }
            R.id.fabAddNewAction->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE

                val i = Intent(this@AccountDetailsActivity, LeadNewActionActivity::class.java)
                startActivity(i)
            }
            R.id.fabAddDocument->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE
//                val i = Intent(this@AccountDetailsActivity, AddDocumentActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
//                startActivity(i)
            }
            R.id.fabAddQuotation->{
                isOpen = true
                fabOpenClose()
                llHistory!!.visibility = View.GONE

                val i = Intent(this@AccountDetailsActivity, AddQuotationActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct", AccountDetailsActivity.ID_LeadGenerateProduct)
                startActivity(i)

                //messagePopup()
            }
            R.id.fabdlteLead->{
                isOpen = true
                fabOpenClose()
                try {

//                    rrrrrrrr
//                    val dialog1 = Dialog(this)
//                    dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
//                    dialog1 .setCancelable(false)
//                    dialog1 .setContentView(R.layout.dlte_lead)
//                    dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
//                    val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
//                    val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
//                    btn_No.setOnClickListener {
//                        dialog1 .dismiss()
//                    }
//                    btn_Yes.setOnClickListener {
//                        dialog1.dismiss()
//                        getDeletelead()
//                    }
//                    dialog1.show()



                    deleteLeadBottomSheet(LeadNo)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            R.id.txtEditLead->{
                isOpen = true
                fabOpenClose()
                try {
//                    val dialog1 = Dialog(this)
//                    dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
//                    dialog1 .setCancelable(false)
//                    dialog1 .setContentView(R.layout.dlte_lead)
//                    dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
//                    val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
//                    val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
//                    btn_No.setOnClickListener {
//                        dialog1 .dismiss()
//                    }
//                    btn_Yes.setOnClickListener {
//                        dialog1.dismiss()
//                        deleteLead = 0
//                        getDeletelead(ID_LeadGenerate,ID_Reason!!)
//                    }
//                    dialog1.show()
                    deleteLeadBottomSheet(LeadNo)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            R.id.fabCloseLead->{
                isOpen = true
                fabOpenClose()
            }
          /*  R.id.ll_msg->{
               *//* val i = Intent(this@AccountDetailsActivity, MessagesActivity::class.java)
                i.putExtra("LgCusMobile",LgCusMobile)
                i.putExtra("LgCusEmail",LgCusEmail)
                startActivity(i)*//*
            }
            R.id.llLocation->{
                getLocationDetails()

            }
            R.id.llImages->{
                val i = Intent(this@AccountDetailsActivity, ImageActivity::class.java)
                i.putExtra("prodid",ID_LeadGenerateProduct)
                startActivity(i)
            }*/
            R.id.tv_actionType->{
                agendaTypeClick1 = "1"
              //  getActionTypes()
                getFollowup(agendaTypeClick1)
            }

            R.id.ll_call->{

                callFunction()

            }
            R.id.ll_meet->{
                val i = Intent(this@AccountDetailsActivity, FollowUpActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                i.putExtra("ID_LeadGenerate",ID_LeadGenerate)
                i.putExtra("FK_Employee",AssignedToID)
                i.putExtra("AssignedTo",AssignedTo)
                i.putExtra("ActionMode","2")
              //  startActivity(i)
                startActivityForResult(i,2)

            }
            R.id.ll_msg->{
                messagePopup()
            }
        }
    }

    private fun callFunction() {

        val ALL_PERMISSIONS = 101

        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE
        )
        if (ContextCompat.checkSelfPermission(
                this@AccountDetailsActivity,
                Manifest.permission.CALL_PHONE
            ) + ContextCompat.checkSelfPermission(
                this@AccountDetailsActivity,
                Manifest.permission.RECORD_AUDIO
            )
            + ContextCompat.checkSelfPermission(
                this@AccountDetailsActivity,
                Manifest.permission.READ_PHONE_STATE
            )
            + ContextCompat.checkSelfPermission(
                this@AccountDetailsActivity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val BroadCallSP = applicationContext.getSharedPreferences(Config.SHARED_PREF16, 0)
            val BroadCallEditer = BroadCallSP.edit()
            BroadCallEditer.putString("BroadCall", "Yes")
            BroadCallEditer.putString("ID_LeadGenerate", ID_LeadGenerate)
            BroadCallEditer.putString("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
            BroadCallEditer.putString("FK_Employee", AssignedToID)
            BroadCallEditer.putString("AssignedTo", AssignedTo)
            BroadCallEditer.commit()

            var mobileno = txtPhone!!.text.toString()
            //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
            intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
            startActivity(intent)
        }
       // Toast.makeText(applicationContext,"Call ",Toast.LENGTH_SHORT).show()

    }

    private fun getDeletelead(ID_LeadGenerate : String,ID_Reason : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                deleteLeadViewModel.getDeletelead(this, ID_LeadGenerate!!,ID_Reason)!!.observe(
                    this,
                    Observer { deleteleadSetterGetter ->
                        val msg = deleteleadSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (deleteLead == 0){
                                    deleteLead++

                                    val jObject = JSONObject(msg)
                                    //  val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("DeleteLeadGenerate")
                                        var responsemessage = jobjt.getString("ResponseMessage")


                                        Log.e("Result",responsemessage)
                                        val builder = AlertDialog.Builder(
                                            this@AccountDetailsActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(responsemessage)
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            //  val i = Intent(this@AddNoteActivity, AccountDetailsActivity::class.java)
                                            // startActivity(i)
                                            onBackPressed()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
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
                                }



                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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
            segmented2.setOnCheckedChangeListener(this@AccountDetailsActivity);

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

    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"data  197  "+data)


        if (data.equals("followuptype")){
           followuptypeAction!!.dismiss()
//            val jsonObject = followupDetailArrayList.getJSONObject(position)
            val jsonObject = followupDetailSort.getJSONObject(position)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActnTypeName"))

          //  getActivitylist(ID_ActionType!!)


        }

        if (data.equals("ViewDescription")) {

            try {
                val jsonObject = documentDetailArrayList.getJSONObject(position)
                val subject = jsonObject.getString("DocumentSubject")
                val description = jsonObject.getString("DocumentDescription")

                openDescBottomSheet(subject,description)
            }
            catch (e:Exception){

            }

        }

        if (data.equals("Documents")) {

            try {

                Log.e(TAG, "Documents   162")
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                    } else {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                else{

                    val jsonObject = documentDetailArrayList.getJSONObject(position)
                    ID_LeadDocumentDetails = jsonObject.getString("ID_LeadDocumentDetails")
                    DocumentImageFormat = jsonObject.getString("DocumentImageFormat")


                    if (DocumentImageFormat.equals("")){
                        Toast.makeText(applicationContext,"Document not found",Toast.LENGTH_SHORT).show()
                    }else{
                        getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
                    }


                }
            }
            catch (e : Exception){

                Log.e(TAG,"1065     "+e.toString())
            }




        }

        if (data.equals("deletereasons")) {

            try {
                recyDelateReason!!.adapter=null
                val jsonObject = reasonArrayList.getJSONObject(position)
                Log.e(TAG,"jsonObject  1157   "+jsonObject)
                tie_DeleteReason!!.setText(""+jsonObject.getString("ResnName"))
                ID_Reason = jsonObject.getString("ID_Reason")

            }
            catch (e:Exception){

            }

        }

    }

    private fun getDocumentView(ID_LeadGenerate: String, ID_LeadGenerateProduct: String, ID_LeadDocumentDetails: String) {

        var typeAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()

                viewDocumentViewModel.getViewDocument(this,ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                Log.e(TAG,"msg   89   "+msg)
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   302   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

//                                AccountDetailsActivity
//                                getImages
//                                https://stackoverflow.com/questions/31129644/how-to-download-an-image-from-server-side-to-android-using-bytearray

                                    try {


                                        if (typeAgenda == 0){
                                            typeAgenda++
                                            val jobjt = jObject.getJSONObject("DocumentImageDetails")
                                            val DocumentImage = jobjt.getString("DocumentImage")
                                            val decodedString = Base64.decode(DocumentImage, Base64.DEFAULT)
                                            //  val mediaData: ByteArray = Base64.decode(decodedString, 0)
                                            //  val bytes = decodedString.toByteArray()
                                            val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
                                            val datetime = sdf.format(Date())
                                         //   destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+System.currentTimeMillis() + DocumentImageFormat)
                                            destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ""+System.currentTimeMillis() + DocumentImageFormat)
//                                    destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+datetime + DocumentImageFormat)
                                            val fo: FileOutputStream
                                            try {
                                                if (!destination!!.getParentFile().exists()) {
                                                    destination!!.getParentFile().mkdirs()
                                                }
                                                if (!destination!!.exists()) {
                                                    destination!!.createNewFile()
                                                }
                                                fo = FileOutputStream(destination)
                                                fo.write(decodedString)
                                                fo.close()

                                                Log.e(TAG,"Success   1230    ")

                                                val file: File = File(destination,"")
                                                val map: MimeTypeMap = MimeTypeMap.getSingleton()
                                                val ext: String = MimeTypeMap.getFileExtensionFromUrl(file.name)
                                                var type: String? = map.getMimeTypeFromExtension(ext)
                                                if (type == null)
                                                    type = "*/*";

                                                val intent = Intent(Intent.ACTION_VIEW)
                                                val data: Uri = Uri.fromFile(file)
                                                intent.setDataAndType(data, type)
                                                startActivity(intent)


                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                                Log.e(TAG,"Exception   1231    "+e.toString())
                                            }
                                        }

                                    }catch (e: Exception){
                                        Log.e(TAG,"Exception   1232    "+e.toString())
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){

                        }

                    })
             //   progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }


    private fun openDescBottomSheet(subject: String, description: String) {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_description, null)

        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val tv_subject = view.findViewById<TextView>(R.id.tv_subject)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)

        tv_subject.text = subject
        tv_description.text = description

        imgClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()

    }

    private fun deleteLeadBottomSheet(LeadNo : String) {




        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_deletelead, null)


        tie_DeleteReason = view.findViewById<TextInputEditText>(R.id.tie_DeleteReason)
        recyDelateReason = view.findViewById<RecyclerView>(R.id.recyDelateReason)
        tv_leadNumber = view.findViewById<TextView>(R.id.tv_leadNumber)
        val btnDeleteNo = view.findViewById<Button>(R.id.btnDeleteNo)
        val btnDeleteYes = view.findViewById<Button>(R.id.btnDeleteYes)
        tie_DeleteReason!!.setText("")
        ID_Reason = ""
        tv_leadNumber!!.text = "Lead Number  :  "+LeadNo
        tie_DeleteReason!!.setOnClickListener {
            leadDelete = 0
            recyDelateReason!!.adapter = null
            getLeadDeleteReason()



        }

        btnDeleteNo!!.setOnClickListener {
            dialog.dismiss()
        }

        btnDeleteYes!!.setOnClickListener {

            if (ID_Reason.equals("")){
                Toast.makeText(applicationContext, "Select Reason", Toast.LENGTH_SHORT).show()
            }else{
                dialog.dismiss()
                deleteLead = 0
                getDeletelead(ID_LeadGenerate ,ID_Reason!! )

            }

        }


        dialog.setCancelable(true)
        dialog!!.setContentView(view)

        dialog.show()

    }

    private fun getLeadDeleteReason() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadDeleteReasonViewModel.getLeadDeleteReason(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   1339   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ReasonDetails")
                                    reasonArrayList = jobjt.getJSONArray("ReasonDetailsList")
                                    if (reasonArrayList.length()>0){
                                        if (leadDelete == 0){
                                            leadDelete++

                                            val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                            recyDelateReason!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ReasonAdapter(this@AccountDetailsActivity, reasonArrayList)
                                            recyDelateReason!!.adapter = adapter
                                            adapter.setClickListener(this@AccountDetailsActivity)
                                        }
                                    }
                                } else {
                                }
                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext,""+e.toString(),Toast.LENGTH_SHORT).show()
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
                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                        recyHistory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = LeadHistoryAdapter(this@AccountDetailsActivity, leadHistoryArrayList)
                                        recyHistory!!.adapter = adapter
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

    private fun getLeadInfoetails() {
        var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                leadInfoViewModel.getLeadInfo(this,ID_LeadGenerateProduct!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   724   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("LeadInfoetails")
                                    leadInfoArrayList = jobjt.getJSONArray("LeadInfoetailsList")
                                    if (leadInfoArrayList.length()>0){
                                        if (leadInfo == 0){
                                            leadInfo++
                                            val jObjectLeadInfo = leadInfoArrayList.getJSONObject(0)
                                            txtName!!.setText(""+jObjectLeadInfo.getString("LgCusName"))
                                            txtAddress!!.setText(""+jObjectLeadInfo.getString("LgCusAddress"))
                                            txtPhone!!.setText(""+jObjectLeadInfo.getString("LgCusMobile"))
                                            txtEmail!!.setText(""+jObjectLeadInfo.getString("LgCusEmail"))
                                            txtLeadNo!!.setText(""+jObjectLeadInfo.getString("LgLeadNo"))
                                            txtCategory!!.setText(""+jObjectLeadInfo.getString("CatName"))
                                            txtProduct!!.setText(""+jObjectLeadInfo.getString("ProdName"))
                                            txtTargetDate!!.setText(""+jObjectLeadInfo.getString("NextActionDate"))
                                            txtAction!!.setText(""+jObjectLeadInfo.getString("NxtActnName"))
                                            LgCusMobile = jObjectLeadInfo.getString("LgCusMobile")
                                            LgCusEmail = jObjectLeadInfo.getString("LgCusEmail")
                                        }
                                    }
                                } else {
                                }
                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext,""+e.toString(),Toast.LENGTH_SHORT).show()
                        }

                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getInfoetails() {
        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_subinfo, null, false)
//        llMainDetail!!.addView(inflatedLayout);
        var imInfoLoading = findViewById<ImageView>(R.id.imInfoLoading)
        var recySubInfo = findViewById<RecyclerView>(R.id.recySubInfo)

        var llInfoDetail = findViewById<LinearLayout>(R.id.llInfoDetail)

//        var txtName1 = inflatedLayout.findViewById<TextView>(R.id.txtName)
//        var txtAddress1 = inflatedLayout.findViewById<TextView>(R.id.txtAddress)
//        var txtMobile1 = inflatedLayout.findViewById<TextView>(R.id.txtMobile)
//        var txtEmail1 = inflatedLayout.findViewById<TextView>(R.id.txtEmail)
//        var txtAssigned1 = inflatedLayout.findViewById<TextView>(R.id.txtAssigned)
//        var txtLeadNo1 = inflatedLayout.findViewById<TextView>(R.id.txtLeadNo)
//        var txtProject1 = inflatedLayout.findViewById<TextView>(R.id.txtProject)
//        var txtProduct1 = inflatedLayout.findViewById<TextView>(R.id.txtProduct)
//        var txtCategoryName1 = inflatedLayout.findViewById<TextView>(R.id.txtCategoryName)
//        var txtNextAction1 = inflatedLayout.findViewById<TextView>(R.id.txtNextAction)
//        var txtNextActionDate1 = inflatedLayout.findViewById<TextView>(R.id.txtNextActionDate)
//        var txtActionType1 = inflatedLayout.findViewById<TextView>(R.id.txtActionType)

        var txtName1 = findViewById<TextView>(R.id.txtInfoName)
        var txtAddress1 = findViewById<TextView>(R.id.txtInfoAddress)
        var txtMobile1 = findViewById<TextView>(R.id.txtInfoMobile)
        var txtEmail1 = findViewById<TextView>(R.id.txtInfoEmail)
        var txtAssigned1 = findViewById<TextView>(R.id.txtInfoAssigned)
        var txtLeadNo1 = findViewById<TextView>(R.id.txtInfoLeadNo)
        var txtProject1 = findViewById<TextView>(R.id.txtInfoProject)
        var txtProduct1 = findViewById<TextView>(R.id.txtInfoProduct)
        var txtCategoryName1 = findViewById<TextView>(R.id.txtInfoCategoryName)
        var txtNextAction1 = findViewById<TextView>(R.id.txtInfoNextAction)
        var txtNextActionDate1 = findViewById<TextView>(R.id.txtInfoNextActionDate)
        var txtActionType1 = findViewById<TextView>(R.id.txtInfoActionType)


        llInfoDetail.visibility = View.GONE

        Glide.with(this).load(R.drawable.loadinggif).into(imInfoLoading);
        var Info = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                imInfoLoading.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.loadinggif).into(imInfoLoading);
                infoViewModel.getInfo(this,ID_LeadGenerateProduct)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   795   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                imInfoLoading.visibility = View.GONE
                                llInfoDetail.visibility = View.VISIBLE
                                val jobjt = jObject.getJSONObject("LeadInfoetails")
                                infoArrayList = jobjt.getJSONArray("LeadInfoetailsList")
                                if (infoArrayList.length()>0){
                                    if (Info == 0){
                                        Info++
//                                        Log.e(TAG,"infoArrayList  845   "+infoArrayList)
//                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
//                                        recySubInfo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        recySubInfo!!.setHasFixedSize(true)
//                                        val adapter = infoSubAdapter(this@AccountDetailsActivity, infoArrayList)
//                                        recySubInfo!!.adapter = adapter

                                        val jsonObject1 = infoArrayList.getJSONObject(0)
                                        txtName1.setText(jsonObject1.getString("LgCusName"))
                                        txtAddress1.setText(jsonObject1.getString("LgCusAddress"))
                                        txtMobile1.setText(jsonObject1.getString("LgCusMobile"))
                                        txtEmail1.setText(jsonObject1.getString("LgCusEmail"))
                                        txtAssigned1.setText(jsonObject1.getString("AssignedTo"))
                                        txtLeadNo1.setText(jsonObject1.getString("LgLeadNo"))
                                        txtProject1.setText(jsonObject1.getString("ProjectName"))
                                        txtProduct1.setText(jsonObject1.getString("ProdName"))
                                        txtCategoryName1.setText(jsonObject1.getString("CatName"))
                                        txtNextAction1.setText(jsonObject1.getString("NxtActnName"))
                                        txtNextActionDate1.setText(jsonObject1.getString("NextActionDate"))
                                        txtActionType1.setText(jsonObject1.getString("ActnTypeName"))
                                    }
                                }
                            } else {
                                imInfoLoading.visibility = View.GONE
                            }
                        } else {
                            imInfoLoading.visibility = View.GONE
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getDocumenttails() {
        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_subdocument, null, false)
        llMainDetail!!.addView(inflatedLayout);


        var imDocumentLoading = inflatedLayout.findViewById<ImageView>(R.id.imgv_docmnt)
        var recySubDocs = inflatedLayout.findViewById<RecyclerView>(R.id.rv_docmnt)
        Glide.with(this).load(R.drawable.loadinggif).into(imDocumentLoading);
        var docs = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                imDocumentLoading.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.loadinggif).into(imDocumentLoading);
                documentViewModel.getDocumentlist(this, ID_LeadGenerateProduct)!!.observe(
                    this,
                    Observer { documentlistSetterGetter ->
                        val msg = documentlistSetterGetter.message
                 /*       if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   458   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                imDocumentLoading.visibility = View.GONE
                                val jobjt = jObject.getJSONObject("LeadInfoetails")
                                documentArrayList = jobjt.getJSONArray("LeadInfoetailsList")
                                if (documentArrayList.length()>0){
                                    if (docs == 0){
                                        docs++
                                        Log.e(TAG,"documentArrayList  845   "+documentArrayList)
                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                        recySubDocs!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        recySubDocs!!.setHasFixedSize(true)
                                        val adapter = DocumentSubAdapter(this@AccountDetailsActivity, documentArrayList)
                                        recySubDocs!!.adapter = adapter
                                    }
                                }
                            } else {
                                imDocumentLoading.visibility = View.GONE
                            }
                        } else {
                            imDocumentLoading.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()
                        }*/
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getQuotationtails() {
        val inflater1 = LayoutInflater.from(this@AccountDetailsActivity)
        val inflatedLayout: View = inflater1.inflate(R.layout.activity_subquotation, null, false)
        llMainDetail!!.addView(inflatedLayout);
        var imQuotationLoading = inflatedLayout.findViewById<ImageView>(R.id.imgv_quotatn)
        var recySubQuotation = inflatedLayout.findViewById<RecyclerView>(R.id.rv_quotatn)
        Glide.with(this).load(R.drawable.loadinggif).into(imQuotationLoading);
        var quotation = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                imQuotationLoading.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.loadinggif).into(imQuotationLoading);
                quotationlistViewModel.getQuotationlist(this)!!.observe(
                    this,
                    Observer { quotationlistSetterGetter ->
                        val msg = quotationlistSetterGetter.message
                      /*  if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   458   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                imQuotationLoading.visibility = View.GONE
                                val jobjt = jObject.getJSONObject("LeadHistoryDetails")
                                quotationArrayList = jobjt.getJSONArray("LeadHistoryDetailsList")
                                if (quotationArrayList.length()>0){
                                    if (quotation == 0){
                                        quotation++
                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                        recySubQuotation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        recySubQuotation!!.setHasFixedSize(true)
                                        val adapter = QuotationSubAdapter(this@AccountDetailsActivity, quotationArrayList)
                                        recySubQuotation!!.adapter = adapter
                                    }
                                }
                            } else {
                                imQuotationLoading.visibility = View.GONE
                            }
                        } else {
                            imQuotationLoading.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()
                        }*/
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }



    private fun getNotelist() {
        var rbActMode = "1"
        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_subnote, null, false)
        llMainDetail!!.addView(inflatedLayout);
        var rv_note = inflatedLayout.findViewById<RecyclerView>(R.id.rv_note)
        var imgv_note = inflatedLayout.findViewById<ImageView>(R.id.imgv_nte)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                imgv_note.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.loadinggif).into(imgv_note);
                notelistViewModel.getNotelist(this)!!.observe(
                    this,
                    Observer { notelistSetterGetter ->
                        val msg = notelistSetterGetter.message
                   /*           if (msg!!.length > 0) {
                                   val jObject = JSONObject(msg)
                                   Log.e(TAG,"msg   458   "+msg)
                                   if (jObject.getString("StatusCode") == "0") {
                                       imgv_note.visibility = View.GONE
                                       val jobjt = jObject.getJSONObject("LeadHistoryDetails")
                                       quotationArrayList = jobjt.getJSONArray("LeadHistoryDetailsList")
                                       if (quotationArrayList.length()>0){
                                           if (quotation == 0){
                                               quotation++
                                               val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                               recySubQuotation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                               recySubQuotation!!.setHasFixedSize(true)
                                               val adapter = NoteListAdapter(this@AccountDetailsActivity, quotationArrayList)
                                               recySubQuotation!!.adapter = adapter
                                           }
                                       }
                                   } else {
                                       imQuotationLoading.visibility = View.GONE
                                   }
                               } else {
                                   imQuotationLoading.visibility = View.GONE
                                   Toast.makeText(
                                       applicationContext,
                                       "Some Technical Issues.",
                                       Toast.LENGTH_LONG
                                   ).show()
                               }*/
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun getHistoryAct(rbActMode: String) {
        var historyAct = 0
        llCall!!.visibility = View.GONE
        llMessage!!.visibility = View.GONE
        llMeeting!!.visibility = View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                if (rbActMode.equals("1")){
                    imActCall!!.visibility = View.VISIBLE
                    llCall!!.visibility = View.VISIBLE
                }
                if (rbActMode.equals("2")){
                    imActMessage!!.visibility = View.VISIBLE
                    llMessage!!.visibility = View.VISIBLE
                }
                if (rbActMode.equals("3")){
                    imActMeeting!!.visibility = View.VISIBLE
                    llMeeting!!.visibility = View.VISIBLE
                }
                try {
                    historyActViewModel.getHistoryAct(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            imActCall!!.visibility = View.GONE
                            imActMessage!!.visibility = View.GONE
                            imActMeeting!!.visibility = View.GONE
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1062   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadHistoryDetails")
                                historyActArrayList = jobjt.getJSONArray("LeadHistoryDetailsList")
                                if (historyActArrayList.length()>0){
                                    if (historyAct == 0){
                                        Log.e(TAG,"leadHistoryArrayList  1067  "+historyActArrayList)
                                        historyAct++
                                        if (rbActMode.equals("1")){
                                            recyActCall!!.visibility = View.VISIBLE
                                            val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                            recyActCall!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = HistoryActCallAdapter(this@AccountDetailsActivity, historyActArrayList)
                                            recyActCall!!.adapter = adapter
                                        }
                                        if (rbActMode.equals("2")){
                                            recyActMessage!!.visibility = View.VISIBLE
                                            val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                            recyActMessage!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = HistoryActMesssageAdapter(this@AccountDetailsActivity, historyActArrayList)
                                            recyActMessage!!.adapter = adapter
                                        }
                                        if (rbActMode.equals("3")){
                                            recyActMeeting!!.visibility = View.VISIBLE
                                            val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                            recyActMeeting!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = HistoryActMeetingAdapter(this@AccountDetailsActivity, historyActArrayList)
                                            recyActMeeting!!.adapter = adapter
                                        }
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
                }
                catch (e : Exception){
                    Log.e(TAG,"Exception  1104   "+e.toString())
                }
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
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




    private fun getActivityDetails1() {
//        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//        val inflatedLayout1: View = inflater.inflate(R.layout.activity_subactivities, null, false)
//        llMainDetail!!.addView(inflatedLayout1);

        rv_activity = findViewById<RecyclerView>(R.id.rv_activity)
        tv_actionType= findViewById<TextView>(R.id.tv_actionType);

      //  tv_actionType!!.setOnClickListener(this)

     //   getFollowup(agendaTypeClick1)

        getActivitylist("0")

      //  getActivitylist(ID_ActionType!!)


    }

    private fun getFollowup(agendaTypeClick1: String?) {
        var agendaAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                followuptypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { followuptypeSetterGetter ->
                        val msg = followuptypeSetterGetter.message
                        progressDialog!!.dismiss()
                        try {
                            if (msg!!.length > 0) {


                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   284   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                    followupDetailArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                    if (followupDetailArrayList.length()>0){
                                        if (agendaAction == 0){
                                            agendaAction++
                                            agendaTypePopup(followupDetailArrayList,agendaTypeClick1)

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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext,""+e.toString(),Toast.LENGTH_SHORT).show()
                        }

                    })

            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }    }

    private fun agendaTypePopup(followupDetailArrayList: JSONArray, agendaTypeClick1: String?) {

        Log.i("Array",followupDetailArrayList.toString()+"\n"+agendaTypeClick)

        if (agendaTypeClick1.equals("0")){
            val jsonObject = followupDetailArrayList.getJSONObject(0)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActnTypeName"))

         //   getActivitylist(ID_ActionType!!)
        //    getActivitylist("0")

        }else{

            try {

                followuptypeAction  = Dialog(this)
                followuptypeAction !!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                followuptypeAction !! .setContentView(R.layout.followup_type_popup)
                followuptypeAction !!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
                recyActionType = followuptypeAction !! .findViewById(R.id.recyFollowupType) as RecyclerView
                val etsearch = followuptypeAction!! .findViewById(R.id.etsearch) as EditText

                followupDetailSort = JSONArray()
                for (k in 0 until followupDetailArrayList.length()) {
                    val jsonObject = followupDetailArrayList.getJSONObject(k)
                    // reportNamesort.put(k,jsonObject)
                    followupDetailSort.put(jsonObject)
                }

                val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//                val adapter = FollowupTypeAdapter(this@AccountDetailsActivity, followupDetailArrayList)
                val adapter = FollowupTypeAdapter(this@AccountDetailsActivity, followupDetailSort)
                recyActionType!!.adapter = adapter
                adapter.setClickListener(this@AccountDetailsActivity)

                etsearch!!.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        //  list_view!!.setVisibility(View.VISIBLE)
                        val textlength = etsearch!!.text.length
                        followupDetailSort = JSONArray()

                        for (k in 0 until followupDetailArrayList.length()) {
                            val jsonObject = followupDetailArrayList.getJSONObject(k)
                            if (textlength <= jsonObject.getString("ActnTypeName").length) {
                                if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                    followupDetailSort.put(jsonObject)
                                }

                            }
                        }

                        Log.e(TAG,"followupDetailSort               7103    "+followupDetailSort)
                        val adapter = FollowupTypeAdapter(this@AccountDetailsActivity, followupDetailSort)
                        recyActionType!!.adapter = adapter
                        adapter.setClickListener(this@AccountDetailsActivity)
                    }
                })

                followuptypeAction!!.show()
                followuptypeAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun getActivitylist(ID_ActionType: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                activitylistViewModel.getActivitylist(this, ID_LeadGenerateProduct,ID_ActionType)!!.observe(
                    this,
                    Observer { activitylistSetterGetter ->
                        val msg = activitylistSetterGetter.message
                        progressDialog!!.dismiss()

                        try {
                            if (msg!!.length > 0) {


                                val jObject = JSONObject(msg)
                                Log.e(TAG,"activity 2182   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ActivitiesDetails")
                                    activityArrayList = jobjt.getJSONArray("ActivitiesDetailsList")
                                    if (activityArrayList.length()>0){
//
                                        rv_activity!!.visibility=View.VISIBLE
//                                        rv_activity = findViewById(R.id.rv_activity) as RecyclerView
                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
                                        rv_activity!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ActivityListAdapter(this@AccountDetailsActivity, activityArrayList)
                                        rv_activity!!.adapter = adapter
                                        adapter.setClickListener(this@AccountDetailsActivity)
//                                        // }

                                    }

                                } else {
                                    rv_activity!!.visibility=View.GONE
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
                        }


                    })
                // progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }


    }

    private fun getLocation() {


        try {
//            val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//            val inflatedLayout1: View = inflater.inflate(R.layout.activity_location, null, false)
//            llMainDetail!!.addView(inflatedLayout1);

            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    locationViewModel.getLocation(this, ID_LeadGenerateProduct, ID_LeadGenerate)!!.observe(
                        this,
                        Observer { locationSetterGetter ->
                            val msg = locationSetterGetter.message
                            try {
                                if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadImageDetails")
                                        latitude = jobjt!!.getString("LocationLatitude")
                                        longitude = jobjt!!.getString("LocationLongitude")
                                        Log.e("LocationDetails", latitude + "\n" + longitude)

                                        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@AccountDetailsActivity)
                                        fetchLocation()


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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                                }
                            }catch (e : Exception){
                                Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
                            }

                        })
                    progressDialog!!.dismiss()
                }
                false -> {
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }catch (e : Exception){
            Log.e(TAG,"Exception  2348   "+e.toString())
        }



    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment!!.getMapAsync(this@AccountDetailsActivity)
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.i("locationdet",latitude+"\n"+longitude)


        //    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        val geocoder: Geocoder
        var addresses: List<Address?>
        geocoder = Geocoder(this, Locale.getDefault())
        addresses = geocoder.getFromLocation(latitude.toDouble(),longitude.toDouble(), 1);
        val city = addresses.get(0)!!.getAddressLine(0);
        Log.i("City",city)


        val markerOptions = MarkerOptions().position(latLng).title(city)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  fetchLocation()
            }
        }
    }

    private fun getImages() {
//        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//        val inflatedLayout1: View = inflater.inflate(R.layout.activity_subimage, null, false)
//        llMainDetail!!.addView(inflatedLayout1);
        imgv_1 = findViewById<ImageView>(R.id.imgv_1)
        imgv_2 = findViewById<ImageView>(R.id.imgv_2)
        crdv_1 = findViewById<CardView>(R.id.crdv_1)
        crdv_2 = findViewById<CardView>(R.id.crdv_2)

        crdv_1!!.visibility=View.GONE
        crdv_2!!.visibility=View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                imageViewModel.getImage(this, ID_LeadGenerateProduct, ID_LeadGenerate)!!.observe(this,
                    { ImageSetterGetter ->
                        val msg = ImageSetterGetter.message
                        if (msg!!.length > 0) {

                            if (imageDet == 0){
                                imageDet++
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    jobjt = jObject.getJSONObject("LeadImageDetails")
                                    landmark = jobjt!!.getString("LocationLandMark1")
                                    landmark2 = jobjt!!.getString("LocationLandMark2")

                                    if(!landmark.equals(""))
                                    {
                                        crdv_1!!.visibility=View.VISIBLE
                                        val decodedString = Base64.decode(landmark, Base64.DEFAULT)
                                        ByteArrayToBitmap(decodedString)
                                        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                                        val stream = ByteArrayOutputStream()
                                        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                        Glide.with(this) .load(stream.toByteArray()).into(imgv_1!!)
                                        crdv_1!!.setOnClickListener(View.OnClickListener {
                                            zoomImage(stream)
                                        })

                                    }
                                    if(!landmark2.equals(""))
                                    {
                                        crdv_2!!.visibility=View.VISIBLE
                                        val decodedString = Base64.decode(landmark2, Base64.DEFAULT)
                                        ByteArrayToBitmap(decodedString)
                                        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                                        val stream = ByteArrayOutputStream()
                                        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                        Glide.with(this) .load(stream.toByteArray()).into(imgv_2!!)
                                        crdv_2!!.setOnClickListener(View.OnClickListener {
                                            zoomImage(stream)
                                        })
                                    }

                                }

                                else {
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
                            }


                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            crdv_1!!.visibility=View.GONE
                            crdv_2!!.visibility=View.GONE
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

    private fun zoomImage(stream: ByteArrayOutputStream) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_image_viewer, null)
        dialogBuilder.setView(dialogView)

        val image = dialogView.findViewById<View>(R.id.id_image) as ImageView
        Glide.with(this) .load(stream.toByteArray()).into(image!!)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun ByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }

    private fun getDocuments() {
//        val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//        val inflatedLayout1: View = inflater.inflate(R.layout.activity_documents, null, false)
//        llMainDetail!!.addView(inflatedLayout1);
        var recyDocumentDetail = findViewById<RecyclerView>(R.id.recyDocumentDetail)
        recyDocumentDetail.adapter = null

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                documentDetailViewModel.getDocumentDetail(this,ID_LeadGenerate,ID_LeadGenerateProduct)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if(documentDet == 0){
                                    documentDet++
                                    Log.e(TAG,"msg   89   "+msg)
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   302   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("DocumentDetails")
                                        documentDetailArrayList = jobjt.getJSONArray("DocumentDetailsList")
                                        if (documentDetailArrayList.length()>0){

                                            Log.e(TAG,"documentDetailArrayList  102   "+documentDetailArrayList)

                                            recyDocumentDetail = findViewById(R.id.recyDocumentDetail) as RecyclerView
                                            // val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                            recyDocumentDetail!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                                            // recyAgendaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = DocumentDetailAdapter(this@AccountDetailsActivity, documentDetailArrayList)
                                            recyDocumentDetail!!.adapter = adapter
                                            adapter.setClickListener(this@AccountDetailsActivity)


                                        }

                                    }
                                    else {

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
                                }


                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG,"resultCode  2591   "+requestCode+"   "+resultCode)
        if (resultCode == 2) {
            onBackPressed()
        }
    }


}

