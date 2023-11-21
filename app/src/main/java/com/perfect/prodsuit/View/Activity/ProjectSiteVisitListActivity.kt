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
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProjectLeadListAdapter
import com.perfect.prodsuit.Viewmodel.ProjectLeadNoViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectSiteVisitListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectSiteVisitListActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    var tv_header    : TextView?    = null
    var jsonObj: JSONObject? = null

    var  projectLeadNoCount = 0
    lateinit var projectLeadNoViewModel: ProjectLeadNoViewModel
    lateinit var projectLeadArrayList: JSONArray
    var recycSiteVisit: RecyclerView? = null
    var mode = ""

    private var ReqMode :  String? =  ""
    private var SubMode :  String? =  ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_site_visit_list)
        context = this@ProjectSiteVisitListActivity

        projectLeadNoViewModel = ViewModelProvider(this).get(ProjectLeadNoViewModel::class.java)

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        ReqMode = intent.getStringExtra("ReqMode").toString()
        SubMode = intent.getStringExtra("SubMode").toString()
        setRegViews()

        tv_header!!.setText(""+jsonObj!!.getString("Type_Name"))

        projectLeadNoCount = 0
        getLeadDetails()

    }

    private fun getLeadDetails() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                projectLeadNoViewModel.getProjectLeadNo(this,ReqMode!!,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {

                                if (projectLeadNoCount == 0){
                                    projectLeadNoCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
//                                        val jobjt = jObject.getJSONObject("LeadDetails")
//                                        projectLeadArrayList = jobjt.getJSONArray("LeadDetailsList")
//
//                                        Log.e(TAG," 788   "+projectLeadArrayList)
//                                        if (projectLeadArrayList.length() > 0){
//                                            val lLayout = GridLayoutManager(this@ProjectSiteVisitListActivity, 1)
//                                            recycSiteVisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
//                                            val adapter = ProjectLeadListAdapter(this@ProjectSiteVisitListActivity, projectLeadArrayList)
//                                            recycSiteVisit!!.adapter = adapter
//                                            adapter.setClickListener(this@ProjectSiteVisitListActivity)
//                                        }
//

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectSiteVisitListActivity,
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
        tv_header = findViewById<TextView>(R.id.tv_header)
        recycSiteVisit = findViewById<RecyclerView>(R.id.recycSiteVisit)

        imback!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("LeadListClick")){
            val jsonObject = projectLeadArrayList.getJSONObject(position)
            val i = Intent(this@ProjectSiteVisitListActivity, ProjectSiteVisitActivity::class.java)
            i.putExtra("mode",mode)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }
}