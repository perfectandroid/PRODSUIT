package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R

class InventoryActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = "InventoryActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    private var llstockrequest: LinearLayout? = null
    private var llstocktransfer: LinearLayout? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_inventory)
        context = this@InventoryActivity

        setRegViews()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        llstockrequest = findViewById<LinearLayout>(R.id.llstockrequest)
        llstocktransfer = findViewById<LinearLayout>(R.id.llstocktransfer)

        llstockrequest!!.setOnClickListener(this)
        llstocktransfer!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.llstockrequest->{
                val i = Intent(this@InventoryActivity, StockRequestActivity::class.java)
                startActivity(i)
            }
            R.id.llstocktransfer->{
                val i = Intent(this@InventoryActivity, StockTransferActivity::class.java)
                startActivity(i)
            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}