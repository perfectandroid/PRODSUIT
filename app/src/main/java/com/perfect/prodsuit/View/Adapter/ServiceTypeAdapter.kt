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
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceTypeAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceTypeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_type, parent, false
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
                holder.txtsino.text        = pos.toString()
                holder.txtServiceType.text        = jsonObject!!.getString("ServiceTypeName")

                holder.llServiceType!!.setTag(position)
                holder.llServiceType!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "serviceTypeClick")
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
        internal var txtsino          : TextView
        internal var txtServiceType          : TextView
        internal var llServiceType    : LinearLayout
        init {
            txtsino        = v.findViewById<View>(R.id.txtsino) as TextView
            txtServiceType        = v.findViewById<View>(R.id.txtServiceType) as TextView
            llServiceType  = v.findViewById<View>(R.id.llServiceType) as LinearLayout
        }
    }



    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}