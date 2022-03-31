package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R

class ContactUsActivity : AppCompatActivity() , View.OnClickListener {

    private var chipNavigationBar: ChipNavigationBar? = null
    private var et_name: EditText? = null
    private var et_subject: EditText? = null
    private var et_msg: EditText? = null
    private var imback: ImageView? = null
    private var btnOk: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        setRegViews()
        bottombarnav()
    }

    private fun setRegViews() {
        et_name = findViewById(R.id.et_name)
        et_subject = findViewById(R.id.et_subject)
        et_msg = findViewById(R.id.et_msg)
        imback = findViewById(R.id.imback)
        btnOk = findViewById(R.id.btnOk)
        btnOk!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.btnOk->{
                validation()
                et_msg!!.setText("")
                et_name!!.setText("")
                et_subject!!.setText("")
            }
        }
    }

    private fun sendMail(subject: String, msg: String){
        val to = "pssappfeedback@gmail.com"
        val subject = subject
        val message = msg
        val intent = Intent(Intent.ACTION_SEND)
        val addressees = arrayOf(to)
        intent.putExtra(Intent.EXTRA_EMAIL, addressees)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.setType("message/rfc822")
        startActivity(Intent.createChooser(intent, "Send Email using:"))
    }

    private fun validation(){
        if (et_name!!.text.toString() == null || et_name!!.text.toString().isEmpty()) {
            et_name!!.setError("Please Enter Your Name.")
        }
        else if (et_subject!!.text.toString() == null || et_subject!!.text.toString().isEmpty()) {
            et_subject!!.setError("Please Enter Your Subject.")
        }
        else if (et_msg!!.text.toString() == null || et_msg!!.text.toString().isEmpty()) {
            et_msg!!.setError("Please Enter Your Message.")
        }else{
            sendMail("ProdSuit - "+et_subject!!.text.toString(),et_msg!!.text.toString()+"\n\n"+et_name!!.text.toString())
        }
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@ContactUsActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@ContactUsActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@ContactUsActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}