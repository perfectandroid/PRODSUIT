package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class

DashboardNewActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener,
    View.OnFocusChangeListener {

    var TAG  ="DashboardNewActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var dashboardmoduleArrayList: JSONArray
    lateinit var dashmoduleSort: JSONArray
    var dashmoduleCount = 0
    lateinit var dashboardModuleViewModel: DashboardModuleViewModel
    var recyclervw_dashboard: RecyclerView? = null

    private var temp_DueDays: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_dashboard_new)
        context = this@DashboardNewActivity
        dashboardModuleViewModel = ViewModelProvider(this).get(DashboardModuleViewModel::class.java)

        setRegViews()

        dashmoduleCount = 0
        getDashboardModules()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyclervw_dashboard = findViewById<RecyclerView>(R.id.recyclervw_dashboard)



    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }

    private fun getDashboardModules() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                dashboardModuleViewModel.getDashboardModuleListReport(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (dashmoduleCount == 0){
                                    dashmoduleCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("DashBoardModule")


                                        dashboardmoduleArrayList = jobjt.getJSONArray("DashBoardModuleList")
                                        if (dashboardmoduleArrayList.length() > 0) {


                                            dashmoduleSort = JSONArray()
                                            for (k in 0 until dashboardmoduleArrayList.length()) {
                                                val jsonObject = dashboardmoduleArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                dashmoduleSort.put(jsonObject)
                                            }


                                            val lLayout = GridLayoutManager(this@DashboardNewActivity, 1)
                                            recyclervw_dashboard!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = DashboardModuleListAdapter(this@DashboardNewActivity,dashmoduleSort)
                                            recyclervw_dashboard!!.adapter = adapter
                                            adapter.setClickListener(this@DashboardNewActivity)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@DashboardNewActivity,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("branch")) {


        }


    }

    override fun onRestart() {
        super.onRestart()
        dashmoduleCount = 0
        getDashboardModules()
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        TODO("Not yet implemented")
    }


}