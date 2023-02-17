package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.UriUtil
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.SaveDocumentViewModel
import org.json.JSONObject
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
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
    private var selectedFilePath: String? = null
    private var bitmap: Bitmap? = null

    var fromDateMode : String?= "1"  // GONE
    var encodeDoc : String = ""

    var tie_Date: TextInputEditText? = null
    var tie_Subject: TextInputEditText? = null
    var tie_Description: TextInputEditText? = null
    var tie_Attachment: TextInputEditText? = null

    var ID_LeadGenerateProduct :String = ""
    var strDate : String = ""
    var strSubject : String = ""
    var strDescription : String = ""
    var documentPath : String = ""


    lateinit var saveDocumentViewModel: SaveDocumentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_document)
        context = this@AddDocumentActivity

        saveDocumentViewModel = ViewModelProvider(this).get(SaveDocumentViewModel::class.java)

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


        tie_Date       = findViewById(R.id.tie_Date) as TextInputEditText
        tie_Subject       = findViewById(R.id.tie_Subject) as TextInputEditText
        tie_Description       = findViewById(R.id.tie_Description) as TextInputEditText
        tie_Attachment       = findViewById(R.id.tie_Attachment) as TextInputEditText

        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        imback!!.setOnClickListener(this)
        imgPick!!.setOnClickListener(this)
        llFromdate!!.setOnClickListener(this)
        imFromDate!!.setOnClickListener(this)
        txtFromSubmit!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)
        tie_Attachment!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_Date!!.setText(currentDate)
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
                    strDate = strDay+"-"+strMonth+"-"+strYear
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }
            }

            R.id.tie_Date->{
                openBottomSheet()
            }
            R.id.tie_Attachment->{
                selectImage()
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


    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }


    private fun removeData() {
        txtFromDate!!.setText("")
        edtSubject!!.setText("")
        edtDescription!!.setText("")
        tie_Subject!!.setText("")
        tie_Description!!.setText("")
        txtAttachmentPath!!.setText("")
        tie_Attachment!!.setText("")
        documentPath = ""
        encodeDoc = ""

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_Date!!.setText(currentDate)
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

            try {
                if (data != null) {
                    try {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
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
                                    this,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                                )
                            }
                        } else {

                            Log.e(TAG,"3961   "+data)
                            Log.e(TAG,"3962   "+data!!.getExtras()!!.get("data"))

                            val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                            val bytes = ByteArrayOutputStream()
                            thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//                    destination = File(
//                        (Environment.getExternalStorageDirectory()).toString() + "/" +
//                                getString(R.string.app_name),
//                        "IMG_" + System.currentTimeMillis() + ".jpg"
//                    )
//                    destination = File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" +
//                               "",
//                        "IMG_" + System.currentTimeMillis() + ".jpg"
//                    )
//                    val fo: FileOutputStream

                            try {
//                        if (!destination!!.getParentFile().exists()) {
//                            destination!!.getParentFile().mkdirs()
//                        }
//                        if (!destination!!.exists()) {
//                            destination!!.createNewFile()
//                        }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    destination = File(
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                        ""
                                    )
                                    // destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)  +"/" +  getString(R.string.app_name));
                                } else {
                                    destination = File(
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                        ""
                                    )
                                }

                                if (!destination!!.exists()) {
                                    destination!!.createNewFile()
                                }

                                destination = File(
                                    (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" +
                                            "",
                                    "IMG_" + System.currentTimeMillis() + ".jpg"
                                )
                                val fo: FileOutputStream


                                fo = FileOutputStream(destination)
                                fo.write(bytes.toByteArray())
                                fo.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                                Log.e(TAG, "FileNotFoundException   23671    " + e.toString())

                            } catch (e: IOException) {
                                e.printStackTrace()
                                Log.e(TAG, "FileNotFoundException   23672    " + e.toString())
                            }


                                imgPath = destination!!.getAbsolutePath()
                                Log.e(TAG, "imgPath  20522    " + imgPath)
                                destination = File(imgPath)
                                documentPath = imgPath!!
                                txtAttachmentPath!!.setText(imgPath)
                                tie_Attachment!!.setText(imgPath)


                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@AddDocumentActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {

            }


//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) !== PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permission is not granted
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//                ) {
//                } else {
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
//                    )
//                }
//            } else {
//                if (data != null) {
//                    val thumbnail = data.extras!!["data"] as Bitmap?
//                    val bytes = ByteArrayOutputStream()
//                    thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//                    destination = File(
//                        Environment.getExternalStorageDirectory().toString() + "/" +
//                                getString(R.string.app_name),
//                        "IMG_" + System.currentTimeMillis() + ".jpg"
//                    )
//                    val fo: FileOutputStream
//                    try {
//                        if (!destination!!.getParentFile().exists()) {
//                            destination!!.getParentFile().mkdirs()
//                        }
//                        if (!destination!!.exists()) {
//                            destination!!.createNewFile()
//                        }
//                        fo = FileOutputStream(destination)
//                        fo.write(bytes.toByteArray())
//                        fo.close()
//                    } catch (e: FileNotFoundException) {
//                        e.printStackTrace()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                    imgPath = destination!!.getAbsolutePath()
//                    destination = File(imgPath)
//                    documentPath = imgPath!!
//                    txtAttachmentPath!!.setText(imgPath)
//                    tie_Attachment!!.setText(imgPath)
//                } else {
//                    Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
        else if (requestCode == PICK_IMAGE_GALLERY) {
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
                    tie_Attachment!!.setText(imgPath)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "No image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        }
        else if (requestCode == PICK_DOCUMRNT_GALLERY) {
            try {
                if (data != null) {
                    val uri = data.data
                    Log.e(TAG,"561  uri "+uri)
                    Log.e(TAG,"561 data  "+data)
                    selectedFilePath = UriUtil.getPath(this, uri!!).toString()
                  //  selectedFilePath = getRealPathFromURI(uri)
                    selectedFilePath = getRealPathFromURI(uri)
                    Log.e(TAG,"561  selectedFilePath   "+selectedFilePath)
                    destination = File(selectedFilePath.toString())
                    Log.e(TAG,"561 destination  "+destination)
                    documentPath = selectedFilePath!!
                    Log.e(TAG,"561 documentPath   "+documentPath)
                    txtAttachmentPath!!.setText(selectedFilePath)
                    tie_Attachment!!.setText(selectedFilePath)
                } else {
                    Toast.makeText(this, "No Document selected", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Log.e(TAG,"561   "+e.toString())
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

        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFollowUp = inputFormat.parse(tie_Date!!.text.toString())
        strDate = outputFormat.format(dateFollowUp)
        strSubject = tie_Subject!!.text.toString()
        strDescription = tie_Description!!.text.toString()

//        strDate = txtFromDate!!.text.toString()
//        strSubject = edtSubject!!.text.toString()
//        strDescription = edtDescription!!.text.toString()

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

            try {

//                val bitmap = BitmapFactory.decodeFile(documentPath)
//                val stream =  ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

//                val fileOuputStream = FileOutputStream("C:\\testing2.txt")

                val inputStream: InputStream = FileInputStream(documentPath)
                val bos = ByteArrayOutputStream()
                val b = ByteArray(1024 * 8)
                var bytesRead = 0

                while (inputStream.read(b).also { bytesRead = it } != -1) {
                    bos.write(b, 0, bytesRead)
                }

             //   byteArray = bos.toByteArray()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    encodeDoc = Base64.getEncoder().encodeToString(stream.toByteArray());
                    encodeDoc = Base64.getEncoder().encodeToString(bos.toByteArray());
                } else {
                  //  encodeDoc = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                    encodeDoc = android.util.Base64.encodeToString(bos.toByteArray(), android.util.Base64.DEFAULT)
                }
                val extension: String = documentPath.substring(documentPath.lastIndexOf("."))
                Log.e(TAG,"encodeDoc   508   "+encodeDoc)
                saveDocuments(strDate,strSubject,strDescription,encodeDoc,extension)
            }
            catch (e: Exception){
                Log.e(TAG,"Exception   508   "+e.toString())
            }


        }


    }

    private fun saveDocuments(strDate: String, strSubject: String, strDescription: String, encodeDoc: String?,extension : String) {

        var saveDoc = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                saveDocumentViewModel.saveDocuments(this,ID_LeadGenerateProduct,strDate,strSubject,strDescription,encodeDoc!!,extension)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   82   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("AddDocument")

                                    val builder = AlertDialog.Builder(
                                        this@AddDocumentActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jobjt.getString("ResponseMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    val i = Intent(this@AddDocumentActivity, AccountDetailsActivity::class.java)
//                                    startActivity(i)
//                                    finish()

                                        onBackPressed()

                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AddDocumentActivity,
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

                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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