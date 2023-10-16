package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CommonSearchListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "CommonSearchListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_common_searchlist, parent, false)
        vh = MainViewHolder(v)

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.ll_leadNo.getBackground().setTint(holder.ll_leadNo.getResources().getColor(R.color.colorPrimary));
//                holder.tv_authdash_name.text        = jsonObject!!.getString("label")
//                holder.tv_authdash_count.text        = jsonObject!!.getString("count")
//
//                holder.tv_authdash_count.setTextColor(Color.parseColor(jsonObject!!.getString("color")))
//
//                val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
//                var IMAGE_URL   = IMAGE_URLSP.getString("IMAGE_URL", null)
//                Log.e("TAG","310112  "+IMAGE_URL+""+jsonObject!!.getString("icon"))
//                PicassoTrustAll.getInstance(context)!!.load(IMAGE_URL+""+jsonObject!!.getString("icon")).error(R.drawable.svg_trans).into(holder.image_authdash)
//
//                holder.ll_authdash!!.setTag(position)
//                holder.ll_authdash!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "authDashClicks")
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
         return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        internal var tv_authdash_name      : TextView
//        internal var tv_authdash_count     : TextView
//        internal var image_authdash        : ImageView
        internal var ll_leadNo           : LinearLayout

        init {
//            tv_authdash_count     = v.findViewById<View>(R.id.tv_authdash_count) as TextView
//            tv_authdash_name      = v.findViewById<View>(R.id.tv_authdash_name) as TextView
//            image_authdash        = v.findViewById<View>(R.id.image_authdash) as ImageView
            ll_leadNo           = v.findViewById<View>(R.id.ll_leadNo) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}