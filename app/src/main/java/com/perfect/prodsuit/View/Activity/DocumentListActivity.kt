package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DocumentDetailAdapter
import com.perfect.prodsuit.Viewmodel.DocumentDetailViewModel
import org.json.JSONArray
import org.json.JSONObject

class DocumentListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "DocumentListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    lateinit var documentDetailViewModel: DocumentDetailViewModel
    lateinit var documentDetailArrayList : JSONArray

    var recyDocumentDetail: RecyclerView? = null

    var ID_LeadGenerate : String = ""
    var ID_LeadGenerateProduct : String = ""
    var ID_LeadDocumentDetails : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_document_list)
        context = this@DocumentListActivity

        documentDetailViewModel = ViewModelProvider(this).get(DocumentDetailViewModel::class.java)

        setRegViews()

        if (getIntent().hasExtra("ID_LeadGenerate")) {
            ID_LeadGenerate = getIntent().getStringExtra("ID_LeadGenerate")!!
        }
        if (getIntent().hasExtra("ID_LeadGenerateProduct")) {
            ID_LeadGenerateProduct = getIntent().getStringExtra("ID_LeadGenerateProduct")!!
        }

        Log.e(TAG,"ID_LeadGenerate         391   "+ID_LeadGenerate)
        Log.e(TAG,"ID_LeadGenerateProduct  392   "+ID_LeadGenerateProduct)

        getDocumentDetail(ID_LeadGenerate,ID_LeadGenerateProduct)
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

//        recyDocumentDetail = findViewById(R.id.recyDocumentDetail)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    private fun getDocumentDetail(ID_LeadGenerate: String, ID_LeadGenerateProduct: String) {

        var typeAgenda = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                documentDetailViewModel.getDocumentDetail(this,ID_LeadGenerate,ID_LeadGenerateProduct)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            Log.e(TAG,"msg   89   "+msg)
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   302   "+msg)
                            if (jObject.getString("StatusCode") == "0") {



                                val jobjt = jObject.getJSONObject("DocumentDetails")
                                documentDetailArrayList = jobjt.getJSONArray("DocumentDetailsList")
                                if (documentDetailArrayList.length()>0){

                                    Log.e(TAG,"documentDetailArrayList  102   "+documentDetailArrayList)



                                    recyDocumentDetail = findViewById(R.id.recyDocumentDetail) as RecyclerView
                                    // val lLayout = GridLayoutManager(this@AgendaActivity, 1)
                                    recyDocumentDetail!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                                    // recyAgendaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    val adapter = DocumentDetailAdapter(this@DocumentListActivity, documentDetailArrayList)
                                    recyDocumentDetail!!.adapter = adapter
                                    adapter.setClickListener(this@DocumentListActivity)


                                }

                            } else {

                                val builder = AlertDialog.Builder(
                                    this@DocumentListActivity,
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("Documents")) {

            Log.e(TAG, "Documents   162")
            val jsonObject = documentDetailArrayList.getJSONObject(position)
            ID_LeadDocumentDetails = jsonObject.getString("ID_LeadDocumentDetails")
            val i = Intent(this@DocumentListActivity, DocumentViewActivity::class.java)
            i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
            i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
            i.putExtra("ID_LeadDocumentDetails", ID_LeadDocumentDetails)
            startActivity(i)

        }

    }

}