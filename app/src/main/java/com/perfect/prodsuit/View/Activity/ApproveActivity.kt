package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ApproveAdapter
import com.perfect.prodsuit.View.Adapter.AuthDsahboardAdapter
import com.perfect.prodsuit.View.Adapter.HomeGrideCountAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalViewModel
import com.perfect.prodsuit.Viewmodel.AuthDashViewModel
import org.json.JSONArray
import org.json.JSONObject

class ApproveActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "ApproveActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    lateinit var approvalViewModel: ApprovalViewModel
    lateinit var approvalArray: JSONArray
    lateinit var approvalArrayList: JSONArray
    internal var recyAprrove: RecyclerView? = null
    internal var recyDahborad: RecyclerView? = null
    internal var imv_nodata: ImageView? = null
    var approveCount = 0

    lateinit var authDashViewModel: AuthDashViewModel
    lateinit var authdashArray: JSONArray
    lateinit var authdashAdapter : AuthDsahboardAdapter
    var authCount = 0

    private var tv_listCount: TextView?          = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approve)
        context = this@ApproveActivity

        approvalViewModel = ViewModelProvider(this).get(ApprovalViewModel::class.java)
        authDashViewModel = ViewModelProvider(this).get(AuthDashViewModel::class.java)

        setRegViews()

        approveCount = 0
        getAppoval()

        authCount = 0
        getAuthdah()
    }

    private fun getAuthdah() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                authDashViewModel.getAuthdashboard(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (authCount == 0) {
                                    authCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   8888801   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthorizationDetails")
                                        authdashArray = jobjt.getJSONArray("AuthorizationDetailsList")

                                        Log.e(TAG, "authdashArray   8888802   " + authdashArray)

                                        if (authdashArray.length()> 0){


                                            val lLayout = GridLayoutManager(this@ApproveActivity, 2)
                                            lLayout.setSpanSizeLookup(object :
                                                GridLayoutManager.SpanSizeLookup() {
                                                override fun getSpanSize(position: Int): Int {
                                                    // Change the span count based on the row position
                                                    var result = 2
                                                    Log.e(TAG,"10700   "+authdashArray.length())
                                                    if (authdashArray.length()%2!=0){
//                                                        return if (position%2 == 0) 1 else 2
                                                        if (position == authdashArray.length() -1){
                                                            result = 2
                                                        }else{
                                                            result = 1
                                                        }
                                                    }

//

                                                    return result
                                                }
                                            })
//                                            val lLayout = LinearLayoutManager(this@ApproveActivity)
//                                            recyDahborad!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            authdashAdapter = AuthDsahboardAdapter(this@ApproveActivity, authdashArray)
//                                            recyDahborad!!.adapter = authdashAdapter
//                                            authdashAdapter.setClickListener(this@ApproveActivity)

                                            val HorizontalLayout = LinearLayoutManager(this@ApproveActivity, LinearLayoutManager.HORIZONTAL, false)
                                            recyDahborad!!.setLayoutManager(HorizontalLayout)
                                            val authdashAdapter = AuthDsahboardAdapter(this@ApproveActivity,authdashArray)
                                            recyDahborad!!.adapter = authdashAdapter
                                            authdashAdapter.setClickListener(this@ApproveActivity)
                                        }



                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApproveActivity,
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
              //  progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getAppoval() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                recyAprrove!!.adapter = null
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                approvalViewModel.getApproval(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (approveCount == 0) {
                                    approveCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthorizationModuleList")
                                        approvalArrayList = jobjt.getJSONArray("AuthorizationModuleDetails")

                                        Log.e(TAG, "approvalArray   999101   " + approvalArrayList)

                                        if (approvalArrayList.length()> 0){

                                            tv_listCount!!.setText(""+approvalArrayList.length())


                                            val lLayout = GridLayoutManager(this@ApproveActivity, 1)
                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ApproveAdapter(this@ApproveActivity, approvalArrayList)
                                            recyAprrove!!.adapter = adapter
                                            adapter.setClickListener(this@ApproveActivity)
                                        }
//                                        else if (approvalArray.equals("")){
//
//                                            Log.e(TAG, "approvalArray   10001   " + approvalArrayList)
//
//                                            recyAprrove!!.visibility = View.GONE
//                                            imv_nodata!!.visibility = View.VISIBLE
//
//                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApproveActivity,
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

    private fun setRegViews() {

        tv_listCount = findViewById(R.id.tv_listCount)

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyAprrove = findViewById(R.id.recyAprrove)
        recyDahborad = findViewById(R.id.recyDahborad)
//        imv_nodata = findViewById(R.id.imv_nodata)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("approveClick")){
            val jsonObject = approvalArrayList.getJSONObject(position)

            val i = Intent(this@ApproveActivity, ApprovalListActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)

        }
        if (data.equals("authDashClicks")){
            val jsonObject = authdashArray.getJSONObject(position)
//            val i = Intent(this@ApproveActivity, AuthorizationMiniDashboardActivity::class.java)
//            i.putExtra("jsonObject",jsonObject.toString())
//            startActivity(i)
            if (position == 2){
                val i = Intent(this@ApproveActivity, CommonSearchActivity::class.java)
                i.putExtra("jsonObject",jsonObject.toString())
                startActivity(i)
            }else{
                val i = Intent(this@ApproveActivity, AuthorizationMiniDashboardActivity::class.java)
                i.putExtra("jsonObject",jsonObject.toString())
                startActivity(i)
            }


        }
    }

    override fun onRestart() {
        super.onRestart()
        approveCount = 0
        getAppoval()
    }
}