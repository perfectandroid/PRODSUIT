package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AgendaActionTypeAdapter
import com.perfect.prodsuit.View.Adapter.MediaTypeAdapter
import com.perfect.prodsuit.Viewmodel.AgendaActionViewModel
import com.perfect.prodsuit.Viewmodel.AgendaCountViewModel
import org.json.JSONArray
import org.json.JSONObject

class AgendaActivity : AppCompatActivity() , View.OnClickListener  , ItemClickListener {

    val TAG : String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

//    private var tabLayout : TabLayout? = null
//    var llMainDetail: LinearLayout? = null
//    var llPending: LinearLayout? = null
//    var llUpComing: LinearLayout? = null
//    var llComplete: LinearLayout? = null

    var tv_today_comp: TextView? = null
    var tv_today_count: TextView? = null

    var tv_count_pending: TextView? = null
    var tv_count_upcoming: TextView? = null
    var tv_count_complete: TextView? = null

    var tv_tab_pending: TextView? = null
    var tv_tab_upcoming: TextView? = null
    var tv_tab_completed: TextView? = null
    var tv_actionType: TextView? = null
    var agendaTypeClick : String?= "0"
    var ID_ActionType : String?= ""

    lateinit var agendaCountViewModel: AgendaCountViewModel
    lateinit var agendaActionViewModel: AgendaActionViewModel
    lateinit var agendaActionArrayList : JSONArray
    var dialogAgendaAction : Dialog? = null
    var recyActionType: RecyclerView? = null




    var txtSelectPend: TextView? = null

  //  var inflaterPend : LayoutInflater? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_agenda)

        context = this@AgendaActivity
        agendaCountViewModel = ViewModelProvider(this).get(AgendaCountViewModel::class.java)
        agendaActionViewModel = ViewModelProvider(this).get(AgendaActionViewModel::class.java)

        setRegViews()
      //  addTabItem()

        getAgendaCounts()
        getActionTypes()

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

//        tabLayout = findViewById(R.id.tabLayout);
//        llMainDetail = findViewById(R.id.llMainDetail);
//        llPending = findViewById(R.id.llPending);
//        llUpComing = findViewById(R.id.llUpComing);
//        llComplete = findViewById(R.id.llComplete);

        tv_today_comp = findViewById(R.id.tv_today_comp);
        tv_today_count = findViewById(R.id.tv_today_count);

        tv_count_pending = findViewById(R.id.tv_count_pending);
        tv_count_upcoming = findViewById(R.id.tv_count_upcoming);
        tv_count_complete = findViewById(R.id.tv_count_complete);

        tv_tab_pending = findViewById(R.id.tv_tab_pending);
        tv_tab_upcoming = findViewById(R.id.tv_tab_upcoming);
        tv_tab_completed = findViewById(R.id.tv_tab_completed);
        tv_actionType = findViewById(R.id.tv_actionType);

        tv_tab_pending!!.setOnClickListener(this)
        tv_tab_upcoming!!.setOnClickListener(this)
        tv_tab_completed!!.setOnClickListener(this)
        tv_actionType!!.setOnClickListener(this)



    }

