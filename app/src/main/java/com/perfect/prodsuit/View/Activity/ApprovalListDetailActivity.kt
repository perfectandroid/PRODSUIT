package com.perfect.prodsuit.View.Activity

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.FullLenghRecyclertview
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ReasonAuthAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject


class ApprovalListDetailActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "ApprovalListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    lateinit var stockRTListViewModel: StockRTListViewModel
    lateinit var approvalDetailViewModel: ApprovalDetailViewModel
    lateinit var stockRTArrayList: JSONArray
    var stockCount = 0

    lateinit var authRejectViewModel: AuthRejectViewModel
    var rejectCount = 0

    lateinit var authApproveViewModel: AuthApproveViewModel
    var approveCount = 0

    var correctionCount = 0
    lateinit var authCorrectionViewModel: AuthCorrectionViewModel





    lateinit var key1ArrayList: JSONArray
    lateinit var key2ArrayList: JSONArray
    lateinit var detailsArrayList: JSONArray
    lateinit var key4ArrayList: JSONArray
    lateinit var key5ArrayList: JSONArray


    internal var tv_Key1Click: TextView? = null
    internal var tv_Key2Click: TextView? = null
    internal var tv_Key3Click: TextView? = null
    internal var tv_Key4Click: TextView? = null
    internal var tv_Key5Click: TextView? = null

    internal var btnReject: Button? = null
    internal var btnCorrection: Button? = null
    internal var btnApprove: Button? = null



    internal var recyAprroveDetails: FullLenghRecyclertview? = null

    internal var card_Key1: CardView? = null
    internal var card_Key2: CardView? = null
    internal var card_Key3: CardView? = null
    internal var card_Key4: CardView? = null
    internal var card_Key5: CardView? = null

    internal var ll_key1: LinearLayout? = null
    internal var ll_key2: LinearLayout? = null
    internal var ll_key3: LinearLayout? = null
    internal var ll_key4: LinearLayout? = null
    internal var ll_key5: LinearLayout? = null
    internal var ll_sub_auth1: LinearLayout? = null
    internal var llBtn: LinearLayout? = null

    private var txtRjtCancel: TextView? = null
    private var txtRjtSubmit: TextView? = null

    private var tie_Reason: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null

    private var til_Reason: TextInputLayout? = null
    private var til_Remarks: TextInputLayout? = null

    private var webTransDetail: WebView? = null
    private var webPartyDetails: WebView? = null
    private var webFooterLeft: WebView? = null
    private var webFooterRight: WebView? = null
    private var webDetails: WebView? = null

    var handler: Handler? = null

    var modeKey1: String? = "0"
    var modeKey2: String? = "0"
    var modeKey3: String? = "0"
    var modeKey4: String? = "0"
    var modeKey5: String? = "0"

    var ID_Reason= ""
    var FK_Master= ""
    var FK_AuthID= ""

    lateinit var reasonViewModel: ReasonViewModel
    lateinit var reasonArrayList: JSONArray
    lateinit var reasonSort: JSONArray
    internal var recyReason: RecyclerView? = null
    var dialogReason: Dialog? = null
    var reasonCount = 0

    var jsonObj: JSONObject? = null
    var Module = ""
    var AuthID = ""

    var TransactionDetails =  ""
    var PartyDetails  =  ""
    var SubTitleHTML  =  ""
    var FooterLeft =  ""
    var FooterRight =  ""

    var saveAttendanceMark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approval_list_detail)
        context = this@ApprovalListDetailActivity

        stockRTListViewModel = ViewModelProvider(this).get(StockRTListViewModel::class.java)
        approvalDetailViewModel = ViewModelProvider(this).get(ApprovalDetailViewModel::class.java)
        reasonViewModel = ViewModelProvider(this).get(ReasonViewModel::class.java)
        authRejectViewModel = ViewModelProvider(this).get(AuthRejectViewModel::class.java)
        authApproveViewModel = ViewModelProvider(this).get(AuthApproveViewModel::class.java)
        authCorrectionViewModel = ViewModelProvider(this).get(AuthCorrectionViewModel::class.java)

        setRegViews()
