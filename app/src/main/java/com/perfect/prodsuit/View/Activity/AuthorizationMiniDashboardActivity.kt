package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ApproveAdapter
import com.perfect.prodsuit.View.Adapter.AuthorizationMixedAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalViewModel
import com.perfect.prodsuit.Viewmodel.AuthorizationMixedViewModel
import org.json.JSONArray
import org.json.JSONObject

class AuthorizationMiniDashboardActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListener {

    val TAG : String = "AuthorizationMiniDashboardActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var jsonObj: JSONObject? = null
    var tv_header: TextView? = null

    internal var recycAuthMini: RecyclerView? = null
    lateinit var authorizationMixedViewModel: AuthorizationMixedViewModel
    lateinit var authMixArrayList: JSONArray
    var authMixCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_authorization_mini_dashboard)

        context = this@AuthorizationMiniDashboardActivity
        authorizationMixedViewModel = ViewModelProvider(this).get(AuthorizationMixedViewModel::class.java)

        setRegViews()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        tv_header!!.setText(jsonObj!!.getString("label"))

        getList()
    }

    private fun getList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                authorizationMixedViewModel.getAuthorizationMixed(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (authMixCount == 0) {
                                    authMixCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   760000   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthorizationList")
                                        authMixArrayList = jobjt.getJSONArray("ListData")

                                        Log.e(TAG, "authMixArrayList   999101   " + authMixArrayList)

                                        if (authMixArrayList.length()> 0){


                                            val lLayout = GridLayoutManager(this@AuthorizationMiniDashboardActivity, 1)
                                            recycAuthMini!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = AuthorizationMixedAdapter(this@AuthorizationMiniDashboardActivity, authMixArrayList)
                                            recycAuthMini!!.adapter = adapter
                                            adapter.setClickListener(this@AuthorizationMiniDashboardActivity)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@AuthorizationMiniDashboardActivity,
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
        tv_header = findViewById<TextView>(R.id.tv_header)
        imback!!.setOnClickListener(this)

        recycAuthMini = findViewById<RecyclerView>(R.id.recycAuthMini)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }

    override fun onClick(position: Int, data: String) {

    }
}