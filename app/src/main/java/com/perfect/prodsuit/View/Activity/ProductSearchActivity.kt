package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.impulsive.zoomimageview.ZoomImageView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProductCategoryAdapter
import com.perfect.prodsuit.View.Adapter.ProductDetailAdapter
import com.perfect.prodsuit.View.Adapter.ProductEnquiryListAdapter
import com.perfect.prodsuit.Viewmodel.ProductCategoryViewModel
import com.perfect.prodsuit.Viewmodel.ProductDetailViewModel
import com.perfect.prodsuit.Viewmodel.ProductEnquiryViewModel
import org.json.JSONArray
import org.json.JSONObject

class ProductSearchActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG = "ProductSearchActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var mSwitch :Switch? = null
    var txt_mSwitch :TextView? = null

    var tie_Category: TextInputEditText? = null
    var tie_Product: TextInputEditText? = null

    var til_Category: TextInputLayout? = null
    var til_Product: TextInputLayout? = null

    var ll_main_page: LinearLayout? = null
    var ll_filter_page: LinearLayout? = null
    var ll_sort: LinearLayout? = null
    var ll_filter: LinearLayout? = null
    var ll_noproduct: LinearLayout? = null

    var img_list: ImageView? = null
    var img_grid: ImageView? = null

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategorySort: JSONArray
    lateinit var prodCategoryArray: JSONArray
    lateinit var prodCategoryArrayList: JSONArray
    private var dialogProdCat: Dialog? = null
    var recyProdCategory: RecyclerView? = null

    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    lateinit var prodDetailSort: JSONArray
    private var dialogProdDet: Dialog? = null
    var recyProdDetail: RecyclerView? = null

    lateinit var productEnquiryViewModel: ProductEnquiryViewModel
    lateinit var prodEnquiryArrayList: JSONArray
    var recycProdEnq: RecyclerView? = null

    private var categotyCount = 0
    private var productCount = 0
    private var enquiryCount = 0

    var isOffersOnly: String? = "0"
    var ID_Category: String? = ""
    var ID_Product: String? = ""

    var isFilter: String? = "0" // 0 = No Filter 1 =  Filter
    private var modelg = 1 // 1 = List 2 =  Grid

    private lateinit var networkChangeReceiver: NetworkChangeReceiver





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_product_search)
        context = this@ProductSearchActivity
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        productEnquiryViewModel = ViewModelProvider(this).get(ProductEnquiryViewModel::class.java)

        setRegViews()

        if (isOffersOnly.equals("0")){
            txt_mSwitch!!.text = "All"
            mSwitch!!.isChecked = false
        }
        mSwitch!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                txt_mSwitch!!.text = "Offers Only"
                isOffersOnly = "1"

                if (!ID_Category.equals("")){
                    enquiryCount = 0
                    getProductEnquiryList()
                }

            } else {
                txt_mSwitch!!.text = "All"
                isOffersOnly = "0"
                if (!ID_Category.equals("")){
                    enquiryCount = 0
                    getProductEnquiryList()
                }
            }
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        val imback_filter = findViewById<ImageView>(R.id.imback_filter)

        mSwitch = findViewById<Switch>(R.id.mSwitch)
        txt_mSwitch = findViewById<TextView>(R.id.txt_mSwitch)

        tie_Category = findViewById<TextInputEditText>(R.id.tie_Category)
        tie_Product = findViewById<TextInputEditText>(R.id.tie_Product)

        til_Category = findViewById<TextInputLayout>(R.id.til_Category)
        til_Product = findViewById<TextInputLayout>(R.id.til_Product)

        ll_main_page = findViewById<LinearLayout>(R.id.ll_main_page)
        ll_filter_page = findViewById<LinearLayout>(R.id.ll_filter_page)
        ll_sort = findViewById<LinearLayout>(R.id.ll_sort)
        ll_filter = findViewById<LinearLayout>(R.id.ll_filter)
        ll_noproduct = findViewById<LinearLayout>(R.id.ll_noproduct)

        img_list = findViewById<ImageView>(R.id.img_list)
        img_grid = findViewById<ImageView>(R.id.img_grid)

        recycProdEnq = findViewById<RecyclerView>(R.id.recycProdEnq)

        imback!!.setOnClickListener(this)
        imback_filter!!.setOnClickListener(this)
        tie_Category!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        ll_main_page!!.setOnClickListener(this)
        ll_filter_page!!.setOnClickListener(this)
        ll_sort!!.setOnClickListener(this)
        ll_filter!!.setOnClickListener(this)
        img_list!!.setOnClickListener(this)
        img_grid!!.setOnClickListener(this)

        ll_main_page!!.visibility = View.VISIBLE
        ll_filter_page!!.visibility = View.GONE

        setGridList()



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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{

                if (isFilter.equals("0")){
                    finish()
                }else{
                    isFilter = "0"
                    ll_main_page!!.visibility = View.VISIBLE
                    ll_filter_page!!.visibility = View.GONE
                }

            }
            R.id.imback_filter->{
                isFilter = "0"
                ll_main_page!!.visibility = View.VISIBLE
                ll_filter_page!!.visibility = View.GONE
            }

            R.id.tie_Category->{
                Config.disableClick(v)
                categotyCount = 0
                getCategory()
            }
            R.id.tie_Product->{
                if (ID_Category.equals("")) {
                    Config.snackBars(applicationContext, v, "Select Category")
                }else{
                    Config.disableClick(v)
                    productCount = 0
                    getProductDetail(ID_Category!!)
                }

            }

            R.id.ll_main_page->{

            }

            R.id.ll_filter_page->{

            }
            R.id.ll_sort->{

            }
            R.id.img_grid->{

                modelg = 2
                changeProductDesign()
            }
            R.id.img_list->{
                modelg = 1
                changeProductDesign()

            }
            R.id.ll_filter->{

                if (isFilter.equals("0")){
                    isFilter = "1"
                    ll_main_page!!.visibility = View.GONE
                    ll_filter_page!!.visibility = View.VISIBLE
                }else{
                    isFilter = "1"
                    ll_main_page!!.visibility = View.VISIBLE
                    ll_filter_page!!.visibility = View.GONE
                }
            }
        }
    }

    private fun changeProductDesign() {
       try {
           setGridList()
           if (prodEnquiryArrayList.length() > 0) {
               ll_noproduct!!.visibility = View.GONE
               val lLayout = GridLayoutManager(this@ProductSearchActivity, modelg)
               recycProdEnq!!.layoutManager = lLayout as RecyclerView.LayoutManager?
               val adapter = ProductEnquiryListAdapter(this@ProductSearchActivity, prodEnquiryArrayList,modelg)
               recycProdEnq!!.adapter = adapter
               adapter.setClickListener(this@ProductSearchActivity)
           }
       }catch (e: Exception){

       }
    }

    private fun getCategory() {
//         var prodcategory = 0
        var ReqMode = "13"
        var SubMode = "0"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this,ReqMode!!,SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (categotyCount == 0) {
                                    categotyCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "174   msg   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        try {
                                            val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                            prodCategoryArray = jobjt.getJSONArray("CategoryList")
                                            prodCategoryArrayList = JSONArray()
                                            Log.e(TAG, "msg   82221   " + prodCategoryArray)
                                            for (k in 0 until prodCategoryArray.length()) {
                                                val jsonObject = prodCategoryArray.getJSONObject(k)
                                                if (!jsonObject!!.getString("Project").equals("1")){
                                                    prodCategoryArrayList.put(jsonObject)
                                                }
                                            }
                                        if (prodCategoryArrayList.length() > 0) {
                                            productCategoryPopup(prodCategoryArrayList)
                                        }
                                              Log.e(TAG, "msg   82222   " + prodCategoryArrayList)
                                        }catch (e: Exception){
                                            Log.e(TAG, "msg   82223   " + e.toString())
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProductSearchActivity,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!!.setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val etsearch = dialogProdCat!!.findViewById(R.id.etsearch) as EditText

            prodCategorySort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCategorySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProductSearchActivity, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductCategoryAdapter(this@ProductSearchActivity, prodCategoryArrayList)
            val adapter = ProductCategoryAdapter(this@ProductSearchActivity, prodCategorySort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@ProductSearchActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodCategorySort = JSONArray()

                    for (k in 0 until prodCategoryArrayList.length()) {
                        val jsonObject = prodCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodCategorySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodCategorySort               7103    " + prodCategorySort)
                    val adapter =
                        ProductCategoryAdapter(this@ProductSearchActivity, prodCategorySort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@ProductSearchActivity)
                }
            })

            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
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
//                                             if (proddetail == 0){
//                                                 proddetail++

                                            productDetailPopup(prodDetailArrayList)
//                                             }

                                        }

                                    } else {

                                        val builder = AlertDialog.Builder(
                                            this@ProductSearchActivity,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText

            prodDetailSort = JSONArray()
            for (k in 0 until prodDetailArrayList.length()) {
                val jsonObject = prodDetailArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodDetailSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProductSearchActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@ProductSearchActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@ProductSearchActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@ProductSearchActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodDetailSort = JSONArray()

                    for (k in 0 until prodDetailArrayList.length()) {
                        val jsonObject = prodDetailArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@ProductSearchActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@ProductSearchActivity)
                }
            })

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    private fun getProductEnquiryList() {
        setGridList()
        var ReqMode = "0"
        recycProdEnq!!.adapter = null
        prodEnquiryArrayList = JSONArray()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productEnquiryViewModel.getProductEnquiry(this, ReqMode,ID_Category!!,ID_Product!!,isOffersOnly!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (enquiryCount == 0) {
                                    enquiryCount++
                                    ll_noproduct!!.visibility = View.VISIBLE
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductEnquiryList")
                                        prodEnquiryArrayList = jobjt.getJSONArray("ProductEnquiryListData")
                                        if (prodEnquiryArrayList.length() > 0) {
                                            ll_noproduct!!.visibility = View.GONE
                                            val lLayout = GridLayoutManager(this@ProductSearchActivity, modelg)
                                            recycProdEnq!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ProductEnquiryListAdapter(this@ProductSearchActivity, prodEnquiryArrayList,modelg)
                                            recycProdEnq!!.adapter = adapter
                                            adapter.setClickListener(this@ProductSearchActivity)
                                        }



                                    } else {
                                        ll_noproduct!!.visibility = View.VISIBLE
                                        val builder = AlertDialog.Builder(
                                            this@ProductSearchActivity,
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

    override fun onClick(position: Int, data: String) {

        if (data.equals("prodcategory")) {
            dialogProdCat!!.dismiss()
//             val jsonObject = prodCategoryArrayList.getJSONObject(position)
            val jsonObject = prodCategorySort.getJSONObject(position)
            Log.e(TAG, "ID_Category   " + jsonObject.getString("ID_Category"))
            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))
            ID_Product = ""
            tie_Product!!.setText("")

            enquiryCount = 0
            getProductEnquiryList()

//           // val jsonObject = prodEnquiryArrayList.getJSONObject(position)
//            val intent = Intent(this, ProductEnquiryDetailActivity::class.java)
//           // intent.putExtra("jsonObject",jsonObject.toString())
//            startActivity(intent)

        }

        if (data.equals("proddetails")) {
            dialogProdDet!!.dismiss()
//             val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)
            Log.e(TAG, "ID_Product   " + jsonObject.getString("ID_Product"))
            ID_Product = jsonObject.getString("ID_Product")
            tie_Product!!.setText(jsonObject.getString("ProductName"))

            enquiryCount = 0
            getProductEnquiryList()
        }

        if (data.equals("productEnquiryList")) {

            val jsonObject = prodEnquiryArrayList.getJSONObject(position)
            val intent = Intent(this, ProductEnquiryDetailActivity::class.java)
            intent.putExtra("jsonObject",jsonObject.toString())
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (isFilter.equals("0")){
            finish()
        }else{
            isFilter = "0"
            ll_main_page!!.visibility = View.VISIBLE
            ll_filter_page!!.visibility = View.GONE
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}