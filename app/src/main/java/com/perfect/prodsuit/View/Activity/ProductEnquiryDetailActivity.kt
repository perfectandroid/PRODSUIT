package com.perfect.prodsuit.View.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProductSimilarAdapter
import com.perfect.prodsuit.View.Adapter.ProductViewPagerAdapter
import com.perfect.prodsuit.Viewmodel.ProductDetailViewModel
import com.perfect.prodsuit.Viewmodel.ProductEnquiryDetailViewModel
import me.relex.circleindicator.CircleIndicator
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ProductEnquiryDetailActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG = "ProductEnquiryDetailActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var productEnquiryDetailViewModel: ProductEnquiryDetailViewModel
    var detailCount = 0


    private var mPager: ViewPager? = null
    private lateinit var dotsLayout: LinearLayout
    private var indicator: CircleIndicator? = null
    private var currentPage = 0
    val XMENArray = ArrayList<String>()

    var jsonObj: JSONObject? = null
    var txtProdct : TextView? = null
    var txtProdctDesc : TextView? = null
    var txtProdct_mrp : TextView? = null
    var txtProdct_sales : TextView? = null
    var txtProdct_qty : TextView? = null
    var ll_main : LinearLayout? = null
    var ll_noimage : LinearLayout? = null
    var ll_viewPager : LinearLayout? = null


    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    private var recycSimilarItem: RecyclerView? = null
    private var productCount = 0
    lateinit var jresult: JSONArray

