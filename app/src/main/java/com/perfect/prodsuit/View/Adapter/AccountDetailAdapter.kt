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

class AccountDetailAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "AccountDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_account_detail, parent, false
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
                holder.txtAccount.text        = jsonObject!!.getString("name")
                holder.imgIcon.setImageResource(jsonObject!!.getInt("image"))
                if (position == row_index){
                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_selected)
                }else{
                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_default)
                }
                holder.llaccontdetail!!.setTag(position)
                holder.llaccontdetail!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        jsonObject!!.getString("name")
                    )
                    row_index=position;
                    notifyDataSetChanged()
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
        internal var imgIcon           : ImageView
        internal var txtAccount        : TextView
        internal var llaccontdetail    : LinearLayout
        init {
            imgIcon                  = v.findViewById<View>(R.id.imgIcon) as ImageView
            txtAccount               = v.findViewById<View>(R.id.txtAccount) as TextView
            llaccontdetail           = v.findViewById<View>(R.id.llaccontdetail) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}