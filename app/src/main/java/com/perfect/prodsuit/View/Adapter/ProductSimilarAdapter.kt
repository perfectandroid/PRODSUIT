package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Paint
import android.media.Image
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import me.relex.circleindicator.CircleIndicator
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ProductSimilarAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var mode: Int, internal var jresult: JSONArray,internal var strName : String,internal var strMRP :String ,internal var strSalPrice : String,internal var strCurQty : String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductSimilarAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    private lateinit var dotsLayout: LinearLayout
    private var currentPage = 0
    val XMENArray = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val vh: RecyclerView.ViewHolder
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_similar, parent, false)
//        vh = MainViewHolder(v)
//        return vh

//        val layoutManager = GridLayoutManager(context, 4)
//
//        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if (position > 3) 3 else 5
//            }
//        })


        var vh: RecyclerView.ViewHolder
        var v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_similar, parent, false)
//        if (mode == 1){
//            v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_enquiry_list, parent, false)
//
//        }
//        if (mode == 2){
//            v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_similar, parent, false)
//        }
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
//            val pos = position - 1
//          //  jsonObject = jsonArray.getJSONObject(position)
//            jsonObject = jsonArray.getJSONObject(pos)
            if (holder is MainViewHolder) {


                Log.e(TAG,"onBindViewHolder   105100   ")

                if (position == 0){
                    val jsonObj = jsonArray.getJSONObject(position)
                    if (mode == 1){
                        holder.img_list!!.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
                        holder.img_grid!!.setColorFilter(ContextCompat.getColor(context, R.color.grey))
                    }
                    if (mode == 2){
                        holder.img_list!!.setColorFilter(ContextCompat.getColor(context, R.color.grey))
                        holder.img_grid!!.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
                    }
                    holder.ll_product_list.visibility = View.GONE
                    holder.txtHeadProdct!!.setText(strName)
                    holder.txtHeadProdctDesc!!.text = jsonObj!!.getString("ProductDescription")
                    holder.txtHeadProdct_mrp!!.setText(Config.changeTwoDecimel(strMRP))
                    holder.txtHeadProdct_sales!!.setText(Config.changeTwoDecimel(strSalPrice))
                    holder.txtHeadProdct_mrp!!.setPaintFlags(holder.txtHeadProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                    if (strCurQty.equals("")){
                        holder. txtHeadProdct_qty!!.text ="Out of Stock"
                    }else{
                        var fQty = strCurQty!!.toFloat()
                        if (fQty <= 0){
                            holder.txtHeadProdct_qty!!.text ="Out of Stock"
                        }else{
                            holder.txtHeadProdct_qty!!.text ="Only "+Config.changeTwoDecimel(strCurQty)+" left"
                        }
                    }
                    if (jresult!!.length()>0){

                        holder.ll_noimage!!.visibility = View.GONE
                        holder.ll_viewPager!!.visibility = View.VISIBLE
                        XMENArray.clear()
                        for (i in 0 until jresult!!.length()) {
                            try {
                                val json = jresult!!.getJSONObject(i)
                                var s = "" + json.getString("Image")
//
//                                            XMENArray!!.add(s)
//                                            viewPager!!.adapter = BannerAdapter(
//                                                this@ProductEnquiryDetailActivity,
//                                                XMENArray
//                                            )
//                                            indicator!!.setViewPager(viewPager)

                                XMENArray!!.add(s)
                                holder.mPager!!.adapter = ProductViewPagerAdapter(
                                    context,
                                    XMENArray
                                )
                                holder.indicator!!.setViewPager(holder.mPager)


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
                            holder.mPager!!.setCurrentItem(currentPage++, true)

                        }
                        val swipeTimer = Timer()
                        swipeTimer.schedule(object : TimerTask() {
                            override fun run() {
                                handler.post(Update)
                            }
                        }, 100, 3000)
                    }
                    else{
                        holder.ll_noimage!!.visibility = View.VISIBLE
                        holder.ll_viewPager!!.visibility = View.GONE
                    }

                    holder.img_grid!!.setTag(position)
                    holder.img_grid!!.setOnClickListener(View.OnClickListener {
                        clickListener!!.onClick(position, "Clickimggrid")
//                        clickListener!!.onClick(position, "productEnquiryDetails")
                    })

                    holder.img_list!!.setTag(position)
                    holder.img_list!!.setOnClickListener(View.OnClickListener {
                        clickListener!!.onClick(position, "Clickimglist")
//                        clickListener!!.onClick(position, "productEnquiryDetails")
                    })

                }else{
                    holder.ll_topimage.visibility = View.GONE
                    val pos = position - 1
                    //  jsonObject = jsonArray.getJSONObject(position)
                    jsonObject = jsonArray.getJSONObject(pos)

                    holder.txtProdct.text        = jsonObject!!.getString("Name")
                    holder.txtProdct_mrp.text        = "₹ "+Config.changeTwoDecimel(jsonObject!!.getString("MRP"))
                    holder.txtProdct_sales.text        = "₹ "+Config.changeTwoDecimel(jsonObject!!.getString("SalPrice"))
                    holder.txtProdct_mrp!!.setPaintFlags(holder.txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                    if (jsonObject!!.getString("CurrentQuantity").equals("")){
                        holder.txtProdct_qty.text ="Out of Stock"
                    }else{
                        var fQty = jsonObject!!.getString("CurrentQuantity").toFloat()
                        if (fQty <= 0){
                            holder.txtProdct_qty.text ="Out of Stock"
                        }else{
                            holder.txtProdct_qty.text ="Only "+Config.changeTwoDecimel(jsonObject!!.getString("CurrentQuantity"))+" left"
                        }
                    }

                    val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
                    var IMAGEURL = IMAGE_URLSP.getString("IMAGE_URL","")
                    val productImage  = IMAGEURL + jsonObject!!.getString("Image")
                    PicassoTrustAll.getInstance(context)!!.load(productImage).error(R.drawable.svg_noimage).into(holder.img_product)


                    holder.ll_product_list!!.setTag(position)
                    holder.ll_product_list!!.setOnClickListener(View.OnClickListener {
                        clickListener!!.onClick(pos, "productEnquiryDetails")
//                        clickListener!!.onClick(position, "productEnquiryDetails")
                    })
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105100   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtProdct          : TextView
        internal var txtProdct_mrp          : TextView
        internal var txtProdct_sales          : TextView
        internal var txtProdct_qty          : TextView
        internal var ll_product_list    : LinearLayout
        internal var img_product    : ImageView

        internal var ll_topimage    : LinearLayout
        internal var ll_noimage    : LinearLayout
        internal var ll_viewPager    : LinearLayout
        internal var indicator    : CircleIndicator
        internal var mPager    : ViewPager

        internal var txtHeadProdct    : TextView
        internal var txtHeadProdct_sales    : TextView
        internal var txtHeadProdct_mrp    : TextView
        internal var txtHeadProdct_qty    : TextView
        internal var txtHeadProdctDesc    : TextView

        internal var img_list    : ImageView
        internal var img_grid    : ImageView
        init {
            txtProdct        = v.findViewById<View>(R.id.txtProdct) as TextView
            txtProdct_mrp        = v.findViewById<View>(R.id.txtProdct_mrp) as TextView
            txtProdct_sales        = v.findViewById<View>(R.id.txtProdct_sales) as TextView
            txtProdct_qty        = v.findViewById<View>(R.id.txtProdct_qty) as TextView
            ll_product_list        = v.findViewById<View>(R.id.ll_product_list) as LinearLayout
            img_product        = v.findViewById<View>(R.id.img_product) as ImageView

            ll_topimage        = v.findViewById<View>(R.id.ll_topimage) as LinearLayout
            ll_noimage        = v.findViewById<View>(R.id.ll_noimage) as LinearLayout
            ll_viewPager        = v.findViewById<View>(R.id.ll_viewPager) as LinearLayout
            indicator        = v.findViewById<View>(R.id.indicator) as CircleIndicator
            mPager             = v.findViewById<View>(R.id.viewPager) as ViewPager

            txtHeadProdct             = v.findViewById<View>(R.id.txtHeadProdct) as TextView
            txtHeadProdct_sales             = v.findViewById<View>(R.id.txtHeadProdct_sales) as TextView
            txtHeadProdct_mrp             = v.findViewById<View>(R.id.txtHeadProdct_mrp) as TextView
            txtHeadProdct_qty             = v.findViewById<View>(R.id.txtHeadProdct_qty) as TextView
            txtHeadProdctDesc             = v.findViewById<View>(R.id.txtHeadProdctDesc) as TextView

            img_list             = v.findViewById<View>(R.id.img_list) as ImageView
            img_grid             = v.findViewById<View>(R.id.img_grid) as ImageView


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}