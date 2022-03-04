package com.perfect.prodsuit.View.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.perfect.prodsuit.R

class ContactUsActivity : AppCompatActivity() , View.OnClickListener {

    private var et_name: EditText? = null
    private var et_subject: EditText? = null
    private var et_msg: EditText? = null
    private var imback: ImageView? = null
    private var btnOk: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        setRegViews()
    }

    private fun setRegViews() {

        et_name = findViewById<EditText>(R.id.et_name)
        et_subject = findViewById<EditText>(R.id.et_subject)
        et_msg = findViewById<EditText>(R.id.et_msg)
        imback = findViewById<ImageView>(R.id.imback)
        btnOk = findViewById<Button>(R.id.btnOk)

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

    private fun sendMail(subject: String, msg: String)
    {
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

}