package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.perfect.prodsuit.R

class EmiActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = "EmiActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    internal var ll_todo: LinearLayout? = null
    internal var ll_overdue: LinearLayout? = null
    internal var ll_upcoming: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi)
        context = this@EmiActivity

        setRegViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        ll_todo = findViewById<LinearLayout>(R.id.ll_todo)
        ll_overdue = findViewById<LinearLayout>(R.id.ll_overdue)
        ll_upcoming = findViewById<LinearLayout>(R.id.ll_upcoming)

        ll_todo!!.setOnClickListener(this)
        ll_overdue!!.setOnClickListener(this)
        ll_upcoming!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.ll_todo->{
                val i = Intent(this@EmiActivity, EmiToDoListActivity::class.java)
                startActivity(i)
            }
            R.id.ll_overdue->{

            }
            R.id.ll_upcoming->{

            }
        }
    }

}