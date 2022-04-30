package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.UriUtil
import com.perfect.prodsuit.R
import java.io.*
import java.lang.Exception
import java.util.*

class AddDocumentActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = "AddDocumentActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var imgPick: ImageView? = null
    var imFromDate: ImageView? = null

    var txtAttachmentPath: TextView? = null
    var txtFromSubmit: TextView? = null
    var txtFromDate: TextView? = null

    var edtSubject: EditText? = null
    var edtDescription: EditText? = null

    var llFromdate: LinearLayout? = null
    var llFromDatePick: LinearLayout? = null

    var datePickerFrom: DatePicker? = null

    var btnReset: Button? = null
    var btnSubmit: Button? = null


    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private var PICK_IMAGE_CAMERA = 1
    private var PICK_IMAGE_GALLERY = 2
    private var PICK_DOCUMRNT_GALLERY = 3
    private var PERMISSION_CAMERA = 1

    private var destination: File? = null
    private var inputStreamImg: InputStream? = null
    private var imgPath: String? = null
    private var selectedFilePath = ""
    private var bitmap: Bitmap? = null

    var fromDateMode : String?= "1"  // GONE
    var encodeDoc : String = ""


    var ID_LeadGenerateProduct :String = ""
    var strDate : String = ""
    var strSubject : String = ""
    var strDescription : String = ""
    var documentPath : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_document)
        context = this@AddDocumentActivity

        setRegViews()
        ID_LeadGenerateProduct = intent.getStringExtra("ID_LeadGenerateProduct")!!
        Log.e(TAG,"ID_LeadGenerateProduct  89    "+ID_LeadGenerateProduct)

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imgPick = findViewById<ImageView>(R.id.imgPick)

        txtAttachmentPath = findViewById<TextView>(R.id.txtAttachmentPath)
        txtFromSubmit = findViewById<TextView>(R.id.txtFromSubmit)
        txtFromDate = findViewById<TextView>(R.id.txtFromDate)

        edtSubject = findViewById<EditText>(R.id.edtSubject)
        edtDescription = findViewById<EditText>(R.id.edtDescription)

        llFromdate = findViewById<LinearLayout>(R.id.llFromdate)
        llFromDatePick = findViewById<LinearLayout>(R.id.llFromDatePick)

        imFromDate = findViewById(R.id.imFromDate) as ImageView

        datePickerFrom = findViewById(R.id.datePickerFrom) as DatePicker

        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        imback!!.setOnClickListener(this)
        imgPick!!.setOnClickListener(this)
        llFromdate!!.setOnClickListener(this)
        imFromDate!!.setOnClickListener(this)
        txtFromSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgPick->{
                selectImage()
            }
            R.id.llFromdate->{
                if (fromDateMode.equals("0")){
                    llFromDatePick!!.visibility = View.GONE
                    fromDateMode = "1"
                }else{
                    llFromDatePick!!.visibility = View.VISIBLE
                    fromDateMode = "0"
                }
            }
            R.id.imFromDate->{
                llFromDatePick!!.visibility = View.GONE
                fromDateMode = "1"
            }

            R.id.txtFromSubmit->{
                try {
                    datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerFrom!!.getDayOfMonth()
                    val mon: Int = datePickerFrom!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerFrom!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtFromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llFromDatePick!!.visibility=View.GONE
                    fromDateMode = "1"
                    SiteVisitActivity.strDate = strDay+"-"+strMonth+"-"+strYear
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }
            }
            R.id.btnReset->{
                removeData()
            }
            R.id.btnSubmit->{
                Config.Utils.hideSoftKeyBoard(context,v)
                Validations(v)
            }
        }

    }



    private fun removeData() {
        txtFromDate!!.setText("")
        edtSubject!!.setText("")
        edtDescription!!.setText("")
        txtAttachmentPath!!.setText("")
        documentPath = ""
        encodeDoc = ""
    }

    private fun selectImage() {
        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
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
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                } else {
                    val options = arrayOf<CharSequence>(
                        "Take Photo",
                        "Choose From Gallery",
                        "Choose Document",
                        "Cancel"
                    )
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Select Option")
                    builder.setItems(options,
                        DialogInterface.OnClickListener { dialog, item ->
                            if (options[item] == "Take Photo") {
                                dialog.dismiss()
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, PICK_IMAGE_CAMERA)
                            } else if (options[item] == "Choose From Gallery") {
                                dialog.dismiss()
                                val pickPhoto = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                            } else if (options[item] == "Choose Document") {
                                browseDocuments()
                            } else if (options[item] == "Cancel") {
                                dialog.dismiss()
                            }
                            dialog.dismiss()
                        })
                    builder.show()
                }
            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun browseDocuments() {
        val mimetypes = arrayOf(
            "application/*",  //"audio/*",
            "font/*",  //"image/*",
            "message/*",
            "model/*",
            "multipart/*",
            "text/*"
        )
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes) //Important part here
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, PICK_DOCUMRNT_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inputStreamImg = null
        if (requestCode == PICK_IMAGE_CAMERA) {
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
            } else {
                if (data != null) {
                    val thumbnail = data.extras!!["data"] as Bitmap?
                    val bytes = ByteArrayOutputStream()
                    thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                    destination = File(
                        Environment.getExternalStorageDirectory().toString() + "/" +
                                getString(R.string.app_name),
                        "IMG_" + System.currentTimeMillis() + ".jpg"
                    )
                    val fo: FileOutputStream
                    try {
                        if (!destination!!.getParentFile().exists()) {
                            destination!!.getParentFile().mkdirs()
                        }
                        if (!destination!!.exists()) {
                            destination!!.createNewFile()
                        }
                        fo = FileOutputStream(destination)
                        fo.write(bytes.toByteArray())
                        fo.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    imgPath = destination!!.getAbsolutePath()
                    destination = File(imgPath)
                    documentPath = imgPath!!
                    txtAttachmentPath!!.setText(imgPath)
                } else {
                    Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            if (data != null) {
                val selectedImage = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    val bytes = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
                    imgPath = getRealPathFromURI(selectedImage)
                    destination = File(imgPath.toString())
                    documentPath = imgPath!!
                    txtAttachmentPath!!.setText(imgPath)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "No image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PICK_DOCUMRNT_GALLERY) {
            if (data != null) {
                val uri = data.data
                selectedFilePath = UriUtil.getPath(this, uri!!).toString()
                destination = File(selectedFilePath.toString())
                documentPath = selectedFilePath!!
                txtAttachmentPath!!.setText(selectedFilePath)
            } else {
                Toast.makeText(this, "No Document selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = managedQuery(contentUri, proj, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


    private fun Validations(v: View) {
        strDate = txtFromDate!!.text.toString()
        strSubject = edtSubject!!.text.toString()
        strDescription = edtDescription!!.text.toString()

        if(strDate.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if(strSubject.equals("")){
            Config.snackBars(context,v,"Enter Subject")
        }
        else if(strDescription.equals("")){
            Config.snackBars(context,v,"Enter Description")
        }
        else if(documentPath.equals("")){
            Config.snackBars(context,v,"Pick Documents")
        }
        else{
            Log.e(TAG,"Validations  382"
                    +"\n"+"strDate          : "+ strDate
                    +"\n"+"strSubject       : "+ strSubject
                    +"\n"+"strDescription   : "+ strDescription
                    +"\n"+"documentPath     : "+ documentPath
            )

            val bitmap = BitmapFactory.decodeFile(documentPath)
            val stream =  ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encodeDoc = Base64.getEncoder().encodeToString(stream.toByteArray());
            } else {
                encodeDoc = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
            }

            saveDocuments(strDate,strSubject,strDescription,encodeDoc)
        }


    }

    private fun saveDocuments(strDate: String, strSubject: String, strDescription: String, encodeDoc: String?) {



    }

}