//    private fun addTabItem() {
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("PENDING"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("UPCOMING"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("COMPLETED"))
//        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
//
//
//
//        PendingTab()
//
//
//
//
//        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabSelected  113  "+tab.position)
//                if (tab.position == 0){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//
//                    PendingTab()
//
//                }
//                if (tab.position == 1){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//                    UpComingTab()
//
//                }
//                if (tab.position == 2){
//                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//
//                    CompleteTab()
//
//                }
//
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabUnselected  162  "+tab.position)
//
//            }
//            override fun onTabReselected(tab: TabLayout.Tab) {
//                Log.e(TAG,"onTabReselected  165  "+tab.position)
//            }
//        })
//
//    }
//
//    private fun PendingTab() {
//        llMainDetail!!.removeAllViews()
//        val inflaterPend = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayoutPend: View = inflaterPend!!.inflate(R.layout.activity_subpending, null, false)
//        llMainDetail!!.addView(inflatedLayoutPend);
//
//        txtSelectPend = inflatedLayoutPend.findViewById<TextView>(R.id.txtSelectPend)
//        txtSelectPend!!.setOnClickListener(this)
//
//
//    }
//
//    private fun UpComingTab() {
//        llMainDetail!!.removeAllViews()
//        val inflater = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_subupcoming, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//    }
//
//    private fun CompleteTab() {
//        llMainDetail!!.removeAllViews()
//        val inflater = LayoutInflater.from(this@AgendaActivity)
//        val inflatedLayout: View = inflater.inflate(R.layout.activity_subcomplete, null, false)
//        llMainDetail!!.addView(inflatedLayout);
//    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.txtSelectPend->{
                Log.e(TAG,"txtSelectPend  232   ")
            }
            R.id.tv_tab_pending->{
                Log.e(TAG,"tv_tab_pending  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                getActionTypes()
            }
            R.id.tv_tab_upcoming->{
                Log.e(TAG,"tv_tab_upcoming  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_color);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_trans);
                agendaTypeClick = "0"
                getActionTypes()
            }
            R.id.tv_tab_completed->{
                Log.e(TAG,"tv_tab_completed  232   ")
                tv_tab_pending!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_upcoming!!.setBackgroundResource(R.drawable.under_line_trans);
                tv_tab_completed!!.setBackgroundResource(R.drawable.under_line_color);
                agendaTypeClick = "0"
                getActionTypes()
            }
            R.id.tv_actionType->{
                Log.e(TAG,"tv_actionType  232   ")
                agendaTypeClick = "1"
                getActionTypes()

            }
        }
    }

    private fun getAgendaCounts() {
        var countAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaCountViewModel.getAgendaCount(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   167   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("PendingCountDetails")
                                tv_count_pending!!.setText(jobjt.getString("Pending"))
                                tv_count_upcoming!!.setText(jobjt.getString("Upcoming"))
                                tv_count_complete!!.setText(jobjt.getString("Completed"))

                                tv_today_comp!!.setText(jobjt.getString("TodayCompletedCount"))
                                tv_today_count!!.setText("out of " +jobjt.getString("TodayCount")+ " activity completed today")
//
//                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
//                                if (leadFromArrayList.length()>0){
//                                    if (countLeadFrom == 0){
//                                        countLeadFrom++
//                                        leadFromPopup(leadFromArrayList)
//                                    }
//
//                                }

                            } else {

                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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

    private fun getActionTypes() {
        var agendaAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                agendaActionViewModel.getAgendaAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   284   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("ActionType")
                                agendaActionArrayList = jobjt.getJSONArray("ActionTypeList")
                                if (agendaActionArrayList.length()>0){
                                    if (agendaAction == 0){
                                        agendaAction++
                                        agendaTypePopup(agendaActionArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@AgendaActivity,
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

    private fun agendaTypePopup(agendaActionArrayList: JSONArray) {

        if (agendaTypeClick.equals("0")){
            val jsonObject = agendaActionArrayList.getJSONObject(0)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))
        }else{

            try {

                dialogAgendaAction = Dialog(this)
                dialogAgendaAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogAgendaAction!! .setContentView(R.layout.agenda_action_popup)
                dialogAgendaAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
                recyActionType = dialogAgendaAction!! .findViewById(R.id.recyActionType) as RecyclerView

                val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
                val adapter = AgendaActionTypeAdapter(this@AgendaActivity, agendaActionArrayList)
                recyActionType!!.adapter = adapter
                adapter.setClickListener(this@AgendaActivity)

                dialogAgendaAction!!.show()
                dialogAgendaAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("agendaactiontype")){
            dialogAgendaAction!!.dismiss()
            val jsonObject = agendaActionArrayList.getJSONObject(position)
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tv_actionType!!.setText(jsonObject.getString("ActionTypeName"))
        }
    }

}