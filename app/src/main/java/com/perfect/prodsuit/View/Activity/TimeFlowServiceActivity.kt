package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.MapRootDetailAdapter
import com.perfect.prodsuit.Viewmodel.EmployeeWiseLocationListViewModel
import com.perfect.prodsuit.Viewmodel.ServiceTimeLineViewModel
import org.json.JSONArray
import org.json.JSONObject

class TimeFlowServiceActivity : AppCompatActivity(), View.OnClickListener {

    var TAG = "TimeFlowServiceActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context


    //...............
    var TicketStatus: String? = ""
    var TicketDate: String? = ""
    var ID_Master: String? = ""
    var TransMode: String? = ""
    //............
    var timeLine = 0
    lateinit var serviceTimeLineViewModel: ServiceTimeLineViewModel
    lateinit var timeLineList : JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_time_flow_service)
        context = this@TimeFlowServiceActivity
        serviceTimeLineViewModel = ViewModelProvider(this).get(ServiceTimeLineViewModel::class.java)
        setRegViews()
        TicketStatus = intent.getStringExtra("TicketStatus")
        TicketDate = intent.getStringExtra("TicketDate")
        ID_Master = intent.getStringExtra("ID_Master")
        TransMode = intent.getStringExtra("TransMode")

        Log.e(
            TAG,"TIME LINE 454553"
                +"\n"+"TicketStatus     : "+ TicketStatus
                +"\n"+"TicketDate                   : "+ TicketDate
                +"\n"+"ID_Master                : "+ ID_Master
                +"\n"+"TransMode            : "+ TransMode
                )


        getTimeLineList(ID_Master,TransMode)
    }

    private fun getTimeLineList(ID_Master: String?, TransMode: String?) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceTimeLineViewModel.getServiceTimeLine(this, ID_Master!!,TransMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (timeLine == 0){
                                    timeLine++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TimeLineDetails")
                                        timeLineList = jobjt.getJSONArray("TimeLineList")
                                        Log.e(
                                            TAG,"array 78111=="+timeLineList

                                        )


//                                        if (locationList.length()>0){
//                                            tv_employeee!!.setText(locationList.getJSONObject(0).getString("EmployeeName"))
//                                            tv_EnteredDate!!.setText("Showing location details as on "+locationList.getJSONObject(0).getString("EnteredDate"))
//
//                                            val lLayout = GridLayoutManager(this@MapRootDetailActivity, 1)
//                                            recyMapRoot!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = MapRootDetailAdapter(this@MapRootDetailActivity, locationList)
//                                            recyMapRoot!!.adapter = adapter
//                                        }





                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TimeFlowServiceActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e:Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }
}