package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AreaListRepository.progressDialog
import com.perfect.prodsuit.View.Adapter.ServiceFollowUpMainProductAdapter

import com.perfect.prodsuit.Viewmodel.ServiceFollowUpInfoViewModel
import com.perfect.prodsuit.Viewmodel.SubProductViewModel
import org.json.JSONArray
import org.json.JSONObject

class ServiceFollowupGroupByActivity : AppCompatActivity(), ItemClickListener {
    var TAG = "ServiceFollowupGroupByActivity"
    private var recy_main_product                       : RecyclerView?       = null
    lateinit var context                                : Context
    lateinit var subProductViewModel                    : SubProductViewModel
    var SubproductDetails                               : JSONObject          = JSONObject()
    var ProductSubDetails                               = JSONArray()
    var mainProductInfo                                 = 0

    lateinit var SubproductDetailsList : JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupby_service_followup)
        context = this@ServiceFollowupGroupByActivity
        subProductViewModel = ViewModelProvider(this).get(SubProductViewModel::class.java)
        setRegviews()
    }

    private fun setRegviews() {
        recy_main_product      = findViewById(R.id.recy_main_product)

//.............
//        var ProductSubDetails = JSONArray()

        var jObject = JSONObject()

        jObject.put("FK_Product",  "557")
        jObject.put("ReplacementWarrantyExpireDate",  "")


       ProductSubDetails.put(jObject)

        jObject = JSONObject()

        jObject.put("FK_Product",  "578")
        jObject.put("ReplacementWarrantyExpireDate",  "")
        ProductSubDetails.put(jObject)

        loadMainProduct()
    }

    private fun loadMainProduct() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        Log.i("resp900","ID_Employee==   "+ID_Employee)

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                subProductViewModel.getSubProduct(this,ID_Employee!!,ProductSubDetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.i("resp900","msg=="+msg)

                                if (mainProductInfo == 0) {
                                    mainProductInfo++

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0")
                                    {
                                      //  SubproductDetails=jObject.getJSONObject("SubproductDetails")
                                        val jobjt = jObject.getJSONObject("SubproductDetails")
                                     //   Log.i("resp900","SubproductDetails=="+SubproductDetails)
                                        SubproductDetailsList = jobjt.getJSONArray("SubproductDetailsList")
                                        Log.i("resp900","SubproductDetailsList=="+SubproductDetailsList)

                                        val lLayout = LinearLayoutManager(this@ServiceFollowupGroupByActivity,LinearLayoutManager.HORIZONTAL,false)
                                        recy_main_product!!.layoutManager = lLayout as RecyclerView.LayoutManager?

//                                        val adapter = ServiceFollowUpMainProductAdapter(this@ServiceFollowupGroupByActivity, SubproductDetailsList!!)
//                                        recy_main_product!!.adapter = adapter
//                                        adapter.setClickListener(this@ServiceFollowupGroupByActivity)
                                    }
                                    else
                                    {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowupGroupByActivity,
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

                            Log.v("fsfsfds", "ex3 " + e)
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



    override fun onClick(position: Int, data: String) {




        if (data.equals("MainProductList"))
        {
            Log.i("resp4545","main")
        }









    }
}