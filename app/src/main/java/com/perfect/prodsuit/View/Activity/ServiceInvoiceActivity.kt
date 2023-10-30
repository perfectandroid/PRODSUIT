package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.BuildConfig
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProductInfoListAdapter
import com.perfect.prodsuit.View.Adapter.ServiceInfoListAdapter
import com.perfect.prodsuit.Viewmodel.ClosedTicketViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ServiceInvoiceActivity : AppCompatActivity(), View.OnClickListener {
    val rupee='\u20B9'
    lateinit var file: File
    var uri: Uri? = null
    var bitmapt: Bitmap? = null
    var TAG = "ServiceInvoiceActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    var Idcudtomerregisterdetails: String? = ""
    var tv_customer: TextView? = null
    var tv_address: TextView? = null
    var tv_ticketno: TextView? = null
    var tv_date: TextView? = null


    var tv_advamt: TextView? = null
    var tv_servicecharge: TextView? = null
    var tv_productcharge: TextView? = null
    var tv_securityamnt: TextView? = null
    var tv_discountamnt: TextView? = null
    var tv_netamnt: TextView? = null

    var btnShare: Button? = null
    var btnCancel: Button? = null


    var llsecurityamt: LinearLayout? = null
    var lldisamnt: LinearLayout? = null
    var llnetamnt: LinearLayout? = null
    var lladvamnt: LinearLayout? = null
    lateinit var serviceInfoListSort: JSONArray



    private var recyler_service                       : RecyclerView?   = null
    private var recycler_product                       : RecyclerView?   = null

    var serviceInfoArrayList = JSONArray()
    lateinit var productInfooArrayList: JSONArray
    lateinit var AmountDetails: JSONArray


    var customer_name: String? = ""
    var customer_address: String? = ""
    var TicketNo: String? = ""
    var date_invoice: String? = ""


    var AdvanceAmount: Double? = 0.0
    var SecurityAmount: Double? = 0.0
    var ServiceCharge: Double? = 0.0
    var DiscountAmount: Double? = 0.0
    var ProductCharge: Double? = 0.0
    var NetAmount: Double? = 0.0

    var modeClosed = 0

    lateinit var closedTicketViewModel: ClosedTicketViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_invoice)
        context = this@ServiceInvoiceActivity
        closedTicketViewModel = ViewModelProvider(this).get(ClosedTicketViewModel::class.java)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        try {
            customer_name = intent.getStringExtra("customer_name").toString()
            customer_address = intent.getStringExtra("CusAddress").toString()
            TicketNo = intent.getStringExtra("TicketNo").toString()
            date_invoice = intent.getStringExtra("RegDate").toString()
            Idcudtomerregisterdetails = intent.getStringExtra("Idcudtomerregisterdetails").toString()

        }catch (e : Exception){

        }
        Log.e(TAG, "customer_name===="+customer_name)
        Log.e(TAG, "TicketNo===="+TicketNo)
        Log.e(TAG, "CusAddress===="+customer_address)
        Log.e(TAG, "Idcust===="+Idcudtomerregisterdetails)
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        tv_customer=findViewById<TextView>(R.id.tv_customer)
        tv_address=findViewById<TextView>(R.id.tv_address)
        tv_ticketno=findViewById<TextView>(R.id.tv_ticketno)
        tv_date=findViewById<TextView>(R.id.tv_date)
        btnShare=findViewById<Button>(R.id.btnShare)
        btnCancel=findViewById<Button>(R.id.btnCancel)


        tv_advamt=findViewById<TextView>(R.id.tv_advamt)
        tv_servicecharge=findViewById<TextView>(R.id.tv_servicecharge)
        tv_productcharge=findViewById<TextView>(R.id.tv_productcharge)
        tv_securityamnt=findViewById<TextView>(R.id.tv_securityamnt)
        tv_discountamnt=findViewById<TextView>(R.id.tv_discountamnt)
        tv_netamnt=findViewById<TextView>(R.id.tv_netamnt)

        recyler_service = findViewById<RecyclerView>(R.id.recyler_service)
        recycler_product = findViewById<RecyclerView>(R.id.recycler_product)


        llsecurityamt = findViewById<LinearLayout>(R.id.llsecurityamt)
        lldisamnt = findViewById<LinearLayout>(R.id.lldisamnt)
        llnetamnt = findViewById<LinearLayout>(R.id.llnetamnt)
        lladvamnt = findViewById<LinearLayout>(R.id.lladvamnt)

        tv_customer!!.text=customer_name
        tv_address!!.text=customer_address
        tv_ticketno!!.text=TicketNo
        tv_date!!.text=date_invoice


        imback!!.setOnClickListener(this)
        btnShare!!.setOnClickListener(this)
        btnCancel!!.setOnClickListener(this)

        modeClosed = 0
        getClosedTicketList()


    }

    private fun getClosedTicketList() {
//        recyServiceList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                closedTicketViewModel.getclosedTicket(this,Idcudtomerregisterdetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (modeClosed == 0) {

                                    modeClosed++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   closedticket   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {




                                        /*  serviceInfoArrayList.put("")
                                          productInfooArrayList.put("")
                                          AmountDetails.put("")*/




                                        val jobjt = jObject.getJSONObject("ServiceInvoice")

                                        serviceInfoArrayList = jobjt.getJSONArray("ServiceInformation")
                                        productInfooArrayList = jobjt.getJSONArray("ProductInformation")
                                        AmountDetails = jobjt.getJSONArray("AmountDetails")

                                        if (serviceInfoArrayList.length() > 0) {



                                            Log.e(TAG, "service info list size   " + serviceInfoArrayList.length())

                                            val lLayout = GridLayoutManager(this@ServiceInvoiceActivity, 1)
                                            recyler_service!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ServiceInfoListAdapter(this@ServiceInvoiceActivity,serviceInfoArrayList!!)
                                            recyler_service!!.adapter = adapter

                                            adapter!!.notifyDataSetChanged()

                                        }

                                        if (productInfooArrayList.length()>0)
                                        {




                                            Log.e(TAG, "product info list size   " + productInfooArrayList.length())

                                            val lLayout = GridLayoutManager(this@ServiceInvoiceActivity, 1)
                                            recycler_product!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = ProductInfoListAdapter(this@ServiceInvoiceActivity,productInfooArrayList!!)
                                            recycler_product!!.adapter = adapter
                                        }
                                        if (AmountDetails.length()>0)
                                        {
                                            Log.e(TAG, "amount info list size   " + AmountDetails.length())

                                            //    holder.txt_mrp.text        = Config.changeTwoDecimel(jsonObject!!.getString("MRP"))


                                            // tv_advamt!!.text=AmountDetails.getJSONObject(0).getString("AdvanceAmount")
                                            tv_advamt!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("AdvanceAmount"))
                                            //  tv_servicecharge!!.text=AmountDetails.getJSONObject(0).getString("ServiceCharge")
                                            tv_servicecharge!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("ServiceCharge"))
                                            //  tv_servicecharge!!.text=AmountDetails.getJSONObject(0).getString("ServiceCharge")

//                                        if (AmountDetails.getJSONObject(0).getString("SecurityAmount").equals("0.0"))

                                            var securityamont=0.0
                                            var discountamont=0.0
                                            securityamont=AmountDetails.getJSONObject(0).getString("SecurityAmount").toDouble()

                                            //         tv_securityamnt!!.text=AmountDetails.getJSONObject(0).getString("SecurityAmount")
                                            tv_securityamnt!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("SecurityAmount"))
                                            //  tv_productcharge!!.text=AmountDetails.getJSONObject(0).getString("ProductCharge")
                                            tv_productcharge!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("ProductCharge"))
                                            // tv_discountamnt!!.text=AmountDetails.getJSONObject(0).getString("DiscountAmount")
                                            tv_discountamnt!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("DiscountAmount"))
                                            //      tv_netamnt!!.text=AmountDetails.getJSONObject(0).getString("NetAmount")
                                            tv_netamnt!!.text=rupee+" "+Config.changeTwoDecimel(AmountDetails.getJSONObject(0).getString("NetAmount"))

                                            AdvanceAmount=AmountDetails.getJSONObject(0).getString("AdvanceAmount").toDouble()
                                            SecurityAmount=AmountDetails.getJSONObject(0).getString("SecurityAmount").toDouble()
                                            ServiceCharge=AmountDetails.getJSONObject(0).getString("ServiceCharge").toDouble()
                                            DiscountAmount=AmountDetails.getJSONObject(0).getString("DiscountAmount").toDouble()
                                            ProductCharge=AmountDetails.getJSONObject(0).getString("ProductCharge").toDouble()
                                            NetAmount=AmountDetails.getJSONObject(0).getString("NetAmount").toDouble()


                                            if (AmountDetails.getJSONObject(0).getString("SecurityAmount").toDouble()==0.0)
                                            {
                                                llsecurityamt!!.visibility=View.GONE
                                            }

                                            if (AmountDetails.getJSONObject(0).getString("DiscountAmount").toDouble()==0.0)
                                            {
                                                lldisamnt!!.visibility=View.GONE
                                            }
                                            if (AmountDetails.getJSONObject(0).getString("NetAmount").toDouble()==0.0)
                                            {
                                                llnetamnt!!.visibility=View.GONE
                                            }

                                            if (AmountDetails.getJSONObject(0).getString("AdvanceAmount").toDouble()==0.0)
                                            {
                                                lladvamnt!!.visibility=View.GONE
                                            }





                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceInvoiceActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }



                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imback -> {
                finish()
            }

            R.id.btnShare -> {
                popUpShare(customer_name,TicketNo,customer_address,AdvanceAmount,SecurityAmount,ServiceCharge,DiscountAmount,ProductCharge,NetAmount)
            }
            R.id.btnCancel -> {
                finish()
            }


        }
    }

    @SuppressLint("MissingInflatedId")
    private fun popUpShare(
        customer_name: String?,
        TicketNo: String?,
        customer_address: String?,
        AdvanceAmount: Double?,
        SecurityAmount: Double?,
        ServiceCharge: Double?,
        DiscountAmount: Double?,
        ProductCharge: Double?,
        NetAmount: Double?
    ) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.popup_share, null)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(true);
        val tv_customer = dialogView.findViewById(R.id.tv_customer)as TextView
        val tv_ticketnumber = dialogView.findViewById(R.id.tv_ticketnumber)as TextView
        val tv_customeraddress = dialogView.findViewById(R.id.tv_customeraddress)as TextView
        val tv_advanceamnt = dialogView.findViewById(R.id.tv_advanceamnt)as TextView
        val tv_securityamnt = dialogView.findViewById(R.id.tv_securityamnt)as TextView

        val tv_servicecharge = dialogView.findViewById(R.id.tv_servicecharge)as TextView
        val tv_discountamnt = dialogView.findViewById(R.id.tv_discountamnt)as TextView
        val tv_productcharge = dialogView.findViewById(R.id.tv_productcharge)as TextView
        val tv_netamount = dialogView.findViewById(R.id.tv_netamount)as TextView

        val lnr_success= dialogView.findViewById(R.id.lnr_success) as LinearLayout
        val llservicecharge= dialogView.findViewById(R.id.llservicecharge) as LinearLayout
        val lldiscountamnt= dialogView.findViewById(R.id.lldiscountamnt) as LinearLayout
        val llsecurityamnt= dialogView.findViewById(R.id.llsecurityamnt) as LinearLayout
        val lladdress= dialogView.findViewById(R.id.lladdress) as LinearLayout

        if (customer_address.equals(""))
        {
            lladdress.visibility=View.GONE
        }


        if (SecurityAmount==0.0)
        {
            llsecurityamnt.visibility=View.GONE
        }

        if (ServiceCharge==0.0)
        {
            llservicecharge.visibility=View.GONE
        }

        if (DiscountAmount==0.0)
        {
            lldiscountamnt.visibility=View.GONE
        }





        val btnShare = dialogView.findViewById(R.id.btnShare) as TextView

        tv_customer.text=customer_name
        tv_ticketnumber.text=TicketNo
        tv_customeraddress.text=customer_address


     //   tv_advanceamnt.text=rupee+" "+AdvanceAmount.toString()
        tv_advanceamnt.text=rupee+" "+Config.changeTwoDecimel(AdvanceAmount.toString())
      //  tv_securityamnt.text=SecurityAmount.toString()
        tv_securityamnt.text=rupee+" "+Config.changeTwoDecimel(SecurityAmount.toString())
      //  tv_servicecharge.text=ServiceCharge.toString()
        tv_servicecharge.text=rupee+" "+Config.changeTwoDecimel(ServiceCharge.toString())


