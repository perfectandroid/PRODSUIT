package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class TodoListAdapter(internal var context: Context, internal var jsonArray: JSONArray):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "TodoListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_todolist, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                Log.i("Todo Date", jsonObject!!.getString("LgLeadDate"))
                var date = jsonObject!!.getString("LgLeadDate")
                var spf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("dd-MM-yyyy")
                date = spf.format(newDate)
                println(date)

             //   internal var impreference    : ImageView

                holder.txtv_date.text        = date
                holder.tv_custmr.text        = jsonObject!!.getString("LgCusName")
                holder.tv_product.text        = "Product Name : "+jsonObject!!.getString("ProdName")
                holder.txtv_collectedby.text        = "Collected By : "+jsonObject!!.getString("LgCollectedBy")
                holder.txtv_preference.text        = jsonObject!!.getString("Preference")
                holder.tv_nextdate.text        = "Next Action Date : "+jsonObject!!.getString("NextActionDate")
                holder.tv_leadno.text        = jsonObject!!.getString("LeadNo")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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
        internal var txtv_date   : TextView
        internal var txtv_preference       : TextView
        internal var tv_leadno    : TextView
        internal var tv_custmr    : TextView
        internal var tv_address    : TextView
        internal var tv_product    : TextView
        internal var tv_nextdate    : TextView
        internal var txtv_collectedby    : TextView
        internal var impreference    : ImageView
        internal var imcall    : ImageView
        internal var immessage    : ImageView
       init {
           txtv_date          = v.findViewById<View>(R.id.txtv_date) as TextView
           txtv_preference          = v.findViewById<View>(R.id.txtv_preference) as TextView
           tv_leadno          = v.findViewById<View>(R.id.tv_leadno) as TextView
           tv_custmr          = v.findViewById<View>(R.id.tv_custmr) as TextView
           tv_address              = v.findViewById<View>(R.id.tv_address) as TextView
           tv_product           = v.findViewById<View>(R.id.tv_product) as TextView
           tv_nextdate           = v.findViewById<View>(R.id.tv_nextdate) as TextView
           txtv_collectedby           = v.findViewById<View>(R.id.txtv_collectedby) as TextView
           impreference           = v.findViewById<View>(R.id.impreference) as ImageView
           imcall           = v.findViewById<View>(R.id.imcall) as ImageView
           immessage           = v.findViewById<View>(R.id.immessage) as ImageView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}