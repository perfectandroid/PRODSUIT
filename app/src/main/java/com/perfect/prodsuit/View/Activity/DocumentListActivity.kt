package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.DocumentDetailAdapter
import com.perfect.prodsuit.Viewmodel.DocumentDetailViewModel
import com.perfect.prodsuit.Viewmodel.ViewDocumentViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DocumentListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    val TAG : String = "DocumentListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1

    lateinit var documentDetailViewModel: DocumentDetailViewModel
    lateinit var documentDetailArrayList : JSONArray

    var recyDocumentDetail: RecyclerView? = null

    var ID_LeadGenerate : String = ""
    var ID_LeadGenerateProduct : String = ""
    var ID_LeadDocumentDetails : String = ""
    var DocumentImageFormat : String = ""

    lateinit var viewDocumentViewModel: ViewDocumentViewModel
    lateinit var documentArrayList : JSONArray

    private var destination: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_document_list)
        context = this@DocumentListActivity

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        documentDetailViewModel = ViewModelProvider(this).get(DocumentDetailViewModel::class.java)
        viewDocumentViewModel = ViewModelProvider(this).get(ViewDocumentViewModel::class.java)

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

            try {

                Log.e(TAG, "Documents   162")
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                    } else {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                else{

                    val jsonObject = documentDetailArrayList.getJSONObject(position)
                    ID_LeadDocumentDetails = jsonObject.getString("ID_LeadDocumentDetails")
                    DocumentImageFormat = jsonObject.getString("DocumentImageFormat")
//                    val i = Intent(this@DocumentListActivity, DocumentViewActivity::class.java)
//                    i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
//                    i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
//                    i.putExtra("ID_LeadDocumentDetails", ID_LeadDocumentDetails)
//                    i.putExtra("DocumentImageFormat", DocumentImageFormat)
//                    startActivity(i)

                    if (DocumentImageFormat.equals("")){
                      Toast.makeText(applicationContext,"Document not found",Toast.LENGTH_SHORT).show()
                    }else{
                        getDocumentView(ID_LeadGenerate,ID_LeadGenerateProduct,ID_LeadDocumentDetails)
                    }


                }
            }
            catch (e : Exception){

            }




        }

        if (data.equals("ViewDescription")) {

            try {
                val jsonObject = documentDetailArrayList.getJSONObject(position)
                val subject = jsonObject.getString("DocumentSubject")
                val description = jsonObject.getString("DocumentDescription")

                openDescBottomSheet(subject,description)
            }
            catch (e:Exception){

            }

        }

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


                                    if (typeAgenda == 0){
                                        typeAgenda++
                                        val jobjt = jObject.getJSONObject("DocumentImageDetails")
                                        val DocumentImage = jobjt.getString("DocumentImage")
                                        val decodedString = Base64.decode(DocumentImage, Base64.DEFAULT)
                                        //  val mediaData: ByteArray = Base64.decode(decodedString, 0)
                                        //  val bytes = decodedString.toByteArray()
                                        val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
                                        val datetime = sdf.format(Date())
                                        destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+System.currentTimeMillis() + DocumentImageFormat)
//                                    destination = File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name), ""+datetime + DocumentImageFormat)
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

                                            val file: File = File(destination,"")
                                            val map: MimeTypeMap = MimeTypeMap.getSingleton()
                                            val ext: String = MimeTypeMap.getFileExtensionFromUrl(file.name)
                                            var type: String? = map.getMimeTypeFromExtension(ext)
                                            if (type == null)
                                                type = "*/*";

                                            val intent = Intent(Intent.ACTION_VIEW)
                                            val data: Uri = Uri.fromFile(file)
                                            intent.setDataAndType(data, type)
                                            startActivity(intent)


                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                            Log.e(TAG,"Exception   1231    "+e.toString())
                                        }
                                    }

                                }catch (e: Exception){
                                    Log.e(TAG,"Exception   1232    "+e.toString())
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


    private fun openDescBottomSheet(subject: String, description: String) {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_description, null)

        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val tv_subject = view.findViewById<TextView>(R.id.tv_subject)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)

        tv_subject.text = subject
        tv_description.text = description

        imgClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()

    }

}