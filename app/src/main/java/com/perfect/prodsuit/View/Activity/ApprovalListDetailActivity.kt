package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ApprovalListDetailAdapter
import com.perfect.prodsuit.View.Adapter.ReasonAuthAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalDetailViewModel
import com.perfect.prodsuit.Viewmodel.ReasonViewModel
import com.perfect.prodsuit.Viewmodel.StockRTListViewModel
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
    internal var btnApprove: Button? = null



    internal var recyAprroveDetails: RecyclerView? = null

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

    private var txtRjtCancel: TextView? = null
    private var txtRjtSubmit: TextView? = null

    private var tie_Reason: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null

    var modeKey1: String? = "0"
    var modeKey2: String? = "0"
    var modeKey3: String? = "0"
    var modeKey4: String? = "0"
    var modeKey5: String? = "0"

    lateinit var reasonViewModel: ReasonViewModel
    lateinit var reasonArrayList: JSONArray
    lateinit var reasonSort: JSONArray
    internal var recyReason: RecyclerView? = null
    var dialogReason: Dialog? = null
    var reasonCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approval_list_detail)
        context = this@ApprovalListDetailActivity

        stockRTListViewModel = ViewModelProvider(this).get(StockRTListViewModel::class.java)
        approvalDetailViewModel = ViewModelProvider(this).get(ApprovalDetailViewModel::class.java)
        reasonViewModel = ViewModelProvider(this).get(ReasonViewModel::class.java)

        setRegViews()
