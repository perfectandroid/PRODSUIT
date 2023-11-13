package com.perfect.prodsuit.View.Adapter

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

class AreaDetailAdapter (internal var context: Context, internal var jsonArray: JSONArray):
   RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AreaDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_area_list, parent, false
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
                holder.txtArea.text        = jsonObject!!.getString("Area")

                holder.llArea!!.setTag(position)
                holder.llArea!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "areadetail"
                    )
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtSino          : TextView
        internal var txtArea          : TextView
        internal var llArea    : LinearLayout
        init {
            txtSino        = v.findViewById<View>(R.id.txtSino) as TextView
            txtArea        = v.findViewById<View>(R.id.txtArea) as TextView
            llArea  = v.findViewById<View>(R.id.llArea) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}