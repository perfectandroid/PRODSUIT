package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Common
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.BuildConfig
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject

class SplashActivity : AppCompatActivity() ,Animation.AnimationListener{



//    DEVELOPMENT OLD
//    val CERT_NAME = "static-vm.pem"
//    val BASE_URL = "https://202.164.150.65:14262/ProdSuitAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14262/ProdSuitAPI/"
//    val BANK_KEY = "-500"

//    val CERT_NAME = "expo.pem"
//    val BASE_URL  = "https://45.118.163.44:15010/ExpoLiveapi/api/"
//    val IMAGE_URL = "https://45.118.163.44:15010/ExpoLiveapi/"
//    val BANK_KEY  = "-500"


//    val CERT_NAME = "development.pem"
//    val BASE_URL  = "https://202.164.150.65:14271/ProdsuiteAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14271/ProdsuiteAPI"
//    val BANK_KEY  = "-500"


    // DEV LICENSE API 14-03-2024
    val CERT_NAME = "development.pem"
    val BASE_URL  = "https://202.164.150.65:14271/PersuiteLicenseAPI/api/"
    val IMAGE_URL = "https://202.164.150.65:14271/PersuiteLicenseAPI"
    val BANK_KEY  = "-500"

    // Common APP Sub Domain 12-03-2024
//    val CERT_NAME = "development.pem"
//    val BASE_URL  = "https://persuitemobapp.perfectlimited.com:14012/api/"
//    val IMAGE_URL = "https://persuitemobapp.perfectlimited.com:14012/"
//    val BANK_KEY  = ""

    //QA IP DEVLOPMENT COPY
  /*  val CERT_NAME = "qadevelop.pem"
    val BASE_URL  = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI/api/"
    val IMAGE_URL = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI"
    val BANK_KEY  = "-500"*/


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

    ////    QA 12.10.2023 Multiple Company
//    val CERT_NAME = "certificate_qa.pem"
//    val BASE_URL  = "https://112.133.227.123:14020/QAPersuiteAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/QAPersuiteAPI/"
//    val BANK_KEY  = "-500"

//  qa temp 30.11.2023 Single Company
//    val CERT_NAME = "persuitqa.pem"
//    val BASE_URL  = "https://112.133.227.123:14020/PersuiteAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/PersuiteAPI/"
//    val BANK_KEY  = "-500"


    // Development NEW IP
//    val CERT_NAME = "persuitqa.pem"
//    val BASE_URL  = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI/"
//    val BANK_KEY  = "-500"

//    qa 22.2.2024
//    val CERT_NAME = "checkqa.pem"
//    val BASE_URL  = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI/api/"
//    val IMAGE_URL = "https://112.133.227.123:14020/PersuiteMobileAppTeamAPI/"
//    val BANK_KEY  = "-500"

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

    // Demo 16-06-2023
//    val CERT_NAME = "supportdemo.pem"
//    val BASE_URL = "https://45.118.163.44:14003/ProdSuiteAPIPerfectDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/ProdSuiteAPIPerfectDemo/"
//    val BANK_KEY = "-510"

    ////   Unisoft` 26.04.2023
//    val CERT_NAME = "unicef.pem"
//    val BASE_URL = "https://45.118.163.44:14003/ProdSuiteAPIUnisoft/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/ProdSuiteAPIUnisoft/"
//    val BANK_KEY = "-512"

    ////   Sunitha` 12.10.2023
//    val CERT_NAME = "sunitha.pem"
//    val BASE_URL = "https://45.118.163.44:14004/ProdSuiteAPISunithaFurniture/api/"
//    val IMAGE_URL = "https://45.118.163.44:14004/ProdSuiteAPISunithaFurniture/"
//    val BANK_KEY = "-511"

    ////    Spectum Live 23.05.2023
//    val CERT_NAME = "spectrumlive.pem"
//    val BASE_URL = "https://45.118.163.44:14002/ProdSuiteAPISpectrum/api/"
//    val IMAGE_URL = "https://45.118.163.44:14002/ProdSuiteAPISpectrum/"
//    val BANK_KEY = "-500"

