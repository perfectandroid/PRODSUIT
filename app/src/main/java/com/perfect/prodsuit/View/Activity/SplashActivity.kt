package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.CommonAppViewModel
import com.perfect.prodsuit.Viewmodel.CompanyCodeViewModel
import com.perfect.prodsuit.Viewmodel.MaintanceMessageViewModel
import com.perfect.prodsuit.Viewmodel.SplashresellerActivityViewModel
import org.json.JSONObject

class SplashActivity : AppCompatActivity() ,Animation.AnimationListener{



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


    ////    DEMO 15.02.2023
//    val CERT_NAME = "democert.pem"
//    val BASE_URL = "https://45.118.163.44:14001/ProdSuiteAPIDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPIDemo/"
//    val BANK_KEY = "-501"

    ////    Travels 23.02.2023
//    val CERT_NAME = "democert.pem"
//    val BASE_URL = "https://45.118.163.44:14001/ProdSuiteAPI/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPI/"
//    val BANK_KEY = "-501"

    ////    QA 16.12.2022
//    val CERT_NAME = "prodsuiteqa.pem"
//    val BASE_URL = "https://112.133.227.123:14020/ProdsuiteAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/ProdsuiteAPI/"
//    val BANK_KEY = "-500"

    ////    DEMO 21.02.2023
//    val CERT_NAME = "demos.pem"
//    val BASE_URL = "https://202.164.150.65:14271/PerfectprodsuiteAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14271/PerfectprodsuiteAPI/"
//    val BANK_KEY = "-501"

    //// Solar Backup QA 13.01.2023
//    val CERT_NAME = "spectruntest.pem"
//    val BASE_URL = "https://10.1.11.58/ProdSuiteSiteTest/api/"
//    val IMAGE_URL = "https://10.1.11.58/ProdSuiteSiteTest/"
//    val BANK_KEY = "-500"

    ////    LIVE 31.12.2022
//    val CERT_NAME = "prodsuitlive.pem"
//    val BASE_URL = "https://103.50.212.195/PerfectWebERPAPI/api/"
//    val IMAGE_URL = "https://103.50.212.195/PerfectWebERPAPI/"
//    val BANK_KEY = "-101"

    ////    Spectum Live 20.01.2023
//    val CERT_NAME = "spectrumlive.pem"
//    val BASE_URL = "https://45.118.163.44:14002/ProdSuiteAPISpectrum/api/"
//    val IMAGE_URL = "https://45.118.163.44:14002/ProdSuiteAPISpectrum/"
//    val BANK_KEY = "-101"

    // Demo 17-03-2023
//    val CERT_NAME = "supportdemo.pem"
//    val BASE_URL = "https://45.118.163.44:14003/ProdSuiteAPIPerfectDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/ProdSuiteAPIPerfectDemo/"
//    val BANK_KEY = "-510"

    var TAG = "SplashActivity"
    lateinit var splashresellerActivityViewModel: SplashresellerActivityViewModel
    lateinit var maintanceMessageViewModel: MaintanceMessageViewModel
    lateinit var commonAppViewModel: CommonAppViewModel
    lateinit var companyCodeViewModel: CompanyCodeViewModel
    lateinit var context: Context

    var checkCommonApp =0
    var checkCompanyApp =0
    var animBlink: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this@SplashActivity

        splashresellerActivityViewModel = ViewModelProvider(this).get(SplashresellerActivityViewModel::class.java)
        maintanceMessageViewModel = ViewModelProvider(this).get(MaintanceMessageViewModel::class.java)
        commonAppViewModel = ViewModelProvider(this).get(CommonAppViewModel::class.java)
        companyCodeViewModel = ViewModelProvider(this).get(CompanyCodeViewModel::class.java)

        var height = Config.getWidth(context)

