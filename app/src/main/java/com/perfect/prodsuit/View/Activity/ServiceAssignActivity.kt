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
import androidx.lifecycle.Observer
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ServiceAssignAdapter
import com.perfect.prodsuit.Viewmodel.ServiceAssignViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceAssignActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "ServiceAssignActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    internal var llNew: LinearLayout? = null
    internal var llOnGoing: LinearLayout? = null

    internal var recyServiceAssign: RecyclerView? = null

    lateinit var serviceAssignViewModel: ServiceAssignViewModel
    lateinit var serviceAssignArrayList : JSONArray


    private var tv_TicketClick: TextView? = null
    private var tv_ServiceClick: TextView? = null
    private var tv_ProductClick: TextView? = null

    private var lnrHead_Ticket: LinearLayout? = null
    private var lnrHead_Service: LinearLayout? = null
    private var lnrHead_Product: LinearLayout? = null


    var ticketMode: String? = "0"
    var serviceMode: String? = "1"
    var productMode: String? = "1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign)
        context = this@ServiceAssignActivity

        serviceAssignViewModel = ViewModelProvider(this).get(ServiceAssignViewModel::class.java)

        setRegViews()

        ticketMode = "0"
        hideViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

       // recyServiceAssign = findViewById<RecyclerView>(R.id.recyServiceAssign)

        tv_TicketClick = findViewById<TextView>(R.id.tv_TicketClick)
        tv_ServiceClick = findViewById<TextView>(R.id.tv_ServiceClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)

        tv_TicketClick!!.setOnClickListener(this)
        tv_ServiceClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)

        lnrHead_Ticket = findViewById<LinearLayout>(R.id.lnrHead_Ticket)
        lnrHead_Service = findViewById<LinearLayout>(R.id.lnrHead_Service)
        lnrHead_Product = findViewById<LinearLayout>(R.id.lnrHead_Product)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tv_TicketClick->{
                ticketMode = "0"
                serviceMode  = "1"
                productMode = "1"
                hideViews()
            }

            R.id.tv_ServiceClick->{
                ticketMode = "1"
                serviceMode  = "0"
                productMode = "1"
                hideViews()
            }

            R.id.tv_ProductClick->{
                ticketMode = "1"
                serviceMode  = "1"
                productMode = "0"
                hideViews()
            }

        }
    }

    private fun hideViews() {
        lnrHead_Ticket!!.visibility = View.VISIBLE
        lnrHead_Service!!.visibility = View.VISIBLE
        lnrHead_Product!!.visibility = View.VISIBLE

        if (ticketMode.equals("1")) {
            lnrHead_Ticket!!.visibility = View.GONE
        }
        if (serviceMode.equals("1")) {
            lnrHead_Service!!.visibility = View.GONE
        }
        if (productMode.equals("1")) {
            lnrHead_Product!!.visibility = View.GONE
        }
    }

    private fun getServiceAssign() {

        var assign = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceAssignViewModel.getServiceAssign(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   92   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ServiceAssignDetails")
                                serviceAssignArrayList = jobjt.getJSONArray("ServiceAssignDetailsList")
                                if (serviceAssignArrayList.length()>0){
                                    if (assign == 0){
                                        assign++
                                        val lLayout = GridLayoutManager(this@ServiceAssignActivity, 1)
                                        recyServiceAssign!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = ServiceAssignAdapter(this@ServiceAssignActivity, serviceAssignArrayList)
                                        recyServiceAssign!!.adapter = adapter
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@ServiceAssignActivity,
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
}