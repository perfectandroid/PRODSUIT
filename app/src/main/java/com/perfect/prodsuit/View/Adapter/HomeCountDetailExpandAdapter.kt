package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class HomeCountDetailExpandAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "HomeCountDetailExpandAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    internal var rcyexpand: RecyclerView? = null
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.child_home_count_details, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)

            Log.e(TAG,"checckkkk child "+jsonObject)
            if (holder is MainViewHolder) {
                holder.tv_leadno.text        = jsonObject!!.getString("TransNumber")
                holder.tv_CustomerName.text        = jsonObject!!.getString("CustomerName")
                holder.tv_Product.text        = jsonObject!!.getString("Product")
                holder.tv_Date.text     = jsonObject!!.getString("Date")
                holder.txtv_AuthorizedBy.text = jsonObject!!.getString("AuthorizedBy")
                holder.tvv_address.text      = jsonObject!!.getString("Address")
//                holder.txtv_Priority.text    = jsonObject!!.getString("Priority")



//                if(jsonObject!!.getString("Priority").equals("Hot")){
//                    holder.imvpriority.setImageResource(R.drawable.preference2)
//                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorHot))
//                }else if (jsonObject!!.getString("Priority").equals("Warm")){
//                    holder.imvpriority.setImageResource(R.drawable.preference3)
//                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorWarm))
//
//                }else if (jsonObject!!.getString("Priority").equals("Cold")){
//                    holder.imvpriority.setImageResource(R.drawable.preference1)
//                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorCold))
//
//                }
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
        internal var tv_leadno         : TextView
        internal var tv_Date             : TextView
        internal var tv_CustomerName     : TextView
        internal var tv_Product          : TextView
        internal var txtv_AuthorizedBy   : TextView
        internal var card_details      : CardView
        internal var tvv_address       : TextView


//        internal var tvv_nodata        : TextView
//        internal var txtv_collectedby  : TextView
//        internal var txtv_Priority     : TextView
//        internal var imvpriority       : ImageView
        init {
            tv_leadno                = v.findViewById<View>(R.id.tv_leadno) as TextView
            tv_Date                   = v.findViewById<View>(R.id.tv_Date) as TextView
            tv_CustomerName           = v.findViewById<View>(R.id.tv_CustomerName) as TextView
            tv_Product                = v.findViewById<View>(R.id.tv_Product) as TextView
            txtv_AuthorizedBy          = v.findViewById<View>(R.id.txtv_AuthorizedBy) as TextView
            card_details             = v.findViewById<View>(R.id.card_details) as CardView
            tvv_address              = v.findViewById<View>(R.id.tvv_address) as TextView

//            tvv_nodata               = v.findViewById<View>(R.id.tvv_nodata) as TextView
//            txtv_collectedby         = v.findViewById<View>(R.id.txtv_collectedby) as TextView
//            txtv_Priority            = v.findViewById<View>(R.id.txtv_Priority) as TextView
//            imvpriority              = v.findViewById<View>(R.id.imvpriority)     as ImageView

        }
    }


//    fun setClickListener(itemClickListener: Context) {
//        context = itemClickListener
//    }
}