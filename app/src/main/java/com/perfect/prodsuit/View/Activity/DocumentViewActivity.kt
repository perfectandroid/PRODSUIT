package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.ViewDocumentViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class DocumentViewActivity : AppCompatActivity() {

    val TAG : String = "DocumentViewActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var ID_LeadGenerate : String = ""
    var ID_LeadGenerateProduct : String = ""
    var ID_LeadDocumentDetails : String = ""
    var DocumentImageFormat : String = ""

    lateinit var viewDocumentViewModel: ViewDocumentViewModel
    lateinit var documentArrayList : JSONArray

    private var destination: File? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_document_view)

        context = this@DocumentViewActivity

        viewDocumentViewModel = ViewModelProvider(this).get(ViewDocumentViewModel::class.java)

        if (getIntent().hasExtra("ID_LeadGenerate")) {
            ID_LeadGenerate = getIntent().getStringExtra("ID_LeadGenerate")!!
        }
        if (getIntent().hasExtra("ID_LeadGenerateProduct")) {
            ID_LeadGenerateProduct = getIntent().getStringExtra("ID_LeadGenerateProduct")!!
        }

        if (getIntent().hasExtra("ID_LeadDocumentDetails")) {
            ID_LeadDocumentDetails = getIntent().getStringExtra("ID_LeadDocumentDetails")!!
        }
        if (getIntent().hasExtra("DocumentImageFormat")) {
            DocumentImageFormat = getIntent().getStringExtra("DocumentImageFormat")!!
        }
        Log.e(TAG,"ID_LeadGenerate         391   "+ID_LeadGenerate)
        Log.e(TAG,"ID_LeadGenerateProduct  392   "+ID_LeadGenerateProduct)
        Log.e(TAG,"ID_LeadDocumentDetails  393   "+ID_LeadDocumentDetails)
        Log.e(TAG,"DocumentImageFormat     394   "+DocumentImageFormat)

        getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun getDocumentView(ID_LeadGenerate: String, ID_LeadGenerateProduct: String, ID_LeadDocumentDetails: String) {

        var typeAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                viewDocumentViewModel.getViewDocument(this,ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            Log.e(TAG,"msg   89   "+msg)
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   302   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                AccountDetailsActivity
//                                getImages
//                                https://stackoverflow.com/questions/31129644/how-to-download-an-image-from-server-side-to-android-using-bytearray

                                    try {
                                        val jobjt = jObject.getJSONObject("DocumentImageDetails")
                                        val DocumentImage = jobjt.getString("DocumentImage")
                                        val decodedString = Base64.decode(DocumentImage, Base64.DEFAULT)
                                      //  val mediaData: ByteArray = Base64.decode(decodedString, 0)
                                      //  val bytes = decodedString.toByteArray()

                                        destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+System.currentTimeMillis() + DocumentImageFormat)
                                        val fo: FileOutputStream
                                        try {
                                            if (!destination!!.getParentFile().exists()) {
                                                destination!!.getParentFile().mkdirs()
                                            }
                                            if (!destination!!.exists()) {
                                                destination!!.createNewFile()
                                            }
                                            fo = FileOutputStream(destination)
                                            fo.write(decodedString)
                                            fo.close()
                                            Log.e(TAG,"Success   1230    ")
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                            Log.e(TAG,"Exception   1231    "+e.toString())
                                        }
                                    }catch (e: Exception){
                                        Log.e(TAG,"Exception   1232    "+e.toString())
                                    }

                            } else {

                                val builder = AlertDialog.Builder(
                                    this@DocumentViewActivity,
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
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }

        }

    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}