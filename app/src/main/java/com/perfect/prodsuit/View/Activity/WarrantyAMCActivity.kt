package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WarrantyAMCActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG  ="WarrantyAMCActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var warrantyAMCViewModel: WarrantyAMCViewModel
    lateinit var warrantyamcArrayList: JSONArray
    lateinit var warrantyamcListSort: JSONArray
    var rclrvw_warrnty: RecyclerView? = null
    var imgv_filter: ImageView? = null
    var txtv_headlabel: TextView? = null
    var tv_listCount: TextView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_warranty_amc)
        context = this@WarrantyAMCActivity
        warrantyAMCViewModel = ViewModelProvider(this).get(WarrantyAMCViewModel::class.java)

        setRegViews()

        getWarrantyAmcList()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        rclrvw_warrnty = findViewById<RecyclerView>(R.id.rclrvw_warrnty)
        rclrvw_warrnty!!.adapter = null
        tv_listCount = findViewById<TextView>(R.id.tv_listCount)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.imgv_filter->{
                /*Config.disableClick(v)
                filterBottomSheet()*/

            }


        }
    }



    private fun getWarrantyAmcList() {
//        recyServiceList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                warrantyAMCViewModel.getWarrantyAMC(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {


                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   warrantyamc  " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("WarrantyAMCDetails")
                                        warrantyamcArrayList = jobjt.getJSONArray("WarrantyAMCDetailsList")
                                        if (warrantyamcArrayList.length() > 0) {
                                           // imgv_filter!!.visibility  =View.VISIBLE

                                            warrantyamcListSort = JSONArray()
                                            for (k in 0 until warrantyamcArrayList.length()) {
                                                val jsonObject = warrantyamcArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                warrantyamcListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+warrantyamcListSort.length())
                                            val lLayout = GridLayoutManager(this@WarrantyAMCActivity, 1)
                                            rclrvw_warrnty!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = WarrantyAMCAdapter(this@WarrantyAMCActivity,warrantyamcArrayList!!)
                                            rclrvw_warrnty!!.adapter = adapter
                                            adapter.setClickListener(this@WarrantyAMCActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@WarrantyAMCActivity,
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("ServiceList")) {
          /*  val jsonObject = serviceListArrayList.getJSONObject(position)
            ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
            FK_CustomerserviceregisterProductDetails = jsonObject.getString("ID_CustomerServiceRegisterProductDetails")
            TicketDate = jsonObject.getString("TicketDate")
            TicketStatus = jsonObject.getString("TicketStatus")

            Log.i("FKK",FK_CustomerserviceregisterProductDetails.toString())
            val i = Intent(this@ServiceHistoryActivity, ServiceAssignActivity::class.java)
            i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
            i.putExtra("FK_CustomerserviceregisterProductDetails",FK_CustomerserviceregisterProductDetails)
            i.putExtra("TicketStatus",TicketStatus)
            i.putExtra("TicketDate",TicketDate)
            startActivity(i)*/
        }





    }



    override fun onRestart() {
        super.onRestart()
        //serviceList = 0
        getWarrantyAmcList()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


}