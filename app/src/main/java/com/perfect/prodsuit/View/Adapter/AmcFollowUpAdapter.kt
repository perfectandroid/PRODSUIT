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


class AmcFollowUpAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_amc, parent, false
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
                holder.tv_type.text = jsonObject!!.getString("type")
                holder.tv_dueDate.text = jsonObject!!.getString("dueDate")
                holder.tv_renewDate.setText(jsonObject!!.getString("renewDate"))
                holder.tv_documentType.setText(jsonObject!!.getString("docType"))
                holder.img_download.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "download", jsonObject!!);
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
        var tv_type: TextView
        var tv_dueDate: TextView
        var tv_renewDate: TextView
        var tv_documentType:TextView
        var lin_main:LinearLayout
        var img_download:ImageView


        init {
            tv_type = v.findViewById(R.id.tv_type) as TextView
            tv_dueDate = v.findViewById(R.id.tv_dueDate) as TextView
            tv_renewDate = v.findViewById(R.id.tv_renewDate) as TextView
            tv_documentType = v.findViewById(R.id.tv_documentType) as TextView
            lin_main = v.findViewById(R.id.lin_main) as LinearLayout
            img_download = v.findViewById(R.id.img_download) as ImageView
        }
    }

    fun addItemClickListener(listener: ItemClickListenerData) {
        mItemClickListener = listener
    }


}