package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ProductEnquiryListAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var mode: Int):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductCategoryAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var vh: RecyclerView.ViewHolder
        var v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_enquiry_list, parent, false)
        if (mode == 1){
            v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_enquiry_list, parent, false)

        }
        if (mode == 2){
             v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product_similar, parent, false)
        }
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.ll_topimage.visibility = View.GONE
                holder.txtProdct.text        = jsonObject!!.getString("Name")
                holder.txtProdct_mrp.text        = "₹ "+jsonObject!!.getString("MRP")
                holder.txtProdct_sales.text        = "₹ "+jsonObject!!.getString("SalPrice")
                holder.txtProdct_mrp!!.setPaintFlags(holder.txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                if (jsonObject!!.getString("CurrentQuantity").equals("")){
                    holder.txtProdct_qty.text ="Out of Stock"
                }else{
                    var fQty = jsonObject!!.getString("CurrentQuantity").toFloat()
                    if (fQty <= 0){
                        holder.txtProdct_qty.text ="Out of Stock"
                    }else{
                        holder.txtProdct_qty.text ="Only "+jsonObject!!.getString("CurrentQuantity")+" left"
                    }
                }

                val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
                var IMAGEURL = IMAGE_URLSP.getString("IMAGE_URL","")
                val productImage  = IMAGEURL + jsonObject!!.getString("Image")
                PicassoTrustAll.getInstance(context)!!.load(productImage).error(R.drawable.svg_noimage).into(holder.img_product)

                holder.ll_product_list!!.setTag(position)
                holder.ll_product_list!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "productEnquiryList")
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
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
        internal var txtProdct_mrp      : TextView
        internal var txtProdct_sales    : TextView
        internal var txtProdct_qty      : TextView
        internal var img_product        : ImageView
        internal var ll_product_list    : LinearLayout
        internal var ll_topimage    : LinearLayout
        init {
            img_product           = v.findViewById<View>(R.id.img_product) as ImageView
            txtProdct             = v.findViewById<View>(R.id.txtProdct) as TextView
            txtProdct_mrp         = v.findViewById<View>(R.id.txtProdct_mrp) as TextView
            txtProdct_sales       = v.findViewById<View>(R.id.txtProdct_sales) as TextView
            txtProdct_qty         = v.findViewById<View>(R.id.txtProdct_qty) as TextView
            ll_product_list       = v.findViewById<View>(R.id.ll_product_list) as LinearLayout
            ll_topimage       = v.findViewById<View>(R.id.ll_topimage) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}