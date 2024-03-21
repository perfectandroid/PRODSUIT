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
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ClosedTicketActivity : AppCompatActivity() , View.OnClickListener, ItemClickListenerData {

    var TAG  ="ClosedTicketActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var closedTicketViewModel: ClosedTicketViewModel
    lateinit var closedTicketArrayList: JSONArray
    lateinit var closedTicketListSort: JSONArray
    var rclv_clsdtktlst: RecyclerView? = null
    var imgv_filter: ImageView? = null
    var txtv_headlabel: TextView? = null
    var tv_listCount: TextView? = null
    var Idcudtomerregisterdetails: String? = "0"
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_closed_ticket)
        context = this@ClosedTicketActivity
        closedTicketViewModel = ViewModelProvider(this,).get(ClosedTicketViewModel::class.java)


        setRegViews()

        getClosedTicketList()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        rclv_clsdtktlst = findViewById<RecyclerView>(R.id.rclv_clsdtktlst)
        rclv_clsdtktlst!!.adapter = null
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



    private fun getClosedTicketList() {
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
                closedTicketViewModel.getclosedTicket(this,Idcudtomerregisterdetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {


                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   closedticket   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                       val jobjt = jObject.getJSONObject("ServiceInvoice")
                                        closedTicketArrayList = jobjt.getJSONArray("TicketDetails")
                                        if (closedTicketArrayList.length() > 0) {
                                           // imgv_filter!!.visibility  =View.VISIBLE

                                            closedTicketListSort = JSONArray()
                                            for (k in 0 until closedTicketArrayList.length()) {
                                                val jsonObject = closedTicketArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                closedTicketListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+closedTicketListSort.length())
                                            val lLayout = GridLayoutManager(this@ClosedTicketActivity, 1)
                                            rclv_clsdtktlst!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ClosedTicketListAdapter(this@ClosedTicketActivity,closedTicketArrayList!!)
                                            rclv_clsdtktlst!!.adapter = adapter
                                            adapter.setClickListener(this@ClosedTicketActivity)

                                        }
                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@ClosedTicketActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") {

                                                dialogInterface, which ->
                                            onBackPressed()
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

//    override fun onClick(position: Int, data: String) {
//
//        if (data.equals("ServiceList")) {
//          /*  val jsonObject = serviceListArrayList.getJSONObject(position)
//            ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
//            FK_CustomerserviceregisterProductDetails = jsonObject.getString("ID_CustomerServiceRegisterProductDetails")
//            TicketDate = jsonObject.getString("TicketDate")
//            TicketStatus = jsonObject.getString("TicketStatus")
//
//            Log.i("FKK",FK_CustomerserviceregisterProductDetails.toString())
//            val i = Intent(this@ServiceHistoryActivity, ServiceAssignActivity::class.java)
//            i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
//            i.putExtra("FK_CustomerserviceregisterProductDetails",FK_CustomerserviceregisterProductDetails)
//            i.putExtra("TicketStatus",TicketStatus)
//            i.putExtra("TicketDate",TicketDate)
//            startActivity(i)*/
//        }
//
//        if (data.equals("ShareInvoice")) {
//
//            val customer_service_register = jsonObject!!.getString("ID_Customerserviceregister")
//
//            Log.e(TAG, "shareInvoice===="+data)
//            val intent = Intent(this@ClosedTicketActivity, ServiceInvoiceActivity::class.java)
//          //  intent.putExtra("customer_service_register", customer_service_register)
//
//            startActivity(intent)
//
//        }
//
//
//
//
//
//    }



    override fun onRestart() {
        super.onRestart()
        //serviceList = 0
        getClosedTicketList()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {
        if (data.equals("ShareInvoice")) {
            val jsonObject1 = closedTicketArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Priority   "+jsonObject1.getString("ID_CustomerServiceRegister"))
            //Log.e(TAG, "onclick   " + data)

            val customer_name = jsonObject1!!.getString("Customer")
            val TicketNo = jsonObject1!!.getString("TicketNo")


            val CusAddress = jsonObject1!!.getString("CusAddress")
            val RegDate = jsonObject1!!.getString("RegDate")

            Idcudtomerregisterdetails= jsonObject1!!.getString("ID_CustomerServiceRegister")
          //  jsonObject = jsonArray.getJSONObject(position)
            val intent = Intent(this@ClosedTicketActivity, ServiceInvoiceActivity::class.java)
            intent.putExtra("customer_name", customer_name)
            intent.putExtra("TicketNo", TicketNo)
            intent.putExtra("CusAddress", CusAddress)
            intent.putExtra("RegDate", RegDate)
            intent.putExtra("Idcudtomerregisterdetails", Idcudtomerregisterdetails)



//            if (CusAddress.equals(""))
//            {
//                intent.putExtra("CusAddress", "")
//            }
//            else
//            {
//                intent.putExtra("CusAddress", CusAddress)
//            }
//            if (RegDate.equals(""))
//            {
//                intent.putExtra("RegDate", "")
//            }
//            else
//            {
//                intent.putExtra("RegDate", RegDate)
//            }



            startActivity(intent)

        }
    }


}