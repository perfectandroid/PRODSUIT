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
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.UriUtil
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.AddQuotationRepository
import com.perfect.prodsuit.Viewmodel.AddNoteViewModel
import com.perfect.prodsuit.Viewmodel.AddQuotationViewModel
import org.json.JSONObject

import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*



class AddQuotationActivity : AppCompatActivity(), View.OnClickListener{

    internal var llattachmnt: LinearLayout? = null
    internal var ll_imgv: LinearLayout? = null
    internal var btnReset: Button? = null
    internal var btnSubmit: Button? = null
    internal var imgv_attachment: ImageView? = null
    internal var txtv_date: TextView? = null
    internal var imgPick: ImageView? = null
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    private var chipNavigationBar: ChipNavigationBar? = null
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
    lateinit var context: Context
    var txtAttachmentPath: TextView? = null
    var etxt_remark: EditText? = null
    lateinit var addQuotationViewModel: AddQuotationViewModel
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addquotation)
        context = this@AddQuotationActivity
        setRegViews()

        getDate()

    }
    private fun getDate() {
        val c = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = simpleDateFormat.format(c)
        txtv_date!!.text=formattedDate
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        txtAttachmentPath = findViewById<TextView>(R.id.txtAttachmentPath)
        llattachmnt= findViewById<LinearLayout>(R.id.llattachmnt)
        txtv_date= findViewById<TextView>(R.id.txtv_date)
        ll_imgv= findViewById<LinearLayout>(R.id.ll_imgv)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnReset = findViewById<Button>(R.id.btnReset)
        etxt_remark= findViewById<EditText>(R.id.etxt_remark)

        imgv_attachment= findViewById<ImageView>(R.id.imgv_attachment)
        imgPick= findViewById<ImageView>(R.id.imgPick)

        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        imgPick!!.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.btnSubmit -> {
                validations(v)
            }
            R.id.btnReset -> {
                ResetData()
            }
            R.id.imgPick -> {
                selectImage()
            }

        }

    }

    private fun ResetData() {
        txtv_date!!.setText("")
        etxt_remark!!.setText("")


    }

    private fun validations(v: View) {

       /* if (txtAttachmentPath!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Select Attachment.")
        }
        else*/ if (etxt_remark!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Add Remark.")
        }

        else
        {

            getQuotation()

        }

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
                    txtAttachmentPath!!.setText(imgPath)
                    if(destination!!.exists())
                    {
                        ll_imgv!!.visibility=View.VISIBLE
                        val bmImg = BitmapFactory.decodeFile(destination!!.getAbsolutePath())
                        imgv_attachment!!.setImageBitmap(bmImg)
                    }

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
                    txtAttachmentPath!!.setText(imgPath)
                    if(destination!!.exists())
                    {
                        ll_imgv!!.visibility=View.VISIBLE
                        val bmImg = BitmapFactory.decodeFile(destination!!.getAbsolutePath())
                        imgv_attachment!!.setImageBitmap(bmImg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "No image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PICK_DOCUMRNT_GALLERY) {
            if (data != null) {
                val uri = data.data
                imgPath = UriUtil.getPath(this, uri!!).toString()
                destination = File(imgPath.toString())
                txtAttachmentPath!!.setText(imgPath)
                ll_imgv!!.visibility=View.GONE
                /*    if(destination!!.exists())
                    {
                        ll_imgv!!.visibility=View.VISIBLE
                        val bmImg = BitmapFactory.decodeFile(destination!!.getAbsolutePath())
                        imgv_attachment!!.setImageBitmap(bmImg)
                    }*/
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

    companion object{
        var transdate= ""
        var remarks= ""
        var imgpth= ""

    }

    private fun getQuotation() {
        transdate = txtv_date!!.text.toString()
        remarks =etxt_remark!!.text.toString()
        imgpth = imgPath.toString()
        Log.i("Details", transdate +"\n"+ remarks )


        addQuotationViewModel = ViewModelProvider(this).get(AddQuotationViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                addQuotationViewModel.getAddquotation(this)!!.observe(
                        this,
                        Observer { addnquotationSetterGetter ->
                            val msg = addnquotationSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                               // val jobjt = jObject.getJSONObject("AddAgentCustomerRemarks")
                                if (jObject.getString("StatusCode") == "0") {

                              /*      var responsemessage = jobjt.getString("ResponseMessage")
                                    val jobjt = jObject.getJSONObject("AddAgentCustomerRemarks")
                                    var responsemsg = jobjt!!.getString("ResponseMessage")

                                    Log.i("Result",responsemsg)
                                    val builder = AlertDialog.Builder(
                                            this@AddNoteActivity,
                                            R.style.MyDialogTheme
                                    )
                                    builder.setMessage(responsemsg)
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        //  val i = Intent(this@AddNoteActivity, AccountDetailsActivity::class.java)
                                        // startActivity(i)
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()*/
                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@AddQuotationActivity,
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
