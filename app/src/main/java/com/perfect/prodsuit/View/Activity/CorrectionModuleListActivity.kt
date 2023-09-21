package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CorrectionModuleListAdapter
import com.perfect.prodsuit.View.Adapter.ProductPriorityAdapter
import com.perfect.prodsuit.View.Adapter.ServicePriorityAdapter
import com.perfect.prodsuit.Viewmodel.CorrectionModuleListViewModel
import com.perfect.prodsuit.Viewmodel.ReportNameViewModel
import com.perfect.prodsuit.Viewmodel.ServicePriorityViewModel
import org.json.JSONArray
import org.json.JSONObject

class CorrectionModuleListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var recycorrectionmodules: RecyclerView? = null
    val TAG : String = "CorrectionModuleListActivity"
    private var progressDialog: ProgressDialog? = null
    private var imback: ImageView? = null
    lateinit var context: Context
    lateinit var correctionmodulelistViewModel: CorrectionModuleListViewModel
    lateinit var correctionmodulelistarray : JSONArray


    var correctionmodulecount = 0
    var jsonObj: JSONObject? = null
    var Module_sub = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correction_module_list)

        context = this@CorrectionModuleListActivity
        correctionmodulelistViewModel = ViewModelProvider(this).get(CorrectionModuleListViewModel::class.java)


//        var jsonObject: String? = intent.getStringExtra("jsonObject")
//        jsonObj = JSONObject(jsonObject)
//        Module = jsonObj!!.getString("Module")
        correctionmodulecount = 0
        getCorrectionModuleList()
        setRegViews()
    }

    private fun setRegViews(){

        recycorrectionmodules = findViewById(R.id.recycorrectionmodules)
        imback = findViewById(R.id.imback)

        imback!!.setOnClickListener(this)
    }

    private fun getCorrectionModuleList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                correctionmodulelistViewModel.getCorrectionModuleList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (correctionmodulecount == 0){
                                    correctionmodulecount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   665   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AuthorizationCorrectionModuleList")
                                        correctionmodulelistarray = jobjt.getJSONArray("AuthorizationModuleCorrectionDetails")
                                        if (correctionmodulelistarray.length()>0){

                                            val lLayout = GridLayoutManager(this@CorrectionModuleListActivity, 1)
                                            recycorrectionmodules!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            recycorrectionmodules!!.setHasFixedSize(true)
                                            val adapter = CorrectionModuleListAdapter(this@CorrectionModuleListActivity, correctionmodulelistarray)
                                            recycorrectionmodules!!.adapter = adapter
                                            adapter.setClickListener(this@CorrectionModuleListActivity)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@CorrectionModuleListActivity,
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("moduleListClick")) {
            Log.e(TAG, "14001  " + position)

            val jsonObject = correctionmodulelistarray.getJSONObject(position)
            Log.e(TAG, "15522  " + correctionmodulelistarray)

            if (jsonObject.getString("Module_Name").equals("Lead")) {

                if (jsonObject.getInt("NoofRecords") == 1){

                    val i = Intent(this@CorrectionModuleListActivity, LeadCorrectionActivity::class.java)
                    i.putExtra("jsonObject",jsonObject.toString())
                    i.putExtra("Module_sub",Module_sub)
                    startActivity(i)

                }else if (jsonObject.getInt("NoofRecords") > 1){

                    val i = Intent(this@CorrectionModuleListActivity, CorrectionSplitupActivity::class.java)
                    i.putExtra("jsonObject",jsonObject.toString())
                    i.putExtra("Module_sub",Module_sub)
                    Log.e(TAG,"Module 4451   "+Module_sub)
                    Log.e(TAG, "552244  " + jsonObject.getString("FK_TransMaster"))
                    startActivity(i)
                }

            }else{
                Toast.makeText(applicationContext, "Work in Progress", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        correctionmodulecount = 0
        getCorrectionModuleList()
//        Config.setRedirection(context,"")
    }
}