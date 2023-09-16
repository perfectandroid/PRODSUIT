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
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CorrectionSplitupAdapter
import com.perfect.prodsuit.Viewmodel.CorrectionSplitupViewModel
import org.json.JSONArray
import org.json.JSONObject

class CorrectionSplitupActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG = "CorrectionSplitupActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var correctionCount: Int = 0
    lateinit var correctionSplitupViewModel: CorrectionSplitupViewModel
    lateinit var correctionSplitupArrayList: JSONArray
    var recycCorrSplit: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_correction_splitup)

        context = this@CorrectionSplitupActivity
        correctionSplitupViewModel = ViewModelProvider(this).get(CorrectionSplitupViewModel::class.java)

        setRegViews()

        correctionCount = 0
        getCorrectionSplitData()
    }

    private fun getCorrectionSplitData() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                correctionSplitupViewModel.getCorrectionSplitup(this,"")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (correctionCount == 0){
                                correctionCount++
                                Log.e(TAG,"msg   4060   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                  //  successPopup(jObject)

                                    val jobjt = jObject.getJSONObject("ExistCustomerDetails")
                                    correctionSplitupArrayList = jobjt.getJSONArray("ExistCustomerDetailList")

                                    if (correctionSplitupArrayList.length() > 0){

                                    val lLayout = GridLayoutManager(this@CorrectionSplitupActivity, 1)
                                    recycCorrSplit!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    recycCorrSplit!!.setHasFixedSize(true)
                                    var adapter = CorrectionSplitupAdapter(applicationContext, correctionSplitupArrayList)
                                    recycCorrSplit!!.adapter = adapter
                                    adapter!!.setClickListener(this@CorrectionSplitupActivity)
                                    }


                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@CorrectionSplitupActivity,
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

        recycCorrSplit = findViewById<RecyclerView>(R.id.recycCorrSplit)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("correctsplitClick")){
            Log.e(TAG,"1300  "+position)

            val i = Intent(this@CorrectionSplitupActivity, LeadCorrectionActivity::class.java)
            startActivity(i)
        }
    }


}