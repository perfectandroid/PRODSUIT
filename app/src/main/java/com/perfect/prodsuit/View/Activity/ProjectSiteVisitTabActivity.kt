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
import com.perfect.prodsuit.View.Adapter.ProjectSiteVisitTabAdapter
import com.perfect.prodsuit.View.Adapter.ServiceCountAdapter
import com.perfect.prodsuit.Viewmodel.BranchViewModel
import com.perfect.prodsuit.Viewmodel.ServiceCountViewModel
import com.perfect.prodsuit.Viewmodel.SiteVisitCountViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectSiteVisitTabActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectSiteVisitTabActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    var sitVisitCount = 0
    lateinit var siteVisitCountViewModel: SiteVisitCountViewModel
    var recyc_sitevisit: RecyclerView? = null
    lateinit var siteVisitArrayList: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_site_visit_tab)
        context = this@ProjectSiteVisitTabActivity

        siteVisitCountViewModel = ViewModelProvider(this).get(SiteVisitCountViewModel::class.java)

        setRegViews()

        sitVisitCount = 0
        getSiteVisitCount()

    }

    private fun getSiteVisitCount() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                siteVisitCountViewModel.getSiteVisitCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (sitVisitCount == 0){
                                    sitVisitCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("SiteTabDetails")
                                        siteVisitArrayList = jobjt.getJSONArray("SiteTabDetailsList")

                                        Log.e(TAG," 788   "+siteVisitArrayList)
                                        if (siteVisitArrayList.length() > 0){
                                            val lLayout = GridLayoutManager(this@ProjectSiteVisitTabActivity, 1)
                                            recyc_sitevisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ProjectSiteVisitTabAdapter(this@ProjectSiteVisitTabActivity, siteVisitArrayList)
                                            recyc_sitevisit!!.adapter = adapter
                                            adapter.setClickListener(this@ProjectSiteVisitTabActivity)
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectSiteVisitTabActivity,
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

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyc_sitevisit = findViewById<RecyclerView>(R.id.recyc_sitevisit)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("SiteVisitTabClick")){
            val jsonObject = siteVisitArrayList.getJSONObject(position)
            if (position == 0){
                val i = Intent(this@ProjectSiteVisitTabActivity, ProjectSiteVisitActivity::class.java)
                i.putExtra("mode","0")
                i.putExtra("jsonObject","")
                startActivity(i)
            }

            if (position == 1){
                val i = Intent(this@ProjectSiteVisitTabActivity, ProjectSiteVisitListActivity::class.java)
                i.putExtra("mode",position)
                i.putExtra("jsonObject",jsonObject.toString())
                startActivity(i)
            }

            if (position == 2){
                val i = Intent(this@ProjectSiteVisitTabActivity, ProjectSiteVisitListActivity::class.java)
                i.putExtra("mode",position)
                i.putExtra("jsonObject",jsonObject.toString())
                startActivity(i)
            }
        }
    }


}