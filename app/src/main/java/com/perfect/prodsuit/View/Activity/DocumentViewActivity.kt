package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.ViewDocumentViewModel
import org.json.JSONObject

class DocumentViewActivity : AppCompatActivity() {

    val TAG : String = "DocumentViewActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var ID_LeadGenerate : String = ""
    var ID_LeadGenerateProduct : String = ""
    var ID_LeadDocumentDetails : String = ""

    lateinit var viewDocumentViewModel: ViewDocumentViewModel

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
        Log.e(TAG,"ID_LeadGenerate         391   "+ID_LeadGenerate)
        Log.e(TAG,"ID_LeadGenerateProduct  392   "+ID_LeadGenerateProduct)
        Log.e(TAG,"ID_LeadDocumentDetails  392   "+ID_LeadDocumentDetails)

        getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)

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



//                                val jobjt = jObject.getJSONObject("DocumentDetails")
//                                documentDetailArrayList = jobjt.getJSONArray("DocumentDetailsList")
//                                if (documentDetailArrayList.length()>0){
//
//                                    Log.e(TAG,"documentDetailArrayList  102   "+documentDetailArrayList)
//
//
//
//                                    recyDocumentDetail = findViewById(R.id.recyDocumentDetail) as RecyclerView
//                                    // val lLayout = GridLayoutManager(this@AgendaActivity, 1)
//                                    recyDocumentDetail!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//                                    // recyAgendaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                    val adapter = DocumentDetailAdapter(this@DocumentListActivity, documentDetailArrayList)
//                                    recyDocumentDetail!!.adapter = adapter
//                                    adapter.setClickListener(this@DocumentListActivity)
//
//
//                                }

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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }
}