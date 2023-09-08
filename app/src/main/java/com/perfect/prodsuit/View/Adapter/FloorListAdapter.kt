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

class FloorListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "FloorListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_floor_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)

            Log.e(TAG,"jsonObject 96589  "+jsonObject!!.getString("LocationName"))

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   111224455   "+position)
                val pos = position+1
                holder.txtsino.text        = pos.toString()
                holder.txtfloorlist.text        = jsonObject!!.getString("LocationName")

                holder.llfloorlist!!.setTag(position)
                holder.llfloorlist!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "ProductLocationList")
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
        internal var txtfloorlist       : TextView
        internal var txtsino           : TextView
        internal var llfloorlist    : LinearLayout
        init {
            txtfloorlist        = v.findViewById<View>(R.id.txtfloorlist) as TextView
            txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            llfloorlist     = v.findViewById<View>(R.id.llfloorlist) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}