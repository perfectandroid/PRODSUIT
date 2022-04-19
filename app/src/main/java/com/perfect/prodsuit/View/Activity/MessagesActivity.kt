package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R

class MessagesActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "MessagesActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    var rbIntimation: RadioButton? = null
    var rbReminder: RadioButton? = null
    var rbOther: RadioButton? = null
    var etMessage: EditText? = null
    var cbWhats: CheckBox? = null
    var cbEmail: CheckBox? = null
    var cbMessages: CheckBox? = null
    var btReset: Button? = null
    var btnSubmit: Button? = null
    companion object{
        var subjectStr : String = "Intimation"
        var messageStr : String = ""
        var Mobile     : String = ""
        var Mailid     : String = ""
        var chk_whats  :String  = "fasle"
        var chk_email  :String  = "fasle"
        var chk_text   :String  = "fasle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_messages)
        context = this@MessagesActivity
        setRegViews()
        removeData()
        Mobile = intent!!.getStringExtra("LgCusMobile").toString()
        Mailid = intent!!.getStringExtra("LgCusEmail").toString()
        rbIntimation!!.setOnClickListener {
            subjectStr = "Intimation"
        }
        rbReminder!!.setOnClickListener {
            subjectStr = "Reminder"
        }
        rbOther!!.setOnClickListener {
            subjectStr = "Others"
        }
        cbWhats!!.setOnClickListener {
            if (cbWhats!!.isChecked()){
                chk_whats = "true"
            }
            else{
                chk_whats = "false"
            }
        }
        cbEmail!!.setOnClickListener {
            if (cbEmail!!.isChecked()){
                chk_email = "true"
            }
            else{
                chk_email = "false"
            }
        }
        cbMessages!!.setOnClickListener {
            if (cbMessages!!.isChecked()){
                chk_text = "true"
            }
            else{
                chk_text = "false"
            }
        }
    }

    private fun removeData() {
        subjectStr = "Intimation"
        messageStr = ""
        chk_whats  = "false"
        chk_email  = "false"
        chk_text   = "false"
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        rbIntimation = findViewById<RadioButton>(R.id.rbIntimation)
        rbReminder = findViewById<RadioButton>(R.id.rbReminder)
        rbOther = findViewById<RadioButton>(R.id.rbOther)
        etMessage = findViewById<EditText>(R.id.etMessage)
        cbWhats = findViewById<CheckBox>(R.id.cbWhats)
        cbEmail = findViewById<CheckBox>(R.id.cbEmail)
        cbMessages = findViewById<CheckBox>(R.id.cbMessages)
        btReset = findViewById<Button>(R.id.btReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.btReset->{
            }
            R.id.btnSubmit->{
                Config.Utils.hideSoftKeyBoard(this,v)
                validation(v)
            }
        }
    }

    private fun validation(v : View) {
        messageStr = etMessage!!.text.toString()
        if(messageStr.trim().length<=0) {
            val snackbar: Snackbar = Snackbar.make(v, "Please Enter Message to Send.", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (!cbWhats!!.isChecked() && !cbEmail!!.isChecked() && !cbMessages!!.isChecked()){
            val snackbar: Snackbar = Snackbar.make(v, "Please select sending option", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
            Log.e(TAG, "NOT CHECKED")
        }else{
            val isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp")
            if (isWhatsappInstalled) {
                sendData1(
                    Mobile,
                    Mailid,
                    subjectStr,
                    messageStr,
                    chk_whats,
                    chk_email,
                    chk_text
                )
            } else {
                if (cbWhats!!.isChecked()){
                    val snackbar: Snackbar = Snackbar.make(v, "WhatsApp not Installed", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }
                else{
                    Log.e(
                        TAG,
                        "SEND MESSAGE   16692  " + Mobile + "  " + Mailid + "  " + subjectStr + "  " + messageStr + "  " + chk_whats + "  " + chk_email + "  " + chk_text
                    )
                    sendData1(
                        Mobile,
                        Mailid,
                        subjectStr,
                        messageStr,
                        chk_whats,
                        chk_email,
                        chk_text
                    )
                }
            }
        }
    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed = false
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            app_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            app_installed = false
        }
        return app_installed
    }

    private fun sendData1(
        mobile: String,
        mailid: String,
        subject: String,
        message: String,
        chkWhats: String,
        chkEmail: String,
        chkText: String
    ) {
        Log.e(TAG, "sendData 16693    " + mobile + "  " + mailid + "  " + subject + "  " + message + "  " + chkWhats + "  " + chkEmail + "  " + chkText)
    }

}