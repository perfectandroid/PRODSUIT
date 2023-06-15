package com.perfect.prodsuit.View.Adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Model.ModelTracker
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class MapRootDetailAdapter  (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MapRootDetailAdapter"
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_maproot_detail, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val animator = ObjectAnimator.ofFloat(holder.ll_main, "alpha", 0f, 1.5f)
                animator.duration = 2500 // Animation duration in milliseconds
                animator.start()
                val pos = position+1
                holder.txtBattery.text        = jsonObject!!.getString("ChargePercentage")+" %"
                holder.txtTime.text        = jsonObject!!.getString("EnteredTime")
                holder.txtAddress.text        = jsonObject!!.getString("LocLocationName")

//                val drawable = ContextCompat.getDrawable(context, R.drawable.shape_dashed)
//                holder.txtLineEnd.background = drawable
                if (position == 0){
                    holder.txtLineStart!!.visibility = View.INVISIBLE
                }

                if (position == jsonArray.length()-1){
                    holder.txtLineEnd!!.visibility = View.INVISIBLE
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtBattery   : TextView
        internal var txtTime      : TextView
        internal var txtAddress   : TextView
        internal var txtLineEnd   : TextView
        internal var txtLineStart : TextView
        internal var ll_main      : LinearLayout

        init {
            txtBattery            = v.findViewById<View>(R.id.txtBattery) as TextView
            txtTime               = v.findViewById<View>(R.id.txtTime) as TextView
            txtAddress            = v.findViewById<View>(R.id.txtAddress) as TextView
            txtLineEnd            = v.findViewById<View>(R.id.txtLineEnd) as TextView
            txtLineStart          = v.findViewById<View>(R.id.txtLineStart) as TextView
            ll_main               = v.findViewById<View>(R.id.ll_main) as LinearLayout

        }
    }
}