//        tv_discountamnt.text=DiscountAmount.toString()
        tv_discountamnt.text=rupee+" "+Config.changeTwoDecimel(DiscountAmount.toString())
     //   tv_productcharge.text=ProductCharge.toString()
        tv_productcharge.text=rupee+" "+Config.changeTwoDecimel(ProductCharge.toString())
     //   tv_netamount.text=NetAmount.toString()
        tv_netamount.text=rupee+" "+Config.changeTwoDecimel(NetAmount.toString())

        sharelayout(dialogView)   //share layout





        val alertDialog = dialogBuilder.create()

        btnShare.setOnClickListener {
//            Log.e(TAG, "share")
//
//
//            val bitmap = Bitmap.createBitmap(
//                lnr_success.width,
//                lnr_success.height, Bitmap.Config.ARGB_8888
//            )
//
//
//            val canvas = Canvas(bitmap)
//            canvas.drawColor(Color.WHITE)
//            lnr_success.draw(canvas)
//
//
//
//            try {
//
//
//
////                val file: File = saveBitmap(
////                    bitmap,
////                    "invoice" + "_" + System.currentTimeMillis() + ".png"
////                )!!
//
//                val file: File = saveBitmap(
//                    bitmap,
//                    "Invoice" + "_" + System.currentTimeMillis() + ".png"
//                )!!
//
//                Log.e(TAG, "filepath"+file.absolutePath)
//                val bmpUri = Uri.fromFile(file)
//
////                val bmpUri = FileProvider.getUriForFile(
////                    this@ServiceInvoiceActivity,
////                    BuildConfig.APPLICATION_ID + ".provider",
////                    file
////                )
//
//                Log.i("Uri", bmpUri.toString())
//
//
//                // Uri bmpUri = getLocalBitmapUri(bitmap);
//                val shareIntent = Intent()
//                shareIntent.action = Intent.ACTION_SEND
//                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
//                shareIntent.type = "image/*"
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                //    shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                //    shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                startActivity(Intent.createChooser(shareIntent, "Share Opportunity"));
//
//
//
//
//
//
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//                Log.e(TAG, "Exception   2496   "+e.toString())
//            }



//...................


            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "image/*"
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            sendIntent.putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    this@ServiceInvoiceActivity,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    file
                )
            )
            startActivity(Intent.createChooser(sendIntent, "Share "))
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(true)
        alertDialog .show()
    }

    private fun sharelayout(customLayout2: View) {

        val view: LinearLayout
        view = customLayout2.findViewById(R.id.lnr_success)
        view.isDrawingCacheEnabled = true
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache(false)
        bitmapt = Bitmap.createBitmap(view.drawingCache)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "invoice.png")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Log.v(
                    "fdsfsdfd",
                    "directory  " + getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                )
            }


            val stream: FileOutputStream = FileOutputStream(file)
            bitmapt!!.compress(
                Bitmap.CompressFormat.PNG,
                90,
                stream
            )
            stream.close()
            uri = Uri.fromFile(file)
        } catch (e: IOException) {
            Log.v("fdsfsdfd", "IOException while trying to write file for sharing: " + e.message)
        }

    }


    private fun saveBitmap(bm: Bitmap, fileName: String): File? {

        //deprecated issue solved -changes in manifest(legacy)



        val docsFolder =
            File(Environment.getExternalStorageDirectory().toString() + "/Download" + "/")

//        val docsFolder =
//            File(Environment.getExternalStorageDirectory().toString())


        Log.e("photoURI", "StatementDownloadViewActivity   5682   ")
        if (!docsFolder.exists()) {
            // isPresent = docsFolder.mkdir();
            docsFolder.mkdir()
            Log.e("photoURI", "StatementDownloadViewActivity   5683   ")
        }
        val file = File(docsFolder, fileName)
        Log.i("Filess", file.toString())
        Log.e("photoURI", "StatementDownloadViewActivity   5686==   "+file.toString())
        if (file.exists()) {
            file.delete()
        }
        try {
            val fOut = FileOutputStream(file)
            bm.setHasAlpha(true)
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return file
    }

    override fun onRestart() {
        super.onRestart()
        //serviceList = 0
//        getClosedTicketList()
    }

}