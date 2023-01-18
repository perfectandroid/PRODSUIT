package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.ServiceListViewModel
import com.perfect.prodsuit.Viewmodel.ServiceOnGoingListViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceOngoingListActivityActivity : AppCompatActivity()  , View.OnClickListener,
    ItemClickListener {

    var TAG  ="ServiceOngoingListActivityActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var serviceOnGoingListViewModel: ServiceOnGoingListViewModel
    lateinit var serviceListArrayList: JSONArray
    var recyServiceList: RecyclerView? = null

    var serviceList = 0
    var SubMode : String?= ""
    var ID_Branch : String?= ""
    var FK_Area : String?= ""
    var ID_Employee : String?= ""
    var strFromDate : String?= ""
    var strToDate : String?= ""
    var strCustomer : String?= ""
    var strMobile : String?= ""
    var strTicketNo : String?= ""
    var strDueDays : String?= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign_ongoing_list_activity)
        context = this@ServiceOngoingListActivityActivity

        serviceOnGoingListViewModel = ViewModelProvider(this).get(ServiceOnGoingListViewModel::class.java)

        setRegViews()

        SubMode   = intent.getStringExtra("SubMode")
        ID_Branch   = intent.getStringExtra("ID_Branch")
        FK_Area     = intent.getStringExtra("FK_Area")
        ID_Employee = intent.getStringExtra("ID_Employee")
        strFromDate = intent.getStringExtra("strFromDate")
        strToDate   = intent.getStringExtra("strToDate")
        strCustomer = intent.getStringExtra("strCustomer")
        strMobile   = intent.getStringExtra("strMobile")
        strTicketNo = intent.getStringExtra("strTicketNo")
        strDueDays  = intent.getStringExtra("strDueDays")


        serviceList = 0
        getServiceOnGoingList()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }


        }
    }

    private fun getServiceOnGoingList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceOnGoingListViewModel.getServiceOngoingList(this,SubMode!!,ID_Branch!!,FK_Area!!,ID_Employee!!,strFromDate!!,strToDate!!,strCustomer!!,strMobile!!,strTicketNo!!,strDueDays!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serviceList == 0) {
                                    serviceList++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceAssignOnGoingDetails")
                                        serviceListArrayList = jobjt.getJSONArray("ServiceAssignOnGoingList")
                                        if (serviceListArrayList.length() > 0) {

                                            val lLayout = GridLayoutManager(this@ServiceOngoingListActivityActivity, 1)
                                            recyServiceList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ServiceListAdapter(this@ServiceOngoingListActivityActivity, serviceListArrayList,SubMode!!)
                                            recyServiceList!!.adapter = adapter
                                            adapter.setClickListener(this@ServiceOngoingListActivityActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceOngoingListActivityActivity,
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

        if (data.equals("ServiceList")) {

            val i = Intent(this@ServiceOngoingListActivityActivity, ServiceAssignActivity::class.java)
            startActivity(i)
        }
        if (data.equals("ServiceEdit")) {

//            val i = Intent(this@ServiceOngoingListActivityActivity, ServiceAssignActivity::class.java)
//            startActivity(i)
        }

    }
}