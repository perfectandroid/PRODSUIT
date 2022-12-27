package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.MaintanceMessageViewModel
import com.perfect.prodsuit.Viewmodel.SplashresellerActivityViewModel
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {



//    DEVELOPMENT OLD
//    val CERT_NAME = "static-vm.pem"
//    val BASE_URL = "https://202.164.150.65:14262/ProdSuitAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14262/ProdSuitAPI/"
//    val BANK_KEY = "-500"

////    DEVELOPMENT 30.11.2022
    val CERT_NAME = "development.pem"
    val BASE_URL = "https://202.164.150.65:14271/ProdsuiteAPI/api/"
    val IMAGE_URL = "https://202.164.150.65:14271/ProdsuiteAPI/"
    val BANK_KEY = "-500"


    ////    QA 16.12.2022
//    val CERT_NAME = "prodsuiteqa.pem"
//    val BASE_URL = "https://112.133.227.123:14020/ProdsuiteAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/ProdsuiteAPI/"
//    val BANK_KEY = "-500"


    var TAG = "SplashActivity"
    lateinit var splashresellerActivityViewModel: SplashresellerActivityViewModel
    lateinit var maintanceMessageViewModel: MaintanceMessageViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this@SplashActivity

        splashresellerActivityViewModel = ViewModelProvider(this).get(
            SplashresellerActivityViewModel::class.java
        )
        maintanceMessageViewModel = ViewModelProvider(this).get(
            MaintanceMessageViewModel::class.java


        )
        val TestingURLpref = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
        val TestingMobileNopref = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
        val TestingBankKeypref = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
        val Testingsslcertificatepref = applicationContext.getSharedPreferences(
            Config.SHARED_PREF13,
            0
        )
        val Loginmobilenumberpref = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        if(Loginmobilenumberpref.getString("Loginmobilenumber", null)!=null
            && TestingURLpref.getString("TestingURL", null)!=null
            && Testingsslcertificatepref.getString("Testingsslcertificate", null)!=null
            && TestingBankKeypref.getString("TestingBankKey", null)!=null
            && TestingMobileNopref.getString("TestingMobileNo", null).equals(
                Loginmobilenumberpref.getString(
                    "Loginmobilenumber",
                    null
                )
            )){
            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
            val BASE_URLEditer = BASE_URLSP.edit()
            BASE_URLEditer.putString("BASE_URL", TestingURLpref.getString("TestingURL", null))
            BASE_URLEditer.commit()

            val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
            val CERT_NAMEEditer = CERT_NAMESP.edit()
            CERT_NAMEEditer.putString(
                "CERT_NAME", Testingsslcertificatepref.getString(
                    "Testingsslcertificate",
                    null
                )
            )
            CERT_NAMEEditer.commit()
            val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
            val BANK_KEYEditer = BANK_KEYESP.edit()
            BANK_KEYEditer.putString(
                "BANK_KEY", TestingBankKeypref.getString(
                    "TestingBankKey",
                    null
                )
            )
            BANK_KEYEditer.commit()
        }else{
            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
            val BASE_URLEditer = BASE_URLSP.edit()
            BASE_URLEditer.putString("BASE_URL", BASE_URL)
            BASE_URLEditer.commit()

            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val IMAGE_URLEditer = IMAGE_URLSP.edit()
            IMAGE_URLEditer.putString("IMAGE_URL", IMAGE_URL)
            IMAGE_URLEditer.commit()


            val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
            val CERT_NAMEEditer = CERT_NAMESP.edit()
            CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
            CERT_NAMEEditer.commit()
            val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
            val BANK_KEYEditer = BANK_KEYESP.edit()
            BANK_KEYEditer.putString("BANK_KEY", BANK_KEY)
            BANK_KEYEditer.commit()
        }
        showMaintanace()
    }

    private fun getResellerData() {
        when(Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                splashresellerActivityViewModel.getReseller(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {

                                try {
                                    var jobj = jObject.getJSONObject("ResellerDetails")

                                    val ResellerNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF32, 0)
                                    val ResellerNameEditer = ResellerNameSP.edit()
                                    ResellerNameEditer.putString("ResellerName",jobj.getString("ResellerName"))
                                    ResellerNameEditer.commit()

                                    val ContactNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF33, 0)
                                    val ContactNumberEditer = ContactNumberSP.edit()
                                    ContactNumberEditer.putString("ContactNumber",jobj.getString("ContactNumber"))
                                    ContactNumberEditer.commit()


                                    val ContactEmailSP = applicationContext.getSharedPreferences(Config.SHARED_PREF34, 0)
                                    val ContactEmailEditer = ContactEmailSP.edit()
                                    ContactEmailEditer.putString("ContactEmail",jobj.getString("ContactEmail"))
                                    ContactEmailEditer.commit()


                                    val ContactAddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF35, 0)
                                    val ContactAddressEditer = ContactAddressSP.edit()
                                    ContactAddressEditer.putString("ContactAddress",jobj.getString("ContactAddress"))
                                    ContactAddressEditer.commit()


                                    val TestingURLSP = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF10,
                                        0
                                    )
                                    val TestingURLEditer = TestingURLSP.edit()
                                    TestingURLEditer.putString(
                                        "TestingURL",
                                        jobj.getString("TestingURL")
                                    )
                                    TestingURLEditer.commit()
                                    val TestingMobileNoSP = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF11,
                                        0
                                    )
                                    val TestingMobileNoEditer = TestingMobileNoSP.edit()
                                    TestingMobileNoEditer.putString(
                                        "TestingMobileNo", jobj.getString(
                                            "TestingMobileNo"
                                        )
                                    )
                                    TestingMobileNoEditer.commit()
                                    val TestingBankKeySP = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF12,
                                        0
                                    )
                                    val TestingBankKeyEditer = TestingBankKeySP.edit()
                                    TestingBankKeyEditer.putString(
                                        "TestingBankKey", jobj.getString(
                                            "BankKey"
                                        )
                                    )
                                    TestingBankKeyEditer.commit()
                                    val TestingsslcertificateSP =
                                        applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF13,
                                            0
                                        )
                                    val TestingsslcertificateEditer = TestingsslcertificateSP.edit()
                                    TestingsslcertificateEditer.putString(
                                        "Testingsslcertificate", jobj.getString(
                                            "CertificateStatus"
                                        )
                                    )
                                    TestingsslcertificateEditer.commit()

                                    val ABOUTUSSP = applicationContext.getSharedPreferences(Config.SHARED_PREF31, 0)
                                    val ABOUTUSEditer = ABOUTUSSP.edit()
                                    ABOUTUSEditer.putString("ABOUTUS", jobj.getString("AboutUs"))
                                    ABOUTUSEditer.commit()


                                    val TestingURLpref = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF10,
                                        0
                                    )
                                    val TestingMobileNopref = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF11,
                                        0
                                    )
                                    val TestingBankKeypref = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF12,
                                        0
                                    )
                                    val Testingsslcertificatepref =
                                        applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF13,
                                            0
                                        )
                                    val Loginmobilenumberpref = applicationContext.getSharedPreferences(
                                        Config.SHARED_PREF14,
                                        0
                                    )




                                    if (Loginmobilenumberpref.getString(
                                            "Loginmobilenumber",
                                            null
                                        ) != null
                                        && TestingURLpref.getString("TestingURL", null) != null
                                        && Testingsslcertificatepref.getString(
                                            "Testingsslcertificate",
                                            null
                                        ) != null
                                        && TestingBankKeypref.getString("TestingBankKey", null) != null
                                        && TestingMobileNopref.getString("TestingMobileNo", null)
                                            .equals(
                                                Loginmobilenumberpref.getString(
                                                    "Loginmobilenumber",
                                                    null
                                                )
                                            )
                                    ) {
                                        val BASE_URLSP = applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF7,
                                            0
                                        )
                                        val BASE_URLEditer = BASE_URLSP.edit()
                                        BASE_URLEditer.putString(
                                            "BASE_URL", TestingURLpref.getString(
                                                "TestingURL",
                                                null
                                            )
                                        )
                                        BASE_URLEditer.commit()
                                        val CERT_NAMESP = applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF8,
                                            0
                                        )
                                        val CERT_NAMEEditer = CERT_NAMESP.edit()
                                        CERT_NAMEEditer.putString(
                                            "CERT_NAME", Testingsslcertificatepref.getString(
                                                "Testingsslcertificate",
                                                null
                                            )
                                        )
                                        CERT_NAMEEditer.commit()
                                        val BANK_KEYESP = applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF9,
                                            0
                                        )
                                        val BANK_KEYEditer = BANK_KEYESP.edit()
                                        BANK_KEYEditer.putString(
                                            "BANK_KEY", TestingBankKeypref.getString(
                                                "TestingBankKey",
                                                null
                                            )
                                        )
                                        BANK_KEYEditer.commit()
                                    } else {
                                        val BASE_URLSP = applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF7,
                                            0
                                        )
                                        val BASE_URLEditer = BASE_URLSP.edit()
                                        BASE_URLEditer.putString("BASE_URL", BASE_URL)
                                        BASE_URLEditer.commit()
                                        val CERT_NAMESP = applicationContext.getSharedPreferences(
                                            Config.SHARED_PREF8,
                                            0
                                        )

                                        val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
                                        val IMAGE_URLEditer = IMAGE_URLSP.edit()
                                        IMAGE_URLEditer.putString("IMAGE_URL", IMAGE_URL)
                                        IMAGE_URLEditer.commit()

                                        val CERT_NAMEEditer = CERT_NAMESP.edit()
                                        CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
                                        CERT_NAMEEditer.commit()

                                        val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
                                        val BANK_KEYEditer = BANK_KEYESP.edit()
                                        BANK_KEYEditer.putString("BANK_KEY", BANK_KEY)
                                        BANK_KEYEditer.commit()




                                    }
                                    doSplash()
                                }catch (e  :Exception){

                                    Log.e(TAG,"  319    "+e.toString())
                                }


                            } else {
                                val builder = AlertDialog.Builder(
                                    this@SplashActivity,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun showMaintanace() {
        when(Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                maintanceMessageViewModel.getMaintanceMessgae(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    getResellerData()
                                }
                                else if(jObject.getString("StatusCode") == "1"){
                                    var jobj = jObject.getJSONObject("MaintenanceMessage")

                                    val builder = AlertDialog.Builder(
                                        this@SplashActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jobj.getString("Description"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        getResellerData()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }
                                else if(jObject.getString("StatusCode") == "2"){
                                    var jobj = jObject.getJSONObject("MaintenanceMessage")
                                    val builder = AlertDialog.Builder(
                                        this@SplashActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jobj.getString("Description"))
                                    /* builder.setPositiveButton("Ok") { dialogInterface, which ->
                                     }*/
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@SplashActivity,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e: Exception){
                            Log.e(TAG,"Exception  419   "+e.toString())
                        }

                    })
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun doSplash() {
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep((4 * 1000).toLong())
                    val Loginpref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
                    val loginstatus =Loginpref.getString("loginsession", null)
                    if (Loginpref.getString("loginsession", null) == null|| Loginpref.getString(
                            "loginsession",
                            null
                        )!!.isEmpty()) {
                        val i = Intent(this@SplashActivity, WelcomeSliderActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (Loginpref.getString("loginsession", null) != null && !Loginpref.getString(
                            "loginsession",
                            null
                        )!!.isEmpty() && Loginpref.getString("loginsession", null) == "Yes") {
                        val i = Intent(this@SplashActivity, MpinActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (Loginpref.getString("loginsession", null) != null && !Loginpref.getString(
                            "loginsession",
                            null
                        )!!.isEmpty() && Loginpref.getString("loginsession", null) == "No") {
                        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        val i = Intent(this@SplashActivity, WelcomeSliderActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }

}