//        generateSS()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)

        Module = intent!!.getStringExtra("Module").toString()
        Log.e(TAG,"jsonObj  16555   "+jsonObj)
        Log.e(TAG,"Module  16555   "+Module)
        if (Module.equals("AWAIT")){
            Module =  jsonObj!!.getString("Module")
            AuthID = jsonObj!!.getString("ID_AuthorizationData")
        }else{

            AuthID = jsonObj!!.getString("ID_FIELD")
        }


        Log.e(TAG,"163333   Start")
        checkAttendance()
        stockCount = 0
        getStckList(Module,AuthID)

    }

    private fun generateSS() {
        val map: MutableMap<String, String> = LinkedHashMap()
        map["Active"] = "33"
        map["Renewals Completed"] = "3"
        map["Application"] = "15"

        Log.e(TAG,"36666   "+map)
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_Key1Click = findViewById(R.id.tv_Key1Click)
        tv_Key2Click = findViewById(R.id.tv_Key2Click)
        tv_Key3Click = findViewById(R.id.tv_Key3Click)
        tv_Key4Click = findViewById(R.id.tv_Key4Click)
        tv_Key5Click = findViewById(R.id.tv_Key5Click)

        btnReject = findViewById(R.id.btnReject)
        btnCorrection = findViewById(R.id.btnCorrection)
        btnApprove = findViewById(R.id.btnApprove)
        llBtn = findViewById(R.id.llBtn)


        recyAprroveDetails = findViewById(R.id.recyAprroveDetails)
        webTransDetail = findViewById(R.id.webTransDetail)
        webPartyDetails = findViewById(R.id.webPartyDetails)
        webFooterLeft = findViewById(R.id.webFooterLeft)
        webFooterRight = findViewById(R.id.webFooterRight)
        webDetails = findViewById(R.id.webDetails)

        ll_key1 = findViewById(R.id.ll_key1)
        ll_key2 = findViewById(R.id.ll_key2)
        ll_key3 = findViewById(R.id.ll_key3)
        ll_key4 = findViewById(R.id.ll_key4)
        ll_key5 = findViewById(R.id.ll_key5)

        card_Key1 = findViewById(R.id.card_Key1)
        card_Key2 = findViewById(R.id.card_Key2)
        card_Key3 = findViewById(R.id.card_Key3)
        card_Key4 = findViewById(R.id.card_Key4)
        card_Key5 = findViewById(R.id.card_Key5)

        tv_Key1Click!!.setOnClickListener(this)
        tv_Key2Click!!.setOnClickListener(this)
        tv_Key3Click!!.setOnClickListener(this)
        tv_Key4Click!!.setOnClickListener(this)
        tv_Key5Click!!.setOnClickListener(this)

        btnReject!!.setOnClickListener(this)
        btnCorrection!!.setOnClickListener(this)
        btnApprove!!.setOnClickListener(this)


    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        Log.e(TAG,"1633331      "+boolAttendance)
        if (boolAttendance) {
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status", "")
            if (status.equals("0") || status.equals("")) {
                Common.punchingRedirectionConfirm(this, "", "")
            } else if (status.equals("1")) {
                saveAttendanceMark = true
            }

        } else {
            saveAttendanceMark = true
        }
    }

    private fun getStckList(Module : String,AuthID : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                approvalDetailViewModel.getApprovalDetail(this,Module!!,AuthID!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (stockCount == 0) {
                                    stockCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   9991010   " + msg)
                                    var ss = 0
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthorizationActionDetails")

//                                        val TransactionDetails: String =  jobjt.getString("TransactionDetails")
//                                        val PartyDetails: String =  jobjt.getString("PartyDetails")
//                                        val SubTitleHTML: String =  jobjt.getString("SubTitleHTML")
////                                        val SubDetails: String =  jobjt.getString("SubDetails")
////                                        val SubTitle: String =  jobjt.getString("SubTitle")
//                                        FK_Master = jobjt.getString("FK_Master")
//                                        val FooterLeft: String =  jobjt.getString("FooterLeft")
//                                        val FooterRight: String =  jobjt.getString("FooterRight")
//                                        key4ArrayList = jobjt.getJSONArray("SubDetailsData")

                                        TransactionDetails =  jobjt.getString("TransactionDetails")
                                        PartyDetails =  jobjt.getString("PartyDetails")
                                        SubTitleHTML =  jobjt.getString("SubTitleHTML")
                                        FK_Master = jobjt.getString("FK_Master")
                                        FK_AuthID = jobjt.getString("AuthID")
                                        FooterLeft =  jobjt.getString("FooterLeft")
                                        FooterRight =  jobjt.getString("FooterRight")
                                        key4ArrayList = jobjt.getJSONArray("SubDetailsData")



                                        if (jobjt.getString("ActiveCorrectionOption").equals("True")){
                                            btnCorrection!!.visibility = View.VISIBLE
                                        }


                                        card_Key1!!.visibility = View.GONE
                                        card_Key2!!.visibility = View.GONE
                                        card_Key3!!.visibility = View.GONE
                                        card_Key4!!.visibility = View.GONE
                                        card_Key5!!.visibility = View.GONE
//                                        val animator = ObjectAnimator.ofFloat(card_Key1, "alpha", 0f, 1.5f)
//                                        animator.duration = 5000 // Animation duration in milliseconds
//                                        animator.start()

//
////
//                                        val handler: Handler = object : Handler() {
//                                            override fun handleMessage(msg: Message) {
//                                                super.handleMessage(msg)
//                                                //specify msg value
//                                                if (msg.what === 10) {
//                                                    //do this
//                                                        Log.e(TAG,"2611110   ")
//                                                    handler!!.sendEmptyMessage(20);
//                                                } else if (msg.what === 20) {
//                                                    // do this
//                                                 Log.e(TAG,"2611111   ")
//                                                } else {
//                                                    //so on....
//                                                }
//                                            }
//                                        }
//
//                                        handler!!.sendEmptyMessage(10);

                                        if (!TransactionDetails.equals("")){
                                            showTransactionDetails()
                                        }
                                        else if (!PartyDetails.equals("")){
                                            showPartyDetails()
                                        }
                                        else if (!FooterLeft.equals("")){
                                            showFooterLeft()
                                        }
                                        else if (!FooterRight.equals("")){
                                            showFooterRight()
                                        }
                                        else if (key4ArrayList.length()>0){
                                            showkey4ArrayList()
                                        }

                                        // HIDE   420

//                                        if (!TransactionDetails.equals("")){
//                                            card_Key1!!.visibility = View.VISIBLE
//                                            webTransDetail!!.getSettings().setJavaScriptEnabled(true);
//                                            webTransDetail!!.loadDataWithBaseURL(null, TransactionDetails, "text/html", "utf-8", null);
//                                        }
//                                        if (!PartyDetails.equals("")){
//                                            card_Key2!!.visibility = View.VISIBLE
//                                            webPartyDetails!!.getSettings().setJavaScriptEnabled(true);
//                                            webPartyDetails!!.loadDataWithBaseURL(null, PartyDetails, "text/html", "utf-8", null);
//                                        }
//
//                                        if (!FooterLeft.equals("")){
//                                            card_Key4!!.visibility = View.VISIBLE
//                                            webFooterLeft!!.getSettings().setJavaScriptEnabled(true);
//                                            webFooterLeft!!.loadDataWithBaseURL(null, FooterLeft, "text/html", "utf-8", null);
//                                        }
//
//                                        if (!FooterRight.equals("")){
//                                            card_Key5!!.visibility = View.VISIBLE
//                                            webFooterRight!!.getSettings().setJavaScriptEnabled(true);
//                                            webFooterRight!!.loadDataWithBaseURL(null, FooterRight, "text/html", "utf-8", null);
//                                        }
//
//                                        if (key4ArrayList.length()>0){
//
//                                            webDetails!!.getSettings().setJavaScriptEnabled(true);
//                                            webDetails!!.loadDataWithBaseURL(null, SubTitleHTML, "text/html", "utf-8", null);
//
////                                            val lLayout = GridLayoutManager(this@ApprovalListDetailActivity, 1)
////                                            recyAprroveDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////                                            val adapter = ApprovalListDetailAdapter(this@ApprovalListDetailActivity, key4ArrayList)
////                                            recyAprroveDetails!!.adapter = adapter
//
//                                            for (i in 0 until key4ArrayList.length()) {
//                                                var jsonObject = key4ArrayList.getJSONObject(i)
//
//                                                val keys = jsonObject!!.keys()
//                                                val dynamicLinearLayoutmain = LinearLayout(context)
//                                                dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
//                                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                                    LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL
//
//                                                while (keys.hasNext()) {
//                                                    val key = keys.next()
//                                                    Log.e(TAG,"JSON_KEY  4566   :  "+ key +"  :  "+jsonObject!!.getString(key)) // Output: key1, key2, key3
//
//                                                    if (!key.equals("SlNo")){
//
//                                                        val dynamicLinearLayout = LinearLayout(context)
//                                                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
//                                                            LinearLayout.LayoutParams.MATCH_PARENT,
//                                                            LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        dynamicLinearLayout.orientation = LinearLayout.HORIZONTAL
//                                                        val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)
//
//                                                        val customFont: Typeface = context.resources.getFont(R.font.myfont)
//
//                                                        val textView1 = TextView(context)
//                                                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        layoutParams1.weight = 1.9f
//                                                        textView1!!.layoutParams = layoutParams1
//
//                                                        textView1.text = key
//                                                        textView1!!.setTextSize(15F)
//                                                        textView1!!.setTypeface(customFont);
//                                                        textView1!!.setTextColor(context.resources.getColor(R.color.black))
//
//                                                        val textView0 = TextView(context)
//                                                        //  textView1.text = key
//                                                        textView0.text = " : "
//                                                        textView0.compoundDrawablePadding = drawablePadding
//                                                        textView0!!.setTextSize(15F)
//                                                        textView0.setCompoundDrawablePadding(2)
//                                                        textView0!!.setTypeface(customFont);
//                                                        textView0!!.setTextColor(context.resources.getColor(R.color.black))
//                                                        textView0!!.setPadding(5, -10, 5, 0)
//
//                                                        val textView2 = TextView(context)
//                                                        val layoutParams2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        layoutParams2.weight = 1.0f
//                                                        textView2!!.layoutParams = layoutParams2
//                                                        textView2.text = jsonObject!!.getString(key)
//                                                        textView2!!.setTextSize(14F)
//                                                        textView2!!.setTypeface(customFont);
//                                                        textView2!!.setTextColor(context.resources.getColor(R.color.greydark))
//                                                        textView2!!.setPadding(0, -10, 0, 0)
//                                                        textView2!!.marginTop.div(-10)
//
//                                                        val layoutParamsSub = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        layoutParamsSub.setMargins(15, -2, 15, -2)
//                                                        dynamicLinearLayout.addView(textView1);
//                                                        dynamicLinearLayout.addView(textView0);
//                                                        dynamicLinearLayout.addView(textView2);
//                                                        dynamicLinearLayout.layoutParams = layoutParamsSub
//
//                                                        val layoutParamMain = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        layoutParamMain.setMargins(5, 5, 5, 0)
//                                                        dynamicLinearLayoutmain.setBackgroundResource(R.drawable.shape_shadownew)
//                                                        dynamicLinearLayoutmain.layoutParams = layoutParamMain
//
//                                                        dynamicLinearLayoutmain.addView(dynamicLinearLayout);
//
//
//                                                    }
//
//
//                                                }
//
//                                                ll_key3!!.addView(dynamicLinearLayoutmain);
//                                            }
//                                        }


                                        // HIDE   420



//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                            tv_Key3Click!!.setText(Html.fromHtml("<strong>Lead Details</strong>", Html.FROM_HTML_MODE_LEGACY));
//                                        } else{
//                                            tv_Key3Click!!.setText(Html.fromHtml("<strong>Lead Details</strong>"));
//                                        }











//                                        Log.e(TAG, "jobjt   99910101   " + msg)
//                                        val keys = jobjt!!.keys()
//                                        while (keys.hasNext()) {
//                                            val key = keys.next()
//                                            Log.e(TAG, "key   99910102   " + key)
//                                            if (ss == 0){
//                                                ss++
//                                                val cleanText: String = Config.addSpaceBetweenLowerAndUpper(key)
//                                                tv_Key1Click!!.setText(cleanText)
//                                                val cleanText1: String =  jobjt.getString(key)
//                                                Log.e(TAG, "key   99910103   " + cleanText1)
//                                                webTransDetail!!.getSettings().setJavaScriptEnabled(true);
//                                                webTransDetail!!.loadDataWithBaseURL(null, cleanText1, "text/html", "utf-8", null);
//
//
//                                            }
//
//                                        }


//                                        val jobjt = jObject.getJSONObject("AuthRTDetails")
//                                        key1ArrayList = jobjt.getJSONArray("Key1")
//                                        key2ArrayList = jobjt.getJSONArray("Key2")
//                                        key4ArrayList = jobjt.getJSONArray("Key4")
//                                        key5ArrayList = jobjt.getJSONArray("Key5")
//                                        Log.e(TAG, "key1ArrayList   999101   " + key1ArrayList)
//
//
//
//                                        if (key1ArrayList.length() > 0) {
//                                         //   card_Key1!!.visibility = View.VISIBLE
//                                            Log.e(TAG, "msg   9991023   " + key1ArrayList)
//                                                val jsonObject = key1ArrayList.getJSONObject(0)
//                                                val length: Int = jsonObject.length()
//                                            Log.e(TAG, "length   9991023   " + length)
//                                                val keys = jsonObject.keys()
//                                                var indexKey1 = 0
//                                                while (keys.hasNext()) {
//                                                    val key = keys.next()
//                                                    if (indexKey1 == 0) {
//                                                        indexKey1++
//                                                        Log.e("JSON_KEY  9444441   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                        tv_Key1Click!!.setText(jsonObject.getString(key))
//                                                    }else{
//                                                        Log.e("JSON_KEY  9444442    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                        indexKey1++
//                                                        val dynamicLinearLayout = LinearLayout(this)
//                                                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
//                                                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                                                LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                        dynamicLinearLayout.orientation = LinearLayout.VERTICAL
//
//                                                        val customFont: Typeface = resources.getFont(R.font.myfont)
//
//                                                        val textView1 = TextView(this)
//                                                        textView1.text = key
//                                                        textView1!!.setTextSize(15F)
//                                                        textView1!!.setTypeface(customFont);
//                                                        textView1!!.setTextColor(resources.getColor(R.color.black))
//
//
//                                                        val textView2 = TextView(this)
//                                                        textView2.text = jsonObject.getString(key)
//                                                        textView2!!.setTextSize(14F)
//                                                        textView2!!.setTypeface(customFont);
//                                                        textView2!!.setTextColor(resources.getColor(R.color.greydark))
//                                                        textView2!!.setPadding(0, -10, 0, 0)
//
//                                                        dynamicLinearLayout.addView(textView1);
//                                                        dynamicLinearLayout.addView(textView2);
//
//                                                        ll_key1!!.addView(dynamicLinearLayout);
//
//
//                                                    }
//
//                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                }
//                                        }
//                                        else{
//                                            card_Key1!!.visibility = View.GONE
//                                        }
//
//                                        //////////
//
//                                        if (key2ArrayList.length() > 0) {
//                                           // card_Key2!!.visibility = View.VISIBLE
//                                            Log.e(TAG, "msg   9991023   " + key2ArrayList)
//                                            val jsonObject = key2ArrayList.getJSONObject(0)
//                                            val length: Int = jsonObject.length()
//                                            Log.e(TAG, "length   9991023   " + length)
//                                            val keys = jsonObject.keys()
//                                            var indexKey1 = 0
//                                            while (keys.hasNext()) {
//                                                val key = keys.next()
//                                                if (indexKey1 == 0) {
//                                                    indexKey1++
//                                                    Log.e("JSON_KEY  9444441   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    tv_Key2Click!!.setText(jsonObject.getString(key))
//                                                }else{
//                                                    Log.e("JSON_KEY  9444442    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    indexKey1++
//                                                    val dynamicLinearLayout = LinearLayout(this)
//                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
//                                                        LinearLayout.LayoutParams.MATCH_PARENT,
//                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL
//
//                                                    val customFont: Typeface = resources.getFont(R.font.myfont)
//
//                                                    val textView1 = TextView(this)
//                                                    textView1.text = key
//                                                    textView1!!.setTextSize(15F)
//                                                    textView1!!.setTypeface(customFont);
//                                                    textView1!!.setTextColor(resources.getColor(R.color.black))
//
//
//                                                    val textView2 = TextView(this)
//                                                    textView2.text = jsonObject.getString(key)
//                                                    textView2!!.setTextSize(14F)
//                                                    textView2!!.setTypeface(customFont);
//                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
//                                                    textView2!!.setPadding(0, -10, 0, 0)
//
//                                                    dynamicLinearLayout.addView(textView1);
//                                                    dynamicLinearLayout.addView(textView2);
//
//                                                    ll_key2!!.addView(dynamicLinearLayout);
//
//
//                                                }
//
//                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                            }
//                                        }
//                                        else{
//                                            card_Key2!!.visibility = View.GONE
//                                        }
//
//                                        val Key3jobjt = jobjt.getJSONObject("Key3")
//                                        Log.e(TAG, "jobjtKey3   9991013   " + Key3jobjt)
//                                        Log.e(TAG, "jobjtKey3   9991013   " + Key3jobjt.getString("SubTitle"))
//                                        tv_Key3Click!!.setText(Key3jobjt.getString("SubTitle").toString())
//                                        detailsArrayList = Key3jobjt.getJSONArray("Details")
//
//                                        if (detailsArrayList.length() > 0){
//                                          //  card_Key3!!.visibility = View.VISIBLE
//                                            Log.e(TAG, "detailsArrayList   9991014   " + detailsArrayList)
//
//                                            val lLayout = GridLayoutManager(this@ApprovalListDetailActivity, 1)
//                                            recyAprroveDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ApprovalListDetailAdapter(this@ApprovalListDetailActivity, detailsArrayList)
//                                            recyAprroveDetails!!.adapter = adapter
//                                            // adapter.setClickListener(this@EmiActivity)
//                                        }else{
//                                            card_Key3!!.visibility = View.GONE
//                                        }
//
//
//                                        if (key4ArrayList.length() > 0) {
//                                            //   card_Key1!!.visibility = View.VISIBLE
//                                            Log.e(TAG, "msg   3044441   " + key4ArrayList)
//                                            val jsonObject = key4ArrayList.getJSONObject(0)
//                                            val length: Int = jsonObject.length()
//                                            Log.e(TAG, "length   3044442   " + length)
//                                            val keys = jsonObject.keys()
//                                            var indexKey1 = 0
//                                            while (keys.hasNext()) {
//                                                val key = keys.next()
//                                                if (indexKey1 == 0) {
//                                                    indexKey1++
//                                                    Log.e("JSON_KEY  3044443   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    tv_Key4Click!!.setText(jsonObject.getString(key))
//                                                }else{
//                                                    Log.e("JSON_KEY  3044444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    indexKey1++
//                                                    val dynamicLinearLayout = LinearLayout(this)
//                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
//                                                        LinearLayout.LayoutParams.MATCH_PARENT,
//                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL
//
//                                                    val customFont: Typeface = resources.getFont(R.font.myfont)
//
//                                                    val textView1 = TextView(this)
//                                                    textView1.text = key
//                                                    textView1!!.setTextSize(15F)
//                                                    textView1!!.setTypeface(customFont);
//                                                    textView1!!.setTextColor(resources.getColor(R.color.black))
//
//
//                                                    val textView2 = TextView(this)
//                                                    textView2.text = jsonObject.getString(key)
//                                                    textView2!!.setTextSize(14F)
//                                                    textView2!!.setTypeface(customFont);
//                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
//                                                    textView2!!.setPadding(0, -10, 0, 0)
//
//                                                    dynamicLinearLayout.addView(textView1);
//                                                    dynamicLinearLayout.addView(textView2);
//
//                                                    ll_key4!!.addView(dynamicLinearLayout);
//
//
//                                                }
//
//                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                            }
//                                        }
//                                        else{
//                                            card_Key4!!.visibility = View.GONE
//                                        }
//
//                                        if (key5ArrayList.length() > 0) {
//                                            //   card_Key1!!.visibility = View.VISIBLE
//                                            Log.e(TAG, "msg   3044441   " + key5ArrayList)
//                                            val jsonObject = key5ArrayList.getJSONObject(0)
//                                            val length: Int = jsonObject.length()
//                                            Log.e(TAG, "length   3044442   " + length)
//                                            val keys = jsonObject.keys()
//                                            var indexKey1 = 0
//                                            while (keys.hasNext()) {
//                                                val key = keys.next()
//                                                if (indexKey1 == 0) {
//                                                    indexKey1++
//                                                    Log.e("JSON_KEY  3044443   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    tv_Key5Click!!.setText(jsonObject.getString(key))
//                                                }else{
//                                                    Log.e("JSON_KEY  3044444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                                    indexKey1++
//                                                    val dynamicLinearLayout = LinearLayout(this)
//                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
//                                                        LinearLayout.LayoutParams.MATCH_PARENT,
//                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
//                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL
//
//                                                    val customFont: Typeface = resources.getFont(R.font.myfont)
//
//                                                    val textView1 = TextView(this)
////                                                    val htmlContent = "<html>\n" +
////                                                            "<body>\n" +
////                                                            "<table><tr><td>Bill Tot </td><td> : </td><td style=\\\"text-align:right;\\\">67500.00</td></tr><tr><td>Discount </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td>OtherCharge </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td>RoundOff </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td><strong>Net Amount </strong></td><td> : </td><td style=\\\"text-align: right;\\\"><strong>67500.00</strong></td></tr></table>\n" +
////                                                            "</body>\n" +
////                                                            "</html>"
//
//                                                    val htmlContent = """<h1>Heading 1</h1>
//        <h2>Heading</h2>
//        <p>This is some html. Look, here\'s an <u>underline</u>.</p>
//        <p>Look, this is <em>emphasized.</em> And here\'s some <b>bold</b>.</p>
//        <p>Here are UL list items:
//        <ul>
//        <li>One</li>
//        <li>Two</li>
//        <li>Three</li>
//        </ul>
//        <p>Here are OL list items:
//        <ol>
//        <li>One</li>
//        <li>Two</li>
//        <li>Three</li>
//        </ol>"""
//
////                                                    val htmlContent = "<h1>Heading 1</h1>\\n\" +\n" +
////                                                            "             \"        <h2>Heading 2</h2>\\n\" +\n" +
////                                                            "             \"        <p>This is some html. Look, here\\\\'s an <u>underline</u>.</p>\\n\" +\n" +
////                                                            "             \"        <p>Look, this is <em>emphasized.</em> And here\\\\'s some <b>bold</b>.</p>\\n\" +\n" +
////                                                            "             \"        <p>Here are UL list items:\\n\" +\n" +
////                                                            "             \"        <ul>\\n\" +\n" +
////                                                            "             \"        <li>One</li>\\n\" +\n" +
////                                                            "             \"        <li>Two</li>\\n\" +\n" +
////                                                            "             \"        <li>Three</li>\\n\" +\n" +
////                                                            "             \"        </ul>\\n\" +\n" +
////                                                            "             \"        <p>Here are OL list items:\\n\" +\n" +
////                                                            "             \"        <ol>\\n\" +\n" +
////                                                            "             \"        <li>One</li>\\n\" +\n" +
////                                                            "             \"        <li>Two</li>\\n\" +\n" +
////                                                            "             \"        <li>Three</li>\\n\" +\n" +
////                                                            "             \"        </ol>"
//
//
//                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                                        textView1.setText(Html.fromHtml(htmlContent.toString(), Html.FROM_HTML_MODE_LEGACY));
//                                                    } else{
//                                                        textView1.setText(Html.fromHtml(htmlContent.toString()));
//                                                    }
//
//                                             //       textView1.setText(Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY))
//                                                  //  textView1.text = key
//                                                    textView1!!.setTextSize(15F)
//                                                    textView1!!.setTypeface(customFont);
//                                                    textView1!!.setTextColor(resources.getColor(R.color.black))
//
//
//                                                    val textView2 = TextView(this)
//                                                    textView2.text = jsonObject.getString(key)
//                                                    textView2!!.setTextSize(14F)
//                                                    textView2!!.setTypeface(customFont);
//                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
//                                                    textView2!!.setPadding(0, -10, 0, 0)
//
//                                                    dynamicLinearLayout.addView(textView1);
//                                                    dynamicLinearLayout.addView(textView2);
//
//                                                    ll_key5!!.addView(dynamicLinearLayout);
//
//
//                                                }
//
//                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
//                                            }
//                                        }
//                                        else{
//                                            card_Key4!!.visibility = View.GONE
//                                        }
//
//
//
//                                        ///////////
//
//                                        if (key1ArrayList.length() > 0){
//                                            modeKey1 = "1"
//                                            modeKey2 = "0"
//                                            modeKey3 = "0"
//                                            modeKey4 = "0"
//                                            modeKey5 = "0"
//                                        }
//                                        else if (key2ArrayList.length() > 0){
//                                            modeKey1 = "0"
//                                            modeKey2 = "1"
//                                            modeKey3 = "0"
//                                            modeKey4 = "0"
//                                            modeKey5 = "0"
//                                        }
//                                        else if (detailsArrayList.length() > 0){
//                                            modeKey1 = "0"
//                                            modeKey2 = "0"
//                                            modeKey3 = "1"
//                                            modeKey4 = "0"
//                                            modeKey5 = "0"
//                                        }
//                                        else if (key4ArrayList.length() > 0){
//                                            modeKey1 = "0"
//                                            modeKey2 = "0"
//                                            modeKey3 = "0"
//                                            modeKey4 = "1"
//                                            modeKey5 = "0"
//                                        }
//
//                                        else if (key5ArrayList.length() > 0){
//                                            modeKey1 = "0"
//                                            modeKey2 = "0"
//                                            modeKey3 = "0"
//                                            modeKey4 = "0"
//                                            modeKey5 = "1"
//                                        }
//
//                                        hideShows()
//
//
//


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun showTransactionDetails() {
        if (!TransactionDetails.equals("")){
            card_Key1!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(card_Key1, "alpha", 0f, 0.5f)
            animator.duration = 900 // Animation duration in milliseconds
            animator.start()
            webTransDetail!!.getSettings().setJavaScriptEnabled(true);
            webTransDetail!!.setBackgroundColor(context.getColor(R.color.web_color));
            webTransDetail!!.loadDataWithBaseURL(null, TransactionDetails, "text/html", "utf-8", null);

            Handler(Looper.getMainLooper()).postDelayed({
                //Do something after 100ms
                showPartyDetails()
            }, 900)
        }else{
            showPartyDetails()
        }
    }

    private fun showPartyDetails() {
        if (!PartyDetails.equals("")){
            card_Key2!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(card_Key2, "alpha", 0f, 0.5f)
            animator.duration = 900 // Animation duration in milliseconds
            animator.start()
            webPartyDetails!!.getSettings().setJavaScriptEnabled(true);
            webPartyDetails!!.setBackgroundColor(context.getColor(R.color.web_color));
            webPartyDetails!!.loadDataWithBaseURL(null, PartyDetails, "text/html", "utf-8", null);
            Handler(Looper.getMainLooper()).postDelayed({
                //Do something after 100ms
                showFooterLeft()
            }, 900)
        }else{
            showFooterLeft()
        }
    }

    private fun showFooterLeft() {
        if (!FooterLeft.equals("")){
            card_Key4!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(card_Key4, "alpha", 0f, 0.5f)
            animator.duration = 900 // Animation duration in milliseconds
            animator.start()
            webFooterLeft!!.getSettings().setJavaScriptEnabled(true);
            webFooterLeft!!.setBackgroundColor(context.getColor(R.color.web_color));
            webFooterLeft!!.loadDataWithBaseURL(null, FooterLeft, "text/html", "utf-8", null);
            Handler(Looper.getMainLooper()).postDelayed({
                //Do something after 100ms
                showFooterRight()
            }, 900)
        }else{
            showFooterRight()
        }
    }

    private fun showFooterRight() {
        Log.e(TAG,"902222  showFooterRight "+FooterRight)
        if (!FooterRight.equals("")){
            card_Key5!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(card_Key5, "alpha", 0f, 0.5f)
            animator.duration = 1000 // Animation duration in milliseconds
            animator.start()
            webFooterRight!!.getSettings().setJavaScriptEnabled(true);
            webFooterRight!!.setBackgroundColor(context.getColor(R.color.web_color));
            webFooterRight!!.loadDataWithBaseURL(null, FooterRight, "text/html", "utf-8", null);
            Handler(Looper.getMainLooper()).postDelayed({
                //Do something after 100ms
                showkey4ArrayList()
            }, 900)
        }else{
            showkey4ArrayList()
        }
    }

    private fun showkey4ArrayList() {

        Log.e(TAG,"902222  showkey4ArrayList "+key4ArrayList)
        if (key4ArrayList.length()>0){
            card_Key3!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(card_Key3, "alpha", 0f, 1.5f)
            animator.duration = 900 // Animation duration in milliseconds
            animator.start()
            webDetails!!.getSettings().setJavaScriptEnabled(true);
            webDetails!!.setBackgroundColor(context.getColor(R.color.web_color));
            webDetails!!.loadDataWithBaseURL(null, SubTitleHTML, "text/html", "utf-8", null);

//                                            val lLayout = GridLayoutManager(this@ApprovalListDetailActivity, 1)
//                                            recyAprroveDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ApprovalListDetailAdapter(this@ApprovalListDetailActivity, key4ArrayList)
//                                            recyAprroveDetails!!.adapter = adapter

            for (i in 0 until key4ArrayList.length()) {
                var jsonObject = key4ArrayList.getJSONObject(i)

                val keys = jsonObject!!.keys()
                val dynamicLinearLayoutmain = LinearLayout(context)
                dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL

                while (keys.hasNext()) {
                    val key = keys.next()
                    Log.e(TAG,"JSON_KEY  4566   :  "+ key +"  :  "+jsonObject!!.getString(key)) // Output: key1, key2, key3

                    if (!key.equals("SlNo")){

                        val dynamicLinearLayout = LinearLayout(context)
                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        dynamicLinearLayout.orientation = LinearLayout.HORIZONTAL
                        val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)

                        val customFont: Typeface = context.resources.getFont(R.font.myfont)

                        val textView1 = TextView(context)
                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams1.weight = 1.9f
                        textView1!!.layoutParams = layoutParams1

                        textView1.text = key
                        textView1!!.setTextSize(15F)
                        textView1!!.setTypeface(customFont);
                        textView1!!.setTextColor(context.resources.getColor(R.color.black))

                        val textView0 = TextView(context)
                        //  textView1.text = key
                        textView0.text = " : "
                        textView0.compoundDrawablePadding = drawablePadding
                        textView0!!.setTextSize(15F)
                        textView0.setCompoundDrawablePadding(2)
                        textView0!!.setTypeface(customFont);
                        textView0!!.setTextColor(context.resources.getColor(R.color.black))
                        textView0!!.setPadding(5, -10, 5, 0)

                        val textView2 = TextView(context)
                        val layoutParams2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams2.weight = 1.0f
                        textView2!!.layoutParams = layoutParams2
                        textView2.text = jsonObject!!.getString(key)
                        textView2!!.setTextSize(14F)
                        textView2!!.setTypeface(customFont);
                        textView2!!.setTextColor(context.resources.getColor(R.color.greydark))
                        textView2!!.setPadding(0, -10, 0, 0)
                        textView2!!.marginTop.div(-10)

                        val layoutParamsSub = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParamsSub.setMargins(15, -2, 15, -2)
                        dynamicLinearLayout.addView(textView1);
                        dynamicLinearLayout.addView(textView0);
                        dynamicLinearLayout.addView(textView2);
                        dynamicLinearLayout.layoutParams = layoutParamsSub

                        val layoutParamMain = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParamMain.setMargins(5, 5, 5, 0)
                        dynamicLinearLayoutmain.setBackgroundResource(R.drawable.shape_approve_list)
                        dynamicLinearLayoutmain.layoutParams = layoutParamMain

                        dynamicLinearLayoutmain.addView(dynamicLinearLayout);


                    }


                }

                ll_key3!!.addView(dynamicLinearLayoutmain);
            }


        }

        llBtn!!.visibility = View.VISIBLE


    }


    private fun hideShows() {

        ll_key1!!.visibility = View.GONE
        ll_key2!!.visibility = View.GONE
        ll_key3!!.visibility = View.GONE
        ll_key4!!.visibility = View.GONE
        ll_key5!!.visibility = View.GONE

        if (modeKey1.equals("1")) {
            ll_key1!!.visibility = View.VISIBLE
        }
        if (modeKey2.equals("1")) {
            ll_key2!!.visibility = View.VISIBLE
        }
        if (modeKey3.equals("1")) {
            ll_key3!!.visibility = View.VISIBLE
        }
        if (modeKey4.equals("1")) {
            ll_key4!!.visibility = View.VISIBLE
        }
        if (modeKey5.equals("1")) {
            ll_key5!!.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tv_Key1Click->{

                if (key1ArrayList.length() > 0){

                    if (modeKey1.equals("0")){
                        modeKey1 = "1"
                    }else{
                        modeKey1 = "0"
                    }

                    //modeKey1 = "1"
                    modeKey2 = "0"
                    modeKey3 = "0"
                    modeKey4 = "0"
                    modeKey5 = "0"
                    hideShows()
                }


            }
            R.id.tv_Key2Click->{
                if (key2ArrayList.length() > 0){

                    if (modeKey2.equals("0")){
                        modeKey2 = "1"
                    }else{
                        modeKey2 = "0"
                    }

                    modeKey1 = "0"
//                    modeKey2 = "1"
                    modeKey3 = "0"
                    modeKey4 = "0"
                    modeKey5 = "0"
                    hideShows()
                }

            }
            R.id.tv_Key3Click->{
               if (detailsArrayList.length() > 0){

                   if (modeKey3.equals("0")){
                       modeKey3 = "1"
                   }else{
                       modeKey3 = "0"
                   }

                    modeKey1 = "0"
                    modeKey2 = "0"
                   // modeKey3 = "1"
                    modeKey4 = "0"
                    modeKey5 = "0"
                    hideShows()
                }

            }

            R.id.tv_Key4Click->{
                if (key4ArrayList.length() > 0){

                    if (modeKey4.equals("0")){
                        modeKey4 = "1"
                    }else{
                        modeKey4 = "0"
                    }

                    modeKey1 = "0"
                    modeKey2 = "0"
                    modeKey3 = "0"
                  //  modeKey4 = "1"
                    modeKey5 = "0"
                    hideShows()
                }

            }
            R.id.tv_Key5Click->{
                if (key5ArrayList.length() > 0){

                    if (modeKey5.equals("0")){
                        modeKey5 = "1"
                    }else{
                        modeKey5 = "0"
                    }

                    modeKey1 = "0"
                    modeKey2 = "0"
                    modeKey3 = "0"
                    modeKey4 = "0"
                   // modeKey5 = "1"
                    hideShows()
                }

            }

            R.id.btnReject->{
                checkAttendance()
                if (saveAttendanceMark) {
                    Config.disableClick(v)
                    rejectBottomSheet()
                }

            }

            R.id.btnApprove->{

                checkAttendance()
                if (saveAttendanceMark) {
                    Config.disableClick(v)
                    approveConfirmation()
                }

            }

            R.id.btnCorrection->{
                checkAttendance()
                if (saveAttendanceMark) {
                    Config.disableClick(v)
                    correctionBottomSheet()
                }
            }

        }
    }




    private fun rejectBottomSheet() {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.authorization_reject, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            txtRjtSubmit = view.findViewById<TextView>(R.id.txtRjtSubmit)
            txtRjtCancel = view.findViewById<TextView>(R.id.txtRjtCancel)

            tie_Reason = view.findViewById<TextInputEditText>(R.id.tie_Reason)
            tie_Remarks = view.findViewById<TextInputEditText>(R.id.tie_Remarks)

            til_Reason = view.findViewById<TextInputLayout>(R.id.til_Reason)
            til_Remarks = view.findViewById<TextInputLayout>(R.id.til_Remarks)



            til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_Remarks!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)

            tie_Reason!!.addTextChangedListener(watcher);
            tie_Remarks!!.addTextChangedListener(watcher);

            ID_Reason = ""
            tie_Reason!!.setText("")
            tie_Remarks!!.setText("")

            tie_Reason!!.setOnClickListener {
                Config.disableClick(it)
                reasonCount = 0
                getReason()
            }

            txtRjtCancel!!.setOnClickListener {
                dialog1.dismiss()
                ID_Reason = ""
                tie_Reason!!.setText("")
                tie_Remarks!!.setText("")
            }

            txtRjtSubmit!!.setOnClickListener {

                Config.disableClick(it)
                if (ID_Reason.equals("")){
                    //Config.snackBarWarning(context,it,"Select Reason")
                    Log.e(TAG,"Select Reason  696   "+ID_Reason)
                    til_Reason!!.setError("Please select Reason")
                    til_Reason!!.setErrorIconDrawable(null)
                }
                else if (tie_Remarks!!.text.toString().equals("")){
                    Config.snackBarWarning(context,it,"Please enter remarks")
                    Log.e(TAG,"Please enter remarks  696   "+ID_Reason)
                    til_Remarks!!.setError("Please enter remarks")
                    til_Remarks!!.setErrorIconDrawable(null)

                    til_Reason!!.isErrorEnabled = false
                    til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                }
                else{
                    Log.e(TAG,"ID_Reason  696   "+ID_Reason)
                    til_Remarks!!.isErrorEnabled = false
                    til_Remarks!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    Log.e(TAG,"ID_Reason  696   "+ID_Reason)

                    rejectCount = 0
                    authorizationReject(tie_Remarks!!.text.toString())

                    dialog1.dismiss()
                    ID_Reason = ""
                    tie_Reason!!.setText("")
                    tie_Remarks!!.setText("")


                }

            }



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }

    private fun correctionBottomSheet() {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.corection_bottomsheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCancelable(true)
            dialog1!!.setCanceledOnTouchOutside(false)
            dialog1!!.getBehavior().setDraggable(false);

            var tv_succesmsg = view.findViewById<TextView>(R.id.tv_succesmsg)
            var tv_gotit = view.findViewById<TextView>(R.id.tv_gotit)
            tv_succesmsg.text = "Confirm correction request ?"
            tv_gotit!!.setOnClickListener {
                dialog1.dismiss()
                correctionCount = 0
                authorizationCorrection()
            }

            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }

    private fun authorizationCorrection() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                authCorrectionViewModel.getAuthCorrection(this,FK_AuthID)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (correctionCount == 0) {
                                    correctionCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "Reject msg   93222220   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {


                                        successBottomSheet(jObject)

//                                        val jobjt = jObject.getJSONObject("ReasonDetails")
//                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailsList")
//
//                                        Log.e(TAG, "reasonArrayList   999101   " + reasonArrayList)
//                                        if (reasonArrayList.length()> 0){
////                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
////                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
////                                            recyAprrove!!.adapter = adapter
////                                            adapter.setClickListener(this@ApprovalListActivity)
//
//                                            reasonpopup(reasonArrayList)
//
//
//                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun authorizationReject(strRemark: String) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                authRejectViewModel.saveAuthReject(this,FK_AuthID,ID_Reason,strRemark!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (rejectCount == 0) {
                                    rejectCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "Reject msg   93222220   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {


                                        successBottomSheet(jObject)

//                                        val jobjt = jObject.getJSONObject("ReasonDetails")
//                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailsList")
//
//                                        Log.e(TAG, "reasonArrayList   999101   " + reasonArrayList)
//                                        if (reasonArrayList.length()> 0){
////                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
////                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
////                                            recyAprrove!!.adapter = adapter
////                                            adapter.setClickListener(this@ApprovalListActivity)
//
//                                            reasonpopup(reasonArrayList)
//
//
//                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun successBottomSheet(jObject : JSONObject) {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.succes_bottomsheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCancelable(false)
            dialog1!!.setCanceledOnTouchOutside(false)
            dialog1!!.getBehavior().setDraggable(false);

            var tv_succesmsg = view.findViewById<TextView>(R.id.tv_succesmsg)
            var tv_gotit = view.findViewById<TextView>(R.id.tv_gotit)

            tv_succesmsg!!.setText(jObject.getString("EXMessage"))
            tv_gotit!!.setOnClickListener {
                dialog1.dismiss()
                finish()
            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }

//    private fun successPopup(jObject: String) {
//
//        try {
//            val suceessDialog = Dialog(this)
//            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            suceessDialog!!.setCancelable(false)
//            suceessDialog!!.setContentView(R.layout.success_popup1)
//            suceessDialog!!.window!!.attributes.gravity =
//                Gravity.CENTER_VERTICAL;
//
////            val tv_succesmsg =
////                suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
////            val tv_leadid =
////                suceessDialog!!.findViewById(R.id.tv_leadid) as TextView
//            val tv_succesok =
//                suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
////            //LeadNumber
////          //  tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
//
//
//            tv_succesok!!.setOnClickListener {
//                suceessDialog!!.dismiss()
//                finish()
//            }
//
//            suceessDialog!!.show()
//            suceessDialog!!.getWindow()!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//        }
//        catch (e : Exception){
//
//        }
//    }

    private fun approveConfirmation() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                authApproveViewModel.saveAuthApprove(this,FK_AuthID)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (approveCount == 0) {
                                    approveCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "Reject msg   93222220   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        successBottomSheet(jObject)
//                                        val jobjt = jObject.getJSONObject("ReasonDetails")
//                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailsList")
//
//                                        Log.e(TAG, "reasonArrayList   999101   " + reasonArrayList)
//                                        if (reasonArrayList.length()> 0){
////                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
////                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
////                                            recyAprrove!!.adapter = adapter
////                                            adapter.setClickListener(this@ApprovalListActivity)
//
//                                            reasonpopup(reasonArrayList)
//
//
//                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {
                editable === tie_Reason!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Reason!!.text.toString().equals("")){
                        til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Reason!!.isErrorEnabled = false
                        til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

                editable === tie_Remarks!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Remarks!!.text.toString().equals("")){
                        til_Remarks!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Remarks!!.isErrorEnabled = false
                        til_Remarks!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }

            }



        }
    }

    private fun getReason() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reasonViewModel.getReason(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (reasonCount == 0) {
                                    reasonCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ReasonDetails")
                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailsList")

                                        Log.e(TAG, "reasonArrayList   999101   " + reasonArrayList)
                                        if (reasonArrayList.length()> 0){
//                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
//                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
//                                            recyAprrove!!.adapter = adapter
//                                            adapter.setClickListener(this@ApprovalListActivity)

                                            reasonpopup(reasonArrayList)


                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListDetailActivity,
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }


    }

    private fun reasonpopup(reasonArrayList: JSONArray) {
        try {

            dialogReason = Dialog(this)
            dialogReason!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReason!! .setContentView(R.layout.popup_reason)
            dialogReason!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val window: Window? = dialogReason!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            recyReason = dialogReason!!.findViewById(R.id.recyReason) as RecyclerView


            reasonSort = JSONArray()
            for (k in 0 until reasonArrayList.length()) {
                val jsonObject = reasonArrayList.getJSONObject(k)
                reasonSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ApprovalListDetailActivity, 1)
            recyReason!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ReasonAuthAdapter(this@ApprovalListDetailActivity, reasonSort)
            recyReason!!.adapter = adapter
            adapter.setClickListener(this@ApprovalListDetailActivity)

            dialogReason!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("reasonClick")){
            dialogReason!!.dismiss()
            val jsonObject = reasonSort.getJSONObject(position)
            tie_Reason!!.setText(jsonObject.getString("ResnName"))
            ID_Reason = jsonObject.getString("ID_Reason")
        }
    }

}