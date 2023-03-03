package com.perfect.prodsuit.View.Adapter

import android.R.attr
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.R.attr.shape

import android.graphics.drawable.ShapeDrawable
import android.R.attr.shape
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.shapes.OvalShape
import android.widget.EditText
import com.perfect.prodsuit.Helper.ItemClickListenerData

class ProductAdapter(
    internal var jsonArray: JSONArray,
    internal var edtProduct: EditText,
    internal var code: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var clickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                holder.code.text = jsonObject!!.getString("ID_Product")
                holder.name.text = jsonObject!!.getString("ProductName")
                holder.llprodstatus.setOnClickListener(View.OnClickListener {
                    if (clickListener != null) {
                        edtProduct!!.setText(jsonObject!!.getString("ProductName"))
                        code = jsonObject!!.getString("ID_Product")
                        clickListener!!.onClick(position, "Product",jsonObject);
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
        var code: TextView
        var name: TextView
        var llprodstatus: LinearLayout

        init {
            code = v.findViewById<View>(R.id.code) as TextView
            name = v.findViewById<View>(R.id.name) as TextView
            llprodstatus = v.findViewById<View>(R.id.llprodstatus) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListenerData?) {
        clickListener = itemClickListener
    }

}