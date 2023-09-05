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

class ComplaintTypeAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ComplaintTypeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_complaint_type, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   555221144   "+position)
                val pos = position+1
                holder.txtsino.text             = pos.toString()
                holder.txtcomplaint.text        = jsonObject!!.getString("ComplaintName")

                Log.e(TAG,"ComplaintName   1051   "+jsonObject!!.getString("ComplaintName"))

                holder.llcomplaint!!.setTag(position)
                holder.llcomplaint!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "ComplaintType")
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
        internal var txtcomplaint       : TextView
        internal var txtsino           : TextView
        internal var llcomplaint    : LinearLayout
        init {
            txtcomplaint        = v.findViewById<View>(R.id.txtcomplaint) as TextView
            txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            llcomplaint     = v.findViewById<View>(R.id.llcomplaint) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}