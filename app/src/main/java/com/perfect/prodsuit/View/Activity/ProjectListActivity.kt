package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProjectListAdapter
import com.perfect.prodsuit.Viewmodel.MaterialUsageProjectViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProjectListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectListActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    lateinit var materialusageProjectViewModel: MaterialUsageProjectViewModel
    var projectcount = 0
    lateinit var projectArrayList  : JSONArray
    lateinit var projectArrayMain  : JSONArray
    private var recycProject       : RecyclerView?   = null
    private var tv_notfound        : TextView?   = null
    private var etsearch       : EditText?   = null
    private var clearIcon       : ImageView?   = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_list)
        context = this@ProjectListActivity

        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)

        setRegViews()

        projectcount = 0
        getProject()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        recycProject = findViewById<RecyclerView>(R.id.recycProject)
        tv_notfound = findViewById<TextView>(R.id.tv_notfound)
        etsearch = findViewById<EditText>(R.id.etsearch)
        clearIcon = findViewById<ImageView>(R.id.clearIcon)
        imback!!.setOnClickListener(this)
        clearIcon!!.setOnClickListener(this)

    }



    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.clearIcon->{
                etsearch!!.setText("")
            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"741  onRestart ")
        projectcount = 0
        getProject()
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("materialUsageClick")){
            val jsonObject = projectArrayList.getJSONObject(position)
            val i = Intent(this@ProjectListActivity, MaterialUsageActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)

        }
        if (data.equals("materialRequestClick")){
            val jsonObject = projectArrayList.getJSONObject(position)
            val i = Intent(this@ProjectListActivity, MaterialRequestActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)

        }
        if (data.equals("projectFollowupClick")){
            val jsonObject = projectArrayList.getJSONObject(position)
            val i = Intent(this@ProjectListActivity, ProjectFollowUpActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
        if (data.equals("materialTransactionClick")){
            val jsonObject = projectArrayList.getJSONObject(position)
            val i = Intent(this@ProjectListActivity, ProjectTransactionActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }

    private fun getProject() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (projectcount == 0){
                                    projectcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1144551   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectList")
                                        projectArrayMain = jobjt.getJSONArray("ProjectListDetails")
                                        Log.e(TAG,"projectArrayList   1144552   "+projectArrayMain.length())
                                        if (projectArrayMain.length()>0){

                                            projectArrayList = JSONArray()
                                            for (k in 0 until projectArrayMain.length()) {
                                                val jsonObject = projectArrayMain.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                projectArrayList.put(jsonObject)
                                            }

                                            if (projectArrayList.length() == 0){
                                                tv_notfound!!.visibility = View.VISIBLE
                                                recycProject!!.visibility = View.GONE
                                            }else{
                                                tv_notfound!!.visibility = View.GONE
                                                recycProject!!.visibility = View.VISIBLE
                                                val lLayout = GridLayoutManager(this@ProjectListActivity, 1)
                                                recycProject!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = ProjectListAdapter(this@ProjectListActivity, projectArrayList,0)
                                                recycProject!!.adapter = adapter
                                                adapter.setClickListener(this@ProjectListActivity)
                                            }



                                            etsearch!!.addTextChangedListener(object : TextWatcher {
                                                override fun afterTextChanged(p0: Editable?) {
                                                }

                                                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                                }

                                                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                                                    //  list_view!!.setVisibility(View.VISIBLE)
                                                    val textlength = etsearch!!.text.length
                                                    projectArrayList = JSONArray()

                                                    for (k in 0 until projectArrayMain.length()) {
                                                        val jsonObject = projectArrayMain.getJSONObject(k)
                                                        if (textlength <= jsonObject.getString("ProjName").length) {
                                                            if (jsonObject.getString("ProjName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                                                projectArrayList.put(jsonObject)
                                                            }

                                                        }
                                                    }

                                                    if (projectArrayList.length() == 0){
                                                        tv_notfound!!.visibility = View.VISIBLE
                                                        recycProject!!.visibility = View.GONE
                                                    }else{
                                                        tv_notfound!!.visibility = View.GONE
                                                        recycProject!!.visibility = View.VISIBLE
                                                        Log.e(TAG,"projectSort               7103    "+projectArrayList)
                                                        val adapter = ProjectListAdapter(this@ProjectListActivity, projectArrayList,0)
                                                        recycProject!!.adapter = adapter
                                                        adapter.setClickListener(this@ProjectListActivity)
                                                    }


                                                }
                                            })

                                        }
                                    } else {
//                                        val builder = AlertDialog.Builder(
//                                            this@ProjectListActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()

                                        tv_notfound!!.visibility = View.VISIBLE
                                        recycProject!!.visibility = View.GONE
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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




}