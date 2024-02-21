package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
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
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import info.hoang8f.android.segmented.SegmentedGroup
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
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
    lateinit var sendemailViewModel: SendEmailViewModel
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
    lateinit var sendmessageViewModel:SendMessageViewModel
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
    private var txtInfoOfferprice : TextView? = null
    private var txtInfoMrp : TextView? = null
    private var txtTargetDate : TextView? = null
    private var txtAction : TextView? = null
    private var tabLayout : TabLayout? = null
    var recyActionType: RecyclerView? = null
    lateinit var activityArrayList : JSONArray
    lateinit var followupDetailArrayList : JSONArray
    lateinit var followupDetailSort : JSONArray
    var recyAgendaDetail: RecyclerView? = null
    var SubMode : String?= ""
//    var Reciever_Id : String? = null
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
    private var Reciever_Id:String? = ""
    private var messageTitle = "";
    private var edt_title : EditText? = null
    private var edt_message_content : EditText? = null
    private var edt_subject : EditText? = null
    private var edt_message : EditText? = null
    private var messageBody = ""
    private var mailid : String? = null
    private var messageSubject = ""
    private var cbWhat = "0";
    private var cbEmail = "0";
    private var cbMessage = "0";

    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private var MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2
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
    var sendmessage = 0
    var SendMailCount = 0

    var AssignedToID : String?= ""
    var AssignedTo : String?= ""

    private var rltv_Info : RelativeLayout? = null
    private var ll_location : LinearLayout? = null
    private var ll_history : LinearLayout? = null
    private var ll_images : LinearLayout? = null
    private var rltv_document : RelativeLayout? = null

    var imageDet = 0
    var documentDet = 0
    var leadInfo = 0
    var leadInfoCount = 0

//    private var mScaleFactor = 1.0f
//    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private lateinit var image: ImageView
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_account_details)

        context = this@AccountDetailsActivity
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

//        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

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
        sendmessageViewModel= ViewModelProvider(this).get(SendMessageViewModel::class.java)
        sendemailViewModel = ViewModelProvider(this).get(SendEmailViewModel::class.java)

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        Log.e(TAG,"jsonObj  123456 "+jsonObj)
        SubMode  = intent.getStringExtra("SubMode")
        Log.e(TAG,"SubMode  12345678 "  +SubMode)
