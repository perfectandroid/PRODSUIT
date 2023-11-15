package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class HomeGrideCountAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "HomeGrideCountAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_home_grid_count, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                Log.e(TAG,"position112233   "+jsonArray.getJSONObject(position).getString("Task"))
                val pos = position+1
                holder.tvv_home_name.text        = jsonObject!!.getString("Task")
                holder.tvv_home_count.text        = jsonObject!!.getString("Value")
               // holder.tvv_home_count.textColors =
                holder.tvv_home_count.setTextColor(Color.parseColor(jsonObject!!.getString("MobColor")))

                val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
                var IMAGE_URL   = IMAGE_URLSP.getString("IMAGE_URL", null)
                Log.e("TAG","310112  "+IMAGE_URL+""+jsonObject!!.getString("MobImage"))
                PicassoTrustAll.getInstance(context)!!.load(IMAGE_URL+""+jsonObject!!.getString("MobImage")).error(R.drawable.svg_trans).into(holder.image)

              //  rrrrr
//                val resourceId = context.resources.getIdentifier(jsonObject!!.getString("sheetal_logoround"), "drawable", context.packageName)
//                holder.image.setImageDrawable(context.resources.getDrawable(resourceId))
//                holder.txtGridNotification.text        = jsonObject!!.getString("count")



//                if (position.equals(0)){
//
//                    val d: Drawable = context.resources.getDrawable(R.drawable.authorization_home1)
//                    holder.image.setImageDrawable(d)
////                    holder.tvv_home_count.setTextColor()
//                    holder.tvv_home_count!!.setTextColor(ContextCompat.getColorStateList(context,R.color.green1))
//                }
//
//                if (position.equals(1)){
//
//                    val d: Drawable = context.resources.getDrawable(R.drawable.lead_home_1)
//                    holder.image.setImageDrawable(d)
//                    holder.tvv_home_count!!.setTextColor(ContextCompat.getColorStateList(context,R.color.ColorWarm))
//                }
//                if (position.equals(2)){
//
//                    val d: Drawable = context.resources.getDrawable(R.drawable.service_home1)
//                    holder.image.setImageDrawable(d)
//                    holder.tvv_home_count!!.setTextColor(ContextCompat.getColorStateList(context,R.color.leadstatus_color5))
//                }
//                if (position.equals(3)){
//
//                    val d: Drawable = context.resources.getDrawable(R.drawable.pickup_delivery_home1)
//                    holder.image.setImageDrawable(d)
//                    holder.tvv_home_count!!.setTextColor(ContextCompat.getColorStateList(context,R.color.pop_gradient3))
//                }
//
//
//                if (jsonObject!!.getString("grid_id").equals("3")){
//
//
//                }

                holder.ll_homedash!!.setTag(position)
                holder.ll_homedash!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "homeDashClicks"
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tvv_home_name          : TextView
        internal var tvv_home_count          : TextView
//        internal var txtGridNotification  : TextView
        internal var image                : ImageView
        internal var ll_homedash          : LinearLayout

        init {
            tvv_home_count          = v.findViewById<View>(R.id.tvv_home_count) as TextView
            tvv_home_name           = v.findViewById<View>(R.id.tvv_home_name) as TextView
            image                   = v.findViewById<View>(R.id.image) as ImageView
//            txtGridNotification   = v.findViewById<View>(R.id.txtGridNotification) as TextView
            ll_homedash           = v.findViewById<View>(R.id.ll_homedash) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}