    ////     Live 13.06.2023  Marketing
//    val CERT_NAME = "perfectlive.pem"
//    val BASE_URL = "https://45.118.163.44:14003/PSuitePerfectAPI/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/PSuitePerfectAPI/"
//    val BANK_KEY = "-515"

//    //     Live 26.06.2023  Support
//    val CERT_NAME = "persuitesupportlive.pem"
//    val BASE_URL = "https://45.118.163.44:14003/PersuitPerfectSupportAPI/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/PersuitPerfectSupportAPI/"
//    val BANK_KEY = "-516"

    //     Live 26.06.2023  Support & Marketing
//    val CERT_NAME = "supportmarketing.pem"
//    val BASE_URL = "https://52.172.2.6:14010/PersuitAPI/api/"
//    val IMAGE_URL = "https://52.172.2.6:14010/PersuitAPI/"
//    val BANK_KEY = "-516"

    //    PerfectUAT 05-07-2023
//    val CERT_NAME = "persuituat.pem"
//    val BASE_URL = "https://202.164.150.65:14271/PSuiteUATAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14271/PSuiteUATAPI/"
//    val BANK_KEY = "-501"


//        sheetal 24/7/2023
//    val CERT_NAME = "sheetal.pem"
//    val BASE_URL  = "https://45.118.163.44:14003/ProdSuiteAPISHEETAL/api/"
//    val IMAGE_URL = "https://45.118.163.44:14003/ProdSuiteAPISHEETAL/"
//    val BANK_KEY  = "-517"


//    val CERT_NAME = "persuituat.pem"
//    val BASE_URL = "https://202.164.150.65:14271/PSuiteUATAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14271/PSuiteUATAPI/"
//    val BANK_KEY = "-501"


//      sheethal 3/8/2023
//    val CERT_NAME = "sheetal1.pem"
//    val BASE_URL  = "https://45.118.163.44:14001/ProdSuiteAPISHEETAL/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPISHEETAL/"
//    val BANK_KEY  = "-517"



//    localsheetal ip 11.8.2023
//    val CERT_NAME = "sheetallocal.pem"
//    val BASE_URL  = "https://192.168.1.40:443/PerSuiteAPISHEETAL/api/"
//    val IMAGE_URL = "https://192.168.1.40:443/PerSuiteAPISHEETAL/"
//    val BANK_KEY  = "-517"


//    sheetalLive 21.10.2023
//    val CERT_NAME = "sheetallive.pem"
//    val BASE_URL  = "https://59.96.61.232:14001/PerSuiteAPISHEETAL/api/"
//    val IMAGE_URL = "https://59.96.61.232:14001/PerSuiteAPISHEETAL/"
//    val BANK_KEY  = "-517"


    ////    DEVELOPMENT 30.11.2022

//    val CERT_NAME = "prodsuiteapi.pem"
//    val BASE_URL  = "https://202.164.150.65:14271/ProdsuiteAPI/api/"
//    val IMAGE_URL = "https://202.164.150.65:14271/ProdsuiteAPI"
//    val BANK_KEY  = "-500"-500


    ///verminatorERP 30.9.2023
//    val CERT_NAME = "verminator.pem"
//    val BASE_URL  = "https://45.118.163.44:14006/ProdSuiteAPIVerminator/api/"
//    val IMAGE_URL = "https://45.118.163.44:14006/ProdSuiteAPIVerminator"
//    val BANK_KEY  = "-519"

//  latest Demo  26.9.2023
//    val CERT_NAME = "demosept.pem"
//    val BASE_URL  = "https://52.172.2.6:14010/PersuitAPI/api/"
//    val IMAGE_URL = "https://52.172.2.6:14010/PersuitAPI"
//    val BANK_KEY  = "-516"


