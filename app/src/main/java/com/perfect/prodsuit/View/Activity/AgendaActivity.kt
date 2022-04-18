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
import com.google.android.material.tabs.TabLayout
import com.perfect.prodsuit.R

class AgendaActivity : AppCompatActivity() , View.OnClickListener {

    val TAG : String = "AgendaActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var tabLayout : TabLayout? = null

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
    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("PENDING"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("UPCOMING"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("COMPLETED"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE






        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//                    llMainDetail!!.removeAllViews()
//                    val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//                    val inflatedLayout: View = inflater.inflate(R.layout.activity_subinfo, null, false)
//                    llMainDetail!!.addView(inflatedLayout);


                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
//                    llMainDetail!!.removeAllViews()



                }
                if (tab.position == 2){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)

//                    llMainDetail!!.removeAllViews()
//                    val inflater = LayoutInflater.from(this@AccountDetailsActivity)
//                    val inflatedLayout: View = inflater.inflate(R.layout.activity_subnote, null, false)
//                    llMainDetail!!.addView(inflatedLayout);


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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }
}