//        generateSS()

        stockCount = 0
        getStckList()

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
        btnApprove = findViewById(R.id.btnApprove)


        recyAprroveDetails = findViewById(R.id.recyAprroveDetails)

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
        btnApprove!!.setOnClickListener(this)


    }

    private fun getStckList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                approvalDetailViewModel.getApprovalDetail(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (stockCount == 0) {
                                    stockCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthRTDetails")
                                        key1ArrayList = jobjt.getJSONArray("Key1")
                                        key2ArrayList = jobjt.getJSONArray("Key2")
                                        key4ArrayList = jobjt.getJSONArray("Key4")
                                        key5ArrayList = jobjt.getJSONArray("Key5")
                                        Log.e(TAG, "key1ArrayList   999101   " + key1ArrayList)



                                        if (key1ArrayList.length() > 0) {
                                         //   card_Key1!!.visibility = View.VISIBLE
                                            Log.e(TAG, "msg   9991023   " + key1ArrayList)
                                                val jsonObject = key1ArrayList.getJSONObject(0)
                                                val length: Int = jsonObject.length()
                                            Log.e(TAG, "length   9991023   " + length)
                                                val keys = jsonObject.keys()
                                                var indexKey1 = 0
                                                while (keys.hasNext()) {
                                                    val key = keys.next()
                                                    if (indexKey1 == 0) {
                                                        indexKey1++
                                                        Log.e("JSON_KEY  9444441   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                        tv_Key1Click!!.setText(jsonObject.getString(key))
                                                    }else{
                                                        Log.e("JSON_KEY  9444442    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                        indexKey1++
                                                        val dynamicLinearLayout = LinearLayout(this)
                                                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        dynamicLinearLayout.orientation = LinearLayout.VERTICAL

                                                        val customFont: Typeface = resources.getFont(R.font.myfont)

                                                        val textView1 = TextView(this)
                                                        textView1.text = key
                                                        textView1!!.setTextSize(15F)
                                                        textView1!!.setTypeface(customFont);
                                                        textView1!!.setTextColor(resources.getColor(R.color.black))


                                                        val textView2 = TextView(this)
                                                        textView2.text = jsonObject.getString(key)
                                                        textView2!!.setTextSize(14F)
                                                        textView2!!.setTypeface(customFont);
                                                        textView2!!.setTextColor(resources.getColor(R.color.greydark))
                                                        textView2!!.setPadding(0, -10, 0, 0)

                                                        dynamicLinearLayout.addView(textView1);
                                                        dynamicLinearLayout.addView(textView2);

                                                        ll_key1!!.addView(dynamicLinearLayout);


                                                    }

                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                }
                                        }
                                        else{
                                            card_Key1!!.visibility = View.GONE
                                        }

                                        //////////

                                        if (key2ArrayList.length() > 0) {
                                           // card_Key2!!.visibility = View.VISIBLE
                                            Log.e(TAG, "msg   9991023   " + key2ArrayList)
                                            val jsonObject = key2ArrayList.getJSONObject(0)
                                            val length: Int = jsonObject.length()
                                            Log.e(TAG, "length   9991023   " + length)
                                            val keys = jsonObject.keys()
                                            var indexKey1 = 0
                                            while (keys.hasNext()) {
                                                val key = keys.next()
                                                if (indexKey1 == 0) {
                                                    indexKey1++
                                                    Log.e("JSON_KEY  9444441   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    tv_Key2Click!!.setText(jsonObject.getString(key))
                                                }else{
                                                    Log.e("JSON_KEY  9444442    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    indexKey1++
                                                    val dynamicLinearLayout = LinearLayout(this)
                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL

                                                    val customFont: Typeface = resources.getFont(R.font.myfont)

                                                    val textView1 = TextView(this)
                                                    textView1.text = key
                                                    textView1!!.setTextSize(15F)
                                                    textView1!!.setTypeface(customFont);
                                                    textView1!!.setTextColor(resources.getColor(R.color.black))


                                                    val textView2 = TextView(this)
                                                    textView2.text = jsonObject.getString(key)
                                                    textView2!!.setTextSize(14F)
                                                    textView2!!.setTypeface(customFont);
                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
                                                    textView2!!.setPadding(0, -10, 0, 0)

                                                    dynamicLinearLayout.addView(textView1);
                                                    dynamicLinearLayout.addView(textView2);

                                                    ll_key2!!.addView(dynamicLinearLayout);


                                                }

                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                            }
                                        }
                                        else{
                                            card_Key2!!.visibility = View.GONE
                                        }

                                        val Key3jobjt = jobjt.getJSONObject("Key3")
                                        Log.e(TAG, "jobjtKey3   9991013   " + Key3jobjt)
                                        Log.e(TAG, "jobjtKey3   9991013   " + Key3jobjt.getString("SubTitle"))
                                        tv_Key3Click!!.setText(Key3jobjt.getString("SubTitle").toString())
                                        detailsArrayList = Key3jobjt.getJSONArray("Details")

                                        if (detailsArrayList.length() > 0){
                                          //  card_Key3!!.visibility = View.VISIBLE
                                            Log.e(TAG, "detailsArrayList   9991014   " + detailsArrayList)

                                            val lLayout = GridLayoutManager(this@ApprovalListDetailActivity, 1)
                                            recyAprroveDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ApprovalListDetailAdapter(this@ApprovalListDetailActivity, detailsArrayList)
                                            recyAprroveDetails!!.adapter = adapter
                                            // adapter.setClickListener(this@EmiActivity)
                                        }else{
                                            card_Key3!!.visibility = View.GONE
                                        }


                                        if (key4ArrayList.length() > 0) {
                                            //   card_Key1!!.visibility = View.VISIBLE
                                            Log.e(TAG, "msg   3044441   " + key4ArrayList)
                                            val jsonObject = key4ArrayList.getJSONObject(0)
                                            val length: Int = jsonObject.length()
                                            Log.e(TAG, "length   3044442   " + length)
                                            val keys = jsonObject.keys()
                                            var indexKey1 = 0
                                            while (keys.hasNext()) {
                                                val key = keys.next()
                                                if (indexKey1 == 0) {
                                                    indexKey1++
                                                    Log.e("JSON_KEY  3044443   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    tv_Key4Click!!.setText(jsonObject.getString(key))
                                                }else{
                                                    Log.e("JSON_KEY  3044444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    indexKey1++
                                                    val dynamicLinearLayout = LinearLayout(this)
                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL

                                                    val customFont: Typeface = resources.getFont(R.font.myfont)

                                                    val textView1 = TextView(this)
                                                    textView1.text = key
                                                    textView1!!.setTextSize(15F)
                                                    textView1!!.setTypeface(customFont);
                                                    textView1!!.setTextColor(resources.getColor(R.color.black))


                                                    val textView2 = TextView(this)
                                                    textView2.text = jsonObject.getString(key)
                                                    textView2!!.setTextSize(14F)
                                                    textView2!!.setTypeface(customFont);
                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
                                                    textView2!!.setPadding(0, -10, 0, 0)

                                                    dynamicLinearLayout.addView(textView1);
                                                    dynamicLinearLayout.addView(textView2);

                                                    ll_key4!!.addView(dynamicLinearLayout);


                                                }

                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                            }
                                        }
                                        else{
                                            card_Key4!!.visibility = View.GONE
                                        }

                                        if (key5ArrayList.length() > 0) {
                                            //   card_Key1!!.visibility = View.VISIBLE
                                            Log.e(TAG, "msg   3044441   " + key5ArrayList)
                                            val jsonObject = key5ArrayList.getJSONObject(0)
                                            val length: Int = jsonObject.length()
                                            Log.e(TAG, "length   3044442   " + length)
                                            val keys = jsonObject.keys()
                                            var indexKey1 = 0
                                            while (keys.hasNext()) {
                                                val key = keys.next()
                                                if (indexKey1 == 0) {
                                                    indexKey1++
                                                    Log.e("JSON_KEY  3044443   :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    tv_Key5Click!!.setText(jsonObject.getString(key))
                                                }else{
                                                    Log.e("JSON_KEY  3044444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                                    indexKey1++
                                                    val dynamicLinearLayout = LinearLayout(this)
                                                    dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                                    dynamicLinearLayout.orientation = LinearLayout.VERTICAL

                                                    val customFont: Typeface = resources.getFont(R.font.myfont)

                                                    val textView1 = TextView(this)
                                                    textView1.text = key
                                                    textView1!!.setTextSize(15F)
                                                    textView1!!.setTypeface(customFont);
                                                    textView1!!.setTextColor(resources.getColor(R.color.black))


                                                    val textView2 = TextView(this)
                                                    textView2.text = jsonObject.getString(key)
                                                    textView2!!.setTextSize(14F)
                                                    textView2!!.setTypeface(customFont);
                                                    textView2!!.setTextColor(resources.getColor(R.color.greydark))
                                                    textView2!!.setPadding(0, -10, 0, 0)

                                                    dynamicLinearLayout.addView(textView1);
                                                    dynamicLinearLayout.addView(textView2);

                                                    ll_key5!!.addView(dynamicLinearLayout);


                                                }

                                                //    Log.e("JSON_KEY  944444    :  ", key +"  :  "+jsonObject.getString(key)) // Output: key1, key2, key3
                                            }
                                        }
                                        else{
                                            card_Key4!!.visibility = View.GONE
                                        }



                                        ///////////

                                        if (key1ArrayList.length() > 0){
                                            modeKey1 = "1"
                                            modeKey2 = "0"
                                            modeKey3 = "0"
                                            modeKey4 = "0"
                                            modeKey5 = "0"
                                        }
                                        else if (key2ArrayList.length() > 0){
                                            modeKey1 = "0"
                                            modeKey2 = "1"
                                            modeKey3 = "0"
                                            modeKey4 = "0"
                                            modeKey5 = "0"
                                        }
                                        else if (detailsArrayList.length() > 0){
                                            modeKey1 = "0"
                                            modeKey2 = "0"
                                            modeKey3 = "1"
                                            modeKey4 = "0"
                                            modeKey5 = "0"
                                        }
                                        else if (key4ArrayList.length() > 0){
                                            modeKey1 = "0"
                                            modeKey2 = "0"
                                            modeKey3 = "0"
                                            modeKey4 = "1"
                                            modeKey5 = "0"
                                        }

                                        else if (key5ArrayList.length() > 0){
                                            modeKey1 = "0"
                                            modeKey2 = "0"
                                            modeKey3 = "0"
                                            modeKey4 = "0"
                                            modeKey5 = "1"
                                        }

                                        hideShows()





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
                Config.disableClick(v)
                rejectBottomSheet()
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

            tie_Reason!!.setOnClickListener {
                Config.disableClick(it)
                reasonCount = 0
                getReason()
            }

            txtRjtCancel!!.setOnClickListener {
                dialog1.dismiss()
            }

            txtRjtSubmit!!.setOnClickListener {

            }



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
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
                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailList")

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
            tie_Reason!!.setText(jsonObject.getString("Reason"))
        }
    }

}