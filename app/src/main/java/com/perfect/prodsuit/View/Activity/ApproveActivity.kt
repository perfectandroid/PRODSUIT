package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ApproveAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalViewModel
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
    internal var imv_nodata: ImageView? = null
    var approveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approve)
        context = this@ApproveActivity

        approvalViewModel = ViewModelProvider(this).get(ApprovalViewModel::class.java)

        setRegViews()

        approveCount = 0
        getAppoval()
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

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyAprrove = findViewById(R.id.recyAprrove)
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
    }

    override fun onRestart() {
        super.onRestart()
        approveCount = 0
        getAppoval()
    }
}