package com.perfect.prodsuit.View.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ProductViewPagerAdapter
import com.perfect.prodsuit.Viewmodel.ProductEnquiryDetailViewModel
import me.relex.circleindicator.CircleIndicator
import org.json.JSONObject
import java.util.*


class ProductEnquiryDetailActivity : AppCompatActivity(), View.OnClickListener{

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_product_enquiry_detail)
        context = this@ProductEnquiryDetailActivity

        productEnquiryDetailViewModel = ViewModelProvider(this).get(ProductEnquiryDetailViewModel::class.java)

        setRegViews()

        detailCount = 0
        getDetailList()



//        val sliderItems = listOf(
//            SliderItem(R.drawable.svg_service_banner),
//            SliderItem(R.drawable.svg_walking_customer),
//            SliderItem(R.drawable.welcomelogo)
//        )
//
//        val sliderAdapter = ProductViewPagerAdapter(sliderItems)
//        viewPager.adapter = sliderAdapter!!
//       // viewPager.setOnClickListener(this@ProductEnquiryDetailActivity)
//
//        // Add dot indicators
//        addDotsIndicator(sliderItems.size)
//
//        // Attach a ViewPager2.OnPageChangeCallback to update dot indicators
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                updateDotsIndicator(position)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//
//            }
//        })





    }

    private fun getDetailList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productEnquiryDetailViewModel.getProductEnquiryDetail(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (detailCount == 0) {
                                detailCount++

                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    Log.e(TAG,"jObject  12001  "+jObject)
                                    val jsonObj: JSONObject = jObject.getJSONObject("BannerDetails")
                                    val jresult = jsonObj.getJSONArray("BannerDetailsList")
                                    Log.e(TAG,"jresult  12002  "+jresult)

                                    for (i in 0 until jresult!!.length()) {
                                        try {
                                            val json = jresult!!.getJSONObject(i)
                                            var s = "" + json.getString("ImagePath")
//
//                                            XMENArray!!.add(s)
//                                            viewPager!!.adapter = BannerAdapter(
//                                                this@ProductEnquiryDetailActivity,
//                                                XMENArray
//                                            )
//                                            indicator!!.setViewPager(viewPager)

                                            XMENArray!!.add(s)
                                            mPager!!.adapter = ProductViewPagerAdapter(
                                                this@ProductEnquiryDetailActivity,
                                                XMENArray
                                            )
                                            indicator!!.setViewPager(mPager)


                                        } catch (e: Exception) {
                                            Log.e(TAG,"Exception  12001  "+e.toString())
                                        }
                                    }

                                    //  mPager!!.setPageTransformer(true, CubeInScalingAnimation())
                                    val handler = Handler()
                                    val Update = Runnable {
                                        //Log.e("TAG","currentPage  438   "+currentPage+"   "+jresult!!.length())
                                        if (currentPage == jresult!!.length()) {
                                            currentPage = 0
                                        }
                                        mPager!!.setCurrentItem(currentPage++, true)

                                    }
                                    val swipeTimer = Timer()
                                    swipeTimer.schedule(object : TimerTask() {
                                        override fun run() {
                                            handler.post(Update)
                                        }
                                    }, 3000, 3000)



                                } else {
//                                    val builder = AlertDialog.Builder(
//                                            this@HomeActivity,
//                                            R.style.MyDialogTheme
//                                    )
//                                    builder.setMessage(jObject.getString("EXMessage"))
//                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    }
//                                    val alertDialog: AlertDialog = builder.create()
//                                    alertDialog.setCancelable(false)
//                                    alertDialog.show()
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
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        mPager = findViewById(R.id.viewPager)
//    //    dotsLayout = findViewById(R.id.dotsLayout)
        indicator =findViewById(R.id.indicator)
        imback!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }
//    private fun addDotsIndicator(numDots: Int) {
//        val dots = arrayOfNulls<ImageView>(numDots)
//        val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
//        val dotMargin = resources.getDimensionPixelSize(R.dimen.dot_margin)
//
//        for (i in 0 until numDots) {
//            dots[i] = ImageView(this)
//            dots[i]?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_inactive))
//            val params = LinearLayout.LayoutParams(dotSize, dotSize)
//            params.setMargins(dotMargin, 0, dotMargin, 0)
//            dotsLayout.addView(dots[i], params)
//        }
//
//        // Set the first dot as active
//        dots[0]?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_active))
//    }
//
//    private fun updateDotsIndicator(currentDot: Int) {
//        val numDots = dotsLayout.childCount
//        for (i in 0 until numDots) {
//            val dot = dotsLayout.getChildAt(i) as ImageView
//            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_inactive))
//        }
//        (dotsLayout.getChildAt(currentDot) as ImageView).setImageDrawable(
//            ContextCompat.getDrawable(this, R.drawable.dot_indicator_active)
//        )
//    }
}