package com.perfect.prodsuit.View.Activity

import CustomerBalanceViewModel
import android.app.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CusbalListAdapter
import org.json.JSONArray
import org.json.JSONObject

class CustomerBalanceActivity : AppCompatActivity(), View.OnClickListener,ItemClickListener{
    var TAG  ="CustomerBalanceActivity"
    lateinit var context: Context
    lateinit var customerbalArrayList : JSONArray
    private var progressDialog: ProgressDialog? = null
    lateinit var customerBalanceViewModel: CustomerBalanceViewModel
    lateinit var customerbalSort: JSONArray
    var rv_custombal: RecyclerView? = null
    var imback :ImageView? = null
    var TicketDate: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_custombal)

        context = this@CustomerBalanceActivity
        customerBalanceViewModel = ViewModelProvider(this).get(CustomerBalanceViewModel::class.java)

        TicketDate = intent.getStringExtra("TicketDate")
        setRegViews()
//        getCustomerBalance(TicketDate)

    }

    private fun setRegViews() {

         rv_custombal = findViewById<RecyclerView>(R.id.rv_custombal)
         imback = findViewById(R.id.imback)as ImageView
         imback!!.setOnClickListener(this)

    }



    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }

        }
    }







    override fun onClick(position: Int, data: String) {

    }
//    private fun getCustomerBalance(Ticketdate: String?) {
////        recyServiceList!!.adapter = null
////        tv_listCount!!.setText("0")
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//
//                customerBalanceViewModel.getCustombal(this, Ticketdate!!)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//
//                        try {
//                            val msg = serviceSetterGetter.message
//                            if (msg!!.length > 0) {
//
//
//                                    val jObject = JSONObject(msg)
//                                    Log.e(TAG, "msg   custbal   " + msg)
//                                    if (jObject.getString("StatusCode") == "0") {
//                                        val jobjt = jObject.getJSONObject("CustomerBalanceDetails")
//                                        customerbalArrayList = jobjt.getJSONArray("CustomerBalanceList")
//                                        if (customerbalArrayList.length() > 0) {
//
//
//                                            customerbalSort = JSONArray()
//                                            for (k in 0 until customerbalArrayList.length()) {
//                                                val jsonObject = customerbalArrayList.getJSONObject(k)
//                                                // reportNamesort.put(k,jsonObject)
//                                                customerbalSort.put(jsonObject)
//                                            }
//
//                                          //  tv_listCount!!.setText(""+serviceListSort.length())
//                                            val lLayout = GridLayoutManager(this@CustomerBalanceActivity, 1)
//                                            rv_custombal!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
//                                            val adapter = CusbalListAdapter(this@CustomerBalanceActivity, customerbalArrayList)
//                                            rv_custombal!!.adapter = adapter
//                                            adapter.setClickListener(this@CustomerBalanceActivity)
//
//                                        }
//                                    } else {
//                                        val builder = AlertDialog.Builder(
//                                            this@CustomerBalanceActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
//                                    }
//
//
//                            } else {
////                                 Toast.makeText(
////                                     applicationContext,
////                                     "Some Technical Issues.",
////                                     Toast.LENGTH_LONG
////                                 ).show()
//                            }
//                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//    }
}
