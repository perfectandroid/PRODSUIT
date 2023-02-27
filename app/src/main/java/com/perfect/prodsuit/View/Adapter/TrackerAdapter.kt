package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class TrackerAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val TAG : String = "TrackerAdapter"
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_tracker, parent, false
        )
        vh = MainViewHolder(v)
        return vh
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
               // holder.txtDate.text        = pos.toString()
                holder.txtMessage.text     = jsonObject!!.getString("Description")

                if (position == 0){
                    holder.txtLineStart!!.visibility = View.INVISIBLE
                }

                if (position == jsonArray.length()-1){
                    holder.txtLineEnd!!.visibility = View.INVISIBLE
                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }


    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtDate       : TextView
        internal var txtMessage    : TextView
        internal var txtLineEnd    : TextView
        internal var txtLineStart  : TextView

        init {
            txtDate        = v.findViewById<View>(R.id.txtDate) as TextView
            txtMessage     = v.findViewById<View>(R.id.txtMessage) as TextView
            txtLineEnd     = v.findViewById<View>(R.id.txtLineEnd) as TextView
            txtLineStart   = v.findViewById<View>(R.id.txtLineStart) as TextView

        }
    }
}