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
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TrackerAdapter
import com.perfect.prodsuit.Viewmodel.CommonViewModel
import org.json.JSONArray
import org.json.JSONObject

class TrackerActivity : AppCompatActivity() , View.OnClickListener{
    var TAG  ="TrackerActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var CompcategoryDet = 0
    var ReqMode: String? = ""
    var SubMode: String? = ""

    lateinit var commonViewModel: CommonViewModel
    lateinit var compCategoryArrayList : JSONArray
    lateinit var compCategorySort: JSONArray
    var recyTracker: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_tracker)
        context = this@TrackerActivity

        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)

        setRegViews()

        CompcategoryDet = 0
        ReqMode = "66"
        SubMode = "20"
        getCompCategory(ReqMode!!,SubMode!!)
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyTracker = findViewById<RecyclerView>(R.id.recyTracker)
        recyTracker!!.adapter = null
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    private fun getCompCategory(ReqMode : String,SubMode : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                commonViewModel.getCommonViewModel(this,ReqMode,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (CompcategoryDet == 0){
                                    CompcategoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1278   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonPopupDetails")
                                        compCategoryArrayList = jobjt.getJSONArray("CommonPopupList")
                                        if (compCategoryArrayList.length()>0){

                                            compCategorySort = JSONArray()
                                            for (k in 0 until compCategoryArrayList.length()) {
                                                val jsonObject = compCategoryArrayList.getJSONObject(k)
                                                compCategorySort.put(jsonObject)
                                            }

                                            val lLayout = GridLayoutManager(this@TrackerActivity, 1)
                                            recyTracker!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = TrackerAdapter(this@TrackerActivity, compCategorySort)
                                            recyTracker!!.adapter = adapter


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@TrackerActivity,
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
}