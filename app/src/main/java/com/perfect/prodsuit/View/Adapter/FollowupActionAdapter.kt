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

class FollowupActionAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "FollowupActionAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_followup_action, parent, false
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
                holder.txtFollowupAction.text        = jsonObject!!.getString("NxtActnName")

                if (position % 2 == 0){
                    holder.llfollowupaction!!.setBackgroundColor(context.getColor(R.color.greylight))
                }
                else{
                    holder.llfollowupaction!!.setBackgroundColor(context.getColor(R.color.white))
                }

                holder.llfollowupaction!!.setTag(position)
                holder.llfollowupaction!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "followupaction")

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

        internal var txtFollowupAction   : TextView
        internal var txtsino             : TextView
        internal var llfollowupaction    : LinearLayout

        init {
            txtFollowupAction      = v.findViewById<View>(R.id.txtFollowupAction) as TextView
            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            llfollowupaction       = v.findViewById<View>(R.id.llfollowupaction) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}