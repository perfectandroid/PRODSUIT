package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.AttendanceFollowUpModel
import com.perfect.prodsuit.Model.HistoryFollowUpModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.HistoryServiceFollowUpAdapter
import com.perfect.prodsuit.Viewmodel.ServiceFollowHistoryViewModel
import com.perfect.prodsuit.Viewmodel.ServiceFollowUpAttendanceListViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceFollowUpHistory : AppCompatActivity(), View.OnClickListener {
    lateinit var recycleView_history: RecyclerView
    lateinit var jsonArrayHistory: JSONArray
    lateinit var imback: ImageView
    var adapterHistoryServiceFollowUp: HistoryServiceFollowUpAdapter? = null

    lateinit var serviceFollowHistoryViewModel: ServiceFollowHistoryViewModel
    var serviceFollowUpHistory = 0
    var customer_service_register: String = ""
    var FK_Customer: String = ""
    var FK_CustomerOthers: String = ""
    var FK_Product: String = ""
    var ID_Branch: String = ""
    var ID_Employee: String = ""
    private var progressDialog: ProgressDialog? = null
    var jsonArrayFollowUpHistory: JSONArray = JSONArray()
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_service_follow_up_history)
        customer_service_register = intent.getStringExtra("customer_service_register").toString()
        FK_Customer = intent.getStringExtra("FK_Customer").toString()
        FK_CustomerOthers = intent.getStringExtra("FK_CustomerOthers").toString()
        FK_Product = intent.getStringExtra("FK_Product").toString()
        val FK_BranchCodeUserSP = this.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
        ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        setId()
        // loadArray()
        loadHistory()
//        SetDataToRecycler(jsonArrayHistory)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setId() {
        imback = findViewById(R.id.imback)
        recycleView_history = findViewById(R.id.recycleView_history)
        imback.setOnClickListener(this)
    }

    private fun loadArray() {
        val historyFollowUpArrayList = ArrayList<HistoryFollowUpModel>()
        val e1 = HistoryFollowUpModel(
            "12458",
            "Battery Check",
            "Loading issue",
            "Pending",
            "26/07/2022",
            "sdsdsdsdsdsds"
        )
        val e2 = HistoryFollowUpModel(
            "47821",
            "Starting Motor",
            "Starting issue",
            "Closed",
            "26/07/2022",
            "dfdfsdfdfddd"
        )
        val e3 = HistoryFollowUpModel(
            "12458",
            "Battery Check",
            "Loading issue",
            "Closed",
            "26/07/2022",
            "sdsdsdsdsdsds"
        )
        historyFollowUpArrayList.add(e1)
        historyFollowUpArrayList.add(e2)
        historyFollowUpArrayList.add(e3)
        val gson = Gson()
        val listString = gson.toJson(
            historyFollowUpArrayList,
            object : TypeToken<ArrayList<AttendanceFollowUpModel?>?>() {}.type
        )
        jsonArrayHistory = JSONArray(listString)
        SetDataToRecycler(jsonArrayHistory)
    }

    private fun loadHistory() {
        recycleView_history!!.adapter = null
        serviceFollowHistoryViewModel =
            ViewModelProvider(this).get(ServiceFollowHistoryViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowHistoryViewModel.getServiceFollowUpHistory(
                    this,
                    customer_service_register,
                    ID_Branch,
                    ID_Employee, FK_Customer, FK_CustomerOthers,FK_Product
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpHistory == 0) {
                                    serviceFollowUpHistory++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ServiceHistoryDetails")
                                        jsonArrayFollowUpHistory =
                                            jobjt.getJSONArray("ServiceHistoryDetailsList")
                                        SetDataToRecycler(jsonArrayFollowUpHistory)
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.show()
                                    }

                                }


                            } else {
                                //swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
//                            swipeRefreshLayout.visibility = View.GONE
//                            swipeRefreshLayout.isRefreshing = false
//                            tv_nodata.visibility = View.VISIBLE
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

    private fun SetDataToRecycler(jsonArrayHistoryData: JSONArray) {
        recycleView_history!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        adapterHistoryServiceFollowUp = HistoryServiceFollowUpAdapter(this, jsonArrayHistoryData)
        recycleView_history!!.adapter = adapterHistoryServiceFollowUp
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imback -> {
                onBackPressed()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}