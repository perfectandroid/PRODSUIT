package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DashCountDetailstAdapter
import com.perfect.prodsuit.Viewmodel.HomeDashBoardCountDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject

class HomeDashBoardDetailPageActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = "HomeDashBoardDetailPageActivity"
    lateinit var context: Context
    lateinit var homedashboardCountDetailsViewModel: HomeDashBoardCountDetailsViewModel
    internal var recycdetail_show: RecyclerView?    = null
    private var progressDialog   : ProgressDialog?  = null
    var CountDetailCount                            = 0
    var DataItemsListArraylist                      = JSONArray()
    var jsonObj: JSONObject? = null
    private var Criteria: String? = ""
    private var ReqMode: String? = ""
//    private var Module_sub: String? = ""
private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board_detail_page)

        context = this@HomeDashBoardDetailPageActivity
        homedashboardCountDetailsViewModel = ViewModelProvider(this).get(HomeDashBoardCountDetailsViewModel::class.java)

//        CountDetailCount = 0
//        getCountDetailList()

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        Criteria= jsonObj!!.getString("Criteria")
        Log.e(TAG,"Criteria 445   "+Criteria)
        Log.e(TAG,"Criteria 445   "+jsonObj)
        setRegViews()

        CountDetailCount = 0
        getCountDetailList()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        recycdetail_show=findViewById(R.id.recycdetail_show)


        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    private fun getCountDetailList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                ReqMode = "18"
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                homedashboardCountDetailsViewModel.getHomeDashBoardCountDetails(this,Criteria!!,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (CountDetailCount == 0) {
                                    CountDetailCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   760000   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UserTaskListDetails")
                                        DataItemsListArraylist = jobjt.getJSONArray("DataItemsList")
                                        Log.e(TAG, "DataItemsListArraylist   6666   " + DataItemsListArraylist)


                                        val lLayout = GridLayoutManager(this@HomeDashBoardDetailPageActivity, 1)
                                        recycdetail_show!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = DashCountDetailstAdapter(this@HomeDashBoardDetailPageActivity, DataItemsListArraylist)
                                        recycdetail_show!!.adapter = adapter
//                                            adapter.setClickListener(this@AuthorizationMiniDashboardActivity)


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@HomeDashBoardDetailPageActivity,
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

                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e(TAG,"ddd "+e)
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imback->{
                finish()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}