    //ASCO ERP 25.9.2023
//    val CERT_NAME = "support_demo.pem"
//    val BASE_URL  = "https://45.118.163.44:14005/ProdSuiteAPIASCO/api/"
//    val IMAGE_URL = "https://45.118.163.44:14005/ProdSuiteAPIASCO"
//    val BANK_KEY  = "-518"

//    15.9.2023
//    val CERT_NAME = "latestdemo.pem"
//    val BASE_URL = "https://45.118.163.44:14001/ProdSuiteAPIPerfectDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPIPerfectDemo"
//    val BANK_KEY = "-510"


//    Demo 4/8/2023
//    val CERT_NAME = "persuitdemo.pem"
//    val BASE_URL  = "https://45.118.163.44:14001/ProdSuiteAPIPerfectDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPIPerfectDemo"
//    val BANK_KEY  = "-510"

//    val CERT_NAME = "marketDemo.pem"
//    val BASE_URL  = "https://45.118.163.44:14001/ProdSuiteAPIMarketingDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14001/ProdSuiteAPIMarketingDemo"
//    val BANK_KEY  = "-520"

//  sheetal 30.10.2023
//    val CERT_NAME = "sheetal_latest.pem"
//    val BASE_URL  = "https://59.96.61.232:14001/PerSuiteAPISHEETAL/api/"
//    val IMAGE_URL = "https://59.96.61.232:14001/PerSuiteAPISHEETAL/"
//    val BANK_KEY  = "-517"


//    Marketing 30.10.2023
//    val CERT_NAME = "maketting_demo.pem"
//    val BASE_URL  = "https://45.118.163.44:14008/ProdSuiteAPIMarketingDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14008/ProdSuiteAPIMarketingDemo/"
//    val BANK_KEY  = "-520"


//  support_demo 9.11.2023
//    val CERT_NAME = "support_demo.pem"
//    val BASE_URL  = "https://45.118.163.44:14008/ProdSuiteAPISupportDemo/api/"
//    val IMAGE_URL = "https://45.118.163.44:14008/ProdSuiteAPISupportDemo/"
//    val BANK_KEY  = "-521"

    //ASCO ERP 30.10.2023
//    val CERT_NAME = "support_demo.pem"
//    val BASE_URL  = "https://45.118.163.44:14005/ProdSuiteAPIASCO/api/"
//    val IMAGE_URL = "https://45.118.163.44:14005/ProdSuiteAPIASCO"
//    val BANK_KEY  = "-518"

//  sunitha_furniture 6.12.2023
//    val CERT_NAME = "sunitha_furniture.pem"
//    val BASE_URL  = "https://45.118.163.44:14004/PerSuiteSunithaFurniture/api/"
//    val IMAGE_URL = "https://45.118.163.44:14004/PerSuiteSunithaFurniture"
//    val BANK_KEY  = "-511"

//
//    val CERT_NAME = "commonapp.pem"
//    val BASE_URL  = "https://45.118.163.44:14008/PerSuiteAPICOMMONAPP/api/"
//    val IMAGE_URL = "https://45.118.163.44:14008/PerSuiteAPICOMMONAPP"
//    val BANK_KEY  = "-528"


    var TAG = "SplashActivity"
    lateinit var splashresellerActivityViewModel: SplashresellerActivityViewModel
    lateinit var maintanceMessageViewModel: MaintanceMessageViewModel
    lateinit var commonAppViewModel: CommonAppViewModel
    lateinit var companyCodeViewModel: CompanyCodeViewModel
    lateinit var versionCheckViewModel: VersionCheckViewModel
    lateinit var context: Context

    var checkCommonApp =0
    var checkCompanyApp =0
    var animBlink: Animation? = null

    var distance: Double? = null
    var checkno = 0
    var ID_PKey: String? = ""
    var db : DBHelper? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        context = this@SplashActivity
        db = DBHelper(this, null)

//        distance = SphericalUtil.computeDistanceBetween(sydney, Brisbane);

        //    val distance: Double = Common.distance("17.372102".toDouble(),"78.484196".toDouble(),"17.375775".toDouble(),"78.469218".toDouble())
//        val distance: Double = Common.distance("11.258753".toDouble(),"75.780411".toDouble(),"11.3891".toDouble(),"75.7604".toDouble())
     //   val distance: Double = Common.distance("11.24855325159412".toDouble(),"75.83330446439908".toDouble(),"11.39936546334902".toDouble(),"75.92666858367409".toDouble())
        val distance: Double = Common.distance("11.24855325159412".toDouble(),"75.83330446439908".toDouble()," 11.68284492077125".toDouble(),"75.97373498291024".toDouble())

        Log.e(TAG,"230    Distance   :  "+distance)
        var km = distance / 0.62137
        Log.e(TAG,"230    km   :  "+km)

        splashresellerActivityViewModel = ViewModelProvider(this).get(SplashresellerActivityViewModel::class.java)
        maintanceMessageViewModel = ViewModelProvider(this).get(MaintanceMessageViewModel::class.java)
        commonAppViewModel = ViewModelProvider(this).get(CommonAppViewModel::class.java)
        companyCodeViewModel = ViewModelProvider(this).get(CompanyCodeViewModel::class.java)
        versionCheckViewModel = ViewModelProvider(this).get(VersionCheckViewModel::class.java)

