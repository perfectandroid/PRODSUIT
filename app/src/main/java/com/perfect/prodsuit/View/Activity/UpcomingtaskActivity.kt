package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.View.Adapter.UpcmngtaskListAdapter
import com.perfect.prodsuit.Viewmodel.UpcomingtasksListViewModel
import org.json.JSONArray
import org.json.JSONObject

class UpcomingtaskActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var upcomingtaskslistViewModel: UpcomingtasksListViewModel
    private var rv_upcmngtasklist: RecyclerView?=null
    lateinit var upcmngtaskArrayList : JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcomingtask)
        setRegViews()
        getUpcomingtasksList()
    }

    private fun setRegViews() {
        rv_upcmngtasklist = findViewById(R.id.rv_upcmngtasklist)
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    private fun getUpcomingtasksList() {
        context = this@UpcomingtaskActivity
        upcomingtaskslistViewModel = ViewModelProvider(this).get(UpcomingtasksListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                upcomingtaskslistViewModel.getUpcomingtasklist(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                    upcmngtaskArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                    val lLayout = GridLayoutManager(this@UpcomingtaskActivity, 1)
                                    rv_upcmngtasklist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_upcmngtasklist!!.setHasFixedSize(true)
                                    val adapter = TodoListAdapter(applicationContext, upcmngtaskArrayList)
                                    rv_upcmngtasklist!!.adapter = adapter
                                    adapter.setClickListener(this@UpcomingtaskActivity)

                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@UpcomingtaskActivity,
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
                                Toast.makeText(
                                        applicationContext,
                                        "Some Technical Issues.",
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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("upcoming")){
            val jsonObject = upcmngtaskArrayList.getJSONObject(position)
            val i = Intent(this@UpcomingtaskActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }
    }

}