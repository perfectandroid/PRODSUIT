package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.perfect.prodsuit.R

class PickUpAndDeliveryActivity : AppCompatActivity() , View.OnClickListener{


    var TAG  ="PickUpAndDeliveryActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var ll_pickup: LinearLayout? = null
    private var ll_delivery: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery)

        context = this@PickUpAndDeliveryActivity
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        ll_pickup = findViewById(R.id.ll_pickup)
        ll_delivery = findViewById(R.id.ll_delivery)

        ll_pickup!!.setOnClickListener(this)
        ll_delivery!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.ll_pickup->{
                val i = Intent(this@PickUpAndDeliveryActivity, PickUpAndDeliveryListActivity::class.java)
                i.putExtra("SubMode","1")
                startActivity(i)
            }
            R.id.ll_delivery->{
                val i = Intent(this@PickUpAndDeliveryActivity, PickUpAndDeliveryListActivity::class.java)
                i.putExtra("SubMode","2")
                startActivity(i)
            }
        }
    }

}