package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.perfect.prodsuit.R

class ServiceAssignTabActivity : AppCompatActivity()  , View.OnClickListener{

    var TAG  ="ServiceAssignTabActivity"
    lateinit var context: Context

    private var card_new: CardView? = null
    private var card_ongoing: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_service_assign_tab)
        context = this@ServiceAssignTabActivity
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        card_new = findViewById<CardView>(R.id.card_new)
        card_ongoing = findViewById<CardView>(R.id.card_ongoing)

        card_new!!.setOnClickListener(this)
        card_ongoing!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.card_new->{
                val i = Intent(this@ServiceAssignTabActivity, ServiceAssignListActivity::class.java)
                startActivity(i)
            }
            R.id.card_ongoing->{

            }
        }
    }
}