package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray


class PaymentMethodAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_payment_method, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                var isChecked = "true"
                val pos = position
                holder.txt_method.text = jsonObject!!.getString("method")
                holder.txt_reff.setText(jsonObject!!.getString("reff"))
                holder.txt_amount.setText(jsonObject!!.getString("amount"))
                holder.img_delete.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "delete");
                    }
                })
                holder.img_edit.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "edit");
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("sadsdsssss", "e  " + e)
        }
    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        lateinit var txt_method: TextView
        lateinit var txt_reff: TextView
        lateinit var txt_amount: TextView
        lateinit var img_delete: ImageView
        lateinit var img_edit: ImageView

        init {
            txt_method = v.findViewById(R.id.txt_method) as TextView
            txt_reff = v.findViewById(R.id.txt_reff) as TextView
            txt_amount = v.findViewById(R.id.txt_amount) as TextView
            img_delete = v.findViewById(R.id.img_delete) as ImageView
            img_edit = v.findViewById(R.id.img_edit) as ImageView
        }
    }

    fun addItemClickListener(listener: ItemClickListener) {
        mItemClickListener = listener
    }

}