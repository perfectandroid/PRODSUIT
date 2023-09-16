package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.CorrectionLeadModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CorrectionProductAdapter
import com.perfect.prodsuit.Viewmodel.CorrectionLeadViewModel
import com.perfect.prodsuit.Viewmodel.PickUpDeliveryUpdateDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject

class LeadCorrectionActivity : AppCompatActivity(),View.OnClickListener, ItemClickListener {

    var TAG = "LeadCorrectionActivity"
    var recycle_productlist                : RecyclerView? = null
    private var imback                     : ImageView? = null
    private var tvv_leadno_1               : TextView? = null
    private var tvv_leadNo_2               : TextView? = null
    private var tvv_leadname_1             : TextView? = null
    private var tvv_leadName_2             : TextView? = null
    private var tvv_leadremark_1           : TextView? = null
    private var tvv_leadremark_2           : TextView? = null
    private var tvv_correctionPerson_1     : TextView? = null
    private var tvv_correctionPerson_2     : TextView? = null
    private var progressDialog             : ProgressDialog? = null
    lateinit var context                   : Context
    lateinit var correctionLeadViewModel   : CorrectionLeadViewModel
    lateinit var correctionleadArrayList   : JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lead_correction)


        correctionLeadViewModel = ViewModelProvider(this).get(CorrectionLeadViewModel::class.java)
        setRegViews()
    }

    private fun setRegViews(){

        imback                    = findViewById(R.id.imback)
        tvv_leadno_1              = findViewById(R.id.tvv_leadno_1)
        tvv_leadNo_2              = findViewById(R.id.tvv_leadNo_2)
        tvv_leadname_1            = findViewById(R.id.tvv_leadname_1)
        tvv_leadName_2            = findViewById(R.id.tvv_leadName_2)
        tvv_leadremark_1          = findViewById(R.id.tvv_leadremark_1)
        tvv_leadremark_2          = findViewById(R.id.tvv_leadremark_2)
        tvv_correctionPerson_1    = findViewById(R.id.tvv_correctionPerson_1)
        tvv_correctionPerson_2    = findViewById(R.id.tvv_correctionPerson_2)
        recycle_productlist       = findViewById(R.id.recycle_productlist)

        imback!!.setOnClickListener(this)

    }

    private fun getProductPriority() {
        var correctioncount = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                correctionLeadViewModel.getCorrectionLead(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   353   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                correctionleadArrayList = jobjt.getJSONArray("PriorityList")
                                if (correctionleadArrayList.length()>0){
                                    if (correctioncount == 0){
                                        correctioncount++
//                                        productPriorityPopup(prodPriorityArrayList)

                                        val lLayout = GridLayoutManager(this@LeadCorrectionActivity, 1)
                                        recycle_productlist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        val adapterHome = CorrectionProductAdapter(this@LeadCorrectionActivity,correctionleadArrayList)
                                        recycle_productlist!!.adapter = adapterHome
                                        adapterHome.setClickListener(this@LeadCorrectionActivity)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadCorrectionActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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
        TODO("Not yet implemented")
    }
}