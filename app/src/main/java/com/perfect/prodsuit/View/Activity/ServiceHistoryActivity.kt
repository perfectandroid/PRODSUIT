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

class ServiceHistoryActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG  ="ServiceHistoryActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var serviceHistViewModel: ServiceHistViewModel
    lateinit var serviceHistoryListArrayList: JSONArray
    lateinit var serviceHistListSort: JSONArray
    var recyServiceHist: RecyclerView? = null
    var imgv_filter: ImageView? = null
    var txtv_headlabel: TextView? = null
    var tv_listCount: TextView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_history)
        context = this@ServiceHistoryActivity
        serviceHistViewModel = ViewModelProvider(this).get(ServiceHistViewModel::class.java)


        setRegViews()

        getServiceHistList()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        recyServiceHist = findViewById<RecyclerView>(R.id.recyServiceHistList)
        recyServiceHist!!.adapter = null
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



    private fun getServiceHistList() {
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
                serviceHistViewModel.getServiceHist(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {


                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   servicehist   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceHistoryDetails")
                                        serviceHistoryListArrayList = jobjt.getJSONArray("ServiceHistoryDetailsList")
                                        if (serviceHistoryListArrayList.length() > 0) {
                                           // imgv_filter!!.visibility  =View.VISIBLE

                                            serviceHistListSort = JSONArray()
                                            for (k in 0 until serviceHistoryListArrayList.length()) {
                                                val jsonObject = serviceHistoryListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                serviceHistListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+serviceHistListSort.length())
                                            val lLayout = GridLayoutManager(this@ServiceHistoryActivity, 1)
                                            recyServiceHist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ServiceListHistAdapter(this@ServiceHistoryActivity,serviceHistoryListArrayList!!)
                                            recyServiceHist!!.adapter = adapter
                                            adapter.setClickListener(this@ServiceHistoryActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceHistoryActivity,
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
        getServiceHistList()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


}