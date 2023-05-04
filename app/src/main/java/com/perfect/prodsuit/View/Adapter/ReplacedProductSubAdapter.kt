package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ReplacedProductSubAdapter(internal var context: Context, internal var jsonArray: JSONArray):
RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ReplacedProductSubAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerValue? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_replaced_product_sub, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.txtSino.text        = pos.toString()
                holder.txtProduct.text        = jsonObject!!.getString("Name")

                holder.llProduct!!.setTag(position)
                holder.llProduct!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "popUpProductsSelect","0")
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
        internal var txtSino          : TextView
        internal var txtProduct          : TextView
        internal var llProduct    : LinearLayout
        init {
            txtSino        = v.findViewById<View>(R.id.txtSino) as TextView
            txtProduct        = v.findViewById<View>(R.id.txtProduct) as TextView
            llProduct  = v.findViewById<View>(R.id.llProduct) as LinearLayout
        }
    }


    fun setClickListener(itemClickListener: ItemClickListenerValue?) {
        clickListener = itemClickListener
    }



}