        Log.e(TAG,"HEIGHT_WIDTH   777   "+height)


//        val TestingURLpref = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
//        val TestingMobileNopref = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
//        val TestingBankKeypref = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
//        val Testingsslcertificatepref = applicationContext.getSharedPreferences(Config.SHARED_PREF13, 0)
//        val Loginmobilenumberpref = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
//
//        if(Loginmobilenumberpref.getString("Loginmobilenumber", null)!=null
//            && TestingURLpref.getString("TestingURL", null)!=null
//            && Testingsslcertificatepref.getString("CertificateStatus", null)!=null
//            && TestingBankKeypref.getString("BankKey", null)!=null
//            && TestingMobileNopref.getString("TestingMobileNo", null).equals(Loginmobilenumberpref.getString("Loginmobilenumber", null)))
//            {
//            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
//            val BASE_URLEditer = BASE_URLSP.edit()
//            BASE_URLEditer.putString("BASE_URL", TestingURLpref.getString("TestingURL", null))
//            BASE_URLEditer.commit()
//
//            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
//            val IMAGE_URLEditer = IMAGE_URLSP.edit()
//            IMAGE_URLEditer.putString("IMAGE_URL", IMAGE_URLSP.getString("TestingImageURL", null))
//            IMAGE_URLEditer.commit()
//
//            val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
//            val CERT_NAMEEditer = CERT_NAMESP.edit()
//            CERT_NAMEEditer.putString("CERT_NAME", Testingsslcertificatepref.getString("CertificateStatus", null))
//            CERT_NAMEEditer.commit()
//
//            val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//            val BANK_KEYEditer = BANK_KEYESP.edit()
//            BANK_KEYEditer.putString("BANK_KEY", TestingBankKeypref.getString("BankKey", null))
//            BANK_KEYEditer.commit()
//
//        }
//        else{
//            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
//            val BASE_URLEditer = BASE_URLSP.edit()
//            BASE_URLEditer.putString("BASE_URL", BASE_URL)
//            BASE_URLEditer.commit()
//
//            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
//            val IMAGE_URLEditer = IMAGE_URLSP.edit()
//            IMAGE_URLEditer.putString("IMAGE_URL", IMAGE_URL)
//            IMAGE_URLEditer.commit()
//
//
//            val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
//            val CERT_NAMEEditer = CERT_NAMESP.edit()
//            CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
//            CERT_NAMEEditer.commit()
//
//            val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//            val BANK_KEYEditer = BANK_KEYESP.edit()
//            BANK_KEYEditer.putString("BANK_KEY", BANK_KEY)
//            BANK_KEYEditer.commit()
//
//
//        }
//        showMaintanace()

        var im_app_logo = findViewById<ImageView>(R.id.im_app_logo)
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        animBlink!!.setAnimationListener(this);
        im_app_logo.startAnimation(animBlink);


        val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
        var chkstatus =commonAppSP.getString("commonApp","")
        val mpinStatusSP = context.getSharedPreferences(Config.SHARED_PREF23, 0)
        var mpinStatus =mpinStatusSP.getString("mpinStatus","")
        Log.e(TAG,"chkstatus   139   "+chkstatus)