        var height = Config.getWidth(context)

        Log.e(TAG,"HEIGHT_WIDTH   777   "+height)

        var im_app_logo = findViewById<ImageView>(R.id.im_app_logo)
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        animBlink!!.setAnimationListener(this);
        im_app_logo.startAnimation(animBlink);


//        checkno = 0
//        if (checkno == 0){

//            Config.RegisterNetworkCallback(context,this,checkno)
//        Config.checkNetworkConnection(context,this)
//            checkno++
//        }

        val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
        var chkstatus =commonAppSP.getString("commonApp","")
        val mpinStatusSP = context.getSharedPreferences(Config.SHARED_PREF23, 0)
        var mpinStatus =mpinStatusSP.getString("mpinStatus","")
        Log.e(TAG,"chkstatus   139   "+chkstatus)
        val ContDeleteModeSP = context.getSharedPreferences(Config.SHARED_PREF84, 0)
        var contDeleteMode =ContDeleteModeSP.getString("ContDeleteMode","")
        Log.e(TAG,"contDeleteMode   139   "+contDeleteMode)


//        Config.logOut(context,1)
//        db!!.deleteIPReseller()
        db!!.deleteCompanyData()
        if(chkstatus.equals("") || mpinStatus.equals("") || contDeleteMode.equals("0"))
        {
            Log.i("response44","bod   ")


            val ContDeleteModeEditer = ContDeleteModeSP.edit()
            ContDeleteModeEditer.putString("ContDeleteMode", "")
            ContDeleteModeEditer.commit()

            checkCommonApp = 0
            commonAppChecking()
        }
        else if(chkstatus.equals("1")||chkstatus.equals("0"))
        {
            Log.i("response44","b.............  ")
//           showMaintanace()

            var companyrray = JSONArray()
            companyrray = db!!.getDefaultIP()
            Log.e(TAG,"pK   33555   "+companyrray)

            if (companyrray.length() > 0){
                var jsonObject = companyrray.getJSONObject(0)

                val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
                val BASE_URLEditer = BASE_URLSP.edit()
                BASE_URLEditer.putString("BASE_URL", jsonObject.getString("Base_Url"))
                BASE_URLEditer.commit()

                val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
                val IMAGE_URLEditer = IMAGE_URLSP.edit()
                IMAGE_URLEditer.putString("IMAGE_URL", jsonObject.getString("Image_Url"))
                IMAGE_URLEditer.commit()


                val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
                val CERT_NAMEEditer = CERT_NAMESP.edit()
                CERT_NAMEEditer.putString("CERT_NAME", jsonObject.getString("Cert_Name"))
                CERT_NAMEEditer.commit()

                val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
                val BANK_KEYEditer = BANK_KEYESP.edit()
                BANK_KEYEditer.putString("BANK_KEY", jsonObject.getString("Bank_key"))
                BANK_KEYEditer.commit()

                Log.e(TAG,"37551  "+BASE_URLSP.getString("BASE_URL", null))
                Log.e(TAG,"37552  "+IMAGE_URLSP.getString("IMAGE_URL", null))
                Log.e(TAG,"37553  "+CERT_NAMESP.getString("CERT_NAME", null))
                Log.e(TAG,"37554  "+BANK_KEYESP.getString("BANK_KEY", null))
            }

            versionCheck()
        }


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun versionCheck() {
        var editLeadGenDet = 0
        try {
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    versionCheckViewModel.getVersion(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                                if (msg!!.length > 0) {

                                    if (editLeadGenDet == 0) {
                                        editLeadGenDet++
                                        val jObject = JSONObject(msg)
                                        Log.e(TAG, "msg   4233   " + msg)
                                        if (jObject.getString("StatusCode") == "0") {


                                            val jobjt =
                                                jObject.getJSONObject("CheckVersionCode")
                                            val ChkStatusOutput =
                                                jobjt.getInt("ChkStatusOutput")
                                            if (ChkStatusOutput == 10) {
                                                playStorePop()
                                            } else {
                                                showMaintanace()
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
                                    }

                                } else {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                }
                                //  }


                            } catch (e: Exception) {

                                Log.e(TAG, "Exception  4000001    " + e.toString())
                                Toast.makeText(
                                    applicationContext,
                                    "" + Config.SOME_TECHNICAL_ISSUES,
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        })
                }
                false -> {
//                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()


//                    val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                    snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                    snackbar.show()
//                    checkno = 1
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception  4000002    " + e.toString())
        }

    }

    private fun playStorePop() {
        try {

            val dialog1 = Dialog(this)
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            dialog1.setContentView(R.layout.play_update_layout)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )


            val txt_warning = dialog1.findViewById(R.id.txt_warning) as TextView
            val txtCancel = dialog1.findViewById(R.id.txtCancel) as TextView
            val txtSubmit = dialog1.findViewById(R.id.txtSubmit) as TextView

            txt_warning.setText("We're excited to bring you the latest version of " + getString(R.string.app_name) + "! In this update, we've worked hard to enhance your experience and introduce some exciting new features.")

            txtCancel.setOnClickListener {
                dialog1.dismiss()
                finishAffinity()
            }

            txtSubmit.setOnClickListener {
                val applicationId = BuildConfig.APPLICATION_ID
                val playstorelink = "https://play.google.com/store/apps/details?id="+applicationId
                Log.v("sfsdfsdfdsdd", "playStore " + playstorelink)
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+applicationId))
                    intent.setPackage("com.android.vending") // Optional, restricts the intent to the Play Store app
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // If the Play Store app is not installed, open the Play Store website
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+applicationId))
                    startActivity(intent)
                }
            }

            dialog1.show()

        } catch (e: Exception) {

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

//                                                showMaintanace()
                                                versionCheck()
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
                                Log.e(TAG,"Exception  419 2  "+e.toString())
                            }

                        })
                }
                false -> {
//                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()
//                    val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                    snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                    snackbar.show()
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
//            dialog1 .setContentView(R.layout.dialog_company)
            dialog1 .setContentView(R.layout.company_test1)
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

                    txt_valid_code!!.visibility = View.GONE
                    var isExist = db!!.ChekCompanyExist(tie_CompanyCode.text.toString())
                    Log.e(TAG,"isExist  657775   "+isExist)
                    if (isExist){
                        warningPopup("Company Already Exist")
                    }else{
                        dialog1.dismiss()
                        checkCompanyApp = 0
                        checkCompanyCode(tie_CompanyCode.text.toString(),Mode)
                    }
                }else{
                    txt_valid_code!!.visibility = View.VISIBLE
                }
            }

            btnGo.setOnClickListener {
                Config.disableClick(it)
                if (tie_CompanyCode.text.toString().length>0){
                    dialog1.dismiss()
                    txt_valid_code!!.visibility = View.GONE
                    var isExist = db!!.ChekCompanyExist(tie_CompanyCode.text.toString())
                    Log.e(TAG,"isExist  657775   "+isExist)
                    if (isExist){
                        continueBottom("Company Already Registered,Do You Want To Register Another Comapny",Mode)
                       // warningPopup("Company Already Exist")
                    }else{
                        checkCompanyApp = 0
                        checkCompanyCode(tie_CompanyCode.text.toString(),Mode)

                      //  addTempBase(tie_CompanyCode.text.toString(),Mode)
                    }

                }else{
                    txt_valid_code!!.visibility = View.VISIBLE
                }
            }


            txt_closed.setOnClickListener {
                finishAffinity()
            }

            dialog1.show()

        }catch (e: Exception){

            Log.e(TAG,"373   "+e.toString())
        }

    }

    private fun addTempBase(companyCode: String,Mode : String) {
        val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
        val BASE_URLEditer = BASE_URLSP.edit()
        BASE_URLEditer.putString("BASE_URL", "https://112.133.227.123:14020/PersuiteAPI/api/")
        BASE_URLEditer.commit()

        val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
        val IMAGE_URLEditer = IMAGE_URLSP.edit()
        IMAGE_URLEditer.putString("IMAGE_URL", "https://112.133.227.123:14020/PersuiteAPI/")
        IMAGE_URLEditer.commit()


        val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
        val CERT_NAMEEditer = CERT_NAMESP.edit()
        CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
        CERT_NAMEEditer.commit()

        val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
        val BANK_KEYEditer = BANK_KEYESP.edit()
        BANK_KEYEditer.putString("BANK_KEY", "-101")
        BANK_KEYEditer.commit()

        val companyCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF17, 0)
        val companyCodeEditer = companyCodeSP.edit()
        companyCodeEditer.putString("companyCode", companyCode)
        companyCodeEditer.commit()

        val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
        val commonAppEditer = commonAppSP.edit()
        commonAppEditer.putString("commonApp", Mode)
        commonAppEditer.commit()


        var BASE_URL = "https://112.133.227.123:14020/PersuiteAPI/api/"
        var IMAGE_URL = "https://112.133.227.123:14020/PersuiteAPI/"
        var BankKey = "-101"

        ID_PKey =  db!!.insertDataCompany(BASE_URL,IMAGE_URL,BankKey,
            CERT_NAME,companyCode,false,false)
        versionCheck()

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
                                            BASE_URLEditer.putString("BASE_URL", jObject.getString("BASE_URL"))
                                            BASE_URLEditer.commit()

                                            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
                                            val IMAGE_URLEditer = IMAGE_URLSP.edit()
                                            IMAGE_URLEditer.putString("IMAGE_URL", jObject.getString("IMAGE_URL"))
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

