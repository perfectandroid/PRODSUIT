package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ProjectLeadListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectLeadListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_project_leadlist, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_Date.text        = jsonObject!!.getString("LeadDate")
                holder.tv_LeadNo.text        =jsonObject!!.getString("LeadNo")
                holder.tv_Name.text        = jsonObject!!.getString("CustomeName")
                holder.tv_Mobile.text        = jsonObject!!.getString("MobileNo")
                holder.tv_Address.text        = jsonObject!!.getString("CusAddress")

                if (jsonObject!!.getString("IsSiteVisit").equals("1")){
                    holder.img_visited.visibility = View.VISIBLE
                }else{
                    holder.img_visited.visibility = View.GONE
                }


                holder.ll_leadlist!!.setTag(position)
                holder.ll_leadlist!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "LeadListClick")
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
        internal var tv_Date   : TextView
        internal var tv_LeadNo   : TextView
        internal var tv_Mobile   : TextView
        internal var tv_Name   : TextView
        internal var tv_Address   : TextView
        internal var img_visited   : ImageView
        internal var ll_leadlist   : LinearLayout
        init {
            tv_Date          = v.findViewById<View>(R.id.tv_Date) as TextView
            tv_LeadNo          = v.findViewById<View>(R.id.tv_LeadNo) as TextView
            tv_Mobile          = v.findViewById<View>(R.id.tv_Mobile) as TextView
            tv_Name          = v.findViewById<View>(R.id.tv_Name) as TextView
            tv_Address          = v.findViewById<View>(R.id.tv_Address) as TextView
            img_visited          = v.findViewById<View>(R.id.img_visited) as ImageView
            ll_leadlist          = v.findViewById<View>(R.id.ll_leadlist) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}