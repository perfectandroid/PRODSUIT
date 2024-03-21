package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ReasonAuthAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalListViewModel
import com.perfect.prodsuit.Viewmodel.ReasonViewModel
import org.json.JSONArray
import org.json.JSONObject

class ApprovalListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {


    val TAG : String = "ApprovalListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    lateinit var approvalListViewModel: ApprovalListViewModel
    lateinit var approvalArrayList: JSONArray
    internal var recyAprrove: RecyclerView? = null
    var approveCount = 0

    lateinit var reasonViewModel: ReasonViewModel
    lateinit var reasonArrayList: JSONArray
    lateinit var reasonSort: JSONArray
    internal var recyReason: RecyclerView? = null
    var dialogReason: Dialog? = null
    var Module = ""

    private var tv_header: TextView? = null
    private var txtRjtCancel: TextView? = null
    private var txtRjtSubmit: TextView? = null

    private var tie_Reason: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null

    private var ll_main: LinearLayout? = null

    var jsonObj: JSONObject? = null
    var reasonCount = 0
    private var tv_listCount: TextView?          = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approval_list)

        context = this@ApprovalListActivity

        approvalListViewModel = ViewModelProvider(this).get(ApprovalListViewModel::class.java)
        reasonViewModel = ViewModelProvider(this).get(ReasonViewModel::class.java)

        setRegViews()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        Module = jsonObj!!.getString("Module")
        tv_header!!.setText(""+jsonObj!!.getString("Module_Name"))

        approveCount = 0
        getAppoval(Module)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun setRegViews() {
        tv_listCount = findViewById(R.id.tv_listCount)
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById(R.id.tv_header)
        recyAprrove = findViewById(R.id.recyAprrove)
        ll_main = findViewById(R.id.ll_main)
    }

    private fun getAppoval(Module :  String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                ll_main!!.removeAllViews()
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                approvalListViewModel.getApprovalList(this,Module!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (approveCount == 0) {
                                    approveCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        ll_main!!.removeAllViews()
//                                        val jobjt = jObject.getJSONObject("ApprovalDetails")
//                                        approvalArrayList = jobjt.getJSONArray("ApprovalDetailList")
                                        val jobjt = jObject.getJSONObject("AuthorizationList")
                                        approvalArrayList = jobjt.getJSONArray("ListData")

                                        Log.e(TAG, "approvalArrayList   999101   " + approvalArrayList)
                                        if (approvalArrayList.length()> 0){

                                            tv_listCount!!.setText(""+approvalArrayList.length())
//                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
//                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
//                                            recyAprrove!!.adapter = adapter
//                                            adapter.setClickListener(this@ApprovalListActivity)
                                            for (i in 0 until approvalArrayList.length()) {
                                                var jsonObject = approvalArrayList.getJSONObject(i)

                                                val customFont: Typeface = context.resources.getFont(R.font.myfont)
                                                val length: Int = jsonObject!!.length()
                                                val keys = jsonObject!!.keys()
                                                val dynamicLinearLayoutmain = LinearLayout(context)
                                                dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT)
                                                dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL
                                                dynamicLinearLayoutmain.setBackgroundColor(context.resources.getColor(R.color.black));

                                                while (keys.hasNext()) {
                                                    val key = keys.next()

                                                    if (!key.equals("SlNo") && !key.equals("ID_FIELD")  && !key.equals("drank") && !key.equals("TotalCount") && !key.equals("ID_AuthorizationData") && !key.equals("Module") && !key.equals("SkipPreviousLevel")){
                                                        Log.e(TAG,"JSON_KEY  4566   :  "+ key +"  :  "+jsonObject!!.getString(key)) // Output: key1, key2, key3

//                    val img: Drawable = context.getResources().getDrawable(R.drawable.vtr_common)

                                                        val drawable1 = ContextCompat.getDrawable(context, R.drawable.vtr_common)
                                                        val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)


                                                        val dynamicLinearLayout = LinearLayout(context)
                                                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        dynamicLinearLayout.orientation = LinearLayout.HORIZONTAL




                                                        val textView1 = TextView(context)
                                                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        layoutParams1.weight = 1.9f
                                                        textView1!!.layoutParams = layoutParams1
                                                        //  textView1.text = key
                                                        val cleanText: String = Config.addSpaceBetweenLowerAndUpper(key)
                                                        Log.e(TAG,"722224   "+key+"  :   cleanText   "+cleanText+"  :  "+Config.addSpaceBetweenLowerAndUpper(key))

                                                        textView1.text = cleanText
                                                        //    textView1.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
                                                        textView1.compoundDrawablePadding = drawablePadding
                                                        textView1!!.setTextSize(15F)
                                                        textView1.setCompoundDrawablePadding(2)
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
                                                        textView2.text = jsonObject!!.getString(key)
                                                        val layoutParams2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        layoutParams2.weight = 1.0f
                                                        textView2!!.layoutParams = layoutParams2
                                                        //  textView2.text = "ghfdghjbvhjbfvghjdfmhjbfbnbjknjkkjvfbdnkngkjdfjglkdftytrytrytrytryryrbfvhfghfghfghfghfhbfhfhhfghkjghdfjhdfhh"
                                                        textView2!!.setTextSize(14F)
                                                        textView2!!.setTypeface(customFont);
                                                        textView2!!.setTextColor(context.resources.getColor(R.color.greydark))
                                                        textView2!!.setPadding(15, -0, 0, 0)

                                                        dynamicLinearLayoutmain.id = i


//                                                        dynamicLinearLayoutmain.setOnClickListener {
//                                                            Log.e(TAG,"dynamicLinearLayoutmain 205555   "+i)
//                                                            val jsonObject = approvalArrayList.getJSONObject(i)
//                                                            val i = Intent(this@ApprovalListActivity, ApprovalListDetailActivity::class.java)
//                                                            i.putExtra("jsonObject",jsonObject.toString())
//                                                            i.putExtra("Module",Module)
//                                                            startActivity(i)
//                                                        }

                                                        val layoutParamsSub = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        layoutParamsSub.setMargins(15, -2, 15, -2)

                                                        dynamicLinearLayout.addView(textView1);
                                                        dynamicLinearLayout.addView(textView0);
                                                        dynamicLinearLayout.addView(textView2);
                                                        dynamicLinearLayout.layoutParams = layoutParamsSub

                                                        val layoutParamMain = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                        layoutParamMain.setMargins(5, 5, 5, 0)
                                                        dynamicLinearLayoutmain.setBackgroundResource(R.drawable.shape_shadownew)
                                                        dynamicLinearLayoutmain.layoutParams = layoutParamMain

                                                        dynamicLinearLayoutmain.addView(dynamicLinearLayout);

                                                    }

                                                }
                                                val textView3 = TextView(context)
                                                //  textView1.text = key
                                                textView3.text = "More Details"
//                                                textView3.compoundDrawablePadding = drawablePadding
                                                textView3!!.setTextSize(16F)
                                                textView3!!.setTypeface(textView3!!.getTypeface(), Typeface.NORMAL);
//                                                textView3!!.setTextColor(Color.parseColor("#FFFFFF"));
                                                textView3.setCompoundDrawablePadding(2)
//                                                textView3!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_next, 0);
                                                // textView3!!.setTypeface(customFont);
                                                textView3!!.setTextColor(context.resources.getColor(R.color.more_textcolor))
                                                textView3!!.setPadding(30, 10, 30, 10)
                                                textView3!!.textAlignment = View.TEXT_ALIGNMENT_CENTER

                                                val layoutParamMain = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                layoutParamMain.setMargins(20, 20, 20, 20)
                                                layoutParamMain.gravity = Gravity.CENTER
                                                //   textView3.setBackgroundResource(R.drawable.shape_border_round_trans)
                                                textView3.setBackgroundResource(R.drawable.shape_bg_view1)
                                                textView3.layoutParams = layoutParamMain

                                                textView3!!.setOnClickListener {
                                                    Log.e(TAG,"dynamicLinearLayoutmain 205555   "+i)
                                                    val jsonObject = approvalArrayList.getJSONObject(i)
                                                    val i = Intent(this@ApprovalListActivity, ApprovalListDetailActivity::class.java)
                                                    i.putExtra("jsonObject",jsonObject.toString())
                                                    i.putExtra("Module",Module)
                                                    startActivity(i)
                                                }


                                                ll_main!!.addView(dynamicLinearLayoutmain);

                                                if (Module.equals("AWAIT")){
                                                    if (jsonObject.getString("SkipPreviousLevel").equals("true")){
                                                        dynamicLinearLayoutmain.addView(textView3);
                                                    }
                                                }else{
                                                    dynamicLinearLayoutmain.addView(textView3);
                                                }


                                            }

                                        }


                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListActivity,
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
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


                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListActivity,
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

            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
            recyReason!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ReasonAuthAdapter(this@ApprovalListActivity, reasonSort)
            recyReason!!.adapter = adapter
            adapter.setClickListener(this@ApprovalListActivity)

            dialogReason!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("approveListClick")){
            Log.e(TAG,"Position   "+position)
            val jsonObject = approvalArrayList.getJSONObject(position)
            val i = Intent(this@ApprovalListActivity, ApprovalListDetailActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            i.putExtra("Module",Module)
            startActivity(i)
        }
        if (data.equals("approveClick")){
            val jsonObject = approvalArrayList.getJSONObject(position)
            Log.e(TAG,"15777  approve "+jsonObject.getString("Transaction No"))

        }
        if (data.equals("rejectClick")){
            val jsonObject = approvalArrayList.getJSONObject(position)
            Log.e(TAG,"15777  reject "+jsonObject.getString("Transaction No"))

            rejectBottomSheet()
        }

        if (data.equals("reasonClick")){
            dialogReason!!.dismiss()
            val jsonObject = reasonSort.getJSONObject(position)
            tie_Reason!!.setText(jsonObject.getString("ResnName"))
        }

    }

    override fun onRestart() {
        super.onRestart()
        approveCount = 0
        getAppoval(Module)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}