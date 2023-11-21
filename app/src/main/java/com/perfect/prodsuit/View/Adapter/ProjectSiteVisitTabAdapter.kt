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

class ProjectSiteVisitTabAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectSiteVisitTabAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_projectsite_visittab, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.txtv_label.text        = jsonObject!!.getString("Label_Name")
                holder.tv_newCount.text        = jsonObject!!.getString("Count")

                if (position == 0){
                  //  holder.tv_newCount.visibility = View.GONE
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.color_site_tab_10))
                    holder.tv_newCount!!.setBackgroundResource(R.color.color_site_tab_10)
                    holder.ll_tab!!.setBackgroundResource(R.drawable.border_sitevisit_1)
                    holder.ll_firstbg!!.setBackgroundResource(R.drawable.shapes_sitevisit_1)
                    holder.img_icon!!.setBackgroundResource(R.drawable.sicon_new)

                }
                else if (position == 1){
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.color_site_tab_20))
                    holder.tv_newCount!!.setBackgroundResource(R.color.color_site_tab_20)
                    holder.ll_tab!!.setBackgroundResource(R.drawable.border_sitevisit_2)
                    holder.ll_firstbg!!.setBackgroundResource(R.drawable.shapes_sitevisit_2)
                    holder.img_icon!!.setBackgroundResource(R.drawable.sicon_todo)
                }

                else if (position == 2){
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.color_site_tab_30))
                    holder.tv_newCount!!.setBackgroundResource(R.color.color_site_tab_30)
                    holder.ll_tab!!.setBackgroundResource(R.drawable.border_sitevisit_3)
                    holder.ll_firstbg!!.setBackgroundResource(R.drawable.shapes_sitevisit_3)
                    holder.img_icon!!.setBackgroundResource(R.drawable.sicon_overdue)

                }
                else if (position == 3){
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.color_site_tab_40))
                    holder.tv_newCount!!.setBackgroundResource(R.color.color_site_tab_40)
                    holder.ll_tab!!.setBackgroundResource(R.drawable.border_sitevisit_4)
                    holder.ll_firstbg!!.setBackgroundResource(R.drawable.shapes_sitevisit_4)
                    holder.img_icon!!.setBackgroundResource(R.drawable.sicon_upcoming)

                }

                holder.ll_tab!!.setTag(position)
                holder.ll_tab!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "SiteVisitTabClick")
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
        internal var txtv_label   : TextView
        internal var tv_newCount   : TextView
        internal var img_icon   : ImageView
//        internal var txtsino         : TextView
        internal var ll_firstbg    : LinearLayout
        internal var ll_tab    : LinearLayout
        init {
            txtv_label          = v.findViewById<View>(R.id.txtv_label) as TextView
            tv_newCount          = v.findViewById<View>(R.id.tv_newCount) as TextView
            img_icon          = v.findViewById<View>(R.id.img_icon) as ImageView
//            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            ll_firstbg           = v.findViewById<View>(R.id.ll_firstbg) as LinearLayout
            ll_tab           = v.findViewById<View>(R.id.ll_tab) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}