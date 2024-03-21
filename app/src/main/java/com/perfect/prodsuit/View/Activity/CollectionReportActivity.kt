package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ReportNameAdapter
import com.perfect.prodsuit.Viewmodel.ReportNameViewModel
import org.json.JSONArray
import org.json.JSONObject

class   CollectionReportActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener,
    ItemClickListenerData {

    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    var tie_ReportName: TextInputEditText? = null
    lateinit var reportNameViewModel: ReportNameViewModel
    lateinit var reportNameArrayList: JSONArray
    private var dialogReportName: Dialog? = null
    var recyReportName: RecyclerView? = null
    lateinit var reportNamesort: JSONArray
    private var ReportMode: String = ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_collection_report)
        context = this@CollectionReportActivity
        reportNameViewModel = ViewModelProvider(this).get(ReportNameViewModel::class.java)
        setRegViews()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        tie_ReportName = findViewById(R.id.tie_ReportName)
        tie_ReportName!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tie_ReportName -> {

                getReportName()
            }
        }

    }

    private fun getReportName() {
        var reportName = 0
        var SubMode = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reportNameViewModel.getReportName(this,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                           // Log.e(TAG, "msg   1062   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("ReportNameDetails")
                                reportNameArrayList = jobjt.getJSONArray("ReportNameDetailsList")
                                if (reportNameArrayList.length() > 0) {
                                    if (reportName == 0) {
                                        reportName++
                                        reportNamePopup(reportNameArrayList)
                                    }

                                }
                            }
                            else if (jObject.getString("StatusCode") == "105"){
                                Config.logoutTokenMismatch(context,jObject)
                            }
                            else {
                                val builder = AlertDialog.Builder(
                                    this@CollectionReportActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun reportNamePopup(reportNameArrayList: JSONArray) {
        try {

            dialogReportName = Dialog(this)
            dialogReportName!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReportName!!.setContentView(R.layout.report_name_popup)
            dialogReportName!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyReportName = dialogReportName!!.findViewById(R.id.recyReportName) as RecyclerView
            val etsearch = dialogReportName!!.findViewById(R.id.etsearch) as EditText

            reportNamesort = JSONArray()
            for (k in 0 until reportNameArrayList.length()) {
                val jsonObject = reportNameArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                reportNamesort.put(jsonObject)
            }


            setValuesRecyclerView(reportNamesort)   //    recycler view adapter


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    reportNamesort = JSONArray()
                  //  Log.i("responsewewe","name"+jsonObject.getString("ReportName").length)

                    for (k in 0 until reportNameArrayList.length()) {
                        val jsonObject = reportNameArrayList.getJSONObject(k)
                        Log.i("responsewewe","len="+jsonObject.getString("ReportName").length)
                        if (textlength <= jsonObject.getString("ReportName").length) {
                            if (jsonObject.getString("ReportName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                reportNamesort.put(jsonObject)
                            }

                        }
                    }

                    setValuesRecyclerView(reportNamesort)

//               //     Log.e(TAG, "reportNamesort               7103    " + reportNamesort)
//                    val adapter = ReportNameAdapter(this@CollectionReportActivity, reportNamesort,"Service")
//                    recyReportName!!.adapter = adapter
//                 //   adapter.setClickListener(this@CollectionReportActivity)
                }
            })

            dialogReportName!!.show()
            dialogReportName!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
         //   Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun setValuesRecyclerView(reportNamesort: JSONArray) {
        val lLayout = GridLayoutManager(this@CollectionReportActivity, 1)
        recyReportName!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
        val adapter = ReportNameAdapter(this@CollectionReportActivity, this.reportNamesort,"Service")
        recyReportName!!.adapter = adapter
         adapter.setClickListener(this@CollectionReportActivity)
      //  adapter.addItemClickListener(this@CollectionReportActivity)
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("reportname")) {
            // resetData()
            dialogReportName!!.dismiss()
            //  val jsonObject = reportNameArrayList.getJSONObject(position)
            val jsonObject = reportNamesort.getJSONObject(position)
            Log.i("sdfdsfsdf", "ReportMode   " + jsonObject.getString("ReportMode"))
            ReportMode = jsonObject.getString("ReportMode")
            tie_ReportName!!.setText(jsonObject.getString("ReportName"))


        }
    }

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {

        if (data.equals("reportname")) {

            Log.i("responseclicked","clicked"+jsonObject.toString())

        }

    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}