//        Reciever_Id  = intent.getStringExtra("Reciever")
//        Log.e(TAG,"Reciever_Id  11555555 "  +Reciever_Id)

        ID_LeadGenerateProduct = jsonObj!!.getString("ID_LeadGenerateProduct")
        ID_LeadGenerate = jsonObj!!.getString("ID_LeadGenerate")
        LeadNo = jsonObj!!.getString("LeadNo")
        AssignedToID = jsonObj!!.getString("FK_Employee")
        AssignedTo = jsonObj!!.getString("AssignedTo")

        setRegViews()
        bottombarnav()
        fabOpenClose()
        leadInfo = 0
        getLeadInfoetails()
        getCalendarId(context)
        if (SubMode.equals("4")){
            ll_meet!!.visibility = View.GONE
            ll_msg!!.visibility = View.VISIBLE
            fab_main!!.visibility = View.GONE
        }
        addTabItem()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Info"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("History"))

        if (!SubMode.equals("4")){
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Location"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Images"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Documents"))
        }

        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        rltv_Info!!.visibility = View.VISIBLE
        leadInfoCount = 0
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

                    leadInfoCount = 0
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

            fabdlteLead!!.visibility = View.GONE
            txtEditLead!!.visibility = View.GONE
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

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

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
            if (SubMode.equals("4")){
                BroadCallEditer.putString("CallRedirection", "Yes")
            }
            BroadCallEditer.commit()


            var mobileno = txtPhone!!.text.toString()

            if (mobileno.equals("")){
                Toast.makeText(applicationContext,""+Config.INVALID_MOBILE,Toast.LENGTH_SHORT).show()
            }else{
                //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
              //  intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mobileno))
                startActivity(intent)
            }

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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    private fun getSendMessage(messageTitle :String,messageDesc: String,Reciever_Id :String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                sendmessageViewModel.sendmessage(this,messageTitle,messageDesc,Reciever_Id)!!.observe(
                    this,
                    Observer { deleteleadSetterGetter ->
                        val msg = deleteleadSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (sendmessage == 0){
                                    sendmessage++

                                    val jObject = JSONObject(msg)
                                    //  val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UpdateNotificationDetails")
                                      try {

                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!!.setContentView(R.layout.pickup_deli_update_success)
                                            suceessDialog!!.window!!.attributes.gravity =
                                                Gravity.CENTER_VERTICAL;

                                            val tv_succesmsg =
                                                suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView

                                            val tv_succesok =
                                                suceessDialog!!.findViewById(R.id.tv_succesok) as TextView

                                            tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                val intent = Intent()
                                                intent.putExtra("MESSAGE", android.R.id.message)
                                                setResult(2, intent)
//                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                            )
                                        } catch (e: Exception) {
                                            e.printStackTrace()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
            dialog1.window!!.attributes.gravity = Gravity.CENTER;

            if (SubMode!!.equals("4")){
                dialog1 .setContentView(R.layout.send_message_popup_1)

                edt_title = (dialog1 .findViewById(R.id.edt_title) as EditText)
                edt_message_content = dialog1 .findViewById(R.id.edt_message_content) as EditText

            }else{
                dialog1 .setContentView(R.layout.mail_message_popup)

                 edt_subject = dialog1 .findViewById(R.id.edt_subject) as EditText
                 edt_message = dialog1 .findViewById(R.id.edt_message) as EditText
            }
//            val rbMessages = dialog1 .findViewById(R.id.rbMessages) as RadioButton
//            val rbReminder = dialog1 .findViewById(R.id.rbReminder) as RadioButton
//            val rbIntimation = dialog1 .findViewById(R.id.rbIntimation) as RadioButton


//            val chk_whats = dialog1 .findViewById(R.id.chk_whats) as CheckBox
//            val chk_Email = dialog1 .findViewById(R.id.chk_Email) as CheckBox
//            val chk_Message = dialog1 .findViewById(R.id.chk_Message) as CheckBox

            val btnMssubmit = dialog1 .findViewById(R.id.btnMssubmit) as Button
            val btnMscancel = dialog1 .findViewById(R.id.btnMscancel) as Button

            val segmented2 = dialog1 .findViewById(R.id.segmented2) as SegmentedGroup
            segmented2.setTintColor(resources.getColor(R.color.color_msg_tab));
            segmented2.setOnCheckedChangeListener(this@AccountDetailsActivity);

//            rbMessages.isChecked  =true
//            rbReminder.isChecked  =false
//            rbIntimation.isChecked  =false

//            chk_whats.setOnClickListener {
//                if (chk_whats.isChecked){
//
//                    cbWhat = "1"
//                }else{
//                    cbWhat = "0"
//                }
//            }
//
//            chk_Email.setOnClickListener {
//                if (chk_Email.isChecked){
//                    cbEmail = "1"
//                }else{
//                    cbEmail = "0"
//                }
//            }
//
//            chk_Message.setOnClickListener {
//
//                if (chk_Message.isChecked){
//                    cbMessage = "1"
//                }else{
//                    cbMessage = "0"
//                }
//            }

            btnMscancel.setOnClickListener {
                dialog1 .dismiss()
            }

            btnMssubmit.setOnClickListener {

                if (SubMode!!.equals("4")){
                    sendMessagePopUp(it)
                    dialog1 .dismiss()

                }else{

                    sendMailMessagePopUp(it)
                    dialog1 .dismiss()
                }

//                messageDesc = edt_message_content!!.text.toString()
//                messageTitle = edt_title!!.text.toString()
////                if (messageType.equals("")){
////
////                }
//                if(messageTitle.equals("")){
//                    Config.snackBars(context,it,"Please Enter Tittle message")
////
//                }
//                else if (messageDesc.equals("")){
//                    Config.snackBars(context,it,"Please enter message")
////
//                }
//                else{
//                    Log.e(TAG,"  927  messageType  "+messageType)
//                    Log.e(TAG,"  927  messageDesc  "+messageDesc)
//                    Log.e(TAG,"  927  HHHHH  "+cbWhat+"  :   "+cbEmail+"  :  "+cbMessage)
//
//                    Config.Utils.hideSoftKeyBoard(context,it)
//                    dialog1 .dismiss()
////                    Toast.makeText(context,""+messageDesc,Toast.LENGTH_SHORT).show()
//                    getSendMessage(messageTitle,messageDesc,Reciever_Id!!)
//                }

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

    private fun sendMessagePopUp(v: View){
        messageDesc = edt_message_content!!.text.toString()
        messageTitle = edt_title!!.text.toString()
//                if (messageType.equals("")){
//
//                }
        if(messageTitle.equals("")){
            Config.snackBars(context,v,"Please Enter Tittle message")
//
        }
        else if (messageDesc.equals("")){
            Config.snackBars(context, v,"Please enter message")
//
        }
        else{
            Log.e(TAG,"  927  messageType  "+messageTitle)
            Log.e(TAG,"  927  messageDesc  "+messageDesc)
//            Log.e(TAG,"  927  HHHHH  "+cbWhat+"  :   "+cbEmail+"  :  "+cbMessage)

            Config.Utils.hideSoftKeyBoard(context,v)
//                    Toast.makeText(context,""+messageDesc,Toast.LENGTH_SHORT).show()
            sendmessage = 0
            getSendMessage(messageTitle,messageDesc,Reciever_Id!!)
        }

    }

    private fun sendMailMessagePopUp(v: View){
        messageSubject = edt_subject!!.text.toString()
        messageBody = edt_message!!.text.toString()
//                if (messageType.equals("")){
//
//                }
        if(messageSubject.equals("")){
            Config.snackBars(context,v,"Please enter Subject")
//
        }
        else if (messageBody.equals("")){
            Config.snackBars(context,v,"Please Enter Message")
//
        }
        else{
            Log.e(TAG,"  927  messageType  "+messageType)
            Log.e(TAG,"  927  messageDesc  "+messageBody)
            Log.e(TAG,"  927  HHHHH  "+cbWhat+"  :   "+cbEmail+"  :  "+cbMessage)

            Config.Utils.hideSoftKeyBoard(context,v)
//                    Toast.makeText(context,""+messageDesc,Toast.LENGTH_SHORT).show()
            SendMailCount = 0
            getSendMail(messageSubject,messageBody,mailid!!)
        }

    }

    private fun getSendMail(messageSubject : String,messageBody : String,mailid : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                sendemailViewModel.sendemail(this,messageSubject,messageBody,mailid!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (SendMailCount == 0) {
                                    SendMailCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("MailResult")
                                        try {

                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!!.setContentView(R.layout.pickup_deli_update_success)
                                            suceessDialog!!.window!!.attributes.gravity =
                                                Gravity.CENTER_VERTICAL;

                                            val tv_succesmsg =
                                                suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView

                                            val tv_succesok =
                                                suceessDialog!!.findViewById(R.id.tv_succesok) as TextView

                                            tv_succesmsg!!.setText(jobjt.getString("Result"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                val intent = Intent()
                                                intent.putExtra("MESSAGE", android.R.id.message)
                                                setResult(2, intent)
//                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                            )
                                        } catch (e: Exception) {
                                            e.printStackTrace()
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
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
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


                Log.e(TAG,"1212  Permission Granted")
                val jsonObject = documentDetailArrayList.getJSONObject(position)
                ID_LeadDocumentDetails = jsonObject.getString("ID_LeadDocumentDetails")
                DocumentImageFormat = jsonObject.getString("DocumentImageFormat")

                if (Build.VERSION.SDK_INT >= 33) {
                    //ActivityCompat.requestPermissions(this,String[]{readMediaAudio},PERMISSION_CODE)
                    Log.e(TAG, "222399912   ")
                    if (Config.check13Permission(context)) {
                        Log.e(TAG, "222399913   ")

                        if (DocumentImageFormat.equals("")){
                            Toast.makeText(applicationContext,"Document not found",Toast.LENGTH_SHORT).show()
                        }else{
                            getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
                        }
                    }
//                    ActivityCompat.requestPermissions(this, arrayOf(readMediaAudio,readMediaImages,readMediaVideo), PERMISSION_CODE)


                }else{
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
                    }
                    else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
                    }
                    else {



                        if (DocumentImageFormat.equals("")){
                            Toast.makeText(applicationContext,"Document not found",Toast.LENGTH_SHORT).show()
                        }else{
                            getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
                        }


                    }
                }


            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@AccountDetailsActivity, "Failed!", Toast.LENGTH_SHORT)
                    .show()
            }

            /////////

//            try {
//
//                Log.e(TAG, "Documents   162")
//                if (ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ) !== PackageManager.PERMISSION_GRANTED
//                ) {
//                    // Permission is not granted
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(
//                            this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        )
//                    ) {
//                    } else {
//                        ActivityCompat.requestPermissions(
//                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
//                        )
//                    }
//                }
//                else{
//
//                    val jsonObject = documentDetailArrayList.getJSONObject(position)
//                    ID_LeadDocumentDetails = jsonObject.getString("ID_LeadDocumentDetails")
//                    DocumentImageFormat = jsonObject.getString("DocumentImageFormat")
//
//
//                    if (DocumentImageFormat.equals("")){
//                        Toast.makeText(applicationContext,"Document not found",Toast.LENGTH_SHORT).show()
//                    }else{
//                        getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
//                    }

//
//                }
//            }
//            catch (e : Exception){
//
//                Log.e(TAG,"1065     "+e.toString())
//            }




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
                                         //   val DocumentImage = "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"


                                            val decodedString = Base64.decode(DocumentImage, Base64.DEFAULT)
                                          //  val myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                                            //  val mediaData: ByteArray = Base64.decode(decodedString, 0)
                                            //  val bytes = decodedString.toByteArray()
                                            val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
                                            val datetime = sdf.format(Date())
                                         //   destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+System.currentTimeMillis() + DocumentImageFormat)
                                          //  destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ""+System.currentTimeMillis() + DocumentImageFormat)
//                                    destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+datetime + DocumentImageFormat)
                                            val fo: FileOutputStream
//                                            try {
//
//
//
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                                                    destination = File(
//                                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
//                                                        ""
//                                                    )
//                                                    // destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)  +"/" +  getString(R.string.app_name));
//                                                } else {
//                                                    destination = File(
//                                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
//                                                        ""
//                                                    )
//                                                }
//
//                                                if (!destination!!.exists()) {
//                                                    destination!!.createNewFile()
//                                                }
//
//                                                destination = File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).absolutePath + "/" + "", ""+System.currentTimeMillis() + DocumentImageFormat)
//
//                                                fo = FileOutputStream(destination)
//                                                fo.write(decodedString)
//                                                fo.close()
//
//                                                Log.e(TAG,"Success   1230    ")
//
//                                                val file: File = File(destination,"")
//                                                Log.e(TAG,"Success   12301    "+System.currentTimeMillis() + DocumentImageFormat)
//                                                Log.e(TAG,"Success   12301    "+file)
//                                                val map: MimeTypeMap = MimeTypeMap.getSingleton()
//                                                val ext: String = MimeTypeMap.getFileExtensionFromUrl(file.name)
//                                                var type: String? = map.getMimeTypeFromExtension(ext)
//                                                if (type == null)
//                                                    type = "*/*";
//
//                                                val intent = Intent(Intent.ACTION_VIEW)
//                                                val data: Uri = Uri.fromFile(file)
//                                                intent.setDataAndType(data, type)
//                                                startActivity(intent)

//
//                                            } catch (e: Exception) {
//                                                e.printStackTrace()
//                                                Log.e(TAG,"Exception   1231    "+e.toString())
//                                            }


                                            try {
//                                                if (ContextCompat.checkSelfPermission(
//                                                        this,
//                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                                    ) != PackageManager.PERMISSION_GRANTED
//                                                ) {
//                                                    if (ActivityCompat.shouldShowRequestPermissionRationale(
//                                                            this,
//                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                                        )
//                                                    ) {
//                                                        // Show an explanation to the user *asynchronously* -- don't block
//                                                        // this thread waiting for the user's response! After the user
//                                                        // sees the explanation, try again to request the permission.
//
//                                                    } else {
//                                                        // No explanation needed; request the permission
//                                                        ActivityCompat.requestPermissions(
//                                                            this,
//                                                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                                                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
//                                                        )
//                                                    }
//                                                }
//                                                else {


                                                    try {

                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                                            destination = File(
                                                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath,
                                                                ""
                                                            )
                                                            // destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)  +"/" +  getString(R.string.app_name));
                                                        } else {
                                                            destination = File(
                                                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath,
                                                                ""
                                                            )
                                                        }

                                                        if (!destination!!.exists()) {
                                                            destination!!.createNewFile()
                                                            Log.e(TAG,"1448  Not Exist")
                                                        }else{
                                                            Log.e(TAG,"1448  Exist")
                                                        }

                                                        destination = File(
                                                            (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)).absolutePath + "/" +
                                                                    "",
                                                            "" + System.currentTimeMillis() + DocumentImageFormat
                                                        )

                                                        Log.e(TAG,"14481  destination   "+destination)
                                                        val fo: FileOutputStream

                                                        fo = FileOutputStream(destination)
                                                        fo.write(decodedString)
                                                        fo.close()


                                                        val file: File = File(destination,"")
                                                        Log.e(TAG,"1448  destination   "+destination)
                                                        Log.e(TAG,"Success   12301    "+System.currentTimeMillis() + DocumentImageFormat)
                                                        Log.e(TAG,"Success   12301    "+file)
                                                        val map: MimeTypeMap = MimeTypeMap.getSingleton()
                                                        val ext: String = MimeTypeMap.getFileExtensionFromUrl(file.name)
                                                        var type: String? = map.getMimeTypeFromExtension(ext)
                                                        if (type == null)
                                                            type = "*/*";

                                                        val intent = Intent(Intent.ACTION_VIEW)
                                                        intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION)
                                                        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
                                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                        val data: Uri = Uri.fromFile(file)
                                                        intent.setDataAndType(data, type)
                                                        startActivity(intent)



                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                        Log.e(TAG, "FileNotFoundException   23671    " + e.toString())

                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                        Log.e(TAG, "FileNotFoundException   23672    " + e.toString())
                                                    }



                                              //  }
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                                Toast.makeText(this@AccountDetailsActivity, "Failed!", Toast.LENGTH_SHORT)
                                                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun getLeadInfoetails() {
       // var leadInfo = 0
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
                                    val jobjt = jObject.getJSONObject("LeadInfoDetails")
                                    leadInfoArrayList = jobjt.getJSONArray("LeadInfoDetailsList")
                                    if (leadInfoArrayList.length()>0){
                                        if (leadInfo == 0){
                                            leadInfo++
                                            val jObjectLeadInfo = leadInfoArrayList.getJSONObject(0)
                                            txtName!!.setText(""+jObjectLeadInfo.getString("Customer"))
                                            txtAddress!!.setText(""+jObjectLeadInfo.getString("Address"))
                                            txtPhone!!.setText(""+jObjectLeadInfo.getString("MobileNumber"))
                                            txtEmail!!.setText(""+jObjectLeadInfo.getString("Email"))
                                            txtLeadNo!!.setText(""+jObjectLeadInfo.getString("LeadNo"))
                                            txtCategory!!.setText(""+jObjectLeadInfo.getString("Category"))
                                            txtProduct!!.setText(""+jObjectLeadInfo.getString("Product"))

                                            mailid = jObjectLeadInfo.getString("Email")
                                            Reciever_Id = jObjectLeadInfo.getString("ID_Users")
                                            Log.e(TAG,"mailid   77777   "+mailid)
                                            Log.e(TAG,"Reciever_Id   77777   "+Reciever_Id)
//                                            txtTargetDate!!.setText(""+jObjectLeadInfo.getString("NextActionDate"))
//                                            txtAction!!.setText(""+jObjectLeadInfo.getString("NxtActnName"))
//                                            LgCusMobile = jObjectLeadInfo.getString("LgCusMobile")
//                                            LgCusEmail = jObjectLeadInfo.getString("LgCusEmail")
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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

        var txtInfoProductlabel = findViewById<TextView>(R.id.txtInfoProductlabel)

        var txtInfoLeadNo1 = findViewById<TextView>(R.id.txtInfoLeadNo)
        var txtInfoLeadDate1 = findViewById<TextView>(R.id.txtInfoLeadDate)
        var txtInfoLeadSource1 = findViewById<TextView>(R.id.txtInfoLeadSource)
        var txtInfoLeadFrom1 = findViewById<TextView>(R.id.txtInfoLeadFrom)
        var txtInfoCategoryName1 = findViewById<TextView>(R.id.txtInfoCategoryName)
        var txtInfoProduct1 = findViewById<TextView>(R.id.txtInfoProduct)
        var txtInfoNextAction1 = findViewById<TextView>(R.id.txtInfoNextAction)
        var txtInfoName1 = findViewById<TextView>(R.id.txtInfoName)
        var txtInfoAddress1 = findViewById<TextView>(R.id.txtInfoAddress)
        var txtInfoMobile1 = findViewById<TextView>(R.id.txtInfoMobile)
        var txtInfoEmail1 = findViewById<TextView>(R.id.txtInfoEmail)
        var txtInfoCollectedBy1 = findViewById<TextView>(R.id.txtInfoCollectedBy)
        var txtInfoAssigned1 = findViewById<TextView>(R.id.txtInfoAssigned)
        var txtInfoNextActionDate1 = findViewById<TextView>(R.id.txtInfoNextActionDate)
        var txtInfoMrp = findViewById<TextView>(R.id.txtInfoMrp)
        var txtInfoOfferprice = findViewById<TextView>(R.id.txtInfoOfferprice)

//        var txtProject1 = findViewById<TextView>(R.id.txtInfoProject)
//        var txtInfoExpected1 = findViewById<TextView>(R.id.txtInfoExpected)
//        var txtProduct1 = findViewById<TextView>(R.id.txtInfoProduct)
//        var txtCategoryName1 = findViewById<TextView>(R.id.txtInfoCategoryName)
//        var txtNextAction1 = findViewById<TextView>(R.id.txtInfoNextAction)
//        var txtNextActionDate1 = findViewById<TextView>(R.id.txtInfoNextActionDate)
//        var txtActionType1 = findViewById<TextView>(R.id.txtInfoActionType)
//
//        var ll_projectVis = findViewById<LinearLayout>(R.id.ll_projectVis)
//        var ll_productVis = findViewById<LinearLayout>(R.id.ll_productVis)
//        var ll_expectedVis = findViewById<LinearLayout>(R.id.ll_expectedVis)


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
                        Log.e(TAG,"msg   795   "+msg)
                        if (msg!!.length > 0) {
                            if (leadInfoCount == 0){
                                leadInfoCount++
                            val jObject = JSONObject(msg)

                            if (jObject.getString("StatusCode") == "0") {
                                imInfoLoading.visibility = View.GONE
                                llInfoDetail.visibility = View.VISIBLE
                                val jobjt = jObject.getJSONObject("LeadInfoDetails")
                                infoArrayList = jobjt.getJSONArray("LeadInfoDetailsList")
                                if (infoArrayList.length()>0){


//                                        Log.e(TAG,"infoArrayList  845   "+infoArrayList)
//                                        val lLayout = GridLayoutManager(this@AccountDetailsActivity, 1)
//                                        recySubInfo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        recySubInfo!!.setHasFixedSize(true)
//                                        val adapter = infoSubAdapter(this@AccountDetailsActivity, infoArrayList)
//                                        recySubInfo!!.adapter = adapter

                                        val jsonObject1 = infoArrayList.getJSONObject(0)

                                        txtInfoLeadNo1.setText(jsonObject1.getString("LeadNo"))
                                        txtInfoLeadDate1.setText(jsonObject1.getString("LeadDate"))
                                        txtInfoLeadSource1.setText(jsonObject1.getString("LeadSource"))
                                        txtInfoLeadFrom1.setText(jsonObject1.getString("LeadFrom"))
                                        txtInfoCategoryName1.setText(jsonObject1.getString("Category"))
                                        txtInfoProduct1.setText(jsonObject1.getString("Product"))
                                        txtInfoNextAction1.setText(jsonObject1.getString("Action"))
                                        txtInfoName1.setText(jsonObject1.getString("Customer"))
                                        txtInfoAddress1.setText(jsonObject1.getString("Address"))
                                        txtInfoMobile1.setText(jsonObject1.getString("MobileNumber"))
                                        txtInfoEmail1.setText(jsonObject1.getString("Email"))
                                        txtInfoCollectedBy1.setText(jsonObject1.getString("CollectedBy"))
                                        txtInfoAssigned1.setText(jsonObject1.getString("AssignedTo"))
                                        txtInfoNextActionDate1.setText(jsonObject1.getString("TargetDate"))
                                        txtInfoMrp!!.setText(Config.changeTwoDecimel(jsonObject1.getString("LgpMRP")))
                                        txtInfoOfferprice!!.setText(Config.changeTwoDecimel(jsonObject1.getString("LgpSalesPrice")))



//                                        txtName1.setText(jsonObject1.getString("LgCusName"))
//                                        txtAddress1.setText(jsonObject1.getString("LgCusAddress"))
//                                        txtMobile1.setText(jsonObject1.getString("LgCusMobile"))
//                                        txtEmail1.setText(jsonObject1.getString("LgCusEmail"))
//                                        txtAssigned1.setText(jsonObject1.getString("AssignedTo"))
//                                        txtLeadNo1.setText(jsonObject1.getString("LgLeadNo"))
//                                        txtCategoryName1.setText(jsonObject1.getString("CatName"))
//                                        txtNextAction1.setText(jsonObject1.getString("NxtActnName"))
//                                        txtNextActionDate1.setText(jsonObject1.getString("NextActionDate"))
//                                        txtActionType1.setText(jsonObject1.getString("ActnTypeName"))
//
                                        val CompanyCategorySP = context.getSharedPreferences(Config.SHARED_PREF46, 0)
                                        var CompanyCategory = CompanyCategorySP.getString("CompanyCategory","").toString()
//
                                        if (CompanyCategory.equals("0")  || CompanyCategory.equals("1")){
//                                            ll_expectedVis.visibility = View.GONE
//                                            if (jsonObject1.getString("ProjectName").equals("")){
//                                                ll_projectVis.visibility = View.GONE
//                                                ll_productVis.visibility = View.VISIBLE
//                                            }else{
//                                                ll_projectVis.visibility = View.VISIBLE
//                                                ll_productVis.visibility = View.GONE
//                                            }
                                            txtInfoProductlabel.setText("Product")
                                        }
                                        else if (CompanyCategory.equals("2")){
//                                            ll_projectVis.visibility = View.GONE
//                                            ll_productVis.visibility = View.GONE
//                                            ll_expectedVis.visibility = View.VISIBLE
                                            txtInfoProductlabel.setText("Destination")
//                                            txtInfoProductlabel.setText(jsonObject1.getString("Destination"))

                                        }
//
//                                        txtInfoExpected1.setText("")
//                                        txtProject1.setText(jsonObject1.getString("ProjectName"))
//                                        txtProduct1.setText(jsonObject1.getString("ProdName"))

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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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
//                                        latitude = jobjt!!.getString("LocationLatitude")
//                                        longitude = jobjt!!.getString("LocationLongitude")

                                        longitude = jobjt!!.getString("LocationLongitude")
                                        latitude = jobjt!!.getString("LocationLatitude")
                                        Log.e("LocationDetails", latitude + "\n" + longitude)

                                        if (!latitude.equals("") || !longitude.equals("")){

                                            fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@AccountDetailsActivity)
                                            fetchLocation()
                                        }



                                    } else {
                                        ll_location!!.visibility = View.GONE
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
//                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()
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
        addresses =
            geocoder.getFromLocation(latitude.toDouble(),longitude.toDouble(), 1) as List<Address?>;
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun zoomImage(stream: ByteArrayOutputStream) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_image_viewer, null)
        dialogBuilder.setView(dialogView)

        image = dialogView.findViewById<View>(R.id.id_image) as ImageView
        Glide.with(this) .load(stream.toByteArray()).into(image!!)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        scaleGestureDetector.onTouchEvent(event)
//        return true
//    }
//
//    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            val scaleFactor = detector.scaleFactor
//            matrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
//            image.imageMatrix = matrix
//            return true
//        }
//    }

//    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
//        mScaleGestureDetector.onTouchEvent(motionEvent)
//        return true
//
//        Log.e(TAG,"Ontouch  ")
//    }
//
//    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= scaleGestureDetector.scaleFactor
//            mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
//            image!!.scaleX = mScaleFactor
//            image!!.scaleY = mScaleFactor
//            return true
//
//            Log.e(TAG,"Ontouch  2")
//        }
//    }

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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
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

