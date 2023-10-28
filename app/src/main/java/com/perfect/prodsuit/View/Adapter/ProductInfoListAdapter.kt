package com.perfect.prodsuit.View.Adapter

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject


class ProductInfoListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductInfoListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerData? = null
    val rupee='\u20B9'

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product_info_invoice, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   "+jsonObject)
          //      holder.txt_scost.text        = Config.changeTwoDecimel(jsonObject!!.getString("ServiceCost"))
                val pos = position+1
                holder.slno.text        = pos.toString()
                holder.txt_product.text        = jsonObject!!.getString("Product")
              //  holder.txt_Qty.text        = jsonObject!!.getString("Quantity")
                holder.txt_Qty.text        = Config.changeTwoDecimel(jsonObject!!.getString("Quantity"))
             //   holder.txt_mrp.text        = jsonObject!!.getString("MRP")
                holder.txt_mrp.text        = rupee+Config.changeTwoDecimel(jsonObject!!.getString("MRP"))
            //    holder.txt_sprice.text        = jsonObject!!.getString("SalePrice")
                holder.txt_sprice.text        = rupee+Config.changeTwoDecimel(jsonObject!!.getString("SalePrice"))
              //  holder.txt_netamt.text        = jsonObject!!.getString("NetAmount")
                holder.txt_netamt.text        = rupee+Config.changeTwoDecimel(jsonObject!!.getString("NetAmount"))


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
        internal var slno          : TextView
        internal var txt_product          : TextView
        internal var txt_Qty          : TextView
        internal var txt_mrp          : TextView
        internal var txt_sprice          : TextView
        internal var txt_netamt          : TextView


        init {
            slno        = v.findViewById<View>(R.id.slno) as TextView
            txt_product        = v.findViewById<View>(R.id.txt_product) as TextView
            txt_Qty        = v.findViewById<View>(R.id.txt_Qty) as TextView
            txt_mrp        = v.findViewById<View>(R.id.txt_mrp) as TextView
            txt_sprice        = v.findViewById<View>(R.id.txt_sprice) as TextView
            txt_netamt        = v.findViewById<View>(R.id.txt_netamt) as TextView


        }
    }

    fun setClickListener(itemClickListener: ItemClickListenerData?) {
        clickListener = itemClickListener
    }
}