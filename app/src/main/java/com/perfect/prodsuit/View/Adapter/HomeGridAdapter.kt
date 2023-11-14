package com.perfect.prodsuit.View.Adapter

import android.R.attr.name
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


class HomeGridAdapter(internal var context: Context, internal var jsonArray: JSONArray, internal var notificationCount: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "HomeGridAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adpate_home_grid, parent, false
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
                holder.txtGridName.text        = jsonObject!!.getString("grid_name")
                val resourceId = context.resources.getIdentifier(jsonObject!!.getString("image"), "drawable", context.packageName)
                holder.image.setImageDrawable(context.resources.getDrawable(resourceId))
                holder.txtGridNotification.text        = jsonObject!!.getString("count")
                if (jsonObject!!.getString("grid_id").equals("3")){
                 //   holder.txtGridNotification.visibility = View.VISIBLE

                }

                holder.ll_homeGrid!!.setTag(position)
                holder.ll_homeGrid!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "homeGrid"
                    )
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
        internal var txtGridName          : TextView
        internal var txtGridNotification  : TextView
        internal var image                : ImageView
        internal var ll_homeGrid          : LinearLayout

        init {
            txtGridName           = v.findViewById<View>(R.id.txtGridName) as TextView
            txtGridNotification   = v.findViewById<View>(R.id.txtGridNotification) as TextView
            image                 = v.findViewById<View>(R.id.image) as ImageView
            ll_homeGrid           = v.findViewById<View>(R.id.ll_homeGrid) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}