//                                            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
//                                            val BASE_URLEditer = BASE_URLSP.edit()
//                                            BASE_URLEditer.putString("BASE_URL", "https://45.118.163.44:14004/ProdSuiteAPISunithaFurniture/api/")
//                                            BASE_URLEditer.commit()
//
//                                            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
//                                            val IMAGE_URLEditer = IMAGE_URLSP.edit()
//                                            IMAGE_URLEditer.putString("IMAGE_URL", "https://45.118.163.44:14004/ProdSuiteAPISunithaFurniture/")
//                                            IMAGE_URLEditer.commit()
//
//
//                                            val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
//                                            val CERT_NAMEEditer = CERT_NAMESP.edit()
//                                            CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
//                                            CERT_NAMEEditer.commit()
//
//                                            val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//                                            val BANK_KEYEditer = BANK_KEYESP.edit()
//                                            BANK_KEYEditer.putString("BANK_KEY", "-511")
//                                            BANK_KEYEditer.commit()
//
//                                            val companyCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF17, 0)
//                                            val companyCodeEditer = companyCodeSP.edit()
//                                            companyCodeEditer.putString("companyCode", companyCode)
//                                            companyCodeEditer.commit()
//
//                                            val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
//                                            val commonAppEditer = commonAppSP.edit()
//                                            commonAppEditer.putString("commonApp", "0")
//                                            commonAppEditer.commit()