        if(chkstatus.equals("") || mpinStatus.equals(""))
        {
            checkCommonApp = 0
            commonAppChecking()
        }
        else if(chkstatus.equals("1")||chkstatus.equals("0"))
        {
           showMaintanace()
        }


    }

    private fun commonAppChecking() {
        try {

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


            when(Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    commonAppViewModel.getCommonApp(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            try {
                                if (msg!!.length > 0) {
                                    if (checkCommonApp == 0){
                                        checkCommonApp++
                                        Log.e(TAG,"commonAppChecking  145   "+msg)
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {

                                            if (jObject.getString("Mode").equals("1")){

                                                CompanyCodePopup(jObject.getString("Mode").toString())

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

                                                val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
                                                val commonAppEditer = commonAppSP.edit()
                                                commonAppEditer.putString("commonApp", jObject.getString("Mode"))
                                                commonAppEditer.commit()

                                                showMaintanace()
                                            }
                                        }
                                        else {

                                        }
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
        }catch (e: Exception){

        }

    }

    private fun CompanyCodePopup(Mode : String) {
        try {

            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            dialog1 .setContentView(R.layout.dialog_company)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            val tie_CompanyCode = dialog1 .findViewById(R.id.tie_CompanyCode) as TextInputEditText
            val txt_valid_code = dialog1 .findViewById(R.id.txt_valid_code) as TextView
            val img_Code = dialog1 .findViewById(R.id.img_Code) as ImageView
            val btnGo = dialog1 .findViewById(R.id.btnGo) as Button
            val txt_closed = dialog1 .findViewById(R.id.txt_closed) as TextView

            img_Code.setOnClickListener {
                Config.disableClick(it)
                if (tie_CompanyCode.text.toString().length>0){
                    dialog1.dismiss()
                    txt_valid_code!!.visibility = View.GONE
                    checkCompanyApp = 0
                    checkCompanyCode(tie_CompanyCode.text.toString(),Mode)

                }else{
                    txt_valid_code!!.visibility = View.VISIBLE
                }
            }

            btnGo.setOnClickListener {
                Config.disableClick(it)
                if (tie_CompanyCode.text.toString().length>0){
                    dialog1.dismiss()
                    txt_valid_code!!.visibility = View.GONE
                    checkCompanyApp = 0
                    checkCompanyCode(tie_CompanyCode.text.toString(),Mode)

                }else{
                    txt_valid_code!!.visibility = View.VISIBLE
                }
            }


            txt_closed.setOnClickListener {
                finishAffinity()
            }

            dialog1.show()

        }catch (e: Exception){

        }

    }

    private fun checkCompanyCode(companyCode: String,Mode : String) {

        try {
            when(Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    companyCodeViewModel.getCompanyCode(this,companyCode)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            try {
                                if (msg!!.length > 0) {
                                    if (checkCompanyApp == 0){
                                        checkCompanyApp++
                                        Log.e(TAG,"checkCompanyCode  263   "+msg)
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {

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
                                            BANK_KEYEditer.putString("BANK_KEY", jObject.getString("BankKey"))
                                            BANK_KEYEditer.commit()

                                            val companyCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF17, 0)
                                            val companyCodeEditer = companyCodeSP.edit()
                                            companyCodeEditer.putString("companyCode", companyCode)
                                            companyCodeEditer.commit()

                                            val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
                                            val commonAppEditer = commonAppSP.edit()
                                            commonAppEditer.putString("commonApp", Mode)
                                            commonAppEditer.commit()


                                            showMaintanace()

                                        }
                                        else {
                                            continueBottom(jObject.getString("EXMessage").toString(),Mode)
                                        }
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
        }catch (e: Exception){

        }
    }

    private fun continueBottom(EXMessage : String,Mode : String) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_company_code, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val txt_warning = view.findViewById<TextView>(R.id.txt_warning)
        txt_warning.setText(""+EXMessage)


        txtCancel.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()

           CompanyCodePopup(Mode)
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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

                                    val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
                                    val AppIconImageCodeEditer = AppIconImageCodeSP.edit()
                                    AppIconImageCodeEditer.putString("AppIconImageCode", jobj.getString("AppIconImageCode"))
                                    AppIconImageCodeEditer.commit()

                                    val CompanyLogoImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
                                    val CompanyLogoImageCodeEditer = CompanyLogoImageCodeSP.edit()
                                    CompanyLogoImageCodeEditer.putString("CompanyLogoImageCode", jobj.getString("CompanyLogoImageCode"))
                                    CompanyLogoImageCodeEditer.commit()

                                    val ProductNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF21, 0)
                                    val ProductNameEditer = ProductNameSP.edit()
                                    ProductNameEditer.putString("ProductName", jobj.getString("ProductName"))
                                    ProductNameEditer.commit()

                                    val PlayStoreLinkSP = applicationContext.getSharedPreferences(Config.SHARED_PREF22, 0)
                                    val PlayStoreLinkEditer = PlayStoreLinkSP.edit()
                                    PlayStoreLinkEditer.putString("PlayStoreLink", jobj.getString("PlayStoreLink"))
                                    PlayStoreLinkEditer.commit()



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

                                    val TestingsslcertificateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF13, 0)
                                    val TestingsslcertificateEditer = TestingsslcertificateSP.edit()
                                    TestingsslcertificateEditer.putString("Testingsslcertificate", jobj.getString("CertificateName"))
                                    TestingsslcertificateEditer.commit()


                                    val TestingURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
                                    val TestingURLEditer = TestingURLSP.edit()
                                    TestingURLEditer.putString("TestingURL", jobj.getString("TestingURL")+"/")
                                    TestingURLEditer.commit()

                                    val TestingImageURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF15, 0)
                                    val TestingImageURLEditer = TestingImageURLSP.edit()
                                    TestingImageURLEditer.putString("TestingImageURL", jobj.getString("TestingImageURL")+"/")
                                    TestingImageURLEditer.commit()

                                    val TestingMobileNoSP = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
                                    val TestingMobileNoEditer = TestingMobileNoSP.edit()
                                    TestingMobileNoEditer.putString("TestingMobileNo", jobj.getString("TestingMobileNo"))
                                    TestingMobileNoEditer.commit()

                                    val TestingBankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
                                    val TestingBankKeyEditer = TestingBankKeySP.edit()
                                    TestingBankKeyEditer.putString("TestingBankKey", jobj.getString("TestingBankKey"))
                                    TestingBankKeyEditer.commit()

                                    val ABOUTUSSP = applicationContext.getSharedPreferences(Config.SHARED_PREF31, 0)
                                    val ABOUTUSEditer = ABOUTUSSP.edit()
                                    ABOUTUSEditer.putString("ABOUTUS", jobj.getString("AboutUs"))
                                    ABOUTUSEditer.commit()


/*
                                    val TestingURLpref = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
                                    val TestingMobileNopref = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
                                    val TestingBankKeypref = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
                                    val Testingsslcertificatepref = applicationContext.getSharedPreferences(Config.SHARED_PREF13, 0)
                                    val Loginmobilenumberpref = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
                                    val TestingImageURLpref = applicationContext.getSharedPreferences(Config.SHARED_PREF15, 0)



                                    if (Loginmobilenumberpref.getString("Loginmobilenumber", null) != null
                                        && TestingURLpref.getString("TestingURL", null) != null
                                        && TestingImageURLpref.getString("TestingImageURL", null) != null
                                        && Testingsslcertificatepref.getString("Testingsslcertificate", null) != null
                                        && TestingBankKeypref.getString("TestingBankKey", null) != null
                                        && TestingMobileNopref.getString("TestingMobileNo", null).equals(Loginmobilenumberpref.getString("Loginmobilenumber", null))) {

                                        val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
                                        val BASE_URLEditer = BASE_URLSP.edit()
                                        BASE_URLEditer.putString("BASE_URL", TestingURLpref.getString("TestingURL", null))
                                        BASE_URLEditer.commit()

                                        val TestingImageURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
                                        val TestingImageURLEditer = TestingImageURLSP.edit()
                                        TestingImageURLEditer.putString("TestingImageURL", jobj.getString("TestingImageURL"))
                                        TestingImageURLEditer.commit()

                                        val TestingMobileNoSP = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
                                        val TestingMobileNoEditer = TestingMobileNoSP.edit()
                                        TestingMobileNoEditer.putString("TestingMobileNo", jobj.getString("TestingMobileNo"))
                                        TestingMobileNoEditer.commit()

                                        val TestingBankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
                                        val TestingBankKeyEditer = TestingBankKeySP.edit()
                                        TestingBankKeyEditer.putString("TestingBankKey", jobj.getString("TestingBankKey"))
                                        TestingBankKeyEditer.commit()

                                        val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
                                        val CERT_NAMEEditer = CERT_NAMESP.edit()
                                        CERT_NAMEEditer.putString("CERT_NAME", Testingsslcertificatepref.getString("CertificateName", null))
                                        CERT_NAMEEditer.commit()



                                    } else {

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

                                    }*/


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
                                 //   getResellerData()
                                    val jObject1 = jObject.getJSONObject("MaintenanceMessage")
                                    Log.e(TAG,"608   "+jObject1.getString("Type"))
                                    var Type = jObject1.getString("Type")
                                    if (Type.equals("0")){
                                        getResellerData()
                                    }
                                    else if (Type.equals("1")){
                                        var jobj = jObject.getJSONObject("MaintenanceMessage")
                                        warningPopup(jobj.getString("Description"))

//                                        var jobj = jObject.getJSONObject("MaintenanceMessage")
//
//                                        val builder = AlertDialog.Builder(
//                                            this@SplashActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jobj.getString("Description"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                            getResellerData()
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
                                    }
                                    if (Type.equals("2")){

                                        var jobj = jObject.getJSONObject("MaintenanceMessage")
                                        errorPopup(jobj.getString("Description"))


//                                        var jobj = jObject.getJSONObject("MaintenanceMessage")
//
//                                        val builder = AlertDialog.Builder(
//                                            this@SplashActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jobj.getString("Description"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                           finishAffinity()
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
                                    }


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

    private fun errorPopup(Description: String) {
        try {

            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            dialog1 .setContentView(R.layout.error_alert)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


            val txt_warning = dialog1 .findViewById(R.id.txt_warning) as TextView
            val txtSubmit = dialog1 .findViewById(R.id.txtSubmit) as TextView
            val btnMssubmit = dialog1 .findViewById(R.id.btnMssubmit) as Button

            txt_warning.setText(""+Description)



            txtSubmit.setOnClickListener {
                dialog1.dismiss()
                finishAffinity()
            }

            dialog1.show()

        }catch (e: Exception){

        }
    }

    private fun warningPopup(Description : String) {
        try {

            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            dialog1 .setContentView(R.layout.warnig_alert)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


            val txt_warning = dialog1 .findViewById(R.id.txt_warning) as TextView
            val txtCancel = dialog1 .findViewById(R.id.txtCancel) as TextView
            val txtSubmit = dialog1 .findViewById(R.id.txtSubmit) as TextView

            txt_warning.setText(""+Description)

            txtCancel.setOnClickListener {
                dialog1.dismiss()
                finishAffinity()
            }

            txtSubmit.setOnClickListener {
                dialog1.dismiss()
                getResellerData()
            }

            dialog1.show()

        }catch (e: Exception){

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

    override fun onAnimationStart(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {

    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

}