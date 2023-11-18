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

class AuthorizationExpandAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "AuthorizationExpandAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    internal var rcyexpand: RecyclerView? = null
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.child_details_minidash, parent, false
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
                holder.tv_custmr.text        = jsonObject!!.getString("CusName")
                holder.tv_mobile.text        = jsonObject!!.getString("CusMobile")
                holder.tv_TransDate.text     = jsonObject!!.getString("TransDate")
                holder.txtv_collectedby.text = jsonObject!!.getString("EnteredBy")
                holder.tvv_address.text      = jsonObject!!.getString("CusAddress")
                holder.txtv_Priority.text    = jsonObject!!.getString("Priority")



                if(jsonObject!!.getString("Priority").equals("Hot")){
                    holder.imvpriority.setImageResource(R.drawable.preference2)
                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorHot))
                }else if (jsonObject!!.getString("Priority").equals("Warm")){
                    holder.imvpriority.setImageResource(R.drawable.preference3)
                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorWarm))

                }else if (jsonObject!!.getString("Priority").equals("Cold")){
                    holder.imvpriority.setImageResource(R.drawable.preference1)
                    holder.imvpriority.setColorFilter(context.getColor(R.color.ColorCold))

                }
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
        internal var tv_TransDate      : TextView
        internal var tv_custmr         : TextView
        internal var tv_entered_date   : TextView
        internal var tvv_nodata        : TextView
        internal var tv_mobile         : TextView
        internal var txtv_collectedby  : TextView
        internal var tvv_address       : TextView
        internal var card_details      : CardView
        internal var txtv_Priority     : TextView
        internal var imvpriority       : ImageView
        init {
            tv_leadno                = v.findViewById<View>(R.id.tv_leadno) as TextView
            tv_TransDate             = v.findViewById<View>(R.id.tv_TransDate) as TextView
            tv_custmr                = v.findViewById<View>(R.id.tv_custmr) as TextView
            tv_entered_date          = v.findViewById<View>(R.id.tv_entered_date) as TextView
            tvv_nodata               = v.findViewById<View>(R.id.tvv_nodata) as TextView
            tv_mobile                = v.findViewById<View>(R.id.tv_mobile) as TextView
            txtv_collectedby         = v.findViewById<View>(R.id.txtv_collectedby) as TextView
            tvv_address              = v.findViewById<View>(R.id.tvv_address) as TextView
            card_details             = v.findViewById<View>(R.id.card_details) as CardView
            txtv_Priority            = v.findViewById<View>(R.id.txtv_Priority) as TextView
            imvpriority              = v.findViewById<View>(R.id.imvpriority)     as ImageView
        }
    }


//    fun setClickListener(itemClickListener: Context) {
//        context = itemClickListener
//    }
}