package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.AmcFollowUpModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AmcFollowUpAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.io.*


class ServiceFollowUpAmcActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListenerData {
    lateinit var imback: ImageView
    lateinit var jsonArray: JSONArray
    lateinit var tv_invoice: TextView
    private lateinit var recyclerView: RecyclerView
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 41
    var manager: DownloadManager? = null
    lateinit var url:String
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_follow_up_amc)
        var invoiceNumber = intent.getStringExtra("invoice")
        setId()
        setListners()
        loadData()
        tv_invoice.text = "Invoice No: " + invoiceNumber

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setId() {
        recyclerView = findViewById(R.id.recycler)
        imback = findViewById(R.id.imback)
        tv_invoice = findViewById(R.id.tv_invoice)
    }

    private fun setListners() {
        imback.setOnClickListener(this)
    }

    private fun loadData() {
        val amcFollowArray = ArrayList<AmcFollowUpModel>()
        val e1 =
            AmcFollowUpModel(
                "60 Months Warranty",
                "21/07/2022",
                "21/07/2022",
                "Warranty",
            "https://cdn.britannica.com/04/171104-050-AEFE3141/Steve-Jobs-iPhone-2010.jpg"
            )
        val e2 =
            AmcFollowUpModel(
                "AMC for Software",
                "06/02/2023",
                "06/02/2023",
                "Production",
                "https://cdn.britannica.com/04/171104-050-AEFE3141/Steve-Jobs-iPhone-2010.jpg"
            )
        amcFollowArray.add(e1)
        amcFollowArray.add(e2)
        val gson = Gson()
        val listString = gson.toJson(
            amcFollowArray,
            object : TypeToken<ArrayList<AmcFollowUpModel?>?>() {}.type
        )
        jsonArray = JSONArray(listString)

        setServiceFollowRecycler()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imback -> {
                onBackPressed()
            }
        }
    }

    private fun setServiceFollowRecycler() {
        recyclerView!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        var adapter = AmcFollowUpAdapter(this, jsonArray)
        recyclerView!!.adapter = adapter
        adapter.addItemClickListener(this)
    }

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {
        if (data == "download") {
            url = jsonObject.getString("url")
            downloadFile(url)
        }
    }

    private fun downloadFile(url: String) {
        if(checkPermissionForReadExtertalStorage()) {
            Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show()
            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Download")
            request.setDescription("Downloading..")
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${System.currentTimeMillis()}"
            )
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
        else
        {
            requestPermissionForReadExtertalStorage()
        }
    }


    fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result: Int = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    @Throws(Exception::class)
    fun requestPermissionForReadExtertalStorage() {
        try {
            (this as Activity?)?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_REQUEST_CODE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadFile(url)
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}

