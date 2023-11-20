package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AuthorizationCountAdapter
import com.perfect.prodsuit.Viewmodel.AuthorizationMixedViewModel
import com.perfect.prodsuit.Viewmodel.HomeDashBoardCountDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject

class HomeDashBoardDetailPageActivity : AppCompatActivity() {

    val TAG : String = "HomeDashBoardDetailPageActivity"
    lateinit var context: Context
    lateinit var homedashboardCountDetailsViewModel: HomeDashBoardCountDetailsViewModel
    internal var recycdetail_show: RecyclerView?    = null
    private var progressDialog   : ProgressDialog?  = null
    var authMixCount                                = 0
    var AttendedLeadsArraylist                      = JSONArray()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board_detail_page)

        context = this@HomeDashBoardDetailPageActivity
        homedashboardCountDetailsViewModel = ViewModelProvider(this).get(HomeDashBoardCountDetailsViewModel::class.java)

        getCountDetailList()
    }

    private fun getCountDetailList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                homedashboardCountDetailsViewModel.getHomeDashBoardCountDetails(this)!!.observe(
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

                                        val jobjt = jObject.getJSONObject("UserTaskListDetails")
                                        AttendedLeadsArraylist = jobjt.getJSONArray("AttendedLeads")
                                        Log.e(TAG, "AttendedLeadsArraylist   6666   " + AttendedLeadsArraylist)


                                        val lLayout = GridLayoutManager(this@HomeDashBoardDetailPageActivity, 1)
                                        recycdetail_show!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapter = AuthorizationCountAdapter(this@HomeDashBoardDetailPageActivity, AttendedLeadsArraylist)
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}