package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.View.Adapter.SummaryReportDetailAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class LeadSummaryDetailReportActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    //.............
    var ID_Master: String? = ""
    var TransMode: String? = ""
    lateinit var summaryreportSort: JSONArray
    //...............
    var exmessage : String? = ""
    var strFromdate : String? = ""
    var strTodate : String? = ""
    var ID_Product : String? = ""
    var ID_Category : String? = ""
    var ID_Branch : String? = ""
    var ID_Employee : String? = ""



    var TAG  ="LeadSummaryDetailReportActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    lateinit var leadsumViewModel: LeadSummaryDetailReportViewModel

    var recySummaryReportdetailList: RecyclerView? = null
    var submode : String?= ""
    private var til_Status: TextInputLayout? = null
    var tv_listCount: TextView? = null
    var summarydetailcount = 0
    var recyFollowupAction: RecyclerView? = null
    var txtv_headlabel: TextView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    lateinit var leadsummaryreportListArrayList: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_summaryreportdetail_list)
        context = this@LeadSummaryDetailReportActivity

        leadsumViewModel = ViewModelProvider(this).get(LeadSummaryDetailReportViewModel::class.java)

        setRegViews()

        submode   = intent.getStringExtra("SubMode")
        strFromdate   = intent.getStringExtra("strFromdate")
        strTodate   = intent.getStringExtra("strTodate")
        ID_Product   = intent.getStringExtra("ID_Product")
        ID_Category   = intent.getStringExtra("ID_Category")
        ID_Branch   = intent.getStringExtra("ID_Branch")
        ID_Employee   = intent.getStringExtra("ID_Employee")
     //   txtv_headlabel!!.setText(label)


        summarydetailcount = 0
        getLeadSummaryDetailReport(submode)


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

       // imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
      //  imgv_filter!!.setOnClickListener(this)

        tv_listCount= findViewById<TextView>(R.id.tv_listCount)


        recySummaryReportdetailList = findViewById<RecyclerView>(R.id.recySummaryReportdetailList)
        recySummaryReportdetailList!!.adapter = null


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                Config.disableClick(v)
                finish()
            }



        }
    }



    private fun getLeadSummaryDetailReport(submode: String?) {
        recySummaryReportdetailList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadsumViewModel.getLeadSummaryDetailReport(this, submode,
                    strFromdate,
                            strTodate,
                            ID_Product,
                            ID_Category,
                            ID_Branch,
                            ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (summarydetailcount == 0) {
                                    summarydetailcount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   leadsummarydetailreport   " + msg)
                                   if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadSummaryDetailsReport")

                                       leadsummaryreportListArrayList = jobjt.getJSONArray("LeadSummaryDetailsList")
                                        if (leadsummaryreportListArrayList.length() > 0) {
                                          //  imgv_filter!!.visibility  =View.VISIBLE

                                            summaryreportSort = JSONArray()

                                            for (k in 0 until leadsummaryreportListArrayList.length()) {
                                                val jsonObject = leadsummaryreportListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                summaryreportSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+summaryreportSort.length())
                                            val lLayout = GridLayoutManager(this@LeadSummaryDetailReportActivity, 1)
                                            recySummaryReportdetailList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = SummaryReportDetailAdapter(this@LeadSummaryDetailReportActivity, summaryreportSort!!)
                                            recySummaryReportdetailList!!.adapter = adapter
                                            adapter.setClickListener(this@LeadSummaryDetailReportActivity)

                                        }
                                    } else {

                                        Log.i("ExMessage",jObject.getString("EXMessage"))
                                        val builder = AlertDialog.Builder(
                                            this@LeadSummaryDetailReportActivity,
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

     /*   if (data.equals("Service_timeLine"))
        {
            val jsonObject = serviceListArrayList.getJSONObject(position)

            TicketDate = jsonObject.getString("TicketDate")
            TicketStatus = jsonObject.getString("TicketStatus")
            ID_Master = jsonObject.getString("ID_Master")
            TransMode = jsonObject.getString("TransMode")

            Log.e(TAG, "time Flow 4545456 transmode  "+TransMode)
            Log.e(TAG, "time Flow 4545456 idcus  "+ID_Master )
            Log.e(TAG, "time Flow 4545456  status "+TicketStatus )


            val i = Intent(this@LeadSummaryDetailReportActivity, TimeFlowServiceActivity::class.java)
            i.putExtra("TicketDate",TicketDate)
            i.putExtra("TicketStatus",TicketStatus)
            i.putExtra("ID_Master",ID_Master)
            i.putExtra("TransMode",TransMode)
            startActivity(i)

            startActivity(i)
        }
*/




    }


    override fun onRestart() {
        super.onRestart()
        summarydetailcount = 0
        getLeadSummaryDetailReport(submode)
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


}