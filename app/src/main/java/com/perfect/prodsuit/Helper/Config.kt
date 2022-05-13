package com.perfect.prodsuit.Helper

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
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

    fun snackBars(context: Context, view: View, message: String) {
        val snackbar: Snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setBackgroundTint(context.resources.getColor(R.color.black))
        snackbar.show()


    }

}
