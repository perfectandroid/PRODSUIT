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

class DocumentSubAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "DocumentSubAdapter"
    internal var jsonObject: JSONObject? = null
    // private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_sub_document, parent, false
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
                holder.txtName.text        = jsonObject!!.getString("LgCusName")
                holder.txtAddress.text     = jsonObject!!.getString("LgCusAddress")
                holder.txtMobile.text      = jsonObject!!.getString("LgCusMobile")

                if (position % 2 == 0){
                    holder.llSubInfo!!.setBackgroundColor(context.getColor(R.color.greylight))
                }
                else{
                    holder.llSubInfo!!.setBackgroundColor(context.getColor(R.color.white))
                }
//
//                holder.llfollowuptype!!.setTag(position)
//                holder.llfollowuptype!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "followuptype")
//
//                })
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

        internal var txtName      : TextView
        internal var txtAddress   : TextView
        internal var txtMobile    : TextView
        internal var llSubInfo    : LinearLayout

        init {
            txtName        = v.findViewById<View>(R.id.txtName) as TextView
            txtAddress     = v.findViewById<View>(R.id.txtAddress) as TextView
            txtMobile      = v.findViewById<View>(R.id.txtMobile) as TextView
            llSubInfo      = v.findViewById<View>(R.id.llSubInfo) as LinearLayout
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}