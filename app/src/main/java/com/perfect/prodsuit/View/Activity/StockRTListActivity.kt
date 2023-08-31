package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ReasonAuthAdapter
import com.perfect.prodsuit.View.Adapter.StockRTListAdapter
import com.perfect.prodsuit.Viewmodel.DeleteStockViewModel
import com.perfect.prodsuit.Viewmodel.ReasonViewModel
import com.perfect.prodsuit.Viewmodel.StockRTListViewModel
import org.json.JSONArray
import org.json.JSONObject

class StockRTListActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG : String = "StockRTListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var imback : ImageView? = null
    var imgClosing : ImageView? = null
    var tv_header : TextView? = null
    var etsearch : EditText? = null

    lateinit var stockRTListViewModel: StockRTListViewModel
    lateinit var stockRTArrayList: JSONArray
    lateinit var stockRTSort: JSONArray
    private var recycStockRT: RecyclerView? = null
    var stockCount = 0

    lateinit var reasonViewModel: ReasonViewModel
    lateinit var reasonArrayList: JSONArray
    lateinit var reasonSort: JSONArray
    internal var recyReason: RecyclerView? = null
    var dialogReason: Dialog? = null
    var reasonCount = 0

    private var txtRjtCancel: TextView? = null
    private var txtRjtSubmit: TextView? = null

    private var tie_Reason: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null

    private var til_Reason: TextInputLayout? = null
    private var til_Remarks: TextInputLayout? = null

    var ID_Reason= ""
    lateinit var deleteStockViewModel: DeleteStockViewModel
    var deleteCount = 0


    var TransMode : String? = ""
    var headerTitle : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_stock_rtlist)
        context = this@StockRTListActivity

        stockRTListViewModel = ViewModelProvider(this).get(StockRTListViewModel::class.java)
        reasonViewModel = ViewModelProvider(this).get(ReasonViewModel::class.java)
        deleteStockViewModel = ViewModelProvider(this).get(DeleteStockViewModel::class.java)

        setRegViews()

        TransMode  = intent.getStringExtra("TransMode")
        headerTitle  = intent.getStringExtra("headerTitle")
        getDetail()

        stockCount = 0
        getStckList()

    }



    private fun setRegViews() {

        imback = findViewById(R.id.imback)
        imgClosing = findViewById(R.id.imgClosing)
        tv_header = findViewById(R.id.tv_header)
        etsearch = findViewById(R.id.etsearch)

        recycStockRT = findViewById(R.id.recycStockRT)

        imback!!.setOnClickListener(this)
        imgClosing!!.setOnClickListener(this)

        tv_header!!.setText(headerTitle)

    }

    private fun getDetail() {
        try {
            val intent = intent
            var headerTitle = intent!!.getStringExtra("headerTitle")
            tv_header!!.setText(headerTitle)

        }catch (e: Exception){

        }

    }

    private fun getStckList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                var Detailed = "0"
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                stockRTListViewModel.getStockRTList(this, TransMode!!,Detailed)!!.observe(
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

                                        val jobjt = jObject.getJSONObject("StockRequestList")
                                        stockRTArrayList = jobjt.getJSONArray("StockRequestListData")
                                        if (stockRTArrayList.length() > 0) {
                                            Log.e(TAG, "msg   999102   " + stockRTArrayList)
                                            stockRTList(stockRTArrayList)
//                                            val lLayout = GridLayoutManager(this@StockRTListActivity)
//                                            recycStockRT!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ProductSimilarAdapter(this@StockRTListActivity, stockRTArrayList)
//                                            recycStockRT!!.adapter = adapter

                                            //    adapter.setClickListener(this@ProductEnquiryDetailActivity)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockRTListActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            finish()
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

    private fun stockRTList(stockRTArrayList: JSONArray) {
        try {
            stockRTSort = JSONArray()
            for (k in 0 until stockRTArrayList.length()) {
                val jsonObject = stockRTArrayList.getJSONObject(k)
                stockRTSort.put(jsonObject)
            }

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stockRTSort = JSONArray()

                    for (k in 0 until stockRTArrayList.length()) {
                        val jsonObject = stockRTArrayList.getJSONObject(k)
                      //  if (textlength <= jsonObject.getString("EmployeeName").length) {
                            if (jsonObject.getString("EmployeeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) || jsonObject.getString("BranchName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                                || jsonObject.getString("DepartmentNameTo")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) || jsonObject.getString("DepartmentName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                                stockRTSort.put(jsonObject)
                            }

                        //}
                    }

                    val adapter = StockRTListAdapter(this@StockRTListActivity, stockRTSort)
                    recycStockRT!!.adapter = adapter
                    adapter.setClickListener(this@StockRTListActivity)
                }
            })

            val lLayout = GridLayoutManager(this@StockRTListActivity, 1)
            recycStockRT!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = StockRTListAdapter(this@StockRTListActivity, stockRTSort)
            recycStockRT!!.adapter = adapter
            adapter.setClickListener(this@StockRTListActivity)

        }catch (e: Exception){

        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgClosing->{
                finish()
            }

        }
    }



    private fun deleteBottomSheet(position : Int,FK_StockTransfer : String) {
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

            til_Remarks!!.visibility =View.GONE


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
//                else if (tie_Remarks!!.text.toString().equals("")){
//                    Config.snackBarWarning(context,it,"Please enter remarks")
//                    Log.e(TAG,"Please enter remarks  696   "+ID_Reason)
//                    til_Remarks!!.setError("Please enter remarks")
//                    til_Remarks!!.setErrorIconDrawable(null)
//
//                    til_Reason!!.isErrorEnabled = false
//
//                    til_Reason!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
//                }
                else{
                    Log.e(TAG,"ID_Reason  696   "+ID_Reason)
                    til_Remarks!!.isErrorEnabled = false
                    til_Remarks!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    Log.e(TAG,"ID_Reason  696   "+ID_Reason)

//                    stockRTSort.remove(position);
//                    recycStockRT!!.adapter!!.notifyItemRemoved(position)



                    deleteCount = 0
                    deleteStockList(position,FK_StockTransfer)

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
                                            this@StockRTListActivity,
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

            val lLayout = GridLayoutManager(this@StockRTListActivity, 1)
            recyReason!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ReasonAuthAdapter(this@StockRTListActivity, reasonSort)
            recyReason!!.adapter = adapter
            adapter.setClickListener(this@StockRTListActivity)

            dialogReason!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun deleteStockList(position: Int,FK_StockTransfer : String) {
        var TransMode = "INTR"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                deleteStockViewModel.deleteStock(this,FK_StockTransfer,TransMode,ID_Reason!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (deleteCount == 0) {
                                    deleteCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "Reject msg   93222220   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                       // successBottomSheet(jObject)
                                        stockRTSort.remove(position);
                                        recycStockRT!!.adapter!!.notifyItemRemoved(position)

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@StockRTListActivity,
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("viewClicks")) {
            val jsonObject = stockRTSort.getJSONObject(position)
            val intent = Intent()
            Log.e(TAG,"207110    "+jsonObject)
            intent.putExtra("jsonObject", jsonObject.toString())
            setResult(Config.CODE_STOCK_LIST!!, intent)
            finish()
        }

        if (data.equals("deleteClicks")) {
            val jsonObject = stockRTSort.getJSONObject(position)
            var FK_StockTransfer = jsonObject.getString("StockTransferID")
            deleteBottomSheet(position,FK_StockTransfer)

        }

        if (data.equals("reasonClick")){
            dialogReason!!.dismiss()
            val jsonObject = reasonSort.getJSONObject(position)
            tie_Reason!!.setText(jsonObject.getString("ResnName"))
            ID_Reason = jsonObject.getString("ID_Reason")
        }

//        val i = Intent(this@ApproveActivity, ApprovalListActivity::class.java)
//        i.putExtra("jsonObject",jsonObject.toString())
//        startActivity(i)


    }

}