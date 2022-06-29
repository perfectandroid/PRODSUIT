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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.ServiceAssignViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceAssignActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "ServiceAssignActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    internal var llNew: LinearLayout? = null
    internal var llOnGoing: LinearLayout? = null

    lateinit var serviceAssignViewModel: ServiceAssignViewModel
    lateinit var serviceAssignArrayList : JSONArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign)
        context = this@ServiceAssignActivity

        serviceAssignViewModel = ViewModelProvider(this).get(ServiceAssignViewModel::class.java)

        setRegViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llNew = findViewById<LinearLayout>(R.id.llNew)
        llOnGoing = findViewById<LinearLayout>(R.id.llOnGoing)

        llNew!!.setOnClickListener(this)
        llOnGoing!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llNew->{

                getServiceAssign()
            }
            R.id.llOnGoing->{

            }
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
//                                        val lLayout = GridLayoutManager(this@CustomerServiceActivity, 1)
//                                        recyServiceWarranty!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapter = ServiceWarrantyAdapter(this@CustomerServiceActivity, serviceWarrantyArrayList)
//                                        recyServiceWarranty!!.adapter = adapter
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