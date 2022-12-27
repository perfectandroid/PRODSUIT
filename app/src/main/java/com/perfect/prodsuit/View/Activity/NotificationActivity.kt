package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.NotificationAdapter
import com.perfect.prodsuit.Viewmodel.NotificationReadStatusViewModel
import com.perfect.prodsuit.Viewmodel.NotificationViewModel
import org.json.JSONArray
import org.json.JSONObject

class NotificationActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var notificationViewModel: NotificationViewModel
    lateinit var notificationReadStatusViewModel: NotificationReadStatusViewModel
    private var rv_notificationlist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray
    lateinit var notifreadArrayList : JSONArray
    var notifAdapter: NotificationAdapter? = null
    var notificationDet = 0
    var notificationStart = 0
    var notificationCount = 1
    val UPDATE_INTERVAL = 2000L
    internal var txtv_notfcount: TextView? = null
    private val updateWidgetHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_notification)
        setRegViews()
        notificationDet = 0
        getNotificationList()

    }
    companion object {
        var count= ""
        var id= ""
        lateinit var notifreadstatusmodel: NotificationReadStatusViewModel
    }
    private fun setRegViews() {
        rv_notificationlist = findViewById(R.id.rv_notificationlist)
        txtv_notfcount= findViewById(R.id.txtv_notfcount)
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        notificationReadStatusViewModel = ViewModelProvider(this).get(NotificationReadStatusViewModel::class.java)
    }

    private var updateWidgetRunnable: Runnable = Runnable {
        run {
            //Update UI
            // Re-run it after the update interval
            notificationDet = 0
            getNotificationList()
            updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
        }

    }

    override fun onResume() {
        super.onResume()
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
    }


    // REMOVE callback if app in background
    override fun onPause() {
        super.onPause()
        updateWidgetHandler.removeCallbacks(updateWidgetRunnable);
    }

    private fun getNotificationList() {
        context = this@NotificationActivity
        notificationViewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                if (notificationStart == 0){
                    progressDialog = ProgressDialog(this, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                }
                notificationViewModel.getNotificaationlist(this)!!.observe(
                        this,
                        Observer { notificationSetterGetter ->
                            val msg = notificationSetterGetter.message
                            try {
                                if (msg!!.length > 0) {
                                    if (notificationStart == 0){
                                        notificationStart++
                                        progressDialog!!.dismiss()
                                    }

                                    if (notificationDet == 0){
                                        notificationDet++
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            val jobjt = jObject.getJSONObject("NotificationDetails")
                                            todoArrayList = jobjt.getJSONArray("NotificationInfo")
                                            notificationCount = 0
                                            count = todoArrayList.length().toString();
                                            txtv_notfcount!!.text=count
                                            Log.i("Array size", count)

                                            // var jobj = jObject.getJSONObject("UserLoginDetails")
                                            val lLayout = GridLayoutManager(this@NotificationActivity, 1)
                                            rv_notificationlist!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            rv_notificationlist!!.setHasFixedSize(true)
                                            val adapter = NotificationAdapter(applicationContext, todoArrayList)
                                            rv_notificationlist!!.adapter = adapter
                                            adapter.setClickListener(this@NotificationActivity)


                                        } else {
                                            txtv_notfcount!!.text="0"
                                            rv_notificationlist!!.adapter = null
                                            if (notificationCount > 0){
                                                notificationCount = 0
                                                val builder = AlertDialog.Builder(
                                                    this@NotificationActivity,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage(jObject.getString("EXMessage"))
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                     onBackPressed()
                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()
                                            }


                                        }
                                    }

                                } else {

//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
                                }
                            }
                            catch (e: Exception){

                            }

                        })
//                progressDialog!!.dismiss()
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
        Log.i("Data",data+"\n"+position)
        if (data.equals("readstatus")){
            val jsonObject = todoArrayList.getJSONObject(position)
           // Log.e("TAG","ID_Category   "+jsonObject.getString("ID_NotificationDetails"))
            id = jsonObject.getString("ID_Notification")
            Log.i("IDd",id)

            getReadstatus()
            try {

                bottomSheetMessage(jsonObject)

//                val dialog1 = Dialog(this)
//                dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
//                dialog1 .setCancelable(false)
//                dialog1 .setContentView(R.layout.notf_popup)
//                dialog1.window!!.attributes.gravity = Gravity.CENTER_HORIZONTAL;
//                val txtv_notifdate = dialog1 .findViewById(R.id.txtv_notifdate) as TextView
//                val txt_msg = dialog1 .findViewById(R.id.txt_msg) as TextView
//               // val txt_dtls = dialog1 .findViewById(R.id.txt_dtls) as TextView
//                val btnCancel = dialog1 .findViewById(R.id.btnCancel) as ImageButton
//                val rl_main1= dialog1 .findViewById(R.id.rl_main1) as LinearLayout
//
//                txtv_notifdate.text=jsonObject.getString("NoticeDate");
//                txt_msg.text=jsonObject.getString("NotificationMessage");
//               // txt_dtls.text=jsonObject.getString("NoticeDate");
//
//                btnCancel.setOnClickListener {
//                    dialog1 .dismiss()
//                }
//
//                dialog1.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun getReadstatus() {
        var notifstatus = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                notificationReadStatusViewModel.getNotifreadstatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            progressDialog!!.dismiss()
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e("TAG","msg   227   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    //  startActivity(getIntent());
//                                    notificationDet = 0
//                                    getNotificationList()
                                } else {
//                                Toast.makeText(applicationContext, ""+jObject.getString("EXMessage"), Toast.LENGTH_LONG)
//                                    .show()
//                                val builder = AlertDialog.Builder(
//                                    this@NotificationActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
                                }
                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e: Exception){
                         //   progressDialog!!.dismiss()
                        }

                    })

            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    private fun bottomSheetMessage(jsonObject :  JSONObject) {

        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottomsheet_messages, null)

            val imgClose = view.findViewById<ImageView>(R.id.imgClose)
            val tv_subject = view.findViewById<TextView>(R.id.tv_subject)
            val tv_description = view.findViewById<TextView>(R.id.tv_description)

            tv_subject.text = jsonObject.getString("Title")
            tv_description.text = jsonObject.getString("Message")

            imgClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()
        }catch (e: Exception){

        }


    }
}
