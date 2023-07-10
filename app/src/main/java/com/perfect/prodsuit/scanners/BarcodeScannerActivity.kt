package com.perfect.prodsuit.scanners

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.perfect.prodsuit.R
import java.io.InputStream

class BarcodeScannerActivity : AppCompatActivity() {

    var TAG = "BarcodeScannerActivity"
    private lateinit var surfaceView: SurfaceView
    private lateinit var overlayView: View
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_barcode_scanner)

        surfaceView = findViewById(R.id.surfaceView)
        overlayView = findViewById(R.id.overlayView)

//        surfaceView.setZOrderOnTop(true);
//        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()



        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setAutoFocusEnabled(true)
            .build()
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (ActivityCompat.checkSelfPermission(
                        this@BarcodeScannerActivity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request camera permission if not granted
                    ActivityCompat.requestPermissions(
                        this@BarcodeScannerActivity,
                        arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_REQUEST
                    )
                    return
                }
                startCamera()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                stopCamera()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes: SparseArray<Barcode> = detections.detectedItems
                if (barcodes.size() > 0) {
                    // Barcode detected, process the result
                    val barcode = barcodes.valueAt(0)
                    val barcodeValue = barcode.displayValue
                    Log.e(TAG,"766001 Camera barcodeValue  "+barcodeValue)

                }
            }
        })

        val selectImageIntent = Intent(Intent.ACTION_PICK)
        selectImageIntent.type = "image/*"

        findViewById<TextView>(R.id.btnSelectImage).setOnClickListener {
            //requestImageSelection.launch(selectImageIntent.type)
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 501)
        }


    }


    @SuppressLint("MissingPermission")
    private fun startCamera() {
        try {
            cameraSource.start(surfaceView.holder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopCamera() {
        cameraSource.stop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                // Camera permission denied, handle accordingly
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG,"7660031 onActivityResult  ")
        if (requestCode == 501 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Pass the selectedImageUri to the image conversion activity or handle it as needed
            Log.e(TAG,"7660032 onActivityResult  "+selectedImageUri)
            try {

                selectedImageUri?.let { uri ->
                    val bitmap: Bitmap? = getBitmapFromUri(uri)
                    Log.e(TAG,"7660033 bitmap  "+bitmap)
                    bitmap?.let { imageBitmap ->
                        val barcodes: SparseArray<Barcode>? = extractBarcodes(bitmap)
                        Log.e(TAG,"766003 barcodes  "+barcodes)

                        if (barcodes!!.size() > 0) {
                            val barcode = barcodes.valueAt(0)
                            val barcodeValue = barcode.displayValue
                            Log.e(TAG,"766001 Gallary barcodeValue  "+barcodeValue)
                        }

                    }

                }

            }catch (e: Exception){
                Log.e(TAG,"766003 Exception  "+e.run {  })
            }

        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val inputStream = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 123
    }

    private fun extractBarcodes(bitmap: Bitmap): SparseArray<Barcode>? {
        val barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        val frame = Frame.Builder()
            .setBitmap(bitmap)
            .build()

        return barcodeDetector.detect(frame)
    }

}