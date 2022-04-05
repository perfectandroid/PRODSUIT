package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
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
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.OverdueListAdapter
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.OverDueListViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject

class OverDueActivity : AppCompatActivity(), View.OnClickListener {
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var overduelistViewModel: OverDueListViewModel
    private var rv_overduelist: RecyclerView?=null
    lateinit var overdueArrayList : JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overdue)
        setRegViews()
        getOverdueList()
    }

    private fun setRegViews() {
        rv_overduelist = findViewById(R.id.rv_overduelist)
        val imback = findViewById<ImageView>(R.id.imback)

        imback!!.setOnClickListener(this)
    }

    private fun getOverdueList() {

        context = this@OverDueActivity
        overduelistViewModel = ViewModelProvider(this).get(OverDueListViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                overduelistViewModel.getOverduelist(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                 //   var jobj = jObject.getJSONObject("UserLoginDetails")

                                    val lLayout = GridLayoutManager(this@OverDueActivity, 1)
                                    rv_overduelist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_overduelist!!.setHasFixedSize(true)
                                    val adapter = OverdueListAdapter(applicationContext, overdueArrayList)
                                    rv_overduelist!!.adapter = adapter

                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@OverDueActivity,
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
}