//                                            showMaintanace()

                                            Log.e(TAG,"724441   "+jObject.getString("BASE_URL"))
                                            Log.e(TAG,"724442   "+jObject.getString("IMAGE_URL"))
                                            Log.e(TAG,"724443   "+jObject.getString("BankKey"))
                                            Log.e(TAG,"724444   "+CERT_NAME)
                                            Log.e(TAG,"724445   "+companyCode)

                                            var BASE_URL = jObject.getString("BASE_URL")
                                            var IMAGE_URL = jObject.getString("IMAGE_URL")
                                            var BankKey = jObject.getString("BankKey")

                                            ID_PKey =  db!!.insertDataCompany(BASE_URL,IMAGE_URL,BankKey,
                                                CERT_NAME,companyCode,false,false)
                                            versionCheck()

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
                                Log.e(TAG,"Exception  419 3  "+e.toString())
                            }

                        })
                }
                false -> {
//                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()

//                    val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                    snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                    snackbar.show()
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

                                Log.e(TAG,"493   "+jObject)
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

                                    val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
                                    val TechnologyPartnerImageEditer = TechnologyPartnerImageSP.edit()
                                    TechnologyPartnerImageEditer.putString("TechnologyPartnerImage", jobj.getString("TechnologyPartnerImage"))
                                    TechnologyPartnerImageEditer.commit()

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

                                    val AudioClipEnabledSP = applicationContext.getSharedPreferences(Config.SHARED_PREF76, 0)
                                    val AudioClipEnabledEditer = AudioClipEnabledSP.edit()
                                    AudioClipEnabledEditer.putString("AudioClipEnabled", jobj.getString("AudioClipEnabled"))
                                    AudioClipEnabledEditer.commit()

                                    val IsLocationDistanceShowingSP = applicationContext.getSharedPreferences(Config.SHARED_PREF77, 0)
                                    val IsLocationDistanceShowingEditer = IsLocationDistanceShowingSP.edit()
                                    IsLocationDistanceShowingEditer.putString("IsLocationDistanceShowing", jobj.getString("IsLocationDistanceShowing"))
                                    IsLocationDistanceShowingEditer.commit()

                                    val EditMRPLeadSP = applicationContext.getSharedPreferences(Config.SHARED_PREF78, 0)
                                    val EditMRPLeadEditer = EditMRPLeadSP.edit()
                                    EditMRPLeadEditer.putString("EditMRPLead", jobj.getString("EditMRPLead"))
                                    EditMRPLeadEditer.commit()

                                    Log.e(TAG,"94555  ID_PKey   "+ID_PKey)

//                                    if (ID_PKey.equals("")){
//                                        ID_PKey = db!!.getDefaultCompanyID()
//                                    }
                                    var ResellerName = jobj.getString("ResellerName")
                                    var AppIconImageCode = jobj.getString("AppIconImageCode")
                                    var TechnologyPartnerImage = jobj.getString("TechnologyPartnerImage")
                                    var ProductName = jobj.getString("ProductName")
                                    var PlayStoreLink = jobj.getString("PlayStoreLink")
                                    var AppStoreLink = jobj.getString("AppStoreLink")
                                    var ContactNumber = jobj.getString("ContactNumber")
                                    var ContactEmail = jobj.getString("ContactEmail")
                                    var ContactAddress = jobj.getString("ContactAddress")
                                    var CertificateName = jobj.getString("CertificateName")
                                    var TestingURL = jobj.getString("TestingURL")
                                    var TestingMachineId = jobj.getString("TestingMachineId")
                                    var TestingImageURL = jobj.getString("TestingImageURL")
                                    var TestingMobileNo = jobj.getString("TestingMobileNo")
                                    var TestingBankKey = jobj.getString("TestingBankKey")
                                    var TestingBankHeader = jobj.getString("TestingBankHeader")
                                    var AboutUs = jobj.getString("AboutUs")
                                    var AudioClipEnabled = jobj.getString("AudioClipEnabled")
                                    var IsLocationDistanceShowing = jobj.getString("IsLocationDistanceShowing")
                                    var EditMRPLead = jobj.getString("EditMRPLead")

                                    if (!ID_PKey.equals("")){
                                        Log.e(TAG,"103331 ID_PKey  "+ID_PKey)
                                        db!!.insertUpdateReseller(ID_PKey!!,ResellerName,AppIconImageCode,TechnologyPartnerImage,ProductName,PlayStoreLink,AppStoreLink,
                                            ContactNumber,ContactEmail,ContactAddress,CertificateName,TestingURL,TestingMachineId,TestingImageURL,TestingMobileNo,
                                            TestingBankKey,TestingBankHeader,AboutUs,AudioClipEnabled,IsLocationDistanceShowing,EditMRPLead)

                                        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
                                        startActivity(i)
                                        finish()

                                    }else{
                                        Log.e(TAG,"103332 ID_PKey  "+ID_PKey)
                                        ID_PKey = db!!.getDefaultCompanyID()
                                        Log.e(TAG,"103333 ID_PKey  "+ID_PKey)
                                        db!!.insertUpdateReseller(ID_PKey!!,ResellerName,AppIconImageCode,TechnologyPartnerImage,ProductName,PlayStoreLink,AppStoreLink,
                                            ContactNumber,ContactEmail,ContactAddress,CertificateName,TestingURL,TestingMachineId,TestingImageURL,TestingMobileNo,
                                            TestingBankKey,TestingBankHeader,AboutUs,AudioClipEnabled,IsLocationDistanceShowing,EditMRPLead)
                                        doSplash()
                                    }



//                                    val AUDIOSP = applicationContext.getSharedPreferences(Config.SHARED_PREF67, 0)
//                                    val AUDIOEditer = AUDIOSP.edit()
//                                    AUDIOEditer.putString("AUDIO", jobj.getString("AudioClipEnabled"))
//                                    AUDIOEditer.commit()


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


//                                    doSplash()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()

//                val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                snackbar.show()
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
                            Log.e(TAG,"Exception  419 1  "+e.toString())
                        }

                    })
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//                val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                snackbar.show()
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
                    Log.e(TAG,"1354 loginsession  :   "+Loginpref.getString("loginsession", null))
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


    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}