//    private var nestedscroll: NestedScrollView? = null

    var ID_Category: String? = ""
    var ID_Product: String? = ""
    var strName: String? = ""
    var strMRP: String? = ""
    var strSalPrice: String? = ""
    var strCode: String? = ""
    var strCurQty: String? = ""
    private var modelg = 2 // 1 = List 2 =  Grid
    var img_list: ImageView? = null
    var img_grid: ImageView? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_product_enquiry_detail)
        context = this@ProductEnquiryDetailActivity

        productEnquiryDetailViewModel = ViewModelProvider(this).get(ProductEnquiryDetailViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        setRegViews()

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        getDetail()


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    private fun setGridList() {
        if (modelg == 1){
            img_list!!.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
            img_grid!!.setColorFilter(ContextCompat.getColor(this, R.color.grey))
        }
        if (modelg == 2){
            img_list!!.setColorFilter(ContextCompat.getColor(this, R.color.grey))
            img_grid!!.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
        }
    }

    private fun getDetail() {
        Log.e(TAG,"577   "+jsonObj)
        // txtProdct!!.text = jsonObj!!.getString("Name")
        strName = jsonObj!!.getString("Name")
        strMRP = Config.changeTwoDecimel(jsonObj!!.getString("MRP"))
        strSalPrice = Config.changeTwoDecimel(jsonObj!!.getString("SalPrice"))
        strCode = jsonObj!!.getString("Code")
        strCurQty = Config.changeTwoDecimel(jsonObj!!.getString("CurrentQuantity"))
        ID_Category = jsonObj!!.getString("ID_Category")
        ID_Product = jsonObj!!.getString("ID_Product")
//        txtProdct_mrp!!.text = "₹ "+jsonObj!!.getString("MRP")
//        txtProdct_sales!!.text = "₹ "+jsonObj!!.getString("SalPrice")
//        txtProdct_mrp!!.setPaintFlags(txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        detailCount = 0
        getDetailList()
        //  getProductDetail("0")
    }

    private fun getDetailList() {
        setGridList()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                ll_main!!.visibility = View.GONE
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productEnquiryDetailViewModel.getProductEnquiryDetail(this,ID_Category!!,ID_Product!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (detailCount == 0) {
                                detailCount++

                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    ll_main!!.visibility = View.VISIBLE
                                    prodDetailArrayList = JSONArray()
                                    jresult = JSONArray()
                                    Log.e(TAG,"jObject  12001  "+jObject)
                                    val jsonObj: JSONObject = jObject.getJSONObject("ProductEnquiryDetails")
                                    jresult = jsonObj.getJSONArray("ImageList")
                                    Log.e(TAG,"jresult  12002  "+jresult)

//                                    txtProdct!!.setText(strName)
//                                    txtProdctDesc!!.text = jsonObj!!.getString("ProductDescription")
//                                    txtProdct_mrp!!.setText(strMRP)
//                                    txtProdct_sales!!.setText(strSalPrice)
//                                    txtProdct_mrp!!.setPaintFlags(txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
//
//                                    if (strCurQty.equals("")){
//                                        txtProdct_qty!!.text ="Out of Stock"
//                                    }else{
//                                        var fQty = strCurQty!!.toFloat()
//                                        if (fQty <= 0){
//                                            txtProdct_qty!!.text ="Out of Stock"
//                                        }else{
//                                            txtProdct_qty!!.text ="Only "+strCurQty+" left"
//                                        }
//                                    }
//
//
//                                    if (jresult!!.length()>0){
//
//                                        ll_noimage!!.visibility = View.GONE
//                                        ll_viewPager!!.visibility = View.VISIBLE
//                                        XMENArray.clear()
//                                        for (i in 0 until jresult!!.length()) {
//                                            try {
//                                                val json = jresult!!.getJSONObject(i)
//                                                var s = "" + json.getString("Image")
////
////                                            XMENArray!!.add(s)
////                                            viewPager!!.adapter = BannerAdapter(
////                                                this@ProductEnquiryDetailActivity,
////                                                XMENArray
////                                            )
////                                            indicator!!.setViewPager(viewPager)
//
//                                                XMENArray!!.add(s)
//                                                mPager!!.adapter = ProductViewPagerAdapter(
//                                                    this@ProductEnquiryDetailActivity,
//                                                    XMENArray
//                                                )
//                                                indicator!!.setViewPager(mPager)
//
//
//                                            } catch (e: Exception) {
//                                                Log.e(TAG,"Exception  12001  "+e.toString())
//                                            }
//                                        }
//                                        //  mPager!!.setPageTransformer(true, CubeInScalingAnimation())
//                                        val handler = Handler()
//                                        val Update = Runnable {
//                                            //Log.e("TAG","currentPage  438   "+currentPage+"   "+jresult!!.length())
//                                            if (currentPage == jresult!!.length()) {
//                                                currentPage = 0
//                                            }
//                                            mPager!!.setCurrentItem(currentPage++, true)
//
//                                        }
//                                        val swipeTimer = Timer()
//                                        swipeTimer.schedule(object : TimerTask() {
//                                            override fun run() {
//                                                handler.post(Update)
//                                            }
//                                        }, 100, 3000)
//                                    }
//                                    else{
//                                        ll_noimage!!.visibility = View.VISIBLE
//                                        ll_viewPager!!.visibility = View.GONE
//                                    }

                                    prodDetailArrayList = jsonObj.getJSONArray("RelatedItem")
                                    Log.e(TAG,"prodDetailArrayList  1666666  "+prodDetailArrayList.length())
                                    if (prodDetailArrayList.length() > 0) {
                                        Log.e(TAG, "msg   2271   " + prodDetailArrayList)
                                        Log.e(TAG, "msg   2271001   ")
                                        val lLayout = GridLayoutManager(this@ProductEnquiryDetailActivity, modelg)
                                        lLayout.setSpanSizeLookup(object :
                                            GridLayoutManager.SpanSizeLookup() {
                                            override fun getSpanSize(position: Int): Int {
                                                return if (position == 0) 2 else 1
                                            }
                                        })
                                        recycSimilarItem!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        recycSimilarItem!!.isNestedScrollingEnabled = true
                                        recycSimilarItem!!.setHasFixedSize(true)
                                        val adapter = ProductSimilarAdapter(this@ProductEnquiryDetailActivity, prodDetailArrayList,modelg,jresult,strName!!,strMRP!!,strSalPrice!!,strCurQty!!)
                                        recycSimilarItem!!.adapter = adapter
//                                        nestedscroll!!.smoothScrollTo(0, 0)
                                        adapter.setClickListener(this@ProductEnquiryDetailActivity)
//                                        recycSimilarItem!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                                            private var lastDy = 0
//
//                                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                                                // dy will be positive when scrolling down and negative when scrolling up
//                                                if (dy > 0) {
//                                                    // Scrolling downwards
//                                                    Log.e(TAG, "Scrolling downwards   227100200   ")
//                                                } else {
//                                                    // Scrolling upwards
//                                                    Log.e(TAG, "Scrolling upwards   227100200   ")
//                                                }
//
//                                                // Optionally, you can check if the RecyclerView has reached the top or bottom
//                                                if (!recyclerView.canScrollVertically(1)) {
//                                                    // Reached the bottom of the RecyclerView
//                                                } else if (!recyclerView.canScrollVertically(-1)) {
//                                                    // Reached the top of the RecyclerView
//                                                }
//
//                                                // Save the last dy value to calculate the scrolling direction
//                                                lastDy = dy
//                                            }
//
//                                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                                                // Do nothing here
//                                            }
//                                        })
                                        Log.e(TAG, "msg   2271002   ")
                                    }





                                } else {
                                    ll_main!!.visibility = View.GONE
                                    val builder = AlertDialog.Builder(
                                            this@ProductEnquiryDetailActivity,
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
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
                        }
                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        mPager = findViewById(R.id.viewPager)
    //    dotsLayout = findViewById(R.id.dotsLayout)
        indicator =findViewById(R.id.indicator)
        txtProdct = findViewById(R.id.txtProdct)
        txtProdctDesc = findViewById(R.id.txtProdctDesc)
        txtProdct_mrp = findViewById(R.id.txtProdct_mrp)
        txtProdct_sales = findViewById(R.id.txtProdct_sales)
        txtProdct_qty = findViewById(R.id.txtProdct_qty)
        ll_main = findViewById(R.id.ll_main)
        ll_viewPager = findViewById(R.id.ll_viewPager)
        ll_noimage = findViewById(R.id.ll_noimage)
     //   nestedscroll = findViewById(R.id.nestedscroll)
        img_list = findViewById<ImageView>(R.id.img_list)
        img_grid = findViewById<ImageView>(R.id.img_grid)

        recycSimilarItem = findViewById(R.id.recycSimilarItem)

        imback!!.setOnClickListener(this)
        img_list!!.setOnClickListener(this)
        img_grid!!.setOnClickListener(this)

//        recycSimilarItem!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val totalItemCount = layoutManager.itemCount
//                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//
//                // Check if the last visible item is the last item in the list
//                Log.e(TAG,"318888   "+totalItemCount)
//                if (lastVisibleItem == totalItemCount - 1) {
//                    Log.e(TAG,"318888   "+totalItemCount)
//                    // This means the RecyclerView has been scrolled to the bottom
//                    // Perform any action you want when the RecyclerView reaches the bottom
//                }
//            }
//        })


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.img_grid->{

                modelg = 2
                changeProductDesign()
            }
            R.id.img_list->{
                modelg = 1
                changeProductDesign()

            }

        }
    }

    private fun changeProductDesign() {
        try {
          //  setGridList()
            if (prodDetailArrayList.length() > 0) {
                Log.e(TAG, "msg   2271   " + prodDetailArrayList)
                Log.e(TAG, "msg   2271001   ")

                val lLayout = GridLayoutManager(this@ProductEnquiryDetailActivity, modelg)
//                lLayout.setSpanSizeLookup(object :
//                    GridLayoutManager.SpanSizeLookup() {
//                    override fun getSpanSize(position: Int): Int {
//                        Log.e(TAG,"position  39000   "+position+"  "+modelg)
////                        var spCount = 1
////                        if (modelg == 1){
////                            spCount = 1
////                        }
////                        if (modelg == 2){
////                            spCount = 2
////                        }
////                        if (position == modelg){
////                            spCount =
////                        }
//                     //   return modelg
//                        return if (position == 0) 1 else 2
//                    }
//                })
                recycSimilarItem!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = ProductSimilarAdapter(this@ProductEnquiryDetailActivity, prodDetailArrayList,modelg,jresult,strName!!,strMRP!!,strSalPrice!!,strCurQty!!)
                recycSimilarItem!!.adapter = adapter
//                nestedscroll!!.smoothScrollTo(0, 0)
                adapter.setClickListener(this@ProductEnquiryDetailActivity)
                Log.e(TAG, "msg   2271002   ")
            }


        }catch (e: Exception){

        }
    }


    private fun getProductDetail(ID_Category: String) {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productDetailViewModel.getProductDetail(this, ID_Category)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (productCount == 0) {
                                    productCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductDetailsList")
                                        prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                        if (prodDetailArrayList.length() > 0) {
                                            Log.e(TAG, "msg   2271   " + prodDetailArrayList)
                                            val lLayout = GridLayoutManager(this@ProductEnquiryDetailActivity, modelg)
                                            lLayout.setSpanSizeLookup(object :
                                                GridLayoutManager.SpanSizeLookup() {
                                                override fun getSpanSize(position: Int): Int {
                                                    return if (position == 0) 2 else 1
                                                }
                                            })
                                            recycSimilarItem!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ProductSimilarAdapter(this@ProductEnquiryDetailActivity, prodDetailArrayList,modelg,jresult,strName!!,strMRP!!,strSalPrice!!,strCurQty!!)
                                            recycSimilarItem!!.adapter = adapter

                                        //    adapter.setClickListener(this@ProductEnquiryDetailActivity)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProductEnquiryDetailActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }

    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("productEnquiryDetails")) {

            Log.e(TAG,"ggggggg   "+position)

            val jsonObject = prodDetailArrayList.getJSONObject(position)

            strName = jsonObject!!.getString("Name")
            strMRP = jsonObject!!.getString("MRP")
            strSalPrice = jsonObject!!.getString("SalPrice")
            strCode = jsonObject!!.getString("Code")
            strCurQty = jsonObject!!.getString("CurrentQuantity")
            ID_Category = jsonObject!!.getString("ID_Category")
            ID_Product = jsonObject!!.getString("ID_Product")
//        txtProdct_mrp!!.text = "₹ "+jsonObj!!.getString("MRP")
//        txtProdct_sales!!.text = "₹ "+jsonObj!!.getString("SalPrice")
//        txtProdct_mrp!!.setPaintFlags(txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            detailCount = 0
            getDetailList()

        }

        if (data.equals("Clickimggrid")) {

            Log.e(TAG,"ggggggg  Clickimggrid   "+position)
            modelg = 2
            changeProductDesign()
        }

        if (data.equals("Clickimglist")) {
            Log.e(TAG,"ggggggg Clickimglist  "+position)
            modelg = 1
            changeProductDesign()

        }
    }

    fun smoothScrollToPosition(recyclerView: RecyclerView, position: Int) {
        val smoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun getVerticalSnapPreference(): Int {
                // Set the scrolling behavior here.
                // SNAP_TO_START will scroll the item to the top of the RecyclerView
                // SNAP_TO_END will scroll the item to the bottom of the RecyclerView
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = position
        recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}