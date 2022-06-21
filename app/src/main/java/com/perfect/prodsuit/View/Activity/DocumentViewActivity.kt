package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.perfect.prodsuit.R

class DocumentViewActivity : AppCompatActivity() {

    val TAG : String = "DocumentViewActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var ID_LeadGenerate : String = ""
    var ID_LeadGenerateProduct : String = ""
    var ID_LeadDocumentDetails : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_document_view)

        context = this@DocumentViewActivity

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

    }
}