package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.PickupDeliveryListAdapter
import com.perfect.prodsuit.Viewmodel.PickDeliveryListViewModel
import org.json.JSONArray
import org.json.JSONObject

class PickUpAndDeliveryListActivity : AppCompatActivity(), View.OnClickListener, ClickListener {

    var TAG  ="PickUpAndDeliveryListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null


    private var SubMode:String?=""

    private var tv_header: TextView? = null

    var pickDeliveryCount = 0
    lateinit var pickUpDeliveryViewModel: PickDeliveryListViewModel
    lateinit var pickUpDeliveryArrayList: JSONArray
    var recyPickUpDelivery: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery_list)
        context = this@PickUpAndDeliveryListActivity

        pickUpDeliveryViewModel = ViewModelProvider(this).get(PickDeliveryListViewModel::class.java)

        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById(R.id.tv_header)
        recyPickUpDelivery = findViewById(R.id.recyPickUpDelivery)
        setHeader()
        pickDeliveryCount = 0
        getPickUpDeliveryList()

    }



    private fun setHeader() {

        if (SubMode.equals("1")){
            tv_header!!.text = "Pick up"
        }
        if (SubMode.equals("2")){
            tv_header!!.text = "Delivery"
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }
    private fun getPickUpDeliveryList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickUpDeliveryViewModel.getPickDeliveryList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (pickDeliveryCount == 0) {
                                    pickDeliveryCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   104   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        pickUpDeliveryArrayList = jobjt.getJSONArray("CategoryList")
                                        if (pickUpDeliveryArrayList.length() > 0) {

                                            val lLayout = GridLayoutManager(this@PickUpAndDeliveryListActivity, 1)
                                            recyPickUpDelivery!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = PickupDeliveryListAdapter(this@PickUpAndDeliveryListActivity, pickUpDeliveryArrayList,SubMode!!)
                                            recyPickUpDelivery!!.adapter = adapter
                                            adapter.setClickListener(this@PickUpAndDeliveryListActivity)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryListActivity,
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

    override fun onClick(position: Int, data: String, view: View) {

        if (data.equals("pickupDelivery")){
            Config.disableClick(view)
            val i = Intent(this@PickUpAndDeliveryListActivity, PickUpAndDeliveryUpdateActivity::class.java)
            i.putExtra("SubMode",SubMode)
            startActivity(i)
        }

        if (data.equals("pickDelCall")){
            Config.disableClick(view)
            var mobileno = "8075283549"
            if (mobileno.equals("")){
                Config.snackBarWarning(context,view,"Invalid mobile number")
            }
            else{
                callFunction(mobileno)
            }

        }

        if (data.equals("pickDelLocation")){
            Config.disableClick(view)
            checkLocationPermission(view)
        }


    }

    private fun callFunction(mobileno : String) {
        val ALL_PERMISSIONS = 101

        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE
        )
        if (ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.CALL_PHONE
            ) + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.RECORD_AUDIO
            )
            + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.READ_PHONE_STATE
            )
            + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {

                //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)
            }

        }

    private fun checkLocationPermission(v: View) {
        val ALL_PERMISSIONS = 102

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) + ContextCompat.checkSelfPermission(
                this@PickUpAndDeliveryListActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val i = Intent(this@PickUpAndDeliveryListActivity, LocationCollectionActivity::class.java)
            startActivity(i)

        }
    }
}


