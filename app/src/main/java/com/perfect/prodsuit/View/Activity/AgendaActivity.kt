package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.perfect.prodsuit.R

class AgendaActivity : AppCompatActivity() , View.OnClickListener {

    val TAG : String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var tabLayout : TabLayout? = null
    var llMainDetail: LinearLayout? = null
    var llPending: LinearLayout? = null
    var llUpComing: LinearLayout? = null
    var llComplete: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_agenda)

        context = this@AgendaActivity

        setRegViews()
        addTabItem()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tabLayout = findViewById(R.id.tabLayout);
        llMainDetail = findViewById(R.id.llMainDetail);
        llPending = findViewById(R.id.llPending);
        llUpComing = findViewById(R.id.llUpComing);
        llComplete = findViewById(R.id.llComplete);
    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("PENDING"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("UPCOMING"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("COMPLETED"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE



        PendingTab()


        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)

                    PendingTab()

                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    UpComingTab()

                }
                if (tab.position == 2){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)

                    CompleteTab()

                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)

            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })

    }

    private fun PendingTab() {
        llMainDetail!!.removeAllViews()
        val inflater = LayoutInflater.from(this@AgendaActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_subpending, null, false)
        llMainDetail!!.addView(inflatedLayout);
    }

    private fun UpComingTab() {
        llMainDetail!!.removeAllViews()
        val inflater = LayoutInflater.from(this@AgendaActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_subupcoming, null, false)
        llMainDetail!!.addView(inflatedLayout);
    }

    private fun CompleteTab() {
        llMainDetail!!.removeAllViews()
        val inflater = LayoutInflater.from(this@AgendaActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_subcomplete, null, false)
        llMainDetail!!.addView(inflatedLayout);
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }
}