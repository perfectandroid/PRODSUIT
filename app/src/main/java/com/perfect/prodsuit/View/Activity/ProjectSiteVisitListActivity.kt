package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.LeadNoAdapter
import com.perfect.prodsuit.View.Adapter.ProjectLeadListAdapter
import com.perfect.prodsuit.Viewmodel.ProjectLeadNoViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectSiteVisitListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectSiteVisitListActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    var tv_header    : TextView?    = null
    var txt_nodata    : TextView?    = null
    var edtSearch    : EditText?    = null
    var jsonObj: JSONObject? = null

    var  projectLeadNoCount = 0
    lateinit var projectLeadNoViewModel: ProjectLeadNoViewModel
    lateinit var projectLeadArrayList: JSONArray
    lateinit var projectLeadSortArrayList: JSONArray
    var recycSiteVisit: RecyclerView? = null
    var mode = ""

    private var ReqMode :  String? =  ""
    private var SubMode :  String? =  ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


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
        tv_header!!.setText(""+intent!!.getStringExtra("Type_Name"))
        txt_nodata!!.setText("Invalid Lead")

        projectLeadNoCount = 0
        getLeadDetails()

        edtSearch!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //  list_view!!.setVisibility(View.VISIBLE)
                val textlength = edtSearch!!.text.length
                projectLeadSortArrayList = JSONArray()
                recycSiteVisit!!.adapter = null
                for (k in 0 until projectLeadArrayList.length()) {
                    val jsonObject = projectLeadArrayList.getJSONObject(k)
                  //  if (textlength <= jsonObject.getString("CustomeName").length) {
                        var searchValue = edtSearch!!.text.toString().toLowerCase().trim()
                        Log.e(TAG,"searchValue  20633  "+searchValue)
                        if (jsonObject.getString("LeadNo")!!.toLowerCase().trim().contains(searchValue) || jsonObject.getString("MobileNo")!!.toLowerCase().trim().contains(searchValue)
                            || jsonObject.getString("CustomeName")!!.toLowerCase().trim().contains(searchValue)){
                            projectLeadSortArrayList.put(jsonObject)
                        }

                   // }
                }

                if (projectLeadSortArrayList.length() <= 0){
                    recycSiteVisit!!.visibility = View.GONE
                    txt_nodata!!.visibility = View.VISIBLE
                }else{
                    recycSiteVisit!!.visibility = View.VISIBLE
                    txt_nodata!!.visibility = View.GONE
                }

                if (projectLeadSortArrayList.length() > 0){
                    val lLayout = GridLayoutManager(this@ProjectSiteVisitListActivity, 1)
                    recycSiteVisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                    val adapter = ProjectLeadListAdapter(this@ProjectSiteVisitListActivity, projectLeadSortArrayList)
                    recycSiteVisit!!.adapter = adapter
                    adapter.setClickListener(this@ProjectSiteVisitListActivity)
                }

            }
        })

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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
                                recycSiteVisit!!.visibility = View.VISIBLE
                                txt_nodata!!.visibility = View.GONE
                                if (projectLeadNoCount == 0){
                                    projectLeadNoCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1062   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectSiteVisitAssign")
                                        projectLeadArrayList = jobjt.getJSONArray("ProjectSiteVisitAssignList")

                                        projectLeadSortArrayList = JSONArray()
                                        for (k in 0 until projectLeadArrayList.length()) {
                                            val jsonObject = projectLeadArrayList.getJSONObject(k)
                                            // reportNamesort.put(k,jsonObject)
                                            projectLeadSortArrayList.put(jsonObject)
                                        }

                                        Log.e(TAG," 788   "+projectLeadSortArrayList)
                                        if (projectLeadSortArrayList.length() > 0){
                                            val lLayout = GridLayoutManager(this@ProjectSiteVisitListActivity, 1)
                                            recycSiteVisit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ProjectLeadListAdapter(this@ProjectSiteVisitListActivity, projectLeadSortArrayList)
                                            recycSiteVisit!!.adapter = adapter
                                            adapter.setClickListener(this@ProjectSiteVisitListActivity)
                                        }


                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        tv_header = findViewById<TextView>(R.id.tv_header)
        txt_nodata = findViewById<TextView>(R.id.txt_nodata)
        recycSiteVisit = findViewById<RecyclerView>(R.id.recycSiteVisit)
        edtSearch = findViewById<EditText>(R.id.edtSearch)

        imback!!.setOnClickListener(this)

        projectLeadArrayList = JSONArray()


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
            val jsonObject = projectLeadSortArrayList.getJSONObject(position)
            val i = Intent(this@ProjectSiteVisitListActivity, ProjectSiteVisitActivity::class.java)
            i.putExtra("mode",mode)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}