package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*


object Config {

    const val SHARED_PREF = "loginsession"
    const val SHARED_PREF1 = "FK_Employee"
    const val SHARED_PREF2 = "UserName"
    const val SHARED_PREF3 = "Address"
    const val SHARED_PREF4 = "MobileNumber"
    const val SHARED_PREF5 = "Token"
    const val SHARED_PREF6 = "Email"
    const val SHARED_PREF7 = "BASE_URL"
    const val SHARED_PREF8 = "CERT_NAME"
    const val SHARED_PREF9 = "BANK_KEY"
    const val SHARED_PREF10 = "TestingURL"
    const val SHARED_PREF11 = "TestingMobileNo"
    const val SHARED_PREF12 = "TestingBankKey"
    const val SHARED_PREF13 = "Testingsslcertificate"
    const val SHARED_PREF14 = "Loginmobilenumber"
    const val SHARED_PREF15 = "callRecord"
    const val SHARED_PREF16 = "BroadCall"

    const val SHARED_PREF17 = "LS_LocLatitude"
    const val SHARED_PREF18 = "LS_LocLongitude"
    const val SHARED_PREF19 = "LS_LocationName"
    const val SHARED_PREF20 = "LS_FK_Employee"
    const val SHARED_PREF21 = "LS_Name"
    const val SHARED_PREF22 = "LS_Address"
    const val SHARED_PREF23 = "LS_LoginDate"
    const val SHARED_PREF24 = "LS_LoginTime"
    const val SHARED_PREF25 = "LS_LoginMode"
    const val SHARED_PREF26 = "LS_LoginStauats"
    const val SHARED_PREF27 = "LS_DutyStatus"

    const val SHARED_PREF28 = "Notifctn_Id"
    const val SHARED_PREF29 = "IMAGE_URL"
    const val SHARED_PREF30 = "LOGIN_DATETIME"
    const val SHARED_PREF31 = "ABOUTUS"
    const val SHARED_PREF32 = "RESELLER_NAME"
    const val SHARED_PREF33 = "CONTACT_NUMBER"
    const val SHARED_PREF34 = "CONTACT_EMAIL"
    const val SHARED_PREF35 = "CONTACT_ADDRESS"

    const val SHARED_PREF36 = "UserCode"  // EntrBy
    const val SHARED_PREF37 = "FK_Branch"
    const val SHARED_PREF38 = "FK_BranchType"
    const val SHARED_PREF39 = "FK_Company"
    const val SHARED_PREF40 = "FK_BranchCodeUser"
    const val SHARED_PREF41 = "FK_UserRole"
    const val SHARED_PREF42 = "UserRole"
    const val SHARED_PREF43 = "IsAdmin"
    const val SHARED_PREF44 = "ID_User"  // FK_User
    const val SHARED_PREF45 = "BranchName"


    const val SOME_TECHNICAL_ISSUES = "Some Technical Issues."

    fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { hostname, session -> true }
    }

    fun getWrappedTrustManagers(trustManagers: Array<TrustManager>): Array<TrustManager> {
        val originalTrustManager = trustManagers[0] as X509TrustManager
        return arrayOf(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return originalTrustManager.acceptedIssuers
            }
            override fun checkClientTrusted(certs: Array<X509Certificate>?, authType: String) {
                try {
                    if (certs != null && certs.size > 0) {
                        certs[0].checkValidity()
                    } else {
                        originalTrustManager.checkClientTrusted(certs, authType)
                    }
                } catch (e: CertificateException) {
                    Log.w("checkClientTrusted", e.toString())
                }
            }
            override fun checkServerTrusted(certs: Array<X509Certificate>?, authType: String) {
                try {
                    if (certs != null && certs.size > 0) {
                        certs[0].checkValidity()
                    } else {
                        originalTrustManager.checkServerTrusted(certs, authType)
                    }
                } catch (e: CertificateException) {
                    Log.w("checkServerTrusted", e.toString())
                }
            }
        })
    }

    @Throws(CertificateException::class,  KeyStoreException::class,IOException::class, NoSuchAlgorithmException::class, KeyManagementException::class )
    fun getSSLSocketFactory(context: Context): SSLSocketFactory {
        val cf = CertificateFactory.getInstance("X.509")
        val CERT_NAMESP = context.getSharedPreferences(SHARED_PREF8, 0)
        val caInput = context!!.assets.open(CERT_NAMESP.getString("CERT_NAME", null)!!)
        val ca = cf.generateCertificate(caInput)
        caInput.close()
        val keyStore = KeyStore.getInstance("BKS")
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)
        val wrappedTrustManagers = getWrappedTrustManagers(tmf.trustManagers)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, wrappedTrustManagers, null)
        return sslContext.socketFactory
    }

    object Utils {
        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    object ConnectivityUtils {
        @SuppressLint("MissingPermission")
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    @SuppressLint("ResourceAsColor")
    fun snackBars(context: Context, view: View, message: String) {
//        val snackbar: Snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_LONG)
//        snackbar.setActionTextColor(Color.WHITE)
//        snackbar.setBackgroundTint(context.resources.getColor(R.color.black))
//        snackbar.show()


        val snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        val textView: TextView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        val typeface = ResourcesCompat.getFont(context, R.font.myfont)
        textView.setTypeface(typeface)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()

    }

    fun getActionTypes(): String {

        var result =""
        try {

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()


            var obj = JSONObject()
            obj.put("action_id", "1")
            obj.put("action", "Add Remark")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "2")
            obj.put("action", "Site Visit")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "3")
            obj.put("action", "Message")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "4")
            obj.put("action", "Quotation")
            array.put(obj)

            jsonObject.put("actionTypeDetails", array)
            jsonObject1.put("actionType", jsonObject)
            Log.e("JsonObject", jsonObject.toString())
            result = jsonObject1.toString()

        } catch (e: JSONException) {
            e.printStackTrace()
            result= ""
        }

        return result

    }

    fun disableClick(v : View) {
        v.isEnabled = false
        v.postDelayed({
            v.isEnabled = true
        },3000)
    }


}
