package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject


class AmcFollowUpListAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_amc_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                var isChecked = ""
                val pos = position
                holder.tv_invoice.text = jsonObject!!.getString("invoiceNo")
                holder.tv_date.text = jsonObject!!.getString("date")
                holder.tv_dealer.setText(jsonObject!!.getString("dealer"))
                holder.lin_main.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "amc", jsonObject!!);
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
        var tv_invoice: TextView
        var tv_date: TextView
        var tv_dealer: TextView
        var lin_main:LinearLayout


        init {
            tv_invoice = v.findViewById(R.id.tv_invoice) as TextView
            tv_date = v.findViewById(R.id.tv_date) as TextView
            tv_dealer = v.findViewById(R.id.tv_dealer) as TextView
            lin_main = v.findViewById(R.id.lin_main) as LinearLayout
        }
    }

    fun addItemClickListener(listener: ItemClickListenerData) {
        mItemClickListener = listener
    }


}