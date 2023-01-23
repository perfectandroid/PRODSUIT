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
import com.perfect.prodsuit.View.Adapter.EmiListAdapter
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.EmiListViewModel
import org.json.JSONArray
import org.json.JSONObject

class EmiToDoListActivity : AppCompatActivity(), View.OnClickListener  {

    val TAG: String = "EmiToDoListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var emiListViewModel: EmiListViewModel
    lateinit var emiListArrayList: JSONArray
    var recyEmiList: RecyclerView? = null

    var emiCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi_to_do_list)
        context = this@EmiToDoListActivity
        emiListViewModel = ViewModelProvider(this).get(EmiListViewModel::class.java)

        setRegViews()
        emiCount = 0
        getEmiList()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyEmiList = findViewById<RecyclerView>(R.id.recyEmiList)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }

    private fun getEmiList() {
        recyEmiList!!.adapter = null
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiListViewModel.getEmiList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (emiCount == 0) {
                                    emiCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        emiListArrayList = jobjt.getJSONArray("CategoryList")
                                        if (emiListArrayList.length() > 0) {

                                            val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
                                            recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListArrayList!!)
                                            recyEmiList!!.adapter = adapter
                                          //  adapter.setClickListener(this@EmiToDoListActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiToDoListActivity,
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
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
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
}