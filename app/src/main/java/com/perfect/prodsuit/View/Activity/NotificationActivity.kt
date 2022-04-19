package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.perfect.prodsuit.View.Adapter.NotificationAdapter
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.NotificationViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject

class NotificationActivity : AppCompatActivity(), View.OnClickListener{
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var notificationViewModel: NotificationViewModel
    private var rv_notificationlist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        setRegViews()
        getNotificationList()
    }
    companion object {
        var count= ""
    }
    private fun setRegViews() {
        rv_notificationlist = findViewById(R.id.rv_notificationlist)
        val imback = findViewById<ImageView>(R.id.imback)

        imback!!.setOnClickListener(this)
    }

    private fun getNotificationList() {

        context = this@NotificationActivity
        notificationViewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                notificationViewModel.getNotificaationlist(this)!!.observe(
                        this,
                        Observer { notificationSetterGetter ->
                            val msg = notificationSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)

                                if (jObject.getString("StatusCode") == "0") {
                                   val jobjt = jObject.getJSONObject("NotificationDetails")
                                    todoArrayList = jobjt.getJSONArray("NotificationInfo")
                                    count = todoArrayList.length().toString();
                                    Log.i("Array size", count)

                                   // var jobj = jObject.getJSONObject("UserLoginDetails")
                                    val lLayout = GridLayoutManager(this@NotificationActivity, 1)
                                    rv_notificationlist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_notificationlist!!.setHasFixedSize(true)
                                    val adapter = NotificationAdapter(applicationContext, todoArrayList)
                                    rv_notificationlist!!.adapter = adapter



                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@NotificationActivity,
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
